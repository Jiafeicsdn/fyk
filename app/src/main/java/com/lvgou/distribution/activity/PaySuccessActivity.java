package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;

/**
 * Created by Administrator on 2017/4/7.
 */

public class PaySuccessActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paysuccess);
        initView();
        initClick();
    }

    private RelativeLayout rl_back;//返回
    private TextView tv_title;//标题
    private TextView tv_back;//返回
    private TextView tv_mycourse;//查看我的课程

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("支付成功");
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_mycourse = (TextView) findViewById(R.id.tv_mycourse);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_mycourse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
            case R.id.tv_back:
                PaySuccessActivity.this.setResult(RESULT_OK);
                PaySuccessActivity.this.finish();
                break;
            case R.id.tv_mycourse:
                openActivity(MyCourseActivity.class);
                break;

        }
    }

    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            PaySuccessActivity.this.setResult(RESULT_OK);
            PaySuccessActivity.this.finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
