package com.example.goldaj.firstapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Asynchronous call to get data from distant server
 */
public class DataRetriever extends AsyncTask<String, Void, String> {
    public String LOGGER = this.getClass().getSimpleName();

    private String mQualityIndex;
    private String mInfoSentence;
    private String mUpdateDate;
    private String mTemperature;

    /**
     * TODO: handle city not found + use Jsoup to get time & temperature
     * @param params url to connect to
     * @return response from server
     */
    @Override
    protected String doInBackground(String... params) {
        boolean ConnectionSuccess;
        String response = "";

        try {
            //Trying to connect to server
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000); //The internet said 10000 is a good value here.
            connection.connect();
            ConnectionSuccess = connection.getResponseCode() == 200;

            //Return null if cannot connect
            if (!ConnectionSuccess) {
                return null;
            }
            //Get server
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            bufferedReader.close();
            response = stringBuilder.toString();

        } catch (IOException e) {
            Log.d(LOGGER, e.getMessage());
        }

        return response;
    }

    /**
     * Calling method to parse data
     * @param data data got from server
     */
    @Override
    protected void onPostExecute(String data) {
        Log.d(LOGGER,"onPostExecute");

        /* Get the aqi value */
        mQualityIndex = genQualityIndex(data);
        Log.d(LOGGER,"qualityIndex: " + mQualityIndex);

        /* Get the info sentence value */
        mInfoSentence = genInfoSentence(mQualityIndex);
        Log.d(LOGGER,"infoSentence: " + mInfoSentence);

        /* TODO */
        /* Get the temperature value */
        mTemperature = "TODO";
        /* Get the update date value */
        mUpdateDate = "TODO";
    }

    /**
     * Parse response to get aqi
     * @param data response from server
     * @return aqi value
     */
    public String genQualityIndex(String data) {
        int strIndex;
        String aqiStr = "\"aqi\":";
        String aqi;

        aqi = data.substring((strIndex = data.indexOf(aqiStr)) + aqiStr.length(),
                data.indexOf(",", strIndex));
        return aqi;
    }

    public String genInfoSentence(String aqiStr) {
        String infoSentence = "";
        int aqi = Integer.parseInt(aqiStr);

        if(aqi < 50)        infoSentence = "Good";
        else if(aqi < 100)  infoSentence = "Moderate";
        else if(aqi < 150)  infoSentence = "Unhealthy for Sensitive people";
        else                infoSentence = "Unhealthy";

        return infoSentence;
    }

    public String getQualityIndex() {
        return mQualityIndex;
    }

    public String getInfoSentence() {
        return mInfoSentence;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public String getUpdateDate() {
        return mUpdateDate;
    }

}
