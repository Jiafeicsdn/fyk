package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/3/14.
 */

public class SearchResultAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public SearchResultAdapter(Context _context) {
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
        HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.skills_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        TextView tv_content = viewHolder.getView(R.id.tv_content, TextView.class);
        TextView tv_name = viewHolder.getView(R.id.tv_name, TextView.class);
        TextView tv_number = viewHolder.getView(R.id.tv_number, TextView.class);
        TextView tv_price = viewHolder.getView(R.id.tv_price, TextView.class);
        Glide.with(context).load(Url.ROOT + info.get("Banner1").toString()).error(R.mipmap.pictures_no).into(im_picture);
        tv_name.setText(info.get("TeacherName").toString());
        tv_content.setText(info.get("Theme").toString());
        String state = info.get("State").toString();
        String bmTuanBi = info.get("BMTuanBi").toString();
        String cKTuanBi = info.get("CKTuanBi").toString();
        String people_Apply = info.get("People_Apply").toString();
        String hits = info.get("Hits").toString();
        if (state.equals("1") || state.equals("3")) {
            if (bmTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(bmTuanBi + "币");
            }
            tv_number.setText(people_Apply + "人次");
        } else if (state.equals("2") || state.equals("4")) {
            if (cKTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(cKTuanBi + "币");
            }
            tv_number.setText(hits + "人次");
        }

//        Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("DistributorID").toString())).into(img_head);
        return viewHolder.convertView;
    }

}
