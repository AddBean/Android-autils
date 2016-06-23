package com.addbean.demo.autilsdemo.demo.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.addbean.aviews.views.ATimeIconView;
import com.addbean.demo.autilsdemo.R;

import java.util.Date;

public class ATimeIconViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atime_icon_view);
        final ATimeIconView aTimeIconView= (com.addbean.aviews.views.ATimeIconView) findViewById(R.id.time);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aTimeIconView.setTime(new Date(),false);
            }
        });
    }
}
