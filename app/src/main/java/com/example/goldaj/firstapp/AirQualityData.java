package com.example.goldaj.firstapp;

/** Immutable result: demo and errors are always explicit, never silently live. */
public final class AirQualityData {
    public final String city;
    public final int aqi;
    public final String temperature;
    public final String updatedAt;
    public final String description;
    public final boolean demo;
    public final String error;

    private AirQualityData(String city, int aqi, String temperature, String updatedAt,
                           String description, boolean demo, String error) {
        this.city = city;
        this.aqi = aqi;
        this.temperature = temperature;
        this.updatedAt = updatedAt;
        this.description = description;
        this.demo = demo;
        this.error = error;
    }

    public static AirQualityData live(String city, int aqi, String temperature, String updatedAt) {
        return new AirQualityData(city, aqi, temperature, updatedAt,
                AirQualityRules.label(aqi), false, null);
    }

    public static AirQualityData demo(String city) {
        // Deliberately NO fake AQI number: a tokenless build never impersonates live data.
        return new AirQualityData(city, -1, "—", "—",
                "DÉMO : ajoutez un jeton WAQI local pour les données réelles", true, null);
    }

    public static AirQualityData failure(String city) {
        return new AirQualityData(city, -1, "—", "—",
                "Données indisponibles", false, "Service inaccessible ou ville introuvable");
    }

    public String indexLabel() {
        return aqi >= 0 ? Integer.toString(aqi) : "—";
    }
}
