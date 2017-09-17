package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.fragment.ActivityFragment;
import com.lvgou.distribution.fragment.BoutiqueFragment;
import com.lvgou.distribution.fragment.ClassifyFragment;
import com.lvgou.distribution.fragment.LiveFragment;
import com.lvgou.distribution.fragment.RecommendFragment;
import com.lvgou.distribution.jpush.ExampleUtil;
import com.lvgou.distribution.presenter.CheckLevelUpPresenter;
import com.lvgou.distribution.presenter.GetUserInfoPresenter;
import com.lvgou.distribution.presenter.QianDaoPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CheckLevelUpView;
import com.lvgou.distribution.view.GetUserInfoView;
import com.lvgou.distribution.view.PopupMenu;
import com.lvgou.distribution.view.QianDaoView;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2017/3/6.
 * 新的学院首页 4.0
 */

public class CollegeManagerActivity extends BaseActivity implements View.OnClickListener, QianDaoView, CheckLevelUpView, GetUserInfoView {
    private static final String[] TITLE = new String[]{"推荐", "直播", "专题", "分类", "活动"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ClassifyFragment classifyFragment;
    private int currentPage = 1;
    private FragmentManager fragmentManager;
    FragmentTransaction transaction;
    private RecommendFragment recommendFragment;
    private LiveFragment liveFragment;
    private BoutiqueFragment boutiqueFragment;
    private ActivityFragment activityFragment;
    private String distributorid;
    private QianDaoPresenter qianDaoPresenter;
    private GetUserInfoPresenter getUserInfoPresenter;
    private CheckLevelUpPresenter checkLevelUpPresenter;
    private static final int MSG_SET_ALIAS = 1001;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college);
        qianDaoPresenter = new QianDaoPresenter(this);
        getUserInfoPresenter = new GetUserInfoPresenter(this);
//        recommendDatasPresenter = new RecommendDatasPresenter(this);
        checkLevelUpPresenter = new CheckLevelUpPresenter(this);

