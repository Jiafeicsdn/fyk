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
import com.lvgou.distribution.entity.CashRecordEntitiy;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.CashRecordViewHolder;
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
 * Created by Ssnow on 2016/3/10 0010.
 * 提现记录
 */
public class CashRecordActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_tile;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.rl_none)
    private RelativeLayout ll_none;
    @ViewInject(R.id.rl_order)
    private RelativeLayout rl_order;
    @ViewInject(R.id.rl_make_any)
    private RelativeLayout rl_make_any;
    @ViewInject(R.id.tv_order)
    private TextView tv_order;
    @ViewInject(R.id.tv_make_any)
    private TextView tv_make_any;
    @ViewInject(R.id.view_order)
    private View view_order;
    @ViewInject(R.id.view_make_any)
    private View view_make_any;
    private ListView lv_list;
    private String distributorid = "";
    private int pageindex = 1;
    private String sign = "";
    private String ParentID = "";
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private int total_page_one;

    private String state = "0";// 0 表示 订单 1  随时赚
    private ListViewDataAdapter<CashRecordEntitiy> payDetialEntityListViewDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_record);
        ViewUtils.inject(this);
        tv_tile.setText("提现记录");

        distributorid = PreferenceHelper.readString(CashRecordActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        ParentID = PreferenceHelper.readString(CashRecordActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID);
        lv_list = pullToRefreshListView.getRefreshableView();
        initSelect();
        tv_order.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        view_order.setVisibility(View.VISIBLE);

        initViewHolder();
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            String sign = TGmd5.getMD5(distributorid + pageindex);
            if (checkNet()) {
                getData(distributorid, pageindex + "", sign);
            }
        }

        initCreateView();
    }

    @OnClick({R.id.rl_back, R.id.rl_order, R.id.rl_make_any})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_order:
                initSelect();
                state = "0";
                pageindex = 1;
                payDetialEntityListViewDataAdapter.removeAll();
                tv_order.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                view_order.setVisibility(View.VISIBLE);
                String sign = TGmd5.getMD5(distributorid + pageindex);
                getData(distributorid, pageindex + "", sign);
                break;
            case R.id.rl_make_any:
                initSelect();
                state = "1";
                pageindex = 1;
                payDetialEntityListViewDataAdapter.removeAll();
                tv_make_any.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                view_make_any.setVisibility(View.VISIBLE);
                String sign_ = TGmd5.getMD5(distributorid + pageindex);
                getDataOne(distributorid, pageindex + "", sign_);
                break;
        }
    }

    public void initSelect() {
        tv_order.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_make_any.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        view_order.setVisibility(View.GONE);
        view_make_any.setVisibility(View.GONE);
    }

    /**
     * main 代码抽取
     */
    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(CashRecordActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                if (state.equals("0")) {
                    pageindex = 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    getData(distributorid, pageindex + "", sign);
                } else if (state.equals("1")) {
                    pageindex = 1;
                    String sign_ = TGmd5.getMD5(distributorid + pageindex);
                    getDataOne(distributorid, pageindex + "", sign_);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (state.equals("0")) {
                    if (pageindex < total_page) {
                        pageindex += 1;
                        String sign = TGmd5.getMD5(distributorid + pageindex);
                        getData(distributorid, pageindex + "", sign);
                    } else {
                        MyToast.show(CashRecordActivity.this, "没有更多数据");
                        new CancleRefreshTask().execute();
                    }
                } else if (state.equals("1")) {
                    if (pageindex < total_page_one) {
                        pageindex += 1;
                        String sign = TGmd5.getMD5(distributorid + pageindex);
                        getDataOne(distributorid, pageindex + "", sign);
                    } else {
                        MyToast.show(CashRecordActivity.this, "没有更多数据");
                        new CancleRefreshTask().execute();
                    }
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void initViewHolder() {
        payDetialEntityListViewDataAdapter = new ListViewDataAdapter<CashRecordEntitiy>();
        payDetialEntityListViewDataAdapter.setViewHolderClass(this, CashRecordViewHolder.class);
        lv_list.setAdapter(payDetialEntityListViewDataAdapter);

    }

    /**
     * 隐藏
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        ll_none.setVisibility(View.GONE);
    }


    /**
     * 提现记录
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getData(String distributorid, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getWithwordsRecord(CashRecordActivity.this, maps, new OnRequestListener());

    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String paystr = array.get(0).toString();
                    JSONObject jsonObject_ = new JSONObject(paystr);
                    String item_ = jsonObject_.getString("Items");
                    total_page = jsonObject_.getInt("TotalPages");
                    JSONArray array_ = new JSONArray(item_);
                    if (mIsUp == false) {
                        payDetialEntityListViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {
                        //
                    }
                    showOrGone();
                    if (array_ != null && array_.length() > 0) {
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_.length(); i++) {
                            JSONObject jsonItem = array_.getJSONObject(i);
                            String id_ = jsonItem.getString("ID");
                            String Price = jsonItem.getString("Price");
                            String State = jsonItem.getString("State");
                            String BankTypeName = jsonItem.getString("BankTypeName");
                            String CardNO = jsonItem.getString("CardNO");
                            String CardName = jsonItem.getString("CardName");
                            String CreateTime = jsonItem.getString("CreateTime");
                            String PayTime = jsonItem.getString("PayTime");
                            CashRecordEntitiy cashRecordEntitiy = new CashRecordEntitiy(id_, Double.valueOf(Price) + "", CreateTime, PayTime, CardName, State, BankTypeName, CardNO);
                            payDetialEntityListViewDataAdapter.append(cashRecordEntitiy);
                        }
                    } else {
                        showOrGone();
                        ll_none.setVisibility(View.VISIBLE);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(CashRecordActivity.this, jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("请登录")) {
                        openActivity(LoginActivity.class);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(CashRecordActivity.this, "");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            closeLoadingProgressDialog();
            pullToRefreshListView.onRefreshComplete();
        }
    }

    /**
     * 提现记录
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getDataOne(String distributorid, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getTiXian(CashRecordActivity.this, maps, new OnOneRequestListener());

    }

    private class OnOneRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String paystr = array.get(0).toString();
                    JSONObject jsonObject_ = new JSONObject(paystr);
                    String item_ = jsonObject_.getString("Items");
                    total_page_one = jsonObject_.getInt("TotalPages");
                    JSONArray array_ = new JSONArray(item_);
                    if (mIsUp == false) {
                        payDetialEntityListViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {
                        //
                    }
                    showOrGone();
                    if (array_ != null && array_.length() > 0) {
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_.length(); i++) {
                            JSONObject jsonItem = array_.getJSONObject(i);
                            String id_ = jsonItem.getString("ID");
                            String Price = jsonItem.getString("Price");
                            String State = jsonItem.getString("State");
                            String BankTypeName = jsonItem.getString("BankTypeName");
                            String CardNO = jsonItem.getString("CardNO");
                            String CardName = jsonItem.getString("CardName");
                            String CreateTime = jsonItem.getString("CreateTime");
                            String PayTime = jsonItem.getString("PayTime");
                            CashRecordEntitiy cashRecordEntitiy = new CashRecordEntitiy(id_, Double.valueOf(Price) + "", CreateTime, PayTime, CardName, State, BankTypeName, CardNO);
                            payDetialEntityListViewDataAdapter.append(cashRecordEntitiy);
                        }
                    } else {
                        showOrGone();
                        ll_none.setVisibility(View.VISIBLE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(CashRecordActivity.this, "");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            pullToRefreshListView.onRefreshComplete();
            closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            pullToRefreshListView.onRefreshComplete();
            closeLoadingProgressDialog();
        }
    }

    /**
     * 异步取消刷新
     */
    private class CancleRefreshTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
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
