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
import com.xdroid.common.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/9.
 */

public class LiveFragmentAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    private int width = 0;
    public LiveFragmentAdapter(Context _context) {
        this.context = _context;
    }

    public void setData(ArrayList<HashMap<String, Object>> list,int width) {
        this.list = list;
        this.width = width;
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
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.live_fragment_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        TextView tv_content = viewHolder.getView(R.id.tv_content, TextView.class);
        TextView live_state = viewHolder.getView(R.id.live_state, TextView.class);
        TextView live_state_go = viewHolder.getView(R.id.live_state_go, TextView.class);
        TextView tv_renshu = viewHolder.getView(R.id.tv_renshu, TextView.class);
        ViewGroup.LayoutParams para = im_picture.getLayoutParams();
        int iheight = (int) ((width - ScreenUtils.dpToPx(context, 40)) * 34 / 67);

        para.height = iheight;// 控件的高强制设成
        im_picture.setLayoutParams(para);
        Glide.with(context).load(Url.ROOT + info.get("Banner1").toString()).into(im_picture);
        tv_content.setText(info.get("Theme").toString());
        String state = info.get("State").toString();
        String type = info.get("Type").toString();
        String people_Apply = info.get("People_Apply").toString();
        String hits = info.get("Hits").toString();
        String People_Min=info.get("People_Min").toString();
        if (state.equals("1") || state.equals("3")) {
            tv_renshu.setText(people_Apply + "人报名");
        } else if (state.equals("2") || state.equals("4")) {
            tv_renshu.setText(hits + "人报名");
        }
        if (state.equals("3")) {
            //直播中
            live_state.setText("直播中");
            live_state.setTextColor(Color.parseColor("#71C713"));
            live_state.setBackgroundResource(R.drawable.live_text_green_bg);
            live_state_go.setText("去听课");
            live_state_go.setTextColor(Color.parseColor("#FEA218"));
            live_state_go.setBackgroundResource(R.drawable.live_text_orange_bg);
            if (type.equals("2")){
                //众筹课
                tv_renshu.setText(people_Apply + "/"+People_Min+"人起");
            }
        } else if (state.equals("1") ) {
            if (type.equals("2")){
                //众筹课
                live_state.setText("众筹中");
                live_state.setTextColor(Color.parseColor("#FEA218"));
                live_state.setBackgroundResource(R.drawable.live_text_orange_bg);
                live_state_go.setText("去众筹");
                live_state_go.setTextColor(Color.parseColor("#FEA218"));
                live_state_go.setBackgroundResource(R.drawable.live_text_orange_bg);
                tv_renshu.setText(people_Apply + "/"+People_Min+"人起");
            }else {
                //报名中
                live_state.setText("报名中");
                live_state.setTextColor(Color.parseColor("#FEA218"));
                live_state.setBackgroundResource(R.drawable.live_text_orange_bg);
                live_state_go.setText("去报名");
                live_state_go.setTextColor(Color.parseColor("#FEA218"));
                live_state_go.setBackgroundResource(R.drawable.live_text_orange_bg);
            }

        }

        return viewHolder.convertView;
    }
}