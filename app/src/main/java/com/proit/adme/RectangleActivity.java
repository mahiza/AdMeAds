package com.proit.adme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.proit.admeads.AdME;
import com.proit.admeads.AdMeRectangleView;
import com.proit.admeads.AdMeView;
import com.proit.admeads.AdsSever;

public class RectangleActivity extends AppCompatActivity implements AdME.AdMeAdsService {
    private AdME adMe;
    private static final String TAG = "AdME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectangle);

        adMe = new AdME("MI-ATHUMANI-BAKARI-MAHIZA", "PRO-ADME-01");
        adMe.Initialize(this);
    }

    @Override
    public void onInitializationComplete(AdsSever adServer) {
        AdMeRectangleView rootRectangle = findViewById(R.id.adMeRectangle);
        AdMeView adRectangleView = rootRectangle.findViewById(com.proit.admeads.R.id.adRectangleView);
        adMe.showRectangleAd(adServer, adRectangleView);
    }
}