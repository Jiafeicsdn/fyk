package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Snow on 2016/5/25 0025.
 * 账户管理
 */
public class ShowAccountActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;
    @ViewInject(R.id.tv_account_type)
    private TextView tv_account_type;
    @ViewInject(R.id.tv_account_num)
    private TextView tv_account_num;
    @ViewInject(R.id.tv_account_name)
    private TextView tv_account_name;
    @ViewInject(R.id.tv_account_phone)
    private TextView tv_account_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_account);
        ViewUtils.inject(this);
        tv_title.setText("提现账户");
        tv_right.setText("修改");
    }

    @OnClick({R.id.rl_back, R.id.rl_publish})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
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
