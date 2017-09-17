package com.lvgou.distribution.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/28.
 */

public class RecommendRecordAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public RecommendRecordAdapter(Context _context) {
        this.context = _context;
    }

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
//        LiveFragmentAdapter.this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.recommend_record_item);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        TextView tv_time = viewHolder.getView(R.id.tv_time, TextView.class);
        TextView tv_state = viewHolder.getView(R.id.tv_state, TextView.class);
        tv_name.setText(info.get("TeacherName").toString());
        String[] split = info.get("CreateTime").toString().split("T");
        tv_time.setText("推荐时间："+split[0]);
        String state = info.get("State").toString();
        if (state.equals("1")) {
            //审核中
            tv_state.setText("待审核");
            tv_state.setTextColor(Color.parseColor("#d5aa5f"));
        } else if (state.equals("2")) {
            //推荐成功
            tv_state.setText("推荐成功");
            tv_state.setTextColor(Color.parseColor("#999999"));
        } else if (state.equals("3")) {
            //推荐不成功
            tv_state.setText("推荐失败");
            tv_state.setTextColor(Color.parseColor("#fc4d30"));
        }
        return viewHolder.convertView;
    }
}