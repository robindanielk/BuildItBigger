package com.example.myandroidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class JokeTellerAndroid extends AppCompatActivity {

    private TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_activity);
        jokeTextView = (TextView) findViewById(R.id.tv_joke_teller);
        if(getIntent().hasExtra("joke"))
        {
            jokeTextView.setText(getIntent().getExtras().getString("joke"));
        }
    }

}
