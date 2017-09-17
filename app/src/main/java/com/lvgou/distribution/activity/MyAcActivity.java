package com.lvgou.distribution.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.lvgou.distribution.R;
import com.lvgou.distribution.fragment.AllClassFragment;
import com.lvgou.distribution.fragment.AllCreClassFragment;
import com.lvgou.distribution.fragment.EndCreActFragment;
import com.lvgou.distribution.fragment.EndFragment;
import com.lvgou.distribution.fragment.GoingClassFragment;
import com.lvgou.distribution.fragment.GoingCreActFragment;
import com.lvgou.distribution.presenter.ApplyForMePresenter;
import com.lvgou.distribution.view.ApplyForMeView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/29.
 */

public class MyAcActivity extends BaseActivity implements View.OnClickListener {
    private static final String[] TITLE = new String[]{"全部", "进行中", "已结束"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();
    public AllClassFragment allClassFragment;
    public GoingClassFragment goingClassFragment;
    public EndFragment endFragment;

    private ViewPager mViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity);

        initView();
        initClick();
    }

    private RelativeLayout rl_back;
    private TextView tv_my_apply;//我报名的
    private TextView tv_my_create;//我创建的
    private ViewPager mViewPager;
    private RelativeLayout rl_scroll_button2;
    private RelativeLayout rl_scroll_button;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_my_apply = (TextView) findViewById(R.id.tv_my_apply);
        tv_my_create = (TextView) findViewById(R.id.tv_my_create);
        rl_scroll_button2 = (RelativeLayout) findViewById(R.id.rl_scroll_button2);
        rl_scroll_button = (RelativeLayout) findViewById(R.id.rl_scroll_button);
        rl_scroll_button.setVisibility(View.VISIBLE);
        rl_scroll_button2.setVisibility(View.GONE);
        mFragments.clear();
        //全部
        allClassFragment = new AllClassFragment();
        mFragments.add(allClassFragment);
        //进行中
        goingClassFragment = new GoingClassFragment();
        mFragments.add(goingClassFragment);
        //已结束
        endFragment = new EndFragment();
        mFragments.add(endFragment);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        SlidingTabLayout tabLayout_4 = (SlidingTabLayout) findViewById(R.id.tl_4);
        tabLayout_4.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);

        mFragments2.clear();

        //全部
        AllCreClassFragment allCreClassFragment = new AllCreClassFragment();
        mFragments2.add(allCreClassFragment);
        //进行中
        GoingCreActFragment goingCreActFragment = new GoingCreActFragment();
        mFragments2.add(goingCreActFragment);
        //已结束
        EndCreActFragment endCreActFragment = new EndCreActFragment();
        mFragments2.add(endCreActFragment);
        mViewPager2 = (ViewPager) findViewById(R.id.vp2);
        mViewPager2.setAdapter(new MyPagerAdapter2(getSupportFragmentManager()));
        SlidingTabLayout tabLayout_2 = (SlidingTabLayout) findViewById(R.id.tl_2);
        tabLayout_2.setViewPager(mViewPager2);
        mViewPager2.setCurrentItem(0);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        tv_my_apply.setOnClickListener(this);
        tv_my_create.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_my_apply:
                //我报名的
                tv_my_create.setBackgroundColor(Color.parseColor("#00000000"));
                tv_my_create.setTextColor(Color.parseColor("#333333"));
                tv_my_apply.setBackgroundResource(R.drawable.myact_left_radius);
                tv_my_apply.setTextColor(Color.parseColor("#ffffff"));
                rl_scroll_button.setVisibility(View.VISIBLE);
                rl_scroll_button2.setVisibility(View.GONE);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_my_create:
                //我创建的
                tv_my_apply.setBackgroundColor(Color.parseColor("#00000000"));
                tv_my_apply.setTextColor(Color.parseColor("#333333"));
                tv_my_create.setBackgroundResource(R.drawable.myact_right_radius);
                tv_my_create.setTextColor(Color.parseColor("#ffffff"));
                rl_scroll_button.setVisibility(View.GONE);
                rl_scroll_button2.setVisibility(View.VISIBLE);
                mViewPager2.setCurrentItem(0);
                break;
        }
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
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        //----------------------------
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }

    /**
     * ViewPager 适配器
     */
    private class MyPagerAdapter2 extends FragmentPagerAdapter {

        public MyPagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments2.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments2.get(position);
        }

        //----------------------------
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }
}
