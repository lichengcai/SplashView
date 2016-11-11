package com.splashview.widget;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.splashview.R;
import com.splashview.bean.Circle;
import com.splashview.ui.SecondActivity;

/**
 * Created by lichengcai on 2016/11/11.
 */

public class CircleView extends View {
    private Paint mPaintText;
    private Paint mPaint;
    private Paint mPaintLine;
    private Circle circle;
    private Circle circleS;

    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaintText = new Paint();
        mPaintText.setColor(getResources().getColor(R.color.black));
        mPaintText.setTextSize(60);

        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.buy_now));

        mPaintLine = new Paint();
        mPaintLine.setColor(getResources().getColor(R.color.red));
        mPaintLine.setStrokeWidth(5);

        circle = new Circle(100,100,30);
        circleS = new Circle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (circleS.getX() == 0) {
            canvas.drawCircle(circle.getX(),circle.getY(),circle.getRadius(),mPaint);
        }
        canvas.drawCircle(circleS.getX(),circleS.getY(),circleS.getRadius(),mPaint);
        canvas.drawLine(100,100,500,500,mPaintLine);
        canvas.drawLine(100,100,900,100,mPaintLine);
        canvas.drawLine(500,500,900,100,mPaintLine);
        canvas.drawText("1",90,90,mPaintText);
        canvas.drawText("3",900,100,mPaintText);
        canvas.drawText("2",480,550,mPaintText);
    }

    public void two() {
        Circle circle = new Circle(100,100,30);
        Circle circle2 = new Circle(900,100,30);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyEvaluator(),circle,circle2);
        valueAnimator.setDuration(5000);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleS = (Circle) animation.getAnimatedValue();
                Log.d("circle"," circle--" + circleS.toString());
                postInvalidate();
            }
        });
    }
    public void one() {
         Circle circle = new Circle(100,100,30);
        Circle circle2 = new Circle(900,100,30);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyEvaluator(),circle,circle2);
        valueAnimator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                Circle circle = new Circle();
                circle.setX((((Circle)endValue).getX()-((Circle)startValue).getX())*fraction + (((Circle) startValue).getX()));
            circle.setY((((Circle)endValue).getY()-((Circle)startValue).getY())*fraction + (((Circle) startValue).getY()));
            circle.setRadius((((Circle)endValue).getRadius()-((Circle)startValue).getRadius())*fraction + (((Circle) startValue).getRadius()));
                return circle;
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleS = (Circle) animation.getAnimatedValue();
                Log.d("circle"," cirlce--" + circleS.toString());
                postInvalidate();
            }
        });
    }

    private class MyEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Circle circle = new Circle();
            if (fraction<=0.5) {
                circle.setX((((Circle)endValue).getX()-((Circle)startValue).getX())*fraction + (((Circle) startValue).getX()));
                circle.setY(800*fraction+100);
                circle.setRadius(30);
            }else {
                circle.setX((((Circle)endValue).getX()-((Circle)startValue).getX())*fraction + (((Circle) startValue).getX()));
                circle.setY(800-800*fraction+100);
                circle.setRadius(30);
            }
//            circle.setX((((Circle)endValue).getX()-((Circle)startValue).getX())*fraction + (((Circle) startValue).getX()));
//            circle.setY((((Circle)endValue).getY()-((Circle)startValue).getY())*fraction + (((Circle) startValue).getY()));
//            circle.setRadius((((Circle)endValue).getRadius()-((Circle)startValue).getRadius())*fraction + (((Circle) startValue).getRadius()));
            return circle;
        }
    }
}
