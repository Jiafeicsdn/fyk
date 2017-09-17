package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;

/**
 * Created by Snow on 2016/4/28 0028.
 * 注册成功
 */
public class RegisterSuccessActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.img_code)
    private ImageView img_code;
    @ViewInject(R.id.btn_download)
    private Button btn_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
        ViewUtils.inject(this);
        tv_title.setText("注册成功");
    }

    @OnClick({R.id.rl_back, R.id.img_code, R.id.btn_download})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_download:

                break;
            case R.id.img_code:

                break;
        }
    }
}
