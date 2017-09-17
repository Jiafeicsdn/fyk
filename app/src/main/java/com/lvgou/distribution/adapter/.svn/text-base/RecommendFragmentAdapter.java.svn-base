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

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.CollegeManagerActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.utils.DensityUtil;
import com.lvgou.distribution.utils.ViewHolder;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/3/10.
 */

public class RecommendFragmentAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;

    public RecommendFragmentAdapter(Context _context) {
        this.context = _context;
    }

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
        RecommendFragmentAdapter.this.notifyDataSetChanged();
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
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.recommend_fragment_item);//
        View view_line = viewHolder.getView(R.id.view_line, View.class);
        RelativeLayout rl_skills_more = viewHolder.getView(R.id.rl_skills_more, RelativeLayout.class);
        view_line.setVisibility(View.VISIBLE);
        if (position == list.size() - 1) {
            view_line.setVisibility(View.GONE);
        }
        TextView tv_skills_title = viewHolder.getView(R.id.tv_skills_title, TextView.class);
        tv_skills_title.setText(info.get("Title").toString());
        ListView lv_skills = viewHolder.getView(R.id.lv_skills, ListView.class);
        final ArrayList<HashMap<String, Object>> listData = new ArrayList<>();
        listData.clear();
        try {
            JSONArray jsonArray = new JSONArray(info.get("studylist").toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsoo = jsonArray.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                listData.add(map1);
            }
            ViewGroup.LayoutParams params = lv_skills.getLayoutParams();
            int height = 0;
            height = listData.size() * DensityUtil.dip2px(context, 110);
            params.height = height;
            lv_skills.setLayoutParams(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SkillsAdapter skillsAdapter = new SkillsAdapter(context);
        skillsAdapter.setData(listData);
        lv_skills.setAdapter(skillsAdapter);
        lv_skills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String isover = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
                String userstate = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
                if (!isRZDialog(userstate, isover)) {
                    ((CollegeManagerActivity) context).showRZDialog(userstate, isover);
                    return;
                } else {
                    Intent intent1 = new Intent(context, CourseIntrActivity.class);
                    intent1.putExtra("id", listData.get(position).get("ID").toString());
                    context.startActivity(intent1);
                }
            }
        });
        rl_skills_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isover = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
                String userstate = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
                if (!isRZDialog(userstate, isover)) {
                    ((CollegeManagerActivity) context).showRZDialog(userstate, isover);
                    return;
                } else {
                    ((CollegeManagerActivity) context).setCurrent(4, info.get("LinkUrl").toString());
                }
            }
        });
//        Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("DistributorID").toString())).into(img_head);
        return viewHolder.convertView;
    }

    public boolean isRZDialog(String state, String isover) {
        if (state.equals("1")) {
            //没有认证
            return false;
        } else if (state.equals("6")) {
            //审核不通过
            return false;
        } else if (state.equals("5") && isover.equals("false")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("2") || state.equals("3")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("4")) {
            //审核中
            return false;
        } else {
            return true;
        }
    }
}