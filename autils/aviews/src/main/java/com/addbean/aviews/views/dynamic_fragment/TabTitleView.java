package com.addbean.aviews.views.dynamic_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by AddBean on 2016/3/1.
 */
public abstract class TabTitleView extends LinearLayout {
    private Object mTag;
    private boolean mSelected = false;
    private LayoutInflater mInflater;
    private View mView;
    private Context mContext;

    public TabTitleView(Context context, Object tag) {
        super(context);
        this.mContext = context;
        this.mTag = tag;
        init();
        initView();
    }

    private void init() {
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(getContentView(), null);
        this.addView(mView);
    }

    public void setSelected(boolean isSelected) {
        this.mSelected = isSelected;
    }

    public boolean isSelected() {
        return this.mSelected;
    }

    public Object getmTag() {
        return mTag;
    }

    public void setmTag(Object mTag) {
        this.mTag = mTag;
    }

    public View getView() {
        return mView;
    }

    protected abstract void initView();

    protected abstract int getContentView();

    protected abstract void onPageSelected(Boolean isSelected, TabTitleView tag,ADynamicBaseSubFragment fragment);
}
