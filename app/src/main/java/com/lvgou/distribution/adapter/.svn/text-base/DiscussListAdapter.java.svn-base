package com.lvgou.distribution.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.ViewHolder;
import com.lvgou.distribution.view.RotateTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/23.
 */

public class DiscussListAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public DiscussListAdapter(Context _context) {
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
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.discuss_list_item);
        View view_line_one = viewHolder.getView(R.id.view_line_one, View.class);//头像
        view_line_one.setVisibility(View.VISIBLE);
        /*if (position == 0) {
            view_line_one.setVisibility(View.GONE);
        }*/
        ImageView img_head_pic = viewHolder.getView(R.id.img_head_pic, ImageView.class);//头像
        ImageView img_teacher_label = viewHolder.getView(R.id.img_teacher_label, ImageView.class);//身份
        img_teacher_label.setVisibility(View.GONE);
        TextView txt_user_name = viewHolder.getView(R.id.txt_user_name, TextView.class);//用户名
        ImageView img_sex = viewHolder.getView(R.id.img_sex, ImageView.class);//性别
        img_sex.setVisibility(View.GONE);
        TextView txt_layer = viewHolder.getView(R.id.txt_layer, TextView.class);//第几楼
        txt_layer.setVisibility(View.GONE);
        ImageView img_reply = viewHolder.getView(R.id.img_reply, ImageView.class);//再次评论小图标
        img_reply.setVisibility(View.GONE);
        TextView txt_comment_content = viewHolder.getView(R.id.txt_comment_content, TextView.class);//评论内容
        RotateTextView tv_paid_num = viewHolder.getView(R.id.tv_paid_num, RotateTextView.class);//赞赏的团币
        TextView txt_issue_time = viewHolder.getView(R.id.txt_issue_time, TextView.class);//时间
        LinearLayout layout_comment = viewHolder.getView(R.id.layout_comment, LinearLayout.class);//二级评论容器
        layout_comment.setVisibility(View.GONE);
        txt_user_name.setText(info.get("DistributorName").toString());
//        txt_user_name.setTextColor(Color.parseColor("#d5aa5f"));
        txt_comment_content.setText(info.get("Content").toString());
        Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("DistributorID").toString())).into(img_head_pic);
//        tv_content.setText(info.get());
        if (Integer.parseInt(info.get("TuanBi").toString()) > 0) {
            String tuanbi = String.format(context.getResources().getString(R.string.text_reward_tuanbi), info.get("TuanBi").toString());
            tv_paid_num.setText(tuanbi);
            tv_paid_num.setVisibility(View.VISIBLE);
        } else {
            tv_paid_num.setVisibility(View.GONE);
        }

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
                txt_issue_time.setText("刚刚");
            } else if (minute1 < 60) {
                txt_issue_time.setText(minute1 + "分钟前");
            } else if (hour1 < 24) {
                txt_issue_time.setText(hour1 + "小时前");
            } else if (day1 < 30) {
                txt_issue_time.setText(day1 + "天前");
            } else if (month1<12){
                txt_issue_time.setText(month1 + "月前");
            }else {
                txt_issue_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        img_head_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("distributorid", Integer.parseInt(info.get("DistributorID").toString()));
                Intent intent_one = new Intent(context, UserPersonalCenterActivity.class);
                intent_one.putExtras(bundle);
                context.startActivity(intent_one);
            }
        });
        txt_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("distributorid", Integer.parseInt(info.get("DistributorID").toString()));
                Intent intent_one = new Intent(context, UserPersonalCenterActivity.class);
                intent_one.putExtras(bundle);
                context.startActivity(intent_one);
            }
        });
        return viewHolder.convertView;
    }
}