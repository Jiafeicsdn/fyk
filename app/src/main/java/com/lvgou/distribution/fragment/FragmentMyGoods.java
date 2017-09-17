package com.lvgou.distribution.fragment;

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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
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
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.entity.GoodMarketChildEntity;
import com.lvgou.distribution.entity.GoodMarketEntity;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnGoodsClickListener;
import com.lvgou.distribution.internal.OnAddToCartAnimationFinishListener;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.CommonFunctions;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CustomProgressDialog;
import com.lvgou.distribution.viewholder.ClassifyOneViewHolder;
import com.lvgou.distribution.viewholder.ClassifyViewHolder;
import com.lvgou.distribution.viewholder.GoodMarketViewHolder;
import com.lvgou.distribution.viewholder.GoodsCityClassifyViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.ScreenUtils;
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
 * Created by Snow on 2016/3/19 0019.
 * 商品市场
 */
public class FragmentMyGoods extends Fragment implements View.OnClickListener, OnClassifyPostionClickListener<ClassifyEntity>, OnGoodsClickListener<GoodMarketEntity>, OnAddToCartAnimationFinishListener {

    private EditText et_search;
    private RelativeLayout rl_classify;
    private RelativeLayout rl_city;
    private RelativeLayout rl_order;
    private TextView tv_classify;
    private TextView tv_city;
    private TextView tv_order;
    private ImageView img_classify;
    private ImageView img_city;
    private ImageView img_order;
    private ImageView img_custome_service;
    private ImageView img_search;
    private ListView lv_list;
    private ImageView back_top;
    private PullToRefreshListView pullToRefreshListView;
    private LinearLayout ll_visibility;
    private RelativeLayout rl_gone;
    private ListView lv_clasifylistview;
    private GridView gradview;
    private View view_top;
    private ImageView line_img_leimu;
    private ImageView line_img_city;
    private ImageView line_img_fenxiao;
    private PopupWindow popupwindow;
    private PopupWindow popupwindowOne;
    private PopupWindow popupwindowTwo;
    private ListViewDataAdapter<ClassifyEntity> classifyOneEntityListViewDataAdapter;// 类目
    private ListViewDataAdapter<ClassifyEntity> classifyTwoEntityListViewDataAdapter;// 已分销
    private ListViewDataAdapter<ClassifyEntity> classifyCityEntityListViewDataAdapter;// 城市

    private ListViewDataAdapter<GoodMarketEntity> goodMarketEntityListViewDataAdapter;
    private ListViewDataAdapter<GoodMarketChildEntity> goodMarketChildEntityListViewDataAdapter;

    private ListViewDataAdapter<GoodMarketChildEntity> childEntityListViewDataAdapterOpen;// 点击查看更多回调返回的 子数据

    private ListViewDataAdapter<GoodMarketChildEntity> childEntityListViewDataAdapter;//收起数据


    private CustomProgressDialog progressDialog;
    private String distributorid = "";
    private String keyword = "";
    private String countrypath = "";
    private String categoryid = "0";
    private String isagent = "0";
    private int pageindex = 1;
    private String sign = "";

    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private String state_up = "1";//分类展开收起标示
    private String state_one = "1";//城市展开收起标示
    private String state_two = "1";//分销展开收起标示

    private String fenxiao = "";
    private String fenxiao_already = "";
    private String is_all = "1";//分销全部标识


    private boolean isTouch = false;

