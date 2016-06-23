package com.addbean.demo.autilsdemo.demo.effect;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.addbean.demo.autilsdemo.R;

public class BlurDemoActivity extends Activity {
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_demo);
        TextView tv1 = (TextView) findViewById(R.id.tv_1);
        TextView tv2 = (TextView) findViewById(R.id.tv_2);
        img = (ImageView) findViewById(R.id.image);
        Button btn = (Button) findViewById(R.id.btn);
    }

    int width;
    int heigh;
}
