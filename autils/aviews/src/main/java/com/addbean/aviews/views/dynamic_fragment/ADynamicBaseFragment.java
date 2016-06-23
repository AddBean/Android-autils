package com.addbean.aviews.views.dynamic_fragment;

import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.HorizontalScrollView;


import com.addbean.autils.tools.ToolsUtils;
import com.addbean.aviews.views.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AddBean on 2016/3/1.
 */
public abstract class ADynamicBaseFragment extends BaseFragment implements TabTitleScrollLayout.OnTabClickListener, ViewPager.OnPageChangeListener, TabTitleScrollLayout.OnIndexDrawListener {

    private HorizontalScrollView mTitleLayout;
    private ViewPager mViewPager;
    private ADynameicAdpter mAdpter;
    private List<ADynamicBaseSubFragment> mBaseFragments;
    private List<TabTitleView> mTabTitleViews = new ArrayList<>();
    private TabTitleScrollLayout mTabTitles;
    private int mPosition;

    @Override
    public void initView() {
        mBaseFragments = getFragmentList();
        mTabTitles = new TabTitleScrollLayout(getActivity());
        mAdpter = new ADynameicAdpter(getActivity().getSupportFragmentManager(), mBaseFragments);
        mTitleLayout = getTabView();

        mViewPager = getViewPageView();
        mViewPager.setAdapter(mAdpter);
        mViewPager.setOnPageChangeListener(this);
        mTabTitles.setOnTabClickListener(this);
        mTabTitles.setOnIndexDrawListener(this);
        mTitleLayout.addView(mTabTitles);

        for (ADynamicBaseSubFragment fragment : mBaseFragments) {
            TabTitleView tabTitleView = getTabTitleView(fragment.getmTag());
            mTabTitleViews.add(tabTitleView);
            mTabTitles.addTabTitleView(tabTitleView);
        }
        onPageSelected(0);
    }


    @Override
    public void onResumeView() {
    }

    public abstract List<ADynamicBaseSubFragment> getFragmentList();

    public abstract TabTitleView getTabTitleView(Object tag);

    @Override
    public void onClick(TabTitleView tab) {
        Log.e("ADynamicBaseFragment", (String) tab.getmTag());
        for (int i = 0; i < mBaseFragments.size(); i++) {
            if (tab.getmTag().equals(mBaseFragments.get(i).getmTag())) {
                mViewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTabTitles.setIndexPosition(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        TabTitleView tab = mTabTitleViews.get(position);
        for (int i = 0; i < mBaseFragments.size(); i++) {
            mTabTitleViews.get(i).setSelected(false);
            mTabTitleViews.get(i).onPageSelected(false, tab, mBaseFragments.get(position));
        }
        tab.setSelected(true);
        tab.onPageSelected(true, tab, mBaseFragments.get(position));
        for (int i = 0; i < mBaseFragments.size(); i++) {
            if (tab.getmTag().equals(mBaseFragments.get(i).getmTag())) {
                mViewPager.setCurrentItem(i);
                mTitleLayout.smoothScrollTo((int) tab.getX() - ToolsUtils.dpConvertToPx(getActivity(), 50), 0);
            }
        }
    }

    public ADynamicBaseSubFragment getCurrentFragment() {
        return mBaseFragments.get(mPosition);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onIndexDraw(Canvas canvas, int position, float positionOffset, int positionOffsetPixels) {
        return false;
    }

    @Override
    public boolean onDefaultIndexDraw(Canvas canvas, int x1, int y1, int x2, int y2) {
        return false;
    }

    protected abstract HorizontalScrollView getTabView();

    protected abstract ViewPager getViewPageView();
}
