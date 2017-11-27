package com.nof.customview.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
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

    private int size = Util.dp2px(50);
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int touchSlop = new ViewConfiguration().getScaledTouchSlop();
    private float curX,curY;
    private float lastX,lastY;
    private float showX,showY;
    private boolean showMenu = false;
    private boolean showLeft = true;
    private FloatPopupItem item;
    private Activity context;
    private OnClickListener onClickListener;
    private Handler handler;
    private Message message;

    @SuppressLint("HandlerLeak")
    public FloatPopup(Context context) {
        this.context = (Activity) context;
        item = new FloatPopupItem(context);
        item.setOnItemClickListener(this);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(!showMenu){
                    toSmallIcon(msg.arg1,msg.arg2);
                }
            }
        };
        message = handler.obtainMessage();
        message.what = 0;

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
//                        float ddx = lastX - curX;
//                        float ddy = lastY - curY;
//                        if(Math.abs(ddx)<touchSlop && Math.abs(ddy)<touchSlop){
//                            return true;
//                        }
                        if(curY<size/2){
                            update((int)event.getRawX() - size/2,0);
                        }else if(curY>screenHeight-size/2){
                            update((int)event.getRawX() - size/2,screenHeight-size);
                        }else{
                            update((int)event.getRawX() - size/2,(int)event.getRawY()-size/2);
                        }
                        if(item.isShowing()){
                            item.dismiss();
                            showMenu = !showMenu;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(curX<screenWidth/2){
                            showLeft = true;
                            showX = 0;
                            showY = event.getRawY()-size/2;
                        }else{
                            showLeft = false;
                            showX = screenWidth-size;
                            showY = event.getRawY()-size/2;
                        }
                        update((int) showX,(int) showY);

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

                        handler.removeMessages(0);

                        message = handler.obtainMessage();
                        message.what = 0;
                        message.arg1 = (int) showX;
                        message.arg2 = (int) showY;
                        handler.sendMessageDelayed(message,5000);
                        System.out.println("oooooooooooooo");
                        break;
                }
                return true;
            }
        });
    }

    private void toSmallIcon(int curx,int cury){
        if(showLeft){
            update(curx,cury,size/2,size/2);
        }else{
            update(curx+size/2,cury,size/2,size/2);
        }
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
