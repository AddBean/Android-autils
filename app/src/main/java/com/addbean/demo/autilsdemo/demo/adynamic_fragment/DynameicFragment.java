package com.addbean.demo.autilsdemo.demo.adynamic_fragment;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.addbean.autils.tools.ToolsUtils;
import com.addbean.autils.views.dynamic_fragment.ADynamicBaseFragment;
import com.addbean.autils.views.dynamic_fragment.ADynamicBaseSubFragment;
import com.addbean.autils.views.dynamic_fragment.TabTitleView;
import com.addbean.demo.autilsdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AddBean on 2016/3/1.
 */
public class DynameicFragment extends ADynamicBaseFragment {
    @Override
    public List<ADynamicBaseSubFragment> getFragmentList() {
        List<ADynamicBaseSubFragment> list = new ArrayList<>();
        list.add(new DynameicSubFragment("刑法"));
        list.add(new DynameicSubFragment("商业法"));
        list.add(new DynameicSubFragment("宪法"));
        list.add(new DynameicSubFragment("危害公共安全罪"));
        list.add(new DynameicSubFragment("强奸罪"));
        list.add(new DynameicSubFragment("侵害公共财产罪"));
        list.add(new DynameicSubFragment("受贿"));
        list.add(new DynameicSubFragment("偷盗罪"));
        list.add(new DynameicSubFragment("非法占用罪"));
        list.add(new DynameicSubFragment("遗产法"));
        return list;
    }

    @Override
    public TabTitleView getTabTitleView(Object tag) {
        DynamicTitleView dynamicTitleView = new DynamicTitleView(getActivity(), tag);
        return dynamicTitleView;
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
}
