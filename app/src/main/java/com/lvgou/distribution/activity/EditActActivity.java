package com.lvgou.distribution.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.luban.Luban;
import com.lvgou.distribution.presenter.ActivityEditloadPresenter;
import com.lvgou.distribution.presenter.ActivitySubmitPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.PermissionManager;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ActivityEditloadView;
import com.lvgou.distribution.view.ActivitySubmitView;
import com.lvgou.distribution.widget.CustomDatePicker;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/26.
 * 编辑活动
 */

public class EditActActivity extends BaseActivity implements View.OnClickListener, ActivitySubmitView, ActivityEditloadView {

    private ActivitySubmitPresenter activitySubmitPresenter;
    private ActivityEditloadPresenter activityEditloadPresenter;
    private String imagePath = null;//当前图片路径
    private final static int REQUEST_CODE_CAMERA = 0x44;//拍照
    private final static int REQUEST_CODE_GALLERY = 0x33;//相册
    private String distributorid;
    String activityid = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabu_act);
        activitySubmitPresenter = new ActivitySubmitPresenter(this);
        activityEditloadPresenter = new ActivityEditloadPresenter(this);
        Constants.TOTAL_ADDRESS = "";
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        activityid = getIntent().getStringExtra("activityid");
        initView();
        initDatas();
        initClick();
        initDatePicker();
        initDatePickerEnd();
    }

    private void initDatas() {
        String sign = TGmd5.getMD5(distributorid + activityid);
        showLoadingProgressDialog(this, "");
        activityEditloadPresenter.activityEditload(distributorid, activityid, sign);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private RelativeLayout rl_back;//返回键
    private TextView tv_title;//标题
    private ImageView im_add_pic;//添加照片
    private TextView tv_markwords;
    private ImageView im_picture_path;//添加后的照片
    private EditText et_activity_title;//活动标题
    private TextView tv_start_time;//开始时间
    private TextView tv_end_time;//结束时间
    private TextView tv_place;//活动地址
    private EditText et_detail_place;//具体地址
    private EditText et_maxpeople;//最大人数
    private RelativeLayout mDialogRootRelativeLayout;
    private LinearLayout mDialogCotentLinearLayout;
    private TextView tv_select_gallery;//从相册选择
    private TextView tv_select_camera;//拍照
    private TextView tv_cancel;//取消
    private EditText et_simple_introduce;//简单介绍
    private TextView tv_text_num;//介绍字数
    private CheckBox cb_xieyi;
    private TextView tv_click_xieyi;//蜂优客服务协议
    private TextView tv_fabu;//发布

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("发布活动");
        im_add_pic = (ImageView) findViewById(R.id.im_add_pic);
        tv_markwords = (TextView) findViewById(R.id.tv_markwords);
        im_picture_path = (ImageView) findViewById(R.id.im_picture_path);
        et_activity_title = (EditText) findViewById(R.id.et_activity_title);
        tv_start_time = (TextView) findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        tv_place = (TextView) findViewById(R.id.tv_place);
        et_detail_place = (EditText) findViewById(R.id.et_detail_place);
        et_maxpeople = (EditText) findViewById(R.id.et_maxpeople);
        mDialogRootRelativeLayout = (RelativeLayout) findViewById(R.id.rl_dialog_ios_7_root);
        mDialogCotentLinearLayout = (LinearLayout) findViewById(R.id.ll_dialog_ios_7_cotent);
        tv_select_gallery = (TextView) findViewById(R.id.tv_select_gallery);
        tv_select_camera = (TextView) findViewById(R.id.tv_select_camera);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        et_simple_introduce = (EditText) findViewById(R.id.et_simple_introduce);
        tv_text_num = (TextView) findViewById(R.id.tv_text_num);
        //改变默认的单行模式
        et_simple_introduce.setSingleLine(false);
        //水平滚动设置为False
        et_simple_introduce.setHorizontallyScrolling(false);
        TextChangeListener();
        cb_xieyi = (CheckBox) findViewById(R.id.cb_xieyi);
        tv_click_xieyi = (TextView) findViewById(R.id.tv_click_xieyi);
        tv_fabu = (TextView) findViewById(R.id.tv_fabu);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        im_add_pic.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        tv_place.setOnClickListener(this);
        im_picture_path.setOnClickListener(this);
        tv_select_gallery.setOnClickListener(this);
        tv_select_camera.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        cb_xieyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    xieyi = "1";
                    cb_xieyi.setBackgroundResource(R.mipmap.check_xieyi_icon);
                } else {
                    xieyi = "0";
                    cb_xieyi.setBackgroundResource(R.mipmap.register_user_default);
                }
            }
        });
        tv_click_xieyi.setOnClickListener(this);
        tv_fabu.setOnClickListener(this);

    }

    private String xieyi = "1";//是否选择协议

    public void TextChangeListener() {
        et_simple_introduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String length = et_simple_introduce.getText().length() + "";
                if (Integer.parseInt(length) >= 0 && Integer.parseInt(length) <= 500) {
                    tv_text_num.setText(length + "/500");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String zbTime = "2017-01-01 10:30";// 开始时间
    private CustomDatePicker customDatePicker2;

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        zbTime = sdf2.format(new Date());
//        tv_start_time.setText(sdf.format(new Date()));


        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                zbTime = time;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(simpleDateFormat.parse(time));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                tv_start_time.setText(simpleDateFormat2.format(c.getTimeInMillis()));
            }
        }, "1970-01-01 00:00", "2100-12-31 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

    private String endTime = "2017-01-01 10:30";// 结束时间
    private CustomDatePicker customDatePicker3;

    private void initDatePickerEnd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        endTime = sdf2.format(new Date());
