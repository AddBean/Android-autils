package com.addbean.aviews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.addbean.aviews.R;


/**
 * Created by AddBean on 2016/3/1.
 */
public class ACheckBox extends ImageView {
    private Context mContext;
    private int mSelectedId;
    private int mUnselectedId;
    private boolean mIsSelected;

    public ACheckBox(Context context) {
        this(context, null);
    }

    public ACheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(attrs);
    }

    public ACheckBox(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    private void initView(AttributeSet attrs) {
        getParams(attrs);
        initSubView();
    }


    public boolean isChecked() {
        return this.mIsSelected;
    }

    public void setChecked(boolean isSelect) {
        if (isSelect != mIsSelected && mOnSelectChangedListener != null) {
            mOnSelectChangedListener.onStateChange(isSelect);
        }
        this.mIsSelected = isSelect;
        initSubView();
    }


    private void initSubView() {
        this.setImageResource(mIsSelected ? mSelectedId : mUnselectedId);
    }

    private void getParams(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.ACheckBox, 0, 0);
        if (a != null) {
            int count = a.getIndexCount();
            for (int i = 0; i < count; ++i) {
                int id = a.getIndex(i);
                if (id == R.styleable.ACheckBox_image_selected) {
                    this.mSelectedId = a.getResourceId(id, R.drawable.arr_down);
                }
                if (id == R.styleable.ACheckBox_image_unselected) {
                    this.mUnselectedId = a.getResourceId(id, R.drawable.arr_up);
                }
                if (id == R.styleable.ACheckBox_select_state) {
                    this.mIsSelected = a.getBoolean(id, false);
                }
            }
        }
        a.recycle();
    }

    private OnSelectChangedListener mOnSelectChangedListener;

    public void setOnSlectChangeListener(OnSelectChangedListener onSelectChangedListener) {
        this.mOnSelectChangedListener = onSelectChangedListener;
    }

    public interface OnSelectChangedListener {
        public void onStateChange(boolean isSelected);
    }
}
