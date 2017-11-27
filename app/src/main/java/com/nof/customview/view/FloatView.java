package com.nof.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nof.customview.R;

/**
 * Created by Administrator on 2017/11/22.
 */

public class FloatView extends RelativeLayout {

    private FloatButton mButton;
    private View mItemsLeft;
    private View mItemsRight;

    public FloatView(Context context) {
        this(context,null);
    }

    public FloatView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init(Context context){
        LinearLayout mContain = new LinearLayout(context);
        LayoutParams p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        p.setLayoutDirection(LinearLayout.HORIZONTAL);
        mContain.setLayoutParams(p);

        LayoutParams pButton = new LayoutParams(Util.dp2px(50), Util.dp2px(50));
        mButton = new FloatButton(context);
        mButton.setLayoutParams(pButton);

        mItemsLeft = LayoutInflater.from(context).inflate(R.layout.layout_item,null);
        mItemsLeft.setMinimumHeight(Util.dp2px(50));
        mItemsRight = LayoutInflater.from(context).inflate(R.layout.layout_item,null);
        mItemsRight.setMinimumHeight(Util.dp2px(50));
        mItemsRight.setVisibility(GONE);

        mContain.addView(mItemsRight);
        mContain.addView(mButton);
        mContain.addView(mItemsLeft);

        addView(mContain);

        mButton.setOnButtonClickListener(new FloatButton.OnButtonClickListener() {
            @Override
            public void onClick(boolean show, boolean left) {
                if(show){
                    if(left){
                        mItemsLeft.setVisibility(VISIBLE);
                        mItemsRight.setVisibility(GONE);
                    }else{
                        mItemsRight.setVisibility(VISIBLE);
                        mItemsLeft.setVisibility(GONE);
                    }
                    mButton.setShowItem(false);
                }else{
                    mItemsLeft.setVisibility(GONE);
                    mItemsRight.setVisibility(GONE);
                    mButton.setShowItem(true);
                }
            }
        });
    }
}




