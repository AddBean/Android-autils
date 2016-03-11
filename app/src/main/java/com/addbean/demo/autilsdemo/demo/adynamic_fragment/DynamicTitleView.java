package com.addbean.demo.autilsdemo.demo.adynamic_fragment;

import android.content.Context;
import android.widget.TextView;

import com.addbean.aviews.views.dynamic_fragment.ADynamicBaseSubFragment;
import com.addbean.aviews.views.dynamic_fragment.TabTitleView;
import com.addbean.demo.autilsdemo.R;

/**
 * Created by AddBean on 2016/3/2.
 */
public class DynamicTitleView extends TabTitleView {
    private TextView mTitle;
    private Context mContext;

    public DynamicTitleView(Context context, Object tag) {
        super(context, tag);
        this.mContext=context;
    }


    @Override
    protected void initView() {
        mTitle = (TextView) getView().findViewById(R.id.text);
        mTitle.setText((String) getmTag());
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dynamic_title_view;
    }

    @Override
    protected void onPageSelected(Boolean isSelected, TabTitleView tag, ADynamicBaseSubFragment fragment) {
        if (isSelected) {
            mTitle.setTextColor(getResources().getColor(R.color.color_gran_dark));
        } else {
            mTitle.setTextColor(getResources().getColor(R.color.color_gran));
        }
    }
}
