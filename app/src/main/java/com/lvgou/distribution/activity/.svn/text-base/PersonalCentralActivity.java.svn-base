package com.lvgou.distribution.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
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
import com.xdroid.utils.AppManager;

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
 * Created by Snow on 2016/3/9
 * 个人中心
 */
public class PersonalCentralActivity extends BaseActivity implements PersonalView {

    @ViewInject(R.id.rl_set)
    private RelativeLayout rl_set;
    @ViewInject(R.id.rl_index)
    private RelativeLayout rl_index;
    @ViewInject(R.id.img_head)
    private ImageView img_head;
//    @ViewInject(R.id.img_teacher_icon)
//    private ImageView img_teacher_icon;
    @ViewInject(R.id.tv_guider_name)
    private TextView tv_name;
//    @ViewInject(R.id.rl_renzheng)
//    private RelativeLayout rl_renzheng;
//    @ViewInject(R.id.img_left_icon)
//    private ImageView img_renzheng_icon;
    @ViewInject(R.id.tv_renzheng_state)
    private TextView tv_renzheng_state;
//    @ViewInject(R.id.img_right_icon)
//    private ImageView img_renzheng_next;
//    @ViewInject(R.id.img_right_icon)
//    private ImageView img_right_icon;
    @ViewInject(R.id.rl_fengwen)
    private RelativeLayout rl_fengwen;
    @ViewInject(R.id.tv_fengwen_num)
    private TextView tv_fengwen_num;
    @ViewInject(R.id.rl_guanzhu)
    private RelativeLayout rl_guanzhu;
    @ViewInject(R.id.tv_guanzhu_num)
    private TextView tv_guanzhu_num;
    @ViewInject(R.id.rl_fans)
    private RelativeLayout rl_fans;
    @ViewInject(R.id.tv_fans_num)
    private TextView tv_fans_num;
    @ViewInject(R.id.rl_class)
    private RelativeLayout rl_class;
    @ViewInject(R.id.tv_classes_num)
    private TextView tv_class_num;
    @ViewInject(R.id.rl_my_messages)
    private RelativeLayout rl_my_messages;
    @ViewInject(R.id.tv_message_num)
    private TextView tv_message_num;
    @ViewInject(R.id.rl_my_class)
    private RelativeLayout rl_my_class;
    @ViewInject(R.id.tv_new_class)
    private TextView tv_new_class;
    @ViewInject(R.id.rl_tuanbi)
    private RelativeLayout rl_tuanbi;
    @ViewInject(R.id.tv_tuanbi)
    private TextView tv_tuanbi;
    @ViewInject(R.id.rl_income_manager)
    private RelativeLayout rl_income_manager;
    @ViewInject(R.id.tv_profit)
    private TextView tv_profit;
    @ViewInject(R.id.rl_new_guider)
    private RelativeLayout rl_new_guider;
    @ViewInject(R.id.rl_about_us)
    private RelativeLayout rl_about_us;
    @ViewInject(R.id.all_order)
    private RelativeLayout all_order;
    @ViewInject(R.id.img_apply_class)
    private ImageView img_apply_class;
    @ViewInject(R.id.img_sex)
    private ImageView igm_sex;
@ViewInject(R.id.rl_my_collect)
private RelativeLayout rl_my_collect;

    /**
     * 当前的图片路径
     */
    private String imagePath = null;
    private final static int REQUEST_CODE_GALLERY = 0x33;
    private final static int REQUEST_CODE_CAMERA = 0x44;
    private final static int REQUEST_CODE_SHOW = 0x55;
    DisplayImageOptions options;
    DisplayImageOptions optionsone;
    private String upload_path = "";

    private String isRenZheng = "";
    private String account_info = "";
    private String distributorid = "";

    private String commentNum = "0";
    private String dianzanNum = "0";
    private String info_num = "0";

    private PersonalPresenter personalPresenter;

    private String userType;

