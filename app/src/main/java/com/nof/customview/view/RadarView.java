package com.nof.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/22.
 */

public class RadarView extends View {

    private int mCount = 6;
    private float mRadius;
    private double mAngle = Math.PI/3;
    private float mSize;

    private double[] data = {90,60,88,32,100,50};
    private float maxValue = 100f;

    private Paint mainPaint;
    private Paint valuePaint;
    private Paint textPaint;
    
    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setStrokeWidth(1);
        mainPaint.setColor(Color.parseColor("#666666"));

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        valuePaint.setStrokeWidth(1);
        valuePaint.setColor(getResources().getColor(android.R.color.holo_red_light));
        valuePaint.setAlpha(127);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mSize = Math.min(width,height);
        setMeasuredDimension((int)mSize,(int)mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mSize/2,mSize/2);
        drawPolygon(canvas);
        drawLines(canvas);
        drawRegion(canvas);
    }

    private void drawPolygon(Canvas canvas){
        Path path = new Path();
        mRadius = mSize*0.9f/2;
        float r = mRadius/mCount;
        for (int i = 1; i <= mCount; i++) {
            float curR = i*r;
            for (int j = 0; j < mCount; j++) {
                if(j==0){
                    path.moveTo(curR,0);
                }else{
                    float x = (float) (curR * Math.cos(mAngle*j));
                    float y = (float) (curR * Math.sin(mAngle*j));
                    path.lineTo(x,y);
                }
            }
            path.close();
        }
        canvas.drawPath(path,mainPaint);
    }

    private void drawLines(Canvas canvas){
        canvas.drawLine(0,0,mRadius,0,mainPaint);
        for (int i = 1; i < 6; i++) {
            canvas.save();
            canvas.rotate((float) (mAngle/Math.PI*180*i));
            canvas.drawLine(0,0,mRadius,0,mainPaint);
            canvas.restore();
        }
    }

    private void drawRegion(Canvas canvas){
        Path path = new Path();
        for (int i = 0; i < data.length; i++) {
            double percent = data[i]/maxValue;
            float x;
            float y;
            if(i==0){
                x = (float) (mRadius * percent);
                y = 0;
                path.moveTo(x,y);
            }else{
                System.out.println("angle:"+mAngle*i);
                System.out.println(" Math.cos(mAngle*j):"+ Math.cos(mAngle*i)+"\n"+"Math.sin(mAngle*j):"+Math.sin(mAngle*i));
                x = (float) (mRadius * Math.cos(mAngle*i) * percent);
                y = (float) (mRadius * Math.sin(mAngle*i) * percent);
                path.lineTo(x,y);
            }
            canvas.drawCircle(x,y,10,valuePaint);
        }
        path.close();
        canvas.drawPath(path,valuePaint);
    }

    private void drawText(Canvas canvas){

    }
}














