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
import com.addbean.autils.utils.ALog;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by AddBean on 2016/3/10.
 */
public class BitmapCache {
    private Context mContext;
    private Object mDiskCacheLock = new Object();
    private final Object mMemCacheLock = new Object();
    private DiskLruCache mDiskLruCache;
    private MemLruCache<MemCacheKey, Bitmap> mMemoryCache;
    private String TAG = "BitmapCache";

    public BitmapCache(Context context) {
        this.mContext = context;
    }

    public void initMemCache(IBitmapConfig bitmapConfig) {
        synchronized (mMemCacheLock) {
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
        synchronized (mDiskCacheLock) {
            if (!bitmapConfig.isDiskEnable() || mDiskLruCache != null)
                return;
            File diskCacheDir = new File(bitmapConfig.getDiskCachePath());
            if (diskCacheDir.exists() || diskCacheDir.mkdirs()) {
                long availableSpace = OtherUtils.getAvailableSpace(diskCacheDir);
                long diskCacheSize = bitmapConfig.getDiskCacheSize();
                diskCacheSize = availableSpace > diskCacheSize ? diskCacheSize : availableSpace;
                ALog.debug("diskCacheSize:" + diskCacheSize);
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
        synchronized (mDiskCacheLock) {
            File diskCacheDir = new File(bitmapConfig.getDiskCachePath());
            if (diskCacheDir.exists() || diskCacheDir.mkdirs()) {
                DiskLruCache.Snapshot snapshot = null;
                Bitmap bitmap = null;
                try {
                    snapshot = mDiskLruCache.get(MD5Utils.String2md5(uri));
                    if (snapshot == null) return null;
                    if (bitmapConfig.isShowOriginal() || bitmapConfig.getImageSize() == null) {//是否显示原图；
                        bitmap = BitmapToolUtils.decodeInputStream(snapshot.getInputStream(DISK_CACHE_INDEX));
                    } else {
                        bitmap = BitmapToolUtils.decodeInputStreamWithSize(
                                snapshot.getInputStream(DISK_CACHE_INDEX),
                                bitmapConfig.getImageSize().getMaxWidth(),
                                bitmapConfig.getImageSize().getMaxHeigh());
                    }
                    if (bitmap != null)
                        ALog.debug(Thread.currentThread().getName() + "--从磁盘加载");
                    if (bitmapConfig.isRotation())
                        bitmap = BitmapToolUtils.rotateBitmap(bitmap, getCacheFileFromKey(bitmapConfig, MD5Utils.String2md5(uri)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bitmap;
            }
            return null;
        }
    }


    private File getCacheFileFromKey(IBitmapConfig bitmapConfig, String key) {
        File file = new File(bitmapConfig.getDiskCachePath() + "/" + key + "." + DISK_CACHE_INDEX);
        return file;
    }

    public Bitmap getBitmapFromMem(String uri, IBitmapConfig bitmapConfig) {
        if (mMemoryCache != null && bitmapConfig.isMemEnable()) {
            synchronized (mMemCacheLock) {
                MemCacheKey key = new MemCacheKey(MD5Utils.String2md5(uri), bitmapConfig);
                Bitmap bitmap = mMemoryCache.get(key);
                if (bitmap != null)
                    ALog.debug(Thread.currentThread().getName() + "--从内存加载");
                return bitmap;
            }
        }
        return null;
    }

    private final int DISK_CACHE_INDEX = 0;

    public void addBitmapToDisk(String uri, IBitmapConfig bitmapConfig, Bitmap bmp) {
        synchronized (mDiskCacheLock) {
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
//                                    ALog.e(Thread.currentThread().getName() + "---载入磁盘缓存");
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
        synchronized (mMemCacheLock) {
            if (!bitmapConfig.isMemEnable()) return;
            if (getBitmapFromMem(uri, bitmapConfig) == null) {
                ALog.debug(Thread.currentThread().getName() + "---载入内存缓存");
                mMemoryCache.put(key, bmp);
            }
        }
    }


    public void clearMemCache() {
        if (mMemoryCache != null) {
            mMemoryCache.evictAll();
        }
    }

    public void clearDiskCache() {
        synchronized (mDiskCacheLock) {
            if (mDiskLruCache != null && !mDiskLruCache.isClosed()) {
                try {
                    mDiskLruCache.delete();
                    mDiskLruCache.close();
                } catch (Throwable e) {
                    ALog.e(e.getMessage());
                }
                mDiskLruCache = null;
            }
        }
    }

}
