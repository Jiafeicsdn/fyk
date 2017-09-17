package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.PublishGroupBean;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.PublishGroupEditBean;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.presenter.PublishGroupPresenter;
import com.lvgou.distribution.utils.CalendarUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.PublishGroupView;
import com.lvgou.distribution.wheelview.OnWheelChangedListener;
import com.lvgou.distribution.wheelview.OnWheelScrollListener;
import com.lvgou.distribution.wheelview.WheelView;
import com.lvgou.distribution.wheelview.adapter.AbstractWheelTextAdapter;
import com.lvgou.distribution.wheelview.adapter.ArrayWheelAdapter;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 发布派团
 * Created by Snow on 2016/9/26.
 */
public class PublishGroupActivity extends BaseActivity implements OnWheelChangedListener, PublishGroupView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.et_title)
    private EditText et_title;
    @ViewInject(R.id.rl_out_group_time)
    private RelativeLayout rl_out_group_time;
    @ViewInject(R.id.tv_out_group_time)
    private TextView tv_out_group_time;
    @ViewInject(R.id.rl_out_group_days)
    private RelativeLayout rl_out_group_days;
    @ViewInject(R.id.tv_out_group_days)
    private TextView tv_out_group_days;
    @ViewInject(R.id.rl_destination)
    private RelativeLayout rl_destination;
    @ViewInject(R.id.tv_destination)
    private TextView tv_destination;
    @ViewInject(R.id.tv_scenic_spot)
    private EditText et_scenic_spot;
    @ViewInject(R.id.rl_group_types)
    private RelativeLayout rl_group_types;
    @ViewInject(R.id.tv_group_types)
    private TextView tv_group_types;
    @ViewInject(R.id.et_tourist_num)
    private EditText et_tourist_num;
    @ViewInject(R.id.et_guider_service)
    private EditText et_guider_service;
    @ViewInject(R.id.et_travel_introduction)
    private EditText et_travel_introduction;
    @ViewInject(R.id.tv_003)
    private TextView tv_003;
    @ViewInject(R.id.rl_guider_nums)
    private RelativeLayout rl_guider_nums;
    @ViewInject(R.id.tv_guider_nums)
    private TextView tv_guider_nums;
    @ViewInject(R.id.rl_guider_stars)
    private RelativeLayout rl_guider_stars;
    @ViewInject(R.id.tv_guider_stars)
    private TextView tv_guider_stars;
    @ViewInject(R.id.rl_sex)
    private RelativeLayout rl_sex;
    @ViewInject(R.id.tv_sex)
    private TextView tv_sex;
    @ViewInject(R.id.rl_daoling)
    private RelativeLayout rl_daoling;
    @ViewInject(R.id.tv_daoling)
    private TextView tv_daoling;
    @ViewInject(R.id.rl_guder_type)
    private RelativeLayout rl_guder_type;
    @ViewInject(R.id.tv_guder_type)
    private TextView tv_guder_type;
    @ViewInject(R.id.rl_languages)
    private RelativeLayout rl_languages;
    @ViewInject(R.id.tv_languages)
    private TextView tv_languages;
    @ViewInject(R.id.tv_other_requriment)
    private EditText et_other_requriment;
    @ViewInject(R.id.tv_publish_name)
    private TextView tv_publish_name;
    @ViewInject(R.id.tv_publish_phone)
    private TextView tv_publish_phone;
    @ViewInject(R.id.et_trval_name)
    private EditText et_trval_name;
    @ViewInject(R.id.et_additional)
    private EditText et_additional;
    @ViewInject(R.id.tv_004)
    private TextView tv_004;
    @ViewInject(R.id.tv_commit)
    private TextView tv_commit;


    private List<String> list_days;// 出团天数
    private List<String> list_month;// 出团日期
    private List<String> list_group_types; // 出团 类型
    private List<String> list_guider_stars;// 导游星级
    private List<String> list_sex;// 性别
    private List<String> list_daoling;// 导领
    private List<String> list_types;// 导游类型

    Dialog dialog_language;
    ImageView img_01, img_02, img_03, img_04, img_05, img_06, img_07, img_08, img_09, img_10, img_11;
    List<String> list_languages = new ArrayList<String>();
    Dialog dialog_year;
    Dialog startDialog;
    WheelView wheel_year;
    WheelView wheel_year_start, wheel_month_start, wheel_day_start;


    String type = "1";
    String str_1 = "0";
    String str_2 = "0";
    String str_3 = "0";
    String str_4 = "0";
    String str_5 = "0";
    String str_6 = "0";
    String str_7 = "0";
    String str_8 = "0";
    String str_9 = "0";
    String str_10 = "0";
    String str_11 = "0";


    List<String> startYearList, startMonthList, startDayList;
    String startYear = "", startMonth = "", startDay = "";
    String[] months, days;
    YearAdapter startYearAdapter;
    MonthAdapter startMonthAdapter;
    DayAdapter startDayAdapter;
    TextView startDone, startCancle;
    final int MIN_YEAR = 1959;
    int max_Year = 2100;
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    int startYearIndex, startMonthIndex, startDayIndex;
    Resources res;
    Date date;

    private String distributorid = "";
    private String group_title = "";//标题
    private String group_date = "";// 出团时间
    private String group_days = "1";// 出团天数
    private String destination = "";//目的地
    private String countryPath = "";//城市节点
    private String groupType = "1";// 团型 1=纯玩团、2=豪华团、3=购物团、4=品质团、5=低价团、6=零负团
    private String peopleCount = "";// 游客人数
    private String price = "";// 导服  decimal
    private String travelIntro = "";// 行程介绍
    private String tourCount = "1";//导游数量
    private String star = "1";// 星级：1-5
    private String sex = "0";//性别 0=不限，1=男，2=女
    private String tourAge = "0";//导龄  0=不限，1=1年以上，2=2年以上，3=3年以上，4=4年以上，5=5年以上
    private String language = "不限";// 语言  例：中文,英文
    private String distributorType = "1";//导游类型  1=地接，2=全陪，3=地接+全陪
    private String other = "";// 其他
    private String travel = "";//旅行社名称
    private String publisherOther = "";//发布者的其它
    private String sign = "";// 签名


    private String name = "";// 发布者
    private String phone = "";// 手机号
    private String selections_str = "";// 选中滚轮
    private boolean is_changed = false;// 是否滚动


    private String postion = "";// 0  不需要获取信息, 1 需要获取信息
    private String seekid = "";// 派团 Id

    private PublishGroupPresenter publishGroupPresenter;

    private Dialog dialog_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_publish_group);
        ViewUtils.inject(this);
        tv_title.setText("发布派团");
        distributorid = PreferenceHelper.readString(PublishGroupActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        name = PreferenceHelper.readString(PublishGroupActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME);
        phone = PreferenceHelper.readString(PublishGroupActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.MOBILE);
        tv_publish_name.setText(name);
        tv_publish_phone.setText(phone);// 2016-09-23
        publishGroupPresenter = new PublishGroupPresenter(this);

        postion = getTextFromBundle("postion");
        if (postion.equals("1")) {
//            String data_one = new CalendarUtils().getNowTime("yyyy-MM-dd").toString();
//            tv_out_group_time.setText(judgeTime(data_one));
//            group_date = new CalendarUtils().getNowTime("yyyy-MM-dd").toString();
        } else if (postion.equals("2")) {
            seekid = getTextFromBundle("seekid");
            String sign = TGmd5.getMD5(distributorid + seekid);
            publishGroupPresenter.getDetialInfo(distributorid, seekid, sign);
        }

        initData();
        initEditext();

    }

    @OnClick({R.id.rl_back, R.id.rl_out_group_time, R.id.rl_out_group_days, R.id.rl_destination, R.id.rl_group_types, R.id.rl_guider_nums,
            R.id.rl_guider_stars, R.id.rl_sex, R.id.rl_daoling, R.id.rl_guder_type, R.id.rl_languages, R.id.tv_commit})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_out_group_time:
                openStartDialog();
                break;
            case R.id.rl_out_group_days:
                is_changed = false;
                type = "1";
                showYear(type);
                break;
            case R.id.rl_destination:
                openActivity(SelectHotCityActivity.class);
                break;
            case R.id.rl_group_types:
                is_changed = false;
                type = "2";
                showYear(type);
                break;
            case R.id.rl_guider_nums:
                is_changed = false;
                type = "3";
                showYear(type);
                break;
            case R.id.rl_guider_stars:
                is_changed = false;
                type = "4";
                showYear(type);
                break;
            case R.id.rl_sex:
                is_changed = false;
                type = "5";
                showYear(type);
                break;
            case R.id.rl_daoling:
                is_changed = false;
                type = "6";
                showYear(type);
                break;
            case R.id.rl_guder_type:
                is_changed = false;
                type = "7";
                showYear(type);
                break;
            case R.id.rl_languages:
                shopLangage();
                break;
            case R.id.tv_commit:
                group_title = et_title.getText().toString();
                destination = et_scenic_spot.getText().toString().trim();
                peopleCount = et_tourist_num.getText().toString().trim();
                price = et_guider_service.getText().toString().trim();
                travelIntro = et_travel_introduction.getText().toString().trim();
                other = et_other_requriment.getText().toString().trim();
                travel = et_trval_name.getText().toString();
                publisherOther = et_additional.getText().toString().trim();
                String time = tv_out_group_time.getText().toString();

                if (StringUtils.isEmpty(group_title)) {
                    MyToast.show(PublishGroupActivity.this, "请输入标题");
                    return;
                }

                if ("请输入出团时间".equals(time)) {
                    MyToast.show(PublishGroupActivity.this, "请输入时间");
                    return;
                }

                if (StringUtils.isEmpty(countryPath)) {
                    MyToast.show(PublishGroupActivity.this, "请选择目的地城市");
                    return;
                }
                if (StringUtils.isEmpty(destination)) {
                    MyToast.show(PublishGroupActivity.this, "请输入你要去的景点");
                    return;
                }
                if (StringUtils.isEmpty(peopleCount)) {
                    MyToast.show(PublishGroupActivity.this, "请输入游客人数");
                    return;
                }
                if (StringUtils.isEmpty(price)) {
                    MyToast.show(PublishGroupActivity.this, "请输入导服");
                    return;
                }
                if (StringUtils.isEmpty(travelIntro)) {
                    MyToast.show(PublishGroupActivity.this, "请输入行程介绍");
                    return;
                }

                if (list_languages.size() == 0) {
                    language = "";
                } else {
                    language = list_languages.toString().substring(1, list_languages.toString().length() - 1).replaceAll(" ", "");
                }



                String sign = TGmd5.getMD5(distributorid + group_title + group_date + group_days + countryPath + destination + groupType + peopleCount +
                        price + travelIntro + tourCount + star + sex + tourAge + language + distributorType + other + travel + publisherOther);

                PublishGroupBean publishGroupBean = new PublishGroupBean(distributorid, group_title, group_date, group_days, countryPath, destination, groupType, peopleCount,
                        price, travelIntro, tourCount, star, sex, tourAge, language, distributorType, other, travel, publisherOther, sign);

                String sign_edit = TGmd5.getMD5(distributorid + seekid + group_title + group_date + group_days + countryPath + destination + groupType + peopleCount +
                        price + travelIntro + tourCount + star + sex + tourAge + language + distributorType + other + travel + publisherOther);

                PublishGroupEditBean publishGroupEditBean = new PublishGroupEditBean(distributorid, seekid, group_title, group_date, group_days, countryPath, destination, groupType, peopleCount,
                        price, travelIntro, tourCount, star, sex, tourAge, language, distributorType, other, travel, publisherOther, sign_edit);

              showQuitDialog(postion, publishGroupBean, publishGroupEditBean);

                break;
        }
    }

    public void initEditext() {
        et_scenic_spot.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_scenic_spot.setSingleLine(false);
        //水平滚动设置为False
        et_scenic_spot.setHorizontallyScrolling(false);
        et_scenic_spot.setHint("请输入你要去的景点,以逗号分割");


        et_travel_introduction.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_travel_introduction.setSingleLine(false);
        //水平滚动设置为False
        et_travel_introduction.setHorizontallyScrolling(false);
        et_travel_introduction.setHint("例: 9.1 南京到杭州, 9.2 西湖景区, 9.3 苏堤,断桥,太子湾, 9.4 西溪湿地");


        et_other_requriment.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_other_requriment.setSingleLine(false);
        //水平滚动设置为False
        et_other_requriment.setHorizontallyScrolling(false);
        et_other_requriment.setHint("例: 需要懂英文,普通话标准的和年轻阳光的灵活的你");


        et_additional.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_additional.setSingleLine(false);
        //水平滚动设置为False
        et_additional.setHorizontallyScrolling(false);
        et_additional.setHint("补充介绍你的个人信息或您所在的个人信息");


    }

    /**
     * @param time
     */
    public String judgeTime(String time) {
        LogUtils.e("time" + time);
        String strDate = "";
        String month = "";
        String day = "";
        String str[] = time.split("-");
        if (str[1].length() == 1) {
            month = "0" + str[1];
        } else if (str[1].length() == 2) {
            month = str[1];
        }

        if (str[2].length() == 1) {
            day = "0" + str[2];
        } else if (str[2].length() == 2) {
            day = str[2];
        }
        strDate = str[0] + "年" + month + "月" + day + "日";
        return strDate;
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        publishGroupPresenter.attach(this);
        if (Constants.TOTAL_ADDRESS_GROUP.equals("")) {
            tv_destination.setText("请选择目的地城市");
            tv_destination.setTextColor(getResources().getColor(R.color.bg_hint_color));
        } else {
            tv_destination.setText(Constants.TOTAL_ADDRESS_GROUP);
            tv_destination.setTextColor(getResources().getColor(R.color.bg_balck_three));
            countryPath = Constants.COUNTRYPATH_GROUP;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        publishGroupPresenter.dettach();
    }


    /**
     * 初始化绑定数据
     */
    public void initData() {

        list_month = new ArrayList<String>();
        list_month.add("1");
        list_month.add("2");
        list_month.add("3");
        list_month.add("4");
        list_month.add("5");
        list_month.add("6");
        list_month.add("7");
        list_month.add("8");
        list_month.add("9");
        list_month.add("10");


        list_group_types = new ArrayList<String>();
        list_group_types.add("纯玩团");
        list_group_types.add("豪华团");
        list_group_types.add("购物团");
        list_group_types.add("品质团");
        list_group_types.add("低价团");
        list_group_types.add("零负团");


        list_guider_stars = new ArrayList<String>();
        list_guider_stars.add("一星");
        list_guider_stars.add("二星");
        list_guider_stars.add("三星");
        list_guider_stars.add("四星");
        list_guider_stars.add("五星");


        list_daoling = new ArrayList<String>();
        list_daoling.add("不限");
        list_daoling.add("1年以上");
        list_daoling.add("2年以上");
        list_daoling.add("3年以上");
        list_daoling.add("4年以上");
        list_daoling.add("5年以上");

        list_types = new ArrayList<String>();
        list_types.add("地接");
        list_types.add("全陪");
        list_types.add("地接+全陪");

        list_sex = new ArrayList<String>();
        list_sex.add("不限");
        list_sex.add("男");
        list_sex.add("女");

    }


    /**
     * 选择语言对话框
     */
    public void shopLangage() {
        dialog_language = new Dialog(PublishGroupActivity.this,
                R.style.style_custom_dialog);
        View view_sex = View.inflate(PublishGroupActivity.this,
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
        RelativeLayout rl_all = (RelativeLayout) view_sex.findViewById(R.id.rl_all);
        tv_cancle_sex.setTextColor(getResources().getColor(R.color.bg_button_green));
        tv_done_sex.setTextColor(getResources().getColor(R.color.bg_button_green));
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

        if (list_languages.contains("中文")) {
            str_1 = "1";
            img_01.setImageResource(R.mipmap.bg_language_green_selected);
        }
        if (list_languages.contains("英语")) {
            str_2 = "1";
            img_02.setImageResource(R.mipmap.bg_language_green_selected);
        }
        if (list_languages.contains("法语")) {
            str_3 = "1";
            img_03.setImageResource(R.mipmap.bg_language_green_selected);
        }
        if (list_languages.contains("德语")) {
            str_4 = "1";
            img_04.setImageResource(R.mipmap.bg_language_green_selected);
        }
        if (list_languages.contains("意大利语")) {
            str_5 = "1";
            img_05.setImageResource(R.mipmap.bg_language_green_selected);
        }
        if (list_languages.contains("日语")) {
            str_6 = "1";
            img_06.setImageResource(R.mipmap.bg_language_green_selected);
        }
        if (list_languages.contains("韩语")) {
            str_7 = "1";
            img_07.setImageResource(R.mipmap.bg_language_green_selected);
        }
        if (list_languages.contains("葡萄牙语")) {
            str_8 = "1";
            img_08.setImageResource(R.mipmap.bg_language_green_selected);
        }
        if (list_languages.contains("泰语")) {
            str_9 = "1";
            img_09.setImageResource(R.mipmap.bg_language_green_selected);
        }
        if (list_languages.contains("越南语")) {
            str_10 = "1";
            img_10.setImageResource(R.mipmap.bg_language_green_selected);
        }
        if (list_languages.contains("印尼语")) {
            str_11 = "1";
            img_11.setImageResource(R.mipmap.bg_language_green_selected);
        }

        tv_done_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_language.dismiss();
                tv_languages.setText("");
                tv_languages.setText(list_languages.toString().substring(1, list_languages.toString().length() - 1).replaceAll(" ", ""));
                tv_languages.setTextColor(getResources().getColor(R.color.bg_balck_three));
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
                    img_01.setImageResource(R.mipmap.bg_language_green_selected);
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
                    img_02.setImageResource(R.mipmap.bg_language_green_selected);
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
                    img_03.setImageResource(R.mipmap.bg_language_green_selected);
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
                    img_04.setImageResource(R.mipmap.bg_language_green_selected);
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
                    img_05.setImageResource(R.mipmap.bg_language_green_selected);
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
                    img_06.setImageResource(R.mipmap.bg_language_green_selected);
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
                    img_07.setImageResource(R.mipmap.bg_language_green_selected);
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
                    img_08.setImageResource(R.mipmap.bg_language_green_selected);
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
                    img_09.setImageResource(R.mipmap.bg_language_green_selected);
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
                    img_10.setImageResource(R.mipmap.bg_language_green_selected);
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
                    img_11.setImageResource(R.mipmap.bg_language_green_selected);
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
    public void showYear(final String type) {
        dialog_year = new Dialog(PublishGroupActivity.this,
                R.style.style_custom_dialog);
        View view_sex = View.inflate(PublishGroupActivity.this,
                R.layout.dialog_select_publish_group, null);
        wheel_year = (WheelView) view_sex.findViewById(R.id.year);
        TextView tv_done_sex = (TextView) view_sex
                .findViewById(R.id.tv_done_sex);
        TextView tv_cancle_sex = (TextView) view_sex
                .findViewById(R.id.tv_cancle_sex);
        RelativeLayout rl_all = (RelativeLayout) view_sex.findViewById(R.id.rl_all);
        if (type.equals("1")) {// 出团天数
            wheel_year.setViewAdapter(new ArrayWheelAdapter(PublishGroupActivity.this, list_month));
            // 设置可见条目数量
            wheel_year.setVisibleItems(5);
            wheel_year.addChangingListener(this);
        } else if (type.equals("2")) {// 团型
            wheel_year.setViewAdapter(new ArrayWheelAdapter(PublishGroupActivity.this, list_group_types));
            // 设置可见条目数量
            wheel_year.setVisibleItems(5);
            wheel_year.addChangingListener(this);
        } else if (type.equals("3")) {// 导游数量
            wheel_year.setViewAdapter(new ArrayWheelAdapter(PublishGroupActivity.this, list_month));
            // 设置可见条目数量
            wheel_year.setVisibleItems(5);
            wheel_year.addChangingListener(this);
        } else if (type.equals("4")) {// 星级
            wheel_year.setViewAdapter(new ArrayWheelAdapter(PublishGroupActivity.this, list_guider_stars));
            // 设置可见条目数量
            wheel_year.setVisibleItems(5);
            wheel_year.setCurrentItem(list_guider_stars.size() / 2);
            wheel_year.addChangingListener(this);
        } else if (type.equals("5")) {// 性别
            wheel_year.setViewAdapter(new ArrayWheelAdapter(PublishGroupActivity.this, list_sex));
            // 设置可见条目数量
            wheel_year.setVisibleItems(5);
            wheel_year.addChangingListener(this);
        } else if (type.equals("6")) {// 导领
            wheel_year.setViewAdapter(new ArrayWheelAdapter(PublishGroupActivity.this, list_daoling));
            // 设置可见条目数量
            wheel_year.setVisibleItems(5);
            wheel_year.addChangingListener(this);
        } else if (type.equals("7")) {// 地接
            wheel_year.setViewAdapter(new ArrayWheelAdapter(PublishGroupActivity.this, list_types));
            // 设置可见条目数量
            wheel_year.setVisibleItems(3);
            wheel_year.addChangingListener(this);

        }

        tv_done_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_year.dismiss();
                judegType(type);
                LogUtils.e(selections_str);
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


    /**
     * 根据type 判断 选择的滚轮
     *
     * @param type
     */
    public void judegType(String type) {
        if (is_changed == false) {
            switch (Integer.parseInt(type)) {
                case 1:
                    group_days = "1";
                    tv_out_group_days.setText("1天");
                    break;
                case 2:
                    groupType = "1";
                    tv_group_types.setText("纯玩团");
                    break;
                case 3:
                    tourCount = "1";
                    tv_guider_nums.setText("1位");
                    break;
                case 4:
                    star = "3";
                    tv_guider_stars.setText("三星");
                    break;
                case 5:
                    sex = "0";
                    tv_sex.setText("不限");
                    break;
                case 6:
                    tourAge = "0";
                    tv_daoling.setText("不限");
                    break;
                case 7:
                    distributorType = "1";
                    tv_guder_type.setText("地接");
                    break;
            }
        } else {
            switch (Integer.parseInt(type)) {
                case 1:
                    group_days = selections_str;
                    tv_out_group_days.setText(group_days + "天");
                    break;
                case 2:
                    if (selections_str.equals("纯玩团")) {
                        groupType = "1";
                        tv_group_types.setText("纯玩团");
                    } else if (selections_str.equals("豪华团")) {
                        groupType = "2";
                        tv_group_types.setText("豪华团");
                    } else if (selections_str.equals("购物团")) {
                        groupType = "3";
                        tv_group_types.setText("购物团");
                    } else if (selections_str.equals("品质团")) {
                        groupType = "4";
                        tv_group_types.setText("品质团");
                    } else if (selections_str.equals("低价团")) {
                        groupType = "5";
                        tv_group_types.setText("低价团");
                    } else if (selections_str.equals("零负团")) {
                        groupType = "6";
                        tv_group_types.setText("零负团");
                    }
                    break;
                case 3:
                    tourCount = selections_str;
                    tv_guider_nums.setText(tourCount + "位");
                    break;
                case 4:
                    if (selections_str.equals("一星")) {
                        star = 1 + "";
                        tv_guider_stars.setText("一星");
                    } else if (selections_str.equals("二星")) {
                        star = 2 + "";
                        tv_guider_stars.setText("二星");
                    } else if (selections_str.equals("三星")) {
                        star = 3 + "";
                        tv_guider_stars.setText("三星");
                    } else if (selections_str.equals("四星")) {
                        star = 4 + "";
                        tv_guider_stars.setText("四星");
                    } else if (selections_str.equals("五星")) {
                        star = 5 + "";
                        tv_guider_stars.setText("五星");
                    }
                    break;
                case 5:
                    if (selections_str.equals("不限")) {
                        sex = "0";
                        tv_sex.setText("不限");
                    } else if (selections_str.equals("男")) {
                        sex = "1";
                        tv_sex.setText("男");
                    } else if (selections_str.equals("女")) {
                        sex = "2";
                        tv_sex.setText("女");
                    }
                    break;
                case 6:
                    if (selections_str.equals("不限")) {
                        tourAge = "0";
                        tv_daoling.setText("不限");
                    } else if (selections_str.equals("1年以上")) {
                        tourAge = "1";
                        tv_daoling.setText("1年以上");
                    } else if (selections_str.equals("2年以上")) {
                        tourAge = "2";
                        tv_daoling.setText("2年以上");
                    } else if (selections_str.equals("3年以上")) {
                        tourAge = "3";
                        tv_daoling.setText("3年以上");
                    } else if (selections_str.equals("4年以上")) {
                        tourAge = "4";
                        tv_daoling.setText("4年以上");
                    } else if (selections_str.equals("5年以上")) {
                        tourAge = "5";
                        tv_daoling.setText("5年以上");
                    }
                    break;
                case 7:
                    if (selections_str.equals("地接")) {
                        distributorType = "1";
                        tv_guder_type.setText("地接");
                    } else if (selections_str.equals("全陪")) {
                        distributorType = "2";
                        tv_guder_type.setText("全陪");
                    } else if (selections_str.equals("地接+全陪")) {
                        distributorType = "3";
                        tv_guder_type.setText("地接+全陪");
                    }
                    break;
            }
        }
    }


    //退出登录
    public void showQuitDialog(final String type, final PublishGroupBean publishGroupBean, final PublishGroupEditBean publishGroupEditBean) {
        dialog_quit = new Dialog(PublishGroupActivity.this, R.style.Mydialog);
        View view1 = View.inflate(PublishGroupActivity.this,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView textView = (TextView) view1.findViewById(R.id.tv_title);
        textView.setText("确认派团吗？");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
                if (type.equals("1")) {// 发布派团
                    publishGroupPresenter.publishGroup(publishGroupBean);
                } else if (type.equals("2")) {// 编辑发布派团
                    publishGroupPresenter.editPublishGroup(publishGroupEditBean);
                }
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


    public void openStartDialog() {
        startDialog = new Dialog(PublishGroupActivity.this,
                R.style.style_custom_dialog);
        View startView = View.inflate(PublishGroupActivity.this,
                R.layout.wheel_view, null);
        wheel_year_start = (WheelView) startView.findViewById(R.id.year);
        wheel_month_start = (WheelView) startView.findViewById(R.id.month);
        wheel_day_start = (WheelView) startView.findViewById(R.id.day);
        startDone = (TextView) startView.findViewById(R.id.done);
        startCancle = (TextView) startView.findViewById(R.id.cancle);
        RelativeLayout rl_all = (RelativeLayout) startView.findViewById(R.id.rl_all);
        res = getResources();
        startDone.setTextColor(getResources().getColor(R.color.bg_button_green));
        startCancle.setTextColor(getResources().getColor(R.color.bg_button_green));
        months = res.getStringArray(R.array.months);
        days = res.getStringArray(R.array.days_31);
        date = new Date();
        String year = yearFormat.format(date);
//        max_Year = Integer.parseInt(year);
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
                            startDayAdapter = new DayAdapter(PublishGroupActivity.this, startDayList);
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
                            startDayAdapter = new DayAdapter(PublishGroupActivity.this, startDayList);
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
                        startDayAdapter = new DayAdapter(PublishGroupActivity.this, startDayList);
                        wheel_day_start.setViewAdapter(startDayAdapter);
                        wheel_day_start.setCurrentItem(startDayIndex);
                    }
                } else if (i == 2) {
                    if (isLeapYear(year)) {
                        //29 闂板勾2鏈?9澶?
                        if (startDayAdapter.list.size() != 29) {
                            startDayList = Arrays.asList(res.getStringArray(R.array.days_29));
                            startDayAdapter = new DayAdapter(PublishGroupActivity.this, startDayList);
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
                            startDayAdapter = new DayAdapter(PublishGroupActivity.this, startDayList);
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
                        startDayAdapter = new DayAdapter(PublishGroupActivity.this, startDayList);
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
                String date_two = startYear + "-" + startMonth + "-" + startDay;
                group_date = startYear + "-" + startMonth + "-" + startDay;
                tv_out_group_time.setText(judgeTime(date_two));
                tv_out_group_time.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
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
            startYear = yearFormat.format(date);
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


    @Override
    public void OnPublishSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    JSONObject jsonObject_one = new JSONObject(respanse);
                    String status = jsonObject_one.getString("status");
                    if (status.equals("1")) {
                        Constants.SELECTE_POSITION06 = "全部";
                        Constants.TOTAL_ADDRESS_GROUP = "";
                        Constants.COUNTRYPATH_GROUP = "";
                        EventFactory.upDateGroupSend(0);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject jsonObject_two = new JSONObject(respanse);
                    String status = jsonObject_two.getString("status");
                    if (status.equals("1")) {
                        Constants.SELECTE_POSITION06 = "全部";
                        Constants.TOTAL_ADDRESS_GROUP = "";
                        Constants.COUNTRYPATH_GROUP = "";
                        EventFactory.upDateGroupSend(0);
                        AppManager.getInstance().killTopActivity();
                        AppManager.getInstance().killTopActivity();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();
                        JSONObject jsonObject_data = new JSONObject(data);
                        String Title = jsonObject_data.getString("Title");
                        String TravelTime = jsonObject_data.getString("TravelTime");
                        String Day = jsonObject_data.getString("Day");
                        String CountryName = jsonObject_data.getString("CountryName");
                        String CountryPath = jsonObject_data.getString("CountryPath");
                        String Destination = jsonObject_data.getString("Destination");
                        String GroupType = jsonObject_data.getString("GroupType");
                        String PeopleCount = jsonObject_data.getString("PeopleCount");
                        String Price = jsonObject_data.getString("Price");
                        String TravelIntro = jsonObject_data.getString("TravelIntro");
                        String TourCount = jsonObject_data.getString("TourCount");
                        String Star = jsonObject_data.getString("Star");
                        String Sex = jsonObject_data.getString("Sex");
                        String TourAge = jsonObject_data.getString("TourAge");
                        String Language = jsonObject_data.getString("Language");
                        String DistributorType = jsonObject_data.getString("DistributorType");
                        String Other = jsonObject_data.getString("Other");
                        String Travel = jsonObject_data.getString("Travel");
                        String PublisherOther = jsonObject_data.getString("PublisherOther");

                        /***************控件显示赋值**********/
                        et_title.setText(Title);
                        String tiem_[] = TravelTime.split("T");
                        tv_out_group_time.setTextColor(getResources().getColor(R.color.bg_balck_three));
                        tv_out_group_time.setText(tiem_[0].split("-")[0] + "年" + tiem_[0].split("-")[1] + "月" + tiem_[0].split("-")[2] + "日");
                        tv_out_group_days.setText(Day);

                        et_scenic_spot.setText(Destination);
                        Constants.TOTAL_ADDRESS_GROUP = CountryName;
                        Constants.COUNTRYPATH_GROUP = CountryPath;
                        tv_destination.setText(CountryName);
                        tv_destination.setTextColor(getResources().getColor(R.color.bg_balck_three));


                        if (GroupType.equals("1")) {
                            tv_group_types.setText("纯玩团");
                        } else if (GroupType.equals("2")) {
                            tv_group_types.setText("豪华团");
                        } else if (GroupType.equals("3")) {
                            tv_group_types.setText("购物团");
                        } else if (GroupType.equals("4")) {
                            tv_group_types.setText("品质团");
                        } else if (GroupType.equals("5")) {
                            tv_group_types.setText("低价团");
                        } else if (GroupType.equals("6")) {
                            tv_group_types.setText("零负团");
                        }

                        et_tourist_num.setText(PeopleCount);
                        et_guider_service.setText(Price);
                        et_travel_introduction.setText(TravelIntro);

                        tv_guider_nums.setText(TourCount + "位");
                        tv_guider_stars.setText(Star + "星");
                        if (Sex.equals("0")) {
                            tv_sex.setText("不限");
                        } else if (Sex.equals("1")) {
                            tv_sex.setText("男");
                        } else if (Sex.equals("2")) {
                            tv_sex.setText("女");
                        }

                        if (TourAge.equals("0")) {
                            tv_daoling.setText("不限");
                        } else {
                            tv_daoling.setText(TourAge + "年以上");
                        }
                        if (Language.length() == 0) {
                            tv_languages.setText("不限");
                        } else {
                            tv_languages.setText(Language);
                        }


                        if (DistributorType.equals("1")) {
                            tv_guder_type.setText("地接");
                        } else if (DistributorType.equals("2")) {
                            tv_guder_type.setText("全陪");
                        } else if (DistributorType.equals("3")) {
                            tv_guder_type.setText("地接+全陪");
                        }

                        et_other_requriment.setText(Other);
                        et_trval_name.setText(Travel);
                        et_additional.setText(PublisherOther);

                        /************表单提交赋值**************/
                        group_title = Title;
                        group_date = tiem_[0];
                        group_days = Day;
                        countryPath = CountryPath;
                        destination = Destination;
                        groupType = GroupType;
                        peopleCount = PeopleCount;
                        price = Price;
                        travelIntro = TravelIntro;
                        tourCount = TourCount;
                        star = Star;
                        sex = Sex;
                        tourAge = TourAge;
                        language = Language;
                        distributorType = DistributorType;
                        other = Other;
                        travel = Travel;
                        publisherOther = PublisherOther;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void OnPublishFialCallBack(String state, String respanse) {
        LogUtils.e(respanse);
        MyToast.show(PublishGroupActivity.this, "请求失败");
    }

    /**
     * 取消进度条
     */
    @Override
    public void closeProgress() {

    }


    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == wheel_year) {
            is_changed = true;
            int currentPostion = wheel_year.getCurrentItem();
            if (type.equals("1")) {
                selections_str = list_month.get(currentPostion);
            } else if (type.equals("2")) {
                selections_str = list_group_types.get(currentPostion);
            } else if (type.equals("3")) {
                selections_str = list_month.get(currentPostion);
            } else if (type.equals("4")) {
                selections_str = list_guider_stars.get(currentPostion);
            } else if (type.equals("5")) {
                selections_str = list_sex.get(currentPostion);
            } else if (type.equals("6")) {
                selections_str = list_daoling.get(currentPostion);
            } else if (type.equals("7")) {
                selections_str = list_types.get(currentPostion);
            }

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
