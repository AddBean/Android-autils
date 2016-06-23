package com.addbean.autils.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class WindowUtils {

	/**
	 * 设置 窗口模式大小
	 * @param context
	 * @param height
	 * @param width
	 */
	
	public static void setDialogWinodwsSize(Context context, float width, float height) {
		Window dialogWindow = ((Activity) context).getWindow();
		WindowManager m = ((Activity) context).getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		p.height = (int) (d.getHeight() * height); // 高度设置为屏幕的0.6
		p.width = (int) (d.getWidth() * width); // 宽度设置为屏幕的0.65
		((Activity) context).getWindow().setAttributes(p);
		

	}
	
	public static void setDialogWinodwsSizeAlgrinBottom(Context context, float width, float height ) {
		Window dialogWindow = ((Activity) context).getWindow();
		WindowManager m = ((Activity) context).getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		p.height = (int) (d.getHeight() * height); // 高度设置为屏幕的0.6
		p.width = (int) (d.getWidth() * width); // 宽度设置为屏幕的0.65
		((Activity) context).getWindow().setAttributes(p);
		((Activity) context).getWindow().setGravity(Gravity.BOTTOM);

	}
}
