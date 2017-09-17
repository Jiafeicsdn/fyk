package com.lvgou.distribution.activity;

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
import com.lvgou.distribution.fragment.NotUseVoucherFragment;
import com.lvgou.distribution.fragment.UseVoucherFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/29.
 * 听课券
 */

public class GiftVoucherActivity extends BaseActivity implements View.OnClickListener {
    private static final String[] TITLE = new String[]{"未使用", "已使用"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giftvoucher_activity);
        initView();
        initClick();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private ViewPager mViewPager;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("听课券");
        mFragments.clear();
        //未使用
        NotUseVoucherFragment notUseVoucherFragment = new NotUseVoucherFragment();
        mFragments.add(notUseVoucherFragment);
        //已使用
        UseVoucherFragment useVoucherFragment = new UseVoucherFragment();
        mFragments.add(useVoucherFragment);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        SlidingTabLayout tabLayout_4 = (SlidingTabLayout) findViewById(R.id.tl_4);
        tabLayout_4.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
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
}
