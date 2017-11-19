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

 public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private  MyJokeApi mApiService = null;
    private Context mContext;
    private String mResult;
    private ProgressBar mProgressBar;

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

    @SafeVarargs
    @Override
    protected final String doInBackground(Pair<Context, String>... params) {
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
            return mApiService.putJoke().execute().getJoke();
        }catch (IOException e){
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(mProgressBar != null)
        {
            mProgressBar.setVisibility(View.GONE);
        }
        mResult = s;
        startJokeDisplayActivity();

    }

    private void startJokeDisplayActivity()
    {
        Intent jokeIntent = new Intent(mContext,JokeTellerAndroid.class);
        jokeIntent.putExtra(JokeTellerAndroid.INTENT_JOKE,mResult);
        jokeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(jokeIntent);
    }
}

