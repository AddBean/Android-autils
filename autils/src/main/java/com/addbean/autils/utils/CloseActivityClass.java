package com.addbean.autils.utils;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

//关闭Activity的类
public class CloseActivityClass {

	public static List<Activity> activityList = new ArrayList<Activity>();

	public static void exitActivity(Context ctx) {
		// 关闭所有Activity
		for (int i = 0; i < activityList.size(); i++) {
			if (null != activityList.get(i)) {
				if(activityList.get(i) != null){
					activityList.get(i).finish();
				}	
			}
		}
	}
}
