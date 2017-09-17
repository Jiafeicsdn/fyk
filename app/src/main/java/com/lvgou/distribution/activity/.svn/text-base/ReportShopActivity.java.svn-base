package com.lvgou.distribution.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.GridViewPathImageAdapter;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.luban.Luban;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.CalendarUtils;
import com.lvgou.distribution.utils.PermissionManager;
import com.lvgou.distribution.utils.PictureUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.Tools;
import com.lvgou.distribution.view.MyGridView;
import com.lvgou.distribution.wheelview.OnWheelChangedListener;
import com.lvgou.distribution.wheelview.WheelView;
import com.lvgou.distribution.wheelview.adapter.ArrayWheelAdapter;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.ObjectUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 * Created by Snow on 2016/7/2
 * 店铺报备操作
 */
public class ReportShopActivity extends BaseActivity implements OnWheelChangedListener, View.OnTouchListener {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.scroll_view)
    private ScrollView scroll_view;
    @ViewInject(R.id.tv_shop_name)
    private TextView tv_shop_name;
    @ViewInject(R.id.et_travel_name)
    private EditText et_travel_name;
    @ViewInject(R.id.tv_name)
    private EditText et_guider_name;
    @ViewInject(R.id.tv_phone)
    private EditText et_phone;
    @ViewInject(R.id.tv_car_num)
    private EditText et_car_num;
    @ViewInject(R.id.rp_num)
    private RadioGroup mGroup_people_num;
    @ViewInject(R.id.radio_num)
    private RadioButton mRadio_num;
    @ViewInject(R.id.radio_size)
    private RadioButton mRadio_size;
    @ViewInject(R.id.radio_female)
    private RadioButton mRadio_sex;
    @ViewInject(R.id.img_num)
    private ImageView img_num;
    @ViewInject(R.id.img_size)
    private ImageView img_size;
    @ViewInject(R.id.img_sex)
    private ImageView img_sex;
    @ViewInject(R.id.ll_num)
    private LinearLayout ll_num;
    @ViewInject(R.id.et_by_num)
    private EditText et_by_num;
    @ViewInject(R.id.ll_adult)
    private LinearLayout ll_adult;
    @ViewInject(R.id.et_by_adult)
    private EditText et_by_adult;
    @ViewInject(R.id.ll_child)
    private LinearLayout ll_child;
    @ViewInject(R.id.et_by_child)
    private EditText et_by_child;
    @ViewInject(R.id.ll_male)
    private LinearLayout ll_male;
    @ViewInject(R.id.et_by_boy)
    private EditText et_by_boy;
    @ViewInject(R.id.ll_femal)
    private LinearLayout ll_femal;
    @ViewInject(R.id.et_by_girl)
    private EditText et_by_girl;
    @ViewInject(R.id.rp_time)
    private RadioGroup mGroupTime;
    @ViewInject(R.id.radio_sure_time)
    private RadioButton radio_sure_time;
    @ViewInject(R.id.radio_not_sure_time)
    private RadioButton radio_not_sure_time;
    @ViewInject(R.id.img_sure)
    private ImageView img_sure_time;
    @ViewInject(R.id.img_not_sure)
    private ImageView img_not_sure_time;
    @ViewInject(R.id.ll_sure_time)
    private LinearLayout ll_sure_time;
    @ViewInject(R.id.rl_address)
    private RelativeLayout rl_address;
    @ViewInject(R.id.et_tourist_source)
    private TextView tv_address;
    @ViewInject(R.id.et_sure_time)
    private TextView et_sure_time;
    @ViewInject(R.id.rl_select_time)
    private RelativeLayout rl_select_time;
    @ViewInject(R.id.ll_not_sure_start_time)
    private LinearLayout ll_not_sure_start_time;
    @ViewInject(R.id.et_not_sure_start_time)
    private TextView et_not_sure_start_time;
    @ViewInject(R.id.ll_not_sure_end_time)
    private LinearLayout ll_not_sure_end_time;
    @ViewInject(R.id.et_not_sure_end_time)
    private TextView et_not_sure_end_time;
    @ViewInject(R.id.tv_sure_time)
    private TextView tv_text_sure_time;
    @ViewInject(R.id.tv_not_sure_start_time)
    private TextView tv_not_sure_start_time;
    @ViewInject(R.id.tv_not_sure_end_time)
    private TextView tv_not_sure_end_time;
    @ViewInject(R.id.et_shop_times)
    private TextView et_shop_times;
    @ViewInject(R.id.rl_select_num)
    private LinearLayout rl_select_num;
    @ViewInject(R.id.tv_one)
    private TextView tv_one;
    @ViewInject(R.id.rl_one)
    private RelativeLayout rl_one;
    @ViewInject(R.id.et_shop_situation)
    private EditText et_shop_situation;
    @ViewInject(R.id.et_teacher_requirement)
    private EditText et_teacher_requirement;
    @ViewInject(R.id.et_teacher_theme)
    private EditText et_teacher_theme;
    @ViewInject(R.id.et_main_product)
    private EditText et_main_product;
    @ViewInject(R.id.et_group_analyz)
    private EditText et_group_analyz;
    @ViewInject(R.id.et_features)
    private EditText et_features;
    @ViewInject(R.id.gridview)
    private MyGridView gradeView;
    @ViewInject(R.id.btn_login)
    private Button btn_login;
    @ViewInject(R.id.btn_login_one)
    private Button btn_login_one;
    @ViewInject(R.id.btn_cancle)
    private Button btn_cancle;
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
    @ViewInject(R.id.tv_shili)
    private TextView tv_shili;
    @ViewInject(R.id.view_adult)
    private View view_adult;
    @ViewInject(R.id.view_boy)
    private View view_boy;
    @ViewInject(R.id.view_time)
    private View view_time;
    @ViewInject(R.id.view_num)
    private View view_num;
    @ViewInject(R.id.rl_teacher_icon)
    private RelativeLayout rl_teacher_icon;
    @ViewInject(R.id.tv_teacher_name)
    private TextView tv_teacher_name;
    @ViewInject(R.id.btn_cancle_one)
    private Button btn_cancle_report;
    private Dialog dialog_shop, dialog_time, call_dialog, cancle_dialog;
    private Dialog dialog;
    private WheelView wheel_shop, wheel_date, wheel_hour, wheel_minute;
    private List<String> shop_data = new ArrayList<String>();
    private List<String> week_data = new ArrayList<String>();
    private List<String> hour_data = new ArrayList<String>();
    private List<String> minute_data = new ArrayList<String>();
    private String shop_times_wheel = "1";
    private String shop_date = "";
    private String shop_hour = "";
    private String shop_minute = "";

    private boolean isWheelChangeShop = false;
    private boolean isWheelChangeDate = false;
    private boolean isWheelChangeHour = false;
    private boolean isWheelChangeMinute = false;
    private String peopleType = "1";//人数类型：1=按人数，2=按大小，3=按男女
    private String reachType = "1";// 到达类型：1=固定时间，2=不确定时间
    private List<String> list_urls = new ArrayList<String>(); //图片地址集合

    private String upLoadPath = "";
    private GridViewPathImageAdapter pathImageAdapter;
    private List<Bitmap> list = new ArrayList<Bitmap>();
    private int size = 0;

    private String receiver_name = "";
    private String reportId = "";
    private String reportSellerId = "";
    private String distributorid = "";
    private String call_phone = "";
    private String user_name = "";

    // 提交信息
    private String travel_name;
    private String travel;
    private String tourName;
    private String mobile;
    private String plate;
    private String peopleCount1;
    private String peopleCount2;
    private String reachTime1;
    private String reachTime2;
    private String source;
    private String city_source;
    private String shopCount;
    private String beforeShopInfo;
    private String lecturer;
    private String lecturerContent;
    private String lecturerProduct;
    private String teamInfo;
    private String pandaInfo;
    private String pictures;
    private String strs = "";

    private String account_name;
    private String real_name;
    private boolean isShowSelectTime = false;

    private String trval_name_one = "";
    private String month = "";
    private String day = "";
    private String hour = "";
    private String minute = "";

    private String index = "";
    /**
     * 当前的图片路径
     */
    private String imagePath = null;
    private final static int REQUEST_CODE_GALLERY = 0x33;
    private final static int REQUEST_CODE_CAMERA = 0x44;
    private String load_path = "";


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    dismissProgressDialog();
                    gradeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == getDataSize() && size <= 4) {// 点击“+”号位置添加图片
                                openDialog();
                            } else if (size > 4) {
                                MyToast.show(ReportShopActivity.this, "最多上传4张图片");
                                return;
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("image_urls", list_urls.toString());
                                bundle.putString("image_index", position + "");
                                openActivity(ImageReportActivity.class, bundle);
                            }
                        }
                    });
                    pathImageAdapter = new GridViewPathImageAdapter(ReportShopActivity.this, list_urls);
                    gradeView.setAdapter(pathImageAdapter);
                    pathImageAdapter.setList(list_urls);
                    scroll_view.scrollTo(10, 10);
                    break;
                case 1001:
                    dismissProgressDialog();
                    gradeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == getDataSize() && size <= 4) {// 点击“+”号位置添加图片
                                openDialog();
                            } else if (size > 4) {
                                MyToast.show(ReportShopActivity.this, "最多上传4张图片");
                                return;
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("image_urls", list_urls.toString());
                                bundle.putString("image_index", position + "");
                                openActivity(ImageReportActivity.class, bundle);
                            }
                        }
                    });
                    pathImageAdapter = new GridViewPathImageAdapter(ReportShopActivity.this, list_urls);
                    gradeView.setAdapter(pathImageAdapter);
                    pathImageAdapter.setList(list_urls);
                    scroll_view.scrollTo(10, 10);
                    break;
                case 1002:
                    pathImageAdapter.setList(list_urls);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_shop);
        ViewUtils.inject(this);
        EventBus.getDefault().register(ReportShopActivity.this);
        distributorid = PreferenceHelper.readString(ReportShopActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        account_name = PreferenceHelper.readString(ReportShopActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ACCOUNT);
        real_name = PreferenceHelper.readString(ReportShopActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME);
        trval_name_one = PreferenceHelper.readString(ReportShopActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TRAVAL_NAME, "");
        reportId = getTextFromBundle("reportId");
        receiver_name = getTextFromBundle("shop_name");
        reportSellerId = getTextFromBundle("reportSellerId");
        index = getTextFromBundle("index");
        tv_shop_name.setText(receiver_name);
        et_travel_name.setText(trval_name_one);
        if (reportId.equals("0")) {
            Constants.TOTAL_CITY = "";
            Constants.CITY_PATH = "";
            initGridView();
            initNumLayout();
            btn_login_one.setText("发送");
            btn_login_one.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.GONE);
            btn_cancle.setVisibility(View.GONE);
            ll_num.setVisibility(View.VISIBLE);
            mRadio_num.setChecked(true);
            img_num.setVisibility(View.VISIBLE);
            initTimeLayout();
            ll_sure_time.setVisibility(View.VISIBLE);
            radio_sure_time.setChecked(true);
            img_sure_time.setVisibility(View.VISIBLE);
            et_phone.setText(account_name);
            et_guider_name.setText(real_name);
            Calendar now = Calendar.getInstance();
            if (((now.get(Calendar.MONTH) + 1) + "").length() == 1) {
                month = "0" + (now.get(Calendar.MONTH) + 1);
            } else if (((now.get(Calendar.MONTH) + 1) + "").length() == 2) {
                month = (now.get(Calendar.MONTH) + 1) + "";
            }
            if ((now.get(Calendar.DAY_OF_MONTH) + "").length() == 1) {
                day = "0" + now.get(Calendar.DAY_OF_MONTH) + "";
            } else if ((now.get(Calendar.DAY_OF_MONTH) + "").length() == 2) {
                day = now.get(Calendar.DAY_OF_MONTH) + "";
            }

            if ((now.get(Calendar.HOUR_OF_DAY) + "").length() == 1) {
                hour = "0" + now.get(Calendar.HOUR_OF_DAY);
            } else if ((now.get(Calendar.HOUR_OF_DAY) + "").length() == 2) {
                hour = now.get(Calendar.HOUR_OF_DAY) + "";
            }
            if ((now.get(Calendar.MINUTE) + "").length() == 1) {
                minute = "0" + now.get(Calendar.MINUTE);
            } else if ((now.get(Calendar.MINUTE) + "").length() == 2) {
                minute = now.get(Calendar.MINUTE) + "";
            }
            et_sure_time.setText(month + "月" + day + "日 " + hour + ":" + minute);
            et_not_sure_start_time.setText(month + "月" + day + "日 " + hour + ":" + minute);
            et_not_sure_end_time.setText(month + "月" + day + "日 " + hour + ":" + minute);
        } else {
            btn_login_one.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);
            btn_cancle.setVisibility(View.VISIBLE);
            if (distributorid.equals("") || distributorid.equals("null")) {
                openActivity(LoginActivity.class);
                finish();
            } else {
                String sign = TGmd5.getMD5(distributorid + reportId);
                getData(distributorid, reportId, sign);
            }
        }
        initRadioGroup();
        initEditText();
        initTimeData();
        view_adult.setVisibility(View.GONE);
        view_num.setVisibility(View.GONE);
        view_boy.setVisibility(View.GONE);
    }

    private PermissionManager permissionManager;
    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick({R.id.rl_back, R.id.rl_select_num, R.id.btn_login, R.id.ll_sure_time, R.id.ll_not_sure_start_time, R.id.ll_not_sure_end_time, R.id.tv_select_gallery, R.id.tv_select_camera, R.id.tv_cancel, R.id.rl_address, R.id.tv_shili, R.id.rl_teacher_icon, R.id.btn_cancle, R.id.btn_login_one, R.id.btn_cancle_one})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                Constants.IS_SHOW_ADD = "0";
                finish();
                break;
            case R.id.rl_select_num:
                openSelextShopDialog();
                break;
            case R.id.btn_login:
                String travel_name = et_travel_name.getText().toString();
                String tourName = et_guider_name.getText().toString();
                String phone = et_phone.getText().toString();
                String car_num = et_car_num.getText().toString();
                String size_num = et_by_num.getText().toString();
                String adult_num = et_by_adult.getText().toString();
                String child_num = et_by_child.getText().toString();
                String boy_num = et_by_boy.getText().toString();
                String girl_num = et_by_girl.getText().toString();
                String sure_time = tv_text_sure_time.getText().toString();
                String start_tiem = tv_not_sure_start_time.getText().toString();
                String end_time = tv_not_sure_end_time.getText().toString();
                String shop_times = et_shop_times.getText().toString();
                String serious = et_shop_situation.getText().toString();
                String requement = et_teacher_requirement.getText().toString();
                String theme = et_teacher_theme.getText().toString();
                String product = et_main_product.getText().toString();
                String analy = et_group_analyz.getText().toString();
                String city = Constants.CITY_PATH;
                Date start_date;
                Date end_date;
                if (travel_name.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入旅行社名称");
                    et_travel_name.requestFocus();
                    return;
                }
                if (tourName.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入导游姓名");
                    return;
                }
                if (phone.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入手机号");
                    return;
                } else if (!StringUtils.isPhone(phone)) {
                    MyToast.show(ReportShopActivity.this, "手机号不合法");
                    return;
                }
                if (car_num.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入车牌号");
                    et_car_num.requestFocus();
                    return;
                }
                if (peopleType.equals("1")) {
                    if (size_num.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入人数");
                        et_by_num.requestFocus();
                        return;
                    }
                } else if (peopleType.equals("2")) {
                    if (adult_num.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入成人人数");
                        et_by_adult.requestFocus();
                        return;
                    }
                    if (child_num.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入孩童人数");
                        et_by_child.requestFocus();
                        return;
                    }
                } else if (peopleType.equals("3")) {
                    if (boy_num.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入男生人数");
                        et_by_boy.requestFocus();
                        return;
                    }
                    if (girl_num.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入女生人数");
                        et_by_girl.requestFocus();
                        return;
                    }
                }

                if (reachType.equals("1")) {
                    if (sure_time.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请选择时间");
                        return;
                    }
                } else if (reachType.equals("2")) {
                    if (start_tiem.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请选择起始时间");
                        return;
                    }
                    if (end_time.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请选择结束时间");
                        return;
                    }
                }
                if (reachType.equals("1")) {
                    if (tv_text_sure_time.getText().toString().substring(0, 2).equals("今天")) {
                        reachTime1 = new CalendarUtils().getNowTime("yyyy-MM-dd") + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                        reachTime2 = new CalendarUtils().getNowTime("yyyy-MM-dd") + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                    } else if (tv_text_sure_time.getText().toString().substring(0, 2).equals("明天")) {
                        reachTime1 = new CalendarUtils().getBeforeOrAfterDay(1) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                        reachTime2 = new CalendarUtils().getBeforeOrAfterDay(1) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                    } else if (tv_text_sure_time.getText().toString().substring(0, 2).equals("后天")) {
                        reachTime1 = new CalendarUtils().getBeforeOrAfterDay(2) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                        reachTime2 = new CalendarUtils().getBeforeOrAfterDay(2) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                    } else {
                        reachTime1 = new CalendarUtils().getNowTime("yyyy") + "-" + tv_text_sure_time.getText().toString().split(" ")[0].substring(0, 2) + "-" + tv_text_sure_time.getText().toString().split(" ")[0].substring(3, 5) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                        reachTime2 = new CalendarUtils().getNowTime("yyyy") + "-" + tv_text_sure_time.getText().toString().split(" ")[0].substring(0, 2) + "-" + tv_text_sure_time.getText().toString().split(" ")[0].substring(3, 5) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                    }
                } else if (reachType.equals("2")) {
                    if (tv_not_sure_start_time.getText().toString().substring(0, 2).equals("今天")) {
                        reachTime1 = new CalendarUtils().getNowTime("yyyy-MM-dd") + " " + tv_not_sure_start_time.getText().toString().split(" ")[1];
                    } else if (tv_not_sure_start_time.getText().toString().substring(0, 2).equals("明天")) {
                        reachTime1 = new CalendarUtils().getBeforeOrAfterDay(1) + " " + tv_not_sure_start_time.getText().toString().split(" ")[1];
                    } else if (tv_not_sure_start_time.getText().toString().substring(0, 2).equals("后天")) {
                        reachTime1 = new CalendarUtils().getBeforeOrAfterDay(2) + " " + tv_not_sure_start_time.getText().toString().split(" ")[1];
                    } else {
                        reachTime1 = new CalendarUtils().getNowTime("yyyy") + "-" + tv_not_sure_start_time.getText().toString().split(" ")[0].substring(0, 2) + "-" + tv_not_sure_start_time.getText().toString().split(" ")[0].substring(3, 5) + " " + tv_not_sure_start_time.getText().toString().split(" ")[1];
                    }
                    if (tv_not_sure_end_time.getText().toString().substring(0, 2).equals("今天")) {
                        reachTime2 = new CalendarUtils().getNowTime("yyyy-MM-dd") + " " + tv_not_sure_end_time.getText().toString().split(" ")[1];
                    } else if (tv_not_sure_end_time.getText().toString().substring(0, 2).equals("明天")) {
                        reachTime2 = new CalendarUtils().getBeforeOrAfterDay(1) + " " + tv_not_sure_end_time.getText().toString().split(" ")[1];
                    } else if (tv_not_sure_end_time.getText().toString().substring(0, 2).equals("后天")) {
                        reachTime2 = new CalendarUtils().getBeforeOrAfterDay(2) + " " + tv_not_sure_end_time.getText().toString().split(" ")[1];
                    } else {
                        reachTime2 = new CalendarUtils().getNowTime("yyyy") + "-" + tv_not_sure_end_time.getText().toString().split(" ")[0].substring(0, 2) + "-" + tv_not_sure_end_time.getText().toString().split(" ")[0].substring(3, 5) + " " + tv_not_sure_end_time.getText().toString().split(" ")[1];
                    }
                }

                SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                if (reachType.equals("1")) {
                    try {
                        start_date = dfs.parse(reachTime1);
                        end_date = dfs.parse(dfs.format(new Date()));
                        if ((start_date.getTime() - end_date.getTime()) / 1000 < 0) {
                            MyToast.show(ReportShopActivity.this, "到店结束时间不可小于当前时间");
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if (reachType.equals("2")) {
                    try {
                        start_date = dfs.parse(reachTime1);
                        end_date = dfs.parse(reachTime2);
                        if ((start_date.getTime() - end_date.getTime()) / 1000 > 0) {
                            MyToast.show(ReportShopActivity.this, "到店结束时间不可大于到店开始时间");
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (city.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请选择客源地");
                    return;
                }
                if (shop_times.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请选择店铺");
                    return;
                }
                if (!shop_times.equals("1")) {
                    if (serious.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入前几店情况");
                        et_shop_situation.requestFocus();
                        return;
                    }
                }
                if (requement.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入讲师要求");
                    et_teacher_requirement.requestFocus();
                    return;
                }
                if (theme.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入主讲概念");
                    et_teacher_theme.requestFocus();
                    return;
                }
                if (product.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入主推产品");
                    et_main_product.requestFocus();
                    return;
                }
                if (analy.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入团队分析");
                    et_group_analyz.requestFocus();
                    return;
                }

                travel_name = et_travel_name.getText().toString();
                tourName = et_guider_name.getText().toString();
                mobile = et_phone.getText().toString();
                plate = et_car_num.getText().toString();
                if (peopleType.equals("1")) {
                    peopleCount1 = et_by_num.getText().toString();
                    peopleCount2 = "0";
                } else if (peopleType.equals("2")) {
                    peopleCount1 = et_by_adult.getText().toString();
                    peopleCount2 = et_by_child.getText().toString();
                } else if (peopleType.equals("3")) {
                    peopleCount1 = et_by_boy.getText().toString();
                    peopleCount2 = et_by_girl.getText().toString();
                }

                source = Constants.CITY_PATH;
                city_source = Constants.TOTAL_CITY;
                shopCount = et_shop_times.getText().toString();
                if (shopCount.equals("1")) {
                    beforeShopInfo = "";
                } else {
                    beforeShopInfo = et_shop_situation.getText().toString();
                }
                lecturer = et_teacher_requirement.getText().toString();
                lecturerContent = et_teacher_theme.getText().toString();
                lecturerProduct = et_main_product.getText().toString();
                teamInfo = et_group_analyz.getText().toString();
                pandaInfo = et_features.getText().toString();
                if (list_urls.size() != 0) {
                    pictures = getJudge(list_urls);
                    strs = pictures.substring(0, pictures.length() - 1);
                }
                String sign = TGmd5.getMD5(distributorid + reportId + reportSellerId + travel_name + tourName + mobile + plate + peopleType + peopleCount1 + peopleCount2 + reachType + reachTime1 + reachTime2 + source + city_source + shopCount + beforeShopInfo +
                        lecturer + lecturerContent + lecturerProduct + teamInfo + pandaInfo + strs);
                btn_login.setEnabled(false);
                doReportShop(distributorid, reportId, reportSellerId, travel_name, tourName, mobile, plate, peopleType, peopleCount1, peopleCount2, reachType, reachTime1, reachTime2, source, city_source, shopCount, beforeShopInfo,
                        lecturer, lecturerContent, lecturerProduct, teamInfo, pandaInfo, strs, sign);
                break;
            case R.id.ll_sure_time:
                openSelectTime("1");
                break;
            case R.id.ll_not_sure_start_time:
                openSelectTime("2");
                break;
            case R.id.ll_not_sure_end_time:
                openSelectTime("3");
                break;
            case R.id.tv_select_gallery:
                closeDialog();
//                FunctionUtils.chooseImageFromGallery(ReportShopActivity.this, REQUEST_CODE_GALLERY);
                // 权限管理工具类
                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                    @Override
                    public void permissionGranted(int requestCode) {
                        if (requestCode == 99) {
                            PhotoPickerIntent intent = new PhotoPickerIntent(ReportShopActivity.this);
                            intent.setPhotoCount(1);
                            intent.setShowCamera(true);
                            intent.setShowGif(true);
                            startActivityForResult(intent, 101);
                        }
                    }
                });
                //申请读权限，和照相机权限
                permissionManager.checkPermission(99, ReportShopActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);

                break;
            case R.id.tv_select_camera:
                closeDialog();
                imagePath = FunctionUtils.chooseImageFromCamera(ReportShopActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
                break;
            case R.id.tv_cancel:
                closeDialog();
                break;
            case R.id.rl_address:
                openActivity(BBSelectProviceActivity.class);
                break;
            case R.id.tv_shili:
                showImage();
                break;
            case R.id.rl_teacher_icon:
                callPhone(call_phone, user_name);
                break;
            case R.id.btn_cancle:
                cancleReportDialog();
                break;
            case R.id.btn_login_one:
                String travel_name_one = et_travel_name.getText().toString();
                String tourName_one = et_guider_name.getText().toString();
                String phone_one = et_phone.getText().toString();
                String car_num_one = et_car_num.getText().toString();
                String size_num_one = et_by_num.getText().toString();
                String adult_num_one = et_by_adult.getText().toString();
                String child_num_one = et_by_child.getText().toString();
                String boy_num_one = et_by_boy.getText().toString();
                String girl_num_one = et_by_girl.getText().toString();
                String sure_time_one = tv_text_sure_time.getText().toString();
                String start_tiem_one = tv_not_sure_start_time.getText().toString();
                String end_time_one = tv_not_sure_end_time.getText().toString();
                String shop_times_one = et_shop_times.getText().toString();
                String serious_one = et_shop_situation.getText().toString();
                String requement_one = et_teacher_requirement.getText().toString();
                String theme_one = et_teacher_theme.getText().toString();
                String product_one = et_main_product.getText().toString();
                String analy_one = et_group_analyz.getText().toString();
                String panan_one = et_features.getText().toString();
                String city_one = Constants.CITY_PATH;
                Date start_date_one;
                Date end_date_one;
                if (travel_name_one.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入旅行社名称");
                    et_travel_name.requestFocus();
                    return;
                }
                if (tourName_one.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入导游姓名");
                    return;
                }
                if (phone_one.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入手机号");
                    return;
                } else if (!StringUtils.isPhone(phone_one)) {
                    MyToast.show(ReportShopActivity.this, "手机号不合法");
                    return;
                }
                if (car_num_one.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入车牌号");
                    et_car_num.requestFocus();
                    return;
                }
                if (peopleType.equals("1")) {
                    if (size_num_one.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入人数");
                        et_by_num.requestFocus();
                        return;
                    }
                } else if (peopleType.equals("2")) {
                    if (adult_num_one.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入成人人数");
                        et_by_adult.requestFocus();
                        return;
                    }
                    if (child_num_one.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入孩童人数");
                        et_by_child.requestFocus();
                        return;
                    }
                } else if (peopleType.equals("3")) {
                    if (boy_num_one.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入男生人数");
                        et_by_boy.requestFocus();
                        return;
                    }
                    if (girl_num_one.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入女生人数");
                        et_by_girl.requestFocus();
                        return;
                    }
                }

                if (reachType.equals("1")) {
                    if (sure_time_one.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请选择时间");
                        return;
                    }
                } else if (reachType.equals("2")) {
                    if (start_tiem_one.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请选择起始时间");
                        return;
                    }
                    if (end_time_one.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请选择结束时间");
                        return;
                    }
                }
                if (reachType.equals("1")) {
                    if (tv_text_sure_time.getText().toString().substring(0, 2).equals("今天")) {
                        reachTime1 = new CalendarUtils().getNowTime("yyyy-MM-dd") + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                        reachTime2 = new CalendarUtils().getNowTime("yyyy-MM-dd") + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                    } else if (tv_text_sure_time.getText().toString().substring(0, 2).equals("明天")) {
                        reachTime1 = new CalendarUtils().getBeforeOrAfterDay(1) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                        reachTime2 = new CalendarUtils().getBeforeOrAfterDay(1) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                    } else if (tv_text_sure_time.getText().toString().substring(0, 2).equals("后天")) {
                        reachTime1 = new CalendarUtils().getBeforeOrAfterDay(2) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                        reachTime2 = new CalendarUtils().getBeforeOrAfterDay(2) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                    } else {
                        reachTime1 = new CalendarUtils().getNowTime("yyyy") + "-" + tv_text_sure_time.getText().toString().split(" ")[0].substring(0, 2) + "-" + tv_text_sure_time.getText().toString().split(" ")[0].substring(3, 5) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                        reachTime2 = new CalendarUtils().getNowTime("yyyy") + "-" + tv_text_sure_time.getText().toString().split(" ")[0].substring(0, 2) + "-" + tv_text_sure_time.getText().toString().split(" ")[0].substring(3, 5) + " " + tv_text_sure_time.getText().toString().split(" ")[1];
                    }
                } else if (reachType.equals("2")) {
                    if (tv_not_sure_start_time.getText().toString().substring(0, 2).equals("今天")) {
                        reachTime1 = new CalendarUtils().getNowTime("yyyy-MM-dd") + " " + tv_not_sure_start_time.getText().toString().split(" ")[1];
                    } else if (tv_not_sure_start_time.getText().toString().substring(0, 2).equals("明天")) {
                        reachTime1 = new CalendarUtils().getBeforeOrAfterDay(1) + " " + tv_not_sure_start_time.getText().toString().split(" ")[1];
                    } else if (tv_not_sure_start_time.getText().toString().substring(0, 2).equals("后天")) {
                        reachTime1 = new CalendarUtils().getBeforeOrAfterDay(2) + " " + tv_not_sure_start_time.getText().toString().split(" ")[1];
                    } else {
                        reachTime1 = new CalendarUtils().getNowTime("yyyy") + "-" + tv_not_sure_start_time.getText().toString().split(" ")[0].substring(0, 2) + "-" + tv_not_sure_start_time.getText().toString().split(" ")[0].substring(3, 5) + " " + tv_not_sure_start_time.getText().toString().split(" ")[1];
                    }
                    if (tv_not_sure_end_time.getText().toString().substring(0, 2).equals("今天")) {
                        reachTime2 = new CalendarUtils().getNowTime("yyyy-MM-dd") + " " + tv_not_sure_end_time.getText().toString().split(" ")[1];
                    } else if (tv_not_sure_end_time.getText().toString().substring(0, 2).equals("明天")) {
                        reachTime2 = new CalendarUtils().getBeforeOrAfterDay(1) + " " + tv_not_sure_end_time.getText().toString().split(" ")[1];
                    } else if (tv_not_sure_end_time.getText().toString().substring(0, 2).equals("后天")) {
                        reachTime2 = new CalendarUtils().getBeforeOrAfterDay(2) + " " + tv_not_sure_end_time.getText().toString().split(" ")[1];
                    } else {
                        reachTime2 = new CalendarUtils().getNowTime("yyyy") + "-" + tv_not_sure_end_time.getText().toString().split(" ")[0].substring(0, 2) + "-" + tv_not_sure_end_time.getText().toString().split(" ")[0].substring(3, 5) + " " + tv_not_sure_end_time.getText().toString().split(" ")[1];
                    }
                }

                SimpleDateFormat dfs_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                if (reachType.equals("1")) {
                    try {
                        start_date_one = dfs_2.parse(reachTime1);
                        end_date_one = dfs_2.parse(dfs_2.format(new Date()));
                        if ((start_date_one.getTime() - end_date_one.getTime()) / 1000 < 0) {
                            MyToast.show(ReportShopActivity.this, "到店结束时间不可小于当前时间");
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if (reachType.equals("2")) {
                    try {
                        start_date_one = dfs_2.parse(reachTime1);
                        end_date_one = dfs_2.parse(reachTime2);
                        if ((start_date_one.getTime() - end_date_one.getTime()) / 1000 > 0) {
                            MyToast.show(ReportShopActivity.this, "到店结束时间不可大于到店开始时间");
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (city_one.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请选择客源地");
                    return;
                }
                if (shop_times_one.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请选择店铺");
                    return;
                }
                if (!shop_times_one.equals("1")) {
                    if (serious_one.length() == 0) {
                        MyToast.show(ReportShopActivity.this, "请输入前几店情况");
                        et_shop_situation.requestFocus();
                        return;
                    }
                }
                if (requement_one.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入讲师要求");
                    et_teacher_requirement.requestFocus();
                    return;
                }
                if (theme_one.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入主讲概念");
                    et_teacher_theme.requestFocus();
                    return;
                }
                if (product_one.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入主推产品");
                    et_main_product.requestFocus();
                    return;
                }
                if (analy_one.length() == 0) {
                    MyToast.show(ReportShopActivity.this, "请输入团队分析");
                    et_group_analyz.requestFocus();
                    return;
                }

                travel_name_one = et_travel_name.getText().toString();
                tourName_one = et_guider_name.getText().toString();
                mobile = et_phone.getText().toString();
                plate = et_car_num.getText().toString();
                if (peopleType.equals("1")) {
                    peopleCount1 = et_by_num.getText().toString();
                    peopleCount2 = "0";
                } else if (peopleType.equals("2")) {
                    peopleCount1 = et_by_adult.getText().toString();
                    peopleCount2 = et_by_child.getText().toString();
                } else if (peopleType.equals("3")) {
                    peopleCount1 = et_by_boy.getText().toString();
                    peopleCount2 = et_by_girl.getText().toString();
                }

                source = Constants.CITY_PATH;
                city_source = Constants.TOTAL_CITY;
                shopCount = et_shop_times.getText().toString();
                if (shopCount.equals("1")) {
                    beforeShopInfo = "";
                } else {
                    beforeShopInfo = et_shop_situation.getText().toString();
                }
                lecturer = et_teacher_requirement.getText().toString();
                lecturerContent = et_teacher_theme.getText().toString();
                lecturerProduct = et_main_product.getText().toString();
                teamInfo = et_group_analyz.getText().toString();
                pandaInfo = et_features.getText().toString();
                if (list_urls.size() != 0) {
                    pictures = getJudge(list_urls);
                    strs = pictures.substring(0, pictures.length() - 1);
                }
                String sign_2 = TGmd5.getMD5(distributorid + reportId + reportSellerId + travel_name_one + tourName_one + mobile + plate + peopleType + peopleCount1 + peopleCount2 + reachType + reachTime1 + reachTime2 + source + city_source + shopCount + beforeShopInfo +
                        lecturer + lecturerContent + lecturerProduct + teamInfo + pandaInfo + strs);
                btn_login_one.setEnabled(false);
                doReportShop(distributorid, reportId, reportSellerId, travel_name_one, tourName_one, mobile, plate, peopleType, peopleCount1, peopleCount2, reachType, reachTime1, reachTime2, source, city_source, shopCount, beforeShopInfo,
                        lecturer, lecturerContent, lecturerProduct, teamInfo, pandaInfo, strs, sign_2);
                break;
            case R.id.btn_cancle_one:
                MyToast.show(ReportShopActivity.this, "已取消");
                break;
        }
    }

    public void initTimeData() {

        shop_data.add("1");
        shop_data.add("2");
        shop_data.add("3");
        shop_data.add("4");
        shop_data.add("5");
        shop_data.add("6");
        shop_data.add("7");
        shop_data.add("8");
        shop_data.add("9");


        week_data.add("今天");
        week_data.add("明天");
        week_data.add("后天");
        week_data.add(new CalendarUtils().getBeforeOrAfterDay(3));
        week_data.add(new CalendarUtils().getBeforeOrAfterDay(4));
        week_data.add(new CalendarUtils().getBeforeOrAfterDay(5));
        week_data.add(new CalendarUtils().getBeforeOrAfterDay(6));
        week_data.add(new CalendarUtils().getBeforeOrAfterDay(7));
        week_data.add(new CalendarUtils().getBeforeOrAfterDay(8));
        week_data.add(new CalendarUtils().getBeforeOrAfterDay(9));
        week_data.add(new CalendarUtils().getBeforeOrAfterDay(10));

        hour_data.add("0时");
        hour_data.add("1时");
        hour_data.add("2时");
        hour_data.add("3时");
        hour_data.add("4时");
        hour_data.add("5时");
        hour_data.add("6时");
        hour_data.add("7时");
        hour_data.add("8时");
        hour_data.add("9时");
        hour_data.add("10时");
        hour_data.add("11时");
        hour_data.add("12时");
        hour_data.add("13时");
        hour_data.add("14时");
        hour_data.add("15时");
        hour_data.add("16时");
        hour_data.add("17时");
        hour_data.add("18时");
        hour_data.add("19时");
        hour_data.add("20时");
        hour_data.add("21时");
        hour_data.add("22时");
        hour_data.add("23时");

        minute_data.add("00分");
        minute_data.add("05分");
        minute_data.add("10分");
        minute_data.add("15分");
        minute_data.add("20分");
        minute_data.add("25分");
        minute_data.add("30分");
        minute_data.add("35分");
        minute_data.add("40分");
        minute_data.add("45分");
        minute_data.add("50分");
        minute_data.add("55分");
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.TOTAL_CITY.length() == 0) {
            tv_address.setText("请选择城市");
            tv_address.setTextColor(getResources().getColor(R.color.bg_default_gray));
        } else {
            tv_address.setText(Constants.TOTAL_CITY);
            tv_address.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(ReportShopActivity.this);
    }

    public String subTime(String str) {
        String time = "";
        if (str.length() > 2) {
            time = str.substring(0, 2);
        } else {
            time = str.substring(0, 1);
        }
        return time;
    }


    public void initGridView() {
        pathImageAdapter = new GridViewPathImageAdapter(ReportShopActivity.this, list_urls);
        gradeView.setAdapter(pathImageAdapter);
        pathImageAdapter.setList(list_urls);

        gradeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == getDataSize() && size <= 4) {// 点击“+”号位置添加图片
                    openDialog();
                } else if (size > 4) {
                    MyToast.show(ReportShopActivity.this, "最多上传4张图片");
                    return;
                } else {// 点击图片删除
                    Bundle bundle = new Bundle();
                    bundle.putString("image_urls", list_urls.toString());
                    bundle.putString("image_index", position + "");
                    openActivity(ImageReportActivity.class, bundle);
                }
            }
        });
    }

    //获取 size
    private int getDataSize() {
        return list_urls == null ? 0 : list_urls.size();
    }

    public void openSelextShopDialog() {
        dialog_shop = new Dialog(ReportShopActivity.this,
                R.style.style_custom_dialog);
        View view_sex = View.inflate(ReportShopActivity.this,
                R.layout.dialog_select_sex, null);
        wheel_shop = (WheelView) view_sex.findViewById(R.id.id_week);
        TextView tv_done_sex = (TextView) view_sex
                .findViewById(R.id.tv_done_sex);
        TextView tv_cancle_sex = (TextView) view_sex
                .findViewById(R.id.tv_cancle_sex);
        wheel_shop.setViewAdapter(new ArrayWheelAdapter(ReportShopActivity.this, shop_data));
        // 设置可见条目数量
        wheel_shop.setVisibleItems(7);
        wheel_shop.setCurrentItem(0);
        wheel_shop.addChangingListener(this);
        tv_done_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_shop.dismiss();
                if (isWheelChangeShop == true) {
                    et_shop_times.setText(shop_times_wheel);
                    if (shop_times_wheel.equals("1")) {
                        tv_one.setVisibility(View.GONE);
                        rl_one.setVisibility(View.GONE);
                    } else {
                        tv_one.setVisibility(View.VISIBLE);
                        rl_one.setVisibility(View.VISIBLE);
                    }
                } else {
                    et_shop_times.setText("1");
                    if (shop_times_wheel.equals("1")) {
                        tv_one.setVisibility(View.GONE);
                        rl_one.setVisibility(View.GONE);
                    } else {
                        tv_one.setVisibility(View.VISIBLE);
                        rl_one.setVisibility(View.VISIBLE);
                    }
                }
                isWheelChangeShop = false;
            }
        });
        tv_cancle_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_shop.dismiss();
                isWheelChangeShop = false;
            }
        });
        dialog_shop.setContentView(view_sex);
        dialog_shop.show();

        WindowManager m2 = getWindowManager();
        Display d2 = m2.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p2 = dialog_shop
                .getWindow().getAttributes(); // 获取对话框当前的参数值
        p2.width = (int) (d2.getWidth()); // 宽度设置为全屏
        dialog_shop.getWindow().setAttributes(p2); // 设置生效
    }

    public void openSelectTime(final String type) {
        dialog_time = new Dialog(ReportShopActivity.this,
                R.style.style_custom_dialog);
        View view_sex = View.inflate(ReportShopActivity.this,
                R.layout.dialog_select_time, null);
        wheel_date = (WheelView) view_sex.findViewById(R.id.id_date);
        wheel_hour = (WheelView) view_sex.findViewById(R.id.id_hour);
        wheel_minute = (WheelView) view_sex.findViewById(R.id.id_minute);
        TextView tv_done_sex = (TextView) view_sex
                .findViewById(R.id.tv_done_sex);
        TextView tv_cancle_sex = (TextView) view_sex
                .findViewById(R.id.tv_cancle_sex);
        wheel_date.setViewAdapter(new ArrayWheelAdapter(ReportShopActivity.this, week_data));
        wheel_hour.setViewAdapter(new ArrayWheelAdapter(ReportShopActivity.this, hour_data));
        wheel_minute.setViewAdapter(new ArrayWheelAdapter(ReportShopActivity.this, minute_data));
        // 设置可见条目数量
        wheel_date.setVisibleItems(7);
        wheel_date.setCurrentItem(0);
        wheel_date.addChangingListener(this);
        // 设置可见条目数量
        wheel_hour.setVisibleItems(7);
        wheel_hour.setCurrentItem(0);
        wheel_hour.addChangingListener(this);
        // 设置可见条目数量
        wheel_minute.setVisibleItems(7);
        wheel_minute.setCurrentItem(0);
        wheel_minute.addChangingListener(this);
        tv_cancle_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_time.dismiss();
                isShowSelectTime = false;
            }
        });
        tv_done_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_time.dismiss();
                isShowSelectTime = false;
                if (isWheelChangeDate == true && isWheelChangeHour == true && isWheelChangeMinute == true) {
                    if (type.equals("1")) {
                        tv_text_sure_time.setVisibility(View.VISIBLE);
                        et_sure_time.setVisibility(View.GONE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_text_sure_time.setText("今天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_text_sure_time.setText("明天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_text_sure_time.setText("后天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        } else {
                            tv_text_sure_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        }
                    } else if (type.equals("2")) {
                        et_not_sure_start_time.setVisibility(View.GONE);
                        tv_not_sure_start_time.setVisibility(View.VISIBLE);
                        tv_not_sure_end_time.setVisibility(View.GONE);
                        et_not_sure_end_time.setVisibility(View.VISIBLE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_not_sure_start_time.setText("今天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_not_sure_start_time.setText("明天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_not_sure_start_time.setText("后天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        } else {
                            tv_not_sure_start_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        }
                    } else if (type.equals("3")) {
                        et_not_sure_end_time.setVisibility(View.GONE);
                        tv_not_sure_end_time.setVisibility(View.VISIBLE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_not_sure_end_time.setText("今天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_not_sure_end_time.setText("明天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_not_sure_end_time.setText("后天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        } else {
                            tv_not_sure_end_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                        }
                    }
                } else if (isWheelChangeDate == false && isWheelChangeHour == false && isWheelChangeMinute == false) {
                    if (type.equals("1")) {
                        tv_text_sure_time.setVisibility(View.VISIBLE);
                        et_sure_time.setVisibility(View.GONE);
                        tv_text_sure_time.setText("今天 0:00");
                    } else if (type.equals("2")) {
                        et_not_sure_start_time.setVisibility(View.GONE);
                        tv_not_sure_start_time.setVisibility(View.VISIBLE);
                        tv_not_sure_start_time.setText("今天 0:00");
                        tv_not_sure_end_time.setVisibility(View.GONE);
                        et_not_sure_end_time.setVisibility(View.VISIBLE);
                    } else if (type.equals("3")) {
                        et_not_sure_end_time.setVisibility(View.GONE);
                        tv_not_sure_end_time.setVisibility(View.VISIBLE);
                        tv_not_sure_end_time.setText("今天 0:00");
                    }
                } else if (isWheelChangeDate == true && isWheelChangeHour == false && isWheelChangeMinute == false) {
                    if (type.equals("1")) {
                        tv_text_sure_time.setVisibility(View.VISIBLE);
                        et_sure_time.setVisibility(View.GONE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_text_sure_time.setText("今天 0:00");
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_text_sure_time.setText("明天 0:00");
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_text_sure_time.setText("后天 0:00");
                        } else {
                            tv_text_sure_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 0:00");
                        }
                    } else if (type.equals("2")) {
                        et_not_sure_start_time.setVisibility(View.GONE);
                        tv_not_sure_start_time.setVisibility(View.VISIBLE);
                        tv_not_sure_end_time.setVisibility(View.GONE);
                        et_not_sure_end_time.setVisibility(View.VISIBLE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_not_sure_start_time.setText("今天 0:00");
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_not_sure_start_time.setText("明天 0:00");
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_not_sure_start_time.setText("后天 0:00");
                        } else {
                            tv_not_sure_start_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 0:00");
                        }
                    } else if (type.equals("3")) {
                        et_not_sure_end_time.setVisibility(View.GONE);
                        tv_not_sure_end_time.setVisibility(View.VISIBLE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_not_sure_end_time.setText("今天 0:00");
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_not_sure_end_time.setText("明天 0:00");
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_not_sure_end_time.setText("后天 0:00");
                        } else {
                            tv_not_sure_end_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 0:00");
                        }
                    }
                } else if (isWheelChangeDate == true && isWheelChangeHour == true && isWheelChangeMinute == false) {
                    if (type.equals("1")) {
                        tv_text_sure_time.setVisibility(View.VISIBLE);
                        et_sure_time.setVisibility(View.GONE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_text_sure_time.setText("今天 " + subTime(shop_hour) + ":00");
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_text_sure_time.setText("明天 " + subTime(shop_hour) + ":00");
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_text_sure_time.setText("后天 " + subTime(shop_hour) + ":00");
                        } else {
                            tv_text_sure_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 " + subTime(shop_hour) + ":00");
                        }
                    } else if (type.equals("2")) {
                        et_not_sure_start_time.setVisibility(View.VISIBLE);
                        tv_not_sure_end_time.setVisibility(View.GONE);
                        et_not_sure_end_time.setVisibility(View.VISIBLE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_not_sure_start_time.setText("今天 " + subTime(shop_hour) + ":00");
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_not_sure_start_time.setText("明天 " + subTime(shop_hour) + ":00");
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_not_sure_start_time.setText("后天 " + subTime(shop_hour) + ":00");
                        } else {
                            tv_not_sure_start_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 " + subTime(shop_hour) + ":00");
                        }
                    } else if (type.equals("3")) {
                        et_not_sure_end_time.setVisibility(View.GONE);
                        tv_not_sure_end_time.setVisibility(View.VISIBLE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_not_sure_end_time.setText("今天 " + subTime(shop_hour) + ":00");
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_not_sure_end_time.setText("明天 " + subTime(shop_hour) + ":00");
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_not_sure_end_time.setText("后天 " + subTime(shop_hour) + ":00");
                        } else {
                            tv_not_sure_end_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 " + subTime(shop_hour) + ":00");
                        }
                    }
                } else if (isWheelChangeDate == true && isWheelChangeHour == false && isWheelChangeMinute == true) {
                    if (type.equals("1")) {
                        tv_text_sure_time.setVisibility(View.VISIBLE);
                        et_sure_time.setVisibility(View.GONE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_text_sure_time.setText("今天 0:" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_text_sure_time.setText("明天 0:" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_text_sure_time.setText("后天 0:" + shop_minute.substring(0, 2));
                        } else {
                            tv_text_sure_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 0:" + shop_minute.substring(0, 2));
                        }
                    } else if (type.equals("2")) {
                        et_not_sure_start_time.setVisibility(View.GONE);
                        tv_not_sure_start_time.setVisibility(View.VISIBLE);
                        tv_not_sure_end_time.setVisibility(View.GONE);
                        et_not_sure_end_time.setVisibility(View.VISIBLE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_not_sure_start_time.setText("今天 0:" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_not_sure_start_time.setText("明天 0:" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_not_sure_start_time.setText("后天 0:" + shop_minute.substring(0, 2));
                        } else {
                            tv_not_sure_start_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 0:" + shop_minute.substring(0, 2));
                        }
                    } else if (type.equals("3")) {
                        et_not_sure_end_time.setVisibility(View.GONE);
                        tv_not_sure_end_time.setVisibility(View.VISIBLE);
                        if (shop_date.substring(0, 2).equals("今天")) {
                            tv_not_sure_end_time.setText("今天 0:" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("明天")) {
                            tv_not_sure_end_time.setText("明天 0:" + shop_minute.substring(0, 2));
                        } else if (shop_date.substring(0, 2).equals("后天")) {
                            tv_not_sure_end_time.setText("后天 0:" + shop_minute.substring(0, 2));
                        } else {
                            tv_not_sure_end_time.setText(shop_date.substring(5, 7) + "月" + shop_date.substring(8, 10) + "日 0:" + shop_minute.substring(0, 2));
                        }
                    }
                } else if (isWheelChangeDate == false && isWheelChangeHour == true && isWheelChangeMinute == false) {
                    if (type.equals("1")) {
                        tv_text_sure_time.setVisibility(View.VISIBLE);
                        et_sure_time.setVisibility(View.GONE);
                        tv_text_sure_time.setText("今天 " + subTime(shop_hour) + ":00");
                    } else if (type.equals("2")) {
                        et_not_sure_start_time.setVisibility(View.GONE);
                        tv_not_sure_start_time.setVisibility(View.VISIBLE);
                        tv_not_sure_end_time.setVisibility(View.GONE);
                        et_not_sure_end_time.setVisibility(View.VISIBLE);
                        tv_not_sure_start_time.setText("今天 " + subTime(shop_hour) + ":00");
                    } else if (type.equals("3")) {
                        et_not_sure_end_time.setVisibility(View.GONE);
                        tv_not_sure_end_time.setVisibility(View.VISIBLE);
                        tv_not_sure_end_time.setText("今天 " + subTime(shop_hour) + ":00");
                    }
                } else if (isWheelChangeDate == false && isWheelChangeHour == true && isWheelChangeMinute == true) {
                    if (type.equals("1")) {
                        et_sure_time.setVisibility(View.GONE);
                        tv_text_sure_time.setVisibility(View.VISIBLE);
                        tv_text_sure_time.setText("今天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                    } else if (type.equals("2")) {
                        et_not_sure_start_time.setVisibility(View.GONE);
                        tv_not_sure_start_time.setVisibility(View.VISIBLE);
                        tv_not_sure_end_time.setVisibility(View.GONE);
                        et_not_sure_end_time.setVisibility(View.VISIBLE);
                        tv_not_sure_start_time.setText("今天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                    } else if (type.equals("3")) {
                        et_not_sure_end_time.setVisibility(View.GONE);
                        tv_not_sure_end_time.setVisibility(View.VISIBLE);
                        tv_not_sure_end_time.setText("今天 " + subTime(shop_hour) + ":" + shop_minute.substring(0, 2));
                    }
                } else if (isWheelChangeDate == false && isWheelChangeHour == false && isWheelChangeMinute == true) {
                    if (type.equals("1")) {
                        et_sure_time.setVisibility(View.GONE);
                        tv_text_sure_time.setVisibility(View.VISIBLE);
                        tv_text_sure_time.setText("今天 " + "0:" + shop_minute.substring(0, 2));
                    } else if (type.equals("2")) {
                        et_not_sure_start_time.setVisibility(View.GONE);
                        tv_not_sure_start_time.setVisibility(View.VISIBLE);
                        tv_not_sure_end_time.setVisibility(View.GONE);
                        et_not_sure_end_time.setVisibility(View.VISIBLE);
                        tv_not_sure_start_time.setText("今天 " + "0:" + shop_minute.substring(0, 2));
                    } else if (type.equals("3")) {
                        et_not_sure_end_time.setVisibility(View.GONE);
                        tv_not_sure_end_time.setVisibility(View.VISIBLE);
                        tv_not_sure_end_time.setText("今天 " + "0:" + shop_minute.substring(0, 2));
                    }
                }
                isWheelChangeDate = false;
                isWheelChangeHour = false;
                isWheelChangeMinute = false;
            }
        });
        dialog_time.setContentView(view_sex);
        dialog_time.show();
        isShowSelectTime = true;
        WindowManager m2 = getWindowManager();
        Display d2 = m2.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p2 = dialog_time
                .getWindow().getAttributes(); // 获取对话框当前的参数值
        p2.width = (int) (d2.getWidth()); // 宽度设置为全屏
        dialog_time.getWindow().setAttributes(p2); // 设置生效

    }

    public void initEditText() {

        et_shop_situation.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_shop_situation.setSingleLine(false);
        //水平滚动设置为False
        et_shop_situation.setHorizontallyScrolling(false);
        et_shop_situation.setHint("例: 珍珠店消费8000元, 茶叶店1000元, 丝绸店12000");

        et_teacher_requirement.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        et_teacher_requirement.setSingleLine(false);
        et_teacher_requirement.setHorizontallyScrolling(false);
        et_teacher_requirement.setHint("例: 团中男士居多, 请安排一个美女讲解员, 多点风趣");

        et_teacher_theme.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        et_teacher_theme.setSingleLine(false);
        et_teacher_theme.setHorizontallyScrolling(false);
        et_teacher_theme.setHint("例: 树立博物馆品牌形象, 提倡健康");

        et_main_product.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        et_main_product.setSingleLine(false);
        et_main_product.setHorizontallyScrolling(false);
        et_main_product.setHint("例: 被子, 丝绸, 旗袍");

        et_group_analyz.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        et_group_analyz.setSingleLine(false);
        et_group_analyz.setHorizontallyScrolling(false);
        et_group_analyz.setHint("例: 5个家庭, 两队情侣, 一个单身狗, 2个熊猫");

        et_features.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        et_features.setSingleLine(false);
        et_features.setHorizontallyScrolling(false);
        et_features.setHint("例: 其中脖子带珍珠和手带玉镯就是");

        et_shop_situation.setOnTouchListener(this);
    }

    public void initRadioGroup() {
        mGroup_people_num.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_num:
                        peopleType = "1";
                        initNumLayout();
                        ll_num.setVisibility(View.VISIBLE);
                        mRadio_num.setChecked(true);
                        img_num.setVisibility(View.VISIBLE);
                        view_adult.setVisibility(View.GONE);
                        view_boy.setVisibility(View.GONE);
                        view_num.setVisibility(View.GONE);
                        break;
                    case R.id.radio_size:
                        peopleType = "2";
                        initNumLayout();
                        ll_adult.setVisibility(View.VISIBLE);
                        ll_child.setVisibility(View.VISIBLE);
                        mRadio_size.setChecked(true);
                        img_size.setVisibility(View.VISIBLE);
                        view_adult.setVisibility(View.VISIBLE);
                        view_num.setVisibility(View.GONE);
                        view_boy.setVisibility(View.GONE);
                        break;
                    case R.id.radio_female:
                        peopleType = "3";
                        initNumLayout();
                        ll_male.setVisibility(View.VISIBLE);
                        ll_femal.setVisibility(View.VISIBLE);
                        mRadio_sex.setChecked(true);
                        img_sex.setVisibility(View.VISIBLE);
                        view_boy.setVisibility(View.VISIBLE);
                        view_num.setVisibility(View.GONE);
                        view_adult.setVisibility(View.GONE);
                        break;
                }
            }
        });
        mGroupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_sure_time:
                        reachType = "1";
                        initTimeLayout();
                        ll_sure_time.setVisibility(View.VISIBLE);
                        radio_sure_time.setChecked(true);
                        img_sure_time.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radio_not_sure_time:
                        reachType = "2";
                        initTimeLayout();
                        ll_not_sure_start_time.setVisibility(View.VISIBLE);
                        ll_not_sure_end_time.setVisibility(View.VISIBLE);
                        radio_not_sure_time.setChecked(true);
                        img_not_sure_time.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
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
                   /* imagePath = FunctionUtils
                            .onActivityResultForChooseImageFromGallery(ReportShopActivity.this,
                                    requestCode, resultCode, data);
                    if (!StringUtils.isEmpty(imagePath)) {
                        *//**
                         * 需要修改，路径为空，默认图片记得添加
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
                        /**
                         * 需要修改，路径为空，默认图片记得添加
                         */
                        compressWithRx(new File(imagePath));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void initNumLayout() {
        ll_num.setVisibility(View.GONE);
        ll_adult.setVisibility(View.GONE);
        ll_child.setVisibility(View.GONE);
        ll_male.setVisibility(View.GONE);
        ll_femal.setVisibility(View.GONE);
        img_num.setVisibility(View.GONE);
        img_sex.setVisibility(View.GONE);
        img_size.setVisibility(View.GONE);
    }

    public void initTimeLayout() {
        ll_sure_time.setVisibility(View.GONE);
        ll_not_sure_start_time.setVisibility(View.GONE);
        ll_not_sure_end_time.setVisibility(View.GONE);
        img_sure_time.setVisibility(View.GONE);
        img_not_sure_time.setVisibility(View.GONE);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == wheel_shop) {
            isWheelChangeShop = true;
            int pCurrent = wheel_shop.getCurrentItem();
            shop_times_wheel = shop_data.get(pCurrent);
        } else if (wheel == wheel_date) {
            int pCurrent = wheel_date.getCurrentItem();
            isWheelChangeDate = true;
            shop_date = week_data.get(pCurrent);
        } else if (wheel == wheel_hour) {
            int pCurren = wheel_hour.getCurrentItem();
            shop_hour = hour_data.get(pCurren);
            isWheelChangeHour = true;
        } else if (wheel == wheel_minute) {
            int pCurrent = wheel_minute.getCurrentItem();
            shop_minute = minute_data.get(pCurrent);
            isWheelChangeMinute = true;
        }
    }

    // <param name="distributorid">导游Id</param>
    // <param name="reportId">报备Id 新增传0 编辑传对应的Id</param>
    // <param name="reportSellerId">报备商Id</param>
    // <param name="travel">旅行社名称</param>
    // <param name="tourName">导游名称</param>
    // <param name="mobile">导游手机</param>
    // <param name="plate">车牌</param>
    // <param name="peopleType">人数类型：1=按人数，2=按大小，3=按男女</param>
    // <param name="peopleCount1">peopleType=1 保存人数；peopleType=2 保存大人人数；peopleType=3 保存男人数</param>
    // <param name="peopleCount2">peopleType=1 =0；peopleType=2 保存孩童人数；peopleType=3 保存女人数</param>
    // <param name="reachType">到达类型：1=固定时间，2=不确定时间</param>
    // <param name="reachTime1">reachType=1 保存到店时间；reachType=2 保存到店开始时间 时间格式（yyyy-MM-dd HH:mm）</param>
    // <param name="reachTime2">reachType=1 =""；reachType=2 保存到店结束时间</param>
    // <param name="source">客源地</param>（保存的是城市节点）
    // <param name="shopCount">第几个店</param>
    // <param name="beforeShopInfo">前几店情况</param>
    // <param name="lecturer">讲师要求</param>
    // <param name="lecturerContent">主讲概念</param>
    // <param name="lecturerProduct">主推产品</param>// <param name="teamInfo">团队分析</param>
    // <param name="pandaInfo">熊猫特征</param>
    // <param name="pictures">照片集合 多个照片用|分割</param>

    public void doReportShop(String distributorid, String reportId, String reportSellerId, String travel, String tourName
            , String mobile, String plate, String peopleType, String peopleCount1, String peopleCount2, String reachType, String reachTime1, String reachTime2,
                             String source, String address, String shopCount, String beforeShopInfo, String lecturer, String lecturerContent, String lecturerProduct, String teamInfo, String pandaInfo, String pictures, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("reportId", reportId);
        maps.put("reportSellerId", reportSellerId);
        maps.put("travel", travel);
        maps.put("tourName", tourName);
        maps.put("mobile", mobile);
        maps.put("plate", plate);
        maps.put("peopleType", peopleType);
        maps.put("peopleCount1", peopleCount1);
        maps.put("peopleCount2", peopleCount2);
        maps.put("reachType", reachType);
        maps.put("reachTime1", reachTime1);
        maps.put("reachTime2", reachTime2);
        maps.put("source", source);
        maps.put("address", address);
        maps.put("shopCount", shopCount);
        maps.put("beforeShopInfo", beforeShopInfo);
        maps.put("lecturer", lecturer);
        maps.put("lecturerContent", lecturerContent);
        maps.put("lecturerProduct", lecturerProduct);
        maps.put("teamInfo", teamInfo);
        maps.put("pandaInfo", pandaInfo);
        maps.put("pictures", pictures);
        maps.put("sign", sign);
        RequestTask.getInstance().doReportShop(ReportShopActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equals("1")) {
                    dismissProgressDialog();
                    MyToast.show(ReportShopActivity.this, "成功");
                    PreferenceHelper.write(ReportShopActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TRAVAL_NAME, et_travel_name.getText().toString());
                    btn_login.setEnabled(true);
                    btn_login_one.setEnabled(true);
                    Constants.TOTAL_CITY = "";
                    Constants.CITY_PATH = "";
                    if (index.equals("1")) {
                        finish();
                    } else if (index.equals("0")) {
                        AppManager.getInstance().killTopActivity();
                        AppManager.getInstance().killTopActivity();
                    } else if (index.equals("2")) {
                        AppManager.getInstance().killTopActivity();
                        AppManager.getInstance().killTopActivity();
                        AppManager.getInstance().killTopActivity();
                    }
                } else {
                    dismissProgressDialog();
                    MyToast.show(ReportShopActivity.this, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showProgressDialog("正在提交...");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            dismissProgressDialog();
        }
    }

    /**
     * 获取详情
     *
     * @param distributorid
     * @param id
     * @param sign
     */
    public void getData(String distributorid, String id, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("id", id);
        maps.put("sign", sign);
        RequestTask.getInstance().getReportDetial(ReportShopActivity.this, maps, new OnDetialRequestListener());
    }


    private class OnDetialRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String state = jsonObject.getString("status");
                if (state.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        String item_ = array.get(0).toString();
                        String os_time = array.get(1).toString();
                        JSONObject jsonObject_item = new JSONObject(item_);
                        String shop_name = jsonObject_item.getString("ShopName");
                        String travel = jsonObject_item.getString("Travel");
                        String state_report = jsonObject_item.getString("State");
                        String guder_name = jsonObject_item.getString("TourName");
                        String phone = jsonObject_item.getString("Mobile");
                        String user_id = jsonObject_item.getString("UserID");
                        user_name = jsonObject_item.getString("RealName");
                        call_phone = jsonObject_item.getString("JJYMobile");
                        String car_name = jsonObject_item.getString("Plate");
                        String PeopleType = jsonObject_item.getString("PeopleType");
                        String PeopleCount1 = jsonObject_item.getString("PeopleCount1");
                        String PeopleCount2 = jsonObject_item.getString("PeopleCount2");
                        String ReachType = jsonObject_item.getString("ReachType");
                        String ReachTime1 = jsonObject_item.getString("ReachTime1");
                        String ReachTime2 = jsonObject_item.getString("ReachTime2");
                        String Source = jsonObject_item.getString("Source");
                        String address = jsonObject_item.getString("Adderss");
                        String ShopCount = jsonObject_item.getString("ShopCount");
                        String BeforeShopInfo = jsonObject_item.getString("BeforeShopInfo");
                        String Lecturer = jsonObject_item.getString("Lecturer");
                        String LecturerContent = jsonObject_item.getString("LecturerContent");
                        String LecturerProduct = jsonObject_item.getString("LecturerProduct");
                        String PandaInfo = jsonObject_item.getString("PandaInfo");
                        String TeamInfo = jsonObject_item.getString("TeamInfo");
                        String Pictures = jsonObject_item.getString("Pictures");

                        if (Integer.parseInt(user_id) > 0) {
                            rl_teacher_icon.setVisibility(View.VISIBLE);
                            tv_teacher_name.setText(user_name);
                        }
                        JSONArray jsonArray = new JSONArray(Pictures);
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject_picture = jsonArray.getJSONObject(i);
                                load_path = jsonObject_picture.getString("fileUrl");
                                list_urls.add(load_path);
                                size++;
                            }
                            Message message = new Message();
                            message.what = 1000;
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.what = 1001;
                            handler.sendMessage(message);
                        }
                        tv_shop_name.setText(shop_name);
                        et_travel_name.setText(travel);
                        et_guider_name.setText(guder_name);
                        et_phone.setText(phone);
                        et_car_num.setText(car_name);
                        if (PeopleType.equals("1")) {
                            peopleType = "1";
                            ll_num.setVisibility(View.VISIBLE);
                            mRadio_num.setChecked(true);
                            img_num.setVisibility(View.VISIBLE);
                            et_by_num.setText(PeopleCount1);
                        } else if (PeopleType.equals("2")) {
                            peopleType = "2";
                            ll_adult.setVisibility(View.VISIBLE);
                            ll_child.setVisibility(View.VISIBLE);
                            mRadio_size.setChecked(true);
                            img_size.setVisibility(View.VISIBLE);
                            et_by_adult.setText(PeopleCount1);
                            et_by_child.setText(PeopleCount2);
                        } else if (PeopleType.equals("3")) {
                            peopleType = "3";
                            initNumLayout();
                            ll_male.setVisibility(View.VISIBLE);
                            ll_femal.setVisibility(View.VISIBLE);
                            mRadio_sex.setChecked(true);
                            img_sex.setVisibility(View.VISIBLE);
                            et_by_boy.setText(PeopleCount1);
                            et_by_girl.setText(PeopleCount2);
                        }
                        // 2016-07-05T11:04:00"
                        if (ReachType.equals("1")) {
                            reachType = "1";
                            ll_sure_time.setVisibility(View.VISIBLE);
                            radio_sure_time.setChecked(true);
                            img_sure_time.setVisibility(View.VISIBLE);
                            et_sure_time.setVisibility(View.GONE);
                            tv_text_sure_time.setVisibility(View.VISIBLE);
                            tv_text_sure_time.setText(ReachTime1.substring(5, 7) + "月" + ReachTime1.substring(8, 10) + "日 " + ReachTime1.substring(11, 16));
                        } else if (ReachType.equals("2")) {
                            reachType = "2";
                            ll_not_sure_start_time.setVisibility(View.VISIBLE);
                            ll_not_sure_end_time.setVisibility(View.VISIBLE);
                            radio_not_sure_time.setChecked(true);
                            img_not_sure_time.setVisibility(View.VISIBLE);
                            et_not_sure_end_time.setVisibility(View.GONE);
                            et_not_sure_start_time.setVisibility(View.GONE);
                            tv_not_sure_start_time.setVisibility(View.VISIBLE);
                            tv_not_sure_end_time.setVisibility(View.VISIBLE);
                            tv_not_sure_start_time.setText(ReachTime1.substring(5, 7) + "月" + ReachTime1.substring(8, 10) + "日 " + ReachTime1.substring(11, 16));
                            tv_not_sure_end_time.setText(ReachTime2.substring(5, 7) + "月" + ReachTime2.substring(8, 10) + "日 " + ReachTime2.substring(11, 16));
                        }

                        Constants.TOTAL_CITY = address;
                        tv_address.setText(address);
                        Constants.CITY_PATH = Source;
                        tv_address.setTextColor(getResources().getColor(R.color.baobei_black_text));
                        et_shop_times.setText(ShopCount);
                        if (ShopCount.equals("1")) {
                            tv_one.setVisibility(View.GONE);
                            rl_one.setVisibility(View.GONE);
                        } else {
                            tv_one.setVisibility(View.VISIBLE);
                            rl_one.setVisibility(View.VISIBLE);
                            et_shop_situation.setText(BeforeShopInfo);
                        }
                        et_teacher_requirement.setText(Lecturer);
                        et_teacher_theme.setText(LecturerContent);
                        et_main_product.setText(LecturerProduct);
                        et_group_analyz.setText(TeamInfo);
                        et_features.setText(PandaInfo);

                        /***********根据返回时间与到店时间的判断内容是否可以编辑**********/
                        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String str_time[] = ReachTime2.split("T");
                        String date_b = str_time[0] + " " + str_time[1];
                        try {
                            Date start_time = dfs.parse(date_b);
                            Date end_time = dfs.parse(os_time);

                            long second = (end_time.getTime() - start_time.getTime()) / 1000;
                            if (second < 0) {// 可编辑
                                if (state_report.equals("1")) {
                                    btn_login.setText("催促确认");
                                    btn_login_one.setVisibility(View.GONE);
                                    btn_login.setVisibility(View.VISIBLE);
                                    btn_cancle.setVisibility(View.VISIBLE);
                                    btn_cancle_report.setVisibility(View.GONE);
                                    Constants.IS_SHOW_ADD = "0";
                                } else if (state_report.equals("2")) {
                                    btn_login.setText("变更发送");
                                    btn_login_one.setVisibility(View.GONE);
                                    btn_login.setVisibility(View.VISIBLE);
                                    btn_cancle.setVisibility(View.VISIBLE);
                                    btn_cancle_report.setVisibility(View.GONE);
                                    Constants.IS_SHOW_ADD = "0";
                                } else if (state_report.equals("3")) {
                                    btn_login_one.setVisibility(View.GONE);
                                    btn_login.setVisibility(View.GONE);
                                    btn_cancle.setVisibility(View.GONE);
                                    btn_cancle_report.setVisibility(View.VISIBLE);
                                    btn_cancle_report.setEnabled(false);
                                    et_travel_name.setFocusable(false);
                                    et_guider_name.setFocusable(false);
                                    et_phone.setFocusable(false);
                                    et_car_num.setFocusable(false);
                                    mRadio_num.setEnabled(false);
                                    mRadio_sex.setEnabled(false);
                                    mRadio_size.setEnabled(false);
                                    et_by_num.setFocusable(false);
                                    et_by_adult.setFocusable(false);
                                    et_by_child.setFocusable(false);
                                    et_by_boy.setFocusable(false);
                                    et_by_girl.setFocusable(false);
                                    radio_sure_time.setEnabled(false);
                                    radio_not_sure_time.setEnabled(false);
                                    ll_sure_time.setClickable(false);
                                    ll_not_sure_start_time.setClickable(false);
                                    ll_not_sure_end_time.setClickable(false);
                                    rl_address.setClickable(false);
                                    rl_select_num.setClickable(false);
                                    et_shop_situation.setFocusable(false);
                                    et_teacher_requirement.setFocusable(false);
                                    et_teacher_theme.setFocusable(false);
                                    et_main_product.setFocusable(false);
                                    et_group_analyz.setFocusable(false);
                                    et_features.setFocusable(false);
                                    Constants.IS_SHOW_ADD = "1";
                                    tv_shili.setEnabled(false);
                                }
                            } else {// 不可编辑
                                btn_cancle.setVisibility(View.GONE);
                                btn_login_one.setVisibility(View.GONE);
                                btn_login.setVisibility(View.GONE);
                                et_travel_name.setFocusable(false);
                                et_guider_name.setFocusable(false);
                                et_phone.setFocusable(false);
                                et_car_num.setFocusable(false);
                                mRadio_num.setEnabled(false);
                                mRadio_sex.setEnabled(false);
                                mRadio_size.setEnabled(false);
                                et_by_num.setFocusable(false);
                                et_by_adult.setFocusable(false);
                                et_by_child.setFocusable(false);
                                et_by_boy.setFocusable(false);
                                et_by_girl.setFocusable(false);
                                radio_sure_time.setEnabled(false);
                                radio_not_sure_time.setEnabled(false);
                                ll_sure_time.setClickable(false);
                                ll_not_sure_start_time.setClickable(false);
                                ll_not_sure_end_time.setClickable(false);
                                rl_address.setClickable(false);
                                rl_select_num.setClickable(false);
                                et_shop_situation.setFocusable(false);
                                et_teacher_requirement.setFocusable(false);
                                et_teacher_theme.setFocusable(false);
                                et_main_product.setFocusable(false);
                                et_group_analyz.setFocusable(false);
                                et_features.setFocusable(false);
                                tv_shili.setEnabled(false);
                                Constants.IS_SHOW_ADD = "1";
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    dismissProgressDialog();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showProgressDialog("加载中...");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            dismissProgressDialog();
        }
    }


    /**
     * 取消报备
     *
     * @param distributorid
     * @param reportid
     * @param sign
     */
    public void doCancleReport(String distributorid, String reportid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("reportid", reportid);
        maps.put("sign", sign);

        RequestTask.getInstance().doCancleReport(ReportShopActivity.this, maps, new OnCancleRequestListener());

    }


    private class OnCancleRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String state = jsonObject.getString("status");
                if (state.equals("1")) {
                    MyToast.show(ReportShopActivity.this, "取消成功");
                    EventFactory.cancleBaoBei(1);
                    if (index.equals("1")) {
                        finish();
                    } else if (index.equals("0")) {
                        AppManager.getInstance().killTopActivity();
                        AppManager.getInstance().killTopActivity();
                    } else if (index.equals("2")) {
                        AppManager.getInstance().killTopActivity();
                        AppManager.getInstance().killTopActivity();
                        AppManager.getInstance().killTopActivity();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    /**
     * 上传图片
     */
    public void doUpload(File file, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("picurl", file);
        maps.put("sign", sign);
        showProgressDialog("正在上传...");
        RequestTask.getInstance().doReportUploadImage(ReportShopActivity.this, maps, new OnUploadRequestListener());
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
                    String result_imgpath = array.get(0).toString();
                    MyToast.show(ReportShopActivity.this, "上传成功");
                    list_urls.add(result_imgpath);
                    size++;
                    Message message = new Message();
                    message.what = 1002;
                    handler.sendMessage(message);
                    dismissProgressDialog();
                } else if (status.equals("0")) {
                    MyToast.show(ReportShopActivity.this, "上传失败");
                    dismissProgressDialog();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showProgressDialog("正在上传");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            dismissProgressDialog();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            dismissProgressDialog();
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 根据集合拼接 字符串
     *
     * @param lists
     */
    public String getJudge(List<String> lists) {
        if (lists.size() == 1) {
            upLoadPath = lists.get(0).toString() + "|";
        } else {
            for (int i = 0; i < lists.size(); i++) {
                upLoadPath += lists.get(i).toString() + "|";
            }
        }
        return upLoadPath;
    }

    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.DELETE_IMAGE_POSTION) {
            int location = event.getExtraData();
            if (list_urls.contains(list_urls.get(location))) {
                list_urls.remove(location);
                pathImageAdapter.setList(list_urls);
            }
        }
    }

    // 实例显示
    public void showImage() {
        dialog = new Dialog(ReportShopActivity.this, R.style.Mydialog);
        View view1 = View.inflate(ReportShopActivity.this,
                R.layout.dialog_show_shili, null);
        ImageView imageView = (ImageView) view1.findViewById(R.id.img_shili);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view1);
        dialog.show();
    }

    public void callPhone(final String phone, final String name) {
        call_dialog = new Dialog(ReportShopActivity.this, R.style.Mydialog);
        View view1 = View.inflate(ReportShopActivity.this,
                R.layout.dialog_call_phone, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_phone = (TextView) view1.findViewById(R.id.tv_content);
        TextView tv_name = (TextView) view1.findViewById(R.id.tv_phone);
        tv_phone.setText("确定拨打" + name);
        tv_name.setText("电话" + phone + "吗？");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_dialog.dismiss();
                FunctionUtils.jump2PhoneView(ReportShopActivity.this, phone);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_dialog.dismiss();
            }
        });
        call_dialog.setContentView(view1);
        call_dialog.show();
    }


    public void cancleReportDialog() {
        cancle_dialog = new Dialog(ReportShopActivity.this, R.style.Mydialog);
        View view1 = View.inflate(ReportShopActivity.this,
                R.layout.dialog_cancle_report, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancle_dialog.dismiss();
                String sign3 = TGmd5.getMD5(distributorid + reportId);
                doCancleReport(distributorid, reportId, sign3);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancle_dialog.dismiss();
            }
        });
        cancle_dialog.setContentView(view1);
        cancle_dialog.show();
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.et_shop_situation:
                // 解决scrollView中嵌套EditText导致不能上下滑动的问题
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                break;
        }
        return false;
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
                        String sign = TGmd5.getMD5("");
                        doUpload(file, sign);
                    }
                });
    }
}


