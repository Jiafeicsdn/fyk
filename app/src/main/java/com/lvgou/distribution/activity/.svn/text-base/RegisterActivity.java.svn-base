package com.lvgou.distribution.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.PermissionManager;
import com.lvgou.distribution.utils.PictureUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.Tools;
import com.umeng.analytics.MobclickAgent;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;


/**
 * Created by Snow on 2016/3/7 0007.
 * 注册
 */
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.et_code_number)
    private EditText et_code_number;
    @ViewInject(R.id.et_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.et_real_name)
    private EditText et_real_name;
    @ViewInject(R.id.et_shop_name)
    private EditText et_shop_name;
    @ViewInject(R.id.et_guide_num_left)
    private EditText et_guide_num_left;
    @ViewInject(R.id.et_guide_num_right)
    private EditText et_guide_num_right;
    @ViewInject(R.id.et_group_num)
    private EditText et_group_num;
    @ViewInject(R.id.et_city)
    private TextView et_city;
    @ViewInject(R.id.img_delete)
    private ImageView img_delete;
    @ViewInject(R.id.img_picture)
    private ImageView img_picture;
    @ViewInject(R.id.tv_load)
    private TextView tv_load;
    @ViewInject(R.id.tv_second)
    private TextView tv_second;
    @ViewInject(R.id.tv_1)
    private TextView tv_content;
    @ViewInject(R.id.btn_register)
    private Button btn_register;
    @ViewInject(R.id.rl_get_code)
    private RelativeLayout rl_get_code;
    @ViewInject(R.id.tv_select_gallery)
    private TextView tv_select_gallery;
    @ViewInject(R.id.tv_select_camera)
    private TextView tv_select_camera;
    @ViewInject(R.id.tv_cancel)
    private TextView tv_cancel;
    @ViewInject(R.id.tv_production)
    private TextView tv_production;
    @ViewInject(R.id.rl_dialog_ios_7_root)
    private RelativeLayout mDialogRootRelativeLayout;
    @ViewInject(R.id.ll_dialog_ios_7_cotent)
    private LinearLayout mDialogCotentLinearLayout;
    @ViewInject(R.id.cb_all)
    private CheckBox cb_all;
    @ViewInject(R.id.cb_dijie)
    private CheckBox cb_dijie;
    @ViewInject(R.id.cb_lingdui)
    private CheckBox cb_lingdui;
    @ViewInject(R.id.cb_oneday)
    private CheckBox cb_oneday;
    @ViewInject(R.id.cb_zhoubian)
    private CheckBox cb_zhoubian;
    @ViewInject(R.id.cb_qita)
    private CheckBox cb_qita;
    @ViewInject(R.id.scroll_view)
    private ScrollView scrollView;
    @ViewInject(R.id.ll_scroll)
    private LinearLayout ll_scroll;
    @ViewInject(R.id.rl_all)
    private RelativeLayout rl_all;
    @ViewInject(R.id.rl_dijie)
    private RelativeLayout rl_dijie;
    @ViewInject(R.id.rl_lingdui)
    private RelativeLayout rl_lingdui;
    @ViewInject(R.id.rl_oneday)
    private RelativeLayout rl_oneday;
    @ViewInject(R.id.rl_zhoubian)
    private RelativeLayout rl_zhoubian;
    @ViewInject(R.id.rl_qita)
    private RelativeLayout rl_qita;
    @ViewInject(R.id.cb_xieyi)
    private CheckBox cb_xieyi;
    @ViewInject(R.id.tv_click_xieyi)
    private TextView tv_xieyi;


    private String code_result = "";
    private String result_imgpath = "";
    private String phone_ = "";
    private String pwd_ = "";

    private String rl_all_state = "0";
    private String rl_dijie_state = "0";
    private String rl_lingdui_state = "0";
    private String rl_oneday_state = "0";
    private String rl_zhoubian_state = "0";
    private String rl_qita_state = "0";
    private String xieyi = "1";
    int all = 0;
    int dijie = 0;
    int lingdui = 0;
    int oneday = 0;
    int zhoubian = 0;
    int qita = 0;

    int attr = 0;


    /**
     * 当前的图片路径
     */
    private String imagePath = null;
    private final static int REQUEST_CODE_GALLERY = 0x33;
    private final static int REQUEST_CODE_CAMERA = 0x44;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.EXECUTE_LOADING:
                    tv_second.setText("(" + msg.arg1 + ")");
                    if (msg.arg1 == 0) {
                        closeTimer();
                        rl_get_code.setClickable(true);
                    }
                    break;
                case Constants.EXECUTE_FINISH:
                    tv_second.setVisibility(View.GONE);
                    rl_get_code.setClickable(true);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewUtils.inject(this);

        tv_title.setText("注册");
        img_picture.setBackgroundResource(R.mipmap.register_moban);
        Constants.COUNTRYPATH = "";
        Constants.TOTAL_ADDRESS = "";
        initTextView();
        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rl_all_state = "0";
                    all = 1;
                    cb_all.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    all = 1;
                    rl_all_state = "1";
                    cb_all.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - all;
                    all = 0;
                }
            }
        });
        cb_dijie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dijie = 2;
                    rl_dijie_state = "0";
                    cb_dijie.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    dijie = 2;
                    rl_dijie_state = "1";
                    cb_dijie.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - dijie;
                    dijie = 0;
                }
            }
        });
        cb_lingdui.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lingdui = 4;
                    rl_lingdui_state = "0";
                    cb_lingdui.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    lingdui = 4;
                    rl_lingdui_state = "1";
                    cb_lingdui.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - lingdui;
                    lingdui = 0;
                }
            }
        });
        cb_oneday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    oneday = 8;
                    rl_oneday_state = "0";
                    cb_oneday.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    oneday = 8;
                    rl_oneday_state = "1";
                    cb_oneday.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - oneday;
                    oneday = 0;
                }
            }
        });
        cb_zhoubian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    zhoubian = 16;
                    rl_zhoubian_state = "0";
                    cb_zhoubian.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    zhoubian = 16;
                    rl_zhoubian_state = "1";
                    cb_zhoubian.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - zhoubian;
                    zhoubian = 0;
                }
            }
        });
        cb_qita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    qita = 32;
                    rl_qita_state = "0";
                    cb_qita.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    qita = 32;
                    rl_qita_state = "1";
                    cb_qita.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - qita;
                    qita = 0;
                }
            }
        });

        cb_xieyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    xieyi = "1";
                    cb_xieyi.setBackgroundResource(R.mipmap.register_user_seleced);
                } else {
                    xieyi = "0";
                    cb_xieyi.setBackgroundResource(R.mipmap.register_user_default);
                }
            }
        });
    }

    public void initTextView() {
        SpannableString spannableOne = new SpannableString("上传导游证名字必须与注册名字一致，");
        spannableOne.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bg_new_guide_black)), 0, spannableOne.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        SpannableString spannableTwo = new SpannableString("照片仅供蜂优客核实使用，请放心上传。");
        spannableTwo.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bg_push_time)), 0, spannableTwo.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_production.append(spannableOne);
        tv_production.append(spannableTwo);
    }


    private PermissionManager permissionManager;

    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick({R.id.rl_back, R.id.rl_get_code, R.id.btn_register, R.id.tv_select_gallery, R.id.tv_select_camera, R.id.tv_cancel, R.id.tv_load, R.id.img_delete, R.id.et_city,
            R.id.rl_all, R.id.rl_dijie, R.id.rl_lingdui, R.id.rl_oneday, R.id.rl_zhoubian, R.id.rl_qita, R.id.tv_click_xieyi})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_get_code:
                String phone = et_phone.getText().toString().trim();
                String sign_ = TGmd5.getMD5(phone);
                if (StringUtils.isEmpty(phone)) {
                    MyToast.show(this, "请输入手机号");
                } else if (!StringUtils.isPhone(phone)) {
                    MyToast.show(this, "手机号不合法");
                } else {
                    if (checkNet()) {
                        getCode(phone, sign_);
                        rl_get_code.setClickable(false);
                        tv_second.setVisibility(View.VISIBLE);
                        startTimer();
                    }
                }
                break;
            case R.id.btn_register:
                phone_ = et_phone.getText().toString().trim();
                pwd_ = et_pwd.getText().toString().trim();
                String phone_one = et_phone.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                String real_name_ = et_real_name.getText().toString().trim();
                String shop_name_ = et_shop_name.getText().toString().trim();
                String left_number = et_guide_num_left.getText().toString().trim();
                String right_number = et_guide_num_right.getText().toString().trim();
                String group_name_ = et_group_num.getText().toString().trim();
                String userno = "D-" + left_number + "-" + right_number;
                String parentid = "0";
                if (group_name_.equals("")) {
                    parentid = "0";
                } else {
                    parentid = group_name_;
                }
                String countrypath_ = Constants.COUNTRYPATH;
                String sign = TGmd5.getMD5(phone_one + pwd + real_name_ + shop_name_ + userno + parentid + countrypath_ + result_imgpath + attr);
                if (checkForm()) {
                    if (checkNet()) {
                        doRegister(phone_one, pwd, real_name_, shop_name_, userno, parentid, countrypath_, result_imgpath, attr + "", sign);
                    }
                }
                break;
            case R.id.tv_select_gallery:
                closeDialog();
                /*FunctionUtils.chooseImageFromGallery(RegisterActivity.this,
                        REQUEST_CODE_GALLERY);*/


                // 权限管理工具类
                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                    @Override
                    public void permissionGranted(int requestCode) {
                        if (requestCode == 99) {
                            PhotoPickerIntent intent = new PhotoPickerIntent(RegisterActivity.this);
                            intent.setPhotoCount(1);
                            intent.setShowCamera(true);
                            intent.setShowGif(true);
                            startActivityForResult(intent, 101);
                        }
                    }
                });
                //申请读权限，和照相机权限
                permissionManager.checkPermission(99, RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                break;
            case R.id.tv_select_camera:
                closeDialog();
                imagePath = FunctionUtils.chooseImageFromCamera(
                        RegisterActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
                break;
            case R.id.tv_cancel:
                closeDialog();
                break;
            case R.id.tv_load:
                openDialog();
                break;
            case R.id.img_delete:
                img_delete.setVisibility(View.GONE);
                tv_load.setVisibility(View.VISIBLE);
                img_picture.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.register_moban));
                imagePath = "";
                break;
            case R.id.et_city:
                Bundle pBundle = new Bundle();
                pBundle.putString("index", "0");
                openActivity(SelectProvinceActivity.class);
                break;
            case R.id.rl_all:
                if (rl_all_state.equals("0")) {
                    cb_all.setChecked(true);
                    rl_all_state = "1";
                    all = 1;
                    cb_all.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_all_state.equals("1")) {
                    rl_all_state = "0";
                    cb_all.setChecked(false);
                    all = 1;
                    cb_all.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - all;
                    all = 0;
                }
                break;
            case R.id.rl_dijie:
                if (rl_dijie_state.equals("0")) {
                    rl_dijie_state = "1";
                    dijie = 2;
                    cb_dijie.setChecked(true);
                    cb_dijie.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_dijie_state.equals("1")) {
                    rl_dijie_state = "0";
                    dijie = 2;
                    cb_dijie.setChecked(false);
                    cb_dijie.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - dijie;
                    dijie = 0;
                }
                break;
            case R.id.rl_lingdui:
                if (rl_lingdui_state.equals("0")) {
                    rl_lingdui_state = "1";
                    lingdui = 4;
                    cb_lingdui.setChecked(true);
                    cb_lingdui.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_all_state.equals("1")) {
                    rl_lingdui_state = "0";
                    lingdui = 4;
                    cb_lingdui.setChecked(false);
                    cb_lingdui.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - lingdui;
                    lingdui = 0;
                }
                break;
            case R.id.rl_oneday:
                if (rl_oneday_state.equals("0")) {
                    rl_oneday_state = "1";
                    oneday = 8;
                    cb_oneday.setChecked(true);
                    cb_oneday.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_oneday_state.equals("1")) {
                    rl_oneday_state = "0";
                    oneday = 8;
                    cb_oneday.setChecked(false);
                    cb_oneday.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - oneday;
                    oneday = 0;
                }
                break;
            case R.id.rl_zhoubian:
                if (rl_zhoubian_state.equals("0")) {
                    rl_zhoubian_state = "1";
                    zhoubian = 16;
                    cb_zhoubian.setChecked(true);
                    cb_zhoubian.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_zhoubian_state.equals("1")) {
                    rl_zhoubian_state = "0";
                    zhoubian = 16;
                    cb_zhoubian.setChecked(false);
                    cb_zhoubian.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - zhoubian;
                    zhoubian = 0;
                }
                break;
            case R.id.rl_qita:
                if (rl_qita_state.equals("0")) {
                    rl_qita_state = "1";
                    qita = 32;
                    cb_qita.setChecked(true);
                    cb_qita.setBackgroundResource(R.mipmap.btn_right_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_qita_state.equals("1")) {
                    rl_qita_state = "0";
                    qita = 32;
                    cb_qita.setChecked(false);
                    cb_qita.setBackgroundResource(R.mipmap.btn_right_not_selected);
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - qita;
                    qita = 0;
                }
                break;
            case R.id.tv_click_xieyi:
                Bundle bundle = new Bundle();
                bundle.putString("url", Url.XIANSHANG_ROOT + "/user/RegisterProtocol.html");
                openActivity(WebViewActivity.class, bundle);
                break;
        }
    }

    private ArrayList<String> data_list = new ArrayList<String>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            Bitmap resultBitmap = null;
            if (resultBitmap != null) {
                resultBitmap.recycle();
            }
            switch (requestCode) {
                case 101:
                  /*  imagePath = FunctionUtils
                            .onActivityResultForChooseImageFromGallery(RegisterActivity.this,
                                    requestCode, resultCode, data);*/
                    if (data != null) {
                        data_list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                        imagePath = data_list.get(0);
                    }
                    if (!StringUtils.isEmpty(imagePath)) {
                        /**
                         *需要修改，路径为空，默认图片记得添加
                         */
                        resultBitmap = PictureUtil.getSmallBitmap(imagePath);
                        img_picture.setImageBitmap(resultBitmap);
                        tv_load.setVisibility(View.GONE);
                        String path_ = Tools.SavePhoto(resultBitmap, "head.jpg");
                        String sign = TGmd5.getMD5("");
                        doUpload(path_, sign);
                    }
                    break;
                case REQUEST_CODE_CAMERA:
                    if (!StringUtils.isEmpty(imagePath)) {
                        resultBitmap = PictureUtil.getSmallBitmap(imagePath);
                        img_picture.setImageBitmap(resultBitmap);
                        tv_load.setVisibility(View.GONE);
                        String path_ = Tools.SavePhoto(resultBitmap, "head.jpg");
                        String sign = TGmd5.getMD5("");
                        doUpload(path_, sign);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (Constants.TOTAL_ADDRESS.equals("")) {
            et_city.setText("请选择城市");
        } else {
            et_city.setText(Constants.TOTAL_ADDRESS);
            et_city.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        }
    }

    /**
     * 表单验证
     */
    public boolean checkForm() {
        String phone = et_phone.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        String code = et_code_number.getText().toString().trim();
        String real_name = et_real_name.getText().toString().trim();
        String shop_name = et_shop_name.getText().toString().trim();
        String userno_left = et_guide_num_left.getText().toString().trim();
        String userno_right = et_guide_num_right.getText().toString().trim();
        String city = Constants.COUNTRYPATH;
        if (StringUtils.isEmpty(phone)) {
            MyToast.show(RegisterActivity.this, "请输入手机号");
            return false;
        } else if (!StringUtils.isPhone(phone)) {
            MyToast.show(RegisterActivity.this, "手机号不合法");
            return false;
        } else if (StringUtils.isEmpty(code)) {
            MyToast.show(RegisterActivity.this, "请输入验证码");
            return false;
        } else if (!code.equals(code_result)) {
            MyToast.show(RegisterActivity.this, "验证码错误");
            return false;
        } else if (StringUtils.isEmpty(pwd)) {
            MyToast.show(RegisterActivity.this, "请输入密码");
            return false;
        } else if (pwd.length() < 6 || pwd.length() > 17) {
            MyToast.show(RegisterActivity.this, "密码由6-16个数字、字母组成");
            return false;
        } else if (StringUtils.isEmpty(real_name)) {
            MyToast.show(RegisterActivity.this, "请输入真实姓名");
            return false;
        } else if (StringUtils.isEmpty(shop_name)) {
            MyToast.show(RegisterActivity.this, "请输入店铺名");
            return false;
        } else if (StringUtils.isEmpty(userno_left)) {
            MyToast.show(RegisterActivity.this, "请输入导游证前4位");
            return false;
        } else if (userno_left.length() != 4) {
            MyToast.show(RegisterActivity.this, "导游编号前四位错误");
            return false;
        } else if (StringUtils.isEmpty(userno_right)) {
            MyToast.show(RegisterActivity.this, "请输入导游证后6位");
            return false;
        } else if (userno_right.length() != 6) {
            MyToast.show(RegisterActivity.this, "导游编号后六位错误");
            return false;
        } else if (StringUtils.isEmpty(city)) {
            MyToast.show(RegisterActivity.this, "请选择城市");
            return false;
        } else if (attr == 0) {
            MyToast.show(RegisterActivity.this, "请选择类别");
            return false;
        } else if (!xieyi.equals("1")) {
            MyToast.show(RegisterActivity.this, "你还没阅读并同意用户协议");
            return false;
        }

       /* else if (StringUtils.isEmpty(imagePath)) {
            MyToast.show(RegisterActivity.this, "请上传导游证");
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


    /**
     * 获取验证码
     */
    public void getCode(String mobile, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("mobile", mobile);
        maps.put("sign", sign);
        RequestTask.getInstance().doSendRegisterCode(RegisterActivity.this, maps, new OnCodeRequestListener());
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
                    MyToast.show(RegisterActivity.this, jsonObject.getString("message"));
                    tv_second.setVisibility(View.GONE);
                    rl_get_code.setClickable(true);
                } else if (status.equals("1")) {
                    MyToast.show(RegisterActivity.this, "发送成功！");
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
     * 注册
     */
    public void doRegister(String mobile, String password, String realname, String companyname, String userno, String parentid, String countrypath, String picurl, String attr, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("mobile", mobile);
        maps.put("password", password);
        maps.put("realname", realname);
        maps.put("companyname", companyname);
        maps.put("userno", userno);
        maps.put("parentid", parentid);
        maps.put("attr", attr);
        maps.put("countrypath", countrypath);
        maps.put("picurl", picurl);
        maps.put("sign", sign);

        RequestTask.getInstance().doRegister(RegisterActivity.this, maps, new OnRegisterRequestListener());

    }

    private class OnRegisterRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    Constants.COUNTRYPATH = "";
                    Constants.TOTAL_ADDRESS = "";
//                    MyToast.show(RegisterActivity.this, "注册成功");

                    String sing_ = TGmd5.getMD5(phone_ + pwd_);
                    doLogin(phone_, pwd_, sing_);

                } else if (status.equals("0")) {
                    MyToast.show(RegisterActivity.this, "注册失败");
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
    public void doUpload(String picurl, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        File file = new File(picurl);
        maps.put("picurl", file);
        maps.put("sign", sign);
        showProgressDialog("正在上传...");
        RequestTask.getInstance().doUploadCard(RegisterActivity.this, maps, new OnUploadRequestListener());
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
                    img_delete.setVisibility(View.VISIBLE);
                    MyToast.show(RegisterActivity.this, "上传成功");
                    scrollView.scrollTo(0, ll_scroll.getMeasuredHeight() - scrollView.getHeight());
                    dismissProgressDialog();
                } else if (status.equals("0")) {
                    MyToast.show(RegisterActivity.this, "上传失败");
                    dismissProgressDialog();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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

    /**
     * 执行登录
     */
    public void doLogin(String name, String pwd, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("username", name);
        maps.put("password", pwd);
        maps.put("sign", sign);
        RequestTask.getInstance().doLogin(RegisterActivity.this, maps, new OnRequestListener());

    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("0")) {
                    MyToast.show(RegisterActivity.this, jsonObject.getString("message"));
                } else if (status.equals("1")) {
                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "true");
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String str_info = array.get(0).toString();
                    JSONObject info = new JSONObject(str_info);
                    String id = info.getString("ID");
                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, id);
                    String tuanbi = info.getString("TuanBi");
                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi);
                    String state = info.getString("State");
                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, state);
                    String ParentID = info.getString("ParentID");
                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, ParentID);
                    String Ratio = info.getString("Ratio");
                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.RATIO, Ratio);
                    String userType = info.getString("UserType");
                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, userType);

//                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGINCOUNT, LoginCount);
                    String opentype = info.getString("OpenType");
                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.OPENTYPE, opentype);
                    String img_path = array.get(2).toString();
                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HEAD_PATH, img_path);
                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PWD, pwd_);
                    PreferenceHelper.write(RegisterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.IS_SHOW_INDEX, "0");
                    Bundle bundle = new Bundle();
                    bundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, bundle);
                    finish();
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
}
