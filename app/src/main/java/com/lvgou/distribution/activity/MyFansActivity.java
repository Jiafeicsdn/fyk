package com.lvgou.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.MyFansAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.MyFansListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.MyFansListView;
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

public class MyFansActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, MyFansListView {
    private MyFansListPresenter myFansListPresenter;
    private String distributorid;
    private String seeDistributorId;
    private String realName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfocus);
        myFansListPresenter = new MyFansListPresenter(this);
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
        myFansListPresenter.myFansList(distributorid, seeDistributorId, page, sign);

    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XListView mListView;
    private MyFansAdapter myFansAdapter;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        if (distributorid.equals(seeDistributorId)) {
            //如果是自己
            tv_title.setText("我的粉丝");
        } else {
            tv_title.setText(realName + "的粉丝");
        }
        mListView = (XListView) findViewById(R.id.list_view);
        myFansAdapter = new MyFansAdapter(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        mListView.setAdapter(myFansAdapter);

    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dataList.get(position - 1).get("UserType").toString().equals("3")) {
                    //如果是讲师
                    Intent intent1 = new Intent(MyFansActivity.this, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", dataList.get(position - 1).get("ID").toString());
                    startActivity(intent1);
                } else {
                    //如果是导游
                    Intent intent1 = new Intent(MyFansActivity.this, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", dataList.get(position - 1).get("ID").toString());
                    startActivity(intent1);
                }
            }
        });
        myFansAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
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


    private final mainHandler mHandler = new mainHandler(this);


    private static class mainHandler extends Handler {
        private final WeakReference<MyFansActivity> mActivity;

        public mainHandler(MyFansActivity activity) {
            mActivity = new WeakReference<MyFansActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MyFansActivity activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                myFansAdapter.setData(dataList);
                myFansAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }

            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            myFansAdapter.setData(dataList);
            myFansAdapter.notifyDataSetChanged();
            dataListTmp.clear();

            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }

    @Override
    public void OnMyFansListSuccCallBack(String state, String respanse) {
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

    @Override
    public void OnMyFansListFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        mListView.stopRefresh();
    }

    @Override
    public void closeMyFansListProgress() {

    }
}