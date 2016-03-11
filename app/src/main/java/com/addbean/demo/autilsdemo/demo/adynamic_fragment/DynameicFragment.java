package com.addbean.demo.autilsdemo.demo.adynamic_fragment;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.addbean.autils.tools.ToolsUtils;
import com.addbean.aviews.views.dynamic_fragment.ADynamicBaseFragment;
import com.addbean.aviews.views.dynamic_fragment.ADynamicBaseSubFragment;
import com.addbean.aviews.views.dynamic_fragment.TabTitleView;
import com.addbean.demo.autilsdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AddBean on 2016/3/1.
 */
public class DynameicFragment extends ADynamicBaseFragment implements CustomScrollView.onTouchEventListener {
    private HorizontalScrollView mTitleLayout;
    private DynameicFragment mContentFragment;
    private TouchHandlerLayout mContentFragmentLayout;
    private RelativeLayout mTitle;
    private LinearLayout mTitleContent;
    private ViewPager mViewPageLayout;
    private float mCurrentTransY = 0;

    @Override
    public List<ADynamicBaseSubFragment> getFragmentList() {
        List<ADynamicBaseSubFragment> list = new ArrayList<>();
        list.add(new DynameicSubFragment("刑法", this));
        list.add(new DynameicSubFragment("商业法", this));
        list.add(new DynameicSubFragment("宪法", this));
        list.add(new DynameicSubFragment("危害公共安全罪", this));
        list.add(new DynameicSubFragment("强奸罪", this));
        list.add(new DynameicSubFragment("侵害公共财产罪", this));
        list.add(new DynameicSubFragment("受贿", this));
        list.add(new DynameicSubFragment("偷盗罪", this));
        list.add(new DynameicSubFragment("非法占用罪", this));
        list.add(new DynameicSubFragment("遗产法", this));
        return list;
    }


    @Override
    public boolean onDefaultIndexDraw(Canvas canvas, int x1, int y1, int x2, int y2) {
        Paint paint = new Paint();
        paint.setStrokeWidth(ToolsUtils.dpConvertToPx(getActivity(), 4));
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.color_gran));
        int padding = ToolsUtils.dpConvertToPx(getActivity(), 10);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(x1 + padding, y1, x2 - padding, y2, paint);
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        mContentFragmentLayout = (TouchHandlerLayout) getCurrentView().findViewById(R.id.content_fragment);
        mTitle = (RelativeLayout) getCurrentView().findViewById(R.id.title);
        mTitleContent = (LinearLayout) getCurrentView().findViewById(R.id.title_content);
        mViewPageLayout = (ViewPager) getCurrentView().findViewById(R.id.content_viewpager);
        getCurrentView().findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }



    @Override
    public TabTitleView getTabTitleView(Object tag) {
        DynamicTitleView dynamicTitleView = new DynamicTitleView(getActivity(), tag);
        return dynamicTitleView;
    }

    @Override
    protected HorizontalScrollView getTabView() {
        mTitleLayout = (HorizontalScrollView) getCurrentView().findViewById(R.id.title_layout);
        return mTitleLayout;
    }

    @Override
    protected ViewPager getViewPageView() {
        return (ViewPager) getCurrentView().findViewById(R.id.content_viewpager);
    }

    @Override
    public int getFragmentView() {
        return R.layout.fragment_dynamic_layout;
    }

    private boolean changTitle(float delat) {
        if (delat < 0) {
            for (int i = 0; i < -delat; i++)
                if (Math.abs(mContentFragmentLayout.getTranslationY()) < (mTitleContent.getMeasuredHeight() - mTitleLayout.getMeasuredHeight())) {
                    mTitle.setTranslationY((int) mTitle.getTranslationY() - 1f);
//                    mTitleLayout.setTranslationY((int) mTitleLayout.getTranslationY() - 1f);
                    mContentFragmentLayout.setTranslationY((int) mContentFragmentLayout.getTranslationY() - 1f);
                } else {
                    return false;
                }
        } else {
            for (int i = 0; i < delat; i++) {
                if (mContentFragmentLayout.getTranslationY() < 0) {
                    mTitle.setTranslationY((int) mTitle.getTranslationY() + 1f);
//                    mTitleLayout.setTranslationY((int) mTitleLayout.getTranslationY() + 1f);
                    mContentFragmentLayout.setTranslationY((int) mContentFragmentLayout.getTranslationY() + 1f);
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    private float mMoveY = 0;

    @Override
    public boolean onMTouchEvent(MotionEvent ev) {
        float delatY;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMoveY = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                delatY = ev.getRawY() - mMoveY;
                if (mMoveY != 0) {
                    changTitle(delatY);
                }
                mMoveY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (mContentFragmentLayout.getTranslationY() < 0 && mContentFragmentLayout.getTranslationY() > -mTitleContent.getMeasuredHeight() + mTitleLayout.getMeasuredHeight()) {
                    doAnim();
                }

                break;
        }
        return false;
    }

    private void doAnim() {
        TransAnim anim;
        mContentFragmentLayout.clearAnimation();
        mCurrentTransY = mContentFragmentLayout.getTranslationY();
        if (Math.abs(mCurrentTransY) < mTitleContent.getMeasuredHeight() / 2) {
            anim = new TransAnim(getActivity(), 0, 0, mContentFragmentLayout,mTitle);

        } else {
            anim = new TransAnim(getActivity(), 0, -mTitleContent.getMeasuredHeight() + mTitleLayout.getMeasuredHeight(), mContentFragmentLayout,mTitle);
        }
        anim.setDuration(300);
        anim.setFillAfter(true);
        mContentFragmentLayout.startAnimation(anim);
    }
}
