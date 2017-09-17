package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ReportSearchReasultEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.ReportLocalPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.route.DriveRouteColorfulOverLay;
import com.lvgou.distribution.utils.DistanceUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ReportLocalView;
import com.lvgou.distribution.viewholder.ReportSearchResultViewHolder;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Created by Administrator on 2016/8/18.
 * 报备定位，以及附近店铺
 */
public class Report_Shop_Location_Activity extends BaseActivity implements LocationSource, AMapLocationListener, OnClassifyPostionClickListener<ReportSearchReasultEntity>, RouteSearch.OnRouteSearchListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, ReportLocalView {


    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.et_search02)
    private EditText et_search;
    @ViewInject(R.id.img_search)
    private ImageView img_serach;
    @ViewInject(R.id.search_02)
    private RelativeLayout rl_search;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.amap)
    private MapView mapView;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;


    private Dialog dialog_select;


    /**************
     * 地图相关类
     ************/

    private AMap aMap;

    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private RouteSearch mRouteSearch;
    private WalkRouteResult mWalkRouteResult;
    private DriveRouteResult mDriveRouteResult;


    PoiSearch.Query query = new PoiSearch.Query("", "", "");
    private String is_showMap = "";// 地图按钮显示标示

    double x_pi = 3.14159265358979324 * 3000.0 / 180.0;


    //声明定位回调监听器
    private double lat;
    private double lon;
    private ListView listView;
    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private String key = "";

    private ListViewDataAdapter<ReportSearchReasultEntity> reportSearchReasultEntityListViewDataAdapter;

    private String index = "";
    private String lat_get = "";
    private String lon_get = "";
    private String title = "";
    private String address = "";
    private double juli = 0.00;

    LatLngBounds bounds;

    boolean first = true;
    LatLngBounds.Builder builder;

    List<ReportSearchReasultEntity> reportSearchReasultEntities = null;

    private ReportLocalPresenter reportLocalPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_amap);
        ViewUtils.inject(this);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        distributorid = PreferenceHelper.readString(Report_Shop_Location_Activity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        listView = pullToRefreshListView.getRefreshableView();

        reportLocalPresenter = new ReportLocalPresenter(this);

        is_showMap = getMapPackageName();
        index = getTextFromBundle("index");
        builder = new LatLngBounds.Builder();
        /**
         * 地图初始化
         */
        init();
        if (!Constants.Latitude.equals("") && !Constants.Longitude.equals("")) {
            aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                    .position(new LatLng(Double.parseDouble(Constants.Latitude), Double.parseDouble(Constants.Longitude))).title("当前位置")
                    .snippet("当前位置").icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_adress_icon))
                    .draggable(false).period(50));
        }

        /**
         * 获取数据
         */
        initCreateView();
        initViewHolder();
        serachData();

        if (index.equals("0")) {
            String sign = TGmd5.getMD5(distributorid + key + Constants.Latitude + Constants.Longitude + pageindex);
            reportLocalPresenter.getLocalData(distributorid, key, Constants.Latitude, Constants.Longitude, pageindex + "", sign);
            rl_search.setVisibility(View.VISIBLE);
            tv_title.setVisibility(View.GONE);
        } else if (index.equals("1")) {
            showOrGone();
            lat_get = getTextFromBundle("lat");
            lon_get = getTextFromBundle("lon");
            title = getTextFromBundle("name");
            address = getTextFromBundle("address");
            rl_search.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);


            /***********创建Marker***********/
            if (!lat_get.equals("") && !lon_get.equals("")) {
                showProgressDialog("搜索中....");
                LatLonPoint mStartPoint = new LatLonPoint(Double.parseDouble(Constants.Latitude), Double.parseDouble(Constants.Longitude));//起点
                LatLonPoint mEndPoint = new LatLonPoint(Double.parseDouble(lat_get), Double.parseDouble(lon_get));//终点
                final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, mEndPoint);
                juli = DistanceUtil.getDistance(Double.parseDouble(lon_get), Double.parseDouble(lat_get), Double.parseDouble(Constants.Longitude), Double.parseDouble(Constants.Latitude));
