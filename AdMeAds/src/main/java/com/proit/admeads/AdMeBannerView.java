package com.proit.admeads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AdMeBannerView extends RelativeLayout {

    public AdMeBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        inflate(getContext(), R.layout.adme_banner, this);
        ImageView privacyIcon = findViewById(R.id.privacyIcon);

        privacyIcon.setOnClickListener(v -> {
            String url = "https://www.pro-clas.com/home/contact.html";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        });
    }

}
