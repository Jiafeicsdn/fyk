package com.lvgou.distribution.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.utils.TGmd5;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Snow on 2016/4/28 0028.
 *
 */
public class ScanDownloadActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.img_code)
    private ImageView icon_img;
    @ViewInject(R.id.et_name)
    private EditText et_phone;
    @ViewInject(R.id.et_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.et_code)
    private EditText et_code;
    @ViewInject(R.id.tv_second)
    private TextView tv_code;
    @ViewInject(R.id.btn_update)
    private Button btn_register;
    @ViewInject(R.id.tv_download_app)
    private TextView tv_download_app;


    private String result_code = "";

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.EXECUTE_LOADING:
                    tv_code.setText("获取验证码(" + msg.arg1 + ")");
                    if (msg.arg1 == 0) {
                        closeTimer();
                        tv_code.setClickable(true);
                    }
                    break;
                case Constants.EXECUTE_FINISH:
                    tv_code.setText("重新获得验证码");
                    tv_code.setClickable(true);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_download);
        ViewUtils.inject(this);
        tv_title.setText("扫码下载APP");
    }

    @OnClick({R.id.rl_back, R.id.tv_second, R.id.btn_update, R.id.tv_download_app})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_second:
                String phone = et_phone.getText().toString().trim();
                String sign_ = TGmd5.getMD5(phone);
                if (!StringUtils.isEmpty(phone)) {
//                    getCode(phone, sign_);
//                    tv_code.setClickable(false);
//                    startTimer();
                } else if (!StringUtils.isPhone(phone)) {
                    MyToast.show(this, "手机号不合法");
                    return;
                } else {
                    MyToast.show(this, "请输入手机号");
                }
                break;
            case R.id.btn_update:
                break;
            case R.id.tv_download_app:
                break;
        }
    }

    public boolean checkForm() {
        String phone = et_phone.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            MyToast.show(ScanDownloadActivity.this, "请输入账号");
            return false;
        } else if (!isValidCode(phone)) {
            MyToast.show(ScanDownloadActivity.this, "账号含有非法字符");
            return false;
        } else if (StringUtils.isEmpty(pwd)) {
            MyToast.show(ScanDownloadActivity.this, "请输入密码");
            return false;
        } else if (pwd.length() < 5 && pwd.length() > 17) {
            MyToast.show(ScanDownloadActivity.this, "请输入由6-16个数字、字母组成的密码");
            return false;
        }else if (StringUtils.isEmpty(code)) {
            MyToast.show(ScanDownloadActivity.this, "请输入验证码");
            return false;
        } else if (!code.equals(result_code)) {
            MyToast.show(ScanDownloadActivity.this, "验证码错误");
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
     * 非法字符验证
     *
     * @param str
     * @return
     */
    public static boolean isValidCode(String str) {
        String rules = "^[a-zA-Z0-9]+";
        if (str.matches(rules)) {
            return true;
        } else {
            return false;
        }
    }
}
