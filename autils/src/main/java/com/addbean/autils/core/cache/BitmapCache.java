package com.addbean.autils.core.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.addbean.autils.core.cache.base.DiskLruCache;
import com.addbean.autils.core.cache.base.MemLruCache;
import com.addbean.autils.core.utils.bitmap.BitmapLoadTask;
import com.addbean.autils.core.utils.bitmap.IBitmapConfig;
import com.addbean.autils.tools.BitmapToolUtils;
import com.addbean.autils.tools.MD5Utils;
import com.addbean.autils.tools.OtherUtils;
import com.addbean.autils.tools.ToolsUtils;
import com.addbean.autils.utils.JLog;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by AddBean on 2016/3/10.
 */
public class BitmapCache {
    private Context mContext;
    private Object mCacheLock = new Object();
    private DiskLruCache mDiskLruCache;
    private MemLruCache<MemCacheKey, Bitmap> mMemoryCache;
    private String TAG = "BitmapCache";
    private final Object mDiskLock = new Object();
    private final Object mMemLock = new Object();

    public BitmapCache(Context context) {
        this.mContext = context;
    }

    public void initMemCache(IBitmapConfig bitmapConfig) {
        synchronized (mCacheLock) {
            if (!bitmapConfig.isMemEnable()) return;
            if (mMemoryCache != null) {
                try {
                    clearMemCache();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            mMemoryCache = new MemLruCache<MemCacheKey, Bitmap>(bitmapConfig.getMemCacheSize()) {
                @Override
                protected int sizeOf(MemCacheKey key, Bitmap bitmap) {
                    if (bitmap == null) return 0;
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }
    }


    public void initDiskCache(IBitmapConfig bitmapConfig) {
        synchronized (mCacheLock) {
            if (!bitmapConfig.isDiskEnable() || mDiskLruCache != null)
                return;
            File diskCacheDir = new File(bitmapConfig.getDiskCachePath());
            if (diskCacheDir.exists() || diskCacheDir.mkdirs()) {
                long availableSpace = OtherUtils.getAvailableSpace(diskCacheDir);
                long diskCacheSize = bitmapConfig.getDiskCacheSize();
                diskCacheSize = availableSpace > diskCacheSize ? diskCacheSize : availableSpace;
                try {
                    mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, diskCacheSize);
                    Log.e(TAG, "create disk cache success");
                } catch (Throwable e) {
                    mDiskLruCache = null;
                    Log.e(TAG, "create disk cache error");
                }
            }
        }
    }

    public void getBitampFromWeb(String uri, IBitmapConfig bitmapConfig, BitmapLoadTask task) {
        task.execute(bitmapConfig.getBitmapThreadPool());//执行获取图片任务并缓存；
    }

    public Bitmap getBitmapFromDisk(String uri, IBitmapConfig bitmapConfig) {
        if (!bitmapConfig.isDiskEnable())
            return null;
        File diskCacheDir = new File(bitmapConfig.getDiskCachePath());
        if (diskCacheDir.exists() || diskCacheDir.mkdirs()) {
            DiskLruCache.Snapshot snapshot = null;
            Bitmap bitmap = null;
            try {
                snapshot = mDiskLruCache.get(MD5Utils.String2md5(uri));
                if (snapshot == null) return null;
                if (bitmapConfig.isShowOriginal()) {//是否显示原图；
                    bitmap = BitmapToolUtils.decodeInputStream(snapshot.getInputStream(DISK_CACHE_INDEX));
                } else {
                    bitmap = BitmapToolUtils.decodeInputStreamWithSize(
                            snapshot.getInputStream(DISK_CACHE_INDEX),
                            bitmapConfig.getImageSize().getmMaxWidth(),
                            bitmapConfig.getImageSize().getmMaxHeigh());
                }
                if (bitmap != null) JLog.e(Thread.currentThread().getName() + "--从硬盘加载");
//                if (bitmapConfig.isRotation())
//                    bitmap = BitmapToolUtils.rotateBitmap(bitmap, );
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        return null;
    }

    public Bitmap getBitmapFromMem(String uri, IBitmapConfig bitmapConfig) {
        if (mMemoryCache != null && bitmapConfig.isMemEnable()) {
            MemCacheKey key = new MemCacheKey(MD5Utils.String2md5(uri), bitmapConfig);
            Bitmap bitmap = mMemoryCache.get(key);
            if (bitmap != null) JLog.e(Thread.currentThread().getName() + "--从内存加载");
            return bitmap;
        }
        return null;
    }

    private final int DISK_CACHE_INDEX = 0;

    public void addBitmapToDisk(String uri, IBitmapConfig bitmapConfig, Bitmap bmp) {
        synchronized (mCacheLock) {
            if (!bitmapConfig.isDiskEnable())
                return;
            try {
                if (bitmapConfig.isDiskEnable()) {
                    if (mDiskLruCache == null) {
                        initDiskCache(bitmapConfig);
                    }
                    OutputStream outputStream = null;
                    DiskLruCache.Snapshot snapshot = null;
                    if (mDiskLruCache != null) {
                        try {
                            snapshot = mDiskLruCache.get(MD5Utils.String2md5(uri));
                            if (snapshot == null) {
                                DiskLruCache.Editor editor = mDiskLruCache.edit(MD5Utils.String2md5(uri));
                                if (editor != null) {
                                    JLog.e(Thread.currentThread().getName() + "---载入磁盘缓存");
                                    outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
                                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                    editor.commit();
                                    outputStream.close();
                                }
                            } else {
                                mDiskLruCache.remove(uri);
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addBitmapToMem(String uri, IBitmapConfig bitmapConfig, Bitmap bmp) {
        MemCacheKey key = new MemCacheKey(MD5Utils.String2md5(uri), bitmapConfig);
        synchronized (mCacheLock) {
            if (!bitmapConfig.isMemEnable()) return;
            if (getBitmapFromMem(uri, bitmapConfig) == null) {
                JLog.e(Thread.currentThread().getName() + "---载入内存缓存");
                mMemoryCache.put(key, bmp);
            }
        }
    }


    private void clearMemCache() {
    }
}
