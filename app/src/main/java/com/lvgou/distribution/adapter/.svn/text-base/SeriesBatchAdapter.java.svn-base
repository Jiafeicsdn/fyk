package com.lvgou.distribution.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.SeriesClassActivity;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/6.
 * 系列课批量处理
 */

public class SeriesBatchAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public SeriesBatchAdapter(Context _context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.series_batch_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        final ImageView cb_check = viewHolder.getView(R.id.cb_check, ImageView.class);
        TextView tv_content = viewHolder.getView(R.id.tv_content, TextView.class);
        RelativeLayout rl_check = viewHolder.getView(R.id.rl_check, RelativeLayout.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        TextView tv_number = viewHolder.getView(R.id.tv_number, TextView.class);
        TextView tv_price = viewHolder.getView(R.id.tv_price, TextView.class);
        Glide.with(context).load(Url.ROOT + info.get("Banner1").toString()).into(im_picture);
        tv_name.setText(info.get("TeacherName").toString());
        tv_number.setText(info.get("Hits").toString() + "人次");
        tv_content.setText(info.get("Theme").toString());
        if (Integer.parseInt(info.get("CKTuanBi").toString()) == 0) {
            tv_price.setText("免费");
        } else {
            tv_price.setText(Integer.parseInt(info.get("CKTuanBi").toString()) + "币");
        }
        if (info.get("isCheck").toString().equals("true")) {
            cb_check.setBackgroundResource(R.mipmap.checkbox_check_icon);
        } else {
            cb_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
        }
//        cb_check.setEnabled(true);

        if (Integer.parseInt(info.get("People_Apply").toString()) > 0) {
            //表示已经购买 不让再点击了
            cb_check.setBackgroundResource(R.mipmap.checkbox_not_check);
//            cb_check.setEnabled(false);
            rl_check.setClickable(false);
            rl_check.setEnabled(false);
        } else {
            rl_check.setClickable(true);
            rl_check.setEnabled(true);
        }

        rl_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).get("isCheck").toString().equals("true")) {
                    //如果原本是选中当中
//                    cb_check.setChecked(false);
                    list.get(position).put("isCheck", "false");
                    cb_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
                } else {
                    //如果原本不是选中当中
//                    cb_check.setChecked(true);
                    list.get(position).put("isCheck", "true");
                    cb_check.setBackgroundResource(R.mipmap.checkbox_check_icon);
                }
                ((SeriesClassActivity) context).doSomeThing(list.get(position));
//                adapterToFra.doSomeThing(list.get(position));
            }
        });
        return viewHolder.convertView;
    }

    /*AdapterToFraImpl adapterToFra;

    public void setAdapterToFraImpl(AdapterToFraImpl adapterToFra) {
        this.adapterToFra = adapterToFra;
    }*/
}