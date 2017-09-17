package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.SignaturePresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.BaseView;
import com.xdroid.common.utils.PreferenceHelper;

/**
 * Created by Administrator on 2016/12/15.
 */
public class SignatureActivity extends BaseActivity implements BaseView {
    @ViewInject(R.id.txt_confirm)
    private TextView txt_confirm;
    @ViewInject(R.id.edit_signature)
    private EditText edit_signature;
    @ViewInject(R.id.txt_signature_length)
    private TextView txt_signature_length;
    private String signature;
    private SignaturePresenter signaturePresenter;
    private String distributorid;
    private boolean ischange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_view);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        signature = getIntent().getStringExtra("signature");
        if (signature.length() > 0) {
            edit_signature.setText(signature);
        } else {
            edit_signature.setText(getResources().getString(R.string.text_hint_signature));
        }
        txt_signature_length.setText(String.valueOf(20 - edit_signature.getText().length()));
        edit_signature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ischange = true;
                if (edit_signature.length() < 1) {
                    txt_signature_length.setText("20");
                } else {
                    txt_signature_length.setText(String.valueOf(20 - s.length()));
                }
            }

        });
        signaturePresenter = new SignaturePresenter(this);
    }

    @OnClick({R.id.img_back, R.id.txt_confirm})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if (ischange) {
                    if (edit_signature.getText().toString().trim().length() > 0) {
                        showHintSaveDialog();
                    }else{
                        finish();
                    }
                } else {
                    finish();
                }
        break;
        case R.id.txt_confirm:
            if (edit_signature.getText().toString().trim().length() > 0) {
                String str_signature = edit_signature.getText().toString();
                String sign = TGmd5.getMD5(distributorid + str_signature);
                signaturePresenter.updateUserSign(distributorid, str_signature, sign);
                Intent intent = new Intent();
                intent.putExtra("signature", str_signature);
                setResult(2, intent);
                finish();
            }else{
                showHintDialog("请输入个性签名");
            }
        break;
    }

}

Dialog dialog_signature;

    public void showHintSaveDialog() {
//    dialog_report_turn
        dialog_signature = new Dialog(this, R.style.Mydialog);
        dialog_signature.setCanceledOnTouchOutside(false);
        View view1 = View.inflate(this,
                R.layout.dialog_signature_view, null);
        Button btn_cancel = (Button) view1.findViewById(R.id.btn_cancel);
        Button btn_save_sign = (Button) view1.findViewById(R.id.btn_save_sign);
        btn_save_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_signature.dismiss();
                String str_signature = edit_signature.getText().toString();
                String sign = TGmd5.getMD5(distributorid + str_signature);
                signaturePresenter.updateUserSign(distributorid, str_signature, sign);
                Intent intent = new Intent();
                intent.putExtra("signature", str_signature);
                setResult(2, intent);
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_signature.dismiss();
                finish();
            }
        });
        dialog_signature.setContentView(view1);
        dialog_signature.show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }

    @Override
    public void excuteSuccessCallBack(String s) {

    }

    @Override
    public void excuteFailedCallBack(String s) {
        MyToast.makeText(SignatureActivity.this, s, Toast.LENGTH_LONG);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ischange) {
                if (edit_signature.getText().toString().trim().length() > 0) {
                    showHintSaveDialog();
                    return true;
                }
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
