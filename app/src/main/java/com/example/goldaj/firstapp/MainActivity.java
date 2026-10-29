package com.example.goldaj.firstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/** Search for a city; open the dialog only AFTER its result arrives. */
public final class MainActivity extends Activity {
    private EditText inputCity;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.main_activity);
        inputCity = (EditText) findViewById(R.id.inputCity_id);
        searchButton = (Button) findViewById(R.id.okButton_id);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View ignored) {
                final String city;
                try {
                    city = AirQualityRules.normalizeCity(inputCity.getText().toString());
                } catch (IllegalArgumentException invalid) {
                    inputCity.setError("Indiquez une ville valide (80 caractères maximum)");
                    return;
                }
                searchButton.setEnabled(false);
                new DataRetriever(city, BuildConfig.WAQI_API_TOKEN, new DataRetriever.Callback() {
                    @Override
                    public void onResult(AirQualityData data) {
                        if (isFinishing() || (Build.VERSION.SDK_INT >= 17 && isDestroyed())) return;
                        searchButton.setEnabled(true);
                        String detail = (data.error != null ? data.error : data.description)
                                + "\nTempérature : " + data.temperature + " °C"
                                + "\nDonnées datées du : " + data.updatedAt;
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(data.city + " : AQI " + data.indexLabel())
                                .setMessage(detail)
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                    }
                }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }
}