        fragmentManager = getSupportFragmentManager();
        currentPage = getIntent().getIntExtra("currentPage", 1);
        if (currentPage == 4) {
            linkUrl = getIntent().getStringExtra("linkUrl");
        }
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        initView();
        String mycurrent = mcache.getAsString("collegecurrentPage");
        if (mycurrent == null || mycurrent.equals("")) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(mycurrent);
        }
        selectTab(currentPage);
        initClick();
        /**/

        doSign();

        /*new Thread(){
            @Override
            public void run() {
                //Time Controller
                //Modify Time After 3000 ms
                startTime = System.currentTimeMillis();
                while(true){
                    long endTime = System.currentTimeMillis();
                    if(endTime - startTime > 3000){
                        startTime = endTime;
                        handler.sendEmptyMessage(UPDATE_MY_TV);
                    }
                }
            }
        }.start();*/
    }

   /* private long startTime;
    private static final int UPDATE_MY_TV = 567;
    //Handler UI modification
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case UPDATE_MY_TV:
//                    Log.e("skahdfkjs", "---------" );
                    //每隔三秒请求一下状态

                    break;
            }
        }
    };*/

    // 签到
    public void doSign() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String is_today = PreferenceHelper.readString(CollegeManagerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.IS_CURENT_TIME, "");
        //每天记录一次，第一次登录时间
        if (!is_today.equals(df.format(new Date()))) {
            String sign = TGmd5.getMD5(distributorid);
            qianDaoPresenter.qianDao(distributorid, sign);
        }

    }

    /**
     * 选中切换
     *
     * @param tabs
     */
    public void selectTab(int tabs) {
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        hideTextAndView();
        switch (tabs) {
            case 1:
                tv_tuijian.setTextColor(Color.parseColor("#000000"));
                view_tuijian.setVisibility(View.VISIBLE);
                if (recommendFragment == null) {
                    recommendFragment = new RecommendFragment();
                    transaction.add(R.id.content, recommendFragment);
                } else {
                    recommendFragment.setUserVisibleHint(true);
                    transaction.show(recommendFragment);
                }
                break;
            case 2:
                tv_zhibo.setTextColor(Color.parseColor("#000000"));
                view_zhibo.setVisibility(View.VISIBLE);
                if (liveFragment == null) {
                    liveFragment = new LiveFragment();
                    transaction.add(R.id.content, liveFragment);
                } else {
                    liveFragment.setUserVisibleHint(true);
                    transaction.show(liveFragment);
                }
                break;
            case 3:
                tv_zhuanti.setTextColor(Color.parseColor("#000000"));
                view_zhuanti.setVisibility(View.VISIBLE);
                if (boutiqueFragment == null) {
                    boutiqueFragment = new BoutiqueFragment();
                    transaction.add(R.id.content, boutiqueFragment);
                } else {
                    boutiqueFragment.setUserVisibleHint(true);
                    transaction.show(boutiqueFragment);
                }
                break;
            case 4:
                mcache.remove("collegecurrentPage");
                tv_fenlei.setTextColor(Color.parseColor("#000000"));
                view_fenlei.setVisibility(View.VISIBLE);
                if (classifyFragment == null) {
                    classifyFragment = new ClassifyFragment();
                    transaction.add(R.id.content, classifyFragment);
                    if (!linkUrl.equals("")) {
                        classifyFragment.setCurrentPosition(linkUrl);
                    }

                } else {
                    classifyFragment.setUserVisibleHint(true);
                    transaction.show(classifyFragment);
                    if (!linkUrl.equals("")) {
                        classifyFragment.setCurrentPosition2(linkUrl);
                    }

                }
                linkUrl = "";
                break;
            case 5:
                tv_huodong.setTextColor(Color.parseColor("#000000"));
                view_huodong.setVisibility(View.VISIBLE);
                if (activityFragment == null) {
                    activityFragment = new ActivityFragment();
                    transaction.add(R.id.content, activityFragment);
                } else {
                    activityFragment.setUserVisibleHint(true);
                    transaction.show(activityFragment);
                }
                break;
        }
        transaction.commit();
    }


    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    public void hideFragments(FragmentTransaction transaction) {
        if (recommendFragment != null) {
            transaction.hide(recommendFragment);
        }
        if (liveFragment != null) {
            transaction.hide(liveFragment);
        }
        if (boutiqueFragment != null) {
            transaction.hide(boutiqueFragment);
        }
        if (classifyFragment != null) {
            transaction.hide(classifyFragment);
        }
        if (activityFragment != null) {
            transaction.hide(activityFragment);
        }
    }


//    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(10);

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }*/

