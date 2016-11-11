package com.splashview.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.splashview.R;
import com.splashview.bean.Circle;
import com.splashview.bean.Person;
import com.splashview.widget.CircleView;
import com.splashview.widget.SplashView;

import static android.R.attr.end;
import static android.R.attr.value;

/**
 * Created by lichengcai on 2016/11/11.
 */

public class SecondActivity extends Activity {
    private ImageView imageView;
    private CircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView = (ImageView) findViewById(R.id.img);
        circleView = (CircleView) findViewById(R.id.circleView);
    }

    public void aniThree(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView,"alpha",1,0,1);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageView,"translationX",0,500);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator).with(objectAnimator1);
        animatorSet.setDuration(5000);
        animatorSet.start();
    }

    public void aniTwo(View view) {
        circleView.two();
    }

    public void aniOne(View view) {
        circleView.one();
    }

    private class MyEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Circle circle = new Circle();
            circle.setX((((Circle)endValue).getX()-((Circle)startValue).getX())*fraction + (((Circle) startValue).getX()));
            circle.setX((((Circle)endValue).getY()-((Circle)startValue).getY())*fraction + (((Circle) startValue).getY()));
            circle.setX((((Circle)endValue).getRadius()-((Circle)startValue).getRadius())*fraction + (((Circle) startValue).getRadius()));
            return circle;
        }
    }
}
