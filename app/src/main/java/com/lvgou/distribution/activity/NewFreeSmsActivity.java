package com.lvgou.distribution.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.GridViewAdapter;
import com.lvgou.distribution.bean.CallBackVo;
import com.lvgou.distribution.bean.SmsBean;
import com.lvgou.distribution.bean.SmsResult;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.orc.Tesseract;
import com.lvgou.distribution.presenter.SendPersenter;
import com.lvgou.distribution.utils.PermissionManager;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.FreeSmsView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;


/**
 * Created by Snow on 2016/3/19 0019.
 * 免费短信
 */
public class NewFreeSmsActivity extends BaseActivity implements FreeSmsView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.tv_right)
    private TextView tv_record;
    @ViewInject(R.id.rl_model)
    private LinearLayout rl_model;
    @ViewInject(R.id.et_new)
    private EditText et_new;
    @ViewInject(R.id.rl_select)
    private RelativeLayout rl_select;
    @ViewInject(R.id.et_sms_content)
    private EditText et_sms_content;
    @ViewInject(R.id.tv_model_name)
    private TextView tv_model_name;
    @ViewInject(R.id.tv_send)
    private TextView tv_send;
    @ViewInject(R.id.cb_qita)
    private CheckBox cb_qita;
    @ViewInject(R.id.gv_test)
    private GridView mGridView;
    @ViewInject(R.id.rl_hand)
    private RelativeLayout rl_hand;
    @ViewInject(R.id.rl_camera)
    private RelativeLayout rl_camera;
    @ViewInject(R.id.tv_exchange_tuanbi)
    private TextView tv_exchange_tuanbi;
    @ViewInject(R.id.rl_gridview)
    private LinearLayout rl_gridView;
    @ViewInject(R.id.scroll_view)
    private ScrollView scroll_view;
    @ViewInject(R.id.rl_tishi)
    private RelativeLayout rl_tishi;
    @ViewInject(R.id.tv_cancel)
    private TextView tv_cancel;
    @ViewInject(R.id.rl_dialog_ios_7_root)
    private RelativeLayout mDialogRootRelativeLayout;
    @ViewInject(R.id.ll_dialog_ios_7_cotent)
    private LinearLayout mDialogCotentLinearLayout;
    @ViewInject(R.id.img_clean)
    private ImageView img_clean;
    public static final int REQUSET_RECORD = 1;
    public static final int REQUEST_GROUP = 2;

    private String rescord_id = "";
    private String select_group_id = "0";

    private String name = "";
    private String content = "";
    private String moibles = "";
    private String distributorid = "";
    private String chb = "2";

    private String phone_lists = "";
    private String state = "";
    private List<Map<String, String>> mDatas;
    private List<String> phones;
    private String str_phones = "";
    private String index = "";

    private GridViewAdapter gridViewAdapter;
    public static int FocusNum = 0;
    private Bitmap mipPicture = null;

    /**
     * 当前的图片路径
     */
    private String imagePath = null;
    private final static int REQUEST_CODE_GALLERY = 0x33;
    private final static int REQUEST_CODE_CAMERA = 0x44;
    private final static int MESSAGE_CODE = 0x55;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (gridViewAdapter != null) {
                gridViewAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            state = bundle.getString("state");
            if (state.equals("1")) {
                mDatas = new ArrayList<Map<String, String>>();
                phone_lists = bundle.getString("phones");
                if (phone_lists.contains(",")) {
                    String phones = phone_lists.replaceAll(" +", "").substring(1, phone_lists.replaceAll(" +", "").length() - 1);
                    String[] strs = phones.split(",");
                    for (int i = 0; i < strs.length; i++) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("num", strs[i] + "");
                        map.put("color", "black");
                        mDatas.add(map);
                    }
                    Map<String, String> map1 = new HashMap<String, String>();
                    map1.put("num", "");
                    map1.put("color", "red");
                    mDatas.add(map1);
                    FocusNum = mDatas.size() - 1;
                    gridViewAdapter = new GridViewAdapter(NewFreeSmsActivity.this, mDatas, handler);
                    mGridView.setAdapter(gridViewAdapter);
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("num", phone_lists.substring(1, phone_lists.length() - 1));
                    map.put("color", "black");
                    mDatas.add(map);
                    Map<String, String> map1 = new HashMap<String, String>();
                    map1.put("num", "");
                    map1.put("color", "red");
                    mDatas.add(map1);
                    FocusNum = mDatas.size() - 1;
                    gridViewAdapter = new GridViewAdapter(NewFreeSmsActivity.this, mDatas, handler);
                    mGridView.setAdapter(gridViewAdapter);
                }
            } else if (state.equals("0")) {
                mDatas = new ArrayList<Map<String, String>>();
                Map<String, String> map = new HashMap<String, String>();
                map.put("num", "");
                map.put("color", "red");
                mDatas.add(map);
                gridViewAdapter = new GridViewAdapter(NewFreeSmsActivity.this, mDatas, handler);
                mGridView.setAdapter(gridViewAdapter);
            }

            distributorid = PreferenceHelper.readString(NewFreeSmsActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
            et_sms_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            //改变默认的单行模式
            et_sms_content.setSingleLine(false);
            //水平滚动设置为False
            et_sms_content.setHorizontallyScrolling(false);
            et_sms_content.setHint("请输入短信内容");
            name = bundle.getString("name");
            content = bundle.getString("content");
            rescord_id = bundle.getString("id");

            if (name.equals("")) {
                tv_model_name.setText("选择模版");
            } else {
                tv_model_name.setText(name);
            }
            if (content.equals("")) {
                et_sms_content.setHint("请输入短信内容");
            } else {
                et_sms_content.setText(content);
            }
            if (rescord_id.equals("")) {
                rescord_id = "0";
            }

            cb_qita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cb_qita.setBackgroundResource((R.mipmap.cb_selected));
                        chb = "1";
                    } else {
                        cb_qita.setBackgroundResource((R.mipmap.cb_default));
                        chb = "2";
                    }
                }
            });
            persenter = new SendPersenter(this);
        }
    }

    // 声明一个Handler对象
    private static Handler handler_one = new Handler();
    SendPersenter persenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        ViewUtils.inject(this);
        tv_title.setText("群发短信");
        EventBus.getDefault().register(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        rl_publish.setVisibility(View.VISIBLE);
        tv_record.setText("记录");
        rl_hand.setBackgroundColor(getResources().getColor(R.color.bg_white));
        rl_gridView.setVisibility(View.VISIBLE);
        scroll_view.setVisibility(View.VISIBLE);
        rl_camera.setBackgroundColor(getResources().getColor(R.color.bg_sms_backround));
        rl_tishi.setVisibility(View.GONE);
        state = getTextFromBundle("state");
        index = getTextFromBundle("index");
        if (state.equals("1")) {
            mDatas = new ArrayList<Map<String, String>>();
            phone_lists = getTextFromBundle("phones");
            if (phone_lists.contains(",")) {
                String phones = phone_lists.replaceAll(" +", "").substring(1, phone_lists.replaceAll(" +", "").length() - 1);
                String[] strs = phones.split(",");
                for (int i = 0; i < strs.length; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("num", strs[i] + "");
                    map.put("color", "black");
                    mDatas.add(map);
                }
                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("num", "");
                map1.put("color", "red");
                mDatas.add(map1);
                FocusNum = mDatas.size() - 1;
                gridViewAdapter = new GridViewAdapter(NewFreeSmsActivity.this, mDatas, handler);
                mGridView.setAdapter(gridViewAdapter);
            } else {
                Map<String, String> map = new HashMap<String, String>();
                map.put("num", phone_lists.substring(1, phone_lists.length() - 1));
                map.put("color", "black");
                mDatas.add(map);
                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("num", "");
                map1.put("color", "red");
                mDatas.add(map1);
                FocusNum = mDatas.size() - 1;
                gridViewAdapter = new GridViewAdapter(NewFreeSmsActivity.this, mDatas, handler);
                mGridView.setAdapter(gridViewAdapter);
            }
        } else if (state.equals("0")) {
            mDatas = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            map.put("num", "");
            map.put("color", "red");
            mDatas.add(map);
            gridViewAdapter = new GridViewAdapter(NewFreeSmsActivity.this, mDatas, handler);
            mGridView.setAdapter(gridViewAdapter);
        }

        distributorid = PreferenceHelper.readString(NewFreeSmsActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        et_sms_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_sms_content.setSingleLine(false);
        //水平滚动设置为False
        et_sms_content.setHorizontallyScrolling(false);
        et_sms_content.setHint("请输入短信内容");

        name = getTextFromBundle("name");
        content = getTextFromBundle("content");
        rescord_id = getTextFromBundle("id");
        if (name.equals("")) {
            tv_model_name.setText("选择模版");
        } else {
            tv_model_name.setText(name);
        }
        if (content.equals("")) {
            et_sms_content.setHint("请输入短信内容");
        } else {
            et_sms_content.setText(content);
        }
        if (rescord_id.equals("")) {
            rescord_id = "0";
        }

        cb_qita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_qita.setBackgroundResource((R.mipmap.cb_selected));
                    chb = "1";
                } else {
                    cb_qita.setBackgroundResource((R.mipmap.cb_default));
                    chb = "2";
                }
            }
        });
        persenter = new SendPersenter(this);
    }
    private PermissionManager permissionManager;
    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick({R.id.rl_back, R.id.rl_publish, R.id.rl_model, R.id.rl_select, R.id.tv_send, R.id.rl_hand, R.id.rl_camera, R.id.tv_exchange_tuanbi
            , R.id.tv_cancel, R.id.tv_select_camera, R.id.tv_select_gallery, R.id.img_clean})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                /*Bundle bundle = new Bundle();
                bundle.putString("selection_postion", "0");
                openActivity(HomeActivity.class, bundle);*/
                finish();
                break;
            case R.id.rl_publish:
                openActivity(SendSmsRecordActivity.class);
                break;
            case R.id.rl_model:
                Intent intent = new Intent(NewFreeSmsActivity.this, SmsModelActivity.class);
                startActivityForResult(intent, REQUSET_RECORD);
                break;
            case R.id.rl_select:
                Intent intent_ = new Intent(NewFreeSmsActivity.this, SelectGroupActivity.class);
                startActivityForResult(intent_, REQUEST_GROUP);
                break;
            case R.id.tv_send:
                getPhone(mDatas);
                String none_blank = phones.toString().replaceAll(" +", "");
                String model_ = tv_model_name.getText().toString().trim();
                String content = et_sms_content.getText().toString().trim();
                if (model_.equals("选择模版")) {
                    MyToast.show(NewFreeSmsActivity.this, "请选择模版");
                    return;
                } else if (content.equals("")) {
                    MyToast.show(NewFreeSmsActivity.this, "请输入短信内容");
                    return;
                } else if (phones.size() == 0) {
                    MyToast.show(NewFreeSmsActivity.this, "请输入手机号码");
                    return;
                } else {
                    tv_send.setEnabled(false);
                    showProgress();
                    persenter.sendSms();
                }
                break;
            case R.id.rl_camera:
                rl_camera.setBackgroundColor(getResources().getColor(R.color.bg_white));
                rl_hand.setBackgroundColor(getResources().getColor(R.color.bg_sms_backround));
                rl_tishi.setVisibility(View.VISIBLE);
                rl_gridView.setVisibility(View.GONE);
                scroll_view.setVisibility(View.GONE);
                break;
            case R.id.rl_hand:
                rl_camera.setBackgroundColor(getResources().getColor(R.color.bg_sms_backround));
                rl_hand.setBackgroundColor(getResources().getColor(R.color.bg_white));
                rl_tishi.setVisibility(View.GONE);
                rl_gridView.setVisibility(View.VISIBLE);
                scroll_view.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_exchange_tuanbi:
                openDialog();
                break;
            case R.id.tv_cancel:
                closeDialog();
                break;
            case R.id.tv_select_gallery:
                closeDialog();
              /*  FunctionUtils.chooseImageFromGallery(NewFreeSmsActivity.this,
                        REQUEST_CODE_GALLERY);*/
                // 权限管理工具类
                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                    @Override
                    public void permissionGranted(int requestCode) {
                        if (requestCode == 99) {
                            PhotoPickerIntent intent = new PhotoPickerIntent(NewFreeSmsActivity.this);
                            intent.setPhotoCount(1);
                            intent.setShowCamera(true);
                            intent.setShowGif(true);
                            startActivityForResult(intent, 101);
                        }
                    }
                });
                //申请读权限，和照相机权限
                permissionManager.checkPermission(99, NewFreeSmsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                break;
            case R.id.tv_select_camera:
                closeDialog();
                imagePath = FunctionUtils.chooseImageFromCamera(
                        NewFreeSmsActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
                break;
            case R.id.img_clean:
                gridViewAdapter.cleanAll();
                mDatas = new ArrayList<Map<String, String>>();
                Map<String, String> map = new HashMap<String, String>();
                map.put("num", "");
                map.put("color", "red");
                mDatas.add(map);
                gridViewAdapter = new GridViewAdapter(NewFreeSmsActivity.this, mDatas, handler);
                mGridView.setAdapter(gridViewAdapter);
                break;
        }
    }


    // 弹出拍照对话框
    private void openDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, true);
    }

    // 关闭拍照对话框
    private void closeDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, false);

    }
    private ArrayList<String> data_list = new ArrayList<String>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NewFreeSmsActivity.REQUSET_RECORD && resultCode == RESULT_OK) {
            rescord_id = data.getStringExtra(SmsModelActivity.KEY_ID);
            tv_model_name.setText(data.getStringExtra(SmsModelActivity.KEY_NAME));
            et_sms_content.setText(data.getStringExtra(SmsModelActivity.KEY_CONTENT));
        } else if (requestCode == NewFreeSmsActivity.REQUEST_GROUP && resultCode == RESULT_OK) {
            et_new.setText(data.getStringExtra(SelectGroupActivity.KEY_GROUP_NAME));
            select_group_id = data.getStringExtra(SelectGroupActivity.KEY_GROUP_ID);
            moibles = data.getStringExtra(SelectGroupActivity.KEY_GROUP_MOBILES);
        }
        if (resultCode == -1) {
            Bitmap resultBitmap = null;
            if (resultBitmap != null) {
                resultBitmap.recycle();
            }
            switch (requestCode) {
                case 101:
                    if (data != null) {
                        data_list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                        imagePath = data_list.get(0);
                    }
//                    imagePath = FunctionUtils
//                            .onActivityResultForChooseImageFromGallery(NewFreeSmsActivity.this,
//                                    requestCode, resultCode, data);
                    if (!StringUtils.isEmpty(imagePath)) {
                        showLoadingProgressDialog(NewFreeSmsActivity.this, "");
                        mipPicture = imageReceive(imagePath);
                        rl_hand.setBackgroundColor(getResources().getColor(R.color.bg_white));
                        rl_gridView.setVisibility(View.VISIBLE);
                        scroll_view.setVisibility(View.VISIBLE);
                        rl_camera.setBackgroundColor(getResources().getColor(R.color.bg_sms_backround));
                        rl_tishi.setVisibility(View.GONE);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handler_one.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        analyzePhone(Tesseract.getText(NewFreeSmsActivity.this, mipPicture));
                                    }
                                });
                            }
                        }).start();
                    }
                    break;
                case REQUEST_CODE_CAMERA:
                    if (!StringUtils.isEmpty(imagePath)) {
                        showLoadingProgressDialog(NewFreeSmsActivity.this, "");
                        mipPicture = imageReceive(imagePath);
                        rl_hand.setBackgroundColor(getResources().getColor(R.color.bg_white));
                        rl_gridView.setVisibility(View.VISIBLE);
                        scroll_view.setVisibility(View.VISIBLE);
                        rl_camera.setBackgroundColor(getResources().getColor(R.color.bg_sms_backround));
                        rl_tishi.setVisibility(View.GONE);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handler_one.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        analyzePhone(Tesseract.getText(NewFreeSmsActivity.this, mipPicture));
                                    }
                                });
                            }
                        }).start();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void getPhone(List<Map<String, String>> mDatas) {
        phones = new ArrayList<String>();
        for (int i = 0; i < mDatas.size(); i++) {
            Map<String, String> map = mDatas.get(i);
            String phone = map.get("num");
            if (StringUtils.isPhone(phone)) {
                phones.add(phone);
            }
        }
    }

    public void analyzePhone(String str) {
        closeLoadingProgressDialog();
        if (!str.equals("[]")) {
            phone_lists = str.replaceAll(" +", "").substring(1, str.replaceAll(" +", "").length() - 1);
            if (phone_lists.contains(",")) {
                String[] strs = phone_lists.split(",");
                for (int i = 0; i < strs.length; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("num", strs[i] + "");
                    map.put("color", "black");
                    mDatas.add(map);
                }
                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("num", "");
                map1.put("color", "red");
                mDatas.add(map1);
                FocusNum = mDatas.size() - 1;
                gridViewAdapter = new GridViewAdapter(NewFreeSmsActivity.this, mDatas, handler);
                mGridView.setAdapter(gridViewAdapter);
            } else {
                Map<String, String> map = new HashMap<String, String>();
                map.put("num", phone_lists.substring(1, phone_lists.length() - 1));
                map.put("color", "black");
                mDatas.add(map);
                Map<String, String> map2 = new HashMap<String, String>();
                map2.put("num", "");
                map2.put("color", "red");
                mDatas.add(map2);
                FocusNum = mDatas.size() - 1;
                gridViewAdapter = new GridViewAdapter(NewFreeSmsActivity.this, mDatas, handler);
                mGridView.setAdapter(gridViewAdapter);
            }
        } else {
            MyToast.show(NewFreeSmsActivity.this, "没有解析到电话号码");
        }
    }


    @Override
    public void showProgress() {
        showLoadingProgressDialog(NewFreeSmsActivity.this, "");
    }

    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }

    @Override
    public void excuteSuccessCallBack(String s) {
        Gson gosn = new Gson();
        CallBackVo mCallBackVo = gosn.fromJson(s, SmsResult.class);
        SmsResult smsResult = (SmsResult) mCallBackVo;
        String status = smsResult.getStatus();
        if (status.equals("1")) {
            tv_send.setEnabled(true);
            MyToast.show(NewFreeSmsActivity.this, "发送成功");
            closeLoadingProgressDialog();
            finish();
        }
    }

    @Override
    public void excuteFailedCallBack(String s) {
        closeLoadingProgressDialog();
        tv_send.setEnabled(true);
        showHintDialog(s);
    }

    /**
     * 接收event事件
     *
     * @param event
     */
    public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.SMS_DETETE) {
            if (rescord_id.equals(event.getExtraData() + "")) {
                et_sms_content.setText("");
                tv_model_name.setText("选择模版");
            }
        }
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        persenter.attach(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        persenter.dettach();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*Bundle bundle = new Bundle();
        bundle.putString("selection_postion", "0");
        openActivity(HomeActivity.class, bundle);*/
        finish();
    }

    public Bitmap imageReceive(String image_path) {
        Bitmap bmp = null;
        try {
            // get the screen size
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int dw = dm.widthPixels;
            int dh = dm.heightPixels;
            // Load up the image's dimensions not the image itself
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            bmp = BitmapFactory.decodeFile(image_path, bmpFactoryOptions);
            int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
                    / (float) dh);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                    / (float) dw);
            // If both of the ratios are greater than 1,
            // one of the sides of the image is greater than the screen
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    // Height ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    // Width ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }
            bmpFactoryOptions.inJustDecodeBounds = false; // Decode it for real
            bmp = BitmapFactory.decodeFile(image_path, bmpFactoryOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }


    @Override
    public SmsBean getParamenters() {
        String none_blank = phones.toString().replaceAll(" +", "");
        String phone_ = none_blank.substring(1, none_blank.length() - 1);
        String content_sms = et_sms_content.getText().toString().trim();
        String sign_ = TGmd5.getMD5(distributorid + rescord_id + "0" + "" + content_sms + phone_ + chb);
        SmsBean smsBean = new SmsBean();
        smsBean.setDistributorid(distributorid);
        smsBean.setTmpid(rescord_id);
        smsBean.setGroupid("0");
        smsBean.setGroupname("");
        smsBean.setContent(content_sms);
        smsBean.setChb(chb);
        smsBean.setMobiles(phone_);
        smsBean.setSign(sign_);
        return smsBean;
    }

}
