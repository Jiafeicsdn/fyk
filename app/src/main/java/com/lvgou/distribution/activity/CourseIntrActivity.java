package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.presenter.UpDoDateStatePresenter;
import com.lvgou.distribution.utils.ACache;
import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.fragment.BaseFragment;
import com.lvgou.distribution.fragment.CourseDiscussFragment;
import com.lvgou.distribution.fragment.CourseIntrFragment;
import com.lvgou.distribution.presenter.CommentTeacherPresenter;
import com.lvgou.distribution.presenter.TeacherDetailPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.AdViewpagerUtil;
import com.lvgou.distribution.utils.PopWindows;
import com.lvgou.distribution.utils.ScrollableLayout;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CommentTeacherView;
import com.lvgou.distribution.view.TeacherDetailView;
import com.lvgou.distribution.view.UpDoDateStateView;
import com.superplayer.library.SuperPlayer;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.ScreenUtils;
import com.xdroid.common.utils.SystemUtils;
import com.lvgou.distribution.utils.MyToast;
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

/**
 * Created by Administrator on 2017/4/5.
 * 课程简介
 */

public class CourseIntrActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, TeacherDetailView, SuperPlayer.VoiceEventListener, SuperPlayer.OnNetChangeListener, CommentTeacherView, UpDoDateStateView {
    private String[] TITLE = new String[]{"简介", "讨论区"};
    private TeacherDetailPresenter teacherDetailPresenter;
    private CommentTeacherPresenter commentTeacherPresenter;
    private String id;
    private CourseIntrFragment courseIntrFragment;
    private CourseDiscussFragment courseDiscussFragment;
    private String videoUrl;
    private String distributorid;
    private String star;
    private ScrollableLayout sl_root;
    private String isLeaning = "0";
    private String courseState;
    private UpDoDateStatePresenter upDoDateStatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_intr);
        teacherDetailPresenter = new TeacherDetailPresenter(this);
        commentTeacherPresenter = new CommentTeacherPresenter(this);
        upDoDateStatePresenter = new UpDoDateStatePresenter(this);
        id = getIntent().getStringExtra("id");
        mcache.remove("livechatbanner");
        initView();
        initDatas();
        initClick();
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        if (view_super_player != null) {
            view_super_player.onPause();
        }
        if (view_super_player22 != null) {
            view_super_player22.onPause();
        }
    }

    @Override
    public void onBackPressed() {
        if (view_super_player != null && view_super_player.onBackPressed()) {
            return;
        }
        if (view_super_player22 != null && view_super_player22.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    private ArrayList<HashMap<String, Object>> finishList;//已经下载完成了的

    private void initDatas() {
        star = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STAR);//当前等级
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + id);
        showLoadingProgressDialog(this, "");
        teacherDetailPresenter.teacherDetail(distributorid, id, sign);
    }

    private ViewPager viewPager;
    private List<BaseFragment> fragmentList;
    private MyPagerAdapter myPagerAdapter;
    private SlidingTabLayout tabLayout_4;
    private RelativeLayout rl_introduction;
    private LinearLayout ll_bottom;
    private RelativeLayout rl_back;
    private ViewPager title_vp;
    private LinearLayout ly_dots;
    private SuperPlayer view_super_player;
    private SuperPlayer view_super_player22;
    private ImageView app_video_playxx;
    private RelativeLayout rl_scroll_button;
    private ImageView iv_title;//如果只有一张图片就显示否则显示viewpager
    private TextView tv_sned;//发表评论
    private EditText et_evaluate;//编辑评论
    private TextView tv_apply;
    private LinearLayout ll_chakan;//查看
    private LinearLayout ll_download;//下载
    private ImageView im_download;//下载图标
    private TextView tv_download;//下载
    private TextView tv_fubichakan;//付币查看

    public TextView getStateTV() {
        return tv_apply;
    }

    public LinearLayout getLLChakan() {
        return ll_chakan;
    }

    public LinearLayout getLLDownload() {
        return ll_download;
    }

    public ImageView getImDownload() {
        return im_download;
    }

    public TextView getTVDownload() {
        return tv_download;
    }

    public TextView getTvFBchakan() {
        return tv_fubichakan;
    }

    public RelativeLayout getImgPlay() {
        return app_video_bottom_boxxx;
    }

    public void setVideoUrl(String url) {
        videoUrl = url;
    }

    public RelativeLayout getRlDialogRoot() {
        return rl_dialog_root;
    }

    public LinearLayout getLlDialog() {
        return ll_dialog;
    }

    public RelativeLayout getRl01() {
        return rl_01;
    }

    public EditText getEttuanbi() {
        return et_tuanbi;
    }

    public TextView getTuanbinum() {
        return txt_tuanbi_num;
    }

    public EditText getEtcontent() {
        return et_content;
    }

    public Button getBtnzanshagn() {
        return btn_zanshang;
    }

    public TextView getShikan() {
        return tv_shikan;
    }

    public RelativeLayout getIntroduction() {
        return rl_introduction;
    }

    private RelativeLayout rl_dialog_root;
    private LinearLayout ll_dialog;
    private RelativeLayout rl_01;
    private EditText et_tuanbi;
    private TextView txt_tuanbi_num;
    private EditText et_content;
    private Button btn_zanshang;
    private RelativeLayout rl_about;
    private RelativeLayout rl_share;
    private RelativeLayout rl_dialog_share_root;
    private LinearLayout ll_dialog_share_cotent;
    private RelativeLayout rl_qq;
    private RelativeLayout rl_qzone;
    private RelativeLayout rl_weixin;
    private RelativeLayout rl_pengyou;
    private RelativeLayout rl_dismiss;
    private RelativeLayout rl_shikan;
    private RelativeLayout app_video_bottom_boxxx;
    private ImageView view_jky_player_fullscreenxx;
    private TextView tv_shikan;
    //------
    private TextView tv_spit;
    private RelativeLayout rl_title;
    private float avatarTop;
    private float hearderMaxHeight;
    private ImageView im_gettuanbi;
    private ImageView im_once_course;

    private void initView() {
        im_once_course = (ImageView) findViewById(R.id.im_once_course);
        String OneActClick = PreferenceHelper.readString(CourseIntrActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONECOURSECLICK, "0");
        if (OneActClick.equals("0")) {
            im_once_course.setVisibility(View.VISIBLE);
        }

        im_gettuanbi = (ImageView) findViewById(R.id.im_gettuanbi);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_title.getBackground().setAlpha(0);
        tv_spit = (TextView) findViewById(R.id.tv_spit);
        sl_root = (ScrollableLayout) findViewById(R.id.sl_root);
        tv_shikan = (TextView) findViewById(R.id.tv_shikan);
        view_jky_player_fullscreenxx = (ImageView) findViewById(R.id.view_jky_player_fullscreenxx);
        app_video_bottom_boxxx = (RelativeLayout) findViewById(R.id.app_video_bottom_boxxx);
        rl_shikan = (RelativeLayout) findViewById(R.id.rl_shikan);
        rl_qq = (RelativeLayout) findViewById(R.id.rl_qq);
        rl_qzone = (RelativeLayout) findViewById(R.id.rl_qzone);
        rl_weixin = (RelativeLayout) findViewById(R.id.rl_weixin);
        ll_dialog_share_cotent = (LinearLayout) findViewById(R.id.ll_dialog_share_cotent);
        rl_dialog_share_root = (RelativeLayout) findViewById(R.id.rl_dialog_share_root);
        rl_share = (RelativeLayout) findViewById(R.id.rl_share);
        rl_pengyou = (RelativeLayout) findViewById(R.id.rl_pengyou);
        rl_dismiss = (RelativeLayout) findViewById(R.id.rl_dismiss);
        rl_about = (RelativeLayout) findViewById(R.id.rl_about);
        rl_dialog_root = (RelativeLayout) findViewById(R.id.rl_dialog_root);
        ll_dialog = (LinearLayout) findViewById(R.id.ll_dialog);
        rl_01 = (RelativeLayout) findViewById(R.id.rl_01);
        et_tuanbi = (EditText) findViewById(R.id.et_tuanbi);
        txt_tuanbi_num = (TextView) findViewById(R.id.txt_tuanbi_num);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_zanshang = (Button) findViewById(R.id.btn_zanshang);
        tv_apply = (TextView) findViewById(R.id.tv_apply);
        ll_chakan = (LinearLayout) findViewById(R.id.ll_chakan);
        ll_download = (LinearLayout) findViewById(R.id.ll_download);
        im_download = (ImageView) findViewById(R.id.im_download);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_fubichakan = (TextView) findViewById(R.id.tv_fubichakan);
        rl_scroll_button = (RelativeLayout) findViewById(R.id.rl_scroll_button);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        view_super_player = (SuperPlayer) findViewById(R.id.view_super_player);
        view_super_player22 = (SuperPlayer) findViewById(R.id.view_super_player22);
        tv_sned = (TextView) findViewById(R.id.tv_sned);
        et_evaluate = (EditText) findViewById(R.id.et_evaluate);
        view_super_player.setListener(this);
        view_super_player22.setListener(this);
        app_video_playxx = (ImageView) findViewById(R.id.app_video_playxx);
        title_vp = (ViewPager) findViewById(R.id.title_vp);
        ly_dots = (LinearLayout) findViewById(R.id.ly_dots);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_introduction = (RelativeLayout) findViewById(R.id.rl_introduction);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        viewPager = (ViewPager) findViewById(R.id.vp);
        fragmentList = new ArrayList<>();
        fragmentList.clear();
        courseIntrFragment = new CourseIntrFragment();
        fragmentList.add(courseIntrFragment);
        courseDiscussFragment = new CourseDiscussFragment();
        Bundle bundle = new Bundle();
        bundle.putString("teacherId", id);
        courseDiscussFragment.setArguments(bundle);
        fragmentList.add(courseDiscussFragment);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout_4 = (SlidingTabLayout) findViewById(R.id.tl_4);
        tabLayout_4.setViewPager(viewPager);
        viewPager.setCurrentItem(0);
        sl_root.getHelper().setCurrentScrollableContainer(fragmentList.get(0));
        sl_root.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int translationY, int maxY) {
                translationY = -translationY;
                if (avatarTop == 0) {
                    avatarTop = tv_spit.getTop();
                }
                if (0 > avatarTop + translationY) {
                    tv_spit.setVisibility(View.VISIBLE);
                } else {
                    tv_spit.setVisibility(View.GONE);
                }
                if (hearderMaxHeight == 0) {
                    hearderMaxHeight = rl_scroll_button.getTop();
                }
                int alpha = 0;
                int baseAlpha = 60;
                if (0 > avatarTop + translationY) {
                    alpha = Math.min(255, (int) (Math.abs(avatarTop + translationY) * (255 - baseAlpha) / (hearderMaxHeight - avatarTop) + baseAlpha));
                }
                float zz = (float) 215.0;
                int alp = (int) (255 * alpha / zz);
                rl_title.getBackground().setAlpha(alp);
                tv_spit.setTextColor(Color.argb(alp, 255, 255, 255));
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (view_super_player != null) {
            view_super_player.onConfigurationChanged(newConfig);
        }
        if (view_super_player22 != null) {
            view_super_player22.onConfigurationChanged(newConfig);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            rl_scroll_button.setVisibility(View.GONE);
            if (posStr.equals("0")) {
                rl_introduction.setVisibility(View.GONE);
            } else if (posStr.equals("1")) {
                ll_bottom.setVisibility(View.GONE);
            }
            im_gettuanbi.setVisibility(View.GONE);
            sl_root.isCanScroll(false);
            view_jky_player_fullscreenxx.setImageResource(R.drawable.ic_not_fullscreen);
            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl_shikan.getLayoutParams();//取控件textView当前的布局参数
            layoutParams.height = display.getHeight();// 控件的高强制设成
            rl_shikan.setLayoutParams(layoutParams);
        } else {
            //竖屏
            sl_root.isCanScroll(true);
            rl_scroll_button.setVisibility(View.VISIBLE);
            if (posStr.equals("0")) {
                rl_introduction.setVisibility(View.VISIBLE);
            } else if (posStr.equals("1")) {
                ll_bottom.setVisibility(View.VISIBLE);
            }
            im_gettuanbi.setVisibility(View.VISIBLE);
            view_jky_player_fullscreenxx.setImageResource(R.drawable.ic_enlarge);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl_shikan.getLayoutParams();//取控件textView当前的布局参数
            layoutParams.height = (int) ScreenUtils.dpToPx(this, 240);// 控件的高强制设成
            rl_shikan.setLayoutParams(layoutParams);
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int iheight = (int) ScreenUtils.dpToPx(this, 240);
            view_super_player.setPlayerWH(width, iheight);
        }
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        app_video_playxx.setOnClickListener(this);
        tv_sned.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_share.setOnClickListener(this);
        rl_qq.setOnClickListener(this);
        rl_qzone.setOnClickListener(this);
        rl_weixin.setOnClickListener(this);
        rl_pengyou.setOnClickListener(this);
        rl_dismiss.setOnClickListener(this);
        view_jky_player_fullscreenxx.setOnClickListener(this);
        im_gettuanbi.setOnClickListener(this);
        im_once_course.setOnClickListener(this);

    }


    public void notifyTitle(String totalitems) {
        TITLE[1] = "讨论区(" + totalitems + ")";
        tabLayout_4.notifyDataSetChanged();
    }

    private String share_url = Url.XIANSHANG_ROOT + "/study/teacherdetail?id=";
    private String share_title = "";
    private String share_img = "";
    private String share_content = "";

    @Override
    public void onClick(View v) {
        UMImage image = new UMImage(CourseIntrActivity.this, share_img);
        switch (v.getId()) {
            case R.id.rl_back:
                if (view_super_player != null && view_super_player.onBackPressed()) {
                    return;
                } else if (view_super_player22 != null && view_super_player22.onBackPressed()) {
                    return;
                } else {
                    try {
                        if (mediaPlayer != null) {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.pause();
                            }
                        }
                    } catch (Exception c) {
                    }

                    finish();
                }
                break;
            case R.id.app_video_playxx:
                //需要修改学习状态
                if (courseState.equals("4") && isLeaning.equals("0")) {
                    showLoadingProgressDialog(this, "");
                    String sign = TGmd5.getMD5(distributorid + id + "");
                    upDoDateStatePresenter.upDoDateState(distributorid, id, sign);
                } else {

                    //先判断是否当前视频已经完成下载，然后判断当前视频是否存在，改变url
                    finishList = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("downloadfinish" + distributorid);
                    if (null == finishList) {
                        finishList = new ArrayList<HashMap<String, Object>>();
                    }
                    boolean finishdown = false;
                    for (HashMap<String, Object> hashMap : finishList) {
                        if (hashMap.get("Comment").toString().equals(videoUrl)) {
                            finishdown = true;
                            break;
                        }
                    }
                    if (finishdown) {
                        String downStr = videoUrl;
                        String name = TGmd5.getMD5(downStr);
                        if (downStr.endsWith(".mp3") || downStr.endsWith(".MP3")) {
                            name = name + ".mp3";
                            view_super_player22.setVisibility(View.VISIBLE);
                            view_jky_player_fullscreenxx.setVisibility(View.GONE);
                            view_super_player22.setview_jky_player_fullscreen();
                            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                            int width = wm.getDefaultDisplay().getWidth();
                            int iheight = (int) ScreenUtils.dpToPx(this, 0);
                            view_super_player22.setPlayerWH(width, iheight);
                        } else {
                            view_super_player.setVisibility(View.VISIBLE);
                            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                            int width = wm.getDefaultDisplay().getWidth();
                            int iheight = (int) ScreenUtils.dpToPx(this, 240);
                            view_super_player.setPlayerWH(width, iheight);
                            name = name + ".mp4";
                        }
                        String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
                        videoUrl = SDPath + name;
                        showLoadingProgressDialog(this, "");
                        mederPlay(videoUrl, false);//第二个参数，是否监听网络变化
                    } else {
                        if (SystemUtils.isWiFi(CourseIntrActivity.this)) {
                            //如果是wifi
                            showLoadingProgressDialog(this, "");
                            mederPlay(videoUrl, true);
                        } else {
                            //非wifi
                            palyNet(videoUrl);
                        }
                    }
                }


                break;
            case R.id.tv_sned://发表评论
                String trim = et_evaluate.getText().toString().trim();
                if (trim == null || trim.equals("")) {
                    MyToast.makeText(this, "请填写评论内容！", Toast.LENGTH_SHORT).show();
                    break;
                }
                String sign1 = TGmd5.getMD5(distributorid + id + trim + 0);
                showLoadingProgressDialog(this, "");
                commentTeacherPresenter.commentTeacher(distributorid, id, trim, 0, sign1);
                break;
            case R.id.rl_about:
                showpicDialog();
                break;
            case R.id.rl_share:
                openDialogShare();
                break;
            case R.id.rl_qq:
                UMWeb web = new UMWeb(share_url + id);
                web.setTitle(share_title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(CourseIntrActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                closeDialogShare();
                break;
            case R.id.rl_qzone:
                UMWeb web1 = new UMWeb(share_url + id);
                web1.setTitle(share_title);//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription(share_content);//描述
                new ShareAction(CourseIntrActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();

                closeDialogShare();
                break;
            case R.id.rl_weixin:
                UMWeb web2 = new UMWeb(share_url + id);
                web2.setTitle(share_title);//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription(share_content);//描述
                new ShareAction(CourseIntrActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                closeDialogShare();
                break;
            case R.id.rl_pengyou:
                UMWeb web3 = new UMWeb(share_url + id);
                web3.setTitle(share_title);//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription(share_content);//描述
                new ShareAction(CourseIntrActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();

                closeDialogShare();
                break;
            case R.id.rl_dismiss:
                closeDialogShare();
                break;
            case R.id.view_jky_player_fullscreenxx:
                //横竖屏切换
                toggleFullScreen();
                break;
            case R.id.im_gettuanbi:
                showPopWindow();
                break;
            case R.id.im_once_course:
                PreferenceHelper.write(CourseIntrActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONECOURSECLICK, "1");
                im_once_course.setVisibility(View.GONE);
                break;
        }
    }

    private void showPopWindow() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pop_gettuanbi, null);
        TextView tv_recharge = (TextView) inflate.findViewById(R.id.tv_recharge);//标题
        RelativeLayout rl_cancel = (RelativeLayout) inflate.findViewById(R.id.rl_cancel);
        final PopWindows popWindows = new PopWindows(this, this, inflate);
        popWindows.showPopWindowBottom();
        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
            }
        });
        tv_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseIntrActivity.this, RechargeMoneyActivity.class);
                startActivity(intent);
                popWindows.cleanPopAlpha();
            }
        });

    }

    /**
     * 设置播放视频的是否是全屏
     */
    public void toggleFullScreen() {
        if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {// 转小屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        } else {// 转全屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private int getScreenOrientation() {
        int rotation = getWindowManager().getDefaultDisplay()
                .getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
                && height > width
                || (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)
                && width > height) {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        } else {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }


    public void showpicDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_bg_pic, null);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);

    }


    private AdViewpagerUtil adViewpagerUtil;
    private int lunbo = 0;

    @Override
    public void OnTeacherDetailSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            courseIntrFragment.setDatas(jsa);
            JSONObject jsonObject = jsa.getJSONObject(0);
            isLeaning = jsa.get(7).toString();
            courseState = jsonObject.get("State").toString();
            share_title = jsonObject.get("Theme").toString();
            tv_spit.setText(share_title);
            share_content = jsonObject.getString("ThemeInfo");
