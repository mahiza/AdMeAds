package com.proit.adme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.proit.admeads.AdME;
import com.proit.admeads.AdsSever;

public class NativeActivity extends AppCompatActivity implements AdME.AdMeAdsService  {
    private AdME adMe;
    private static final String TAG = "AdME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_ad_layout);

        adMe = new AdME("MI-ATHUMANI-BAKARI-MAHIZA", "PRO-ADME-01");
        adMe.Initialize(this);
    }

    @Override
    public void onInitializationComplete(AdsSever adServer) {
        ImageView native_ad_icon_image = findViewById(R.id.native_ad_icon_image);
        TextView native_ad_title = findViewById(R.id.native_ad_title);
        TextView native_title_text_view = findViewById(R.id.native_title_text_view);
        ImageView native_ad_privacy_icon_image = findViewById(R.id.native_ad_privacy_icon_image);
        ImageView native_ad_main_image = findViewById(R.id.native_ad_main_image);
        TextView native_ad_text = findViewById(R.id.native_ad_text);
        Button native_ad_cta = findViewById(R.id.native_ad_cta);

        adMe.setNativeAdIconImage(native_ad_icon_image);
        adMe.setNativeAdTitle(native_ad_title);
        adMe.setNativeTitleText(native_title_text_view);
        adMe.setNativeAdPrivacyIconImage(native_ad_privacy_icon_image);
        adMe.setNativeAdMainImage(native_ad_main_image);
        adMe.setNativeAdText(native_ad_text);
        adMe.setNativeAdCTA(native_ad_cta);
        adMe.setLargeNativeAd(adServer);
    }
}
