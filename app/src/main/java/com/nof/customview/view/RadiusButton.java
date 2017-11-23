package com.nof.customview.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.nof.customview.R;

/**
 * Created by Administrator on 2017/11/21.
 */

public class RadiusButton extends Button {

    private Paint mPaint;

    private float mRadius;
    private int mPressColor;
    private int mUnPressColor;

    private RectF rectF;
    private Path path = new Path();

    public RadiusButton(Context context) {
        this(context,null);
    }

    public RadiusButton(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public RadiusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadiusButton);
        mRadius = ta.getDimension(R.styleable.RadiusButton_button_radius,5);
        mPressColor = ta.getColor(R.styleable.RadiusButton_button_pressColor, Color.parseColor("#999999"));
        mUnPressColor = ta.getColor(R.styleable.RadiusButton_button_unpressColor, Color.parseColor("#FF7256"));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mUnPressColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            mPaint.setColor(mUnPressColor);
            invalidate();
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            mPaint.setColor(mPressColor);
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rectF = new RectF(0,0,getMeasuredWidth(),getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();
        path.addRoundRect(rectF,dp2px(mRadius),dp2px(mRadius), Path.Direction.CW);
        canvas.drawPath(path,mPaint);
        super.onDraw(canvas);
        canvas.restore();
    }

    private int dp2px(float dp){
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }
}
