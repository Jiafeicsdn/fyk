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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/15.
 */

public class TeacherSearchAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public TeacherSearchAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        Log.e("haskdfha", "---------"+info );
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.teacher_search_list_item);
        ImageView im_header = viewHolder.getView(R.id.im_header, ImageView.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        ImageView img_teacher_label = viewHolder.getView(R.id.img_teacher_label, ImageView.class);
        img_teacher_label.setVisibility(View.VISIBLE);

        if (list.size()!=6){
            tv_name.setVisibility(View.VISIBLE);
            Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("ID").toString())).error(R.mipmap.teacher_default_head).into(im_header);
            tv_name.setText(info.get("RealName").toString());

            if (info.get("UserType").toString().equals("3")) {
                img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
                tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
            } else if (info.get("UserType").toString().equals("2")) {
                /*if (circleDynamicEntities.get(position).getIsRZ() == 1) {
                    viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_certified);
                    viewHolder.txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
                } else {
                    viewHolder.img_teacher_label.setVisibility(View.GONE);
                    viewHolder.txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
                }*/
                img_teacher_label.setVisibility(View.GONE);
                tv_name.setTextColor(Color.parseColor("#7b7b7b"));
            } else if (info.get("UserType").toString().equals("1")) {
                img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
                tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
            }
        }else {
            if (position==list.size()-1){
                im_header.setBackgroundResource(R.mipmap.teacher_search_more);
                tv_name.setVisibility(View.GONE);
                img_teacher_label.setVisibility(View.GONE);
            }else {
                tv_name.setVisibility(View.VISIBLE);
                Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("ID").toString())).error(R.mipmap.teacher_default_head).into(im_header);
                tv_name.setText(info.get("RealName").toString());
                if (info.get("UserType").toString().equals("3")) {
                    img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
                    tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
                } else if (info.get("UserType").toString().equals("2")) {
                /*if (circleDynamicEntities.get(position).getIsRZ() == 1) {
                    viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_certified);
                    viewHolder.txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
                } else {
                    viewHolder.img_teacher_label.setVisibility(View.GONE);
                    viewHolder.txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
                }*/
                    img_teacher_label.setVisibility(View.GONE);
                    tv_name.setTextColor(Color.parseColor("#7b7b7b"));
                } else if (info.get("UserType").toString().equals("1")) {
                    img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
                    tv_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
                }
            }
        }


        return viewHolder.convertView;
    }
}