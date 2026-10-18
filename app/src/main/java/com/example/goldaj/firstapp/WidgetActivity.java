package com.example.goldaj.firstapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Desktop widget activity
 */
public class WidgetActivity extends Activity {
    public String LOGGER = this.getClass().getSimpleName();

    private int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private EditText mCityValue;
    private Button mOkButton;
    private View mWidgetLayout;

    /**
     * Overwritten method call on widget creation
     * @param savedInstanceState instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOGGER, "on create");
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {finish();}
        Intent resultValue = new Intent(this, WidgetActivity.class);

        setContentView(R.layout.widget_popup_activity);
        mCityValue = (EditText) findViewById(R.id.widgetCity_id);
        mOkButton = (Button) findViewById(R.id.widgetOkButton_id);

        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, resultValue);

        configureWidget(getApplicationContext());
    }

    /**
     * Overwritten method called on widget start
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOGGER, "on start");
    }

    /**
     * Unused here
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOGGER,"on resume");
        //Setting click listener
        mWidgetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOGGER,"onTouch");
                mCityValue.setText("Hello Activity!");
                //TODO: send city value to widget
            }
        });

        configureWidget(getApplicationContext());
    }

    /**
     * Configuring & updating widget
     * @param context calling context
     */
    public void configureWidget(Context context) {
        Log.d(LOGGER,"on configure widget");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        WidgetProvider.updateAppWidget(context, appWidgetManager, widgetId);
    }
}
