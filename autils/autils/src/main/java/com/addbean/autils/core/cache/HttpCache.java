package com.addbean.autils.core.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.addbean.autils.core.cache.base.DiskLruCache;
import com.addbean.autils.core.utils.file.IHttpConfig;
import com.addbean.autils.tools.BitmapToolUtils;
import com.addbean.autils.tools.MD5Utils;
import com.addbean.autils.tools.OtherUtils;
import com.addbean.autils.utils.ALog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by AddBean on 2016/7/7.
 */
public class HttpCache {
    private Context mContext;
    private Object mDiskCacheLock = new Object();
    private DiskLruCache mDiskLruCache;
    private String TAG = "HttpCache";
    private final int DISK_CACHE_INDEX = 0;

    public HttpCache(Context context) {
        this.mContext = context;
    }

    public void initDiskCache(IHttpConfig httpConfig) {
        synchronized (mDiskCacheLock) {
            if (!httpConfig.isDiskEnable())
                return;
            File diskCacheDir = new File(httpConfig.getDiskCachePath());
            if (diskCacheDir.exists() || diskCacheDir.mkdirs()) {
                long availableSpace = OtherUtils.getAvailableSpace(diskCacheDir);
                long diskCacheSize = httpConfig.getDiskCacheSize();
                diskCacheSize = availableSpace > diskCacheSize ? diskCacheSize : availableSpace;
                ALog.debug("diskHttpCacheSize:" + diskCacheSize);
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

    public String getStringFromDisk(String uri, IHttpConfig httpConfig) {
        if (!httpConfig.isDiskEnable())
            return null;
        synchronized (mDiskCacheLock) {
            File diskCacheDir = new File(httpConfig.getDiskCachePath());
            if (diskCacheDir.exists() || diskCacheDir.mkdirs()) {
                DiskLruCache.Snapshot snapshot = null;
                String str = null;
                try {
                    snapshot = mDiskLruCache.get(MD5Utils.String2md5(uri));
                    if (snapshot == null) return null;
                    str = readString((FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX));
                    if (str != null)
                        ALog.debug(Thread.currentThread().getName() + "--从磁盘加载");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return str;
            }
            return null;
        }
    }


    private static String readString(FileInputStream in) {
        String str = "";
        try {
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer, "UTF8");
        } catch (IOException e) {
            return null;
        }
        return str;

    }


    private File getCacheFileFromKey(IHttpConfig httpConfig, String key) {
        File file = new File(httpConfig.getDiskCachePath() + "/" + key + "." + DISK_CACHE_INDEX);
        return file;
    }


    public void addStringToDisk(String uri, IHttpConfig httpConfig, String content) {
        synchronized (mDiskCacheLock) {
            if (!httpConfig.isDiskEnable())
                return;
            try {
                if (httpConfig.isDiskEnable()) {
                    if (mDiskLruCache == null) {
                        initDiskCache(httpConfig);
                    }
                    OutputStream outputStream = null;
                    DiskLruCache.Snapshot snapshot = null;
                    if (mDiskLruCache != null) {
                        try {
                            snapshot = mDiskLruCache.get(MD5Utils.String2md5(uri));
                            if (snapshot == null) {
                                DiskLruCache.Editor editor = mDiskLruCache.edit(MD5Utils.String2md5(uri));
                                if (editor != null) {
                                    outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
                                    PrintStream ps = new PrintStream(outputStream);
                                    ps.print(content);
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
