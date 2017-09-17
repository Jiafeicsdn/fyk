package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lvgou.distribution.R;
import com.lvgou.distribution.animactivity3d.ActivitySwitcher;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.guide.GuideActivity;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.DataCleanManager;
import com.lvgou.distribution.utils.DownloadManager;
import com.lvgou.distribution.utils.DownloadService;
import com.lvgou.distribution.utils.TGmd5;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.SystemUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.update.UpdateController;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/4/21 0021.
 */
public class SplashActivity extends BaseActivity {

    @ViewInject(R.id.rl_splash)
    private RelativeLayout rl_splash;
    private int versionCode;
    private int mandatory;
    private String download_path;

    private Dialog dialog_stop;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ViewUtils.inject(this);


        makdirFile();
        String sign_ = TGmd5.getMD5("1");
        checkUpdate("1", sign_);
        init();
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SCREEN_WIDTH, screenWidth);
        PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SCREEN_HEIGHT, screenHeight);

    }

    public void init() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
        alphaAnimation.setDuration(3000);
        rl_splash.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Boolean isFirstLunch = PreferenceHelper.readBoolean(
                        SplashActivity.this,
                        SPConstants.SHARED_PREFERENCE_NAME,
                        SPConstants.IS_FIRST_LAUNCH, true);
                if (isFirstLunch == true) {
                    try {
                        DownloadManager downloadManager = DownloadService.getDownloadManager(SplashActivity.this);
                        downloadManager.removeAllDownload();

                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/";
                    DataCleanManager.cleanCustomCache(SDPath);
                    openActivity(GuideActivity.class);
                    SplashActivity.this.finish();
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.IS_FIRST_LAUNCH, false);
                } else if (isFirstLunch == false) {
                    int currentVersionCode_ = SystemUtils.getAppVersionCode(SplashActivity.this);
                    if (versionCode <= currentVersionCode_) {
                        String isFirstLogin = PreferenceHelper.readString(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "false");
                        if (isFirstLogin.equals("false")) {
                            openActivity(LoginActivity.class);
                            SplashActivity.this.finish();
                            PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "false");
                        } else if (isFirstLogin.equals("true")) {
                            openActivity(HomeActivity.class);
                            SplashActivity.this.finish();
//                            animatedStartActivity();
                        }
                    }
                }
            }
        });

    }

    private void animatedStartActivity() {
        // we only animateOut this activity here.
        // The new activity will animateIn from its onResume() - be sure to implement it.
        final Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        // disable default animation for new intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ActivitySwitcher.animationOut(findViewById(R.id.rl_splash), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
            @Override
            public void onAnimationFinished() {
                startActivity(intent);
                SplashActivity.this.finish();
            }
        });
    }

    private void checkUpdate(String type, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("type", type);
        maps.put("sign", sign);
        RequestTask.getInstance().doUpdate(this, maps, new OnUpdateRequestListener());

    }

    //版本更新
    private class OnUpdateRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response,
                           DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array_ = new JSONArray(result);
                    String date_ = array_.get(0).toString();
                    JSONObject jsonObject_ = new JSONObject(date_);
                    versionCode = jsonObject_.getInt("Int4");
                    download_path = jsonObject_.getString("String3");
                    mandatory = jsonObject_.getInt("Int1");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int currentVersionCode = SystemUtils.getAppVersionCode(SplashActivity.this);
            if (versionCode > currentVersionCode) {
                if (mandatory == 1) {
                    showOneDialog();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                    builder.setTitle("更新提示");
                    builder.setMessage("检测到有更新,是否立刻更新？");
                    builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Boolean isFirstLunch = PreferenceHelper.readBoolean(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.IS_FIRST_LAUNCH, true);
                            if (isFirstLunch == true) {
                                openActivity(GuideActivity.class);
                                SplashActivity.this.finish();
                                PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.IS_FIRST_LAUNCH, false);
                            } else if (isFirstLunch == false) {
                                String isFirstLogin = PreferenceHelper.readString(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "false");
                                if (isFirstLogin.equals("false")) {
                                    openActivity(LoginActivity.class);
                                    SplashActivity.this.finish();
                                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "false");
                                } else if (isFirstLogin.equals("true")) {
                                    openActivity(HomeActivity.class);
                                    SplashActivity.this.finish();
                                }
                            }
                        }
                    });
                    builder.setPositiveButton("立刻更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UpdateController controller = UpdateController.getInstance();
                            controller.setNotificationView(R.layout.view_update_notify, R.id.update_notification_layout, R.id.update_notification_icon, R.id.update_notification_text, R.id.update_notification_progress);
                            controller.init(SplashActivity.this, R.mipmap.ic_launcher);
                            String header = "http://";
                            if (!download_path.startsWith(header)) {
                                download_path = header + download_path;
                            }
                            controller.beginDownLoad(download_path);
                        }
                    });
                    builder.setCancelable(false);
                    builder.create().show();
                }

            }
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
        }
    }

    public void showOneDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_oneclick, null);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("检测到有更新,请立刻更新！");
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateController controller = UpdateController.getInstance();
                controller.setNotificationView(R.layout.view_update_notify, R.id.update_notification_layout, R.id.update_notification_icon, R.id.update_notification_text, R.id.update_notification_progress);
                controller.init(SplashActivity.this, R.mipmap.ic_launcher);
                String header = "http://";
                if (!download_path.startsWith(header)) {
                    download_path = header + download_path;
                }
                controller.beginDownLoad(download_path);
                mAlertDialog.dismiss();
            }
        });

    }

    public void onResume() {
        super.onResume();

    }

    public void onPause() {
        super.onPause();

    }

    /**
     * 新建语言包，扫描所需要的语言包
     * 以防被删掉，每次都要检查是否新建
     */
    public void makdirFile() {
        String sdcard = Environment.getExternalStorageDirectory() + "/";
        String filepath = sdcard + "tessdata/";
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("assets/" + "eng.traineddata");
            BufferedInputStream bis = new BufferedInputStream(is);
            File dir = new File(filepath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(filepath, "eng.traineddata");
            file.createNewFile();
            FileOutputStream output = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = bis.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
            output.close();
            is.close();
        } catch (Exception e) {
            // 如果下载出现异常，传递值8代表下载失败
            e.printStackTrace();
        }
    }

    /**
     * 执行登录
     */
    public void doLogin(String name, String pwd, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("username", name);
        maps.put("password", pwd);
        maps.put("sign", sign);
        RequestTask.getInstance().doLogin(SplashActivity.this, maps, new OnRequestListener());

    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("0")) {
                    showStop(jsonObject.getString("message"));
                } else if (status.equals("1")) {
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "true");
                    MyToast.show(SplashActivity.this, "登录成功");
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String str_info = array.get(0).toString();
                    JSONObject info = new JSONObject(str_info);
                    String id = info.getString("ID");
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, id);
                    String tuanbi = info.getString("TuanBi");
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi);
                    String state = info.getString("State");
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, state);
                    String ParentID = info.getString("ParentID");
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, ParentID);
                    String Ratio = info.getString("Ratio");
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.RATIO, Ratio);
                    String userType = info.getString("UserType");
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, userType);
                    String opentype = info.getString("OpenType");
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.OPENTYPE, opentype);
                    String img_path = array.get(2).toString();
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HEAD_PATH, img_path);
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ACCOUNT, "13986666666");
                    PreferenceHelper.write(SplashActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PWD, "123456");
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
            showProgressDialog("正在登录....");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            dismissProgressDialog();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            dismissProgressDialog();
        }
    }


    /**
     * 账号停用弹窗
     */
    public void showStop(String message) {
        dialog_stop = new Dialog(SplashActivity.this, R.style.Mydialog);
        View view1 = View.inflate(SplashActivity.this,
                R.layout.dialog_show_check_stop, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(message);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_stop.dismiss();
                AppManager.getInstance().AppExit(getApplicationContext());
            }
        });
        dialog_stop.setContentView(view1);
        dialog_stop.show();
    }

}
