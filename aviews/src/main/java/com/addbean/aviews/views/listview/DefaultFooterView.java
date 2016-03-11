package com.addbean.aviews.views.listview;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.TextView;

import com.addbean.aviews.R;


/**
 * Created by AddBean on 2016/2/16.
 */
public class DefaultFooterView extends BaseSideView {
    public DefaultFooterView(Context context) {
        super(context);
    }

    private TextView msg;

    @Override
    protected void init() {
        msg = (TextView) getmView().findViewById(R.id.textView);
    }

    @Override
    public Boolean getViewType() {
        return false;
    }

    @Override
    public int getViewResId() {
        return R.layout.listview_footer_view;
    }

    @Override
    public void onPulling() {
        msg.setText("上拉加载");
    }

    @Override
    public void onRelease() {
        msg.setText("取消加载");
    }

    @Override
    public void onReady() {
        msg.setText("松开加载");
    }

    @Override
    public void onRefreshing() {
        msg.setText("正在加载……请稍后");
    }

    @Override
    public void onDone() {
        msg.setText("加载完成");
    }

    @Override
    public Boolean onTouch(MotionEvent event) {
        return null;
    }

}
