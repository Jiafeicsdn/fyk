package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.base.BaseApplication;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.utils.DataCleanManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.utils.AppManager;

import java.io.File;

/**
 * Created by Administrator on 2016/10/12.
 * 设置页面
 */
public class SetActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_update_pwd)
    private RelativeLayout rl_update_pwd;
    @ViewInject(R.id.rl_renzheng)
    private RelativeLayout rl_renzheng;
    @ViewInject(R.id.rl_clear_cache)
    private RelativeLayout rl_clear;
    @ViewInject(R.id.tv_text)
    private TextView tv_text;
    @ViewInject(R.id.tv_text_one)
    private TextView tv_text_one;
    @ViewInject(R.id.tv_cache_size)
    private TextView tv_cache_size;
    @ViewInject(R.id.tv_quit)
    private TextView tv_quit;

    private String state = "";

    private Dialog dialog_quit;

    private String cacheSize = "0";
    private double size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        ViewUtils.inject(this);
        tv_title.setText("设置");
        loadCache();
        state = getTextFromBundle("state");
        if (state.equals("1") || state.equals("3")) { // 证件管理
            tv_text.setText("导游证管理");
            tv_text_one.setVisibility(View.INVISIBLE);
        } else if (state.equals("2") || state.equals("4") || state.equals("5") || state.equals("6")) {
            tv_text.setText("实名认证");
            tv_text_one.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.rl_back, R.id.rl_update_pwd, R.id.rl_renzheng, R.id.tv_quit, R.id.rl_clear_cache})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_update_pwd:
                openActivity(UpdatePwdActivity.class);
                break;
            case R.id.rl_renzheng:
                Bundle bundle = new Bundle();
                if (state.equals("1") || state.equals("3")) {
                    bundle.putString("state", state);
                    openActivity(GuideCradMnagerActivity.class, bundle);
                } else if (state.equals("2") || state.equals("4") || state.equals("5") || state.equals("6")) {
                    bundle.putString("index", "0");
                    bundle.putString("state", state);
                    openActivity(AuthenticationActivity.class, bundle);
                }
                break;
            case R.id.tv_quit:
                showQuitDialog();
                break;
            case R.id.rl_clear_cache:
                if (size > 0) {
                    cleanCacheDialog();
                } else {
                    MyToast.show(SetActivity.this, "没有可清理的东西!");
                }
                break;
        }
    }

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
            tv_cache_size.setText(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //退出登录
    public void showQuitDialog() {
        dialog_quit = new Dialog(SetActivity.this, R.style.Mydialog);
        View view1 = View.inflate(SetActivity.this,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
                loginOut();
                openActivity(LoginActivity.class);
                SetActivity.this.finish();
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

    //清空缓存
    public void cleanCacheDialog() {
        dialog_quit = new Dialog(SetActivity.this, R.style.Mydialog);
        View view1 = View.inflate(SetActivity.this,
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
                DataCleanManager.cleanApplicationData(SetActivity.this);
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

    /**
     * 退出登录
     */
    public void loginOut() {
        PreferenceHelper.write(SetActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_DISTRIBUTORID, "");
        PreferenceHelper.write(SetActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, "");
        PreferenceHelper.write(SetActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, "");
        PreferenceHelper.write(SetActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "false");
        Constants.COUNTRYPATH = "";
        Constants.TOTAL_ADDRESS = "";
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
