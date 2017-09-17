package com.lvgou.distribution.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.luban.Luban;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.CalendarUtils;
import com.lvgou.distribution.utils.PermissionManager;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.wechat.imageloader.MainActivity;
import com.lvgou.distribution.wheelview.OnWheelChangedListener;
import com.lvgou.distribution.wheelview.OnWheelScrollListener;
import com.lvgou.distribution.wheelview.WheelView;
import com.lvgou.distribution.wheelview.adapter.AbstractWheelTextAdapter;
import com.lvgou.distribution.wheelview.adapter.ArrayWheelAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Snow on 2016/7/21 0021.
 * 实名认证
 */
public class AuthenticationActivity extends BaseActivity implements OnWheelChangedListener {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.et_name)
    private EditText et_name;
    @ViewInject(R.id.radioGroup)
    private RadioGroup mGroup;
    @ViewInject(R.id.radio_male)
    private RadioButton radio_male;
    @ViewInject(R.id.radio_female)
    private RadioButton radio_female;
    @ViewInject(R.id.rl_select_city)
    private RelativeLayout rl_select_city;
    @ViewInject(R.id.tv_city)
    private TextView tv_city;
    @ViewInject(R.id.rl_select_birth)
    private RelativeLayout rl_select_birth;
    @ViewInject(R.id.tv_birthday)
    private TextView tv_birthday;
    @ViewInject(R.id.rl_select_year)
    private RelativeLayout rl_select_year;
    @ViewInject(R.id.tv_start_year)
    private TextView tv_start_year;
    @ViewInject(R.id.rl_select_languages)
    private RelativeLayout rl_select_languages;
    @ViewInject(R.id.tv_languages)
    private TextView tv_languages;
    @ViewInject(R.id.et_four_qian)
    private EditText et_four_qian;
    @ViewInject(R.id.et_four_hou)
    private EditText et_four_hou;
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
    @ViewInject(R.id.et_content)
    private EditText et_content;
    @ViewInject(R.id.tv_load)
    private TextView tv_load;
    @ViewInject(R.id.img_picture)
    private ImageView img_picture;
    @ViewInject(R.id.img_delete)
    private ImageView img_delete;
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
    private String str_select_city = "";
    private String str_select_birthday = "";
    private String str_slect_year = "";
    private String sex = "0";// 1  男，2 女  默认 男
    DisplayImageOptions options;
    private String rl_all_state = "0";
    private String rl_dijie_state = "0";
    private String rl_lingdui_state = "0";
    private String rl_oneday_state = "0";
    private String rl_zhoubian_state = "0";
    private String rl_qita_state = "0";
    int all = 0;
    int dijie = 0;
    int lingdui = 0;
    int oneday = 0;
    int zhoubian = 0;
    int qita = 0;

    int attr = 0;

    private String str_1 = "0";
    private String str_2 = "0";
    private String str_3 = "0";
    private String str_4 = "0";
    private String str_5 = "0";
    private String str_6 = "0";
    private String str_7 = "0";
    private String str_8 = "0";
    private String str_9 = "0";
    private String str_10 = "0";
    private String str_11 = "0";

    private Dialog dialog_language;
    private Dialog dialog_year;
    private Dialog startDialog;
    private WheelView wheel_year, wheel_month;
    private WheelView wheel_year_start, wheel_month_start, wheel_day_start;
    private List<String> list_year;
    private List<String> list_month;
    private List<String> list_languages = new ArrayList<String>();
    private String select_year;
    private String select_month;


    private ImageView img_01, img_02, img_03, img_04, img_05, img_06, img_07, img_08, img_09, img_10, img_11;
    private List<String> startYearList, startMonthList, startDayList;
    private String startYear = "", startMonth = "", startDay = "";
    private String[] years, months, days;
    private YearAdapter startYearAdapter;
    private MonthAdapter startMonthAdapter;
    private DayAdapter startDayAdapter;
    private TextView startDone, startCancle;
    private final int MIN_YEAR = 1959;
    private int max_Year = 2001;
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    private SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    private int startYearIndex, startMonthIndex, startDayIndex;
    private Resources res;
    private Date date;
    private boolean isChange = false;
    private boolean isChangeyear = false;
    private boolean isChangemonth = false;

    private String distributorid = "";
    private String index = "";
    private String state = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_authentication);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(AuthenticationActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        Constants.COUNTRYPATH = "";
        Constants.TOTAL_ADDRESS = "";
        index = getTextFromBundle("index");
        state = getTextFromBundle("state");
        initCheckBox();
        initData();
        if (!distributorid.equals("") || !distributorid.equals("null")) {
            String sign = TGmd5.getMD5(distributorid);
            if (checkNet()) {
                getInfo(distributorid, sign);
            }
        } else {
            openActivity(LoginActivity.class);
            finish();
        }
    }
    private PermissionManager permissionManager;
    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick({R.id.rl_back, R.id.rl_select_city, R.id.rl_select_birth, R.id.rl_select_year, R.id.rl_select_languages, R.id.tv_load, R.id.img_delete, R.id.btn_save,
            R.id.tv_select_gallery, R.id.tv_select_camera, R.id.tv_cancel, R.id.rl_all, R.id.rl_dijie, R.id.rl_lingdui, R.id.rl_oneday, R.id.rl_zhoubian, R.id.rl_qita})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    bundle.putString("selection_postion", "2");
                    openActivity(HomeActivity.class, bundle);
                } else {
                    finish();
                }
                break;
            case R.id.rl_select_city:
                openActivity(SelectProvinceActivity.class);
                break;
            case R.id.rl_select_birth:
                openStartDialog();
                break;
            case R.id.rl_select_year:
                showYear();
                break;
            case R.id.rl_select_languages:
                shopLangage();
                break;
            case R.id.tv_load:
                openDialog();
                break;
            case R.id.btn_save:
                String name = et_name.getText().toString().trim();
                String city = Constants.COUNTRYPATH;
                String birth = tv_birthday.getText().toString().trim();
                String start_year = tv_start_year.getText().toString().trim();
                String str_four = et_four_qian.getText().toString().trim();
                String str_six = et_four_hou.getText().toString().trim();
                String str_content = et_content.getText().toString().trim();
                if (StringUtils.isEmpty(name)) {
                    MyToast.show(AuthenticationActivity.this, "请输入姓名");
                    return;
                }
                if (sex.equals("0")) {
                    MyToast.show(AuthenticationActivity.this, "请选择性别");
                    return;
                }
                if (StringUtils.isEmpty(city)) {
                    MyToast.show(AuthenticationActivity.this, "请选择你所在的城市");
                    return;
                }
                if (birth.equals("请选择出生年月")) {
                    MyToast.show(AuthenticationActivity.this, "请选择出生年月");
                    return;
                }
                if (start_year.equals("请选择从事年份")) {
                    MyToast.show(AuthenticationActivity.this, "请选择从事年份");
                    return;
                }
                if (list_languages.size() == 0) {
                    MyToast.show(AuthenticationActivity.this, "请选择你擅长的语种");
                    return;
                }
                if (StringUtils.isEmpty(str_four)) {
                    MyToast.show(AuthenticationActivity.this, "请输入导游证前四位");
                    return;
                }
                if (str_four.length() != 4) {
                    MyToast.show(AuthenticationActivity.this, "导游编号前四位错误");
                    return;
                }
                if (StringUtils.isEmpty(str_six)) {
                    MyToast.show(AuthenticationActivity.this, "请输入导游证后六位");
                    return;
                }
                if (str_six.length() != 6) {
                    MyToast.show(AuthenticationActivity.this, "导游编号后六位错误");
                    return;
                }
                if (attr == 0) {
                    MyToast.show(AuthenticationActivity.this, "请选择导游类型");
                    return;
                }
                if (StringUtils.isEmpty(str_content)) {
                    MyToast.show(AuthenticationActivity.this, "请输入擅长路线");
                    return;
                }

                if (StringUtils.isEmpty(result_imgpath)) {
                    MyToast.show(AuthenticationActivity.this, "请上传身份证照片");
                    return;
                }


                String time = "";
                if ((start_year.split("年")[1]).split("月")[0].length() == 1) {
                    time = start_year.split("年")[0] + "-0" + (start_year.split("年")[1]).split("月")[0];
                } else {
                    time = start_year.split("年")[0] + "-" + (start_year.split("年")[1]).split("月")[0];
                }

                String userno = "D-" + str_four + "-" + str_six;
                String languages = list_languages.toString().trim().replaceAll(" ", "");
                String sign = TGmd5.getMD5(distributorid + name + sex + city + birth + time + languages.toString().trim().substring(1, languages.toString().length() - 1) + userno + attr + str_content + result_imgpath);
                if (checkNet()) {
                    saveInfo(distributorid, name, sex, city, birth, time, languages.toString().trim().substring(1, languages.toString().length() - 1), userno, attr + "", str_content, result_imgpath, sign);
                }
                break;
            case R.id.tv_select_gallery:
                closeDialog();
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionsResultAction() {

                            @Override
                            public void onGranted() {
//                                FunctionUtils.chooseImageFromGallery(AuthenticationActivity.this, REQUEST_CODE_GALLERY);
                                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                                    @Override
                                    public void permissionGranted(int requestCode) {
                                        if (requestCode == 99) {
                                            PhotoPickerIntent intent = new PhotoPickerIntent(AuthenticationActivity.this);
                                            intent.setPhotoCount(1);
                                            intent.setShowCamera(true);
                                            intent.setShowGif(true);
                                            startActivityForResult(intent, 101);
                                        }
                                    }
                                });
                                //申请读权限，和照相机权限
                                permissionManager.checkPermission(99, AuthenticationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
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
                                imagePath = FunctionUtils.chooseImageFromCamera(AuthenticationActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
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
            case R.id.img_delete:
                img_delete.setVisibility(View.GONE);
                tv_load.setVisibility(View.VISIBLE);
                img_picture.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.register_one_auth));
                result_imgpath = "";
                break;
            case R.id.rl_all:
                if (rl_all_state.equals("0")) {
                    cb_all.setChecked(true);
                    rl_all_state = "1";
                    all = 1;
                    cb_all.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_all_state.equals("1")) {
                    rl_all_state = "0";
                    cb_all.setChecked(false);
                    all = 1;
                    cb_all.setBackgroundResource((R.mipmap.register_user_default));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - all;
                    all = 0;
                }
                break;
            case R.id.rl_dijie:
                if (rl_dijie_state.equals("0")) {
                    rl_dijie_state = "1";
                    dijie = 2;
                    cb_dijie.setChecked(true);
                    cb_dijie.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_dijie_state.equals("1")) {
                    rl_dijie_state = "0";
                    dijie = 2;
                    cb_dijie.setChecked(false);
                    cb_dijie.setBackgroundResource((R.mipmap.register_user_default));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - dijie;
                    dijie = 0;
                }
                break;
            case R.id.rl_lingdui:
                if (rl_lingdui_state.equals("0")) {
                    rl_lingdui_state = "1";
                    lingdui = 4;
                    cb_lingdui.setChecked(true);
                    cb_lingdui.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_all_state.equals("1")) {
                    rl_lingdui_state = "0";
                    lingdui = 4;
                    cb_lingdui.setChecked(false);
                    cb_lingdui.setBackgroundResource((R.mipmap.register_user_default));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - lingdui;
                    lingdui = 0;
                }
                break;
            case R.id.rl_oneday:
                if (rl_oneday_state.equals("0")) {
                    rl_oneday_state = "1";
                    oneday = 8;
                    cb_oneday.setChecked(true);
                    cb_oneday.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_oneday_state.equals("1")) {
                    rl_oneday_state = "0";
                    oneday = 8;
                    cb_oneday.setChecked(false);
                    cb_oneday.setBackgroundResource((R.mipmap.register_user_default));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - oneday;
                    oneday = 0;
                }
                break;
            case R.id.rl_zhoubian:
                if (rl_zhoubian_state.equals("0")) {
                    rl_zhoubian_state = "1";
                    zhoubian = 16;
                    cb_zhoubian.setChecked(true);
                    cb_zhoubian.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_zhoubian_state.equals("1")) {
                    rl_zhoubian_state = "0";
                    zhoubian = 16;
                    cb_zhoubian.setChecked(false);
                    cb_zhoubian.setBackgroundResource((R.mipmap.register_user_default));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - zhoubian;
                    zhoubian = 0;
                }
                break;
            case R.id.rl_qita:
                if (rl_qita_state.equals("0")) {
                    rl_qita_state = "1";
                    qita = 32;
                    cb_qita.setChecked(true);
                    cb_qita.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else if (rl_qita_state.equals("1")) {
                    rl_qita_state = "0";
                    qita = 32;
                    cb_qita.setChecked(false);
                    cb_qita.setBackgroundResource((R.mipmap.register_user_default));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - qita;
                    qita = 0;
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (Constants.TOTAL_ADDRESS.equals("")) {
            tv_city.setText("请选择你所在的城市");
            tv_city.setTextColor(getResources().getColor(R.color.bg_push_time));
        } else {
            tv_city.setText(Constants.TOTAL_ADDRESS);
            tv_city.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        }
    }

    private void openDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, true);
    }

    private void closeDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, false);
    }


    public void initCheckBox() {
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_male:
                        sex = "1";
                        break;
                    case R.id.radio_female:
                        sex = "2";
                        break;
                }
            }
        });

        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rl_all_state = "0";
                    all = 1;
                    cb_all.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    all = 1;
                    rl_all_state = "1";
                    cb_all.setBackgroundResource((R.mipmap.register_user_default));
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
                    cb_dijie.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    dijie = 2;
                    rl_dijie_state = "1";
                    cb_dijie.setBackgroundResource((R.mipmap.register_user_default));
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
                    cb_lingdui.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    lingdui = 4;
                    rl_lingdui_state = "1";
                    cb_lingdui.setBackgroundResource((R.mipmap.register_user_default));
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
                    cb_oneday.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    oneday = 8;
                    rl_oneday_state = "1";
                    cb_oneday.setBackgroundResource((R.mipmap.register_user_default));
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
                    cb_zhoubian.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    zhoubian = 16;
                    rl_zhoubian_state = "1";
                    cb_zhoubian.setBackgroundResource((R.mipmap.register_user_default));
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
                    cb_qita.setBackgroundResource((R.mipmap.register_user_seleced));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita;
                } else {
                    qita = 32;
                    rl_qita_state = "1";
                    cb_qita.setBackgroundResource((R.mipmap.register_user_default));
                    attr = all + dijie + lingdui + oneday + zhoubian + qita - qita;
                    qita = 0;
                }
            }
        });
    }

    private ArrayList<String> data_list = new ArrayList<String>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == -1) {
            switch (requestCode) {
                case 101:
                   /* imagePath = FunctionUtils
                            .onActivityResultForChooseImageFromGallery(AuthenticationActivity.this,
                                    requestCode, resultCode, data);
                    if (!StringUtils.isEmpty(imagePath)) {
                        *//**
                         *需要修改，路径为空，默认图片记得添加
                         *//*
                        compressWithRx(new File(imagePath));
                    }*/
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
     * 上传照片请求
     */
    public void doUpload(File file, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("picurl", file);
        maps.put("sign", sign);
        showLoadingProgressDialog(AuthenticationActivity.this, "");
        RequestTask.getInstance().uploadRenZheng(AuthenticationActivity.this, maps, new OnUploadRequestListener());
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
                    MyToast.show(AuthenticationActivity.this, "上传成功");
                    closeLoadingProgressDialog();
                } else if (status.equals("0")) {
                    MyToast.show(AuthenticationActivity.this, "上传失败");
                    img_delete.setVisibility(View.VISIBLE);
                    closeLoadingProgressDialog();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void saveInfo(String distributorid, String realName, String sex, String countryPath, String birthDay, String workDay, String language, String userNo, String attr, String line, String idCard, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("realName", realName);
        maps.put("sex", sex);
        maps.put("countryPath", countryPath);
        maps.put("birthDay", birthDay);
        maps.put("workDay", workDay);
        maps.put("language", language);
        maps.put("userNo", userNo);
        maps.put("attr", attr);
        maps.put("line", line);
        maps.put("idCard", idCard);
        maps.put("sign", sign);
        RequestTask.getInstance().saveRenZheng(AuthenticationActivity.this, maps, new OnRequestListener());

    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(AuthenticationActivity.this, "提交成功");
                    openActivity(CommitSuccessActivity.class);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(AuthenticationActivity.this, "");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            closeLoadingProgressDialog();
        }
    }


    public void getInfo(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().loadRenZheng(AuthenticationActivity.this, maps, new OnLoadRequestListener());
    }

    private class OnLoadRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject_one = new JSONObject(jsonArray.get(0).toString());
                    String name = jsonObject_one.getString("RealName");
                    et_name.setText(name);
                    et_name.setTextColor(getResources().getColor(R.color.bg_new_guide_black));

                    Constants.COUNTRYPATH = jsonObject_one.getString("CountryPath");
                    if (!StringUtils.isEmpty(Constants.COUNTRYPATH)) {
                        tv_city.setText(jsonArray.get(2).toString());
                        tv_city.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                        Constants.TOTAL_ADDRESS = jsonArray.get(2).toString();
                    } else {
                        tv_city.setText("请选择你所在的城市");
                        tv_city.setTextColor(getResources().getColor(R.color.bg_push_time));
                    }

                    String attr_ = jsonObject_one.getString("Attr");
                    attr = Integer.parseInt(attr_);
                    int in_attr = Integer.parseInt(attr_);
                    if ((in_attr & 1) == 1) {// 全陪
                        rl_all_state = "0";
                        all = 1;
                        cb_all.setBackgroundResource((R.mipmap.register_user_seleced));
                    }
                    if ((in_attr & 2) == 2) {//地接
                        dijie = 2;
                        rl_dijie_state = "0";
                        cb_dijie.setBackgroundResource((R.mipmap.register_user_seleced));
                    }
                    if ((in_attr & 4) == 4) {//领队
                        lingdui = 4;
                        rl_lingdui_state = "0";
                        cb_lingdui.setBackgroundResource((R.mipmap.register_user_seleced));
                    }
                    if ((in_attr & 8) == 8) {//一日游
                        oneday = 8;
                        rl_oneday_state = "0";
                        cb_oneday.setBackgroundResource((R.mipmap.register_user_seleced));
                    }
                    if ((in_attr & 16) == 16) {//周边游
                        oneday = 8;
                        rl_oneday_state = "0";
                        cb_oneday.setBackgroundResource((R.mipmap.register_user_seleced));
                    }
                    if ((in_attr & 32) == 32) {//其他
                        qita = 32;
                        rl_qita_state = "0";
                        cb_qita.setBackgroundResource((R.mipmap.register_user_seleced));
                    }

                    JSONObject jsonObject_two = new JSONObject(jsonArray.get(1).toString());

                    sex = jsonObject_two.getString("Sex");
                    if (sex.equals("1")) {
                        radio_male.setChecked(true);
                        radio_male.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                        radio_female.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                    } else if (sex.equals("2")) {
                        radio_female.setChecked(true);
                        radio_male.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                        radio_female.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                    } else {
                        radio_male.setChecked(false);
                        radio_female.setChecked(false);
                        radio_male.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                        radio_female.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                    }

                    if (!jsonObject_two.getString("Birthday").split("T")[0].equals("1900-01-01")) {
                        tv_birthday.setText(jsonObject_two.getString("Birthday").split("T")[0]);
                        tv_birthday.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                    } else {
                        tv_birthday.setText("请选择出生年月");
                        tv_birthday.setTextColor(getResources().getColor(R.color.bg_push_time));
                    }

                    if (!jsonObject_two.getString("WorkDay").split("T")[0].equals("1900-01-01")) {
                        tv_start_year.setText(jsonObject_two.getString("WorkDay").split("T")[0].substring(0, 4) + "年" + jsonObject_two.getString("WorkDay").split("T")[0].substring(5, 7) + "月");
                        tv_start_year.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                    } else {
                        tv_start_year.setText("请选择从事年份");
                        tv_start_year.setTextColor(getResources().getColor(R.color.bg_push_time));
                    }
                    String language = jsonObject_two.getString("Language").replace(" ", "");
                    if (!StringUtils.isEmpty(language)) {
                        tv_languages.setText(language);
                        tv_languages.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                        if (language.contains(",")) {
                            String[] str_L = language.split(",");
                            for (int i = 0; i < str_L.length; i++) {
                                list_languages.add(str_L[i]);
                            }
                        } else {
                            list_languages.add(language);
                        }
                    } else {
                        tv_languages.setText("请选择您擅长的语种");
                        tv_languages.setTextColor(getResources().getColor(R.color.bg_push_time));
                    }
                    String line = jsonObject_two.getString("Line");
                    if (!StringUtils.isEmpty(line)) {
                        et_content.setText(line);
                    } else {
                        et_content.setHint("请输入擅长路线");
                    }
                    String user_num = jsonObject_two.getString("UserNO");
                    if (!StringUtils.isEmpty(user_num)) {
                        String str[] = user_num.split("-");
                        if (str.length == 3) {
                            et_four_hou.setText(user_num.split("-")[2]);
                            et_four_qian.setText(user_num.split("-")[1]);
                            et_four_hou.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                            et_four_qian.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                        }
                    } else {
                        et_four_qian.setHint("请输入前四位");
                        et_four_hou.setHint("请输入后六位");
                    }

                    String idCard = jsonObject_two.getString("IDCard");
                    result_imgpath = jsonObject_two.getString("IDCard");
                    if (!StringUtils.isEmpty(idCard)) {
                        options = new DisplayImageOptions.Builder()
                                .showImageForEmptyUri(R.mipmap.head_default)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.head_default)            // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                                .build();
                        ImageLoader.getInstance().displayImage(Url.ROOT + idCard, img_picture, options);
                        img_delete.setVisibility(View.VISIBLE);
                        tv_load.setVisibility(View.GONE);
                    }
                    judgeSate(state);
                } else if (status.equals("0")) {
                    MyToast.show(AuthenticationActivity.this, jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("请登录")) {
                        openActivity(LoginActivity.class);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化绑定数据
     */
    public void initData() {
        list_year = new ArrayList<String>();
        list_month = new ArrayList<String>();

        list_month.add("1月");
        list_month.add("2月");
        list_month.add("3月");
        list_month.add("4月");
        list_month.add("5月");
        list_month.add("6月");
        list_month.add("7月");
        list_month.add("8月");
        list_month.add("9月");
        list_month.add("10月");
        list_month.add("11月");
        list_month.add("12月");

        int yaar = Integer.parseInt(CalendarUtils.getYear() + "");
        int start = yaar - 25;
        for (int i = start; i <= yaar; i++) {
            list_year.add(i + "年");
        }
    }

    public void judgeSate(String state) {
        if (state!=null&&!"".equals(state)&&state.equals("5")) {
            et_name.setEnabled(false);
            radio_male.setEnabled(false);
            radio_female.setEnabled(false);
            rl_select_city.setEnabled(false);
            rl_select_birth.setEnabled(false);
            rl_select_year.setEnabled(false);
            rl_select_languages.setEnabled(false);
            et_four_qian.setEnabled(false);
            et_four_hou.setEnabled(false);
            rl_all.setEnabled(false);
            cb_all.setEnabled(false);
            rl_dijie.setEnabled(false);
            cb_dijie.setEnabled(false);
            rl_lingdui.setEnabled(false);
            cb_lingdui.setEnabled(false);
            rl_oneday.setEnabled(false);
            cb_oneday.setEnabled(false);
            rl_zhoubian.setEnabled(false);
            cb_zhoubian.setEnabled(false);
            rl_qita.setEnabled(false);
            cb_qita.setEnabled(false);
            et_content.setEnabled(false);
            img_delete.setVisibility(View.GONE);
            btn_register.setEnabled(false);
            btn_register.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 选择语言对话框
     */
    public void shopLangage() {
        dialog_language = new Dialog(AuthenticationActivity.this,
                R.style.style_custom_dialog);
        View view_sex = View.inflate(AuthenticationActivity.this,
                R.layout.dialog_select_language, null);
        TextView tv_done_sex = (TextView) view_sex
                .findViewById(R.id.tv_done_sex);
        TextView tv_cancle_sex = (TextView) view_sex
                .findViewById(R.id.tv_cancle_sex);
        RelativeLayout rl_01 = (RelativeLayout) view_sex.findViewById(R.id.rl_01);
        RelativeLayout rl_02 = (RelativeLayout) view_sex.findViewById(R.id.rl_02);
        RelativeLayout rl_03 = (RelativeLayout) view_sex.findViewById(R.id.rl_03);
        RelativeLayout rl_04 = (RelativeLayout) view_sex.findViewById(R.id.rl_04);
        RelativeLayout rl_05 = (RelativeLayout) view_sex.findViewById(R.id.rl_05);
        RelativeLayout rl_06 = (RelativeLayout) view_sex.findViewById(R.id.rl_06);
        RelativeLayout rl_07 = (RelativeLayout) view_sex.findViewById(R.id.rl_07);
        RelativeLayout rl_08 = (RelativeLayout) view_sex.findViewById(R.id.rl_08);
        RelativeLayout rl_09 = (RelativeLayout) view_sex.findViewById(R.id.rl_09);
        RelativeLayout rl_10 = (RelativeLayout) view_sex.findViewById(R.id.rl_10);
        RelativeLayout rl_11 = (RelativeLayout) view_sex.findViewById(R.id.rl_11);
        img_01 = (ImageView) view_sex.findViewById(R.id.img_1);
        img_02 = (ImageView) view_sex.findViewById(R.id.img_2);
        img_03 = (ImageView) view_sex.findViewById(R.id.img_3);
        img_04 = (ImageView) view_sex.findViewById(R.id.img_4);
        img_05 = (ImageView) view_sex.findViewById(R.id.img_5);
        img_06 = (ImageView) view_sex.findViewById(R.id.img_6);
        img_07 = (ImageView) view_sex.findViewById(R.id.img_7);
        img_08 = (ImageView) view_sex.findViewById(R.id.img_8);
        img_09 = (ImageView) view_sex.findViewById(R.id.img_9);
        img_10 = (ImageView) view_sex.findViewById(R.id.img_10);
        img_11 = (ImageView) view_sex.findViewById(R.id.img_11);
        RelativeLayout rl_all = (RelativeLayout) view_sex.findViewById(R.id.rl_all);

        if (list_languages.contains("中文")) {
            str_1 = "1";
            img_01.setImageResource(R.mipmap.bg_languages_selected);
        }
        if (list_languages.contains("英语")) {
            str_2 = "1";
            img_02.setImageResource(R.mipmap.bg_languages_selected);
        }
        if (list_languages.contains("法语")) {
            str_3 = "1";
            img_03.setImageResource(R.mipmap.bg_languages_selected);
        }
        if (list_languages.contains("德语")) {
            str_4 = "1";
            img_04.setImageResource(R.mipmap.bg_languages_selected);
        }
        if (list_languages.contains("意大利语")) {
            str_5 = "1";
            img_05.setImageResource(R.mipmap.bg_languages_selected);
        }
        if (list_languages.contains("日语")) {
            str_6 = "1";
            img_06.setImageResource(R.mipmap.bg_languages_selected);
        }
        if (list_languages.contains("韩语")) {
            str_7 = "1";
            img_07.setImageResource(R.mipmap.bg_languages_selected);
        }
        if (list_languages.contains("葡萄牙语")) {
            str_8 = "1";
            img_08.setImageResource(R.mipmap.bg_languages_selected);
        }
        if (list_languages.contains("泰语")) {
            str_9 = "1";
            img_09.setImageResource(R.mipmap.bg_languages_selected);
        }
        if (list_languages.contains("越南语")) {
            str_10 = "1";
            img_10.setImageResource(R.mipmap.bg_languages_selected);
        }
        if (list_languages.contains("印尼语")) {
            str_11 = "1";
            img_11.setImageResource(R.mipmap.bg_languages_selected);
        }

        tv_done_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_language.dismiss();
                tv_languages.setText("");
                tv_languages.setText(list_languages.toString().substring(1, list_languages.toString().length() - 1));
                tv_languages.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
            }
        });
        tv_cancle_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_language.dismiss();
            }
        });
        rl_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_1.equals("0")) {
                    str_1 = "1";
                    img_01.setImageResource(R.mipmap.bg_languages_selected);
                    list_languages.add("中文");
                } else if (str_1.equals("1")) {
                    str_1 = "0";
                    list_languages.remove("中文");
                    img_01.setImageResource(R.mipmap.bg_languages_default);
                }
            }
        });
        rl_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_2.equals("0")) {
                    str_2 = "1";
                    img_02.setImageResource(R.mipmap.bg_languages_selected);
                    list_languages.add("英语");
                } else if (str_2.equals("1")) {
                    str_2 = "0";
                    list_languages.remove("英语");
                    img_02.setImageResource(R.mipmap.bg_languages_default);
                }
            }
        });
        rl_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_3.equals("0")) {
                    str_3 = "1";
                    img_03.setImageResource(R.mipmap.bg_languages_selected);
                    list_languages.add("法语");
                } else if (str_3.equals("1")) {
                    str_3 = "0";
                    list_languages.remove("法语");
                    img_03.setImageResource(R.mipmap.bg_languages_default);
                }
            }
        });
        rl_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_4.equals("0")) {
                    str_4 = "1";
                    img_04.setImageResource(R.mipmap.bg_languages_selected);
                    list_languages.add("德语");
                } else if (str_4.equals("1")) {
                    str_4 = "0";
                    list_languages.remove("德语");
                    img_04.setImageResource(R.mipmap.bg_languages_default);
                }
            }
        });
        rl_05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_5.equals("0")) {
                    str_5 = "1";
                    img_05.setImageResource(R.mipmap.bg_languages_selected);
                    list_languages.add("意大利语");
                } else if (str_5.equals("1")) {
                    str_5 = "0";
                    list_languages.remove("意大利语");
                    img_05.setImageResource(R.mipmap.bg_languages_default);
                }
            }
        });
        rl_06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_6.equals("0")) {
                    str_6 = "1";
                    img_06.setImageResource(R.mipmap.bg_languages_selected);
                    list_languages.add("日语");
                } else if (str_6.equals("1")) {
                    str_6 = "0";
                    list_languages.remove("日语");
                    img_06.setImageResource(R.mipmap.bg_languages_default);
                }
            }
        });
        rl_07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_7.equals("0")) {
                    str_7 = "1";
                    img_07.setImageResource(R.mipmap.bg_languages_selected);
                    list_languages.add("韩语");
                } else if (str_7.equals("1")) {
                    str_7 = "0";
                    list_languages.remove("韩语");
                    img_07.setImageResource(R.mipmap.bg_languages_default);
                }
            }
        });
        rl_08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_8.equals("0")) {
                    str_8 = "1";
                    img_08.setImageResource(R.mipmap.bg_languages_selected);
                    list_languages.add("葡萄牙语");
                } else if (str_8.equals("1")) {
                    str_8 = "0";
                    list_languages.remove("葡萄牙语");
                    img_08.setImageResource(R.mipmap.bg_languages_default);
                }
            }
        });
        rl_09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_9.equals("0")) {
                    str_9 = "1";
                    img_09.setImageResource(R.mipmap.bg_languages_selected);
                    list_languages.add("泰语");
                } else if (str_9.equals("1")) {
                    str_9 = "0";
                    list_languages.remove("泰语");
                    img_09.setImageResource(R.mipmap.bg_languages_default);
                }
            }
        });
        rl_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_10.equals("0")) {
                    str_10 = "1";
                    img_10.setImageResource(R.mipmap.bg_languages_selected);
                    list_languages.add("越南语");
                } else if (str_10.equals("1")) {
                    str_10 = "0";
                    list_languages.remove("越南语");
                    img_10.setImageResource(R.mipmap.bg_languages_default);
                }
            }
        });
        rl_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_11.equals("0")) {
                    str_11 = "1";
                    img_11.setImageResource(R.mipmap.bg_languages_selected);
                    list_languages.add("印尼语");
                } else if (str_11.equals("1")) {
                    str_11 = "0";
                    list_languages.remove("印尼语");
                    img_11.setImageResource(R.mipmap.bg_languages_default);
                }
            }
        });

        dialog_language.setContentView(view_sex);
        dialog_language.show();
        WindowManager m2 = getWindowManager();
        Display d2 = m2.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p2 = dialog_language
                .getWindow().getAttributes(); // 获取对话框当前的参数值
        p2.width = (int) (d2.getWidth()); // 宽度设置为全屏
        dialog_language.getWindow().setAttributes(p2); // 设置生效
    }

    /**
     * 事业年份
     */
    public void showYear() {
        dialog_year = new Dialog(AuthenticationActivity.this,
                R.style.style_custom_dialog);
        View view_sex = View.inflate(AuthenticationActivity.this,
                R.layout.dialog_select_year, null);
        wheel_year = (WheelView) view_sex.findViewById(R.id.year);
        wheel_month = (WheelView) view_sex.findViewById(R.id.month);
        TextView tv_done_sex = (TextView) view_sex
                .findViewById(R.id.tv_done_sex);
        TextView tv_cancle_sex = (TextView) view_sex
                .findViewById(R.id.tv_cancle_sex);
        RelativeLayout rl_all = (RelativeLayout) view_sex.findViewById(R.id.rl_all);
        wheel_year.setViewAdapter(new ArrayWheelAdapter(AuthenticationActivity.this, list_year));
        // 设置可见条目数量
        wheel_year.setVisibleItems(7);
        wheel_year.setCurrentItem(list_year.size() / 2);
        wheel_year.addChangingListener(this);

        wheel_month.setViewAdapter(new ArrayWheelAdapter(AuthenticationActivity.this, list_month));
        // 设置可见条目数量
        wheel_month.setVisibleItems(7);
        wheel_month.setCurrentItem(list_month.size() / 2);
        wheel_month.addChangingListener(this);

        tv_done_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_year.dismiss();
                if (isChangeyear == false && isChangemonth == false) {
                    tv_start_year.setText(list_year.get(list_year.size() / 2) + list_month.get(list_month.size() / 2));
                    tv_start_year.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                } else if (isChangeyear == false && isChangemonth == true) {
                    tv_start_year.setText(list_year.get(list_year.size() / 2) + select_month);
                    tv_start_year.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                } else if (isChangeyear == true && isChangemonth == false) {
                    tv_start_year.setText(select_year + list_month.get(list_month.size() / 2));
                    tv_start_year.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                } else if (isChangeyear == true && isChangemonth == true) {
                    tv_start_year.setText(select_year + select_month);
                    tv_start_year.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                }
            }
        });
        tv_cancle_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_year.dismiss();
            }
        });
        dialog_year.setContentView(view_sex);
        dialog_year.show();
        WindowManager m2 = getWindowManager();
        Display d2 = m2.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p2 = dialog_year
                .getWindow().getAttributes(); // 获取对话框当前的参数值
        p2.width = (int) (d2.getWidth()); // 宽度设置为全屏
        dialog_year.getWindow().setAttributes(p2); // 设置生效
    }

    public void openStartDialog() {
        startDialog = new Dialog(AuthenticationActivity.this,
                R.style.style_custom_dialog);
        View startView = View.inflate(AuthenticationActivity.this,
                R.layout.wheel_view, null);
        wheel_year_start = (WheelView) startView.findViewById(R.id.year);
        wheel_month_start = (WheelView) startView.findViewById(R.id.month);
        wheel_day_start = (WheelView) startView.findViewById(R.id.day);
        startDone = (TextView) startView.findViewById(R.id.done);
        startCancle = (TextView) startView.findViewById(R.id.cancle);
        RelativeLayout rl_all = (RelativeLayout) startView.findViewById(R.id.rl_all);
        res = getResources();
        months = res.getStringArray(R.array.months);
        days = res.getStringArray(R.array.days_31);
        date = new Date();
        String year = yearFormat.format(date);
        max_Year = Integer.parseInt(year);
        startYearList = new ArrayList<String>();
        for (int i = MIN_YEAR; i <= max_Year; i++) {
            startYearList.add(i + "");
        }
        startMonthList = Arrays.asList(months);
        startDayList = Arrays.asList(days);
        startYearAdapter = new YearAdapter(this, startYearList);
        startMonthAdapter = new MonthAdapter(this, startMonthList);
        startDayAdapter = new DayAdapter(this, startDayList);
        wheel_year_start.setViewAdapter(startYearAdapter);
        wheel_month_start.setViewAdapter(startMonthAdapter);
        wheel_day_start.setViewAdapter(startDayAdapter);

        wheel_year_start.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                startYearIndex = wheel.getCurrentItem();
                String year = (String) startYearAdapter.getItemText(startYearIndex);
                String month = (String) startMonthAdapter.getItemText(startMonthIndex);
                if (Integer.parseInt(month) == 2) {
                    if (isLeapYear(year)) {
                        //29 闰年2月29天
                        if (startDayAdapter.list.size() != 29) {
                            startDayList = Arrays.asList(res.getStringArray(R.array.days_29));
                            startDayAdapter = new DayAdapter(AuthenticationActivity.this, startDayList);
                            wheel_day_start.setViewAdapter(startDayAdapter);
                            if (startDayIndex > 28) {
                                wheel_day_start.setCurrentItem(0);
                                startDayIndex = 0;
                            } else {
                                wheel_day_start.setCurrentItem(startDayIndex);
                            }
                        }
                    } else {
                        //28 非闰年2月28天
                        if (startDayAdapter.list.size() != 28) {
                            startDayList = Arrays.asList(res.getStringArray(R.array.days_28));
                            startDayAdapter = new DayAdapter(AuthenticationActivity.this, startDayList);
                            wheel_day_start.setViewAdapter(startDayAdapter);
                            if (startDayIndex > 27) {
                                wheel_day_start.setCurrentItem(0);
                                startDayIndex = 0;
                            } else {
                                wheel_day_start.setCurrentItem(startDayIndex);
                            }
                        }
                    }
                }
            }
        });

        wheel_month_start.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                startMonthIndex = wheel.getCurrentItem();
                String year = (String) startYearAdapter.getItemText(startYearIndex);
                String month = (String) startMonthAdapter.getItemText(startMonthIndex);
                int i = Integer.parseInt(month);
                if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
                    //31
                    if (startDayAdapter.list.size() != 31) {
                        startDayList = Arrays.asList(res.getStringArray(R.array.days_31));
                        startDayAdapter = new DayAdapter(AuthenticationActivity.this, startDayList);
                        wheel_day_start.setViewAdapter(startDayAdapter);
                        wheel_day_start.setCurrentItem(startDayIndex);
                    }
                } else if (i == 2) {
                    if (isLeapYear(year)) {
                        //29 闂板勾2鏈?9澶?
                        if (startDayAdapter.list.size() != 29) {
                            startDayList = Arrays.asList(res.getStringArray(R.array.days_29));
                            startDayAdapter = new DayAdapter(AuthenticationActivity.this, startDayList);
                            wheel_day_start.setViewAdapter(startDayAdapter);
                            if (startDayIndex > 28) {
                                wheel_day_start.setCurrentItem(0);
                                startDayIndex = 0;
                            } else {
                                wheel_day_start.setCurrentItem(startDayIndex);
                            }
                        }
                    } else {
                        //28 闈為棸骞?鏈?8澶?
                        if (startDayAdapter.list.size() != 28) {
                            startDayList = Arrays.asList(res.getStringArray(R.array.days_28));
                            startDayAdapter = new DayAdapter(AuthenticationActivity.this, startDayList);
                            wheel_day_start.setViewAdapter(startDayAdapter);
                            if (startDayIndex > 27) {
                                wheel_day_start.setCurrentItem(0);
                                startDayIndex = 0;
                            } else {
                                wheel_day_start.setCurrentItem(startDayIndex);
                            }
                        }
                    }
                } else {
                    //30
                    if (startDayAdapter.list.size() != 30) {
                        startDayList = Arrays.asList(res.getStringArray(R.array.days_30));
                        startDayAdapter = new DayAdapter(AuthenticationActivity.this, startDayList);
                        wheel_day_start.setViewAdapter(startDayAdapter);
                        if (startDayIndex > 29) {
                            wheel_day_start.setCurrentItem(0);
                            startDayIndex = 0;
                        } else {
                            wheel_day_start.setCurrentItem(startDayIndex);
                        }
                    }
                }
            }
        });
        wheel_day_start.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                startDayIndex = wheel.getCurrentItem();
            }
        });
        startDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog.dismiss();
                startYear = (String) startYearAdapter.getItemText(startYearIndex);
                startMonth = (String) startMonthAdapter.getItemText(startMonthIndex);
                startDay = (String) startDayAdapter.getItemText(startDayIndex);
                tv_birthday.setText(startYear + "-" + startMonth + "-" + startDay);
                tv_birthday.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
            }
        });
        startCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog.dismiss();
            }
        });
        startDialog.setContentView(startView);
        if ("".equals(startYear)) {
            startYear = max_Year + "";
            startMonth = monthFormat.format(date);
            startDay = dayFormat.format(date);
        }
        startYearIndex = startYearList.indexOf(startYear);
        startMonthIndex = startMonthList.indexOf(startMonth);
        startDayIndex = startDayList.indexOf(startDay);
        if (startYearIndex == -1) {
            startYearIndex = 0;
        }
        if (startMonthIndex == -1) {
            startMonthIndex = 0;
        }
        if (startDayIndex == -1) {
            startDayIndex = 0;
        }
        wheel_year_start.setCurrentItem(startYearIndex);
        wheel_month_start.setCurrentItem(startMonthIndex);
        wheel_day_start.setCurrentItem(startDayIndex);
        startDialog.show();
        WindowManager m2 = getWindowManager();
        Display d2 = m2.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p2 = startDialog
                .getWindow().getAttributes(); // 获取对话框当前的参数值
        p2.width = (int) (d2.getWidth()); // 宽度设置为全屏
        startDialog.getWindow().setAttributes(p2); // 设置生效
    }

    /**
     * 判断是否是闰年
     */
    public static boolean isLeapYear(String str) {
        int year = Integer.parseInt(str);
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    private class YearAdapter extends AbstractWheelTextAdapter {
        /**
         * Constructor
         */
        public List<String> list;

        protected YearAdapter(Context context, List<String> list) {
            super(context, R.layout.wheel_view_layout, NO_RESOURCE);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);

            TextView textCity = (TextView) view.findViewById(R.id.textView);
            textCity.setText(list.get(index));
            return view;
        }

        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index);
        }
    }

    private class MonthAdapter extends AbstractWheelTextAdapter {
        /**
         * Constructor
         */
        public List<String> list;

        protected MonthAdapter(Context context, List<String> list) {
            super(context, R.layout.wheel_view_layout, NO_RESOURCE);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);

            TextView textCity = (TextView) view.findViewById(R.id.textView);
            textCity.setText(list.get(index));
            return view;
        }

        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index);
        }
    }

    private class DayAdapter extends AbstractWheelTextAdapter {
        /**
         * Constructor
         */
        public List<String> list;

        protected DayAdapter(Context context, List<String> list) {
            super(context, R.layout.wheel_view_layout, NO_RESOURCE);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);

            TextView textCity = (TextView) view.findViewById(R.id.textView);
            textCity.setText(list.get(index));
            return view;
        }

        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index);
        }
    }

    /**
     * 滚动事件监听
     *
     * @param wheel    the wheel view whose state has changed
     * @param oldValue the old value of current item
     * @param newValue
     */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == wheel_year) {
            isChangeyear = true;
            int pCurrent = wheel_year.getCurrentItem();
            select_year = list_year.get(pCurrent);
        } else if (wheel == wheel_month) {
            isChangemonth = true;
            int pCurrent = wheel_month.getCurrentItem();
            select_month = list_month.get(pCurrent);
        }
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
    private void compressWithRx(File file) {
        Luban.get(this)
                .load(file)
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
                        img_picture.setImageBitmap(convertFullBitmap(imagePath));
                        tv_load.setVisibility(View.GONE);
                        String sign = TGmd5.getMD5("");
                        doUpload(file, sign);
                    }
                });
    }
}
