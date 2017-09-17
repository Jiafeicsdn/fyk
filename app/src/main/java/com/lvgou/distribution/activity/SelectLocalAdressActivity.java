package com.lvgou.distribution.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.LocalAddressEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.LocalAddressViewHolder;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import java.util.List;

/**
 * Created by Swow on 2016/11/5.
 * 发布派团，选择地址
 */
public class SelectLocalAdressActivity extends BaseActivity implements LocationSource, PoiSearch.OnPoiSearchListener, OnListItemClickListener<LocalAddressEntity> {


    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.et_search)
    private EditText et_search;
    @ViewInject(R.id.img_search_icon)
    private ImageView img_search;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibilty;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;

@ViewInject(R.id.ll_address_view)
private LinearLayout ll_address_view;

    @ViewInject(R.id.txt_cancel)
    private TextView txt_cancel;
    @ViewInject(R.id.txt_confirm)
    private TextView txt_confirm;

    @ViewInject(R.id.txt_address)
    private TextView txt_address;

    private ListView lv_list;

    private final static int REQUEST_CODE_ADDRESS = 0x22;

    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private LatLonPoint lp = new LatLonPoint(39.993167, 116.473274);//
    private String keyWord = "";
    private String city = "";
    private PoiSearch poiSearch;
    private List<PoiItem> poiItems;// poi数据
    private boolean location_state = true;
    boolean mIsUp;// 是否上拉加载

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    private ListViewDataAdapter<LocalAddressEntity> localAddressEntityListViewDataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_loacal_address);
        ViewUtils.inject(this);
        tv_title.setText("选取位置");


        lv_list = pullToRefreshListView.getRefreshableView();

        initViewHolder();
        initCreateView();
        try {
            serachData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoadingProgressDialog(SelectLocalAdressActivity.this, "");
        try {
            if (mLocationClient == null) {
                mLocationOption = new AMapLocationClientOption();
                //初始化定位
                mLocationClient = new AMapLocationClient(getApplicationContext());
                //设置定位回调监听
                mLocationClient.setLocationListener(mLocationListener);
                //设置为高精度定位模式
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
                //设置定位参数
                mLocationClient.setLocationOption(mLocationOption);
                // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                // 在定位结束后，在合适的生命周期调用onDestroy()方法
                // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                mLocationClient.startLocation();
            }

            LocalAddressEntity localAddressEntity_one = new LocalAddressEntity("", "不显示位置", "", "");
            localAddressEntityListViewDataAdapter.append(localAddressEntity_one);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showOrGone() {
        rl_none.setVisibility(View.GONE);
        ll_visibilty.setVisibility(View.GONE);
    }

    public void initViewHolder() {
        localAddressEntityListViewDataAdapter = new ListViewDataAdapter<LocalAddressEntity>();
        localAddressEntityListViewDataAdapter.setViewHolderClass(this, LocalAddressViewHolder.class);
        LocalAddressViewHolder.setLocalAddressEntityOnListItemClickListener(this);
        lv_list.setAdapter(localAddressEntityListViewDataAdapter);

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
                String label = DateUtils.formatDateTime(SelectLocalAdressActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                currentPage = 0;
                doSearchQuery();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                currentPage += 1;
                doSearchQuery();
            }
        });
    }


    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    aMapLocation.getLatitude();//获取纬度
                    aMapLocation.getLongitude();//获取经度
                    closeLoadingProgressDialog();
                    if (location_state) {
                        lp = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                        doSearchQuery();
                    }
                    location_state = false;
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    @OnClick({R.id.rl_back,R.id.txt_cancel,R.id.txt_confirm})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.txt_cancel:
                ll_address_view.setVisibility(View.GONE);
                break;
            case R.id.txt_confirm:
                Intent intent = new Intent();
                intent.putExtra("city_name", keyWord);
                intent.putExtra("title","");
                setResult(REQUEST_CODE_ADDRESS, intent);
                finish();
                break;
        }
    }


    /**
     * 搜索操作
     */
    public void serachData() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                showLoadingProgressDialog(SelectLocalAdressActivity.this, "");
                // 执行获取数据操作
                currentPage = 1;
                keyWord = et_search.getText().toString();
                txt_address.setText(keyWord);
                ll_address_view.setVisibility(View.VISIBLE);
                doSearchQuery();

                return true;
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_search.getText().length() == 0) {
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


    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        pullToRefreshListView.onRefreshComplete();
        currentPage = 0;
        query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));//
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始

                    if (poiItems != null && poiItems.size() > 0) {
                        showOrGone();
                        ll_visibilty.setVisibility(View.VISIBLE);
                        for (int i = 0; i < poiItems.size(); i++) {
                            String title = poiItems.get(i).getTitle();
                            String address = poiItems.get(i).getSnippet();
                            String city_name = poiItems.get(i).getCityName();
                            LocalAddressEntity localAddressEntity = new LocalAddressEntity(i + "", title, address, city_name);
                            localAddressEntityListViewDataAdapter.append(localAddressEntity);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                        MyToast.show(SelectLocalAdressActivity.this, "对不起，没有搜索到相关数据！");
                    }
                }
            } else {
                showOrGone();
                rl_none.setVisibility(View.VISIBLE);
                MyToast.show(SelectLocalAdressActivity.this, "对不起，没有搜索到相关数据！");
            }
        } else {
            showOrGone();
            rl_none.setVisibility(View.VISIBLE);
//                MyToast.show(SelectLocalAdressActivity.this, R.string.no_result);
        }
        closeLoadingProgressDialog();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
//    private void showSuggestCity(List<SuggestionCity> cities) {
//        String infomation = "推荐城市\n";
//        for (int i = 0; i < cities.size(); i++) {
//            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
//                    + cities.get(i).getCityCode() + "城市编码:"
//                    + cities.get(i).getAdCode() + "\n";
//        }
//        ToastUtil.show(PoiKeywordSearchActivity.this, infomation);
//
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }


//    /**
//     * 异步取消刷新
//     */
//    private class CancleRefreshTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            pullToRefreshListView.onRefreshComplete();
//            super.onPostExecute(aVoid);
//        }
//    }


    /**
     * tiem 点击事件
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(LocalAddressEntity itemData) {
        localAddressEntityListViewDataAdapter.notifyDataSetChanged();
        Intent intent = new Intent();
        intent.putExtra("city_name", itemData.getCity_name());
        intent.putExtra("title", itemData.getTitle());
        setResult(REQUEST_CODE_ADDRESS, intent);
        finish();
    }
}
