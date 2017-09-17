package com.lvgou.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.fragment.ApplicationFragment;
import com.lvgou.distribution.fragment.ProfitFragment;
import com.lvgou.distribution.fragment.RecordFragment;
import com.umeng.analytics.MobclickAgent;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.utils.AppManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2016/4/27 0027.
 * 随时赚  老版  可删
 */
public class MakeAnyTimeActivity extends FragmentActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;
    @ViewInject(R.id.rl_application)
    private RelativeLayout rl_application;
    @ViewInject(R.id.rl_profit)
    private RelativeLayout rl_profit;
    @ViewInject(R.id.rl_withwords)
    private RelativeLayout rl_withwords;
    @ViewInject(R.id.tv_application)
    private TextView tv_application;
    @ViewInject(R.id.tv_profit)
    private TextView tv_profit;
    @ViewInject(R.id.tv_withwords)
    private TextView tv_withwords;
    @ViewInject(R.id.view_application)
    private View view_application;
    @ViewInject(R.id.view_profit)
    private View view_profit;
    @ViewInject(R.id.view_withwords)
    private View view_withwords;
    @ViewInject(R.id.id_viewpager)
    private ViewPager viewPager;


    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_anytime);
        ViewUtils.inject(this);
        tv_title.setText("选择应用");
        rl_publish.setVisibility(View.VISIBLE);
        rl_back.setVisibility(View.GONE);
        tv_right.setText("说明");
    }

    @OnClick({R.id.rl_back, R.id.rl_publish, R.id.rl_application, R.id.rl_profit, R.id.rl_withwords})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
                Intent intent = new Intent(MakeAnyTimeActivity.this, ExplainActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_application:
                setSelect(0);
                break;
            case R.id.rl_profit:
                setSelect(1);
                break;
            case R.id.rl_withwords:
                setSelect(2);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.INDEX_PROFIT.equals("0")) {
            setSelect(0);
        } else if (Constants.INDEX_PROFIT.equals("1")) {
            setSelect(1);
        }
    }

    public void onPause() {
        super.onPause();
    }

    /**
     * 清空所有选中状态
     */
    public void initSelected() {
        tv_application.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        tv_profit.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        tv_withwords.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        view_application.setVisibility(View.GONE);
        view_profit.setVisibility(View.GONE);
        view_withwords.setVisibility(View.GONE);
    }

    private void setSelect(int i) {
        initView();
        setTab(i);
        viewPager.setCurrentItem(i);
    }

    public void initView() {
        mFragments = new ArrayList<Fragment>();
        Fragment mTab01 = new ApplicationFragment();
        Fragment mTab02 = new ProfitFragment();
        Fragment mTab03 = new RecordFragment();

        mFragments.add(mTab01);
        mFragments.add(mTab02);
        mFragments.add(mTab03);


        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }
            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        viewPager.setAdapter(mAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                int currentItem = viewPager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }
    
    private void setTab(int i) {
        initSelected();
        switch (i) {
            case 0:
                tv_application.setTextColor(getResources().getColor(R.color.bg_my_task_num_color));
                view_application.setVisibility(View.VISIBLE);
                tv_title.setText("选择应用");
                break;
            case 1:
                tv_profit.setTextColor(getResources().getColor(R.color.bg_my_task_num_color));
                view_profit.setVisibility(View.VISIBLE);
                tv_title.setText("推广收益");
                break;
            case 2:
                tv_withwords.setTextColor(getResources().getColor(R.color.bg_my_task_num_color));
                view_withwords.setVisibility(View.VISIBLE);
                tv_title.setText("提现记录");
                break;
        }
    }
    long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            AppManager.getInstance().AppExit(getApplicationContext());
        } else {
            MyToast.show(MakeAnyTimeActivity.this, "再按一次退出应用！");
        }
        back_pressed = System.currentTimeMillis();
    }
}
