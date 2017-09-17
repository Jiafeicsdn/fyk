package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.CommentZanBean;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.CircleCommZanEntity;
import com.lvgou.distribution.entity.CommentListEntity;
import com.lvgou.distribution.entity.ImageHeadEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnSelectClickListener;
import com.lvgou.distribution.inter.ScrollViewListener;
import com.lvgou.distribution.presenter.FamousTeacherDetaiPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.video.FullVideoActivity;
import com.lvgou.distribution.video.VideoSuperPlayer;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.MyGridView;
import com.lvgou.distribution.view.MyListView;
import com.lvgou.distribution.view.MyRelativeLayout;
import com.lvgou.distribution.view.ObservableScrollView;
import com.lvgou.distribution.viewholder.CircleCommentZanViewHolder;
import com.lvgou.distribution.viewholder.CommentListViewHolder;
import com.lvgou.distribution.viewholder.ImageHeadViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.superplayer.library.SuperPlayer;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.xdroid.common.utils.SystemUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Snow on 2016/5/16 0016.
 * 课程详细
 */
public class FamousTeacherDetialActivity extends BaseFamousActivity implements View.OnTouchListener, FamousTeacherDetiaiView, OnSelectClickListener, OnClassifyPostionClickListener<CommentListEntity>, SuperPlayer.OnNetChangeListener, SuperPlayer.VoiceEventListener {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.img_back)
    private ImageView img_back;
    @ViewInject(R.id.img_search)
    private ImageView img_share;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_share)
    private RelativeLayout rl_share;
    @ViewInject(R.id.rl_intro)
    private RelativeLayout rl_intro;
    @ViewInject(R.id.rl_all)
    private RelativeLayout rl_all;
    @ViewInject(R.id.img_head)
    private ImageView img_head;
    @ViewInject(R.id.tv_num_acture)
    private TextView tv_num_acture;
    @ViewInject(R.id.tv_limit)
    private TextView tv_limit;
    @ViewInject(R.id.rl_tuanyuan)
    private MyRelativeLayout rl_tuanyuan;
    @ViewInject(R.id.img_grid_view)
    private MyGridView myGridView;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_prduction)
    private TextView tv_prduction;
    @ViewInject(R.id.tv_theme_title)
    private TextView tv_theme_title;
    @ViewInject(R.id.rl_dialog_share_root)
    private RelativeLayout rl_dialog_share_root;
    @ViewInject(R.id.ll_dialog_share_cotent)
    private LinearLayout ll_dialog_share_cotent;
    @ViewInject(R.id.rl_qq)
    private RelativeLayout rl_qq;
    @ViewInject(R.id.rl_qzone)
    private RelativeLayout rl_qzone;
    @ViewInject(R.id.rl_weixin)
    private RelativeLayout rl_weixin;
    @ViewInject(R.id.rl_pengyou)
    private RelativeLayout rl_pengyou;
    @ViewInject(R.id.rl_dismiss)
    private RelativeLayout rl_dismiss;
    @ViewInject(R.id.ll_all)
    private LinearLayout ll_all;
    //    @ViewInject(R.id.rl_all_baomming)
//    private RelativeLayout rl_all_baomming;
    @ViewInject(R.id.rl_press)
    private RelativeLayout rl_press;
    @ViewInject(R.id.img_press)
    private ImageView img_press;
    @ViewInject(R.id.tv_jindu)
    private TextView tv_jindu;
    @ViewInject(R.id.tv_total_jindu)
    private TextView tv_total;
    @ViewInject(R.id.seekBar)
    private SeekBar seek_bar;
    @ViewInject(R.id.rl_voice)
    private RelativeLayout rl_voice;
    DisplayImageOptions options;
    @ViewInject(R.id.lv_listview)
    private MyListView myListView;
    @ViewInject(R.id.load_more_progressbar)
    private ProgressBar load_more_progressbar;
    @ViewInject(R.id.rl_video_gone)
    private RelativeLayout rl_video_gong;
    //    @ViewInject(R.id.rl_video_visiable)
//    private RelativeLayout rl_video_visiable;
//    @ViewInject(R.id.video_player)
//    private VideoSuperPlayer videoSuperPlayer;
    @ViewInject(R.id.view_super_player)
    private SuperPlayer view_super_player;
    @ViewInject(R.id.img_play)
    private ImageView img_paly;
    @ViewInject(R.id.rl_top)
    private FrameLayout rl_top;
    @ViewInject(R.id.scroll_view)
    private ObservableScrollView scroll_view;
    @ViewInject(R.id.tv_day)
    private TextView tv_day;
    @ViewInject(R.id.tv_minute)
    private TextView tv_minute;
    @ViewInject(R.id.tv_hour)
    private TextView tv_hour;
    @ViewInject(R.id.tv_second)
    private TextView tv_second;
    @ViewInject(R.id.ll_daojishi)
    private LinearLayout ll_daojishi;
    @ViewInject(R.id.ll_paid_seek)
    private LinearLayout ll_paid_seek;
    @ViewInject(R.id.tv_paid_tuanbi_seek)
    private TextView tv_paid_tuanbi_seek;
    @ViewInject(R.id.tv_bottom_show)
    private TextView tv_bottom_show;
    @ViewInject(R.id.tv_opearte)
    private TextView tv_opearte;
    @ViewInject(R.id.tv_new_user)
    private TextView tv_new_user;
    @ViewInject(R.id.tv_tuanbi_num)
    private TextView tv_tuanbi_num_pay;
    @ViewInject(R.id.tv_proper_corwd_people)
    private TextView tv_proper_corwd_people;
    @ViewInject(R.id.tv_learn_num)
    private TextView tv_learn_num;
    @ViewInject(R.id.tv_go_zhibojian)
    private TextView tv_go_zhibojian;
    @ViewInject(R.id.img_zan)
    private ImageView img_zan;
    @ViewInject(R.id.tv_money)
    private TextView tv_money;
    @ViewInject(R.id.tv_people_num)
    private TextView tv_people_num;
    @ViewInject(R.id.et_evaluate)
    private EditText et_evaluate;
    @ViewInject(R.id.tv_sned)
    private TextView tv_sned;
    @ViewInject(R.id.ll_visiable)
    private LinearLayout ll_visiable;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;
    @ViewInject(R.id.ll_dianzan)
    private LinearLayout ll_dianzan;
    @ViewInject(R.id.rl_fabu)
    private RelativeLayout rl_fabu;
    @ViewInject(R.id.ll_dialog)
    private LinearLayout ll_dialog;
    @ViewInject(R.id.rl_dialog_root)
    private RelativeLayout rl_dialog_root;
    @ViewInject(R.id.ll_img_content)
    private LinearLayout ll_img_intro;
    @ViewInject(R.id.rl_img_intro_root)
    private RelativeLayout rl_img_intro_root;
    @ViewInject(R.id.et_tuanbi)
    private EditText et_tuanbi;
    @ViewInject(R.id.txt_tuanbi_num)
    private TextView tv_tuanbi_num;
    @ViewInject(R.id.show_ratingbar)
    private RatingBar show_ratingbar;
    @ViewInject(R.id.ll_ratingbar)
    private LinearLayout ll_ratingbar;
    @ViewInject(R.id.tv_evaluate_num)
    private TextView tv_evaluate_num;
    @ViewInject(R.id.et_content)
    private EditText et_content;
    @ViewInject(R.id.rl_01)
    private RelativeLayout rl_close;
    @ViewInject(R.id.btn_zanshang)
    private Button btn_zanshang;
    @ViewInject(R.id.img_backround)
    private ImageView img_backround;
    @ViewInject(R.id.view_location)
    private View view_location;
    @ViewInject(R.id.rl_evaluate)
    private RelativeLayout rl_evaluate;
    @ViewInject(R.id.room_ratingbar)
    private RatingBar ratingBar;
    @ViewInject(R.id.tv_level)
    private TextView tv_levels;
    @ViewInject(R.id.tv_commit)
    private TextView tv_commit;
    @ViewInject(R.id.tv_identity)
    private TextView tv_identity;
    @ViewInject(R.id.tv_guider_intro)
    private TextView tv_guider_intro;
    @ViewInject(R.id.rl_notice)
    private RelativeLayout rl_notice;
    @ViewInject(R.id.ll_close)
    private LinearLayout ll_close;
    @ViewInject(R.id.rl_listening)
    private RelativeLayout rl_listening;
    @ViewInject(R.id.rl_turn)
    private RelativeLayout rl_turn;
    @ViewInject(R.id.img_play_voice)
    private ImageView img_play_voice;
    @ViewInject(R.id.img_play_voice_one)
    private ImageView img_play_voice_one;
    @ViewInject(R.id.txt_course_start_date)
    private TextView txt_course_start_date;
    @ViewInject(R.id.txt_comment_number)
    private TextView txt_comment_number;
    @ViewInject(R.id.img_teacher_more_intro)
    private ImageView img_teacher_more_intro;
    @ViewInject(R.id.img_course_more_intro)
    private ImageView img_course_more_intro;
    private Dialog dialog, dialog_paly;
    private String id_ = "";
    private String distributorid = "";
    private String tuanbi = "";

    private String baoming = "";
    private String CKtuanbi = "";
    private String GID;
    private String share_title = "";
    private String share_content = "";
    private String share_img = "";
    private String share_url = Url.XIANSHANG_ROOT + "/study/teacherdetail?id=";

    private String str_time = "";
    private long between;
    private String is_start = "1";// 1  是  2  否
    private String img_path;
    private String theme;
    private String teacher_name;
    private String teacherDistributorID;
    private int KDBDistributorID;
    private int KTID;
    private String is_new_user = "0";// 1  是  0  否
    private String is_study = "0";// 1  是  0  否
    private String rl_bottom_click_state = "1";//地步按钮点击事件状态区分
    private ListViewDataAdapter<ImageHeadEntity> imageHeadEntityListViewDataAdapter;

    private boolean teacher_more_flag = true;
    private boolean course_more_flag = true;
    private ListViewDataAdapter<CommentListEntity> commentListEntityListViewDataAdapter;
    private String voice_path = "";
    DisplayImageOptions optionsone;
    boolean TimeTreadFlag = false;
    private Handler handler = new Handler();
    private MediaPlayer mediaPlayer = null;
