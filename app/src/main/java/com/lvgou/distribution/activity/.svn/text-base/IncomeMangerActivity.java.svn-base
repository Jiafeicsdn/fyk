package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;

/**
 * Created by Snow on 2016/10/12.
 * 收支管理
 */
public class IncomeMangerActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_income)
    private RelativeLayout rl_income;
    @ViewInject(R.id.rl_account)
    private RelativeLayout rl_account;
    @ViewInject(R.id.tv_account)
    private TextView tv_account;
    @ViewInject(R.id.rl_income_detial)
    private RelativeLayout rl_income_detial;
    @ViewInject(R.id.rl_tixian_record)
    private RelativeLayout rl_tixian_record;

    private String account = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_manger);
        ViewUtils.inject(this);
        tv_title.setText("收支管理");

        account = getTextFromBundle("account");

        tv_account.setText(account);
    }

    @OnClick({R.id.rl_back, R.id.rl_account, R.id.rl_income_detial, R.id.rl_tixian_record, R.id.rl_income})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_account:
                if (account.equals("")) {
                    openActivity(CashReceiveActivity.class);
                } else {
                    openActivity(AccountUpdateActivity.class);
                }
                break;
            case R.id.rl_income_detial:
                bundle.putString("selection_postion", "0");
                openActivity(PayDetailActivity.class, bundle);
                break;
            case R.id.rl_tixian_record:
                openActivity(CashRecordActivity.class);
                break;
            case R.id.rl_income:
                bundle.putString("index", "0");
                openActivity(MyProfitActivity.class, bundle);
                break;
        }
    }
}
