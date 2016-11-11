package com.splashview.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.splashview.R;

/**
 * Created by lichengcai on 2016/11/9.
 */

public class FirstActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void main(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    public void testView(View view) {
        startActivity(new Intent(this,TestViewActivity.class));
    }
    public void second(View view) {
        startActivity(new Intent(this,SecondActivity.class));
    }
}
