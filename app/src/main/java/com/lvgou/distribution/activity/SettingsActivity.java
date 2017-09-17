package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.base.BaseApplication;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.utils.DataCleanManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;

import java.io.File;

/**
 * Created by Administrator on 2017/4/7.
 * 设置
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private String isover;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        isover = PreferenceHelper.readString(SettingsActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
        state = PreferenceHelper.readString(SettingsActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
        initView();
        loadCache();
        initClick();
    }


    private RelativeLayout rl_back;
    private TextView tv_title;
    private RelativeLayout rl_certification;//导游认证
    private RelativeLayout rl_change_pw;//修改密码
    private RelativeLayout rl_sign;//个性签名
    private RelativeLayout rl_clean;//清理缓存
    private RelativeLayout rl_about;//关于途购
    private TextView tv_exit;//退出
    private TextView tv_catch_size;//缓存大小
    private TextView tv_certification;//认证状态

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("设置");
        rl_certification = (RelativeLayout) findViewById(R.id.rl_certification);
        rl_change_pw = (RelativeLayout) findViewById(R.id.rl_change_pw);
        rl_sign = (RelativeLayout) findViewById(R.id.rl_sign);
        rl_clean = (RelativeLayout) findViewById(R.id.rl_clean);
        rl_about = (RelativeLayout) findViewById(R.id.rl_about);
        tv_exit = (TextView) findViewById(R.id.tv_exit);
        tv_catch_size = (TextView) findViewById(R.id.tv_catch_size);
        tv_certification = (TextView) findViewById(R.id.tv_certification);
        if (isover.equals("true") && state.equals("5")) {
            tv_certification.setText("已认证");
        } else {
            tv_certification.setText("立即认证");
            tv_certification.setTextColor(Color.parseColor("#fc4d30"));
        }

    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_certification.setOnClickListener(this);
        rl_change_pw.setOnClickListener(this);
        rl_sign.setOnClickListener(this);
        rl_clean.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_certification://导游认证
                if (isover.equals("true") && state.equals("5")) {
                    //实名认证审核通过（State=5）时候 且 登录接口第四个参数返回的是false时候，弹出实名认证，调用实名认证接口获取数据，用户完善资料再次提交。
                    //进入实名认证浏览页
                    openActivity(SeeCertificationActivity.class);
                } else {
                    openActivity(CertificationActivity.class);
                }
                break;
            case R.id.rl_change_pw://修改密码
                openActivity(UpdatePwdActivity.class);
                break;
            case R.id.rl_sign://个性签名
                String usetisover = PreferenceHelper.readString(SettingsActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
                String userstate = PreferenceHelper.readString(SettingsActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
                if (isRZDialog(userstate, usetisover)) {
                    openActivity(PersonalSignActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }
                break;
            case R.id.rl_clean://清理缓存
                if (size > 0) {
                    cleanCacheDialog();
                } else {
                    MyToast.show(SettingsActivity.this, "没有可清理的东西!");
                }
                break;
            case R.id.rl_about://关于途购
                openActivity(AboutTugouActivity.class);
                break;
            case R.id.tv_exit://退出
                showQuitDialog();
                break;

        }
    }


    private boolean isRZDialog(String state, String isover) {
        if (state.equals("1")) {
            //没有认证
            return false;
        } else if (state.equals("6")) {
            //审核不通过
            return false;
        } else if (state.equals("5") && isover.equals("false")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("2") || state.equals("3")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("4")) {
            //审核中
            return false;
        } else {
            return true;
        }
    }

    private double size = 0;
    private String cacheSize = "0";

    private void loadCache() {
        try {
            File cacheDir = StorageUtils.getOwnCacheDirectory(this, BaseApplication.context.BASE_IMAGE_CACHE);
            java.text.DecimalFormat df = new java.text.DecimalFormat("######0.00");

            size = DataCleanManager.getCacheSize(cacheDir);
            size += DataCleanManager.getCacheSize(getExternalCacheDir());
            if (size > 0) {
                cacheSize = DataCleanManager.getFormatSize(DataCleanManager.getCacheSize(cacheDir));
//                        df.format(DataCleanManager.getCacheSize(cacheDir)) + "MB";
            } else {
                cacheSize = "0MB";
            }
            tv_catch_size.setText(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //清空缓存
    public void cleanCacheDialog() {
        dialog_quit = new Dialog(SettingsActivity.this, R.style.Mydialog);
        View view1 = View.inflate(SettingsActivity.this,
                R.layout.dialog_quit_show, null);
        TextView textView = (TextView) view1.findViewById(R.id.tv_title);
        textView.setText("删除后不可恢复，确认删除缓存中的视频，语音，图片吗？");
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
                ImageLoader.getInstance().clearMemoryCache();//清除内存
                ImageLoader.getInstance().clearDiskCache();
//                DataCleanManager.cleanApplicationData(SettingsActivity.this);
                size = 0;
                loadCache();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
            }
        });
        dialog_quit.setContentView(view1);
        dialog_quit.show();
    }

    private Dialog dialog_quit;

    //退出登录
    public void showQuitDialog() {
        dialog_quit = new Dialog(SettingsActivity.this, R.style.Mydialog);
        View view1 = View.inflate(SettingsActivity.this,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
                loginOut();
                openActivity(LoginActivity.class);
                SettingsActivity.this.finish();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
            }
        });
        dialog_quit.setContentView(view1);
        dialog_quit.show();
    }

    /**
     * 退出登录
     */
    public void loginOut() {
        PreferenceHelper.write(SettingsActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_DISTRIBUTORID, "");
        PreferenceHelper.write(SettingsActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, "");
        PreferenceHelper.write(SettingsActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, "");
        PreferenceHelper.write(SettingsActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "false");
        Constants.COUNTRYPATH = "";
        Constants.TOTAL_ADDRESS = "";
    }
}
