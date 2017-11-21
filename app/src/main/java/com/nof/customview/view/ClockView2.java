package com.nof.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Administrator on 2017/11/21.
 */

public class ClockView2 extends View {

    private static final int DEFAULTSIZT = Util.dp2px(50);
    private static final int PAINTSTROKE = Util.dp2px(2);
    private RectF mRectF;
    private RectF nRectF;
    private Time mTime;
    private float mSecond;

    private Paint mPaint;
    private float mWidth;
    private float mHeight;

    private Handler mHandler;

    public ClockView2(Context context) {
        this(context,null);
    }

    public ClockView2(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ClockView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(PAINTSTROKE);
        mPaint.setAntiAlias(true);
        mTime = new Time();
        mTime.setToNow();
        mSecond = (float) mTime.second;
        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this,1000);
                mTime.setToNow();
                mSecond = mTime.second;
                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        if(modeWidth == MeasureSpec.EXACTLY){
            mWidth = sizeWidth;
        }else{
            mWidth = DEFAULTSIZT;
            if(modeWidth == MeasureSpec.AT_MOST){
                mWidth = Math.min(mWidth,sizeWidth);
            }
        }
        if(modeHeight == MeasureSpec.EXACTLY){
            mHeight = sizeHeight;
        }else{
            mHeight = DEFAULTSIZT;
            if(modeHeight == MeasureSpec.AT_MOST){
                mHeight = Math.min(mHeight,sizeHeight);
            }
        }
        mRectF = new RectF(-mWidth/2+2*PAINTSTROKE,-mHeight/2+2*PAINTSTROKE,
                mWidth/2-2*PAINTSTROKE,mHeight/2-2*PAINTSTROKE);
        nRectF = new RectF(-mWidth/2+6*PAINTSTROKE,-mHeight/2+6*PAINTSTROKE,
                mWidth/2-6*PAINTSTROKE,mHeight/2-6*PAINTSTROKE);
        setMeasuredDimension((int)mWidth,(int)mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawCircle(0,0,mWidth/15,mPaint);
        canvas.drawCircle(0,0,mWidth/2 - 3*PAINTSTROKE,mPaint);

        for (int i = 0; i < 60; i++) {
            if(i%5==0){
                mPaint.setStrokeWidth(3*PAINTSTROKE);
            }else{
                mPaint.setStrokeWidth(PAINTSTROKE);
            }
            canvas.drawArc(mRectF,-91+6*i,2,false, mPaint);
        }

        canvas.save();
        mPaint.setStrokeWidth(PAINTSTROKE);
        canvas.rotate(mSecond/60*360);
        System.out.println("mSecond:"+mSecond/60*360);
        canvas.restore();
        canvas.drawArc(nRectF,mSecond/60*360-91,2,true,mPaint);
    }
}


















