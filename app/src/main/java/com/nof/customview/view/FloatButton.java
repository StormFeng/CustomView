package com.nof.customview.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.nof.customview.R;

/**
 * Created by Administrator on 2017/11/22.
 */

public class FloatButton extends View {

    private float lastX;
    private float lastY;

    private int mScreenWidth;
    private int mScreenHeight;

    private Bitmap mBitmap;

    private Scroller mScroller;

    public FloatButton(Context context) {
        this(context,null);
    }

    public FloatButton(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public FloatButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        mScreenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        mScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        System.out.println("mScreenWidth:"+mScreenWidth+"\n"+"mScreenHeight:"+mScreenHeight);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int oX = (int) (x - lastX);
                int oY = (int) (y - lastY);
                ((View) getParent()).scrollBy(-oX, -oY);
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                int[] p = new int[2];
                getLocationOnScreen(p);
                int left = p[0];
                int right = p[0]+getMeasuredWidth();

//                View parent = (View) getParent();
                if(left<mScreenWidth-right){
                    mScroller.startScroll(-p[0],
                            -p[1],
                            p[0],
                            0);
                }else{
                    mScroller.startScroll(-p[0],
                            -p[1],
                            -(mScreenWidth-p[0]-getMeasuredWidth()),
                            0);
                }
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(
                    mScroller.getCurrX(),mScroller.getCurrY()
            );
            if(mScroller.getCurrY()>=0){
                ((View)getParent()).scrollTo(
                        mScroller.getCurrX(),0
                );
            }
            if(mScroller.getCurrY()<=-mScreenHeight+getMeasuredWidth()){
                ((View)getParent()).scrollTo(
                        mScroller.getCurrX(),-mScreenHeight+getMeasuredHeight()
                );
            }
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap,0,0,new Paint());
    }
}
