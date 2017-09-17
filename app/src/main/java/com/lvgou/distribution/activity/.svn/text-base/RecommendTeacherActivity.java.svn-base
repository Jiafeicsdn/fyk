package com.lvgou.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.RecommendSubmitPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.RecommendSubmitView;
import com.xdroid.common.utils.PreferenceHelper;

/**
 * Created by Administrator on 2017/4/28.
 */

public class RecommendTeacherActivity extends BaseActivity implements View.OnClickListener, RecommendSubmitView {
    private RecommendSubmitPresenter recommendSubmitPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendteacher);
        recommendSubmitPresenter = new RecommendSubmitPresenter(this);
        initView();
        initClick();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private RelativeLayout rl_record;
    private EditText et_name;
    private EditText et_weixin;
    private EditText et_call;
    private EditText et_simple_introduce;
    private TextView tv_submit;//提交申请

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("推荐讲师");
        rl_record = (RelativeLayout) findViewById(R.id.rl_record);
        et_name = (EditText) findViewById(R.id.et_name);
        et_weixin = (EditText) findViewById(R.id.et_weixin);
        et_call = (EditText) findViewById(R.id.et_call);
        et_simple_introduce = (EditText) findViewById(R.id.et_simple_introduce);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        //改变默认的单行模式
        et_simple_introduce.setSingleLine(false);
        //水平滚动设置为False
        et_simple_introduce.setHorizontallyScrolling(false);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_record.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_record://推荐记录
                Intent intent = new Intent(RecommendTeacherActivity.this, RecommendRecordActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_submit://提交申请
                String name = et_name.getText().toString().trim();
                if (name.equals("")) {
                    MyToast.makeText(this, "请填写推荐讲师的姓名", Toast.LENGTH_SHORT).show();
                    break;
                }
                String weixin = et_weixin.getText().toString().trim();
                if (weixin.equals("")) {
                    MyToast.makeText(this, "请填写推荐讲师的微信号码", Toast.LENGTH_SHORT).show();
                    break;
                }
                String call = et_call.getText().toString().trim();
                if (call.equals("")) {
                    MyToast.makeText(this, "请填写推荐讲师的手机号码", Toast.LENGTH_SHORT).show();
                    break;
                }
                String introduce = et_simple_introduce.getText().toString().trim();
                if (introduce.equals("")) {
                    MyToast.makeText(this, "请填写推荐的理由", Toast.LENGTH_SHORT).show();
                    break;
                }
                String distributorid = PreferenceHelper.readString(RecommendTeacherActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, "0");
                String sign = TGmd5.getMD5(distributorid + name + weixin + call + introduce);
                showLoadingProgressDialog(this, "");
                recommendSubmitPresenter.recommendSubmit(distributorid, name, weixin, call, introduce, sign);
                break;
        }
    }

    @Override
    public void OnRecommendSubmitSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "推荐成功，请等待审核", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnRecommendSubmitFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeRecommendSubmitProgress() {

    }
}
