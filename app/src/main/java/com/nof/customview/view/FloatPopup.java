package com.nof.customview.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.nof.customview.R;

/**
 * Created by Administrator on 2017/11/27.
 */

public class FloatPopup extends PopupWindow implements FloatPopupItem.OnItemClickListener {

    int size = Util.dp2px(50);
    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    int touchSlop = new ViewConfiguration().getScaledTouchSlop();
    float curX,curY;
    float lastX,lastY;
    float showX,showY;
    boolean showMenu = false;
    boolean showLeft = true;
    FloatPopupItem item;
    Activity context;
    OnClickListener onClickListener;

    public FloatPopup(Context context) {
        this.context = (Activity) context;
        item = new FloatPopupItem(context);
        item.setOnItemClickListener(this);

        ImageView iv = new ImageView(context);
        iv.setMinimumWidth(Util.dp2px(50));
        iv.setMinimumHeight(Util.dp2px(50));
        iv.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher_round));
        setContentView(iv);
        setWidth(size);
        setHeight(size);
        setFocusable(false);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setOutsideTouchable(false);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                curX = event.getRawX();
                curY = event.getRawY();
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        update((int)event.getRawX() - size/2,(int)event.getRawY()-size/2);
                        if(item.isShowing()){
                            item.dismiss();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(curX<screenWidth/2){
                            showLeft = true;
                            showX = 0;
                            showY = event.getRawY()-size/2;
                            update((int) showX,(int) showY);
                        }else{
                            showLeft = false;
                            showX = screenWidth-size;
                            showY = event.getRawY()-size/2;
                            update((int) showX,(int) showY);
                        }

                        float dx = lastX - curX;
                        float dy = lastY - curY;
                        if(Math.abs(dx)<touchSlop && Math.abs(dy)<touchSlop){
                            if(!showMenu){
                                // TODO: 2017/11/27 显示
                                showMenu();
                            }else{
                                // TODO: 2017/11/27 隐藏
                                hideMenu();
                            }
                            showMenu = !showMenu;
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void hideMenu() {
        if(item!=null){
            item.dismiss();
        }
    }

    private void showMenu(){
        if(showLeft){
            item.showAtLocation(context.getWindow().getDecorView(),Gravity.NO_GRAVITY,(int)(showX+size),(int)showY);
        }else{
            item.showAtLocation(context.getWindow().getDecorView(),Gravity.NO_GRAVITY,(int)(showX-item.width),(int)showY);
        }
    }

    @Override
    public void update(int x, int y) {
        this.update(x,y,size, size);
    }
 
    @Override
    public void update(int x, int y, int width, int height) {
        super.update(x, y, width, height);
    }

    @Override
    public void onItemClick(int i) {
        if(onClickListener!=null){
            onClickListener.onClick(i);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onClick(int i);
    }
}
