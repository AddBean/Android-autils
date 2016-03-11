package com.addbean.aviews.views.hoverlist;

import android.widget.LinearLayout;


import com.addbean.aviews.utils.multiadapter.MultiAdapter;

import java.util.List;

/**
 * Created by AddBean on 2016/2/17.
 */
public interface IHoverAdpterListener extends MultiAdapter.IAdpterListener{
    public List<Integer> onHoverChanage(LinearLayout view, Object data, int position, int itemResId);
}
