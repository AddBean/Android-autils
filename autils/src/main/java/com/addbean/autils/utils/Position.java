package com.addbean.autils.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class Position implements LocationListener {
	private LocationManager locationManager;
	private LocationListener locationListener;
	private Context context;
	private String TAG = "Position";
	private IPosition iPosition;

	public Position(Context context, IPosition iPosition) {
		this.context = context;
		this.iPosition = iPosition;
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> lp = locationManager.getAllProviders();// 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
		for (String item : lp) {
			Log.i(TAG, "可用位置服务：" + item);
		}
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(false); // 设置位置服务免费
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); // 设置水平位置精度
		String providerName = locationManager.getBestProvider(criteria, true);// getBestProvider 只有允许访问调用活动的位置供应商将被返回
		Log.i(TAG, "------位置服务：" + providerName);
		if (providerName != null) {
			Location location = locationManager
					.getLastKnownLocation(providerName);
			if (location == null) {// 如果获取最后位置失败；
				Log.d(TAG, "回掉获取");
				locationManager
						.requestLocationUpdates(providerName, 0, 0, this);// 回调获取数据；
			} else {
				callBack(location);
			}

		} else {
			callBack(null);// 网络未开或没有获取位置权限；
		}
	}
	
	public void callBack(Location location){
		this.iPosition.LoctionListener(null);
	};

	@Override
	public void onLocationChanged(Location location) {
		Log.d(TAG, "onLocationChanged:"+location.getLatitude()+"/"+location.getLongitude());
		callBack( location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d(TAG, "onProviderDisabled");
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d(TAG, "onProviderEnabled");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onStatusChanged");
	}

	public interface IPosition {
		public void LoctionListener(Location location);
	}

}
