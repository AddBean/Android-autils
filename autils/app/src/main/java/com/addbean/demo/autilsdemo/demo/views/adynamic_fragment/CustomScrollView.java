package com.addbean.demo.autilsdemo.demo.views.adynamic_fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by AddBean on 2016/3/4.
 */
public class CustomScrollView extends ScrollView {
    public CustomScrollView(Context context) {
        super(context);
        initView();
    }


    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();

    }

    private void initView() {
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }


    private float mDownY;
    private float mMoveY;
    private float mTouchSlop = 50;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (mOnTouchEventListener != null)
            mOnTouchEventListener.onMTouchEvent(ev);
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mDownY = ev.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mMoveY = ev.getRawY();
//                float diff = Math.abs(mMoveY - mDownY);
//                if (diff > mTouchSlop) {
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
        return  super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onCustomTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    private onTouchEventListener mOnTouchEventListener;

    public void setmOnTouchEventListener(onTouchEventListener mOnTouchEventListener) {
        this.mOnTouchEventListener = mOnTouchEventListener;
    }

    public interface onTouchEventListener {
        public boolean onMTouchEvent(MotionEvent ev);
    }

}
