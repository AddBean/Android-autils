package com.addbean.autils.core.utils.bitmap;

import android.graphics.BitmapFactory;
import android.view.View;

import com.addbean.autils.core.cache.BitmapCache;
import com.addbean.autils.core.http.download.AbsDownloader;
import com.addbean.autils.core.task.TaskExecutors;

/**
 * Created by AddBean on 2016/3/10.
 */
public interface IBitmapConfig {
    public TaskExecutors getBitmapThreadPool();//获取下载bitmap任务线程池；

    public BitmapFactory getBitmapFactory();

    public IBitmapCallback getCallbackListener();

    public void setCallbackListener(IBitmapCallback bitmapCallback);

    public AbsDownloader getBitmapDownLoader();//获取下载器；

    public BitmapCache getBitmapCache();//获取缓存对象；

    public String getDiskCachePath();//获取磁盘缓存路径；

    public boolean isMemEnable();//内存缓存使能；

    public boolean isDiskEnable();//硬盘缓存使能；

    public boolean isRotation();//是否自动旋转；

    public boolean isShowOriginal();

    public int getMemCacheSize();//获取最大内存缓存大小；

    public int getDiskCacheSize();//获取最大硬盘缓存大小；

    public BitmapImageSize getImageSize();//获取图片大小；

    public int getLoadingImage();

    public int getLoadingFailedImage();

    public int getLoadingEmptyImage();

    public void setLoadingImage(int resId);

    public void setLoadingFailedImage(int resId);

    public void setLoadingEmptyImage(int resId);

    public void setMinCacheDisk(int mMinCacheDisk);

    public void setMinCacheMem(int mMinCacheMem);

    public void setMemCacheEnable(boolean mMemCacheEnable);

    public void setDiskCacheEnable(boolean mDiskCacheEnable);

    public void setRotation(boolean mRotation);

    public void setShowOriginal(boolean mShowOriginal);

    public void setBitmapImageSize(BitmapImageSize mBitmapImageSize);

    public void setDiskCachePath(String mDiskCachePath);

}
