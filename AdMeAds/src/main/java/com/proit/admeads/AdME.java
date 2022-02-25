package com.proit.admeads;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uuid.AppDataSource;
import uuid.Uuid;

public class AdME {
    private static final String serviceUrl = "http://164.90.218.23/adServices/";
    private static final String TAG = "AdME";
    private String apiKey="na";
    private String accountID = "na";
    private String SERVER = "";
    private Context context;

    ImageView native_ad_icon_image;
    TextView native_ad_title;
    TextView native_title_text_view;
    ImageView native_ad_privacy_icon_image;
    ImageView native_ad_main_image;
    TextView native_ad_text;
    Button native_ad_cta;

    public AdME(String apiKey, String accountID) {
        this.apiKey = apiKey;
        this.accountID = accountID;
    }

    public void Initialize(Context context) {
        this.context = context;
        ServerTask task = new ServerTask(context);
        task.execute();
    }

    public void showBannerAd(AdsSever adServer, AdMeView adView) {
        int ln = adServer.getAdMeAds().size();
        ArrayList<AdMeAds> bannerAds = new ArrayList<>();
        String uniqueId = getUserUniqueID();

        for(int i=0; i<ln; i++) {
            String adtype = adServer.getAdMeAds().get(i).getAdType();
            if(adtype.equalsIgnoreCase(AdType.BANNER)) {
                bannerAds.add(adServer.getAdMeAds().get(i));
            }
        }

        if(bannerAds.size()>0) {
            int count = 0;
            Collections.shuffle(bannerAds);

            Picasso.get().load(bannerAds.get(count).getAdImage()).fit().centerCrop()
                    .into(adView);
            new StatTask(AdType.BANNER, uniqueId , 1, 0).execute();

            adView.setOnClickListener(v -> {
                new StatTask(AdType.BANNER, uniqueId , 0, 1).execute();
                String url = bannerAds.get(count).adCallToAction;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            });
        }
    }

    public void showRectangleAd(AdsSever adServer, AdMeView adView) {
        int ln = adServer.getAdMeAds().size();
        ArrayList<AdMeAds> bannerAds = new ArrayList<>();
        String uniqueId = getUserUniqueID();

        for(int i=0; i<ln; i++) {
            String adtype = adServer.getAdMeAds().get(i).getAdType();
            if(adtype.equalsIgnoreCase(AdType.RECTANGLE)) {
                bannerAds.add(adServer.getAdMeAds().get(i));
            }
        }

        if(bannerAds.size()>0) {
            int count = 0;
            Collections.shuffle(bannerAds);
            Picasso.get().load(bannerAds.get(count).getAdImage()).fit().centerCrop()
                    .into(adView);
            new StatTask(AdType.RECTANGLE, uniqueId , 1, 0).execute();

            adView.setOnClickListener(v -> {
                new StatTask(AdType.RECTANGLE, uniqueId , 0, 1).execute();
                String url = bannerAds.get(count).adCallToAction;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            });

        }
    }

    public void showInterstitialAd(AdsSever adServer) {
        String uniqueId = getUserUniqueID();
        int ln = adServer.getAdMeAds().size();
        ArrayList<AdMeAds> bannerAds = new ArrayList<>();

        for(int i=0; i<ln; i++) {
            String adtype = adServer.getAdMeAds().get(i).getAdType();
            if(adtype.equalsIgnoreCase(AdType.INTERSTITIAL)) {
                bannerAds.add(adServer.getAdMeAds().get(i));
            }
        }

        if(bannerAds.size()>0) {
            int count = 0;
            Collections.shuffle(bannerAds);

            Intent intent = new Intent(context, AdMeInterstitialActivity.class);
            intent.putExtra("adIMAGE", bannerAds.get(count).getAdImage());
            intent.putExtra("adCTA", bannerAds.get(count).getAdCallToAction());
            intent.putExtra("user", uniqueId);
            intent.putExtra("server", SERVER);
            intent.putExtra("accountID", accountID);
            intent.putExtra("apiKey", apiKey);
            context.startActivity(intent);

        }
    }

