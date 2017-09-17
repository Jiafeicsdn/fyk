package com.lvgou.distribution.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.LoginActivity;
import com.lvgou.distribution.activity.OrderActivity;
import com.lvgou.distribution.activity.ShopMnagerActivity;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.entity.GoodMarketChildEntity;
import com.lvgou.distribution.entity.GoodMarketEntity;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnGoodsClickListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CustomProgressDialog;
import com.lvgou.distribution.view.MyGridView;
import com.lvgou.distribution.viewholder.CityClassifyViewHolder;
import com.lvgou.distribution.viewholder.ClassifyViewHolder;
import com.lvgou.distribution.viewholder.GuoNeiViewHolder;
import com.umeng.analytics.MobclickAgent;
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
 * Created by Snow on 2016/3/19 0019
 * 我的货架
 */
public class FragmentDistributionMarket extends Fragment implements View.OnClickListener, OnClassifyPostionClickListener<ClassifyEntity>, OnGoodsClickListener<GoodMarketEntity> {

    private RelativeLayout rl_classify;
    private RelativeLayout rl_city;
    private RelativeLayout rl_order;
    private EditText et_search;
    private TextView tv_classify;
    private TextView tv_city;
    private TextView tv_order;
    private ImageView img_classify;
    private ImageView img_city;
    private ImageView img_order;
    private ListView lv_list;
    private PullToRefreshListView pullToRefreshListView;
    private LinearLayout ll_guonei;
    private LinearLayout ll_out_sea;

    private LinearLayout ll_visibility;
    private LinearLayout ll_visibilty_one;
    private RelativeLayout rl_none_guonei;
    private RelativeLayout rl_none_haiwai;
    private TextView tv_add_goods;
    private ImageView img_go_shop;
    private MyGridView expand_listview;
    private GridView grid_view;
    private ListView lv_clasifylistview;
    private PopupWindow popupwindow;
    private PopupWindow popupwindowOne;
    private Dialog dialog;
    private CustomProgressDialog progressDialog;

    private ListViewDataAdapter<ClassifyEntity> classifyOneEntityListViewDataAdapter;// 类目
    private ListViewDataAdapter<ClassifyEntity> classifyCityEntityListViewDataAdapter;// 城市
    private ListViewDataAdapter<GoodMarketEntity> goodGuoNeiListViewDataAdapter;

    private ListViewDataAdapter<GoodMarketChildEntity> goodMarketChildEntityListViewDataAdapter;
    private ListViewDataAdapter<GoodMarketChildEntity> childEntityListViewDataAdapterOpen;// 查看更多


    private ListViewDataAdapter<GoodMarketChildEntity> goodMarketChildEntityListViewDataAdapterShouQi;// 展开收起绑定数据


    private String distributorid = "";
    private String keyword = "";
    private String countrypath = "";
    private String categoryid = "0";
    private int pageindex = 1;
    private String sign = "";
    private String sign_ = "";// 移除商品  sign
    private String id = "";//  移除商品 id

    private LinearLayout ll_remove;
    private RelativeLayout search02;
    private ImageView img_serach;

    boolean mIsUp;// 是否上拉加载
    private String pull_state = "1";// 1  国内商品，2 海外商品

    private String state_up = "1";// 分类 展开与收起
    private String state_up_city = "1";// 城市的展开与收起

    private String guonei_xianshi;// 空数据，1 请求getData  2:请求 getDataOne


