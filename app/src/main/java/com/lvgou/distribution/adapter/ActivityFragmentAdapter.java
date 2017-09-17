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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/9.
 */

public class ActivityFragmentAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public ActivityFragmentAdapter(Context _context) {
        this.context = _context;
    }

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
        Log.e("ksjhdfjs", "-----------"+list );
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
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.activity_fragment_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        TextView tv_content = viewHolder.getView(R.id.tv_content, TextView.class);
        TextView tv_data = viewHolder.getView(R.id.tv_data, TextView.class);
        TextView tv_place = viewHolder.getView(R.id.tv_place, TextView.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        TextView tv_state = viewHolder.getView(R.id.tv_state, TextView.class);
        Glide.with(context).load(Url.ROOT + info.get("PicUrl").toString()).into(im_picture);
        tv_content.setText(info.get("Title").toString());
//        tv_data.setText(info.get("").toString());
        tv_place.setText(info.get("CountryPath").toString());
        tv_name.setText("组织者："+info.get("DistributorName").toString());
        String startTime = info.get("StartTime").toString().replace("T", " ");
        if (info.get("State").toString().equals("1")) {
            //进行中

            try {
                Date nowData = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startData = sdf.parse(startTime);
                boolean boo = compareDate(nowData, startData);
                if (boo) {
                    //当前时间大
                    tv_state.setText("进行中");
                    tv_state.setTextColor(Color.parseColor("#6cc50b"));
                } else {
                    //当前时间小
                    tv_state.setText("报名中");
                    tv_state.setTextColor(Color.parseColor("#fc4d30"));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else if (info.get("State").toString().equals("2")) {
            //报名中
            tv_state.setText("已结束");
            tv_state.setTextColor(Color.parseColor("#777777"));
        }
        String[] split = info.get("StartTime").toString().split("T");
        tv_data.setText(split[0]);
        return viewHolder.convertView;
    }

    /**
     * 比较两个日期之间的大小
     *
     * @param d1
     * @param d2
     * @return 前者大于后者返回true 反之false
     */
    public boolean compareDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        int result = c1.compareTo(c2);
        if (result >= 0)
            return true;
        else
            return false;
    }
}