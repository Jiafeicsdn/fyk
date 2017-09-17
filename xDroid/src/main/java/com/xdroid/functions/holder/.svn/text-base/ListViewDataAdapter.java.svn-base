package com.xdroid.functions.holder;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用viewholder显示Lsitview的item的适配器，继承于ListViewDataAdapterBase，实现了getCount与getItem等函数
 * getView于ListViewDataAdapterBase中实现
 * 
 * @param <ItemDataType> Listview显示的数据类型
 * @author Robin
 */
public class ListViewDataAdapter<ItemDataType> extends ListViewDataAdapterBase<ItemDataType> {

    protected ArrayList<ItemDataType> mItemDataList = new ArrayList<ItemDataType>();

    public ListViewDataAdapter() {

    }

    /**
     * @param viewHolderCreator The view holder creator will create a View Holder that extends {@link ViewHolderBase}
     */
    public ListViewDataAdapter(ViewHolderCreator<ItemDataType> viewHolderCreator) {
        super(viewHolderCreator);
    }

    public ArrayList<ItemDataType> getDataList() {
        return mItemDataList;
    }
    
    public void append(ItemDataType itemDataType){
    	if (itemDataType!=null) {
			mItemDataList.add(itemDataType);
			notifyDataSetChanged();
		}
    }
    
    public void append(List<ItemDataType> itemDataTypes){
    	if (itemDataTypes.size()>0) {
    		for (ItemDataType itemDataType : itemDataTypes) {
    			mItemDataList.add(itemDataType);
			}
    		notifyDataSetChanged();
		}
    }
    
    public void replace(List<ItemDataType> itemDataTypes){
    	mItemDataList.clear();
    	if (itemDataTypes.size()>0) {
    		mItemDataList.addAll(itemDataTypes);
    		notifyDataSetChanged();
		}
    }
    
    public void remove(List<ItemDataType> itemDataList){
    	if (itemDataList.size()>0) {
    		for (ItemDataType itemDataType : itemDataList) {
    			if (mItemDataList.contains(itemDataType)) {
    				mItemDataList.remove(itemDataType);
    			}
    		}
    		notifyDataSetChanged();
		}
    }
    
    public void remove(ItemDataType itemDataType){
    	if (mItemDataList.contains(itemDataType)) {
			mItemDataList.remove(itemDataType);
			notifyDataSetChanged();
		}
    }
    
    public void remove(int position){
    	mItemDataList.remove(position);
    	notifyDataSetChanged();
    }
    
    public void removeAll(){
    	mItemDataList.clear();
    	notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItemDataList.size();
    }

    @Override
    public ItemDataType getItem(int position) {
        if (mItemDataList.size() <= position || position < 0) {
            return null;
        }
        return mItemDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
