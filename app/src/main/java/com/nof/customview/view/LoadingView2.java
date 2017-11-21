package com.nof.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/20.
 */

public class LoadingView2 extends View {

    private int mWidth;
    private int mHeight;
    private int mPadding = Util.dp2px(3);
    private float mAngle = 5;

    private Handler mHandler;

    private Paint mPaint;


    public LoadingView2(Context context) {
        this(context,null);
    }

    public LoadingView2(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public LoadingView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setStrokeWidth(Util.dp2px(3));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this,10);
                invalidate();
                mAngle+=5;
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
            mWidth = Util.dp2px(20);
            if(modeWidth == MeasureSpec.AT_MOST){
                mWidth = Math.min(mWidth,sizeWidth);
            }
        }

        if(modeHeight == MeasureSpec.EXACTLY){
            mHeight = sizeHeight;
        }else{
            mHeight = Util.dp2px(20);
            if(modeHeight == MeasureSpec.AT_MOST){
                mHeight = Math.min(mHeight,sizeHeight);
            }
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        canvas.save();
        canvas.rotate(mAngle);
        RectF rectF = new RectF(-mWidth/2+mPadding,-mHeight/2+mPadding,mWidth/2-mPadding,mHeight/2-mPadding);
        canvas.drawArc(rectF,0,300,false,mPaint);
        canvas.restore();

        canvas.scale(0.8f,0.8f);
        canvas.rotate(-mAngle);
        RectF rectF1 = new RectF(-mWidth/3+mPadding,-mHeight/3+mPadding,mWidth/3-mPadding,mHeight/3-mPadding);
        canvas.drawArc(rectF1,0,300,false,mPaint);
    }
}
