package com.lvgou.distribution.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.ExtensionProfitAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.PayDetialEntity;
import com.lvgou.distribution.entity.ProfitEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.PayDetialViewHolder;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Snow on 2016/3/10
 * 收支明细
 */
public class PayDetailActivity extends BaseActivity implements OnListItemClickListener<PayDetialEntity> {

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

    @ViewInject(R.id.pull_refresh_list_make_any)
    private PullToRefreshListView pullToRefreshListView_make_any;
    @ViewInject(R.id.ll_visibilty_make_any)
    private LinearLayout ll_visibilty_make_any;
    @ViewInject(R.id.rl_none_make_any)
    private RelativeLayout rl_none_make_any;
    @ViewInject(R.id.rl_withdrawals)
    private RelativeLayout rl_withdrawals;
    @ViewInject(R.id.tv_withdrawals_money)
    private TextView tv_withdrawals_money;
    @ViewInject(R.id.tv_ketixian)
    private TextView tv_ketixian;
    @ViewInject(R.id.ll_order)
    private LinearLayout ll_order;
    @ViewInject(R.id.rl_make_any_one)
    private RelativeLayout rl_make_any_one;
    @ViewInject(R.id.ll_orde_detial)
    private LinearLayout ll_order_detial;
    @ViewInject(R.id.tv_totle)
    private TextView tv_total;
    @ViewInject(R.id.tv_yongjin)
    private TextView tv_comssion;
    @ViewInject(R.id.tv_status)
    private TextView tv_status;
    @ViewInject(R.id.tv_order_num)
    private TextView tv_order_num;
    @ViewInject(R.id.tv_create_time)
    private TextView tv_create_time;
    @ViewInject(R.id.tv_tixian_time)
    private TextView tv_tixian_time;
    @ViewInject(R.id.rl_tixian)
    private RelativeLayout rl_tixian;
    @ViewInject(R.id.btn_seek)
    private Button btn_seek;
    @ViewInject(R.id.btn_go_list)
    private Button btn_go_list;
    @ViewInject(R.id.tv_withdrawals_no_money)
    private TextView tv_withdrawals_no_money;
    @ViewInject(R.id.ll_01)
    private LinearLayout ll_01;

    private ListView listView_make_any;
    private ListView lv_list;

    private String distributorid = "";
    private int pageindex = 1;
    private String sign = "";
    private String ParentID = "";
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private ListViewDataAdapter<PayDetialEntity> payDetialEntityListViewDataAdapter;
    /**
     * 随时赚 页面 所需数据
     */
    boolean mIsUp_make;// 是否上拉加载
    private int pageInex_make = 1;
    private int total_pages_make;
    private ExtensionProfitAdapter extensionProfitAdapter;
    private List<ProfitEntity> profitEntityLists;
    private String tixian_price;
    private String name;
    private String account_name;
    private String account_num;
    /**
     * 订单详情页面所需数据
     */
    private String incomeid;
    private String sellerID;
    private String order_num;
    private String detial_data;

