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
import com.lvgou.distribution.entity.OrderCentralEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.OrderCentralViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/3/14 0014.
 * 订单中心
 */
public class OrderCentralActivity extends BaseActivity implements OnListItemClickListener<OrderCentralEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_all)
    private RelativeLayout rl_all;
    @ViewInject(R.id.rl_paid)
    private RelativeLayout rl_paid;
    @ViewInject(R.id.rl_send)
    private RelativeLayout rl_send;
    @ViewInject(R.id.rl_send_already)
    private RelativeLayout rl_send_already;
    @ViewInject(R.id.rl_success)
    private RelativeLayout rl_success;
    @ViewInject(R.id.view_all)
    private View view_all;
    @ViewInject(R.id.view_paid)
    private View view_paid;
    @ViewInject(R.id.view_send)
    private View view_send;
    @ViewInject(R.id.view_send_already)
    private View view_send_already;
    @ViewInject(R.id.view_success)
    private View view_success;
    @ViewInject(R.id.tv_total)
    private TextView tv_total;
    @ViewInject(R.id.tv_paid)
    private TextView tv_paid;
    @ViewInject(R.id.tv_send)
    private TextView tv_send;
    @ViewInject(R.id.tv_send_already)
    private TextView tv_send_already;
    @ViewInject(R.id.tv_success)
    private TextView tv_success;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;
    @ViewInject(R.id.rl_tixian)
    private RelativeLayout rl_tixian;
    @ViewInject(R.id.tv_profit)
    private TextView tv_profit;
    @ViewInject(R.id.tv_tixian)
    private TextView tv_tixian;
    private ListView lv_list;


    private ListViewDataAdapter<OrderCentralEntity> orderCentralEntityListViewDataAdapter;

    private String type = "5";
    private String distributorid = "";
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private String index = "0";// 返回主页
    private String tab = "0";// 全部，待发货

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_centre);
        ViewUtils.inject(this);
        tv_title.setText("订单中心");
        distributorid = PreferenceHelper.readString(OrderCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        lv_list = pullToRefreshListView.getRefreshableView();
        initViewHolder();
        initViewLine();

        index = getTextFromBundle("index");
        tab = getTextFromBundle("tab");
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            String sign = TGmd5.getMD5(distributorid + tab + "1");
            getData(distributorid, tab, "1", sign);
            initViewLine();
            if ("1".equals(tab)) {
                view_paid.setVisibility(View.VISIBLE);
                tv_paid.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
            } else if ("2".equals(tab)) {
                view_send.setVisibility(View.VISIBLE);
                tv_send.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
            } else if ("3".equals(tab)) {
                view_send_already.setVisibility(View.VISIBLE);
                tv_send_already.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
            } else if ("4".equals(tab)) {
                view_success.setVisibility(View.VISIBLE);
                tv_success.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
            } else if ("5".equals(tab)) {
                view_all.setVisibility(View.VISIBLE);
                tv_total.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
            }
        }
        initCreateView();
    }

    @OnClick({R.id.rl_back, R.id.rl_all, R.id.rl_paid, R.id.rl_send, R.id.rl_send_already, R.id.rl_success, R.id.rl_tixian})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    bundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, bundle);
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.rl_all:
                type = "5";
                mIsUp = false;
                pageindex = 1;
                initViewLine();
                view_all.setVisibility(View.VISIBLE);
                tv_total.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                String sign = TGmd5.getMD5(distributorid + type + pageindex);
                getData(distributorid, type, pageindex + "", sign);
                if (!orderCentralEntityListViewDataAdapter.isEmpty()) {
                    lv_list.setSelection(0);
                }
                break;
            case R.id.rl_paid:
                type = "1";
                mIsUp = false;
                pageindex = 1;
                initViewLine();
                view_paid.setVisibility(View.VISIBLE);
                tv_paid.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                String sign1 = TGmd5.getMD5(distributorid + type + pageindex);
                getData(distributorid, type, pageindex + "", sign1);
                if (!orderCentralEntityListViewDataAdapter.isEmpty()) {
                    lv_list.setSelection(0);
                }
                break;
            case R.id.rl_send:
                type = "2";
                mIsUp = false;
                pageindex = 1;
                initViewLine();
                view_send.setVisibility(View.VISIBLE);
                tv_send.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                String sign2 = TGmd5.getMD5(distributorid + type + pageindex);
                getData(distributorid, type, pageindex + "", sign2);
                if (!orderCentralEntityListViewDataAdapter.isEmpty()) {
                    lv_list.setSelection(0);
                }
                break;
            case R.id.rl_send_already:
                type = "3";
                pageindex = 1;
                mIsUp = false;
                initViewLine();
                view_send_already.setVisibility(View.VISIBLE);
                tv_send_already.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                String sign3 = TGmd5.getMD5(distributorid + type + pageindex);
                getData(distributorid, type, pageindex + "", sign3);
                if (!orderCentralEntityListViewDataAdapter.isEmpty()) {
                    lv_list.setSelection(0);
                }
                break;
            case R.id.rl_success:
                type = "4";
                mIsUp = false;
                pageindex = 1;
                initViewLine();
                view_success.setVisibility(View.VISIBLE);
                tv_success.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                String sign4 = TGmd5.getMD5(distributorid + type + pageindex);
                getData(distributorid, type, pageindex + "", sign4);
                if (!orderCentralEntityListViewDataAdapter.isEmpty()) {
                    lv_list.setSelection(0);
                }
                break;
            case R.id.rl_tixian:
                bundle.putString("index", "1");
                openActivity(MyProfitActivity.class, bundle);
                break;
        }
    }

    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(OrderCentralActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + type + pageindex);
                getData(distributorid, type, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + type + pageindex);
                    getData(distributorid, type, pageindex + "", sign);
                } else {
                    MyToast.show(OrderCentralActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    /**
     * 初始化ViewHolder
     */
    public void initViewHolder() {
        orderCentralEntityListViewDataAdapter = new ListViewDataAdapter<OrderCentralEntity>();
        orderCentralEntityListViewDataAdapter.setViewHolderClass(this, OrderCentralViewHolder.class);
        OrderCentralViewHolder.setOnListItemClickListener(this);
        lv_list.setAdapter(orderCentralEntityListViewDataAdapter);
    }

    /**
     * 商品空数据显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    /**
     * 隐藏所有的view line
     */
    public void initViewLine() {
        tv_total.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_paid.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_send.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_send_already.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_success.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        view_all.setVisibility(View.GONE);
        view_paid.setVisibility(View.GONE);
        view_send.setVisibility(View.GONE);
        view_send_already.setVisibility(View.GONE);
        view_success.setVisibility(View.GONE);
        rl_tixian.setVisibility(View.GONE);
    }

    /**
     * 获取数据
     */
    public void getData(String distributorid, String orderstate, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("orderstate", orderstate);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getOrderList(OrderCentralActivity.this, maps, new OnRequestListener());
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
                    String result_str = array.get(0).toString();


                    JSONObject jsonObject_item = new JSONObject(result_str);
                    String items = jsonObject_item.getString("Items");
                    total_page = jsonObject_item.getInt("TotalPages");
                    JSONArray array_item = new JSONArray(items);
                    if (mIsUp == false) {
                        orderCentralEntityListViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {
                        // 不做处理
                    }
                    showOrGone();
                    if (array_item != null && array_item.length() > 0) {
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_item.length(); i++) {
                            JSONArray array_one = new JSONArray(array_item.get(i).toString());
                            int amout = 0;
                            float commission = 0;//佣金
                            String createTime = "";
                            String OrderNO = "";
                            String OrderState = "";
                            String Price_Total_Real = "";
                            String AgentRealName = "";
                            if (array_one != null && array_one.length() > 0) {
                                for (int j = 0; j < array_one.length(); j++) {
                                    JSONObject json_one = array_one.getJSONObject(j);
                                    createTime = json_one.getString("CreateTime");
                                    OrderNO = json_one.getString("OrderNO");
                                    AgentRealName = json_one.getString("AgentRealName");
                                    OrderState = json_one.getString("OrderState");
                                    Price_Total_Real = json_one.getString("Price_Total_Real");
                                    float Price_Real = Float.parseFloat(json_one.getString("Price_Real"));
                                    float Price_Agent = Float.parseFloat(json_one.getString("Price_Agent"));
                                    int Amount = json_one.getInt("Amount");
                                    float commission_ = (Price_Real - Price_Agent) * Amount;
                                    commission = commission + commission_;
                                    amout = amout + Amount;
                                }
                                OrderCentralEntity orderCentralEntity = new OrderCentralEntity(createTime, array_one.get(0).toString(), amout + "", Price_Total_Real, array_item.get(i).toString(), OrderState, OrderNO, AgentRealName, commission + "");
                                orderCentralEntityListViewDataAdapter.append(orderCentralEntity);
                                amout = 0;
                                commission = 0;
                            }
                        }
                    } else {
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(OrderCentralActivity.this, jsonObject.getString("message"));
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
            showLoadingProgressDialog(OrderCentralActivity.this, "");
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
     * item 点击事件
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(OrderCentralEntity itemData) {
        Bundle pBuddle = new Bundle();
        pBuddle.putString("detial_list", itemData.getOrder_list());
        openActivity(OrderCentralDetialActivity.class, pBuddle);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
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
