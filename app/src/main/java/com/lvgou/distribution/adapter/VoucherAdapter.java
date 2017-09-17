package com.lvgou.distribution.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.utils.ACache;
import com.lvgou.distribution.utils.ViewHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/7.
 */

public class VoucherAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    private int courseSize = 0;
    private int isselecter = 0;

    public VoucherAdapter(Context _context, int courseSize, int commentSize) {
        this.context = _context;
        this.courseSize = courseSize;
        this.isselecter = commentSize;
    }

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
        for (HashMap<String, Object> hashMap : list) {
            if (hashMap.get("Type").toString().equals("4")) {
                //如果是全局券
                if (hashMap.get("isCheck").toString().equals("true")) {
                    isselecter++;
                }
            }else {
                if (hashMap.get("isCheck").toString().equals("false")) {
                    isselecter--;
                }
            }

        }
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
        HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.voucher_list_item);
        TextView tv_price = viewHolder.getView(R.id.tv_price, TextView.class);
        TextView tv_title = viewHolder.getView(R.id.tv_title, TextView.class);
        TextView tv_limit_time = viewHolder.getView(R.id.tv_limit_time, TextView.class);
        RelativeLayout rl_check = viewHolder.getView(R.id.rl_check, RelativeLayout.class);//选中扩大的点击
        final ImageView cb_check = viewHolder.getView(R.id.cb_check, ImageView.class);//是否选中

        tv_price.setText(info.get("TuanBi").toString());
        //Type:听课卷类型：1=通用卷（针对某一个课程报名+查看）、2=报名卷（针对某一个课程报名）、
        // 3=查看卷（针对某一个课程查看）、4=全局卷（所有课程报名+查看）
        /*if (info.get("Type").toString().equals("1")) {
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
        tv_title.setText(info.get("LectureName").toString());
        String endtime = info.get("EndTime").toString();
        String[] ts = endtime.split("T");
        if (ts[0].equals("1900-01-01")) {
            tv_limit_time.setText("有效期：无限制");
        } else {
            tv_limit_time.setText(ts[0]);
        }
        if (info.get("isCheck").toString().equals("true")) {
            //如果是选中的
            cb_check.setBackgroundResource(R.mipmap.checkbox_check_icon);
        } else {
            cb_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
        }

        rl_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).get("isCheck").toString().equals("true")) {
                    //如果原本是选中当中
//                    cb_check.setChecked(false);
                    list.get(position).put("isCheck", "false");
                    cb_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
                    isselecter--;
                    adapterToFra.doSomeThing(list.get(position));
                } else {
                    //如果原本不是选中当中
//                    cb_check.setChecked(true);
                    if (isselecter == courseSize) {
                        MyToast.makeText(context, "不能再选了！", Toast.LENGTH_SHORT).show();
                    } else {
                        list.get(position).put("isCheck", "true");
                        cb_check.setBackgroundResource(R.mipmap.checkbox_check_icon);
                        isselecter++;
                        adapterToFra.doSomeThing(list.get(position));
                    }

                }

            }
        });
        return viewHolder.convertView;
    }

    AdapterToFraImpl adapterToFra;

    public void setAdapterToFraImpl(AdapterToFraImpl adapterToFra) {
        this.adapterToFra = adapterToFra;
    }
}