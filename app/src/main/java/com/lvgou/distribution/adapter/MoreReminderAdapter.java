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
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MoreReminderAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public MoreReminderAdapter(Context _context) {
        this.context = _context;
    }

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list==null){
            return 0;
        }else {
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
        Log.e("kjashdfjkah", "-------"+info );
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.dashang_reminder_item);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        ImageView img_head = viewHolder.getView(R.id.img_head, ImageView.class);
        TextView tv_time = viewHolder.getView(R.id.tv_time, TextView.class);
//        ImageView img_sex = viewHolder.getView(R.id.img_sex, ImageView.class);
        ImageView img_teacher_label = viewHolder.getView(R.id.img_teacher_label, ImageView.class);//导游类型
        img_teacher_label.setVisibility(View.GONE);
        TextView tv_tuanbi_number = viewHolder.getView(R.id.tv_tuanbi_number, TextView.class);
        if (info.get("UserType").toString().equals("1")){
            //官方显示官
            img_teacher_label.setVisibility(View.VISIBLE);
            img_teacher_label.setBackgroundResource(R.mipmap.icon_official_certified);
        }else if (info.get("UserType").toString().equals("2")){
            //就是导游
            /*if (info.get("IsRZ").toString().equals("1")){
                //如果已经认证的导游
                img_teacher_label.setBackgroundResource(R.mipmap.icon_certified);
            }else {
                //没有认证的导游
                img_teacher_label.setVisibility(View.GONE);
            }*/
        }else if (info.get("UserType").toString().equals("2")){
            //代表讲师
            img_teacher_label.setVisibility(View.VISIBLE);
            img_teacher_label.setBackgroundResource(R.mipmap.jiangshi_icon);
        }

        Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("DistributorID").toString())).into(img_head);
        tv_name.setText(info.get("DistributorName").toString());
        tv_tuanbi_number.setText(info.get("TuanBi").toString());

        /*if (info.get("Sex").toString().equals("1")) {
            img_sex.setImageResource(R.mipmap.icon_man);
        } else if (info.get("Sex").toString().equals("2")) {
            img_sex.setImageResource(R.mipmap.icon_woman);
        }*/
        if (info.get("CreateTime") != null && info.get("CreateTime").toString().length() > 0) {
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
                    tv_time.setText("刚刚");
                } else if (minute1 < 60) {
                    tv_time.setText(minute1 + "分钟前");
                } else if (hour1 < 24) {
                    tv_time.setText(hour1 + "小时前");
                } else if (day1 < 30) {
                    tv_time.setText(day1 + "天前");
                } else if (month1<12){
                    tv_time.setText(month1 + "月前");
                }else {
                    tv_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }






        return viewHolder.convertView;
    }
}
