package com.nof.customview.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Administrator on 2017/11/21.
 */

public class Test extends Button {

    private Paint mPaint;
    private float mViewWidth;

    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private float mTranslate;

    public Test(Context context) {
        this(context,null);
    }

    public Test(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public Test(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mViewWidth == 0){
            mViewWidth = getMeasuredWidth();
            if(mViewWidth > 0){
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0,0,mViewWidth,0,
                        new int[]{Color.BLUE,0xffffff},
                        null,
                        Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mMatrix!=null){
            mTranslate+=mViewWidth/2;
            if(mTranslate>mViewWidth){
                mTranslate = 0;
            }
            mMatrix.setTranslate(mTranslate,0);
            mLinearGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(1000);
        }
    }

    private int dp2px(float dp){
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }
}
