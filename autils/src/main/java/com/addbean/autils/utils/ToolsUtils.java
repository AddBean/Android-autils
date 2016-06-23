package com.addbean.autils.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;

public class ToolsUtils {
	/**
	 * 关闭软键盘
	 */
	public static void closeKeyboard(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * dp转px；
	 * 
	 * @param dp
	 * @return
	 */
	public static int dpConvertToPx(Context context, int dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		int px = (int) (dp * scale + 0.5f);
		return px;
	}

	/**
	 * 监测网络是否可行；
	 * 
	 * @param act
	 * @return
	 */
	public static boolean detectNetwork(Activity act) {
		ConnectivityManager connectivity = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}
}
