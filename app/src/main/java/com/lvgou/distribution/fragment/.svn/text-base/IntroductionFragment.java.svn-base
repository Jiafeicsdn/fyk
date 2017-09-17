package com.lvgou.distribution.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ActBMActivity;
import com.lvgou.distribution.activity.ActDetailActivity;
import com.lvgou.distribution.activity.ActivityBMActivity;
import com.lvgou.distribution.activity.CollegeManagerActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.activity.TuanYuanActivity;
import com.lvgou.distribution.adapter.ApplyViewAdapter;
import com.lvgou.distribution.adapter.BoutiqueFragmentAdapter;
import com.lvgou.distribution.adapter.TeacgerViewAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.CompetitiveStudyPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.WorksGridView;
import com.lvgou.distribution.view.CompetitiveStudyView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/3/21.
 * 简介
 */

public class IntroductionFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private String activityID;
    private String distributorid;
    private String manDistributorID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_introduction, container, false);
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        initView();
        initClick();
        return view;
    }


    private ScrollView sl_all;
    private TextView tv_title;//标题
    private TextView tv_time;//时间
    private TextView tv_location;//地点
    private TextView tv_organizer;//组织者
    private TextView tv_call;//电话
    private TextView tv_apply_num;//报名人数
    private TextView tv_act_detail;//关于活动
    private RelativeLayout rl_baoming_people;//报名人数列表
    private View view_app_people;

    private void initView() {
        view_app_people=view.findViewById(R.id.view_app_people);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        sl_all = (ScrollView) view.findViewById(R.id.sl_all);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        tv_organizer = (TextView) view.findViewById(R.id.tv_organizer);
        tv_call = (TextView) view.findViewById(R.id.tv_call);
        tv_apply_num = (TextView) view.findViewById(R.id.tv_apply_num);
        tv_act_detail = (TextView) view.findViewById(R.id.tv_act_detail);
        rl_baoming_people = (RelativeLayout) view.findViewById(R.id.rl_baoming_people);


    }


    public void setIntroduct(JSONArray jsa) {
        try {
            JSONObject jsonObject = jsa.getJSONObject(0);
            manDistributorID = jsonObject.get("DistributorID").toString();
            tv_title.setText(jsonObject.get("Title").toString());
            String starttime = jsonObject.get("StartTime").toString();
            String[] ts = starttime.split("T");
            String[] split = ts[0].split("-");
            String[] split1 = ts[1].split(":");
            String endTime = jsonObject.get("EndTime").toString();
            String[] ts1 = endTime.split("T");
            String[] split2 = ts1[0].split("-");
            String[] split3 = ts1[1].split(":");
            activityID = jsonObject.get("ID").toString();
            tv_time.setText(split[1] + "-" + split[2] + " " + split1[0] + ":" + split1[1] + "/" + split2[1] + "-" + split2[2] + " " + split3[0] + ":" + split3[1]);
            tv_location.setText(jsonObject.get("Address").toString());
            tv_organizer.setText("组织者：" + jsonObject.get("DistributorName").toString());
            tv_call.setText(jsonObject.get("Mobile").toString());
            tv_act_detail.setText(jsonObject.get("ActivityIntro").toString());
            SpannableString spanString = new SpannableString(jsa.get(2).toString() + "人/" + jsonObject.get("PeopleCount").toString() + "人");
            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#333333"));
            spanString.setSpan(span, 0, jsa.get(2).toString().length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_apply_num.setText(spanString);
//            tv_apply_num.setText(jsa.get(2).toString() + "人/" + jsonObject.get("PeopleCount").toString() + "人");//当前报名人数
            applyLists.clear();
            JSONArray jsonArray = jsa.getJSONArray(3);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsoo = jsonArray.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                applyLists.add(map1);
            }
            initApplyGtideView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initClick() {
        rl_baoming_people.setOnClickListener(this);
        tv_apply_num.setOnClickListener(this);
        view_app_people.setOnClickListener(this);

    }

    private WorksGridView applyGridView;
    private ArrayList<HashMap<String, Object>> applyLists = new ArrayList<>();

    private void initApplyGtideView() {
        applyGridView = (WorksGridView) view.findViewById(R.id.grid_teacher);
        setApplyGridView();
        applyGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        applyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*HashMap<String, Object> dic2 = starLists.get(position);
                Intent intent = new Intent();
                intent.setClass(GuangChangActivity.this, UserInfoActivity_No2.class);
                intent.putExtra("sso_id", dic2.get("id").toString());
                startActivity(intent);*/
                if (distributorid.equals(manDistributorID)){
                    Intent intent=new Intent(getActivity(),ActBMActivity.class);
                    intent.putExtra("id",activityID);
                    startActivity(intent);
                }else {
                    //如果不是组织者
                    Bundle bundle = new Bundle();
                    bundle.putString("id", activityID);
                    ((ActDetailActivity) getActivity()).openActivity(ActivityBMActivity.class, bundle);
                }


            }
        });
    }

    private void setApplyGridView() {
        int size = applyLists.size();
//        int size = 5;
        int length = 35;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        applyGridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        applyGridView.setColumnWidth(itemWidth); // 设置列表项宽
//        applyGridView.setHorizontalSpacing(20); // 设置列表项水平间距
        applyGridView.setStretchMode(GridView.NO_STRETCH);
        applyGridView.setNumColumns(size); // 设置列数量=列表集合数
        ApplyViewAdapter adapter = new ApplyViewAdapter(getActivity(), applyLists);
        applyGridView.setAdapter(adapter);
    }

    @Override
    public void pullToRefresh() {

    }

    @Override
    public void refreshComplete() {

    }

    @Override
    public View getScrollableView() {
        return sl_all;
    }


    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
//        ((ActDetailActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply_num:
            case R.id.view_app_people:
            case R.id.rl_baoming_people:
                if (distributorid.equals(manDistributorID)){
                    Intent intent=new Intent(getActivity(),ActBMActivity.class);
                    intent.putExtra("id",activityID);
                    startActivity(intent);
                }else {
                    //不是组织者
                    Bundle bundle = new Bundle();
                    bundle.putString("id", activityID);
                    ((ActDetailActivity) getActivity()).openActivity(ActivityBMActivity.class, bundle);
                }

                break;

        }
    }
}
