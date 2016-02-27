package com.addbean.autils.core.utils.bitmap.task;

import android.graphics.Bitmap;
import android.view.View;

import com.addbean.autils.core.task.BaseAsyncTask;

/**
 * Created by AddBean on 2016/2/14.
 */
public class BitmapLoadTask<T extends View> extends BaseAsyncTask<Object,Object, Bitmap> {
    public BitmapLoadTask(T container,String url) {
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }
}