    public void setLargeNativeAd(AdsSever adServer) {
        int ln = adServer.getAdMeAds().size();
        ArrayList<AdMeAds> bannerAds = new ArrayList<>();
        String uniqueId = getUserUniqueID();

        for(int i=0; i<ln; i++) {
            String adtype = adServer.getAdMeAds().get(i).getAdType();
            if(adtype.equalsIgnoreCase(AdType.NATIVE)) {
                bannerAds.add(adServer.getAdMeAds().get(i));
            }
        }

        if(bannerAds.size()>0) {
            int count = 0;
            Collections.shuffle(bannerAds);

            native_ad_title.setText(bannerAds.get(count).getAdSender());
            native_title_text_view.setText(bannerAds.get(count).getAdTitle());
            native_ad_text.setText(bannerAds.get(count).getAdText());
            native_ad_cta.setText(bannerAds.get(count).getAdCallToActionTxt());

            Picasso.get().load(bannerAds.get(count).getAdSenderIcon()).fit().centerCrop()
                    .into(native_ad_icon_image);

            Picasso.get().load(bannerAds.get(count).getAdPrivacyIcon()).fit().centerCrop()
                    .into(native_ad_privacy_icon_image);

            Picasso.get().load(bannerAds.get(count).getAdImage()).fit().centerCrop()
                    .into(native_ad_main_image);

            new StatTask(AdType.NATIVE, uniqueId , 1, 0).execute();

            native_ad_privacy_icon_image.setOnClickListener(v -> {
                String url = bannerAds.get(count).adPrivacyUrl;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            });

            native_ad_main_image.setOnClickListener(v -> {
                new StatTask(AdType.NATIVE, uniqueId , 0, 1).execute();
                String url = bannerAds.get(count).adCallToAction;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            });

            native_ad_cta.setOnClickListener(v -> {
                new StatTask(AdType.NATIVE, uniqueId , 0, 1).execute();
                String url = bannerAds.get(count).adCallToAction;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            });
        }
    }

    public void setMediumNativeAd(AdsSever adServer) {
        int ln = adServer.getAdMeAds().size();
        ArrayList<AdMeAds> bannerAds = new ArrayList<>();
        String uniqueId = getUserUniqueID();

        for(int i=0; i<ln; i++) {
            String adtype = adServer.getAdMeAds().get(i).getAdType();
            if(adtype.equalsIgnoreCase(AdType.NATIVE)) {
                bannerAds.add(adServer.getAdMeAds().get(i));
            }
        }

        if(bannerAds.size()>0) {
            int count = 0;
            Collections.shuffle(bannerAds);

            native_ad_title.setText(bannerAds.get(count).getAdSender());
            native_ad_text.setText(bannerAds.get(count).getAdTitle());
            native_ad_cta.setText(bannerAds.get(count).getAdCallToActionTxt());

            Picasso.get().load(bannerAds.get(count).getAdSenderIcon()).fit().centerCrop()
                    .into(native_ad_icon_image);

            Picasso.get().load(bannerAds.get(count).getAdPrivacyIcon()).fit().centerCrop()
                    .into(native_ad_privacy_icon_image);

            new StatTask(AdType.NATIVE, uniqueId , 1, 0).execute();

            native_ad_privacy_icon_image.setOnClickListener(v -> {
                String url = bannerAds.get(count).adPrivacyUrl;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            });

            native_ad_cta.setOnClickListener(v -> {
                new StatTask(AdType.NATIVE, uniqueId , 0, 1).execute();
                String url = bannerAds.get(count).adCallToAction;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            });
        }
    }

    public void setNativeAdIconImage(ImageView native_ad_icon_image) {
        this.native_ad_icon_image = native_ad_icon_image;
    }

    public void setNativeTitleText(TextView native_title_text_view) {
        this.native_title_text_view = native_title_text_view;
    }

    public void setNativeAdTitle(TextView native_ad_title) {
        this.native_ad_title = native_ad_title;
    }

    public void setNativeAdPrivacyIconImage(ImageView native_ad_privacy_icon_image) {
        this.native_ad_privacy_icon_image = native_ad_privacy_icon_image;
    }

