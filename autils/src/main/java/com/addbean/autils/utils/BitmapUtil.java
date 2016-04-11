package com.addbean.autils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * 图片压缩工具类
 * 
 * @author qiuxinggang 2015-1-5 下午1:29:59
 */
public class BitmapUtil {

	public static Bitmap toRoundBitmap(Bitmap bitmap) {

		// 圆形图片宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		boolean isNeedMatrix = false;
		Bitmap resizeBmp = null;
		// 正方形的边长
		int r = 0;
		// 取最短边做边长
		if (width > height) {
			r = height;
			/* 修改最小为64d */

			double scale = (double) 150 / (double) r;
			Matrix matrix = new Matrix();
			matrix.postScale((float) scale, (float) scale);
			resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
			isNeedMatrix = true;
			r = resizeBmp.getHeight();
			width = resizeBmp.getHeight();
			height = resizeBmp.getHeight();

		} else {
			r = width;
			/* 修改最小为64d */

			double scale = (double) 150 / (double) r;
			Matrix matrix = new Matrix();
			matrix.postScale((float) scale, (float) scale);
			resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
			isNeedMatrix = true;
			r = resizeBmp.getWidth();
			width = resizeBmp.getWidth();
			height = resizeBmp.getWidth();

		}

		if (isNeedMatrix) {

			// 构建一个bitmap
			Bitmap backgroundBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			// new一个Canvas，在backgroundBmp上画图
			Canvas canvas = new Canvas(backgroundBmp);
			Paint paint = new Paint();
			// 设置边缘光滑，去掉锯齿
			paint.setAntiAlias(true);
			// 宽高相等，即正方形
			// RectF rect = new RectF(0, 0, r, r);
			RectF rect = new RectF(0, 0, r, r);

			// 通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
			// 且都等于r/2时，画出来的圆角矩形就是圆形
			canvas.drawRoundRect(rect, r / 2, r / 2, paint);
			// 设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			// canvas将bitmap画在backgroundBmp上

			canvas.drawBitmap(resizeBmp, null, rect, paint);

			// 返回已经绘画好的backgroundBmp
			return backgroundBmp;

		} else {
			// 构建一个bitmap
			Bitmap backgroundBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			// new一个Canvas，在backgroundBmp上画图
			Canvas canvas = new Canvas(backgroundBmp);
			Paint paint = new Paint();
			// 设置边缘光滑，去掉锯齿
			paint.setAntiAlias(true);
			// 宽高相等，即正方形
			// RectF rect = new RectF(0, 0, r, r);
			RectF rect = new RectF(0, 0, r, r);

			// 通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
			// 且都等于r/2时，画出来的圆角矩形就是圆形
			canvas.drawRoundRect(rect, r / 2, r / 2, paint);
			// 设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			// canvas将bitmap画在backgroundBmp上

			canvas.drawBitmap(bitmap, null, rect, paint);
			// 返回已经绘画好的backgroundBmp
			return backgroundBmp;
		}
	}

	/**
	 * * 获取圆形图片方法
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return Bitmap
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap, int pixels) {
		Paint paint = new Paint();
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		int x = bitmap.getWidth();

		canvas.drawCircle(x / 2, x / 2, x / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;

	}
	
	public static Bitmap upImageSize(Context context, Bitmap bmp, int width, int height) {
		if (bmp == null) {
			return null;
		}
		// 计算比例
		float scaleX = (float) width / bmp.getWidth();// 宽的比例
		float scaleY = (float) height / bmp.getHeight();// 高的比例
		// 新的宽高
		int newW = 0;
		int newH = 0;
		if (scaleX > scaleY) {
			newW = (int) (bmp.getWidth() * scaleX);
			newH = (int) (bmp.getHeight() * scaleX);
		} else if (scaleX <= scaleY) {
			newW = (int) (bmp.getWidth() * scaleY);
			newH = (int) (bmp.getHeight() * scaleY);
		}
		return Bitmap.createScaledBitmap(bmp, newW, newH, true);
	}
}