    private boolean isTouch = false;
    private boolean keyUpDown = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_markets, container, false);
        initView(view);
        lv_list = pullToRefreshListView.getRefreshableView();
        EventBus.getDefault().register(this);

        initGoodsViewHolder();
        initViewHolder();
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (checkNet(getActivity())) {
            sign = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + pageindex);
            getData(distributorid, keyword, countrypath, categoryid, pageindex + "", sign);
        }

        initCreateView();

        return view;
    }

    public void initView(View view) {
        rl_classify = (RelativeLayout) view.findViewById(R.id.rl_classify);
        rl_city = (RelativeLayout) view.findViewById(R.id.rl_city);
        rl_order = (RelativeLayout) view.findViewById(R.id.rl_order);
        et_search = (EditText) view.findViewById(R.id.et_search);

        img_classify = (ImageView) view.findViewById(R.id.img_classify);
        img_city = (ImageView) view.findViewById(R.id.img_city);
        img_order = (ImageView) view.findViewById(R.id.img_order);
        img_serach = (ImageView) view.findViewById(R.id.img_search);

        tv_classify = (TextView) view.findViewById(R.id.tv_classify);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_order = (TextView) view.findViewById(R.id.tv_order);
        ll_guonei = (LinearLayout) view.findViewById(R.id.ll_guonei);
        ll_out_sea = (LinearLayout) view.findViewById(R.id.ll_out_sea);
        grid_view = (GridView) view.findViewById(R.id.grid_view);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list_guonei);
        ll_visibility = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        ll_visibilty_one = (LinearLayout) view.findViewById(R.id.ll_visibilty_one);
        rl_none_guonei = (RelativeLayout) view.findViewById(R.id.rl_none_guonei);
        rl_none_haiwai = (RelativeLayout) view.findViewById(R.id.rl_none_haiwai);
        tv_add_goods = (TextView) view.findViewById(R.id.add_goods);
        img_go_shop = (ImageView) view.findViewById(R.id.go_shop);
        search02 = (RelativeLayout) view.findViewById(R.id.search02);
        img_serach = (ImageView) view.findViewById(R.id.img_search);
        rl_classify.setOnClickListener(this);
        rl_city.setOnClickListener(this);
        rl_order.setOnClickListener(this);
        tv_add_goods.setOnClickListener(this);
        img_go_shop.setOnClickListener(this);

    }


    public void initCreateView() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                keyword = et_search.getText().toString().trim();
                distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                countrypath = "";
                categoryid = "0";
                pageindex = 1;
                Constants.SELECTE_POSITION01 = "0";
                Constants.SELECTE_POSITION02 = "0";
                Constants.SELECTE_POSITION03 = "0";
                String sign_ = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + pageindex);
                getData(distributorid, keyword, countrypath, categoryid, pageindex + "", sign_);
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
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

        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign_ = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + pageindex);
                getData(distributorid, keyword, countrypath, categoryid, pageindex + "", sign_);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                pageindex += 1;
                String sign_ = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + pageindex);
                getData(distributorid, keyword, countrypath, categoryid, pageindex + "", sign_);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_classify:
                if (state_up.equals("1")) {
                    state_up = "2";
                    tv_classify.setTextColor(getActivity().getResources().getColor(R.color.bg_new_guide_black));
                    img_classify.setBackgroundResource(R.mipmap.bg_classify_select);
                } else if (state_up.equals("2")) {
                    state_up = "1";
                    initWindowd();
                }
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    return;
                } else {
                    initmPopupWindowView("one");
                    popupwindow.showAsDropDown(v, 0, 5);
                }
                if (popupwindowOne != null && popupwindowOne.isShowing()) {
                    popupwindowOne.dismiss();
                    return;
                }
                break;
            case R.id.rl_city:
                if (state_up_city.equals("1")) {
                    state_up_city = "2";
                    tv_city.setTextColor(getActivity().getResources().getColor(R.color.bg_new_guide_black));
                    img_city.setBackgroundResource(R.mipmap.bg_classify_select);
                } else if (state_up_city.equals("2")) {
                    state_up_city = "1";
                    initWindowd();
                }
                if (popupwindowOne != null && popupwindowOne.isShowing()) {
                    popupwindowOne.dismiss();
                    return;
                } else {
                    initCityPopupWindowView();
                    popupwindowOne.showAsDropDown(v, 0, 5);
                }
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    return;
                }
                break;
            case R.id.rl_order:
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                getActivity().startActivity(intent);

                if (popupwindowOne != null && popupwindowOne.isShowing()) {
                    popupwindowOne.dismiss();
                    return;
                }
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    return;
                }
                break;
            case R.id.add_goods:
                EventFactory.sendUpdateCartCount(2);
                break;
            case R.id.go_shop:
                Intent intent_two = new Intent(getActivity(), ShopMnagerActivity.class);
                getActivity().startActivity(intent_two);
                break;
        }
    }

    public void initWindowd() {
        tv_classify.setTextColor(getActivity().getResources().getColor(R.color.bg_bottom_gray));
        tv_city.setTextColor(getActivity().getResources().getColor(R.color.bg_bottom_gray));
        img_city.setBackgroundResource(R.mipmap.goods_up);
        img_classify.setBackgroundResource(R.mipmap.goods_up);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("DistributionMarket");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("DistributionMarket");
    }

    public void initGoodsViewHolder() {
        goodGuoNeiListViewDataAdapter = new ListViewDataAdapter<GoodMarketEntity>();
        goodGuoNeiListViewDataAdapter.setViewHolderClass(getActivity(), GuoNeiViewHolder.class);
        GuoNeiViewHolder.setOnGoodsClickListener(this);
        lv_list.setAdapter(goodGuoNeiListViewDataAdapter);
    }

    public void initViewHolder() {
        classifyOneEntityListViewDataAdapter = new ListViewDataAdapter<ClassifyEntity>();
        classifyOneEntityListViewDataAdapter.setViewHolderClass(this, ClassifyViewHolder.class);
        ClassifyViewHolder.setClassifyEntityOnClassifyPostionClickListener(this);

        classifyCityEntityListViewDataAdapter = new ListViewDataAdapter<ClassifyEntity>();
        classifyCityEntityListViewDataAdapter.setViewHolderClass(this, CityClassifyViewHolder.class);
        CityClassifyViewHolder.setClassifyEntityOnClassifyPostionClickListener(this);

    }

    /**
     * 国内有无数据页面显示
     */
    public void showOrGone() {
        ll_visibilty_one.setVisibility(View.GONE);
        rl_none_guonei.setVisibility(View.GONE);
    }

    /**
     * 分类与分销
     */
    private void initmPopupWindowView(String text) {
        // 获取自定义布局文件pop.xml的视图
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popwindow, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        popupwindow.setFocusable(false);
//        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow != null && popupwindow.isShowing()) {
                    state_up = "1";
                    initWindowd();
                    popupwindow.dismiss();
                    popupwindow = null;
                }
                return false;
            }
        });
        popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                state_up = "1";
                tv_classify.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
                img_classify.setBackgroundResource(R.mipmap.goods_up);
            }
        });

        // 数据赋值
        lv_clasifylistview = (ListView) customView.findViewById(R.id.lv_first_classifying);
        if (text.equals("one")) {
            lv_clasifylistview.setAdapter(classifyOneEntityListViewDataAdapter);
        }
        LinearLayout linearLayout = (LinearLayout) customView.findViewById(R.id.ll_list);
        ImageView bg = (ImageView) customView.findViewById(R.id.bg);
        bg.setVisibility(View.VISIBLE);

        Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_in);
        Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_in);
        linearLayout.startAnimation(animation1);

        bg.startAnimation(animation2);

    }

    /**
     * 城市
     */
    private void initCityPopupWindowView() {
        // 获取自定义布局文件pop.xml的视图
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.city_popwindow, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindowOne = new PopupWindow(customView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        popupwindowOne.setFocusable(false);
//        popupwindowOne.setBackgroundDrawable(new BitmapDrawable());
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindowOne != null && popupwindowOne.isShowing()) {
                    state_up_city = "1";
                    initWindowd();
                    popupwindowOne.dismiss();
                    popupwindowOne = null;
                }
                return false;
            }
        });
        popupwindowOne.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                state_up_city = "1";
                tv_city.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
                img_city.setBackgroundResource(R.mipmap.goods_up);
            }
        });
        // 数据赋值
        grid_view = (GridView) customView.findViewById(R.id.lv_first_classifying);
        grid_view.setAdapter(classifyCityEntityListViewDataAdapter);

        LinearLayout linearLayout = (LinearLayout) customView.findViewById(R.id.ll_list);
        ImageView bg = (ImageView) customView.findViewById(R.id.bg);
        bg.setVisibility(View.VISIBLE);

        Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_in);
        Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_in);
        linearLayout.startAnimation(animation1);

        bg.startAnimation(animation2);

    }

    /**
     * 获取国内数据
     *
     * @param distributorid
     * @param keywords
     * @param countrypath
     * @param categoryid
     * @param pageindex
     * @param sign
     */
    public void getData(String distributorid, String keywords, String countrypath, String categoryid, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("keyword", keywords);
        maps.put("countrypath", countrypath);
        maps.put("categoryid", categoryid);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getGoodListIn(getActivity(), maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                String result = jsonObject.getString("result");
                if (status.equals("1")) {
                    JSONArray array = new JSONArray(result);
                    /*********解析分类数据*******/
                    String fenlei = array.get(0).toString();
                    if (mIsUp == false) {
                        classifyOneEntityListViewDataAdapter.removeAll();
                    } else {
                        // 不做处理
                    }
                    JSONArray array_classify = new JSONArray(fenlei);
                    ClassifyEntity classifyEntity1 = new ClassifyEntity("0", "全部分类", "");
                    classifyOneEntityListViewDataAdapter.append(classifyEntity1);
                    if (array_classify != null && array_classify.length() > 0) {
                        for (int i = 0; i < array_classify.length(); i++) {
                            JSONObject json_classify = array_classify.getJSONObject(i);
                            String id = json_classify.getString("ID");
                            String name = json_classify.getString("CategoryName");
                            String num = json_classify.getString("Depth");
                            ClassifyEntity classifyEntity = new ClassifyEntity(id, name, num);
                            classifyOneEntityListViewDataAdapter.append(classifyEntity);
                        }
                    }
                    /*********解析城市数据*******/
                    String city = array.get(1).toString();
                    JSONArray array_city = new JSONArray(city);
                    if (mIsUp == false) {
                        classifyCityEntityListViewDataAdapter.removeAll();
                    } else {
                        // 不做处理
                    }
                    ClassifyEntity classifyEntity2 = new ClassifyEntity("0", "全部", "");
                    classifyCityEntityListViewDataAdapter.append(classifyEntity2);
                    if (array_city != null && array_city.length() > 0) {
                        for (int i = 0; i < array_city.length(); i++) {
                            JSONObject json_city = array_city.getJSONObject(i);
                            String id = json_city.getString("ID");
                            String name = json_city.getString("CountryName");
                            String path = json_city.getString("CountryPath");
                            ClassifyEntity classifyEntity = new ClassifyEntity(id, name, path);
                            classifyCityEntityListViewDataAdapter.append(classifyEntity);
                        }
                    }
                    Constants.AGGENT_COUNT = array.get(3).toString();
                    EventFactory.sendGoodsGuider(Integer.parseInt(Constants.AGGENT_COUNT));
                    /*********列表数据*******/
                    String list = array.get(2).toString();
                    JSONArray array_item = new JSONArray(list);
                    if (mIsUp == false) {
                        goodGuoNeiListViewDataAdapter.removeAll();
                    } else {
                        // 不做处理
                    }
                    if (array_item != null && array_item.length() > 0) {
                        showOrGone();
                        guonei_xianshi = "2";
                        ll_visibilty_one.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_item.length(); i++) {
                            JSONObject json_item = array_item.getJSONObject(i);
                            String id = json_item.getString("ID");
                            String sellerId = json_item.getString("SellerID");
                            String title = json_item.getString("ShopName");
                            String saleAttribute = json_item.getString("DistributorID");
                            String isMore_ = json_item.getString("IsMore");
                            String child_data = json_item.getString("ProductList");
                            goodMarketChildEntityListViewDataAdapter = new ListViewDataAdapter<GoodMarketChildEntity>();
                            JSONArray array_child = new JSONArray(child_data);
                            if (array_child != null && array_child.length() > 0) {
                                for (int j = 0; j < array_child.length(); j++) {
                                    JSONObject jsonObject_child = array_child.getJSONObject(j);
                                    String id_ = jsonObject_child.getString("ID");
                                    String name_ = jsonObject_child.getString("ProductName");
                                    String sale_price_ = jsonObject_child.getString("Price_Min");
                                    String price_Distributor_ = jsonObject_child.getString("Price_Distributor");
                                    String sale_num_ = jsonObject_child.getString("SellAmount");
                                    String kucun_ = jsonObject_child.getString("Amount");
                                    String img_url_ = jsonObject_child.getString("PicUrl");
                                    String quota_ = jsonObject_child.getString("Quota");
                                    GoodMarketChildEntity childEntity = new GoodMarketChildEntity(id_, kucun_, price_Distributor_, sale_num_, sale_price_, name_, img_url_, quota_);
                                    goodMarketChildEntityListViewDataAdapter.append(childEntity);
                                }
                            }
                            GoodMarketEntity goodMarketEntity = new GoodMarketEntity(id, title, saleAttribute, "", isMore_, goodMarketChildEntityListViewDataAdapter, "1", sellerId, "0", "0");
                            goodGuoNeiListViewDataAdapter.append(goodMarketEntity);
                        }
                    } else {
                        guonei_xianshi = "1";
                        MyToast.show(getActivity(), "没有更多数据");
                    }
                } else if (status.equals("0")) {
                    MyToast.show(getActivity(), jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("请登录")) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showProgressDialog();

        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            pullToRefreshListView.onRefreshComplete();
            dismissProgressDialog();

        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            pullToRefreshListView.onRefreshComplete();
            dismissProgressDialog();
        }
    }

    /**
     * 获取国内数据
     *
     * @param distributorid
     * @param keywords
     * @param countrypath
     * @param categoryid
     * @param pageindex
     * @param sign
     */
    public void getDataOne(String distributorid, String keywords, String countrypath, String categoryid, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("keyword", keywords);
        maps.put("countrypath", countrypath);
        maps.put("categoryid", categoryid);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getGoodListIn(getActivity(), maps, new OnRequestOneListener());
    }

    private class OnRequestOneListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                String result = jsonObject.getString("result");
                if (status.equals("1")) {
                    JSONArray array = new JSONArray(result);
                    /*********解析分类数据*******/
                    String fenlei = array.get(0).toString();
                    if (mIsUp == false) {
                        classifyOneEntityListViewDataAdapter.removeAll();
                    } else {
                        // 不做处理
                    }
                    JSONArray array_classify = new JSONArray(fenlei);
                    ClassifyEntity classifyEntity1 = new ClassifyEntity("0", "全部分类", "");
                    classifyOneEntityListViewDataAdapter.append(classifyEntity1);
                    if (array_classify != null && array_classify.length() > 0) {
                        for (int i = 0; i < array_classify.length(); i++) {
                            JSONObject json_classify = array_classify.getJSONObject(i);
                            String id = json_classify.getString("ID");
                            String name = json_classify.getString("CategoryName");
                            String num = json_classify.getString("Depth");
                            ClassifyEntity classifyEntity = new ClassifyEntity(id, name, num);
                            classifyOneEntityListViewDataAdapter.append(classifyEntity);
                        }
                    }
                    /*********解析城市数据*******/
                    String city = array.get(1).toString();
                    JSONArray array_city = new JSONArray(city);
                    if (mIsUp == false) {
                        classifyCityEntityListViewDataAdapter.removeAll();
                    } else {
                        // 不做处理
                    }
                    ClassifyEntity classifyEntity2 = new ClassifyEntity("0", "全部", "");
                    classifyCityEntityListViewDataAdapter.append(classifyEntity2);
                    if (array_city != null && array_city.length() > 0) {
                        for (int i = 0; i < array_city.length(); i++) {
                            JSONObject json_city = array_city.getJSONObject(i);
                            String id = json_city.getString("ID");
                            String name = json_city.getString("CountryName");
                            String path = json_city.getString("CountryPath");
                            ClassifyEntity classifyEntity = new ClassifyEntity(id, name, path);
                            classifyCityEntityListViewDataAdapter.append(classifyEntity);
                        }
                    }
                    String list = array.get(2).toString();
                    JSONArray array_item = new JSONArray(list);
                    if (array_item != null && array_item.length() > 0) {
                    } else {
                        guonei_xianshi = "1";
                        EventFactory.sendUpdateCartCount(3);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showProgressDialog();

        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            pullToRefreshListView.onRefreshComplete();
            dismissProgressDialog();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            pullToRefreshListView.onRefreshComplete();
            dismissProgressDialog();
        }
    }

    /**
     * 移出商品操作
     *
     * @param distributorid
     * @param id
     * @param sign
     */
    public void doRemove(String distributorid, String id, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("id", id);
        maps.put("sign", sign);

        RequestTask.getInstance().doRemove(getActivity(), maps, new OnRemoveRequestListener());
    }

    private class OnRemoveRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(getActivity(), "移出成功");
                    sign = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + pageindex);
                    getDataOne(distributorid, keyword, countrypath, categoryid, pageindex + "", sign);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载更多
     *
     * @param sellerID
     * @param productIDs
     * @param sign
     */
    public void loadMoreData(String sellerID, String productIDs, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("sellerID", sellerID);
        maps.put("productIDs", productIDs);
        maps.put("sign", sign);
        RequestTask.getInstance().doLoadMore(getActivity(), maps, new OnLoadMoreRequestListener());
    }

    private class OnLoadMoreRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject_ = new JSONObject(response);
                String status = jsonObject_.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject_.getString("result");
                    JSONArray array = new JSONArray(result);
                    String result_ = array.get(0).toString();
                    JSONArray array_ = new JSONArray(result_);
                    if (array_ != null && array_.length() > 0) {
                        for (int i = 0; i < array_.length(); i++) {
                            JSONObject jsonObject_child = array_.getJSONObject(i);
                            String id_ = jsonObject_child.getString("ID");
                            String name_ = jsonObject_child.getString("ProductName");
                            String sale_price_ = jsonObject_child.getString("Price_Min");
                            String price_Distributor_ = jsonObject_child.getString("Price_Distributor");
                            String sale_num_ = jsonObject_child.getString("SellAmount");
                            String kucun_ = jsonObject_child.getString("Amount");
                            String img_url_ = jsonObject_child.getString("PicUrl");
                            String quota_ = jsonObject_child.getString("Quota");
                            GoodMarketChildEntity childEntity = new GoodMarketChildEntity(id_, kucun_, price_Distributor_, sale_num_, sale_price_, name_, img_url_, quota_);
                            childEntityListViewDataAdapterOpen.append(childEntity);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下拉框点击回调
     */
    @Override
    public void onClassifyPostionClick(ClassifyEntity itemData, int postion) {
        switch (postion) {
            case 1:
                mIsUp = false;
                state_up = "1";
                pageindex = 1;
                initWindowd();
                categoryid = itemData.getId();
                classifyOneEntityListViewDataAdapter.notifyDataSetChanged();
                sign = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + pageindex);
                getData(distributorid, keyword, countrypath, categoryid, pageindex + "", sign);
                if (!goodGuoNeiListViewDataAdapter.isEmpty()) {
                    lv_list.setSelection(0);
                }
                popupwindow.dismiss();
                break;
            case 3:
                mIsUp = false;
                state_up_city = "1";
                pageindex = 1;
                initWindowd();
                countrypath = itemData.getNum();
                classifyCityEntityListViewDataAdapter.notifyDataSetChanged();
                sign = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + pageindex);
                getData(distributorid, keyword, countrypath, categoryid, pageindex + "", sign);
                if (!goodGuoNeiListViewDataAdapter.isEmpty()) {
                    lv_list.setSelection(0);
                }
                popupwindowOne.dismiss();
                break;
        }
    }

    /**
     * 父 item  点击回调
     */
    @Override
    public void onGoodsClick(final GoodMarketEntity itemData, int postion, View view, int num) {
        switch (num) {
            case 1:// 移除商品操作
                sign_ = TGmd5.getMD5(distributorid + itemData.getId());
                id = itemData.getId();
                dialog = new Dialog(getActivity(),
                        R.style.Mydialog);
                View view1 = View.inflate(getActivity(),
                        R.layout.delete_shop_dialog, null);
                Button sure = (Button) view1.findViewById(R.id.sure);
                Button cancle = (Button) view1.findViewById(R.id.cancle);
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doRemove(distributorid, id, sign_);
                        goodGuoNeiListViewDataAdapter.remove(itemData);
                        if (goodGuoNeiListViewDataAdapter.isEmpty()) {
                            showOrGone();
                            rl_none_guonei.setVisibility(View.VISIBLE);
                        }
                        dialog.dismiss();
                    }
                });
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(view1);
                dialog.show();
                break;
            case 2://更多
                goodMarketChildEntityListViewDataAdapterShouQi = new ListViewDataAdapter<GoodMarketChildEntity>();
                itemData.setGuide_status("2");
                childEntityListViewDataAdapterOpen = itemData.getGoodMarketChildEntityListViewDataAdapter();
                int size = childEntityListViewDataAdapterOpen.getCount();
                String ids = "";
                for (int k = 0; k < size; k++) {
                    GoodMarketChildEntity childEntity = childEntityListViewDataAdapterOpen.getItem(k);
                    ids += childEntity.getId() + ",";
                    goodMarketChildEntityListViewDataAdapterShouQi.append(childEntity);
                }

                String str_ids = ids.substring(0, ids.length() - 1);
                String sign_more = TGmd5.getMD5(itemData.getSellerId() + str_ids);

                loadMoreData(itemData.getSellerId(), str_ids, sign_more);
                itemData.setGoodMarketChildEntityListViewDataAdapter(childEntityListViewDataAdapterOpen);
                goodGuoNeiListViewDataAdapter.notifyDataSetChanged();
                break;
            case 3:// 收起 功能
                itemData.setGuide_status("1");
                itemData.setGoodMarketChildEntityListViewDataAdapter(goodMarketChildEntityListViewDataAdapterShouQi);
                goodGuoNeiListViewDataAdapter.notifyDataSetChanged();
                lv_list.setItemChecked(postion + 1, true);
                lv_list.setSelection(postion + 1);
                lv_list.smoothScrollToPosition(postion + 1);
                break;
        }
    }

    /**
     * 销毁进度条对话框
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 显示进度条对话框
     */
    public void showProgressDialog() {
        showProgressDialog("加载中...", true, null);
    }

    /**
     * 显示进度条对话框
     *
     * @param message
     * @param cancel
     */
    public void showProgressDialog(String message, boolean cancel, DialogInterface.OnCancelListener cancelListener) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(getActivity());
        }
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancel);
        progressDialog.setCanceledOnTouchOutside(false);
        if (cancelListener != null) {
            progressDialog.setOnCancelListener(cancelListener);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    /**
     * 判断网络是否连接
     */
    public static boolean checkNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null;// 网络是否连接
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


    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.ORDER_COMPLETE) {
            //保存成功，拉取第一页数据
            pageindex = 1;
            String sign_ = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + pageindex);
            getData(distributorid, keyword, countrypath, categoryid, pageindex + "", sign_);
            lv_list.setSelection(0);
        } else if (event.getEventType() == EventType.MARKET_DISMISS) {
            if (popupwindowOne != null && popupwindowOne.isShowing()) {
                popupwindowOne.dismiss();
                return;
            }
            if (popupwindow != null && popupwindow.isShowing()) {
                popupwindow.dismiss();
                return;
            }
        }
    }
}
