package com.lvgou.distribution.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.AllTopicActivity;
import com.lvgou.distribution.activity.TopicDetailsActivity;
import com.lvgou.distribution.adapter.HomePageAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.bean.FengWenBean;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ProvinceEntity;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.HomePagePresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.FengCircleView;
import com.lvgou.distribution.viewholder.HotCityOneViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.eventbus.EventBus;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 * 发现
 */
public class FindFragment extends Fragment implements FengCircleView, View.OnClickListener, OnClassifyPostionClickListener<ProvinceEntity> {
    boolean mIsUp;// 是否上拉加载
    private PullToRefreshListView pull_refresh_list;
    public static ListView listView;
    private View empty_view;
    OnArticleSelectedListener mListener;
    private RelativeLayout layout_filter;
    private ImageView img_goods_up;
    HomePagePresenter imFmPersenter;
    private HomePageAdapter homePageAdapter;
    private String fincircle_sign;
    private String keyword = "";
    private String tagId = "";
    private int currPage = 1;
    private int dataPageCount = 0;
    private String findtagandtopic_sign;

    private PopupWindow popupwindowCity;
    private String cityOne = "0";

    private String prePageLastDataObjectId = "";

    private ListViewDataAdapter<ProvinceEntity> classifyEntityListViewDataAdapter;


    private boolean isflag = false;//判断是否显示空view

    private Dialog dialog_progress;

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("========================" + navigation_title.getTop());
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        startTime = System.currentTimeMillis();
        MobclickAgent.onPageEnd(getClass().getName());
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        if (stateReceiver != null) {
            getActivity().unregisterReceiver(stateReceiver);
        }

        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private FengCircleDynamicBean adapterDynamicBean;
    private StateReceiver stateReceiver;
    private IntentFilter intentFilter;

