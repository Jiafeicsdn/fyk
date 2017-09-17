package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.BankEntity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Snow on 2016/7/27 0027.
 * 新手指南
 */
public class OpreatGuideActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.ll_00)
    private LinearLayout ll_00;
    @ViewInject(R.id.ll_01)
    private LinearLayout ll_01;
    @ViewInject(R.id.ll_02)
    private LinearLayout ll_02;
    @ViewInject(R.id.ll_03)
    private LinearLayout ll_03;
    @ViewInject(R.id.ll_04)
    private LinearLayout ll_04;
    @ViewInject(R.id.ll_05)
    private LinearLayout ll_05;
    @ViewInject(R.id.ll_06)
    private LinearLayout ll_06;
    @ViewInject(R.id.ll_07)
    private LinearLayout ll_07;

    private String index = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opreat_guide);
        ViewUtils.inject(this);
        index = getTextFromBundle("index");
    }

    @OnClick({R.id.rl_back, R.id.ll_00, R.id.ll_01, R.id.ll_02, R.id.ll_03, R.id.ll_04, R.id.ll_05, R.id.ll_06, R.id.ll_07})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    bundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, bundle);
                } else {
                    finish();
                }
                break;
            case R.id.ll_00:
                bundle.putString("page_id", "132");
                bundle.putString("name", "找团不愁，派团不忧");
                openActivity(NewGuidersDetialActivity.class, bundle);
                break;
            case R.id.ll_01:// 蜂优学院
                bundle.putString("page_id", "122");
                bundle.putString("name", "导游从业者的在线学习社群");
                openActivity(NewGuidersDetialActivity.class, bundle);
                break;
            case R.id.ll_02:// 随时赚
                bundle.putString("page_id", "127");
                bundle.putString("name", "轻轻松松赚闲钱");
                openActivity(NewGuidersDetialActivity.class, bundle);
                break;
            case R.id.ll_03:// 进店报备
                bundle.putString("page_id", "130");
                bundle.putString("name", "一键预约，信息直达");
                openActivity(NewGuidersDetialActivity.class, bundle);
                break;
            case R.id.ll_04:// 免费短信
                bundle.putString("page_id", "129");
                bundle.putString("name", "省去时间，简单操作");
                openActivity(NewGuidersDetialActivity.class, bundle);
                break;
            case R.id.ll_05:// 团币
                bundle.putString("page_id", "124");
                bundle.putString("name", "团币的用途和赚取方式");
                openActivity(NewGuidersDetialActivity.class, bundle);
                break;
            case R.id.ll_06:// 新手前三步
                bundle.putString("page_id", "118");
                bundle.putString("name", "头像，店铺名称，绑定账号");
                openActivity(NewGuidersDetialActivity.class, bundle);
                break;
            case R.id.ll_07:// 如何提现佣金
                bundle.putString("page_id", "126");
                bundle.putString("name", "把赚到的佣金转入你的账户吧");
                openActivity(NewGuidersDetialActivity.class, bundle);
                break;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
