package com.lvgou.distribution.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.DensityUtil;
import com.lvgou.distribution.utils.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/3/13.
 */

public class ClassifyAdapter2 extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public ClassifyAdapter2(Context _context) {
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
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.classify_item2);
        TextView tv_title = viewHolder.getView(R.id.tv_title, TextView.class);
        ListView listv2 = viewHolder.getView(R.id.listv2, ListView.class);
        View view_line = viewHolder.getView(R.id.view_line, View.class);
        if (position == 0) {
            view_line.setVisibility(View.GONE);
        } else {
            view_line.setVisibility(View.VISIBLE);
        }
        tv_title.setText(info.get("LabelName").toString());
        final ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();
        try {
            JSONArray jsa = new JSONArray(info.get("studylist").toString());
            dataListTmp.clear();
            for (int i = 0; i < jsa.length(); i++) {
                JSONObject jsoo = jsa.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                dataListTmp.add(map1);
            }
            ViewGroup.LayoutParams params = listv2.getLayoutParams();
            int height = 0;
            height = dataListTmp.size() * DensityUtil.dip2px(context, 200);
            params.height = height;
            listv2.setLayoutParams(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ClassifyListAdapter classifyListAdapter = new ClassifyListAdapter(context);
        classifyListAdapter.setData(dataListTmp);
        listv2.setAdapter(classifyListAdapter);
        listv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(context, CourseIntrActivity.class);
                intent1.putExtra("id", dataListTmp.get(position).get("ID").toString());
                context.startActivity(intent1);
            }
        });
        return viewHolder.convertView;
    }
}