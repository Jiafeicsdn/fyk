package com.lvgou.distribution.activity;

import android.content.Intent;
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
import com.lvgou.distribution.fragment.HottestDoggerelFragment;
import com.lvgou.distribution.fragment.NewestDoggerelFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/31.
 * 顺口溜
 */

public class DoggerelActivity extends BaseActivity implements View.OnClickListener {

    private static final String[] TITLE = new String[]{"最新", "最热"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doggerel);
        initView();
        initClick();
    }

    private RelativeLayout rl_back;//返回
    private TextView tv_title;//标题
    private RelativeLayout rl_collect;//我的收藏
    private ViewPager mViewPager;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("顺口溜");
        rl_collect = (RelativeLayout) findViewById(R.id.rl_collect);
        mFragments.clear();
        NewestDoggerelFragment newestDoggerelFragment = new NewestDoggerelFragment();
        mFragments.add(newestDoggerelFragment);
        HottestDoggerelFragment hottestDoggerelFragment = new HottestDoggerelFragment();
        mFragments.add(hottestDoggerelFragment);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        SlidingTabLayout tabLayout_4 = (SlidingTabLayout) findViewById(R.id.tl_4);
        tabLayout_4.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_collect.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_collect://我的收藏
                Intent intent = new Intent(DoggerelActivity.this, MyCollectionActivity.class);
                intent.putExtra("position", 2);
                startActivity(intent);
//                openActivity(MyCollectionActivity.class);
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
