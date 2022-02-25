package com.proit.adme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.proit.admeads.AdME;
import com.proit.admeads.AdMeBannerView;
import com.proit.admeads.AdMeView;
import com.proit.admeads.AdsSever;

public class BannerActivity extends AppCompatActivity implements AdME.AdMeAdsService {
    private AdME adMe;
    private static final String TAG = "AdME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        adMe = new AdME("MI-ATHUMANI-BAKARI-MAHIZA", "PRO-ADME-01");
        adMe.Initialize(this);
    }

    @Override
    public void onInitializationComplete(AdsSever adServer) {
        AdMeBannerView rootBanner = findViewById(R.id.adMeBanner);
        AdMeView adBannerView = rootBanner.findViewById(com.proit.admeads.R.id.adBannerView);
        adMe.showBannerAd(adServer, adBannerView);
    }
}