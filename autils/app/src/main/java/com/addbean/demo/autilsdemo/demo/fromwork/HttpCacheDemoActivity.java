package com.addbean.demo.autilsdemo.demo.fromwork;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.addbean.autils.core.utils.AHttpUtils;
import com.addbean.autils.utils.AnimUtils;
import com.addbean.demo.autilsdemo.R;

import java.io.File;

/**
 * Created by AddBean on 2016/7/7.
 */
public class HttpCacheDemoActivity extends Activity {
    private TextView mTextRead;
    private TextView mTextWrite;
    private TextView mTextClear;
    private AHttpUtils mHttpUtils;
    private Context mContext;
    private TextView mTextRewrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_demo);
        mContext = this;
        mTextRead = (TextView) findViewById(R.id.text_read);
        mTextWrite = (TextView) findViewById(R.id.text_write);
        mTextRewrite = (TextView) findViewById(R.id.text_rewrite);
        mTextClear = (TextView) findViewById(R.id.text_clear);
        mHttpUtils = new AHttpUtils(this);
        mHttpUtils.getmHttpConfig().setDiskCachePath(getHttpCacheDir());
        mHttpUtils.getmHttpConfig().setDiskEnable(true);
        mHttpUtils.setHttpConfig(mHttpUtils.getmHttpConfig());
        bindEvent();
    }
    /**
     * 文件下载/p2p缓存等的主目录；
     *
     * @return
     */
    public static String getBaseDir() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test/";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path;
    }

    /**
     * 获取HTTP catch路径
     * @return
     */
    public static String getHttpCacheDir() {
        String path = getBaseDir()+ "http/";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path;
    }

    private void bindEvent() {
        mTextWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimUtils.ScaleAnim(view);
                mHttpUtils.save("这是测试", "test_key");
            }
        });
        mTextRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimUtils.ScaleAnim(view);
                String result = mHttpUtils.get("test_key");
                if (!TextUtils.isEmpty(result)) {
                    Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "读取失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mTextRewrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimUtils.ScaleAnim(view);
                mHttpUtils.save("这是覆盖测试", "test_key");
            }
        });
        mTextClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimUtils.ScaleAnim(view);
                mHttpUtils.clearCache();
            }
        });
    }
}
