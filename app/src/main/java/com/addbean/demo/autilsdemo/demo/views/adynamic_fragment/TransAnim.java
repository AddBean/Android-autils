package com.addbean.demo.autilsdemo.demo.views.adynamic_fragment;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by AddBean on 2016/3/4.
 */
public class TransAnim extends Animation {

    private float mFromY2;
    private View mTitle;
    private Context mContext;
    private float mFromY;
    private float mToY;
    private View mView;
    private float mDeltaY;
    private float mTempTime = 0;

    public TransAnim(Context mContext, float mFromY, float mToY, View mView, View mTitle) {
        this.mContext = mContext;
        this.mFromY = mFromY;
        this.mToY = mToY;
        this.mView = mView;
        mDeltaY = mToY - mView.getTranslationY();
        this.mFromY = mView.getTranslationY();
        this.mFromY2 = mTitle.getTranslationY();
        this.mTitle = mTitle;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Log.e("interpolatedTime", "interpolatedTime  " + interpolatedTime);
        mView.setTranslationY(mFromY + interpolatedTime * mDeltaY);
        mTitle.setTranslationY(mFromY2 + interpolatedTime * mDeltaY);
    }
}
