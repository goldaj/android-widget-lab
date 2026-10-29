package com.example.goldaj.firstapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/** Launcher-managed widget configuration; city persists per widget ID. */
public final class WidgetActivity extends Activity {
    private int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        Intent incoming = getIntent();
        if (incoming != null) {
            widgetId = incoming.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        setContentView(R.layout.widget_popup_activity);
        final EditText cityInput = (EditText) findViewById(R.id.widgetCity_id);
        Button submit = (Button) findViewById(R.id.widgetOkButton_id);
        cityInput.setText(CitySettings.city(this, widgetId));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                final String city;
                try {
                    city = AirQualityRules.normalizeCity(cityInput.getText().toString());
                } catch (IllegalArgumentException invalid) {
                    cityInput.setError("Nom de ville invalide");
                    return;
                }
                CitySettings.setCity(WidgetActivity.this, widgetId, city);
                AppWidgetManager manager = AppWidgetManager.getInstance(WidgetActivity.this);
                WidgetProvider.updateAppWidget(WidgetActivity.this, manager, widgetId);
                Intent result = new Intent();
                result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
