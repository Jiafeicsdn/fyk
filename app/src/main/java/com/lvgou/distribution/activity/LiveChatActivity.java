package com.lvgou.distribution.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.StatFs;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.BannedListAdapter1;
import com.lvgou.distribution.adapter.ChatRowAdapter;
import com.lvgou.distribution.adapter.DirectChatRowAdapter;
import com.lvgou.distribution.adapter.TDirectChatRowAdapter;
import com.lvgou.distribution.bean.AddUser;
import com.lvgou.distribution.bean.GetGroupMessage;
import com.lvgou.distribution.bean.GetMemberList;
import com.lvgou.distribution.bean.GroupMember;
import com.lvgou.distribution.bean.GroupMessageExtData;
import com.lvgou.distribution.bean.JoinChatGroupBean;
import com.lvgou.distribution.bean.NickNameBean;
import com.lvgou.distribution.bean.UGSV2;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.driect.ChatClient;
import com.lvgou.distribution.driect.OnBottomMenuClickListener;
import com.lvgou.distribution.driect.entity.ActionNotification;
import com.lvgou.distribution.driect.entity.ActionNotificationJsonConverter;
import com.lvgou.distribution.driect.entity.ActionType;
import com.lvgou.distribution.driect.entity.ActionTypeJsonConverter;
import com.lvgou.distribution.driect.entity.DirectRowPictureEntity;
import com.lvgou.distribution.driect.entity.DirectRowVoiceEntity;
import com.lvgou.distribution.driect.entity.EMError;
import com.lvgou.distribution.driect.entity.GroupMessage;
import com.lvgou.distribution.driect.entity.GroupMessageJsonConverter;
import com.lvgou.distribution.driect.entity.MessageType;
import com.lvgou.distribution.driect.entity.MessageTypeJsonConverter;
import com.lvgou.distribution.driect.entity.ShortVideoGroupMessage;
import com.lvgou.distribution.driect.entity.SingleMessage;
import com.lvgou.distribution.driect.entity.TextGroupMessage;
import com.lvgou.distribution.driect.entity.TextSingleMessage;
import com.lvgou.distribution.driect.view.EaseVoiceRecorder;
import com.lvgou.distribution.luban.Luban;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.ErrorLogPresenter;
import com.lvgou.distribution.presenter.IMPersenter;
import com.lvgou.distribution.presenter.StudentFragmentDashangPresenter;
import com.lvgou.distribution.response.GetServerResponse;
import com.lvgou.distribution.response.UploadVideoResponse;
import com.lvgou.distribution.utils.AESUtils;
import com.lvgou.distribution.utils.AdViewpagerRightUtil;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.MyToast;
import com.lvgou.distribution.utils.PathUtil;
import com.lvgou.distribution.utils.PermissionManager;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.BaseView1;
import com.lvgou.distribution.view.ButtomChatMenu;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.CircleProgress;
import com.lvgou.distribution.view.CustomeChatMenu;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.IMView;
import com.lvgou.distribution.view.MyListView;
import com.lvgou.distribution.view.StudentFragmentDashangView;
import com.lvgou.distribution.widget.XListView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.ScreenUtils;
import com.xdroid.common.utils.StringUtils;
import com.xdroid.common.utils.SystemUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import microsoft.aspnet.signalr.client.Action;
import microsoft.aspnet.signalr.client.ConnectionState;
import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.LogLevel;
import microsoft.aspnet.signalr.client.Logger;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.StateChangedCallback;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.transport.AutomaticTransport;
import microsoft.aspnet.signalr.client.transport.HttpClientTransport;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.lvgou.distribution.api.Api.GetDeviceName;

/**
 * Created by Administrator on 2017/5/27.
 */

public class LiveChatActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, BaseView1, OnBottomMenuClickListener, IMView, DistributorHomeView, StudentFragmentDashangView {
    private final static int REQUEST_CODE_GALLERY = 0x33;
    private final static int REQUEST_CODE_CAMERA = 0x44;
    protected static final int REQUEST_CODE_MAP = 1;
    private String chatserver = "chat03.safetree.com.cn";
    private AdViewpagerRightUtil adViewpagerUtil;
    private String teacherDistributorID;//讲师id
    private String groupId;//群组id
    private String theme;//课程名称
    private String studyid;//课堂ID
    private String kdbdistributorid;//课代表id
    private String courseState; //课程状态
    private String distributorid;//当前导游id
    private boolean isShowBanner = false;
    private ErrorLogPresenter errorLogPresenter;
    private Drawable[] micImages;  //存储很多张话筒图片的数组
    protected EaseVoiceRecorder voiceRecorder;//语音
    protected PowerManager.WakeLock wakeLock;// 屏幕 管理工具
    IMPersenter imPersenter;
    private String userName;
    public static DbUtils dbUtils;
    private List<GroupMessageExtData> MsgList;
    private String imagePath = null;//当前图片的路径
    private String sendServer = "http://115.236.185.26:8083/";
    private int voice_length;
    private int stopRecoding;
    private DistributorHomePresenter distributorHomePresenter;
    private StudentFragmentDashangPresenter studentFragmentDashangPresenter;
    private String onChatClick;