    public class StateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if ("com.distribution.tugou.state".equals(intent.getAction())) {
                    FengCircleDynamicBean circleDynamicBean = (FengCircleDynamicBean) intent.getSerializableExtra("itemData");
                    try {
                        int position = intent.getIntExtra("position", 0);
                        for (int i = 0; i < homePageAdapter.getCount(); i++) {
                            if (circleDynamicBean.getDistributorID() == homePageAdapter.getItem(i).getDistributorID()) {
                                homePageAdapter.getItem(i).setFollowed(circleDynamicBean.getFollowed());
                            }
                            if (circleDynamicBean.getID().equals(homePageAdapter.getItem(i).getID())) {
                                homePageAdapter.getItem(i).setCommentCount(circleDynamicBean.getCommentCount());
                                homePageAdapter.getItem(i).setZaned(circleDynamicBean.getZaned());
                                homePageAdapter.getItem(i).setZanCount(circleDynamicBean.getZanCount());
                            }
                        }
                    } catch (Exception e) {
                    }
                } else if ("com.distribution.tugou.del".equals(intent.getAction())) {
                    String fengwenId = intent.getStringExtra("fengwenId");
                    for (int i = 0; i < homePageAdapter.getCount(); i++) {
                        if (fengwenId.equals(homePageAdapter.getItem(i).getID())) {
                            homePageAdapter.getFengcircleData().remove(i);
                        }
                    }
                }
                homePageAdapter.notifyDataSetChanged();
            }
        }
    }

    private View activity_horizontalscrollview;
    LinearLayout hsview_layout;
    TextView txt_navigation_title;
    private long startTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        layout_filter = (RelativeLayout) view.findViewById(R.id.layout_filter);
        layout_filter.setVisibility(View.VISIBLE);
        startTime = System.currentTimeMillis();
        img_goods_up = (ImageView) view.findViewById(R.id.img_top_bottom);
        pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        listView = pull_refresh_list.getRefreshableView();
        empty_view = LayoutInflater.from(getActivity()).inflate(R.layout.none_data_layout, null);
        activity_horizontalscrollview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_horizontalscrollview, null);
        hsview_layout = (LinearLayout) activity_horizontalscrollview.findViewById(R.id.hsview_layout);
        txt_navigation_title = (TextView) activity_horizontalscrollview.findViewById(R.id.txt_navigation_title);
        listView.addHeaderView(activity_horizontalscrollview);
        navigation_title = LayoutInflater.from(getActivity()).inflate(R.layout.layout_navigation_title_one, null);
        TextView textView = (TextView) navigation_title.findViewById(R.id.txt_navigation_title);
        textView.setText("蜂圈动态");
        listView.addHeaderView(navigation_title);
        mListener = (OnArticleSelectedListener) getActivity();
        imFmPersenter = new HomePagePresenter(FindFragment.this);
        EventBus.getDefault().register(this);

        init();
        showProgressDialog();
        fincircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + keyword + tagId + prePageLastDataObjectId + currPage);
        imFmPersenter.getFindCircle(mListener.onArticleSelected(), keyword, tagId, prePageLastDataObjectId, currPage, fincircle_sign);
        findtagandtopic_sign = TGmd5.getMD5(mListener.onArticleSelected());
        imFmPersenter.findtagandtopic(mListener.onArticleSelected(), findtagandtopic_sign);
        layout_filter.setOnClickListener(this);
        //注册广播
        stateReceiver = new StateReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.distribution.tugou.state");
        intentFilter.addAction("com.distribution.tugou.del");
        getActivity().registerReceiver(stateReceiver, intentFilter);
        return view;
    }

    private void init() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currPage = 1;
                prePageLastDataObjectId = "";
                fincircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + keyword + tagId + prePageLastDataObjectId + currPage);
                imFmPersenter.getFindCircle(mListener.onArticleSelected(), keyword, tagId, prePageLastDataObjectId, currPage, fincircle_sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currPage++;
                if (currPage <= dataPageCount) {
                    fincircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + keyword + tagId + prePageLastDataObjectId + currPage);
                    imFmPersenter.getFindCircle(mListener.onArticleSelected(), keyword, tagId, prePageLastDataObjectId, currPage, fincircle_sign);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pull_refresh_list.onRefreshComplete();
                        }
                    }, 1000);
                }
            }
        });
        initViewHolder();
    }

    public void initViewHolder() {
        homePageAdapter = new HomePageAdapter(getActivity(), imFmPersenter);
        homePageAdapter.setFengcircleData(new ArrayList<FengCircleDynamicBean>());
        homePageAdapter.setmAdapterListener(adapterCallBack);
        listView.setAdapter(homePageAdapter);

        classifyEntityListViewDataAdapter = new ListViewDataAdapter<ProvinceEntity>();
        classifyEntityListViewDataAdapter.setViewHolderClass(this, HotCityOneViewHolder.class);
        HotCityOneViewHolder.setClassifyEntityOnClassifyPostionClickListener(this);

    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_filter:
                if (cityOne.equals("1")) {
                    img_goods_up.setBackgroundResource(R.mipmap.bg_profit_down);
                    cityOne = "2";
                } else {
                    img_goods_up.setBackgroundResource(R.mipmap.bg_profit_up);
                    cityOne = "1";
                }
                if (popupwindowCity != null && popupwindowCity.isShowing()) {
                    popupwindowCity.dismiss();
                    return;
                } else {
                    initmPopupWindowCity();
                    popupwindowCity.showAsDropDown(v, 0, 5);
                }
                break;
        }
    }


    /**
     * 选择城市弹框
     */
    public void initmPopupWindowCity() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.dialog_find_by_classify, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindowCity = new PopupWindow(customView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupwindowCity.setFocusable(false);
        GridView grid_hot_city = (GridView) customView.findViewById(R.id.grid_view);
        grid_hot_city.setAdapter(classifyEntityListViewDataAdapter);
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

            }
        });
    }


    @Override
    public void onClassifyPostionClick(ProvinceEntity itemData, int postion) {
        switch (postion) {
            case 1:
                if (popupwindowCity != null && popupwindowCity.isShowing()) {
                    popupwindowCity.dismiss();
                    popupwindowCity = null;
                }
                classifyEntityListViewDataAdapter.notifyDataSetChanged();
                currPage = 1;
                tagId = itemData.getId();
                prePageLastDataObjectId = "";
                fincircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + keyword + tagId + prePageLastDataObjectId + currPage);
                imFmPersenter.getFindCircle(mListener.onArticleSelected(), keyword, tagId, prePageLastDataObjectId, currPage, fincircle_sign);
                break;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteSuccessCallBack(String s) {

    }

    @Override
    public void excuteFailedCallBack(String s) {
        closeProgressDialog();
        MyToast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        pull_refresh_list.onRefreshComplete();
    }


    public interface OnArticleSelectedListener {
        public String onArticleSelected();
    }

    View navigation_title;

    @Override
    public void findcircleResponse(String s) {
        pull_refresh_list.onRefreshComplete();
        try {
            JSONObject jsonObject = new JSONObject(s);
            int status = jsonObject.getInt("status");
            closeProgressDialog();
            if (status == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                JSONArray jsonArray1 = jsonObject1.getJSONArray("Data");
                if (currPage == 1) {
                    dataPageCount = jsonObject1.getInt("DataPageCount");
                }
                if (isflag) {
                    listView.removeHeaderView(empty_view);
                }
                listView.addHeaderView(empty_view);
                isflag = true;
                List<FengCircleDynamicBean> circleDynamicBeans = new ArrayList<FengCircleDynamicBean>();
                if (currPage == 1) {
                    homePageAdapter.getFengcircleData().clear();
                }
                if (jsonArray1 != null && jsonArray1.length() > 0) {
                    isflag = false;
                    listView.removeHeaderView(empty_view);
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        FengCircleDynamicBean fengCircleDynamicBean = new FengCircleDynamicBean();
                        fengCircleDynamicBean.setID(((JSONObject) jsonArray1.get(i)).getString("ID"));
                        prePageLastDataObjectId = ((JSONObject) jsonArray1.get(i)).getString("ID");
                        fengCircleDynamicBean.setDistributorID(((JSONObject) jsonArray1.get(i)).getInt("DistributorID"));
                        fengCircleDynamicBean.setDistributorName(((JSONObject) jsonArray1.get(i)).getString("DistributorName"));
                        fengCircleDynamicBean.setUserType(((JSONObject) jsonArray1.get(i)).getInt("UserType"));
                        fengCircleDynamicBean.setIsRZ(((JSONObject) jsonArray1.get(i)).getInt("IsRZ"));
                        fengCircleDynamicBean.setCategoryIDs(((JSONObject) jsonArray1.get(i)).getString("CategoryIDs"));
                        JSONArray jsonCategory = ((JSONObject) jsonArray1.get(i)).getJSONArray("CategoryNames");
                        List<String> categoryNames = new ArrayList<>();
                        for (int j = 0; j < jsonCategory.length(); j++) {
                            categoryNames.add((String) jsonCategory.get(j));
                        }
                        fengCircleDynamicBean.setCategoryNames(categoryNames);
                        fengCircleDynamicBean.setTitle(((JSONObject) jsonArray1.get(i)).getString("Title"));
                        fengCircleDynamicBean.setContent(((JSONObject) jsonArray1.get(i)).getString("Content"));
                        fengCircleDynamicBean.setPicUrl(((JSONObject) jsonArray1.get(i)).getString("PicUrl"));
                        JSONArray piclists = ((JSONObject) jsonArray1.get(i)).getJSONArray("PicJson");
                        List<FengCircleImageBean> circleImageBeans = new ArrayList<>();
                        if (piclists != null) {
                            for (int j = 0; j < piclists.length(); j++) {
                                FengCircleImageBean circleImageBean = new FengCircleImageBean();
                                if (((String) piclists.get(j)).indexOf("{") != -1) {
                                    JSONObject jsonObject2 = new JSONObject((String) piclists.get(j));
                                    if(((String) piclists.get(j)).indexOf("smallPicUrl") != -1){
                                        circleImageBean.setSmallPicUrl(Url.ROOT + jsonObject2.getString("smallPicUrl"));
                                    }
                                    if(((String) piclists.get(j)).indexOf("picUrl") != -1){
                                        circleImageBean.setPicUrl(Url.ROOT + jsonObject2.getString("picUrl"));
                                    }
                                    circleImageBean.setHeight(jsonObject2.getInt("height"));
                                    circleImageBean.setWidth(jsonObject2.getInt("width"));
                                }
                                circleImageBeans.add(circleImageBean);
                            }
                        }
                        fengCircleDynamicBean.setmImgUrlList(circleImageBeans);
                        fengCircleDynamicBean.setZanCount(((JSONObject) jsonArray1.get(i)).getInt("ZanCount"));
                        fengCircleDynamicBean.setCommentCount(((JSONObject) jsonArray1.get(i)).getInt("CommentCount"));
                        fengCircleDynamicBean.setSourceDistributorID(((JSONObject) jsonArray1.get(i)).getInt("SourceDistributorID"));
                        fengCircleDynamicBean.setSourceDistributorName(((JSONObject) jsonArray1.get(i)).getString("SourceDistributorName"));
                        fengCircleDynamicBean.setSourceTitle(((JSONObject) jsonArray1.get(i)).getString("SourceTitle"));
                        fengCircleDynamicBean.setCreateTime(((JSONObject) jsonArray1.get(i)).getString("CreateTime"));
                        fengCircleDynamicBean.setFollowed(((JSONObject) jsonArray1.get(i)).getString("Followed"));
                        fengCircleDynamicBean.setZaned(((JSONObject) jsonArray1.get(i)).getInt("Zaned"));
                        fengCircleDynamicBean.setSex(((JSONObject) jsonArray1.get(i)).getString("Sex"));
                        fengCircleDynamicBean.setCurrentLocation(((JSONObject) jsonArray1.get(i)).getString("CurrentLocation"));
                        circleDynamicBeans.add(fengCircleDynamicBean);
                    }
                    homePageAdapter.setFengcircleData(circleDynamicBeans);

                }
                homePageAdapter.notifyDataSetChanged();
                if (currPage == 1) {
                    listView.setSelection(0);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void zanResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                List<FengCircleDynamicBean> list = homePageAdapter.getFengcircleData();
                list.get(position).setZaned(1);
                list.get(position).setZanCount(list.get(position).getZanCount() + 1);
                homePageAdapter.notifyDataSetChanged();
                sendBrodCastReciver();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void unzanResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                List<FengCircleDynamicBean> list = homePageAdapter.getFengcircleData();
                list.get(position).setZaned(0);
                list.get(position).setZanCount(list.get(position).getZanCount() - 1);
                homePageAdapter.notifyDataSetChanged();
                sendBrodCastReciver();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void followResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                List<FengCircleDynamicBean> list = homePageAdapter.getFengcircleData();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getDistributorID() == list.get(position).getDistributorID()) {
                        list.get(i).setFollowed(String.valueOf("1"));
                    }
                }
                homePageAdapter.notifyDataSetChanged();
                sendBrodCastReciver();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void unfollowResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                List<FengCircleDynamicBean> list = homePageAdapter.getFengcircleData();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getDistributorID() == list.get(position).getDistributorID()) {
                        list.get(i).setFollowed(String.valueOf(0));
                    }
                }
                homePageAdapter.notifyDataSetChanged();
                sendBrodCastReciver();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void findtagandtopicResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if (null != jsonArray && jsonArray.length() > 0) {
                    JSONArray jsonArray1 = (JSONArray) jsonArray.get(0);
                    ProvinceEntity filterBean_one = new ProvinceEntity("", "", "全部", "");
                    classifyEntityListViewDataAdapter.append(filterBean_one);
                    if (null != jsonArray1 && jsonArray1.length() > 0) {
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            ProvinceEntity filterBean = new ProvinceEntity();
                            filterBean.setId(((JSONObject) jsonArray1.get(i)).getString("ID"));
                            filterBean.setName(((JSONObject) jsonArray1.get(i)).getString("CagegoryName"));
                            filterBean.setDicpath(((JSONObject) jsonArray1.get(i)).getString("State"));
                            filterBean.setSon(((JSONObject) jsonArray1.get(i)).getString("OrderIndex"));
                            classifyEntityListViewDataAdapter.append(filterBean);
                        }
                    }
                    txt_navigation_title.setText("热门话题");
                    if (hsview_layout != null) {
                        hsview_layout.removeAllViews();
                    }
                    JSONArray jsonArray2 = (JSONArray) jsonArray.get(1);
                    if (null != jsonArray2 && jsonArray2.length() > 0) {
                        List<FengWenBean> fengWenBeanList = new ArrayList<>();
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            final FengWenBean fengWenBean = new FengWenBean();
                            fengWenBean.setOrderIndex(((JSONObject) jsonArray2.get(i)).getInt("OrderIndex"));
                            fengWenBean.setState(((JSONObject) jsonArray2.get(i)).getInt("State"));
                            fengWenBean.setID(((JSONObject) jsonArray2.get(i)).getString("ID"));
                            fengWenBean.setContent(((JSONObject) jsonArray2.get(i)).getString("Content"));
                            fengWenBean.setCreateTime(((JSONObject) jsonArray2.get(i)).getString("CreateTime"));
                            fengWenBean.setHits(((JSONObject) jsonArray2.get(i)).getInt("Hits"));
                            fengWenBean.setPicUrl(((JSONObject) jsonArray2.get(i)).getString("PicUrl"));
                            fengWenBean.setUserID(((JSONObject) jsonArray2.get(i)).getInt("UserID"));
                            fengWenBean.setTitle(((JSONObject) jsonArray2.get(i)).getString("Title"));
                            fengWenBean.setUserName(((JSONObject) jsonArray2.get(i)).getString("UserName"));
                            fengWenBeanList.add(fengWenBean);
                            View hsview_recom_course = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_fengwen, null);
                            ImageView img_recom_course = (ImageView) hsview_recom_course.findViewById(R.id.img_fengwen);
                            TextView txt_recom_course_title = (TextView) hsview_recom_course.findViewById(R.id.txt_fengwen_title);
                            txt_recom_course_title.setText("#  " + ((JSONObject) jsonArray2.get(i)).getString("Title"));
                            DisplayImageOptions options = new DisplayImageOptions.Builder()
                                    .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                                    .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                                    .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                                    .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                                    .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                                    .displayer(new RoundedBitmapDisplayer(8))    // 设置成圆角图片
                                    .build();
                            ImageLoader.getInstance().displayImage(Url.ROOT + ((JSONObject) jsonArray2.get(i)).getString("PicUrl"), img_recom_course, options);
                            hsview_recom_course.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), TopicDetailsActivity.class);
                                    intent.putExtra("topicId", fengWenBean.getID());
                                    startActivity(intent);
                                }
                            });
                            hsview_layout.addView(hsview_recom_course);
                        }
                        View layout_more = LayoutInflater.from(getActivity()).inflate(R.layout.layout_more, null);
                        ImageView img_more = (ImageView) layout_more.findViewById(R.id.img_topic_more);
