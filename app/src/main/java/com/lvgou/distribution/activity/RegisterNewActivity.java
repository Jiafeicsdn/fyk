package com.lvgou.distribution.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.luban.Luban;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.PermissionManager;
import com.lvgou.distribution.utils.PictureUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.Tools;
import com.lvgou.distribution.wechat.imageloader.MainActivity;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Snow on 2016/7/20 0020.
 * 注册
 */
public class RegisterNewActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.et_name)
    private EditText et_name;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.et_code)
    private EditText et_code;
    @ViewInject(R.id.tv_code)
    private TextView tv_code;
    @ViewInject(R.id.et_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.et_invite_num)
    private EditText et_invite_num;
    //    @ViewInject(R.id.tv_load)
//    private TextView tv_load;
//    @ViewInject(R.id.img_picture)
//    private ImageView img_picture;
//    @ViewInject(R.id.img_delete)
//    private ImageView img_delete;
    @ViewInject(R.id.cb_xieyi)
    private CheckBox cb_xieyi;
    @ViewInject(R.id.tv_click_xieyi)
    private TextView tv_xieyi;
    @ViewInject(R.id.btn_save)
    private Button btn_register;
    @ViewInject(R.id.tv_select_gallery)
    private TextView tv_select_gallery;
    @ViewInject(R.id.tv_select_camera)
    private TextView tv_select_camera;
    @ViewInject(R.id.tv_cancel)
    private TextView tv_cancel;
    @ViewInject(R.id.rl_dialog_ios_7_root)
    private RelativeLayout mDialogRootRelativeLayout;
    @ViewInject(R.id.ll_dialog_ios_7_cotent)
    private LinearLayout mDialogCotentLinearLayout;

    /**
     * 当前的图片路径
     */
    private String imagePath = null;
    private final static int REQUEST_CODE_GALLERY = 0x33;
    private final static int REQUEST_CODE_CAMERA = 0x44;

    private String result_imgpath = "";
    private String code_result = "";
    private String xieyi = "1";
    private String phone = "";
    private String pwd = "";


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.EXECUTE_LOADING:
                    tv_code.setText("正在获取 (" + msg.arg1 + ")");
                    if (msg.arg1 == 0) {
                        closeTimer();
                        tv_code.setClickable(true);
                    }
                    break;
                case Constants.EXECUTE_FINISH:
                    tv_code.setClickable(true);
                    tv_code.setText("获取验证码");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
        ViewUtils.inject(this);

        cb_xieyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    xieyi = "1";
                    cb_xieyi.setBackgroundResource(R.mipmap.checkbox_check_icon);
                } else {
                    xieyi = "0";
                    cb_xieyi.setBackgroundResource(R.mipmap.register_user_default);
                }
            }
        });
    }

    private PermissionManager permissionManager;

    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick({R.id.rl_back, R.id.tv_code, R.id.tv_load, R.id.btn_save, R.id.tv_select_gallery, R.id.tv_select_camera, R.id.tv_cancel, R.id.img_delete, R.id.tv_click_xieyi})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_code:
                String phone_ = et_phone.getText().toString().trim();
                String sign_ = TGmd5.getMD5(phone_);
                if (StringUtils.isEmpty(phone_)) {
                    MyToast.show(this, "请输入手机号");
                } else if (!StringUtils.isPhone(phone_)) {
                    MyToast.show(this, "手机号不合法");
                } else {
                    if (checkNet()) {
                        getCode(phone_, sign_);
                        tv_code.setClickable(false);
                        startTimer();
                    }
                }
                break;
            case R.id.tv_load:
                openDialog();
                break;
            case R.id.btn_save:
                String name_ = et_name.getText().toString().trim();
                phone = et_phone.getText().toString().trim();
                pwd = et_pwd.getText().toString().trim();
                String invite_num = et_invite_num.getText().toString().trim();
                if (StringUtils.isEmpty(invite_num)) {
                    invite_num = "0";
                } else {
                    invite_num = et_invite_num.getText().toString().trim();
                }
                String sign = TGmd5.getMD5(phone + pwd + name_ + result_imgpath + invite_num);
                if (checkForm()) {
                    if (checkNet()) {
                        doRegister(phone, pwd, name_, result_imgpath, invite_num, sign);
                    }
                }
                break;
            case R.id.tv_select_gallery:
                closeDialog();
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                /*FunctionUtils.chooseImageFromGallery(RegisterNewActivity.this,
                                        REQUEST_CODE_GALLERY);*/

                                // 权限管理工具类
                                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                                    @Override
                                    public void permissionGranted(int requestCode) {
                                        if (requestCode == 99) {
                                            PhotoPickerIntent intent = new PhotoPickerIntent(RegisterNewActivity.this);
                                            intent.setPhotoCount(1);
                                            intent.setShowCamera(true);
                                            intent.setShowGif(true);
                                            startActivityForResult(intent, 101);
                                        }
                                    }
                                });
                                //申请读权限，和照相机权限
                                permissionManager.checkPermission(99, RegisterNewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                            }

                            @Override
                            public void onDenied(String permission) {
                            }
                        }
                );
                break;
            case R.id.tv_select_camera:
                closeDialog();
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.CAMERA}, new PermissionsResultAction() {

                            @Override
                            public void onGranted() {
                                imagePath = FunctionUtils.chooseImageFromCamera(RegisterNewActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
                            }

                            @Override
                            public void onDenied(String permission) {
                            }
                        }
                );
                break;
            case R.id.tv_cancel:
                closeDialog();
                break;
