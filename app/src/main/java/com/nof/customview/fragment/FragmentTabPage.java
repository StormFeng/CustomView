package com.nof.customview.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

/**
 * Created by Administrator on 2017/11/21.
 */

public class FragmentTabPage extends FragmentActivity {

    private RadioGroup mTabs;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private FragmentManager fm;
    private FragmentTransaction transaction;

    private int fragmentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = getResources().getIdentifier("fragment", "layout",
                getPackageName());
        setContentView(layout);
        mTabs = findViewById(
                getResources().getIdentifier("rgTabs","id",getPackageName()));
        fragmentId = getResources().getIdentifier("fragment","id",getPackageName());

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fm = getSupportFragmentManager();
        mTabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                transaction = fm.beginTransaction();
                if(checkedId == getResources().getIdentifier("rb1","id",getPackageName())){
                    transaction.replace(fragmentId,fragment1);
                }else if(checkedId == getResources().getIdentifier("rb2","id",getPackageName())){
                    transaction.replace(fragmentId,fragment2);
                }else{
                    transaction.replace(fragmentId,fragment3);
                }
                transaction.commit();
            }
        });
    }
}
