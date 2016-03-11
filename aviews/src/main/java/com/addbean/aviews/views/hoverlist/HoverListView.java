package com.addbean.aviews.views.hoverlist;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.addbean.aviews.R;
import com.addbean.aviews.utils.multiadapter.AdapterHelper;
import com.addbean.aviews.utils.multiadapter.ListItemEx;
import com.addbean.aviews.utils.multiadapter.MultiAdapter;
import com.addbean.aviews.views.listview.AListView;
import com.addbean.aviews.views.listview.IOnPullListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AddBean on 2016/2/17.
 */
public class HoverListView extends FrameLayout implements AbsListView.OnScrollListener {
    private Context mContext;
    private LayoutInflater mInflater;
    private View mView;
    private LinearLayout mHoverLayout;
    private AListView mListView;
    private int mCurrentPosition = 0;
    private MultiAdapter mAdapter;
    private List<ListItemEx> mData = new ArrayList<ListItemEx>();
    private IHoverAdpterListener mAdpterListener;
    private int[] mTypeList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter.notifyDataSetChanged();
        }
    };
    private int mHoverResId;

    public HoverListView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public HoverListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public HoverListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        this.mView = mInflater.inflate(R.layout.hover_list_view, null);
        this.addView(this.mView);
        this.mHoverLayout = (LinearLayout) this.mView.findViewById(R.id.hover_layout);
        this.mListView = (AListView) this.mView.findViewById(R.id.list);
        this.mListView.setOnScrollListener(this);
    }

    public void setup() {
        this.mAdapter = new MultiAdapter(mContext, mData, new MultiAdapter.IAdpterListener() {
            @Override
            public void convert(AdapterHelper helper, MultiAdapter.ConvertViewInf data) {
//                Log.e("convert","convert");
                if (mAdpterListener == null)
                    throw new IllegalArgumentException("mAdpterListener不能为空");
                mAdpterListener.convert(helper, data);
            }
        });
        this.addTypes();
        this.mListView.setAdapter(this.mAdapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mListView.setOnItemClickListener(onItemClickListener);
    }

    private void addTypes() {
        if (mTypeList == null || mTypeList.length == 0)
            throw new IllegalArgumentException("未指定item布局资源值");
        for (int typeId : mTypeList) {
            mAdapter.addType(typeId);
        }
    }

    public void setDate(List<ListItemEx> data) {
        if (data == null)
            throw new IllegalArgumentException("data不能为空");
        synchronized (mData) {
            this.mData = new ArrayList<ListItemEx>(data);
        }
        mHandler.sendEmptyMessage(1);
    }

    public void addData(int type, Object data) {
        mData.add(new ListItemEx(type, data));
        mHandler.sendEmptyMessage(1);
    }

    public void setAdpterListener(IHoverAdpterListener adpterListener) {
        this.mAdpterListener = adpterListener;
    }

    public void setRefreshAndLoadingEnable(boolean refreshEnable, boolean loadingEnable) {
        this.mListView.setRefreshAndLoadingEnable(refreshEnable, loadingEnable);
    }

    public void setOnPullListener(IOnPullListener onPullListener) {
        this.mListView.setOnPullListener(onPullListener);
    }

    public void setRefreshComplete() {
        this.mListView.setRefreshComplete();
    }

    public void setLoadingComplete() {
        this.mListView.setLoadingComplete();
    }

    public void setTypeList(int[] types) {
        this.mTypeList = types;
    }

    public void setHoverResId(int resId) {
        this.mHoverResId = resId;
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    private Boolean mHoverFlag = false;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem != mCurrentPosition) {
            this.mCurrentPosition = firstVisibleItem;
            if (mCurrentPosition > 0) {
                this.mAdpterListener.onHoverChanage(this.mHoverLayout, mData.get(mCurrentPosition - 1), mCurrentPosition, mTypeList[mData.get(mCurrentPosition - 1).getmType()]);
            }
            mHoverFlag = !mHoverFlag;
        }
    }

}
