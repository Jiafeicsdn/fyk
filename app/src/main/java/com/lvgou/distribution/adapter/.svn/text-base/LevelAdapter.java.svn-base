package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/1.
 */

public class LevelAdapter extends BaseAdapter {
    Context context;
    String mylevel = "0";
    ArrayList<String> list = new ArrayList<>();

    public LevelAdapter(Context context, String mylevel, ArrayList<String> list) {
        this.context = context;
        if (!mylevel.equals("")) {
            this.mylevel = mylevel;
        }
        this.mylevel = mylevel;
        this.list = list;
    }


    @Override
    public int getCount() {
        return 10;

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.level_list_item);
        ImageView im_level = viewHolder.getView(R.id.im_level, ImageView.class);
        RelativeLayout rl_one = viewHolder.getView(R.id.rl_one, RelativeLayout.class);
        RelativeLayout rl_two = viewHolder.getView(R.id.rl_two, RelativeLayout.class);
        RelativeLayout rl_three = viewHolder.getView(R.id.rl_three, RelativeLayout.class);
        RelativeLayout rl_four = viewHolder.getView(R.id.rl_four, RelativeLayout.class);
        RelativeLayout rl_five = viewHolder.getView(R.id.rl_five, RelativeLayout.class);
//        ImageView one_point = viewHolder.getView(R.id.one_point, ImageView.class);
//        ImageView two_point = viewHolder.getView(R.id.two_point, ImageView.class);
//        ImageView three_point = viewHolder.getView(R.id.three_point, ImageView.class);
//        ImageView four_point = viewHolder.getView(R.id.four_point, ImageView.class);
//        ImageView five_point = viewHolder.getView(R.id.five_point, ImageView.class);
        TextView tv_onetext = viewHolder.getView(R.id.tv_onetext, TextView.class);
        TextView tv_twotext = viewHolder.getView(R.id.tv_twotext, TextView.class);
        TextView tv_threetext = viewHolder.getView(R.id.tv_threetext, TextView.class);
        TextView tv_fourtext = viewHolder.getView(R.id.tv_fourtext, TextView.class);
        TextView tv_fivetext = viewHolder.getView(R.id.tv_fivetext, TextView.class);
        /*one_point.setVisibility(View.GONE);
        two_point.setVisibility(View.GONE);
        three_point.setVisibility(View.GONE);
        four_point.setVisibility(View.GONE);
        five_point.setVisibility(View.GONE);*/
        tv_onetext.setTextColor(Color.parseColor("#cfcfcf"));
        tv_twotext.setTextColor(Color.parseColor("#cfcfcf"));
        tv_threetext.setTextColor(Color.parseColor("#cfcfcf"));
        tv_fourtext.setTextColor(Color.parseColor("#cfcfcf"));
        tv_fivetext.setTextColor(Color.parseColor("#cfcfcf"));
        if (info.equals("0")) {
            if (Integer.parseInt(mylevel) >(Integer.parseInt(info))){
                im_level.setBackgroundResource(R.mipmap.onelevel_light_icon);
            }else {
                im_level.setBackgroundResource(R.mipmap.onelevel_normal_icon);
            }

            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.GONE);
            rl_three.setVisibility(View.GONE);
            rl_four.setVisibility(View.GONE);
            rl_five.setVisibility(View.GONE);
            tv_onetext.setText("下载特权");
        } else if (info.equals("1")) {
            if (Integer.parseInt(mylevel) > (Integer.parseInt(info))){
                im_level.setBackgroundResource(R.mipmap.twolevel_light_icon);
            }else {
                im_level.setBackgroundResource(R.mipmap.twolevel_normal_icon);
            }
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.GONE);
            rl_three.setVisibility(View.GONE);
            rl_four.setVisibility(View.GONE);
            rl_five.setVisibility(View.GONE);
            tv_onetext.setText("随机获赠1-5团币");
        } else if (info.equals("2")) {
            if (Integer.parseInt(mylevel) > (Integer.parseInt(info))){
                im_level.setBackgroundResource(R.mipmap.threelevel_light_icon);
            }else {
                im_level.setBackgroundResource(R.mipmap.threelevel_normal_icon);
            }
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.GONE);
            rl_four.setVisibility(View.GONE);
            rl_five.setVisibility(View.GONE);
            tv_onetext.setText("团币充值98折");
            tv_twotext.setText("+10团币");
        } else if (info.equals("3")) {
            if (Integer.parseInt(mylevel) > (Integer.parseInt(info))){
                im_level.setBackgroundResource(R.mipmap.fourlevel_light_icon);
            }else {
                im_level.setBackgroundResource(R.mipmap.fourlevel_normal_icon);
            }
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.VISIBLE);
            rl_four.setVisibility(View.GONE);
            rl_five.setVisibility(View.GONE);
            tv_onetext.setText("团币充值95折");
            tv_twotext.setText("随机获赠1-20团币");
            tv_threetext.setText("10币听课券一张(3个月有效");
        } else if (info.equals("4")) {
            if (Integer.parseInt(mylevel) > (Integer.parseInt(info))){
                im_level.setBackgroundResource(R.mipmap.fivelevel_light_icon);
            }else {
                im_level.setBackgroundResource(R.mipmap.fivelevel_normal_icon);
            }
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.GONE);
            rl_four.setVisibility(View.GONE);
            rl_five.setVisibility(View.GONE);
            tv_onetext.setText("团币充值92折");
            tv_twotext.setText("+20团币");
        } else if (info.equals("5")) {
            if (Integer.parseInt(mylevel) > (Integer.parseInt(info))){
                im_level.setBackgroundResource(R.mipmap.sixlevel_light_icon);
            }else {
                im_level.setBackgroundResource(R.mipmap.sixlevel_normal_icon);
            }
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.VISIBLE);
            rl_four.setVisibility(View.VISIBLE);
            rl_five.setVisibility(View.GONE);
            tv_onetext.setText("团币充值9折");
            tv_twotext.setText("随机获赠10-20团币");
            tv_threetext.setText("10币听课券一张(3个月有效");
            tv_fourtext.setText("20币听课券一张(3个月有效");
        } else if (info.equals("6")) {
            if (Integer.parseInt(mylevel) > (Integer.parseInt(info))){
                im_level.setBackgroundResource(R.mipmap.sevenlevel_light_icon);
            }else {
                im_level.setBackgroundResource(R.mipmap.sevenlevel_normal_icon);
            }
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.VISIBLE);
            rl_four.setVisibility(View.VISIBLE);
            rl_five.setVisibility(View.GONE);
            tv_onetext.setText("团币充值88折");
            tv_twotext.setText("+20团币");
            tv_threetext.setText("20币听课券一张(6个月有效");
            tv_fourtext.setText("30币听课券一张(6个月有效");
        } else if (info.equals("7")) {
            if (Integer.parseInt(mylevel) > (Integer.parseInt(info))){
                im_level.setBackgroundResource(R.mipmap.eightlevel_light_icon);
            }else {
                im_level.setBackgroundResource(R.mipmap.eightlevel_normal_icon);
            }
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.VISIBLE);
            rl_four.setVisibility(View.VISIBLE);
            rl_five.setVisibility(View.GONE);
            tv_onetext.setText("团币充值85折");
            tv_twotext.setText("随机获赠30-50团币");
            tv_threetext.setText("20币听课券一张(6个月有效");
            tv_fourtext.setText("50币听课券一张(6个月有效");
        } else if (info.equals("8")) {
            if (Integer.parseInt(mylevel) > (Integer.parseInt(info))){
                im_level.setBackgroundResource(R.mipmap.ninelevel_light_icon);
            }else {
                im_level.setBackgroundResource(R.mipmap.ninelevel_normal_icon);
            }
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.VISIBLE);
            rl_four.setVisibility(View.VISIBLE);
            rl_five.setVisibility(View.VISIBLE);
            tv_onetext.setText("团币充值82折");
            tv_twotext.setText("+50团币");
            tv_threetext.setText("20币听课券一张(6个月有效");
            tv_fourtext.setText("30币听课券一张(6个月有效");
            tv_fivetext.setText("100币听课券一张(6个月有效");
        } else if (info.equals("9")) {
            if (Integer.parseInt(mylevel) > (Integer.parseInt(info))){
                im_level.setBackgroundResource(R.mipmap.tenlevel_light_icon);
            }else {
                im_level.setBackgroundResource(R.mipmap.tenlevel_normal_icon);
            }
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.VISIBLE);
            rl_four.setVisibility(View.VISIBLE);
            rl_five.setVisibility(View.VISIBLE);
            tv_onetext.setText("团币充值8折");
            tv_twotext.setText("随机获赠50-100团币");
            tv_threetext.setText("30币听课券一张(12个月有效");
            tv_fourtext.setText("50币听课券一张(12个月有效");
            tv_fivetext.setText("1000币听课券一张(6个月有效");
        }
        if (Integer.parseInt(mylevel) > (Integer.parseInt(info))) {
            tv_onetext.setTextColor(Color.parseColor("#D5AA5F"));
            tv_twotext.setTextColor(Color.parseColor("#D5AA5F"));
            tv_threetext.setTextColor(Color.parseColor("#D5AA5F"));
            tv_fourtext.setTextColor(Color.parseColor("#D5AA5F"));
            tv_fivetext.setTextColor(Color.parseColor("#D5AA5F"));
            /*one_point.setVisibility(View.VISIBLE);
            two_point.setVisibility(View.VISIBLE);
            three_point.setVisibility(View.VISIBLE);
            four_point.setVisibility(View.VISIBLE);
            five_point.setVisibility(View.VISIBLE);*/

        }
        return viewHolder.convertView;
    }
}