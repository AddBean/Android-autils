package com.addbean.autils.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.addbean.autils.core.cache.BitmapCache;
import com.addbean.autils.core.utils.bitmap.BitmapConfig;
import com.addbean.autils.core.utils.bitmap.BitmapDrawable;
import com.addbean.autils.core.utils.bitmap.BitmapLoadTask;
import com.addbean.autils.core.utils.bitmap.IBitmapCallback;
import com.addbean.autils.core.utils.bitmap.IBitmapConfig;
import com.addbean.autils.utils.ALog;

/**
 * Created by AddBean on 2016/2/3.
 */


public class ABitmapUtils {
    private Context mContext;
    private IBitmapConfig mBitmapConfig;
    private BitmapCache mBitmapCache;

    public ABitmapUtils(Context mContext) {
        this.mContext = mContext;
        getBitmapConfig();
        mBitmapCache = mBitmapConfig.getBitmapCache();
        mBitmapCache.initDiskCache(mBitmapConfig);
        mBitmapCache.initMemCache(mBitmapConfig);
    }


    public void setBitmapConfig(IBitmapConfig mBitmapConfig) {
        this.mBitmapConfig = mBitmapConfig;
    }

    /**
     * ----|获取配置策略;
     * ----|加载一级缓存:
     * --------|为空，加载二级缓存(比较耗时，在异步线程加载):
     * --------|----|为空，从线程池获取加载线程:
     * --------|----|----|该线程是否存在（防止多线程加载同一资源）；
     * --------|----|----|----|存在，则返回等待回调；
     * --------|----|----|----|不存在，启动网络异步加载;
     * --------|----|----|----|----|开始网络加载
     * --------|----|----|----|----|存入一级缓存和二级缓存,并回调加载位图；
     * --------|----|非空，回调并加载位图；
     * --------|非空，回调并加载位图；
     */
    public <T extends View> void load(T view, String uri) {
        load(view, uri, null);
    }

    public <T extends View> void load(T view, String uri, IBitmapCallback mBitamCallback) {
        Bitmap bmp1 = mBitmapCache.getBitmapFromMem(uri, mBitmapConfig);
        if (mBitmapConfig != null)
            mBitmapConfig.setCallbackListener(mBitamCallback);
        if (bmp1 != null) {
            displayBitmap(view, bmp1);
            return;
        }
        if (!loadTaskExist(view, uri, mBitmapConfig)) {
            mBitmapCache.getBitampFromWeb(uri, mBitmapConfig, getNewBitmapTask(view, uri, mBitamCallback));
        }
    }


    public void setOnCallbackListener(IBitmapCallback bitmapCallback) {
        if (mBitmapConfig != null)
            mBitmapConfig.setCallbackListener(bitmapCallback);
    }

    private <T extends View> BitmapLoadTask getNewBitmapTask(T view, String uri, IBitmapCallback mBitamCallback) {
        BitmapLoadTask bitmapLoadTask = new BitmapLoadTask(view, uri, mBitmapConfig);
        BitmapDrawable drawable = new BitmapDrawable(null, bitmapLoadTask);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(drawable);
        }
        return bitmapLoadTask;
    }

    private <T extends View> boolean loadTaskExist(T view, String uri, IBitmapConfig mBitmapConfig) {
        Drawable drawable = ((ImageView) view).getDrawable();
        if (drawable == null)
            return false;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            BitmapLoadTask task = bitmapDrawable.getBitmapWorkerTask();
            if (task != null) {
                ALog.debug("任务已存在");
                return true;
            }
        }
        return false;
    }

    private <T extends View> void displayBitmap(T view, Bitmap bmp) {
        if (view instanceof ImageView) {
            ((ImageView) view).setImageBitmap(bmp);
        }
    }

    public IBitmapConfig getBitmapConfig() {
        if (mBitmapConfig == null)
            setBitmapConfig(BitmapConfig.getInstance(mContext, getDefaultDiskPath()));
        return mBitmapConfig;
    }

    private String getDefaultDiskPath() {
        return mContext.getCacheDir().getPath();
    }

}
