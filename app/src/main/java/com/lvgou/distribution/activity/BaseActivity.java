package com.lvgou.distribution.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.ParseResultBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.CreateDailogView;
import com.lvgou.distribution.utils.ACache;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CustomProgressDialog;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xdroid.common.utils.AnimationUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.SystemUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Created by Snow on 2015/12/28 0028.
 */
public class BaseActivity extends FragmentActivity {

    private Context context;

    private CustomProgressDialog progressDialog;

    private Dialog dialogProgress;
    public ACache mcache;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int ss = msg.arg1;
            if (ss == 7) {
                // 广播通知图库更新
                MyToast.show(BaseActivity.this, "保存成功");
            } else if (ss == 8) {
                MyToast.show(BaseActivity.this, "保存失败");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcache = ACache.get(this);
        context = this;
        AppManager.getInstance().addActivity(this);

    }

    public String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    public void setCustomerService(ViewGroup view) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.mipmap.mq_voice_level8);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        view.addView(imageView, layoutParams);
    }


    /**
     * 从Bundle中根据键获取字符串值
     *
     * @param key 键
     * @return 值
     */
    public String getTextFromBundle(String key) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getString(key);
        }
        return "";
    }
   /*——————————————————————————启动Activity封装函数————————————————————————————*/

    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 显示加载
     *
     * @param context
     * @param message
     */
    public void showLoadingProgressDialog(Context context, String message) {
        dialogProgress = createLoadingDialog(context, message);
        dialogProgress.show();
    }

    /**
     * 关闭加载
     */
    public void closeLoadingProgressDialog() {
        if (dialogProgress != null && dialogProgress.isShowing()) {
            dialogProgress.dismiss();
        }
    }

    /**
     * 执行对话框动画
     *
     * @param rootView    背景View
     * @param contentView 内容View
     * @param isShow      true 执行显示动画 false 执行隐藏动画
     */
    protected void performDialogAnimation(final RelativeLayout rootView,
                                          final LinearLayout contentView, final Boolean isShow) {
        float[] floats = null;
        if (isShow) {
            rootView.setVisibility(View.VISIBLE);
            floats = new float[]{0.0f, 1.0f};
        } else {
            floats = new float[]{1.0f, 0.0f};
        }
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rootView, "alpha", floats[0], floats[1]);
        alphaAnimator.setDuration(500);
        alphaAnimator.start();
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isShow) {
                    rootView.setVisibility(View.VISIBLE);
                } else {
                    rootView.setVisibility(View.GONE);
                }

            }
        });
        if (isShow) {
            AnimationUtils.expandingAnimation(contentView);
        } else {
            AnimationUtils.collapsingAnimation(contentView);
        }

    }

    // 根据url下载图片，并保存至sd卡
    protected void downimage(final List<String> url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获取SDCard根目录
                    String sdcard = Environment.getExternalStorageDirectory() + "/";
                    // 这个是要保存的目录
                    String filepath = sdcard + "LGDistribution" + "/Images/";
                    try {
                        for (int i = 0; i < url.size(); i++) {
                            URL fileUrl = new URL(url.get(i));
                            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
                            InputStream is = conn.getInputStream();
                            BufferedInputStream bis = new BufferedInputStream(is);
                            // 根据当前时间给下载的文件重新命名
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                            Date curDate = new Date(System.currentTimeMillis());
                            String fileName = formatter.format(curDate) + "."
                                    + url.get(i).substring(url.get(i).lastIndexOf(".") + 1);
                            LogUtils.e("IMGNAME" + fileName);
                            File dir = new File(filepath);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            File file = new File(filepath, fileName);
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
                            // 广播通知图库更新
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri1 = Uri.fromFile(file);
                            intent.setData(uri1);
                            sendBroadcast(intent);
                            // 每下一张图休眠一秒，防止文件命名相同
                            Thread.sleep(1000);
                        }
                        // 下载完成，传递值7代表下载完成
                        msg.arg1 = 7;
                        handler.sendMessage(msg);

                    } catch (Exception e) {
                        // 如果下载出现异常，传递值8代表下载失败
                        e.printStackTrace();
                        msg.arg1 = 8;
                        handler.sendMessage(msg);
                    }
                }
            }
        }).start();
    }

    public UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
