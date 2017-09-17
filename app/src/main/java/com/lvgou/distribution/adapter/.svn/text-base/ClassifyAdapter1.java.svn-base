package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/13.
 */

public class ClassifyAdapter1 extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    private int selectorPosition=0;

    public ClassifyAdapter1(Context _context) {
        this.context = _context;
    }

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
//        LiveFragmentAdapter.this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list==null){
            return 0;
        }else {
            return list.size();
        }
    }
    public void setSelectPosition(int position){
        this.selectorPosition=position;
//        ClassifyAdapter1.this.notifyDataSetChanged();
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
        HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.classify_item1);
        TextView tv_content = viewHolder.getView(R.id.tv_content, TextView.class);
        View view_select = viewHolder.getView(R.id.view_select, View.class);
        tv_content.setText(info.get("LabelName").toString());
        tv_content.setTextColor(Color.parseColor("#999999"));
        tv_content.setTextSize(14);
        view_select.setVisibility(View.GONE);
        if (selectorPosition==position){
            tv_content.setTextColor(Color.parseColor("#000000"));
            tv_content.setTextSize(17);
            view_select.setVisibility(View.VISIBLE);
        }
//        Glide.with(context).load(Url.ROOT+info.get("Banner1").toString()).into(im_picture);
        return viewHolder.convertView;
    }
}