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
 * Created by Administrator on 2017/3/14.
 */

public class ClassifyListAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public ClassifyListAdapter(Context _context) {
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
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.classify_list2_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        ImageView im_renshu = viewHolder.getView(R.id.im_renshu, ImageView.class);
        TextView tv_content = viewHolder.getView(R.id.tv_content, TextView.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        TextView tv_renshu = viewHolder.getView(R.id.tv_renshu, TextView.class);
        TextView tv_count = viewHolder.getView(R.id.tv_count, TextView.class);
        ImageView im_state = viewHolder.getView(R.id.im_state, ImageView.class);
        Glide.with(context).load(Url.ROOT + info.get("Banner1").toString()).into(im_picture);
        tv_name.setText(info.get("TeacherName").toString());
        tv_content.setText(info.get("Theme").toString());
        String state = info.get("State").toString();
        String bmTuanBi = info.get("BMTuanBi").toString();
        String people_Apply = info.get("People_Apply").toString();
        String People_Min = info.get("People_Min").toString();
        String cKTuanBi = info.get("CKTuanBi").toString();
        String hits = info.get("Hits").toString();
        String type = info.get("Type").toString();
        im_renshu.setBackgroundResource(R.mipmap.live_renshu_icon);
        if (state.equals("3")) {
            //直播中
            im_state.setVisibility(View.VISIBLE);
            im_state.setBackgroundResource(R.mipmap.classify_zhiboz);
            if (type.equals("2")){
                //众筹中
                tv_renshu.setText(people_Apply + "/"+People_Min+"人起");
            }else {
                //进行中
                tv_renshu.setText(people_Apply + "人报名");
            }
            if (bmTuanBi.equals("0")) {
                tv_count.setText("免费");
            } else {
                tv_count.setText(bmTuanBi + "币");
            }
        } else if (state.equals("1")) {
            if (type.equals("2")){
                //众筹中
                im_state.setVisibility(View.VISIBLE);
                im_state.setBackgroundResource(R.mipmap.classify_zhongchouz);
                tv_renshu.setText(people_Apply + "/"+People_Min+"人起");
            }else {
                //报名中
                im_state.setVisibility(View.VISIBLE);
                im_state.setBackgroundResource(R.mipmap.classify_baoming);
                tv_renshu.setText(people_Apply + "人报名");
            }
            if (bmTuanBi.equals("0")) {
                tv_count.setText("免费");
            } else {
                tv_count.setText(bmTuanBi + "币");
            }

        }  else if (state.equals("6")) {
            //录制中
            im_state.setVisibility(View.VISIBLE);
            im_state.setBackgroundResource(R.mipmap.classify_luzhi);
            if (bmTuanBi.equals("0")) {
                tv_count.setText("免费");
            } else {
                tv_count.setText(bmTuanBi + "币");
            }
            im_renshu.setBackgroundResource(R.mipmap.bg_right_default);
            tv_renshu.setText(hits + "人次");
        }else if (state.equals("4") || state.equals("2")) {
            im_state.setVisibility(View.GONE);
            if (cKTuanBi.equals("0")) {
                tv_count.setText("免费");
            } else {
                tv_count.setText(cKTuanBi + "币");
            }
            im_renshu.setBackgroundResource(R.mipmap.bg_right_default);
            tv_renshu.setText(hits + "人次");
        }


        return viewHolder.convertView;
    }
}