package com.lvgou.distribution.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.exception.DbException;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.fragment.DownloadingFragment;
import com.lvgou.distribution.fragment.ListenClassFragment;
import com.lvgou.distribution.fragment.OpenClassFragment;
import com.lvgou.distribution.utils.DownloadInfo;
import com.lvgou.distribution.utils.DownloadManager;
import com.lvgou.distribution.utils.DownloadService;
import com.lvgou.distribution.video.TextureVideoView;
import com.superplayer.library.SuperPlayer;
import com.xdroid.common.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;

import cn.aigestudio.downloader.bizs.DLManager;

/**
 * Created by Administrator on 2017/3/24.
 * 我的课程
 */

public class MyCourseActivity extends BaseActivity implements View.OnClickListener {
    private static final String[] TITLE = new String[]{"我买的课", "我开的课", "下载列表"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    public ArrayList<HashMap<String, Object>> array = new ArrayList<HashMap<String, Object>>();
    public ListenClassFragment listenClassFragment;
    public OpenClassFragment openClassFragment;
    public DownloadingFragment downloadingFragment;
    private String distributorid = "";
    private int currentItem=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycourse);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        currentItem=getIntent().getIntExtra("currentItem",0);
        initDatas();
        initView();
        initClick();
    }

    private void initDatas() {
        array = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("xiazaiduilie" + distributorid);
        if (array == null) {
            array = new ArrayList<>();
        }
        //app启动的时候停止所有下载
        DownloadManager downloadManager = DownloadService.getDownloadManager(this);
        try {
            downloadManager.stopAllDownload();

            for (HashMap<String, Object> hashMap : array) {
                if (hashMap.get("isdown").toString().endsWith("true")) {
                    DownloadInfo downloadInfo = downloadManager.getDownLoadInfo(hashMap.get("Comment").toString());
                    if (downloadInfo != null) {
                        downloadManager.resumeDownload(downloadInfo, null);
                    }
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private TextView tv_gold_notes;//听课券
    private ViewPager mViewPager;
    private RelativeLayout rl_gold_notes;//听课券
//    private TextView notes_num;//听课券数量

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的课程");
        tv_gold_notes = (TextView) findViewById(R.id.tv_gold_notes);
        rl_gold_notes = (RelativeLayout) findViewById(R.id.rl_gold_notes);
//        notes_num = (TextView) findViewById(R.id.notes_num);
        mFragments.clear();
        //我买的课
        listenClassFragment = new ListenClassFragment();
        mFragments.add(listenClassFragment);
        //我开的课
        openClassFragment = new OpenClassFragment();
        mFragments.add(openClassFragment);
        //下载中
        downloadingFragment = new DownloadingFragment();
        mFragments.add(downloadingFragment);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        SlidingTabLayout tabLayout_4 = (SlidingTabLayout) findViewById(R.id.tl_4);
        tabLayout_4.setViewPager(mViewPager);
        mViewPager.setCurrentItem(currentItem);

    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_gold_notes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_gold_notes://听课券
                //进入听课券页面
                openActivity(GiftVoucherActivity.class);
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