    public void setNativeAdMainImage(ImageView native_ad_main_image) {
        this.native_ad_main_image = native_ad_main_image;
    }

    public void setNativeAdText(TextView native_ad_text) {
        this.native_ad_text = native_ad_text;
    }

    public void setNativeAdCTA(Button native_ad_cta) {
        this.native_ad_cta = native_ad_cta;
    }

    public interface AdMeAdsService {
        void onInitializationComplete(AdsSever adsServer);
    }

    private class ServerTask extends AsyncTask<String, String, Server> {
        Context context;

        public ServerTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Server doInBackground(String... strings) {
            return getServices();
        }

        @Override
        protected void onPostExecute(Server server) {
            super.onPostExecute(server);
            try {
                if(server.getCode()==200) {
                    SERVER = server.getServerUrl();
                    Log.d(TAG, "Connection to AdMEAds Server was Successful..!");
                    // Request Ads
                    AdsTask task = new AdsTask(server.getServerUrl());
                    task.execute();
                    task.delegate = (AdMeAdsService) context;
                } else {
                    Log.d(TAG, ""+"apiKey/AccountID not found.!");
                }
            } catch(NullPointerException e) {
                Log.d(TAG, ""+e.getMessage());
            }
        }

        Server getServices() {
            Server server = null;
            final OkHttpClient httpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("apiKey", apiKey)
                    .add("accountID", accountID)
                    .build();

            Request request = new Request.Builder()
                    .url(serviceUrl)
                    .addHeader("User-Agent", "OkHttp Bot")
                    .post(formBody)
                    .build();

            try {
                Response response = httpClient.newCall(request).execute();
                if(response.isSuccessful()) {
                    JSONObject json = new JSONObject(response.body().string());
                    String key = json.getString("apiKey");
                    String acn = json.getString("accountID");
                    String sUrl = json.getString("serverUrl");
                    int code = json.getInt("code");

                    server = new Server();
                    server.setApiKey(key);
                    server.setAccountID(acn);
                    server.setServerUrl(sUrl);
                    server.setCode(code);

                    response.close();
                }
            } catch(UnknownHostException | NullPointerException e){
                Log.d(TAG, e.getMessage());
            } catch(JSONException | IOException e){
                Log.d(TAG, ""+e.getMessage());
            }

            return server;
        }

    }

    private class AdsTask extends AsyncTask<String, String, AdsSever> {
        AdMeAdsService delegate=null;
        String sUrl;

