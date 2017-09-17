package com.lvgou.distribution.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.fragment.FragmentDistributionMarket;
import com.lvgou.distribution.fragment.FragmentMyGoods;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.eventbus.EventBus;


/**
 * Created by Administrator on 2016/3/24 0024.
 * 商品管理
 */
public class GoodsMnagerActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.rl_shop)
    private RelativeLayout rl_shop;
    @ViewInject(R.id.rl_market)
    private RelativeLayout rl_market;
    @ViewInject(R.id.tv_shop)
    private TextView tv_shop;
    @ViewInject(R.id.tv_market)
    private TextView tv_market;
    @ViewInject(R.id.view_shop)
    private ImageView view_shop;
    @ViewInject(R.id.view_market)
    private ImageView view_market;
    @ViewInject(R.id.img_one)
    private ImageView img_one;
    @ViewInject(R.id.img_two)
    private ImageView img_two;

    private FragmentDistributionMarket fragmentDistributionMarket;
    private FragmentMyGoods fragmentMyGoods;

    private FragmentManager fragmentManager;
    private String selected = "";

    private String login_count = "";

    private String aggent_count = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_manger);
        ViewUtils.inject(this);
        EventBus.getDefault().register(this);
        selected = getTextFromBundle("selected");
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        selectTab(Integer.parseInt(selected));
    }

    @OnClick({R.id.rl_back, R.id.rl_shop, R.id.rl_market, R.id.img_one, R.id.img_two})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                Constants.SELECTE_POSITION01 = "0";
                Constants.SELECTE_POSITION02 = "0";
                Constants.SELECTE_POSITION03 = "0";
//                Constants.SELECTE_POSITION04 = "0";
                Constants.SELECTE_POSITION05 = "0";
                break;
            case R.id.rl_shop:
                EventFactory.marketDismiss(1);
                selectTab(2);// 分销市场
                if (aggent_count.equals("0")) {
                    img_two.setVisibility(View.VISIBLE);
                }
                Constants.SELECTE_POSITION01 = "0";
                Constants.SELECTE_POSITION02 = "0";
                Constants.SELECTE_POSITION03 = "0";
                break;
            case R.id.rl_market:
                Constants.SELECTE_POSITION01 = "0";
                Constants.SELECTE_POSITION05 = "0";
                EventFactory.goodsDismiss(1);
                selectTab(1);// 我的商品
                break;
            case R.id.img_one:
                img_one.setVisibility(View.GONE);
                break;
            case R.id.img_two:
                img_two.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标。 1 店铺商品，2 分销市场
     */
    public void selectTab(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 1:
                fragmentMyGoods = null;
                tv_market.setTextColor(getResources().getColor(R.color.bg_goods_manger_black));
                view_market.setVisibility(View.VISIBLE);

                if (fragmentDistributionMarket == null) {
                    fragmentDistributionMarket = new FragmentDistributionMarket();
                    transaction.add(R.id.content, fragmentDistributionMarket);
                } else {
                    transaction.show(fragmentDistributionMarket);
                }
                break;
            case 2:
                fragmentDistributionMarket = null;
                tv_shop.setTextColor(getResources().getColor(R.color.bg_goods_manger_black));
                view_shop.setVisibility(View.VISIBLE);
                if (login_count.equals("1")) {
                    img_one.setVisibility(View.VISIBLE);
                }
                if (fragmentMyGoods == null) {
                    fragmentMyGoods = new FragmentMyGoods();
                    transaction.add(R.id.content, fragmentMyGoods);
                } else {
                    transaction.show(fragmentMyGoods);
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
    private void hideFragments(FragmentTransaction transaction) {
        if (fragmentDistributionMarket != null) {
            transaction.hide(fragmentDistributionMarket);
        }
        if (fragmentMyGoods != null) {
            transaction.hide(fragmentMyGoods);
        }
    }

    /**
     * 初始化状态选中
     */
    public void clearSelection() {
        tv_shop.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_market.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        view_shop.setVisibility(View.GONE);
        view_market.setVisibility(View.GONE);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.GOODS_INDEX) {
            if (event.getExtraData() == 2) {
                selectTab(event.getExtraData());// 分销市场
                img_two.setVisibility(View.VISIBLE);
            } else if (event.getExtraData() == 3) {
                aggent_count = "0";
            }
        } else if (event.getEventType() == EventType.GOODS_GUIDER) {
            if (event.getExtraData() == 0) {
                img_one.setVisibility(View.VISIBLE);
            }
            aggent_count = event.getExtraData() + "";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
