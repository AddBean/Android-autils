package com.addbean.autils.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.SparseArray;

public class ResultActivityAdaptor {

    /**
     * 起始requestCode，用于兼容有一些老的逻辑，不让request code冲突
     */
    private final static int REQUEST_CODE_START=20000;

    //记录每一次请求的回调方法
    private SparseArray requests=new SparseArray();

    private Activity mActivity;

    //记录下一个请求的时候会生成的REQUEST_CODE
    private int currentReqCode=REQUEST_CODE_START;

    /**
     * 测试
     */
    public ResultActivityAdaptor(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * @param i
     * @param listener
     */
    public void startActivityForResult(Intent i, ResultActivityListener listener){
        currentReqCode++;
        requests.put(currentReqCode,listener);
        mActivity.startActivityForResult(i,currentReqCode);
    }

    /**
     * 调用
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public boolean onResult(int requestCode,int resultCode,Intent data){
        ResultActivityListener listener=(ResultActivityListener) requests.get(requestCode);
            if(listener!=null) {
                listener.onResult(requestCode, resultCode, data);
                //请求完就清除掉
                requests.remove(requestCode);
                return true;
            }
            return false;
    }
}

