package com.example.goldaj.firstapp;

import android.os.AsyncTask;

/** Per-request network worker; callbacks always execute on the Android main thread. */
public final class DataRetriever extends AsyncTask<Void, Void, AirQualityData> {
    public interface Callback {
        void onResult(AirQualityData data);
    }

    private final String city;
    private final String token;
    private final Callback callback;

    public DataRetriever(String city, String token, Callback callback) {
        this.city = city;
        this.token = token;
        this.callback = callback;
    }

    @Override
    protected AirQualityData doInBackground(Void... ignored) {
        return AirQualityClient.fetch(city, token);
    }

    @Override
    protected void onPostExecute(AirQualityData data) {
        if (callback != null) callback.onResult(data);
    }
}
