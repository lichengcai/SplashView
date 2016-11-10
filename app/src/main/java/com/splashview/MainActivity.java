package com.splashview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.splashview.widget.SplashView;

public class MainActivity extends AppCompatActivity {
    private SplashView splashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splashView = (SplashView) findViewById(R.id.splashView);
    }

    public void one(View view) {
        splashView.start();
    }
}
