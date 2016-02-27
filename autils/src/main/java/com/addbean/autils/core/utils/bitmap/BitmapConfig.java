package com.addbean.autils.core.utils.bitmap;

import android.content.Context;
import android.text.TextUtils;

import com.addbean.autils.core.task.TaskExecutors;
import com.addbean.autils.tools.OtherUtils;

import java.util.HashMap;

/**
 * Created by AddBean on 2016/2/3.
 */
public class BitmapConfig {
    private int MIN_CACHE_DISK = 2 * 1024 * 1024;//2m;
    private int MIN_CACHE_MEM = 5 * 1024 * 1024;//5M;
    private Boolean mMemCacheEnable = true;
    private Boolean mDiskCacheEnable = true;
    private TaskExecutors BITMAP_LOAD_EXECUTOR = new TaskExecutors();
    private TaskExecutors BITMAP_CATCH_EXECUTOR = new TaskExecutors(2);
    private String DEFAULT_CACHE_PATH = "";
    private Context mContext;
    private String mDiskCachePath;
    private HashMap<String, BitmapConfig> mHashMap = new HashMap<>();//每一个磁盘缓存地址对应一个config;

    public BitmapConfig(Context context, String diskCachePath) {
        if (context == null)
            throw new IllegalArgumentException("context can not be null!");
        this.mContext = context;
        this.mDiskCachePath = diskCachePath;
        initCache();
    }

    public BitmapConfig getInstance(Context context, String diskCachePath) {
        this.mContext = context;
        if (TextUtils.isEmpty(diskCachePath))
            diskCachePath = OtherUtils.getDiskCacheDir(context, "aBitmapCache");
        if (mHashMap.containsKey(diskCachePath)) {
            return mHashMap.get(diskCachePath);
        } else {
            mHashMap.put(diskCachePath, new BitmapConfig(context, diskCachePath));
            return mHashMap.get(diskCachePath);
        }
    }

    private void initCache() {
    }

}
