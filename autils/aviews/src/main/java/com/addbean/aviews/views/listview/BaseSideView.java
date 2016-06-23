package com.addbean.aviews.views.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by AddBean on 2016/2/16.
 */
public abstract class BaseSideView extends LinearLayout {
    private Context mContext;
    private LayoutInflater mInflater;
    private View mView;

    public final static class State {
        public static final int HEADER_PULLING = 0;//正在下拉
        public static final int HEADER_RELEASE = 1;//下拉时手指放开；
        public static final int HEADER_READY = 2;//下拉到底，准备刷新；
        public static final int HEADER_REFRESHING = 3;//正在刷新；
        public static final int HEADER_DONE = 4;//刷新完成；

        public static final int FOOTER_PULLING = 5;
        public static final int FOOTER_RELEASE = 6;
        public static final int FOOTER_READY = 7;
        public static final int FOOTER_REFRESHING = 8;
        public static final int FOOTER_DONE = 9;
    }

    private int mState;
    public Boolean mIsHeader;

    public BaseSideView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public BaseSideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public BaseSideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(getViewResId(), null);
        this.addView(mView);
        measureSelf();
        invalidate();
        setmIsHeader(getViewType());
        init();
    }


    public void setStatePulling() {

        if (!mIsHeader) setmState(State.FOOTER_PULLING);
        else setmState(State.HEADER_PULLING);
        onPulling();
//        Log.e("BaseSideView", "----------------Pulling-------------------" + mState);
    }

    public void setStateRelease() {
        if (!mIsHeader) setmState(State.FOOTER_RELEASE);
        else setmState(State.HEADER_RELEASE);
        onRelease();
//        Log.e("BaseSideView", "----------------Release-------------------" + mState);
    }

    public void setStateReady() {
        if (!mIsHeader) setmState(State.FOOTER_READY);
        else setmState(State.HEADER_READY);
        onReady();
//        Log.e("BaseSideView", "----------------Ready-------------------" + mState);
    }

    public void setStateRefreshing() {
        if (!mIsHeader) setmState(State.FOOTER_REFRESHING);
        else setmState(State.HEADER_REFRESHING);
        onRefreshing();
//        Log.e("BaseSideView", "----------------Refreshing-------------------" + mState);
    }

    public void setStateDone() {
        if (!mIsHeader) setmState(State.FOOTER_DONE);
        else setmState(State.HEADER_DONE);
        onDone();
//        Log.e("BaseSideView", "----------------Done-------------------" + mState);
    }

    public void measureSelf() {
        ViewGroup.LayoutParams params = this.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, params.width);
        int lpHeight = params.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        this.measure(childWidthSpec, childHeightSpec);
    }

    protected abstract void init();

    public abstract Boolean getViewType();

    public abstract int getViewResId();

    public abstract void onPulling();

    public abstract void onRelease();

    public abstract void onReady();

    public abstract void onRefreshing();

    public abstract void onDone();

    public abstract Boolean onTouch(MotionEvent event);

    public Boolean getmIsHeader() {
        return mIsHeader;
    }

    public void setmIsHeader(Boolean mIsHeader) {
        this.mIsHeader = mIsHeader;
    }

    public int getmState() {

        return mState;
    }

    public void setmState(int mState) {
        this.mState = mState;
    }

    public View getmView() {
        return mView;
    }
}
