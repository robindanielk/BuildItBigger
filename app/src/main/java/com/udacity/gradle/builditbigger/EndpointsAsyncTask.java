package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myandroidlibrary.JokeTellerAndroid;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.builditbigger.backend.myJokeApi.MyJokeApi;
import com.google.builditbigger.backend.myJokeApi.model.MyJokeBean;

import java.io.IOException;

/**
 * Created by ridsys-001 on 9/10/17.
 */

class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private  MyJokeApi mApiService = null;
    private Context mContext;
    private String mResult;
    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;
    private static final String TAG = EndpointsAsyncTask.class.getSimpleName();

    public EndpointsAsyncTask(Context mContext,ProgressBar mProgressBar)
    {
        this.mContext = mContext;
        this.mProgressBar = mProgressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mProgressBar != null)
        {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(mApiService == null)
        {
            MyJokeApi.Builder builder = new MyJokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
            mApiService = builder.build();
        }try{
            return mApiService.putJoke(new MyJokeBean()).execute().getJoke();
        }catch (IOException e){
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mResult = s;
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if(mProgressBar != null)
                {
                    mProgressBar.setVisibility(View.GONE);
                }
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (mProgressBar != null)
                {
                    mProgressBar.setVisibility(View.GONE);
                    Log.e(TAG,"Failed to load,Error Code :" + i );
                }
                startJokeDisplayActivity();

            }

            @Override
            public void onAdClosed() {
                startJokeDisplayActivity();
            }
        });

        AdRequest addRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(addRequest);
    }

    private void startJokeDisplayActivity()
    {
        Intent jokeIntent = new Intent(mContext,JokeTellerAndroid.class);
        jokeIntent.putExtra(JokeTellerAndroid.INTENT_JOKE,mResult);
        jokeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(jokeIntent);
    }
}

