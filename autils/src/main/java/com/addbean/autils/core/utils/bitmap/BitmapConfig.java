package com.addbean.autils.core.utils.bitmap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;

import com.addbean.autils.core.cache.BitmapCache;
import com.addbean.autils.core.http.download.AbsDownloader;
import com.addbean.autils.core.http.download.DefaultDownloader;
import com.addbean.autils.core.task.TaskExecutors;
import com.addbean.autils.tools.OtherUtils;

import java.util.HashMap;

/**
 * Created by AddBean on 2016/2/3.
 */
public class BitmapConfig implements IBitmapConfig {
    private int mMinCacheDisk = 2 * 1024 * 1024;//2m;
    private int mMinCacheMem = 5 * 1024 * 1024;//5M;
    private boolean mMemCacheEnable = true;
    private boolean mDiskCacheEnable = true;
    private boolean mRotation = true;
    private boolean mShowOriginal = false;
    private BitmapImageSize mBitmapImageSize;
    private Context mContext;
    private String mDiskCachePath;
    public static HashMap<String, BitmapConfig> mHashMap = new HashMap<String, BitmapConfig>();//每一个磁盘缓存地址对应一个config;
    private static BitmapCache mBitmapCache;
    private IBitmapCallback mOnCallbackListener;
    private int mLoadingImage = -1;
    private int mLoadingFailedImage = -1;
    private int mLoadingEmptyImage = -1;

    public BitmapConfig(Context context, String diskCachePath) {
        if (context == null)
            throw new IllegalArgumentException("context can not be null!");
        this.mContext = context;
        this.mDiskCachePath = diskCachePath;
    }

    public static BitmapConfig getInstance(Context context, String diskCachePath) {
        if (TextUtils.isEmpty(diskCachePath))
            diskCachePath = OtherUtils.getDiskCacheDir(context, "aBitmapCache");
        if (mHashMap.containsKey(diskCachePath)) {
            return mHashMap.get(diskCachePath);
        } else {
            mHashMap.put(diskCachePath, new BitmapConfig(context, diskCachePath));
            return mHashMap.get(diskCachePath);
        }
    }

    @Override
    public TaskExecutors getBitmapThreadPool() {
        return null;
    }

    @Override
    public BitmapFactory getBitmapFactory() {
        return null;
    }

    @Override
    public IBitmapCallback getCallbackListener() {
        return this.mOnCallbackListener;
    }

    @Override
    public void setCallbackListener(IBitmapCallback bitmapCallback) {
        this.mOnCallbackListener = bitmapCallback;
    }

    @Override
    public AbsDownloader getBitmapDownLoader() {
        return new DefaultDownloader(mContext);
    }

    @Override
    public BitmapCache getBitmapCache() {
        if (mBitmapCache == null)
            mBitmapCache = new BitmapCache(mContext);
        return mBitmapCache;
    }

    @Override
    public String getDiskCachePath() {
        return this.mDiskCachePath;
    }

    @Override
    public boolean isMemEnable() {
        return this.mMemCacheEnable;
    }

    @Override
    public boolean isDiskEnable() {
        return this.mDiskCacheEnable;
    }

    @Override
    public boolean isRotation() {
        return this.mRotation;
    }

    @Override
    public boolean isShowOriginal() {
        return this.mShowOriginal;
    }

    @Override
    public int getMemCacheSize() {
        return this.mMinCacheMem;
    }

    @Override
    public int getDiskCacheSize() {
        return this.mMinCacheDisk;
    }

    @Override
    public BitmapImageSize getImageSize() {
        return mBitmapImageSize;
    }

    @Override
    public int getLoadingImage() {
        return mLoadingImage;
    }

    @Override
    public int getLoadingFailedImage() {
        return mLoadingFailedImage;
    }

    @Override
    public int getLoadingEmptyImage() {
        return mLoadingEmptyImage;
    }

    @Override
    public void setLoadingImage(int resId) {
        this.mLoadingImage = resId;
    }

    @Override
    public void setLoadingFailedImage(int resId) {
        this.mLoadingFailedImage = resId;
    }

    @Override
    public void setLoadingEmptyImage(int resId) {
        this.mLoadingEmptyImage = resId;
    }

    @Override
    public void setMinCacheDisk(int mMinCacheDisk) {
        this.mMinCacheDisk = mMinCacheDisk;
    }

    @Override
    public void setMinCacheMem(int mMinCacheMem) {
        this.mMinCacheMem = mMinCacheMem;
    }

    @Override
    public void setMemCacheEnable(boolean mMemCacheEnable) {
        this.mMemCacheEnable = mMemCacheEnable;
    }

    @Override
    public void setDiskCacheEnable(boolean mDiskCacheEnable) {
        this.mDiskCacheEnable = mDiskCacheEnable;
    }

    @Override
    public void setRotation(boolean mRotation) {
        this.mRotation = mRotation;
    }

    @Override
    public void setShowOriginal(boolean mShowOriginal) {
        this.mShowOriginal = mShowOriginal;
    }

    @Override
    public void setBitmapImageSize(BitmapImageSize mBitmapImageSize) {
        this.mBitmapImageSize = mBitmapImageSize;
    }

    @Override
    public void setDiskCachePath(String mDiskCachePath) {
        this.mDiskCachePath = mDiskCachePath;
    }


}