//            case R.id.img_delete:
//                img_delete.setVisibility(View.GONE);
//                tv_load.setVisibility(View.VISIBLE);
//                img_picture.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.register_one_reg));
//                imagePath = "";
//                break;
            case R.id.tv_click_xieyi:
                Bundle bundle = new Bundle();
                bundle.putString("index", "0");
                bundle.putString("url", Url.XIANSHANG_ROOT + "/user/RegisterProtocol.html");
                openActivity(WebViewActivity.class, bundle);
                break;
        }
    }

    public boolean checkForm() {
        String ck_name = et_name.getText().toString();
        String ck_phone = et_phone.getText().toString();
        String ck_code = et_code.getText().toString();
        String ck_pwd = et_pwd.getText().toString();
        if (StringUtils.isEmpty(ck_name)) {
            MyToast.show(RegisterNewActivity.this, "请填写真实姓名");
            return false;
        } else if (StringUtils.isEmpty(ck_phone)) {
            MyToast.show(RegisterNewActivity.this, "请输入手机号");
            return false;
        } else if (!StringUtils.isPhone(ck_phone)) {
            MyToast.show(RegisterNewActivity.this, "手机号不合法");
            return false;
        } else if (StringUtils.isEmpty(ck_code)) {
            MyToast.show(RegisterNewActivity.this, "请输入验证码");
            return false;
        } else if (!code_result.equals(ck_code)) {
            MyToast.show(RegisterNewActivity.this, "验证码不正确");
            return false;
        } else if (StringUtils.isEmpty(ck_pwd)) {
            MyToast.show(RegisterNewActivity.this, "请输入密码");
            return false;
        } else if (ck_pwd.length() < 6 || ck_pwd.length() > 17) {
            MyToast.show(RegisterNewActivity.this, "密码由6-16个数字、字母组成");
            return false;
        } else if (!xieyi.equals("1")) {
            MyToast.show(RegisterNewActivity.this, "你还没阅读并同意用户协议");
            return false;
        }

       /* else if (StringUtils.isEmpty(imagePath)) {
            MyToast.show(RegisterNewActivity.this, "请上传导游证");
            return false;
        }*/
        return true;
    }


    private void openDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, true);
    }

    private void closeDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, false);

    }

    private ArrayList<String> data_list = new ArrayList<String>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case 101:
