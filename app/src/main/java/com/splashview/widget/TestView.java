package com.splashview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.splashview.R;

/**
 * Created by lichengcai on 2016/11/9.
 */

public class TestView extends View {
    Paint p = new Paint();
    Paint mPaintText = new Paint();
    Paint mPaintLine = new Paint();
    Rect bounds = new Rect();
    final String mText = "Hello World";

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                float rx1 = event.getRawX();
                float ry1 = event.getRawY();
                Log.d("ACTION_MOVE","x1==" + x1 + "  y1==" + y1 + "  rx1==" + rx1 + "  ry1==" + ry1);
                break;
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                float rx = event.getRawX();
                float ry = event.getRawY();
                Log.d("ACTION_DOWN","x==" + x + "  y==" + y + "  rx==" + rx + "  ry==" + ry);
                break;
            case MotionEvent.ACTION_UP:
                float x2 = event.getX();
                float y2 = event.getY();
                float rx2 = event.getRawX();
                float ry2 = event.getRawY();
                Log.d("ACTION_MOVE","x2==" + x2 + "  y2==" + y2 + "  rx2==" + rx2 + "  ry2==" + ry2);
                break;
        }
        return super.onTouchEvent(event);
    }

    private float width;
    public void testR() {
        ValueAnimator animator = ValueAnimator.ofFloat(0,500);
        animator.setDuration(5000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                width = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //起始线
        p.setStrokeWidth(3);
        p.setColor(getResources().getColor(R.color.red));
        canvas.drawLine(100,100,100,1000,p);

        //测试文本
        mPaintText.setColor(getResources().getColor(R.color.buy_now));
        mPaintText.setTextSize(90);
        mPaintText.getTextBounds(mText,0,mText.length(),bounds);
        canvas.drawText(mText,100,200,mPaintText);

        //使用measureText()得到宽度画出的一条横线
        mPaintLine.setColor(getResources().getColor(R.color.black));
        mPaintLine.setStrokeWidth(5);
        canvas.drawLine(100,210,mPaintText.measureText(mText)+100,210,mPaintLine);

        Log.d("onDraw"," getTextBounds()--" + bounds.width() + "  measureWith()--" + mPaintText.measureText(mText)
         + " bounds.toShortString--" + bounds.toShortString()
        + " bounds.left--" + bounds.left
        + " bounds.right--" + bounds.right);


        canvas.drawText("画矩形：", 10, 80, p);
        p.setColor(Color.GRAY);// 设置灰色
        p.setStyle(Paint.Style.FILL);//设置填满
        canvas.drawRect(500-width, 600, 500+width, 800, p);// 正方形






//        p.setTextSize(60);
//
//        p.getTextBounds(s, 0, s.length(), bounds);
//        float mt = p.measureText(s);
//        int bw = bounds.width();
//
//        Log.i("LCG", String.format(
//                "measureText %f, getTextBounds %d (%s)",
//                mt,
//                bw, bounds.toShortString())
//        );
//
//        bounds.offset(0, -bounds.top);
//        p.setStyle(Paint.Style.STROKE);
//        canvas.drawColor(0xff000080);
//        p.setColor(0xffff0000);
//        canvas.drawRect(bounds, p);
//        p.setColor(0xff00ff00);
//        canvas.drawText(s, 0, bounds.bottom, p);
    }
}
