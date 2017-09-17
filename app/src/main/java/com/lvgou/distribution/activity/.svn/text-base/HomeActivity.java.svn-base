package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.presenter.HomePagePresenter;
import com.lvgou.distribution.utils.MyToast;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.BaseView;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.eventbus.EventBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Snow on 2016/5/20
 * 底部切换栏
 */
public class HomeActivity extends TabActivity implements BaseView {

    private TabHost mTabHost;
    @ViewInject(R.id.home_radio_button_group)
    private LinearLayout home_radio_button_group;
    //    private RadioGroup radioGroup;
    @ViewInject(R.id.home_tab_index)
    private RadioButton radio_index;
    @ViewInject(R.id.home_tab_make_any)
    private RadioButton radio_make_any;
    @ViewInject(R.id.home_tab_college)
    private RadioButton radio_college;
    /*  @ViewInject(R.id.home_tab_temai)
      private RadioButton radio_temai;*/
    @ViewInject(R.id.home_tab_my)
    private RadioButton radio_my;
    @ViewInject(R.id.view_points)
    private View view_potions;
    @ViewInject(R.id.txt_no_read_count)
    private TextView txt_no_read_count;
    @ViewInject(R.id.btn_badge_view)
    private Button btn_badge_view;
    private static final String TAB_INDEX = "MAIN_ACTIVITY";// 主页
    private static final String TAB_MAKE = "MAKE_ACTIVITY";// 订单
    private static final String TAB_COLLEGE = "COLLEGE_ACTVITIY";// 任务
    private static final String TAB_MY = "CENTRAL_ACTIVITY";// 个人中心
//    private static final String TAB_TEMAI = "TEMAI";// 特卖

