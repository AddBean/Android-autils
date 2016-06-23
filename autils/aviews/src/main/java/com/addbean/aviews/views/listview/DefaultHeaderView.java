package com.addbean.aviews.views.listview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.addbean.aviews.R;


/**
 * Created by AddBean on 2016/2/16.
 */
public class DefaultHeaderView extends BaseSideView {
    public DefaultHeaderView(Context context) {
        super(context);
    }

    private TextView msg;
    private ImageView img;

    @Override
    protected void init() {
        msg = (TextView) getmView().findViewById(R.id.textView);
        img = (ImageView) getmView().findViewById(R.id.imageView);
    }

    @Override
    public Boolean getViewType() {
        return true;
    }

    @Override
    public int getViewResId() {
        return R.layout.listview_header_view;
    }

    @Override
    public void onPulling() {
        msg.setText("下拉刷新");
        img.setImageResource(R.drawable.arr_down);
    }

    @Override
    public void onRelease() {
        msg.setText("取消刷新");
    }

    @Override
    public void onReady() {
        msg.setText("松开刷新");
        img.setImageResource(R.drawable.arr_up);
    }

    @Override
    public void onRefreshing() {
        msg.setText("正在刷新……");
        img.setImageResource(R.drawable.loading);
        startAnim(img);
    }


    @Override
    public void onDone() {
        msg.setText("刷新完成");
        stopAnim(img);
    }

    @Override
    public Boolean onTouch(MotionEvent event) {
        return true;
    }

    public void startAnim(View v) {
        RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(-1);
        v.setAnimation(animation);
        animation.startNow();
    }

    public void stopAnim(View v) {
        v.clearAnimation();
    }
}
