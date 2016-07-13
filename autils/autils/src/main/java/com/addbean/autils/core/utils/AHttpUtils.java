package com.addbean.autils.core.utils;

import android.content.Context;

import com.addbean.autils.core.cache.HttpCache;
import com.addbean.autils.core.utils.file.HttpConfig;
import com.addbean.autils.core.utils.file.IHttpConfig;

/**
 * Created by AddBean on 2016/2/3.
 */
public class AHttpUtils {
    private Context mContext;
    private IHttpConfig mHttpConfig;
    private HttpCache mHttpCache;

    public AHttpUtils(Context context) {
        this.mContext = context;

        this.mHttpCache = new HttpCache(mContext);
        this.mHttpConfig = new HttpConfig(mContext, mHttpCache);
        this.mHttpCache.initDiskCache(mHttpConfig);
    }

    public String get(String url) {
        return this.mHttpCache.getStringFromDisk(url, mHttpConfig);
    }

    /**
     * 保存string文件到本地；
     *
     * @param content
     * @param key
     */
    public void save(String content, String key) {
        this.mHttpCache.addStringToDisk(key, mHttpConfig, content);
    }

    public void clearCache() {
        if (mHttpConfig == null) return;
        mHttpConfig.getHttpCache().clearDiskCache();
    }


    public IHttpConfig getmHttpConfig() {
        return mHttpConfig;
    }

    public void setmHttpConfig(IHttpConfig mHttpConfig) {
        this.mHttpConfig = mHttpConfig;
    }

    public HttpCache getmHttpCache() {
        return mHttpCache;
    }

    public void setmHttpCache(HttpCache mHttpCache) {
        this.mHttpCache = mHttpCache;
    }
}
