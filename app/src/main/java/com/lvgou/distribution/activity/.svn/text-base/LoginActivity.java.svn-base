package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.animactivity3d.ActivitySwitcher;
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
import com.xdroid.utils.AppManager;

import org.apache.commons.logging.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/3/7 0007.
 * 登录
 */
public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.et_name)
    private EditText et_phone;
    @ViewInject(R.id.et_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.tv_register)
    private TextView tv_register;
    @ViewInject(R.id.tv_forget_pwd)
    private TextView tv_forget;
    @ViewInject(R.id.btn_login)
    private Button btn_login;

    private Dialog dialog_stop;
    private String is_loggin;

    private String name;
    private String pwd;

    private String str_name;
    private String str_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        ActivitySwitcher.animationIn(findViewById(R.id.ll_login), getWindowManager());
        ViewUtils.inject(this);
        str_name = PreferenceHelper.readString(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ACCOUNT);
        str_pwd = PreferenceHelper.readString(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PWD);
        et_phone.setText(str_name);
        et_phone.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    @OnClick({R.id.tv_register, R.id.tv_forget_pwd, R.id.btn_login})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                openActivity(RegisterNewActivity.class);
                break;
            case R.id.tv_forget_pwd:
                openActivity(ForgetPwdActivity.class);
                break;
            case R.id.btn_login:
                name = et_phone.getText().toString().trim();
                pwd = et_pwd.getText().toString().trim();
                String sign = TGmd5.getMD5(name + pwd);
                if (checkForm()) {
                    if (checkNet()) {
                        doLogin(name, pwd, sign);
                    }
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
     * 账号停用弹窗
     */
    public void showStop(String message) {
        dialog_stop = new Dialog(LoginActivity.this, R.style.Mydialog);
        View view1 = View.inflate(LoginActivity.this,
                R.layout.dialog_show_check_stop, null);
        TextView sure = (TextView) view1.findViewById(R.id.sure);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(message);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_stop.dismiss();
            }
        });
        dialog_stop.setContentView(view1);
        dialog_stop.show();
    }


    /**
     * 表单验证
     */
    public boolean checkForm() {
        String name = et_phone.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        if (StringUtils.isEmpty(name)) {
            MyToast.show(LoginActivity.this, "请输入手机号");
            return false;
        } else if (!StringUtils.isPhone(name)) {
            MyToast.show(LoginActivity.this, "请输入合法手机号");
            return false;
        } else if (StringUtils.isEmpty(pwd)) {
            MyToast.show(LoginActivity.this, "请输入密码");
            return false;
        } else if (pwd.length() < 5 && pwd.length() > 17) {
            MyToast.show(LoginActivity.this, "请输入由6-16个数字、字母组成的密码");
            return false;
        }
        return true;
    }


    /**
     * 执行登录
     */
    public void doLogin(String name, String pwd, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("username", name);
        maps.put("password", pwd);
        maps.put("sign", sign);
        RequestTask.getInstance().doLogin(LoginActivity.this, maps, new OnRequestListener());

    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("0")) {
                    //showStop(jsonObject.getString("message"));
                    MyToast.show(LoginActivity.this,jsonObject.getString("message"));
                } else if (status.equals("1")) {
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "true");
                    MyToast.show(LoginActivity.this, "登录成功");
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String str_info = array.get(0).toString();
                    JSONObject info = new JSONObject(str_info);
                    String id = info.getString("ID");
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, id);
                    String tuanbi = info.getString("TuanBi");
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi);
                    String state = info.getString("State");
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, state);
                    String ParentID = info.getString("ParentID");
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, ParentID);
                    String Ratio = info.getString("Ratio");
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.RATIO, Ratio);
                    String userType = info.getString("UserType");
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, userType);
                    String mobile = info.getString("Mobile");
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.MOBILE, mobile);
                    String img_path = array.get(2).toString();
                    String loginname = info.getString("LoginName");
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HEAD_PATH, img_path);
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ACCOUNT, loginname);
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PWD, pwd);
                    String info4 = array.get(3).toString();
                    PreferenceHelper.write(LoginActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, info4);
                    Bundle pBundle = new Bundle();
                    pBundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, pBundle);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(LoginActivity.this, "");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
        }
    }


    long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            AppManager.getInstance().AppExit(getApplicationContext());
        } else {
            MyToast.show(LoginActivity.this, "再按一次退出应用！");
        }

        back_pressed = System.currentTimeMillis();
    }
}
