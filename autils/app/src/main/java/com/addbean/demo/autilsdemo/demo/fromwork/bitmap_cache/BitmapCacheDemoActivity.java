package com.addbean.demo.autilsdemo.demo.fromwork.bitmap_cache;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.addbean.autils.core.utils.ABitmapUtils;
import com.addbean.autils.core.utils.bitmap.BitmapImageSize;
import com.addbean.autils.core.utils.bitmap.IBitmapCallback;
import com.addbean.autils.core.utils.bitmap.IBitmapConfig;
import com.addbean.autils.utils.ALog;
import com.addbean.aviews.utils.multiadapter.AdapterHelper;
import com.addbean.aviews.utils.multiadapter.ListItemEx;
import com.addbean.aviews.utils.multiadapter.MultiAdapter;
import com.addbean.aviews.views.listview.AListView;
import com.addbean.aviews.views.listview.IOnPullListener;
import com.addbean.demo.autilsdemo.R;

import java.util.ArrayList;
import java.util.List;

public class BitmapCacheDemoActivity extends Activity {
    private AListView mList;
    private MultiAdapter mAdpter;
    private List<ListItemEx> mData = new ArrayList<>();
    private ABitmapUtils mBitmapUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_cache_demo);
        mBitmapUtils = new ABitmapUtils(getApplicationContext());
        IBitmapConfig bitmapConfig = mBitmapUtils.getBitmapConfig();
        bitmapConfig.setDiskCacheEnable(true);
        bitmapConfig.setMemCacheEnable(true);
        bitmapConfig.setLoadingEmptyImage(R.drawable.ic_launcher);
        bitmapConfig.setLoadingFailedImage(R.drawable.ic_launcher);
        bitmapConfig.setLoadingImage(R.drawable.ic_launcher);
//        bitmapConfig.setMinCacheMem(10);
        bitmapConfig.setBitmapImageSize(new BitmapImageSize(50, 150));
        mList = (AListView) findViewById(R.id.list);
        mAdpter = new MultiAdapter(getApplicationContext(), mData, new MultiAdapter.IAdpterListener() {
            @Override
            public void convert(AdapterHelper helper, MultiAdapter.ConvertViewInf data) {
                final String url = (String) data.getData();
                mBitmapUtils.load(data.getView().findViewById(R.id.image_1), url,null, new IBitmapCallback() {
                    @Override
                    public void onPreLoad(View container, String uri, IBitmapConfig config) {
                        ALog.debug("onPreLoad");
                    }

                    @Override
                    public void onLoading(View container, String uri, IBitmapConfig config, long current, long total) {
                        ALog.debug("onLoading:" + current + " " + total);
                    }

                });
            }
        });
        mAdpter.addType(R.layout.item_image_cache);
        mList.setAdapter(mAdpter);
        mList.setOnPullListener(new IOnPullListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoading() {

            }
        });
        List<String> list = (new TestImageList()).getmList();
        for (int i = 0; i < list.size(); i++) {
            mData.add(new ListItemEx(0, list.get(i)));
        }
        mAdpter.notifyDataSetChanged();
    }
}
