package com.nof.customview.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;


/**
 * Created by Administrator on 2017/11/24.
 */

public class GroupView extends ViewGroup {

    private float lastX;
    private int currPosition = 0;
    private int itemWidth;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private VelocityTracker velocityTracker;
    private Scroller mScroller;

    private View nextView;

    public GroupView(Context context) {
        this(context,null);
    }

    public GroupView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public GroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        velocityTracker = VelocityTracker.obtain();
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int totalWidth = 0;
        int totalHeight = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            totalWidth += child.getMeasuredWidth();
            if(child.getMeasuredHeight()>totalHeight){
                totalHeight = child.getMeasuredHeight();
            }
        }
        setMeasuredDimension(totalWidth,totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int left = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            itemWidth = childWidth;
            int offset = (screenWidth-childWidth)/2;
            child.layout(left+offset,0,left+childWidth+offset,childWidth);
            left += childWidth;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        nextView = getChildAt(currPosition+1);
        float x = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                float delX = x-lastX;
                scrollBy(-(int)delX,0);
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                velocityTracker.computeCurrentVelocity(1000);
                float vX= velocityTracker.getXVelocity();
                if(Math.abs(vX) >= 10){
                    currPosition = vX>0 ? currPosition-1 : currPosition+1;
                }else{
                    currPosition = (scrollX+itemWidth/2)/itemWidth;
                }
                currPosition = Math.max(0,Math.min(currPosition,4));
                int dx = currPosition * itemWidth - scrollX;
                smoothScrollBy(dx, 0);
                velocityTracker.clear();
                break;
        }
        return true;
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = true;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept = true;
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return intercept;
    }
}











