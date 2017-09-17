package com.lvgou.distribution.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ReportEntity;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.ReportPredenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ReportView;
import com.lvgou.distribution.viewholder.ReportViewHolder;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snow on 2016/7/2 0002.
 * 报备
 */
public class ReportActivity extends BaseActivity implements OnListItemClickListener<ReportEntity>, AMapLocationListener, ReportView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_xieyi)
    private RelativeLayout rl_xieyi;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_right;
    @ViewInject(R.id.tv_right)
    private TextView tv_publish;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibilty;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;

    private ListView listView;
    private ListViewDataAdapter<ReportEntity> reportEntityListViewDataAdapter;

    private String distributorid = "";
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private String index = "";

    private boolean is_refresh = false;

    /****
     * 定位相关
     ****/

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    private ReportPredenter reportPredenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        ViewUtils.inject(this);
        tv_title.setText("预约列表");
        rl_right.setVisibility(View.VISIBLE);
        tv_publish.setText("预约");
        tv_publish.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
        index = getTextFromBundle("index");
        distributorid = PreferenceHelper.readString(ReportActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        listView = pullToRefreshListView.getRefreshableView();

        reportPredenter = new ReportPredenter(this);

        /**
         * EventBus 注册
         */
        EventBus.getDefault().register(this);
        /**
         * 初始化 定位操作
         */
        initLocal();

        initViewHolder();

        initCreateView();

    }

    @OnClick({R.id.rl_back, R.id.rl_publish, R.id.rl_xieyi})
    public void OnClick(View view) {
        Bundle pBundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    pBundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, pBundle);
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.rl_publish:
                openActivity(ReportSearchShopActivity.class);
                break;
            case R.id.rl_xieyi:
                mIsUp = false;
                pBundle.putString("url", "http://agent.quygt.com/user/reportprotocol");
                pBundle.putString("index", "0");
                openActivity(WebViewActivity.class, pBundle);// 其余均是 url 文网页打开
                break;
        }
    }


    /**
     * 定位获取经纬度
     */
    public void initLocal() {
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为低功耗模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        locationOption.setOnceLocation(false);//只有持续定位设置定位间隔才有效，单次定位无效
        locationOption.setNeedAddress(false); // 设置是否需要显示地址信息
        locationOption.setInterval(1000);// 设置定位时间间隔  1000 毫秒
        //设置定位监听
        locationClient.setLocationListener(this);
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        //启动定位
        locationClient.startLocation();

    }

    public void initViewHolder() {
        reportEntityListViewDataAdapter = new ListViewDataAdapter<ReportEntity>();
        reportEntityListViewDataAdapter.setViewHolderClass(this, ReportViewHolder.class);
        ReportViewHolder.setOnListItemClickListener(this);
        listView.setAdapter(reportEntityListViewDataAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        reportPredenter.attach(this);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            String sign = TGmd5.getMD5(distributorid + pageindex);
            if (checkNet()) {
                showProgressDialog("加载中...");
                reportPredenter.getReportList(distributorid, pageindex + "", sign);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        reportPredenter.dettach();

        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
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
                String label = DateUtils.formatDateTime(ReportActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                reportPredenter.getReportList(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    reportPredenter.getReportList(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(ReportActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    /**
     * 获取数据成功回调
     *
     * @param response
     */
    @Override
    public void applcationSuccCallBck(String response) {
        pullToRefreshListView.onRefreshComplete();
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
                if (pageindex ==1) {
                    reportEntityListViewDataAdapter.removeAll();
                }
                showOrGone();
                JSONArray array_ = new JSONArray(item_);
                if (array_ != null && array_.length() > 0) {
                    ll_visibilty.setVisibility(View.VISIBLE);
                    for (int i = 0; i < array_.length(); i++) {
                        JSONObject jsonItem = array_.getJSONObject(i);
                        String id_ = jsonItem.getString("ID");
                        String reportSellerId_ = jsonItem.getString("ReportSellerID");
                        String date = jsonItem.getString("CreateTime");
                        String name = jsonItem.getString("ShopName");
                        String a = jsonItem.getString("PeopleCount1");
                        String b = jsonItem.getString("PeopleCount2");
                        int num = Integer.parseInt(a) + Integer.parseInt(b);
                        String state = jsonItem.getString("State");
                        ReportEntity report = new ReportEntity(id_, state, num + "", date, name, reportSellerId_);
                        reportEntityListViewDataAdapter.append(report);
                    }
                } else {
                    showOrGone();
                    rl_none.setVisibility(View.VISIBLE);
                }
            } else if (status.equals("0")) {
                MyToast.show(ReportActivity.this, jsonObject.getString("message"));
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
     * 获取数据失败回调
     *
     * @param response
     */
    @Override
    public void applcationFailCallBck(String response) {
        pullToRefreshListView.onRefreshComplete();
        MyToast.show(ReportActivity.this, "请求失败");
    }

    /**
     * 取消进度条
     *
     * @param
     */
    @Override
    public void closeProgress() {
        dismissProgressDialog();
    }


    /***
     * 经纬度 变化监听
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (!is_refresh) {
            is_refresh = true;
            if (aMapLocation != null) {
                Constants.Latitude = aMapLocation.getLatitude() + "";// 纬度
                Constants.Longitude = aMapLocation.getLongitude() + "";// 经度
            }
            // 发送更新地图
            EventFactory.updateMap(0);
        }
    }

    /**
     * item 点击事件
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(ReportEntity itemData) {
        mIsUp = false;
        Bundle bundle = new Bundle();
        bundle.putString("index", "1");
        bundle.putString("reportId", itemData.getId());
        bundle.putString("shop_name", itemData.getName());
        bundle.putString("reportSellerId", itemData.getReportSellerId());
        openActivity(ReportShopActivity.class, bundle);
    }


    /**
     * 隐藏or显示
     */
    public void showOrGone() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.CANCLE_REPORT) {
            pageindex = 1;
            String sign = TGmd5.getMD5(distributorid + pageindex);
            if (checkNet()) {
                showProgressDialog("加载中...");
                reportPredenter.getReportList(distributorid, pageindex + "", sign);
            }
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