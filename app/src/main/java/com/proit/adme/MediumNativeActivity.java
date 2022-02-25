package com.proit.adme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.proit.admeads.AdME;
import com.proit.admeads.AdsSever;

public class MediumNativeActivity extends AppCompatActivity implements AdME.AdMeAdsService  {
    private AdME adMe;
    private static final String TAG = "AdME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_ad_medium);

        adMe = new AdME("MI-ATHUMANI-BAKARI-MAHIZA", "PRO-ADME-01");
        adMe.Initialize(this);
    }

    @Override
    public void onInitializationComplete(AdsSever adServer) {
        ImageView native_ad_icon_image = findViewById(R.id.native_ad_icon_image);
        TextView native_ad_title = findViewById(R.id.native_ad_title);
        ImageView native_ad_privacy_icon_image = findViewById(R.id.native_ad_privacy_icon_image);
        TextView native_ad_text = findViewById(R.id.native_ad_short_text_view);
        Button native_ad_cta = findViewById(R.id.native_ad_cta);

        adMe.setNativeAdIconImage(native_ad_icon_image);
        adMe.setNativeAdTitle(native_ad_title);
        adMe.setNativeAdPrivacyIconImage(native_ad_privacy_icon_image);
        adMe.setNativeAdText(native_ad_text);
        adMe.setNativeAdCTA(native_ad_cta);
        adMe.setMediumNativeAd(adServer);
    }
}