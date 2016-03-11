package com.addbean.autils.core.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by AddBean on 2016/3/10.
 */
public interface IBitmapCallback<T extends View> {

    public void onPreLoad(T container, String uri, IBitmapConfig config);

    public void onLoadStarted(T container, String uri, IBitmapConfig config);

    public void onLoading(T container, String uri, IBitmapConfig config, long total, long current);

    public void onLoadCompleted(T container, String uri, Bitmap bitmap, IBitmapConfig config);

    public void onLoadFailed(T container, String uri);
}
