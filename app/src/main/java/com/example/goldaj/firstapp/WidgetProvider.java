
package com.example.goldaj.firstapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Date;

/**
 * home screen widget provider
 */
public class WidgetProvider extends AppWidgetProvider {
    public String LOGGER = this.getClass().getSimpleName();
    private static String ROOT_URL = "http://api.waqi.info/feed/";
    private static String TOKEN_REQUEST = "?token=" + BuildConfig.WAQI_API_TOKEN;

    private DataRetriever mDataRetriever = new DataRetriever();
    private String mLink;
    private String mCity;

    @Override
    public void onEnabled(Context context) {
        Intent intent = new Intent (context, WidgetActivity.class);
        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity (intent);
    }

    /**
     * Overwritten method called when widget updates
     * @param context calling context
     * @param appWidgetManager widget manager
     * @param appWidgetIds widget ids (if several widgets on screen)
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(LOGGER, "on update");

        for (int currentWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, currentWidgetId);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_provider);

            Log.d(LOGGER, "do stuff here");
            remoteViews.setTextViewText(R.id.cityValue_id, "Hello Provider! " + new Date());

            Intent intent = new Intent(context, WidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setOnClickPendingIntent(R.id.widgetLayout_id , pendingIntent);

            mLink = (ROOT_URL + mCity + TOKEN_REQUEST);
            mDataRetriever.execute(mLink);
            treatAnswer(remoteViews);

            appWidgetManager.updateAppWidget(currentWidgetId, remoteViews);
        }
    }

    /**
     * Used to update widget
     * @param context calling context
     * @param appWidgetManager widget manager
     * @param appWidgetId widget ids
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_provider);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Treat data received
     * TODO:
     */
    public void treatAnswer(RemoteViews remoteViews) {
        Log.d(LOGGER,"treating answer");

        remoteViews.setTextViewText(R.id.aqiValue_id, mDataRetriever.getQualityIndex());
        remoteViews.setTextViewText(R.id.temperatureValue_id, mDataRetriever.getTemperature());
        remoteViews.setTextViewText(R.id.healthValue_id, mDataRetriever.getInfoSentence());
        remoteViews.setTextViewText(R.id.healthValue_id, mDataRetriever.getInfoSentence());
    }
}
