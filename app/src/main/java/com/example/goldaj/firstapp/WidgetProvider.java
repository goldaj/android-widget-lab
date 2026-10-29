package com.example.goldaj.firstapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.AsyncTask;
import android.widget.RemoteViews;

/** Independent AQI instances, each refreshing only after its own network callback. */
public final class WidgetProvider extends AppWidgetProvider {
    public static final String ACTION_REFRESH = "com.example.goldaj.firstapp.REFRESH_AQI";

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] widgetIds) {
        for (int id : widgetIds) updateAppWidget(context, manager, id);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_REFRESH.equals(intent.getAction())) {
            int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            if (id != AppWidgetManager.INVALID_APPWIDGET_ID) {
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                if (manager.getAppWidgetInfo(id) != null) updateAppWidget(context, manager, id);
            }
        }
    }

    @Override
    public void onDeleted(Context context, int[] widgetIds) {
        for (int id : widgetIds) CitySettings.delete(context, id);
        super.onDeleted(context, widgetIds);
    }

    public static void updateAppWidget(final Context context,
                                       final AppWidgetManager manager, final int widgetId) {
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID ||
                manager.getAppWidgetInfo(widgetId) == null) return;
        final Context app = context.getApplicationContext();
        final String city = CitySettings.city(app, widgetId);
        render(app, manager, widgetId, city, "…", "Chargement…", "—", "—");

        final DataRetriever request = new DataRetriever(city, BuildConfig.WAQI_API_TOKEN,
                new DataRetriever.Callback() {
                    @Override
                    public void onResult(AirQualityData data) {
                        if (manager.getAppWidgetInfo(widgetId) == null) return;
                        // Stale response from an earlier city selection must not overwrite new data.
                        if (!city.equals(CitySettings.city(app, widgetId))) return;
                        String description = data.error != null ? data.error : data.description;
                        render(app, manager, widgetId, city,
                                data.indexLabel(), description, data.temperature, data.updatedAt);
                    }
                });
        request.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private static void render(Context context, AppWidgetManager manager, int widgetId,
                               String city, String aqi, String description,
                               String temperature, String updated) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_provider);
        views.setTextViewText(R.id.cityValue_id, city + "  ·  toucher pour actualiser");
        views.setTextViewText(R.id.aqiValue_id, aqi);
        views.setTextViewText(R.id.healthValue_id, description);
        views.setTextViewText(R.id.temperatureValue_id, temperature);
        views.setTextViewText(R.id.updatedValue_id, "Mis à jour : " + updated);

        Intent refresh = new Intent(context, WidgetProvider.class);
        refresh.setAction(ACTION_REFRESH);
        refresh.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) flags |= PendingIntent.FLAG_IMMUTABLE;
        PendingIntent intent = PendingIntent.getBroadcast(context, widgetId, refresh, flags);
        views.setOnClickPendingIntent(R.id.widgetLayout_id, intent);
        manager.updateAppWidget(widgetId, views);
    }
}
