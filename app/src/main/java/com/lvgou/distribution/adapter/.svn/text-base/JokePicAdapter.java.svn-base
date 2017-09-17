package com.lvgou.distribution.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/31.
 */

public class JokePicAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public JokePicAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.joke_pic_list_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)info.get("with"), (int)info.get("hight"));
        im_picture.setLayoutParams(layoutParams);
        Glide.with(context).load(Url.ROOT + info.get("picUrl")).into(im_picture);
        return viewHolder.convertView;
    }
}