package com.addbean.demo.autilsdemo.demo.adynamic_fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.addbean.demo.autilsdemo.R;

public class ADynamicFragmentActivity extends FragmentActivity  {
    private HorizontalScrollView mTitleLayout;
    private DynameicFragment mContentFragment;
    private TouchHandlerLayout mContentFragmentLayout;
    private RelativeLayout mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adynamic_fragment);
        initFragment();

    }

    private void initFragment() {
        mContentFragment = new DynameicFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_fragment, mContentFragment);
        transaction.show(mContentFragment).commit();
    }








}
