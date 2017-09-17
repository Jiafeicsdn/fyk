package com.lvgou.distribution.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.media.Image;
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
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.BannedListAdapter;
import com.lvgou.distribution.adapter.BannedListAdapter1;
import com.lvgou.distribution.adapter.DirectChatRowAdapter;
import com.lvgou.distribution.adapter.TDirectChatRowAdapter;
import com.lvgou.distribution.bean.AddUser;
import com.lvgou.distribution.bean.BannedUserBean;
import com.lvgou.distribution.bean.GetMemberList;
import com.lvgou.distribution.bean.GroupMember;
import com.lvgou.distribution.bean.GroupMessageExtData;
import com.lvgou.distribution.bean.JoinChatGroupBean;
import com.lvgou.distribution.bean.MoveUp;
import com.lvgou.distribution.bean.NickNameBean;
import com.lvgou.distribution.bean.UGSV2;
import com.lvgou.distribution.constants.MessageType1;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.driect.ChatClient;
import com.lvgou.distribution.driect.OnBannedListener;
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
import com.lvgou.distribution.entity.ChatRowMessageEntity;
import com.lvgou.distribution.entity.GroupMessageEntity;
import com.lvgou.distribution.fragment.StudentFragment;
import com.lvgou.distribution.fragment.TeacherFragment;
import com.lvgou.distribution.fragment.TeacherFragment.OnArticleSelectedListener;
import com.lvgou.distribution.luban.Luban;
import com.lvgou.distribution.presenter.ErrorLogPresenter;
import com.lvgou.distribution.presenter.IMPersenter;
import com.lvgou.distribution.response.GetServerResponse;
import com.lvgou.distribution.response.UploadVideoResponse;
import com.lvgou.distribution.utils.AESUtils;
import com.lvgou.distribution.utils.CameraUtil;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.PathUtil;
import com.lvgou.distribution.utils.PermissionManager;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.BaseView1;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.CircleProgress;
import com.lvgou.distribution.view.CustomeChatMenu;
import com.lvgou.distribution.view.IMView;
import com.lvgou.distribution.view.MyListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.xdroid.common.utils.SystemUtils;
import com.lvgou.distribution.utils.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
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
import java.util.Timer;
import java.util.TimerTask;
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
 * Created by Administrator on 2016/8/9 0009.
 * 直播间
 */
