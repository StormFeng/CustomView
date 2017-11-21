package com.nof.customview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/21.
 */

public class LinearGradientView extends View {

    public LinearGradientView(Context context) {
        this(context,null);
    }

    public LinearGradientView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public LinearGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(Util.dp2px(80),Util.dp2px(80));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LinearGradient mLinearGradient = new LinearGradient(0,0,getMeasuredWidth(),0,
                new int[]{Color.RED,Color.CYAN},new float[]{0,1.0f}, Shader.TileMode.REPEAT);
        Paint paint = new Paint();
        paint.setShader(mLinearGradient);
        Rect rect = new Rect(0,0,Util.dp2px(80),Util.dp2px(80));
        canvas.drawRect(rect,paint);

    }
}
