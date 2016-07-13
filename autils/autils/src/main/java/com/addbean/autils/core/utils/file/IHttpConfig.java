package com.addbean.autils.core.utils.file;

import com.addbean.autils.core.cache.HttpCache;

/**
 * Created by AddBean on 2016/7/7.
 */
public interface IHttpConfig {

    public HttpCache getHttpCache();//获取缓存对象；

    public String getDiskCachePath();//获取磁盘缓存路径；

    public int getDiskCacheSize();//获取最大硬盘缓存大小；

    public void setMinCacheDisk(int mMinCacheDisk);

    public void setDiskCachePath(String mDiskCachePath);

    public void setDiskEnable(boolean enable);

    public boolean isDiskEnable();
}
