package com.lvgou.distribution.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.SendGroupRecorderEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.SendGroupRecordPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.SendGroupRecordViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snow on 2016/10/14.
 * 带团记录
 */
public class SendGroupRecordActivity extends BaseActivity implements GroupView, OnListItemClickListener<SendGroupRecorderEntity> {


    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;


    private ListView listView;
    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求


    private ListViewDataAdapter<SendGroupRecorderEntity> sendGroupRecorderEntityListViewDataAdapter;


    private SendGroupRecordPresenter sendGroupRecordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);
        ViewUtils.inject(this);
        tv_title.setText("派团记录");

        sendGroupRecordPresenter = new SendGroupRecordPresenter(this);

        distributorid = PreferenceHelper.readString(SendGroupRecordActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);


        listView = pullToRefreshListView.getRefreshableView();

        initViewHolder();

        initCreateView();

        if (checkNet()) {
            showLoadingProgressDialog(SendGroupRecordActivity.this, "");
            String sign = TGmd5.getMD5(distributorid + pageindex);
            sendGroupRecordPresenter.getSendGroupRecord(distributorid, pageindex + "", sign);
        }


    }


    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }


    public void initViewHolder() {
        sendGroupRecorderEntityListViewDataAdapter = new ListViewDataAdapter<SendGroupRecorderEntity>();
        sendGroupRecorderEntityListViewDataAdapter.setViewHolderClass(this, SendGroupRecordViewHolder.class);
        SendGroupRecordViewHolder.setOnListItemClickListener(this);
        listView.setAdapter(sendGroupRecorderEntityListViewDataAdapter);
    }

    /**
     * 下拉刷新 抽取
     */
    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(SendGroupRecordActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                sendGroupRecordPresenter.getSendGroupRecord(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    sendGroupRecordPresenter.getSendGroupRecord(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(SendGroupRecordActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();

                        JSONObject jsonObject_data = new JSONObject(data);
                        total_page = jsonObject_data.getInt("TotalPages");
                        String items = jsonObject_data.getString("Items");
                        if (mIsUp == false) {
                            sendGroupRecorderEntityListViewDataAdapter.removeAll();
                        } else {

                        }
                        JSONArray array_items = new JSONArray(items);
                        if (array_items != null && array_items.length() > 0) {
                            showOrGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_items.length(); i++) {
                                JSONObject jsonObject_one = array_items.getJSONObject(i);
                                String id = jsonObject_one.getString("ID");
                                String title = jsonObject_one.getString("Title");
                                String TravelTime = jsonObject_one.getString("TravelTime");
                                String Day = jsonObject_one.getString("Day");
                                String city = jsonObject_one.getString("CountryName");
                                String Destination = jsonObject_one.getString("Destination");
                                String Sex = jsonObject_one.getString("Sex");
                                String Price = jsonObject_one.getString("Price");
                                String Grade = jsonObject_one.getString("Grade");
                                String State = jsonObject_one.getString("State");
                                String DistributorID_ = jsonObject_one.getString("DistributorID");
                                SendGroupRecorderEntity sendGroupRecorderEntity = new SendGroupRecorderEntity(id, title, State, TravelTime, Day, city, Destination, Sex, Price, DistributorID_, distributorid, Grade, "");
                                sendGroupRecorderEntityListViewDataAdapter.append(sendGroupRecorderEntity);
                            }
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                MyToast.show(SendGroupRecordActivity.this, "请求失败");
                break;
        }
    }


    @Override
    public void closeProgress() {
       closeLoadingProgressDialog();
    }

    @Override
    public void showProgress() {

    }


    /**
     * item 点击事件
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(SendGroupRecorderEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("seekid", itemData.getId());
        bundle.putString("groupType", "1");
        openActivity(PublishGroupDetialActivity.class, bundle);
    }


    @Override
    protected void onResume() {
        super.onResume();
        sendGroupRecordPresenter.attach(this);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendGroupRecordPresenter.dettach();
        MobclickAgent.onPause(this);
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
