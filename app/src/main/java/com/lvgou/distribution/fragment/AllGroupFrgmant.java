package com.lvgou.distribution.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.PublishGroupDetialActivity;
import com.lvgou.distribution.bean.GroupAllBean;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.DayEntity;
import com.lvgou.distribution.entity.GroupIndexEntity;
import com.lvgou.distribution.entity.ProvinceEntity;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.GroupAllPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.CalendarUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.DaySelectViewHolder;
import com.lvgou.distribution.viewholder.GroupIndexViewHolder;
import com.lvgou.distribution.viewholder.HotCityViewHolder;
import com.lvgou.distribution.wheelview.OnWheelScrollListener;
import com.lvgou.distribution.wheelview.WheelView;
import com.lvgou.distribution.wheelview.adapter.AbstractWheelTextAdapter;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.microedition.khronos.egl.EGLDisplay;

/**
 * Created by Snow on 2016/9/30.
 * 全部派团
 */
public class AllGroupFrgmant extends Fragment implements GroupView, View.OnClickListener, OnClassifyPostionClickListener<ProvinceEntity>, OnListItemClickListener<GroupIndexEntity> {


    private RelativeLayout rl_date;
    private RelativeLayout rl_city;
    private RelativeLayout rl_daofu;
    private RelativeLayout rl_state;
    private TextView tv_data;
    private TextView tv_city;
    private TextView tv_day;
    private TextView tv_daofu;
    private ImageView img_date;
    private ImageView img_city;
    private ImageView img_daofu;
    private ImageView img_state;

    private LinearLayout ll_visibilty;
    private RelativeLayout rl_none;
    private PullToRefreshListView pullToRefreshListView;


    private ListViewDataAdapter<GroupIndexEntity> groupIndexEntityListViewDataAdapter;


    private ListView listView;


    private String distributorid;
    private String travelTime = "";
    private String countryPath = "";
    private String day = "0";
    private String startPrice = "0";
    private String endPrice = "0";
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private GroupAllPresenter groupAllPresenter;

    private ListViewDataAdapter<ProvinceEntity> dayEntityListViewDataAdapter;
    private ListViewDataAdapter<ProvinceEntity> listViewDataAdapter;


    private PopupWindow popupwindowDate;
    private TextView tv_date;
    private TextView tv_week;
    private PopupWindow popupwindowCity;
    private PopupWindow popupwindowDay;
    private PopupWindow popupwindowDaoFu;


    private String group_date = "";

    private String start_price = "";
    private String end_price = "";

    Dialog startDialog;
    WheelView wheel_year_start, wheel_month_start, wheel_day_start;
    List<String> startYearList, startMonthList, startDayList;
    String startYear = "", startMonth = "", startDay = "";
    String[] months, days;
    YearAdapter startYearAdapter;
    MonthAdapter startMonthAdapter;
    DayAdapter startDayAdapter;
    TextView startDone, startCancle;
    final int MIN_YEAR = 1959;
    int max_Year = 2001;
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    int startYearIndex, startMonthIndex, startDayIndex;
    Resources res;
    Date date;


    private String str_date = "1";
    private String str_city = "1";
    private String str_day = "1";
    private String str_daofu = "1";
    private long startTime;

