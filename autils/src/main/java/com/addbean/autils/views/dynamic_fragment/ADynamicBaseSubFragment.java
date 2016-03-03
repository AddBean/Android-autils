package com.addbean.autils.views.dynamic_fragment;

import com.addbean.autils.views.BaseFragment;

/**
 * Created by AddBean on 2016/3/1.
 */
public abstract class ADynamicBaseSubFragment extends BaseFragment implements IDynamicFragmentLifeCycle {
    private Object mTag;

    public ADynamicBaseSubFragment(Object mTag) {
        this.mTag = mTag;
    }

    public Object getmTag() {
        return mTag;
    }

    public void setmTag(Object mTag) {
        this.mTag = mTag;
    }
}
