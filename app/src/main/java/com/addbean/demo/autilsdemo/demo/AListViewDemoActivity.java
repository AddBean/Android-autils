package com.addbean.demo.autilsdemo.demo;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.addbean.autils.views.listview.AListView;
import com.addbean.autils.views.listview.IOnPullListener;
import com.addbean.demo.autilsdemo.R;

import java.util.ArrayList;
import java.util.List;

public class AListViewDemoActivity extends ActionBarActivity {
    private AListView mList;
    private String[] titles = {"1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alist_view_demo);
        mList = (AListView) findViewById(R.id.list);
        MAdpter adpter = new MAdpter();
        mList.setAdapter(adpter);
        mList.setOnPullListener(new IOnPullListener() {
            @Override
            public void onRefresh() {
                AsyncTask<Void, Void, Boolean> Asytask = new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        mList.setRefreshComplete();
                    }
                };
                Asytask.execute();
            }

            @Override
            public void onLoading() {
                AsyncTask<Void, Void, Boolean> Asytask = new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        mList.setLoadingComplete();
                    }
                };
                Asytask.execute();
            }
        });

    }

    private class MAdpter extends BaseAdapter {

        @Override
        public int getCount() {
            return titles.length;//返回titles.length，总共是6个。
        }

        @Override
        public int getItemViewType(int position) {
            return R.layout.item_1;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //将布局inflate出来，然后获得布局上的控件，设置值
            Log.e("getview", "getview");
            View view = LayoutInflater.from(AListViewDemoActivity.this).inflate(R.layout.item_1, null);

            TextView tvTitle = (TextView) view.findViewById(R.id.tv1);
            tvTitle.setText("ba title " + position);

            return view;
        }
    }
}