//            MyToast.makeText(BaseActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            MyToast.makeText(BaseActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            MyToast.makeText(BaseActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 获取一个进度条对话框
     *
     * @return
     */
    public Dialog getProgressDialog() {
        return getProgressDialog("加载中...");
    }

    /**
     * 获取一个进度条对话框
     *
     * @param message 显示文字
     * @return
     */
    public Dialog getProgressDialog(String message) {
        CustomProgressDialog progressDialog = CustomProgressDialog
                .createDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public void showOneTextDialog(String str) {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_text, null);
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText(str);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
    }

    /**
     * 显示进度条对话框
     */
    public void showProgressDialog() {
        showProgressDialog("加载中...", true, null);
    }

    /**
     * 显示进度条对话框
     *
     * @param message 显示文字
     */
    public void showProgressDialog(String message) {
        showProgressDialog(message, true, null);
    }

    /**
     * 显示自定义对话框
     *
     * @param mCanceledOnTouchOutside 触碰外围是否可销毁
     * @param mCancelable             点击返回是否可销毁
     */
    public Dialog showCustomDialog(Boolean mCanceledOnTouchOutside,
                                   Boolean mCancelable, CreateDailogView createDailogView) {
        Dialog dialog = new Dialog(context, R.style.style_custom_dialog);
        dialog.setContentView(createDailogView.createDialogView());
        dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        dialog.setCancelable(mCancelable);
        dialog.show();
        return dialog;
    }

    /**
     * 显示进度条对话框
     *
     * @param message
     * @param cancel
     */
    public void showProgressDialog(String message, boolean cancel, DialogInterface.OnCancelListener cancelListener) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(context);
        }
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancel);
        progressDialog.setCanceledOnTouchOutside(false);
        if (cancelListener != null) {
            progressDialog.setOnCancelListener(cancelListener);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    /**
     * 销毁进度条对话框
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 检测网络,为true则有网
     */
    public Boolean checkNet() {
        if (SystemUtils.checkNet(context)) {
            return true;
        }
        MyToast.show(context, "未检测到网络连接!");
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /***
     * 导航相关
     */


    /**
     * 检查地图包名是否存在
     *
     * @param packageName
     * @return
     */
    public static boolean isInstallByRead(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }


    /**
     * @param context
     * @param sourceApplication 必填 第三方调用应用名称。如 包名
     * @param poiname           非必填 POI 名称
     * @param lat               必填 纬度
     * @param lon               必填 经度
     * @param dev               必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style             必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static void goToNaviActivity(Context context, String sourceApplication, String poiname, String lat, String lon, String dev, String style) {
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=")
                .append(sourceApplication);
        if (!TextUtils.isEmpty(poiname)) {
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);
        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }


    /**
     * @param lat         必填 纬度
     * @param lon         必填 经度
     * @param title       必填 标题
     * @param companyName 必填 公司名称
     * @param packageName 必填 应用名称
     */
    public void goToBaiDuActivity(String lat, String lon, String title, String companyName, String packageName) {
        StringBuffer stringBuffer = new StringBuffer("intent://map/marker?");
        stringBuffer.append("location=").append(lat)
                .append(",").append(lon)
                .append("&title=").append(title)
                .append("&content=&src=thirdapp.marker.").append(companyName)
                .append(".").append(packageName)
                .append("#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
        try {
            Intent intent = Intent.getIntent(stringBuffer.toString());
            startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type    出行方式  默认驾车
     * @param fromLat 当前纬度
     * @param fromLon 当前经度
     * @param toLat   目的地纬度
     * @param toLon   目的地经度
     */
    public void goToTencentActivity(String type, String title, String fromLat, String fromLon, String toLat, String toLon) {
        StringBuffer stringBuffer = new StringBuffer("qqmap://map/routeplan?");
        stringBuffer.append("type=").append(type)
                .append("&from=当前位置&fromcoord=").append(fromLat)
                .append(",").append(fromLon)
                .append("&to=").append(title)
                .append("&tocoord=").append(toLat)
                .append(",").append(toLon);
        try {
            Intent intent = Intent.getIntent(stringBuffer.toString());
            startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    /**
     * 检查手机是否安装指定地图
     *
     * @return
     */
    public String getMapPackageName() {
        String is_map = "0";
        boolean is = isInstallByRead("com.baidu.BaiduMap");// 百度地图
        boolean one = isInstallByRead("com.autonavi.minimap");// 高德地图
        boolean two = isInstallByRead("com.tencent.map"); // 腾讯地图
        if (is == false && one == false && two == false) {
            is_map = "0";
            // 都没有
        } else if (is == true && one == false && two == false) {
            is_map = "1";
            // 百度
        } else if (is == false && one == true && two == false) {
            is_map = "2";
            //高德
        } else if (is == false && one == false && two == true) {
            is_map = "3";
            //腾讯
        } else if (is == true && one == true && two == false) {
            is_map = "4";
            // 百度，高德
        } else if (is == true && one == false && two == true) {
            is_map = "5";
            // 百度，腾讯
        } else if (is == false && one == true && two == true) {
            is_map = "6";
            // 高德，腾讯
        } else if (is == true && one == true && two == true) {
            is_map = "7";
            // 百度，高德，腾讯
        }
        return is_map;
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
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loding, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }

    public Dialog hint_dialog;

    public void showHintDialog(String msg) {
        LayoutInflater inflater = LayoutInflater.from(this);
        hint_dialog = new Dialog(this, R.style.Mydialog);// 创建自定义样式dialog
        View view = inflater.inflate(R.layout.dialog_hint_view, null);// 得到加载view
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(msg);// 设置加载信息
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hint_dialog.dismiss();
            }
        });
//        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        hint_dialog.setCancelable(false);// 不可以用“返回键”取消
        hint_dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        hint_dialog.show();
    }

    private Dialog dialog_quit;

    public void showStop(String str) {
        dialog_quit = new Dialog(this, R.style.Mydialog);
        View view1 = View.inflate(this, R.layout.dialog_show_check_stop, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(str);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
                finish();
            }
        });
        dialog_quit.setContentView(view1);
        dialog_quit.show();
    }

    public Dialog guide_dialog;

    public void showGuideDialog(final String state, String str) {
        LayoutInflater inflater = LayoutInflater.from(this);
        guide_dialog = new Dialog(this, R.style.Mydialog);// 创建自定义样式dialog
        View view = inflater.inflate(R.layout.dialog_hint_daoyou, null);// 得到加载view
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(str);// 设置加载信息
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide_dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("state", state);
                openActivity(GuideCradMnagerActivity.class, bundle);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide_dialog.dismiss();
            }
        });
