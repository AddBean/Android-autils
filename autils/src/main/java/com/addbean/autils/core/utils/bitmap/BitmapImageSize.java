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

    public int getMaxWidth() {
        return mMaxWidth;
    }

    public void setMaxWidth(int mMaxWidth) {
        this.mMaxWidth = mMaxWidth;
    }

    public int getMaxHeigh() {
        return mMaxHeigh;
    }

    public void setMaxHeigh(int mMaxHeigh) {
        this.mMaxHeigh = mMaxHeigh;
    }
}