//            videoUrl = jsonObject.get("Content").toString();
            String courseState = jsonObject.get("State").toString();
            String reviewType = jsonObject.get("ReviewType").toString();
            String banner3 = "";
            String img_path = jsonObject.getString("PicUrl");
            banner3 = jsonObject.get("Banner3").toString();
            Log.e("akdshfjhad", "----------"+banner3 );
            mcache.put("livechatbanner",banner3);
            share_img = Url.ROOT + img_path;
            if (courseState.equals("4")) {
                //已经结束并且回顾类型是音频  1视频2音频3直播间
                if (reviewType.equals("2") || reviewType.equals("3")) {
                    if (banner3.contains(",")) {
                        String[] spl = banner3.split(",");
                        for (String s : spl) {
                            pictureUrls.add(Url.ROOT + s);
                        }
                        iv_title.setVisibility(View.GONE);
                        title_vp.setVisibility(View.VISIBLE);
                        ly_dots.setVisibility(View.VISIBLE);
                        lunbo = 1;
                        if (adViewpagerUtil == null) {
                            adViewpagerUtil = new AdViewpagerUtil(CourseIntrActivity.this, title_vp, ly_dots, 8, pictureUrls.size(), pictureUrls);
                            adViewpagerUtil.setIsAuto(true);
                            adViewpagerUtil.initVps();
                        }
                    } else {
                        iv_title.setVisibility(View.VISIBLE);
                        title_vp.setVisibility(View.GONE);
                        ly_dots.setVisibility(View.GONE);
                        Glide.with(CourseIntrActivity.this).load(Url.ROOT + banner3).error(R.mipmap.pictures_no).into(iv_title);
                    }
                } else {
                    iv_title.setVisibility(View.VISIBLE);
                    title_vp.setVisibility(View.GONE);
                    ly_dots.setVisibility(View.GONE);
                    if (banner3.contains(",")) {
                        String[] spl = banner3.split(",");
                        Glide.with(CourseIntrActivity.this).load(Url.ROOT + spl[0]).error(R.mipmap.pictures_no).into(iv_title);
                    } else {
                        Glide.with(CourseIntrActivity.this).load(Url.ROOT + banner3).error(R.mipmap.pictures_no).into(iv_title);
                    }
                }

            } else {
                iv_title.setVisibility(View.VISIBLE);
                title_vp.setVisibility(View.GONE);
                ly_dots.setVisibility(View.GONE);
                if (banner3.contains(",")) {
                    String[] spl = banner3.split(",");
                    Glide.with(CourseIntrActivity.this).load(Url.ROOT + spl[0]).error(R.mipmap.pictures_no).into(iv_title);
                } else {
                    Glide.with(CourseIntrActivity.this).load(Url.ROOT + banner3).error(R.mipmap.pictures_no).into(iv_title);
                }
            }
            pictureUrls.clear();