        AdsTask(String sUrl) {
            this.sUrl = sUrl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected AdsSever doInBackground(String... strings) {
            return getAds();
        }

        @Override
        protected void onPostExecute(AdsSever adsServer) {
            super.onPostExecute(adsServer);
            delegate.onInitializationComplete(adsServer);
            try {
                int ln = adsServer.getAdMeAds().size();
                if(ln>0) {
                    Log.d(TAG, ""+"Ads were loaded");
                } else {
                    Log.d(TAG, ""+"No Ads available at the moment!");
                }

            } catch (NullPointerException e) {
                Log.d(TAG, ""+"An error occurred.!");
            }

        }

        AdsSever getAds() {
            ArrayList<AdMeAds> adMeAds = new ArrayList<>();
            final OkHttpClient httpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("tool", "3")
                    .build();

            Request request = new Request.Builder()
                    .url(sUrl)
                    .addHeader("User-Agent", "OkHttp Bot")
                    .post(formBody)
                    .build();

            try {
                Response response = httpClient.newCall(request).execute();
                if(response.isSuccessful()) {
                    JSONArray jsonArr = new JSONArray(response.body().string());
                    int ln = jsonArr.length();

                    int id;
                    String accountID;
                    String adSender;
                    String adTitle;
                    String adText;
                    String adType;
                    String adSenderIcon;
                    String adImage;
                    String adPrivacyIcon;
                    String adPrivacyUrl;
                    String adCallToAction;
                    String adCallToActionTxt;
                    String adStatus;

                    for(int i=0; i<ln; i++) {
                        JSONObject json = jsonArr.getJSONObject(i);
                        id = json.getInt("id");
                        accountID = json.getString("accountID");
                        adSender = json.getString("adSender");
                        adTitle = json.getString("adTitle");
                        adText = json.getString("adText");
                        adType = json.getString("adType");
                        adSenderIcon = json.getString("adSenderIcon");
                        adImage = json.getString("adImage");
                        adPrivacyIcon = json.getString("adPrivacyIcon");
                        adPrivacyUrl = json.getString("adPrivacyUrl");
                        adCallToAction = json.getString("adCallToAction");
                        adCallToActionTxt = json.getString("adCallToActionTxt");
                        adStatus = json.getString("adStatus");

                        AdMeAds adMeAd = new AdMeAds();
                        adMeAd.setId(id);
                        adMeAd.setAccountID(accountID);
                        adMeAd.setAdSender(adSender);
                        adMeAd.setAdTitle(adTitle);
                        adMeAd.setAdText(adText);
                        adMeAd.setAdType(adType);
                        adMeAd.setAdSenderIcon(adSenderIcon);
                        adMeAd.setAdImage(adImage);
                        adMeAd.setAdPrivacyIcon(adPrivacyIcon);
                        adMeAd.setAdPrivacyUrl(adPrivacyUrl);
                        adMeAd.setAdCallToAction(adCallToAction);
                        adMeAd.setAdCallToActionTxt(adCallToActionTxt);
                        adMeAd.setAdStatus(adStatus);

                        adMeAds.add(adMeAd);
                    }

                    response.close();
                }
            } catch(UnknownHostException | NullPointerException e){
                Log.d(TAG, e.getMessage());
            } catch(JSONException | IOException e){
                Log.d(TAG, ""+e.getMessage());
            }

            Collections.shuffle(adMeAds);
            return new AdsSever(adMeAds);
        }

    }

    public String getUserUniqueID() {
        AppDataSource db = new AppDataSource(context);
        String sId = "";
        try {
            db.open();
            List<Uuid> uObj = db.getUserID();
            int ln = uObj.size();
            if(ln==0) {
                UUID idOne = UUID.randomUUID();
                sId = idOne.toString();
                long rows=db.newUserID(sId);
            } else {
                sId = uObj.get(ln-1).getUuId();
            }

        }catch(SQLException e){} catch(ArrayIndexOutOfBoundsException e){} catch(NullPointerException e){}
        finally {
            Log.d(TAG, "USER : "+sId);
            db.close();
        }

        return sId;
    }


    private class StatTask extends AsyncTask<String, String, String> {
        String adType;
        String advertisingId;
        int views;
        int clicks;

        StatTask(String adType, String advertisingId, int views, int clicks) {
            this.adType = adType;
            this.advertisingId = advertisingId;
            this.views = views;
            this.clicks = clicks;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            return getLogInfo();
        }

        @Override
        protected void onPostExecute(String info) {
            super.onPostExecute(info);
            try {
                if(info.equalsIgnoreCase("Ok")) {
                    Log.d(TAG, "Activity was logged successful");
                } else {
                    Log.d(TAG, ""+"System failed to log the Activity");
                }
            } catch(NullPointerException e) {
                Log.d(TAG, ""+e.getMessage());
            }
        }

        String getLogInfo() {
            String status = "FAIL";
            final OkHttpClient httpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("tool", "6")
                    .add("adType", adType)
                    .add("advertisingId", advertisingId)
                    .add("views", Integer.toString(views))
                    .add("clicks", Integer.toString(clicks))
                    .add("accountID", accountID)
                    .add("developerKey", apiKey)
                    .build();

            Request request = new Request.Builder()
                    .url(SERVER)
                    .addHeader("User-Agent", "OkHttp Bot")
                    .post(formBody)
                    .build();

            try {
                Response response = httpClient.newCall(request).execute();
                if(response.isSuccessful()) {
                    JSONObject json = new JSONObject(response.body().string());
                    status = json.getString("status");
                    response.close();
                }
            } catch(UnknownHostException | NullPointerException e){
                Log.d(TAG, e.getMessage());
            } catch(JSONException | IOException e){
                Log.d(TAG, ""+e.getMessage());
            }

            return status;
        }

    }

}
