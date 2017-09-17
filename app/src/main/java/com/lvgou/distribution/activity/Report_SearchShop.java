package com.lvgou.distribution.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ReportInfoEntitiy;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.ReportSearchHistoryViewHolder;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
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
 * Created by Administrator on 2016/8/17.
 */
public class Report_SearchShop extends BaseActivity implements OnClassifyPostionClickListener<ReportInfoEntitiy> {
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.et_search02)
    private EditText et_search02;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pull_refresh_list;
    @ViewInject(R.id.img_search)
    private ImageView img_search;
    @ViewInject(R.id.ll_item_report1)
    private LinearLayout ll_item_report1;
    @ViewInject(R.id.tv_tittle_report1)
    private TextView tv_tittle_report1;
    @ViewInject(R.id.iv_item_report1)
    private ImageView iv_item_report1;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visiable;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;

    private String distributorid = "";
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int totalPages;
    public ListView listView;


    private ListViewDataAdapter<ReportInfoEntitiy> reportInfoEntitiyListViewDataAdapter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_shop);
        ViewUtils.inject(this);
        EventBus.getDefault().register(this);
        distributorid = PreferenceHelper.readString(Report_SearchShop.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        listView = pull_refresh_list.getRefreshableView();


        serachData();
        initCreateView();
        initViewHolder();
        String sign = TGmd5.getMD5(distributorid + pageindex);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                initData(distributorid, pageindex + "", sign);
            }
        }
    }


    /**
     * 初始化viewHolder
     */
    public void initViewHolder() {
        reportInfoEntitiyListViewDataAdapter = new ListViewDataAdapter<ReportInfoEntitiy>();
        reportInfoEntitiyListViewDataAdapter.setViewHolderClass(this, ReportSearchHistoryViewHolder.class);
        ReportSearchHistoryViewHolder.setOnListItemClickListener(this);
        listView.setAdapter(reportInfoEntitiyListViewDataAdapter);
    }


    /**
     * 空数据显示隐藏
     */
    public void showOrgone() {
        ll_visiable.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }


    /**
     * 搜索操作
     */
    public void serachData() {
        et_search02.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Bundle pBundle = new Bundle();
                pBundle.putString("key", et_search02.getText().toString().trim());
                openActivity(Report_SearchShop_Result.class, pBundle);
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                // 执行获取数据操作
                return true;
            }
        });

        et_search02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_search02.getText().length() == 0) {
                    img_search.setVisibility(View.VISIBLE);
                } else {
                    img_search.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initCreateView() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(Report_SearchShop.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pull_refresh_list.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                initData(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < totalPages) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    initData(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(Report_SearchShop.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    /**
     * 获取数据
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    private void initData(String distributorid, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getReport_shop(Report_SearchShop.this, maps, new OnReportRequestListener());
    }


    private class OnReportRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                int status = jsonObject.getInt("status");
                if (status == 1) {
                    String result = jsonObject.getString("result");
                    parseArr(result);
                } else {
                    MyToast.show(Report_SearchShop.this, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            pull_refresh_list.onRefreshComplete();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            pull_refresh_list.onRefreshComplete();
        }
    }


    public void parseArr(String result) {
        try {
            if (mIsUp == false) {
                reportInfoEntitiyListViewDataAdapter.removeAll();
            } else {

            }
            JSONArray jsonArray = new JSONArray(result);
            String stringArr = jsonArray.get(0).toString();
            JSONArray js = new JSONArray(stringArr);
            if (pageindex == 1) {
                for (int j = 0; j < js.length(); j++) {
                    JSONObject js6 = js.getJSONObject(j);
                    String id = js6.getString("ReportSellerID");
                    String shopName = js6.getString("ShopName");
                    String adderss = js6.getString("Adderss");
                    String business = js6.getString("Business");
                    String latitude = js6.getString("Latitude");
                    String longitude = js6.getString("Longitude");
                    ReportInfoEntitiy reportInfoEntitiy = new ReportInfoEntitiy(id, shopName, adderss, latitude, longitude, Constants.Latitude, Constants.Longitude, business, js.length() + "", "0");
                    reportInfoEntitiyListViewDataAdapter.append(reportInfoEntitiy);
                }
            }

            JSONObject js3 = new JSONObject(jsonArray.get(1).toString());
            totalPages = js3.getInt("TotalPages");
            String items1 = js3.getString("Items");
            JSONArray jsonArray1 = new JSONArray(items1);
            int m = 0;
            if (jsonArray1 != null && jsonArray1.length() > 0) {
                for (int k = 0; k < jsonArray1.length(); k++) {
                    JSONObject jsonObject3 = jsonArray1.getJSONObject(k);
                    int size = js.length();
                    String id = jsonObject3.getString("ID");
                    String shopName = jsonObject3.getString("ShopName");
                    String adderss = jsonObject3.getString("Adderss");
                    String business = jsonObject3.getString("Business");
                    String latitude = jsonObject3.getString("Latitude");
                    String longitude = jsonObject3.getString("Longitude");
                    ReportInfoEntitiy reportInfoEntitiy = new ReportInfoEntitiy(id, shopName, adderss, latitude, longitude, Constants.Latitude, Constants.Longitude, business, size + "", "1");
                    reportInfoEntitiyListViewDataAdapter.append(reportInfoEntitiy);
                }
            }
            /**
             * 只有两个数组都为空的时候，才显示空数据
             */
            if (js.length() == 0 && jsonArray1.length() == 0) {
                showOrgone();
                rl_none.setVisibility(View.VISIBLE);
            } else {
                showOrgone();
                ll_visiable.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.rl_back, R.id.rl_publish})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                Constants.IS_SHOW_ADD = "0";
                finish();
                break;
            case R.id.rl_publish:
                bundle.putString("index", "0");
                openActivity(Report_Shop_Location_Activity.class, bundle);
                break;
            default:
                break;
        }
    }

    /**
     * item 点击回调
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(ReportInfoEntitiy itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                bundle.putString("index", "1");
                bundle.putString("reportId", "0");
                bundle.putString("shop_name", itemData.getShopname());
                bundle.putString("reportSellerId", itemData.getId());
                openActivity(ReportShopActivity.class, bundle);
                break;
            case 2:
                try {
                    bundle.putString("index", "1");
                    bundle.putString("name", itemData.getShopname());
                    bundle.putString("lat", itemData.getLatitude());
                    bundle.putString("lon", itemData.getLongitude());
                    bundle.putString("address", itemData.getAddress());
                    openActivity(Report_Shop_Location_Activity.class, bundle);
                } catch (Exception e) {
                }
                break;
        }
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
            pull_refresh_list.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.MAP_REFRESH) {
            reportInfoEntitiyListViewDataAdapter.notifyDataSetChanged();
        }
    }
}
