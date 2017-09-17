package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.TaskDetialEntity;

import java.util.List;

/**
 * Created by Snow on 2016/5/31 0031.
 */
public class ExchangeRecordAdapter extends BaseAdapter {

    private Context context;
    private List<TaskDetialEntity> orderEntityLists;
    private LayoutInflater inflater;


    public ExchangeRecordAdapter(Context context, List<TaskDetialEntity> orderEntityLists) {
        this.context = context;
        this.orderEntityLists = orderEntityLists;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_exchange_record, null);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_points = (TextView) convertView.findViewById(R.id.tv_points);
            viewHolder.view_point = (View) convertView.findViewById(R.id.view_circle);
            viewHolder.rl_red_circle = (RelativeLayout) convertView.findViewById(R.id.rl_red_circle);
            viewHolder.rl_gray_circle = (RelativeLayout) convertView.findViewById(R.id.rl_gray_circle);
            viewHolder.view_last = (View) convertView.findViewById(R.id.view_last);
            viewHolder.view_top=(View)convertView.findViewById(R.id.view_top);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            viewHolder.rl_red_circle.setVisibility(View.VISIBLE);
            viewHolder.view_point.setVisibility(View.GONE);
            viewHolder.rl_gray_circle.setVisibility(View.GONE);
            viewHolder.view_top.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.rl_red_circle.setVisibility(View.GONE);
            viewHolder.view_point.setVisibility(View.VISIBLE);
            viewHolder.rl_gray_circle.setVisibility(View.GONE);
            viewHolder.view_top.setVisibility(View.VISIBLE);
        }

        if (position == orderEntityLists.size() - 1){
            viewHolder.view_last.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.view_last.setVisibility(View.VISIBLE);
        }
        viewHolder.tv_name.setText(orderEntityLists.get(position).getName());
        viewHolder.tv_time.setText(orderEntityLists.get(position).getTiem().split("T")[0]);
        viewHolder.tv_points.setText("-" + orderEntityLists.get(position).getPotions());
        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_time;
        TextView tv_points;
        View view_point;
        RelativeLayout rl_red_circle;
        RelativeLayout rl_gray_circle;
        View view_last;
        View view_top;
    }
}
