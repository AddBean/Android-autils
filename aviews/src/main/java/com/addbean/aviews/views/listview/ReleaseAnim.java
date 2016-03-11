package com.addbean.aviews.views.listview;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by AddBean on 2016/2/16.
 */
public class ReleaseAnim extends Animation {
    private View mView;
    private final int mViewHeigh;
    private int mStartPadding;
    private Boolean mIsRefresh;
    private int mEndPadding;

    public ReleaseAnim(View view, int offset,int endPading, int startPading, Boolean isRefresh) {
        this.mView = view;
        this.mStartPadding = startPading;
        this.mViewHeigh = offset;
        this.mIsRefresh = isRefresh;
        this.mEndPadding =endPading;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    //其中interpolatedTime 为当前动画帧对应的相对时间,值总在0-1之间
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (mIsRefresh) {
            int paddingTop = (-(mViewHeigh-mEndPadding) + (int) (((1 - interpolatedTime) * (mStartPadding))));
//            if(Math.abs(paddingTop)<=Math.abs(mViewHeigh))
            this.mView.setPadding(0, paddingTop, 0, 0);
        } else {
            int paddingBottom = (-(mViewHeigh-mEndPadding) + (int) (((1 - interpolatedTime) * Math.abs(mStartPadding))));
            this.mView.setPadding(0, 0, 0, paddingBottom);
        }

        this.mView.invalidate();
    }
}
