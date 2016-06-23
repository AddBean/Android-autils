package com.addbean.autils.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.addbean.autils.core.utils.bitmap.BitmapImageSize;
import com.addbean.autils.utils.ALog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by AddBean on 2016/3/11.
 */
public class BitmapToolUtils {
    private static final Object lock = new Object();

    private BitmapToolUtils() {

    }

    public static Bitmap decodeInputStream(InputStream inputStream) {
        Bitmap bmp = null;
        bmp = BitmapFactory.decodeStream(inputStream);
        return bmp;
    }

    public static Bitmap decodeInputStreamWithSize(InputStream inputStream, int width, int heigh) {
        Bitmap bmp = null;
        bmp = BitmapFactory.decodeStream(inputStream);
        return bmp;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, File file) {
        Bitmap result = bitmap;

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(file.getPath());
        } catch (Throwable e) {
            return result;
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        int angle = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                angle = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                angle = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                angle = 270;
                break;
            default:
                angle = 0;
                break;
        }
        if (angle != 0) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            bitmap.recycle();
            bitmap = null;
        }
        return result;
    }

    /**
     * 根据byte[]返回bitmap
     *
     * @param data
     * @param maxSize
     * @return
     */
    public static Bitmap decodeSampledBitmapFromByteArray(byte[] data, BitmapImageSize maxSize) {
        synchronized (lock) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPurgeable = true;
            options.inInputShareable = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, options);
            if (maxSize != null)
                options.inSampleSize = calculateInSampleSize(options, maxSize.getMaxWidth(), maxSize.getMaxHeigh());
            options.inJustDecodeBounds = false;
            try {
                return BitmapFactory.decodeByteArray(data, 0, data.length, options);
            } catch (Throwable e) {
                ALog.e(e.getMessage());
                return null;
            }
        }
    }

    /**
     * 根据inputstream压缩图片；
     *
     * @param inputStream
     * @param maxSize
     * @return
     */
    public static Bitmap decodeSampledBitmapFromInputStream(InputStream inputStream, BitmapImageSize maxSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        if (maxSize != null)
            options.inSampleSize = calculateInSampleSize(options, maxSize.getMaxWidth(), maxSize.getMaxHeigh());
        options.inJustDecodeBounds = false;

        try {
            return BitmapFactory.decodeStream(inputStream, null, options);
        } catch (Throwable e) {
            ALog.e(e.getMessage());
            return null;
        }
    }

    /**
     * 计算缩放值
     *
     * @param options
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (width > maxWidth || height > maxHeight) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) maxHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) maxWidth);
            }

            final float totalPixels = width * height;

            final float maxTotalPixels = maxWidth * maxHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > maxTotalPixels) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
}
