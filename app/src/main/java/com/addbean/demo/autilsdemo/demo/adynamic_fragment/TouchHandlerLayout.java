package com.addbean.demo.autilsdemo.demo.adynamic_fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mDownY = ev.getRawY();
//                mOnInterceptTouchEventListener.onMTouchEvent(ev);
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
//        return super.onInterceptTouchEvent(ev);
//    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e("TouchHandlerLayout","layout");
    }
}
