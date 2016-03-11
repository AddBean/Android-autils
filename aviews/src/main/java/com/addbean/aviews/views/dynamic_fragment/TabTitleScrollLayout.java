package com.addbean.aviews.views.dynamic_fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.addbean.autils.tools.ToolsUtils;

/**
 * Created by AddBean on 2016/3/1.
 */
public class TabTitleScrollLayout extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private OnTabClickListener mOnTabClickListener;
    private int mPosition;
    private float mPositionOffset;
    private int mPositionOffsetPixels;
    private int mIndexHeigh;
    private int mPadding = 0;

    public TabTitleScrollLayout(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mIndexHeigh = ToolsUtils.dpConvertToPx(mContext, 3);
        ViewGroup.LayoutParams fl = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(fl);
        this.setOrientation(HORIZONTAL);
    }

    public void addTabTitleView(TabTitleView tabView) {
        this.addView(tabView);
        tabView.setOnClickListener(this);
        this.requestLayout();
    }

    @Override
    public void onClick(View v) {
        TabTitleView tabView = (TabTitleView) v;
        if (mOnTabClickListener != null)
            mOnTabClickListener.onClick(tabView);

    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.mOnTabClickListener = onTabClickListener;
    }

    public interface OnTabClickListener {
        public void onClick(TabTitleView tab);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!mOnIndexDrawListener.onIndexDraw(canvas, mPosition, mPositionOffset, mPositionOffsetPixels)) {
            drawDefaultIndex(canvas);
        }
    }

    private void drawDefaultIndex(Canvas canvas) {
        int centerPrevX = 0;
        int centerNextX = 0;
        int centerX = 0;
        int tempX1 = 0;
        int tempX2 = 0;
        int tempW = 0;
        if (mPosition < getChildCount() - 1) {
            View view0 = this.getChildAt(mPosition);
            View view1 = this.getChildAt(mPosition + 1);
            centerPrevX = (int) (view0.getX() + view0.getMeasuredWidth() / 2);
            centerNextX = (int) (view1.getX() + view1.getMeasuredWidth() / 2);
            centerX = (int) (centerPrevX + (centerNextX - centerPrevX) * mPositionOffset);
            tempW = (int) (view0.getMeasuredWidth() + (view1.getMeasuredWidth() - view0.getMeasuredWidth()) * mPositionOffset);
            tempX1 = (int) (centerX - tempW / 2);
            tempX2 = (int) (centerX + tempW / 2);
            drawIndex(canvas, tempX1, getMeasuredHeight() - mIndexHeigh, tempX2, getMeasuredHeight() - mIndexHeigh);
        } else if (mPosition == getChildCount() - 1) {
            tempX1 = (int) this.getChildAt(mPosition).getX();
            tempX2 = (int) (this.getChildAt(mPosition).getX() + this.getChildAt(mPosition).getMeasuredWidth());
            drawIndex(canvas, tempX1, getMeasuredHeight() - mIndexHeigh, tempX2, getMeasuredHeight() - mIndexHeigh);
        }
    }

    private void drawIndex(Canvas canvas, int x1, int y1, int x2, int y2) {
        if (!mOnIndexDrawListener.onDefaultIndexDraw(canvas, x1, y1, x2, y2)) {
            Paint paint = new Paint();
            paint.setStrokeWidth(mIndexHeigh);
            paint.setAntiAlias(true);
            canvas.drawLine(x1, y1, x2, y2, paint);
        }
    }

    public void setIndexPosition(int position, float positionOffset, int positionOffsetPixels) {
//        Log.e("TabTitleScrollLayout", "position : " + position + " positionOffset : " + positionOffset + " positionOffsetPixels : " + positionOffsetPixels);
        this.mPosition = position;
        this.mPositionOffset = positionOffset;
        this.mPositionOffsetPixels = positionOffsetPixels;
        invalidate();
    }

    private OnIndexDrawListener mOnIndexDrawListener;

    public void setOnIndexDrawListener(OnIndexDrawListener onIndexDrawListener) {
        this.mOnIndexDrawListener = onIndexDrawListener;
    }

    public interface OnIndexDrawListener {
        public boolean onIndexDraw(Canvas canvas, int position, float positionOffset, int positionOffsetPixels);

        public boolean onDefaultIndexDraw(Canvas canvas, int x1, int y1, int x2, int y2);
    }
}
