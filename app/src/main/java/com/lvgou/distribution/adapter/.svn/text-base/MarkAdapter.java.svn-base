package com.lvgou.distribution.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.FengMarkActivity;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/17.
 */

public class MarkAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    private int lenth = 0;
    private ArrayList<HashMap<String, Object>> markList = new ArrayList();

    public MarkAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<HashMap<String, Object>> list, ArrayList<HashMap<String, Object>> markList) {
        this.list = list;
        this.markList = markList;
        lenth = markList.size();
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.mark_list_item);
        final ImageView im_check = viewHolder.getView(R.id.im_check, ImageView.class);
        im_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
        TextView tv_text = viewHolder.getView(R.id.tv_text, TextView.class);
        RelativeLayout tl_all = viewHolder.getView(R.id.tl_all, RelativeLayout.class);
        tv_text.setText(info.get("CagegoryName").toString());
        if (info.get("State").toString().equals("0")) {
            im_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
        } else if (info.get("State").toString().equals("1")) {
            im_check.setBackgroundResource(R.mipmap.check_xieyi_icon);
        }

        tl_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (lenth >= 2) {
                    MyToast.makeText(context, "做多只能选择两个！", Toast.LENGTH_SHORT).show();
                } else {*/
                if (info.get("State").toString().equals("0")) {
                    if (lenth >= 2) {
                        MyToast.makeText(context, "做多只能选择两个！", Toast.LENGTH_SHORT).show();
                    } else {
                        //之前没有选中
                        im_check.setBackgroundResource(R.mipmap.check_xieyi_icon);
                        markList.add(info);
                        lenth++;
                        list.get(position).put("State", "1");
                        Log.e("khakshdf", "========" + markList);
                    }
                } else if (info.get("State").toString().equals("1")) {
                    //之前选中了的
                    im_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
                    for (int i = 0; i < markList.size(); i++) {
                        if (markList.get(i).get("ID").toString().equals(info.get("ID").toString())) {
                            markList.remove(i);
                            break;
                        }
                    }
                    lenth--;
                    Log.e("khakshdf", "--------" + markList);
                    list.get(position).put("State", "0");
                }
                ((FengMarkActivity) context).setCheckDatas(markList);

            }
        });
        return viewHolder.convertView;
    }
}