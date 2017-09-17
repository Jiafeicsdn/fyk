package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/13.
 */

public class TeacherCourseAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public TeacherCourseAdapter(Context _context) {
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
        final HashMap<String, Object> info = list.get(position);
//        State=课程状态(0=审核中，1=报名中，2=审核不通过，3=进行中，4=已结束,5=停用 6=录制中)
        ViewHolder viewHolder = null;
        Log.e("kjsaghdfjhs", "-----------"+info);
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.teacher_course_item);//live_text_orange_bg  FEA41B
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);//图片
        TextView tv_title = viewHolder.getView(R.id.tv_title, TextView.class);//标题
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);//讲师名字
        TextView tv_money = viewHolder.getView(R.id.tv_money, TextView.class);//付币
        TextView tv_state = viewHolder.getView(R.id.tv_state, TextView.class);//状态
        RelativeLayout rl_renci = viewHolder.getView(R.id.rl_renci, RelativeLayout.class);//人次
        TextView tv_renci = viewHolder.getView(R.id.tv_renci, TextView.class);//人次
        tv_title.setText(info.get("Theme").toString());
        tv_name.setText(info.get("TeacherName").toString());
        String state = info.get("State").toString();
        String type = info.get("Type").toString();
        rl_renci.setVisibility(View.VISIBLE);
        tv_state.setVisibility(View.GONE);
        if (state.equals("3")) {
            //直播中
            tv_state.setVisibility(View.VISIBLE);
            rl_renci.setVisibility(View.GONE);
            tv_state.setText("直播中");
            tv_state.setTextColor(Color.parseColor("#71C713"));
            tv_state.setBackgroundResource(R.drawable.live_text_green_bg);
        } else if (state.equals("1") ) {
            if (type.equals("2")){
                tv_state.setVisibility(View.VISIBLE);
                rl_renci.setVisibility(View.GONE);
                tv_state.setText("众筹中");
                tv_state.setTextColor(Color.parseColor("#FEA41B"));
                tv_state.setBackgroundResource(R.drawable.live_text_orange_bg);
            }else {
                tv_state.setVisibility(View.VISIBLE);
                rl_renci.setVisibility(View.GONE);
                tv_state.setText("报名中");
                tv_state.setTextColor(Color.parseColor("#FEA41B"));
                tv_state.setBackgroundResource(R.drawable.live_text_orange_bg);
            }
        }  else if (state.equals("4") || type.equals("2")) {
            rl_renci.setVisibility(View.VISIBLE);
            tv_state.setVisibility(View.GONE);
            tv_renci.setText(info.get("Hits").toString() + "人次");
        }else if (state.equals("6")){
            //录制中
            rl_renci.setVisibility(View.VISIBLE);
            tv_state.setVisibility(View.GONE);
            tv_renci.setText(info.get("Hits").toString() + "人次");
        }
        if (state.equals("1") || state.equals("3")) {
            if (info.get("BMTuanBi").toString().equals("0")) {
                tv_money.setText("免费");
            } else {
                tv_money.setText(info.get("BMTuanBi").toString() + "币");
            }

        } else  if (state.equals("4") || state.equals("6")){
            if (info.get("CKTuanBi").toString().equals("0")) {
                tv_money.setText("免费");
            } else {
                tv_money.setText(info.get("CKTuanBi").toString() + "币");
            }
        }

        Glide.with(context).load(Url.ROOT + info.get("Banner1").toString()).error(R.mipmap.pictures_no).into(im_picture);


        return viewHolder.convertView;
    }
}