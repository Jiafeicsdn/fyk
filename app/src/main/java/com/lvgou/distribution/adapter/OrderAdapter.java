package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.OrderEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.inter.OnPushSpeedClickListener;

import java.util.List;


/**
 * Created by Snow on 2016/3/29 0029.
 * 排序Adapter
 */
public class OrderAdapter extends BaseAdapter {

    private Context context;
    private List<OrderEntity> orderEntityLists;
    private LayoutInflater inflater;
    private static OnPushSpeedClickListener<OrderEntity> onListItemClickListener;

    public OrderAdapter(List<OrderEntity> orderEntityLists, Context context) {
        this.orderEntityLists = orderEntityLists;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderEntityLists.size();
    }

    @Override
    public Object getItem(int position) {
        return orderEntityLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(int arg0) {//删除指定位置的item
        orderEntityLists.remove(arg0);
        this.notifyDataSetChanged();//不要忘记更改适配器对象的数据源
    }

    public void insert(OrderEntity item, int arg0) {//在指定位置插入item
        orderEntityLists.add(arg0, item);
        this.notifyDataSetChanged();
    }

    // 返回所有数据
    public List<OrderEntity> getAllData() {
        return orderEntityLists;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_order, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(orderEntityLists.get(position).getName());
        viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onPushSpeedListener(orderEntityLists.get(position),position);
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView tvTitle;
        TextView tv_delete;
    }

    public static void setOnListItemClickListener(OnPushSpeedClickListener<OrderEntity> onListItemClickListener) {
        OrderAdapter.onListItemClickListener = onListItemClickListener;
    }
}
