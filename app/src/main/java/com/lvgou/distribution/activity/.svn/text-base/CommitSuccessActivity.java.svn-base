package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.xdroid.common.utils.FunctionUtils;

/**
 * Created by Snow on 2016/7/27 0027.
 * 提交成功
 */
public class CommitSuccessActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.rl_call)
    private RelativeLayout rl_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_success);
        ViewUtils.inject(this);
    }

    @OnClick({R.id.rl_back, R.id.rl_call})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_call:
                FunctionUtils.jump2PhoneView(CommitSuccessActivity.this, "4008017579");
                break;
        }
    }
}