//        tv_end_time.setText(sdf.format(new Date()));


        customDatePicker3 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                endTime = time;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(simpleDateFormat.parse(time));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                tv_end_time.setText(simpleDateFormat2.format(c.getTimeInMillis()));
            }
        }, "1970-01-01 00:00", "2100-12-31 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker3.showSpecificTime(true); // 显示时和分
        customDatePicker3.setIsLoop(true); // 允许循环滚动
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.im_add_pic://添加照片
                openDialog();
                break;
            case R.id.im_picture_path://照片添加之后再次点击
                openDialog();
                break;
            case R.id.tv_start_time://开始时间
                String tvstartttime = tv_start_time.getText().toString().trim();
                if (tvstartttime.equals("")) {
                    customDatePicker2.show(zbTime);
                } else {
                    customDatePicker2.show(tvstartttime);
                }

                break;
            case R.id.tv_end_time://结束时间
                String tvendttime = tv_end_time.getText().toString().trim();
                if (tvendttime.equals("")) {
                    customDatePicker3.show(endTime);
                } else {
                    customDatePicker3.show(tvendttime);
                }
//                customDatePicker3.show(endTime);
                break;
            case R.id.tv_place://活动地址
                openActivity(SelectProvinceActivity.class);
                break;
            case R.id.tv_select_gallery://从相册选择
                closeDialog();
                // 权限管理工具类
                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                    @Override
                    public void permissionGranted(int requestCode) {
                        if (requestCode == 99) {
                            PhotoPickerIntent intent = new PhotoPickerIntent(EditActActivity.this);
                            intent.setPhotoCount(1);
                            intent.setShowCamera(true);
                            intent.setShowGif(true);
                            startActivityForResult(intent, REQUEST_CODE_GALLERY);
                        }
                    }
                });
                //申请读权限，和照相机权限
                permissionManager.checkPermission(99, EditActActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                break;
            case R.id.tv_select_camera://拍照
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.CAMERA}, new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                imagePath = FunctionUtils.chooseImageFromCamera(EditActActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
                            }

                            @Override
                            public void onDenied(String permission) {
                            }
                        }
                );
                closeDialog();
                break;
            case R.id.tv_cancel://取消
                closeDialog();
                break;
            case R.id.tv_click_xieyi://蜂优客服务协议
                Bundle bundle = new Bundle();
                bundle.putString("url", Url.XIANSHANG_ROOT + "/user/RegisterProtocol.html");
                bundle.putString("index", "0");
                openActivity(WebViewActivity.class, bundle);
                break;
            case R.id.tv_fabu://发布活动
                if (picurl == null || picurl.equals("")) {
                    MyToast.makeText(this, "请上传活动照片!", Toast.LENGTH_SHORT).show();
                    break;
                }
                title = et_activity_title.getText().toString().trim();
                if (title == null || title.equals("")) {
                    MyToast.makeText(this, "请添加活动标题!", Toast.LENGTH_SHORT).show();
                    break;
                }
                starttime = tv_start_time.getText().toString().trim();
                if (starttime == null || starttime.equals("")) {
                    MyToast.makeText(this, "请选择开始时间!", Toast.LENGTH_SHORT).show();
                    break;
                }
                endtime = tv_end_time.getText().toString().trim();
                if (endtime == null || endtime.equals("")) {
                    MyToast.makeText(this, "请选择结束时间!", Toast.LENGTH_SHORT).show();
                    break;
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date startData = sdf.parse(starttime);
                    Date endData = sdf.parse(endtime);
                    boolean boo = compareDate(endData, startData);
                    if (!boo) {
                        MyToast.makeText(this, "开始时间必须小于结束时间", Toast.LENGTH_SHORT).show();
                        break;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                countrypath = tv_place.getText().toString().trim();
                if (countrypath == null || countrypath.equals("")) {
                    MyToast.makeText(this, "请选择活动地址!", Toast.LENGTH_SHORT).show();
                    break;
                }
                address = et_detail_place.getText().toString().trim();
                if (address == null || address.equals("")) {
                    MyToast.makeText(this, "请输入具体地址!", Toast.LENGTH_SHORT).show();
                    break;
                }
                String maxnumber = et_maxpeople.getText().toString().trim();
                if (maxnumber == null || maxnumber.equals("")) {
                    MyToast.makeText(this, "请输入人数上限!", Toast.LENGTH_SHORT).show();
                    break;
                }
                maxpeople = Integer.parseInt(maxnumber);
                info = et_simple_introduce.getText().toString().trim();
                if (info == null || info.equals("")) {
                    MyToast.makeText(this, "请简单介绍!", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (xieyi == null || !xieyi.equals("1")) {
                    MyToast.makeText(this, "您还没同意《蜂优客服务协议》!", Toast.LENGTH_SHORT).show();
                    break;
                }

                String city = Constants.COUNTRYPATH;
                String sign = TGmd5.getMD5(distributorid + activityid + picurl + title + starttime + endtime + city + address + maxpeople + info);
                Log.e("khaskdfj", "**********" + distributorid + picurl + title + starttime + endtime + city + address + maxpeople + info);
                showLoadingProgressDialog(this, "");
                activitySubmitPresenter.activitySubmitDatas(distributorid, activityid, picurl, title, starttime, endtime, city, address, maxpeople, info, sign);
                break;
        }
    }

    /**
     * 比较两个日期之间的大小
     *
     * @param d1
     * @param d2
     * @return 前者大于后者返回true 反之false
     */
    public boolean compareDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        int result = c1.compareTo(c2);
        if (result >= 0)
            return true;
        else
            return false;
    }

    private String picurl = "";//活动图片路径
    private String title = "";//活动标题
    private String starttime = "";//活动开始时间 格式:2017-3-10 10:00
    private String endtime = "";//活动结束时间 格式:2017-3-10 15:00
    private String countrypath = "";//城市节点
    private String address = "";//详细地址
    private int maxpeople = 0;//活动上限人数
    private String info = "";//活动简介

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.TOTAL_ADDRESS.equals("")) {
            tv_place.setText("请选择");
            tv_place.setTextColor(Color.parseColor("#cccccc"));
        } else {
            tv_place.setText(Constants.TOTAL_ADDRESS);
            tv_place.setTextColor(Color.parseColor("#000000"));
        }
    }

    private PermissionManager permissionManager;

    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                case REQUEST_CODE_CAMERA://拍照的
                    if (!StringUtils.isEmpty(imagePath)) {
                        showLoadingProgressDialog(EditActActivity.this, "");
//                        compressWithRx(new File(imagePath));

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                compressWithRx(new File(imagePath));
                            }
                        }).start();
                    }
                    break;
                case REQUEST_CODE_GALLERY://相册的
                    if (data != null) {
                        data_list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                        imagePath = data_list.get(0);
                        if (!StringUtils.isEmpty(imagePath)) {
                            showLoadingProgressDialog(EditActActivity.this, "");
//                            compressWithRx(new File(imagePath));
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    compressWithRx(new File(imagePath));
                                }
                            }).start();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 压缩单张图片 RxJava 方式
     */
    private void compressWithRx(File file) {
        if (file.exists()) {
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
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    MyToast.show(EditActActivity.this, "蜂优客没有系统权限，文件目录不存在");
                }
            });
        }
    }

    /**
     * 上传图片
     */
    public void doUpload(File file, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("picurl", file);
        maps.put("sign", sign);
        RequestTask.getInstance().doActivityUploadImage(EditActActivity.this, maps, new OnUploadRequestListener());
    }


    private class OnUploadRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            closeLoadingProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    Log.e("ashfkhas", "---------" + array.get(0));
                    im_add_pic.setVisibility(View.GONE);
                    tv_markwords.setVisibility(View.GONE);
                    im_picture_path.setVisibility(View.VISIBLE);
                    picurl = array.get(0).toString();
                    Glide.with(EditActActivity.this).load(Url.ROOT + array.get(0).toString()).into(im_picture_path);
                    /*String data = array.get(0).toString();
                    JSONArray array1 = new JSONArray(data);
                    if (array1 != null && array1.length() > 0) {
                        for (int i = 0; i < array1.length(); i++) {
                            JSONObject jsonObject_ = array1.getJSONObject(i);
                            String result_imgpath = jsonObject_.getString("picUrl");
                            String smallPicUrl = jsonObject_.getString("smallPicUrl");
                            String width = jsonObject_.getString("width");
                            String hight = jsonObject_.getString("height");
                            String upload_urls = result_imgpath + "&" + smallPicUrl + "&" + width + "&" + hight + "|";
                            //
                            Log.e("jhsadfka", "------------"+upload_urls );
                        }
                        Message message = new Message();
                        message.what = 1002;
                        handler.sendMessage(message);
                    }*/
                } else if (status.equals("0")) {
                    MyToast.show(EditActActivity.this, "上传失败");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
//          showLoadingProgressDialog(PublishFenwenActivity.this, "");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
//          closeLoadingProgressDialog();
        }
    }


    @Override
    public void OnActivitySubmitSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        finish();
        EventBus.getDefault().post("editedactsuccess");
        MyToast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnActivitySubmitFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        Log.e("ashdfkha", "*********" + respanse);
        MyToast.makeText(this, "发布失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeActivitySubmitProgress() {

    }

    @Override
    public void OnActivityEditloadSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            Log.e("lkahsdfkhj", "-----------" + respanse);
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            im_picture_path.setVisibility(View.VISIBLE);
            picurl = jsonObject1.get("PicUrl").toString();
            Glide.with(EditActActivity.this).load(Url.ROOT + jsonObject1.get("PicUrl").toString()).into(im_picture_path);
            et_activity_title.setText(jsonObject1.get("Title").toString());
            String[] split = jsonObject1.get("StartTime").toString().split("T");
            String[] split1 = split[1].split(":");
            tv_start_time.setText(split[0] + " " + split1[0] + ":" + split1[1]);
            String[] split2 = jsonObject1.get("EndTime").toString().split("T");
            String[] split3 = split2[1].split(":");
            tv_end_time.setText(split2[0] + " " + split3[0] + ":" + split3[1]);
            String[] countryPaths = jsonObject1.get("CountryPath").toString().split(",");
            Constants.TOTAL_ADDRESS = countryPaths[1];
            Constants.COUNTRYPATH = countryPaths[0];
            tv_place.setText(Constants.TOTAL_ADDRESS);
            tv_place.setTextColor(Color.parseColor("#000000"));
            et_detail_place.setText(jsonObject1.get("Address").toString());
            et_maxpeople.setText(jsonObject1.get("PeopleCount").toString());
            et_simple_introduce.setText(jsonObject1.get("ActivityIntro").toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnActivityEditloadFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        Log.e("lkahsdfkhj", "*************" + respanse);
    }

    @Override
    public void closeActivityEditloadProgress() {
        closeLoadingProgressDialog();
    }

}