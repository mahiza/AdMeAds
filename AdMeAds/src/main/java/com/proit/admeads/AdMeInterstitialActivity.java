package com.proit.admeads;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.proit.admeads.databinding.ActivityAdMeInterstitialBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AdMeInterstitialActivity extends AppCompatActivity {
    private static final String TAG = "AdME";
    private String adCTA;
    private String SERVER;
    private String uniqueId;
    private String accountID;
    private String apiKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.proit.admeads.databinding.ActivityAdMeInterstitialBinding binding = ActivityAdMeInterstitialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView mControlView = binding.closeAdBtn;
        AdMeView mContentView = binding.fullscreenContent;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String adIMAGE = extras.getString("adIMAGE");
            adCTA = extras.getString("adCTA");
            uniqueId = extras.getString("user");
            SERVER = extras.getString("server");
            accountID = extras.getString("accountID");
            apiKey = extras.getString("apiKey");

            Picasso.get().load(adIMAGE).fit().centerCrop()
                    .into(mContentView);
            new StatTask("INTERSTITIAL", uniqueId , 1, 0).execute();

            // Set up the user interaction to manually show or hide the system UI.
            mContentView.setOnClickListener(view -> {
                new StatTask("INTERSTITIAL", uniqueId , 0, 1).execute();
                String url = adCTA;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            });

        }

        mControlView.setOnClickListener(view -> {
            finish();
        });
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