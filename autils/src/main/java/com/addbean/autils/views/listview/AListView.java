package com.addbean.autils.views.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AbsListView;
import android.widget.ListView;

import com.addbean.autils.views.hoverlist.HoverListView;

/**
 * Created by AddBean on 2016/2/16.
 */
public class AListView extends ListView implements AbsListView.OnScrollListener {
    private Context mContext;
    private BaseSideView mHeaderView;
    private BaseSideView mFooterView;
    private int mHeaderOffset;
    private int mFooterOffset;
    private float mStartY;
    private IOnPullListener mOnPullListener;
    private boolean mIsRefreshUnlock;
    private boolean mIsLoadingUnlock;
    private float mRefreshDelatY = 0;
    private float mLoadingDelatY = 0;
    private float mRefreshEndDelatY = 0;
    private float mLoadingEndDelatY = 0;
    private boolean mRefreshEnable = true;
    private boolean mLoadingEnable = true;


    public AListView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public AListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public AListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        if (this.mHeaderView == null) setHeaderView(new DefaultHeaderView(this.mContext));
        if (this.mFooterView == null) setFooterView(new DefaultFooterView(this.mContext));
        this.mHeaderOffset = this.mHeaderView.getMeasuredHeight();
        this.mFooterOffset = this.mFooterView.getMeasuredHeight();
        this.mHeaderView.setPadding(0, -1 * this.mHeaderOffset, 0, 0);
        this.mFooterView.setPadding(0, 0, 0, -1 * this.mHeaderOffset);
        this.mHeaderView.setStateDone();
        this.mFooterView.setStateDone();
        this.addHeaderView(mHeaderView);
        this.addFooterView(mFooterView);
        setOnScrollListener(this);// 设置滚动监听事件
    }

    public void setOnPullListener(IOnPullListener onPullListener) {
        this.mOnPullListener = onPullListener;
    }

    public void setHeaderView(BaseSideView headerView) {
        this.mHeaderView = headerView;
    }

    public void setFooterView(BaseSideView footerView) {
        this.mFooterView = footerView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        setOnScrollListener(this);
        if (mRefreshEnable) {
            refreshTouchHandler(event);
        }
        if (mLoadingEnable) {
            loadingTouchHandler(event);
        }
        return super.onTouchEvent(event);
    }

    public void setRefreshAndLoadingEnable(boolean refreshEnable, boolean loadingEnable) {
        this.mRefreshEnable = refreshEnable;
        this.mLoadingEnable = loadingEnable;
    }

    public boolean isLoadingEnable() {
        return mLoadingEnable;
    }

    public boolean isRefreshEnable() {
        return mRefreshEnable;
    }

    private Boolean refreshTouchHandler(MotionEvent event) {
        if (!mIsRefreshUnlock) {
            if (mHeaderView.getmState() == BaseSideView.State.HEADER_DONE)
                return false;
            mHeaderView.setStateDone();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mHeaderView.getmState() == BaseSideView.State.HEADER_DONE) {
                    mHeaderView.clearAnimation();
                    mHeaderView.setStatePulling();
                }
                float tempY = event.getY();
                if (mHeaderView.getmState() == BaseSideView.State.HEADER_PULLING || mHeaderView.getmState() == BaseSideView.State.HEADER_READY || mHeaderView.getmState() == BaseSideView.State.HEADER_RELEASE) {
                    mRefreshDelatY = tempY - mStartY;
//                    Log.e("AListView", "delatY:" + delatY + " mStartY:" + mStartY);
                    if (mRefreshDelatY >= mHeaderOffset) {//拉出高度；
                        if (mHeaderView.getmState() == BaseSideView.State.HEADER_PULLING)
                            mHeaderView.setStateReady();
                    }
                    if (mRefreshDelatY <= mHeaderOffset * 2) {
                        mHeaderView.setPadding(0, (int) (-1 * (mHeaderOffset - mRefreshDelatY)), 0, 0);
                        mRefreshEndDelatY = mRefreshDelatY;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mHeaderView.getmState() == BaseSideView.State.HEADER_PULLING) {
                    mHeaderView.setStateRelease();
                    doHeaderCollapse((int) (mRefreshDelatY), 0);
                }
                if (mHeaderView.getmState() == BaseSideView.State.HEADER_READY) {
                    doHeaderCollapse((int) (mRefreshEndDelatY) / 2, mHeaderOffset);
                    mHeaderView.setStateRefreshing();
                    if (this.mOnPullListener != null) {
                        this.mOnPullListener.onRefresh();
                    }
                }
                break;
        }
        return this.mHeaderView.onTouch(event);
    }


    private Boolean loadingTouchHandler(MotionEvent event) {
        if (!mIsLoadingUnlock) {
            if (mFooterView.getmState() == BaseSideView.State.FOOTER_DONE)
                return false;
            mFooterView.setStateDone();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mFooterView.getmState() == BaseSideView.State.FOOTER_DONE) {
                    mFooterView.clearAnimation();
                    mFooterView.setStatePulling();
                }
                float tempY = event.getY();
                if (mFooterView.getmState() == BaseSideView.State.FOOTER_PULLING || mFooterView.getmState() == BaseSideView.State.FOOTER_READY || mFooterView.getmState() == BaseSideView.State.FOOTER_RELEASE) {
                    mLoadingDelatY = -tempY + mStartY;
//                    Log.e("AListView", "delatY:" + delatY);
                    if (Math.abs(mLoadingDelatY) > mFooterOffset) {//拉出高度；
                        mFooterView.setStateReady();
                    }
                    if (mLoadingDelatY <= mFooterOffset * 2) {
//                        mFooterView.setStatePulling();
                        mFooterView.setPadding(0, 0, 0, (int) (-1 * (mFooterOffset - mLoadingDelatY)));
                        mLoadingEndDelatY = mLoadingDelatY;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mFooterView.getmState() == BaseSideView.State.FOOTER_PULLING) {
                    mFooterView.setStateRelease();
                    Log.e("AListView", "mLoadingDelatY - mStartY:" + (mLoadingDelatY - mStartY));
                    doFooterCollapse((int) (mLoadingDelatY - mStartY), 0);
                }
                if (mFooterView.getmState() == BaseSideView.State.FOOTER_READY) {
                    doFooterCollapse((int) (mLoadingEndDelatY) / 2, mFooterOffset);
                    mFooterView.setStateRefreshing();
                    if (this.mOnPullListener != null) {
                        this.mOnPullListener.onLoading();
                    }
                }
                break;
        }
        return this.mFooterView.onTouch(event);

    }

    public void setRefreshComplete() {
        if (mHeaderView.getmState() == BaseSideView.State.HEADER_REFRESHING) {
            doHeaderCollapse(mHeaderOffset, 0);
        }
        this.mHeaderView.setStateDone();
    }

    public void setLoadingComplete() {
        if (mFooterView.getmState() == BaseSideView.State.FOOTER_REFRESHING) {
            doFooterCollapse(mFooterOffset, 0);

        }
        this.mFooterView.setStateDone();
    }

    private void doHeaderCollapse(int startPadding, final int endPadding) {
        ReleaseAnim anim = new ReleaseAnim(this.mHeaderView, mHeaderOffset, endPadding, startPadding, true);
        anim.setDuration(300);
        AnimationSet anmSet = new AnimationSet(true);
        anmSet.addAnimation(anim);
        anmSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (endPadding == 0)//若是到底;
                    mHeaderView.setStateDone();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anmSet.startNow();
        this.mHeaderView.startAnimation(anmSet);
    }

    private void doFooterCollapse(int startPadding, final int endPadding) {
        ReleaseAnim anim = new ReleaseAnim(this.mFooterView, mFooterOffset, endPadding, startPadding, false);
        anim.setDuration(300);
        AnimationSet anmSet = new AnimationSet(true);
        anmSet.addAnimation(anim);
        anmSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (endPadding == 0)
                    mFooterView.setStateDone();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anmSet.startNow();
        this.mFooterView.startAnimation(anmSet);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (firstVisibleItem == 0) {
            mIsRefreshUnlock = true;
            Log.e("onScroll","到顶:");
        } else {
            mIsRefreshUnlock = false;
            this.mHeaderView.setPadding(0, -1 * this.mHeaderOffset, 0, 0);
        }

        if (visibleItemCount + firstVisibleItem == totalItemCount&&firstVisibleItem != 0) {
            Log.e("onScroll","到低");
            mIsLoadingUnlock = true;
        } else {
            mIsLoadingUnlock = false;
            this.mFooterView.setPadding(0, 0, 0, -1 * this.mHeaderOffset);
        }

    }

    public BaseSideView getmFooterView() {
        return mFooterView;
    }

    public BaseSideView getmHeaderView() {
        return mHeaderView;
    }
}