    private Dialog dialog_progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_group, container, false);
        startTime = System.currentTimeMillis();
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        initView(view);
        listView = pullToRefreshListView.getRefreshableView();

        EventBus.getDefault().register(this);

        initCreateView();

        initViewHolder();
        initData();


        groupAllPresenter = new GroupAllPresenter(this);

        showProgressDialog();
        String sign = TGmd5.getMD5(distributorid + travelTime + countryPath + day + startPrice + endPrice + pageindex);
        GroupAllBean groupAllBean = new GroupAllBean(distributorid, travelTime, countryPath, day, startPrice, endPrice, pageindex + "", sign);
        groupAllPresenter.getAllGroup(groupAllBean);

        String sign_one = TGmd5.getMD5(distributorid);
        groupAllPresenter.getHotCity(distributorid, sign_one);

        return view;
    }


    public void initView(View view) {
        rl_date = (RelativeLayout) view.findViewById(R.id.rl_date);
        rl_city = (RelativeLayout) view.findViewById(R.id.rl_city);
        rl_daofu = (RelativeLayout) view.findViewById(R.id.rl_daofu);
        rl_state = (RelativeLayout) view.findViewById(R.id.rl_state);
        tv_data = (TextView) view.findViewById(R.id.tv_date);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_day = (TextView) view.findViewById(R.id.tv_day);
        tv_daofu = (TextView) view.findViewById(R.id.tv_daofu);
        img_date = (ImageView) view.findViewById(R.id.img_date);
        img_city = (ImageView) view.findViewById(R.id.img_city);
        img_daofu = (ImageView) view.findViewById(R.id.img_daofu);
        img_state = (ImageView) view.findViewById(R.id.img_state);

        ll_visibilty = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);

        rl_date.setOnClickListener(this);
        rl_city.setOnClickListener(this);
        rl_daofu.setOnClickListener(this);
        rl_state.setOnClickListener(this);

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
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + travelTime + countryPath + day + startPrice + endPrice + pageindex);
                GroupAllBean groupAllBean = new GroupAllBean(distributorid, travelTime, countryPath, day, startPrice, endPrice, pageindex + "", sign);
                groupAllPresenter.getAllGroup(groupAllBean);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + travelTime + countryPath + day + startPrice + endPrice + pageindex);
                    GroupAllBean groupAllBean = new GroupAllBean(distributorid, travelTime, countryPath, day, startPrice, endPrice, pageindex + "", sign);
                    groupAllPresenter.getAllGroup(groupAllBean);

                } else {
                    MyToast.show(getActivity(), "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    public void initViewHolder() {
        groupIndexEntityListViewDataAdapter = new ListViewDataAdapter<GroupIndexEntity>();
        groupIndexEntityListViewDataAdapter.setViewHolderClass(this, GroupIndexViewHolder.class);
        listView.setAdapter(groupIndexEntityListViewDataAdapter);
        GroupIndexViewHolder.setOnClassifyPostionClickListener(this);

        dayEntityListViewDataAdapter = new ListViewDataAdapter<ProvinceEntity>();
        dayEntityListViewDataAdapter.setViewHolderClass(this, DaySelectViewHolder.class);
        DaySelectViewHolder.setOnClassifyPostionClickListener(this);


        listViewDataAdapter = new ListViewDataAdapter<ProvinceEntity>();
        listViewDataAdapter.setViewHolderClass(this, HotCityViewHolder.class);
        HotCityViewHolder.setClassifyEntityOnClassifyPostionClickListener(this);

    }


    public void initData() {
        ProvinceEntity provinceEntity1 = new ProvinceEntity("1", "", "不限", "");
        ProvinceEntity provinceEntity2 = new ProvinceEntity("2", "", "1天", "");
        ProvinceEntity provinceEntity3 = new ProvinceEntity("3", "", "2天", "");
        ProvinceEntity provinceEntity4 = new ProvinceEntity("4", "", "3天", "");
        ProvinceEntity provinceEntity5 = new ProvinceEntity("5", "", "4天", "");
        ProvinceEntity provinceEntity6 = new ProvinceEntity("6", "", "5天", "");
        ProvinceEntity provinceEntity7 = new ProvinceEntity("7", "", "6天", "");
        ProvinceEntity provinceEntity8 = new ProvinceEntity("8", "", "7天", "");
        dayEntityListViewDataAdapter.append(provinceEntity1);
        dayEntityListViewDataAdapter.append(provinceEntity2);
        dayEntityListViewDataAdapter.append(provinceEntity3);
        dayEntityListViewDataAdapter.append(provinceEntity4);
        dayEntityListViewDataAdapter.append(provinceEntity5);
        dayEntityListViewDataAdapter.append(provinceEntity6);
        dayEntityListViewDataAdapter.append(provinceEntity7);
        dayEntityListViewDataAdapter.append(provinceEntity8);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_date:
                if (str_date.equals("1")) {
                    str_date = "0";
                    tv_data.setTextColor(getResources().getColor(R.color.bg_button_green));
                    img_date.setBackgroundResource(R.mipmap.bg_main_group_pull_down);
                } else if (str_date.equals("0")) {
                    str_date = "1";
                    initSelect();
                }
                if (popupwindowDate != null && popupwindowDate.isShowing()) {
                    popupwindowDate.dismiss();
                    return;
                } else {
                    initmPopupWindowDate();
                    popupwindowDate.showAsDropDown(v, 0, 5);
                }
                if (popupwindowCity != null && popupwindowCity.isShowing()) {
                    popupwindowCity.dismiss();
                    return;
                }
                if (popupwindowDay != null && popupwindowDay.isShowing()) {
                    popupwindowDay.dismiss();
                    return;
                }
                if (popupwindowDaoFu != null && popupwindowDaoFu.isShowing()) {
                    popupwindowDaoFu.dismiss();
                    return;
                }

                break;
            case R.id.rl_city:
                if (str_city.equals("1")) {
                    str_city = "0";
                    tv_city.setTextColor(getResources().getColor(R.color.bg_button_green));
                    img_city.setBackgroundResource(R.mipmap.bg_main_group_pull_down);
                } else if (str_city.equals("0")) {
                    str_city = "1";
                    initSelect();
                }
                if (popupwindowCity != null && popupwindowCity.isShowing()) {
                    popupwindowCity.dismiss();
                    return;
                } else {
                    initmPopupWindowCity();
                    popupwindowCity.showAsDropDown(v, 0, 5);
                }

                if (popupwindowDate != null && popupwindowDate.isShowing()) {
                    popupwindowDate.dismiss();
                    return;
                }
                if (popupwindowDay != null && popupwindowDay.isShowing()) {
                    popupwindowDay.dismiss();
                    return;
                }
                if (popupwindowDaoFu != null && popupwindowDaoFu.isShowing()) {
                    popupwindowDaoFu.dismiss();
                    return;
                }

                break;
            case R.id.rl_daofu:// 导服
                if (str_daofu.equals("1")) {
                    str_daofu = "0";
                    tv_daofu.setTextColor(getResources().getColor(R.color.bg_button_green));
                    img_daofu.setBackgroundResource(R.mipmap.bg_main_group_pull_down);
                } else if (str_daofu.equals("0")) {
                    str_daofu = "1";
                    initSelect();
                }
                if (popupwindowDaoFu != null && popupwindowDaoFu.isShowing()) {
                    popupwindowDaoFu.dismiss();
                    return;
                } else {
                    initmPopupWindowDaoFu();
                    popupwindowDaoFu.showAsDropDown(v, 0, 5);
                }

                if (popupwindowCity != null && popupwindowCity.isShowing()) {
                    popupwindowCity.dismiss();
                    return;
                }
                if (popupwindowDate != null && popupwindowDate.isShowing()) {
                    popupwindowDate.dismiss();
                    return;
                }
                if (popupwindowDay != null && popupwindowDay.isShowing()) {
                    popupwindowDay.dismiss();
                    return;
                }

                break;
            case R.id.rl_state:// 天数
                if (str_day.equals("1")) {
                    str_day = "0";
                    tv_day.setTextColor(getResources().getColor(R.color.bg_button_green));
                    img_state.setBackgroundResource(R.mipmap.bg_main_group_pull_down);
                } else if (str_day.equals("0")) {
                    str_day = "1";
                    initSelect();
                }
                if (popupwindowDay != null && popupwindowDay.isShowing()) {
                    popupwindowDay.dismiss();
                    return;
                } else {
                    initmPopupWindowDay();
                    popupwindowDay.showAsDropDown(v, 0, 5);
                }

                if (popupwindowCity != null && popupwindowCity.isShowing()) {
                    popupwindowCity.dismiss();
                    return;
                }

                if (popupwindowDate != null && popupwindowDate.isShowing()) {
                    popupwindowDate.dismiss();
                    return;
                }

                if (popupwindowDaoFu != null && popupwindowDaoFu.isShowing()) {
                    popupwindowDaoFu.dismiss();
                    return;
                }
                break;
        }
    }


    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                pullToRefreshListView.onRefreshComplete();
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String state_ = jsonObject.getString("status");
                    if (state_.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        String datas = jsonArray.get(0).toString();
                        JSONObject jsonObject_ = new JSONObject(datas);
                        total_page = jsonObject_.getInt("TotalPages");
                        String items = jsonObject_.getString("Items");


                        if (mIsUp == false) {
                            groupIndexEntityListViewDataAdapter.removeAll();
                        } else {

                        }
                        JSONArray json_items = new JSONArray(items);
                        if (json_items != null && json_items.length() > 0) {
                            showOrGone();
                            ll_visibilty.setVisibility(View.VISIBLE);
                            for (int i = 0; i < json_items.length(); i++) {
                                JSONObject jsonObject_item = json_items.getJSONObject(i);
                                String id = jsonObject_item.getString("ID");
                                String title = jsonObject_item.getString("Title");
                                String TravelTime = jsonObject_item.getString("TravelTime");
                                String Day = jsonObject_item.getString("Day");
                                String city = jsonObject_item.getString("CountryPath");
                                String Destination = jsonObject_item.getString("Destination");
                                String Sex = jsonObject_item.getString("Sex");
                                String Price = jsonObject_item.getString("Price");
                                String DistributorID = jsonObject_item.getString("DistributorID");
                                String State = jsonObject_item.getString("State");
                                GroupIndexEntity groupIndexEntity = new GroupIndexEntity(id, title, State, TravelTime, Day, Destination, Sex, Price, DistributorID, distributorid, "", city);
                                groupIndexEntityListViewDataAdapter.append(groupIndexEntity);
                            }
                            if (pageindex == 1) {
                                listView.setSelection(0);
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
                break;
            case 2:
                try {
                    JSONObject jsonObject_one = new JSONObject(respanse);
                    String status = jsonObject_one.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject_one.getString("result");
                        JSONArray array_hot = new JSONArray(result);
                        String city_data = array_hot.get(0).toString();
                        ProvinceEntity provinceEntity_one = new ProvinceEntity("", "", "全部", "");
                        listViewDataAdapter.append(provinceEntity_one);
                        JSONArray jsonArray_city = new JSONArray(city_data);
                        if (jsonArray_city != null && jsonArray_city.length() > 0) {
                            for (int i = 0; i < jsonArray_city.length(); i++) {
                                JSONObject hot_ = jsonArray_city.getJSONObject(i);
                                String id = hot_.getString("ID");
                                String name = hot_.getString("CountryName");
                                String countryPath = hot_.getString("CountryPath");
                                ProvinceEntity provinceEntity = new ProvinceEntity(id, "", name, countryPath);
                                listViewDataAdapter.append(provinceEntity);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        MyToast.show(getActivity(), "请求失败");
    }

    @Override
    public void closeProgress() {
        closeProgressDialog();
    }

    @Override
    public void showProgress() {

    }


    public void showOrGone() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }


    /**
     * 筛选条件点击回调
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(ProvinceEntity itemData, int postion) {
        switch (postion) {
            case 1:
                str_city = "1";
                initSelect();
                if (popupwindowCity != null && popupwindowCity.isShowing()) {
                    popupwindowCity.dismiss();
                    popupwindowCity = null;
                }
                listViewDataAdapter.notifyDataSetChanged();
                mIsUp = false;
                pageindex = 1;
                countryPath = itemData.getDicpath();
                String sign = TGmd5.getMD5(distributorid + travelTime + countryPath + day + startPrice + endPrice + pageindex);
                GroupAllBean groupAllBean = new GroupAllBean(distributorid, travelTime, countryPath, day, startPrice, endPrice, pageindex + "", sign);
                groupAllPresenter.getAllGroup(groupAllBean);
                break;
            case 5:
                str_day = "1";
                initSelect();
                if (popupwindowDay != null && popupwindowDay.isShowing()) {
                    popupwindowDay.dismiss();
                    popupwindowDay = null;
                }
                dayEntityListViewDataAdapter.notifyDataSetChanged();
                mIsUp = false;
                pageindex = 1;
                if (itemData.getName().equals("不限")) {
                    day = "0";
                } else if (itemData.getName().equals("1天")) {
                    day = "1";
                } else if (itemData.getName().equals("2天")) {
                    day = "2";
                } else if (itemData.getName().equals("3天")) {
                    day = "3";
                } else if (itemData.getName().equals("4天")) {
                    day = "4";
                } else if (itemData.getName().equals("5天")) {
                    day = "5";
                } else if (itemData.getName().equals("6天")) {
                    day = "6";
                } else if (itemData.getName().equals("7天")) {
                    day = "7";
                }
                String sign_one = TGmd5.getMD5(distributorid + travelTime + countryPath + day + startPrice + endPrice + pageindex);
                GroupAllBean groupAllBean_one = new GroupAllBean(distributorid, travelTime, countryPath, day, startPrice, endPrice, pageindex + "", sign_one);
                groupAllPresenter.getAllGroup(groupAllBean_one);
                break;
        }
    }

    /**
     * item 点击事件
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(GroupIndexEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("seekid", itemData.getId());
        bundle.putString("groupType", "1");
        Intent intent = new Intent(getActivity(), PublishGroupDetialActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    /**
     * 选择日期弹框
     */
    public void initmPopupWindowDate() {
        // 获取自定义布局文件pop.xml的视图
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.dailog_all_choice_by_time, null, false);
        tv_date = (TextView) customView.findViewById(R.id.tv_date);
        tv_week = (TextView) customView.findViewById(R.id.tv_week);
        LinearLayout ll_view = (LinearLayout) customView.findViewById(R.id.ll_view);
        RelativeLayout rl_next = (RelativeLayout) customView.findViewById(R.id.rl_select_time);
        TextView tv_sure = (TextView) customView.findViewById(R.id.btn_sure);
        TextView tv_buyian = (TextView) customView.findViewById(R.id.btn_buxian);

        if (group_date != null && group_date.length() > 0) {
            travelTime = group_date;
            String week_one = new CalendarUtils().getWeek(group_date);
            tv_week.setText("周" + week_one.substring(week_one.length() - 1));
            tv_date.setText(group_date.split("-")[1] + "月" + group_date.split("-")[2] + "日");
        } else {
            String day_ = new CalendarUtils().getNowTime("yyyy-MM-dd");
            travelTime = day_;
            String week = new CalendarUtils().getWeek(day_);
            tv_week.setText("周" + week.substring(week.length() - 1));
            tv_date.setText(day_.split("-")[1] + "月" + day_.split("-")[2] + "日");
        }


        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindowDate = new PopupWindow(customView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupwindowDate.setFocusable(false);
        rl_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartDialog();
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupwindowDate != null && popupwindowDate.isShowing()) {
                    popupwindowDate.dismiss();
                    popupwindowDate = null;
                }
                mIsUp = false;
                pageindex = 1;
                group_date = travelTime;
                String sign = TGmd5.getMD5(distributorid + travelTime + countryPath + day + startPrice + endPrice + pageindex);
                GroupAllBean groupAllBean = new GroupAllBean(distributorid, travelTime, countryPath, day, startPrice, endPrice, pageindex + "", sign);
                groupAllPresenter.getAllGroup(groupAllBean);
            }
        });

        tv_buyian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupwindowDate != null && popupwindowDate.isShowing()) {
                    popupwindowDate.dismiss();
                    popupwindowDate = null;
                }
                mIsUp = false;
                pageindex = 1;
                group_date = "";
                travelTime = "";
                String sign = TGmd5.getMD5(distributorid + travelTime + countryPath + day + startPrice + endPrice + pageindex);
                GroupAllBean groupAllBean = new GroupAllBean(distributorid, travelTime, countryPath, day, startPrice, endPrice, pageindex + "", sign);
                groupAllPresenter.getAllGroup(groupAllBean);
            }
        });

        // 自定义view添加触摸事件
        ll_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindowDate != null && popupwindowDate.isShowing()) {
                    popupwindowDate.dismiss();
                    popupwindowDate = null;
                    travelTime = "";
                }
                return false;
            }
        });
        popupwindowDate.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                str_date = "1";
                tv_data.setTextColor(getResources().getColor(R.color.bg_balck_three));
                img_date.setBackgroundResource(R.mipmap.bg_main_group_pull_up);
            }
        });
    }

    /**
     * 选择城市弹框
     */
    public void initmPopupWindowCity() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.dialog_all_choice_by_city, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindowCity = new PopupWindow(customView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupwindowCity.setFocusable(false);
        GridView grid_hot_city = (GridView) customView.findViewById(R.id.grid_hot_city);
        grid_hot_city.setAdapter(listViewDataAdapter);
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindowCity != null && popupwindowCity.isShowing()) {
                    popupwindowCity.dismiss();
                    popupwindowCity = null;
                }
                return false;
            }
        });

        popupwindowCity.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                str_city = "1";
                tv_city.setTextColor(getResources().getColor(R.color.bg_balck_three));
                img_city.setBackgroundResource(R.mipmap.bg_main_group_pull_up);
            }
        });
    }


    /**
     * 选择天数弹框
     */
    public void initmPopupWindowDay() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.dialog_all_choice_by_day, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindowDay = new PopupWindow(customView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupwindowDay.setFocusable(false);
        ListView lv_select_day = (ListView) customView.findViewById(R.id.lv_select_day);
        lv_select_day.setAdapter(dayEntityListViewDataAdapter);
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindowDay != null && popupwindowDay.isShowing()) {
                    str_day = "1";
                    initSelect();
                    popupwindowDay.dismiss();
                    popupwindowDay = null;
                }
                return false;
            }
        });

        popupwindowDay.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                str_day = "1";
                tv_day.setTextColor(getResources().getColor(R.color.bg_balck_three));
                img_state.setBackgroundResource(R.mipmap.bg_main_group_pull_up);
            }
        });
    }

    /**
     * 选择导服弹框
     */
    public void initmPopupWindowDaoFu() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.dialog_all_choice_by_price, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindowDaoFu = new PopupWindow(customView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupwindowDaoFu.setOutsideTouchable(true);
        popupwindowDaoFu.setFocusable(true);

        final EditText et_start_price = (EditText) customView.findViewById(R.id.et_start_price);
        final EditText et_end_price = (EditText) customView.findViewById(R.id.et_end_price);
        final TextView tv_buyian = (TextView) customView.findViewById(R.id.btn_buxian);

        TextView btn_sure = (TextView) customView.findViewById(R.id.btn_sure);
        et_start_price.setText(start_price);
        et_end_price.setText(end_price);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(et_start_price.getText().toString())) {
                    MyToast.show(getActivity(), "请输入最低价格");
                    return;
                } else if (StringUtils.isEmpty(et_end_price.getText().toString())) {
                    MyToast.show(getActivity(), "请输入最高价格");
                    return;
                } else {
                    if (popupwindowDaoFu != null && popupwindowDaoFu.isShowing()) {
                        popupwindowDaoFu.dismiss();
                        popupwindowDaoFu = null;
                    }

                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
                    mIsUp = false;
                    pageindex = 1;
                    startPrice = et_start_price.getText().toString().trim();
                    endPrice = et_end_price.getText().toString().trim();
                    start_price = et_start_price.getText().toString().trim();
                    end_price = et_end_price.getText().toString().trim();

                    String sign = TGmd5.getMD5(distributorid + travelTime + countryPath + day + startPrice + endPrice + pageindex);
                    GroupAllBean groupAllBean = new GroupAllBean(distributorid, travelTime, countryPath, day, startPrice, endPrice, pageindex + "", sign);
                    groupAllPresenter.getAllGroup(groupAllBean);
                }
            }
        });

        tv_buyian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupwindowDaoFu != null && popupwindowDaoFu.isShowing()) {
                    popupwindowDaoFu.dismiss();
                    popupwindowDaoFu = null;
                }
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }

                mIsUp = false;
                pageindex = 1;
                startPrice = "0";
                endPrice = "0";
                start_price = "";
                end_price = "";
                String sign = TGmd5.getMD5(distributorid + travelTime + countryPath + day + startPrice + endPrice + pageindex);
                GroupAllBean groupAllBean = new GroupAllBean(distributorid, travelTime, countryPath, day, startPrice, endPrice, pageindex + "", sign);
                groupAllPresenter.getAllGroup(groupAllBean);
            }
        });
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindowDaoFu != null && popupwindowDaoFu.isShowing()) {
                    popupwindowDaoFu.dismiss();
                    popupwindowDaoFu = null;
                }
                return false;
            }
        });

        popupwindowDaoFu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                str_daofu = "1";
                tv_daofu.setTextColor(getResources().getColor(R.color.bg_balck_three));
                img_daofu.setBackgroundResource(R.mipmap.bg_main_group_pull_up);
            }
        });

    }


    public void initSelect() {
        tv_data.setTextColor(getResources().getColor(R.color.bg_balck_three));
        tv_city.setTextColor(getResources().getColor(R.color.bg_balck_three));
        tv_day.setTextColor(getResources().getColor(R.color.bg_balck_three));
        tv_daofu.setTextColor(getResources().getColor(R.color.bg_balck_three));

        img_date.setBackgroundResource(R.mipmap.bg_main_group_pull_up);
        img_city.setBackgroundResource(R.mipmap.bg_main_group_pull_up);
        img_state.setBackgroundResource(R.mipmap.bg_main_group_pull_up);
        img_daofu.setBackgroundResource(R.mipmap.bg_main_group_pull_up);

    }


    public void showProgressDialog() {
        dialog_progress = createLoadingDialog(getActivity(), "");
        dialog_progress.show();
    }

    public void closeProgressDialog() {
        if (dialog_progress != null && dialog_progress.isShowing()) {
            dialog_progress.dismiss();
        }
    }

    public void openStartDialog() {
        startDialog = new Dialog(getActivity(),
                R.style.style_custom_dialog);
        View startView = View.inflate(getActivity(),
                R.layout.wheel_view, null);
        wheel_year_start = (WheelView) startView.findViewById(R.id.year);
        wheel_month_start = (WheelView) startView.findViewById(R.id.month);
        wheel_day_start = (WheelView) startView.findViewById(R.id.day);
        startDone = (TextView) startView.findViewById(R.id.done);
        startCancle = (TextView) startView.findViewById(R.id.cancle);
        RelativeLayout rl_all = (RelativeLayout) startView.findViewById(R.id.rl_all);
        res = getResources();
        startDone.setTextColor(getResources().getColor(R.color.bg_button_green));
        startCancle.setTextColor(getResources().getColor(R.color.bg_button_green));
        months = res.getStringArray(R.array.months);
        days = res.getStringArray(R.array.days_31);
        date = new Date();
        String year = yearFormat.format(date);
        max_Year = Integer.parseInt(year);
        startYearList = new ArrayList<String>();
        for (int i = MIN_YEAR; i <= max_Year; i++) {
            startYearList.add(i + "");
        }
        startMonthList = Arrays.asList(months);
        startDayList = Arrays.asList(days);
        startYearAdapter = new YearAdapter(getActivity(), startYearList);
        startMonthAdapter = new MonthAdapter(getActivity(), startMonthList);
        startDayAdapter = new DayAdapter(getActivity(), startDayList);
        wheel_year_start.setViewAdapter(startYearAdapter);
        wheel_month_start.setViewAdapter(startMonthAdapter);
        wheel_day_start.setViewAdapter(startDayAdapter);


        wheel_year_start.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                startYearIndex = wheel.getCurrentItem();
                String year = (String) startYearAdapter.getItemText(startYearIndex);
                String month = (String) startMonthAdapter.getItemText(startMonthIndex);
                if (Integer.parseInt(month) == 2) {
                    if (isLeapYear(year)) {
                        //29 闰年2月29天
                        if (startDayAdapter.list.size() != 29) {
                            startDayList = Arrays.asList(res.getStringArray(R.array.days_29));
                            startDayAdapter = new DayAdapter(getActivity(), startDayList);
                            wheel_day_start.setViewAdapter(startDayAdapter);
                            if (startDayIndex > 28) {
                                wheel_day_start.setCurrentItem(0);
                                startDayIndex = 0;
                            } else {
                                wheel_day_start.setCurrentItem(startDayIndex);
                            }
                        }
                    } else {
                        //28 非闰年2月28天
                        if (startDayAdapter.list.size() != 28) {
                            startDayList = Arrays.asList(res.getStringArray(R.array.days_28));
                            startDayAdapter = new DayAdapter(getActivity(), startDayList);
                            wheel_day_start.setViewAdapter(startDayAdapter);
                            if (startDayIndex > 27) {
                                wheel_day_start.setCurrentItem(0);
                                startDayIndex = 0;
                            } else {
                                wheel_day_start.setCurrentItem(startDayIndex);
                            }
                        }
                    }
                }
            }
        });

        wheel_month_start.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                startMonthIndex = wheel.getCurrentItem();
                String year = (String) startYearAdapter.getItemText(startYearIndex);
                String month = (String) startMonthAdapter.getItemText(startMonthIndex);
                int i = Integer.parseInt(month);
                if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
                    //31
                    if (startDayAdapter.list.size() != 31) {
                        startDayList = Arrays.asList(res.getStringArray(R.array.days_31));
                        startDayAdapter = new DayAdapter(getActivity(), startDayList);
                        wheel_day_start.setViewAdapter(startDayAdapter);
                        wheel_day_start.setCurrentItem(startDayIndex);
                    }
                } else if (i == 2) {
                    if (isLeapYear(year)) {
                        //29 闂板勾2鏈?9澶?
                        if (startDayAdapter.list.size() != 29) {
                            startDayList = Arrays.asList(res.getStringArray(R.array.days_29));
                            startDayAdapter = new DayAdapter(getActivity(), startDayList);
                            wheel_day_start.setViewAdapter(startDayAdapter);
                            if (startDayIndex > 28) {
                                wheel_day_start.setCurrentItem(0);
                                startDayIndex = 0;
                            } else {
                                wheel_day_start.setCurrentItem(startDayIndex);
                            }
                        }
                    } else {
                        //28 闈為棸骞?鏈?8澶?
                        if (startDayAdapter.list.size() != 28) {
                            startDayList = Arrays.asList(res.getStringArray(R.array.days_28));
                            startDayAdapter = new DayAdapter(getActivity(), startDayList);
                            wheel_day_start.setViewAdapter(startDayAdapter);
                            if (startDayIndex > 27) {
                                wheel_day_start.setCurrentItem(0);
                                startDayIndex = 0;
                            } else {
                                wheel_day_start.setCurrentItem(startDayIndex);
                            }
                        }
                    }
                } else {
                    //30
                    if (startDayAdapter.list.size() != 30) {
                        startDayList = Arrays.asList(res.getStringArray(R.array.days_30));
                        startDayAdapter = new DayAdapter(getActivity(), startDayList);
                        wheel_day_start.setViewAdapter(startDayAdapter);
                        if (startDayIndex > 29) {
                            wheel_day_start.setCurrentItem(0);
                            startDayIndex = 0;
                        } else {
                            wheel_day_start.setCurrentItem(startDayIndex);
                        }
                    }
                }
            }
        });
        wheel_day_start.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                startDayIndex = wheel.getCurrentItem();
            }
        });
        startDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog.dismiss();
                startYear = (String) startYearAdapter.getItemText(startYearIndex);
                startMonth = (String) startMonthAdapter.getItemText(startMonthIndex);
                startDay = (String) startDayAdapter.getItemText(startDayIndex);
                travelTime = startYear + "-" + startMonth + "-" + startDay;
                String week = new CalendarUtils().getWeek(travelTime);
                tv_date.setText(startMonth + "月" + startDay + "日");
                tv_week.setText("周" + week.substring(week.length() - 1));
            }
        });
        startCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog.dismiss();
            }
        });
        startDialog.setContentView(startView);
        if ("".equals(startYear)) {
            startYear = max_Year + "";
            startMonth = monthFormat.format(date);
            startDay = dayFormat.format(date);
        }
        startYearIndex = startYearList.indexOf(startYear);
        startMonthIndex = startMonthList.indexOf(startMonth);
        startDayIndex = startDayList.indexOf(startDay);
        if (startYearIndex == -1) {
            startYearIndex = 0;
        }
        if (startMonthIndex == -1) {
            startMonthIndex = 0;
        }
        if (startDayIndex == -1) {
            startDayIndex = 0;
        }
        wheel_year_start.setCurrentItem(startYearIndex);
        wheel_month_start.setCurrentItem(startMonthIndex);
        wheel_day_start.setCurrentItem(startDayIndex);
        startDialog.show();
        WindowManager m2 = getActivity().getWindowManager();
        Display d2 = m2.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p2 = startDialog
                .getWindow().getAttributes(); // 获取对话框当前的参数值
        p2.width = (int) (d2.getWidth()); // 宽度设置为全屏
        startDialog.getWindow().setAttributes(p2); // 设置生效
    }

    /**
     * 判断是否是闰年
     */
    public static boolean isLeapYear(String str) {
        int year = Integer.parseInt(str);
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }


    private class YearAdapter extends AbstractWheelTextAdapter {
        /**
         * Constructor
         */
        public List<String> list;

        protected YearAdapter(Context context, List<String> list) {
            super(context, R.layout.wheel_view_layout, NO_RESOURCE);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);

            TextView textCity = (TextView) view.findViewById(R.id.textView);
            textCity.setText(list.get(index));
            return view;
        }

        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index);
        }
    }

    private class MonthAdapter extends AbstractWheelTextAdapter {
        /**
         * Constructor
         */
        public List<String> list;

        protected MonthAdapter(Context context, List<String> list) {
            super(context, R.layout.wheel_view_layout, NO_RESOURCE);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);

            TextView textCity = (TextView) view.findViewById(R.id.textView);
            textCity.setText(list.get(index));
            return view;
        }

        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index);
        }
    }

    private class DayAdapter extends AbstractWheelTextAdapter {
        /**
         * Constructor
         */
        public List<String> list;

        protected DayAdapter(Context context, List<String> list) {
            super(context, R.layout.wheel_view_layout, NO_RESOURCE);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);

            TextView textCity = (TextView) view.findViewById(R.id.textView);
            textCity.setText(list.get(index));
            return view;
        }

        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        groupAllPresenter.attach(this);
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        groupAllPresenter.dettach();
        EventBus.getDefault().unregister(this);

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
        if (event.getEventType() == EventType.DISSMISS_POPWINDOW_SEND) {
            if (popupwindowDate != null && popupwindowDate.isShowing()) {
                popupwindowDate.dismiss();
                str_date = "1";
                initSelect();
                return;
            }

            if (popupwindowCity != null && popupwindowCity.isShowing()) {
                popupwindowCity.dismiss();
                str_city = "1";
                initSelect();
                return;
            }
            if (popupwindowDay != null && popupwindowDay.isShowing()) {
                popupwindowDay.dismiss();
                str_day = "1";
                initSelect();
                return;
            }
            if (popupwindowDaoFu != null && popupwindowDaoFu.isShowing()) {
                popupwindowDaoFu.dismiss();
                str_daofu = "1";
                initSelect();
                return;
            }

        } else if (event.getEventType() == EventType.DISSMISS_POPWINDOW_CARRY) {
            if (popupwindowDate != null && popupwindowDate.isShowing()) {
                popupwindowDate.dismiss();
                str_date = "1";
                initSelect();
                return;
            }

            if (popupwindowCity != null && popupwindowCity.isShowing()) {
                popupwindowCity.dismiss();
                str_city = "1";
                initSelect();
                return;
            }
            if (popupwindowDay != null && popupwindowDay.isShowing()) {
                popupwindowDay.dismiss();
                str_day = "1";
                initSelect();
                return;
            }
            if (popupwindowDaoFu != null && popupwindowDaoFu.isShowing()) {
                popupwindowDaoFu.dismiss();
                str_daofu = "1";
                initSelect();
                return;
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == false) {
            startTime = System.currentTimeMillis();
        } else {
            long time = System.currentTimeMillis() - startTime;
            if (time >= 1000 * 60 * Constants.SECOND) {
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + travelTime + countryPath + day + startPrice + endPrice + pageindex);
                GroupAllBean groupAllBean = new GroupAllBean(distributorid, travelTime, countryPath, day, startPrice, endPrice, pageindex + "", sign);
                groupAllPresenter.getAllGroup(groupAllBean);
            }
            startTime = System.currentTimeMillis();
        }
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loding, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }

}
