package com.addbean.aviews.utils.multiadapter;


public class ListItemEx 
{
	public int mType;
	public Object mData ;
	
	public ListItemEx(int type, Object data)
	{
		mType = type;
		mData = data;
	}

	public int getmType() {
		return mType;
	}

	public void setmType(int mType) {
		this.mType = mType;
	}

	public Object getmData() {
		return mData;
	}

	public void setmData(Object mData) {
		this.mData = mData;
	}
	
}
