package com.addbean.autils.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

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
}
