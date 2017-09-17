package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.PersonalInfoPersenter;
import com.lvgou.distribution.pullzoomview.PullToZoomScrollViewEx;
import com.lvgou.distribution.utils.CalendarUtils;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.PictureUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Swow on 2016/10/13.
 */
public class ApplyerInfoActivity extends BaseActivity implements View.OnClickListener, GroupView {


    private RelativeLayout rl_back;
    private TextView tv_title;
    private ImageView img_head;
    private ImageView img_one;
    private ImageView img_two;
    private ImageView img_three;
    private ImageView img_four;
    private ImageView img_five;
    private RelativeLayout rl_send_num;
    private RelativeLayout rl_carry_num;
    private RelativeLayout rl_call;
    private TextView tv_send_group_num;
    private TextView tv_carry_group_num;
    private TextView tv_phone;
    private ImageView img_phone_icon;
    private TextView tv_city;
    private TextView tv_years_old;
    private TextView tv_daoling;
    private TextView tv_language;
    private TextView tv_load;
    private ImageView img_id_card;

    @ViewInject(R.id.scroll_view)
    private PullToZoomScrollViewEx scrollView;
    @ViewInject(R.id.tv_employment)
    private TextView tv_employ;

    private PersonalInfoPersenter personalInfoPersenter;

    private String distributorid = "";
    private String seekid = "";
    private String applyid = "";

    private String img_path_idcard = "";
    DisplayImageOptions options;
    DisplayImageOptions optionsone;
    Dialog dialog_show;
    private String phone_num = "";
    private String isEmploy = "";

    private String distributorid_one = "";

