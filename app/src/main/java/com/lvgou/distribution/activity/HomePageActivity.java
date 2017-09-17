package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.HomePageAdapter;
import com.lvgou.distribution.bean.BannerBean;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.bean.MyZanListBean;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.jpush.ExampleUtil;
import com.lvgou.distribution.presenter.HomePagePresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.AdViewpagerUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.HomePageView;
import com.lvgou.distribution.view.PopupMenu;
import com.lvgou.distribution.widget.LamaAdViewPage;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2016/10/13.
 * 首页
 */
public class HomePageActivity extends BaseActivity implements HomePageView {
    private LamaAdViewPage adPage;
    @ViewInject(R.id.tv_ring_num)
    private TextView tv_ring_num;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pull_refresh_list;
    public ListView listView;
    private HomePageAdapter homePageAdapter;
    private String distributorid;
    private String shiming_state;
    private String keyword = "";
    private String tagId = "";
    private int currPage = 1;
    HomePagePresenter imFmPersenter;
    private String index_sign = "";
    private String fincircle_sign = "";
    private PopupMenu popupMenu;
    private int unmessagenum = 0;
    private int getNewestDistributorId = 1;
    private String getnews_sign;
    private String commentNum = "";
    private String dianzanNum = "";

    private String shop_name = "";

    DisplayImageOptions options;
    private String state = "";
    private String user_type = "";
    private String LoginCount = "";
    private Dialog dialog_stop, dialog_report, dialog_report_one;
    private String lianxu_num = "";
    private String lianxujiangli = "";
    private String prePageLastDataObjectId = "";
    private String guide_picurl = "";
    private static final int MSG_SET_ALIAS = 1001;

    private long startTime;

