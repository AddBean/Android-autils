package com.addbean.demo.autilsdemo.demo.views.adynamic_fragment;

import android.annotation.SuppressLint;

import com.addbean.aviews.views.dynamic_fragment.ADynamicBaseSubFragment;
import com.addbean.demo.autilsdemo.R;

/**
 * Created by AddBean on 2016/3/1.
 */
@SuppressLint("ValidFragment")
public class DynameicSubFragment extends ADynamicBaseSubFragment {
    private CustomScrollView.onTouchEventListener  mOnTouchEventListener;

    public DynameicSubFragment(Object mTag, CustomScrollView.onTouchEventListener onTouchEventListener) {
        super(mTag);
        this.mOnTouchEventListener=onTouchEventListener;
    }

    public CustomScrollView mScrollView;

    @Override
    public int getFragmentView() {
        return R.layout.fragment_content;
    }

    @Override
    public void initView() {
        mScrollView= (CustomScrollView) getCurrentView().findViewById(R.id.scroll_view);
        mScrollView.setmOnTouchEventListener(mOnTouchEventListener);
    }

    public CustomScrollView getmScrollView() {
        return mScrollView;
    }

    @Override
    public void onResumeView() {

    }
}
