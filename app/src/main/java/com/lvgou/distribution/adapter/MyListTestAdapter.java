package com.lvgou.distribution.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.ChildItem;
import com.lvgou.distribution.utils.GoodsGridView;
import com.lvgou.distribution.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/10/18.
 */
public class MyListTestAdapter extends BaseAdapter {
    Context context;
    private List<String> groupTitle;
    private Map<Integer, List<ChildItem>> childMap;
    private ArrayList<String> isVISIBLE=new ArrayList<>();
    private Map<Integer, Integer> aaa;

    public MyListTestAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> groupData, Map<Integer, List<ChildItem>> childData, Map<Integer, Integer> aaa) {
        this.groupTitle = groupData;
        this.childMap = childData;
        this.aaa=aaa;
        notifyDataSetChanged();
    }

    public void setIsVISIBLE(ArrayList<String> isVISIBLE) {
        this.isVISIBLE = isVISIBLE;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return groupTitle == null ? 0 : groupTitle.size();
    }

    @Override
    public Object getItem(int position) {
        return groupTitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.actor_tag_item_list);
        TextView tv_group_text = viewHolder.getView(R.id.tv_group_text, TextView.class);
//        RecyclerView recycler_view_child = viewHolder.getView(R.id.recycler_view_child, RecyclerView.class);
//        recycler_view_child.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
//        RecyChildAdapter dynamicMarkAdapter = new RecyChildAdapter(context);
//        dynamicMarkAdapter.setData(childMap.get(position),aaa,position);
//        dynamicMarkAdapter.setIsVISIBLE(isVISIBLE);
//        recycler_view_child.setAdapter(dynamicMarkAdapter);
        tv_group_text.setText("【"+groupTitle.get(position)+"】");
        //------
        GoodsGridView grid_view_child = viewHolder.getView(R.id.grid_view_child, GoodsGridView.class);
        GridChildAdapter gridChildAdapter=new GridChildAdapter(context);
        gridChildAdapter.setData(childMap.get(position), aaa, position);
        gridChildAdapter.setIsVISIBLE(isVISIBLE);
        grid_view_child.setAdapter(gridChildAdapter);
        return viewHolder.convertView;
    }
}