    private Dialog dialog_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider_personal);
        ViewUtils.inject(this);

        personalInfoPersenter = new PersonalInfoPersenter(this);

        distributorid = PreferenceHelper.readString(ApplyerInfoActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        seekid = getTextFromBundle("seekid");

        applyid = getTextFromBundle("applyid");

        loadViewForCode();

        tv_employ.setOnClickListener(this);

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (12.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(localObject);

        String sign = TGmd5.getMD5(distributorid + seekid + applyid);

        personalInfoPersenter.getPersonalInfo(distributorid, seekid, applyid, sign);

    }

    private void loadViewForCode() {
        View headView = LayoutInflater.from(this).inflate(R.layout.head_personal_view, null, false);
        initHeadView(headView);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.profile_zoom_view, null, false);
        initZoomView(zoomView);
        View contentView = LayoutInflater.from(this).inflate(R.layout.profile_content_view, null, false);
        initConentView(contentView);


        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
    }

    /**
     * headView 控件初始化
     *
     * @param view
     */
    public void initHeadView(View view) {
        rl_back = (RelativeLayout) view.findViewById(R.id.rl_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        img_head = (ImageView) view.findViewById(R.id.img_head);
        img_one = (ImageView) view.findViewById(R.id.img_one);
        img_two = (ImageView) view.findViewById(R.id.img_two);
        img_three = (ImageView) view.findViewById(R.id.img_three);
        img_four = (ImageView) view.findViewById(R.id.img_four);
        img_five = (ImageView) view.findViewById(R.id.img_five);
        rl_back.setOnClickListener(this);
    }

    /**
     * ZoomView 控件初始化
     *
     * @param view
     */
    public void initZoomView(View view) {
        rl_send_num = (RelativeLayout) view.findViewById(R.id.rl_send_num);
        rl_carry_num = (RelativeLayout) view.findViewById(R.id.rl_carry_num);
        tv_send_group_num = (TextView) view.findViewById(R.id.tv_send_group_num);
        tv_carry_group_num = (TextView) view.findViewById(R.id.tv_carry_group_num);
        rl_send_num.setOnClickListener(this);
        rl_carry_num.setOnClickListener(this);
    }

    /**
     * ConentView 控件初始化
     *
     * @param view
     */
    public void initConentView(View view) {
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        img_phone_icon = (ImageView) view.findViewById(R.id.img_phone_icon);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_years_old = (TextView) view.findViewById(R.id.tv_years_old);
        tv_daoling = (TextView) view.findViewById(R.id.tv_daoling);
        tv_language = (TextView) view.findViewById(R.id.tv_language);
        tv_load = (TextView) view.findViewById(R.id.tv_luxian);
        img_id_card = (ImageView) view.findViewById(R.id.img_id_card);
        rl_call = (RelativeLayout) view.findViewById(R.id.rl_call);
        img_id_card.setOnClickListener(this);
        rl_call.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_send_num:
                bundle.putString("distributorid", distributorid_one);
                openActivity(SendGroupRecordActivity.class, bundle);
                break;
            case R.id.rl_carry_num:
                bundle.putString("distributorid", distributorid_one);
                openActivity(CarryGroupRecordActivity.class, bundle);
                break;
            case R.id.tv_employment:
                if (tv_employ.getText().equals("录用")) {
                    showQuitDialog();
                }
                break;
            case R.id.img_id_card:
                showQuitDialog(img_path_idcard);
                break;
            case R.id.rl_call:
                if (isEmploy.equals("1")) {
                    FunctionUtils.jump2PhoneView(ApplyerInfoActivity.this, phone_num);
                }
                break;
        }
    }

    /**
     * 星级
     *
     * @param star
     */
    public void initStar(String star) {
        switch (Integer.parseInt(star)) {
            case 1:
                img_one.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_two.setBackgroundResource(R.mipmap.bg_carry_group_light);
                img_three.setBackgroundResource(R.mipmap.bg_carry_group_light);
                img_four.setBackgroundResource(R.mipmap.bg_carry_group_light);
                img_five.setBackgroundResource(R.mipmap.bg_carry_group_light);
                break;
            case 2:
                img_one.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_two.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_three.setBackgroundResource(R.mipmap.bg_carry_group_light);
                img_four.setBackgroundResource(R.mipmap.bg_carry_group_light);
                img_five.setBackgroundResource(R.mipmap.bg_carry_group_light);
                break;
            case 3:
                img_one.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_two.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_three.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_four.setBackgroundResource(R.mipmap.bg_carry_group_light);
                img_five.setBackgroundResource(R.mipmap.bg_carry_group_light);
                break;
            case 4:
                img_one.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_two.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_three.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_four.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_five.setBackgroundResource(R.mipmap.bg_carry_group_light);
                break;
            case 5:
                img_one.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_two.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_three.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_four.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                img_five.setBackgroundResource(R.mipmap.bg_carry_group_yellow);
                break;
        }
    }

    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);

                        String data_one = array.get(0).toString();

                        String data_two = array.get(1).toString();
                        String data_three = array.get(2).toString();

                        String data_four = array.get(3).toString();

                        String data_five = array.get(4).toString();

                        JSONObject jsonObject_one = new JSONObject(data_one);
                        String img_head_path = jsonObject_one.getString("DistributorID");
                        distributorid_one = jsonObject_one.getString("DistributorID");
                        String star = jsonObject_one.getString("Star");
                        isEmploy = jsonObject_one.getString("IsEmploy");
                        String State = jsonObject_one.getString("State");


                        JSONObject jsonObject_two = new JSONObject(data_two);
                        String name = jsonObject_two.getString("RealName");
                        String phone = jsonObject_two.getString("Mobile");
                        String city = jsonObject_two.getString("CountryPath");
                        phone_num = jsonObject_two.getString("Mobile");
                        String urlPicl = jsonObject_two.getString("PicUrl");
                        img_path_idcard = jsonObject_two.getString("PicUrl");

                        JSONObject jsonObject_three = new JSONObject(data_three);
                        String yeas_old = jsonObject_three.getString("Birthday");
                        String workDay = jsonObject_three.getString("WorkDay");
                        String language = jsonObject_three.getString("Language");
                        String line = jsonObject_three.getString("Line");

                        options = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                                .build();
                        ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(img_head_path), img_head, options);
                        tv_title.setText(name);

                        initStar(star);

                        tv_send_group_num.setText(data_four);
                        tv_carry_group_num.setText(data_five);

                        if (isEmploy.equals("0")) {
                            img_phone_icon.setVisibility(View.VISIBLE);
                            if (phone.length() >= 11) {
                                tv_phone.setText(phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length()));
                            }
                        } else if (isEmploy.equals("1")) {
                            img_phone_icon.setVisibility(View.GONE);
                            tv_phone.setText(phone);
                        }
                        tv_city.setText(city);

                        String startBirthday = yeas_old.split("-")[0];
                        String startWorkDay = workDay.split("-")[0];
                        String endTime = new CalendarUtils().getYear() + "";

                        int birthday = Integer.parseInt(endTime) - Integer.parseInt(startBirthday);
                        int workday = Integer.parseInt(endTime) - Integer.parseInt(startWorkDay);

                        tv_years_old.setText(birthday + "岁");
                        tv_daoling.setText(workday + "年");

                        tv_language.setText(language);

                        tv_load.setText(line);

                        optionsone = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.daoyouzheng)            // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.daoyouzheng)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.daoyouzheng)            // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                                .build();
                        ImageLoader.getInstance().displayImage(Url.ROOT + urlPicl, img_id_card, optionsone);


                        if (isEmploy.equals("1")) {
                            tv_employ.setText("已录用");
                            tv_employ.setBackgroundResource(R.drawable.bg_conner_groupgray_shape);
                        } else if (isEmploy.equals("0")) {
                            if (State.equals("4")) {
                                tv_employ.setVisibility(View.GONE);
                            } else {
                                tv_employ.setVisibility(View.VISIBLE);
                                tv_employ.setText("录用");
                                tv_employ.setBackgroundResource(R.drawable.bg_conner_circle_green_shape);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String state_one = jsonObject.getString("status");
                    if (state_one.equals("1")) {
                        MyToast.show(ApplyerInfoActivity.this, "录用成功");
                        tv_employ.setText("已录用");
                        tv_employ.setBackgroundResource(R.drawable.bg_conner_groupgray_shape);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        MyToast.show(ApplyerInfoActivity.this, "请求失败");
    }


    @Override
    public void closeProgress() {

    }

    @Override
    public void showProgress() {

    }


    //退出登录
    public void showQuitDialog(String path) {
        dialog_show = new Dialog(ApplyerInfoActivity.this, R.style.Mydialog);
        View view1 = View.inflate(ApplyerInfoActivity.this, R.layout.dialog_show_idcard, null);
        ImageView img_idcard = (ImageView) view1.findViewById(R.id.img_show);
        optionsone = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icon_none_bee)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_none_bee)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_none_bee)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + path, img_idcard, optionsone);

        img_idcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_show.dismiss();
            }
        });
        dialog_show.setContentView(view1);
        dialog_show.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        personalInfoPersenter.attach(this);
        MobclickAgent.onResume(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        personalInfoPersenter.dettach();
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //退出登录
    public void showQuitDialog() {
        dialog_quit = new Dialog(ApplyerInfoActivity.this, R.style.Mydialog);
        View view1 = View.inflate(ApplyerInfoActivity.this, R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText("确定录用吗?");

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
                String sign = TGmd5.getMD5(distributorid + seekid + applyid);
                personalInfoPersenter.doEmployment(distributorid, seekid, applyid, sign);
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
}