    private String go_back = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_detail);
        ViewUtils.inject(this);
        tv_tile.setText("收益明细");
        go_back = getTextFromBundle("selection_postion");
        distributorid = PreferenceHelper.readString(PayDetailActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        ParentID = PreferenceHelper.readString(PayDetailActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID);
        lv_list = pullToRefreshListView.getRefreshableView();
        listView_make_any = pullToRefreshListView_make_any.getRefreshableView();
        initSelect();
        profitEntityLists = new ArrayList<ProfitEntity>();
        if (go_back.equals("1")) {
            rl_make_any_one.setVisibility(View.VISIBLE);
            tv_make_any.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
            view_make_any.setVisibility(View.VISIBLE);
            initViewHolder();
            if (distributorid.equals("") || distributorid.equals("null")) {
                openActivity(LoginActivity.class);
                finish();
            } else {
                if (checkNet()) {
                    String sign_ = TGmd5.getMD5(distributorid + pageInex_make);
                    getDataMake(distributorid, pageInex_make + "", sign_);
                }
            }
        } else if (go_back.equals("0")) {
            ll_order.setVisibility(View.VISIBLE);
            tv_order.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
            view_order.setVisibility(View.VISIBLE);
            initViewHolder();
            if (distributorid.equals("") || distributorid.equals("null")) {
                openActivity(LoginActivity.class);
                finish();
            } else {
                if (checkNet()) {
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    getData(distributorid, pageindex + "", sign);
                }
            }
        }
        initCreateView();

    }

    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isback) {
                isback = false;
                ll_order.setVisibility(View.VISIBLE);
                ll_order_detial.setVisibility(View.GONE);
            } else {
                PayDetailActivity.this.finish();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @OnClick({R.id.rl_back, R.id.rl_order, R.id.rl_make_any, R.id.rl_withdrawals, R.id.btn_seek, R.id.btn_go_list})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                Log.e("kashkjhas", "------------" + isback);
                if (isback) {
                    isback = false;
                    ll_order.setVisibility(View.VISIBLE);
                    ll_order_detial.setVisibility(View.GONE);
                } else {
                    PayDetailActivity.this.finish();
                }

                break;
            case R.id.rl_order:
                initSelect();
                ll_order.setVisibility(View.VISIBLE);
                tv_order.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                view_order.setVisibility(View.VISIBLE);
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                getData(distributorid, pageindex + "", sign);
                break;
            case R.id.rl_make_any:
                initSelect();
                rl_make_any_one.setVisibility(View.VISIBLE);
                tv_make_any.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                view_make_any.setVisibility(View.VISIBLE);
                pageInex_make = 1;
                String sign_ = TGmd5.getMD5(distributorid + pageInex_make);
                getDataMake(distributorid, pageInex_make + "", sign_);
                break;
            case R.id.rl_withdrawals:
                if (Float.parseFloat(tixian_price) > 0) {
                    bundle.putString("index", "1");
                    openActivity(MyProfitActivity.class, bundle);
                }
                break;
            case R.id.btn_seek:
                bundle.putString("detial_list", detial_data);
                openActivity(OrderCentralDetialActivity.class, bundle);
                break;
            case R.id.btn_go_list:
                ll_order.setVisibility(View.VISIBLE);
                ll_order_detial.setVisibility(View.GONE);
                break;
        }
    }

    public void initSelect() {
        tv_order.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_make_any.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        view_order.setVisibility(View.GONE);
        view_make_any.setVisibility(View.GONE);
        ll_order.setVisibility(View.GONE);
        rl_make_any_one.setVisibility(View.GONE);
        ll_order_detial.setVisibility(View.GONE);
    }

    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(PayDetailActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                getData(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    getData(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(PayDetailActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });


        pullToRefreshListView_make_any.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView_make_any.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView_make_any.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView_make_any.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(PayDetailActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView_make_any.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp_make = false;
                pageInex_make = 1;
                String sign = TGmd5.getMD5(distributorid + pageInex_make);
                getDataMake(distributorid, pageInex_make + "", sign);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp_make = true;
                if (pageInex_make < total_pages_make) {
                    pageInex_make += 1;
                    String sign = TGmd5.getMD5(distributorid + pageInex_make);
                    getDataMake(distributorid, pageInex_make + "", sign);
                } else {
                    MyToast.show(PayDetailActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    public void initViewHolder() {
        payDetialEntityListViewDataAdapter = new ListViewDataAdapter<PayDetialEntity>();
        payDetialEntityListViewDataAdapter.setViewHolderClass(this, PayDetialViewHolder.class);
        PayDetialViewHolder.setPayDetialEntityOnListItemClickListener(this);
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
     * 有无数据页面显示
     */
    public void showOrGoneMake() {
        ll_visibilty_make_any.setVisibility(View.GONE);
        rl_none_make_any.setVisibility(View.GONE);
    }

    /**
     * 获取数据
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
        RequestTask.getInstance().getUserIncome(PayDetailActivity.this, maps, new OnRequestListener());

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
                    total_page = jsonObject_.getInt("TotalPages");
                    String item_ = jsonObject_.getString("Items");
                    JSONArray array_item = new JSONArray(item_);
                    if (mIsUp == false) {
                        payDetialEntityListViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {
                        //不作处理
                    }
                    showOrGone();
                    if (array_item != null && array_item.length() > 0) {
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_item.length(); i++) {
                            JSONObject json_item = array_item.getJSONObject(i);
                            String id = json_item.getString("ID");
                            String time = json_item.getString("CreateTime");
                            String orderNo = json_item.getString("OrderNO");
                            String SellerID = json_item.getString("SellerID");
                            if (ParentID.equals("0")) {
                                String status_ = json_item.getString("StateLXS");
                                double money_ = json_item.getDouble("PriceLXS");
                                PayDetialEntity payDetialEntity = new PayDetialEntity(id, orderNo, time, money_ + "", status_, SellerID);
                                payDetialEntityListViewDataAdapter.append(payDetialEntity);
                            } else {
                                String status_ = json_item.getString("StateDY");
                                double money_ = json_item.getDouble("PriceDY");
                                PayDetialEntity payDetialEntity = new PayDetialEntity(id, orderNo, time, money_ + "", status_, SellerID);
                                payDetialEntityListViewDataAdapter.append(payDetialEntity);
                            }
                        }
                    } else {
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
            showLoadingProgressDialog(PayDetailActivity.this, "");
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
     * 获取随时赚数据
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getDataMake(String distributorid, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);

        RequestTask.getInstance().getProfit(PayDetailActivity.this, maps, new OnOneRequestListener());
    }

    private class OnOneRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            pullToRefreshListView_make_any.onRefreshComplete();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            pullToRefreshListView_make_any.onRefreshComplete();
        }

        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array_ = new JSONArray(result);
                    String data = array_.get(0).toString();
                    JSONObject json_data = new JSONObject(data);
                    total_pages_make = json_data.getInt("TotalPages");
                    String items = json_data.getString("Items");
                    if (mIsUp_make == false) {
                        profitEntityLists.clear();
                    } else if (mIsUp_make == true) {
                        // 上拉加载，不清空 listViewDataAdapter
                    }
                    showOrGoneMake();
                    JSONArray array_items = new JSONArray(items);
                    if (array_items != null && array_items.length() > 0) {
                        ll_visibilty_make_any.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_items.length(); i++) {
                            JSONObject json_items = array_items.getJSONObject(i);
                            String id = json_items.getString("ID");
                            String tiem_ = json_items.getString("CreateTime");
                            String supply_name = json_items.getString("SupplyName");
                            String weixin = json_items.getString("WeiXin");
                            String user_type = json_items.getString("Sate_TG");
                            String state = json_items.getString("State_Pay");
                            String price = json_items.getString("Price_Distributor");
                            ProfitEntity profitEntity = new ProfitEntity(id, state, price, user_type, weixin, supply_name, tiem_);
                            profitEntityLists.add(profitEntity);
                        }
                        extensionProfitAdapter = new ExtensionProfitAdapter(PayDetailActivity.this, profitEntityLists);
                        listView_make_any.setAdapter(extensionProfitAdapter);
                    } else {
                        showOrGone();
                        rl_none_make_any.setVisibility(View.VISIBLE);
                    }
                    // 提现数据解析
                    tixian_price = array_.get(1).toString();
                    account_name = array_.get(2).toString();
                    account_num = array_.get(3).toString();
                    name = array_.get(4).toString();
                    DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                    if (Float.parseFloat(tixian_price) > 0) {
                        tv_ketixian.setVisibility(View.VISIBLE);
                        tv_withdrawals_money.setVisibility(View.VISIBLE);
                        tv_withdrawals_no_money.setVisibility(View.GONE);
                        tv_withdrawals_money.setText("¥" + decimalFormat.format(Float.parseFloat(tixian_price)));
                        tv_withdrawals_money.setTextColor(getResources().getColor(R.color.bg_code_number));
                        ll_01.setBackgroundResource(R.mipmap.bg_profit_bottom);
                    } else {
                        tv_withdrawals_no_money.setVisibility(View.VISIBLE);
                        tv_ketixian.setVisibility(View.GONE);
                        tv_ketixian.setVisibility(View.GONE);
                        tv_withdrawals_money.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
                        ll_01.setBackgroundResource(R.mipmap.bg_profit_bottom_gray);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(PayDetailActivity.this, jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("请登录")) {
                        openActivity(LoginActivity.class);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取订单详情数据
     *
     * @param distributorid
     * @param incomeid
     * @param sign
     */
    public void getDataDetial(String distributorid, String incomeid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("incomeid", incomeid);
        maps.put("sign", sign);
        RequestTask.getInstance().getIncomeDetial(PayDetailActivity.this, maps, new OnTwoRequestListener());
    }

    private class OnTwoRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                String result = jsonObject.getString("result");
                if (status.equals("1")) {
                    JSONArray array = new JSONArray(result);
                    String item = array.get(0).toString();
                    String status_ = array.get(1).toString();
                    JSONObject object = new JSONObject(item);
                    order_num = object.getString("OrderNO");
                    String price_total = object.getString("Price");
                    String createTime = object.getString("CreateTime");
                    sellerID = object.getString("SellerID");
                    String[] str = createTime.split("T");
                    String[] str_ = str[1].split(":");
                    tv_create_time.setText(str[0] + " " + str_[0] + ":" + str_[1]);
                    tv_order_num.setText(order_num);
                    DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                    tv_total.setText("¥" + decimalFormat.format(Float.parseFloat(price_total)));
                    // 0=可提现，1=申请中，2=已结算，3=锁定中，4=已取消
                    if (status_.equals("0")) {// 旅行社
                        String lxs_price = object.getString("PriceLXS");
                        String lxs_state = object.getString("StateLXS");
                        String out_lxs = object.getString("OutTimeLXS");
                        tv_comssion.setText("¥" + decimalFormat.format(Float.parseFloat(lxs_price)));
                        if (lxs_state.equals("2")) {
                            String[] str1 = out_lxs.split("T");
                            String[] str_1 = str1[1].split(":");
                            rl_tixian.setVisibility(View.VISIBLE);
                            tv_status.setText("已结算");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_text_black));
                            tv_tixian_time.setText(str1[0] + " " + str_1[0] + ":" + str_1[1]);
                        } else if (lxs_state.equals("0")) {
                            tv_status.setText("可提现");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_blue_cose));
                        } else if (lxs_state.equals("1")) {
                            tv_status.setText("申请中");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_button));
                        } else if (lxs_state.equals("3")) {
                            tv_status.setText("锁定中");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_button));
                        } else if (lxs_state.equals("4")) {
                            tv_status.setText("已取消");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_text_black));
                        }
                    } else {
                        String dy_price_ = object.getString("PriceDY");
                        String dy_state_ = object.getString("StateDY");
                        String out_dy = object.getString("OutTimeDY");
                        tv_comssion.setText("¥" + decimalFormat.format(Float.parseFloat(dy_price_)));
                        if (dy_state_.equals("2")) {
                            String[] str1 = out_dy.split("T");
                            String[] str_1 = str1[1].split(":");
                            rl_tixian.setVisibility(View.VISIBLE);
                            tv_status.setText("已结算");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_text_black));
                            tv_tixian_time.setText(str1[0] + " " + str_1[0] + ":" + str_1[1]);
                        } else if (dy_state_.equals("0")) {
                            tv_status.setText("可提现");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_blue_cose));
                        } else if (dy_state_.equals("1")) {
                            tv_status.setText("申请中");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_button));
                        } else if (dy_state_.equals("3")) {
                            tv_status.setText("锁定中");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_button));
                        } else if (dy_state_.equals("4")) {
                            tv_status.setText("已取消");
                            tv_status.setTextColor(getResources().getColor(R.color.bg_text_black));
                        }
                    }
                    // 获取详情数据
                    String sign_ = TGmd5.getMD5(distributorid + order_num + sellerID);
                    getSeekDetial(distributorid, order_num, sellerID, sign_);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // 查看订单详情数据
    public void getSeekDetial(String distributorid, String orderno, String sellerid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("orderno", orderno);
        maps.put("sellerid", sellerid);
        maps.put("sign", sign);
        RequestTask.getInstance().seekIncomeDetial(PayDetailActivity.this, maps, new OnSeekRequestListener());

    }

    private class OnSeekRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    detial_data = array.get(0).toString();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onListItemClick(PayDetialEntity itemData) {
        incomeid = itemData.getId();
        ll_order.setVisibility(View.GONE);
        ll_order_detial.setVisibility(View.VISIBLE);
        isback = true;
        String sign = TGmd5.getMD5(distributorid + incomeid);
        getDataDetial(distributorid, incomeid, sign);
    }

    private boolean isback = false;


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
            pullToRefreshListView_make_any.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }
}
