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
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.fragment.CollecteDoggerelFragment;
import com.lvgou.distribution.fragment.CollecteDynamicFragment;
import com.lvgou.distribution.fragment.CollecteJokeFragment;
import com.xdroid.common.utils.PreferenceHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/21.
 * 我的收藏
 */

public class MyCollectionActivity extends BaseActivity implements View.OnClickListener {
    private static final String[] TITLE = new String[]{"动态", "笑话", "顺口溜"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String distributorid = "";
    private int currPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        currPosition = getIntent().getIntExtra("position", 0);
        initView();
        initClick();
    }

    private RelativeLayout rl_back;//返回
    private TextView tv_title;
    private ViewPager mViewPager;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的收藏");

        mFragments.clear();
        //动态
        CollecteDynamicFragment collecteDynamicFragment = new CollecteDynamicFragment();
        mFragments.add(collecteDynamicFragment);
        //笑话
        CollecteJokeFragment collecteJokeFragment = new CollecteJokeFragment();
        mFragments.add(collecteJokeFragment);
        //顺口溜
        CollecteDoggerelFragment collecteDoggerelFragment = new CollecteDoggerelFragment();
        mFragments.add(collecteDoggerelFragment);

        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        SlidingTabLayout tabLayout_4 = (SlidingTabLayout) findViewById(R.id.tl_4);
        tabLayout_4.setViewPager(mViewPager);
        mViewPager.setCurrentItem(currPosition);
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