public class LiveDirectActivity extends BaseActivity implements IMView, StudentFragment.OnArticleSelectedListener, OnArticleSelectedListener, OnBottomMenuClickListener, View.OnTouchListener, ErrorCallback, BaseView1 {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_teacher)
    private RelativeLayout rl_teacher;
    @ViewInject(R.id.rl_student)
    private RelativeLayout rl_student;
    @ViewInject(R.id.tv_teacher)
    private TextView tv_teacher;
    @ViewInject(R.id.tv_student)
    private TextView tv_studnet;
    @ViewInject(R.id.view_teacher)
    private View view_teacher;
    @ViewInject(R.id.view_student)
    private View view_student;
    @ViewInject(R.id.id_content)
    private FrameLayout id_content;
    @ViewInject(R.id.txt_groupmemer_list)
    private TextView txt_groupmemer_list;
    @ViewInject(R.id.img_record)
    private ImageView img_record;
    @ViewInject(R.id.inclue_layout)
    private LinearLayout inclue_layout;
    @ViewInject(R.id.txt_teach_noread)
    private TextView txt_teach_noread;

    @ViewInject(R.id.ll_record_view)
    private LinearLayout ll_record_view;
    @ViewInject(R.id.img_record_hint)
    private ImageView img_record_hint;
    @ViewInject(R.id.txt_record_hint)
    private TextView txt_record_hint;
    @ViewInject(R.id.txt_countdown)
    private TextView txt_countdown;
    @ViewInject(R.id.img_banned)
    private ImageView img_banned;

    //存储很多张话筒图片的数组
    private Drawable[] micImages;

    protected PowerManager.WakeLock wakeLock;// 屏幕 管理工具

    List<GroupMember> list1;//在线群成员列表
    private HubConnection connection;
    private HubProxy proxy;
    private ChatClient chatClient;

    protected EaseVoiceRecorder voiceRecorder;
    private List<ChatRowMessageEntity> chatRowMessageEntities;

    private String distributorid = "";
    private String sendServer = "http://115.236.185.26:8083/";

    private String groupId = "";//群组id
    private static String theme = "";//课程名称
    private String courseState="";//课程类型，如果是已结束的课程不让进行聊天，只能看聊天记录
    /**
     * 当前的图片路径
     */
    private String imagePath = null;
    private final static int REQUEST_CODE_GALLERY = 0x33;
    private final static int REQUEST_CODE_CAMERA = 0x44;
    protected static final int REQUEST_CODE_MAP = 1;
    private MessageType1 text;
    private TeacherFragment tea_fragment;
    private StudentFragment stu_fragment;
    private String userName;
    IMPersenter imPersenter;
    private String chatserver = "chat03.safetree.com.cn";
    private boolean started;
    private int errorTimes;
    private boolean IsReconnection = false;
    public static boolean CanReconnection = true;
    private int locateTimes;
    private Logger logger;
    MyReceiver myReceiver;
    MyReceiver myReceiver1;
    IntentFilter intentFilter, intentFilter2, intentFilter3, intentFilter4, intentFilter1;
    NetworkChangeReceive networkChangeReceive;
    GetMemberList getMemberList;
    boolean isfirst = true;
    GetGroupMemberReceiver getGroupMemberReceiver;
    String teacherDistributorID = "";//讲师id
    private int noreadNumer = 0;
    private int teacher_noreadnumber = 0;
    private String select_str = "tea";
    private int voice_length;
    public static DbUtils dbUtils;
    Map<String, NickNameBean> hashMap = new HashMap<String, NickNameBean>();
    private Object syncRoot = null;
    private List<GroupMessageExtData> MsgList;
    private boolean isSendSucces = true;
    private BannedListAdapter1 bannedListAdapter1;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownTimer1;
    private ImageView im_nolist;
    private int stopRecoding;
    private AnimationDrawable animationDrawable;
    //    private Timer timer;

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

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction.equals("com.distribution.group.message")) {
                GroupMessageExtData message = (GroupMessageExtData) intent.getSerializableExtra("message");
                String si = message.getSI();
//                GroupMessageEntity groupMessageEntity = new GroupMessageEntity();
//                groupMessageEntity.setId(message.getI());
//                groupMessageEntity.setSenderId(message.getSI());
//                groupMessageEntity.setGroupId(message.getGI());
//                groupMessageEntity.setCreationTime(message.getCT());
//                groupMessageEntity.setVoicetime(message.getL());
//                groupMessageEntity.setUrl(message.getU());
//                groupMessageEntity.setMessageType(message.getMT());
//                groupMessageEntity.setCoverImage(message.getCI());
//                groupMessageEntity.setO(message.getO());
//                groupMessageEntity.setThumbnailUrl(message.getT());
//                groupMessageEntity.setContent(message.getC());
//                try {
//                    dbUtils.save(groupMessageEntity);
//                } catch (DbException e) {
//                    e.printStackTrace();
//                }
                try {
                    String si_id = AESUtils.Decrypt(si);
                    if (si_id.equals(teacherDistributorID)) {
                        teacher_noreadnumber++;
                        if (select_str.equals("stu")) {
                            txt_teach_noread.setVisibility(View.VISIBLE);
                            txt_teach_noread.setText(String.valueOf(teacher_noreadnumber));
                        }
                        tea_fragment.directChatRowAdapter.getData().add(message);
                        tea_fragment.directChatRowAdapter.notifyDataSetChanged();
                        boolean flag = isListViewReachBottomEdge(tea_fragment.listView);
                        if (flag) {
                            teacher_noreadnumber = 0;
                            tea_fragment.getTxt_message_numer().setVisibility(View.GONE);
                            tea_fragment.listView.setSelection(tea_fragment.listView.getCount() - 1);
                        } else {
                            tea_fragment.listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
                            tea_fragment.getTxt_message_numer().setText(String.valueOf(teacher_noreadnumber));
                            tea_fragment.getTxt_message_numer().setVisibility(View.VISIBLE);
                        }

//                        if (select_str.equals("stu")) {
//                            tea_fragment.getTxt_message_numer().setVisibility(View.GONE);
//                        }
                    } else {
                        stu_fragment.directChatRowAdapter.getData().add(message);
                        stu_fragment.directChatRowAdapter.notifyDataSetChanged();
                        boolean flag = isListViewReachBottomEdge(stu_fragment.listView);
                        if (flag) {
                            stu_fragment.getTxt_message_numer().setVisibility(View.GONE);
                            stu_fragment.listView.setSelection(stu_fragment.listView.getCount() - 1);
                        } else {
                            noreadNumer++;
                            stu_fragment.getTxt_message_numer().setText(String.valueOf(noreadNumer));
                            stu_fragment.getTxt_message_numer().setVisibility(View.VISIBLE);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (intentAction.equals("com.distribution.single.message")) {
                GroupMessageExtData message = (GroupMessageExtData) intent.getSerializableExtra("message");
                if ((message.getC() + "").equals("IMChatGroupLockPersonStringKey")) {
                    onBannedListener.setViewVisibility("visibility");
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
                    }
//                    CustomeChatMenu.onBottomMenuClickListener
                } else if ((message.getC() + "").equals("IMChatGroupUNLockPersonStringKey")) {
                    onBannedListener.setViewVisibility("gone");

                }
            }
        }
    }

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

//    class HomeKeyEventBroadCastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            customeChatMenu.hidekeyboard();
//        }
//    }

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

    private View top_cannot_other;


    @Override
    public void onSaveInstanceState(Bundle outState) {
//界面销毁之前保存数据
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
//执行于onStart()之后，回复之前保存过的数据，其实可以不要，因为oncreate中已经获取过了
        super.onRestoreInstanceState(savedInstanceState);
    }

    private ErrorLogPresenter errorLogPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_direct_seeding);
        new CustomeChatMenu(this);
        ViewUtils.inject(this);
        errorLogPresenter = new ErrorLogPresenter(this);
        top_cannot_other = findViewById(R.id.top_cannot_other);
        top_cannot_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //注册eventbus
        EventBus.getDefault().register(this);
        MsgList = new ArrayList<>();
        init();
        syncRoot = new Object();
        CustomeChatMenu.setOnBottomMenuClickListener(this);
        tv_title.setText("欢迎进入讨论组...");
        teacherDistributorID = getIntent().getStringExtra("teacherId");
        groupId = getIntent().getStringExtra("GId");
        Log.e("jhakfdhas", "*******groupId****** " + groupId);
        theme = getIntent().getStringExtra("theme");
        studyid = String.valueOf(getIntent().getIntExtra("ktID", 0));
        kdbdistributorid = String.valueOf(getIntent().getIntExtra("KDBDistributorID", 0));
        courseState = getIntent().getStringExtra("courseState");
        distributorid = PreferenceHelper.readString(LiveDirectActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (String.valueOf(kdbdistributorid).equals(distributorid)) {
            img_banned.setVisibility(View.VISIBLE);
        } else {
            img_banned.setVisibility(View.GONE);
        }
        userName = PreferenceHelper.readString(LiveDirectActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ACCOUNT);


        showLoadingProgressDialog(this, "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeLoadingProgressDialog();
            }
        }, 30000);
        voiceRecorder = new EaseVoiceRecorder();
        wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK, "project");
        tea_fragment = new TeacherFragment();
        stu_fragment = new StudentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("studyid", studyid);
        bundle.putString("teacherDistributorID", teacherDistributorID);
        bundle.putString("distributorid", distributorid);
        stu_fragment.setArguments(bundle);
        tea_fragment.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.id_content, tea_fragment);
        tx.add(R.id.id_content, stu_fragment).hide(stu_fragment);
        tx.commit();
        //调用新增用户接口
        imPersenter = new IMPersenter(this);
        Log.e("hkasdfhk", "------userName-------" + userName);
        imPersenter.addUser(userName);

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


