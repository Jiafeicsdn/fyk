package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.lvgou.distribution.entity.SeaoutClassifyEntity;
import com.lvgou.distribution.view.MyGridView;
import com.xdroid.functions.holder.ListViewDataAdapter;

import java.util.List;



/**
 * Created by Snow on 2016/3/30 0030.
 * 海外 分类适配器
 */
public class ClassfiyExpandAdapter extends BaseExpandableListAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<SeaoutClassifyEntity> seaoutLists;// 父层数据
    private List<SeaoutClassifyEntity> objects; // 子数据

    public ClassfiyExpandAdapter(Context context, List<SeaoutClassifyEntity> seaoutLists, List<SeaoutClassifyEntity> objects) {
        this.context = context;
        this.seaoutLists = seaoutLists;
        this.objects = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return seaoutLists.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return objects.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return seaoutLists.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return objects.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // 要想实现Child点击事件，必须设为true
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        ListViewDataAdapter<SeaoutClassifyEntity> orderEntityListViewDataAdapter;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = inflater.inflate(R.layout.item_parent_seaout, null);
//            viewHolder.gridViewParent = (MyGridView) convertView.findViewById(R.id.grid_view_praent);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        orderEntityListViewDataAdapter = new ListViewDataAdapter<SeaoutClassifyEntity>();
//        // 赋值操作
//        for (int i = 0; i < seaoutLists.size(); i++) {
//            SeaoutClassifyEntity seaoutClassifyEntity = seaoutLists.get(i);
//            orderEntityListViewDataAdapter.append(seaoutClassifyEntity);
//        }
//        orderEntityListViewDataAdapter.setViewHolderClass(context, ClassifySeaoutViewHolder.class);
//        viewHolder.gridViewParent.setAdapter(orderEntityListViewDataAdapter);

        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        ViewChildHolder viewChildHolder;
////        ListViewDataAdapter<SeaoutClassifyEntity> orderEntityChildListViewDataAdapter;
//        if (convertView == null) {
//            viewChildHolder = new ViewChildHolder();
//            convertView = inflater.inflate(R.layout.item_child_seaout, null);
//            viewChildHolder.gridViewChild = (MyGridView) convertView.findViewById(R.id.grid_view_child);
//            convertView.setTag(viewChildHolder);
//        } else {
//            viewChildHolder = (ViewChildHolder) convertView.getTag();
//        }
//        // 赋值操作
//        orderEntityChildListViewDataAdapter = new ListViewDataAdapter<SeaoutClassifyEntity>();
//        for (int i = 0; i < objects.size(); i++) {
//            SeaoutClassifyEntity seaoutClassifyEntity = objects.get(i);
//            orderEntityChildListViewDataAdapter.append(seaoutClassifyEntity);
//        }
//        orderEntityChildListViewDataAdapter.setViewHolderClass(context, ClassifySeaoutViewHolder.class);
//        viewChildHolder.gridViewChild.setAdapter(orderEntityChildListViewDataAdapter);
        return null;
    }

    public static class ViewHolder {
        MyGridView gridViewParent;
    }

    public static class ViewChildHolder {
        MyGridView gridViewChild;
    }

}
