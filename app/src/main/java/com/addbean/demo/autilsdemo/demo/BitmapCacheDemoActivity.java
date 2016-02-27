package com.addbean.demo.autilsdemo.demo;

import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.addbean.autils.core.task.TaskExecutors;
import com.addbean.autils.core.utils.bitmap.task.BitmapLoadTask;
import com.addbean.demo.autilsdemo.R;

public class BitmapCacheDemoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_cache_demo);
        TaskExecutors executors = new TaskExecutors();
        for (int i = 0; i < 10; i++) {
            BitmapLoadTask<ImageView> task = new BitmapLoadTask<ImageView>(new ImageView(this), "tast" + i);
            task.executeOnExecutor(executors);
        }
    }
}