//                if (juli < 3000.00) {
//                    /**
//                     * 步行
//                     */
////                    setfromandtoMarker(mStartPoint, mEndPoint, title, address);
//                    RouteSearch.WalkRouteQuery query_walk = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
//                    mRouteSearch.calculateWalkRouteAsyn(query_walk);// 异步路径规划步行模式查询
//                } else {
                /**
                 * 驾车
                 */
//                    setfromandtoMarkerDrive(Constants.Latitude, Constants.Longitude, lat_get, lon_get, title, address);
                RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null, null, "");
                // 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
                mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
//                }
            } else {
                MyToast.show(Report_Shop_Location_Activity.this, "店铺经纬度有误，不能导航！");
            }
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

                // 执行获取数据操作
                pageindex = 1;
                key = et_search.getText().toString();
                String sign = TGmd5.getMD5(distributorid + key + Constants.Latitude + Constants.Longitude + pageindex);
                reportLocalPresenter.getLocalData(distributorid, key, Constants.Latitude, Constants.Longitude, pageindex + "", sign);
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
                    img_serach.setVisibility(View.VISIBLE);
                } else {
                    img_serach.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //  选择地图弹框
    public void showMapSelectDialog(String str, final Marker marker) {
        dialog_select = new Dialog(Report_Shop_Location_Activity.this, R.style.Mydialog);
        View view1 = View.inflate(Report_Shop_Location_Activity.this, R.layout.dialog_show_guider, null);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        Button btn_baidu_map = (Button) view1.findViewById(R.id.btn_baidu_map);
        Button btn_gaode_map = (Button) view1.findViewById(R.id.btn_gaode_map);
        Button btn_tencent_map = (Button) view1.findViewById(R.id.btn_tencent_map);
        TextView cancle = (TextView) view1.findViewById(R.id.tv_cancle);
        switch (str) {
            case "0":
                tv_title.setText("请先下载地图!");
                break;
            case "1":
                tv_title.setText("选择你使用的地图");
                btn_baidu_map.setVisibility(View.VISIBLE);
                break;
            case "2":
                tv_title.setText("选择你使用的地图");
                btn_gaode_map.setVisibility(View.VISIBLE);
                break;
            case "3":
                tv_title.setText("选择你使用的地图");
                btn_tencent_map.setVisibility(View.VISIBLE);
                break;
            case "4":
                tv_title.setText("选择你使用的地图");
                btn_baidu_map.setVisibility(View.VISIBLE);
                btn_gaode_map.setVisibility(View.VISIBLE);
                break;
            case "5":
                tv_title.setText("选择你使用的地图");
                btn_baidu_map.setVisibility(View.VISIBLE);
                btn_tencent_map.setVisibility(View.VISIBLE);
                break;
            case "6":
                tv_title.setText("选择你使用的地图");
                btn_gaode_map.setVisibility(View.VISIBLE);
                btn_tencent_map.setVisibility(View.VISIBLE);
                break;
            case "7":
                tv_title.setText("选择你使用的地图");
                btn_baidu_map.setVisibility(View.VISIBLE);
                btn_gaode_map.setVisibility(View.VISIBLE);
                btn_tencent_map.setVisibility(View.VISIBLE);
                break;
        }
        btn_baidu_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 火星坐标转换为百度坐标
                 */

                try {
                    double x = marker.getPosition().longitude, y = marker.getPosition().latitude;
                    double z = sqrt(x * x + y * y) + 0.00002 * sin(y * x_pi);
                    double theta = atan2(y, x) + 0.000003 * cos(x * x_pi);
                    double bd_lon = z * cos(theta) + 0.0065;
                    double bd_lat = z * sin(theta) + 0.006;

                    goToBaiDuActivity(bd_lat + "", bd_lon + "", marker.getTitle(), "杭州途购", "com.lvgou.distribution");
                    dialog_select.dismiss();
                    marker.hideInfoWindow();

                } catch (Exception c) {
                    MyToast.show(Report_Shop_Location_Activity.this, "请检查地图安装情况");
                    dialog_select.dismiss();
                    marker.hideInfoWindow();
                }
            }
        });

        btn_gaode_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    goToNaviActivity(Report_Shop_Location_Activity.this, "蜂优客", null, marker.getPosition().latitude + "", marker.getPosition().longitude + "", "1", "2");
                    dialog_select.dismiss();
                    marker.hideInfoWindow();
                } catch (Exception c) {
                    MyToast.show(Report_Shop_Location_Activity.this, "请检查地图安装情况");
                    dialog_select.dismiss();
                    marker.hideInfoWindow();
                }
            }
        });
        btn_tencent_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    goToTencentActivity("drive", marker.getTitle(), Constants.Latitude, Constants.Longitude, marker.getPosition().latitude + "", marker.getPosition().longitude + "");
                    dialog_select.dismiss();
                    marker.hideInfoWindow();
                } catch (Exception c) {
                    MyToast.show(Report_Shop_Location_Activity.this, "请检查地图安装情况");
                    dialog_select.dismiss();
                    marker.hideInfoWindow();
                }

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_select.dismiss();
            }
        });

        dialog_select.setContentView(view1);
        dialog_select.show();
    }


    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
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
                String label = DateUtils.formatDateTime(Report_Shop_Location_Activity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + key + Constants.Latitude + Constants.Longitude + pageindex);
                reportLocalPresenter.getLocalData(distributorid, key, Constants.Latitude, Constants.Longitude, pageindex + "", sign);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + key + Constants.Latitude + Constants.Longitude + pageindex);
                    reportLocalPresenter.getLocalData(distributorid, key, Constants.Latitude, Constants.Longitude, pageindex + "", sign);
                } else {
                    MyToast.show(Report_Shop_Location_Activity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    /**
     * 初始化 viewholder
     */
    public void initViewHolder() {
        reportSearchReasultEntityListViewDataAdapter = new ListViewDataAdapter<ReportSearchReasultEntity>();
        reportSearchReasultEntityListViewDataAdapter.setViewHolderClass(Report_Shop_Location_Activity.this, ReportSearchResultViewHolder.class);
        ReportSearchResultViewHolder.setOnClassifyPostionClickListener(this);
        listView.setAdapter(reportSearchReasultEntityListViewDataAdapter);
    }


    /**
     * 成功回调
     *
     * @param response
     */
    @Override
    public void applcationSuccCallBck(String response) {
        pullToRefreshListView.onRefreshComplete();
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
                    aMap.clear();

                    builder = null;
                    builder = new LatLngBounds.Builder();
                } else if (mIsUp == true) {
                    // 上拉加载，不清空 listViewDataAdapter
                }

                showOrGone();
                JSONArray array_one = new JSONArray(items);
                if (array_one != null && array_one.length() > 0) {
                    ll_visibility.setVisibility(View.VISIBLE);
                    for (int i = 0; i < array_one.length(); i++) {
                        JSONObject jsonObject_ = array_one.getJSONObject(i);
                        String id = jsonObject_.getString("ID");
                        String name = jsonObject_.getString("ShopName");
                        String address = jsonObject_.getString("Adderss");
                        String Latitude = jsonObject_.getString("Latitude");
                        String Longitude = jsonObject_.getString("Longitude");
                        String Business = jsonObject_.getString("Business");
                        String img_path = jsonObject_.getString("Logo");
                        /***********创建Marker***********/
                        if (!Latitude.equals("") && !Longitude.equals("")) {
                            Marker marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                                    .position(new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude))).title(name)
                                    .snippet(address).icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_adress_icon))
                                    .draggable(false).period(50));
                            Log.i("+++++++++++++++", marker.getId());
                            LatLng ll1 = new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude));
                            builder.include(ll1);
                            ReportSearchReasultEntity reportSearchReasultEntity = new ReportSearchReasultEntity(id, Constants.Latitude, Constants.Longitude, Longitude, Latitude, Business, address, name, marker.getId());
                           reportSearchReasultEntity.setLogo(img_path);
                           reportSearchReasultEntityListViewDataAdapter.append(reportSearchReasultEntity);
                        }
                    }
                    bounds = builder.build();
                    aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 60));
                } else {
                    showOrGone();
                    rl_none.setVisibility(View.VISIBLE);
                }
            } else {
                MyToast.show(Report_Shop_Location_Activity.this, "请重新获取定位信息");
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
        pullToRefreshListView.onRefreshComplete();
        MyToast.show(Report_Shop_Location_Activity.this, "请求失败");
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


//    /**
//     * 获取数据
//     *
//     * @param distributorid
//     * @param key
//     * @param latitude
//     * @param longitude
//     * @param pageindex
//     * @param sign
//     */
//    public void getData(String distributorid, String key, String latitude, String longitude, String pageindex, String sign) {
//        Map<String, Object> maps = new HashMap<String, Object>();
//        maps.put("distributorid", distributorid);
//        maps.put("key", key);
//        maps.put("latitude", latitude);
//        maps.put("longitude", longitude);
//        maps.put("pageindex", pageindex);
//        maps.put("sign", sign);
//
//        RequestTask.getInstance().getLocalData(Report_Shop_Location_Activity.this, maps, new OnRequestListener());
//
//    }
//
//    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
//        @Override
//        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
//            super.onDone(request, response, dataType);
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                String statue = jsonObject.getString("status");
//                if (statue.equals("1")) {
//                    String result = jsonObject.getString("result");
//                    JSONArray array = new JSONArray(result);
//                    String data = array.get(0).toString();
//                    JSONObject jsonObject_one = new JSONObject(data);
//                    total_page = jsonObject_one.getInt("TotalPages");
//                    String items = jsonObject_one.getString("Items");
//                    if (mIsUp == false) {// 下拉刷新，清空listViewDataAdapter
//                        reportSearchReasultEntityListViewDataAdapter.removeAll();
//                        aMap.clear();
//
//                        builder = null;
//                        builder = new LatLngBounds.Builder();
//                    } else if (mIsUp == true) {
//                        // 上拉加载，不清空 listViewDataAdapter
//                    }
//
//                    showOrGone();
//                    JSONArray array_one = new JSONArray(items);
//                    if (array_one != null && array_one.length() > 0) {
//                        ll_visibility.setVisibility(View.VISIBLE);
//                        for (int i = 0; i < array_one.length(); i++) {
//                            JSONObject jsonObject_ = array_one.getJSONObject(i);
//                            String id = jsonObject_.getString("ID");
//                            String name = jsonObject_.getString("ShopName");
//                            String address = jsonObject_.getString("Adderss");
//                            String Latitude = jsonObject_.getString("Latitude");
//                            String Longitude = jsonObject_.getString("Longitude");
//                            String Business = jsonObject_.getString("Business");
//
//                            /***********创建Marker***********/
//                            if (!Latitude.equals("") && !Longitude.equals("")) {
//                                Marker marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
//                                        .position(new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude))).title(name)
//                                        .snippet(address).icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_adress_icon))
//                                        .draggable(false).period(50));
//                                Log.i("+++++++++++++++", marker.getId());
//                                LatLng ll1 = new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude));
//                                builder.include(ll1);
//                                ReportSearchReasultEntity reportSearchReasultEntity = new ReportSearchReasultEntity(id, Constants.Latitude, Constants.Longitude, Longitude, Latitude, Business, address, name, marker.getId());
//                                reportSearchReasultEntityListViewDataAdapter.append(reportSearchReasultEntity);
//                            }
//                        }
//                        bounds = builder.build();
//                        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 60));
//                    } else {
//                        showOrGone();
//                        rl_none.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    MyToast.show(Report_Shop_Location_Activity.this, "请重新获取定位信息");
//                    showOrGone();
//                    rl_none.setVisibility(View.VISIBLE);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        @Override
//        public void onRequestPrepare() {
//            super.onRequestPrepare();
//            showProgressDialog("正在加载...");
//        }
//
//        @Override
//        public void onRequestFinish(String data) {
//            super.onRequestFinish(data);
//            dismissProgressDialog();
//            pullToRefreshListView.onRefreshComplete();
//
//        }
//
//        @Override
//        public void onRequestFailed(String error) {
//            super.onRequestFailed(error);
//            dismissProgressDialog();
//            pullToRefreshListView.onRefreshComplete();
//        }
//    }

    /**
     * * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        reportLocalPresenter.attach(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        reportLocalPresenter.dettach();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                Constants.Latitude = aMapLocation.getLatitude() + "";
                Constants.Longitude = aMapLocation.getLongitude() + "";
                if (first) {
                    if (bounds != null) {
                        first = false;
                    }
                    aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
                }
                Log.e("AmapErr", "getLatitude()====" + aMapLocation.getLatitude() + "     getLongitude()===" + aMapLocation.getLongitude());
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }


    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    /**
     * InfoWindowAdapter 实现方法
     *
     * @param marker
     * @return
     */
    @Override
    public View getInfoWindow(final Marker marker) {
        Constants.MAKERID = marker.getId();
        for (int i = 0; i < reportSearchReasultEntityListViewDataAdapter.getDataList().size(); i++) {
            if (marker.getId().equals(reportSearchReasultEntityListViewDataAdapter.getDataList().get(i).getMarkId())) {
                listView.smoothScrollToPosition(listView.getHeaderViewsCount() + i);
            }
        }

        reportSearchReasultEntityListViewDataAdapter.notifyDataSetChanged();

        View infoWindow = getLayoutInflater().inflate(R.layout.custome_guider_infowindow, null);
        TextView tv_title = (TextView) infoWindow.findViewById(R.id.tv_title);
        TextView tv_address = (TextView) infoWindow.findViewById(R.id.tv_address);
        ImageView img_guider = (ImageView) infoWindow.findViewById(R.id.img_guider);
        tv_title.setText(marker.getTitle());
        tv_address.setText(marker.getSnippet());
        img_guider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMapSelectDialog(is_showMap, marker);
            }
        });

        return infoWindow;
    }

    /**
     * InfoWindowAdapter 实现方法
     *
     * @param marker
     * @return
     */
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


    /**
     * onInfoWindowClick 点击事件
     *
     * @param marker
     * @return
     */
    @Override
    public void onInfoWindowClick(Marker marker) {


    }


    /**
     * 公交路线搜索结果回调
     *
     * @param busRouteResult
     * @param errorCode
     */
    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int errorCode) {
        dismissProgressDialog();
    }

    /**
     * 自驾车路线结果回调
     *
     * @param result
     * @param errorCode
     */
    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        dismissProgressDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == 1000) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths().get(0);
                    DriveRouteColorfulOverLay drivingRouteOverlay = new DriveRouteColorfulOverLay(
                            aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap(title, address);
                    if (index.equals("0")) {
                        drivingRouteOverlay.removeFromMap2();
                    }
                    drivingRouteOverlay.zoomToSpan();
                } else if (result != null && result.getPaths() == null) {
                    MyToast.show(Report_Shop_Location_Activity.this, "没有搜索到结果");
                }
            } else {
                MyToast.show(Report_Shop_Location_Activity.this, "没有搜索到结果");
            }
        } else {
            MyToast.show(Report_Shop_Location_Activity.this, "没有搜索到结果");
        }
        if (reportSearchReasultEntities == null) {
            reportSearchReasultEntities = new ArrayList<>();
        } else {
            reportSearchReasultEntities.clear();
        }
        List<ReportSearchReasultEntity> dataList = reportSearchReasultEntityListViewDataAdapter.getDataList();
        for (int i = 0; i < dataList.size(); i++) {
            Marker marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                    .position(new LatLng(Double.parseDouble(dataList.get(i).getLatitude()), Double.parseDouble(dataList.get(i).getLongitude()))).title(dataList.get(i).getName())
                    .snippet(dataList.get(i).getAddress()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_adress_icon))
                    .draggable(false).period(50));
            ReportSearchReasultEntity reportSearchReasultEntity = new ReportSearchReasultEntity(dataList.get(i).getId(), Constants.Latitude, Constants.Longitude, dataList.get(i).getLongitude(), dataList.get(i).getLatitude(), dataList.get(i).getBusiness(), dataList.get(i).getAddress(), dataList.get(i).getName(), marker.getId());
            reportSearchReasultEntity.setLogo(dataList.get(i).getLogo());
            reportSearchReasultEntities.add(reportSearchReasultEntity);
        }
        reportSearchReasultEntityListViewDataAdapter.replace(reportSearchReasultEntities);
    }


    /**
     * 步行 结果回调
     *
     * @param result
     * @param errorCode
     */
    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        dismissProgressDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == 1000) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    final WalkPath walkPath = mWalkRouteResult.getPaths().get(0);
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
                            this, aMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                } else {
                    MyToast.show(Report_Shop_Location_Activity.this, "没有搜索到结果");
                }
            } else {
                MyToast.show(Report_Shop_Location_Activity.this, "没有搜索到结果");
            }
        }

    }

    public void addStartAndEndMarker() {
        aMap.addMarker((new MarkerOptions()).position(new LatLng(Double.parseDouble(Constants.Latitude), Double.parseDouble(Constants.Longitude))).icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_adress_icon)).title("当前位置"));
        aMap.addMarker((new MarkerOptions()).position(new LatLng(Double.parseDouble(lat_get), Double.parseDouble(lon_get))).icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_adress_icon)).title(title).snippet(address));

    }

    @Override
    public void onClassifyPostionClick(ReportSearchReasultEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1: // 跳到 店铺报备页面
                bundle.putString("index", "1");
                bundle.putString("reportId", "0");
                bundle.putString("shop_name", itemData.getName());
                bundle.putString("reportSellerId", itemData.getId());
                openActivity(ReportShopActivity.class, bundle);
                break;
            case 2:// 导航路线
                if (!itemData.getLatitude().equals("") && !itemData.getLongitude().equals("")) {
                    showProgressDialog("搜索中....");
                    LatLonPoint mStartPoint = new LatLonPoint(Double.parseDouble(Constants.Latitude), Double.parseDouble(Constants.Longitude));//起点
                    LatLonPoint mEndPoint = new LatLonPoint(Double.parseDouble(itemData.getLatitude()), Double.parseDouble(itemData.getLongitude()));//终点
                    title = itemData.getName();
                    address = itemData.getAddress();
                    double juli_one = 0.00;
                    final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                            mStartPoint, mEndPoint);
                    if (!Constants.Longitude.equals("") && !Constants.Latitude.equals("")) {
                        juli_one = DistanceUtil.getDistance(Double.parseDouble(itemData.getLongitude()), Double.parseDouble(itemData.getLatitude()), Double.parseDouble(Constants.Longitude), Double.parseDouble(Constants.Latitude));
                    }
//                    if (juli_one < 3000) {
//                        /**
//                         * 步行
//                         */
////                      setfromandtoMarker(mStartPoint, mEndPoint, itemData.getName(), itemData.getAddress());
//                        RouteSearch.WalkRouteQuery query_walk = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
//                        mRouteSearch.calculateWalkRouteAsyn(query_walk);// 异步路径规划步行模式查询
//                    } else {
                    /**
                     * 驾车
                     */
//                      setfromandtoMarkerDrive(itemData.getWeidu(), itemData.getJingdu(), itemData.getLatitude(), itemData.getLongitude(), itemData.getName(), itemData.getAddress());
                    RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null, null, "");
                    // 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
                    mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
//                    }
                } else {
                    MyToast.show(Report_Shop_Location_Activity.this, "店铺经纬度有误，不能导航！");
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
            pullToRefreshListView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }

}
