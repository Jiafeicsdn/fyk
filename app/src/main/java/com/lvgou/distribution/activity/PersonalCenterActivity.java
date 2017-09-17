package com.lvgou.distribution.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.presenter.PersonalPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.PermissionManager;
import com.lvgou.distribution.utils.PictureUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.Tools;
import com.lvgou.distribution.view.PersonalView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by Administrator on 2017/3/23.
 * 个人中心
 */

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener, PersonalView {
    private PersonalPresenter personalPresenter;
    private String star = "";
    private String personalSign;
    private String account_info = "";
    private String usetisover;
    private String userstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        personalPresenter = new PersonalPresenter(this);
        initView();
        initClick();
    }

    private String distributorid = "";

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        usetisover = PreferenceHelper.readString(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
        userstate = PreferenceHelper.readString(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
        distributorid = PreferenceHelper.readString(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, "0");
        String sign = TGmd5.getMD5(distributorid);
        personalPresenter.getData(distributorid, sign);
        personalPresenter.attach(this);
        String sex = PreferenceHelper.readString(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SEX);
        if ("1".equals(sex)) {
            im_sex.setImageResource(R.mipmap.sex_boy_icon);
        } else if ("2".equals(sex)) {
            im_sex.setImageResource(R.mipmap.sex_girl_icon);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        personalPresenter.dettach();
        MobclickAgent.onPause(this);

    }

    private ImageView im_header;//用户头像
    private RelativeLayout rl_personal_mail;//邮件消息
    private RelativeLayout rl_personal_home;//个人主页
    private ImageView im_camera;//相机
    private TextView tv_name;//用户名
    private ImageView im_sex;//性别
    private ImageView im_level;//等级
    private RelativeLayout rl_invitation;//邀请好友
    private RelativeLayout rl_course;//团币课程
    private RelativeLayout rl_task;//团币任务
    private RelativeLayout rl_my_course;//我的课程
    private RelativeLayout rl_my_activity;//我的活动
    private RelativeLayout rl_my_collect;//我的收藏
    //    private RelativeLayout rl_guide;//新手指南
    private RelativeLayout rl_setting;//设置
    private RelativeLayout tl_feedback;//新手反馈
    private RelativeLayout rl_picture;//头像
    private TextView tv_message_num;//邮件那的数字
    private RelativeLayout rl_money_manager;//收支管理
    private TextView tv_money;//账户金额
    private RelativeLayout rl_myorder;//我的订单
    private TextView tv_class_num;//课程未读消息

    private void initView() {
        tv_class_num = (TextView) findViewById(R.id.tv_class_num);
        im_header = (ImageView) findViewById(R.id.im_header);
        rl_personal_mail = (RelativeLayout) findViewById(R.id.rl_personal_mail);
        rl_personal_home = (RelativeLayout) findViewById(R.id.rl_personal_home);
        im_camera = (ImageView) findViewById(R.id.im_camera);
        tv_name = (TextView) findViewById(R.id.tv_name);
        im_sex = (ImageView) findViewById(R.id.im_sex);
        im_level = (ImageView) findViewById(R.id.im_level);
        rl_invitation = (RelativeLayout) findViewById(R.id.rl_invitation);
        rl_course = (RelativeLayout) findViewById(R.id.rl_course);
        rl_task = (RelativeLayout) findViewById(R.id.rl_task);
        rl_my_course = (RelativeLayout) findViewById(R.id.rl_my_course);
        rl_my_activity = (RelativeLayout) findViewById(R.id.rl_my_activity);
        rl_my_collect = (RelativeLayout) findViewById(R.id.rl_my_collect);
//        rl_guide = (RelativeLayout) findViewById(R.id.rl_guide);
        rl_setting = (RelativeLayout) findViewById(R.id.rl_setting);
        tl_feedback = (RelativeLayout) findViewById(R.id.tl_feedback);
        rl_picture = (RelativeLayout) findViewById(R.id.rl_picture);
        tv_message_num = (TextView) findViewById(R.id.tv_message_num);
        rl_money_manager = (RelativeLayout) findViewById(R.id.rl_money_manager);
        tv_money = (TextView) findViewById(R.id.tv_money);
        rl_myorder = (RelativeLayout) findViewById(R.id.rl_myorder);

    }

    private void initClick() {
        rl_personal_mail.setOnClickListener(this);
        rl_personal_home.setOnClickListener(this);
        im_camera.setOnClickListener(this);
        rl_invitation.setOnClickListener(this);
        rl_course.setOnClickListener(this);
        rl_task.setOnClickListener(this);
        rl_my_course.setOnClickListener(this);
        rl_my_activity.setOnClickListener(this);
        rl_my_collect.setOnClickListener(this);
//        rl_guide.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        tl_feedback.setOnClickListener(this);
        rl_picture.setOnClickListener(this);
        im_level.setOnClickListener(this);
        rl_money_manager.setOnClickListener(this);
        rl_myorder.setOnClickListener(this);
    }

    private boolean isRZDialog(String state, String isover) {
        if (state.equals("1")) {
            //没有认证
            return false;
        } else if (state.equals("6")) {
            //审核不通过
            return false;
        } else if (state.equals("5") && isover.equals("false")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("2") || state.equals("3")) {
            //认证了，完善消息
            return false;
        } else if (state.equals("4")) {
            //审核中
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.rl_personal_mail://邮件点击
                openActivity(MyMessageActivity.class);
                break;
            case R.id.rl_personal_home://个人主页点击
                /*bundle.putInt("distributorid", Integer.parseInt(distributorid));
                if ("1".equals(userType)) {
                    bundle.putString("seeDistributorId", distributorid);
                    openActivity(OfficialHomePageActivity.class, bundle);
                } else {
                    openActivity(UserPersonalCenterActivity.class, bundle);
                }*/
                if (isRZDialog(userstate, usetisover)) {
                    if (userType.equals("3")) {
                        //如果是讲师
                        Intent intent1 = new Intent(PersonalCenterActivity.this, TeacherHomeActivity.class);
                        intent1.putExtra("seeDistributorId", distributorid);
                        startActivity(intent1);
                    } else {
                        //普通导游
                        Intent intent1 = new Intent(PersonalCenterActivity.this, DistributorHomeActivity.class);
                        intent1.putExtra("seeDistributorId", distributorid);
                        startActivity(intent1);
                    }
                } else {
                    showRZDialog(userstate, usetisover);
                }


                break;
            case R.id.rl_picture:
            case R.id.im_camera://更换头像
                showDialog();
                break;
            case R.id.rl_invitation://邀请好友
                if (isRZDialog(userstate, usetisover)) {
                    openActivity(InviteFrendsActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }

                break;
            case R.id.rl_course://团币商城
                if (isRZDialog(userstate, usetisover)) {
                    openActivity(TuanbiShopActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }

                break;
            case R.id.rl_task://团币任务
                if (isRZDialog(userstate, usetisover)) {
                    openActivity(MyTuanBiActivity.class);
//                openActivity(TuanbiMangerActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }

                break;
            case R.id.rl_my_course://我的课程
                if (isRZDialog(userstate, usetisover)) {
                    openActivity(MyCourseActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }
                break;
            case R.id.rl_my_activity://我的活动
                if (isRZDialog(userstate, usetisover)) {
                    openActivity(MyAcActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }

                break;
            case R.id.rl_my_collect://我的收藏
                if (isRZDialog(userstate, usetisover)) {
//                openActivity(MyCollectActivity.class);
                    openActivity(MyCollectionActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }

                break;
           /* case R.id.rl_guide://新手指南
                if (isRZDialog(userstate, usetisover)) {
                    openActivity(OpreatGuideActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }

                break;*/
            case R.id.rl_setting://设置
                openActivity(SettingsActivity.class);
                break;
            case R.id.tl_feedback://反馈
                if (isRZDialog(userstate, usetisover)) {
                    openActivity(FeedBackActivity.class);
                } else {
                    showRZDialog(userstate, usetisover);
                }

                break;
            case R.id.im_level://我的等级
                if (isRZDialog(userstate, usetisover)) {
                    Intent intent = new Intent(PersonalCenterActivity.this, MyLevelActivity.class);
                    intent.putExtra("melevel", star);
                    startActivity(intent);
                } else {
                    showRZDialog(userstate, usetisover);
                }

                break;
            case R.id.rl_money_manager://收支管理
                if (isRZDialog(userstate, usetisover)) {
                    bundle.putString("account", account_info);
                    openActivity(IncomeMangerActivity.class, bundle);
                } else {
                    showRZDialog(userstate, usetisover);
                }

                break;
            case R.id.rl_myorder://我的订单
                if (isRZDialog(userstate, usetisover)) {
                    bundle.putString("index", "0");
                    bundle.putString("tab", "5");
                    openActivity(OrderCentralActivity.class, bundle);
                } else {
                    showRZDialog(userstate, usetisover);
                }

                break;
        }
    }

    private PermissionManager permissionManager;

    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private final static int REQUEST_CODE_CAMERA = 0x44;
    private final static int REQUEST_CODE_SHOW = 0x55;

    /**
     * 上传头像选择弹框
     */
    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_mian_head, null);
        final Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT));
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(PersonalCenterActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
//                                FunctionUtils.chooseImageFromGallery(PersonalCentralActivity.this, REQUEST_CODE_GALLERY);
                                // 权限管理工具类
                                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                                    @Override
                                    public void permissionGranted(int requestCode) {
                                        if (requestCode == 99) {
                                            PhotoPickerIntent intent = new PhotoPickerIntent(PersonalCenterActivity.this);
                                            intent.setPhotoCount(1);
                                            intent.setShowCamera(true);
                                            intent.setShowGif(true);
                                            startActivityForResult(intent, 101);
                                        }
                                    }
                                });
                                //申请读权限，和照相机权限
                                permissionManager.checkPermission(99, PersonalCenterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);

                            }

                            @Override
                            public void onDenied(String permission) {
                            }
                        }
                );
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(PersonalCenterActivity.this,
                        new String[]{Manifest.permission.CAMERA}, new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                imagePath = FunctionUtils.chooseImageFromCamera(
                                        PersonalCenterActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
                            }

                            @Override
                            public void onDenied(String permission) {
                            }
                        }
                );
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private ArrayList<String> data_list = new ArrayList<String>();
    private String imagePath = null;
    private String upload_path = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 11) {
            if (CutActivity.bitmap == null) {
                return;
            }
            showLoadingProgressDialog(PersonalCenterActivity.this, "");

            // 上传头像
            Bitmap bit_head = PictureUtil.getSmallBitmap(Tools.SavePhoto(CutActivity.bitmap, "a.jpg"));
            upload_path = Tools.SavePhoto(bit_head, "avator.jpg");
            String sign = TGmd5.getMD5(distributorid);
            uploadHead(distributorid, upload_path, sign);


        } else if (resultCode == 10) {
//            FunctionUtils.chooseImageFromGallery(PersonalCentralActivity.this, REQUEST_CODE_GALLERY);

            // 权限管理工具类
            permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                @Override
                public void permissionGranted(int requestCode) {
                    if (requestCode == 99) {
                        PhotoPickerIntent intent = new PhotoPickerIntent(PersonalCenterActivity.this);
                        intent.setPhotoCount(1);
                        intent.setShowCamera(true);
                        intent.setShowGif(true);
                        startActivityForResult(intent, 101);
                    }
                }
            });
            //申请读权限，和照相机权限
            permissionManager.checkPermission(99, PersonalCenterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
        } else if (resultCode == 9) {
            FunctionUtils.chooseImageFromCamera(PersonalCenterActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
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
//                            .onActivityResultForChooseImageFromGallery(PersonalCentralActivity.this, requestCode, resultCode, data);
                    if (!StringUtils.isEmpty(imagePath)) {
                        /**
                         *需要修改，路径为空，默认图片记得添加
                         */
                        Bundle bundle = new Bundle();
                        bundle.putString("img_path", imagePath);
                        bundle.putString("code_", "1");
                        Intent intent = new Intent(PersonalCenterActivity.this, CutActivity.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, REQUEST_CODE_SHOW);
                    }


                    break;
                case REQUEST_CODE_CAMERA:
                    if (!StringUtils.isEmpty(imagePath)) {
                        Intent intent = new Intent(PersonalCenterActivity.this, CutActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("code_", "2");
                        bundle.putString("img_path", imagePath);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, REQUEST_CODE_SHOW);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    DisplayImageOptions optionsone;

    /**
     * 头像上传
     *
     * @param distributorid
     * @param path
     * @param sign
     */
    public void uploadHead(String distributorid, String path, String sign) {
        File file = new File(path);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("picurl", file);
        maps.put("sign", sign);

        RequestTask.getInstance().doUploadHead(PersonalCenterActivity.this, maps, new OnHeadRequestListener());
    }

    private class OnHeadRequestListener extends OnRequestListenerAdapter<Object> {
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
                    optionsone = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.head_default)            // 设置图片下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.head_default)    // 设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.head_default)            // 设置图片加载或解码过程中发生错误显示的图片
                            .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                            .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                            .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                            .build();
//                    Glide.with(PersonalCenterActivity.this).load(ImageUtils.getInstance().getPath(distributorid)).error(R.mipmap.teacher_default_head).into(im_header);
                    ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(distributorid), im_header, optionsone);
                    MyToast.show(PersonalCenterActivity.this, "上传成功");
                } else if (status.equals("0")) {
                    MyToast.show(PersonalCenterActivity.this, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private String userType = "";
    private String isRenZheng = "";
    DisplayImageOptions options;

    @Override
    public void OnRequestSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1://获取个人中心数据
                try {
                    /**
                     * 获取未读消息数
                     */
                    String sign_one = TGmd5.getMD5(distributorid + "1");
                    personalPresenter.getMessageNum(distributorid, "1", sign_one);

                    JSONObject jsb = new JSONObject(respanse);
                    JSONArray jsa = new JSONArray(jsb.getString("result"));
                    String info6 = jsa.get(6).toString();
                    account_info = jsa.get(4).toString();
                    DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                    tv_money.setText("￥" + decimalFormat.format(Float.parseFloat(jsa.get(5).toString())));
                    PreferenceHelper.write(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, info6);
                    JSONObject jsonObject = jsa.getJSONObject(0);
                    tv_name.setText(jsonObject.get("RealName").toString());

                    options = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.icon_none_bee)            // 设置图片下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.icon_none_bee)    // 设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.icon_none_bee)            // 设置图片加载或解码过程中发生错误显示的图片
                            .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                            .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                            .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                            .build();
                    Log.e("skjdafjhas", "-----------" + ImageUtils.getInstance().getPath(distributorid));
                    ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(distributorid), im_header, options);
//                    Glide.with(PersonalCenterActivity.this).load(ImageUtils.getInstance().getPath(distributorid)).error(R.mipmap.teacher_default_head).into(im_header);
                    userType = jsonObject.get("UserType").toString();
                    isRenZheng = jsonObject.get("State").toString();
                    star = jsonObject.get("Star").toString();
                    String realName = jsonObject.get("RealName").toString();
                    PreferenceHelper.write(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STAR, star);
                    PreferenceHelper.write(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, isRenZheng);
                    PreferenceHelper.write(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.REAL_NAME, realName);
                    if (isRenZheng.equals("7")) {
                        loginOut();
                        openActivity(LoginActivity.class);
                        PersonalCenterActivity.this.finish();
                    }
                    personalSign = jsa.get(3).toString();
                    mcache.put("personalsign", personalSign);
                    if (star.equals("0")) {
                        //im_level
                        im_level.setVisibility(View.GONE);
                    } else if (star.equals("1")) {
                        im_level.setVisibility(View.VISIBLE);
                        im_level.setBackgroundResource(R.mipmap.level_one_icon);
                    } else if (star.equals("2")) {
                        im_level.setVisibility(View.VISIBLE);
                        im_level.setBackgroundResource(R.mipmap.level_two_icon);
                    } else if (star.equals("3")) {
                        im_level.setVisibility(View.VISIBLE);
                        im_level.setBackgroundResource(R.mipmap.level_three_icon);
                    } else if (star.equals("4")) {
                        im_level.setVisibility(View.VISIBLE);
                        im_level.setBackgroundResource(R.mipmap.level_four_icon);
                    } else if (star.equals("5")) {
                        im_level.setVisibility(View.VISIBLE);
                        im_level.setBackgroundResource(R.mipmap.level_five_icon);
                    } else if (star.equals("6")) {
                        im_level.setVisibility(View.VISIBLE);
                        im_level.setBackgroundResource(R.mipmap.level_six_icon);
                    } else if (star.equals("7")) {
                        im_level.setVisibility(View.VISIBLE);
                        im_level.setBackgroundResource(R.mipmap.level_seven_icon);
                    } else if (star.equals("8")) {
                        im_level.setVisibility(View.VISIBLE);
                        im_level.setBackgroundResource(R.mipmap.level_eight_icon);
                    } else if (star.equals("9")) {
                        im_level.setVisibility(View.VISIBLE);
                        im_level.setBackgroundResource(R.mipmap.level_nine_icon);
                    } else if (star.equals("10")) {
                        im_level.setVisibility(View.VISIBLE);
                        im_level.setBackgroundResource(R.mipmap.level_ten_icon);
                    }
            /*if (jsa != null && !jsa.equals("")) {
                actDetailList.clear();
                JSONArray jsonArray = jsa.getJSONArray(0);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsoo = jsonArray.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    actDetailList.add(map1);
                }
                Log.e("jhaskfsas", "------- " + actDetailList);
            }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3://获取未读数量
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {

                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String commentNum = array.get(0).toString();
                        String dianzanNum = array.get(1).toString();
                        int classNum = (int) array.get(3);
                        int systemNum = (int) array.get(4);
                        int fengyouNum = (int) array.get(5);
                        PreferenceHelper.write(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, commentNum);
                        PreferenceHelper.write(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ZAN_NUM, dianzanNum);
                        PreferenceHelper.write(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CENTER_NUMBER, String.valueOf(classNum + systemNum + fengyouNum));

                        String total_num = String.valueOf(systemNum + fengyouNum);
                        PreferenceHelper.write(PersonalCenterActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.MESSAGE_NUM, total_num + "");

                        if ("0".equals(total_num)) {
                            tv_message_num.setVisibility(View.GONE);
                        } else {
                            tv_message_num.setVisibility(View.VISIBLE);
//                            tv_message_num.setText(total_num);
                        }

                        if (classNum > 0) {
                            tv_class_num.setText("您有未学习课程");
                            tv_class_num.setTextColor(Color.parseColor("#fc4d30"));
                        } else {
                            tv_class_num.setText("暂没有未学习课程");
                            tv_class_num.setTextColor(Color.parseColor("#bababa"));
                        }
                        /**
                         * 更新蜂圈的角标
                         */
                        EventFactory.updateHomeCircle("0");
                        /**
                         * 更新我的角标
                         */
                        EventFactory.updateHomeCenter("0");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 退出登录
     */
    public void loginOut() {
        PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_DISTRIBUTORID, "");
        PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, "");
        PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, "");
        PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.HAS_LOGGED, "false");
        Constants.COUNTRYPATH = "";
        Constants.TOTAL_ADDRESS = "";
    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void OnRequestFialCallBack(String state, String respanse) {

    }
}
