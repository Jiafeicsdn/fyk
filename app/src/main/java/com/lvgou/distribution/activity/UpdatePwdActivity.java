package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ssnow on 2016/3/9 0009.
 * 修改密码
 */
public class UpdatePwdActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_tilte;
    @ViewInject(R.id.et_old_pwd)
    private EditText et_old_pwd;
    @ViewInject(R.id.et_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.et_sure_pwd)
    private EditText et_sure_pwd;
    @ViewInject(R.id.btn_save)
    private Button btn_save;

    private String distributorid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        ViewUtils.inject(this);
        tv_tilte.setText("修改密码");
        distributorid = PreferenceHelper.readString(UpdatePwdActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
    }

    @OnClick({R.id.rl_back, R.id.btn_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_save:
                String old_pwd_ = et_old_pwd.getText().toString().trim();
                String pwd_ = et_pwd.getText().toString().trim();
                String sure_pwd_ = et_sure_pwd.getText().toString().trim();
                String sign = TGmd5.getMD5(distributorid + old_pwd_ + pwd_ + sure_pwd_);
                if (checkForm()) {
                    if (checkNet()) {
                        updatePwd(distributorid, old_pwd_, pwd_, sure_pwd_, sign);
                    }
                }
                break;
        }
    }

    /**
     * 表单验证
     */
    public boolean checkForm() {
        String old_pwd = et_old_pwd.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        String sure_pwd = et_sure_pwd.getText().toString().trim();
        if (StringUtils.isEmpty(old_pwd)) {
            MyToast.show(UpdatePwdActivity.this, "请输入旧密码");
            return false;
        } else if (old_pwd.length() < 6 || old_pwd.length() > 17) {
            MyToast.show(UpdatePwdActivity.this, "密码由6-16个数字、字母组成");
            return false;
        } else if (StringUtils.isEmpty(pwd)) {
            MyToast.show(UpdatePwdActivity.this, "请输入新密码");
            return false;
        } else if (pwd.length() < 6 || pwd.length() > 17) {
            MyToast.show(UpdatePwdActivity.this, "密码由6-16个数字、字母组成");
            return false;
        } else if (StringUtils.isEmpty(sure_pwd)) {
            MyToast.show(UpdatePwdActivity.this, "请确认密码");
            return false;
        } else if (!sure_pwd.equals(pwd)) {
            MyToast.show(UpdatePwdActivity.this, "两次密码不一致");
            return false;
        }
        return true;
    }

    /**
     * 修改密码
     */
    public void updatePwd(String distributorid, String oldpwd, String newpwd, String newpwd1, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("oldpwd", oldpwd);
        maps.put("newpwd", newpwd);
        maps.put("newpwd1", newpwd1);
        maps.put("sign", sign);

        RequestTask.getInstance().doUpdatePwd(UpdatePwdActivity.this, maps, new OnRequestListener());
    }


    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(UpdatePwdActivity.this, "修改成功");
                    finish();
                } else if (status.equals("0")) {
                    MyToast.show(UpdatePwdActivity.this, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
