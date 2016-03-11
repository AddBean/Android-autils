package com.addbean.autils.core.utils.bitmap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

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
    private int MIN_CACHE_DISK = 2 * 1024 * 1024;//2m;
    private int MIN_CACHE_MEM = 5 * 1024 * 1024;//5M;
    private boolean mMemCacheEnable = true;
    private boolean mDiskCacheEnable = true;
    private boolean mRotation = true;
    private TaskExecutors BITMAP_LOAD_EXECUTOR = new TaskExecutors();
    private TaskExecutors BITMAP_CATCH_EXECUTOR = new TaskExecutors(2);
    private String DEFAULT_CACHE_PATH = "";
    private Context mContext;
    private String mDiskCachePath;
    public static HashMap<String, BitmapConfig> mHashMap = new HashMap<String, BitmapConfig>();//每一个磁盘缓存地址对应一个config;
    private static BitmapCache mBitmapCache;

    public BitmapConfig(Context context, String diskCachePath) {
        if (context == null)
            throw new IllegalArgumentException("context can not be null!");
        this.mContext = context;
        this.mDiskCachePath = diskCachePath;
        initConfig();
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

    private void initConfig() {
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
        return false;
    }

    @Override
    public int getMemCacheSize() {
        return MIN_CACHE_MEM;
    }

    @Override
    public int getDiskCacheSize() {
        return MIN_CACHE_DISK;
    }

    @Override
    public BitmapImageSize getImageSize() {
        return new BitmapImageSize(100,100);
    }
}
