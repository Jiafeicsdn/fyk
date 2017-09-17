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
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.ViewHolder;
import com.lvgou.distribution.view.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/16.
 */

public class TeacherResultAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public TeacherResultAdapter(Context _context) {
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
        Log.e("haskfdasdf", "----------" + info);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.teahcer_item_news);
        ImageView image = viewHolder.getView(R.id.image, ImageView.class);//头像
        ImageView img_user_identify = viewHolder.getView(R.id.img_user_identify, ImageView.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        ImageView img_sex = viewHolder.getView(R.id.img_sex, ImageView.class);
        TextView tv_fengwen_num = viewHolder.getView(R.id.tv_fengwen_num, TextView.class);
        TextView tv_fans_num = viewHolder.getView(R.id.tv_fans_num, TextView.class);
        Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("ID").toString())).error(R.mipmap.teacher_default_head).into(image);
        tv_name.setText(info.get("RealName").toString());
        tv_fengwen_num.setText(info.get("TuanBi").toString());
        tv_fans_num.setText(info.get("Ratio").toString());
        if (info.get("Attr").toString().equals("1")) {
            img_sex.setBackgroundResource(R.mipmap.icon_man);
        } else {
            img_sex.setBackgroundResource(R.mipmap.icon_woman);
        }
        if (info.get("UserType").toString().equals("3")) {
            img_user_identify.setImageResource(R.mipmap.bg_tecaher_authentication);
            tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        } else if (info.get("UserType").toString().equals("2")) {
            if (info.get("State").toString().equals("5")) {
                img_user_identify.setImageResource(R.mipmap.icon_certified);
                tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
            } else {
                img_user_identify.setVisibility(View.GONE);
                tv_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
            }
        } else if (info.get("UserType").toString().equals("1")) {
            img_user_identify.setImageResource(R.mipmap.icon_official_certified);
            tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        }

        return viewHolder.convertView;
    }

}