//    private MediaPlayer videoMediaPlyer = null;

    int CurrentTime;
    private String timeString;
    private String ZBTime = "";
    private String perssion = "";// 权限
    private String download_url = "";
    private String tuanb_or_comment = "0";// 如果，团币为0 默认 评论
    private String index = "";
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private int TotalItems;
    float x1 = 0;
    float y1 = 0;
    float x2 = 0;
    float y2 = 0;
    private boolean islast_one = false;
    private String video_url = "";
    private int hight = 0;

    private int starNum = 5;
    private String is_vioce_palying = "0";// 0 为播放 1  正在播放
    private String type_comment = "0";
    private String state_one = "";
    private String message_5 = "";
    private String message_6 = "";
    private AnimationDrawable animationDrawable;

    private FamousTeacherDetaiPresenter famousTeacherDetaiPresenter;
    private Dialog dialog_go;
    private int del_layer_index;

    private int course_intro_line;
    private int teacher_intro_line;

    private int tingKeQuanID=0;//用来判断是否有听课券的大于零代表有
    private int tingKeQuanType=0;//用来判断听课券的类型，1->通用券 2->报名券  3->查看券
    private int tingKeQuanTuanBi=0;//听课券的数值大小

    //处理进度条更新
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //更新进度
                    if (mediaPlayer != null) {
                        int position = mediaPlayer.getCurrentPosition();
                        int time = mediaPlayer.getDuration();
                        int max = seek_bar.getMax();
                        seek_bar.setProgress(position * max / time);
                        if (seek_bar.getProgress() == 100) {
                            animationDrawable = (AnimationDrawable) img_play_voice.getDrawable();
                            animationDrawable.stop();
                            img_play_voice.setVisibility(View.GONE);
                            img_play_voice_one.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case 1005:
                    String Smm;
                    String Sss;
                    int CureetTimeSS = mediaPlayer.getDuration() / 1000;
                    int mm = CureetTimeSS / 60;
                    int ss = CureetTimeSS % 60;
                    if (mm / 10 == 0) {
                        Smm = "0" + mm;
                    } else {
                        Smm = "" + mm;
                    }
                    if (ss / 10 == 0) {
                        Sss = "0" + ss;
                    } else {
                        Sss = "" + ss;
                    }
                    String allTimeString = Smm + ":" + Sss;
                    tv_total.setText(allTimeString);
                    break;
                case 7:
                    download_url = Environment.getExternalStorageDirectory() + "/vedio.mp3";
                    showTotalTime();
                    break;
                case Constants.EXECUTE_LOADING:
                    if (between == 0) {
                        closeTimer();
                    } else if (between < 0) {
                        closeTimer();
                        rl_notice.setVisibility(View.GONE);
                        ll_daojishi.setVisibility(View.GONE);
                    }
                    long day1 = between / (24 * 3600);
                    long hour1 = between / 3600 - day1 * 24;
                    long minute1 = between / 60 - day1 * 24 * 60 - hour1 * 60;
                    long second = between - day1 * 24 * 60 * 60 - hour1 * 60 * 60 - minute1 * 60;
                    if (day1 > 0) {
                        tv_day.setText(day1 + "");
                        tv_hour.setText(hour1 + "");
                        tv_minute.setText(minute1 + "");
                        tv_second.setText(second + "");
                    } else if (hour1 > 0) {
                        tv_day.setText("00");
                        tv_hour.setText(hour1 + "");
                        tv_minute.setText(minute1 + "");
                        tv_second.setText(second + "");
                    } else if (minute1 > 0) {
                        tv_day.setText("00");
                        tv_hour.setText("00");
                        tv_minute.setText(minute1 + "");
                        tv_second.setText(second + "");
                    } else {
                        tv_day.setText("00");
                        tv_hour.setText("00");
                        tv_minute.setText("00");
                        tv_second.setText(second + "");
                    }
                    between = between - 1;
                    break;
                case Constants.EXECUTE_FINISH:

                    break;
                case 1009:
                    MyToast.show(FamousTeacherDetialActivity.this, "报名成功");
                    break;
                case 1010:
                    MyToast.show(FamousTeacherDetialActivity.this, message_5);
                    break;
                case 1011:
                    MyToast.show(FamousTeacherDetialActivity.this, message_6);
                    break;
                default:
                    break;
            }
        }
    };
    private int fufei=0;
    private int fufei2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_detial);
        ViewUtils.inject(this);
        Log.e("ahdfkashf", "打开之后");
        tv_title.setVisibility(View.GONE);
//        id_ = getTextFromBundle("id");
//        index = getTextFromBundle("index");
//        Log.e("kjhsadfkah", "--------id_" + id_);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                id_ = extras.getString("id");
                index = extras.getString("index");
            }
        }

        view_super_player.setListener(this);
        distributorid = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        famousTeacherDetaiPresenter = new FamousTeacherDetaiPresenter(this);

        /**
         * 获取 屏幕分辨率  高度
         */
        Display display = getWindowManager().getDefaultDisplay(); //Activity#getWindowManager()
        Point size = new Point();
        display.getSize(size);
        hight = size.y;
        initViewHolder();
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                showLoadingProgressDialog(FamousTeacherDetialActivity.this, "");
                String sign_ = TGmd5.getMD5(distributorid + id_);
                famousTeacherDetaiPresenter.getData(distributorid, id_, sign_);
                String sign_one = TGmd5.getMD5(id_ + pageindex);
                famousTeacherDetaiPresenter.getCommentData(id_, pageindex + "", sign_one);
            }
        }
        myListView.setFocusable(false);
        seek_bar.setOnSeekBarChangeListener(new seekBarListener());

        scroll_view.setScrollViewListener(new ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy, boolean isLast) {
                /**
                 * 底部发布评论显示与隐藏
                 */
                islast_one = isLast;
                mIsUp = true;
                int[] loacation = new int[2];
                view_location.getLocationOnScreen(loacation);
                if (loacation[1] < hight - 90) {
                    rl_fabu.setVisibility(View.VISIBLE);
                } else {
                    rl_fabu.setVisibility(View.GONE);
                }

            }
        });
        scroll_view.setOnTouchListener(this);

    }

    @OnClick({R.id.rl_back, R.id.img_course_more_intro, R.id.img_teacher_more_intro, R.id.rl_share, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss, R.id.rl_tuanyuan, R.id.ll_all, R.id.rl_press, R.id.img_play,
            R.id.tv_go_zhibojian, R.id.tv_sned, R.id.img_zan, R.id.rl_01, R.id.btn_zanshang, R.id.tv_commit, R.id.rl_intro, R.id.ll_img_content, R.id.ll_close
            , R.id.rl_listening, R.id.img_head, R.id.rl_turn, R.id.tv_opearte})
    public void OnClick(View view) {
        UMImage image = new UMImage(FamousTeacherDetialActivity.this, share_img);
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (view_super_player != null && view_super_player.onBackPressed()) {
                    return;
                } else {
                    try {
                        if (mediaPlayer != null) {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.pause();
                            }
                        }
//                    if (videoMediaPlyer != null) {
//                        if (videoMediaPlyer.isPlaying()) {
//                            videoMediaPlyer.pause();
//                        }
//                    }
                    } catch (Exception c) {
                    }
//                if (index.equals("1")) {
//                    bundle.putString("selection_postion", "0");
//                    openActivity(HomeActivity.class, bundle);
//                    finish();
//                } else {
                    finish();
//                }
                }
                break;
            case R.id.img_course_more_intro:
                if (course_more_flag) {
                    tv_prduction.setMaxLines(course_intro_line);
                    course_more_flag = false;
                    img_course_more_intro.setImageResource(R.mipmap.icon_more_up);
                } else {
                    tv_prduction.setMaxLines(3);
                    course_more_flag = true;
                    img_course_more_intro.setImageResource(R.mipmap.icon_more_down);
                }
                break;
            case R.id.img_teacher_more_intro:
                if (teacher_more_flag) {
                    tv_guider_intro.setMaxLines(teacher_intro_line);
                    teacher_more_flag = false;
                    img_teacher_more_intro.setImageResource(R.mipmap.icon_more_up);
                } else {
                    tv_guider_intro.setMaxLines(3);
                    teacher_more_flag = true;
                    img_teacher_more_intro.setImageResource(R.mipmap.icon_more_down);
                }
                break;
            case R.id.rl_share:
                openDialogShare();
                break;
            case R.id.rl_qq:
                UMWeb web = new UMWeb(share_url + id_);
                web.setTitle(share_title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(FamousTeacherDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withTargetUrl(share_url + id_)
                        .withText(share_content)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_qzone:
                UMWeb web1 = new UMWeb(share_url + id_);
                web1.setTitle(share_title);//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription(share_content);//描述
                new ShareAction(FamousTeacherDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withTargetUrl(share_url + id_)
                        .withText(share_content)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_weixin:
                UMWeb web2 = new UMWeb(share_url + id_);
                web2.setTitle(share_title);//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription(share_content);//描述
                new ShareAction(FamousTeacherDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                Log.e("kjashdfkas", "----------------" );
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withTargetUrl(share_url + id_)
                        .withText(share_content)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_pengyou:
                UMWeb web3 = new UMWeb(share_url + id_);
                web3.setTitle(share_title);//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription(share_content);//描述
                new ShareAction(FamousTeacherDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withTargetUrl(share_url + id_)
                        .withText(share_content)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_dismiss:
                closeDialogShare();
                break;
            case R.id.rl_tuanyuan:
                try {
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                        }
                    }
//                    if (videoMediaPlyer != null) {
//                        if (videoMediaPlyer.isPlaying()) {
//                            videoMediaPlyer.pause();
//                        }
//                    }
                } catch (Exception c) {
                }
                bundle.putString("id", id_);
                openActivity(TuanYuanActivity.class, bundle);
                break;
            case R.id.rl_listening:
                if (tv_total.getText().equals("00:00")) {
                    MyToast.show(FamousTeacherDetialActivity.this, "正在加载音频，请稍候..");
                } else {
                    if (is_vioce_palying.equals("0")) {
                        is_vioce_palying = "1";

                        /**
                         * 暂停视频播放
                         */
//                        if (videoMediaPlyer != null) {
//                            if (videoMediaPlyer.isPlaying()) {
//                                videoMediaPlyer.pause();
//                                videoSuperPlayer.pausePlay();
//                            }
//                        }
                        view_super_player.pause();
                        mediaPlayer.start();
                        img_play_voice.setVisibility(View.VISIBLE);
                        img_play_voice_one.setVisibility(View.GONE);
                        img_play_voice.setImageResource(R.drawable.voice_play_anim);
                        animationDrawable = (AnimationDrawable) img_play_voice.getDrawable();
                        animationDrawable.start();
                        if (TimeTreadFlag == false) {
                            TimeTreadFlag = true;
                            new TimeThread().start();
                        }
                    } else if (is_vioce_palying.equals("1")) {
                        is_vioce_palying = "0";
                        mediaPlayer.pause();
                        img_play_voice.setVisibility(View.GONE);
                        img_play_voice_one.setVisibility(View.VISIBLE);
                        img_play_voice.setImageResource(R.drawable.voice_play_anim);
                        animationDrawable = (AnimationDrawable) img_play_voice.getDrawable();
                        animationDrawable.stop();
                    }
                }
                break;
            case R.id.img_play:
                if (rl_bottom_click_state.equals("3")) {
                    // 调用 免费查看内容 接口
                    doTask(rl_bottom_click_state);
                } else if (rl_bottom_click_state.equals("4")) {
                    doTask(rl_bottom_click_state);
                } else {
                    Log.e("sadkfksjahf", "------5-----");
                    String sign_ = TGmd5.getMD5(distributorid + id_);
                    showLoadingProgressDialog(FamousTeacherDetialActivity.this,"");
                    famousTeacherDetaiPresenter.doFamousSeek(distributorid, id_, sign_);
                    if (SystemUtils.isWiFi(FamousTeacherDetialActivity.this)) {
                        Log.e("sadkfksjahf", "------video_url-----"+video_url);
                        mederPlay(video_url);
                    } else {
                        palyNet(video_url);
                    }
                }
                break;
            case R.id.tv_go_zhibojian:// 进入直播间
                String sign_one = TGmd5.getMD5(distributorid + id_);
                famousTeacherDetaiPresenter.updateState(distributorid, id_, sign_one);
                Bundle bundle1 = new Bundle();
                bundle1.putString("teacherId", teacherDistributorID);
                bundle1.putInt("ktID", KTID);
                bundle1.putInt("KDBDistributorID", KDBDistributorID);
                bundle1.putString("GId", GID);
                bundle1.putString("theme", theme);
                openActivity(LiveDirectActivity.class, bundle1);
                break;
            case R.id.tv_opearte:// 报名,查看,等操作按钮
                doTask(rl_bottom_click_state);
                break;
            case R.id.tv_sned:
                type_comment = "1";
                String content = et_evaluate.getText().toString().trim();
                if (StringUtils.isEmpty(content)) {
                    MyToast.show(FamousTeacherDetialActivity.this, "请输入评论内容");
                } else {
                    String sign = TGmd5.getMD5(distributorid + id_ + content + "0");
                    CommentZanBean commentZanBean = new CommentZanBean(distributorid, id_, content, "0", sign);
                    famousTeacherDetaiPresenter.doCommentZan(commentZanBean);
                }
                break;
            case R.id.img_zan:
                type_comment = "2";
                openDialogZanShang();
                String tuan_ = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, "0");
                tv_tuanbi_num.setText("您剩余" + tuan_ + "团币");
                break;
            case R.id.btn_zanshang:
                String tuan_one = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, "0");
                String tuanbi_num = et_tuanbi.getText().toString().trim();
                String content_zan = et_content.getText().toString().trim();
                if (StringUtils.isEmpty(tuanbi_num)) {
                    MyToast.show(FamousTeacherDetialActivity.this, "输入团币");
                    return;
                } else if (Integer.parseInt(tuanbi_num) >= Integer.parseInt(tuan_one)) {
                    LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = LayoutInflater.from(this);
                    View view1 = inflater.inflate(R.layout.dialog_lack_conis, null);//自定义的布局文件
                    TextView tv_sure = (TextView) view1.findViewById(R.id.tv_sure);
                    TextView tv_cancel = (TextView) view1.findViewById(R.id.tv_cancel);

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
                            Intent intent = new Intent(FamousTeacherDetialActivity.this, RechargeMoneyActivity.class);
                            startActivity(intent);
                            mAlertDialog.dismiss();
                        }
                    });
