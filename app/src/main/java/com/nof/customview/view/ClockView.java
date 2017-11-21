package com.nof.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;

import com.nof.customview.R;

/**
 * Created by Administrator on 2017/11/20.
 */

public class ClockView extends View {

    private Time mCalendar;

    private Drawable mHourHand;
    private Drawable mMinuteHand;
    private Drawable mSecondHand;
    private Drawable mDial;

    private int mDialWidth;
    private int mDialHeight;
    private float mScale;

    private float mMinutes;
    private float mHour;
    private float mSecond;

    private boolean mAttached;

    private Paint mPaint;
    private Handler mHandler;

    public ClockView(Context context) {
        this(context,null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mDial = context.getResources().getDrawable(R.mipmap.clock_dial);
        mHourHand = context.getResources().getDrawable(R.mipmap.clock_hand_hour);
        mMinuteHand = context.getResources().getDrawable(R.mipmap.clock_hand_minute);
        mSecondHand = context.getResources().getDrawable(R.mipmap.clock_hand_minute);
        mCalendar = new Time();
        mDialWidth = mDial.getIntrinsicWidth();
        mDialHeight = mDial.getIntrinsicHeight();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this,1000);
                onTimeChanged();
                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float wScale = 1.0f;
        float hScale = 1.0f;

        if(widthMode != MeasureSpec.UNSPECIFIED){
            wScale = (float) widthSize / mDialWidth;
        }
        if(heightMode != MeasureSpec.UNSPECIFIED){
            hScale = (float) heightSize / mDialHeight;
        }
        mScale = Math.min(wScale,hScale);
        setMeasuredDimension(
                resolveSizeAndState((int) (mDialWidth * mScale),widthMeasureSpec,0),
                resolveSizeAndState((int) (mDialHeight * mScale),heightMeasureSpec,0)
        );
    }

    private void onTimeChanged(){
        mCalendar.setToNow();
        int hour = mCalendar.hour;
        int minute = mCalendar.minute;
        int second = mCalendar.second;

        mSecond = (float) second;
        mMinutes = minute + second / 60.0f;
        mHour = hour + mMinutes / 60.0f;
    }

//    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if(intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)){
//                String tz = intent.getStringExtra("time-zone");
//                mCalendar = new Time(TimeZone.getTimeZone(tz).getID());
//            }
//            onTimeChanged();
//            invalidate();
//        }
//    };

//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        if(!mAttached){
//            mAttached = true;
//            IntentFilter filter = new IntentFilter();
//            filter.addAction(Intent.ACTION_TIME_TICK);
//            filter.addAction(Intent.ACTION_TIME_CHANGED);
//            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
//            getContext().registerReceiver(mIntentReceiver, filter);
//        }
//        mCalendar = new Time();
//        onTimeChanged();
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        if(mAttached){
//            getContext().unregisterReceiver(mIntentReceiver);
//            mAttached = false;
//        }
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = (int) (mDialWidth * mScale);
        int height = (int) (mDialHeight * mScale);
        mDial.setBounds(0,0,width,height);
        mDial.draw(canvas);
        canvas.translate(width/2, height/2);

        canvas.save();
        canvas.rotate(mHour / 12 * 360);
        int mHourWidth = (int) (mHourHand.getIntrinsicWidth()*mScale);
        int mHourHeight = (int) (mHourHand.getIntrinsicHeight()*mScale);
        mHourHand.setBounds(-mHourWidth/2,-mHourHeight/2,mHourWidth/2,mHourHeight/2);
        mHourHand.draw(canvas);
        canvas.restore();

        canvas.save();
        canvas.rotate(mMinutes / 60 * 360);
        int mMinuteWidth = (int) (mMinuteHand.getIntrinsicWidth()*mScale);
        int mMinuteHeight = (int) (mMinuteHand.getIntrinsicHeight()*mScale);
        mMinuteHand.setBounds(-mMinuteWidth/2,-mMinuteHeight/2,mMinuteWidth/2,mMinuteHeight/2);
        mMinuteHand.draw(canvas);
        canvas.restore();

        canvas.rotate(mSecond / 60 * 360);
        int mSecondWidth = (int) (mSecondHand.getIntrinsicWidth()*mScale);
        int mSecondHeight = (int) (mSecondHand.getIntrinsicHeight()*mScale);
        mSecondHand.setBounds(-mSecondWidth/2,-mSecondHeight/2,mSecondWidth/2,mSecondHeight/2);
        mSecondHand.draw(canvas);
    }
}