//        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        guide_dialog.setCancelable(false);// 不可以用“返回键”取消
        guide_dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        guide_dialog.show();
    }

    public void showRZDialog(String state, String isover) {
        if (state.equals("4")) {
            MyToast.show(BaseActivity.this, "我们正在加速审核中\n请耐心等待哦^_^");
        } else if(!state.equals("4")) {
            LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
            TextView text1 = (TextView) view.findViewById(R.id.text1);
            View view_line = view.findViewById(R.id.view_line);
            TextView yes = (TextView) view.findViewById(R.id.yes);
            TextView cancle = (TextView) view.findViewById(R.id.cancle);
            if (state.equals("1")) {
                //没有认证
                text1.setText("只有认证通过后\n才能使用更多功能^_^");
                text1.setTextColor(Color.parseColor("#000000"));
                yes.setText("去认证");
            } else if (state.equals("6")) {
                //审核不通过
                text1.setText("您的信息审核不通过\n请重新提交哦^_^");
                text1.setTextColor(Color.parseColor("#FC4D30"));
            } else if (state.equals("5") && isover.equals("false")) {
                //认证了，完善消息
                text1.setText("蜂优客4.0全新开启\n请完善个人资料哦^_^");
                text1.setTextColor(Color.parseColor("#000000"));
                yes.setText("去完善");
            } else if (state.equals("2") || state.equals("3")) {
                //认证了，完善消息
                text1.setText("蜂优客4.0全新开启\n请完善个人资料哦^_^");
                text1.setTextColor(Color.parseColor("#000000"));
                yes.setText("去完善");
            }

            final AlertDialog mAlertDialog = builder.create();
            mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
            mAlertDialog.show();
            mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            mAlertDialog.getWindow().setContentView(view, pm);
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BaseActivity.this, CertificationActivity.class);
                    startActivity(intent);
                    mAlertDialog.dismiss();
                }
            });
        }
    }

    //获取文件大小 单位kb
    public long getFolderSize(java.io.File file) throws Exception {
        long size = 0;
        java.io.File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size / 1024;
    }
}