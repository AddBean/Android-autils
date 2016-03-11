package com.addbean.aviews.views.listview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by AddBean on 2016/2/16.
 */
public class AListView extends ListView implements AbsListView.OnScrollListener {
    private Context mContext;
    private BaseSideView mHeaderView;
    private BaseSideView mFooterView;
    private int mHeaderOffset;
    private int mFooterOffset;
    private IOnPullListener mOnPullListener;
    private boolean mIsRefreshUnlock;
    private boolean mIsLoadingUnlock;
    private float mRefreshDelatY = 0;
    private float mLoadingDelatY = 0;
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

        float delatY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mRefreshDelatY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mHeaderView.getmState() == BaseSideView.State.HEADER_DONE) {
                    mHeaderView.clearAnimation();
                    mHeaderView.setStatePulling();
                }
                delatY = event.getRawY() - mRefreshDelatY;
                mRefreshDelatY = event.getRawY();
                if (mHeaderView.getmState() == BaseSideView.State.HEADER_PULLING || mHeaderView.getmState() == BaseSideView.State.HEADER_READY || mHeaderView.getmState() == BaseSideView.State.HEADER_RELEASE) {
                    if (mHeaderView.getPaddingTop() >= mHeaderOffset) {//拉出高度；
                        if (mHeaderView.getmState() == BaseSideView.State.HEADER_PULLING)
                            mHeaderView.setStateReady();
                    }
//                    if (mHeaderView.getPaddingTop() <= mHeaderOffset * 2) {
//                    mHeaderView.setPadding(0, (int) (mHeaderView.getPaddingTop() + delatY), 0, 0);
                    paddingView(true, (int) delatY);
//                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mHeaderView.getmState() == BaseSideView.State.HEADER_PULLING) {
                    mHeaderView.setStateRelease();
                    doHeaderCollapse(mHeaderOffset + mHeaderView.getPaddingTop(), 0);
                }
                if (mHeaderView.getmState() == BaseSideView.State.HEADER_READY) {
                    mHeaderView.setStateRefreshing();
                    doHeaderCollapse(mHeaderView.getPaddingTop(), mHeaderOffset);
                    if (this.mOnPullListener != null) {
                        this.mOnPullListener.onRefresh();
                    }
                }
                break;
        }
        return this.mHeaderView.onTouch(event);
    }

    private final int REFRESH_MSG = 1;
    private final int LOADING_MSG = 2;

    private void startRefeshTimer() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mUiHandler.sendEmptyMessage(REFRESH_MSG);
                super.run();
            }
        }.start();
    }

    Handler mUiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_MSG:

                    break;
                case LOADING_MSG:
                    break;
            }
        }
    };

    private Boolean loadingTouchHandler(MotionEvent event) {
        if (!mIsLoadingUnlock) {
            if (mFooterView.getmState() == BaseSideView.State.FOOTER_DONE)
                return false;
            mFooterView.setStateDone();
        }
        float delatY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLoadingDelatY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mFooterView.getmState() == BaseSideView.State.FOOTER_DONE) {
                    mFooterView.clearAnimation();
                    mFooterView.setStatePulling();
                }
                delatY = event.getRawY() - mLoadingDelatY;
                mLoadingDelatY = event.getRawY();
                if (mFooterView.getmState() == BaseSideView.State.FOOTER_PULLING || mFooterView.getmState() == BaseSideView.State.FOOTER_READY || mFooterView.getmState() == BaseSideView.State.FOOTER_RELEASE) {
                    if (Math.abs(mFooterView.getPaddingBottom()) > mFooterOffset) {//拉出高度；
                        mFooterView.setStateReady();
                    }
//                    if (mLoadingDelatY <= mFooterOffset * 2) {
//                    mFooterView.setPadding(0, 0, 0, (int) ( mFooterView.getPaddingBottom() - delatY));
                    paddingView(false, (int) delatY);
//                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mFooterView.getmState() == BaseSideView.State.FOOTER_PULLING) {
                    mFooterView.setStateRelease();
                    doFooterCollapse((mFooterOffset - mFooterView.getPaddingBottom()), 0);
                }
                if (mFooterView.getmState() == BaseSideView.State.FOOTER_READY) {
                    doFooterCollapse(mFooterView.getPaddingBottom(), mFooterOffset);
                    mFooterView.setStateRefreshing();
                    if (this.mOnPullListener != null) {
                        this.mOnPullListener.onLoading();
                    }
                }
                break;
        }
        return this.mFooterView.onTouch(event);

    }

    public void paddingView(boolean isHeader, int delatY) {
        if (!isHeader) {
            for (int i = 0; i < Math.abs(delatY); i++) {
                mFooterView.setPadding(0, 0, 0, mFooterView.getPaddingBottom() + (delatY > 0 ? -1 : 1));
            }
        } else {
            for (int i = 0; i < Math.abs(delatY); i++) {
                mHeaderView.setPadding(0, mHeaderView.getPaddingTop() + (delatY > 0 ? 1 : -1), 0, 0);
            }
        }


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
//            Log.e("onScroll","到顶:");
        } else {
            mIsRefreshUnlock = false;
            this.mHeaderView.setPadding(0, -1 * this.mHeaderOffset, 0, 0);
        }

        if (visibleItemCount + firstVisibleItem == totalItemCount && firstVisibleItem != 0) {
//            Log.e("onScroll","到低");
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
