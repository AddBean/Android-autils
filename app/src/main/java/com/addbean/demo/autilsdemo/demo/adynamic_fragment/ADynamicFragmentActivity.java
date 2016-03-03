package com.addbean.demo.autilsdemo.demo.adynamic_fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.addbean.demo.autilsdemo.R;

public class ADynamicFragmentActivity extends FragmentActivity implements TouchHandlerLayout.onInterceptTouchEventListener {

    private DynameicFragment mContentFragment;
    private TouchHandlerLayout mContentFragmentLayout;
    private RelativeLayout mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adynamic_fragment);
        mContentFragmentLayout = (TouchHandlerLayout) findViewById(R.id.content_layout);
        mTitle = (RelativeLayout) findViewById(R.id.title);
        initFragment();
        mContentFragmentLayout.setmOnInterceptTouchEventListener(this);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initFragment() {
        mContentFragment = new DynameicFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_fragment, mContentFragment);
        transaction.show(mContentFragment).commit();
    }


    private void changTitle(float delatY) {
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mTitle.getMeasuredHeight() + delatY));
        mTitle.setLayoutParams(lp2);
//        mContentFragmentLayout.setY(mContentFragmentLayout.getY() + delatY);
        Log.e("ADynamicFragment", "mContentFragmentLayout Y:" + mContentFragmentLayout.getY());
    }


    private float mMoveY;

    @Override
    public boolean onMTouchEvent(MotionEvent ev) {
        float delatY;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMoveY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                delatY = ev.getY() - mMoveY;
                changTitle(delatY);
                mMoveY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

}