//            JSONArray jsonArray = jsa.getJSONArray(0);
            /*for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsoo = jsonArray.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                courseStuInfo.add(map1);
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<HashMap<String, Object>> courseStuInfo = new ArrayList<>();

    @Override
    public void OnTeacherDetailFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();

    }

    @Override
    public void closeTeacherDetailProgress() {

    }

    private MediaPlayer mediaPlayer = null;
    private String is_vioce_palying = "0";// 0 为播放 1  正在播放

    @Override
    public void callBackListener() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            is_vioce_palying = "0";
            /*img_play_voice.setVisibility(View.GONE);
            img_play_voice_one.setVisibility(View.VISIBLE);
            img_play_voice.setImageResource(R.drawable.voice_play_anim);
            animationDrawable = (AnimationDrawable) img_play_voice.getDrawable();
            animationDrawable.stop();*/
        }
    }

    @Override
    public void OnCommentTeacherSuccCallBack(String state, String respanse) {
        //发表评论成功
        closeLoadingProgressDialog();
        MyToast.makeText(this, "发表评论成功！", Toast.LENGTH_SHORT).show();
        et_evaluate.setText("");
        courseDiscussFragment.onRefresh();
    }

    @Override
    public void OnCommentTeacherFialCallBack(String state, String respanse) {
        //发表评论失败
        closeLoadingProgressDialog();
        MyToast.makeText(this, "发表评论失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeCommentTeacherProgress() {

    }

    @Override
    public void OnUpDoDateStateSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();

        //先判断是否当前视频已经完成下载，然后判断当前视频是否存在，改变url
        finishList = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("downloadfinish" + distributorid);
        if (null == finishList) {
            finishList = new ArrayList<HashMap<String, Object>>();
        }
        boolean finishdown = false;
        for (HashMap<String, Object> hashMap : finishList) {
            if (hashMap.get("Comment").toString().equals(videoUrl)) {
                finishdown = true;
                break;
            }
        }
        if (finishdown) {
            String downStr = videoUrl;
            String name = TGmd5.getMD5(downStr);
            if (downStr.endsWith(".mp3") || downStr.endsWith(".MP3")) {
                name = name + ".mp3";
                view_super_player22.setVisibility(View.VISIBLE);
                view_jky_player_fullscreenxx.setVisibility(View.GONE);
                view_super_player22.setview_jky_player_fullscreen();
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                int width = wm.getDefaultDisplay().getWidth();
                int iheight = (int) ScreenUtils.dpToPx(this, 0);
                view_super_player22.setPlayerWH(width, iheight);
            } else {
                name = name + ".mp4";
                view_super_player.setVisibility(View.VISIBLE);
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                int width = wm.getDefaultDisplay().getWidth();
                int iheight = (int) ScreenUtils.dpToPx(this, 240);
                view_super_player.setPlayerWH(width, iheight);
            }
            String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
            videoUrl = SDPath + name;
            showLoadingProgressDialog(this, "");
            mederPlay(videoUrl, false);//第二个参数，是否监听网络变化
        } else {
            if (SystemUtils.isWiFi(CourseIntrActivity.this)) {
                //如果是wifi
                showLoadingProgressDialog(this, "");
                mederPlay(videoUrl, true);
            } else {
                //非wifi
                palyNet(videoUrl);
            }
        }

    }

    @Override
    public void OnUpDoDateStateFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeUpDoDateStateProgress() {

    }

    /**
     * ViewPager 适配器
     */

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position];
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }

        //----------------------------
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            rl_introduction.setVisibility(View.VISIBLE);
            ll_bottom.setVisibility(View.GONE);
            im_gettuanbi.setVisibility(View.VISIBLE);
            posStr = "0";
        } else if (position == 1) {
            rl_introduction.setVisibility(View.GONE);
            ll_bottom.setVisibility(View.VISIBLE);
            im_gettuanbi.setVisibility(View.GONE);
            posStr = "1";
        }
        sl_root.getHelper().setCurrentScrollableContainer(fragmentList.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private String posStr = "0";//判断当前在哪个fragment
    private ArrayList<String> pictureUrls = new ArrayList<>();


    //---------播放

    /**
     * 记得修改，播放处理
     *
     * @param url
     */
    public void mederPlay(String url, boolean boo) {
        /**
         * 播放时，显示点赞
         */
       /* if (View.GONE == ll_dianzan.getVisibility()) {
            ll_dianzan.setVisibility(View.VISIBLE);
        } else {
            ll_dianzan.setVisibility(View.VISIBLE);
        }*/
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                is_vioce_palying = "0";
                /*img_play_voice.setVisibility(View.GONE);
                img_play_voice_one.setVisibility(View.VISIBLE);
                img_play_voice.setImageResource(R.drawable.voice_play_anim);
                animationDrawable = (AnimationDrawable) img_play_voice.getDrawable();
                animationDrawable.stop();*/
            }
        }
        if (judgeMp3Or4(url).equals("mp4")) {
            view_super_player.setVisibility(View.VISIBLE);
//            String sign_ = TGmd5.getMD5(distributorid + id_);
            Log.e("sadkfksjahf", "------请求前-----");
            //播放点击量
//            famousTeacherDetaiPresenter.doPlayTimes(distributorid, id_, sign_);
            Log.e("sadkfksjahf", "------请求后-----");
            view_super_player.setNetChangeListener(boo)//设置监听手机网络的变化
                    .setOnNetChangeListener(this)//实现网络变化的回调
                    .onPrepared(new SuperPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared() {
                            closeLoadingProgressDialog();
                            app_video_bottom_boxxx.setVisibility(View.GONE);
                            title_vp.setVisibility(View.GONE);
                            ly_dots.setVisibility(View.GONE);
                            iv_title.setVisibility(View.GONE);
                            Log.e("sadkfksjahf", "------准备完成开始播放-----");
                            /*
                             * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                             */
                        }
                    }).onComplete(new Runnable() {
                @Override
                public void run() {
                    Log.e("sadkfksjahf", "------播放完成-----");
                    /*
                     * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                     */
                }
            }).onInfo(new SuperPlayer.OnInfoListener() {
                @Override
                public void onInfo(int what, int extra) {
                    Log.e("sadkfksjahf", "------相关信息-----");
                    /*
                     * 监听视频的相关信息。
                     */

                }
            }).onError(new SuperPlayer.OnErrorListener() {
                @Override
                public void onError(int what, int extra) {
                    if (pictureUrls.size() > 0) {
                        title_vp.setVisibility(View.VISIBLE);
                        ly_dots.setVisibility(View.VISIBLE);
                    } else {
                        iv_title.setVisibility(View.VISIBLE);
                    }
                    closeLoadingProgressDialog();
                    app_video_bottom_boxxx.setVisibility(View.VISIBLE);
                    Log.e("sadkfksjahf", "------播放失败-----");
                    /**
                     * 监听视频播放失败的回调
                     */

                }//设置视频的titleName
            }).setTitle(url).play(url);//开始播放视频
            view_super_player.setScaleType(SuperPlayer.SCALETYPE_WRAPCONTENT);
            view_super_player.setPlayerWH(0, view_super_player.getMeasuredHeight());//设置竖屏的时候屏幕的高度，如果不设置会切换后按照16:9的高度重置
        } else if (judgeMp3Or4(url).equals("mp3")) {
            view_super_player22.setVisibility(View.VISIBLE);
            view_jky_player_fullscreenxx.setVisibility(View.GONE);
            view_super_player22.setview_jky_player_fullscreen();
//            String sign_ = TGmd5.getMD5(distributorid + id_);
            Log.e("sadkfksjahf", "------请求前-----");
            //播放点击量
//            famousTeacherDetaiPresenter.doPlayTimes(distributorid, id_, sign_);
            Log.e("sadkfksjahf", "------请求后-----");
            view_super_player22.setNetChangeListener(boo)//设置监听手机网络的变化
                    .setOnNetChangeListener(this)//实现网络变化的回调
                    .onPrepared(new SuperPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared() {
                            closeLoadingProgressDialog();
                            app_video_bottom_boxxx.setVisibility(View.GONE);
                            view_super_player22.setview_jky_player_fullscreen();
//                            title_vp.setVisibility(View.GONE);
//                            ly_dots.setVisibility(View.GONE);
//                            iv_title.setVisibility(View.GONE);
                            Log.e("sadkfksjahf", "------准备完成开始播放-----");
                            /*
                             * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                             */
                        }
                    }).onComplete(new Runnable() {
                @Override
                public void run() {
                    Log.e("sadkfksjahf", "------播放完成-----");
                    /*
                     * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                     */
                }
            }).onInfo(new SuperPlayer.OnInfoListener() {
                @Override
                public void onInfo(int what, int extra) {
                    Log.e("sadkfksjahf", "------相关信息-----");
                    /*
                     * 监听视频的相关信息。
                     */

                }
            }).onError(new SuperPlayer.OnErrorListener() {
                @Override
                public void onError(int what, int extra) {
                    if (pictureUrls.size() > 0) {
//                        title_vp.setVisibility(View.VISIBLE);
//                        ly_dots.setVisibility(View.VISIBLE);
                    } else {
//                        iv_title.setVisibility(View.VISIBLE);
                    }
                    closeLoadingProgressDialog();
                    app_video_bottom_boxxx.setVisibility(View.VISIBLE);
                    Log.e("sadkfksjahf", "------播放失败-----");
                    /**
                     * 监听视频播放失败的回调
                     */

                }//设置视频的titleName
            }).setTitle(url).play(url);//开始播放视频
            view_super_player22.setScaleType(SuperPlayer.SCALETYPE_FITXY);
            int iheight = (int) ScreenUtils.dpToPx(this, 0);
            view_super_player22.setPlayerWH(0, iheight);//设置竖屏的时候屏幕的高度，如果不设置会切换后按照16:9的高度重置

        } else {
            closeLoadingProgressDialog();
            MyToast.show(CourseIntrActivity.this, "不支持的播放格式");
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

    private Dialog dialog_paly;

    /**
     * 播放
     */
    public void palyNet(final String url) {
        dialog_paly = new Dialog(CourseIntrActivity.this, R.style.Mydialog);
        View view1 = View.inflate(CourseIntrActivity.this,
                R.layout.dialog_paly_net, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_paly.dismiss();
                showLoadingProgressDialog(CourseIntrActivity.this, "");
                mederPlay(url, true);
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

    public void updataUrl(String url) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.stop();
                mediaPlayer = null;
            }
            tv_shikan.setVisibility(View.GONE);
            if (lunbo == 1) {
                title_vp.setVisibility(View.VISIBLE);
                ly_dots.setVisibility(View.VISIBLE);
            } else {
                iv_title.setVisibility(View.VISIBLE);
            }
            app_video_bottom_boxxx.setVisibility(View.VISIBLE);
            if (view_super_player != null) {
                view_super_player.showBottomControl(false);
                view_super_player.onDestroy();
            }
            if (view_super_player22 != null) {
                view_jky_player_fullscreenxx.setVisibility(View.VISIBLE);
                view_super_player22.showBottomControl(false);
                view_super_player22.onDestroy();
            }

        } catch (Exception c) {
        }
        videoUrl = url;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.stop();
                mediaPlayer = null;
            }
            if (view_super_player != null) {
                view_super_player.onDestroy();
            }
            if (view_super_player22 != null) {
                view_super_player22.onDestroy();
            }

        } catch (Exception c) {
        }
    }

    public UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            String sign = TGmd5.getMD5(distributorid);
            getTuanBi(distributorid, sign);
//            MyToast.makeText(BaseActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            MyToast.makeText(CourseIntrActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            MyToast.makeText(CourseIntrActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 分享成功，获取团币
     *
     * @param distributorid
     * @param sign
     */
    public void getTuanBi(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getShareTuanBi(CourseIntrActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                MyToast.show(CourseIntrActivity.this, jsonObject1.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
