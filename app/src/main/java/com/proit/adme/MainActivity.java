package com.proit.adme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.proit.admeads.AdME;
import com.proit.admeads.AdMeBannerView;
import com.proit.admeads.AdMeRectangleView;
import com.proit.admeads.AdMeView;
import com.proit.admeads.AdsSever;

public class MainActivity extends AppCompatActivity implements AdME.AdMeAdsService {
    private AdME adMe;
    private static final String TAG = "AdME";
    private AdsSever adS = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button banner = findViewById(R.id.banner);
        Button rectangle = findViewById(R.id.rectangle);
        Button interstitial = findViewById(R.id.interstitial);
        Button mediumnativead = findViewById(R.id.mediumnativead);
        Button nativead = findViewById(R.id.nativead);

        adMe = new AdME("MI-ATHUMANI-BAKARI-MAHIZA", "PRO-ADME-01");
        adMe.Initialize(this);

        banner.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BannerActivity.class);
            startActivity(intent);
        });

        rectangle.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RectangleActivity.class);
            startActivity(intent);
        });

        interstitial.setOnClickListener(v -> {
            if(adS != null) {
                adMe.showInterstitialAd(adS);
            } else {
                Toast.makeText(this,"AdServer not ready",Toast.LENGTH_SHORT).show();
            }
        });

        mediumnativead.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MediumNativeActivity.class);
            startActivity(intent);
        });

        nativead.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NativeActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onInitializationComplete(AdsSever adServer) {
        this.adS = adServer;
    }
}