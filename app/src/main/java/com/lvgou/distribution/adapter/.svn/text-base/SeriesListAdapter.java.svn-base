package com.lvgou.distribution.adapter;

import android.content.Context;
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
 * Created by Administrator on 2017/4/6.
 */

public class SeriesListAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public SeriesListAdapter(Context _context) {
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
        HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.series_list_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        TextView tv_content = viewHolder.getView(R.id.tv_content, TextView.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        TextView tv_number = viewHolder.getView(R.id.tv_number, TextView.class);
        TextView tv_price = viewHolder.getView(R.id.tv_price, TextView.class);
        if (Integer.parseInt(info.get("CKTuanBi").toString()) == 0) {
            tv_price.setText("免费");
        } else {
            tv_price.setText(Integer.parseInt(info.get("CKTuanBi").toString()) + "币");
        }
        Glide.with(context).load(Url.ROOT + info.get("Banner1").toString()).error(R.mipmap.pictures_no).into(im_picture);
        tv_name.setText(info.get("TeacherName").toString());
        tv_number.setText(info.get("Hits").toString() + "人次");
        tv_content.setText(info.get("Theme").toString());
        return viewHolder.convertView;
    }
}