//                    imagePath = FunctionUtils
//                            .onActivityResultForChooseImageFromGallery(RegisterNewActivity.this,
//                                    requestCode, resultCode, data);
//                    if (!StringUtils.isEmpty(imagePath)) {
//                        /**
//                         *需要修改，路径为空，默认图片记得添加
//                         */
//                        compressWithRx(new File(imagePath));
//                    }
                    if (data != null) {
                        data_list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                        imagePath = data_list.get(0);
                        compressWithRx(new File(imagePath));
                    }
                    break;
                case REQUEST_CODE_CAMERA:
                    if (!StringUtils.isEmpty(imagePath)) {
                        compressWithRx(new File(imagePath));
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 注册
     */
    public void doRegister(String mobile, String password, String realname, String picurl, String invite_num, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("mobile", mobile);
        maps.put("password", password);
        maps.put("realname", realname);
        maps.put("picurl", picurl);
        maps.put("invite", invite_num);
        maps.put("sign", sign);
        showLoadingProgressDialog(this, "");
        RequestTask.getInstance().doRegister(RegisterNewActivity.this, maps, new OnRegisterRequestListener());

    }

    private class OnRegisterRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                String message = jsonObject.getString("message");
                if (status.equals("1")) {
//                    MyToast.show(RegisterNewActivity.this, message);
                    String sing_ = TGmd5.getMD5(phone + pwd);
                    doLogin(phone, pwd, sing_);
                } else if (status.equals("0")) {
                    MyToast.show(RegisterNewActivity.this, message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
        }
    }


    /**
     * 上传证件照片请求
     */
    public void doUpload(File file, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("picurl", file);
        maps.put("sign", sign);
        showLoadingProgressDialog(RegisterNewActivity.this, "");
        RequestTask.getInstance().doUploadCard(RegisterNewActivity.this, maps, new OnUploadRequestListener());
    }

    private class OnUploadRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    result_imgpath = array.get(0).toString();
//                    img_delete.setVisibility(View.VISIBLE);
                    MyToast.show(RegisterNewActivity.this, "上传成功");
                    closeLoadingProgressDialog();
                } else if (status.equals("0")) {
                    MyToast.show(RegisterNewActivity.this, "上传失败");
//                    img_delete.setVisibility(View.VISIBLE);
                    closeLoadingProgressDialog();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取验证码
     */
    public void getCode(String mobile, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("mobile", mobile);
        maps.put("sign", sign);
        RequestTask.getInstance().doSendRegisterCode(RegisterNewActivity.this, maps, new OnCodeRequestListener());
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
                    MyToast.show(RegisterNewActivity.this, jsonObject.getString("message"));
                    tv_code.setClickable(true);
                } else if (status.equals("1")) {
                    MyToast.show(RegisterNewActivity.this, "发送成功！");
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        code_result = array.get(0).toString();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        RequestTask.getInstance().doLogin(RegisterNewActivity.this, maps, new OnRequestListener());

    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            closeLoadingProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("0")) {
                    MyToast.show(RegisterNewActivity.this, jsonObject.getString("message"));
                } else if (status.equals("1")) {
                    PreferenceHelper.write(RegisterNewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "true");
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String str_info = array.get(0).toString();
                    JSONObject info = new JSONObject(str_info);
                    String id = info.getString("ID");
                    PreferenceHelper.write(RegisterNewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, id);
                    String tuanbi = info.getString("TuanBi");
                    PreferenceHelper.write(RegisterNewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi);
                    String state = info.getString("State");
                    PreferenceHelper.write(RegisterNewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, state);
                    String ParentID = info.getString("ParentID");
                    PreferenceHelper.write(RegisterNewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, ParentID);
                    String Ratio = info.getString("Ratio");
                    PreferenceHelper.write(RegisterNewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.RATIO, Ratio);
                    String userType = info.getString("UserType");
                    PreferenceHelper.write(RegisterNewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, userType);
                    String img_path = array.get(2).toString();
                    PreferenceHelper.write(RegisterNewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HEAD_PATH, img_path);
                    PreferenceHelper.write(RegisterNewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ACCOUNT, phone);
                    PreferenceHelper.write(RegisterNewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PWD, pwd);
                    PreferenceHelper.write(RegisterNewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.IS_SHOW_INDEX, "0");
                    showRegistDialog();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);

        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
        }
    }

    public void showRegistDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_regist_click, null);
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
                Bundle bundle = new Bundle();
                bundle.putString("selection_postion", "0");
                openActivity(HomeActivity.class, bundle);
                finish();

            }
        });

        mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
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
     * 转换原图，进行缩放处理
     *
     * @return 转换后的Bitmap
     */
    private Bitmap convertFullBitmap(String fullImagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        String fullFilePath = fullImagePath;
        Bitmap bm = BitmapFactory.decodeFile(fullFilePath, options);
        WindowManager windowManager = getWindowManager();
        Display windowDisplay = windowManager.getDefaultDisplay();
        int windowWidth = windowDisplay.getWidth();
        int scale = 1;
        if (options.outWidth > windowWidth) {
            scale = options.outWidth / windowWidth;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        bm = BitmapFactory.decodeFile(fullFilePath, options);
        return bm;
    }

    /**
     * 压缩单张图片 RxJava 方式
     */
    private void compressWithRx(final File fileoo) {
        Luban.get(this)
                .load(fileoo)
                .putGear(Luban.THIRD_GEAR)
                .asObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                    @Override
                    public Observable<? extends File> call(Throwable throwable) {
                        return Observable.empty();
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
//                        Bitmap bitmap = convertFullBitmap(imagePath);
//                        img_picture.setImageBitmap(bitmap);
//                        tv_load.setVisibility(View.GONE);
                        if (fileoo.length()>300){
                            String sign = TGmd5.getMD5("");
                            doUpload(file, sign);
                        }else {
                            String sign = TGmd5.getMD5("");
                            doUpload(fileoo, sign);
                        }

                    }
                });
    }
}
