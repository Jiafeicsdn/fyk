package com.lvgou.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.fragment.FragmentMyTask;
import com.lvgou.distribution.fragment.FragmentTuanbiDetial;
import com.lvgou.distribution.presenter.MyTaskListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snwo on 2016/10/14.
 * 团币管理
 */
public class TuanbiMangerActivity extends FragmentActivity implements GroupView {

    @ViewInject(R.id.id_viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.rl_top_view)
    private RelativeLayout rl_top_view;
    @ViewInject(R.id.ll_tab_view)
    private LinearLayout ll_tab_view;
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_bakc;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    /*    @ViewInject(R.id.tv_right)
        private TextView tv_right;*/
    @ViewInject(R.id.tv_tuanbi)
    private TextView tv_tuanbi_num;
    @ViewInject(R.id.img_recharge)
    private ImageView img_recharge;
    @ViewInject(R.id.tv_sign_day)
    private TextView tv_day_num;
    @ViewInject(R.id.rl_task)
    private RelativeLayout rl_task;
    @ViewInject(R.id.tv_task)
    private TextView tv_task;
    @ViewInject(R.id.view_task)
    private View view_task;
    @ViewInject(R.id.rl_detial)
    private RelativeLayout rl_detial;
    @ViewInject(R.id.tv_detial)
    private TextView tv_detial;
    @ViewInject(R.id.view_detial)
    private View view_detial;
 /*   @ViewInject(R.id.rl_exchange)
    private RelativeLayout rl_exchange;*/
/*    @ViewInject(R.id.tv_exchange)
    private TextView tv_exchange;
    @ViewInject(R.id.view_exchange)
    private View view_exchange;*/
    /*@ViewInject(R.id.img_day_one)
    private ImageView img_day_one;
    @ViewInject(R.id.img_day_two)
    private ImageView img_day_two;
    @ViewInject(R.id.img_day_three)
    private ImageView img_day_three;
    @ViewInject(R.id.img_day_four)
    private ImageView img_day_four;
    @ViewInject(R.id.img_day_five)
    private ImageView img_day_five;
    @ViewInject(R.id.img_day_six)
    private ImageView img_day_six;
    @ViewInject(R.id.img_day_seven)
    private ImageView img_day_seven;*/
    /*@ViewInject(R.id.view_01)
    private View view_one;
    @ViewInject(R.id.view_02)
    private View view_two;
    @ViewInject(R.id.view_03)
    private View view_three;
    @ViewInject(R.id.view_04)
    private View view_four;
    @ViewInject(R.id.view_05)
    private View view_five;
    @ViewInject(R.id.view_06)
    private View view_six;*/

    private String sign_day_num;


    private List<Fragment> fragmentList = new ArrayList<>();

    private MyTaskListPresenter myTaskListPresenter;

    private String distributorid = "";
    private int tabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuanbi_new);
        ViewUtils.inject(this);
        EventBus.getDefault().register(this);
        tv_title.setText("团币");
        myTaskListPresenter = new MyTaskListPresenter(this);
        distributorid = PreferenceHelper.readString(TuanbiMangerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        rl_publish.setVisibility(View.GONE);
//        tv_right.setText("兑换记录");
        tabIndex = getIntent().getIntExtra("tabIndex", 0);
        initFragments();

        initView();
        initSection();
        if (tabIndex == 1) {
            tv_detial.setTextColor(getResources().getColor(R.color.bg_balck_three));
            view_detial.setVisibility(View.VISIBLE);
        } /*else if (tabIndex == 2) {
            tv_exchange.setTextColor(getResources().getColor(R.color.bg_balck_three));
            view_exchange.setVisibility(View.VISIBLE);
        }*/ else {
            tv_task.setTextColor(getResources().getColor(R.color.bg_balck_three));
            view_task.setVisibility(View.VISIBLE);
        }
        String sign = TGmd5.getMD5(distributorid);
        myTaskListPresenter.geMyTaskList(distributorid, sign);

    }


    @OnClick({R.id.rl_back, R.id.rl_task, R.id.rl_detial, R.id.rl_exchange, R.id.img_recharge})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_task:
                initSection();
                tv_task.setTextColor(getResources().getColor(R.color.bg_balck_three));
                view_task.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0);
                break;
            case R.id.rl_detial:
                initSection();
                tv_detial.setTextColor(getResources().getColor(R.color.bg_balck_three));
                view_detial.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(1);
                break;
         /*   case R.id.rl_exchange:
                initSection();
                tv_exchange.setTextColor(getResources().getColor(R.color.bg_balck_three));
                view_exchange.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(2);
                break;*/
            case R.id.img_recharge:
                Intent intent = new Intent(TuanbiMangerActivity.this, RechargeMoneyActivity.class);
                startActivity(intent);
                break;
            /*case R.id.rl_publish:
                Intent intent_one = new Intent(TuanbiMangerActivity.this, ExchangeTuanbiRecordActivity.class);
                startActivity(intent_one);
                break;*/
        }
    }

    public void initSection() {
        tv_task.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        view_task.setVisibility(View.GONE);

        tv_detial.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        view_detial.setVisibility(View.GONE);
/*
        tv_exchange.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        view_exchange.setVisibility(View.GONE);*/

    }

    private void initFragments() {
        FragmentMyTask fragmentMyTask = new FragmentMyTask();
        fragmentList.add(fragmentMyTask);
        FragmentTuanbiDetial fragmentDetial = new FragmentTuanbiDetial();
        fragmentList.add(fragmentDetial);
      /*  FragmentExchangeNew fragmentExchangeNew = new FragmentExchangeNew();
        fragmentList.add(fragmentExchangeNew);*/
    }

    private void initView() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    initSection();
                    tv_task.setTextColor(getResources().getColor(R.color.bg_balck_three));
                    view_task.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    initSection();
                    tv_detial.setTextColor(getResources().getColor(R.color.bg_balck_three));
                    view_detial.setVisibility(View.VISIBLE);
                } /*else if (position == 2) {
                    initSection();
                    tv_exchange.setTextColor(getResources().getColor(R.color.bg_balck_three));
                    view_exchange.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }
        });
        viewPager.setCurrentItem(tabIndex);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        myTaskListPresenter.attach(this);
        String tuanbi = PreferenceHelper.readString(TuanbiMangerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
        tv_tuanbi_num.setText(tuanbi);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTaskListPresenter.dettach();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        tv_tuanbi_num.setText(array.get(0).toString());
//                        tv_day_num.setText("共" + array.get(1).toString() + "天");
//                        initImage(array.get(2).toString());
                        PreferenceHelper.write(TuanbiMangerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, array.get(0).toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        MyToast.show(TuanbiMangerActivity.this, "请求失败");
    }


    @Override
    public void closeProgress() {

    }

    @Override
    public void showProgress() {

    }


    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.UPDATE_TUANBI_SHOW) {
            String sign = TGmd5.getMD5(distributorid);
            myTaskListPresenter.geMyTaskList(distributorid, sign);
        } else if (event.getEventType() == EventType.UPDATE_TUANBI_TASK) {
            String tuanbi = PreferenceHelper.readString(TuanbiMangerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
            tv_tuanbi_num.setText(tuanbi);
        }
    }
}
