package com.twentyfour.chavel.service;

import android.support.v7.app.AppCompatActivity;

import com.twentyfour.chavel.misc.Tools;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Tools.systemBarLolipop(this);
        // mEmoView = (RelativeLayout) getEmoView();

    }
}