//                      img_more.setBackgroundResource(R.mipmap.icon_topic_gengduo);
                        img_more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转更多话题
                                Intent intent = new Intent(getActivity(), AllTopicActivity.class);
                                startActivity(intent);
                            }
                        });
                        hsview_layout.addView(layout_more);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HomePageAdapter.AdapterCallBack adapterCallBack = new HomePageAdapter.AdapterCallBack() {
        @Override
        public void getItemData(FengCircleDynamicBean circleDynamicBean) {
            adapterDynamicBean = circleDynamicBean;
        }
    };

    private void sendBrodCastReciver() {
        Intent intent = new Intent();
        intent.setAction("com.distribution.tugou.state");
        intent.putExtra("itemData", adapterDynamicBean);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void followcircleResponse(String resonpse) {
    }

    @Override
    public void mayknowpersonResponse(String resonpse) {

    }

    @Override
    public void unreadcountResponse(String resonpse) {
        //不用
    }

    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.DIMISS_FIND_TANKUANG) {
            if (popupwindowCity != null && popupwindowCity.isShowing()) {
                popupwindowCity.dismiss();
                popupwindowCity = null;
            }
//            Constants.SELECTE_POSITION06 = "全部";
//            currPage = 1;
//            prePageLastDataObjectId = "";
//            fincircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + keyword + tagId + prePageLastDataObjectId + currPage);
//            imFmPersenter.getFindCircle(mListener.onArticleSelected(), keyword, tagId, prePageLastDataObjectId, currPage, fincircle_sign);
        } else if (event.getEventType() == EventType.UPDATE_FENGWEN) {
            currPage = 1;
            prePageLastDataObjectId = "";
            fincircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + keyword + tagId + prePageLastDataObjectId + currPage);
            imFmPersenter.getFindCircle(mListener.onArticleSelected(), keyword, tagId, prePageLastDataObjectId, currPage, fincircle_sign);
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
                currPage = 1;
                showProgressDialog();
                fincircle_sign = TGmd5.getMD5(mListener.onArticleSelected() + keyword + tagId + prePageLastDataObjectId + currPage);
                imFmPersenter.getFindCircle(mListener.onArticleSelected(), keyword, tagId, prePageLastDataObjectId, currPage, fincircle_sign);
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