    private boolean keyUpDown = false;
    private int timer = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fargment_shoppings, null);
        initView(view);
        lv_list = pullToRefreshListView.getRefreshableView();
        EventBus.getDefault().register(this);
        initViewHolder();
        initGoodsViewHolder();
        onScrollView(lv_list);

        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (checkNet(getActivity())) {
            sign = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + isagent + pageindex);
            getData(distributorid, keyword, countrypath, categoryid, isagent, pageindex + "", sign);
        }
        initCreateView();

        return view;
    }

    public void initGoodsViewHolder() {
        goodMarketEntityListViewDataAdapter = new ListViewDataAdapter<GoodMarketEntity>();
        goodMarketEntityListViewDataAdapter.setViewHolderClass(getActivity(), GoodMarketViewHolder.class);
        GoodMarketViewHolder.setGoodMarketEntityOnGoodsClickListener(this);
        lv_list.setAdapter(goodMarketEntityListViewDataAdapter);
    }


    /**
     * 滑动监听
     *
     * @param listView
     */
    public void onScrollView(ListView listView) {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                         @Override
                                         public void onScrollStateChanged(AbsListView view, int scrollState) {
                                         }

                                         @Override
                                         public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                             if (firstVisibleItem == 0) {
                                                 back_top.setVisibility(View.GONE);
                                             } else if (firstVisibleItem > 1) {
                                                 back_top.setVisibility(View.VISIBLE);
                                             }
                                         }
                                     }

        );
    }

    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_gone.setVisibility(View.GONE);
    }

    /**
     * 初始化控件
     */
    public void initView(View view) {
        et_search = (EditText) view.findViewById(R.id.et_search);
        rl_classify = (RelativeLayout) view.findViewById(R.id.rl_classify);
        rl_city = (RelativeLayout) view.findViewById(R.id.rl_city);
        rl_order = (RelativeLayout) view.findViewById(R.id.rl_order);
        tv_classify = (TextView) view.findViewById(R.id.tv_classify);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_order = (TextView) view.findViewById(R.id.tv_order);
        tv_classify = (TextView) view.findViewById(R.id.tv_classify);
        tv_classify = (TextView) view.findViewById(R.id.tv_classify);
        img_classify = (ImageView) view.findViewById(R.id.img_classify);
        img_city = (ImageView) view.findViewById(R.id.img_city);
        img_order = (ImageView) view.findViewById(R.id.img_order);
        img_search = (ImageView) view.findViewById(R.id.img_search);
        img_custome_service = (ImageView) view.findViewById(R.id.img_custom_service);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        ll_visibility = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        rl_gone = (RelativeLayout) view.findViewById(R.id.rl_none);
        view_top = (View) view.findViewById(R.id.view_top);
        back_top = (ImageView) view.findViewById(R.id.back_top);
        line_img_leimu = (ImageView) view.findViewById(R.id.img_leimu);
        line_img_city = (ImageView) view.findViewById(R.id.img_city_one);
        line_img_fenxiao = (ImageView) view.findViewById(R.id.img_fenxiao);
        rl_city.setOnClickListener(this);
        rl_classify.setOnClickListener(this);
        rl_order.setOnClickListener(this);
        back_top.setOnClickListener(this);
    }

    public void initCreateView() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                    keyword = et_search.getText().toString().trim();
                    countrypath = "";
                    categoryid = "0";
                    isagent = "0";
                    pageindex = 1;
                    Constants.SELECTE_POSITION01 = "0";
                    Constants.SELECTE_POSITION02 = "0";
                    Constants.SELECTE_POSITION03 = "0";
                    String sign_ = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + isagent + pageindex);
                    getData(distributorid, keyword, countrypath, categoryid, isagent, pageindex + "", sign_);

                    // 隐藏软键盘
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
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
                    img_search.setVisibility(View.VISIBLE);
                } else {
                    img_search.setVisibility(View.GONE);
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
                String sign_ = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + isagent + pageindex);
                getData(distributorid, keyword, countrypath, categoryid, isagent, pageindex + "", sign_);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign_ = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + isagent + pageindex);
                    getData(distributorid, keyword, countrypath, categoryid, isagent, pageindex + "", sign_);
                } else {
                    MyToast.show(getActivity(), "没有更多数据");
                    new CancleRefreshTask().execute();
                }
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
                    line_img_leimu.setVisibility(View.VISIBLE);
                } else if (state_up.equals("2")) {
                    state_up = "1";
                    initSelected();
                }
                if (popupwindow != null && popupwindow.isShowing()){
                    popupwindow.dismiss();
                    return;
                } else {
                    initmPopupWindowView();
                    popupwindow.showAsDropDown(v, 0, 5);
                }
                if (popupwindowOne != null && popupwindowOne.isShowing()) {
                    popupwindowOne.dismiss();
                    return;
                }
                if (popupwindowTwo != null && popupwindowTwo.isShowing()) {
                    popupwindowTwo.dismiss();
                    return;
                }
                break;
            case R.id.rl_city:
                if (state_one.equals("1")) {
                    state_one = "2";
                    tv_city.setTextColor(getActivity().getResources().getColor(R.color.bg_new_guide_black));
                    img_city.setBackgroundResource(R.mipmap.bg_classify_select);
                    line_img_city.setVisibility(View.VISIBLE);
                } else if (state_one.equals("2")) {
                    state_one = "1";
                    initSelected();
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
                if (popupwindowTwo != null && popupwindowTwo.isShowing()) {
                    popupwindowTwo.dismiss();
                    return;
                }
                break;
            case R.id.rl_order:
                if (state_two.equals("1")) {
                    state_two = "2";
                    tv_order.setTextColor(getActivity().getResources().getColor(R.color.bg_new_guide_black));
                    img_order.setBackgroundResource(R.mipmap.bg_classify_select);
                    line_img_fenxiao.setVisibility(View.VISIBLE);
                } else if (state_two.equals("2")) {
                    state_two = "1";
                    initSelected();
                }
                if (popupwindowTwo != null && popupwindowTwo.isShowing()) {
                    popupwindowTwo.dismiss();
                    return;
                } else {
                    initmPopupWindowViewOne();
                    popupwindowTwo.showAsDropDown(v, 0, 5);
                }
                if (popupwindowOne != null && popupwindowOne.isShowing()) {
                    popupwindowOne.dismiss();
                    return;
                }
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    return;
                }
                break;
            case R.id.back_top:
                lv_list.setSelection(0);
                break;
        }
    }


    // 选中状态
    public void initSelected() {
        tv_classify.setTextColor(getActivity().getResources().getColor(R.color.bg_bottom_gray));
        tv_city.setTextColor(getActivity().getResources().getColor(R.color.bg_bottom_gray));
        tv_order.setTextColor(getActivity().getResources().getColor(R.color.bg_bottom_gray));
        img_classify.setBackgroundResource(R.mipmap.goods_up);
        img_city.setBackgroundResource(R.mipmap.goods_up);
        img_order.setBackgroundResource(R.mipmap.goods_up);
        line_img_leimu.setVisibility(View.GONE);
        line_img_city.setVisibility(View.GONE);
        line_img_fenxiao.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("FragmentMyGoods");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("FragmentMyGoods");
    }

    public void initViewHolder() {
        classifyOneEntityListViewDataAdapter = new ListViewDataAdapter<ClassifyEntity>();
        classifyOneEntityListViewDataAdapter.setViewHolderClass(this, ClassifyViewHolder.class);
        ClassifyViewHolder.setClassifyEntityOnClassifyPostionClickListener(this);

        classifyTwoEntityListViewDataAdapter = new ListViewDataAdapter<ClassifyEntity>();
        classifyTwoEntityListViewDataAdapter.setViewHolderClass(this, ClassifyOneViewHolder.class);
        ClassifyOneViewHolder.setClassifyEntityOnClassifyPostionClickListener(this);

        classifyCityEntityListViewDataAdapter = new ListViewDataAdapter<ClassifyEntity>();
        classifyCityEntityListViewDataAdapter.setViewHolderClass(this, GoodsCityClassifyViewHolder.class);
        GoodsCityClassifyViewHolder.setClassifyEntityOnClassifyPostionClickListener(this);

    }


    /**
     * 分类与分销
     */
    private void initmPopupWindowView() {
        // 获取自定义布局文件pop.xml的视图
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popwindow, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupwindow.setFocusable(false);
//      popupwindow.setBackgroundDrawable(new BitmapDrawable());
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow != null && popupwindow.isShowing()) {
                    state_up = "1";
                    initSelected();
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
                tv_classify.setTextColor(getActivity().getResources().getColor(R.color.bg_bottom_gray));
                img_classify.setBackgroundResource(R.mipmap.goods_up);
            }
        });

        // 数据赋值
        lv_clasifylistview = (ListView) customView.findViewById(R.id.lv_first_classifying);
        lv_clasifylistview.setAdapter(classifyOneEntityListViewDataAdapter);
        LinearLayout linearLayout = (LinearLayout) customView.findViewById(R.id.ll_list);
        ImageView bg = (ImageView) customView.findViewById(R.id.bg);
        bg.setVisibility(View.VISIBLE);

        Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_in);
        Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_in);
        linearLayout.startAnimation(animation1);

        bg.startAnimation(animation2);

    }

    /**
     * 分类与分销
     */
    private void initmPopupWindowViewOne() {
        // 获取自定义布局文件pop.xml的视图
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popwindow, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindowTwo = new PopupWindow(customView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupwindowTwo.setFocusable(false);
//        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindowTwo != null && popupwindowTwo.isShowing()) {
                    state_two = "1";
                    initSelected();
                    popupwindowTwo.dismiss();
                    popupwindowTwo = null;
                }
                return false;
            }
        });
        popupwindowTwo.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                state_two = "1";
                tv_order.setTextColor(getActivity().getResources().getColor(R.color.bg_bottom_gray));
                img_order.setBackgroundResource(R.mipmap.goods_up);
            }
        });

        // 数据赋值
        lv_clasifylistview = (ListView) customView.findViewById(R.id.lv_first_classifying);
        lv_clasifylistview.setAdapter(classifyTwoEntityListViewDataAdapter);
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
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupwindowOne.setFocusable(false);
//        popupwindowOne.setBackgroundDrawable(new BitmapDrawable());
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindowOne != null && popupwindowOne.isShowing()) {
                    state_one = "1";
                    initSelected();
                    popupwindowOne.dismiss();
                    popupwindowOne = null;
                }
                return false;
            }
        });

        popupwindowOne.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                state_one = "1";
                tv_city.setTextColor(getActivity().getResources().getColor(R.color.bg_bottom_gray));
                img_city.setBackgroundResource(R.mipmap.goods_up);

            }
        });
        // 数据赋值
        gradview = (GridView) customView.findViewById(R.id.lv_first_classifying);
        gradview.setAdapter(classifyCityEntityListViewDataAdapter);

        LinearLayout linearLayout = (LinearLayout) customView.findViewById(R.id.ll_list);
        ImageView bg = (ImageView) customView.findViewById(R.id.bg);
        bg.setVisibility(View.VISIBLE);

        Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_in);
        Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_in);
        linearLayout.startAnimation(animation1);

        bg.startAnimation(animation2);

    }


    /**
     * 点击回调
     */
    @Override
    public void onClassifyPostionClick(ClassifyEntity itemData, int postion) {
        switch (postion) {
            case 1:
                mIsUp = false;
                state_up = "1";
                initSelected();
                pageindex = 1;
                categoryid = itemData.getId();
                classifyOneEntityListViewDataAdapter.notifyDataSetChanged();
                sign = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + isagent + pageindex);
                getData(distributorid, keyword, countrypath, categoryid, isagent, pageindex + "", sign);
                popupwindow.dismiss();
                if (!goodMarketEntityListViewDataAdapter.isEmpty()) {
                    lv_list.setSelection(0);
                }
                break;
            case 2:
                mIsUp = false;
                pageindex = 1;
                state_two = "1";
                if (itemData.getId().equals("0")) {
                    is_all = "1";
                } else {
                    is_all = "0";
                }
                initSelected();
                tv_order.setText(itemData.getName());
                isagent = itemData.getId();
                classifyTwoEntityListViewDataAdapter.notifyDataSetChanged();
                sign = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + isagent + pageindex);
                getData(distributorid, keyword, countrypath, categoryid, isagent, pageindex + "", sign);
                popupwindowTwo.dismiss();
                if (!goodMarketEntityListViewDataAdapter.isEmpty()) {
                    lv_list.setSelection(0);
                }
                break;
            case 3:
                pageindex = 1;
                state_one = "1";
                mIsUp = false;
                initSelected();
                countrypath = itemData.getNum();
                sign = TGmd5.getMD5(distributorid + keyword + countrypath + categoryid + isagent + pageindex);
                getData(distributorid, keyword, countrypath, categoryid, isagent, pageindex + "", sign);
                popupwindowOne.dismiss();
                if (!goodMarketEntityListViewDataAdapter.isEmpty()) {
                    lv_list.setSelection(0);
                }
                break;
        }
    }

    /**
     * 获取数据
     *
     * @param distributorid
     * @param keywords
     * @param countrypath
     * @param categoryid
     * @param isagent
     * @param pageindex
     * @param sign
     */
    public void getData(String distributorid, String keywords, String countrypath, String categoryid, String isagent, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("keyword", keywords);
        maps.put("countrypath", countrypath);
        maps.put("categoryid", categoryid);
        maps.put("isagent", isagent);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);

        RequestTask.getInstance().getGoodsList(getActivity(), maps, new OnRequestListener());
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
                    classifyOneEntityListViewDataAdapter.removeAll();
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
                    classifyCityEntityListViewDataAdapter.removeAll();
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
                    /*********分销数据*******/
                    fenxiao = array.get(2).toString();
                    fenxiao_already = array.get(3).toString();
                    classifyTwoEntityListViewDataAdapter.removeAll();
                    ClassifyEntity classifyEntitythree_one = new ClassifyEntity("0", "全部商品", "");
                    ClassifyEntity classifyEntitythree = new ClassifyEntity("1", "未分销品牌", "(" + fenxiao + ")");
                    ClassifyEntity classifyEntitythree_ = new ClassifyEntity("2", "已分销品牌", "(" + fenxiao_already + ")");

                    classifyTwoEntityListViewDataAdapter.append(classifyEntitythree_one);
                    classifyTwoEntityListViewDataAdapter.append(classifyEntitythree);
                    classifyTwoEntityListViewDataAdapter.append(classifyEntitythree_);

                    /*********列表数据*******/
                    String list = array.get(4).toString();
                    JSONObject json_list = new JSONObject(list);
                    String items = json_list.getString("Items");
                    total_page = json_list.getInt("TotalPages");
                    Constants.ORIGN_DATAS_GUONEI = items;
                    JSONArray array_item = new JSONArray(items);
                    if (mIsUp == false) {
                        goodMarketEntityListViewDataAdapter.removeAll();
                    } else if (mIsUp == true) {
                    }
                    if (array_item != null && array_item.length() > 0) {
                        showOrGone();
                        ll_visibility.setVisibility(View.VISIBLE);
                        for (int i = 0; i < array_item.length(); i++) {
                            JSONObject json_item = array_item.getJSONObject(i);
                            String id = json_item.getString("ID");
                            String title = json_item.getString("ShopName");
                            String saleAttribute = json_item.getString("SaleAttribute");
                            String isDistributor = json_item.getString("IsDistributor");
                            String isMore_ = json_item.getString("IsMore");
                            String child_data = json_item.getString("ProductList");
                            /*********列表内层数据*******/
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
                            if (is_all.equals("1")) {
                                GoodMarketEntity goodMarketEntity = new GoodMarketEntity(id, title, saleAttribute, isDistributor, isMore_, goodMarketChildEntityListViewDataAdapter, "1", "", "2", "1");
                                goodMarketEntityListViewDataAdapter.append(goodMarketEntity);
                            } else {
                                GoodMarketEntity goodMarketEntity = new GoodMarketEntity(id, title, saleAttribute, isDistributor, isMore_, goodMarketChildEntityListViewDataAdapter, "1", "", "2", "2");
                                goodMarketEntityListViewDataAdapter.append(goodMarketEntity);
                            }
                        }
                    } else {
                        showOrGone();
                        rl_gone.setVisibility(View.VISIBLE);
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
     * 获取更多
     *
     * @param sellerID
     * @param productIDs
     * @param sign
     */
    public void loadMore(String sellerID, String productIDs, String sign) {
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
     * 移入货架
     *
     * @param distributorid
     * @param sellerID
     * @param sign
     */
    public void romveGood(String distributorid, String sellerID, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sellerID", sellerID);
        maps.put("sign", sign);

        RequestTask.getInstance().doRemoveGoods(getActivity(), maps, new OnRemoveRequestListener());
    }

    private class OnRemoveRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                String status = jsonObject1.getString("status");
                if (status.equals("1")) {
                    MyToast.show(getActivity(), "分销成功");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 点击回调
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onGoodsClick(GoodMarketEntity itemData, int postion, View view, int num) {
        switch (num) {
            case 3:// 移入货架
                String sign_fenixao = TGmd5.getMD5(distributorid + itemData.getId());
                romveGood(distributorid, itemData.getId(), sign_fenixao);
                View view_ = new View(getActivity());
                view_.setBackgroundResource(R.drawable.red_out_stroke_shape);
                view_.setMinimumWidth(15);
                view_.setMinimumHeight(15);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        (int) ScreenUtils.dpToPx(getActivity(), 100),
                        (int) ScreenUtils.dpToPx(getActivity(), 100));
                int[] start_location = new int[2];// 存储起始坐标X,Y数组
                view.getLocationInWindow(start_location);// 获取view在屏幕的位置，即起始坐标

                int[] end_location = new int[2];// 动画结束坐标
                view_top.getLocationInWindow(end_location);// 模拟位置
                // 计算位移
                int endX = (end_location[0] - start_location[0] - (int) ScreenUtils.dpToPx(getActivity(), 40));// 动画位移的X坐标
                int endY = end_location[1] - start_location[1] - (int) ScreenUtils.dpToPx(getActivity(), 20);// 动画位移的y坐标

                CommonFunctions.getInstance().setAddToCartAnimation(getActivity(), view_, lp, start_location, endX, endY);

                fenxiao = (Integer.parseInt(fenxiao) - 1) + "";
                fenxiao_already = (Integer.parseInt(fenxiao_already) + 1) + "";
                classifyTwoEntityListViewDataAdapter.removeAll();
                ClassifyEntity classifyEntitythree_one = new ClassifyEntity("0", "全部商品", "");
                ClassifyEntity classifyEntitythree = new ClassifyEntity("1", "未分销品牌", "(" + fenxiao + ")");
                ClassifyEntity classifyEntitythree_ = new ClassifyEntity("2", "已分销品牌", "(" + fenxiao_already + ")");

                classifyTwoEntityListViewDataAdapter.append(classifyEntitythree_one);
                classifyTwoEntityListViewDataAdapter.append(classifyEntitythree);
                classifyTwoEntityListViewDataAdapter.append(classifyEntitythree_);
                break;
            case 4:// 加载更多
                childEntityListViewDataAdapter = new ListViewDataAdapter<GoodMarketChildEntity>();
                itemData.setGuide_status("2");
                childEntityListViewDataAdapterOpen = itemData.getGoodMarketChildEntityListViewDataAdapter();
                int size = childEntityListViewDataAdapterOpen.getCount();
                String ids = "";
                for (int k = 0; k < size; k++) {
                    GoodMarketChildEntity childEntity = childEntityListViewDataAdapterOpen.getItem(k);
                    ids += childEntity.getId() + ",";
                    childEntityListViewDataAdapter.append(childEntity);
                }
                String str_ids = ids.substring(0, ids.length() - 1);
                String sign_ = TGmd5.getMD5(itemData.getId() + str_ids);

                loadMore(itemData.getId(), str_ids, sign_);
                itemData.setGoodMarketChildEntityListViewDataAdapter(childEntityListViewDataAdapterOpen);
                goodMarketEntityListViewDataAdapter.notifyDataSetChanged();
                break;
            case 5:// 点击收起
                itemData.setGuide_status("1");
                itemData.setGoodMarketChildEntityListViewDataAdapter(childEntityListViewDataAdapter);
                goodMarketEntityListViewDataAdapter.notifyDataSetChanged();
                lv_list.setItemChecked(postion + 1, true);
                lv_list.setSelection(postion + 1);
                lv_list.smoothScrollToPosition(postion + 1);
                break;
            case 6:// 点击收起商品
                itemData.setStatus_zhankai("2");
                itemData.setStatus_zhankai_show("2");
                goodMarketEntityListViewDataAdapter.notifyDataSetChanged();
                break;
            case 7:// 点击展开商品
                itemData.setStatus_zhankai("1");
                itemData.setStatus_zhankai_show("2");
                goodMarketEntityListViewDataAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 动画结束 接口
     */
    @Override
    public void OnAddToCartAnimationFinish() {
        CommonFunctions.getInstance().setCartImageAnim(view_top);
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

    private int keyUpDownListener() {
        new Thread() {
            public void run() {
                while (keyUpDown) {
                    try {
                        sleep(100);
                        timer++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return timer;
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
        if (event.getEventType() == EventType.GOOD_DISMISS) {
            if (popupwindowOne != null && popupwindowOne.isShowing()) {
                popupwindowOne.dismiss();
                return;
            }
            if (popupwindow != null && popupwindow.isShowing()) {
                popupwindow.dismiss();
                return;
            }
            if (popupwindowTwo != null && popupwindowTwo.isShowing()) {
                popupwindowTwo.dismiss();
                return;
            }
        }
    }
}
