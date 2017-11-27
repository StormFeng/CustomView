package com.nof.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/11/25.
 */

public class Test extends ListView {

    private int mMaxOverDistance = Util.dp2px(50);

    public Test(Context context) {
        this(context,null);
    }

    public Test(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public Test(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                   int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, mMaxOverDistance, isTouchEvent);
    }
}



