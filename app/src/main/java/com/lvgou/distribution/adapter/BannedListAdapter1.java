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
import com.lvgou.distribution.view.CircleImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/2/21.
 */

public class BannedListAdapter1 extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    private BannedListAdapter1.BannedAdapterListener bannedAdapterListener;
    public BannedListAdapter1(Context _context) {
        this.context = _context;
    }
    public void setmAdapterListener(BannedListAdapter1.BannedAdapterListener adapterListener) {
        bannedAdapterListener = adapterListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.banned_item_view);
        ImageView img_head_pic = viewHolder.getView(R.id.img_head_pic, CircleImageView.class);
        ImageView img_teacher_label = viewHolder.getView(R.id.img_teacher_label, ImageView.class);
        TextView txt_user_name = viewHolder.getView(R.id.txt_user_name, TextView.class);
        ImageView img_sex = viewHolder.getView(R.id.img_sex, ImageView.class);
        ImageView img_del_banned = viewHolder.getView(R.id.img_del_banned, ImageView.class);
        Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("DistributorID").toString())).into(img_head_pic);
        img_teacher_label.setVisibility(View.VISIBLE);
        if (info.get("UserType").toString().equals("3")) {
            img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
            txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        } else if (info.get("UserType").toString().equals("2")) {
            if (info.get("IsRZ").toString().equals("1")) {
                img_teacher_label.setImageResource(R.mipmap.icon_certified);
                txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
            } else {
                img_teacher_label.setVisibility(View.GONE);
                txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
            }
        } else if (info.get("UserType").toString().equals("1")) {
            img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
            txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        }
        if (info.get("Sex").toString().equals("1")) {
            img_sex.setImageResource(R.mipmap.icon_man);
        } else if (info.get("Sex").toString().equals("2")) {
            img_sex.setImageResource(R.mipmap.icon_woman);
        }
        txt_user_name.setText(info.get("DistributorName").toString());
        img_del_banned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bannedAdapterListener.releaseBanned(info.get("DistributorID").toString());
            }
        });



        return viewHolder.convertView;
    }
    public interface BannedAdapterListener {
        public void releaseBanned(String distributId);
    }
}