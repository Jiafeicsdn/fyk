package com.lvgou.distribution.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/31.
 */
public class ViewHolder {
    public View convertView;
    public static ViewHolder getVH(View convertView, Context content, int layout)
    {
        if(convertView==null) {

            convertView = View.inflate(content, layout, null);
        }

        ViewHolder holder = new ViewHolder(convertView);

        return  holder;
    }
    //     ImageView singlenews_iv;
//     TextView   title_tv;
//     TextView   time_tv;
    public Map<Integer,View> maps = new HashMap<Integer, View>();
    public ViewHolder(View convertView)
    {
        this.convertView = convertView;

    }
    public <T extends  View> T getView(int id)
    {
        if(maps.get(id)==null)
        {
            maps.put(id,convertView.findViewById(id));
        }
        return (T)maps.get(id);

    }
    public <T extends  View> T getView(int id,Class<T> clazz)
    {
        return getView(id);
    }
    public TextView getTv(int id)
    {
        return getView(id,TextView.class);
    }
    public ImageView getIv(int id)
    {
        return getView(id,ImageView.class);
    }
}
