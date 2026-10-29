package com.example.goldaj.firstapp;

import android.content.Context;
import android.content.SharedPreferences;

/** A separate city for each home-screen widget instance. */
public final class CitySettings {
    private static final String PREFS = "air_quality_widget_cities";
    private CitySettings() {}

    public static String city(Context context, int widgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getString("city_" + widgetId, "Paris");
    }

    public static void setCity(Context context, int widgetId, String raw) {
        String city = AirQualityRules.normalizeCity(raw);
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
                .putString("city_" + widgetId, city).apply();
    }

    public static void delete(Context context, int widgetId) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
                .remove("city_" + widgetId).apply();
    }
}
