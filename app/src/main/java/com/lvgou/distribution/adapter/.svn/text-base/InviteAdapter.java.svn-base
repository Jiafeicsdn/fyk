package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/12.
 */

public class InviteAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public InviteAdapter(Context _context) {
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
            return list.size()/2 + list.size()%2;
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
        HashMap<String, Object> info = list.get(position*2);

        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.item_grid_invite_recoder);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);//名字
        TextView tv_state = viewHolder.getView(R.id.tv_state, TextView.class);
        TextView tv_time = viewHolder.getView(R.id.tv_time, TextView.class);
        TextView tv_name2 = viewHolder.getView(R.id.tv_name2, TextView.class);//名字
        TextView tv_state2 = viewHolder.getView(R.id.tv_state2, TextView.class);
        TextView tv_time2 = viewHolder.getView(R.id.tv_time2, TextView.class);
        LinearLayout ll_posidouble = viewHolder.getView(R.id.ll_posidouble, LinearLayout.class);
        if (list.size()>=(position*2+2)){
            ll_posidouble.setVisibility(View.VISIBLE);
            HashMap<String, Object> info2 = list.get(position*2+1);
            tv_name2.setText(info2.get("RealName").toString());
            String isGet2 = info2.get("IsGet").toString();
            String state2 = info2.get("State").toString();
            tv_state2.setText("");
            if (isGet2.equals("1")) {
                tv_state2.setText("(成功)");
                tv_state2.setTextColor(Color.parseColor("#6BBC62"));
            } else if (isGet2.equals("2") && state2.equals("1")) {
                tv_state2.setText("(待审核)");
                tv_state2.setTextColor(Color.parseColor("#fc4d30"));
            } else if (isGet2.equals("2") && state2.equals("3")) {
                tv_state2.setText("(不通过)");
                tv_state2.setTextColor(Color.parseColor("#fc4d30"));
            }
            String createTime2 = info2.get("CreateTime").toString();
            String[] ts2 = createTime2.split("T");
            tv_time2.setText(ts2[0]);
        }else {
            ll_posidouble.setVisibility(View.INVISIBLE);
        }



        tv_name.setText(info.get("RealName").toString());
        String isGet = info.get("IsGet").toString();
        String state = info.get("State").toString();
        tv_state.setText("");
        if (isGet.equals("1")) {
            tv_state.setText("(成功)");
            tv_state.setTextColor(Color.parseColor("#6BBC62"));
        } else if (isGet.equals("2") && state.equals("1")) {
            tv_state.setText("(待审核)");
            tv_state.setTextColor(Color.parseColor("#fc4d30"));
        } else if (isGet.equals("2") && state.equals("3")) {
            tv_state.setText("(不通过)");
            tv_state.setTextColor(Color.parseColor("#fc4d30"));
        }
        String createTime = info.get("CreateTime").toString();
        String[] ts = createTime.split("T");
        tv_time.setText(ts[0]);

        return viewHolder.convertView;
    }
}