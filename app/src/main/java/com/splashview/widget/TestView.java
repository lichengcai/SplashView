package com.splashview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
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
