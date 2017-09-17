package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.CircleCommZanEntity;
import com.lvgou.distribution.fragment.MyZanListFragment;
import com.lvgou.distribution.fragment.PraisedListFragment;
import com.lvgou.distribution.presenter.PersonalCircleListPresenter;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.functions.holder.ListViewDataAdapter;

/**
 * Created by Snow on 2016/10/18.
 * 我的点赞列表
 */
public class MyZanListActivity extends BaseActivity {

    @ViewInject(R.id.txt_myzan)
    private TextView txt_myzan;
    @ViewInject(R.id.view01)
    private View view01;
    @ViewInject(R.id.txt_praised)
    private TextView txt_praised;
    @ViewInject(R.id.view02)
    private View view02;
    @ViewInject(R.id.fm_content)
    private FrameLayout fm_content;
    private ListViewDataAdapter<CircleCommZanEntity> circleCommZanEntityListViewDataAdapter;

    private ListView listView;
    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private PersonalCircleListPresenter personalCircleListPresenter;

    private String prePageLastDataObjectId = "";

    private String fenwenId = "";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    private MyZanListFragment myZanListFragment;
    private PraisedListFragment praisedListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zan);
        ViewUtils.inject(this);
        if (savedInstanceState == null) {
            praisedListFragment = new PraisedListFragment();
//            myZanListFragment = new MyZanListFragment();
//            praisedListFragment = new PraisedListFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.add(R.id.fm_content, praisedListFragment);
//            tx.add(R.id.fm_content, praisedListFragment).hide(praisedListFragment);
            tx.commit();
        }
    }


    @OnClick({R.id.img_back, R.id.rl_title1, R.id.rl_title2})
    public void OnClick(View view) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_title1:
                hideFragments(tx);
                changeView();
                txt_myzan.setTextColor(getResources().getColor(R.color.text_color_333333));
                view01.setVisibility(View.VISIBLE);
                if (myZanListFragment == null) {
                    myZanListFragment = new MyZanListFragment();
                    tx.add(R.id.fm_content, myZanListFragment);
                } else {
                    tx.show(myZanListFragment);
                }
                tx.commit();
                break;
            case R.id.rl_title2:
                hideFragments(tx);
                if (praisedListFragment == null) {
                    praisedListFragment = new PraisedListFragment();
                    tx.add(R.id.fm_content, praisedListFragment);
                } else {
                    tx.show(praisedListFragment);
                }
                tx.commit();
                changeView();
                txt_praised.setTextColor(getResources().getColor(R.color.text_color_333333));
                view02.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void hideFragments(FragmentTransaction transaction) {
        if (myZanListFragment != null) {
            transaction.hide(myZanListFragment);
        }
        if (praisedListFragment != null) {
            transaction.hide(praisedListFragment);
        }
    }
    private void changeView() {
        txt_myzan.setTextColor(getResources().getColor(R.color.text_color_999999));
        txt_praised.setTextColor(getResources().getColor(R.color.text_color_999999));
        view01.setVisibility(View.INVISIBLE);
        view02.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

//    /***
//     * item 点击事件处理
//     *
//     * @param itemData
//     * @param postion
//     */
//    @Override
//    public void onClassifyPostionClick(CircleCommZanEntity itemData, int postion) {
//        Bundle bundle = new Bundle();
//        switch (postion) {
//            case 1:
//                fenwenId = itemData.getFenwenId();
//                String sign_ = TGmd5.getMD5(distributorid + itemData.getFenwenId());
//                personalCircleListPresenter.getUserType(distributorid, itemData.getFenwenId(), sign_);
//                break;
//            case 3:
//                bundle.putInt("distributorid", Integer.parseInt(itemData.getFengwenDistributorID()));
//                openActivity(UserPersonalCenterActivity.class, bundle);
//                break;
//            case 2:
//                bundle.putInt("distributorid", Integer.parseInt(itemData.getDistributorID()));
//                openActivity(UserPersonalCenterActivity.class, bundle);
//                break;
//        }
//    }
}