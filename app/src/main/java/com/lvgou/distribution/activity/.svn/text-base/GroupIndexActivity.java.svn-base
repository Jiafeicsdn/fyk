package com.lvgou.distribution.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.fragment.AllClassesFragment;
import com.lvgou.distribution.fragment.AllGroupFrgmant;
import com.lvgou.distribution.fragment.CarryGroupFragment;
import com.lvgou.distribution.fragment.RecommendBeeFragment;
import com.lvgou.distribution.fragment.SendGroupFragment;
import com.lvgou.distribution.fragment.TeacherSeatFragment;
import com.umeng.analytics.MobclickAgent;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
import com.xdroid.utils.AppManager;

/**
 * Created by Snow on 2016/9/29.
 * 找团派团首页
 */
public class GroupIndexActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.rl_tuanbi)
    private RelativeLayout rl_tuanbi;
    @ViewInject(R.id.tv_all)
    private TextView tv_all;
    @ViewInject(R.id.tv_send_group)
    private TextView tv_send_group;
    @ViewInject(R.id.tv_carry_group)
    private TextView tv_carry_group;


    private AllGroupFrgmant allGroupFrgmant;
    private SendGroupFragment sendGroupFragment;
    private CarryGroupFragment carryGroupFragment;

    private FragmentManager fragmentManager;
    private int current_index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_index);
        ViewUtils.inject(this);

        fragmentManager = getFragmentManager();

        tv_all.setBackgroundResource(R.drawable.bg_conner_circle_green_shape);
        tv_all.setTextColor(getResources().getColor(R.color.bg_white));
        tv_send_group.setBackgroundResource(R.drawable.bg_college_white_shape);
        tv_send_group.setTextColor(getResources().getColor(R.color.bg_button_green));
        tv_carry_group.setBackgroundResource(R.drawable.bg_college_white_shape);
        tv_carry_group.setTextColor(getResources().getColor(R.color.bg_button_green));
        selectTab(1);

    }

    @OnClick({R.id.rl_back, R.id.rl_tuanbi, R.id.tv_all, R.id.tv_send_group, R.id.tv_carry_group, R.id.rl_date, R.id.rl_days, R.id.rl_daofu
            , R.id.rl_state})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_tuanbi:
                openActivity(TuanbiRuleActivity.class);
                break;
            case R.id.tv_all:
                Constants.ALL_SELECT_DAY = "";
                selectTabOne(current_index);
                tv_all.setBackgroundResource(R.drawable.bg_conner_circle_green_shape);
                tv_send_group.setBackgroundResource(R.drawable.bg_college_white_shape);
                tv_carry_group.setBackgroundResource(R.drawable.bg_college_white_shape);
                tv_all.setTextColor(getResources().getColor(R.color.bg_white));
                tv_send_group.setTextColor(getResources().getColor(R.color.bg_button_green));
                tv_carry_group.setTextColor(getResources().getColor(R.color.bg_button_green));
                current_index = 1;
                selectTab(current_index);
                EventFactory.dissPopwindowCarry(0);
                EventFactory.dissPopwindowSend(0);
                break;
            case R.id.tv_send_group:
                Constants.ALL_SELECT_DAY = "";
                Constants.ALL_SELECT_STATE = "";
                selectTabOne(current_index);
                tv_all.setBackgroundResource(R.drawable.bg_college_white_shape);
                tv_send_group.setBackgroundResource(R.drawable.bg_conner_circle_green_shape);
                tv_carry_group.setBackgroundResource(R.drawable.bg_college_white_shape);
                tv_all.setTextColor(getResources().getColor(R.color.bg_button_green));
                tv_send_group.setTextColor(getResources().getColor(R.color.bg_white));
                tv_carry_group.setTextColor(getResources().getColor(R.color.bg_button_green));
                current_index = 2;
                selectTab(current_index);
                EventFactory.dissPopwindowCarry(0);
                EventFactory.dissPopwindowAll(0);
                break;
            case R.id.tv_carry_group:
                Constants.ALL_SELECT_DAY = "";
                Constants.ALL_SELECT_STATE = "";
                selectTabOne(current_index);
                tv_all.setBackgroundResource(R.drawable.bg_college_white_shape);
                tv_send_group.setBackgroundResource(R.drawable.bg_college_white_shape);
                tv_carry_group.setBackgroundResource(R.drawable.bg_conner_circle_green_shape);
                tv_all.setTextColor(getResources().getColor(R.color.bg_button_green));
                tv_send_group.setTextColor(getResources().getColor(R.color.bg_button_green));
                tv_carry_group.setTextColor(getResources().getColor(R.color.bg_white));
                current_index = 3;
                selectTab(current_index);
                EventFactory.dissPopwindowSend(0);
                EventFactory.dissPopwindowAll(0);
                break;
        }
    }

    /**
     * 选中切换
     *
     * @param tabs
     */
    public void selectTab(int tabs) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (tabs) {
            case 1:
                if (allGroupFrgmant == null) {
                    allGroupFrgmant = new AllGroupFrgmant();
                    transaction.add(R.id.content, allGroupFrgmant);
                } else {
                    allGroupFrgmant.setUserVisibleHint(true);
                    transaction.show(allGroupFrgmant);
                }
                break;
            case 2:
                if (sendGroupFragment == null) {
                    sendGroupFragment = new SendGroupFragment();
                    transaction.add(R.id.content, sendGroupFragment);
                } else {
                    sendGroupFragment.setUserVisibleHint(true);
                    transaction.show(sendGroupFragment);
                }
                break;
            case 3:
                if (carryGroupFragment == null) {
                    carryGroupFragment = new CarryGroupFragment();
                    transaction.add(R.id.content, carryGroupFragment);
                } else {
                    carryGroupFragment.setUserVisibleHint(true);
                    transaction.show(carryGroupFragment);
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
    public void selectTabOne(int tabs) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (tabs) {
            case 1:
                if (allGroupFrgmant == null) {
                    allGroupFrgmant = new AllGroupFrgmant();
                    transaction.add(R.id.content, allGroupFrgmant);
                } else {
                    allGroupFrgmant.setUserVisibleHint(false);
                    transaction.show(allGroupFrgmant);
                }
                break;
            case 2:
                if (sendGroupFragment == null) {
                    sendGroupFragment = new SendGroupFragment();
                    transaction.add(R.id.content, sendGroupFragment);
                } else {
                    sendGroupFragment.setUserVisibleHint(false);
                    transaction.show(sendGroupFragment);
                }
                break;
            case 3:
                if (carryGroupFragment == null) {
                    carryGroupFragment = new CarryGroupFragment();
                    transaction.add(R.id.content, carryGroupFragment);
                } else {
                    carryGroupFragment.setUserVisibleHint(false);
                    transaction.show(carryGroupFragment);
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
        if (allGroupFrgmant != null) {
            transaction.hide(allGroupFrgmant);
        }

        if (sendGroupFragment != null) {
            transaction.hide(sendGroupFragment);
        }

        if (carryGroupFragment != null) {
            transaction.hide(carryGroupFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
