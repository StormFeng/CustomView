package com.nof.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/22.
 */

public class AudioView extends View {

    private static final float MAXHEIGHT = 500f;
    private int mItemWidth = 50;
    private int mInterval = 15;

    private Paint mItemPaint;
    private LinearGradient mLinearGradient;

    public AudioView(Context context) {
        this(context,null);
    }

    public AudioView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public AudioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mItemPaint = new Paint();
        mItemPaint.setStyle(Paint.Style.FILL);
        mItemPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0,getMeasuredHeight());
        canvas.scale(1,-1);

        for (int i = 0; i < 15; i++) {
            float curHeight = (float) (Math.random() * MAXHEIGHT);
            mLinearGradient = new LinearGradient(0,curHeight,
                    0,0,
                    new int[]{Color.RED,Color.CYAN},
                    new float[]{0.1f,0.9f}, Shader.TileMode.CLAMP);
            mItemPaint.setShader(mLinearGradient);
            RectF rectF = new RectF(i*mItemWidth+i*mInterval,0,
                    (i+1)*mItemWidth+i*mInterval,curHeight);
            canvas.drawRect(rectF,mItemPaint);
        }

        postInvalidateDelayed(300);
    }
}
