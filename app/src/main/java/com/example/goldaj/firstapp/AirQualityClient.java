package com.example.goldaj.firstapp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/** WAQI HTTPS adapter: bounded network IO, no logging of API credentials. */
public final class AirQualityClient {
    private static final int MAX_CHARS = 131072;
    private AirQualityClient() {}

    public static AirQualityData fetch(String city, String token) {
        final String cleanCity;
        try {
            cleanCity = AirQualityRules.normalizeCity(city);
        } catch (IllegalArgumentException exception) {
            return AirQualityData.failure("Ville invalide");
        }
        if (token == null || token.trim().length() == 0) return AirQualityData.demo(cleanCity);

        HttpURLConnection connection = null;
        try {
            String encodedCity = URLEncoder.encode(cleanCity, "UTF-8").replace("+", "%20");
            String encodedToken = URLEncoder.encode(token, "UTF-8");
            URL url = new URL("https://api.waqi.info/feed/" + encodedCity + "/?token=" + encodedToken);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setRequestProperty("Accept", "application/json");
            connection.setInstanceFollowRedirects(false);
            if (connection.getResponseCode() != 200) return AirQualityData.failure(cleanCity);
            StringBuilder body = new StringBuilder();
            InputStream stream = connection.getInputStream();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                char[] chunk = new char[2048];
                int read;
                while ((read = reader.read(chunk)) != -1) {
                    if (body.length() + read > MAX_CHARS) return AirQualityData.failure(cleanCity);
                    body.append(chunk, 0, read);
                }
            } finally {
                stream.close();
            }
            JSONObject root = new JSONObject(body.toString());
            if (!"ok".equals(root.optString("status"))) return AirQualityData.failure(cleanCity);
            JSONObject data = root.optJSONObject("data");
            if (data == null) return AirQualityData.failure(cleanCity);
            int aqi = data.optInt("aqi", -1);
            if (aqi < 0) aqi = -1;
            JSONObject iaqi = data.optJSONObject("iaqi");
            JSONObject temperature = iaqi == null ? null : iaqi.optJSONObject("t");
            String temp = temperature == null ? "—" : temperature.optString("v", "—");
            JSONObject time = data.optJSONObject("time");
            String updated = time == null ? "—" : time.optString("s", "—");
            return AirQualityData.live(cleanCity, aqi, temp, updated);
        } catch (Exception exception) {
            // Do not log URL, exception text, token, or remote response.
            return AirQualityData.failure(cleanCity);
        } finally {
            if (connection != null) connection.disconnect();
        }
    }
}
