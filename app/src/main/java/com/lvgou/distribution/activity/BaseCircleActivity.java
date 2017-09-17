package com.lvgou.distribution.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.ShareCirclePresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CustomProgressDialog;
import com.lvgou.distribution.view.GroupView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xdroid.common.utils.AnimationUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.SystemUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.utils.AppManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/15.
 */
public class BaseCircleActivity extends FragmentActivity implements GroupView {

    private Context context;

    private CustomProgressDialog progressDialog;
    private String distributorid;

    private ShareCirclePresenter shareCirclePresenter;
    private Dialog dialogProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        shareCirclePresenter = new ShareCirclePresenter(this);

        distributorid = PreferenceHelper.readString(BaseCircleActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        AppManager.getInstance().addActivity(this);
    }


    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        try {
            JSONObject jsonObject1 = new JSONObject(respanse);
            String message = jsonObject1.getString("message");
            MyToast.show(context, message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        MyToast.show(context, "分享成功！");
    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void showProgress() {

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
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
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


    public UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            String sign = TGmd5.getMD5(distributorid);
            shareCirclePresenter.doShareCircle(distributorid, sign);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    public void showLoadingProgressDialog(Context context, String message) {
        dialogProgress = createLoadingDialog(context, message);
        dialogProgress.show();
    }


    public void closeLoadingProgressDialog() {
        if (dialogProgress != null && dialogProgress.isShowing()) {
            dialogProgress.dismiss();
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        shareCirclePresenter.attach(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        shareCirclePresenter.dettach();
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
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }

    public Dialog hint_dialog;
    public void showHintDialog(String msg) {
        if (msg.equals("团币不足")){
            LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            View view1 = inflater.inflate(R.layout.dialog_lack_conis, null);//自定义的布局文件
            TextView tv_sure = (TextView) view1.findViewById(R.id.tv_sure);
            TextView tv_cancel = (TextView) view1.findViewById(R.id.tv_cancel);

            final AlertDialog mAlertDialog = builder.create();
            mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
            mAlertDialog.show();
            mAlertDialog.getWindow().setContentView(view1, pm);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到充值页面
                    Intent intent = new Intent(BaseCircleActivity.this, RechargeMoneyActivity.class);
                    startActivity(intent);
                    mAlertDialog.dismiss();
                }
            });
        }else {
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

    }
    private Dialog dialog_quit;
    public void showStop(String str) {
        dialog_quit = new Dialog(this, R.style.Mydialog);
        dialog_quit.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        View view1 = View.inflate(this, R.layout.dialog_show_check_stop, null);
        TextView sure = (TextView) view1.findViewById(R.id.sure);
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
}
