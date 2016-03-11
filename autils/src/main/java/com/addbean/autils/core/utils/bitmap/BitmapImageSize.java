package com.addbean.autils.core.utils.bitmap;

/**
 * Created by AddBean on 2016/3/11.
 */
public class BitmapImageSize {
    private int mMaxWidth;
    private int mMaxHeigh;

    public BitmapImageSize(int mMaxWidth, int mMaxHeigh) {
        this.mMaxWidth = mMaxWidth;
        this.mMaxHeigh = mMaxHeigh;
    }

    public int getmMaxWidth() {
        return mMaxWidth;
    }

    public void setmMaxWidth(int mMaxWidth) {
        this.mMaxWidth = mMaxWidth;
    }

    public int getmMaxHeigh() {
        return mMaxHeigh;
    }

    public void setmMaxHeigh(int mMaxHeigh) {
        this.mMaxHeigh = mMaxHeigh;
    }
}
