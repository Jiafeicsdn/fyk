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
import com.lvgou.distribution.inter.AdapterToActImpl;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.utils.ACache;
import com.lvgou.distribution.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

import static com.lvgou.distribution.R.id.cb_check;

/**
 * Created by Administrator on 2017/4/20.
 */

public class BuyBatchClassAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    protected ACache mcache;
    private ArrayList<HashMap<String, Object>> finishList;//已经完成
    private ArrayList<HashMap<String, Object>> downingList;//正在下载中

    public BuyBatchClassAdapter(Context _context) {
        this.context = _context;
        mcache = ACache.get(context);
    }

    public void setData(ArrayList<HashMap<String, Object>> list, ArrayList<HashMap<String, Object>> finishList, ArrayList<HashMap<String, Object>> downingList) {
        this.list = list;
        this.finishList = finishList;
        this.downingList = downingList;

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
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.listen_batch_calss_item);
        RelativeLayout rl_item = viewHolder.getView(R.id.rl_item, RelativeLayout.class);
        final ImageView im_check = viewHolder.getView(R.id.im_check, ImageView.class);
        TextView tv_title = viewHolder.getView(R.id.tv_title, TextView.class);
        TextView tv_size = viewHolder.getView(R.id.tv_size, TextView.class);
        tv_title.setText(info.get("Theme").toString());
        tv_size.setText(info.get("AreaName").toString() + "M");
        rl_item.setClickable(true);
        rl_item.setEnabled(true);
        if (info.get("isCheck").toString().equals("true")) {
            im_check.setBackgroundResource(R.mipmap.checkbox_check_icon);
        } else {
            im_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
        }
        for (HashMap<String, Object> stringObjectHashMap : finishList) {//已完成
            if (stringObjectHashMap.get("Comment").toString().equals(info.get("Comment").toString())) {
                im_check.setBackgroundResource(R.mipmap.checkbox_not_check);
                rl_item.setClickable(false);
                rl_item.setEnabled(false);
                break;
            }
        }
        for (HashMap<String, Object> stringObjectHashMap : downingList) {//正在下载中
            if (stringObjectHashMap.get("Comment").toString().equals(info.get("Comment").toString())) {
                im_check.setBackgroundResource(R.mipmap.checkbox_not_check);
                rl_item.setClickable(false);
                rl_item.setEnabled(false);
                break;
            }
        }
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                if (list.get(position).get("isCheck").toString().equals("true")) {
                    //如果原本是选中当中
                    list.get(position).put("isCheck", "false");
                    im_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
                } else {
                    //如果原本不是选中当中
                    list.get(position).put("isCheck", "true");
                    im_check.setBackgroundResource(R.mipmap.checkbox_check_icon);
                }
                adapterToAct.doSomeThing(list.get(position));
            }
        });


        return viewHolder.convertView;
    }

    AdapterToActImpl adapterToAct;

    public void setAdapterToActImpl(AdapterToActImpl adapterToAct) {
        this.adapterToAct = adapterToAct;
    }
}