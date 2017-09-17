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
import com.lvgou.distribution.emoji.ParseEmojiMsgUtil;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/24.
 */

public class CollecteDynamicAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public CollecteDynamicAdapter(Context _context) {
        this.context = _context;
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
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.apply_collection_dynamic);
        ImageView image = viewHolder.getView(R.id.image, ImageView.class);
        TextView author = viewHolder.getView(R.id.author, TextView.class);
        TextView dtime = viewHolder.getView(R.id.dtime, TextView.class);
        TextView summary = viewHolder.getView(R.id.summary, TextView.class);
        ImageView img_teacher_label=viewHolder.getView(R.id.img_teacher_label,ImageView.class);
        Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("FengwenDistributorID").toString())).error(R.mipmap.teacher_default_head).into(image);
        author.setText(info.get("FengwenDistributorName").toString());
        if(info.get("FengwenUserType").toString().equals("1")){
            author.setTextColor(0xFFFF9900);
            img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
        }else if(info.get("FengwenUserType").toString().equals("3")){
            author.setTextColor(0xFFFF9900);
            img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
        }else{
            img_teacher_label.setVisibility(View.GONE);
        }
        ParseEmojiMsgUtil.getExpressionString(context, info.get("ID").toString(), summary, info.get("FengwenTitle").toString());
        String[] str = info.get("CreateTime").toString().split("T");
        //2016-04-22 16:32:50
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_e = dfs.format(new Date());
        String date_b = str[0] + " " + str[1];
        try {
            Date begin = dfs.parse(date_b);
            Date end = dfs.parse(date_e);
            long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
            long month1= between / (30*24 * 3600);
            long day1 = between / (24 * 3600);
            long hour1 = between / 3600;
            long minute1 = between / 60;
            if (minute1 == 0) {
                dtime.setText("刚刚");
            } else if (minute1 < 60) {
                dtime.setText(minute1 + "分钟前");
            } else if (hour1 < 24) {
                dtime.setText(hour1 + "小时前");
            } else if (day1 < 30) {
                dtime.setText(day1 + "天前");
            } else if (month1<12){
                dtime.setText(month1 + "月前");
            }else {
                dtime.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return viewHolder.convertView;
    }
}