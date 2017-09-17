package com.lvgou.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.ApplyActivityAdapter;
import com.lvgou.distribution.adapter.MyFocusAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.MyFollowListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.MyFollowListView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/26.
 */

public class MyFocusActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, MyFollowListView {
    private MyFollowListPresenter myFollowListPresenter;
    private String distributorid;
    private String seeDistributorId;
    private String realName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfocus);
        myFollowListPresenter = new MyFollowListPresenter(this);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        seeDistributorId = getIntent().getStringExtra("seeDistributorId");
        realName = getIntent().getStringExtra("realName");
        initView();

        initClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    private int page = 1;

    private void initDatas() {
        String sign = TGmd5.getMD5(distributorid + seeDistributorId + page);
        showLoadingProgressDialog(this, "");
        myFollowListPresenter.myFollowList(distributorid, seeDistributorId, page, sign);

    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XListView mListView;
    private MyFocusAdapter myFocusAdapter;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        if (distributorid.equals(seeDistributorId)) {
            //如果是自己
            tv_title.setText("我的关注");
        } else {
            tv_title.setText(realName + "的关注");
        }
        mListView = (XListView) findViewById(R.id.list_view);
        myFocusAdapter = new MyFocusAdapter(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        mListView.setAdapter(myFocusAdapter);

    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dataList.get(position - 1).get("UserType").toString().equals("3")) {
                    //如果是讲师
                    Intent intent1 = new Intent(MyFocusActivity.this, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", dataList.get(position - 1).get("ID").toString());
                    startActivity(intent1);
                } else {
                    //如果是导游
                    Intent intent1 = new Intent(MyFocusActivity.this, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", dataList.get(position - 1).get("ID").toString());
                    startActivity(intent1);
                }
            }
        });
        myFocusAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                for (int i = 0; i < dataList.size(); i++) {
                    if (info.get("ID").toString().equals(dataList.get(i).get("ID").toString())) {
                        dataList.get(i).put("TuanBi", "2");
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    @Override
    public void onRefresh() {
        page = 1;
        dataList.clear();
        initDatas();
    }

    @Override
    public void onLoadMore() {
        page++;
        initDatas();
    }

    @Override
    public void OnMyFollowListSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        mListView.stopRefresh();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("Data"));
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
            if (page < Integer.parseInt(jsonObject1.get("DataPageCount").toString())) {
                mHandler.sendEmptyMessage(1);
            } else {
                mHandler.sendEmptyMessage(2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<MyFocusActivity> mActivity;

        public mainHandler(MyFocusActivity activity) {
            mActivity = new WeakReference<MyFocusActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MyFocusActivity activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                myFocusAdapter.setData(dataList);
                myFocusAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }

            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            myFocusAdapter.setData(dataList);
            myFocusAdapter.notifyDataSetChanged();
            dataListTmp.clear();

            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }

    @Override
    public void OnMyFollowListFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        mListView.stopRefresh();
        Log.e("kjshakdf", "***************" + respanse);
    }

    @Override
    public void closeMyFollowListProgress() {

    }
}
