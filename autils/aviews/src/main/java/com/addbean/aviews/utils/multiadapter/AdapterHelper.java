package com.addbean.aviews.utils.multiadapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class AdapterHelper {
    private Context context;
    private ViewGroup _view;


    public AdapterHelper(Context context, ViewGroup view) {
        this.context = context;
        this._view = view;
    }

    public void setText(int viewId, String text) {
        ((TextView) _view.findViewById(viewId)).setText(text);
    }
    public void setOnViewClick(int viewId, OnClickListener onClickListener) {
        _view.findViewById(viewId).setOnClickListener(onClickListener);
    }



    public void setImageSrc(int viewId, int resId) {
        ImageView view = (ImageView) _view.findViewById(viewId);
        view.setImageResource(resId);
    }

    public void setViewVisiable(int viewId, int visibility) {
        View view = (View) _view.findViewById(viewId);
        view.setVisibility(visibility);
    }

    public void setRateingBar(int viewId, float star) {
        RatingBar view = (RatingBar) _view.findViewById(viewId);
        view.setRating(star);
    }


}
