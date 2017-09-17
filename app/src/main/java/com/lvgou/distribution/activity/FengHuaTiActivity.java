package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.HuatiAdapter;
import com.lvgou.distribution.adapter.MarkAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.fragment.FaXianFragment;
import com.lvgou.distribution.presenter.FindTagandTopicPresenter;
import com.lvgou.distribution.presenter.TopicListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.TopicListView;
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
 * Created by Administrator on 2017/4/17.
 */

public class FengHuaTiActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, TopicListView {
    private TopicListPresenter topicListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feng_mark);
        topicListPresenter = new TopicListPresenter(this);
        initView();
        onRefresh();
        initClick();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XListView mListView;
    private HuatiAdapter markAdapter;
    private TextView tv_sure;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("相关话题");
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_sure.setVisibility(View.GONE);
        mListView = (XListView) findViewById(R.id.list_view);
        markAdapter = new HuatiAdapter(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        mListView.setAdapter(markAdapter);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mcache.put("fenghuatilist", dataList.get(position - 1));
                finish();
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

    private int page = 1;
    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    private void initDatas() {
        String sign = TGmd5.getMD5(page + "");
        showLoadingProgressDialog(this, "");
        topicListPresenter.topicList(page, sign);

    }

    @Override
    public void OnTopicListSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
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
            Log.e("lkhasks", "-------" + dataListTmp);
            if (page == Integer.parseInt(jsonObject1.get("DataPageCount").toString())) {
                //如果当前页等于总页数，就没有下一页
                mHandler.sendEmptyMessage(2);
            } else {
                //有下一页
                mHandler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnTopicListFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeTopicListProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<FengHuaTiActivity> mActivity;

        public mainHandler(FengHuaTiActivity activity) {
            mActivity = new WeakReference<FengHuaTiActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FengHuaTiActivity activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }

    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                markAdapter.setData(dataList);
                markAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }

            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            markAdapter.setData(dataList);
            markAdapter.notifyDataSetChanged();
            dataListTmp.clear();

            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }
}