    public class GetGroupMemberReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction.equals("com.distribution.group.member.list")) {
                if (imPersenter != null) {
                    imPersenter.member_list(distributorid, getMemberList);
                }
            }

        }
    }

    private int noreadNumer = 0;//未读消息数量

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction.equals("com.distribution.group.message")) {
                GroupMessageExtData message = (GroupMessageExtData) intent.getSerializableExtra("message");
                /*try {
                    String si = message.getSI();//消息发送者id
                    String si_id = AESUtils.Decrypt(si);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                if (message.getGI().equals(groupId)) {

                    try {
                        GroupMessageExtData byId = dbUtils.findById(GroupMessageExtData.class, message.getI());
                        if (byId == null) {
                            dbUtils.save(message);//先保存到数据库
                            newbuttomID = message.getI();//把上拉加载的id给改变
                        }

                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    if (seeTeaOrAll.equals("seeAll")) {
                        //如果处于查看全部状态
                        if (!chatRowAdapter.getData().contains(message)) {
                            chatRowAdapter.getData().add(message);
                            chatRowAdapter.notifyDataSetChanged();
                            boolean flag = isListViewReachBottomEdge(mListView);//判断是否要出现下面浮标
                            if (flag) {
                                txt_message_numer.setVisibility(View.GONE);
                                mListView.setSelection(mListView.getCount() - 1);
                            } else {
                                noreadNumer++;
                                txt_message_numer.setText(String.valueOf(noreadNumer));
                                txt_message_numer.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        //如果在查看讲师状态
                        try {
                            String si = message.getSI();//消息发送者id
                            String si_id = AESUtils.Decrypt(si);
                            if (si_id.equals(teacherDistributorID)) {
                                //如果是讲师发的消息
                                if (!chatRowAdapter.getData().contains(message)) {
                                    chatRowAdapter.getData().add(message);
                                    chatRowAdapter.notifyDataSetChanged();
                                    boolean flag = isListViewReachBottomEdge(mListView);//判断是否要出现下面浮标
                                    if (flag) {
                                        txt_message_numer_tea.setVisibility(View.GONE);
                                        mListView.setSelection(mListView.getCount() - 1);
                                    } else {
                                        noreadNumer++;
                                        txt_message_numer_tea.setText(String.valueOf(noreadNumer));
                                        txt_message_numer_tea.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            } else if (intentAction.equals("com.distribution.single.message")) {

                GroupMessageExtData message = (GroupMessageExtData) intent.getSerializableExtra("message");
                if ((message.getC() + "").equals(groupId + "IMChatGroupLockPersonStringKey")) {
                    Log.e("kjadhfkjhas", "-------------" + message);
                    //禁言
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    if (countDownTimer1 != null) {
                        countDownTimer1.cancel();
                    }
                    if (voiceRecorder.isRecording()) {
                        voiceRecorder.discardRecording();
                    }
                    customechatmenu_one.isBanned(true);
                    //隐藏软键盘
                    hintInputMethod();

                    /*onBannedListener.setViewVisibility("visibility");
                    top_cannot_other.setVisibility(View.GONE);
                    stu_fragment.cannot_other.setVisibility(View.GONE);
                    tea_fragment.cannot_other.setVisibility(View.GONE);
                    rl_teacher.setClickable(true);
                    rl_student.setClickable(true);
                    stu_fragment.setViewVisibility("visibility");
                    stu_fragment.getCustomeMenu().HideView();
                    stu_fragment.getCustomeMenu().getMyPagerAdapter().isClick = 0;
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    if (countDownTimer1 != null) {
                        countDownTimer1.cancel();
                    }
                    if (voiceRecorder.isRecording()) {
                        voiceRecorder.discardRecording();
                    }*/
//                    CustomeChatMenu.onBottomMenuClickListener
                } else if ((message.getC() + "").equals(groupId + "IMChatGroupUNLockPersonStringKey")) {
                    //解除禁言
//                    onBannedListener.setViewVisibility("gone");
                    customechatmenu_one.isBanned(false);

                } else if ((message.getC() + "").contains("IMChatGroupUNLockPersonStringKeyother")) {
                    //解除禁言，另外一个管理员发来的
                    if (dialog_img_banned_icon != null) {
                        String otheruserID = message.getC().replace("IMChatGroupUNLockPersonStringKeyother", "");
                        prohibitlist_sign = TGmd5.getMD5(studyid);
                        imPersenter.Prohibitlist(studyid, prohibitlist_sign);
                        if (dataListTmp != null && dataListTmp.size() > 0) {
                            for (int i = 0; i < dataListTmp.size(); i++) {
                                if (dataListTmp.get(i).get("DistributorID").equals("" + otheruserID)) {
                                    dialog_img_banned_icon.setImageResource(R.mipmap.icon_banned);
                                    break;
                                }
                            }
                        }
                    } else if (bannedListAdapter1 != null) {
                        prohibitlist_sign = TGmd5.getMD5(studyid);
                        imPersenter.Prohibitlist(studyid, prohibitlist_sign);
                    }

                } else if ((message.getC() + "").contains("IMChatGroupLockPersonStringKeyother")) {
                    //禁言，另外一个管理员发来的
                    if (dialog_img_banned_icon != null) {
                        String otheruserID = message.getC().replace("IMChatGroupLockPersonStringKeyother", "");
                        prohibitlist_sign = TGmd5.getMD5(studyid);
                        imPersenter.Prohibitlist(studyid, prohibitlist_sign);
                        if (BannedUid.equals(otheruserID)) {
                            dialog_img_banned_icon.setImageResource(R.mipmap.icon_banneing);
                        }
                    } else if (bannedListAdapter1 != null) {
                        prohibitlist_sign = TGmd5.getMD5(studyid);
                        imPersenter.Prohibitlist(studyid, prohibitlist_sign);
                    }
                }
            }
        }
    }
    private void hintInputMethod(){
        //隐藏软键盘
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }

    boolean isfirst = true;

    class NetworkChangeReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (!isfirst) {
                if (networkInfo != null && networkInfo.isAvailable()) {
                    createConnect();
                } else {
                    deleteConnect(false);
                }
//                createConnect();
            }
            isfirst = false;
        }
    }

    private String prohibitlist_sign;
    MyReceiver myReceiver;
    MyReceiver myReceiver1;
    IntentFilter intentFilter, intentFilter2, intentFilter3, intentFilter4, intentFilter1;
    NetworkChangeReceive networkChangeReceive;
    GetGroupMemberReceiver getGroupMemberReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_chat);
        teacherDistributorID = getIntent().getStringExtra("teacherId");
        groupId = getIntent().getStringExtra("GId");
        theme = getIntent().getStringExtra("theme");
        studyid = String.valueOf(getIntent().getIntExtra("ktID", 0));
        kdbdistributorid = String.valueOf(getIntent().getIntExtra("KDBDistributorID", 0));
        courseState = getIntent().getStringExtra("courseState");
        distributorid = PreferenceHelper.readString(LiveChatActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        userName = PreferenceHelper.readString(LiveChatActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ACCOUNT);
        onChatClick = PreferenceHelper.readString(LiveChatActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONCHATCLICK + groupId, "0");
        //注册eventbus
        EventBus.getDefault().register(this);
        MsgList = new ArrayList<>();
        errorLogPresenter = new ErrorLogPresenter(this);
        distributorHomePresenter = new DistributorHomePresenter(this);
        studentFragmentDashangPresenter = new StudentFragmentDashangPresenter(this);
        new ButtomChatMenu(this);
        init();
        syncRoot = new Object();
        ButtomChatMenu.setOnBottomMenuClickListener(this);
        initView();

        voiceRecorder = new EaseVoiceRecorder();
        wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK, "project");
        //调用新增用户接口 就是注册，进入直播间调用第一个接口
        imPersenter = new IMPersenter(this);
        imPersenter.addUser(userName);
        //获取禁言列表
        prohibitlist_sign = TGmd5.getMD5(studyid);
        imPersenter.Prohibitlist(studyid, prohibitlist_sign);
        //群聊的
        myReceiver = new MyReceiver();
        intentFilter = new IntentFilter("com.distribution.group.message");
        registerReceiver(myReceiver, intentFilter);
        //单聊的
        myReceiver1 = new MyReceiver();
        intentFilter1 = new IntentFilter("com.distribution.single.message");
        registerReceiver(myReceiver1, intentFilter1);
        intentFilter2 = new IntentFilter();
        //addAction
        intentFilter2.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceive = new NetworkChangeReceive();
        registerReceiver(networkChangeReceive, intentFilter2);
        getGroupMemberReceiver = new GetGroupMemberReceiver();
        intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.distribution.group.member.list");
        registerReceiver(getGroupMemberReceiver, intentFilter3);
        getHistoryData();//获取历史消息记录
        initClick();
    }

    private void createDatabase(String dbname) {
        String sdpath = Environment.getExternalStorageDirectory() + "/tugou";
        DbUtils.DaoConfig daoConfig = new DbUtils.DaoConfig(this);
        daoConfig.setDbVersion(1);
        daoConfig.setDbName(dbname);
        daoConfig.setDbDir(sdpath);
        dbUtils = DbUtils.create(daoConfig);
    }

    private void getHistoryData() {
        showLoadingProgressDialog(this, "");
        String dataName = TGmd5.getMD5(groupId);
        createDatabase(dataName);
        List<GroupMessageExtData> dataAll = null;
        try {
//            dbUtils.dropDb();
//            List<GroupMessageExtData> dataAll2 = dbUtils.findAll(Selector.from(GroupMessageExtData.class).orderBy("I", true).limit(3));
            dataAll = dbUtils.findAll(Selector.from(GroupMessageExtData.class).orderBy("I", true).limit(1));
        } catch (DbException e) {
            e.printStackTrace();
        }
        GetGroupMessage getGroupMessage = new GetGroupMessage();
        getGroupMessage.setGN(0);
        getGroupMessage.setDM(0);//如果是讲师就传2，如果不是就传0
        if (dataAll != null && dataAll.size() > 0) {
            getGroupMessage.setMI(dataAll.get(dataAll.size() - 1).getI());
        } else {
            getGroupMessage.setMI("");
        }
        //登录id
        getGroupMessage.setSI(Integer.parseInt(teacherDistributorID));
        getGroupMessage.setCUI(groupId);
        getGroupMessage.setMGT(-1);
        getGroupMessage.setIILM(true);
        imPersenter.GetGroupMessageExt(distributorid, getGroupMessage);

    }

    public void init() {
        micImages = new Drawable[]{
                getResources().getDrawable(R.mipmap.school_chat_audio_volume1),
                getResources().getDrawable(R.mipmap.school_chat_audio_volume2),
                getResources().getDrawable(R.mipmap.school_chat_audio_volume3),
                getResources().getDrawable(R.mipmap.school_chat_audio_volume4),
                getResources().getDrawable(R.mipmap.school_chat_audio_volume5)};
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
        }
        if (networkChangeReceive != null) {
            unregisterReceiver(networkChangeReceive);
        }
        if (getGroupMemberReceiver != null) {
            unregisterReceiver(getGroupMemberReceiver);
        }
        if (myReceiver1 != null) {
            unregisterReceiver(myReceiver1);
        }
        deleteConnect(false);
    }

    /**
     * 用来判断最后可见条目位置，用来显示下面的消息框
     *
     * @param listView
     * @return
     */
    public boolean isListViewReachBottomEdge(final ListView listView) {
        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 3) || listView.getLastVisiblePosition() == (listView.getCount() - 2)) {
            result = true;
        }
        return result;
    }

    private RelativeLayout rl_back;//返回键
    private TextView tv_title;//标题
    private ImageView img_banned;//禁言
    private RelativeLayout rl_adroot;//广告
    private ViewPager ad_viewpager;//广告banner
    private TextView tv_banner_num;//banner右下角的页数
    private LinearLayout lydots;//banner底部的圆点
    private XListView mListView;
    private ChatRowAdapter chatRowAdapter;
    private ArrayList<String> pictureUrls = new ArrayList<>();//用来存banner图片的
    private ImageView iv_title;//banner为一张图片的时候不显示banner
    private TextView tv_hindden;//控制轮播的显示和隐藏
    private RelativeLayout rl_back_top;//返回最顶部
    private RelativeLayout rl_back_botton;//返回最底部
    private TextView txt_message_numer;//最底部未读消息
    private ButtomChatMenu customechatmenu_one;//底部菜单
    private TextView tv_change_list;//只看讲师或者查看全部
    private TextView txt_message_numer_tea;//查看讲师时候的气泡

    private void initView() {
        tv_change_list = (TextView) findViewById(R.id.tv_change_list);
        WindowManager wmm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int heightm = wmm.getDefaultDisplay().getHeight();
        int iheightm = heightm * 3 / 4;
        if (distributorid.equals(teacherDistributorID)) {
            //如果是讲师
            iheightm = heightm * 5 / 7;
        } else {
            iheightm = heightm * 3 / 4;
        }

        ViewGroup.MarginLayoutParams ptv_change_list = (ViewGroup.MarginLayoutParams) tv_change_list.getLayoutParams();
        ptv_change_list.setMargins(0, iheightm, (int) ScreenUtils.dpToPx(this, 15), 0);
        tv_change_list.requestLayout();
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        img_banned = (ImageView) findViewById(R.id.img_banned);
        if (teacherDistributorID.equals(distributorid) || kdbdistributorid.equals(distributorid)) {
            //如果当前用户是讲师或者是课代表
            img_banned.setVisibility(View.VISIBLE);
        } else {
            img_banned.setVisibility(View.GONE);
        }
        txt_message_numer_tea = (TextView) findViewById(R.id.txt_message_numer_tea);
        txt_message_numer = (TextView) findViewById(R.id.txt_message_numer);
        rl_adroot = (RelativeLayout) findViewById(R.id.rl_adroot);
        ad_viewpager = (ViewPager) findViewById(R.id.ad_viewpager);
        tv_banner_num = (TextView) findViewById(R.id.tv_banner_num);
        lydots = (LinearLayout) findViewById(R.id.ly_dots);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        customechatmenu_one = (ButtomChatMenu) findViewById(R.id.customechatmenu_one);
        if (teacherDistributorID.equals(distributorid)) {
            customechatmenu_one.getLLMenu().setVisibility(View.VISIBLE);
        } else {
            customechatmenu_one.getLLMenu().setVisibility(View.GONE);
        }
        tv_title.setText("欢迎进入讨论组...");
        String bannerStr = mcache.getAsString("livechatbanner");
        if (bannerStr == null) {
            bannerStr = "";
        }
        if (bannerStr.contains(",")) {
            String[] spl = bannerStr.split(",");
            for (String s : spl) {
                pictureUrls.add(Url.ROOT + s);
            }
            iv_title.setVisibility(View.GONE);
            ad_viewpager.setVisibility(View.VISIBLE);
            tv_banner_num.setVisibility(View.VISIBLE);
            if (adViewpagerUtil == null) {
                adViewpagerUtil = new AdViewpagerRightUtil(LiveChatActivity.this, ad_viewpager, tv_banner_num, 8);
            }
            adViewpagerUtil.setDatas(pictureUrls.size(), pictureUrls);
        } else {
           /* WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams para = iv_title.getLayoutParams();
            int iheight = (int) (width * 194 / 375);
            para.height = iheight;// 控件的高强制设成
            iv_title.setLayoutParams(para);
            iv_title.setVisibility(View.VISIBLE);
            ad_viewpager.setVisibility(View.GONE);
            tv_banner_num.setVisibility(View.GONE);*/
            Glide.with(LiveChatActivity.this).load(Url.ROOT + bannerStr).error(R.mipmap.pictures_no).into(iv_title);
        }
        /*WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams para = rl_adroot.getLayoutParams();
        ViewGroup.LayoutParams paravp = ad_viewpager.getLayoutParams();
        int iheight = (int) (width * 194 / 375);
        para.height = iheight;// 控件的高强制设成
        paravp.height = iheight;
        rl_adroot.setLayoutParams(para);
        ad_viewpager.setLayoutParams(paravp);*/
        tv_hindden = (TextView) findViewById(R.id.tv_hindden);
        rl_back_top = (RelativeLayout) findViewById(R.id.rl_back_top);
        rl_back_botton = (RelativeLayout) findViewById(R.id.rl_back_botton);
        mListView = (XListView) findViewById(R.id.list_view);
        chatRowAdapter = new ChatRowAdapter(this, "all", teacherDistributorID);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(getTime());
        mListView.setDivider(null);
        mListView.setAdapter(chatRowAdapter);
        /*View chatFooter = LayoutInflater.from(LiveChatActivity.this).inflate(R.layout.live_chat_footer, null);
        mListView.addFooterView(chatFooter);*/
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        tv_hindden.setOnClickListener(this);
        rl_back_top.setOnClickListener(this);
        rl_back_botton.setOnClickListener(this);
        img_banned.setOnClickListener(this);
        txt_message_numer.setOnClickListener(this);
        txt_message_numer_tea.setOnClickListener(this);
        tv_change_list.setOnClickListener(this);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 当不滚动时
                        // 判断滚动到底部
                        if (seeTeaOrAll.equals("seeAll")) {
                            //处于查看全部状态
                            if (mListView.getLastVisiblePosition() >= (mListView.getCount() - 2)) {
                                txt_message_numer.setVisibility(View.GONE);
                                noreadNumer = 0;//未读消息数量为0
                            }
                        } else {
                            if (mListView.getLastVisiblePosition() >= (mListView.getCount() - 2)) {
                                txt_message_numer_tea.setVisibility(View.GONE);
                                noreadNumer = 0;//未读消息数量为0
                            }
                        }

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        EventBus.getDefault().post("oneclickvoicecancel");
                        customechatmenu_one.HideView();
                        customechatmenu_one.getLlbottomVoiceText().setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back://返回
                finish();
                break;
            case R.id.tv_hindden://控制幻灯片的显示和隐藏
                if (isShowBanner) {
                    rl_adroot.setVisibility(View.VISIBLE);
                    tv_hindden.setText("隐藏");
                } else {
                    rl_adroot.setVisibility(View.GONE);
                    tv_hindden.setText("课件");
                }
                isShowBanner = !isShowBanner;
                break;
            case R.id.img_banned://顶部禁言点击
                showBannedListDialog();
                break;
            case R.id.rl_back_top://返回最顶部
                gotoTop();

                break;
            case R.id.rl_back_botton://返回最底部
                if (chatListDatas != null && chatListDatas.size() > 0) {
                    gotobutton();
                }
                break;
            case R.id.txt_message_numer://底部未读消息点击
                txt_message_numer.setVisibility(View.GONE);//小圆球消失
                mListView.setSelection(mListView.getCount() - 1);//滑动到最底部
                noreadNumer = 0;//未读消息数量为0
                break;
            case R.id.txt_message_numer_tea://底部未读消息点击
                txt_message_numer_tea.setVisibility(View.GONE);//小圆球消失
                mListView.setSelection(mListView.getCount() - 1);//滑动到最底部
                noreadNumer = 0;//未读消息数量为0
                break;
            case R.id.tv_change_list: //查看全部和查看讲师点击
                txt_message_numer_tea.setVisibility(View.GONE);
                if (seeTeaOrAll.equals("seeAll")) {
                    //如果处于查看全部状态
                    seeTeaOrAll = "seeTea";
                    gotobutton();
                    tv_change_list.setBackgroundResource(R.drawable.seeall_text_radius);
                    tv_change_list.setText("查看全部");
                    if (!distributorid.equals(teacherDistributorID)) {
                        //如果不是讲师
                        customechatmenu_one.setVisibility(View.GONE);
                    }
                } else {
                    //处于查看讲师状态
                    seeTeaOrAll = "seeAll";
                    gotobutton();
                    tv_change_list.setBackgroundResource(R.drawable.seeteacher_text_radius);
                    tv_change_list.setText("只看讲师");
                    customechatmenu_one.setVisibility(View.VISIBLE);

                }
                break;
        }
    }

    private void gotoTop() {
        //返回顶部
        try {
            if (seeTeaOrAll.equals("seeAll")) {
                //查看全部
                chatListDatas = dbUtils.findAll(Selector.from(GroupMessageExtData.class).orderBy("I", false).limit(20));
            } else {
                //查看讲师
                chatListDatas = dbUtils.findAll(Selector.from(GroupMessageExtData.class).where(WhereBuilder.b("SI", "==", AESUtils.method3(teacherDistributorID).trim())).orderBy("I", false).or("C","LIKE","7f3cbcc68541d35f72feb0e4914e9b82%").limit(20));

            }

            if (chatListDatas == null) {
                chatListDatas = new ArrayList<>();
            }
            chatRowAdapter.setData(chatListDatas);
            chatRowAdapter.notifyDataSetChanged();
            if (chatListDatas.size() > 10) {
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }
            if (chatListDatas != null && chatListDatas.size() > 0) {
                newTopID = chatListDatas.get(0).getI();
                newbuttomID = chatListDatas.get(chatListDatas.size() - 1).getI();
            }
//                    mListView.smoothScrollToPosition(1);
            mListView.setSelection(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDaShangDetailDialog(String strMoney, String SI) {
        String sirealName = "";
        String tearealName = "";
        String siuid = "";
        try {
            siuid = AESUtils.Decrypt(SI);
            sirealName = hashMap.get(siuid).getRealName();
            tearealName = hashMap.get(teacherDistributorID).getRealName();
        } catch (Exception e) {
            e.printStackTrace();
        }


        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dashang_detail, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        ImageView img_head_header = (ImageView) view.findViewById(R.id.img_head_header);
        TextView tv_sendname = (TextView) view.findViewById(R.id.tv_sendname);
        TextView tv_resive_name = (TextView) view.findViewById(R.id.tv_resive_name);
        TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
        RelativeLayout dashang_exit = (RelativeLayout) view.findViewById(R.id.dashang_exit);
        tv_money.setText(strMoney);
        tv_sendname.setText(sirealName + " 赞赏了");
        tv_resive_name.setText(tearealName);
        Glide.with(LiveChatActivity.this).load(ImageUtils.getInstance().getPath(siuid)).error(R.mipmap.teacher_default_head).into(img_head_header);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        dashang_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
    }

    private String dashangmonty = "";//打赏金额
    private RelativeLayout dashang_exit;
    private TextView dashang_mycount;
    private RelativeLayout two_money;
    private TextView tv_two;
    private TextView tv_two_count;
    private RelativeLayout five_money;
    private TextView tv_five;
    private TextView tv_five_count;
    private RelativeLayout ten_money;
    private TextView tv_ten;
    private TextView tv_ten_count;
    private RelativeLayout twenty_money;
    private TextView tv_twenty;
    private TextView tv_twenty_count;
    private RelativeLayout fifty_money;
    private TextView tv_fifty;
    private TextView tv_fifty_count;
    private RelativeLayout hundred_money;
    private TextView tv_hundred;
    private TextView tv_hundred_count;
    private EditText et_addmoney;
    private ImageView im_deletemoney;
    private TextView tv_dashang_true;
//    private LinearLayout dashang_reminder;

    public void showDaShangDialog() {
        if (distributorid.equals(teacherDistributorID)) {
            //直接去打赏列表
            Bundle bundle = new Bundle();
            bundle.putString("studyid", studyid);
            Intent intent = new Intent(LiveChatActivity.this, MoreReminderActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            final String mTuanbi = PreferenceHelper.readString(LiveChatActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
            //打赏弹框
            LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.dialog_dashang, null);//自定义的布局文件
            ImageView img_head_header = (ImageView) view.findViewById(R.id.img_head_header);//头像图标
            Glide.with(LiveChatActivity.this).load(ImageUtils.getInstance().getPath(teacherDistributorID)).error(R.mipmap.teacher_default_head).into(img_head_header);
            TextView tv_dashang_name = (TextView) view.findViewById(R.id.tv_dashang_name);
            tv_dashang_name.setText(hashMap.get(teacherDistributorID).getRealName());
            dashang_exit = (RelativeLayout) view.findViewById(R.id.dashang_exit);//退出弹窗
            dashang_mycount = (TextView) view.findViewById(R.id.dashang_mycount);//我剩余的金币
            dashang_mycount.setText(mTuanbi);
            two_money = (RelativeLayout) view.findViewById(R.id.two_money);//两金币
            tv_two = (TextView) view.findViewById(R.id.tv_two);//2
            tv_two_count = (TextView) view.findViewById(R.id.tv_two_count);//2币
            five_money = (RelativeLayout) view.findViewById(R.id.five_money);//五金币
            tv_five = (TextView) view.findViewById(R.id.tv_five);//5
            tv_five_count = (TextView) view.findViewById(R.id.tv_five_count);//5币
            ten_money = (RelativeLayout) view.findViewById(R.id.ten_money);//十金币
            tv_ten = (TextView) view.findViewById(R.id.tv_ten);//10
            tv_ten_count = (TextView) view.findViewById(R.id.tv_ten_count);//10币
            twenty_money = (RelativeLayout) view.findViewById(R.id.twenty_money);//二十金币
            tv_twenty = (TextView) view.findViewById(R.id.tv_twenty);//20
            tv_twenty_count = (TextView) view.findViewById(R.id.tv_twenty_count);//20币
            fifty_money = (RelativeLayout) view.findViewById(R.id.fifty_money);//五十金币
            tv_fifty = (TextView) view.findViewById(R.id.tv_fifty);//50
            tv_fifty_count = (TextView) view.findViewById(R.id.tv_fifty_count);//50币
            hundred_money = (RelativeLayout) view.findViewById(R.id.hundred_money);//一百金币
            tv_hundred = (TextView) view.findViewById(R.id.tv_hundred);//100
            tv_hundred_count = (TextView) view.findViewById(R.id.tv_hundred_count);//100币
            et_addmoney = (EditText) view.findViewById(R.id.et_addmoney);//手动编辑金币
            im_deletemoney = (ImageView) view.findViewById(R.id.im_deletemoney);//编辑金币后面的删除按钮
            tv_dashang_true = (TextView) view.findViewById(R.id.tv_dashang_true);//打赏
//            dashang_reminder = (LinearLayout) view.findViewById(R.id.dashang_reminder);//打赏记录


            final AlertDialog mAlertDialog = builder.create();
            mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
            mAlertDialog.show();
            mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            mAlertDialog.getWindow().setContentView(view, pm);
            dashang_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                }
            });
            //两金币的点击
            two_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_addmoney.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_addmoney.getWindowToken(), 0);
                    two_money.setSelected(true);
                    tv_two.setTextColor(Color.parseColor("#ffffff"));
                    tv_two_count.setTextColor(Color.parseColor("#ffffff"));
                    five_money.setSelected(false);
                    tv_five.setTextColor(Color.parseColor("#fc4d30"));
                    tv_five_count.setTextColor(Color.parseColor("#fc4d30"));
                    ten_money.setSelected(false);
                    tv_ten.setTextColor(Color.parseColor("#fc4d30"));
                    tv_ten_count.setTextColor(Color.parseColor("#fc4d30"));
                    twenty_money.setSelected(false);
                    tv_twenty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_twenty_count.setTextColor(Color.parseColor("#fc4d30"));
                    fifty_money.setSelected(false);
                    tv_fifty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_fifty_count.setTextColor(Color.parseColor("#fc4d30"));
                    hundred_money.setSelected(false);
                    tv_hundred.setTextColor(Color.parseColor("#fc4d30"));
                    tv_hundred_count.setTextColor(Color.parseColor("#fc4d30"));
                    dashangmonty = "2";
                    tv_dashang_true.setBackgroundResource(R.drawable.bg_radius_apply_red);
                }
            });
            //五金币的点击
            five_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_addmoney.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_addmoney.getWindowToken(), 0);
                    two_money.setSelected(false);
                    tv_two.setTextColor(Color.parseColor("#fc4d30"));
                    tv_two_count.setTextColor(Color.parseColor("#fc4d30"));
                    five_money.setSelected(true);
                    tv_five.setTextColor(Color.parseColor("#ffffff"));
                    tv_five_count.setTextColor(Color.parseColor("#ffffff"));
                    ten_money.setSelected(false);
                    tv_ten.setTextColor(Color.parseColor("#fc4d30"));
                    tv_ten_count.setTextColor(Color.parseColor("#fc4d30"));
                    twenty_money.setSelected(false);
                    tv_twenty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_twenty_count.setTextColor(Color.parseColor("#fc4d30"));
                    fifty_money.setSelected(false);
                    tv_fifty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_fifty_count.setTextColor(Color.parseColor("#fc4d30"));
                    hundred_money.setSelected(false);
                    tv_hundred.setTextColor(Color.parseColor("#fc4d30"));
                    tv_hundred_count.setTextColor(Color.parseColor("#fc4d30"));
                    dashangmonty = "5";
                    tv_dashang_true.setBackgroundResource(R.drawable.bg_radius_apply_red);
                }
            });
            //十金币的点击
            ten_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_addmoney.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_addmoney.getWindowToken(), 0);
                    two_money.setSelected(false);
                    tv_two.setTextColor(Color.parseColor("#fc4d30"));
                    tv_two_count.setTextColor(Color.parseColor("#fc4d30"));
                    five_money.setSelected(false);
                    tv_five.setTextColor(Color.parseColor("#fc4d30"));
                    tv_five_count.setTextColor(Color.parseColor("#fc4d30"));
                    ten_money.setSelected(true);
                    tv_ten.setTextColor(Color.parseColor("#ffffff"));
                    tv_ten_count.setTextColor(Color.parseColor("#ffffff"));
                    twenty_money.setSelected(false);
                    tv_twenty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_twenty_count.setTextColor(Color.parseColor("#fc4d30"));
                    fifty_money.setSelected(false);
                    tv_fifty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_fifty_count.setTextColor(Color.parseColor("#fc4d30"));
                    hundred_money.setSelected(false);
                    tv_hundred.setTextColor(Color.parseColor("#fc4d30"));
                    tv_hundred_count.setTextColor(Color.parseColor("#fc4d30"));
                    dashangmonty = "10";
                    tv_dashang_true.setBackgroundResource(R.drawable.bg_radius_apply_red);
                }
            });
            //二十金币的点击
            twenty_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_addmoney.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_addmoney.getWindowToken(), 0);
                    two_money.setSelected(false);
                    tv_two.setTextColor(Color.parseColor("#fc4d30"));
                    tv_two_count.setTextColor(Color.parseColor("#fc4d30"));
                    five_money.setSelected(false);
                    tv_five.setTextColor(Color.parseColor("#fc4d30"));
                    tv_five_count.setTextColor(Color.parseColor("#fc4d30"));
                    ten_money.setSelected(false);
                    tv_ten.setTextColor(Color.parseColor("#fc4d30"));
                    tv_ten_count.setTextColor(Color.parseColor("#fc4d30"));
                    twenty_money.setSelected(true);
                    tv_twenty.setTextColor(Color.parseColor("#ffffff"));
                    tv_twenty_count.setTextColor(Color.parseColor("#ffffff"));
                    fifty_money.setSelected(false);
                    tv_fifty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_fifty_count.setTextColor(Color.parseColor("#fc4d30"));
                    hundred_money.setSelected(false);
                    tv_hundred.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_hundred_count.setTextColor(Color.parseColor("#d5aa5f"));
                    dashangmonty = "20";
                    tv_dashang_true.setBackgroundResource(R.drawable.bg_radius_apply_red);
                }
            });
            //五十金币的点击
            fifty_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_addmoney.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_addmoney.getWindowToken(), 0);
                    two_money.setSelected(false);
                    tv_two.setTextColor(Color.parseColor("#fc4d30"));
                    tv_two_count.setTextColor(Color.parseColor("#fc4d30"));
                    five_money.setSelected(false);
                    tv_five.setTextColor(Color.parseColor("#fc4d30"));
                    tv_five_count.setTextColor(Color.parseColor("#fc4d30"));
                    ten_money.setSelected(false);
                    tv_ten.setTextColor(Color.parseColor("#fc4d30"));
                    tv_ten_count.setTextColor(Color.parseColor("#fc4d30"));
                    twenty_money.setSelected(false);
                    tv_twenty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_twenty_count.setTextColor(Color.parseColor("#fc4d30"));
                    fifty_money.setSelected(true);
                    tv_fifty.setTextColor(Color.parseColor("#ffffff"));
                    tv_fifty_count.setTextColor(Color.parseColor("#ffffff"));
                    hundred_money.setSelected(false);
                    tv_hundred.setTextColor(Color.parseColor("#fc4d30"));
                    tv_hundred_count.setTextColor(Color.parseColor("#fc4d30"));
                    dashangmonty = "50";
                    tv_dashang_true.setBackgroundResource(R.drawable.bg_radius_apply_red);
                }
            });
            //一百金币的点击
            hundred_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_addmoney.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_addmoney.getWindowToken(), 0);
                    two_money.setSelected(false);
                    tv_two.setTextColor(Color.parseColor("#fc4d30"));
                    tv_two_count.setTextColor(Color.parseColor("#fc4d30"));
                    five_money.setSelected(false);
                    tv_five.setTextColor(Color.parseColor("#fc4d30"));
                    tv_five_count.setTextColor(Color.parseColor("#fc4d30"));
                    ten_money.setSelected(false);
                    tv_ten.setTextColor(Color.parseColor("#fc4d30"));
                    tv_ten_count.setTextColor(Color.parseColor("#fc4d30"));
                    twenty_money.setSelected(false);
                    tv_twenty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_twenty_count.setTextColor(Color.parseColor("#fc4d30"));
                    fifty_money.setSelected(false);
                    tv_fifty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_fifty_count.setTextColor(Color.parseColor("#fc4d30"));
                    hundred_money.setSelected(true);
                    tv_hundred.setTextColor(Color.parseColor("#ffffff"));
                    tv_hundred_count.setTextColor(Color.parseColor("#ffffff"));
                    dashangmonty = "100";
                    tv_dashang_true.setBackgroundResource(R.drawable.bg_radius_apply_red);

                }
            });
            et_addmoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    two_money.setSelected(false);
                    tv_two.setTextColor(Color.parseColor("#fc4d30"));
                    tv_two_count.setTextColor(Color.parseColor("#fc4d30"));
                    five_money.setSelected(false);
                    tv_five.setTextColor(Color.parseColor("#fc4d30"));
                    tv_five_count.setTextColor(Color.parseColor("#fc4d30"));
                    ten_money.setSelected(false);
                    tv_ten.setTextColor(Color.parseColor("#fc4d30"));
                    tv_ten_count.setTextColor(Color.parseColor("#fc4d30"));
                    twenty_money.setSelected(false);
                    tv_twenty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_twenty_count.setTextColor(Color.parseColor("#fc4d30"));
                    fifty_money.setSelected(false);
                    tv_fifty.setTextColor(Color.parseColor("#fc4d30"));
                    tv_fifty_count.setTextColor(Color.parseColor("#fc4d30"));
                    hundred_money.setSelected(false);
                    tv_hundred.setTextColor(Color.parseColor("#fc4d30"));
                    tv_hundred_count.setTextColor(Color.parseColor("#fc4d30"));
                    dashangmonty = "";
                    tv_dashang_true.setBackgroundResource(R.drawable.bg_radius_apply_nomal);
                }
            });
           /* dashang_reminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                MyToast.show(getActivity(), "打赏记录");
//                Bundle bundle = new Bundle();
//                bundle.putStringArrayList("image_urls", "");
                    Intent intent = new Intent(LiveChatActivity.this, MoreReminderActivity.class);
//                intent.putExtras(bundle);
                    startActivity(intent);
                }
            });*/
            et_addmoney.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0) {
                        dashangmonty = "";
                        im_deletemoney.setVisibility(View.GONE);
                        tv_dashang_true.setBackgroundResource(R.drawable.bg_radius_apply_nomal);
                    } else {
                        dashangmonty = s.toString();
                        im_deletemoney.setVisibility(View.VISIBLE);
                        tv_dashang_true.setBackgroundResource(R.drawable.bg_radius_apply_red);
                    }

                }
            });
            im_deletemoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dashangmonty = "";
                    et_addmoney.setText("");
                    tv_dashang_true.setBackgroundResource(R.drawable.bg_radius_apply_nomal);
                }
            });

            tv_dashang_true.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dashangmonty.equals("")) {
                        //如果没有输入和没有选中金币
                        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        AlertDialog.Builder builder = new AlertDialog.Builder(LiveChatActivity.this);
                        LayoutInflater inflater = LayoutInflater.from(LiveChatActivity.this);
                        View view = inflater.inflate(R.layout.dialog_dashang_reminder, null);//自定义的布局文件
                        TextView tv_reminder_sure = (TextView) view.findViewById(R.id.tv_reminder_sure);

                        final AlertDialog mAlertDialog = builder.create();
                        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
                        mAlertDialog.show();
                        mAlertDialog.getWindow().setContentView(view, pm);
                        tv_reminder_sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAlertDialog.dismiss();
                            }
                        });

                    } else {
                        //已经输入或者已经选好金币
                        int idashagn = Integer.parseInt(dashangmonty);
                        String distributorid = PreferenceHelper.readString(LiveChatActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                        if (Integer.parseInt(mTuanbi) < idashagn) {
                            LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                            AlertDialog.Builder builder = new AlertDialog.Builder(LiveChatActivity.this);
                            LayoutInflater inflater = LayoutInflater.from(LiveChatActivity.this);
                            View view1 = inflater.inflate(R.layout.dialog_lack_conis, null);//自定义的布局文件
                            TextView message = (TextView) view1.findViewById(R.id.message);
                            message.setTextColor(Color.parseColor("#000000"));
                            message.setText("团币不足，请充值！");
                            TextView tv_sure = (TextView) view1.findViewById(R.id.tv_sure);
                            tv_sure.setText("去充值");
                            tv_sure.setTextColor(Color.parseColor("#fc4d30"));
                            TextView tv_cancel = (TextView) view1.findViewById(R.id.tv_cancel);
                            tv_cancel.setText("取消");
                            tv_cancel.setTextColor(Color.parseColor("#000000"));
                            final AlertDialog mAlertDialog = builder.create();
                            mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
                            mAlertDialog.show();
                            mAlertDialog.getWindow().setContentView(view1, pm);
                            tv_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mAlertDialog.dismiss();
                                }
                            });
                            tv_sure.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //跳转到充值页面
                                    Intent intent = new Intent(LiveChatActivity.this, RechargeMoneyActivity.class);
                                    startActivity(intent);
                                    mAlertDialog.dismiss();
                                }
                            });
                        } else {
                            showLoadingProgressDialog(LiveChatActivity.this, "");
                            String sign = TGmd5.getMD5(distributorid + studyid + dashangmonty);
                            studentFragmentDashangPresenter.dashang(distributorid, studyid, Integer.parseInt(dashangmonty), sign);
                        }

                        mAlertDialog.dismiss();

                    }
                }
            });
        }

    }

    private String seeTeaOrAll = "seeAll";

    private ImageView im_nolist;
    private BannedListAdapter1 bannedListAdapter1;

    /**
     * 禁言列表
     */
    public void showBannedListDialog() {
        prohibitlist_sign = TGmd5.getMD5(studyid);
        imPersenter.Prohibitlist(studyid, prohibitlist_sign);
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_banned_list, null);//自定义的布局文件
        TextView tv_complete = (TextView) view.findViewById(R.id.tv_complete);
        RelativeLayout img_closs_banned = (RelativeLayout) view.findViewById(R.id.img_closs_banned);
        im_nolist = (ImageView) view.findViewById(R.id.im_nolist);
        im_nolist.setVisibility(View.VISIBLE);


        MyListView banned_listview = (MyListView) view.findViewById(R.id.banned_listview);
        banned_listview.setDivider(null);
        bannedListAdapter1 = new BannedListAdapter1(this);
        bannedListAdapter1.setmAdapterListener(adapterCallBack);
        bannedListAdapter1.setData(new ArrayList<HashMap<String, Object>>());
        banned_listview.setAdapter(bannedListAdapter1);


        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(view, pm);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        img_closs_banned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
    }

    BannedListAdapter1.BannedAdapterListener adapterCallBack = new BannedListAdapter1.BannedAdapterListener() {
        @Override
        public void releaseBanned(String distributeId) {
            String release_sign = TGmd5.getMD5(kdbdistributorid + studyid + distributeId);
            imPersenter.Releaseshutup(kdbdistributorid, studyid, distributeId, release_sign);
            //单聊的禁言发消息
            ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, groupId + "IMChatGroupUNLockPersonStringKey", distributeId);
            if (distributorid.equals(teacherDistributorID)) {
                //如果是讲师
                ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupUNLockPersonStringKeyother" + distributeId, kdbdistributorid);
            } else {
                //课代表
                ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupUNLockPersonStringKeyother" + distributeId, teacherDistributorID);

            }
        }
    };

    public void showHeaderDialog(String uid) {
        if (distributorid.equals(uid)) {
            //如果点击自己头像，就进入自己主页
            String sign = TGmd5.getMD5("" + distributorid + uid);
            showLoadingProgressDialog(this, "");
            distributorHomePresenter.distributorHome(distributorid, uid, sign);

        } else if (uid.equals(teacherDistributorID)) {
            String sign = TGmd5.getMD5("" + distributorid + uid);
            showLoadingProgressDialog(this, "");
            distributorHomePresenter.distributorHome(distributorid, uid, sign);
        } else if (distributorid.equals(kdbdistributorid) || distributorid.equals(teacherDistributorID)) {
            showBannedDialog(uid);
        } else {
            String sign = TGmd5.getMD5("" + distributorid + uid);
            showLoadingProgressDialog(this, "");
            distributorHomePresenter.distributorHome(distributorid, uid, sign);

        }
    }

    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject distributorModeljsa = jsa.getJSONObject(0);
            if (distributorModeljsa.get("UserType").toString().equals("3")) {
                //如果是讲师
                Intent intent1 = new Intent(LiveChatActivity.this, TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(LiveChatActivity.this, DistributorHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnDistributorHomeFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeDistributorHomeProgress() {

    }

    /**
     * 禁言弹框
     */
//    private Dialog banned_dialog;
    ImageView dialog_img_banned_icon;
    private String BannedUid = "";

    public void showBannedDialog(String uid) {
        BannedUid = uid;
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final NickNameBean nickNameBean = hashMap.get(uid);
        View view = inflater.inflate(R.layout.banned_dialog1, null);//自定义的布局文件

        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);


        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.img_head_pic);
        Glide.with(this).load(ImageUtils.getInstance().getPath(String.valueOf(nickNameBean.getID()))).into(circleImageView);
        ImageView img_teacher_label = (ImageView) view.findViewById(R.id.img_teacher_label);
        if (nickNameBean.getUserType() == 3) {
            img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
        } else if (nickNameBean.getUserType() == 2) {
           /* if (nickNameBean.getState() == 5) {
                img_teacher_label.setImageResource(R.mipmap.icon_certified);
            } else {
                img_teacher_label.setVisibility(View.GONE);
            }*/
            img_teacher_label.setVisibility(View.GONE);
        } else if (nickNameBean.getUserType() == 1) {
            img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
        }
        TextView txt_user_name = (TextView) view.findViewById(R.id.txt_user_name);
        txt_user_name.setText(nickNameBean.getRealName());
        RelativeLayout rl_go_home = (RelativeLayout) view.findViewById(R.id.rl_go_home);
        dialog_img_banned_icon = (ImageView) view.findViewById(R.id.img_banned_icon);
        RelativeLayout rl_banned_speak = (RelativeLayout) view.findViewById(R.id.rl_banned_speak);
        dialog_img_banned_icon.setImageResource(R.mipmap.icon_banned);
        ImageView img_teacher_certified = (ImageView) view.findViewById(R.id.img_teacher_certified);
        if (nickNameBean.getUserType() == 3) {
            img_teacher_certified.setVisibility(View.VISIBLE);
        } else {
            img_teacher_certified.setVisibility(View.GONE);
        }


        if (dataListTmp != null && dataListTmp.size() > 0) {
            for (int i = 0; i < dataListTmp.size(); i++) {
                if (dataListTmp.get(i).get("DistributorID").equals("" + nickNameBean.getID())) {
                    dialog_img_banned_icon.setImageResource(R.mipmap.icon_banneing);
                    break;
                }
               /* if (bannedUserBeanList.get(i).getDistributorID() == nickNameBean.getID()) {

                }*/
            }
        } else {
            dialog_img_banned_icon.setImageResource(R.mipmap.icon_banned);
        }

        rl_banned_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isbanned = false;
                String banned_sign = TGmd5.getMD5(kdbdistributorid + studyid + nickNameBean.getID());
                if (dataListTmp != null && dataListTmp.size() > 0) {
                    for (int i = 0; i < dataListTmp.size(); i++) {
                        if (dataListTmp.get(i).get("DistributorID").equals("" + nickNameBean.getID())) {
                            //解除禁言
                            imPersenter.Releaseshutup(kdbdistributorid, studyid, String.valueOf(nickNameBean.getID()), banned_sign);
                            ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, groupId + "IMChatGroupUNLockPersonStringKey", String.valueOf(nickNameBean.getID()));
                            isbanned = true;
                            if (distributorid.equals(teacherDistributorID)) {
                                //如果是讲师
                                ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupUNLockPersonStringKeyother" + String.valueOf(nickNameBean.getID()), kdbdistributorid);
                            } else {
                                //课代表
                                ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupUNLockPersonStringKeyother" + String.valueOf(nickNameBean.getID()), teacherDistributorID);

                            }
                            break;
                        }
                        /*if (bannedUserBeanList.get(i).getDistributorID() == nickNameBean.getID()) {

                        }*/
                    }
                    if (!isbanned) {
                        imPersenter.Bannedtopost(kdbdistributorid, studyid, String.valueOf(nickNameBean.getID()), banned_sign);
                        //禁言开始
                        ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, groupId + "IMChatGroupLockPersonStringKey", String.valueOf(nickNameBean.getID()));
                       /* stu_fragment.getCustomeMenu().HideView();
                        if (voiceRecorder.isRecording()) {
                            voiceRecorder.discardRecording();
                        }
*/
                        if (distributorid.equals(teacherDistributorID)) {
                            //如果是讲师
                            ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupLockPersonStringKeyother" + String.valueOf(nickNameBean.getID()), kdbdistributorid);
                        } else {
                            //课代表
                            ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupLockPersonStringKeyother" + String.valueOf(nickNameBean.getID()), teacherDistributorID);

                        }
                    }
                } else {
                    imPersenter.Bannedtopost(kdbdistributorid, studyid, String.valueOf(nickNameBean.getID()), banned_sign);
                    //禁言开始
                    ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, groupId + "IMChatGroupLockPersonStringKey", String.valueOf(nickNameBean.getID()));
                    /*stu_fragment.getCustomeMenu().HideView();
                    if (voiceRecorder.isRecording()) {
                        voiceRecorder.discardRecording();
                    }*/
                    if (distributorid.equals(teacherDistributorID)) {
                        //如果是讲师
                        ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupLockPersonStringKeyother" + String.valueOf(nickNameBean.getID()), kdbdistributorid);
                    } else {
                        //课代表
                        ButtomChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupLockPersonStringKeyother" + String.valueOf(nickNameBean.getID()), teacherDistributorID);

                    }
                }
            }
        });
        rl_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign = TGmd5.getMD5("" + distributorid + nickNameBean.getID());
                showLoadingProgressDialog(LiveChatActivity.this, "");
                distributorHomePresenter.distributorHome(distributorid, nickNameBean.getID() + "", sign);
                /*if (nickNameBean.getUserType() == 1) {
                    Intent intent = new Intent(LiveChatActivity.this, OfficialHomePageActivity.class);
                    intent.putExtra("seeDistributorId", nickNameBean.getID());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LiveChatActivity.this, UserPersonalCenterActivity.class);
                    intent.putExtra("distributorid", nickNameBean.getID());
                    startActivity(intent);
                }*/
                mAlertDialog.dismiss();
            }
        });
        RelativeLayout img_closs_dialog = (RelativeLayout) view.findViewById(R.id.img_closs_dialog);
        img_closs_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });

    }

    private void gotobutton() {
        try {
            List<GroupMessageExtData> bottomListDatas = new ArrayList<>();
            if (seeTeaOrAll.equals("seeAll")) {
                bottomListDatas = dbUtils.findAll(Selector.from(GroupMessageExtData.class).orderBy("I", true).limit(20));
            } else {
                bottomListDatas = dbUtils.findAll(Selector.from(GroupMessageExtData.class).where(WhereBuilder.b("SI", "==", AESUtils.method3(teacherDistributorID).trim().trim())).or("C","LIKE","7f3cbcc68541d35f72feb0e4914e9b82%").orderBy("I", true).limit(20));
//                String sql="";
//                bottomListDatas=  dbUtils.findDbModelAll(sql); // 自定义sql查询
//                dbUtils.execNonQuery(sql); // 执行自定义sql
            }
            if (bottomListDatas == null) {
                bottomListDatas = new ArrayList<>();
            }
            chatListDatas.clear();
            for (int i = bottomListDatas.size() - 1; i >= 0; i--) {
                chatListDatas.add(bottomListDatas.get(i));
            }
            chatRowAdapter.setData(chatListDatas);
            chatRowAdapter.notifyDataSetChanged();
            if (chatListDatas.size() > 10) {
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }
            if (chatListDatas != null && chatListDatas.size() > 0) {
                newTopID = chatListDatas.get(0).getI();
                newbuttomID = chatListDatas.get(chatListDatas.size() - 1).getI();
            }

//                    mListView.smoothScrollToPosition(chatListDatas.size());
            mListView.setSelection(mListView.getCount() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String newTopID = "";
    private List<GroupMessageExtData> chatListDatas = new ArrayList<>();

    @Override
    public void onRefresh() {
        //下拉刷新
        try {
            List<GroupMessageExtData> refresListDatas = new ArrayList<>();
            if (seeTeaOrAll.equals("seeAll")) {
                refresListDatas = dbUtils.findAll(Selector.from(GroupMessageExtData.class).orderBy("I", true).where(WhereBuilder.b("I", "<", newTopID)).limit(20));
            } else {
                refresListDatas = dbUtils.findAll(Selector.from(GroupMessageExtData.class).orderBy("I", true).where(WhereBuilder.b("I", "<", newTopID)).and(WhereBuilder.b("SI", "==", AESUtils.method3(teacherDistributorID).trim()).or("C","LIKE","7f3cbcc68541d35f72feb0e4914e9b82%")).limit(20));
            }
            mListView.stopRefresh();
            if (refresListDatas != null && refresListDatas.size() > 0) {
                List<GroupMessageExtData> linShiEntities = new ArrayList<>();
                linShiEntities.addAll(chatListDatas);
                chatListDatas.clear();
                for (int i = refresListDatas.size() - 1; i >= 0; i--) {
                    chatListDatas.add(refresListDatas.get(i));
                }
                chatListDatas.addAll(linShiEntities);
                newTopID = chatListDatas.get(0).getI();
                chatRowAdapter.notifyDataSetChanged();
                mListView.setSelection(refresListDatas.size() - 1);
                mListView.setPullLoadEnable(true);
            } else {
                MyToast.show(this, "已经到顶了");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String newbuttomID = "";

    @Override
    public void onLoadMore() {
        //上拉加载
        try {
            List<GroupMessageExtData> loadListDatas = new ArrayList<>();
            if (seeTeaOrAll.equals("seeAll")) {
                loadListDatas = dbUtils.findAll(Selector.from(GroupMessageExtData.class).orderBy("I", false).where(WhereBuilder.b("I", ">", newbuttomID)).limit(20));
            } else {
                loadListDatas = dbUtils.findAll(Selector.from(GroupMessageExtData.class).orderBy("I", false).where(WhereBuilder.b("I", ">", newbuttomID)).and(WhereBuilder.b("SI", "==", AESUtils.method3(teacherDistributorID).trim()).or("C","LIKE","7f3cbcc68541d35f72feb0e4914e9b82%")).limit(20));
            }
            mListView.stopLoadMore();
            if (loadListDatas != null && loadListDatas.size() > 0) {
                newbuttomID = loadListDatas.get(loadListDatas.size() - 1).getI();
                chatListDatas.addAll(loadListDatas);
                chatRowAdapter.notifyDataSetChanged();
            } else {
                MyToast.show(this, "已经到底了");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void OnStudentFragmentDashangSuccCallBack(String state, String respanse) {
        //打赏成功
        closeLoadingProgressDialog();
        ButtomChatMenu.onBottomMenuClickListener.OnBottomMenuClickListener(2, "7f3cbcc68541d35f72feb0e4914e9b82" + dashangmonty);
        String mTuanbi = PreferenceHelper.readString(LiveChatActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
        int ituanbi = Integer.parseInt(mTuanbi) - Integer.parseInt(dashangmonty);
        PreferenceHelper.write(LiveChatActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, ituanbi + "");
        dashangmonty = "";
    }

    @Override
    public void OnStudentFragmentDashangFialCallBack(String state, String respanse) {
        //打赏失败
        closeLoadingProgressDialog();
    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteSuccessCallBack(String s) {
        /**
         *直播间禁言 返回回调
         */
        if (dialog_img_banned_icon != null) {
            dialog_img_banned_icon.setImageResource(R.mipmap.icon_banneing);
        }
        prohibitlist_sign = TGmd5.getMD5(studyid);
        imPersenter.Prohibitlist(studyid, prohibitlist_sign);
    }

    @Override
    public void excuteFailedCallBack(String s) {
        Log.e("lkjasdlfkha", "=========" + s);
    }

    @Override
    public void excuteErrSuccessCallBack(String s) {

    }

    @Override
    public void excuteErrFailedCallBack(String s) {

    }

    /**
     * 点击事件属于群聊
     *
     * @param postion
     * @param sequence
     */
    @Override
    public void OnBottomMenuClickListener(int postion, String sequence) {
//        final ListView adapterView;
        switch (postion) {
            case 2: // 发送按钮
                //发送emoji表情
                GroupMessageExtData groupMessageExtData = new GroupMessageExtData();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                groupMessageExtData.setCT(simpleDateFormat.format(new Date()));
                groupMessageExtData.setC(sequence);
                groupMessageExtData.setMT(0);
                groupMessageExtData.setIsSendSuccess(false);
                groupMessageExtData.setMessage_state(false);

                groupMessageExtData.setI(String.valueOf(System.currentTimeMillis()));
                try {
                    groupMessageExtData.setSI(AESUtils.method3(distributorid).trim());
                } catch (Exception e) {

                }
                if (!chatRowAdapter.getData().contains(groupMessageExtData)) {
                    chatRowAdapter.getData().add(groupMessageExtData);
                    chatRowAdapter.notifyDataSetChanged();
                    mListView.setSelection(mListView.getCount() - 1);
                }

                /*if (teacherDistributorID.equals(distributorid)) {
                    TDirectChatRowAdapter adapter = tea_fragment.directChatRowAdapter;
                    adapterView = tea_fragment.listView;
                    adapter.getData().add(groupMessageExtData);
                    adapter.notifyDataSetChanged();
                } else {
                    DirectChatRowAdapter adapter = stu_fragment.directChatRowAdapter;
                    adapterView = stu_fragment.listView;
                    adapter.getData().add(groupMessageExtData);
                    adapter.notifyDataSetChanged();
                }
                if (teacherDistributorID.equals(distributorid)) {
                    tea_fragment.listView.setSelection(tea_fragment.listView.getCount() - 1);
                } else {
                    stu_fragment.listView.setSelection(stu_fragment.listView.getCount() - 1);
                }*/
                TextGroupMessage textGroupMessage = new TextGroupMessage();
                textGroupMessage.setGroupId(groupId);
                textGroupMessage.setContent(sequence);
                MsgList.add(groupMessageExtData);
                SignalRFuture<String> signalRFuture = chatClient.send(textGroupMessage);
                if (null != signalRFuture) {
                    signalRFuture.done(new Action<String>() {
                        @Override
                        public void run(final String s) throws Exception {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    MsgList.get(0).setI(s);
                                    for (int i = 0; i < 100 && i < chatRowAdapter.getData().size(); i++) {
                                        if (MsgList.get(0).getCT().equals(chatRowAdapter.getData().get(i).getCT())) {
                                            chatRowAdapter.getData().get(i).setIsSendSuccess(true);
                                            chatRowAdapter.getData().get(i).setI(s);
                                            try {
                                                GroupMessageExtData byId = dbUtils.findById(GroupMessageExtData.class, MsgList.get(0).getI());
                                                if (byId == null) {
                                                    dbUtils.save(MsgList.get(0));//保存数据到数据库
                                                    newbuttomID = s;//把上拉加载的id给改变
                                                }
                                            } catch (DbException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    chatRowAdapter.notifyDataSetChanged();
                                   /* if (teacherDistributorID.equals(distributorid)) {
                                        TDirectChatRowAdapter adapter = tea_fragment.directChatRowAdapter;
                                        for (int i = 0; i < 100 && i < adapter.getData().size(); i++) {
                                            if (MsgList.get(0).getCT().equals(adapter.getData().get(i).getCT())) {
                                                adapter.getData().get(i).setIsSendSuccess(true);
                                            }
                                        }
                                        adapter.notifyDataSetChanged();

                                    } else {
                                        DirectChatRowAdapter adapter = stu_fragment.directChatRowAdapter;
                                        for (int i = 0; i < 100 && i < adapter.getData().size(); i++) {
                                            if (MsgList.get(0).getCT().equals(adapter.getData().get(i).getCT())) {
                                                adapter.getData().get(i).setIsSendSuccess(true);
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                    }*/
                                    MsgList.remove(0);
                                }
                            });
                        }
                    });
                    signalRFuture.onError(new ErrorCallback() {
                        @Override
                        public void onError(Throwable throwable) {

                        }
                    });
                }
                break;
            case 3:// 照片 选择
                // 权限管理工具类
                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                    @Override
                    public void permissionGranted(int requestCode) {
                        if (requestCode == 99) {
                            PhotoPickerIntent intent = new PhotoPickerIntent(LiveChatActivity.this);
                            intent.setPhotoCount(1);
                            intent.setShowCamera(true);
                            intent.setShowGif(true);
                            startActivityForResult(intent, 101);
                        }
                    }
                });
                //申请读权限，和照相机权限
                permissionManager.checkPermission(99, LiveChatActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                break;
            case 4:// 拍摄照片
                imagePath = FunctionUtils.chooseImageFromCamera(LiveChatActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
                break;
            case 5:// 录视频
                Intent intent = new Intent(LiveChatActivity.this, RecorderVideoActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MAP);
                break;
            case 6:// 语音emoji键盘，文本框
                if (chatListDatas != null && chatListDatas.size() > 0) {
                    gotobutton();
                }
                rl_adroot.setVisibility(View.GONE);
                tv_hindden.setText("课件");
                break;
        }
    }

    private PermissionManager permissionManager;

    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private ArrayList<String> data_list = new ArrayList<String>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 101) {//照片
            if (data != null) {
                data_list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                imagePath = data_list.get(0);
                compressWithRx(new File(imagePath));
            }
            return;
        }
        if (resultCode == -1) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY:
                    /*CameraUtil cameraUtil=new CameraUtil(LiveDirectActivity.this);
                    imagePath=cameraUtil.getPhotoPathByLocalUri(LiveDirectActivity.this,data);*/
                    imagePath = FunctionUtils
                            .onActivityResultForChooseImageFromGallery(LiveChatActivity.this,
                                    requestCode, resultCode, data);
                    if (!StringUtils.isEmpty(imagePath)) {
                        /**
                         *需要修改，路径为空，默认图片记得添加
                         */
                        compressWithRx(new File(imagePath));
                    }
                    break;
                case REQUEST_CODE_CAMERA:
                    if (!StringUtils.isEmpty(imagePath)) {
                        compressWithRx(new File(imagePath));
                    }
                    break;
                case REQUEST_CODE_MAP:
                    Uri uri = data.getParcelableExtra("uri");
                    String[] projects = new String[]{MediaStore.Video.Media.DATA,
                            MediaStore.Video.Media.DURATION};
                    Cursor cursor = getContentResolver().query(uri,
                            projects, null, null, null);
                    int duration = 0;
                    String filePath = null;
                    if (cursor.moveToFirst()) {
                        // 路径：MediaStore.Audio.Media.DATA
                        filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                        // 总播放时长：MediaStore.Audio.Media.DURATION
                        duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    }
                    if (cursor != null) {
                        cursor.close();
                        cursor = null;
                    }
                    File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(filePath, 5);
                        ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.close();
                        /**
                         * 发送 视频消息 操作
                         */
                        LogUtils.e("file++" + file.getAbsolutePath());
                        LogUtils.e("duration==" + duration);
                        GroupMessageExtData groupMessageExtData = new GroupMessageExtData();
                        groupMessageExtData.setSI(AESUtils.method3(distributorid).trim());
                        groupMessageExtData.setMT(3);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        groupMessageExtData.setCT(simpleDateFormat.format(new Date()));
                        groupMessageExtData.setU(filePath);
                        groupMessageExtData.setBitmap(ThumbBitmap);
                        /*dbUtils.save(groupMessageExtData);//保存数据到数据库
                        newbuttomID = groupMessageExtData.getI();//把上拉加载的id给改变*/
                        if (!chatRowAdapter.getData().contains(groupMessageExtData)){
                            chatRowAdapter.getData().add(groupMessageExtData);
                            chatRowAdapter.notifyDataSetChanged();
                            mListView.setSelection(mListView.getCount() - 2);
                        }

                        /*if (teacherDistributorID.equals(distributorid)) {
                            TDirectChatRowAdapter adapter = tea_fragment.directChatRowAdapter;
                            adapter.getData().add(groupMessageExtData);
                            adapter.notifyDataSetChanged();
                        } else {
                            DirectChatRowAdapter adapter = stu_fragment.directChatRowAdapter;
                            adapter.getData().add(groupMessageExtData);
                            adapter.notifyDataSetChanged();
                        }

                        if (teacherDistributorID.equals(distributorid)) {
                            tea_fragment.listView.setSelection(tea_fragment.listView.getCount() - 2);
                        } else {
                            stu_fragment.listView.setSelection(stu_fragment.listView.getCount() - 2);
                        }*/
                        XutilsUploadVideo(filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void compressWithRx(File file) {
        Luban.get(this)
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .asObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                    @Override
                    public Observable<? extends File> call(Throwable throwable) {
                        return Observable.empty();
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        try {
                            XutilsUploadImage(file);
                            GroupMessageExtData groupMessageExtData = new GroupMessageExtData();
                            groupMessageExtData.setSI(AESUtils.method3(distributorid).trim());
                            groupMessageExtData.setT("file://" + imagePath);
                            groupMessageExtData.setO("file://" + imagePath);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            groupMessageExtData.setCT(simpleDateFormat.format(new Date()));
                            groupMessageExtData.setMT(1);
//                            dbUtils.save(groupMessageExtData);//保存数据到数据库
//                            newbuttomID = groupMessageExtData.getI();//把上拉加载的id给改变
                            chatRowAdapter.getData().add(groupMessageExtData);
                            chatRowAdapter.notifyDataSetChanged();
                            mListView.setSelection(mListView.getCount() - 1);
                           /* if (teacherDistributorID.equals(distributorid)) {
                                TDirectChatRowAdapter adapter = tea_fragment.directChatRowAdapter;
                                adapter.getData().add(groupMessageExtData);
                                adapter.notifyDataSetChanged();
                            } else {
                                DirectChatRowAdapter adapter = stu_fragment.directChatRowAdapter;
                                adapter.getData().add(groupMessageExtData);
                                adapter.notifyDataSetChanged();
                            }

                            if (teacherDistributorID.equals(distributorid)) {
                                tea_fragment.listView.setSelection(tea_fragment.listView.getCount() - 1);
                            } else {
                                stu_fragment.listView.setSelection(stu_fragment.listView.getCount() - 1);
                            }*/

                        } catch (Exception e) {

                        }
                    }
                });
    }

//    CircleProgress circleProgress;
//    CircleProgress pic_circleProgress;
//    ProgressBar progressBar;

    private void XutilsUploadVideo(String filePath)
            throws IOException {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("file", new File(filePath), "video/*");
        HttpUtils http = new HttpUtils(1000 * 60);
        http.configResponseTextCharset("UTF-8");
        http.configCurrentHttpCacheExpiry(1000 * 60);
        http.configSoTimeout(5000 * 60);
        http.send(HttpRequest.HttpMethod.POST, sendServer + "api/UpLoadFile/UploadVideo?access_token=3178", params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        //adapter中布局progress的显示
                        /*ListView listView;
                        if (teacherDistributorID.equals(distributorid)) {
                            listView = tea_fragment.listView;
                        } else {
                            listView = stu_fragment.listView;
                        }*/
                        /*LinearLayout linearLayout = (LinearLayout) mListView.getChildAt(mListView.getCount() - 2 - mListView.getFirstVisiblePosition());

                        if (linearLayout != null) {
                            circleProgress = (CircleProgress) linearLayout.findViewById(R.id.circleProgress);
                            circleProgress.setVisibility(View.VISIBLE);
                        }*/
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        /*if (isUploading) {
                            circleProgress.setValue((int) (((int) current / (float) total) * 100));
                        }*/
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        progressDialog.dismiss();
//                        circleProgress.setVisibility(View.GONE);

                        hideBottomView();//隐藏底部

                        Gson gson = new Gson();
                        UploadVideoResponse uploadVideoResponse = gson.fromJson(responseInfo.result, UploadVideoResponse.class);
                        if (null != uploadVideoResponse && uploadVideoResponse.getData() != null) {
                            ShortVideoGroupMessage shortVideoGroupMessage = new ShortVideoGroupMessage();
                            shortVideoGroupMessage.setUrl(sendServer + uploadVideoResponse.getData().getUrl());
                            shortVideoGroupMessage.setCoverImage(sendServer + uploadVideoResponse.getData().getCoverImage());
                            shortVideoGroupMessage.setGroupId(groupId);
                            GroupMessageExtData extData = chatRowAdapter.getItem(chatRowAdapter.getCount() - 1);
                            extData.setU(sendServer + uploadVideoResponse.getData().getUrl());
                            extData.setCI(sendServer + uploadVideoResponse.getData().getCoverImage());
                            chatRowAdapter.notifyDataSetChanged();
                            /*if (teacherDistributorID.equals(distributorid)) {
                                GroupMessageExtData extData = tea_fragment.directChatRowAdapter.getItem(tea_fragment.directChatRowAdapter.getCount() - 1);
                                extData.setU(sendServer + uploadVideoResponse.getData().getUrl());
                                extData.setCI(sendServer + uploadVideoResponse.getData().getCoverImage());
                                tea_fragment.directChatRowAdapter.notifyDataSetChanged();
                            } else {
                                GroupMessageExtData extData = stu_fragment.directChatRowAdapter.getItem(stu_fragment.directChatRowAdapter.getCount() - 1);
                                extData.setU(sendServer + uploadVideoResponse.getData().getUrl());
                                extData.setCI(sendServer + uploadVideoResponse.getData().getCoverImage());
                                stu_fragment.directChatRowAdapter.notifyDataSetChanged();
                            }*/
                            try {
                                SignalRFuture<String> signalRFuture = chatClient.send(shortVideoGroupMessage);
                                if (null != signalRFuture) {
                                    signalRFuture.done(new Action<String>() {
                                        @Override
                                        public void run(String s) throws Exception {
                                            //发送成功
                                            GroupMessageExtData extData = chatRowAdapter.getItem(chatRowAdapter.getCount() - 1);
                                            extData.setI(s);
                                            GroupMessageExtData byId = dbUtils.findById(GroupMessageExtData.class, s);
                                            if (byId==null){
                                                dbUtils.save(extData);//保存数据到数据库
                                                newbuttomID = s;//把上拉加载的id给改变
//                                                mListView.setSelection(mListView.getCount() - 1);
                                            }

//                                        if (teacherDistributorID.equals(distributorid)) {
//                                            tea_fragment.listView.setSelection(tea_fragment.listView.getCount() - 1);
//                                        } else {
//                                            stu_fragment.listView.setSelection(stu_fragment.listView.getCount() - 1);
//                                        }
//                                        MyToast.makeText(LiveDirectActivity.this, "成功", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    signalRFuture.onError(new ErrorCallback() {
                                        @Override
                                        public void onError(Throwable throwable) {
                                            MyToast.makeText(LiveChatActivity.this, "失败", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } catch (Exception e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(com.lidroid.xutils.exception.HttpException error, String msg) {
//                        progressDialog.dismiss();
//                        circleProgress.setVisibility(View.GONE);
                    }

                });
    }

    private void hideBottomView() {
        customechatmenu_one.HideView();
       /* if (teacherDistributorID.equals(distributorid)) {
            tea_fragment.getCustomeMenu().HideView();
        } else {
            stu_fragment.getCustomeMenu().HideView();
        }*/
    }

    private void XutilsUploadImage(File file)
            throws IOException {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("file", file, "image/*");
        HttpUtils http = new HttpUtils(1000 * 60);
        http.configResponseTextCharset("UTF-8");
        http.configCurrentHttpCacheExpiry(1000 * 60);
        http.configSoTimeout(5000 * 60);
        http.send(HttpRequest.HttpMethod.POST, sendServer + "api/UpLoadFile/UploadImage?access_token=3178", params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                       /* ListView listView;
                        if (teacherDistributorID.equals(distributorid)) {
                            listView = tea_fragment.listView;
                        } else {
                            listView = stu_fragment.listView;
                        }*/
                        /*LinearLayout linearLayout = (LinearLayout) mListView.getChildAt(mListView.getCount() - 2);
                        if (linearLayout != null) {
                            pic_circleProgress = (CircleProgress) linearLayout.findViewById(R.id.pic_circleProgress);
                            pic_circleProgress.setVisibility(View.VISIBLE);
                        }*/
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        if (isUploading) {
//                            pic_circleProgress.setValue((int) (((int) current / (float) total) * 100));
                        }
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        hideBottomView();
                        Gson gson = new Gson();
                        UploadVideoResponse uploadVideoResponse = gson.fromJson(responseInfo.result, UploadVideoResponse.class);
//                        pic_circleProgress.setVisibility(View.GONE);
                        if (uploadVideoResponse != null && uploadVideoResponse.getData() != null) {
                            DirectRowPictureEntity directRowPictureEntity = new DirectRowPictureEntity();
                            directRowPictureEntity.setUrl(sendServer + uploadVideoResponse.getData().getUrl());
                            directRowPictureEntity.setThumbnailUrl(sendServer + uploadVideoResponse.getData().getThumbnailUrl());
                            directRowPictureEntity.setGroupId(groupId);
                            try {
                                SignalRFuture<String> signalRFuture = chatClient.send(directRowPictureEntity);
                                if (null != signalRFuture) {
                                    signalRFuture.done(new Action<String>() {
                                        @Override
                                        public void run(String s) throws Exception {
                                            GroupMessageExtData extData = chatRowAdapter.getItem(chatRowAdapter.getCount() - 1);
                                            extData.setI(s);
                                            GroupMessageExtData byId = dbUtils.findById(GroupMessageExtData.class, s);
                                            if (byId == null) {
                                                dbUtils.save(extData);//保存数据到数据库
                                                newbuttomID = s;//把上拉加载的id给改变
//                                                mListView.setSelection(mListView.getCount() - 1);
                                            }

//                                            if (teacherDistributorID.equals(distributorid)) {
//                                                tea_fragment.listView.setSelection(tea_fragment.listView.getCount() - 1);
//                                            } else {
//                                                stu_fragment.listView.setSelection(stu_fragment.listView.getCount() - 1);
//                                            }
//                                            MyToast.makeText(LiveDirectActivity.this, "成功", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    signalRFuture.onError(new ErrorCallback() {
                                        @Override
                                        public void onError(Throwable throwable) {
                                            MyToast.makeText(LiveChatActivity.this, "失败", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } catch (Exception e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(com.lidroid.xutils.exception.HttpException error, String msg) {
//                        pic_circleProgress.setVisibility(View.GONE);
                    }

                });
    }

    private void XutilsUploadVoice(String filePath)
            throws IOException {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("file", new File(filePath), "audio/mp3");
        HttpUtils http = new HttpUtils(1000 * 60);
        http.configResponseTextCharset("UTF-8");
        http.configCurrentHttpCacheExpiry(1000 * 60);
        http.configSoTimeout(5000 * 60);
        http.send(HttpRequest.HttpMethod.POST, sendServer + "api/UpLoadFile/UploadAudio?access_token=3178", params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        if (isUploading) {
                        }
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        hideBottomView();
                        Log.e("lksdjfklajhdf", "----------"+responseInfo.result );
                        TextGroupMessage text=new TextGroupMessage();
                        Gson gson = new Gson();
                        UploadVideoResponse uploadVideoResponse = gson.fromJson(responseInfo.result, UploadVideoResponse.class);
                        DirectRowVoiceEntity directRowVoiceEntity = new DirectRowVoiceEntity();
                        if (null != directRowVoiceEntity) {
                            if (uploadVideoResponse.getData() != null) {
                                directRowVoiceEntity.setUrl(sendServer + uploadVideoResponse.getData().getUrl());
                                directRowVoiceEntity.setVoicetime(voice_length);
                                directRowVoiceEntity.setGroupId(groupId);
                                try {
                                    SignalRFuture<String> signalRFuture = chatClient.send(directRowVoiceEntity);
                                    if (null != signalRFuture) {
                                        signalRFuture.done(new Action<String>() {
                                            @Override
                                            public void run(final String s) throws Exception {
                                                GroupMessageExtData extData = chatRowAdapter.getItem(chatRowAdapter.getCount() - 1);
                                                extData.setI(s);
                                                GroupMessageExtData byId = null;
                                                try {
                                                    byId = dbUtils.findById(GroupMessageExtData.class, s);
                                                    if (byId == null) {
                                                        dbUtils.save(extData);//保存数据到数据库
                                                        newbuttomID = s;//把上拉加载的id给改变
                                                    }
                                                } catch (DbException e) {
                                                    e.printStackTrace();
                                                }

//                                                mListView.setSelection(mListView.getCount() - 1);
//                                        if (teacherDistributorID.equals(distributorid)) {
//                                            tea_fragment.listView.setSelection(tea_fragment.listView.getCount() - 1);
//                                        } else {
//                                            stu_fragment.listView.setSelection(stu_fragment.listView.getCount() - 1);
//                                        }
//                                        MyToast.makeText(LiveDirectActivity.this, "成功", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        signalRFuture.onError(new ErrorCallback() {
                                            @Override
                                            public void onError(Throwable throwable) {
//                                                Log.e("kasjdhfkash", "-----------"+throwable );
                                                MyToast.makeText(LiveChatActivity.this, "失败", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                } catch (Exception e) {

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }

                });
    }

    @Override
    public void OnEditorActionListener(String s) {

    }

    /**
     * 单聊的部分
     *
     * @param postion
     * @param sequence
     * @param receiveId
     */
    @Override
    public void OnSingleChatListener(int postion, String sequence, String receiveId) {
//        final ListView adapterView;
        switch (postion) {
            case 2: // 发送按钮
                TextSingleMessage textSingleMessage = new TextSingleMessage();
                String sreceiveId = null;
                String sdistributorid = null;
                try {
                    sreceiveId = AESUtils.method3(receiveId).trim();
                    textSingleMessage.setReceiverId(sreceiveId);//接受者id
                    sdistributorid = AESUtils.method3(distributorid).trim();
                    textSingleMessage.setSenderId(sdistributorid);//发送者id
                    textSingleMessage.setContent(sequence);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                SignalRFuture<String> signalRFuture = chatClient.send(textSingleMessage);
                if (null != signalRFuture) {
                    signalRFuture.done(new Action<String>() {
                        @Override
                        public void run(String s) throws Exception {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    });
                    signalRFuture.onError(new ErrorCallback() {
                        @Override
                        public void onError(Throwable throwable) {
                        }
                    });
                }
                break;
        }
    }

    @Override
    public AddUser getParamenters() {
        AddUser addUser = null;
        try {
            String encryptBASE64 = AESUtils.method3(distributorid).trim();
            addUser = new AddUser();
            addUser.setUI(encryptBASE64);
            return addUser;
        } catch (Exception e) {

        }
        return addUser;
    }

    @Override
    public JoinChatGroupBean getParamenters2() {
        JoinChatGroupBean joinChatGroupBean = new JoinChatGroupBean();
        joinChatGroupBean.setGI(groupId);
        List<AddUser> list = new ArrayList<AddUser>();
        list.add(getParamenters());
        joinChatGroupBean.setM(list);
        return joinChatGroupBean;
    }

    @Override
    public String getParamenters3() {
        return distributorid;
    }

    @Override
    public void addUser_response(String s) {
        Log.e("hkasdfhk", "------userName返回成功-------" + s);
        //进入直播间第二步，关联群
        imPersenter.JoinChatGroup();
    }

    @Override
    public void JoinChatGroup_response(String s) {
        /**
         * 修改
         */
        imPersenter.getServer();
    }

    GetMemberList getMemberList;

    @Override
    public void getServer_response(String s) {
        getMemberList = new GetMemberList();
        List<UGSV2> list = new ArrayList<UGSV2>();
        UGSV2 ugsv2 = new UGSV2();
        ugsv2.setGI(groupId);
        ugsv2.setV(0);
        list.add(ugsv2);
        getMemberList.setIMGMV(list);
        Gson gson = new Gson();
        GetServerResponse getServerResponse = gson.fromJson(s, GetServerResponse.class);
        if (getServerResponse != null) {
            chatserver = getServerResponse.getD().getU();
            PreferenceHelper.write(LiveChatActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CHAT_SERVER, chatserver);
            createConnect();
        }
        imPersenter.member_list(distributorid, getMemberList);
//        tea_fragment.initData(2, distributorid);
//        stu_fragment.initData(1, distributorid);
    }

    //    List<GroupMember> list1;//在线群成员列表
    StringBuffer stringBuffer;

    @Override
    public void member_list_response(String s) {
        //数据的解析 获取用户信息
        try {
            closeLoadingProgressDialog();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("IMGMD");
            Gson gson = new Gson();
            List<GroupMember> list = new ArrayList<>();
            try {
                list = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<GroupMember>>() {
                }.getType());
            } catch (Exception e) {
                // TODO: handle exception
            }

            /*if (list1 == null) {
                list1 = new ArrayList<GroupMember>();
            } else {
                list1.clear();
            }*/
            stringBuffer = null;
            stringBuffer = new StringBuffer();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0) {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append(AESUtils.Decrypt(list.get(i).getFI()));

                    /*if (list.get(i).isID() == false) {
                        list1.add(list.get(i));
                    }*/
                }
            }

            String sign = TGmd5.getMD5(stringBuffer.toString());
            imPersenter.GetNickName(stringBuffer.toString(), sign);//根据聊天中的用户信息请求服务器获取本地的用户信息
//            txt_groupmemer_list.setText(list1.size() + "观看");
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void GetGroupMessageExt_response(String s) {
        closeLoadingProgressDialog();
        //数据的解析 //获取历史消息
        try {
            JSONObject jsonObject = new JSONObject(s);

            int e1 = jsonObject.getInt("e1");
            if (e1 == 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("d");
                Gson gson = new Gson();
                List<GroupMessageExtData> list = new ArrayList<>();
                try {
                    list = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<GroupMessageExtData>>() {
                    }.getType());
                    List<GroupMessageExtData> lastOne = dbUtils.findAll(Selector.from(GroupMessageExtData.class).orderBy("I", true).limit(1));
                    if (lastOne != null && lastOne.size() > 0) {
                        if (lastOne.get(0).getI().equals(list.get(0).getI())) {
                            list.remove(0);
                        }
                    }
                    for (GroupMessageExtData groupMessageExtData : list) {
                        dbUtils.save(groupMessageExtData);
                    }
                    if (onChatClick.equals("0")) {
                        PreferenceHelper.write(LiveChatActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONCHATCLICK + groupId, "1");
                        chatListDatas = dbUtils.findAll(Selector.from(GroupMessageExtData.class).orderBy("I", false).limit(20));
                        if (chatListDatas == null) {
                            chatListDatas = new ArrayList<>();
                        }
                        chatRowAdapter.setData(chatListDatas);
                        chatRowAdapter.notifyDataSetChanged();
                        if (chatListDatas.size() > 10) {
                            mListView.setPullLoadEnable(true);
                        } else {
                            mListView.setPullLoadEnable(false);
                        }
                        newTopID = chatListDatas.get(0).getI();
                        newbuttomID = chatListDatas.get(chatListDatas.size() - 1).getI();
                    } else {
                        gotobutton();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void excuteGroupMessageFailedCallBack(String s) {
        closeLoadingProgressDialog();
    }

    @Override
    public void getServer_error() {
        chatserver = PreferenceHelper.readString(LiveChatActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CHAT_SERVER);
        getMemberList = new GetMemberList();
        List<UGSV2> list = new ArrayList<UGSV2>();
        UGSV2 ugsv2 = new UGSV2();
        ugsv2.setGI(groupId);
        ugsv2.setV(0);
        list.add(ugsv2);
        getMemberList.setIMGMV(list);
        if (chatserver != null && !"".equals(chatserver)) {
            createConnect();
        } else {
            chatserver = "http://chat03.safetree.com.cn";
            createConnect();
        }
        imPersenter.member_list(distributorid, getMemberList);
//        tea_fragment.initData(2, distributorid);
//        stu_fragment.initData(1, distributorid);
    }

    Map<String, NickNameBean> hashMap = new HashMap<String, NickNameBean>();

    @Override
    public void getNickNameResponse(String s) {
        //根据聊天那边的id请求本地服务器获取用户信息
        try {
            closeLoadingProgressDialog();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            if (jsonArray != null && jsonArray.length() > 0) {
                JSONArray jsonArray1 = (JSONArray) jsonArray.get(0);
                hashMap.clear();
                if (jsonArray1 != null && jsonArray1.length() > 0) {
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        int id = ((JSONObject) jsonArray1.get(i)).getInt("ID");
                        NickNameBean nickNameBean = new NickNameBean();
                        String realName = ((JSONObject) jsonArray1.get(i)).getString("RealName");
                        int UserType = ((JSONObject) jsonArray1.get(i)).getInt("UserType");
                        int state = ((JSONObject) jsonArray1.get(i)).getInt("State");
                        nickNameBean.setRealName(realName);
                        nickNameBean.setUserType(UserType);
                        nickNameBean.setState(state);
                        nickNameBean.setID(id);
                        hashMap.put(String.valueOf(id), nickNameBean);
                    }
                }
            }
            chatRowAdapter.setGroupMemberList(hashMap);

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    @Override
    public void prohibitlistResponse(String s) {
        //禁言列表
        try {
            closeLoadingProgressDialog();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            if (jsonArray != null && jsonArray.length() > 0) {
                JSONArray jsonArray1 = (JSONArray) jsonArray.get(0);
                dataListTmp.clear();
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject jsoo = jsonArray1.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    if (map1.get("DistributorID").toString().equals(distributorid)) {
                        //如果是已经被禁言了的
//                        onBannedListener.setViewVisibility("visibility");
                        customechatmenu_one.isBanned(true);
                    }
                    dataListTmp.add(map1);
                }
                if (dataListTmp.size() == 0) {
                    //弹框中禁言列表为空的显示
                    im_nolist.setVisibility(View.VISIBLE);
                } else {
                    im_nolist.setVisibility(View.GONE);
                }
                if (bannedListAdapter1 != null) {
                    bannedListAdapter1.setData(dataListTmp);
                    bannedListAdapter1.notifyDataSetChanged();
                }

            }
        } catch (Exception e) {
            System.out.print(e);
        }

    }

    @Override
    public void ReleaseshutupResponse(String s) {
        try {
            closeLoadingProgressDialog();
            if (dialog_img_banned_icon != null) {
                dialog_img_banned_icon.setImageResource(R.mipmap.icon_banned);
            }
            prohibitlist_sign = TGmd5.getMD5(studyid);
            imPersenter.Prohibitlist(studyid, prohibitlist_sign);
            MyToast.show(this, "解除禁言成功！");
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    /**
     * 创建服务器连接
     */
    public void createConnect() {
        initialize();
        try {
            this.start(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private HubConnection connection;
    private HubProxy proxy;
    private ChatClient chatClient;
    private Logger logger;
    private int locateTimes;

    /**
     * 初始 化
     */
    private void initialize() {
        if (this.connection != null) {
            try {
                this.deleteConnect(true);
            } catch (Exception e) {
                this.logger.log(e.getLocalizedMessage(), LogLevel.Critical);
            }
        }
        this.locateTimes++;
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(GroupMessage.class,
                        new GroupMessageJsonConverter())
//                .registerTypeAdapter(SingleMessage.class,
//                        new SingleMessageJsonConverter())
                .registerTypeAdapter(MessageType.class,
                        new MessageTypeJsonConverter())
//                .registerTypeAdapter(AreaType.class,
//                        new AreaTypeJsonConverter())
//                .registerTypeAdapter(DataVersionTargetType.class,
//                        new DataVersionTargetTypeJsonConverter())
                .registerTypeAdapter(ActionNotification.class,
                        new ActionNotificationJsonConverter())
                .registerTypeAdapter(ActionType.class,
                        new ActionTypeJsonConverter());
//                .registerTypeAdapter(OfflineReason.class,
//                        new OfflineReasonConverter());
        Gson gson = gsonBuilder.create();
        connection = new HubConnection(chatserver);
        connection.setGson(gson);
        //access_token 导游id
        connection.getHeaders().put("access_token", distributorid);
//        connection.error(this);
        connection.stateChanged(stateChangedCallback);
//        connection.closed(handlerClosed);
        proxy = connection.createHubProxy("ChatHub");
        chatClient = new ChatClient(connection, proxy);
        this.logger = new ConsoleLogger();
    }

    public class ConsoleLogger implements Logger {

        @Override
        public void log(String arg0, LogLevel arg1) {
            if (arg0 == null) {
                System.out.println("arg0 is null");
            }
            System.out.println(arg0);
        }
    }

    /**
     * 监测到连接状态改变为未连接后执行重连
     */
    StateChangedCallback stateChangedCallback = new StateChangedCallback() {
        @Override
        public void stateChanged(ConnectionState oldState,
                                 ConnectionState newState) {
            LogUtils.e("StateChangedCallback链接变化");
            Log.e("hsakdfha", "连接变化了00000 ");
            if (newState == ConnectionState.Disconnected
                    || newState == ConnectionState.Reconnecting) {
                reconnect();
            }
            // 发送状态变化的广播

            String state = "";
            if (newState == ConnectionState.Connecting) {
//                GloableParams.SigRConnecting = true;
                state = "正在连接..";
                Log.e("hsakdfha", "正在连接 ");
                LogUtils.e("正在连接");
                tv_title.setText("正在连接");
            } else if (newState == ConnectionState.Disconnected) {
//                GloableParams.SigRConnecting = false;
                state = "已断开";
                Log.e("hsakdfha", "已断开 ");
                LogUtils.e("已断开");
                tv_title.setText("已断开");
            } else if (newState == ConnectionState.Reconnecting) {
//                state = "正在重连";
//                LogUtils.e("正在重连");
//                tv_title.setText("正在重连");
                Log.e("hsakdfha", "正在重连 ");
//                GloableParams.SigRConnecting = true;
            } else if (newState == ConnectionState.Connected) {
//                GloableParams.SigRConnecting = false;
//                GloableParams.SigRConnectOk = true;
                //定义字段判断是否成功
                LogUtils.e("链接成功");
                Log.e("hsakdfha", "链接成功 ");
                Log.e("hkfas", "------" + theme);
                tv_title.setText(theme);
            }
        }
    };
    private boolean IsReconnection = false;
    public static boolean CanReconnection = true;
    private boolean started;

    public synchronized void reconnect() {
        Log.i("IsReconnection", this.IsReconnection + "");
        if (IsReconnection || !CanReconnection) {
            return;
        }
        if (!SystemUtils.checkNet(LiveChatActivity.this)) {
            return;
        }
        this.started = false;
        this.IsReconnection = true;
        this.locateTimes = 0;
        this.initialize();
        boolean connected = false;
        int tryTimes = 0;
        while (!connected) {
            try {
                if (tryTimes < 16) {
                    tryTimes++;
                } else {
                    // TODO:重试超过16次则不再重试,提示用户网络异常
                    break;
                }
                this.start();
                IsReconnection = false;
                connected = true;
            } catch (Exception e) {
                try {
                    Thread.sleep((long) Math.pow(2, tryTimes));
                } catch (InterruptedException e1) {
                }
            }
        }
    }

    /**
     * 启动客户端的链接
     */
    public void start() throws ExecutionException, InterruptedException {
        this.start(false);
    }

    private int errorTimes;
    private Object syncRoot = null;

    /**
     * 启动客户端的链接
     *
     * @param reconnect 是否是重连调用
     */
    private synchronized void start(boolean reconnect)
            throws ExecutionException, InterruptedException {
        // ----------耗时的操作，开始---------------
        Log.i("ChatClient", "start");
        if (!reconnect) {
            synchronized (syncRoot) {
                if (started) {
                    return;
                }
                started = true;
            }
        }
        if (SystemUtils.checkNet(LiveChatActivity.this)) {
            HttpClientTransport transport = new AutomaticTransport(logger);
            SignalRFuture<Void> awaitConnection = connection.start();
            int k = 0;
            boolean isConnection = false;
            while (k < 60) {
                synchronized (connection) {
                    if (connection.getState() == ConnectionState.Connected) {
                        isConnection = true;
                        break;
                    }
                }
                try {
                    k++;
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (connection.getState() == ConnectionState.Connected) {
                    awaitConnection.get();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            errorTimes = 0;
        }
    }

    public synchronized void deleteConnect(boolean canReconnection) {
        Log.i("singleR", "断开链接");
        try {
            // 新加的if语句
            if (null != connection) {
                this.chatClient = null;
                this.proxy = null;
                if (connection != null) {
                    connection.disconnect();
                    connection.stop();
                }
                connection = null;
                this.started = false;
                IsReconnection = true;
                CanReconnection = canReconnection;
            }
        } catch (Exception e) {
        }
    }

    /******************************发送语音有关操作************************************/
    /**
     * 触摸事件
     * 发送语音按钮
     *
     * @param v
     * @param event
     * @return
     */

    private boolean msg_flag = true;
    private boolean intercepter;
    private long startTime;


    private int count_down;
    private boolean isshow_Countdown;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    handler.sendEmptyMessageDelayed(1, 1000);
                    if (System.currentTimeMillis() - startTime >= 10000) {
                        isshow_Countdown = true;
                        /*if (isshow_Countdown) {
                            txt_countdown.setVisibility(View.VISIBLE);
                            ll_record_view.setVisibility(View.GONE);
                        } else {
                            txt_countdown.setVisibility(View.GONE);
                            ll_record_view.setVisibility(View.VISIBLE);
                        }
                        txt_countdown.setText(String.valueOf(count_down));*/
                        count_down--;

                    }
                    break;
            }
        }
    };


    private CountDownTimer countDownTimer1;
    private CountDownTimer countDownTimer;
    private AnimationDrawable animationDrawable;

    /**
     * 在主线程接受 eventbus发送的事件
     *
     * @param action
     * @Subscribe 这个注解必须加上
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIEvent(String action) {
        // 如果接受到注销的事件 就销毁自己
        //长按 录音按下
        if ("luyinanxia".equals(action)) {
            stopRecoding = 0;
//            v.setPressed(true);
            countDownTimer1 = new CountDownTimer(121000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long miao = 120 - millisUntilFinished / 1000;

                    customechatmenu_one.getChanganProgress().setVisibility(View.VISIBLE);
                    customechatmenu_one.getLlbottomVoiceText().setVisibility(View.GONE);
                    customechatmenu_one.getChanganProgress().setProgress((int) (millisUntilFinished / 1000));
                    customechatmenu_one.getDaojishiNum().setText("(" + miao + "s)");
                    /*if (teacherDistributorID.equals(distributorid)) {
                        tea_fragment.getCustomeMenu().getChanganProgress().setVisibility(View.VISIBLE);
                        tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.GONE);
                        tea_fragment.getCustomeMenu().getChanganProgress().setProgress((int) (millisUntilFinished / 1000));
                        tea_fragment.getCustomeMenu().getDaojishiNum().setText("(" + miao + "s)");
                    } else {
                        stu_fragment.getCustomeMenu().getChanganProgress().setVisibility(View.VISIBLE);
                        stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.GONE);
                        stu_fragment.getCustomeMenu().getChanganProgress().setProgress((int) (millisUntilFinished / 1000));
                        stu_fragment.getCustomeMenu().getDaojishiNum().setText("(" + miao + "s)");
                    }*/
                  /*  stu_fragment.getCustomeMenu().getChanganProgress().setProgress((int) (millisUntilFinished/1000));
                    stu_fragment.getCustomeMenu().getDaojishi().setText("松开发送("+miao+"s)");*/
                }

                @Override
                public void onFinish() {
                    isshow_Countdown = false;
                    if (!intercepter) {
                        customechatmenu_one.getLlbottomVoiceText().setVisibility(View.VISIBLE);
                        /*if (teacherDistributorID.equals(distributorid)) {
                            tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                        } else {
                            stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                        }*/

                        uploadSendVoice(true, false);
                    }
                }
            };
            countDownTimer1.start();


            intercepter = false;
            count_down = 10;
//            inclue_layout.setVisibility(View.VISIBLE);
            showReleaseToCancelHint();
            startRecording(true);
            updateMicStatus();
            startTime = System.currentTimeMillis();
            handler.sendEmptyMessage(1);

        }
        //长按 录音移动
        if ("luyinyidong".equals(action)) {

            if (msg_flag) {
                msg_flag = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!intercepter) {
                            uploadSendVoice(false, false);
                        }
                    }
                }, 120000);
            }
            /*if (yY < -100) {
                ll_record_view.setVisibility(View.GONE);
                txt_countdown.setVisibility(View.GONE);
                img_record_hint.setVisibility(View.VISIBLE);
                txt_record_hint.setText("松开手指 取消发送");
//                intercepter=true;
            } else {
                if (isshow_Countdown) {
                    txt_countdown.setVisibility(View.VISIBLE);
                    ll_record_view.setVisibility(View.GONE);
                } else {
                    txt_countdown.setVisibility(View.GONE);
                    ll_record_view.setVisibility(View.VISIBLE);
                }
                img_record_hint.setVisibility(View.GONE);
                txt_record_hint.setText("手指上滑 取消发送");
                //倒计时
            }*/
        }
        //长按 录音抬起
        if ("luyintaiqi".equals(action)) {
            msg_flag = true;
            if (countDownTimer1 != null) {
                countDownTimer1.cancel();
            }
            isshow_Countdown = false;
            if (!intercepter) {
                customechatmenu_one.setisUseVoic();
                customechatmenu_one.getLlbottomVoiceText().setVisibility(View.VISIBLE);
//                tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
//                stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                uploadSendVoice(false, false);
            }
        }
        if ("changanvoicecancel".equals(action)) {
            //上滑取消
            msg_flag = true;
            isshow_Countdown = false;
            if (!intercepter) {
                if (countDownTimer1 != null) {
                    countDownTimer1.cancel();
                }
                stopRecoding = 0;
                intercepter = true;
                customechatmenu_one.getLlbottomVoiceText().setVisibility(View.VISIBLE);
                customechatmenu_one.HideView();
                /*tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                stu_fragment.getCustomeMenu().HideView();
                tea_fragment.getCustomeMenu().HideView();*/
                stopRecoding();
            }
        }

        if ("oneclickvoicecancel".equals(action)) {
            msg_flag = true;
            isshow_Countdown = false;
            if (!intercepter) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                stopRecoding = 0;
                /*stu_fragment.cannot_other.setVisibility(View.GONE);
                tea_fragment.cannot_other.setVisibility(View.GONE);
                top_cannot_other.setVisibility(View.GONE);*/
                customechatmenu_one.getLlbottomVoiceText().setVisibility(View.VISIBLE);
                /*tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                rl_teacher.setClickable(true);
                rl_student.setClickable(true);*/
                stopRecoding();
            }
        }


        //第一次点击
        if ("oneclickvoice".equals(action)) {
            stopRecoding = 0;
            intercepter = false;
            customechatmenu_one.getLlbottomVoiceText().setVisibility(View.GONE);
            /*if (teacherDistributorID.equals(distributorid)) {
                tea_fragment.cannot_other.setVisibility(View.VISIBLE);//语音开始不能点其他的位子
                tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.GONE);
            } else {
                stu_fragment.cannot_other.setVisibility(View.VISIBLE);
                stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.GONE);
            }*/


            /*top_cannot_other.setVisibility(View.VISIBLE);
            rl_teacher.setClickable(false);
            rl_student.setClickable(false);*/
            customechatmenu_one.getShortMiao().setText("(120s)");
            customechatmenu_one.getDanjiProgress().setVisibility(View.VISIBLE);
            ImageView danjiAnimation = customechatmenu_one.getDanjiAnimation();
            danjiAnimation.setImageResource(R.drawable.animationlist);
            animationDrawable = (AnimationDrawable) danjiAnimation.getDrawable();
            animationDrawable.setOneShot(false);
            animationDrawable.start();
            /*if (teacherDistributorID.equals(distributorid)) {
                tea_fragment.getCustomeMenu().getShortMiao().setText("(120s)");
                tea_fragment.getCustomeMenu().getDanjiProgress().setVisibility(View.VISIBLE);
            } else {
                stu_fragment.getCustomeMenu().getShortMiao().setText("(120s)");
                stu_fragment.getCustomeMenu().getDanjiProgress().setVisibility(View.VISIBLE);
                ImageView danjiAnimation = stu_fragment.getCustomeMenu().getDanjiAnimation();
                danjiAnimation.setImageResource(R.drawable.animationlist);
                animationDrawable = (AnimationDrawable) danjiAnimation.getDrawable();
                animationDrawable.setOneShot(false);
                animationDrawable.start();
            }*/
            /*stu_fragment.getCustomeMenu().getShortMiao().setText("(120s)");
            stu_fragment.getCustomeMenu().getDanjiProgress().setVisibility(View.VISIBLE);*/
            count_down = 10;
//            inclue_layout.setVisibility(View.VISIBLE);
            startRecording(false);
            updateMicStatus();
            startTime = System.currentTimeMillis();
            handler.sendEmptyMessage(1);
            countDownTimer = new CountDownTimer(120000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long miao = millisUntilFinished / 1000;
                    customechatmenu_one.getDanjiProgress().setProgress((int) (millisUntilFinished / 1000));
                    customechatmenu_one.getShortMiao().setText("(" + miao + "s)");
                    /*if (teacherDistributorID.equals(distributorid)) {
                        tea_fragment.getCustomeMenu().getDanjiProgress().setProgress((int) (millisUntilFinished / 1000));
                        tea_fragment.getCustomeMenu().getShortMiao().setText("(" + miao + "s)");
                    } else {
                        stu_fragment.getCustomeMenu().getDanjiProgress().setProgress((int) (millisUntilFinished / 1000));
                        stu_fragment.getCustomeMenu().getShortMiao().setText("(" + miao + "s)");
                    }*/
                    /*stu_fragment.getCustomeMenu().getDanjiProgress().setProgress((int) (millisUntilFinished/1000));
                    stu_fragment.getCustomeMenu().getShortMiao().setText("("+miao+"s)");*/
                }

                @Override
                public void onFinish() {
                    isshow_Countdown = false;
                    if (!intercepter) {
                        uploadSendVoice(true, false);
                        /*rl_teacher.setClickable(true);
                        rl_student.setClickable(true);
                        top_cannot_other.setVisibility(View.GONE);
                        stu_fragment.cannot_other.setVisibility(View.GONE);
                        tea_fragment.cannot_other.setVisibility(View.GONE);*/
                        customechatmenu_one.getLlbottomVoiceText().setVisibility(View.VISIBLE);
                        /*tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                        stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);*/
                    }
                }
            };
            countDownTimer.start();
        }
        if ("zantingclickvoice".equals(action)) {
            //暂停的
            msg_flag = true;
            isshow_Countdown = false;
            if (!intercepter) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
//                voiceRecorder.discardRecording();
//                stopRecoding();
                if (animationDrawable != null) {
                    animationDrawable.stop();
                }
                stopRecoding = stopRecoding();
            }
        }
        //第二次点击
        if ("twoclickvoice".equals(action)) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            if (animationDrawable != null) {
                animationDrawable.stop();
            }
            isshow_Countdown = false;
            if (!intercepter) {
               /* rl_teacher.setClickable(true);
                rl_student.setClickable(true);
                top_cannot_other.setVisibility(View.GONE);
                stu_fragment.cannot_other.setVisibility(View.GONE);
                tea_fragment.cannot_other.setVisibility(View.GONE);*/
                customechatmenu_one.setisUseVoic();
                customechatmenu_one.getLlbottomVoiceText().setVisibility(View.VISIBLE);
                /*tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);*/
                uploadSendVoice(true, true);
            }
        }

    }

    public void showMoveUpToCancelHint() {
        customechatmenu_one.getTitlemenu().setVisibility(View.VISIBLE);
        customechatmenu_one.getTitleLongpass().setVisibility(View.GONE);
        /*if (teacherDistributorID.equals(distributorid)) {
            tea_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.VISIBLE);
            tea_fragment.getCustomeMenu().getTitleLongpass().setVisibility(View.GONE);
        } else {
            stu_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.VISIBLE);
            stu_fragment.getCustomeMenu().getTitleLongpass().setVisibility(View.GONE);
        }*/
    }

    public void showMoveUpCancelHint2() {
        customechatmenu_one.getTitlemenu().setVisibility(View.VISIBLE);
        customechatmenu_one.getTitleShortPass().setVisibility(View.GONE);
        /*if (teacherDistributorID.equals(distributorid)) {
            tea_fragment.getCustomeMenu().getTitleShortPass().setVisibility(View.GONE);
            tea_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.VISIBLE);
        } else {
            stu_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.VISIBLE);
            stu_fragment.getCustomeMenu().getTitleShortPass().setVisibility(View.GONE);
        }*/
    }

    private void uploadSendVoice(boolean shorta, boolean isstop) {
        intercepter = true;
//        inclue_layout.setVisibility(View.GONE);
        handler.removeCallbacksAndMessages(null);
        showMoveUpToCancelHint();
        if (shorta) {
            showMoveUpCancelHint2();

        }
        try {
            if (!isstop) {
                stopRecoding = stopRecoding();
            }
            if (stopRecoding > 0) {
                // 获取语音时长与地址路径
                LogUtils.e("录音时长==" + stopRecoding);
                LogUtils.e("路径====" + getVoiceFilePath());
                voice_length = stopRecoding;
                try {
                    //发送语音消息
                    GroupMessageExtData groupMessageExtData = new GroupMessageExtData();
                    groupMessageExtData.setSI(AESUtils.method3(distributorid).trim());
                    groupMessageExtData.setU(voiceRecorder.getVoiceFilePath());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    groupMessageExtData.setCT(simpleDateFormat.format(new Date()));
                    groupMessageExtData.setMT(2);
                    groupMessageExtData.setL(stopRecoding);
//                    ListView adapterView;
                    /*try {
                        dbUtils.save(groupMessageExtData);//先保存到数据库
                        newbuttomID = groupMessageExtData.getI();//把上拉加载的id给改变
                    } catch (DbException e) {
                        e.printStackTrace();
                    }*/
                    if ( !chatRowAdapter.getData().contains(groupMessageExtData)){
                        chatRowAdapter.getData().add(groupMessageExtData);
                        chatRowAdapter.notifyDataSetChanged();
                        mListView.setSelection(mListView.getCount() - 1);
                    }

                    /*if (teacherDistributorID.equals(distributorid)) {
                        TDirectChatRowAdapter adapter = tea_fragment.directChatRowAdapter;
                        adapterView = tea_fragment.listView;
                        adapter.getData().add(groupMessageExtData);
                        adapter.notifyDataSetChanged();
                    } else {
                        DirectChatRowAdapter adapter = stu_fragment.directChatRowAdapter;
                        adapterView = stu_fragment.listView;
                        adapter.getData().add(groupMessageExtData);
                        adapter.notifyDataSetChanged();
                    }*/

//                    adapterView.setSelection(adapterView.getCount() - 1);
                    XutilsUploadVoice(voiceRecorder.getVoiceFilePath());
                } catch (Exception e) {
                    e.printStackTrace();
                    MyToast.show(LiveChatActivity.this, "发送失败,连接异常!");
                }
            } else if (stopRecoding == EMError.FILE_INVALID) {
                MyToast.show(LiveChatActivity.this, "录音失败!");
            } else {
                MyToast.show(LiveChatActivity.this, "录音时间太短!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyToast.show(LiveChatActivity.this, "录音失败,查看权限是否打开");
        }
    }

    public void showReleaseToCancelHint() {
        customechatmenu_one.getTitlemenu().setVisibility(View.GONE);
        customechatmenu_one.getTitleLongpass().setVisibility(View.VISIBLE);
        /*if (teacherDistributorID.equals(distributorid)) {
            tea_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.GONE);
            tea_fragment.getCustomeMenu().getTitleLongpass().setVisibility(View.VISIBLE);
//            tea_fragment.getCustomeMenu().getVoiceButton().setText("松开 发送");
        } else {
            stu_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.GONE);
            stu_fragment.getCustomeMenu().getTitleLongpass().setVisibility(View.VISIBLE);
//            stu_fragment.getCustomeMenu().getVoiceButton().setText("松开 发送");
        }*/
    }

    public void startRecording(boolean islong) {
        /**
         * 判断SD 卡 是否存在
         */
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            MyToast.show(LiveChatActivity.this, "没有SD卡");
            return;
        }
        try {
            wakeLock.acquire();
            if (islong) {
                showReleaseToCancelHint();
            }

            voiceRecorder.startRecording(LiveChatActivity.this, distributorid);
        } catch (Exception e) {
            e.printStackTrace();
            if (wakeLock.isHeld())
                wakeLock.release();
            if (voiceRecorder != null)
                voiceRecorder.discardRecording();
            MyToast.show(LiveChatActivity.this, "录音失败，请重试！");


            //-----------------------------捕获异常
            String androidId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            ConnectivityManager connectionManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            //获取wifi服务
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            //判断wifi是否开启
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();

            PackageManager manager;

            PackageInfo info = null;

            manager = getApplicationContext().getPackageManager();

            try {

                info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);

            } catch (PackageManager.NameNotFoundException m) {

// TODO Auto-generated catch block

                m.printStackTrace();

            }

            StringBuilder sb = new StringBuilder();
            String diviceName = GetDeviceName();
            sb.append("手机型号 : " + diviceName + ";  ");
//                    sb.append("DeviceId(IMEI) = " + tm.getDeviceId()+";");
            sb.append("手机号 : " + tm.getLine1Number() + ";  ");
            sb.append("网络类型 = " + tm.getNetworkOperatorName() + ";  ");
            sb.append("网络 = " + networkInfo.getType() + ";  ");
            sb.append("SimSerialNumber = " + tm.getSimSerialNumber() + ";  ");
//                    sb.append("SubscriberId(IMSI) = " + tm.getSubscriberId()+";");
            sb.append("内存:" + getMemoryInfo(Environment.getDataDirectory(), getApplicationContext()) + ";  ");
            sb.append("IP地址:" + intToIp(ipAddress) + ";  ");
            sb.append("app版本:" + info.versionCode + ";  ");
            sb.append("SDK版本:" + android.os.Build.VERSION.SDK + ";  ");
            sb.append("系统版本:" + android.os.Build.VERSION.RELEASE + ";  ");


            String distributorid = PreferenceHelper.readString(getApplicationContext(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
            String sign = TGmd5.getMD5(distributorid + 2 + androidId + "客户端异常录音失败" + sb.toString() + e.toString());
            errorLogPresenter.apperrorlog(distributorid, 2, androidId, "客户端异常录音失败", sb.toString(), e.toString(), sign);


            return;
        }
    }

    /**
     * 根据路径获取内存状态
     *
     * @param path
     * @return
     */
    private String getMemoryInfo(File path, Context context) {
        // 获得一个磁盘状态对象
        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSize();   // 获得一个扇区的大小

        long totalBlocks = stat.getBlockCount();    // 获得扇区的总数

        long availableBlocks = stat.getAvailableBlocks();   // 获得可用的扇区数量

        // 总空间
        String totalMemory = Formatter.formatFileSize(context, totalBlocks * blockSize);
        // 可用空间
        String availableMemory = Formatter.formatFileSize(context, availableBlocks * blockSize);

        return "总空间: " + totalMemory + " 可用空间: " + availableMemory;
    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    public String getVoiceFilePath() {
        return voiceRecorder.getVoiceFilePath();
    }

    private void updateMicStatus() {
        if (voiceRecorder != null) {
            int ratio = voiceRecorder.getRatio();
            int db = 0;// 分贝
            if (ratio > -1) {
                if (ratio > 1)
                    db = (int) (20 * Math.log10(ratio));
                System.out.println("分贝值：" + db + "     " + Math.log10(ratio));
                //我对着手机说话声音最大的时候，db达到了35左右，
                mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
                //所以除了2，为的就是对应14张图片
                mHandler.sendEmptyMessage(db / 2);
            }
        }
    }

    private int SPACE = 100;// 间隔取样时间
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };
    private final Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            //根据mHandler发送what的大小决定话筒的图片是哪一张
            //说话声音越大,发送过来what值越大
            if (what > 4) {
                what = 4;
            }
//            img_record.setImageDrawable(micImages[what]);
        }
    };

    public int stopRecoding() {
        if (wakeLock.isHeld())
            wakeLock.release();
        return voiceRecorder.stopRecoding();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatRowAdapter.onStopVoice();
    }
}
