package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.CollecteJokeAdapter;
import com.lvgou.distribution.adapter.RecommendRecordAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.RecommendListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.RecommendListView;
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
 * Created by Administrator on 2017/4/28.
 */

public class RecommendRecordActivity extends BaseActivity implements View.OnClickListener, RecommendListView, XListView.IXListViewListener {
    private RecommendListPresenter recommendListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_record);
        recommendListPresenter = new RecommendListPresenter(this);
        initView();
        onRefresh();
        initClick();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private TextView tv_success_num;
    private XListView mListView;
    private RecommendRecordAdapter recommendRecordAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) findViewById(R.id.rl_none_one);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("推荐记录");
        tv_success_num = (TextView) findViewById(R.id.tv_success_num);

        mListView = (XListView) findViewById(R.id.list_view);
        recommendRecordAdapter = new RecommendRecordAdapter(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        mListView.setAdapter(recommendRecordAdapter);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

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

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();
    private int page = 1;

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5("" + distributorid + page);
        showLoadingProgressDialog(this, "");
        recommendListPresenter.recommendList(distributorid, page, sign);
    }

    @Override
    public void OnRecommendListSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        mListView.stopRefresh();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("Items"));
            int jsonObject2 = jsonArray.getInt(1);
            if (jsonObject2 == 0) {
                tv_success_num.setVisibility(View.GONE);
            } else {
                tv_success_num.setVisibility(View.VISIBLE);
                tv_success_num.setText("成功推荐" + jsonObject2 + "人");
            }


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
            if (dataListTmp.size() < Integer.parseInt(jsonObject1.get("ItemsPerPage").toString()) && Integer.parseInt(jsonObject1.get("ItemsPerPage").toString()) != 0) {
                mHandler.sendEmptyMessage(2);
            } else {
                mHandler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnRecommendListFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeRecommendListProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<RecommendRecordActivity> mActivity;

        public mainHandler(RecommendRecordActivity activity) {
            mActivity = new WeakReference<RecommendRecordActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RecommendRecordActivity activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                recommendRecordAdapter.setData(dataList);
                recommendRecordAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }
            if (dataList.size() == 0) {
                mListView.setPullLoadEnable(false);
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            recommendRecordAdapter.setData(dataList);
            recommendRecordAdapter.notifyDataSetChanged();
            dataListTmp.clear();
            if (dataList.size() == 0) {
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }


}