//                    MyToast.show(FamousTeacherDetialActivity.this, "团币不足");
                    return;
                } else {
                    tuanb_or_comment = et_tuanbi.getText().toString().trim();
                    if (content_zan.equals("")) {
                        content_zan = "您的演讲好棒,忍不住赞赏！";
                    } else {
                        content_zan = et_content.getText().toString().trim();
                    }
                    String sign = TGmd5.getMD5(distributorid + id_ + content_zan + tuanbi_num);
                    CommentZanBean commentZanBean = new CommentZanBean(distributorid, id_, content_zan, tuanbi_num, sign);
                    famousTeacherDetaiPresenter.doCommentZan(commentZanBean);
                    closeDialogZanShang();
                }
                break;
            case R.id.rl_01:
                closeDialogZanShang();
                tuanb_or_comment = "0";
                break;
            case R.id.rl_intro:
                openDialogIntro();
                break;
            case R.id.ll_close:
                closeDialogIntro();
                break;
            case R.id.ll_img_content:
                closeDialogIntro();
                break;
//            case R.id.rl_all_baomming:
//                try {
//                    if (mediaPlayer != null) {
//                        if (mediaPlayer.isPlaying()) {
//                            mediaPlayer.pause();
//                        }
//                    }
//                    if (videoMediaPlyer != null) {
//                        if (videoMediaPlyer.isPlaying()) {
//                            videoMediaPlyer.pause();
//                        }
//                    }
//                } catch (Exception c) {
//                }
//                bundle.putString("id", id_);
//                openActivity(TuanYuanActivity.class, bundle);
//                break;
            case R.id.img_head:
                try {
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                        }
                    }
//                    if (videoMediaPlyer != null) {
//                        if (videoMediaPlyer.isPlaying()) {
//                            videoMediaPlyer.pause();
//                        }
//                    }
                } catch (Exception c) {
                }
                if (!teacherDistributorID.equals("")) {
                    bundle.putInt("distributorid", Integer.parseInt(teacherDistributorID));
                    openActivity(UserPersonalCenterActivity.class, bundle);
//                    finish();
                }
                break;
            case R.id.rl_turn:
                try {
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                        }
                    }
