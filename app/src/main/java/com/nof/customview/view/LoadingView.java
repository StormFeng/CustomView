package com.nof.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import com.nof.customview.R;

/**
 * Created by Administrator on 2017/11/20.
 */

public class LoadingView extends View {

    private Drawable mLoadingPic;
    private float mIntrinsicWidth;
    private float mIntrinsicHeight;

    private int mWidth;
    private int mHeight;

    private float mScale = 1.0f;

    private float mAngle = 0.0f;
    private Handler mHandler;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mLoadingPic = context.getResources().getDrawable(R.mipmap.loading);
        mIntrinsicWidth = mLoadingPic.getIntrinsicWidth();
        mIntrinsicHeight = mLoadingPic.getIntrinsicHeight();
        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this,10);
                mAngle += 5f;
                if(mAngle == 360){
                    mAngle = 0.0f;
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

        float wScale = 1.0f;
        float hScale = 1.0f;

        if(modeWidth!=MeasureSpec.UNSPECIFIED){
            wScale = sizeWidth / mIntrinsicWidth;
        }
        if(modeHeight!=MeasureSpec.UNSPECIFIED){
            hScale = sizeHeight / mIntrinsicHeight;
        }
        mScale = Math.min(wScale,hScale);
        mWidth = (int) (mIntrinsicWidth * mScale);
        mHeight = (int) (mIntrinsicHeight*mScale);
        setMeasuredDimension(
                resolveSizeAndState(mWidth,widthMeasureSpec,0),
                resolveSizeAndState(mHeight,heightMeasureSpec,0)
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        canvas.rotate(-mAngle);
        mLoadingPic.setBounds(-mWidth/2,-mHeight/2,mWidth/2 , mHeight/2);
        mLoadingPic.draw(canvas);
    }
}
