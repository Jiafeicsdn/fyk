package com.lvgou.distribution.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2016/3/7 0007.
 * 忘记密码
 */
public class ForgetPwdActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.et_code)
    private EditText et_code;
    @ViewInject(R.id.et_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.rl_get_code)
    private RelativeLayout rl_get_code;
    @ViewInject(R.id.tv_second)
    private TextView tv_code;
    @ViewInject(R.id.btn_update)
    private Button btn_update;

    private String code_result = "";

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.EXECUTE_LOADING:
                    tv_code.setText("(" + msg.arg1 + ")");
                    if (msg.arg1 == 0) {
                        closeTimer();
                        rl_get_code.setClickable(true);
                    }
                    break;
                case Constants.EXECUTE_FINISH:
                    tv_code.setVisibility(View.GONE);
                    rl_get_code.setClickable(true);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ViewUtils.inject(this);
        tv_title.setText("找回密码");

    }

    @OnClick({R.id.rl_back, R.id.rl_get_code, R.id.btn_update})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                String phone_ = et_phone.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                String sign = TGmd5.getMD5(phone_ + pwd);
                if (checkForm()) {
                    if (checkNet()) {
                        getPassword(phone_, pwd, sign);
                    }
                }
                break;
            case R.id.rl_get_code:
                String phone = et_phone.getText().toString().trim();
                String sign_ = TGmd5.getMD5(phone);
                if (StringUtils.isEmpty(phone)) {
                    MyToast.show(this, "请输入手机号");
                    return;
                } else if (!StringUtils.isPhone(phone)) {
                    MyToast.show(this, "手机号不合法");
                    return;
                } else {
                    if (checkNet()) {
                        getCode(phone, sign_);
                        /*rl_get_code.setClickable(false);
                        tv_code.setVisibility(View.VISIBLE);
                        startTimer();*/
                    }
                }
                break;
            case R.id.rl_back:
                finish();
                break;
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

    /**
     * 表单验证
     */
    public boolean checkForm() {
        String phone = et_phone.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            MyToast.show(ForgetPwdActivity.this, "请输入手机号");
            return false;
        } else if (!StringUtils.isPhone(phone)) {
            MyToast.show(this, "手机号不合法");
            return false;
        } else if (StringUtils.isEmpty(code)) {
            MyToast.show(ForgetPwdActivity.this, "请输入验证码");
            return false;
        } else if (!code.equals(code_result)) {
            MyToast.show(ForgetPwdActivity.this, "验证码错误");
            return false;
        } else if (StringUtils.isEmpty(pwd)) {
            MyToast.show(ForgetPwdActivity.this, "请输入密码");
            return false;
        } else if (pwd.length() < 6 || pwd.length() > 17) {
            MyToast.show(ForgetPwdActivity.this, "密码由6-16个数字、字母组成");
            return false;
        }
        return true;
    }

    // =======================倒计时模块================================//

    private int mTimerId = 120;

    private TimerTask timerTask;

    private Timer timer;

    /**
     * 开始倒计时
     */
    private void startTimer() {
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = Constants.EXECUTE_LOADING;
                    msg.arg1 = (int) (--mTimerId);
                    handler.sendMessage(msg);
                }
            };
            timer = new Timer();
            // schedule(TimerTask task, long delay, long period)
            // 安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。
            // task - 所要安排的任务。
            // delay - 执行任务前的延迟时间，单位是毫秒。
            // period - 执行各后续任务之间的时间间隔，单位是毫秒。
            timer.schedule(timerTask, 100, 1000);
        }
    }

    /**
     * 结束计时
     */
    private void closeTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        mTimerId = 120;
        handler.sendEmptyMessage(Constants.EXECUTE_FINISH);
    }

    /**
     * 获取验证码
     */
    public void getCode(String mobile, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("mobile", mobile);
        maps.put("sign", sign);
        RequestTask.getInstance().doSendCode(ForgetPwdActivity.this, maps, new OnCodeRequestListener());
    }

    private class OnCodeRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("0")) {
                    closeTimer();
                    MyToast.show(ForgetPwdActivity.this, jsonObject.getString("message"));
                    tv_code.setText("获取验证码");
                    tv_code.setClickable(true);
                } else if (status.equals("1")) {
                    MyToast.show(ForgetPwdActivity.this, "发送成功！");
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        code_result = array.get(0).toString();
                    }
                    rl_get_code.setClickable(false);
                    tv_code.setVisibility(View.VISIBLE);
                    startTimer();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 找回密码
     */
    public void getPassword(String phone, String pwd, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("mobile", phone);
        maps.put("password", pwd);
        maps.put("sign", sign);
        RequestTask.getInstance().doFindPwd(ForgetPwdActivity.this, maps, new OnPasswordRequestListener());
    }

    private class OnPasswordRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                String status = jsonObject1.getString("status");
                if (status.equals("1")) {
                    MyToast.show(ForgetPwdActivity.this, "修改成功！");
                    finish();
                } else if (status.equals("0")) {
                    MyToast.show(ForgetPwdActivity.this, "修改失败！");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
