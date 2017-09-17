package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.utils.MyToast;
import com.lvgou.distribution.utils.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/30.
 */

public class MyActivityAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public MyActivityAdapter(Context _context) {
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
        final HashMap<String, Object> info = list.get(position);
        Log.e("kjsghajdf", "---------" + info);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.my_activity_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);//图片
        TextView tv_title = viewHolder.getView(R.id.tv_title, TextView.class);//标题
        final TextView tv_state = viewHolder.getView(R.id.tv_state, TextView.class);//状态
        TextView tv_time = viewHolder.getView(R.id.tv_time, TextView.class);//时间
        TextView tv_place = viewHolder.getView(R.id.tv_place, TextView.class);//地点
        RelativeLayout rl_more = viewHolder.getView(R.id.rl_more, RelativeLayout.class);//更多
        tv_title.setText(info.get("Title").toString());
        String[] split = info.get("StartTime").toString().split("T");
        tv_time.setText(split[0]);
//状态（State）：状态：1=进行中、2=已结束
       final String startTime = info.get("StartTime").toString().replace("T", " ");
        if (info.get("State").toString().equals("1")) {
            //进行中
            try {
                Date nowData = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startData = sdf.parse(startTime);
                boolean boo = compareDate(nowData, startData);
                if (boo) {
                    //当前时间大
                    //活动开始了
                    tv_state.setText("进行中");
                    tv_state.setTextColor(Color.parseColor("#6cc50b"));
                } else {
                    //当前时间小
                    //报名中
                    tv_state.setText("报名中");
                    tv_state.setTextColor(Color.parseColor("#fc4d30"));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else if (info.get("State").toString().equals("2")) {
            tv_state.setText("已结束");
            tv_state.setTextColor(Color.parseColor("#777777"));
        }
        tv_place.setText(info.get("CountryPath").toString());
        Glide.with(context).load(Url.ROOT + info.get("PicUrl").toString()).into(im_picture);

        rl_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*try {
                    Date nowData = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startData = sdf.parse(startTime);
                    boolean  boo = compareDate(nowData, startData);
                    if (info.get("State").toString().equals("1")&&boo) {
                        //进行中
                        MyToast.show(context,"活动已经开始，无法操作喔");
                    }else {
                        adapterToFra.doSomeThing(info);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                adapterToFra.doSomeThing(info);


            }
        });


        return viewHolder.convertView;
    }

    AdapterToFraImpl adapterToFra;

    public void setAdapterToFraImpl(AdapterToFraImpl adapterToFra) {
        this.adapterToFra = adapterToFra;
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