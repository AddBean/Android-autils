package com.addbean.demo.autilsdemo.demo.adynamic_fragment;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.addbean.autils.views.dynamic_fragment.ADynamicBaseSubFragment;
import com.addbean.demo.autilsdemo.R;

/**
 * Created by AddBean on 2016/3/1.
 */
@SuppressLint("ValidFragment")
public class DynameicSubFragment extends ADynamicBaseSubFragment {


    public DynameicSubFragment(Object mTag) {
        super(mTag);
    }

    @Override
    public int getFragmentView() {
        return R.layout.fragment_content;
    }

    @Override
    public void initView() {
        TextView text = (TextView) getCurrentView().findViewById(R.id.text);
        text.setText((String) getmTag());
    }

    @Override
    public void onResumeView() {

    }
}