    // 调用jpush接口设置别名
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), distributorid, null, mAliasCallback);
                    break;
                default:
            }
        }
    };


    // 设置jpush别名设置回调，并获取返回值
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";// 别名设置成功
                    LogUtils.e("别名设置回调结果" + String.valueOf(code) + "=====alias=  " + alias);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    if (ExampleUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                    }
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
        }
    };


    private FengCircleDynamicBean adapterDynamicBean;
    private StateReceiver stateReceiver;
    private IntentFilter intentFilter;

    public class StateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if ("com.distribution.tugou.state".equals(intent.getAction())) {
                    FengCircleDynamicBean circleDynamicBean = (FengCircleDynamicBean) intent.getSerializableExtra("itemData");
                    int position = intent.getIntExtra("position", 0);
                    try {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ViewUtils.inject(this);
        adPage = null;
        EventBus.getDefault().register(this);
        startTime = System.currentTimeMillis();
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        shiming_state = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
        listView = pull_refresh_list.getRefreshableView();

        imFmPersenter = new HomePagePresenter(HomePageActivity.this);
        init();

        showLoadingProgressDialog(HomePageActivity.this, "");

        index_sign = TGmd5.getMD5(distributorid);
        imFmPersenter.getIndex(distributorid, index_sign);

        //签到
       /* SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String is_today = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.IS_CURENT_TIME, "");
        //每天记录一次，第一次登录时间
        if (!is_today.equals(df.format(new Date()))) {
            String sign = TGmd5.getMD5(distributorid);
            doSign(distributorid, sign);
        }*/

        //注册广播
        stateReceiver = new StateReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.distribution.tugou.state");
        intentFilter.addAction("com.distribution.tugou.del");
        registerReceiver(stateReceiver, intentFilter);
    }

    //蜂圈动态
    private void init() {
        popupMenu = new PopupMenu(this);
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currPage = 1;
                index_sign = TGmd5.getMD5(distributorid);
                imFmPersenter.getIndex(distributorid, index_sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
        initViewHolder();
        header = LayoutInflater.from(this).inflate(R.layout.activity_banner_header, null);
        viewpager = (ViewPager) header.findViewById(R.id.viewpager);
        lydots = (LinearLayout) header.findViewById(R.id.ly_dots);
        RelativeLayout rl_toutiao = (RelativeLayout) header.findViewById(R.id.rl_toutiao);
        View view_line_botton = header.findViewById(R.id.view_line_botton);
        rl_toutiao.setVisibility(View.GONE);
        view_line_botton.setVisibility(View.GONE);
        listView.addHeaderView(header);
    }

    public void initViewHolder() {
        homePageAdapter = new HomePageAdapter(HomePageActivity.this, imFmPersenter);
        homePageAdapter.setFengcircleData(new ArrayList<FengCircleDynamicBean>());
        homePageAdapter.setmAdapterListener(adapterCallBack);
        listView.setAdapter(homePageAdapter);
    }

    @OnClick({R.id.img_custom_service, R.id.rl_notice})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.img_custom_service:
                popupMenu.showLocation(R.id.view_007);// 设置弹出菜单弹出的位置
                popupMenu.setOnItemClickListener(new PopupMenu.OnItemClickListener() {
                    @Override
                    public void onClick(PopupMenu.MENUITEM item, String str) {
                        switch (str) {
                            case "1":
                                Intent intent = new MQIntentBuilder(HomePageActivity.this).build();
                                startActivity(intent);
                                break;
                            case "2":
                                FunctionUtils.jump2PhoneView(HomePageActivity.this, "4008017579");
                                break;
                        }
                    }
                });
                break;
            case R.id.rl_notice:
                openActivity(MyMessageActivity.class);
                break;
        }
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
        closeLoadingProgressDialog();
        MyToast.makeText(this, s, Toast.LENGTH_LONG).show();
        if (s.equals("请登录")) {
            openActivity(LoginActivity.class);
            finish();
        }
    }

    View navigation_view;
    View horizontalscrollview;
    View navigation_title;
    LinearLayout hsview_layout;


    private View header;
    private ViewPager viewpager;
    private LinearLayout lydots;
    private ArrayList<String> urls;
    private ArrayList<String> pathUrl;
    private AdViewpagerUtil adViewpagerUtil;


    @Override
    public void indexresponse(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                currPage = 1;
                fincircle_sign = TGmd5.getMD5(distributorid + keyword + tagId + prePageLastDataObjectId + currPage);
                imFmPersenter.getFindCircle(distributorid, keyword, tagId, prePageLastDataObjectId, currPage, fincircle_sign);

                JSONArray jsonArray = jsonObject.getJSONArray("result");
//                unmessagenum = (int) jsonArray.get(0);
//                if (unmessagenum>0) {
//                    tv_ring_num.setVisibility(View.VISIBLE);
//                    tv_ring_num.setText(String.valueOf(unmessagenum));
//                } else {
//                    tv_ring_num.setVisibility(View.GONE);
//                }
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GROUP_MESSAGE_NUM, unmessagenum + "");
                /******************个人信息数据****************/
                String personal_info = jsonArray.get(1).toString();
                JSONObject info = new JSONObject(personal_info);
                shop_name = info.getString("CompanyName");
                String tuanbi = info.getString("TuanBi");
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi);
                state = info.getString("State");
                guide_picurl = info.getString("PicUrl");
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL, guide_picurl);
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, state);
                String ParentID = info.getString("ParentID");
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, ParentID);
                String Ratio = info.getString("Ratio");
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.RATIO, Ratio);
                user_type = info.getString("UserType");
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, user_type);
                LoginCount = info.getString("LoginCount");
                String login_name = info.getString("LoginName");
                String real_name = info.getString("RealName");
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ACCOUNT, login_name);
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME, real_name);
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGINCOUNT, LoginCount);

                if (state.equals("7")) {
                    showStop();
                }

                String sex = jsonArray.get(4).toString() + "";
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SEX, sex);

                //首页banner
                urls = new ArrayList<>();
                pathUrl = new ArrayList<>();
               /* final List<BannerBean> beanList = new ArrayList<BannerBean>();*/
                List<BannerBean> recomCourseList = new ArrayList<BannerBean>();
                JSONArray jsonArray_banner = jsonArray.getJSONArray(2);
                if (jsonArray_banner != null && jsonArray_banner.length() > 0) {
                    for (int i = 0; i < jsonArray_banner.length(); i++) {
                        urls.add(Url.ROOT + ((JSONObject) jsonArray_banner.get(i)).getString("PicUrl"));
                        pathUrl.add(((JSONObject) jsonArray_banner.get(i)).getString("LinkUrl"));
                        /*BannerBean bannerBean = new BannerBean();
                        bannerBean.setID(((JSONObject) jsonArray_banner.get(i)).getInt("ID"));
                        bannerBean.setPicUrl(Url.ROOT + ((JSONObject) jsonArray_banner.get(i)).getString("PicUrl"));
                        bannerBean.setDicPath(((JSONObject) jsonArray_banner.get(i)).getString("DicPath"));
                        bannerBean.setLinkUrl(((JSONObject) jsonArray_banner.get(i)).getString("LinkUrl"));
                        bannerBean.setState(((JSONObject) jsonArray_banner.get(i)).getInt("State"));
                        bannerBean.setPrice(((JSONObject) jsonArray_banner.get(i)).getInt("Price"));
                        bannerBean.setOther(((JSONObject) jsonArray_banner.get(i)).getInt("Other"));
                        bannerBean.setCheap(((JSONObject) jsonArray_banner.get(i)).getInt("Cheap"));
                        bannerBean.setTitle(((JSONObject) jsonArray_banner.get(i)).getString("Title"));
                        bannerBean.setIntro(((JSONObject) jsonArray_banner.get(i)).getString("Intro"));
                        bannerBean.setAddUserID(((JSONObject) jsonArray_banner.get(i)).getInt("AddUserID"));
                        bannerBean.setAddRealName(((JSONObject) jsonArray_banner.get(i)).getString("AddRealName"));
                        bannerBean.setOrderIndex(((JSONObject) jsonArray_banner.get(i)).getInt("OrderIndex"));
                        bannerBean.setCreateTime(((JSONObject) jsonArray_banner.get(i)).getString("CreateTime"));
                        bannerBean.setModifiedTime(((JSONObject) jsonArray_banner.get(i)).getString("ModifiedTime"));
                        beanList.add(bannerBean);*/
                    }
                    /*if (adPage == null) {
                        adPage = new LamaAdViewPage(this);
                        adPage.setActivity(this);
                        // 第一次设置集合
                        adPage.setViewData(beanList);
                        adPage.startCarousel();
                        adPage.setBannerItemListener(itemCallback);
                        listView.addHeaderView(adPage);
                    } else {
                        adPage.setViewData(beanList);
                        adPage.startCarousel();
                    }*/


                    //------
                    if (adViewpagerUtil == null) {
                        adViewpagerUtil = new AdViewpagerUtil(this, viewpager, lydots, 8, urls.size(), urls);
                        adViewpagerUtil.initVps();
                    }



                    /*if (adViewpagerUtil==null){
                        adViewpagerUtil = new AdViewpagerUtil(this, viewpager, lydots, 8, 4, urls);
                        adViewpagerUtil.initVps();

                    }*/
                    adViewpagerUtil.setOnAdItemClickListener(new AdViewpagerUtil.OnAdItemClickListener() {
                        @Override
                        public void onItemClick(View v, int flag) {
                            turnView(pathUrl.get(flag));
                            Log.e("askdfhka", "-----------" + pathUrl.get(flag));
                        }
                    });


                    //----
                    if (navigation_view == null) {
                        navigation_view = LayoutInflater.from(this).inflate(R.layout.activity_navigation, null);
                        RelativeLayout exchange_layout = (RelativeLayout) navigation_view.findViewById(R.id.exchange_layout);
                        //兑换
                        exchange_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                state = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
                                Bundle bundle = new Bundle();
                                bundle.putInt("tabIndex", 2);
                                openActivity(TuanbiMangerActivity.class, bundle);
                            }
                        });
                        RelativeLayout subscribe_layout = (RelativeLayout) navigation_view.findViewById(R.id.subscribe_layout);
                        subscribe_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shiming_state = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
                                switch (shiming_state) {
                                    case "5":
                                        openActivity(ReportActivity.class);
                                        break;
                                    case "1":
                                        showHintDialog("导游信息审核通过并实名认证后才可使用");
                                        break;
                                    case "3":
                                        showHintDialog("审核失败请重新提交导游证信息");
                                        break;
                                    case "2":
                                    case "4":
                                    case "6":
                                        showHintSaveDialog();
                                        break;

                                }
                            }
                        });
                        RelativeLayout gain_layout = (RelativeLayout) navigation_view.findViewById(R.id.gain_layout);
                        gain_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                if (state.equals("1") || state.equals("3")) {
                                    /*if (!"".equals(guide_picurl) && guide_picurl != null && guide_picurl.length() > 0) {
                                        if (state.equals("1")) {
                                            showHintDialog(getResources().getString(R.string.guide_certificate_audit));
                                        } else {
                                            showHintDialog(getResources().getString(R.string.guide_certificate_failure_audit));
                                        }
                                    } else {
                                        showGuideDialog(state);
                                    }*/
                                String guide_picurl = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL);
                                String state = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
                                if (state.equals("1")) {
                                    if (guide_picurl.equals("") || guide_picurl == null && guide_picurl.length() == 0) {
                                        showGuideDialog(state, "请上传导游证！");
                                    } else {
                                        showHintDialog(getResources().getString(R.string.guide_certificate_audit));
                                    }
                                } else if (state.equals("3")) {
                                    showGuideDialog(state, "还没有上传导游证，是否去上传？");
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("index", "0");
                                    openActivity(ApplicationActivity.class, bundle);
                                }
                            /*    Bundle bundle = new Bundle();
                                bundle.putString("index", "0");
                                openActivity(ApplicationActivity.class, bundle);*/


                            }
                        });
                        RelativeLayout guide_layout = (RelativeLayout) navigation_view.findViewById(R.id.guide_layout);
                        guide_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openActivity(GroupIndexActivity.class);
                            }
                        });
                        RelativeLayout free_layout = (RelativeLayout) navigation_view.findViewById(R.id.free_layout);
                        free_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle pBundle = new Bundle();
                                pBundle.putString("index", "0");
                                pBundle.putString("state", "0");
                                pBundle.putString("name", "");
                                pBundle.putString("content", "");
                                pBundle.putString("id", "");
                                openActivity(NewFreeSmsActivity.class, pBundle);
                            }
                        });
                        listView.addHeaderView(navigation_view);
                    }
                }

                //推荐课程
                JSONArray jsonArray_recom_course = jsonArray.getJSONArray(3);
                if (jsonArray_recom_course != null && jsonArray_recom_course.length() > 0) {
                    if (horizontalscrollview == null) {
                        horizontalscrollview = LayoutInflater.from(this).inflate(R.layout.activity_horizontalscrollview, null);
                        RelativeLayout rl_study = (RelativeLayout) horizontalscrollview.findViewById(R.id.rl_study);
                        TextView txt_more_course = (TextView) horizontalscrollview.findViewById(R.id.txt_more_course);
                        txt_more_course.setVisibility(View.VISIBLE);
                        rl_study.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString("selection_postion", "1");
                                openActivity(HomeActivity.class, bundle);
                            }
                        });
                        hsview_layout = (LinearLayout) horizontalscrollview.findViewById(R.id.hsview_layout);
                        listView.addHeaderView(horizontalscrollview);
                    }
                    hsview_layout.removeAllViews();
                    for (int i = 0; i < jsonArray_recom_course.length(); i++) {
                        final BannerBean bannerBean = new BannerBean();
                        bannerBean.setID(((JSONObject) jsonArray_recom_course.get(i)).getInt("ID"));
                        bannerBean.setPicUrl(((JSONObject) jsonArray_recom_course.get(i)).getString("PicUrl"));
                        bannerBean.setDicPath(((JSONObject) jsonArray_recom_course.get(i)).getString("DicPath"));
                        bannerBean.setLinkUrl(((JSONObject) jsonArray_recom_course.get(i)).getString("LinkUrl"));
                        bannerBean.setState(((JSONObject) jsonArray_recom_course.get(i)).getInt("State"));
                        bannerBean.setPrice(((JSONObject) jsonArray_recom_course.get(i)).getInt("Price"));
                        bannerBean.setOther(((JSONObject) jsonArray_recom_course.get(i)).getInt("Other"));
                        bannerBean.setCheap(((JSONObject) jsonArray_recom_course.get(i)).getInt("Cheap"));
                        bannerBean.setTitle(((JSONObject) jsonArray_recom_course.get(i)).getString("Title"));
                        bannerBean.setIntro(((JSONObject) jsonArray_recom_course.get(i)).getString("Intro"));
                        bannerBean.setAddUserID(((JSONObject) jsonArray_recom_course.get(i)).getInt("AddUserID"));
                        bannerBean.setAddRealName(((JSONObject) jsonArray_recom_course.get(i)).getString("AddRealName"));
                        bannerBean.setOrderIndex(((JSONObject) jsonArray_recom_course.get(i)).getInt("OrderIndex"));
                        bannerBean.setCreateTime(((JSONObject) jsonArray_recom_course.get(i)).getString("CreateTime"));
                        bannerBean.setModifiedTime(((JSONObject) jsonArray_recom_course.get(i)).getString("ModifiedTime"));

                        recomCourseList.add(bannerBean);
                        View hsview_recom_course = LayoutInflater.from(this).inflate(R.layout.hsview_recom_course, null);
                        ImageView img_recom_course = (ImageView) hsview_recom_course.findViewById(R.id.img_recom_course);
                        ImageView img_applying = (ImageView) hsview_recom_course.findViewById(R.id.img_applying);

                        if (bannerBean.getOther() != 1) {
                            img_applying.setVisibility(View.GONE);
                        } else {
                            img_applying.setVisibility(View.VISIBLE);
                        }
                        TextView txt_recom_course_title = (TextView) hsview_recom_course.findViewById(R.id.txt_recom_course_title);
                        txt_recom_course_title.setText(((JSONObject) jsonArray_recom_course.get(i)).getString("Title"));
                        options = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                                .displayer(new RoundedBitmapDisplayer(10))    // 设置成圆角图片
                                .build();
                        ImageLoader.getInstance().displayImage(Url.ROOT + ((JSONObject) jsonArray_recom_course.get(i)).getString("PicUrl"), img_recom_course, options);
                        hsview_recom_course.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString("id", bannerBean.getLinkUrl() + "");
                                bundle.putString("index", "0");
                                openActivity(FamousTeacherDetialActivity.class, bundle);
                            }
                        });
                        hsview_layout.addView(hsview_recom_course);
                    }
                    if (navigation_title == null) {
                        navigation_title = LayoutInflater.from(this).inflate(R.layout.layout_navigation_title_one, null);
                        TextView textView = (TextView) navigation_title.findViewById(R.id.txt_navigation_title);
                        textView.setText("蜂圈动态");
                        listView.addHeaderView(navigation_title);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    Dialog dialog_signature;

    public void showHintSaveDialog() {
//    dialog_report_turn
        dialog_signature = new Dialog(this, R.style.Mydialog);
        dialog_signature.setCanceledOnTouchOutside(false);
        View view1 = View.inflate(this,
                R.layout.dialog_signature_view, null);
        TextView txt_msg_title = (TextView) view1.findViewById(R.id.txt_msg_title);
        txt_msg_title.setText("请点击确定进行实名认证");
        Button btn_cancel = (Button) view1.findViewById(R.id.btn_cancel);
        btn_cancel.setText("取消");
        Button btn_save_sign = (Button) view1.findViewById(R.id.btn_save_sign);
        btn_save_sign.setText("确定");
        btn_save_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_signature.dismiss();
                Intent intent = new Intent(HomePageActivity.this, AuthenticationActivity.class);
                startActivity(intent);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_signature.dismiss();
            }
        });
        dialog_signature.setContentView(view1);
        dialog_signature.show();
    }

    //banner跳转
    public LamaAdViewPage.BannerItemCallback itemCallback = new LamaAdViewPage.BannerItemCallback() {
        @Override
        public void ItemClick(String url) {
            LogUtils.e(url);
            turnView(url);
        }
    };
    LinearLayout linearLayout_more;

    @Override
    public void findcircleResponse(String s) {
        pull_refresh_list.onRefreshComplete();
        try {
            JSONObject jsonObject = new JSONObject(s);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {

                getnews_sign = TGmd5.getMD5(distributorid + getNewestDistributorId);
                imFmPersenter.unreadcount(distributorid, getNewestDistributorId, getnews_sign);
                if (currPage == 1) {
                    homePageAdapter.getFengcircleData().clear();
                }
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                JSONArray jsonArray1 = jsonObject1.getJSONArray("Data");
//                Gson gson = new Gson();
//                List<FengCircleDynamicBean> circleDynamicBeans = gson.fromJson(jsonArray1.toString(), new TypeToken<List<FengCircleDynamicBean>>() {
//                }.getType());
//                if(circleDynamicBeans!=null&&circleDynamicBeans.size()>0){
                List<FengCircleDynamicBean> circleDynamicBeans = new ArrayList<FengCircleDynamicBean>();
                if (jsonArray1 != null && jsonArray1.length() > 0) {
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        FengCircleDynamicBean fengCircleDynamicBean = new FengCircleDynamicBean();
                        fengCircleDynamicBean.setID(((JSONObject) jsonArray1.get(i)).getString("ID"));
                        fengCircleDynamicBean.setDistributorID(((JSONObject) jsonArray1.get(i)).getInt("DistributorID"));
                        fengCircleDynamicBean.setDistributorName(((JSONObject) jsonArray1.get(i)).getString("DistributorName"));
                        fengCircleDynamicBean.setUserType(((JSONObject) jsonArray1.get(i)).getInt("UserType"));
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
                                    if (((String) piclists.get(j)).indexOf("smallPicUrl") != -1) {
                                        circleImageBean.setSmallPicUrl(Url.ROOT + jsonObject2.getString("smallPicUrl"));
                                    }
                                    if (((String) piclists.get(j)).indexOf("picUrl") != -1) {
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
                jsonObject1.getInt("DataCount");
                jsonObject1.getInt("DataPageCount");
            }

            if (linearLayout_more == null) {
                linearLayout_more = new LinearLayout(HomePageActivity.this);
                TextView textView = new TextView(HomePageActivity.this);
                Drawable drawable = getResources().getDrawable(R.mipmap.icon_home_bottom_more);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(null, null, drawable, null);
                textView.setCompoundDrawablePadding(10);
                textView.setText("更多");
                textView.setTextColor(getResources().getColor(R.color.text_color_d5aa5f));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.fourteen));
                linearLayout_more.setLayoutParams(new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 120));
                linearLayout_more.setGravity(Gravity.CENTER);
                linearLayout_more.addView(textView);
                linearLayout_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //导游证认证判断
                       /* if (state.equals("1") || state.equals("3")) {
                            if (!"".equals(guide_picurl) && guide_picurl != null && guide_picurl.length() > 0) {
                                if (state.equals("1")) {
                                    showHintDialog(getResources().getString(R.string.guide_certificate_audit));
                                } else {
                                    showHintDialog(getResources().getString(R.string.guide_certificate_failure_audit));
                                }
                            } else {
                                showGuideDialog(state);
                            }
                        }*/
                        String guide_picurl = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL);
                        String state = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
                        if (state.equals("1")) {
                            if (guide_picurl.equals("") || guide_picurl == null && guide_picurl.length() == 0) {
                                showGuideDialog(state, "请上传导游证！");
                            } else {
                                showHintDialog(getResources().getString(R.string.guide_certificate_audit));
                            }
                        } else if (state.equals("3")) {
                            showGuideDialog(state, "还没有上传导游证，是否去上传？");
                        } else {
                            Intent intent = new Intent(HomePageActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("selection_postion", "3");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
                listView.addFooterView(linearLayout_more);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unreadcount(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            String status = jsonObject.getString("status");
            closeLoadingProgressDialog();
            if (status.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                int uncomment = (int) jsonArray.get(0);
                int unlike = (int) jsonArray.get(1);
                commentNum = jsonArray.get(0).toString();
                dianzanNum = jsonArray.get(1).toString();
                int classNum = (int) jsonArray.get(3);
                int systemNum = (int) jsonArray.get(4);
                int fengyouNum = (int) jsonArray.get(5);
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CENTER_NUMBER, String.valueOf(classNum + systemNum + fengyouNum));
                if (classNum + systemNum + fengyouNum > 0) {
                    //我的显示未读角标
                    EventFactory.updateHomeCenter("0");
                }
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, commentNum);
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ZAN_NUM, dianzanNum);

                // 公告角标
                int gonggao_num = systemNum + fengyouNum;
                PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.MESSAGE_NUM, String.valueOf(gonggao_num));
                if (gonggao_num > 0) {
                    tv_ring_num.setVisibility(View.VISIBLE);
                    tv_ring_num.setText(String.valueOf(gonggao_num));
                } else {
                    tv_ring_num.setVisibility(View.GONE);
                }
                /**
                 * 更新蜂圈的角标
                 */
                EventFactory.updateHomeCircle("0");
            }
        } catch (Exception e) {

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
                        list.get(i).setFollowed(String.valueOf(1));
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
    protected void onResume() {
        super.onResume();
        getnews_sign = TGmd5.getMD5(distributorid + getNewestDistributorId);
        imFmPersenter.unreadcount(distributorid, getNewestDistributorId, getnews_sign);
        long time = System.currentTimeMillis() - startTime;
        if (time >= 1000 * 60 * Constants.SECOND) {
            showLoadingProgressDialog(this, "");
            index_sign = TGmd5.getMD5(distributorid);
            imFmPersenter.getIndex(distributorid, index_sign);
        }
        startTime = System.currentTimeMillis();
        // jpush设置alias(别名设置) :以 distributorid 为别名
        Message message = new Message();
        message.what = MSG_SET_ALIAS;
        mHandler.sendMessage(message);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (adPage != null) {
            adPage.onFree();
        }
        if (stateReceiver != null) {
            unregisterReceiver(stateReceiver);
        }
    }


    // 签到
    public void doSign(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);

        RequestTask.getInstance().qianDao(HomePageActivity.this, maps, new OnSignRequestListener());
    }

    private class OnSignRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.IS_CURENT_TIME, df.format(new Date()).toString());
                    String result = jsonObject.getString("result");
                    //data{"status":1,"message":"签到成功，团币+1，连续奖励+0","result":[73,1,0,1]}
                    JSONArray array = new JSONArray(result);
                    String tuanbi_ = array.get(0).toString();
                    lianxujiangli = array.get(2).toString();
                    lianxu_num = array.get(3).toString();
                    showSignDialog(lianxu_num, lianxujiangli);
                    PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi_ + "");
                } else if (status.equals("0")) {
//                    is_sign = "1";
//                    MyToast.show(HomePageActivity.this, "您已签到");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void showSignDialog(String lianxunum, String lianxujiangli) {
        View view = getLayoutInflater().inflate(R.layout.dialog_qiandao, null);
        final Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.MATCH_PARENT));

        ImageView img_day_one = (ImageView) view.findViewById(R.id.img_day_one);
        ImageView img_day_two = (ImageView) view.findViewById(R.id.img_day_two);
        ImageView img_day_three = (ImageView) view.findViewById(R.id.img_day_three);
        ImageView img_day_four = (ImageView) view.findViewById(R.id.img_day_four);
        ImageView img_day_five = (ImageView) view.findViewById(R.id.img_day_five);
        ImageView img_day_six = (ImageView) view.findViewById(R.id.img_day_six);
        ImageView img_day_seven = (ImageView) view.findViewById(R.id.img_day_seven);

        View view_one = (View) view.findViewById(R.id.view_one);
        View view_two = (View) view.findViewById(R.id.view_two);
        View view_three = (View) view.findViewById(R.id.view_three);
        View view_four = (View) view.findViewById(R.id.view_four);
        View view_five = (View) view.findViewById(R.id.view_five);
        View view_six = (View) view.findViewById(R.id.view_six);
        TextView tv_day_nums = (TextView) view.findViewById(R.id.tv_day_nums);
        TextView tv_continue_day = (TextView) view.findViewById(R.id.tv_continue_day);
        tv_day_nums.setText(lianxunum);
        tv_continue_day.setText("+" + lianxujiangli);
        if (lianxunum.equals("1")) {
            img_day_one.setBackgroundResource(R.mipmap.sign_deep_orange_one);
            img_day_two.setBackgroundResource(R.mipmap.sign_light_two);
            img_day_three.setBackgroundResource(R.mipmap.sign_light_two);
            img_day_four.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_five.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_six.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_seven.setBackgroundResource(R.mipmap.sign_light_six);

            view_one.setBackgroundResource(R.color.bg_c_eight);
            view_two.setBackgroundResource(R.color.bg_c_eight);
            view_three.setBackgroundResource(R.color.bg_c_eight);
            view_four.setBackgroundResource(R.color.bg_c_eight);
            view_five.setBackgroundResource(R.color.bg_c_eight);
            view_six.setBackgroundResource(R.color.bg_c_eight);
        } else if (lianxunum.equals("2")) {
            img_day_one.setBackgroundResource(R.mipmap.sign_deep_orange_one);
            img_day_two.setBackgroundResource(R.mipmap.sign_deep_orange_two);
            img_day_three.setBackgroundResource(R.mipmap.sign_light_two);
            img_day_four.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_five.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_six.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_seven.setBackgroundResource(R.mipmap.sign_light_six);

            view_one.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_two.setBackgroundResource(R.color.bg_c_eight);
            view_three.setBackgroundResource(R.color.bg_c_eight);
            view_four.setBackgroundResource(R.color.bg_c_eight);
            view_five.setBackgroundResource(R.color.bg_c_eight);
            view_six.setBackgroundResource(R.color.bg_c_eight);
        } else if (lianxunum.equals("3")) {
            img_day_one.setBackgroundResource(R.mipmap.sign_deep_orange_one);
            img_day_two.setBackgroundResource(R.mipmap.sign_deep_orange_two);
            img_day_three.setBackgroundResource(R.mipmap.sign_deep_orange_two);
            img_day_four.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_five.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_six.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_seven.setBackgroundResource(R.mipmap.sign_light_six);

            view_one.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_two.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_three.setBackgroundResource(R.color.bg_c_eight);
            view_four.setBackgroundResource(R.color.bg_c_eight);
            view_five.setBackgroundResource(R.color.bg_c_eight);
            view_six.setBackgroundResource(R.color.bg_c_eight);
        } else if (lianxunum.equals("4")) {
            img_day_one.setBackgroundResource(R.mipmap.sign_deep_orange_one);
            img_day_two.setBackgroundResource(R.mipmap.sign_deep_orange_two);
            img_day_three.setBackgroundResource(R.mipmap.sign_deep_orange_two);
            img_day_four.setBackgroundResource(R.mipmap.sign_deep_orange_three);
            img_day_five.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_six.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_seven.setBackgroundResource(R.mipmap.sign_light_six);

            view_one.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_two.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_three.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_four.setBackgroundResource(R.color.bg_c_eight);
            view_five.setBackgroundResource(R.color.bg_c_eight);
            view_six.setBackgroundResource(R.color.bg_c_eight);
        } else if (lianxunum.equals("5")) {
            img_day_one.setBackgroundResource(R.mipmap.sign_deep_orange_one);
            img_day_two.setBackgroundResource(R.mipmap.sign_deep_orange_two);
            img_day_three.setBackgroundResource(R.mipmap.sign_deep_orange_two);
            img_day_four.setBackgroundResource(R.mipmap.sign_deep_orange_three);
            img_day_five.setBackgroundResource(R.mipmap.sign_deep_orange_three);
            img_day_six.setBackgroundResource(R.mipmap.sign_light_three);
            img_day_seven.setBackgroundResource(R.mipmap.sign_light_six);

            view_one.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_two.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_three.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_four.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_five.setBackgroundResource(R.color.bg_c_eight);
            view_six.setBackgroundResource(R.color.bg_c_eight);
        } else if (lianxunum.equals("6")) {
            img_day_one.setBackgroundResource(R.mipmap.sign_deep_orange_one);
            img_day_two.setBackgroundResource(R.mipmap.sign_deep_orange_two);
            img_day_three.setBackgroundResource(R.mipmap.sign_deep_orange_two);
            img_day_four.setBackgroundResource(R.mipmap.sign_deep_orange_three);
            img_day_five.setBackgroundResource(R.mipmap.sign_deep_orange_three);
            img_day_six.setBackgroundResource(R.mipmap.sign_deep_orange_three);
            img_day_seven.setBackgroundResource(R.mipmap.sign_light_six);

            view_one.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_two.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_three.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_four.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_five.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_six.setBackgroundResource(R.color.bg_c_eight);
        } else if (lianxunum.equals("7")) {
            img_day_one.setBackgroundResource(R.mipmap.sign_deep_orange_one);
            img_day_two.setBackgroundResource(R.mipmap.sign_deep_orange_two);
            img_day_three.setBackgroundResource(R.mipmap.sign_deep_orange_two);
            img_day_four.setBackgroundResource(R.mipmap.sign_deep_orange_three);
            img_day_five.setBackgroundResource(R.mipmap.sign_deep_orange_three);
            img_day_six.setBackgroundResource(R.mipmap.sign_deep_orange_three);
            img_day_seven.setBackgroundResource(R.mipmap.sign_deep_orange_six);

            view_one.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_two.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_three.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_four.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_five.setBackgroundResource(R.color.bg_daoliu_yellow_one);
            view_six.setBackgroundResource(R.color.bg_daoliu_yellow_one);
        }

        RelativeLayout rl_close = (RelativeLayout) view.findViewById(R.id.rl_close);
        rl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        // 设置显示动画
        // window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 账号停用弹窗
     */
    public void showStop() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        dialog_stop = new Dialog(HomePageActivity.this, R.style.Mydialog);
        View view1 = View.inflate(HomePageActivity.this,
                R.layout.dialog_show_check_stop, null);
        TextView sure = (TextView) view1.findViewById(R.id.sure);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText("该账号已停用！请联系蜂优客客服。\n客服热线:400-801-7579");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_stop.dismiss();
                loginOut();
            }
        });
        dialog_stop.getWindow().setBackgroundDrawableResource(R.color.touming);
        dialog_stop.show();
        dialog_stop.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog_stop.getWindow().setContentView(view1, pm);
