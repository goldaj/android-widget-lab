/*
TODO:
utiliser jsoup et gérer si la ville n'existe pas
Créer le layout pour la popup de selection de ville avant le widget
check si c'est WidgetProvider qui call widgetActivity ou l'inverse
Regarder comment passer valeur de widgetActivity vers widgetProvider (pour le nom de la ville).
 */

package com.example.goldaj.firstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Main activity, when launching the app
 */
public class MainActivity extends Activity {

    public String LOGGER = this.getClass().getSimpleName();

    private static String ROOT_URL = "http://api.waqi.info/feed/";
    private static String TOKEN_REQUEST = "?token=" + BuildConfig.WAQI_API_TOKEN;

    private EditText mInputCity;
    private Button mOkButton;
    private String mCity;
    private String mLink;
    private String mQualityIndex;
    private String mInfoSentence;
    private String mUpdateDate;
    private String mTemperature;
    private Context mContext;
    private DataRetriever mDataRetriever = new DataRetriever();

    /**
     * launched when app is created
     * @param savedInstanceState unused overwritten parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mInputCity = (EditText) findViewById(R.id.inputCity_id);
        mOkButton = (Button) findViewById(R.id.okButton_id);
        Log.d(LOGGER,"mInputCity: " + mInputCity.getText().toString());
        mContext = this;
    }

    /**
     * Launched when app resumes
     */
    @Override
    protected void onResume() {
        super.onResume();

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOGGER,"onClick");
                mCity = mInputCity.getText().toString();
                mLink = (ROOT_URL + mCity + TOKEN_REQUEST);
                mDataRetriever.execute(mLink);
                popUpCall();
            }
        });
    }

    /**
     * generating pop-up
     */
    public void popUpCall() {
        Log.d(LOGGER,"creating pop-up");

        mQualityIndex = mDataRetriever.getQualityIndex();
        mInfoSentence = mDataRetriever.getInfoSentence();
        mTemperature = mDataRetriever.getTemperature();
        mUpdateDate = mDataRetriever.getUpdateDate();

        Log.d(LOGGER,"qualityIndex: " + mQualityIndex );
        Log.d(LOGGER,"infoSentence: " + mInfoSentence);
        Log.d(LOGGER,"temperature: " + mTemperature);
        Log.d(LOGGER,"updateDate: " + mUpdateDate);

        //pop-up call
        new AlertDialog.Builder(mContext)
                .setTitle("Air Pollution in " + mCity + ":")
                .setMessage("Air Quality Index: " + mQualityIndex +
                        "\n" + mInfoSentence +
                        "\nTemp.:" + mTemperature + "°C" +
                        "\nUpdated on " + mUpdateDate)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).show();
    }
}