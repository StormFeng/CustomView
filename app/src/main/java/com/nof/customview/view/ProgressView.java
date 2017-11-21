package com.nof.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import com.nof.customview.R;


/**
 * Created by Administrator on 2017/11/20.
 */

public class ProgressView extends View {

    private int mColor = 0xFF0000;
    private int mWidth;
    private int mHeight;

    private Paint mPaint;

    private Handler mHandler;
    private int mLendth = 200;
    private int mInterval = mLendth / 10;
    private int mIndex = mLendth / mInterval;

    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        mColor = ta.getColor(R.styleable.ProgressView_progressColor, Color.RED);
        float dimension = ta.getDimension(R.styleable.ProgressView_progressItemLength, 40.0f);
        mLendth = Util.dp2px(dimension < 40.0 ? (int) 40.0 : (int) dimension);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(Util.dp2px(6));
        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this,100);
                mIndex -- ;
                if(mIndex == 0){
                    mIndex = mLendth / mInterval;
                }
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
            mWidth =100;
            if(modeHeight == MeasureSpec.AT_MOST){
                mWidth = Math.min(mWidth,sizeWidth);
            }
        }

        if(modeHeight == MeasureSpec.EXACTLY){
            mHeight = sizeHeight;
        }else{
            mHeight =Util.dp2px(6);
            if(modeHeight == MeasureSpec.AT_MOST){
                mHeight = Math.min(mHeight,sizeHeight);
            }
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float[] floats = new float[]{
                0,0,
                mLendth-mIndex*mInterval,0,
                mLendth-(mIndex-1)*mInterval,0,
                2*mLendth-mIndex*mInterval,0,
                2*mLendth-(mIndex-1)*mInterval,0,
                3*mLendth-mIndex*mInterval,0,
                3*mLendth-(mIndex-1)*mInterval,0,
                4*mLendth-mIndex*mInterval,0,
                4*mLendth-(mIndex-1)*mInterval,0,
                5*mLendth-mIndex*mInterval,0,
                5*mLendth-(mIndex-1)*mInterval,0,
                6*mLendth-mIndex*mInterval,0,
                6*mLendth-(mIndex-1)*mInterval,0,
                7*mLendth-mIndex*mInterval,0,
                7*mLendth-(mIndex-1)*mInterval,0,
                8*mLendth-mIndex*mInterval,0,
                8*mLendth-(mIndex-1)*mInterval,0,
                9*mLendth-mIndex*mInterval,0,
                9*mLendth-(mIndex-1)*mInterval,0,
                10*mLendth-mIndex*mInterval,0,
                10*mLendth-(mIndex-1)*mInterval,0,
                11*mLendth-mIndex*mInterval,0,
        };
        canvas.drawLines(floats,mPaint);
    }
}
