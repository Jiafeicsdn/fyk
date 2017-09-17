package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.utils.ViewHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/29.
 */

public class UseVoucherAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public UseVoucherAdapter(Context _context) {
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
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.use_fragment_item);
        TextView tv_price = viewHolder.getView(R.id.tv_price, TextView.class);
        TextView tv_title = viewHolder.getView(R.id.tv_title, TextView.class);
        TextView tv_limit_time = viewHolder.getView(R.id.tv_limit_time, TextView.class);
        ImageView im_isused = viewHolder.getView(R.id.im_isused, ImageView.class);
        tv_price.setText(info.get("TuanBi").toString());
        //Type:听课卷类型：1=通用卷（针对某一个课程报名+查看）、2=报名卷（针对某一个课程报名）、
        // 3=查看卷（针对某一个课程查看）、4=全局卷（所有课程报名+查看）
        tv_title.setText(info.get("LectureName").toString());
       /* if (info.get("Type").toString().equals("1")) {
            //通用券
            tv_title.setText("通用券");
        } else if (info.get("Type").toString().equals("2")) {
            //报名券
            tv_title.setText("报名券");
        } else if (info.get("Type").toString().equals("3")) {
            //查看券
            tv_title.setText("查看券");
        } else if (info.get("Type").toString().equals("4")) {
            //全局券
            tv_title.setText("全局券");
        }*/
        //有效期:自领取日30日内
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (info.get("EndTime").toString().contains("1900-01-01")) {
            tv_limit_time.setText("有效期：无限制使用");
        } else {
           /* try {
                Date d1 = df.parse(info.get("CreateTime").toString().replace("T", " "));//创建时间
                Date d2 = df.parse(info.get("CreateTime").toString().replace("T", " "));//结束时间
                long diff = d1.getTime() - d2.getTime();
                long days = diff / (1000 * 60 * 60 * 24);
                tv_limit_time.setText("有效期：自领取日" + days + "日");
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            String[] split = info.get("EndTime").toString().split("T");
            tv_limit_time.setText("有效期："+split[0]);
        }
        if (info.get("IsUsed").toString().equals("1")){
            //已使用
            im_isused.setBackgroundResource(R.mipmap.isused_icon);
        }else if (info.get("IsUsed").toString().equals("2")){
            //已过期
            im_isused.setBackgroundResource(R.mipmap.overdue_icon);
        }
        return viewHolder.convertView;
    }
}