//        intentFilter4 = new IntentFilter();
/*
        intentFilter4.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceive = new NetworkChangeReceive();
        registerReceiver(networkChangeReceive, intentFilter4);*/


        getGroupMemberReceiver = new GetGroupMemberReceiver();
        intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.distribution.group.member.list");
        registerReceiver(getGroupMemberReceiver, intentFilter3);

//        homeKeyEventReceiver = new HomeKeyEventBroadCastReceiver();
//        registerReceiver(homeKeyEventReceiver, new IntentFilter(
//                Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    private void createDatabase() {
        String sdpath = Environment.getExternalStorageDirectory() + "/tugou";
        DbUtils.DaoConfig daoConfig = new DbUtils.DaoConfig(this);
        daoConfig.setDbVersion(1);
        daoConfig.setDbName("IM_TUGOU");
        daoConfig.setDbDir(sdpath);
        dbUtils = DbUtils.create(daoConfig);
    }

    /**
     * 数据的插入
     */
    public void insert() {
//        try {
//            dbUtils.saveOrUpdate(groupMessageEntity);
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 数据查询
     */
    public void find() {
        try {
            // 查找表中的所有数据
            List<GroupMessageEntity> list = dbUtils.findAll(GroupMessageEntity.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     */
    public void delete() {

    }

    /**
     * 数据更新
     */
    public void update() {

    }

    private String prohibitlist_sign;
    private String kdbdistributorid; //课代表id
    private String studyid; //课堂ID

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


    }

    /**
     * 禁言弹框
     */
//    private Dialog banned_dialog;
    ImageView dialog_img_banned_icon;

    public void showBannedDialog(String uid) {
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
                            CustomeChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupUNLockPersonStringKey", String.valueOf(nickNameBean.getID()));
                            isbanned = true;
                            break;
                        }
                        /*if (bannedUserBeanList.get(i).getDistributorID() == nickNameBean.getID()) {

                        }*/
                    }
                    if (!isbanned) {
                        imPersenter.Bannedtopost(kdbdistributorid, studyid, String.valueOf(nickNameBean.getID()), banned_sign);
                        //禁言开始
                        CustomeChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupLockPersonStringKey", String.valueOf(nickNameBean.getID()));
                       /* stu_fragment.getCustomeMenu().HideView();
                        if (voiceRecorder.isRecording()) {
                            voiceRecorder.discardRecording();
                        }
*/
                    }
                } else {
                    imPersenter.Bannedtopost(kdbdistributorid, studyid, String.valueOf(nickNameBean.getID()), banned_sign);
                    //禁言开始
                    CustomeChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupLockPersonStringKey", String.valueOf(nickNameBean.getID()));
                    /*stu_fragment.getCustomeMenu().HideView();
                    if (voiceRecorder.isRecording()) {
                        voiceRecorder.discardRecording();
                    }*/
                }
            }
        });
        rl_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nickNameBean.getUserType() == 1) {
                    Intent intent = new Intent(LiveDirectActivity.this, OfficialHomePageActivity.class);
                    intent.putExtra("seeDistributorId", nickNameBean.getID());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LiveDirectActivity.this, UserPersonalCenterActivity.class);
                    intent.putExtra("distributorid", nickNameBean.getID());
                    startActivity(intent);
                }
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

