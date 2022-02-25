package com.proit.admeads;

import java.util.ArrayList;

public class AdsSever {
    ArrayList<AdMeAds> adMeAds;

    public AdsSever ( ArrayList<AdMeAds> adMeAds) {
        this.adMeAds = adMeAds;
    }

    public ArrayList<AdMeAds> getAdMeAds() {
        return adMeAds;
    }
}
