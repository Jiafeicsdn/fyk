package com.lvgou.distribution.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.fragment.CheckInviteFragment;
import com.lvgou.distribution.fragment.SuccInviteFragment;

/**
 * Created by Administrator on 2017/4/12.
 * 邀请记录
 */

public class InviteRecordActivity extends BaseActivity implements View.OnClickListener {
    private CheckInviteFragment checkInviteFragment;
    private SuccInviteFragment succInviteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_record);
        initView();
        initClick();
        fragmentManager = getSupportFragmentManager();
        selectTab(1);
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private RelativeLayout rl_daishehe;//待审核
    private TextView tv_daishenhe;
    private View view_daishenhe;
    private RelativeLayout rl_success;//邀请成功
    private TextView tv_success;
    private View view_success;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("邀请记录");
        rl_daishehe = (RelativeLayout) findViewById(R.id.rl_daishehe);
        tv_daishenhe = (TextView) findViewById(R.id.tv_daishenhe);
        view_daishenhe = findViewById(R.id.view_daishenhe);
        rl_success = (RelativeLayout) findViewById(R.id.rl_success);
        tv_success = (TextView) findViewById(R.id.tv_success);
        view_success = findViewById(R.id.view_success);

    }

    private FragmentManager fragmentManager;
    FragmentTransaction transaction;

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
                tv_daishenhe.setTextColor(Color.parseColor("#000000"));
                view_daishenhe.setVisibility(View.VISIBLE);
                if (checkInviteFragment == null) {
                    checkInviteFragment = new CheckInviteFragment();
                    transaction.add(R.id.content, checkInviteFragment);
                } else {
                    checkInviteFragment.setUserVisibleHint(true);
                    transaction.show(checkInviteFragment);
                }
                break;
            case 2:
                tv_success.setTextColor(Color.parseColor("#000000"));
                view_success.setVisibility(View.VISIBLE);
                if (succInviteFragment == null) {
                    succInviteFragment = new SuccInviteFragment();
                    transaction.add(R.id.content, succInviteFragment);
                } else {
                    succInviteFragment.setUserVisibleHint(true);
                    transaction.show(succInviteFragment);
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
        if (checkInviteFragment != null) {
            transaction.hide(checkInviteFragment);
        }
        if (succInviteFragment != null) {
            transaction.hide(succInviteFragment);
        }
    }

    private void hideTextAndView() {
        tv_daishenhe.setTextColor(Color.parseColor("#999999"));
        view_daishenhe.setVisibility(View.GONE);
        tv_success.setTextColor(Color.parseColor("#999999"));
        view_success.setVisibility(View.GONE);
    }

    public void notifyTitle(String checkitems, String succitems) {
        if (!checkitems.equals("") && !checkitems.equals("0")) {
            String daiStr = "待审核(" + checkitems + ")";
            SpannableString spanString = new SpannableString(daiStr);
            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#fc4d30"));
            spanString.setSpan(span, 3, daiStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_daishenhe.setText(spanString);
        }
        if (!succitems.equals("") && !succitems.equals("0")) {
            String succStr = "成功邀请" + succitems;
            tv_success.setText(succStr);
        }
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_daishehe.setOnClickListener(this);
        rl_success.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_daishehe:
                selectTab(1);
                break;
            case R.id.rl_success:
                selectTab(2);
                break;
        }
    }

}
