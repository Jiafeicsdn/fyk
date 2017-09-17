/**
 * 此包下为Listview的viewholde与Adapterr封装
 * @author Robin
 * time 2015-03-22 22:04:46
 *
 */
package com.xdroid.functions.holder;


/*
 *使用方法：
 	    ListViewDataAdapter<String> adapter=new ListViewDataAdapter<>();  //创建Adapter
		adapter.setViewHolderClass(this, TestViewHolder.class);  //设置viewholder
		lv.setAdapter(adapter);  //为Listview设置Adapter
		
		adapter.getDataList().addAll(getData()); //向Adapter添加数据 ,这是完整数据集合添加方式，若要动态添加,使用 	adapter.getDataList().add("条目"+i);
		adapter.notifyDataSetChanged(); //通知Adapter更新
		
		 上面两行是添加数据，也可以调用
		 append(ItemDataType itemDataType);   //动态添加每条数据
		 append(List<ItemDataType> itemDataTypes); 动态添加整个集合
		 replace(List<ItemDataType> itemDataTypes); 动态替换整个集合
		 
		
		public List<String> getData(){  //获取Listview数据
		List<String> strings=new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			strings.add("条目"+i);
		}
		return strings;
	}
		
		//Listview的viewholder
		public class TestViewHolder extends ViewHolderBase<String> {
		TextView textView;
		@Override
		public View createView(LayoutInflater layoutInflater) {
			textView=new TextView(MainActivity.this);
			
			return textView;
		}

		@Override
		public void showData(int position, String itemData) {
			textView.setText(itemData);
			
		}

	}
	

 
 */