    private String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_central);
        ViewUtils.inject(this);
        personalPresenter = new PersonalPresenter(this);

    }

    @OnClick({R.id.rl_my_collect,R.id.rl_set, R.id.rl_index, R.id.img_head, R.id.tv_renzheng_state, R.id.rl_fengwen, R.id.rl_guanzhu, R.id.rl_fans, R.id.rl_class,
            R.id.rl_my_messages, R.id.rl_my_class, R.id.rl_tuanbi, R.id.rl_income_manager, R.id.rl_new_guider, R.id.rl_about_us, R.id.all_order
            , R.id.img_apply_class})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_my_collect:
                openActivity(MyCollectActivity.class);
                break;
            case R.id.rl_set:
                bundle.putString("state", isRenZheng);
                openActivity(SetActivity.class, bundle);
                break;
            case R.id.rl_index:
                bundle.putInt("distributorid", Integer.parseInt(distributorid));
                if ("1".equals(userType)) {
                    bundle.putString("seeDistributorId", distributorid);
                    openActivity(OfficialHomePageActivity.class,bundle);
                } else {
                    openActivity(UserPersonalCenterActivity.class, bundle);
                }
                break;
            case R.id.img_head:
                showDialog();
                break;
            case R.id.tv_renzheng_state:
                if (isRenZheng.equals("2") || isRenZheng.equals("4") || isRenZheng.equals("5") || isRenZheng.equals("6")) {
                    bundle.putString("index", "0");
                    bundle.putString("state", isRenZheng);
                    openActivity(AuthenticationActivity.class, bundle);
                } else if (isRenZheng.equals("1") || isRenZheng.equals("3")) {
                    bundle.putString("state", isRenZheng);
                    openActivity(GuideCradMnagerActivity.class, bundle);
                }
                break;
            case R.id.rl_fengwen:
                openActivity(MyFengwenAcitivity.class);
                break;
            case R.id.rl_guanzhu:
                bundle.putString("type", "2");
                bundle.putString("seeDistributorId", distributorid);
                openActivity(FansFollowListActivity.class, bundle);
                break;
            case R.id.rl_fans:
                bundle.putString("seeDistributorId", distributorid);
                openActivity(MyFansListActivity.class, bundle);
                break;
            case R.id.rl_class:
                openActivity(PersonalClassesActivity.class);
                break;
            case R.id.rl_my_messages:
                openActivity(MyMessageActivity.class);
                break;
            case R.id.rl_new_guider:
                openActivity(OpreatGuideActivity.class);
                break;
            case R.id.rl_my_class:
                openActivity(MyClassesActivity.class);
                break;
            case R.id.rl_tuanbi:
                openActivity(TuanbiMangerActivity.class);
                break;
            case R.id.rl_income_manager:
                bundle.putString("account", account_info);
                openActivity(IncomeMangerActivity.class, bundle);
                break;
            case R.id.rl_about_us:
                openActivity(AboutTugouActivity.class);
                break;
            case R.id.all_order:
                bundle.putString("index", "0");
                bundle.putString("tab", "5");
                openActivity(OrderCentralActivity.class, bundle);
                break;
            case R.id.img_apply_class:
                bundle.putString("name", userName);
                openActivity(ApplyClassActivity.class, bundle);
                break;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        distributorid = PreferenceHelper.readString(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, "0");
        String sign = TGmd5.getMD5(distributorid);
        personalPresenter.getData(distributorid, sign);


        personalPresenter.attach(this);

        String sex = PreferenceHelper.readString(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.SEX);
        if ("1".equals(sex)) {
            igm_sex.setImageResource(R.mipmap.personal_nan);
        } else if ("2".equals(sex)) {
            igm_sex.setImageResource(R.mipmap.personal_nv);
        }

    }

    public void onPause() {
        super.onPause();
        personalPresenter.dettach();
        MobclickAgent.onPause(this);

    }


    private PermissionManager permissionManager;
    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private ArrayList<String> data_list = new ArrayList<String>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 11) {
            if (CutActivity.bitmap == null) {
                return;
            }
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
                        PhotoPickerIntent intent = new PhotoPickerIntent(PersonalCentralActivity.this);
                        intent.setPhotoCount(1);
                        intent.setShowCamera(true);
                        intent.setShowGif(true);
                        startActivityForResult(intent, 101);
                    }
                }
            });
            //申请读权限，和照相机权限
            permissionManager.checkPermission(99, PersonalCentralActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
        } else if (resultCode == 9) {
            FunctionUtils.chooseImageFromCamera(PersonalCentralActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
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
                        Intent intent = new Intent(PersonalCentralActivity.this, CutActivity.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, REQUEST_CODE_SHOW);
                    }


                    break;
                case REQUEST_CODE_CAMERA:
                    if (!StringUtils.isEmpty(imagePath)) {
                        Intent intent = new Intent(PersonalCentralActivity.this, CutActivity.class);
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


    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnRequestSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {

                    /**
                     * 获取未读消息数
                     */
                    String sign_one = TGmd5.getMD5(distributorid + "1");
                    personalPresenter.getMessageNum(distributorid, "1", sign_one);


                    JSONObject jsonObject_one = new JSONObject(respanse);
                    String status = jsonObject_one.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject_one.getString("result");
                        JSONArray array = new JSONArray(result);

                        account_info = array.get(3).toString();
                        info_num = array.get(1).toString();

                        String class_num = array.get(2).toString();

                        String profit = array.get(4).toString();
                        String data = array.get(0).toString();
                        JSONObject jsonObject_data = new JSONObject(data);


                        options = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.icon_none_bee)            // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.icon_none_bee)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.icon_none_bee)            // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                                .build();
                        ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(distributorid), img_head, options);
                        // 1 官方  2  导游  3  讲师
                        userType = jsonObject_data.getString("UserType");
                        img_apply_class.setVisibility(View.GONE);
                        if (userType.equals("2")) {
//                            img_teacher_icon.setVisibility(View.GONE);
                            rl_class.setVisibility(View.GONE);
                        } else if (userType.equals("3")) {
                            img_apply_class.setVisibility(View.VISIBLE);
//                            img_teacher_icon.setBackgroundResource(R.mipmap.bg_tecaher_authentication);
//                            img_teacher_icon.setVisibility(View.VISIBLE);
                            rl_class.setVisibility(View.VISIBLE);
                        } else if (userType.equals("1")) {
//                            img_teacher_icon.setBackgroundResource(R.mipmap.icon_official);
//                            img_teacher_icon.setVisibility(View.GONE);
                            rl_class.setVisibility(View.GONE);
                        }

                        /**
                         *  1.审核中 2.去实名认证 3.审核不通过
                         *  4.认证中 5.已实名认证 6.认证不通过
                         */
                        isRenZheng = jsonObject_data.getString("State");
                        PreferenceHelper.write(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, isRenZheng);
                        if (isRenZheng.equals("1")) {
//                            img_renzheng_icon.setBackgroundResource(R.mipmap.personal_remaind_new);
                            tv_renzheng_state.setText("审核中");
                                Drawable drawable = getResources().getDrawable(R.mipmap.icon_no_certified);
                                /// 这一步必须要做,否则不会显示.
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            tv_renzheng_state.setCompoundDrawables(drawable, null, null, null);
//                            img_renzheng_next.setVisibility(View.VISIBLE);
                        } else if (isRenZheng.equals("2")) {
//                            img_renzheng_icon.setBackgroundResource(R.mipmap.personal_remaind_new);
                            tv_renzheng_state.setText("去认证");
//                            img_renzheng_next.setVisibility(View.VISIBLE);
                            Drawable drawable = getResources().getDrawable(R.mipmap.icon_no_certified);
                            /// 这一步必须要做,否则不会显示.
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            tv_renzheng_state.setCompoundDrawables(drawable, null, null, null);
                        } else if (isRenZheng.equals("3")) {
//                            img_renzheng_icon.setBackgroundResource(R.mipmap.personal_remaind_new);
                            tv_renzheng_state.setText("审核失败");
//                            img_renzheng_next.setVisibility(View.VISIBLE);
                            Drawable drawable = getResources().getDrawable(R.mipmap.icon_no_certified);
                            /// 这一步必须要做,否则不会显示.
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            tv_renzheng_state.setCompoundDrawables(drawable, null, null, null);
                        } else if (isRenZheng.equals("4")) {
//                            img_renzheng_icon.setBackgroundResource(R.mipmap.personal_remaind_new);
                            tv_renzheng_state.setText("认证中");
//                            img_renzheng_next.setVisibility(View.VISIBLE);
                            Drawable drawable = getResources().getDrawable(R.mipmap.icon_no_certified);
                            /// 这一步必须要做,否则不会显示.
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            tv_renzheng_state.setCompoundDrawables(drawable, null, null, null);
                        } else if (isRenZheng.equals("5")) {
//                            img_renzheng_icon.setBackgroundResource(R.mipmap.v_three);
                            tv_renzheng_state.setText("已认证 ");
//                            img_renzheng_next.setVisibility(View.VISIBLE);
                            Drawable drawable = getResources().getDrawable(R.mipmap.icon_certified);
                            /// 这一步必须要做,否则不会显示.
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            tv_renzheng_state.setCompoundDrawables(drawable, null, null, null);
                        } else if (isRenZheng.equals("6")) {
                            Drawable drawable = getResources().getDrawable(R.mipmap.icon_no_certified);
                            /// 这一步必须要做,否则不会显示.
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            tv_renzheng_state.setCompoundDrawables(drawable, null, null, null);
//                            img_renzheng_icon.setBackgroundResource(R.mipmap.personal_remaind_new);
                            tv_renzheng_state.setText("认证失败");
//                            img_renzheng_next.setVisibility(View.VISIBLE);
                        }
                        DecimalFormat decimalFormat = new DecimalFormat("##0.00");

                        String FengwenCount = jsonObject_data.getString("FengwenCount");
                        String FollowCount = jsonObject_data.getString("FollowCount");
                        String FansCount = jsonObject_data.getString("FansCount");
                        String tuanbi = jsonObject_data.getString("TuanBi");
                        String name = jsonObject_data.getString("RealName");
                        userName = jsonObject_data.getString("RealName");
                        String Ratio = jsonObject_data.getString("Ratio");
                        tv_fengwen_num.setText(FengwenCount);
                        tv_guanzhu_num.setText(FollowCount);
                        tv_fans_num.setText(FansCount);
                        tv_class_num.setText(Ratio);
                        tv_profit.setText(decimalFormat.format(Float.parseFloat(profit)));
                        tv_tuanbi.setText(tuanbi);
                        tv_name.setText(name);

                        String id = jsonObject_data.getString("ID");
                        PreferenceHelper.write(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, id);
                        PreferenceHelper.write(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi);
                        String state_ = jsonObject_data.getString("State");
                        PreferenceHelper.write(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, state_);
                        String ParentID = jsonObject_data.getString("ParentID");
                        PreferenceHelper.write(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.PARENT_ID, ParentID);
                        PreferenceHelper.write(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.RATIO, Ratio);
                        PreferenceHelper.write(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, userType);

                        if (class_num.equals("0")) {
                            tv_new_class.setVisibility(View.GONE);
                        } else {
                            tv_new_class.setVisibility(View.VISIBLE);
                        }
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
                        commentNum = array.get(0).toString();
                        dianzanNum = array.get(1).toString();
                        int classNum = (int)array.get(3);
                        int systemNum = (int)array.get(4);
                        int fengyouNum =(int)array.get(5);
                        PreferenceHelper.write(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, commentNum);
                        PreferenceHelper.write(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ZAN_NUM, dianzanNum);
                        PreferenceHelper.write(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CENTER_NUMBER, String.valueOf(classNum + systemNum + fengyouNum));

                        String total_num = String.valueOf(systemNum + fengyouNum);
                        PreferenceHelper.write(PersonalCentralActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.MESSAGE_NUM, total_num + "");

                        if ("0".equals(total_num)) {
                            tv_message_num.setVisibility(View.GONE);
                        } else {
                            tv_message_num.setVisibility(View.VISIBLE);
                            tv_message_num.setText(total_num);
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
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnRequestFialCallBack(String state, String respanse) {
    }


    @Override
    public void closeProgress() {

    }


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
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(PersonalCentralActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
//                                FunctionUtils.chooseImageFromGallery(PersonalCentralActivity.this, REQUEST_CODE_GALLERY);
                                // 权限管理工具类
                                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                                    @Override
                                    public void permissionGranted(int requestCode) {
                                        if (requestCode == 99) {
                                            PhotoPickerIntent intent = new PhotoPickerIntent(PersonalCentralActivity.this);
                                            intent.setPhotoCount(1);
                                            intent.setShowCamera(true);
                                            intent.setShowGif(true);
                                            startActivityForResult(intent, 101);
                                        }
                                    }
                                });
                                //申请读权限，和照相机权限
                                permissionManager.checkPermission(99, PersonalCentralActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);

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
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(PersonalCentralActivity.this,
                        new String[]{Manifest.permission.CAMERA}, new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                imagePath = FunctionUtils.chooseImageFromCamera(
                                        PersonalCentralActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
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

    long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            AppManager.getInstance().AppExit(getApplicationContext());
        } else {
            MyToast.show(PersonalCentralActivity.this, "再按一次退出应用！");
        }
        back_pressed = System.currentTimeMillis();
    }

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

        RequestTask.getInstance().doUploadHead(PersonalCentralActivity.this, maps, new OnHeadRequestListener());
    }

    private class OnHeadRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
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
                    ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(distributorid), img_head, optionsone);
                    MyToast.show(PersonalCentralActivity.this, "上传成功");
                } else if (status.equals("0")) {
                    MyToast.show(PersonalCentralActivity.this, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
