package com.nof.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/11/22.
 */

public class FloatView extends RelativeLayout {

    private FloatButton mButton;

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
        RelativeLayout rl = new RelativeLayout(context);
        LayoutParams p = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rl.setLayoutParams(p);

        LayoutParams p1 = new LayoutParams(Util.dp2px(50), Util.dp2px(50));
        mButton = new FloatButton(context);
        mButton.setLayoutParams(p1);

        rl.addView(mButton);
        addView(rl);
    }
}