/*    public interface MyOnTouchListener {
        public boolean dispatchTouchEvent(MotionEvent ev);
    }*/

    //        RecommendFragment  推荐
    //        LiveFragment  直播
    //        BoutiqueFragment  精品
    //        ClassifyFragment  分类
    //        ActivityFragment  活动
    private ImageView college_title_search;//搜索按钮
    private ImageView img_custom_service;//客服按钮
    private TextView et_title_search;//搜索框
    //    private View view_all;
    private RelativeLayout rl_tuijian;
    private RelativeLayout rl_zhibo;
    private RelativeLayout rl_zhuanti;
    private RelativeLayout rl_fenlei;
    private RelativeLayout rl_huodong;
    private TextView tv_tuijian;
    private View view_tuijian;
    private TextView tv_zhibo;
    private View view_zhibo;
    private TextView tv_zhuanti;
    private View view_zhuanti;
    private TextView tv_fenlei;
    private View view_fenlei;
    private TextView tv_huodong;
    private View view_huodong;
    //private ImageView im_once_act;

    private void initView() {
//        view_all = findViewById(R.id.view_all);
        popupMenu = new PopupMenu(CollegeManagerActivity.this);
        //im_once_act = (ImageView) findViewById(R.id.im_once_act);
        college_title_search = (ImageView) findViewById(R.id.college_title_search);
        img_custom_service = (ImageView) findViewById(R.id.img_custom_service);
        et_title_search = (TextView) findViewById(R.id.et_title_search);
        rl_tuijian = (RelativeLayout) findViewById(R.id.rl_tuijian);
        rl_zhibo = (RelativeLayout) findViewById(R.id.rl_zhibo);
        rl_zhuanti = (RelativeLayout) findViewById(R.id.rl_zhuanti);
        rl_fenlei = (RelativeLayout) findViewById(R.id.rl_fenlei);
        rl_huodong = (RelativeLayout) findViewById(R.id.rl_huodong);
        tv_tuijian = (TextView) findViewById(R.id.tv_tuijian);
        view_tuijian = findViewById(R.id.view_tuijian);
        tv_zhibo = (TextView) findViewById(R.id.tv_zhibo);
        view_zhibo = findViewById(R.id.view_zhibo);
        tv_zhuanti = (TextView) findViewById(R.id.tv_zhuanti);
        view_zhuanti = findViewById(R.id.view_zhuanti);
        tv_fenlei = (TextView) findViewById(R.id.tv_fenlei);
        view_fenlei = findViewById(R.id.view_fenlei);
        tv_huodong = (TextView) findViewById(R.id.tv_huodong);
        view_huodong = findViewById(R.id.view_huodong);

    }

    private void hideTextAndView() {
        tv_tuijian.setTextColor(Color.parseColor("#999999"));
        view_tuijian.setVisibility(View.GONE);
        tv_zhibo.setTextColor(Color.parseColor("#999999"));
        view_zhibo.setVisibility(View.GONE);
        tv_zhuanti.setTextColor(Color.parseColor("#999999"));
        view_zhuanti.setVisibility(View.GONE);
        tv_fenlei.setTextColor(Color.parseColor("#999999"));
        view_fenlei.setVisibility(View.GONE);
        tv_huodong.setTextColor(Color.parseColor("#999999"));
        view_huodong.setVisibility(View.GONE);
    }

    private String linkUrl = "";

    public void setCurrent(int currentpage, String linkUrl) {
        this.linkUrl = linkUrl;
        selectTab(currentpage);
/*        if (classifyFragment==null){
            classifyFragment.setCurrentPosition(linkUrl);
        }else {
            classifyFragment.setCurrentPosition2(linkUrl);
        }*/
    }

    private void initClick() {
        college_title_search.setOnClickListener(this);
        img_custom_service.setOnClickListener(this);
        et_title_search.setOnClickListener(this);
//        view_all.setOnClickListener(this);
        rl_tuijian.setOnClickListener(this);
        rl_zhibo.setOnClickListener(this);
        rl_zhuanti.setOnClickListener(this);
        rl_fenlei.setOnClickListener(this);
        rl_huodong.setOnClickListener(this);
        //im_once_act.setOnClickListener(this);

    }

    private PopupMenu popupMenu;

   /* public void showView(boolean isShow) {
        if (isShow) {
            view_all.setVisibility(View.GONE);
        } else {
            view_all.setVisibility(View.VISIBLE);
        }
    }*/

    public boolean isRZDialog(String state, String isover) {
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

    @Override
    public void onClick(View v) {
        String isover = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
        String userstate = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
        if (!isRZDialog(userstate, isover)) {
            showRZDialog(userstate, isover);
            return;
        }
        switch (v.getId()) {
            case R.id.college_title_search://搜索按钮点击
            case R.id.et_title_search://搜索框的点击
                //跳转到搜索页面
                openActivity(CollegeSearchActivity.class);
                break;
            case R.id.img_custom_service://客服按钮点击
                popupMenu.showLocation(R.id.view_007);// 设置弹出菜单弹出的位置
                popupMenu.setOnItemClickListener(new PopupMenu.OnItemClickListener() {
                    @Override
                    public void onClick(PopupMenu.MENUITEM item, String str) {
                        switch (str) {
                            case "1":
//                                startActivity(new Intent(CollegeManagerActivity.this, MQMessageFormActivity.class));
                                Intent intent = new MQIntentBuilder(CollegeManagerActivity.this).build();
                                startActivity(intent);
                                break;
                            case "2":
                                FunctionUtils.jump2PhoneView(CollegeManagerActivity.this, "4008017579");
                                break;
                        }
                    }
                });
                break;
           /* case R.id.view_all:
                String isover = PreferenceHelper.readString(CollegeManagerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
                String state = PreferenceHelper.readString(CollegeManagerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
                showRZDialog(userstate, isover);
                break;*/
            case R.id.rl_tuijian:
                selectTab(1);
                break;
            case R.id.rl_zhibo:
                selectTab(2);
                break;
            case R.id.rl_zhuanti:
                selectTab(3);
                break;
            case R.id.rl_fenlei:
                selectTab(4);
                break;
            case R.id.rl_huodong:
                String OneActClick = PreferenceHelper.readString(CollegeManagerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONEACTCLICK, "0");
                if (OneActClick.equals("0")) {
                    //im_once_act.setVisibility(View.VISIBLE);
                    Intent intent = new Intent("com.lvgou.distribution.home");
                    intent.putExtra("isshowonceimg",true);
                    sendBroadcast(intent);
                    showOneActDialog();
                }
                selectTab(5);
                break;
            /*case R.id.im_once_act:
                PreferenceHelper.write(CollegeManagerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONEACTCLICK, "1");
                //im_once_act.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.putExtra("isshowonceimg",false);
                sendBroadcast(intent);
                break;*/
        }
    }

    public void showOneActDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_act_oneclick, null);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceHelper.write(CollegeManagerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONEACTCLICK, "1");
                mAlertDialog.dismiss();
            }
        });
    }

    @Override
    public void OnQianDaoSuccCallBack(String state, String respanse) {
        try {
            JSONObject jsa = new JSONObject(respanse);
            if (jsa.get("message") != null && !jsa.get("message").toString().equals("")) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                PreferenceHelper.write(CollegeManagerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.IS_CURENT_TIME, df.format(new Date()).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnQianDaoFialCallBack(String state, String respanse) {
    }

    @Override
    public void closeQianDaoProgress() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        // jpush设置alias(别名设置) :以 distributorid 为别名
        Message message = new Message();
        message.what = MSG_SET_ALIAS;
        mHandler.sendMessage(message);

        String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
        getUserInfoPresenter.getUserInfo(distributorid, sign);
        checkLevelUpPresenter.checkLevelUp(distributorid, sign);
    }


    @Override
    public void OnCheckLevelUpSuccCallBack(String state, String respanse) {
        try {
            JSONObject jsb = new JSONObject(respanse);
            String message = jsb.get("message").toString();
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            String nowState = jsa.get(0).toString();
            if (message != null && !message.equals("")) {
                showUpDialog(message, nowState);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnCheckLevelUpFialCallBack(String state, String respanse) {

    }

    @Override
    public void closeCheckLevelUpProgress() {

    }

    public void showUpDialog(String message, final String state) {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_upload, null);
        TextView tv_see = (TextView) view.findViewById(R.id.tv_see);//去看看
        RelativeLayout rl_cancel = (RelativeLayout) view.findViewById(R.id.rl_cancel);//取消
        ImageView im_level = (ImageView) view.findViewById(R.id.im_level);//当前等级
        if (state.equals("1")) {
            im_level.setBackgroundResource(R.mipmap.onelevel_light_icon);
        } else if (state.equals("2")) {
            im_level.setBackgroundResource(R.mipmap.twolevel_light_icon);
        } else if (state.equals("3")) {
            im_level.setBackgroundResource(R.mipmap.threelevel_light_icon);
        } else if (state.equals("4")) {
            im_level.setBackgroundResource(R.mipmap.fourlevel_light_icon);
        } else if (state.equals("5")) {
            im_level.setBackgroundResource(R.mipmap.fivelevel_light_icon);
        } else if (state.equals("6")) {
            im_level.setBackgroundResource(R.mipmap.sixlevel_light_icon);
        } else if (state.equals("7")) {
            im_level.setBackgroundResource(R.mipmap.sevenlevel_light_icon);
        } else if (state.equals("8")) {
            im_level.setBackgroundResource(R.mipmap.eightlevel_light_icon);
        } else if (state.equals("9")) {
            im_level.setBackgroundResource(R.mipmap.ninelevel_light_icon);
        } else if (state.equals("10")) {
            im_level.setBackgroundResource(R.mipmap.tenlevel_light_icon);
        }
        RelativeLayout rl_one = (RelativeLayout) view.findViewById(R.id.rl_one);//第一个权益
        rl_one.setVisibility(View.GONE);
        TextView tv_onetext = (TextView) view.findViewById(R.id.tv_onetext);//第一个权益内容
        RelativeLayout rl_two = (RelativeLayout) view.findViewById(R.id.rl_two);//第二个权益
        rl_two.setVisibility(View.GONE);
        TextView tv_twotext = (TextView) view.findViewById(R.id.tv_twotext);//第二个权益内容
        RelativeLayout rl_three = (RelativeLayout) view.findViewById(R.id.rl_three);//第三个权益
        rl_three.setVisibility(View.GONE);
        TextView tv_threetext = (TextView) view.findViewById(R.id.tv_threetext);//第三个权益内容
        RelativeLayout rl_four = (RelativeLayout) view.findViewById(R.id.rl_four);//第四个权益
        rl_four.setVisibility(View.GONE);
        TextView tv_fourtext = (TextView) view.findViewById(R.id.tv_fourtext);//第四个权益内容
        RelativeLayout rl_five = (RelativeLayout) view.findViewById(R.id.rl_five);//第五个权益
        rl_five.setVisibility(View.GONE);
        TextView tv_fivetext = (TextView) view.findViewById(R.id.tv_fivetext);//第五个权益内容
        if (message.contains(",")) {
            String[] split = message.split(",");
            if (split.length == 2) {
                rl_one.setVisibility(View.VISIBLE);
                tv_onetext.setText(split[0]);
                rl_two.setVisibility(View.VISIBLE);
                tv_twotext.setText(split[1]);

            } else if (split.length == 3) {
                rl_one.setVisibility(View.VISIBLE);
                tv_onetext.setText(split[0]);
                rl_two.setVisibility(View.VISIBLE);
                tv_twotext.setText(split[1]);
                rl_three.setVisibility(View.VISIBLE);
                tv_threetext.setText(split[2]);

            } else if (split.length == 4) {
                rl_one.setVisibility(View.VISIBLE);
                tv_onetext.setText(split[0]);
                rl_two.setVisibility(View.VISIBLE);
                tv_twotext.setText(split[1]);
                rl_three.setVisibility(View.VISIBLE);
                tv_threetext.setText(split[2]);
                rl_four.setVisibility(View.VISIBLE);
                tv_fourtext.setText(split[3]);

            } else if (split.length == 5) {
                rl_one.setVisibility(View.VISIBLE);
                tv_onetext.setText(split[0]);
                rl_two.setVisibility(View.VISIBLE);
                tv_twotext.setText(split[1]);
                rl_three.setVisibility(View.VISIBLE);
                tv_threetext.setText(split[2]);
                rl_four.setVisibility(View.VISIBLE);
                tv_fourtext.setText(split[3]);
                rl_five.setVisibility(View.VISIBLE);
                tv_fivetext.setText(split[4]);
            }
        } else {
            rl_one.setVisibility(View.VISIBLE);
            tv_onetext.setText(message);
        }

        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        tv_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollegeManagerActivity.this, MyLevelActivity.class);
                intent.putExtra("melevel", state);
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnGetUserInfoSuccCallBack(String state, String respanse) {
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject jsonObject = jsa.getJSONObject(0);
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, jsonObject.get("ID").toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, jsonObject.get("TuanBi").toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, jsonObject.get("State").toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, jsonObject.get("ParentID").toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.RATIO, jsonObject.get("Ratio").toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, jsonObject.get("UserType").toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.MOBILE, jsonObject.get("Mobile").toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HEAD_PATH, jsonObject.get("PicUrl").toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ACCOUNT, jsonObject.get("LoginName").toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PWD, jsonObject.get("PassWord").toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME, jsonObject.get("RealName").toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STAR, jsonObject.get("Star").toString());
            JSONObject jsonObject1 = jsa.getJSONObject(1);
            if (Integer.parseInt(jsonObject1.get("CertificateLevel").toString()) > 0) {
                PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "true");
            } else {
                PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnGetUserInfoFialCallBack(String state, String respanse) {

    }

    @Override
    public void closeGetUserInfoProgress() {

    }
}
