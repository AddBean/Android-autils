package com.addbean.demo.autilsdemo.demo.bitmap_cache;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.addbean.autils.core.utils.BitmapUtils;
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
    private BitmapUtils mBitmapUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_cache_demo);
        mBitmapUtils=new BitmapUtils(getApplicationContext());
        mList = (AListView) findViewById(R.id.list);
        mAdpter = new MultiAdapter(getApplicationContext(), mData, new MultiAdapter.IAdpterListener() {
            @Override
            public void convert(AdapterHelper helper, MultiAdapter.ConvertViewInf data) {
                String url= (String) data.getData();
                mBitmapUtils.load(data.getView().findViewById(R.id.image_1),url);
                mBitmapUtils.load(data.getView().findViewById(R.id.image_2),url);
                mBitmapUtils.load(data.getView().findViewById(R.id.image_3),url);
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
        for (int i=0;i<3;i++) {
            mData.add(new ListItemEx(0, list.get(i)));
        }
        mAdpter.notifyDataSetChanged();
    }
}
