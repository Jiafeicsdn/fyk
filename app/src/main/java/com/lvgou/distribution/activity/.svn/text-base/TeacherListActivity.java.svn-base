package com.lvgou.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.TeacherListAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.fragment.AllClassFragment;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.TeacherListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.TeacherListView;
import com.lvgou.distribution.widget.XRecyclerView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/12.
 */

public class TeacherListActivity extends BaseActivity implements XRecyclerView.OnLoadNextPageListener, TeacherListView, View.OnClickListener, DistributorHomeView {
    private TeacherListPresenter teacherListPresenter;
    private TeacherListAdapter teacherListAdapter;
    private String usertype;
    private DistributorHomePresenter distributorHomePresenter;
    private String distributorid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherlist);
        teacherListPresenter = new TeacherListPresenter(this);
        distributorHomePresenter = new DistributorHomePresenter(this);
        usertype = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, "0");
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        dataList.clear();
        dataListTmp.clear();
        initView();
        initDatas();
        initClick();
    }

    private int page = 1;

    private void initDatas() {
        String sign = TGmd5.getMD5(distributorid + "" + page);
        showLoadingProgressDialog(this, "");
        teacherListPresenter.teacherList(distributorid, page, sign);

    }

    private XRecyclerView re_recyclerview;
    private RelativeLayout rl_back;//返回
    private TextView tv_title;//标题
    private TextView tv_apply_teacher;//申请讲师
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) findViewById(R.id.rl_none_one);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("蜂优讲师");
        tv_apply_teacher = (TextView) findViewById(R.id.tv_apply_teacher);
        if (usertype.equals("3")) {
            tv_apply_teacher.setVisibility(View.GONE);
        }
        re_recyclerview = (XRecyclerView) findViewById(R.id.re_recyclerview);
        re_recyclerview.setOnLoadNextPageListener(this);
        teacherListAdapter = new TeacherListAdapter(this);
        re_recyclerview.setLayoutManager(new GridLayoutManager(TeacherListActivity.this, 2));
        re_recyclerview.setAdapter(teacherListAdapter);

    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        tv_apply_teacher.setOnClickListener(this);
        teacherListAdapter.setOnItemClickListener(new TeacherListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String seedistributorid = dataList.get(position).get("ID").toString();
                String sign = TGmd5.getMD5("" + distributorid + seedistributorid);
                showLoadingProgressDialog(TeacherListActivity.this, "");
                distributorHomePresenter.distributorHome(distributorid, seedistributorid, sign);

                /*if (userType.equals("3")) {
                    //如果是讲师
                    Intent intent1 = new Intent(TeacherListActivity.this, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", seedistributorid);
                    startActivity(intent1);
                } else {
                    //普通导游
                    Intent intent1 = new Intent(TeacherListActivity.this, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", seedistributorid);
                    startActivity(intent1);
                }*/
            }
        });
    }


    @Override
    public void onLodNextPage() {
        page++;
        initDatas();
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    @Override
    public void OnTeacherListSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("Items"));

            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsoo = jsonArray1.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                dataListTmp.add(map1);
            }
            if (dataListTmp.size() < Integer.parseInt(jsonObject1.get("ItemsPerPage").toString())&&Integer.parseInt(jsonObject1.get("ItemsPerPage").toString())!=0) {
                mHandler.sendEmptyMessage(2);
            } else {
                mHandler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnTeacherListFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();

    }

    @Override
    public void closeTeacherListProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_apply_teacher:
                Intent intent = new Intent(this, ApplyTeacherActivity.class);
                startActivity(intent);
                break;
        }
    }

    private static class mainHandler extends Handler {
        private final WeakReference<TeacherListActivity> mActivity;

        public mainHandler(TeacherListActivity activity) {
            mActivity = new WeakReference<TeacherListActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TeacherListActivity activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                teacherListAdapter.setData(dataList);
//                mListView.setPullLoadEnable(true);
            } else {
//                mListView.setPullLoadEnable(false);
            }
            if (dataList.size() == 0) {
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            dataListTmp.clear();
//            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            teacherListAdapter.setData(dataList);
            dataListTmp.clear();
            if (dataList.size() == 0) {
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
//            mListView.setPullLoadEnable(false);
//            mListView.stopLoadMore();
        }
    }


    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject distributorModeljsa = jsa.getJSONObject(0);
            if (distributorModeljsa.get("UserType").toString().equals("3")) {
                //如果是讲师
                Intent intent1 = new Intent(TeacherListActivity.this, TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(TeacherListActivity.this, DistributorHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnDistributorHomeFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeDistributorHomeProgress() {

    }
}
