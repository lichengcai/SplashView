package com.splashview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.splashview.widget.TestView;

/**
 * Created by lichengcai on 2016/11/9.
 */
public class TestViewActivity extends Activity {
    private TestView testView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);
        testView = (TestView) findViewById(R.id.testView);
    }

    public void testOne(View view) {
        testView.testR();
    }
}
