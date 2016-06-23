package com.addbean.autils.utils;

import android.content.Intent;

public interface ResultActivityListener {
	public void onResult(int requestCode, int resultCode, Intent data);
}