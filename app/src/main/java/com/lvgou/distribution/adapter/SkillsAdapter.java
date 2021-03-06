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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/13.
 */

public class SkillsAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public SkillsAdapter(Context _context) {
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
            /*if (list.size() > 2) {
                return 2;
            } else {
                return list.size();
            }*/
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
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.skills_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        TextView tv_content = viewHolder.getView(R.id.tv_content, TextView.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        ImageView im_default = viewHolder.getView(R.id.im_default, ImageView.class);
        TextView tv_number = viewHolder.getView(R.id.tv_number, TextView.class);
        TextView tv_price = viewHolder.getView(R.id.tv_price, TextView.class);
        TextView tv_state = viewHolder.getView(R.id.tv_state, TextView.class);
        Glide.with(context).load(Url.ROOT + info.get("Banner1").toString()).error(R.mipmap.pictures_no).into(im_picture);
        tv_name.setText(info.get("TeacherName").toString());
        tv_content.setText(info.get("Theme").toString());
        String state = info.get("State").toString();
        String bmTuanBi = info.get("BMTuanBi").toString();
        String people_Apply = info.get("People_Apply").toString();
        String People_Min = info.get("People_Min").toString();
        String cKTuanBi = info.get("CKTuanBi").toString();
        String hits = info.get("Hits").toString();
        String type = info.get("Type").toString();
        tv_number.setVisibility(View.VISIBLE);
        im_default.setVisibility(View.VISIBLE);
        tv_state.setVisibility(View.GONE);
        //State=课程状态(0=审核中，1=报名中，2=审核不通过，3=进行中，4=已结束,5=停用 6=录制中)
        if (state.equals("1")){
           if (type.equals("2")){
                //众筹中
                tv_state.setVisibility(View.VISIBLE);
                tv_number.setVisibility(View.GONE);
                im_default.setVisibility(View.GONE);
                tv_state.setText("众筹中");
                tv_state.setTextColor(Color.parseColor("#FEA41B"));
                tv_state.setBackgroundResource(R.drawable.live_text_orange_bg);
                tv_number.setText(people_Apply + "/"+People_Min+"人起");
            }else{
                tv_state.setVisibility(View.VISIBLE);
                tv_number.setVisibility(View.GONE);
                im_default.setVisibility(View.GONE);
                tv_state.setText("报名中");
                tv_state.setTextColor(Color.parseColor("#FEA41B"));
                tv_state.setBackgroundResource(R.drawable.live_text_orange_bg);
                tv_number.setText(people_Apply + "人报名");
            }
            if (bmTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(bmTuanBi + "币");
            }
        }else if (state.equals("3")){
            tv_state.setVisibility(View.VISIBLE);
            tv_number.setVisibility(View.GONE);
            im_default.setVisibility(View.GONE);
            tv_state.setText("直播中");
            tv_state.setTextColor(Color.parseColor("#71C713"));
            tv_state.setBackgroundResource(R.drawable.live_text_green_bg);
            if (bmTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(bmTuanBi + "币");
            }
        }else if (state.equals("6")){
            tv_state.setVisibility(View.VISIBLE);
            tv_number.setVisibility(View.GONE);
            im_default.setVisibility(View.GONE);
            tv_state.setText("录制中");
            tv_state.setTextColor(Color.parseColor("#ff0000"));
            tv_state.setBackgroundResource(R.drawable.live_text_red_norad_bg);
            tv_number.setText(hits + "人次");
            if (cKTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(cKTuanBi + "币");
            }
        }else if (state.equals("4")){
            //已结束
            tv_state.setVisibility(View.GONE);
            im_default.setVisibility(View.VISIBLE);
            tv_number.setVisibility(View.VISIBLE);
            tv_number.setText(hits + "人次");
            if (cKTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(cKTuanBi + "币");
            }
        }
        return viewHolder.convertView;
    }
}