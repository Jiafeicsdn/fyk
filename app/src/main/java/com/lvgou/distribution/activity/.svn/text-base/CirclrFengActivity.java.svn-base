package com.lvgou.distribution.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.fragment.FaXianFragment;
import com.lvgou.distribution.fragment.FollowCircleFragment;
import com.lvgou.distribution.fragment.FragmentRecommend;
import com.lvgou.distribution.presenter.PersonalPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.PersonalView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/14.
 * 蜂圈
 */

public class CirclrFengActivity extends BaseActivity implements View.OnClickListener, PersonalView, FollowCircleFragment.OnArticleSelectedListener {
    private FragmentManager fragmentManager;
    FragmentTransaction transaction;
    private PersonalPresenter personalPresenter;
    public String distributorid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circlefeng);
        personalPresenter = new PersonalPresenter(this);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        fragmentManager = getSupportFragmentManager();
        initView();
        initClick();
        selectTab(1);
    }

    private RelativeLayout rl_faxian;//发现
    private TextView tv_faxian;
    private View view_faxian;
    private RelativeLayout rl_toutiao;//头条
    private TextView tv_toutiao;
    private View view_toutiao;
    private RelativeLayout rl_guanzhu;//关注
    private TextView tv_guanzhu;
    private View view_gaunzhu;
    private RelativeLayout rl_search;//搜索
    private View point_guanzhu;

    private void initView() {
        rl_faxian = (RelativeLayout) findViewById(R.id.rl_faxian);
        tv_faxian = (TextView) findViewById(R.id.tv_faxian);
        view_faxian = findViewById(R.id.view_faxian);
        rl_toutiao = (RelativeLayout) findViewById(R.id.rl_toutiao);
        tv_toutiao = (TextView) findViewById(R.id.tv_toutiao);
        view_toutiao = findViewById(R.id.view_toutiao);
        rl_guanzhu = (RelativeLayout) findViewById(R.id.rl_guanzhu);
        tv_guanzhu = (TextView) findViewById(R.id.tv_guanzhu);
        view_gaunzhu = findViewById(R.id.view_gaunzhu);
        rl_search = (RelativeLayout) findViewById(R.id.rl_search);
        point_guanzhu = findViewById(R.id.point_guanzhu);
    }

    private void initClick() {
        rl_faxian.setOnClickListener(this);
        rl_toutiao.setOnClickListener(this);
        rl_guanzhu.setOnClickListener(this);
        rl_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_faxian://发现点击
                tv_faxian.setTextColor(Color.parseColor("#000000"));
                tv_faxian.setTextSize(18);
                tv_toutiao.setTextColor(Color.parseColor("#999999"));
                tv_toutiao.setTextSize(14);
                tv_guanzhu.setTextColor(Color.parseColor("#999999"));
                tv_guanzhu.setTextSize(14);
                rl_search.setVisibility(View.VISIBLE);
                view_faxian.setVisibility(View.VISIBLE);
                view_toutiao.setVisibility(View.GONE);
                view_gaunzhu.setVisibility(View.GONE);
                selectTab(1);
                break;
            case R.id.rl_toutiao://头条点击
                tv_toutiao.setTextColor(Color.parseColor("#000000"));
                tv_toutiao.setTextSize(18);
                tv_faxian.setTextColor(Color.parseColor("#999999"));
                tv_faxian.setTextSize(14);
                tv_guanzhu.setTextColor(Color.parseColor("#999999"));
                tv_guanzhu.setTextSize(14);
                rl_search.setVisibility(View.GONE);
                view_toutiao.setVisibility(View.VISIBLE);
                view_faxian.setVisibility(View.GONE);
                view_gaunzhu.setVisibility(View.GONE);
                selectTab(2);
                break;
            case R.id.rl_guanzhu://关注点击
                tv_guanzhu.setTextColor(Color.parseColor("#000000"));
                tv_guanzhu.setTextSize(18);
                tv_faxian.setTextColor(Color.parseColor("#999999"));
                tv_faxian.setTextSize(14);
                tv_toutiao.setTextColor(Color.parseColor("#999999"));
                tv_toutiao.setTextSize(14);
                rl_search.setVisibility(View.VISIBLE);
                view_gaunzhu.setVisibility(View.VISIBLE);
                view_toutiao.setVisibility(View.GONE);
                view_faxian.setVisibility(View.GONE);
                selectTab(3);
                break;
            case R.id.rl_search://搜索
                Bundle bundle = new Bundle();
                bundle.putString("type", "1");
                openActivity(FenwenSearchActivity.class, bundle);
                break;
        }
    }

    private FragmentRecommend recommendFrgament;
    private FaXianFragment faXianFragment;
    private FollowCircleFragment attentionFragment;

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
                rl_search.setVisibility(View.VISIBLE);
                if (faXianFragment == null) {
                    faXianFragment = new FaXianFragment();
                    transaction.add(R.id.content, faXianFragment);
                } else {
                    faXianFragment.setUserVisibleHint(true);
                    transaction.show(faXianFragment);
                }
                break;
            case 2:
                rl_search.setVisibility(View.GONE);
                if (recommendFrgament == null) {
                    recommendFrgament = new FragmentRecommend();
                    transaction.add(R.id.content, recommendFrgament);
                } else {
                    recommendFrgament.setUserVisibleHint(true);
                    transaction.show(recommendFrgament);
                }
                break;
            case 3:
                rl_search.setVisibility(View.VISIBLE);
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
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    public void hideFragments(FragmentTransaction transaction) {
        if (recommendFrgament != null) {
            transaction.hide(recommendFrgament);
        }
        if (faXianFragment != null) {
            transaction.hide(faXianFragment);
        }
        if (attentionFragment != null) {
            transaction.hide(attentionFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign_one = TGmd5.getMD5(distributorid + "1");
        personalPresenter.getMessageNum(distributorid, "1", sign_one);
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
                        int classNum = (int) array.get(3);
                        int systemNum = (int) array.get(4);
                        int fengyouNum = (int) array.get(5);
                        if (classNum + systemNum + fengyouNum > 0) {
                            //我的显示未读角标
                            EventFactory.updateHomeCenter("0");
                        }
                        PreferenceHelper.write(CirclrFengActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, String.valueOf(commentNum));
                        PreferenceHelper.write(CirclrFengActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ZAN_NUM, String.valueOf(dianzanNum));
                        PreferenceHelper.write(CirclrFengActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CENTER_NUMBER, String.valueOf(classNum + systemNum + fengyouNum));
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

    public void changeTopView(int unread_count) {
        if (unread_count == 0) {
            point_guanzhu.setVisibility(View.GONE);
        } else {
//            txt_message_count.setText(String.valueOf(unread_count));
            point_guanzhu.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void OnRequestFialCallBack(String state, String respanse) {

    }

    @Override
    public String onArticleSelected() {
        return distributorid;
    }
}
