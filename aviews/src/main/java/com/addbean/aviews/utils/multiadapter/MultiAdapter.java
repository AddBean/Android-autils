package com.addbean.aviews.utils.multiadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MultiAdapter extends BaseAdapter {
    private List<ListItemEx> _mData;
    private LayoutInflater _mInflater;
    private ArrayList<Integer> TypeList = new ArrayList<Integer>();
    private static IItemClickListener _iItemClickListener;
    private IAdpterListener _iAdpterListener;
    private Context context;

    public void addType(int mResource) {
        TypeList.add(mResource);
    }

    public MultiAdapter(Context context, List<ListItemEx> data, IAdpterListener iAdpterListener) {
        _mData = data;
        this.context = context;
        _mInflater = LayoutInflater.from(context);
        _iAdpterListener = iAdpterListener;

    }

//    @Override
//    public void unregisterDataSetObserver(DataSetObserver observer) {
//        if (observer != null) {
//            super.unregisterDataSetObserver(observer);
//        }
//    }

    public void setOnIItemClickListener(IItemClickListener itemClickListener) {
        _iItemClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return _mData.get(position).mType;
    }

    @Override
    public int getViewTypeCount() {
        if (TypeList.size() == 0)
            return 1;
        else
            return TypeList.size();
    }

    public int getCount() {

        return _mData.size();
    }

    public ListItemEx getItem(int position) {

        return _mData.get(position);
        // return null;
    }

    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
//        Log.e("getView", "getView");
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = _mInflater.inflate(TypeList.get(type), null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.SetValue(_mData.get(position), position, (ViewGroup) convertView);// 绑定数据；
        return convertView;
    }

    public interface IAdpterListener {
        public void convert(AdapterHelper helper, ConvertViewInf data);
    }

    public void setAdpterListener(IAdpterListener iAdpterListener) {
        this._iAdpterListener = iAdpterListener;
    }

    /*
     * Holder静态变量，提高效率
     */
    public class ViewHolder {
        View convertView;

        public boolean SetValue(ListItemEx item, final int position, final ViewGroup convertView) {
            if (_iAdpterListener != null) {
                AdapterHelper adapterHelper = new AdapterHelper(context, convertView);
                _iAdpterListener.convert(adapterHelper, new ConvertViewInf(position, item.getmData(), convertView, TypeList.get(item.getmType())));
            }
            return false;
        }

        public View getConvertView() {
            return convertView;
        }

        public void setConvertView(View convertView) {
            this.convertView = convertView;
        }
    }

    /*
     * 单击事件实现
     */
    public class ItemOnClickListener implements OnClickListener {
        public int position;
        public View parentView;

        public ItemOnClickListener(int p, View pv) {
            position = p;
            parentView = pv;
        }

        @Override
        public void onClick(View v) {
            if (_iItemClickListener != null) {
                _iItemClickListener.ItemOnClick(v, parentView, position);
            }
        }
    }

    /*
     * 点击子控件回调接口；
     */
    public interface IItemClickListener {
        public void ItemOnClick(View v, View parentView, int postion);
    }

    public interface ICheckBoxListener {
        public void ItmeOnCheckChange(View v, View parentView, int postion, Boolean status);
    }

    public void setICheckBoxListener(ICheckBoxListener checkBoxListener) {
    }

    public class ConvertViewInf {
        public int position;
        public Object data;
        public ViewGroup view;
        public int layoutId;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public ViewGroup getView() {
            return view;
        }

        public void setView(ViewGroup view) {
            this.view = view;
        }

        public int getLayoutId() {
            return layoutId;
        }

        public void setLayoutId(int layoutId) {
            this.layoutId = layoutId;
        }

        public ConvertViewInf(int position, Object object, ViewGroup view, int layoutId) {
            this.position = position;
            this.data = object;
            this.view = view;
            this.layoutId = layoutId;
        }

    }
}
