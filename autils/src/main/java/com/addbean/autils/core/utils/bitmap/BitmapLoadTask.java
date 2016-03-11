package com.addbean.autils.core.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.addbean.autils.core.task.BaseAsyncTask;
import com.addbean.autils.tools.MD5Utils;
import com.addbean.autils.utils.JLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by AddBean on 2016/2/14.
 */
public class BitmapLoadTask<T extends View> extends BaseAsyncTask<Object, Object, Bitmap> {
    private IBitmapConfig mBitmapConfig;
    private T mView;
    private String mUrl;
    private String TAG = "BitmapLoadTask";
    private IBitmapCallback mCallback;
    private WeakReference<T> mViewReference;
    private static final int PROGRESS_LOAD_STARTED = 0;
    private static final int PROGRESS_LOADING = 1;

    public BitmapLoadTask(T view, String url, IBitmapConfig bitmapConfig, IBitmapCallback callback) {
        this.mBitmapConfig = bitmapConfig;
        this.mView = view;
        this.mUrl = url;
        this.mCallback = callback;
        this.mViewReference = new WeakReference<T>(view);
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        Bitmap bmp = mBitmapConfig.getBitmapCache().getBitmapFromDisk(mUrl, mBitmapConfig);
        if (bmp != null) {
            mBitmapConfig.getBitmapCache().addBitmapToMem(mUrl, mBitmapConfig, bmp);
            return bmp;
        }
        bmp = downloadBitmap();
        JLog.e(Thread.currentThread().getName() + "--从网络加载");
        mBitmapConfig.getBitmapCache().addBitmapToMem(mUrl, mBitmapConfig, bmp);
        mBitmapConfig.getBitmapCache().addBitmapToDisk(mUrl, mBitmapConfig, bmp);
        return bmp;
    }

    private Bitmap downloadBitmap() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mBitmapConfig.getBitmapDownLoader().downloadToStream(mUrl, outputStream);
        byte[] data = outputStream.toByteArray();
        Bitmap bmp = null;
        try {
            bmp = decodeBitmap(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }


    private Bitmap decodeBitmap(byte[] data) throws IOException {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable = true;
        try {
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public void updateProgress(long total, long current) {
        this.publishProgress(PROGRESS_LOADING, total, current);
    }


    @Override
    protected void onProgressUpdate(Object... values) {
        if (values == null || values.length == 0) return;
        if (mView == null) return;
        switch ((Integer) values[0]) {
            case PROGRESS_LOAD_STARTED:
                if (mCallback != null)
                    mCallback.onLoadStarted(mView, mUrl, mBitmapConfig);
                break;
            case PROGRESS_LOADING:
                if (values.length != 3) return;
                if (mCallback != null)
                    mCallback.onLoading(mView, mUrl, mBitmapConfig, (Long) values[1], (Long) values[2]);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (mView != null) {
            if (bitmap != null) {
                if (mView instanceof ImageView) {
                    ((ImageView) mView).setImageBitmap(bitmap);
                }
                if (mCallback != null)
                    mCallback.onLoadCompleted(
                            mView,
                            this.mUrl,
                            bitmap,
                            mBitmapConfig);
            } else {
                if (mCallback != null)
                    mCallback.onLoadFailed(
                            mView,
                            this.mUrl);
            }
        }
    }

}
