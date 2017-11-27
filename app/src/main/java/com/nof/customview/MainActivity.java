package com.nof.customview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import com.nof.customview.view.FloatPopup;

public class MainActivity extends FragmentActivity{

    FloatPopup popupWindow;
    Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        btnShow = findViewById(R.id.btnShow);
        popupWindow = new FloatPopup(this);
        popupWindow.setOnClickListener(new FloatPopup.OnClickListener() {
            @Override
            public void onClick(int i) {
                System.out.println("item "+i+" is clicked");
            }
        });
    }

    public void show(View view){
        if(!popupWindow.isShowing()){
            popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY,0,0);
        }
    }
}
