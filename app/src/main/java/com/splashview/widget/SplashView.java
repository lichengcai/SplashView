package com.splashview.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.splashview.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 * Created by lichengcai on 2016/11/7.
 */

public class SplashView extends View {
    private int tab =0;
    private float mTextW = 0;
    private float mTextH = 0;
    private float mRightWidth = 0;
    private float mRad =0;
    private float mSpreadWidth = 0;
    /**
     * 粒子覆盖文字内容
     */
    private String mSplashText;
    /**
     * 粒子覆盖文字最终大小
     */
    private int mSplashSizeEnd;
    /**
     * 默认文字初始大小值
     */
    private int mSplashSizeStart = sp2px(60);
    private int mSplashSizeDef = dip2px(180);
    /**
     * 粒子半径
     */
    private int mRadius = dip2px(5);
    /**
     * 粒子覆盖文字画笔
     */
    private Paint mPaintSplashText;
    /**
     * 粒子画笔
     */
    private Paint mPaintCircle;
    private Rect mBound = new Rect();
    private Rect mBoundEnd = new Rect();
    private HashMap<Integer, ArrayList<Circle>> mCircleMapStart = new HashMap<>();
    private HashMap<Integer, ArrayList<Circle>> mCircleMapEnd = new HashMap<>();

    public SplashView(Context context) {
        this(context,null);
    }

    public SplashView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SplashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SplashView,defStyleAttr,0);
        int n = array.getIndexCount();
        for (int i=0; i<n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.SplashView_splash_text:
                    mSplashText = array.getString(attr);
                    if (mSplashText == null)
                        mSplashText = ".com";
                    break;
                case R.styleable.SplashView_splash_size:
                    mSplashSizeEnd = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();

    }

    public void initPaint() {
        mPaintSplashText = new Paint();
        mPaintSplashText.setColor(getResources().getColor(R.color.transTest));


        mPaintSplashText.setTextSize(mSplashSizeStart);

//        Log.d("init"," mBound--" + mBound.width()+ "---" + mBound.height()
//         + "  mBoundEnd--" + mBoundEnd.width() + "--" + mBoundEnd.height());

//        int widthSize = mBoundEnd.width() + 40;
//        int heightSize = mBoundEnd.height() + 20;
//        int r = widthSize/38;
//        int b = heightSize/9;
//        mSplashSizeX = (getWidth()/2-mBoundEnd.width()/2) - 10 + r;
//        mSplashSizeY = (getHeight()/2-mBoundEnd.height()/2) - 12 + r;
//        for (int i=0; i<10; i++) {
//            Circle circle = new Circle(mSplashSizeX+(4*r)*i,mSplashSizeY,r);
//            ArrayList<Circle> arrayList = new ArrayList<>();
//            for (int j=0; j<10; j++) {
//                Circle circle2 = new Circle(circle.x,circle.y+b*j,r);
//                Log.d("circle2","circle2===" + circle2.toString());
//                arrayList.add(circle2);
//            }
//            mCircleMapEnd.put(i,arrayList);
//        }

        mPaintCircle = new Paint();
        mPaintCircle.setColor(getResources().getColor(R.color.white));

        mCircleMapStart = new HashMap<>();
        int x = (getWidth()/2-mSplashSizeDef/2);
        int y = getHeight()/2-mSplashSizeDef/2;
        for (int i=0; i<10; i++) {
            Circle circle = new Circle(x + dip2px(20)*i,y,mRadius);
            ArrayList<Circle> arrayList = new ArrayList<>();
            for (int j=0; j<10; j++) {
                Circle circle2 = new Circle(circle.x,circle.y+dip2px(20)*j,mRadius);
                arrayList.add(circle2);
            }
            mCircleMapStart.put(i,arrayList);
        }
    }

    public void start() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mSplashSizeStart,mSplashSizeEnd);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int textSize = (int) animation.getAnimatedValue();
                mPaintSplashText.setTextSize(textSize);
