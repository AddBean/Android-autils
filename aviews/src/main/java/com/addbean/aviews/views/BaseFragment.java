package com.addbean.aviews.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by AddBean on 2016/3/1.
 */
public abstract class BaseFragment extends Fragment {
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(getFragmentView(), container, false);
        initView();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumeView();
    }

    public View getCurrentView() {
        return this.mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public Context getContext() {
        return getActivity();
    }

    public abstract int getFragmentView();

    public abstract void initView();

    public abstract void onResumeView();

}
