package com.snowmanlabs.lovie.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.snowmanlabs.lovie.R;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                finish();
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
        }, 4000);
    }
}
