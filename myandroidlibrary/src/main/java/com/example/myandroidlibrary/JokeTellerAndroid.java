package com.example.myandroidlibrary;

import android.content.pm.InstrumentationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class JokeTellerAndroid extends AppCompatActivity {

    private TextView jokeTextView;
    public static final String INTENT_JOKE = "intent_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_activity);
        jokeTextView = (TextView) findViewById(R.id.tv_joke_teller);
        String joke = getIntent().getStringExtra(INTENT_JOKE);
        jokeTextView.setText(joke);
    }

}
