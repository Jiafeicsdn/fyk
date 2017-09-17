package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.fragment.FragmentDetial;
import com.lvgou.distribution.fragment.FragmentExchange;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Snow on 2016/3/24 0024.
 * 我的团币
 */
public class TaskDetialActivity extends FragmentActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_detial)
    private RelativeLayout rl_detial;
    @ViewInject(R.id.rl_exchange)
    private RelativeLayout rl_exchange;
    @ViewInject(R.id.tv_detial)
    private TextView tv_detial;
    @ViewInject(R.id.tv_exchange)
    private TextView tv_exchange;
    @ViewInject(R.id.view_detial)
    private ImageView view_detial;
    @ViewInject(R.id.view_exchange)
    private ImageView view_exchange;
    @ViewInject(R.id.id_viewpager)
    private ViewPager viewPager;

    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;
    private String tips = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        ViewUtils.inject(this);
        tv_title.setText("我的团币");
        EventBus.getDefault().register(this);
        tips = getIntent().getStringExtra("tips");
        if (tips.equals("1")) {
            setSelect(0);
        } else if (tips.equals("2")) {
            setSelect(1);
        }
    }

    @OnClick({R.id.rl_back, R.id.rl_detial, R.id.rl_exchange})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_detial:
                setSelect(0);
                break;
            case R.id.rl_exchange:
                setSelect(1);
                break;
        }
    }

    private void setSelect(int i) {
        initView();
        setTab(i);
        viewPager.setCurrentItem(i);
    }

    public void initView() {
        mFragments = new ArrayList<Fragment>();
        Fragment mTab02 = new FragmentDetial();
        Fragment mTab03 = new FragmentExchange();

        mFragments.add(mTab03);
        mFragments.add(mTab02);


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
        resetImgs();
        switch (i) {
            case 0:
                tv_detial.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                view_detial.setVisibility(View.VISIBLE);
                tv_title.setText("我的团币");
                break;
            case 1:
                tv_exchange.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                view_exchange.setVisibility(View.VISIBLE);
                tv_title.setText("我的团币");
                break;
        }
    }

    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.TASK_INDEX) {
            setSelect(event.getExtraData());
        }
    }

    /**
     * 清空所有选中状态
     */
    private void resetImgs() {
        tv_detial.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_exchange.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        view_detial.setVisibility(View.GONE);
        view_exchange.setVisibility(View.GONE);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
