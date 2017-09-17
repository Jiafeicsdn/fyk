package com.lvgou.distribution.activity;

import android.Manifest;
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
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import com.lvgou.distribution.cutView.CutFragment;
import com.lvgou.distribution.cutView.CutView;
import com.lvgou.distribution.luban.Luban;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.PermissionManager;
import com.lvgou.distribution.utils.PictureUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.Tools;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Snow on 2016/3/11 0011.
 * 导游证管理
 */
public class GuideCradMnagerActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_product)
    private TextView tv_content;
    @ViewInject(R.id.tv_load)
    private TextView tv_load;
    @ViewInject(R.id.tv_status)
    private TextView tv_status;
    @ViewInject(R.id.img_delete)
    private ImageView img_delete;
    @ViewInject(R.id.btn_save)
    private Button btn_louad;
    @ViewInject(R.id.img_picture)
    private ImageView img_picture;
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
    @ViewInject(R.id.rl_all)
    private RelativeLayout rl_all;
    @ViewInject(R.id.rl_load)
    private RelativeLayout rl_load;

    private CutFragment cutFragment;
    /**
     * 当前的图片路径
     */
    private String imagePath = null;
    private final static int REQUEST_CODE_GALLERY = 0x33;
    private final static int REQUEST_CODE_CAMERA = 0x44;

    DisplayImageOptions options;
    DisplayImageOptions optionsone;
    private String result_imgpath = "";
    private String distributorid = "";

    private String upload_path = "";
    private String state = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_card_manger);
        ViewUtils.inject(this);
        tv_title.setText("导游证管理");
        initTextView();
        distributorid = PreferenceHelper.readString(GuideCradMnagerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        state = getTextFromBundle("state");
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                String sign_ = TGmd5.getMD5(distributorid);
                getImage(distributorid, sign_);
            }
        }
    }

    public void initTextView() {
        SpannableString spannableOne = new SpannableString("上传导游证名字必须与注册名字一致，");
        spannableOne.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bg_new_guide_black)), 0, spannableOne.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        SpannableString spannableTwo = new SpannableString("上传照片需清晰看见证件号码及姓名，照片仅供蜂优客核实使用，请放心上传。");
        spannableTwo.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bg_push_time)), 0, spannableTwo.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_content.append(spannableOne);
        tv_content.append(spannableTwo);
    }
    private ArrayList<String> data_list = new ArrayList<String>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        } else {
            Bitmap resultBitmap = null;
            if (resultBitmap != null) {
                resultBitmap.recycle();
            }
            switch (requestCode) {
                case 101:
                    /*imagePath = FunctionUtils
                            .onActivityResultForChooseImageFromGallery(GuideCradMnagerActivity.this,
                                    requestCode, resultCode, data);*/
                    if (data != null) {
                        data_list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                        imagePath = data_list.get(0);
                    }
                    if (!StringUtils.isEmpty(imagePath)) {
                        /**
                         *需要修改，路径为空，默认图片记得添加
                         */
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

    private PermissionManager permissionManager;
    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick({R.id.rl_back, R.id.tv_load, R.id.img_delete, R.id.btn_save, R.id.tv_select_gallery, R.id.tv_select_camera, R.id.tv_cancel})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
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
            case R.id.tv_select_gallery:
                closeDialog();
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionsResultAction() {

                            @Override
                            public void onGranted() {
                              /*  FunctionUtils.chooseImageFromGallery(GuideCradMnagerActivity.this,
                                        REQUEST_CODE_GALLERY);*/
                                // 权限管理工具类
                                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                                    @Override
                                    public void permissionGranted(int requestCode) {
                                        if (requestCode == 99) {
                                            PhotoPickerIntent intent = new PhotoPickerIntent(GuideCradMnagerActivity.this);
                                            intent.setPhotoCount(1);
                                            intent.setShowCamera(true);
                                            intent.setShowGif(true);
                                            startActivityForResult(intent, 101);
                                        }
                                    }
                                });
                                //申请读权限，和照相机权限
                                permissionManager.checkPermission(99, GuideCradMnagerActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
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
                                imagePath = FunctionUtils.chooseImageFromCamera(
                                        GuideCradMnagerActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
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
            case R.id.btn_save:
                if (result_imgpath.toString().length() > 0) {
                    String sign = TGmd5.getMD5(distributorid + result_imgpath);
                    savePicture(distributorid, result_imgpath, sign);

                } else {
                    MyToast.show(GuideCradMnagerActivity.this, "请先选择上传的图片");
                }
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

    /**
     * 上传证件照片请求
     */
    public void doUpload(File file, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("picurl", file);
        maps.put("sign", sign);

        RequestTask.getInstance().uploadCardGuide(GuideCradMnagerActivity.this, maps, new OnUploadRequestListener());
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
                    Log.e("jkhasdkfh", "============="+array);
                    PreferenceHelper.write(GuideCradMnagerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL, result_imgpath);
                    PreferenceHelper.write(GuideCradMnagerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "1");
                    MyToast.show(GuideCradMnagerActivity.this, "上传成功");
                    tv_status.setText("导游证已上传");
                    tv_status.setTextColor(getResources().getColor(R.color.bg_main_clissfy_text));
                } else if (status.equals("0")) {
                    MyToast.show(GuideCradMnagerActivity.this, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取证件照片请求
     */
    public void getImage(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getCardGuide(GuideCradMnagerActivity.this, maps, new OnLoadImageRequestListener());
    }

    private class OnLoadImageRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String image_path = array.get(0).toString().trim();
                    // 已经上传 状态
                    //状态：1=审核中，2=审核通过，3=审核不通过，4=实名认证审核中，5=实名认证通过，6=实名认证不通过，7=停用
                    if (state.equals("3")) {
                        tv_load.setVisibility(View.VISIBLE);
                        btn_louad.setVisibility(View.VISIBLE);
                    } else {
                        tv_load.setVisibility(View.GONE);
                        btn_louad.setVisibility(View.GONE);
                    }
                    String guide_picurl = PreferenceHelper.readString(GuideCradMnagerActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL);
                    if (state.equals("1")&&(guide_picurl.equals("") || guide_picurl == null && guide_picurl.length() == 0)){
                        //审核中并且导游证为空
                        tv_load.setVisibility(View.VISIBLE);
                        btn_louad.setVisibility(View.VISIBLE);
                    }

                    if (image_path != null && image_path.length() > 0) {
                        tv_status.setText("导游证已上传");
                        tv_status.setTextColor(getResources().getColor(R.color.bg_main_clissfy_text));
                        options = new DisplayImageOptions.Builder()
                                .showImageForEmptyUri(R.mipmap.head_default)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.head_default)            // 设置图片加载或解码过程中发生错误显示的图片
                                .build();
                        ImageLoader.getInstance().displayImage(Url.ROOT + image_path + "?v=" + Calendar.getInstance().getTimeInMillis(), img_picture, options);
                    } else {
                        tv_status.setText("你还没有上传导游证哦");
                        tv_status.setTextColor(getResources().getColor(R.color.bg_code_number));
                    }
                } else if (status.equals("0")) {
                    MyToast.show(GuideCradMnagerActivity.this, jsonObject.getString("message"));
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
     * 保存图片
     *
     * @param distributorid
     * @param path
     * @param sign
     */
    public void savePicture(String distributorid, String path, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("picurl", path);
        maps.put("sign", sign);
        RequestTask.getInstance().savePicurl(GuideCradMnagerActivity.this, maps, new OnSaveRequestListener());
    }

    private class OnSaveRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    tv_load.setVisibility(View.GONE);
                    btn_louad.setVisibility(View.GONE);
                    img_delete.setVisibility(View.GONE);
                    Log.e("jkhasdkfh", "------------------"+jsonObject);
                    MyToast.show(GuideCradMnagerActivity.this, "保存成功！");
                } else {
                    MyToast.show(GuideCradMnagerActivity.this, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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
                        tv_load.setVisibility(View.GONE);
                        img_delete.setVisibility(View.VISIBLE);
                        Bitmap bitmap = convertFullBitmap(imagePath);
                        img_picture.setImageBitmap(bitmap);
                        String sign = TGmd5.getMD5("");
                        doUpload(file, sign);
                    }
                });
    }
}
