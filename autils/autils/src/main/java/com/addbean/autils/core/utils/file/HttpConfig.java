package com.addbean.autils.core.utils.file;

import android.content.Context;

import com.addbean.autils.core.cache.HttpCache;

/**
 * Created by AddBean on 2016/7/7.
 */
public class HttpConfig implements IHttpConfig {
    private int mMinCacheDisk = 5 * 1024 * 1024;//5m;
    private Context mContext;
    private boolean mDiskCacheEnable = true;
    private String mDiskCachePath;
    private HttpCache mHttpCache;

    public HttpConfig(Context context, HttpCache mHttpCache) {
        this.mContext = context;
        this.mHttpCache = mHttpCache;
        setDiskCachePath(getDefaultDiskPath());
    }

    @Override
    public HttpCache getHttpCache() {
        return mHttpCache;
    }

    @Override
    public String getDiskCachePath() {
        return mDiskCachePath;
    }

    @Override
    public int getDiskCacheSize() {
        return mMinCacheDisk;
    }

    @Override
    public void setMinCacheDisk(int mMinCacheDisk) {
        this.mMinCacheDisk = mMinCacheDisk;
    }

    @Override
    public void setDiskCachePath(String mDiskCachePath) {
        this.mDiskCachePath = mDiskCachePath;
    }

    @Override
    public void setDiskEnable(boolean enable) {
        this.mDiskCacheEnable = enable;
    }

    @Override
    public boolean isDiskEnable() {
        return mDiskCacheEnable;
    }

    private String getDefaultDiskPath() {
        return mContext.getCacheDir().getPath();
    }
}
