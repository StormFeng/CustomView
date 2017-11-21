package com.nof.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.nof.customview.R;


/**
 * Created by Administrator on 2017/11/20.
 */

public class ProgressView2 extends View {

    private float mSize;
    private int mPadding = Util.dp2px(15);
    private int mPercent = 90;

    private Paint mPaint;
    private Paint mTextPaint;

    private Handler mHandler;

    public ProgressView2(Context context) {
        this(context,null);
    }

    public ProgressView2(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ProgressView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(Util.dp2px(18));
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this,100);
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

        int mWidth;
        int mHeight;

        if(modeWidth == MeasureSpec.EXACTLY){
            mWidth = sizeWidth;
        }else{
            mWidth = Util.dp2px(50);
            if(modeHeight == MeasureSpec.AT_MOST){
                mWidth = Math.min(mWidth,sizeWidth);
            }
        }

        if(modeHeight == MeasureSpec.EXACTLY){
            mHeight = sizeHeight;
        }else{
            mHeight = Util.dp2px(50);
            if(modeHeight == MeasureSpec.AT_MOST){
                mHeight = Math.min(mHeight,sizeHeight);
            }
        }
        mSize = Math.min(mWidth,mHeight);
        setMeasuredDimension((int)mSize,(int)mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mSize/2,mSize/2);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(Util.dp2px(2));
        canvas.drawCircle(0,0,mSize/2-mPadding/2,mPaint);

        mPaint.setStrokeWidth(mPadding);
        RectF rectF = new RectF(-mSize/2+mPadding,-mSize/2+mPadding,
                mSize/2-mPadding,mSize/2-mPadding);
        for (float i = 0; i < 100; i++) {
            if(i<mPercent){
                mPaint.setColor(Color.RED);
            }else{
                mPaint.setColor(Color.LTGRAY);
            }
            canvas.drawArc(rectF, 3.6f * i-90,3.0f,false,mPaint);
        }

        float[] floats = {0,0,0,0};
        mTextPaint.getTextWidths(mPercent+"%",floats);
        float xCenter=0;
        for (int i = 0; i < floats.length; i++) {
            xCenter+=floats[i];
        }
        xCenter = xCenter/2;
        int yCenter = (mTextPaint.getFontMetricsInt().descent + mTextPaint.getFontMetricsInt().ascent) / 2;
        canvas.drawText(mPercent+"%",-xCenter,-yCenter,mTextPaint);
    }
}
