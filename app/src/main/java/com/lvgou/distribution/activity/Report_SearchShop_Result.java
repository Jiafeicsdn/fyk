package com.lvgou.distribution.activity;

import android.content.Context;
import android.content.Intent;
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
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ReportSearchReasultEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.ReportShopSearchPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ReportShopSearchView;
import com.lvgou.distribution.viewholder.ReportSearchOneViewHolder;
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
 * Created by Administrator on 2016/8/18.
 * 报备店铺搜索结果
 */
public class Report_SearchShop_Result extends BaseActivity implements OnClassifyPostionClickListener<ReportSearchReasultEntity>, ReportShopSearchView {
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.et_search02)
    private EditText et_search02;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    private ListView lv_search_result;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pull_refresh_list;
    @ViewInject(R.id.img_search)
    private ImageView img_search;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;

    private String key;
    boolean mIsUp;// 是否上拉加载
    private int pageindex = 1;
    private int total_page = 0;
    private String distributorid;
    private ListViewDataAdapter<ReportSearchReasultEntity> reportSearchReasultEntityListViewDataAdapter;

    private ReportShopSearchPresenter reportShopSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_search_result);
        ViewUtils.inject(this);
        key = getTextFromBundle("key");//记得换回来
        et_search02.setText(key);
        distributorid = PreferenceHelper.readString(Report_SearchShop_Result.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        lv_search_result = pull_refresh_list.getRefreshableView();

        reportShopSearchPresenter = new ReportShopSearchPresenter(this);

        initCreateView();
        initViewHolder();
        serachData();

        String sign = TGmd5.getMD5(distributorid + key + pageindex);
        reportShopSearchPresenter.getSearchShopData(distributorid, key, pageindex + "", sign);

    }


    @OnClick({R.id.rl_back, R.id.rl_publish})
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
                bundle.putString("index", "0");
                openActivity(Report_Shop_Location_Activity.class, bundle);
                break;
        }
    }

    public void initViewHolder() {
        reportSearchReasultEntityListViewDataAdapter = new ListViewDataAdapter<ReportSearchReasultEntity>();
        reportSearchReasultEntityListViewDataAdapter.setViewHolderClass(this, ReportSearchOneViewHolder.class);
        ReportSearchOneViewHolder.setOnClassifyPostionClickListener(this);
        lv_search_result.setAdapter(reportSearchReasultEntityListViewDataAdapter);
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
     * @param response
     */
    @Override
    public void applcationSuccCallBck(String response) {
        pull_refresh_list.onRefreshComplete();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String statue = jsonObject.getString("status");
            if (statue.equals("1")) {
                String result = jsonObject.getString("result");
                JSONArray array = new JSONArray(result);
                String data = array.get(0).toString();
                JSONObject jsonObject_one = new JSONObject(data);
                total_page = jsonObject_one.getInt("TotalPages");
                String items = jsonObject_one.getString("Items");
                if (mIsUp == false) {// 下拉刷新，清空listViewDataAdapter
                    reportSearchReasultEntityListViewDataAdapter.removeAll();
                } else if (mIsUp == true) {
                    // 上拉加载，不清空 listViewDataAdapter
                }
                JSONArray array_one = new JSONArray(items);
                if (array_one != null && array_one.length() > 0) {
                    showOrGone();
                    ll_visibility.setVisibility(View.VISIBLE);
                    for (int i = 0; i < array_one.length(); i++) {
                        JSONObject jsonObject_ = array_one.getJSONObject(i);
                        String id = jsonObject_.getString("ID");
                        String name = jsonObject_.getString("ShopName");
                        String address = jsonObject_.getString("Adderss");
                        String Latitude = jsonObject_.getString("Latitude");
                        String Longitude = jsonObject_.getString("Longitude");
                        String Business = jsonObject_.getString("Business");
                        String logo = jsonObject_.getString("Logo");
                        ReportSearchReasultEntity reportSearchReasultEntity = new ReportSearchReasultEntity(id, Constants.Latitude, Constants.Longitude, Longitude, Latitude, Business, address, name, "");
                        reportSearchReasultEntity.setLogo(logo);
                        reportSearchReasultEntityListViewDataAdapter.append(reportSearchReasultEntity);
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

    }

    /**
     * 失败回调
     *
     * @param response
     */
    @Override
    public void applcationFailCallBck(String response) {
        pull_refresh_list.onRefreshComplete();
        MyToast.show(Report_SearchShop_Result.this, "请求失败");
    }

    /**
     * 取消弹窗
     */
    @Override
    public void closeProgress() {
        dismissProgressDialog();
    }


    /**
     * 搜索操作
     */
    public void serachData() {
        et_search02.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                // 执行获取数据操作
                pageindex = 1;
                key = et_search02.getText().toString().trim();
                String sign = TGmd5.getMD5(distributorid + key + pageindex);
                reportShopSearchPresenter.getSearchShopData(distributorid, key, pageindex + "", sign);

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
                String label = DateUtils.formatDateTime(Report_SearchShop_Result.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pull_refresh_list.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                key = et_search02.getText().toString().trim();
                String sign = TGmd5.getMD5(distributorid + key + pageindex);
                reportShopSearchPresenter.getSearchShopData(distributorid, key, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    key = et_search02.getText().toString().trim();
                    String sign = TGmd5.getMD5(distributorid + key + pageindex);
                    reportShopSearchPresenter.getSearchShopData(distributorid, key, pageindex + "", sign);

                } else {
                    MyToast.show(Report_SearchShop_Result.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    /**
     * item 点击回调
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(ReportSearchReasultEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
              /*  bundle.putString("index", "1");
                bundle.putString("reportId", "0");
                bundle.putString("shop_name", itemData.getName());
                bundle.putString("reportSellerId", itemData.getId());
                openActivity(ReportShopActivity.class, bundle);*/
                Intent intent = new Intent(Report_SearchShop_Result.this, MerchantCenterActivity.class);
                intent.putExtra("reportid", itemData.getId());
                startActivity(intent);
                break;
            case 2:
                try {
                    bundle.putString("index", "1");
                    bundle.putString("name", itemData.getName());
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


}
