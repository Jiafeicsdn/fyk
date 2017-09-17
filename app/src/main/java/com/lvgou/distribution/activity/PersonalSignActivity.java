package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.SignaturePresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.BaseView;
import com.xdroid.common.utils.PreferenceHelper;

/**
 * Created by Administrator on 2017/4/7.
 * 个性签名
 */

public class PersonalSignActivity extends BaseActivity implements View.OnClickListener, BaseView {
    private SignaturePresenter signaturePresenter;
    private String personalsign;
    private String str_signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_sign);
        signaturePresenter = new SignaturePresenter(this);
        personalsign = mcache.getAsString("personalsign");
        initView();
        initClick();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private TextView tv_sure;//保存
    private EditText et_sign;//编辑个性签名
    private TextView tv_text_num;//字数

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("个性签名");
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        et_sign = (EditText) findViewById(R.id.et_sign);
        et_sign.setText(personalsign);
        tv_text_num = (TextView) findViewById(R.id.tv_text_num);
        //改变默认的单行模式
        et_sign.setSingleLine(false);
        //水平滚动设置为False
        et_sign.setHorizontallyScrolling(false);
        TextChangeListener();
    }

    public void TextChangeListener() {
        et_sign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = et_sign.getText().toString().length();
                if (length >= 0 && length <= 20) {
                    tv_text_num.setText((20 - length)+"");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_sure://保存
                str_signature = et_sign.getText().toString().trim();
                if (str_signature == null || str_signature.equals("")) {
                    MyToast.makeText(this, "请输入个性签名！", Toast.LENGTH_SHORT).show();
                    break;
                }
                String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                String sign = TGmd5.getMD5(distributorid + str_signature);
                showLoadingProgressDialog(this, "");
                signaturePresenter.updateUserSign(distributorid, str_signature, sign);
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteSuccessCallBack(String s) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        mcache.put("personalsign",str_signature);
        finish();
    }

    @Override
    public void excuteFailedCallBack(String s) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "" + s, Toast.LENGTH_SHORT).show();
    }
}