//                mPaintSplashText.getTextBounds(mSplashText, 0, mSplashText.length(), mBound);
//                int boundWidth = mBound.width();
//                int boundHeight = mBound.height();
//                Log.d("onMeasure"," boundWidth--" + boundWidth + "  boundHeight--" + boundHeight);
                postInvalidate();
                if (textSize == mSplashSizeEnd) {
                    Log.d("onAnimationEnd","animation is finish");
                }
            }
        });


        valueAnimator.start();

        mPaintSplashText.setTextSize(mSplashSizeEnd);

        float textWidth = getTextWidth(mSplashText,mPaintSplashText);
        float textHeight = getTextHeight(mSplashText,mPaintSplashText);
        Log.d("EndText"," textWidth--"+textWidth + "  textHeight--" + textHeight);
        float ra = (textWidth+40)/56;
        mTextW = textWidth+40;
        mRad = ra;
        float da = (textHeight+20)/9;
        float Ex = getWidth()/2-textWidth/2-20+ra;
        float Ey = getHeight()/2-textHeight/2-10;
        for (int i=0; i<10; i++) {
            Circle circle = new Circle(Ex+(6*ra)*i,Ey,ra);
            ArrayList<Circle> arrayList = new ArrayList<>();
            for (int j=0; j<10; j++) {
                Circle circle1 = new Circle(circle.x,circle.y+da*j,ra);
                arrayList.add(circle1);
            }
            mCircleMapEnd.put(i,arrayList);
        }

        Collection<Animator> animList = new ArrayList<>();
        AnimatorSet set = new AnimatorSet();
        for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
                Log.d("animList"," mCircleMapStart--" + mCircleMapStart.get(i).get(j).toString()
                 + " mCircleMapEnd---" + mCircleMapEnd.get(i).get(j).toString());
                ValueAnimator objectAnimator =  ObjectAnimator.ofObject(new CircleEvaluator(),mCircleMapStart.get(i).get(j),mCircleMapEnd.get(i).get(j));
                objectAnimator.setDuration((long) (Math.random() + 100 * i + 150 * j));
                final int finalI = i;
                final int finalJ = j;

                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Circle circle = (Circle) animation.getAnimatedValue();
                        Log.d("onAnimation"," circle--" + circle.toString());
                        mCircleMapStart.get(finalI).get(finalJ).setX(circle.x);
                        mCircleMapStart.get(finalI).get(finalJ).setY(circle.y);
                        mCircleMapStart.get(finalI).get(finalJ).setR(circle.r);
                        postInvalidate();
                    }
                });
                animList.add(objectAnimator);
            }
        }
        set.playTogether(animList);
        set.start();

        final ValueAnimator value = ValueAnimator.ofFloat(0,(textWidth+40)/2);
        value.setDuration(500);
        value.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSpreadWidth = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                value.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        final ValueAnimator valueRight = ValueAnimator.ofFloat(0,(textWidth+40)/2);
        valueRight.setDuration(1000);
        valueRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRightWidth = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        value.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tab = 1;
                valueRight.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initPaint();

        Log.d("onSizeChanged"," w--" + w + "  h--" + h + "oldW--" + oldw + "  oldH--" + oldh);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (tab) {
            case 0:
                for (ArrayList<Circle> array : mCircleMapStart.values()) {
                    for (int i=0; i<array.size(); i++) {
                        Circle circle = array.get(i);
                        canvas.drawCircle(circle.getX(),circle.getY(),circle.getR(),mPaintCircle);
                    }
                }

                canvas.drawRect(getWidth()/2-mSpreadWidth,getHeight()/2-getTextHeight(mSplashText,mPaintSplashText)/2-10-mRad
                        ,getWidth()/2+mSpreadWidth,getHeight()/2+getTextHeight(mSplashText,mPaintSplashText)/2+10+mRad,mPaintCircle);

                mPaintSplashText.setColor(getResources().getColor(R.color.transTest));
                mPaintSplashText.getTextBounds(mSplashText, 0, mSplashText.length(), mBound);
                canvas.drawText(mSplashText, (getWidth() / 2 - getTextWidth(mSplashText,mPaintSplashText) / 2),
                        (getHeight() / 2 + getTextHeight(mSplashText,mPaintSplashText) / 2), mPaintSplashText);

                break;
            case 1:

                canvas.drawText("learn", (getWidth() / 2-10 - getTextWidth("learn",mPaintSplashText) / 2-mRightWidth),
                        (getHeight() / 2 + getTextHeight("learn",mPaintSplashText) / 2), mPaintSplashText);

                canvas.drawRect(getWidth()/2-mTextW/2+mRightWidth,getHeight()/2-getTextHeight(mSplashText,mPaintSplashText)/2-10-mRad,
                        getWidth()/2+mTextW/2+mRightWidth,getHeight()/2+getTextHeight(mSplashText,mPaintSplashText)/2+10+mRad,mPaintCircle);

                mPaintSplashText.setColor(getResources().getColor(R.color.buy_now));
                canvas.drawText(mSplashText, (getWidth() / 2 - getTextWidth(mSplashText,mPaintSplashText) / 2+mRightWidth),
                        (getHeight() / 2 + getTextHeight(mSplashText,mPaintSplashText) / 2), mPaintSplashText);


                break;
        }

    }


    private float getTextWidth(String text, Paint paint) {
        return paint.measureText(text);
    }


    private float getTextHeight(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height() / 1.1f;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                event.getX();
                break;


        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class CircleEvaluator implements TypeEvaluator<Circle> {

        @Override
        public Circle evaluate(float fraction, Circle startValue, Circle endValue) {
            Circle particle = new Circle();
            particle.x = startValue.x + (endValue.x - startValue.x) * fraction;
            particle.y = startValue.y + (endValue.y - startValue.y) * fraction;
            particle.r = startValue.r + (endValue.r - startValue.r) * fraction;
            return particle;
        }
    }
    private class Circle {
        private float x;
        private float y;
        private float r;

        public Circle() {

        }
        @Override
        public String toString() {
            return "Circle{" +
                    "x=" + x +
                    ", y=" + y +
                    ", r=" + r +
                    '}';
        }

        public Circle(float x, float y, float r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getR() {
            return r;
        }

        public void setR(float r) {
            this.r = r;
        }
    }
    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int px2dip( float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    private int px2sp(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue/fontScale + 0.5f);
    }

}
