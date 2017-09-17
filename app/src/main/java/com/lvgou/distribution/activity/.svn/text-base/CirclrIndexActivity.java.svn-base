package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.fragment.FindFragment;
import com.lvgou.distribution.fragment.FollowCircleFragment;
import com.lvgou.distribution.fragment.FragmentRecommend;
import com.lvgou.distribution.presenter.PersonalPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.PersonalView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2016/10/18.
 * 蜂圈首页
 */
public class CirclrIndexActivity extends BaseActivity implements PersonalView, FindFragment.OnArticleSelectedListener, FollowCircleFragment.OnArticleSelectedListener {

    @ViewInject(R.id.rl_search)
    private RelativeLayout rl_search;
    @ViewInject(R.id.rl_camera)
    private RelativeLayout rl_camera;
    @ViewInject(R.id.tv_find)
    private TextView tv_find;
    @ViewInject(R.id.tv_recommend)
    private TextView tv_recommend;
    @ViewInject(R.id.tv_follow)
    private TextView tv_follow;
    @ViewInject(R.id.rl_icon)
    private RelativeLayout rl_choujiang;
    @ViewInject(R.id.img_choujiang)
    private ImageView img_icon;
    @ViewInject(R.id.rl_dimiss)
    private RelativeLayout rl_dimss;
    @ViewInject(R.id.txt_message_count)
    private TextView txt_message_count;

    private FragmentRecommend recommendFrgament;
    private FindFragment findFragment;
    private FollowCircleFragment attentionFragment;

    private FragmentManager fragmentManager;

    private String type = "1";
    public String distributorid;

    FragmentTransaction transaction;
    private Dialog dialog;
    private String url_load = "";
    private long startTime;
    private int current_index = 1;