    private String selection_postion = "0";
    private String distributorid = "";
    private int getNewestDistributorId = 1;
    private HomePagePresenter imFmPersenter;
    @ViewInject(R.id.im_once_image)
    private ImageView im_once_image;
    @ViewInject(R.id.im_once_act)
    private ImageView im_once_act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        ActivitySwitcher.animationIn(findViewById(R.id.home_view), getWindowManager());
        ViewUtils.inject(this);
        EventBus.getDefault().register(this);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        imFmPersenter = new HomePagePresenter(HomeActivity.this);
        IntentFilter filter = new IntentFilter("com.lvgou.distribution.home");
        registerReceiver(broadcastReceiver, filter);
        initView();
        /* distributorId 	是 	long 	导游ID
    type 	是 	int 	设备类型：1=IOS，2=安卓
    ippath 	是 	string 	设备IP地址
    errorurl 	是 	string 	接口地址
    appintro 	是 	string 	设备信息描述
    errorintro 	是 	string 	错误内容
    sign 	是 	string 	用户名*/


        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(String permission) {
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selection_postion = bundle.getString("selection_postion");
        }
        switch (Integer.parseInt(selection_postion)) {
            case 0:
                mTabHost.setCurrentTabByTag(TAB_INDEX);
                initTextColor();
                radio_index.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                radio_index.setChecked(true);
                break;
            case 1:
                mTabHost.setCurrentTabByTag(TAB_MAKE);
                initTextColor();
                radio_make_any.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                radio_make_any.setChecked(true);
                break;
            case 2:
                mTabHost.setCurrentTabByTag(TAB_MY);
                initTextColor();
                radio_my.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                radio_my.setChecked(true);
                break;
            case 3:
                mTabHost.setCurrentTabByTag(TAB_COLLEGE);
                initTextColor();
                radio_college.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                radio_college.setChecked(true);
                break;
           /* case 4:
                mTabHost.setCurrentTabByTag(TAB_TEMAI);
                initTextColor();
                radio_temai.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                radio_temai.setChecked(true);
                break;*/
        }

//        Utils.getSystemVersionCode();
//        try {
//         System.out.println("====================" + Utils.getVersionName(this));
//            System.out.println("====================" + Utils.getIp(this));
//            System.out.println("====================" + Utils.getAvailMemory(this));
//            System.out.println("====================" + Utils.getNetworkState(this));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            selection_postion = bundle.getString("selection_postion");
        }
        String isover = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
        String state = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
        boolean isTrue = isRZDialog(state, isover);
        switch (Integer.parseInt(selection_postion)) {
            case 0:
                mTabHost.setCurrentTabByTag(TAB_INDEX);
                initTextColor();
                radio_index.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                radio_index.setChecked(true);
                break;
            case 1:
                if (isTrue) {
                    mTabHost.setCurrentTabByTag(TAB_MAKE);
                    initTextColor();
                    radio_make_any.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                    radio_make_any.setChecked(true);
                } else {
                    //弹框
                    radio_make_any.setTextColor(getResources().getColor(R.color.bottom_text));
                    radio_make_any.setChecked(false);
                    showRZDialog(state, isover);
                }
                break;
            case 2:
                mTabHost.setCurrentTabByTag(TAB_MY);
                radio_my.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                radio_my.setChecked(true);
                break;
            case 3:
                if (isTrue) {
//                    clickListener();
                    initTextColor();
                    radio_college.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                    radio_college.setChecked(true);
                    mTabHost.setCurrentTabByTag(TAB_COLLEGE);
                } else {
                    //弹框
                    radio_college.setTextColor(getResources().getColor(R.color.bottom_text));
                    radio_college.setChecked(false);
                    showRZDialog(state, isover);
                }
//                clickListener();
                break;
           /* case 4:
                mTabHost.setCurrentTabByTag(TAB_TEMAI);
                initTextColor();
                radio_temai.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                radio_temai.setChecked(true);
                break;*/
        }
    }


    public void initView() {
        mTabHost = getTabHost();
        Intent i_main = new Intent(this, CollegeManagerActivity.class);//new学院
        Intent i_make = new Intent(this, ToolActivity.class);
//        Intent i_make = new Intent(this, CollegeMangeActivity.class);//old学院
//        Intent i_college = new Intent(this, CirclrIndexActivity.class);
        Intent i_college = new Intent(this, CirclrFengActivity.class);
        Intent i_central = new Intent(this, PersonalCenterActivity.class);//new个人中心
//        Intent i_central = new Intent(this, PersonalCentralActivity.class);//old个人中心
//        Intent temai_intent = new Intent(this, HomePageActivity.class);
//        Intent temai_intent = new Intent(this, TeMeiActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_INDEX).setIndicator(TAB_INDEX).setContent(i_main));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAKE).setIndicator(TAB_MAKE).setContent(i_make));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_COLLEGE).setIndicator(TAB_COLLEGE).setContent(i_college));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_MY).setIndicator(TAB_MY).setContent(i_central));
    }

    @OnClick({R.id.home_tab_index, R.id.home_tab_make_any, R.id.home_tab_my, R.id.home_tab_college,R.id.im_once_image,R.id.im_once_act})
    public void OnClick(View view) {
        String isover = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
        String state = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
        boolean isTrue = isRZDialog(state, isover);
        switch (view.getId()) {
            case R.id.home_tab_index://学院
                initTextColor();
                Constants.INDEX_PROFIT = "0";
                radio_index.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                radio_index.setChecked(true);
                mTabHost.setCurrentTabByTag(TAB_INDEX);
                break;
            /*case R.id.home_tab_temai:
                initTextColor();
                radio_temai.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                radio_temai.setChecked(true);
                Constants.INDEX_PROFIT = "0";
                mTabHost.setCurrentTabByTag(TAB_TEMAI);
                break;*/
            case R.id.home_tab_make_any://工具
                if (isTrue) {
                    Constants.INDEX_PROFIT = "0";
                    initTextColor();
                    radio_make_any.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                    radio_make_any.setChecked(true);
                    mTabHost.setCurrentTabByTag(TAB_MAKE);
                } else {
                    //弹框
                    radio_make_any.setTextColor(getResources().getColor(R.color.bottom_text));
                    radio_make_any.setChecked(false);
                    showRZDialog(state, isover);
                }

                break;
            case R.id.home_tab_college://蜂圈
                if (isTrue) {
//                    clickListener();
                    Constants.INDEX_PROFIT = "0";
                    initTextColor();
                    radio_college.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                    radio_college.setChecked(true);
                    mTabHost.setCurrentTabByTag(TAB_COLLEGE);
                } else {
                    //弹框
                    radio_college.setTextColor(getResources().getColor(R.color.bottom_text));
                    radio_college.setChecked(false);
                    showRZDialog(state, isover);
                }
                break;
            case R.id.home_tab_my:
                Constants.INDEX_PROFIT = "0";
                initTextColor();
                radio_my.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
                radio_my.setChecked(true);
                mTabHost.setCurrentTabByTag(TAB_MY);
                String OneActClick = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONEPERSONALCLICK, "0");
                if (OneActClick.equals("0")) {
                    im_once_image.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.im_once_image:
                PreferenceHelper.write(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONEPERSONALCLICK, "1");
                im_once_image.setVisibility(View.GONE);
                break;
            case R.id.im_once_act:
                PreferenceHelper.write(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONEACTCLICK, "1");
                im_once_act.setVisibility(View.GONE);
                break;
        }
    }

    private void showRZDialog(String state, String isover) {
        if (state.equals("4")) {
            MyToast.show(HomeActivity.this, "我们正在加速审核中\n请耐心等待哦^_^");
        } else if(!state.equals("4")) {

            LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
            TextView text1 = (TextView) view.findViewById(R.id.text1);
            View view_line = view.findViewById(R.id.view_line);
            TextView yes = (TextView) view.findViewById(R.id.yes);
            TextView cancle = (TextView) view.findViewById(R.id.cancle);
            if (state.equals("1")) {
                //没有认证
                text1.setText("只有认证通过后\n才能使用更多功能^_^");
                text1.setTextColor(Color.parseColor("#000000"));
                yes.setText("去认证");
            } else if (state.equals("6")) {
                //审核不通过
                text1.setText("您的信息审核不通过\n请重新提交哦^_^");
                text1.setTextColor(Color.parseColor("#FC4D30"));
            } else if (state.equals("5") && isover.equals("false")) {
                //认证了，完善消息
                text1.setText("蜂优客4.0全新开启\n请完善个人资料哦^_^");
                text1.setTextColor(Color.parseColor("#000000"));
                yes.setText("去完善");
            } else if (state.equals("2") || state.equals("3")) {
                //认证了，完善消息
                text1.setText("蜂优客4.0全新开启\n请完善个人资料哦^_^");
                text1.setTextColor(Color.parseColor("#000000"));
                yes.setText("去完善");
            }

            final AlertDialog mAlertDialog = builder.create();
            mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
            mAlertDialog.show();
            mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            mAlertDialog.getWindow().setContentView(view, pm);
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, CertificationActivity.class);
                    startActivity(intent);
                    mAlertDialog.dismiss();
                }
            });

        }
    }

    private boolean isRZDialog(String state, String isover) {
        if (state.equals("1")) {
            //没有认证
            return false;
        } else if (state.equals("6")) {
            //审核不通过
            return false;
        } else if (state.equals("5") && isover.equals("false")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("2") || state.equals("3")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("4")) {
            //审核中
            return false;
        } else {
            return true;
        }
    }

    private void clickListener() {
        radio_college.setChecked(false);
        String guide_picurl = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL);
        String state = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
        //导游证认证判断
        //状态：1=审核中，2=审核通过，3=审核不通过，4=实名认证审核中，5=实名认证通过，6=实名认证不通过，7=停用
        if (state.equals("1")) {
            if (guide_picurl.equals("") || guide_picurl == null && guide_picurl.length() == 0) {
                showGuideDialog(state, "请上传导游证！");
            } else {
                showHintDialog(getResources().getString(R.string.guide_certificate_audit));
            }
        } else if (state.equals("3")) {
            showGuideDialog(state, "还没有上传导游证，是否去上传？");
        } else {
            Constants.INDEX_PROFIT = "0";
            initTextColor();
            radio_college.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
            radio_college.setChecked(true);
            mTabHost.setCurrentTabByTag(TAB_COLLEGE);
        }

       /* //------
        if (guide_picurl.equals("") || guide_picurl == null && guide_picurl.length() == 0) {
            showGuideDialog(state);
        } else if (state.equals("1")) {
            showHintDialog(getResources().getString(R.string.guide_certificate_audit));
        }*/
    }

    public Dialog hint_dialog;

    public void showHintDialog(String msg) {
        LayoutInflater inflater = LayoutInflater.from(this);
        hint_dialog = new Dialog(this, R.style.Mydialog);// 创建自定义样式dialog
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

    public void initTextColor() {
        radio_index.setTextColor(getResources().getColor(R.color.bottom_text));
        radio_make_any.setTextColor(getResources().getColor(R.color.bottom_text));
        radio_college.setTextColor(getResources().getColor(R.color.bottom_text));
        radio_my.setTextColor(getResources().getColor(R.color.bottom_text));
//        radio_temai.setTextColor(getResources().getColor(R.color.bottom_text));

        radio_index.setChecked(false);
        radio_make_any.setChecked(false);
        radio_college.setChecked(false);
        radio_my.setChecked(false);
//        radio_temai.setChecked(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(broadcastReceiver);
    }

    public Dialog guide_dialog;

    public void showGuideDialog(final String state, String str) {
        LayoutInflater inflater = LayoutInflater.from(this);
        guide_dialog = new Dialog(this, R.style.Mydialog);// 创建自定义样式dialog
        View view = inflater.inflate(R.layout.dialog_hint_daoyou, null);// 得到加载view
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(str);// 设置加载信息
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide_dialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide_dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("state", state);
                Intent intent = new Intent(HomeActivity.this, GuideCradMnagerActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        guide_dialog.setCancelable(false);// 不可以用“返回键”取消
        guide_dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        guide_dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ActivitySwitcher.animationIn(findViewById(R.id.home_view), getWindowManager());
//        String zan_num = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, "0");
//        String comment_num = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ZAN_NUM, "0");
//        int a = Integer.parseInt(zan_num) + Integer.parseInt(comment_num);
//
//        if (a == 0) {
//            view_potions.setVisibility(View.GONE);
//        } else {
//            view_potions.setVisibility(View.VISIBLE);
//        }
        System.out.println("======================homeactivity");
        String unreadcount_sign = TGmd5.getMD5(distributorid + getNewestDistributorId);
        imFmPersenter.unreadcount(distributorid, getNewestDistributorId, unreadcount_sign);
    }

    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.TURN_HOME_COLLEGE) {
            Constants.INDEX_PROFIT = "0";
            mTabHost.setCurrentTabByTag(TAB_MAKE);
            initTextColor();
            radio_make_any.setTextColor(getResources().getColor(R.color.bg_baoming_yellow));
            radio_make_any.setChecked(true);
        } else if (event.getEventType() == EventType.UPDATE_HOME_CIRCLE) {
            String zan_num = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, "0");
            String comment_num = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ZAN_NUM, "0");
            int a = Integer.parseInt(zan_num) + Integer.parseInt(comment_num);
            if (a > 0) {
                txt_no_read_count.setVisibility(View.VISIBLE);
                txt_no_read_count.setText(String.valueOf(a));
            } else {
                txt_no_read_count.setVisibility(View.GONE);
            }
        } else if (event.getEventType() == EventType.UPDATE_HOME_CENTER) {
            String center_num = PreferenceHelper.readString(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CENTER_NUMBER, "0");

            if ("0".equals(center_num)) {
                view_potions.setVisibility(View.GONE);
            } else {
                view_potions.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteSuccessCallBack(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                int uncomment = (int) jsonArray.get(0);
                int unlike = (int) jsonArray.get(1);
                String commentNum = jsonArray.get(0).toString();
                String dianzanNum = jsonArray.get(1).toString();
                int systemNum = (int) jsonArray.get(4);
                int fengyouNum = (int) jsonArray.get(5);
                PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GROUP_MESSAGE_NUM, String.valueOf(fengyouNum));
                int no_study_count = (int) jsonArray.get(3);
                if (no_study_count + systemNum + fengyouNum <= 0) {
                    view_potions.setVisibility(View.GONE);
                } else {
                    view_potions.setVisibility(View.VISIBLE);
                }
                PreferenceHelper.write(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, commentNum);
                PreferenceHelper.write(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ZAN_NUM, dianzanNum);

                // 公告角标
                String gonggao_num = String.valueOf(systemNum + fengyouNum);
                PreferenceHelper.write(HomeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.MESSAGE_NUM, gonggao_num + "");
//                if (!gonggao_num.equals("0")) {
//                    tv_ring_num.setVisibility(View.VISIBLE);
//                    tv_ring_num.setText(gonggao_num);
//                } else {
//                    tv_ring_num.setVisibility(View.GONE);
//                }
                if ((uncomment + unlike) > 0) {
                    txt_no_read_count.setVisibility(View.VISIBLE);
                    txt_no_read_count.setText(String.valueOf((uncomment + unlike)));
                } else {
                    txt_no_read_count.setVisibility(View.GONE);
                }

//                BadgeView badgeView = new BadgeView(this);
//                badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
//                badgeView.setTargetView(radio_college);
//                badgeView.setBadgeCount(uncomment + unlike);
                /**
                 * 更新蜂圈的角标
                 */
//                EventFactory.updateHomeCircle("0");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excuteFailedCallBack(String s) {

    }

    public static String ReadTxtFile(String strFilePath) {
        String path = strFilePath;
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            Log.d("TestFile", "The File doesn't not exist.");
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while ((line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }
                    instream.close();
                }
            } catch (java.io.FileNotFoundException e) {
                Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("TestFile", e.getMessage());
            }
        }
        return content;
    }

    public void deleteFile1(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFile1(files[i]); // 把每个文件 用这个方法进行迭代
                }
                Log.e("aksdfhka", "上传成功！");
            }
            file.delete();
        } else {
            Log.e("aksdfhka", "文件不存在！");
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            boolean isshow= intent.getBooleanExtra("isshowonceimg",false);
            if(isshow){
                im_once_act.setVisibility(View.VISIBLE);
            }

        }
    };
}
