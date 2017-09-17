package com.lvgou.distribution.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.SmsRecordEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.SendRecrodPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.SendSmsRecordView;
import com.lvgou.distribution.viewholder.SmsRecordViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/4/18 0018.
 * 发送记录
 */
public class SendSmsRecordActivity extends BaseActivity implements OnListItemClickListener<SmsRecordEntity>, SendSmsRecordView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;
    private ListView lv_sms_record;
    private ListViewDataAdapter<SmsRecordEntity> smsRecordEntityListViewDataAdapter;


    private String distributorid = "";
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private SendRecrodPresenter recrodPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_record);
        ViewUtils.inject(this);
        tv_title.setText("发送记录");

        recrodPresenter = new SendRecrodPresenter(this);

        distributorid = PreferenceHelper.readString(SendSmsRecordActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        lv_sms_record = pullToRefreshListView.getRefreshableView();
        initViewHolder();
        if (checkNet()) {
            String sign = TGmd5.getMD5(distributorid + pageindex);
            recrodPresenter.getSendSmsRecord(distributorid, pageindex + "", sign);
        }
        initCreate();

    }

    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    public void initCreate() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(SendSmsRecordActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                recrodPresenter.getSendSmsRecord(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    recrodPresenter.getSendSmsRecord(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(SendSmsRecordActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        recrodPresenter.attach(this);

    }

    public void initViewHolder() {
        smsRecordEntityListViewDataAdapter = new ListViewDataAdapter<SmsRecordEntity>();
        smsRecordEntityListViewDataAdapter.setViewHolderClass(this, SmsRecordViewHolder.class);
        SmsRecordViewHolder.setOnListItemClickListener(this);
        lv_sms_record.setAdapter(smsRecordEntityListViewDataAdapter);
    }

    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }


    /**
     * 显示进度条
     */
    @Override
    public void showProgress() {

    }


    /**
     * 取消进度条
     */
    @Override
    public void closeProgress() {

    }

    /**
     * 成功回调
     *
     * @param s
     */
    @Override
    public void excuteSuccessCallBack(String s) {
        pullToRefreshListView.onRefreshComplete();
        try {
            JSONObject jsonObject = new JSONObject(s);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                String result = jsonObject.getString("result");
                JSONArray array = new JSONArray(result);
                String date = array.get(0).toString();
                JSONObject jsonresult = new JSONObject(date);
                String items = jsonresult.getString("Items");
                if (mIsUp == false) {
                    smsRecordEntityListViewDataAdapter.removeAll();
                } else if (mIsUp == true) {
                    // 上拉加载，不清空 listViewDataAdapter
                }
                showOrGone();
                JSONArray array_ = new JSONArray(items);
                ll_visibility.setVisibility(View.VISIBLE);
                if (array_ != null && array_.length() > 0) {
                    for (int i = 0; i < array_.length(); i++) {
                        JSONObject jsonData = array_.getJSONObject(i);
                        String id = jsonData.getString("ID");
                        String title = jsonData.getString("TmpTitle");
                        String content = jsonData.getString("Content");
                        String time = jsonData.getString("CreateTime");
                        SmsRecordEntity smsRecordEntity = new SmsRecordEntity(id, content, time, title);
                        smsRecordEntityListViewDataAdapter.append(smsRecordEntity);
                    }
                } else {
                    showOrGone();
                    rl_none.setVisibility(View.VISIBLE);
                }
            } else if (status.equals("0")) {
                MyToast.show(SendSmsRecordActivity.this, jsonObject.getString("message"));
                if (jsonObject.getString("message").equals("请登录")) {
                    openActivity(LoginActivity.class);
                    finish();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 失败回调
     *
     * @param s
     */
    @Override
    public void excuteFailedCallBack(String s) {
        MyToast.show(SendSmsRecordActivity.this, "请求错误");
    }


    /**
     * @param itemData
     */
    @Override
    public void onListItemClick(SmsRecordEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("msgId", itemData.getId());
        openActivity(SendRecordDetialActivity.class, bundle);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recrodPresenter.dettach();
    }

    /**
     * 异步取消刷新
     */
    private class CancleRefreshTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pullToRefreshListView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }
}
