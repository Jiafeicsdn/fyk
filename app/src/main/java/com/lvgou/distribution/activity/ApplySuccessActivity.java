package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.xdroid.common.utils.FunctionUtils;

/**
 * Created by Snow on 2016/9/23.
 * 申请成功
 */
public class ApplySuccessActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_call_phone)
    private TextView tv_call_phone;
    @ViewInject(R.id.tv_apply_teacher)
    private TextView tv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_success);
        ViewUtils.inject(this);
    }

    @OnClick({R.id.rl_back, R.id.tv_call_phone, R.id.tv_apply_teacher})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_call_phone:
                FunctionUtils.jump2PhoneView(ApplySuccessActivity.this, "4008017579");
                break;
            case R.id.tv_apply_teacher:
                finish();
                break;
        }
    }
}