//    private BannedListAdapter bannedListAdapter;

   /* public void initViewHolder() {
        bannedListAdapter1 = new BannedListAdapter1(this);
        bannedListAdapter1.setmAdapterListener(adapterCallBack);
        listView.setAdapter(bannedListAdapter);
    }*/

    BannedListAdapter1.BannedAdapterListener adapterCallBack = new BannedListAdapter1.BannedAdapterListener() {
        @Override
        public void releaseBanned(String distributeId) {
            String release_sign = TGmd5.getMD5(kdbdistributorid + studyid + distributeId);
            imPersenter.Releaseshutup(kdbdistributorid, studyid, distributeId, release_sign);
            //单聊的禁言发消息
            CustomeChatMenu.onBottomMenuClickListener.OnSingleChatListener(2, "IMChatGroupUNLockPersonStringKey", distributeId);

        }
    };

    @OnClick({R.id.rl_back, R.id.img_banned, R.id.rl_teacher, R.id.rl_student, R.id.id_content, R.id.txt_message_numer, R.id.txt_tea_msg_numer})
    public void OnClick(View view) {
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.img_banned:
                showBannedListDialog();
                break;
            case R.id.rl_back:
                imPersenter.RemoveMemberGroup("1");
                finish();
                break;
            case R.id.rl_teacher:
                hideFragments(tx);
                select_str = "tea";
                txt_teach_noread.setVisibility(View.GONE);
                if (teacherDistributorID.equals(distributorid)&&!courseState.equals("4")) {
                    tea_fragment.showCustomeMenu();
                } else {
                    tea_fragment.hideCustomeMenu();
                }
                if (teacher_noreadnumber > 0) {
                    tea_fragment.getTxt_message_numer().setVisibility(View.VISIBLE);
                    tea_fragment.getTxt_message_numer().setText(String.valueOf(teacher_noreadnumber));
                }
                initViewSelect();
                if (tea_fragment == null) {
                    tea_fragment = new TeacherFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("studyid", studyid);
                    tea_fragment.setArguments(bundle);
                    tx.add(R.id.id_content, tea_fragment);
                } else {
                    tx.show(tea_fragment);
                }
                tv_teacher.setTextColor(Color.parseColor("#d5aa5f"));
                view_teacher.setVisibility(View.GONE);
                tx.commit();
                break;
            case R.id.rl_student:
                hideFragments(tx);
                select_str = "stu";
                if (teacher_noreadnumber > 0) {
                    txt_teach_noread.setVisibility(View.VISIBLE);
                }
                if (noreadNumer > 0) {
                    stu_fragment.getTxt_message_numer().setVisibility(View.VISIBLE);
                    stu_fragment.getTxt_message_numer().setText(String.valueOf(noreadNumer));
                }
                if (teacherDistributorID.equals(distributorid)||courseState.equals("4")) {
                    stu_fragment.hideCustomeMenu();
                } else {
                    stu_fragment.showCustomeMenu();
                }

                /*if (!courseState.equals("4")){
                    //已结束的课程
                }*/
                initViewSelect();
                if (stu_fragment == null) {
                    stu_fragment = new StudentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("studyid", studyid);
                    stu_fragment.setArguments(bundle);
                    tx.add(R.id.id_content, stu_fragment);
                } else {
                    tx.show(stu_fragment);
                }
                tx.commit();
                tv_studnet.setTextColor(Color.parseColor("#d5aa5f"));
                view_student.setVisibility(View.GONE);

                break;
        }
    }

    public void hideFragments(FragmentTransaction transaction) {
        if (tea_fragment != null) {
            transaction.hide(tea_fragment);
        }
        if (stu_fragment != null) {
            transaction.hide(stu_fragment);
        }
    }

    public boolean isListViewReachBottomEdge(final ListView listView) {

        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 3) || listView.getLastVisiblePosition() == (listView.getCount() - 2)) {
//            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
//            result = (listView.getHeight() >= bottomChildView.getBottom());
            result = true;
        }
        return result;
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
                            .onActivityResultForChooseImageFromGallery(LiveDirectActivity.this,
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
                        groupMessageExtData.setSI(AESUtils.method3(distributorid));
                        groupMessageExtData.setMT(3);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        groupMessageExtData.setCT(simpleDateFormat.format(new Date()));
                        groupMessageExtData.setU(filePath);
                        groupMessageExtData.setBitmap(ThumbBitmap);
                        if (teacherDistributorID.equals(distributorid)) {
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
                        }
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
                            groupMessageExtData.setSI(AESUtils.method3(distributorid));
                            groupMessageExtData.setT("file://" + imagePath);
                            groupMessageExtData.setO("file://" + imagePath);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            groupMessageExtData.setCT(simpleDateFormat.format(new Date()));
                            groupMessageExtData.setMT(1);
                            if (teacherDistributorID.equals(distributorid)) {
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
                            }

                        } catch (Exception e) {

                        }
                    }
                });
    }

    CircleProgress circleProgress;
    CircleProgress pic_circleProgress;
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
                        ListView listView;
                        if (teacherDistributorID.equals(distributorid)) {
                            listView = tea_fragment.listView;
                        } else {
                            listView = stu_fragment.listView;
                        }
                        LinearLayout linearLayout = (LinearLayout) listView.getChildAt(listView.getCount() - 2 - listView.getFirstVisiblePosition());

                        if (linearLayout != null) {
                            circleProgress = (CircleProgress) linearLayout.findViewById(R.id.circleProgress);
                            circleProgress.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        if (isUploading) {
                            circleProgress.setValue((int) (((int) current / (float) total) * 100));
                        }
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        progressDialog.dismiss();
                        circleProgress.setVisibility(View.GONE);
                        hideBottomView();

                        Gson gson = new Gson();
                        UploadVideoResponse uploadVideoResponse = gson.fromJson(responseInfo.result, UploadVideoResponse.class);
                        if (null != uploadVideoResponse && uploadVideoResponse.getData() != null) {
                            ShortVideoGroupMessage shortVideoGroupMessage = new ShortVideoGroupMessage();
                            shortVideoGroupMessage.setUrl(sendServer + uploadVideoResponse.getData().getUrl());
                            shortVideoGroupMessage.setCoverImage(sendServer + uploadVideoResponse.getData().getCoverImage());
                            shortVideoGroupMessage.setGroupId(groupId);
                            if (teacherDistributorID.equals(distributorid)) {
                                GroupMessageExtData extData = tea_fragment.directChatRowAdapter.getItem(tea_fragment.directChatRowAdapter.getCount() - 1);
                                extData.setU(sendServer + uploadVideoResponse.getData().getUrl());
                                extData.setCI(sendServer + uploadVideoResponse.getData().getCoverImage());
                                tea_fragment.directChatRowAdapter.notifyDataSetChanged();
                            } else {
                                GroupMessageExtData extData = stu_fragment.directChatRowAdapter.getItem(stu_fragment.directChatRowAdapter.getCount() - 1);
                                extData.setU(sendServer + uploadVideoResponse.getData().getUrl());
                                extData.setCI(sendServer + uploadVideoResponse.getData().getCoverImage());
                                stu_fragment.directChatRowAdapter.notifyDataSetChanged();
                            }
                            try {
                                SignalRFuture<String> signalRFuture = chatClient.send(shortVideoGroupMessage);
                                if (null != signalRFuture) {
                                    signalRFuture.done(new Action<String>() {
                                        @Override
                                        public void run(String s) throws Exception {
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
                                            MyToast.makeText(LiveDirectActivity.this, "失败", Toast.LENGTH_LONG).show();
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
                        circleProgress.setVisibility(View.GONE);
                    }

                });
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
                        ListView listView;
                        if (teacherDistributorID.equals(distributorid)) {
                            listView = tea_fragment.listView;
                        } else {
                            listView = stu_fragment.listView;
                        }
                        LinearLayout linearLayout = (LinearLayout) listView.getChildAt(listView.getCount() - 2 - listView.getFirstVisiblePosition());
                        if (linearLayout != null) {
                            pic_circleProgress = (CircleProgress) linearLayout.findViewById(R.id.pic_circleProgress);
                            pic_circleProgress.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        if (isUploading) {
                            pic_circleProgress.setValue((int) (((int) current / (float) total) * 100));
                        }
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        hideBottomView();
                        Gson gson = new Gson();
                        UploadVideoResponse uploadVideoResponse = gson.fromJson(responseInfo.result, UploadVideoResponse.class);
                        pic_circleProgress.setVisibility(View.GONE);
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
                                            MyToast.makeText(LiveDirectActivity.this, "失败", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } catch (Exception e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(com.lidroid.xutils.exception.HttpException error, String msg) {
                        pic_circleProgress.setVisibility(View.GONE);
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
                                            public void run(String s) throws Exception {
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
                                                MyToast.makeText(LiveDirectActivity.this, "失败", Toast.LENGTH_LONG).show();
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

    /**
     * 顶部切换按钮，初始化
     */
    public void initViewSelect() {
        tv_studnet.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_teacher.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        view_student.setVisibility(View.GONE);
        view_teacher.setVisibility(View.GONE);
    }

    private void hideBottomView() {
        if (teacherDistributorID.equals(distributorid)) {
            tea_fragment.getCustomeMenu().HideView();
        } else {
            stu_fragment.getCustomeMenu().HideView();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideBottomView();
        return super.onTouchEvent(event);
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
        final ListView adapterView;
        switch (postion) {
            case 2: // 发送按钮
               /* //发送emoji表情
                GroupMessageExtData groupMessageExtData = new GroupMessageExtData();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                groupMessageExtData.setCT(simpleDateFormat.format(new Date()));
                groupMessageExtData.setC(sequence);
                groupMessageExtData.setMT(0);
                groupMessageExtData.setIsSendSuccess(false);
                groupMessageExtData.setMessage_state(false);
                groupMessageExtData.setI(String.valueOf(System.currentTimeMillis()));
                try {
                    groupMessageExtData.setSI(AESUtils.method3(distributorid));
                } catch (Exception e) {

                }
                if (teacherDistributorID.equals(distributorid)) {
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

                TextSingleMessage textSingleMessage = new TextSingleMessage();
//                TextGroupMessage textGroupMessage = new TextGroupMessage();
                String sreceiveId = null;
                String sdistributorid = null;
                try {
                    sreceiveId = AESUtils.method3(receiveId);
                    textSingleMessage.setReceiverId(sreceiveId);//接受者id
                    sdistributorid = AESUtils.method3(distributorid);
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
                                    /*if (teacherDistributorID.equals(distributorid)) {
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
                                    }
                                    MsgList.remove(0);*/
//                                    Message message = new Message();
//                                    message.what = 1;
//                                    handler_message.sendMessage(message);
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
//                            MsgList.add(groupMessageExtData);
//                    Message message = new Message();
//                    message.what = 1;
//                    handler_message.sendMessage(message);
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

    /**
     * 点击事件属于群聊
     *
     * @param postion
     * @param sequence
     */
    @Override
    public void OnBottomMenuClickListener(int postion, String sequence) {
        final ListView adapterView;
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
                    groupMessageExtData.setSI(AESUtils.method3(distributorid));
                } catch (Exception e) {

                }
                if (teacherDistributorID.equals(distributorid)) {
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
                }
                TextGroupMessage textGroupMessage = new TextGroupMessage();
                textGroupMessage.setGroupId(groupId);
                textGroupMessage.setContent(sequence);
                MsgList.add(groupMessageExtData);
                SignalRFuture<String> signalRFuture = chatClient.send(textGroupMessage);
                if (null != signalRFuture) {
                    signalRFuture.done(new Action<String>() {
                        @Override
                        public void run(String s) throws Exception {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (teacherDistributorID.equals(distributorid)) {
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
                                    }
                                    MsgList.remove(0);
//                                    Message message = new Message();
//                                    message.what = 1;
//                                    handler_message.sendMessage(message);
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
//                            MsgList.add(groupMessageExtData);
//                    Message message = new Message();
//                    message.what = 1;
//                    handler_message.sendMessage(message);
                break;
            case 3:// 照片 选择
//                FunctionUtils.chooseImageFromGallery(LiveDirectActivity.this, REQUEST_CODE_GALLERY);
//                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                intent1.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent1, REQUEST_CODE_GALLERY);


                // 权限管理工具类
                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                    @Override
                    public void permissionGranted(int requestCode) {
                        if (requestCode == 99) {
                            PhotoPickerIntent intent = new PhotoPickerIntent(LiveDirectActivity.this);
                            intent.setPhotoCount(1);
                            intent.setShowCamera(true);
                            intent.setShowGif(true);
                            startActivityForResult(intent, 101);
                        }
                    }
                });
                //申请读权限，和照相机权限
                permissionManager.checkPermission(99, LiveDirectActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                break;
            case 4:// 拍摄照片
                imagePath = FunctionUtils.chooseImageFromCamera(LiveDirectActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
                break;
            case 5:// 录视频
                Intent intent = new Intent(LiveDirectActivity.this, RecorderVideoActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MAP);
                break;
        }
    }

    @Override
    public void OnEditorActionListener(String s) {
    }

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

    public synchronized void reconnect() {
        Log.i("IsReconnection", this.IsReconnection + "");
        if (IsReconnection || !CanReconnection) {
            return;
        }
        if (!SystemUtils.checkNet(LiveDirectActivity.this)) {
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

    /**
     * 获取连接状态
     *
     * @return 连接状态
     */
    public ConnectionState getState() {
        return this.connection != null ? this.connection.getState()
                : ConnectionState.Disconnected;
    }

    /**
     * 启动客户端的链接
     */
    public void start() throws ExecutionException, InterruptedException {
        this.start(false);
    }

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
        if (SystemUtils.checkNet(LiveDirectActivity.this)) {
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
                        if (isshow_Countdown) {
                            txt_countdown.setVisibility(View.VISIBLE);
                            ll_record_view.setVisibility(View.GONE);
                        } else {
                            txt_countdown.setVisibility(View.GONE);
                            ll_record_view.setVisibility(View.VISIBLE);
                        }
                        txt_countdown.setText(String.valueOf(count_down));
                        count_down--;

                    }
                    break;
            }
        }
    };

    private void uploadSendVoice(boolean shorta, boolean isstop) {
        intercepter = true;
        inclue_layout.setVisibility(View.GONE);
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
                    groupMessageExtData.setSI(AESUtils.method3(distributorid));
                    groupMessageExtData.setU(voiceRecorder.getVoiceFilePath());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    groupMessageExtData.setCT(simpleDateFormat.format(new Date()));
                    groupMessageExtData.setMT(2);
                    groupMessageExtData.setL(stopRecoding);
                    ListView adapterView;
                    if (teacherDistributorID.equals(distributorid)) {
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
                    adapterView.setSelection(adapterView.getCount() - 1);
                    XutilsUploadVoice(voiceRecorder.getVoiceFilePath());
                } catch (Exception e) {
                    e.printStackTrace();
                    MyToast.show(LiveDirectActivity.this, "发送失败,连接异常!");
                }
            } else if (stopRecoding == EMError.FILE_INVALID) {
                MyToast.show(LiveDirectActivity.this, "录音失败!");
            } else {
                MyToast.show(LiveDirectActivity.this, "录音时间太短!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyToast.show(LiveDirectActivity.this, "录音失败,查看权限是否打开");
        }
    }

    private int SPACE = 100;// 间隔取样时间

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
            img_record.setImageDrawable(micImages[what]);
        }
    };

    public void showReleaseToCancelHint() {
        if (teacherDistributorID.equals(distributorid)) {
            tea_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.GONE);
            tea_fragment.getCustomeMenu().getTitleLongpass().setVisibility(View.VISIBLE);
//            tea_fragment.getCustomeMenu().getVoiceButton().setText("松开 发送");
        } else {
            stu_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.GONE);
            stu_fragment.getCustomeMenu().getTitleLongpass().setVisibility(View.VISIBLE);
//            stu_fragment.getCustomeMenu().getVoiceButton().setText("松开 发送");
        }
    }

    public void showMoveUpToCancelHint() {
        if (teacherDistributorID.equals(distributorid)) {
            tea_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.VISIBLE);
            tea_fragment.getCustomeMenu().getTitleLongpass().setVisibility(View.GONE);
//            tea_fragment.getCustomeMenu().getVoiceButton().setText("按住 说话");
        } else {
            stu_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.VISIBLE);
            stu_fragment.getCustomeMenu().getTitleLongpass().setVisibility(View.GONE);
//            stu_fragment.getCustomeMenu().getVoiceButton().setText("按住 说话");
        }
    }

    public void showMoveUpCancelHint2() {
        if (teacherDistributorID.equals(distributorid)) {
            tea_fragment.getCustomeMenu().getTitleShortPass().setVisibility(View.GONE);
            tea_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.VISIBLE);
        } else {
            stu_fragment.getCustomeMenu().getTitlemenu().setVisibility(View.VISIBLE);
            stu_fragment.getCustomeMenu().getTitleShortPass().setVisibility(View.GONE);
        }
    }

    public void startRecording(boolean islong) {
        /**
         * 判断SD 卡 是否存在
         */
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            MyToast.show(LiveDirectActivity.this, "没有SD卡");
            return;
        }
        try {
            wakeLock.acquire();
            if (islong) {
                showReleaseToCancelHint();
            }

            voiceRecorder.startRecording(LiveDirectActivity.this, distributorid);
        } catch (Exception e) {
            e.printStackTrace();
            if (wakeLock.isHeld())
                wakeLock.release();
            if (voiceRecorder != null)
                voiceRecorder.discardRecording();
            MyToast.show(LiveDirectActivity.this, "录音失败，请重试！");


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


    public void discardRecording() {
        if (wakeLock.isHeld())
            wakeLock.release();
        try {
            // 停止录音
            if (voiceRecorder.isRecording()) {
                voiceRecorder.discardRecording();
            }
        } catch (Exception e) {
        }
    }

    public int stopRecoding() {
        if (wakeLock.isHeld())
            wakeLock.release();
        return voiceRecorder.stopRecoding();
    }

    public String getVoiceFilePath() {
        return voiceRecorder.getVoiceFilePath();
    }

    public String getVoiceFileName() {
        return voiceRecorder.getVoiceFileName();
    }

    public boolean isRecording() {
        return voiceRecorder.isRecording();
    }

    @Override
    public String onArticleSelected() {
        return teacherDistributorID;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public void onSelectbottom() {
        if (select_str.equals("stu")) {
//            boolean flag = isListViewReachBottomEdge(fragment2.listView);
//            if (flag) {
            noreadNumer = 0;
            stu_fragment.listView.setSelection(stu_fragment.listView.getCount() - 1);
//            }
        } else {
//            boolean flag = isListViewReachBottomEdge(fragment1.listView);
//            if (flag) {
            teacher_noreadnumber = 0;
            tea_fragment.listView.setSelection(tea_fragment.listView.getCount() - 1);
//            }
        }
    }

    @Override
    public void getNickName(String id) {
        String sign = TGmd5.getMD5(id);
        if (imPersenter != null) {
            imPersenter.GetNickName(id, sign);
        }

    }

    @Override
    public String getTeachId() {
        return teacherDistributorID;
    }

    @Override
    public void showDialog(String uid) {
        if (distributorid.equals(uid)) {
            //如果点击自己头像，就进入自己主页

            Bundle bundle = new Bundle();
            bundle.putInt("distributorid", Integer.parseInt(distributorid));
            Intent intent = new Intent(LiveDirectActivity.this, UserPersonalCenterActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (uid.equals(teacherDistributorID)) {
            Bundle bundle = new Bundle();
            bundle.putInt("distributorid", Integer.parseInt(uid));
            Intent intent = new Intent(LiveDirectActivity.this, UserPersonalCenterActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (distributorid.equals(kdbdistributorid)) {
            showBannedDialog(uid);
        } else {

            Bundle bundle = new Bundle();
            bundle.putInt("distributorid", Integer.parseInt(uid));
            Intent intent = new Intent(LiveDirectActivity.this, UserPersonalCenterActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
           /* Intent intent = new Intent(LiveDirectActivity.this, OfficialHomePageActivity.class);
            intent.putExtra("seeDistributorId", uid);
            startActivity(intent);*/
        }
    }


    @Override
    public AddUser getParamenters() {
        AddUser addUser = null;
        try {
            String encryptBASE64 = AESUtils.method3(distributorid);
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
        imPersenter.JoinChatGroup();
    }

    @Override
    public void JoinChatGroup_response(String s) {

        /**
         * 修改
         */
        imPersenter.getServer();
    }

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
            PreferenceHelper.write(LiveDirectActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CHAT_SERVER, chatserver);
            createConnect();
        }
        imPersenter.member_list(distributorid, getMemberList);
        tea_fragment.initData(2, distributorid);
        stu_fragment.initData(1, distributorid);
    }

    @Override
    public void getNickNameResponse(String s) {
        try {
            closeLoadingProgressDialog();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            if (jsonArray != null && jsonArray.length() > 0) {
                JSONArray jsonArray1 = (JSONArray) jsonArray.get(0);

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
            stu_fragment.directChatRowAdapter.setGroupMemberList(hashMap);
            tea_fragment.directChatRowAdapter.setGroupMemberList(hashMap);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    @Override
    public void prohibitlistResponse(String s) {
        Log.e("ksahdfka", "===================" + s);
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
                        onBannedListener.setViewVisibility("visibility");
                    }
                    dataListTmp.add(map1);
                }
                bannedListAdapter1.setData(dataListTmp);
                if (dataListTmp.size() == 0) {
                    im_nolist.setVisibility(View.VISIBLE);
                } else {
                    im_nolist.setVisibility(View.GONE);
                }
                bannedListAdapter1.notifyDataSetChanged();
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

    @Override
    public void excuteFailedCallBack(String s) {
        Log.e("hkasdfhk", "------userName返回失败-------" + s);
    }

    StringBuffer stringBuffer;

    @Override
    public void member_list_response(String s) {
        //数据的解析
        try {
            closeLoadingProgressDialog();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("IMGMD");
            Gson gson = new Gson();
            List<GroupMember> list = new ArrayList<>();
            try {
                list = gson.fromJson(String.valueOf(jsonArray),
                        new TypeToken<List<GroupMember>>() {
                        }.getType());
            } catch (Exception e) {
                // TODO: handle exception
            }

            if (list1 == null) {
                list1 = new ArrayList<GroupMember>();
            } else {
                list1.clear();
            }
            stringBuffer = null;
            stringBuffer = new StringBuffer();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0) {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append(AESUtils.Decrypt(list.get(i).getFI()));

                    if (list.get(i).isID() == false) {
                        list1.add(list.get(i));
                    }
                }
            }

            String sign = TGmd5.getMD5(stringBuffer.toString());
            imPersenter.GetNickName(stringBuffer.toString(), sign);
            txt_groupmemer_list.setText(list1.size() + "观看");
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void GetGroupMessageExt_response(String s) {
        //数据的解析
        try {
            JSONObject jsonObject = new JSONObject(s);
            int e1 = jsonObject.getInt("e1");
            if (e1 == 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("d");
                Gson gson = new Gson();
                List<GroupMessageExtData> list = new ArrayList<>();
                try {
                    list = gson.fromJson(String.valueOf(jsonArray),
                            new TypeToken<List<GroupMessageExtData>>() {
                            }.getType());
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

    }

    @Override
    public void getServer_error() {
        chatserver = PreferenceHelper.readString(LiveDirectActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CHAT_SERVER);
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
        tea_fragment.initData(2, distributorid);
        stu_fragment.initData(1, distributorid);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteErrSuccessCallBack(String s) {

    }

    @Override
    public void excuteErrFailedCallBack(String s) {

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
    public void onError(Throwable throwable) {
        Log.i("发送错误重连", "错误重连");
        throwable.printStackTrace();
        this.errorTimes++;
        if (this.errorTimes < 15) {
            this.reconnect();
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

    public class ConsoleLogger implements Logger {

        @Override
        public void log(String arg0, LogLevel arg1) {
            if (arg0 == null) {
                System.out.println("arg0 is null");
            }
            System.out.println(arg0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        imPersenter.RemoveMemberGroup("1");
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
    protected void onPause() {
        super.onPause();
        Log.e("kjhakfha", "onPause:========== ");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stu_fragment.directChatRowAdapter.onStopVoice();
        tea_fragment.directChatRowAdapter.onStopVoice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (teacherDistributorID.equals(distributorid)&&!courseState.equals("4")) {
            tea_fragment.showCustomeMenu();
        }
//        tea_fragment.getCustomeMenu().getVoiceButton().setOnTouchListener(this);
//        stu_fragment.getCustomeMenu().getVoiceButton().setOnTouchListener(this);
        MobclickAgent.onResume(this);
    }

    Handler handler_message = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        synchronized (this) {
                            if (MsgList.size() > 0) {
                                GroupMessageExtData groupMessageExtData = MsgList.get(0);
                                TextGroupMessage textGroupMessage = new TextGroupMessage();
                                textGroupMessage.setGroupId(groupId);
                                textGroupMessage.setContent(groupMessageExtData.getC());
                                SignalRFuture<String> signalRFuture = chatClient.send(textGroupMessage);
                                if (null != signalRFuture) {
                                    signalRFuture.done(new Action<String>() {
                                        @Override
                                        public void run(String s) throws Exception {
                                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (teacherDistributorID.equals(distributorid)) {
                                                        TDirectChatRowAdapter adapter = tea_fragment.directChatRowAdapter;
                                                        for (int i = 0; i < 100; i++) {
                                                            if (MsgList.get(0).getCT().equals(adapter.getData().get(i).getCT())) {
                                                                adapter.getData().get(i).setIsSendSuccess(true);
                                                                break;
                                                            }
                                                        }
                                                        adapter.notifyDataSetChanged();

                                                    } else {
                                                        DirectChatRowAdapter adapter = stu_fragment.directChatRowAdapter;
                                                        for (int i = 0; i < 100; i++) {
                                                            if (MsgList.get(0).getCT().equals(adapter.getData().get(i).getCT())) {
                                                                adapter.getData().get(i).setIsSendSuccess(true);
                                                                break;
                                                            }
                                                        }
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                    MsgList.remove(0);
                                                    Message message = new Message();
                                                    message.what = 1;
                                                    handler_message.sendMessage(message);
                                                }
                                            });
                                        }
                                    });
                                    signalRFuture.onError(new ErrorCallback() {
                                                              @Override
                                                              public void onError(Throwable throwable) {

                                                              }
                                                          }

                                    );
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                    break;
            }
        }
    };
    public static OnBannedListener onBannedListener = null;

    public static void setOnBannedListener(OnBannedListener onBannedListener) {
        LiveDirectActivity.onBannedListener = onBannedListener;
    }

    private float xX = 0;
    private float yY = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIEvent(MoveUp moveUp) {
        xX = moveUp.getXX();
        yY = moveUp.getYY();
    }


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
                    if (teacherDistributorID.equals(distributorid)) {
                        tea_fragment.getCustomeMenu().getChanganProgress().setVisibility(View.VISIBLE);
                        tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.GONE);
                        tea_fragment.getCustomeMenu().getChanganProgress().setProgress((int) (millisUntilFinished / 1000));
                        tea_fragment.getCustomeMenu().getDaojishiNum().setText("(" + miao + "s)");
                    } else {
                        stu_fragment.getCustomeMenu().getChanganProgress().setVisibility(View.VISIBLE);
                        stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.GONE);
                        stu_fragment.getCustomeMenu().getChanganProgress().setProgress((int) (millisUntilFinished / 1000));
                        stu_fragment.getCustomeMenu().getDaojishiNum().setText("(" + miao + "s)");
                    }
                  /*  stu_fragment.getCustomeMenu().getChanganProgress().setProgress((int) (millisUntilFinished/1000));
                    stu_fragment.getCustomeMenu().getDaojishi().setText("松开发送("+miao+"s)");*/
                }

                @Override
                public void onFinish() {
                    isshow_Countdown = false;
                    if (!intercepter) {
                        if (teacherDistributorID.equals(distributorid)) {
                            tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                        } else {
                            stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                        }

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
            if (yY < -100) {
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
            }
        }
        //长按 录音抬起
        if ("luyintaiqi".equals(action)) {
            msg_flag = true;
            if (countDownTimer1 != null) {
                countDownTimer1.cancel();
            }
            isshow_Countdown = false;
            if (!intercepter) {
                tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
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
                tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                stu_fragment.getCustomeMenu().HideView();
                tea_fragment.getCustomeMenu().HideView();
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
                stu_fragment.cannot_other.setVisibility(View.GONE);
                tea_fragment.cannot_other.setVisibility(View.GONE);
                top_cannot_other.setVisibility(View.GONE);
                tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                rl_teacher.setClickable(true);
                rl_student.setClickable(true);
                stopRecoding();
            }
        }


        //第一次点击
        if ("oneclickvoice".equals(action)) {
            stopRecoding = 0;
            intercepter = false;
            if (teacherDistributorID.equals(distributorid)) {
                tea_fragment.cannot_other.setVisibility(View.VISIBLE);
                tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.GONE);
            } else {
                stu_fragment.cannot_other.setVisibility(View.VISIBLE);
                stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.GONE);
            }


            top_cannot_other.setVisibility(View.VISIBLE);
            rl_teacher.setClickable(false);
            rl_student.setClickable(false);
            if (teacherDistributorID.equals(distributorid)) {
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
            }
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
                    if (teacherDistributorID.equals(distributorid)) {
                        tea_fragment.getCustomeMenu().getDanjiProgress().setProgress((int) (millisUntilFinished / 1000));
                        tea_fragment.getCustomeMenu().getShortMiao().setText("(" + miao + "s)");
                    } else {
                        stu_fragment.getCustomeMenu().getDanjiProgress().setProgress((int) (millisUntilFinished / 1000));
                        stu_fragment.getCustomeMenu().getShortMiao().setText("(" + miao + "s)");
                    }
                    /*stu_fragment.getCustomeMenu().getDanjiProgress().setProgress((int) (millisUntilFinished/1000));
                    stu_fragment.getCustomeMenu().getShortMiao().setText("("+miao+"s)");*/
                }

                @Override
                public void onFinish() {
                    isshow_Countdown = false;
                    if (!intercepter) {
                        uploadSendVoice(true, false);
                        rl_teacher.setClickable(true);
                        rl_student.setClickable(true);
                        top_cannot_other.setVisibility(View.GONE);
                        stu_fragment.cannot_other.setVisibility(View.GONE);
                        tea_fragment.cannot_other.setVisibility(View.GONE);
                        tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                        stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
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
                rl_teacher.setClickable(true);
                rl_student.setClickable(true);
                top_cannot_other.setVisibility(View.GONE);
                stu_fragment.cannot_other.setVisibility(View.GONE);
                tea_fragment.cannot_other.setVisibility(View.GONE);
                tea_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                stu_fragment.getCustomeMenu().getLlbottomVoiceText().setVisibility(View.VISIBLE);
                uploadSendVoice(true, true);
            }
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setPressed(true);
                intercepter = false;
                count_down = 10;
                inclue_layout.setVisibility(View.VISIBLE);
                showReleaseToCancelHint();
                startRecording(true);
                updateMicStatus();
                startTime = System.currentTimeMillis();
                handler.sendEmptyMessage(1);


//                  break;
            case MotionEvent.ACTION_MOVE:
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
                if (event.getY() < -20) {
                    ll_record_view.setVisibility(View.GONE);
                    txt_countdown.setVisibility(View.GONE);
                    img_record_hint.setVisibility(View.VISIBLE);
                    txt_record_hint.setText("松开手指 取消发送");
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
                }
//                   break;
                return true;
            case MotionEvent.ACTION_UP:
                msg_flag = true;
                isshow_Countdown = false;
                if (!intercepter) {
                    uploadSendVoice(false, false);

                }
                return true;
            default:
//                discardRecording();
                return false;
        }
    }


}