//                    if (videoMediaPlyer != null) {
//                        if (videoMediaPlyer.isPlaying()) {
//                            videoMediaPlyer.pause();
//                        }
//                    }
                } catch (Exception c) {
                }
                if (!teacherDistributorID.equals("")) {
                    bundle.putInt("distributorid", Integer.parseInt(teacherDistributorID));
                    openActivity(UserPersonalCenterActivity.class, bundle);
                }
                break;
        }
    }


    /**
     * 全屏之后，点击退出全屏 继续播放
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
//                if (videoMediaPlyer != null) {
//                    videoMediaPlyer.seekTo(data.getIntExtra("position", 0));
//                    if (!videoMediaPlyer.isPlaying()) {
//                        videoMediaPlyer.start();
//                    }
//                }
                break;
        }
    }

    /**
     * 初始化 ViewHolder
     */
    public void initViewHolder() {
        imageHeadEntityListViewDataAdapter = new ListViewDataAdapter<ImageHeadEntity>();
        imageHeadEntityListViewDataAdapter.setViewHolderClass(this, ImageHeadViewHolder.class);
        myGridView.setAdapter(imageHeadEntityListViewDataAdapter);

        commentListEntityListViewDataAdapter = new ListViewDataAdapter<CommentListEntity>();
        commentListEntityListViewDataAdapter.setViewHolderClass(this, CommentListViewHolder.class);
        CommentListViewHolder.setOnClassifyPostionClickListener(this);
        myListView.setAdapter(commentListEntityListViewDataAdapter);

    }

    // 弹出分享对话框
    private void openDialogShare() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, true);
    }

    // 关闭分享对话框
    private void closeDialogShare() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, false);

    }

    // 弹出点赞对话框
    private void openDialogZanShang() {
        performDialogAnimation(rl_dialog_root,
                ll_dialog, true);
    }

    // 关闭点赞对话框
    private void closeDialogZanShang() {
        performDialogAnimation(rl_dialog_root,
                ll_dialog, false);

    }

    // 弹出介绍对话框
    private void openDialogIntro() {
        performDialogAnimation(rl_img_intro_root,
                ll_img_intro, true);
    }

    // 关闭介绍对话框
    private void closeDialogIntro() {
        performDialogAnimation(rl_img_intro_root,
                ll_img_intro, false);

    }

    /**
     * 上拉加载更多数据，底部评论列表
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                y2 = event.getY();
                if (y1 - y2 > 100 && islast_one == true) {
                    if (pageindex < total_page) {
                        pageindex += 1;
                        load_more_progressbar.setVisibility(View.VISIBLE);
                        String sign_one = TGmd5.getMD5(id_ + pageindex);
                        famousTeacherDetaiPresenter.getCommentData(id_, pageindex + "", sign_one);
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
        }
        return false;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        famousTeacherDetaiPresenter.attach(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        if (view_super_player != null) {
            view_super_player.onPause();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (view_super_player != null) {
            view_super_player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (view_super_player != null && view_super_player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        try {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.stop();
                mediaPlayer = null;
            }
            if (view_super_player != null) {
                view_super_player.onDestroy();
            }
//            if (videoMediaPlyer != null) {
//                    videoMediaPlyer.reset();
//                    videoMediaPlyer.stop();
//                    videoMediaPlyer=null;
//            }
        } catch (Exception c) {
        }
        famousTeacherDetaiPresenter.dettach();
    }

    public Dialog guide_dialog;

    public void showGuideDialog(final String state, String str) {
        LayoutInflater inflater = LayoutInflater.from(this);
        if (guide_dialog==null){
            guide_dialog = new Dialog(this, R.style.Mydialog);// 创建自定义样式dialog
        }

        View view = inflater.inflate(R.layout.dialog_hint_daoyou, null);// 得到加载view
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(str);// 设置加载信息
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide_dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("state", state);
                Intent intent = new Intent(FamousTeacherDetialActivity.this, GuideCradMnagerActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide_dialog.dismiss();
            }
        });
//        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        guide_dialog.setCancelable(false);// 不可以用“返回键”取消
        guide_dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        guide_dialog.show();
    }
    public Dialog hint_dialog;
    public void showHintDialog(String msg) {
        LayoutInflater inflater = LayoutInflater.from(this);
        if (hint_dialog==null){
            hint_dialog = new Dialog(this, R.style.Mydialog);// 创建自定义样式dialog
        }

        View view = inflater.inflate(R.layout.dialog_hint_view, null);// 得到加载view
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(msg);// 设置加载信息
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hint_dialog.dismiss();
            }
        });
//        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        hint_dialog.setCancelable(false);// 不可以用“返回键”取消
        hint_dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        hint_dialog.show();
    }

    public void doTask(String statues_) {
        switch (statues_) {
            case "2":// 团币报名
                String guide_picurl = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL);
                String state = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
                final String tuanbi_one = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, "0");
                //导游证认证判断
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
                if (state.equals("1")){
                    if (guide_picurl.equals("") || guide_picurl == null && guide_picurl.length() == 0){
                        showGuideDialog(state,"请上传导游证！");
                    }else {
                        showHintDialog(getResources().getString(R.string.guide_certificate_audit));
                    }
                }else if (state.equals("3")){
                    showGuideDialog(state,"还没有上传导游证，是否去上传？");
                } else if (tingKeQuanID>0&&(tingKeQuanType==1||tingKeQuanType==2)){
                    LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = LayoutInflater.from(this);
                    View view = inflater.inflate(R.layout.dialog_tingkequan_bg, null);//自定义的布局文件
                    TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
                    TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
                    TextView message = (TextView) view.findViewById(R.id.message);
                    if (tingKeQuanTuanBi>=Integer.parseInt(baoming)){
                        message.setText("本课程你有一张"+tingKeQuanTuanBi+"团币抵用券,"+"可免费听课");

                    }else {
                        fufei = Integer.parseInt(baoming)-tingKeQuanTuanBi;
                        message.setText("本课程你有一张"+tingKeQuanTuanBi+"团币抵用券,"+"支付"+ fufei +"团币即可听课");
                    }


                    final AlertDialog mAlertDialog = builder.create();
                    mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
                    mAlertDialog.show();
                    mAlertDialog.getWindow().setContentView(view, pm);
                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAlertDialog.dismiss();
                        }
                    });
                    tv_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Integer.parseInt(tuanbi_one) >= (Integer.parseInt(baoming)-tingKeQuanTuanBi)){
                                String sign = TGmd5.getMD5(distributorid + id_);
                                showLoadingProgressDialog(FamousTeacherDetialActivity.this,"");
                                famousTeacherDetaiPresenter.doFamousSignUp(distributorid, id_, sign);
                                mAlertDialog.dismiss();
                            }else {
                                //团币不足请充值
                                LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                AlertDialog.Builder builder = new AlertDialog.Builder(FamousTeacherDetialActivity.this);
                                LayoutInflater inflater = LayoutInflater.from(FamousTeacherDetialActivity.this);
                                View view = inflater.inflate(R.layout.dialog_lack_conis, null);//自定义的布局文件
                                TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
                                TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

                                final AlertDialog mAlertDialog1 = builder.create();
                                mAlertDialog1.getWindow().setBackgroundDrawableResource(R.color.touming);
                                mAlertDialog1.show();
                                mAlertDialog1.getWindow().setContentView(view, pm);
                                tv_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mAlertDialog1.dismiss();
                                    }
                                });
                                tv_sure.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //跳转到充值页面
                                        Intent intent = new Intent(FamousTeacherDetialActivity.this, RechargeMoneyActivity.class);
                                        startActivity(intent);
                                        mAlertDialog1.dismiss();
                                    }
                                });
                                mAlertDialog.dismiss();
                            }
                        }
                    });

                }else if (Integer.parseInt(tuanbi_one) >= Integer.parseInt(baoming)) {
                    dialog = new Dialog(FamousTeacherDetialActivity.this,
                            R.style.Mydialog);
                    View view1 = View.inflate(FamousTeacherDetialActivity.this,
                            R.layout.delete_shop_dialog, null);
                    Button sure = (Button) view1.findViewById(R.id.sure);
                    Button cancle = (Button) view1.findViewById(R.id.cancle);
                    TextView tv_content = (TextView) view1.findViewById(R.id.tv_content);
                    tv_content.setText("该课程需"+baoming+"付币报名，确认报名吗？");
                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String sign = TGmd5.getMD5(distributorid + id_);
                            showLoadingProgressDialog(FamousTeacherDetialActivity.this,"");
                            famousTeacherDetaiPresenter.doFamousSignUp(distributorid, id_, sign);
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
                } else {
                    //团币不足请充值
                    LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = LayoutInflater.from(this);
                    View view = inflater.inflate(R.layout.dialog_lack_conis, null);//自定义的布局文件
                    TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
                    TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

                    final AlertDialog mAlertDialog = builder.create();
                    mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
                    mAlertDialog.show();
                    mAlertDialog.getWindow().setContentView(view, pm);
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
                            Intent intent = new Intent(FamousTeacherDetialActivity.this, RechargeMoneyActivity.class);
                            startActivity(intent);
                            mAlertDialog.dismiss();
                        }
                    });


//                    MyToast.show(FamousTeacherDetialActivity.this, "团币不足!");
                }
                break;
            case "1":// 免费报名
                String sign_ = TGmd5.getMD5(distributorid + id_);

                String guide_picur3 = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL);
                String state3 = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
                if (state3.equals("1")){
                    if (guide_picur3.equals("") || guide_picur3 == null && guide_picur3.length() == 0){
                        showGuideDialog(state3,"请上传导游证！");
                    }else {
                        showHintDialog(getResources().getString(R.string.guide_certificate_audit));
                    }
                }else if (state3.equals("3")){
                    showGuideDialog(state3,"还没有上传导游证，是否去上传？");
                }else {
                    showLoadingProgressDialog(FamousTeacherDetialActivity.this,"");
                    famousTeacherDetaiPresenter.doFamousSignUp(distributorid, id_, sign_);
                }
//                dialog = new Dialog(FamousTeacherDetialActivity.this,
//                        R.style.Mydialog);
//                View view1 = View.inflate(FamousTeacherDetialActivity.this,
//                        R.layout.delete_shop_dialog, null);
//                Button sure = (Button) view1.findViewById(R.id.sure);
//                Button cancle = (Button) view1.findViewById(R.id.cancle);
//                TextView tv_content = (TextView) view1.findViewById(R.id.tv_content);
//                tv_content.setText("确定免费报名吗？");
//                sure.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String sign_ = TGmd5.getMD5(distributorid + id_);
//                        famousTeacherDetaiPresenter.doFamousSignUp(distributorid, id_, sign_);
//                        dialog.dismiss();
//                    }
//                });
//                cancle.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.setContentView(view1);
//                dialog.show();
                break;
            case "4":// 团币查看内容
                String guide_picur2 = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL);
                String state2 = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
                final String tuanbi_two = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, "0");
                if (state2.equals("1")){
                    if (guide_picur2.equals("") || guide_picur2 == null && guide_picur2.length() == 0){
                        showGuideDialog(state2,"请上传导游证！");
                    }else {
                        showHintDialog(getResources().getString(R.string.guide_certificate_audit));
                    }
                }else if (state2.equals("3")){
                    showGuideDialog(state2,"还没有上传导游证，是否去上传？");
                }else if (tingKeQuanID>0&&(tingKeQuanType==1||tingKeQuanType==3)){
                    LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = LayoutInflater.from(this);
                    View view = inflater.inflate(R.layout.dialog_tingkequan_bg, null);//自定义的布局文件
                    TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
                    TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
                    TextView message = (TextView) view.findViewById(R.id.message);
                    if (tingKeQuanTuanBi>=Integer.parseInt(CKtuanbi)){
                        message.setText("本课程你有一张"+tingKeQuanTuanBi+"团币抵用券,"+"可免费听课");

                    }else {
                        fufei2 = Integer.parseInt(CKtuanbi)-tingKeQuanTuanBi;
                        message.setText("本课程你有一张"+tingKeQuanTuanBi+"团币抵用券,"+"支付"+ fufei2 +"团币即可听课");
                    }


                    final AlertDialog mAlertDialog = builder.create();
                    mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
                    mAlertDialog.show();
                    mAlertDialog.getWindow().setContentView(view, pm);
                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAlertDialog.dismiss();
                        }
                    });
                    tv_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Integer.parseInt(tuanbi_two) >= (Integer.parseInt(CKtuanbi)-tingKeQuanTuanBi)){
//                            if (Integer.parseInt(tuanbi_two) >= Integer.parseInt(baoming)){
                                String sign_ = TGmd5.getMD5(distributorid + id_);
                                showLoadingProgressDialog(FamousTeacherDetialActivity.this,"");
                                famousTeacherDetaiPresenter.doFamousSeek(distributorid, id_, sign_);
                                mAlertDialog.dismiss();
                            }else {
                                //团币不足请充值
                                LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                AlertDialog.Builder builder = new AlertDialog.Builder(FamousTeacherDetialActivity.this);
                                LayoutInflater inflater = LayoutInflater.from(FamousTeacherDetialActivity.this);
                                View view = inflater.inflate(R.layout.dialog_lack_conis, null);//自定义的布局文件
                                TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
                                TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

                                final AlertDialog mAlertDialog1 = builder.create();
                                mAlertDialog1.getWindow().setBackgroundDrawableResource(R.color.touming);
                                mAlertDialog1.show();
                                mAlertDialog1.getWindow().setContentView(view, pm);
                                tv_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mAlertDialog1.dismiss();
                                    }
                                });
                                tv_sure.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //跳转到充值页面
                                        Intent intent = new Intent(FamousTeacherDetialActivity.this, RechargeMoneyActivity.class);
                                        startActivity(intent);
                                        mAlertDialog1.dismiss();
                                    }
                                });
                                mAlertDialog.dismiss();
                            }
                        }
                    });

                }else if (Integer.parseInt(tuanbi_two) >= Integer.parseInt(CKtuanbi)) {
                    dialog = new Dialog(FamousTeacherDetialActivity.this,
                            R.style.Mydialog);
                    View view2 = View.inflate(FamousTeacherDetialActivity.this,
                            R.layout.delete_shop_dialog, null);
                    Button sure2 = (Button) view2.findViewById(R.id.sure);
                    Button cancle2 = (Button) view2.findViewById(R.id.cancle);
                    TextView tv_content2 = (TextView) view2.findViewById(R.id.tv_content);
                    tv_content2.setText("该课程需付币查看，确认查看吗？");
                    sure2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String sign_ = TGmd5.getMD5(distributorid + id_);
                            showLoadingProgressDialog(FamousTeacherDetialActivity.this,"");
                            famousTeacherDetaiPresenter.doFamousSeek(distributorid, id_, sign_);
                            dialog.dismiss();
                        }
                    });
                    cancle2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setContentView(view2);
                    dialog.show();
                } else {
                    LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = LayoutInflater.from(this);
                    View view = inflater.inflate(R.layout.dialog_lack_conis, null);//自定义的布局文件
                    TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
                    TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

                    final AlertDialog mAlertDialog = builder.create();
                    mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
                    mAlertDialog.show();
                    mAlertDialog.getWindow().setContentView(view, pm);
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
                            Intent intent = new Intent(FamousTeacherDetialActivity.this, RechargeMoneyActivity.class);
                            startActivity(intent);
                            mAlertDialog.dismiss();
                        }
                    });
//                    MyToast.show(FamousTeacherDetialActivity.this, "团币不足!");
                }
                break;
            case "3":// 免费查看内容
                String sign = TGmd5.getMD5(distributorid + id_);
                String guide_picur4 = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL);
                String state4 = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
                if (state4.equals("1")){
                    if (guide_picur4.equals("") || guide_picur4 == null && guide_picur4.length() == 0){
                        showGuideDialog(state4,"请上传导游证！");
                    }else {
                        showHintDialog(getResources().getString(R.string.guide_certificate_audit));
                    }
                }else if (state4.equals("3")){
                    showGuideDialog(state4,"还没有上传导游证，是否去上传？");
                }else {
                    showLoadingProgressDialog(FamousTeacherDetialActivity.this,"");
                    famousTeacherDetaiPresenter.doFamousSeek(distributorid, id_, sign);
                }



                break;
            case "5":
                MyToast.show(FamousTeacherDetialActivity.this, "已报名");
                break;
            case "6":
                MyToast.show(FamousTeacherDetialActivity.this, "人数已满");
                break;
            case "7":
                String sign_2 = TGmd5.getMD5(distributorid + id_);
                showLoadingProgressDialog(FamousTeacherDetialActivity.this,"");
                famousTeacherDetaiPresenter.doFamousSeek(distributorid, id_, sign_2);
                break;
        }
    }


    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        fubichakan=false;
        switch (state) {
            /**
             * 获取数据成功回调
             */
            case "1":
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();
                        is_study = array.get(1).toString();
                        is_new_user = array.get(3).toString();
                        JSONObject jsonObject_ = new JSONObject(data);
                        img_path = jsonObject_.getString("PicUrl");
                        String tingkequanStr=array.get(8).toString();
                        JSONObject tingKeQuanObj=new JSONObject(tingkequanStr);
                        tingKeQuanID=tingKeQuanObj.getInt("ID");
                        tingKeQuanTuanBi=tingKeQuanObj.getInt("TuanBi");
                        tingKeQuanType=tingKeQuanObj.getInt("Type");
                        voice_path = jsonObject_.getString("ClassSound");
                        if (!voice_path.equals("null") && voice_path.length() > 0) {
                            downvoice(Url.XIANSHANG_ROOT + voice_path);
                        } else {
                            rl_listening.setVisibility(View.GONE);
                        }
                        video_url = jsonObject_.getString("Content");
                        ZBTime = jsonObject_.getString("ZBTime");
                        String courseTime = ZBTime.replace("T", " ");
                        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Calendar c = Calendar.getInstance();
                            c.setTime(dfs.parse(courseTime));

                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
                            String coures_time = dateFormat.format(c.getTimeInMillis());
                            txt_course_start_date.setText(String.format(getResources().getString(R.string.text_course_time), coures_time));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        KTID = jsonObject_.getInt("ID");
                        KDBDistributorID = jsonObject_.getInt("KDBDistributorID");
                        teacherDistributorID = jsonObject_.getString("TeacherDistributorID");
                        options = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.teacherhead_fail_bg)            // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.teacherhead_fail_bg)     // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.teacherhead_fail_bg)          // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(true)                             // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(true)                               // 设置下载的图片是否缓存在SD卡中
                                .displayer(new RoundedBitmapDisplayer(360))      // 设置成圆角图片
                                .build();
                        ImageLoader.getInstance().displayImage(Url.ROOT + img_path + "?v=" + Calendar.getInstance().getTimeInMillis(), img_head, options);

                        share_img = Url.ROOT + img_path;
                        GID = jsonObject_.getString("GID");
                        baoming = jsonObject_.getString("BMTuanBi");
                        CKtuanbi = jsonObject_.getString("CKTuanBi");
                        theme = jsonObject_.getString("Theme");
                        share_title = jsonObject_.getString("Theme");
                        state_one = jsonObject_.getString("State");
                        String limit = jsonObject_.getString("People_All");
                        if (Integer.parseInt(limit) > 0) {
                            tv_limit.setText("人/限额" + limit + "人");
                        } else {
                            tv_limit.setText("人/无限制");
                        }
                        String kan_num = jsonObject_.getString("Hits");
                        tv_learn_num.setText(kan_num + "人看过");
                        String apply = jsonObject_.getString("People_Apply");
                        tv_num_acture.setText(apply);
                        teacher_name = jsonObject_.getString("TeacherName");
                        String TeacherInfo = jsonObject_.getString("TeacherInfo");
                        String ThemeInfo = jsonObject_.getString("ThemeInfo");
                        String crowd = jsonObject_.getString("Crowd");
                        share_content = jsonObject_.getString("ThemeInfo");
                        tv_name.setText(teacher_name);
                        tv_guider_intro.setText(TeacherInfo);
                        tv_proper_corwd_people.setText(crowd);
                        tv_guider_intro.post(new Runnable() {
                            @Override
                            public void run() {
                                teacher_intro_line = tv_guider_intro.getLineCount();
                                if (teacher_intro_line > 3) {
                                    tv_guider_intro.setMaxLines(3);
                                    img_teacher_more_intro.setVisibility(View.VISIBLE);
                                } else {
                                    img_teacher_more_intro.setVisibility(View.GONE);
                                }
                            }
                        });
                        String img_path = jsonObject_.getString("Banner3");

                        optionsone = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.famous_bg)            // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.famous_bg)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.famous_bg)            // 设置图片加载或解码过程中发生错误显示的图片
                                .build();
                        ImageLoader.getInstance().displayImage(Url.ROOT + img_path, img_backround, optionsone);

                        tv_prduction.setText(ThemeInfo);
                        tv_prduction.post(new Runnable() {
                            @Override
                            public void run() {
                                course_intro_line = tv_prduction.getLineCount();
                                if (course_intro_line > 3) {
                                    tv_prduction.setMaxLines(3);
                                    img_course_more_intro.setVisibility(View.VISIBLE);
                                } else {
                                    img_course_more_intro.setVisibility(View.GONE);
                                }
                            }
                        });


                        tv_theme_title.setText(theme);

                        tv_money.setText(array.get(4).toString());
                        tv_people_num.setText(array.get(5).toString());


                        if (state_one.equals("4") && is_study.equals("1")) {
                            ll_dianzan.setVisibility(View.VISIBLE);
                        }
                        switch (state_one) {
                            case "1":// 未开课
                                if (Integer.parseInt(baoming) > 0) {
                                    tv_tuanbi_num_pay.setText(baoming + "团币");
                                } else {
                                    tv_tuanbi_num_pay.setText("免费");
                                }
                                rl_notice.setVisibility(View.VISIBLE);
                                ll_daojishi.setVisibility(View.VISIBLE);
                                judgeTime(ZBTime);
                                if (is_new_user.equals("1")) {// 免费报名 新用户免费报名
                                    if (is_study.equals("1")) {
                                        tv_new_user.setVisibility(View.VISIBLE);
                                        tv_new_user.setText("(新用户免费观看)");
                                        tv_opearte.setText("已报名");
                                        rl_bottom_click_state = "5";//已报名
                                        tv_opearte.setTextColor(getResources().getColor(R.color.bg_white));
                                        tv_opearte.setBackgroundResource((R.drawable.duihuan_grey_conner_shape));
                                    } else {
                                        if (Integer.parseInt(limit) == 0) {
                                            rl_bottom_click_state = "1";//新用户 免费报名
                                            tv_new_user.setVisibility(View.VISIBLE);
                                            tv_new_user.setText("(新用户免费报名)");
                                            tv_opearte.setText("报名");
                                            tv_opearte.setTextColor(getResources().getColor(R.color.bg_balck_three));
                                            tv_opearte.setBackgroundResource((R.drawable.bg_conner_yellow_dialog_shape));
                                        } else {
                                            if (Integer.parseInt(apply) >= Integer.parseInt(limit)) {
                                                tv_new_user.setVisibility(View.VISIBLE);
                                                rl_bottom_click_state = "6";//人数已满
                                                tv_new_user.setText("(新用户免费观看)");
                                                tv_opearte.setText("人数已满");
                                                tv_opearte.setTextColor(getResources().getColor(R.color.bg_white));
                                                tv_opearte.setBackgroundResource((R.drawable.duihuan_grey_conner_shape));
                                            } else {
                                                rl_bottom_click_state = "1";//新用户 免费报名
                                                tv_new_user.setVisibility(View.VISIBLE);
                                                tv_new_user.setText("(新用户免费报名)");
                                                tv_opearte.setText("报名");
                                                tv_opearte.setTextColor(getResources().getColor(R.color.bg_balck_three));
                                                tv_opearte.setBackgroundResource((R.drawable.bg_conner_yellow_dialog_shape));
                                            }
                                        }
                                    }
                                } else {
                                    if (is_study.equals("1")) {
                                        rl_bottom_click_state = "5";//已报名
                                        tv_opearte.setText("已报名");
                                        tv_opearte.setTextColor(getResources().getColor(R.color.bg_white));
                                        tv_opearte.setBackgroundResource((R.drawable.duihuan_grey_conner_shape));
                                    } else {
                                        if (Integer.parseInt(limit) == 0) {
                                            if (Integer.parseInt(baoming) > 0) {
                                                rl_bottom_click_state = "2";//老用户花费团币报名
                                            } else {
                                                rl_bottom_click_state = "1";//老用户免费报名
                                            }
                                            tv_opearte.setText("报名");
                                            tv_opearte.setTextColor(getResources().getColor(R.color.bg_balck_three));
                                            tv_opearte.setBackgroundResource((R.drawable.bg_conner_yellow_dialog_shape));
                                        } else {
                                            if (Integer.parseInt(apply) >= Integer.parseInt(limit)) {
                                                tv_opearte.setText("人数已满");
                                                rl_bottom_click_state = "6";//人数已满
                                                tv_opearte.setTextColor(getResources().getColor(R.color.bg_white));
                                                tv_opearte.setBackgroundResource((R.drawable.duihuan_grey_conner_shape));
                                            } else {
                                                if (Integer.parseInt(baoming) > 0) {
                                                    rl_bottom_click_state = "2";//老用户花费团币报名
                                                } else {
                                                    rl_bottom_click_state = "1";//老用户免费报名
                                                }
                                                tv_opearte.setText("报名");
                                                tv_opearte.setTextColor(getResources().getColor(R.color.bg_balck_three));
                                                tv_opearte.setBackgroundResource((R.drawable.bg_conner_yellow_dialog_shape));
                                            }
                                        }
                                    }
                                }
                                break;
                            case "2":
                                if (Integer.parseInt(baoming) > 0) {
                                    tv_tuanbi_num_pay.setText(baoming + "团币");
                                } else {
                                    tv_tuanbi_num_pay.setText("免费");
                                }
                                rl_notice.setVisibility(View.VISIBLE);
                                ll_daojishi.setVisibility(View.VISIBLE);
                                judgeTime(ZBTime);
                                if (is_new_user.equals("1")) {// 免费报名 新用户免费报名
                                    if (is_study.equals("1")) {
                                        tv_new_user.setVisibility(View.VISIBLE);
                                        tv_new_user.setText("(新用户免费观看)");
                                        tv_opearte.setText("已报名");
                                        rl_bottom_click_state = "5";//已报名
                                        tv_opearte.setTextColor(getResources().getColor(R.color.bg_white));
                                        tv_opearte.setBackgroundResource((R.drawable.duihuan_grey_conner_shape));
                                    } else {
                                        if (Integer.parseInt(limit) == 0) {
                                            rl_bottom_click_state = "1";//新用户 免费报名
                                            tv_new_user.setVisibility(View.GONE);
                                            tv_opearte.setVisibility(View.GONE);
                                        } else {
                                            if (Integer.parseInt(apply) >= Integer.parseInt(limit)) {
                                                tv_new_user.setVisibility(View.VISIBLE);
                                                rl_bottom_click_state = "6";//人数已满
                                                tv_new_user.setText("(新用户免费观看)");
                                                tv_opearte.setText("人数已满");
                                                tv_opearte.setTextColor(getResources().getColor(R.color.bg_white));
                                                tv_opearte.setBackgroundResource((R.drawable.duihuan_grey_conner_shape));
                                            } else {
                                                rl_bottom_click_state = "1";//新用户 免费报名
                                                tv_new_user.setVisibility(View.VISIBLE);
                                                tv_new_user.setText("(新用户免费报名)");
                                                tv_opearte.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                } else {
                                    if (is_study.equals("1")) {
                                        rl_bottom_click_state = "5";//已报名
                                        tv_opearte.setText("已报名");
                                        tv_opearte.setTextColor(getResources().getColor(R.color.bg_white));
                                        tv_opearte.setBackgroundResource((R.drawable.duihuan_grey_conner_shape));
                                    } else {
                                        if (Integer.parseInt(limit) == 0) {
                                            if (Integer.parseInt(baoming) > 0) {
                                                rl_bottom_click_state = "2";//老用户花费团币报名
                                            } else {
                                                rl_bottom_click_state = "1";//老用户免费报名
                                            }
                                            tv_opearte.setVisibility(View.GONE);
                                        } else {
                                            if (Integer.parseInt(apply) >= Integer.parseInt(limit)) {
                                                tv_opearte.setText("人数已满");
                                                rl_bottom_click_state = "6";//人数已满
                                                tv_opearte.setTextColor(getResources().getColor(R.color.bg_white));
                                                tv_opearte.setBackgroundResource((R.drawable.duihuan_grey_conner_shape));
                                            } else {
                                                if (Integer.parseInt(baoming) > 0) {
                                                    rl_bottom_click_state = "2";//老用户花费团币报名
                                                } else {
                                                    rl_bottom_click_state = "1";//老用户免费报名
                                                }
                                                tv_opearte.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }
                                break;
                            case "3":// 进行中
                                if (Integer.parseInt(baoming) > 0) {
                                    tv_tuanbi_num_pay.setText(baoming + "团币");
                                } else {
                                    tv_tuanbi_num_pay.setText("免费");
                                }
                                if (is_study.equals("1") || distributorid.equals("22") || distributorid.equals(teacherDistributorID)) {
                                    rl_bottom_click_state = "5";//已报名
                                    tv_opearte.setText("已报名");
                                    tv_opearte.setTextColor(getResources().getColor(R.color.bg_white));
                                    tv_opearte.setBackgroundResource((R.drawable.duihuan_grey_conner_shape));
                                    if (GID != null && GID.length() > 0) {
                                        tv_go_zhibojian.setVisibility(View.VISIBLE);
                                        rl_notice.setVisibility(View.VISIBLE);
                                        tv_bottom_show.setVisibility(View.VISIBLE);
                                        tv_bottom_show.setText("开课中, 赶快进入直播间吧！");
                                    } else {
                                        tv_go_zhibojian.setVisibility(View.GONE);
                                        rl_notice.setVisibility(View.VISIBLE);
                                        tv_bottom_show.setVisibility(View.VISIBLE);
                                        tv_bottom_show.setText("开课中, 赶快加入报名吧！");
                                    }
                                } else {
                                    rl_notice.setVisibility(View.VISIBLE);
                                    tv_bottom_show.setVisibility(View.VISIBLE);
                                    tv_bottom_show.setText("开课中, 赶快加入报名吧！");
                                    if (is_new_user.equals("1")) {
                                        tv_new_user.setVisibility(View.VISIBLE);
                                        if (Integer.parseInt(limit) == 0) {
                                            rl_bottom_click_state = "1";//新用户 免费报名
                                            tv_new_user.setText("(新用户免费报名)");
                                            tv_opearte.setText("报名");
                                            tv_opearte.setTextColor(getResources().getColor(R.color.bg_balck_three));
                                            tv_opearte.setBackgroundResource((R.drawable.bg_conner_yellow_dialog_shape));
                                        } else {
                                            if (Integer.parseInt(apply) >= Integer.parseInt(limit)) {
                                                rl_bottom_click_state = "6";//人数已满
                                                tv_opearte.setText("人数已满");
                                                tv_new_user.setText("(新用户免费观看)");
                                                tv_opearte.setTextColor(getResources().getColor(R.color.bg_white));
                                                tv_opearte.setBackgroundResource((R.drawable.duihuan_grey_conner_shape));
                                                rl_notice.setVisibility(View.VISIBLE);
                                                tv_bottom_show.setVisibility(View.VISIBLE);
                                                tv_bottom_show.setText("开课中, 请等待视频上传...");
                                            } else {
                                                rl_bottom_click_state = "1";//新用户 免费报名
                                                tv_new_user.setText("(新用户免费报名)");
                                                tv_opearte.setText("报名");
                                                tv_opearte.setTextColor(getResources().getColor(R.color.bg_balck_three));
                                                tv_opearte.setBackgroundResource((R.drawable.bg_conner_yellow_dialog_shape));
                                            }
                                        }
                                    } else {
                                        if (Integer.parseInt(limit) == 0) {
                                            if (Integer.parseInt(baoming) > 0) {
                                                rl_bottom_click_state = "2";//老用户花费团币报名
                                            } else {
                                                rl_bottom_click_state = "1";//老用户免费报名
                                            }
                                            tv_opearte.setText("报名");
                                            tv_opearte.setTextColor(getResources().getColor(R.color.bg_balck_three));
                                            tv_opearte.setBackgroundResource((R.drawable.bg_conner_yellow_dialog_shape));
                                        } else {
                                            if (Integer.parseInt(apply) >= Integer.parseInt(limit)) {
                                                tv_opearte.setText("人数已满");
                                                rl_bottom_click_state = "6";//人数已满
                                                tv_opearte.setTextColor(getResources().getColor(R.color.bg_white));
                                                tv_opearte.setBackgroundResource((R.drawable.duihuan_grey_conner_shape));
                                                rl_notice.setVisibility(View.VISIBLE);
                                                tv_bottom_show.setVisibility(View.VISIBLE);
                                                tv_bottom_show.setText("开课中, 请等待视频上传...");
                                            } else {
                                                if (Integer.parseInt(baoming) > 0) {
                                                    rl_bottom_click_state = "2";//老用户花费团币报名
                                                } else {
                                                    rl_bottom_click_state = "1";//老用户免费报名
                                                }
                                                tv_opearte.setText("报名");
                                                tv_opearte.setTextColor(getResources().getColor(R.color.bg_balck_three));
                                                tv_opearte.setBackgroundResource((R.drawable.bg_conner_yellow_dialog_shape));
                                            }
                                        }
                                    }
                                }
                                break;
                            case "4":// 已结束
                                ll_ratingbar.setVisibility(View.VISIBLE);
                                if (array != null && array.length() > 6) {
                                    tv_evaluate_num.setText(String.valueOf(array.get(6)));
                                }
                                if (array != null && array.length() > 7) {
                                    setRatingBar((int) array.get(7));
                                }
                                if (Integer.parseInt(CKtuanbi) > 0) {
                                    tv_tuanbi_num_pay.setText(CKtuanbi + "团币");
                                } else {
                                    tv_tuanbi_num_pay.setText("免费");
                                }
                                img_paly.setVisibility(View.VISIBLE);
                                if (is_study.equals("1")) {
                                    tv_opearte.setVisibility(View.GONE);
                                    rl_bottom_click_state = "7";//已经查看，再次查看
                                } else {
                                    if (is_new_user.equals("1")) {
                                        rl_bottom_click_state = "3";// 新用户查看内容
                                        tv_new_user.setVisibility(View.VISIBLE);
                                        tv_new_user.setText("(新用户免费查看)");
                                        tv_opearte.setVisibility(View.GONE);
                                    } else {
                                        if (Integer.parseInt(CKtuanbi) > 0) {
                                            rl_bottom_click_state = "4";// 用户付币查看内容
                                            tv_opearte.setText("付币查看");
                                            fubichakan=true;
                                            rl_notice.setVisibility(View.VISIBLE);
                                            ll_paid_seek.setVisibility(View.VISIBLE);
                                            tv_opearte.setTextColor(getResources().getColor(R.color.bg_balck_three));
                                            tv_opearte.setBackgroundResource((R.drawable.bg_conner_yellow_dialog_shape));
                                        } else {
                                            tv_opearte.setVisibility(View.GONE);
                                            rl_bottom_click_state = "7";//免费产看，直接显示播放按钮，隐藏，操作按钮
                                        }
                                    }
                                }
                                break;
                        }

                        String urls = array.get(2).toString();
                        JSONArray array_ = new JSONArray(urls);
                        imageHeadEntityListViewDataAdapter.removeAll();
                        if (array_ != null && array_.length() > 0) {
                            rl_tuanyuan.setVisibility(View.VISIBLE);
                            if (array_.length() > 6) {
                                for (int i = 0; i < 6; i++) {
                                    JSONObject json_1 = array_.getJSONObject(i);
                                    String id = json_1.getString("ID");
                                    String img_path_ = json_1.getString("DistributorName");
                                    ImageHeadEntity imageHeadEntity = new ImageHeadEntity(id, img_path_);
                                    imageHeadEntityListViewDataAdapter.append(imageHeadEntity);
                                }
                            } else {
                                for (int i = 0; i < array_.length(); i++) {
                                    JSONObject json_2 = array_.getJSONObject(i);
                                    String id = json_2.getString("ID");
                                    String img_path_ = json_2.getString("DistributorName");
                                    ImageHeadEntity imageHeadEntity = new ImageHeadEntity(id, img_path_);
                                    imageHeadEntityListViewDataAdapter.append(imageHeadEntity);
                                }
                            }
                        } else {
                            rl_tuanyuan.setVisibility(View.GONE);
                        }
                    } else if (status.equals("0")) {
                        MyToast.show(FamousTeacherDetialActivity.this, jsonObject.getString("message"));
                        if (jsonObject.getString("message").equals("请登录")) {
                            openActivity(LoginActivity.class);
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            /**
             * 评论列表成功回调
             */
            case "2":
                load_more_progressbar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        String datas_ = jsonArray.get(0).toString();
                        JSONObject jsonObject_datas = new JSONObject(datas_);
                        total_page = jsonObject_datas.getInt("TotalPages");
                        TotalItems = jsonObject_datas.getInt("TotalItems");
                        String commentNumber = String.format(getResources().getString(R.string.text_comment_number), TotalItems);
                        txt_comment_number.setText(commentNumber);
                        String items = jsonObject_datas.getString("Items");
                        if (mIsUp == false) {
                            commentListEntityListViewDataAdapter.removeAll();
                        } else if (mIsUp == true) {
                            // 上拉加载，不清空 listViewDataAdapter
                        }
                        JSONArray jsonArray_ = new JSONArray(items);
                        if (jsonArray_ != null && jsonArray_.length() > 0) {
                            rl_none.setVisibility(View.GONE);
                            ll_visiable.setVisibility(View.VISIBLE);
                            for (int i = 0; i < jsonArray_.length(); i++) {
                                JSONObject json_item = jsonArray_.getJSONObject(i);
                                String id = json_item.getString("ID");
                                String DistributorID = json_item.getString("DistributorID");
                                String name = json_item.getString("DistributorName");
                                String Content = json_item.getString("Content");
                                String CreateTime = json_item.getString("CreateTime");
                                String TuanBi = json_item.getString("TuanBi");
                                String path_id = json_item.getString("DistributorID");
                                String state_two = json_item.getString("State");
                                CommentListEntity commentListEntity = new CommentListEntity(id, path_id, CreateTime, name, Content, TuanBi, state_two);
                                commentListEntity.setDistributorID(DistributorID);
                                commentListEntityListViewDataAdapter.append(commentListEntity);
                            }
                        } else {
                            rl_none.setVisibility(View.VISIBLE);
                            ll_visiable.setVisibility(View.GONE);
                            rl_fabu.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            /**
             * 点赞评论成功回调
             */
            case "3":
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        if (type_comment.equals("1")) {
                            MyToast.show(FamousTeacherDetialActivity.this, "评论成功");
                            et_evaluate.setText("");
                            mIsUp = false;
                            String sign_one = TGmd5.getMD5(id_ + "1");
                            famousTeacherDetaiPresenter.getCommentData(id_, "1", sign_one);
                            myListView.smoothScrollToPosition(0);
                        } else if (type_comment.equals("2")) {
                            mIsUp = false;
                            MyToast.show(FamousTeacherDetialActivity.this, "赞赏成功");
                            et_tuanbi.setText("");
                            et_content.setText("");
                            String num = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, "0");
                            int num_one = Integer.parseInt(num) - Integer.parseInt(tuanb_or_comment);
                            int num_2 = Integer.parseInt(tuanb_or_comment) + Integer.parseInt(tv_money.getText().toString().trim());
                            tv_money.setText(num_2 + "");
                            int num_3 = Integer.parseInt(tv_people_num.getText().toString().trim()) + 1;
                            tv_people_num.setText(num_3 + "");
                            PreferenceHelper.write(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, num_one + "");
                            String sign_one = TGmd5.getMD5(id_ + "1");
                            famousTeacherDetaiPresenter.getCommentData(id_, "1", sign_one);
                            myListView.smoothScrollToPosition(0);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            /**
             * 课程查看成功回调
             */
            case "4":
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        rl_notice.setVisibility(View.GONE);
                        tv_opearte.setVisibility(View.GONE);
                        if (rl_bottom_click_state.equals("4")) {
                            String tuanbi_four = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, "0");
                            int tuanbi_ = Integer.parseInt(tuanbi_four) - Integer.parseInt(CKtuanbi);
                            PreferenceHelper.write(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi_ + "");
                        }
                        String sign_ = TGmd5.getMD5(distributorid + id_);
                        famousTeacherDetaiPresenter.getData(distributorid, id_, sign_);
                        if (SystemUtils.isWiFi(FamousTeacherDetialActivity.this)) {
                            mederPlay(video_url);
                        } else {
                            palyNet(video_url);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            /**
             * 报名听课成功回调
             */
            case "5":
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        if (rl_bottom_click_state.equals("2")) {
                            String tuanbi_three = PreferenceHelper.readString(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, "0");
                            int tuanbi_ = Integer.parseInt(tuanbi_three) - Integer.parseInt(baoming);
                            PreferenceHelper.write(FamousTeacherDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi_ + "");
                        }

                        String sign_ = TGmd5.getMD5(distributorid + id_);
                        famousTeacherDetaiPresenter.getData(distributorid, id_, sign_);

                        Message msg = new Message();
                        msg.what = 1009;
                        mHandler.sendMessage(msg);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            /**
             * 播放点击量成功回调
             */
            case "6":


                break;
            /**
             * 更改学习状态
             */
            case "7":

                break;
            case "delcomment":
                //刷新数据
                commentListEntityListViewDataAdapter.getDataList().remove(del_layer_index);
                String commentNumber = String.format(getResources().getString(R.string.text_comment_number), --TotalItems);
                txt_comment_number.setText(commentNumber);
                commentListEntityListViewDataAdapter.notifyDataSetChanged();
                break;
        }
    }
    private boolean fubichakan=false;
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        Log.e("ajsdfhkaa", "-------------"+respanse);
        closeLoadingProgressDialog();
        switch (state) {
            /**
             * 获取数据失败回调
             */
            case "1":
                closeLoadingProgressDialog();
                MyToast.show(FamousTeacherDetialActivity.this, "请求失败");
                break;
            /**
             * 评论列表失败回调
             */
            case "2":
                MyToast.show(FamousTeacherDetialActivity.this, "请求失败");
                break;
            /**
             * 点赞评论失败回调
             */
            case "3":
                MyToast.show(FamousTeacherDetialActivity.this, "请求失败");
                break;

            /**
             * 课程查看失败回调
             */
            case "4":
                try {
                    JSONObject jsonObject_fail = new JSONObject(respanse);
                    message_6 = jsonObject_fail.getString("message");

                    Message msg = new Message();
                    msg.what = 1011;
                    mHandler.sendMessage(msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            /**
             * 报名听课失败回调
             */
            case "5":
                try {
                    JSONObject jsonObject_fail = new JSONObject(respanse);
                    message_5 = jsonObject_fail.getString("message");

                    String sign_ = TGmd5.getMD5(distributorid + id_);
                    famousTeacherDetaiPresenter.getData(distributorid, id_, sign_);

                    Message msg = new Message();
                    msg.what = 1010;
                    mHandler.sendMessage(msg);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            /**
             * 播放点击量成功回调
             */
            case "6":
                MyToast.show(FamousTeacherDetialActivity.this, "请求失败");
                break;
            case "delcomment":
                MyToast.show(FamousTeacherDetialActivity.this, "删除失败");
                break;
        }
    }

    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }

    @Override
    public void onClassifyPostionClick(CommentListEntity itemData, int postion) {
        if (distributorid.equals(itemData.getDistributorID())) {
            del_layer_index = postion;
            showpopUpWindow(itemData.getId());
        }
    }

    private PopupWindow popupWindow;

    public void showpopUpWindow(final String talkcommentId) {
        View contentview = LayoutInflater.from(this)
                .inflate(R.layout.delete_comment_view, null);
        LinearLayout ll_del_view = (LinearLayout) contentview.findViewById(R.id.ll_del_view);
        popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        TextView txt_confirm_del = (TextView) contentview.findViewById(R.id.txt_confirm_del);
        TextView txt_cancel_del = (TextView) contentview.findViewById(R.id.txt_cancel_del);
        txt_confirm_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刷新界面
                String del_comment_sign = TGmd5.getMD5(distributorid + talkcommentId);
                famousTeacherDetaiPresenter.delcomment(distributorid, String.valueOf(talkcommentId), del_comment_sign);
                popupWindow.dismiss();
            }
        });
        txt_cancel_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ll_del_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(img_back, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 网络链接监听类
     */
    @Override
    public void onWifi() {
        MyToast.makeText(this, "当前网络环境是WIFI", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMobile() {
        MyToast.makeText(this, "当前网络环境是手机网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisConnect() {
        MyToast.makeText(this, "网络链接断开", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoAvailable() {
        MyToast.makeText(this, "无网络链接", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callBackListener() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            is_vioce_palying = "0";
            img_play_voice.setVisibility(View.GONE);
            img_play_voice_one.setVisibility(View.VISIBLE);
            img_play_voice.setImageResource(R.drawable.voice_play_anim);
            animationDrawable = (AnimationDrawable) img_play_voice.getDrawable();
            animationDrawable.stop();
        }
    }

    //时间线程
    class TimeThread extends Thread {
        @Override
        public void run() {
            while (TimeTreadFlag) {
                handler.post(new Runnable() {
                    public void run() {
                        if (mediaPlayer != null) {
                            CurrentTime = mediaPlayer.getCurrentPosition();
                        } else {
                            CurrentTime = 0;
                        }
                    }
                });
                String Smm;
                String Sss;
                int CureetTimeSS = CurrentTime / 1000;
                int mm = CureetTimeSS / 60;
                int ss = CureetTimeSS % 60;
                if (mm / 10 == 0) {
                    Smm = "0" + mm;
                } else {
                    Smm = "" + mm;
                }
                if (ss / 10 == 0) {
                    Sss = "0" + ss;
                } else {
                    Sss = "" + ss;
                }
                timeString = Smm + ":" + Sss;
                handler.post(new Runnable() {
                    public void run() {
                        tv_jindu.setText(timeString);
                    }
                });
                Message msg = new Message();
                msg.what = 0;
                mHandler.sendMessage(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    //设置显示语音总时间
    public void showTotalTime() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(download_url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // prepare 通过异步的方式装载媒体资源
            mediaPlayer.prepareAsync();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = new Message();
            message.what = 1005;
            mHandler.sendMessage(message);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downvoice(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获取SDCard根目录
                    String sdcard = Environment.getExternalStorageDirectory() + "/";
                    // 这个是要保存的目录
                    try {
                        URL fileUrl = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
                        InputStream is = conn.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        // 根据当前时间给下载的文件重新命名
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date curDate = new Date(System.currentTimeMillis());
                        String fileName = "vedio.mp3";
                        File dir = new File(sdcard);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File file = new File(sdcard, fileName);
                        file.createNewFile();
                        FileOutputStream output = new FileOutputStream(file);
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = bis.read(buffer)) != -1) {
                            output.write(buffer, 0, len);
                        }
                        output.flush();
                        output.close();
                        is.close();
                        // 下载完成，传递值7代表下载完成
                        Message msg = new Message();
                        msg.what = 7;
                        mHandler.sendMessage(msg);
                    } catch (Exception e) {
                        //如果下载出现异常，传递值8代表下载失败
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**************
     * 拖动SeekBar 且播放指定位置的音乐
     *****************/
    class seekBarListener implements SeekBar.OnSeekBarChangeListener {

        //拖动前音乐播放的状态,拖动前音乐是什么状态，拖动后就是什么状态
        private boolean state = false;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

            if (mediaPlayer != null) {
                //得到拖动前的状态
                if (mediaPlayer.isPlaying()) {
                    state = true;
                    mediaPlayer.pause();//暂停播放
                } else {
                    state = false;
                }
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //当拖动停止时
            int dest = seekBar.getProgress();
            if (mediaPlayer != null) {
                int mMax = mediaPlayer.getDuration();
                int sMax = seekBar.getMax();
                mediaPlayer.seekTo(mMax * dest / sMax);
                if (state) {
                    mediaPlayer.start();
                }
                Message msg = new Message();
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        }
    }

    /**
     * 计算倒计时时间
     *
     * @param zbTime
     */
    public void judgeTime(String zbTime) {
        String[] str = zbTime.split("T");
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_e = dfs.format(new Date());// 当前时间
        String date_b = str[0] + " " + str[1];
        try {
            Date begin = dfs.parse(date_b);
            Date end = dfs.parse(date_e);
            between = (begin.getTime() - end.getTime()) / 1000;//除以1000是为了转换成秒

            Message message = new Message();
            message.what = 1000;
            mHandler.sendMessage(message);

            startTimer();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //=======================倒计时模块================================//

    private int mTimerId = 120;

    private TimerTask timerTask;

    private Timer timer;


    /**
     * 开始倒计时
     */
    private void startTimer() {
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = Constants.EXECUTE_LOADING;
                    msg.arg1 = (int) (--mTimerId);
                    mHandler.sendMessage(msg);
                }
            };
            timer = new Timer();
            // schedule(TimerTask task, long delay, long period)
            // 安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。
            // task - 所要安排的任务。
            // delay - 执行任务前的延迟时间，单位是毫秒。
            // period - 执行各后续任务之间的时间间隔，单位是毫秒。
            timer.schedule(timerTask, 1000, 1000);
        }
    }

    /**
     * 结束计时
     */
    private void closeTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        mTimerId = 120;
        mHandler.sendEmptyMessage(Constants.EXECUTE_FINISH);
    }


    /**
     * 设置星星
     *
     * @param num
     */
    public void setRatingBar(double num) {
        show_ratingbar.setClickable(false);
        if (num == 0) {
            show_ratingbar.setRating(0.0f);
        } else if (num > 0 && num < 1) {
            show_ratingbar.setRating(0.5f);
        } else if (num == 1) {
            show_ratingbar.setRating(1);
        } else if (num > 1 && num < 2) {
            show_ratingbar.setRating(1.5f);
        } else if (num == 2) {
            show_ratingbar.setRating(2);
        } else if (num > 2 && num < 3) {
            show_ratingbar.setRating(2.5f);
        } else if (num == 3) {
            show_ratingbar.setRating(3);
        } else if (num > 3 && num < 4) {
            show_ratingbar.setRating(3.5f);
        } else if (num == 4) {
            show_ratingbar.setRating(4);
        } else if (num > 4 && num < 5) {
            show_ratingbar.setRating(4.5f);
        } else {
            show_ratingbar.setRating(5);
        }
    }


    public String judgeMp3Or4(String url) {
        String string_ = "";// 默认 mp4
        if (url.length() > 0 && url.contains(".")) {
            String[] str = url.split("\\.");
            int postion = str.length - 1;
            if (str[postion].equals("mp3")) {
                string_ = "mp3";
            } else if (str[postion].equals("mp4")) {
                string_ = "mp4";
            }
        }
        return string_;
    }


    /**
     * 记得修改，播放处理
     *
     * @param url
     */
    public void mederPlay(String url) {
        /**
         * 播放时，显示点赞
         */
        if (View.GONE == ll_dianzan.getVisibility()) {
            ll_dianzan.setVisibility(View.VISIBLE);
        } else {
            ll_dianzan.setVisibility(View.VISIBLE);
        }
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                is_vioce_palying = "0";
                img_play_voice.setVisibility(View.GONE);
                img_play_voice_one.setVisibility(View.VISIBLE);
                img_play_voice.setImageResource(R.drawable.voice_play_anim);
                animationDrawable = (AnimationDrawable) img_play_voice.getDrawable();
                animationDrawable.stop();
            }
        }
        if (judgeMp3Or4(url).equals("mp4") || judgeMp3Or4(url).equals("mp3")) {

            rl_video_gong.setVisibility(View.GONE);
            view_super_player.setVisibility(View.VISIBLE);
            String sign_ = TGmd5.getMD5(distributorid + id_);
            Log.e("sadkfksjahf", "------请求前-----");
            famousTeacherDetaiPresenter.doPlayTimes(distributorid, id_, sign_);
            Log.e("sadkfksjahf", "------请求后-----");
            view_super_player.setNetChangeListener(true)//设置监听手机网络的变化
                    .setOnNetChangeListener(this)//实现网络变化的回调
                    .onPrepared(new SuperPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared() {
                            Log.e("sadkfksjahf", "------准备完成开始播放-----");
                            /**
                             * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                             */
                        }
                    }).onComplete(new Runnable() {
                @Override
                public void run() {
                    Log.e("sadkfksjahf", "------播放完成-----");
                    /**
                     * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                     */
                }
            }).onInfo(new SuperPlayer.OnInfoListener() {
                @Override
                public void onInfo(int what, int extra) {
                    Log.e("sadkfksjahf", "------相关信息-----");
                    /**
                     * 监听视频的相关信息。
                     */

                }
            }).onError(new SuperPlayer.OnErrorListener() {
                @Override
                public void onError(int what, int extra) {
                    Log.e("sadkfksjahf", "------播放失败-----");
                    /**
                     * 监听视频播放失败的回调
                     */

                }//设置视频的titleName
            }).setTitle(url).play(url);//开始播放视频
            view_super_player.setScaleType(SuperPlayer.SCALETYPE_FITXY);
            view_super_player.setPlayerWH(0, view_super_player.getMeasuredHeight());//设置竖屏的时候屏幕的高度，如果不设置会切换后按照16:9的高度重置
        } else {
            MyToast.show(FamousTeacherDetialActivity.this, "不支持的播放格式");
        }
//        videoMediaPlyer = new MediaPlayer();
//
//        if (judgeMp3Or4(url).equals("mp4")) {
//            rl_video_gong.setVisibility(View.GONE);
//            rl_video_visiable.setVisibility(View.VISIBLE);
//            videoSuperPlayer.loadAndPlay(videoMediaPlyer, url, 0, false);
//            videoSuperPlayer.setVideoPlayCallback(new MyVideoPlayCallback(videoSuperPlayer, url));
//            view_super_player.updatePlayer();
//            VideoSuperPlayer.setOnSelectClickListener(this);
//            String sign_ = TGmd5.getMD5(distributorid + id_);
//            famousTeacherDetaiPresenter.doPlayTimes(distributorid, id_, sign_);
//
//        } else if (judgeMp3Or4(url).equals("mp3")) {
//            rl_video_gong.setVisibility(View.GONE);
//            rl_video_visiable.setVisibility(View.VISIBLE);
//            videoSuperPlayer.loadAndPlay(videoMediaPlyer, url, 0, false);
//            videoSuperPlayer.setVideoPlayCallback(new MyVideoPlayCallback(videoSuperPlayer, url));
//            view_super_player.updatePlayer();
//            VideoSuperPlayer.setOnSelectClickListener(this);
//            String sign_ = TGmd5.getMD5(distributorid + id_);
//            famousTeacherDetaiPresenter.doPlayTimes(distributorid, id_, sign_);
//        } else {
//            MyToast.show(FamousTeacherDetialActivity.this, "不支持的播放格式");
//        }
    }

    /**
     * 播放
     */
    public void palyNet(final String url) {
        dialog_paly = new Dialog(FamousTeacherDetialActivity.this, R.style.Mydialog);
        View view1 = View.inflate(FamousTeacherDetialActivity.this,
                R.layout.dialog_paly_net, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_paly.dismiss();
                mederPlay(url);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_paly.dismiss();
            }
        });
        dialog_paly.setContentView(view1);
        dialog_paly.show();
    }

    private class MyVideoPlayCallback implements VideoSuperPlayer.VideoPlayCallbackImpl {

        private VideoSuperPlayer mSuperVideoPlayer;
        private String url;

        public MyVideoPlayCallback(VideoSuperPlayer mSuperVideoPlayer, String url) {
            this.mSuperVideoPlayer = mSuperVideoPlayer;
            this.url = url;
        }

        @Override
        public void onCloseVideo() {

        }

        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                Intent intent = new Intent(FamousTeacherDetialActivity.this, FullVideoActivity.class);
                intent.putExtra("video", url);
                intent.putExtra("position", mSuperVideoPlayer.getCurrentPosition());
                startActivityForResult(intent, 100);
            }
        }

        @Override
        public void onPlayFinish() {

        }
    }

    @Override
    public void OnSelectClickListener(String state) {
        if (state.equals("play")) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    is_vioce_palying = "0";
                    img_play_voice.setVisibility(View.GONE);
                    img_play_voice_one.setVisibility(View.VISIBLE);
                    img_play_voice.setImageResource(R.drawable.voice_play_anim);
                    animationDrawable = (AnimationDrawable) img_play_voice.getDrawable();
                    animationDrawable.stop();
                }
            }
        }
    }

    //退出登录
    public void showQuitDialog() {
        dialog_go = new Dialog(FamousTeacherDetialActivity.this, R.style.Mydialog);
        View view1 = View.inflate(FamousTeacherDetialActivity.this,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText("确定进入直播间?");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_go.dismiss();
                openActivity(LoginActivity.class);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_go.dismiss();
            }
        });
        dialog_go.setContentView(view1);
        dialog_go.show();
    }
}