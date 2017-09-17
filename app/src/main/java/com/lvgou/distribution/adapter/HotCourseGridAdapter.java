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
 * Created by Administrator on 2017/3/14.
 */

public class HotCourseGridAdapter extends BaseAdapter {
    //上下文对象
    private Context context;
    private ArrayList<HashMap<String, Object>> list;

    public HotCourseGridAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
    }

    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    public Object getItem(int item) {
        return item;
    }

    public long getItemId(int id) {
        return id;
    }

    //创建View方法
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.hot_course_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        TextView tv_content = viewHolder.getView(R.id.tv_content, TextView.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        TextView tv_renshu = viewHolder.getView(R.id.tv_renshu, TextView.class);
        TextView tv_count = viewHolder.getView(R.id.tv_count, TextView.class);
        tv_content.setText(info.get("Theme").toString());
        tv_name.setText(info.get("TeacherName").toString());
        Glide.with(context).load(Url.ROOT + info.get("Banner1").toString()).error(R.mipmap.pictures_no).into(im_picture);
        String state = info.get("State").toString();
        String bmTuanBi = info.get("BMTuanBi").toString();
        String cKTuanBi = info.get("CKTuanBi").toString();
        String people_Apply = info.get("People_Apply").toString();
        String hits = info.get("Hits").toString();
        if (state.equals("1") || state.equals("3")) {
            if (bmTuanBi.equals("0")) {
                tv_count.setText("免费");
            } else {
                tv_count.setText(bmTuanBi + "币");
            }
            tv_renshu.setText(people_Apply + "人次");
        } else if (state.equals("2") || state.equals("4")) {
            if (cKTuanBi.equals("0")) {
                tv_count.setText("免费");
            } else {
                tv_count.setText(cKTuanBi + "币");
            }
            tv_renshu.setText(hits + "人次");
        }

        return viewHolder.convertView;
    }
}