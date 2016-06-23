package com.addbean.autils.utils;

import android.util.Log;

import com.addbean.autils.DebugConfig;
import com.google.gson.Gson;

/**
 * Created by AddBean on 2016/2/16.
 */
public class ALog {

    public static void debug(Object object) {
        if (DebugConfig._debug_enable)
            ALog.e(object);
    }

    public static final String TAG = "ULog";
    public static final String MATCH = "%s->%s->%d";
    public static final String CONNECTOR = ":<--->:";
    public static final boolean SWITCH = true;


    public static String buildHeader() {
        StackTraceElement stack = Thread.currentThread().getStackTrace()[4];
        return String.format("%s->%s->%d", new Object[]{stack.getClassName(), stack.getMethodName(), Integer.valueOf(stack.getLineNumber())}) + ":<--->:";
    }

    public static void v(Object msg) {
        Gson gson = new Gson();
        String string = "";
        if (msg != null) {
            string = gson.toJson(msg);
        }

        Log.v("ULog", buildHeader() + " " + string);
    }

    public static void d(Object msg) {
        Gson gson = new Gson();
        String string = "";
        if (msg != null) {
            string = gson.toJson(msg);
        }

        Log.d("ULog", buildHeader() + " " + string);
    }

    public static void i(Object msg) {
        Gson gson = new Gson();
        String string = "";
        if (msg != null) {
            string = gson.toJson(msg);
        }

        Log.i("ULog", buildHeader() + " " + string);
    }

    public static void i(Object msg, String tag) {
        Gson gson = new Gson();
        String string = "";
        if (msg != null) {
            string = gson.toJson(msg);
        }

        Log.i(tag, buildHeader() + " " + string);
    }

    public static void w(Object msg) {
        Gson gson = new Gson();
        String string = "";
        if (msg != null) {
            string = gson.toJson(msg);
        }

        Log.w("ULog", buildHeader() + " " + string);
    }

    public static void e(Object msg) {
        if (!DebugConfig._debug_enable)
            return;
        Gson gson = new Gson();
        String string = "";
        if (msg instanceof String) {
            Log.e("ULog", buildHeader() + " " + msg);
            return;
        }
        if (msg != null) {
            string = gson.toJson(msg);
        }
        Log.e("ULog", buildHeader() + " " + string);
    }
}
