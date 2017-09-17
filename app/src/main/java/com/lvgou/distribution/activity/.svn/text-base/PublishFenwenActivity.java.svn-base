package com.lvgou.distribution.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.lvgou.distribution.adapter.FengWenClassifyAdapter;
import com.lvgou.distribution.adapter.GridViewPathImageOneAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.luban.Luban;
import com.lvgou.distribution.presenter.PublishFengWenPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.view.MyGridView;
import com.lvgou.distribution.viewholder.FengwenClassifyViewHolder;
import com.lvgou.distribution.wechat.imageloader.MainActivity;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/19.
 * 发布蜂文
 */
public class PublishFenwenActivity extends BaseActivity implements GroupView, OnClassifyPostionClickListener<ClassifyEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.grid_view)
    private MyGridView gridView_tag;
    @ViewInject(R.id.rl_show_up)
    private RelativeLayout rl_show_up;
    @ViewInject(R.id.et_content)
    private EditText et_cxontent;
    @ViewInject(R.id.tv_text_num)
    private TextView tv_text_num;
    @ViewInject(R.id.rl_select_address)
    private RelativeLayout rl_select_address;
    @ViewInject(R.id.img_address)
    private ImageView img_address;
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    @ViewInject(R.id.gridview)
    private MyGridView gridView_img;
    @ViewInject(R.id.tv_select_gallery)
    private TextView tv_select_gallery;
    @ViewInject(R.id.tv_select_camera)
    private TextView tv_select_camera;
    @ViewInject(R.id.img_up_down)
    private ImageView img_up_down;
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
    private final static int REQUEST_CODE_ADDRESS = 0x22;
    private final static int REQUEST_CODE_GALLERY = 0x33;
    private final static int REQUEST_CODE_CAMERA = 0x44;
    private List<String> list_urls = new ArrayList<String>(); //图片地址集合
    private List<String> list_urls_one = new ArrayList<String>(); //图片地址集合

    private GridViewPathImageOneAdapter pathImageAdapter;
    private List<Bitmap> list = new ArrayList<Bitmap>();
    private int size = 0;

    private List<String> ids = new ArrayList<String>();
    private String distributorid = "";
    private String tagIds = "";
    private String picUrls = "";
    private String location_str = "";

    private String zhankai = "0";

    private PublishFengWenPresenter publishFengWenPresenter;
    private FengWenClassifyAdapter fengWenClassifyAdapter;
    private List<ClassifyEntity> classifyEntityLists;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1002:
                    pathImageAdapter.setList(list_urls);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_fenwen);
        ViewUtils.inject(this);
        EventBus.getDefault().register(this);
        distributorid = PreferenceHelper.readString(PublishFenwenActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        publishFengWenPresenter = new PublishFengWenPresenter(this);

        initGridView();


        String sign = TGmd5.getMD5(distributorid);
        publishFengWenPresenter.getTag(distributorid, sign);


        et_cxontent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_cxontent.setSingleLine(false);
        //水平滚动设置为False
        et_cxontent.setHorizontallyScrolling(false);
        et_cxontent.setHint("说说你的故事...");
        TextChangeListener();

    }


    @OnClick({R.id.rl_back, R.id.rl_publish, R.id.tv_select_gallery, R.id.tv_select_camera, R.id.tv_cancel, R.id.rl_select_address, R.id.rl_show_up})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
                String str = et_cxontent.getText().toString().trim();
                if (StringUtils.isEmpty(str)) {
                    MyToast.show(this, "请输入内容");
                    return;
                } else {
                    showLoadingProgressDialog(PublishFenwenActivity.this, "");
                    location_str = tv_address.getText().toString();
                    if (location_str.equals("所在位置")) {
                        location_str = "";
                    }
                    if (ids.size() == 0) {
                        tagIds = "";
                    } else {
                        tagIds = ids.toString().substring(1, ids.toString().length() - 1).replace(", ", "|");
                    }
                    if (list_urls.size() == 0) {
                        picUrls = "";
                    } else {
                        picUrls = list_urls_one.toString().substring(1, list_urls_one.toString().length() - 2).replace(", ", "");
                    }

                    String sign = TGmd5.getMD5(distributorid + tagIds + str + picUrls + location_str);
                    publishFengWenPresenter.publishFengWen(distributorid, tagIds, str, picUrls, location_str, sign);
                }
                break;
            case R.id.tv_select_gallery:
                closeDialog();
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionsResultAction() {

                            @Override
                            public void onGranted() {
                                Bundle bundle = new Bundle();
                                bundle.putString("num_", list_urls.size() + "");
                                Intent intent = new Intent(PublishFenwenActivity.this, MainActivity.class);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, REQUEST_CODE_GALLERY);
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
                                imagePath = FunctionUtils.chooseImageFromCamera(PublishFenwenActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
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
            case R.id.rl_select_address:
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                Intent intent_one = new Intent(PublishFenwenActivity.this, SelectLocalAdressActivity.class);
                                startActivityForResult(intent_one, REQUEST_CODE_ADDRESS);
                            }

                            @Override
                            public void onDenied(String permission) {
                            }
                        });

                break;
            case R.id.rl_show_up:
                if (zhankai.equals("0")) {
                    zhankai = "1";
                    img_up_down.setBackgroundResource(R.mipmap.xiala_down);
                    fengWenClassifyAdapter.addItemNum(classifyEntityLists.size());
                    fengWenClassifyAdapter.notifyDataSetChanged();
                } else if (zhankai.equals("1")) {
                    zhankai = "0";
                    img_up_down.setBackgroundResource(R.mipmap.shouqi_up);
                    fengWenClassifyAdapter.addItemNum(3);
                    fengWenClassifyAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    public void TextChangeListener() {
        et_cxontent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String length = et_cxontent.getText().length() + "";
                if (Integer.parseInt(length) >= 0 && Integer.parseInt(length) <= 2000) {
                    tv_text_num.setText(length + "/2000");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void initGridView() {
        pathImageAdapter = new GridViewPathImageOneAdapter(PublishFenwenActivity.this, list_urls);
        gridView_img.setAdapter(pathImageAdapter);
        pathImageAdapter.setList(list_urls);

        gridView_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == getDataSize() && size <= 6) {// 点击“+”号位置添加图片
                    openDialog();
                } else if (size > 6) {
                    MyToast.show(PublishFenwenActivity.this, "最多上传6张图片");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_CODE_GALLERY) {
            showLoadingProgressDialog(PublishFenwenActivity.this, "");
            final List<String> path_list = data.getStringArrayListExtra("img_path");
            if (path_list != null && path_list.size() > 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < path_list.size(); i++) {
                            compressWithRx(new File(path_list.get(i)));
                        }
                        closeLoadingProgressDialog();
                    }
                }).start();
            }
        } else if (resultCode == REQUEST_CODE_ADDRESS) {
            String title = data.getStringExtra("title");
            String city_ = data.getStringExtra("city_name");
            if (title.equals("不显示位置")) {
                img_address.setBackgroundResource(R.mipmap.dingwei_black);
                tv_address.setText("所在位置");
            } else {
                img_address.setBackgroundResource(R.mipmap.dingwei_yellow);
                tv_address.setText(city_ + " " + title);
            }
        } else if (resultCode == -1) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    if (!StringUtils.isEmpty(imagePath)) {
                        /**
                         * 需要修改，路径为空，默认图片记得添加
                         */
                        showLoadingProgressDialog(PublishFenwenActivity.this, "");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                compressWithRx(new File(imagePath));
                                closeLoadingProgressDialog();
                            }
                        }).start();
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 分类筛选点击事件
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(ClassifyEntity itemData, int postion) {
        switch (postion) {
            case 1:
                if (itemData.getNum().equals("1")) {
                    if (ids.contains(itemData.getId())) {
                        ids.remove(itemData.getId());
                        itemData.setNum("0");
                        fengWenClassifyAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (ids.size() < 2) {
                        itemData.setNum("1");
                        fengWenClassifyAdapter.notifyDataSetChanged();
                        ids.add(itemData.getId());
                    } else {
                        MyToast.show(this, "最多选择两个分类");
                    }
                }
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
                    classifyEntityLists = new ArrayList<ClassifyEntity>();
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String tag_data = array.get(0).toString();
                        JSONArray jsonArray = new JSONArray(tag_data);
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject_tag = jsonArray.getJSONObject(i);
                                String ID = jsonObject_tag.getString("ID");
                                String CagegoryName = jsonObject_tag.getString("CagegoryName");
                                ClassifyEntity classifyEntity = new ClassifyEntity(ID, CagegoryName, "0");
                                classifyEntityLists.add(classifyEntity);
                            }
                        }

                        fengWenClassifyAdapter = new FengWenClassifyAdapter(PublishFenwenActivity.this, classifyEntityLists);
                        FengWenClassifyAdapter.setClassifyEntityOnClassifyPostionClickListener(this);
                        gridView_tag.setAdapter(fengWenClassifyAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    closeLoadingProgressDialog();
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String is_show = array.get(0).toString();
                        String url = array.get(1).toString();
                        MyToast.show(this, "发布成功");
                        EventFactory.updateFengWn(0);

                        if (is_show.equals("true")) {
                            EventFactory.showChoujiang(url);
                        }

                        finish();
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
        switch (Integer.parseInt(state)) {
            case 1:
                MyToast.show(this,respanse);
                break;
            case 2:
                MyToast.show(this,respanse);
                break;
        }
    }

    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }

    @Override
    public void showProgress() {

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
     * 压缩单张图片 RxJava 方式
     */
    private void compressWithRx(File file) {
        if(file.exists()) {
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
        }else{
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    MyToast.show(PublishFenwenActivity.this, "蜂优客没有系统权限，文件目录不存在");
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        publishFengWenPresenter.attach(this);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        publishFengWenPresenter.dettach();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 上传图片
     */
    public void doUpload(File file, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("picurl", file);
        maps.put("sign", sign);
        RequestTask.getInstance().doCircleUploadImage(PublishFenwenActivity.this, maps, new OnUploadRequestListener());
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
                    String data = array.get(0).toString();
                    JSONArray array1 = new JSONArray(data);
                    if (array1 != null && array1.length() > 0) {
                        for (int i = 0; i < array1.length(); i++) {
                            JSONObject jsonObject_ = array1.getJSONObject(i);
                            String result_imgpath = jsonObject_.getString("picUrl");
                            String smallPicUrl = jsonObject_.getString("smallPicUrl");
                            String width = jsonObject_.getString("width");
                            String hight = jsonObject_.getString("height");
                            String upload_urls = result_imgpath + "&" + smallPicUrl + "&" + width + "&" + hight + "|";
                            list_urls_one.add(upload_urls);
                            list_urls.add(result_imgpath);
                            size++;
                        }
                        Message message = new Message();
                        message.what = 1002;
                        handler.sendMessage(message);
                    }
                } else if (status.equals("0")) {
                    MyToast.show(PublishFenwenActivity.this, "上传失败");
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
//          closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
//          closeLoadingProgressDialog();
        }
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
                size--;
            }

            if (list_urls_one.contains(list_urls_one.get(location))) {
                list_urls_one.remove(location);
                String sign = TGmd5.getMD5(distributorid + list_urls_one.get(location));
                deleteImage(distributorid, list_urls_one.get(location), sign);
            }
        }
    }


    private void deleteImage(String distributorid, String path, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("path", path);
        maps.put("sign", sign);

        RequestTask.getInstance().doDeleteImage(PublishFenwenActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(PublishFenwenActivity.this, "删除成功");
                } else {
                    MyToast.show(PublishFenwenActivity.this, "删除失败");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
