package com.addbean.demo.autilsdemo.demo.adynamic_fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by AddBean on 2016/3/3.
 */
public class TouchHandlerLayout extends LinearLayout {
    public TouchHandlerLayout(Context context) {
        super(context);
    }

    public TouchHandlerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchHandlerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float mDownY;
    private float mMoveY;
    private float mTouchSlop = 50;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = ev.getY();
                float diff = Math.abs(mMoveY - mDownY);
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mOnInterceptTouchEventListener != null)
            return mOnInterceptTouchEventListener.onMTouchEvent(ev);
        else
            return super.onTouchEvent(ev);
    }

    private onInterceptTouchEventListener mOnInterceptTouchEventListener;

    public void setmOnInterceptTouchEventListener(onInterceptTouchEventListener mOnInterceptTouchEventListener) {
        this.mOnInterceptTouchEventListener = mOnInterceptTouchEventListener;
    }

    public interface onInterceptTouchEventListener {
        public boolean onMTouchEvent(MotionEvent ev);
    }
}
