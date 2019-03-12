package com.m_docket.ui.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.m_docket.R;
import com.m_docket.utils.DotsLoaderView;

public class SplashActivity extends AppCompatActivity {


    private Handler handler;
    private Runnable runnable;

    private DotsLoaderView dotsLoaderView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        dotsLoaderView = findViewById(R.id.dotsLoaderView);
        handler = new Handler();
        dotsLoaderView.show();
        dotsLoaderView.animateViews();

        long delayMillis = 4000;
        runnable = () -> {

            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            dotsLoaderView.hide();
            finish();
        };
        handler.postDelayed(runnable, delayMillis);


    }

    @Override
    public void onBackPressed() {
        if (handler != null)
            handler.removeCallbacks(runnable);
        super.onBackPressed();
    }

}

