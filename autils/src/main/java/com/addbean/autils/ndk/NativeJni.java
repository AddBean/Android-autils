package com.addbean.autils.ndk;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by AddBean on 2016/2/17.
 */
public class NativeJni {
    static Context mContext;

    static {
        System.loadLibrary("aJni");//导入生成的链接库文件
    }

    public NativeJni(Context context) {
        mContext = context;
        nativeInit();
    }

    private native void nativeInit();

    public native String getString(String msg);

    public native int[] blur(int[] pixes, int w, int h, int r);

    public static void loge(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void toast(String msg) {
        Toast.makeText(mContext, "NDK : "+msg, Toast.LENGTH_SHORT).show();
    }
}