//        dialog_stop.setContentView(view1);
//        dialog_stop.show();
    }


    /**
     * 点击报备，弹窗提示
     */
    public void showReport(final String state) {
        dialog_report = new Dialog(HomePageActivity.this, R.style.Mydialog);
        View view1 = View.inflate(HomePageActivity.this,
                R.layout.dialog_report_turn, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        if (state.equals("2") || state.equals("4") || state.equals("6")) {
            tv_title.setText("请点击确定进行实名认证!");
        }
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_report.dismiss();
                Bundle pBundle = new Bundle();
                pBundle.putString("index", "0");
                pBundle.putString("state", state);
                openActivity(AuthenticationActivity.class, pBundle);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_report.dismiss();
            }
        });
        dialog_report.setContentView(view1);
        dialog_report.show();
    }


    /**
     * 点击报备，弹窗提示
     */
    public void showReportOne(final String state) {
        dialog_report_one = new Dialog(HomePageActivity.this, R.style.Mydialog);
        View view1 = View.inflate(HomePageActivity.this,
                R.layout.dialog_show_check_stop, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        if (state.equals("1")) {
            tv_title.setText("导游信息审核通过并实名认证后才可使用!");
        } else if (state.equals("3")) {
            tv_title.setText("审核失败请重新提交导游证信息!");
        }
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_report_one.dismiss();
            }
        });
        dialog_report_one.setContentView(view1);
        dialog_report_one.show();
    }


    /**
     * 退出登录
     */
    public void loginOut() {
        PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_DISTRIBUTORID, "");
        PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, "");
        PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, "");
        PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, "");
        PreferenceHelper.write(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "false");
        Constants.COUNTRYPATH = "";
        Constants.TOTAL_ADDRESS = "";
        openActivity(LoginActivity.class);
    }

    long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            AppManager.getInstance().AppExit(getApplicationContext());
        } else {
            MyToast.show(HomePageActivity.this, "再按一次退出应用！");
        }
        back_pressed = System.currentTimeMillis();
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
        sendBroadcast(intent);
    }

    /**
     * 根据url 判断公告跳转页面
     * "http://agent.quygt.com/supply/selectsupply" 随时赚
     * "http://agent.quygt.com/user/messagedetail/66" 公告详细
     * http://agent.quygt.com/product/details/3036?distributorId=1&source=5 商品详细
     * http://agent.quygt.com/tuanbi/mytasklist 我的任务
     * http://m.quygt.com/product/productsshare 爆品速推
     * http://agent.quygt.com/study/teacherdetail?id=20  名师讲堂
     * http://m.quygt.com/product/sellerproduct?sellerid=813&distributorId=22
     * 导游APP：首页弹出公告活动--》点击跳转时判断，判断如果地址为/product/sellerproduct时，在原来的地址后面加上&distributorId=22
     *
     * @param url
     */
    public void turnView(String url) {
        Log.e("kafhkhaf", "=============" + url);
        Bundle bundle = new Bundle();
        if (url != null && url.length() > 0) {
            try {
                if (url.equals("#")) {

                } else if (url.contains("user/messagedetail")) {
                    String[] str_urls = url.split("/");
                    bundle.putString("id", str_urls[5]);
                    bundle.putString("index", "0");
                    openActivity(NoticeDetialActivity.class, bundle);//公告详情 webview 打开
                } else if (url.contains("supply/selectsupply")) {//随时赚  原生态页面打开
                    /*if (state.equals("1") || state.equals("3")) {
                        if (!"".equals(guide_picurl) && guide_picurl != null && guide_picurl.length() > 0) {
                            if (state.equals("1")) {
                                showHintDialog(getResources().getString(R.string.guide_certificate_audit));
                            } else {
                                showHintDialog(getResources().getString(R.string.guide_certificate_failure_audit));
                            }
                        } else {
                            showGuideDialog(state);
                        }
                    }*/
                    String guide_picurl = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL);
                    String state = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
                    if (state.equals("1")) {
                        if (guide_picurl.equals("") || guide_picurl == null && guide_picurl.length() == 0) {
                            showGuideDialog(state, "请上传导游证！");
                        } else {
                            showHintDialog(getResources().getString(R.string.guide_certificate_audit));
                        }
                    } else if (state.equals("3")) {
                        showGuideDialog(state, "还没有上传导游证，是否去上传？");
                    } else {
                        bundle.putString("index", "1");
                        openActivity(ApplicationActivity.class, bundle);
                    }

                   /* bundle.putString("index", "1");
                    openActivity(ApplicationActivity.class, bundle);*/
                } else if (url.contains("product/details")) {
                    String[] str_urls = url.split("/");
                    String id_ = str_urls[5];
                    bundle.putString("type_share", "1");
                    bundle.putString("goods_id", id_);
                    bundle.putString("shop_name", shop_name);
                    openActivity(PushSpeedDetialActivity.class, bundle);//  商品详情页  webview打开
                } else if (url.contains("product/productsshare")) {
                    /*bundle.putString("index", "1");
                    bundle.putString("goods_id", "0");
                    bundle.putString("shop_name", shop_name);
                    openActivity(PushSpeedActivity.class, bundle);//  爆品速推  原生态打开打开*/
                } else if (url.contains("tuanbi")) {
                    openActivity(TuanbiMangerActivity.class);//  我的任务 原生态打开打开
                } else if (url.contains("study/teacherdetail")) {
                    String[] ids_ = url.split("[?]");
                    bundle.putString("index", "1");
                    bundle.putString("id", ids_[1].substring(3, ids_[1].length()));
                    openActivity(FamousTeacherDetialActivity.class, bundle);//  名师讲堂 详情页 原生态打开打开
                } else {
                    bundle.putString("url", url);
                    bundle.putString("index", "0");
                    openActivity(WebViewActivity.class, bundle);// 其余均是 url 文网页打开
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        startTime = System.currentTimeMillis();
    }

    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.UPDATE_CORNER_INDEX) {
            String zna_ = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ZAN_NUM, "0");
            String comment_ = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, "0");
            String gonggao_ = PreferenceHelper.readString(HomePageActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GROUP_MESSAGE_NUM, "0");
            int total_ = Integer.parseInt(zna_) + Integer.parseInt(comment_) + Integer.parseInt(gonggao_);
            if (total_ > 0) {
                tv_ring_num.setVisibility(View.VISIBLE);
                tv_ring_num.setText(total_ + "");
            } else {
                tv_ring_num.setVisibility(View.GONE);
            }
        }
    }
}