    private PersonalPresenter personalPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_index);
        ViewUtils.inject(this);
        EventBus.getDefault().register(this);
        personalPresenter = new PersonalPresenter(this);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        fragmentManager = getSupportFragmentManager();
        startTime = System.currentTimeMillis();
        initSelected();
        tv_find.setTextColor(getResources().getColor(R.color.bg_white));
        tv_find.setBackgroundResource(R.drawable.bg_college_balck_shape);
        selectTab(1);
        rl_camera.setVisibility(View.VISIBLE);
        rl_search.setVisibility(View.VISIBLE);


    }

    /**
     * 按钮 切换事件
     *
     * @param view
     */
    @OnClick({R.id.rl_back, R.id.tv_find, R.id.tv_recommend, R.id.tv_follow, R.id.rl_search, R.id.rl_camera, R.id.rl_dimiss, R.id.img_choujiang})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_find:
                type = "1";
                initSelected();
                tv_find.setTextColor(getResources().getColor(R.color.bg_white));
                tv_find.setBackgroundResource(R.drawable.bg_college_balck_shape);
                selectTab_hint(current_index);
                current_index = 1;
                selectTab(current_index);
                rl_camera.setVisibility(View.VISIBLE);
                rl_search.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_recommend:
                EventFactory.dimissFindTanKuang(0);
                initSelected();
                selectTab_hint(current_index);
                tv_recommend.setTextColor(getResources().getColor(R.color.bg_white));
                tv_recommend.setBackgroundResource(R.drawable.bg_college_balck_shape);
                current_index = 2;
                selectTab(current_index);
                rl_camera.setVisibility(View.GONE);
                rl_search.setVisibility(View.GONE);
                break;
            case R.id.tv_follow:
                EventFactory.dimissFindTanKuang(0);
                type = "1";
                selectTab_hint(current_index);
                initSelected();
                tv_follow.setTextColor(getResources().getColor(R.color.bg_white));
                tv_follow.setBackgroundResource(R.drawable.bg_college_balck_shape);
                current_index = 3;
                selectTab(current_index);
                rl_camera.setVisibility(View.VISIBLE);
                rl_search.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_search:
                bundle.putString("type", type);
                openActivity(FenwenSearchActivity.class, bundle);
                break;
            case R.id.rl_camera:
                openActivity(PublishFenwenActivity.class);
                break;
            case R.id.rl_dimiss:
                rl_choujiang.setVisibility(View.GONE);
                break;
            case R.id.img_choujiang:
                rl_choujiang.setVisibility(View.GONE);
                bundle.putString("url", url_load);
                bundle.putString("index", "0");
                openActivity(WebViewActivity.class, bundle);
                break;
        }
    }

    public void changeTopView(int unread_count) {
        if (unread_count == 0) {
            txt_message_count.setVisibility(View.GONE);
        } else {
            txt_message_count.setText(String.valueOf(unread_count));
            txt_message_count.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化，选中状态
     */
    public void initSelected() {
        tv_recommend.setTextColor(getResources().getColor(R.color.bg_balck_three));
        tv_follow.setTextColor(getResources().getColor(R.color.bg_balck_three));
        tv_find.setTextColor(getResources().getColor(R.color.bg_balck_three));
        tv_recommend.setBackgroundResource(R.drawable.bg_college_white_shape);
        tv_follow.setBackgroundResource(R.drawable.bg_college_white_shape);
        tv_find.setBackgroundResource(R.drawable.bg_college_white_shape);
    }

    /**
     * 选中切换
     *
     * @param tabs
     */
    public void selectTab(int tabs) {
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (tabs) {
            case 1:
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    transaction.add(R.id.content, findFragment);
                } else {
                    findFragment.setUserVisibleHint(true);
                    transaction.show(findFragment);
                }
                break;
            case 2:
                if (recommendFrgament == null) {
                    recommendFrgament = new FragmentRecommend();
                    transaction.add(R.id.content, recommendFrgament);
                } else {
                    recommendFrgament.setUserVisibleHint(true);
                    transaction.show(recommendFrgament);
                }
                break;
            case 3:
                if (attentionFragment == null) {
                    attentionFragment = new FollowCircleFragment();
                    transaction.add(R.id.content, attentionFragment);
                } else {
                    attentionFragment.setUserVisibleHint(true);
                    transaction.show(attentionFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 选中切换
     *
     * @param tabs
     */
    public void selectTab_hint(int tabs) {
        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (tabs) {
            case 1:
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    transaction.add(R.id.content, findFragment);
                } else {
                    findFragment.setUserVisibleHint(false);
                    transaction.show(findFragment);
                }
                break;
            case 2:
                if (recommendFrgament == null) {
                    recommendFrgament = new FragmentRecommend();
                    transaction.add(R.id.content, recommendFrgament);
                } else {
                    recommendFrgament.setUserVisibleHint(false);
                    transaction.show(recommendFrgament);
                }
                break;
            case 3:
                if (attentionFragment == null) {
                    attentionFragment = new FollowCircleFragment();
                    transaction.add(R.id.content, attentionFragment);
                } else {
                    attentionFragment.setUserVisibleHint(false);
                    transaction.show(attentionFragment);
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
        if (recommendFrgament != null) {
            transaction.hide(recommendFrgament);
        }
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (attentionFragment != null) {
            transaction.hide(attentionFragment);
        }
    }


    @Override
    public String onArticleSelected() {
        return distributorid;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    //退出登录
    public void showQuitDialog() {
        dialog = new Dialog(CirclrIndexActivity.this, R.style.Mydialog_NoShape);
        View view1 = View.inflate(CirclrIndexActivity.this, R.layout.dialog_choujiang, null);
        ImageView sure = (ImageView) view1.findViewById(R.id.img_choujiang);
        RelativeLayout cancle = (RelativeLayout) view1.findViewById(R.id.rl_dimiss);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    }

    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.SHOW_CHOUJIANG) {
            url_load = event.getExtraData() + "";
            rl_choujiang.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
            distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
            String sign_one = TGmd5.getMD5(distributorid + "1");
            personalPresenter.getMessageNum(distributorid, "1", sign_one);

            long time = System.currentTimeMillis() - startTime;
            if (time >= 1000 * 60 * Constants.SECOND) {
                switch (current_index) {
                    case 1:
                        findFragment.setUserVisibleHint(true);
                        break;
                    case 2:
                        recommendFrgament.setUserVisibleHint(true);
                        break;
                    case 3:
                        attentionFragment.setUserVisibleHint(true);
                        break;
                }
                startTime = System.currentTimeMillis();
            }
    }


    @Override
    public void OnRequestSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 3:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        int commentNum = (int) array.get(0);
                        int dianzanNum = (int) array.get(1);
                        changeTopView((commentNum + dianzanNum));
                        int classNum = (int)array.get(3);
                        int systemNum = (int)array.get(4);
                        int fengyouNum =(int)array.get(5);
                        if(classNum+systemNum+fengyouNum>0){
                            //我的显示未读角标
                            EventFactory.updateHomeCenter("0");
                        }
                        PreferenceHelper.write(CirclrIndexActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, String.valueOf(commentNum));
                        PreferenceHelper.write(CirclrIndexActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ZAN_NUM, String.valueOf(dianzanNum));
                        PreferenceHelper.write(CirclrIndexActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CENTER_NUMBER, String.valueOf(classNum + systemNum + fengyouNum));
//                        /**
//                         * 更新蜂圈的角标
//                         */
                        EventFactory.updateHomeCircle("0");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void OnRequestFialCallBack(String state, String respanse) {

    }

    @Override
    public void closeProgress() {

    }
}