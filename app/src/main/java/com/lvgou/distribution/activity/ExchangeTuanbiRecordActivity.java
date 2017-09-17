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
import com.lvgou.distribution.entity.ExchangeTuanbiRecorderEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.TuanbiExchangePresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.ExchangeTunabiRecorderViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/3.
 * 团币兑换记录
 */
public class ExchangeTuanbiRecordActivity extends BaseActivity implements GroupView, OnListItemClickListener<ExchangeTuanbiRecorderEntity> {


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

    private ListView lv_list;

    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private ListViewDataAdapter<ExchangeTuanbiRecorderEntity> exchangeTuanbiRecorderEntityListViewDataAdapter;

    private TuanbiExchangePresenter tuanbiExchangePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);
        ViewUtils.inject(this);
        tv_title.setText("兑换记录");

        distributorid = PreferenceHelper.readString(ExchangeTuanbiRecordActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        lv_list = pullToRefreshListView.getRefreshableView();

        tuanbiExchangePresenter = new TuanbiExchangePresenter(this);

        initCreateView();
        initViewHolder();
        showLoadingProgressDialog(ExchangeTuanbiRecordActivity.this, "");
        String sign = TGmd5.getMD5(distributorid + pageindex);
        tuanbiExchangePresenter.getExchangelogList(distributorid, pageindex + "", sign);
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
                String label = DateUtils.formatDateTime(ExchangeTuanbiRecordActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                tuanbiExchangePresenter.getExchangelogList(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    tuanbiExchangePresenter.getExchangelogList(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(ExchangeTuanbiRecordActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    public void initViewHolder() {
        exchangeTuanbiRecorderEntityListViewDataAdapter = new ListViewDataAdapter<ExchangeTuanbiRecorderEntity>();
        exchangeTuanbiRecorderEntityListViewDataAdapter.setViewHolderClass(this, ExchangeTunabiRecorderViewHolder.class);
        ExchangeTunabiRecorderViewHolder.setExchangeTuanbiRecorderEntityOnListItemClickListener(this);
        lv_list.setAdapter(exchangeTuanbiRecorderEntityListViewDataAdapter);
    }


    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }


    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 7:
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();
                        JSONObject jsonObject_data = new JSONObject(data);
                        total_page = jsonObject_data.getInt("TotalPages");
                        String items_ = jsonObject_data.getString("Items");
                        if (mIsUp == false) {
                            exchangeTuanbiRecorderEntityListViewDataAdapter.removeAll();
                        } else if (mIsUp == true) {
                            // 上拉加载，不清空 listViewDataAdapter
                        }
                        JSONArray array_one = new JSONArray(items_);
                        if (array_one != null && array_one.length() > 0) {
                            showOrGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_one.length(); i++) {
                                JSONObject jsonObject_item = array_one.getJSONObject(i);
                                String id = jsonObject_item.getString("ID");
                                String CreateTime = jsonObject_item.getString("CreateTime");
                                String State = jsonObject_item.getString("State");
                                String FullAddress = jsonObject_item.getString("FullAddress");
                                String TuanBi = jsonObject_item.getString("TuanBi");
                                String amount = jsonObject_item.getString("Amount");
                                String title = jsonObject_item.getString("ProductName");
                                String DistributorID = jsonObject_item.getString("DistributorID");
                                ExchangeTuanbiRecorderEntity exchangeTuanbiRecorderEntity = new ExchangeTuanbiRecorderEntity(id, CreateTime, State, FullAddress, title, DistributorID, amount, TuanBi);
                                exchangeTuanbiRecorderEntityListViewDataAdapter.append(exchangeTuanbiRecorderEntity);
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

    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 7:
                MyToast.show(ExchangeTuanbiRecordActivity.this, "请求失败");
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
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    /**
     * tiem  点击事件
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(ExchangeTuanbiRecorderEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("productId", itemData.getId());
        openActivity(ExchangeTuanbiRecordDetialActivity.class, bundle);
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
