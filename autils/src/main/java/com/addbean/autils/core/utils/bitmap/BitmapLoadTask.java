package com.addbean.autils.core.utils.bitmap;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.addbean.autils.core.http.download.IDownloadListener;
import com.addbean.autils.core.task.BaseAsyncTask;
import com.addbean.autils.tools.BitmapToolUtils;
import com.addbean.autils.utils.ALog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by AddBean on 2016/2/14.
 */
public class BitmapLoadTask<T extends View> extends BaseAsyncTask<Object, Object, Bitmap> implements IDownloadListener {
    private IBitmapConfig mBitmapConfig;
    private T mView;
    private String mUrl;
    private String TAG = "BitmapLoadTask";
    private IBitmapCallback mCallback;
    private WeakReference<T> mViewReference;
    private static final int PROGRESS_STARTED = 0;
    private static final int PROGRESS_LOADING = 1;
    private static final int PROGRESS_SUCCESS = 2;
    private static final int PROGRESS_FAILED = 3;

    public BitmapLoadTask(T view, String url, IBitmapConfig bitmapConfig) {
        this.mBitmapConfig = bitmapConfig;
        this.mView = view;
        this.mUrl = url;
        this.mCallback = bitmapConfig.getCallbackListener();
        this.mViewReference = new WeakReference<T>(view);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mCallback != null)
            mCallback.onPreLoad(mView, mUrl, mBitmapConfig);
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        Bitmap bmp = mBitmapConfig.getBitmapCache().getBitmapFromDisk(mUrl, mBitmapConfig);
        if (bmp != null) {
            this.publishProgress(PROGRESS_STARTED);
            mBitmapConfig.getBitmapCache().addBitmapToMem(mUrl, mBitmapConfig, bmp);
            return bmp;
        }
        bmp = downloadBitmap();
        if (bmp == null)
            return null;
        mBitmapConfig.getBitmapCache().addBitmapToMem(mUrl, mBitmapConfig, bmp);
        mBitmapConfig.getBitmapCache().addBitmapToDisk(mUrl, mBitmapConfig, bmp);
        return mBitmapConfig.getBitmapCache().getBitmapFromMem(mUrl, mBitmapConfig);
    }

    private Bitmap downloadBitmap() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mBitmapConfig.getBitmapDownLoader().downloadToStream(mUrl, outputStream, this);
        byte[] data = outputStream.toByteArray();
        ALog.e(Thread.currentThread().getName() + "--从网络加载");
        Bitmap bmp = null;
        try {
            if (mBitmapConfig.getImageSize() == null)
                bmp = decodeBitmap(data, new BitmapImageSize(mView.getMeasuredWidth(), mView.getMeasuredHeight()));
            else
                bmp = decodeBitmap(data, mBitmapConfig.getImageSize());
        } catch (IOException e) {
            e.printStackTrace();
            if (mView instanceof ImageView && mBitmapConfig.getLoadingImage() > 0) {
                ((ImageView) mView).setImageResource(mBitmapConfig.getLoadingFailedImage());
            }
        }
        return bmp;
    }


    private Bitmap decodeBitmap(byte[] data, BitmapImageSize imageSize) throws IOException {
        try {
            return BitmapToolUtils.decodeSampledBitmapFromByteArray(data, imageSize);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }


    @Override
    protected void onProgressUpdate(Object... values) {
        if (values == null || values.length == 0) return;
        if (mView == null) return;
        switch ((Integer) values[0]) {
            case PROGRESS_STARTED:
                if (mView instanceof ImageView && mBitmapConfig.getLoadingImage() > 0) {
                    ((ImageView) mView).setImageResource(mBitmapConfig.getLoadingImage());
                }
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
                if (mCallback != null) {
                    mCallback.onLoadCompleted(
                            mView,
                            this.mUrl,
                            bitmap,
                            mBitmapConfig);
                }
            } else {
                if (mView instanceof ImageView && mBitmapConfig.getLoadingImage() > 0) {
                    ((ImageView) mView).setImageResource(mBitmapConfig.getLoadingEmptyImage());
                }
                if (mCallback != null) {
                    mCallback.onLoadFailed(
                            mView,
                            this.mUrl);
                }
            }
        }
    }

    @Override
    public void downloadStart() {
        this.publishProgress(PROGRESS_STARTED);
    }

    @Override
    public void downloadUpdate(long currentLen, long totalLen) {
        this.publishProgress(PROGRESS_LOADING, currentLen, totalLen);
    }

}
