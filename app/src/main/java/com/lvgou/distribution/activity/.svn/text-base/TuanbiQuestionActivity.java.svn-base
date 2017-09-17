package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;

/**
 * Created by Administrator on 2017/5/5.
 */

public class TuanbiQuestionActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuanbi_question);
        initView();
        initClick();
    }

    private TextView tv_title;
    private RelativeLayout rl_sure;//确定

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("常见问题");
        rl_sure = (RelativeLayout) findViewById(R.id.rl_sure);
    }

    private void initClick() {
        rl_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_sure:
                finish();
                break;
        }
    }
}
