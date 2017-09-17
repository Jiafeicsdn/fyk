package com.lvgou.distribution.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.GridViewPathImageOneAdapter;
import com.lvgou.distribution.bean.DelPic;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.emoji.SelectFaceHelper;
import com.lvgou.distribution.event.Event;
import com.lvgou.distribution.event.EventType;
import com.lvgou.distribution.luban.Luban;
import com.lvgou.distribution.presenter.PublishTalkPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.ImageLoaderEmoji;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.MyGridView;
import com.lvgou.distribution.view.PublishTalkView;
import com.lvgou.distribution.wechat.imageloader.*;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2017/4/17.
 * 发布蜂文
 */

public class PublishFengActivity extends BaseActivity implements View.OnClickListener, PublishTalkView {
    private final static int REQUEST_CODE_ADDRESS = 0x22;
    private final static int REQUEST_CODE_GALLERY = 0x33;
    private final static int REQUEST_CODE_CAMERA = 0x44;
    private String imagePath = null;
    private List<String> list_urls = new ArrayList<String>(); //图片地址集合
    private List<String> list_urls_one = new ArrayList<String>(); //图片地址集合
    private int size = 0;
    private GridViewPathImageOneAdapter pathImageAdapter;
    private String distributorid = "";
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
    private ArrayList<HashMap<String, Object>> checkData;
    private PublishTalkPresenter publishTalkPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_feng);
        publishTalkPresenter = new PublishTalkPresenter(this);
        distributorid = PreferenceHelper.readString(PublishFengActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        mcache.remove("fengmarklist");
        mcache.remove("fenghuatilist");
        initView();
        initClick();
        et_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_content.setSingleLine(false);
        //水平滚动设置为False
        et_content.setHorizontallyScrolling(false);
        et_content.setHint("欢迎各种爆料，线索一经采用，奖励10团币");
        EventBus.getDefault().register(this);

        String OneActClick = PreferenceHelper.readString(PublishFengActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONEFENGCLICK, "0");
        if (OneActClick.equals("0")) {
            showOneFengDialog();
        }
    }


    public void showOneFengDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_feng_oneclick, null);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceHelper.write(PublishFengActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ONEFENGCLICK, "1");
                mAlertDialog.dismiss();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private RelativeLayout rl_back;//返回
    private RelativeLayout rl_publish;//发布
    private EditText et_content;//编辑蜂文
    private RelativeLayout rl_select_address;//地址选择
    private TextView tv_address;
    private ImageView img_address;
    private RelativeLayout mDialogRootRelativeLayout;
    private LinearLayout mDialogCotentLinearLayout;
    private MyGridView gridView_img;
    private RelativeLayout rl_mark;//标签
    private TextView tv_select_gallery;
    private TextView tv_select_camera;
    private TextView tv_cancel;
    private TextView tv_markone;
    private TextView tv_marktwo;
    private RelativeLayout rl_markdata;
    private RelativeLayout rl_huati;
    private RelativeLayout rl_emoji;//emoji
    private LinearLayout ll_emoji;
    private RelativeLayout rl_all;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_publish = (RelativeLayout) findViewById(R.id.rl_publish);
        et_content = (EditText) findViewById(R.id.et_content);
        setEtListener();
        rl_select_address = (RelativeLayout) findViewById(R.id.rl_select_address);
        tv_address = (TextView) findViewById(R.id.tv_address);
        img_address = (ImageView) findViewById(R.id.img_address);
        rl_markdata = (RelativeLayout) findViewById(R.id.rl_markdata);
        tv_markone = (TextView) findViewById(R.id.tv_markone);
        tv_marktwo = (TextView) findViewById(R.id.tv_marktwo);
        rl_huati = (RelativeLayout) findViewById(R.id.rl_huati);
        rl_emoji = (RelativeLayout) findViewById(R.id.rl_emoji);
        ll_emoji = (LinearLayout) findViewById(R.id.ll_emoji);
        mDialogRootRelativeLayout = (RelativeLayout) findViewById(R.id.rl_dialog_ios_7_root);
        mDialogCotentLinearLayout = (LinearLayout) findViewById(R.id.ll_dialog_ios_7_cotent);
        tv_select_gallery = (TextView) findViewById(R.id.tv_select_gallery);
        tv_select_camera = (TextView) findViewById(R.id.tv_select_camera);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        gridView_img = (MyGridView) findViewById(R.id.gridview);
        initGridView();
        rl_mark = (RelativeLayout) findViewById(R.id.rl_mark);
        rl_all = (RelativeLayout) findViewById(R.id.rl_all);

    }

    private void setEtListener() {
        et_content.addTextChangedListener(new TextWatcher() {
            int beforeChanged = 0;
            int onChanged = 0;
            String othertext = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeChanged = et_content.getText().toString().length();
                if (huatiLenth > 0) {
                    String huatiTitle = "#" + huatiDataList.get("Title").toString() + "# ";
                    othertext = et_content.getText().toString().replace(huatiTitle, "");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onChanged = et_content.getText().toString().length();
                if (beforeChanged > onChanged) {
                    //说明是删除
                    if (et_content.getSelectionStart() < huatiLenth) {
                        //如果光标的位子小于话题长度
                        huatiLenth = 0;
                        try {
                            SpannableString spannableString = ImageLoaderEmoji.getInstance().dealExpression(othertext);
                            et_content.setText("");
                            et_content.append(spannableString);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mcache.remove("fengmarklist");

                    }
                   /* if (onChanged < huatiLenth) {
                        //删除的时候，发现文本内容小鱼话题长度，就清空话题
                        huatiLenth = 0;
                        et_content.setText("");
                    }*/
                } else {
                    //说明是在输入
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_publish.setOnClickListener(this);
        rl_select_address.setOnClickListener(this);
        rl_mark.setOnClickListener(this);
        tv_select_gallery.setOnClickListener(this);
        tv_select_camera.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        rl_huati.setOnClickListener(this);
        rl_emoji.setOnClickListener(this);
        rl_all.setOnClickListener(this);
        et_content.setOnClickListener(this);

    }

    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String sscontentStr = et_content.getText().toString().trim();
            if (!sscontentStr.equals("")) {
                showBackDialog();
            } else {
                finish();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void showBackDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("确定抛下我吗?");
        View view_line = view.findViewById(R.id.view_line);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        yes.setText("继续发布 ");
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        cancle.setText("不发布");
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishFengActivity.this.finish();
                mAlertDialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        ll_emoji.setVisibility(View.GONE);
        switch (v.getId()) {
            case R.id.rl_back:
                String sscontentStr = et_content.getText().toString().trim();
                if (!sscontentStr.equals("")) {
                    showBackDialog();
                } else {
                    finish();
                }

                break;
            case R.id.rl_publish://发布
                String contentStr = et_content.getText().toString().trim();
                if (contentStr.equals("")) {
                    MyToast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                    break;
                }
                String location_str = tv_address.getText().toString();
                if (location_str.equals("定位当前位置") || location_str.equals("所在位置")) {
                    location_str = "";
                }
                String tagIds = "";
                if (null == checkData || checkData.size() == 0) {
                    tagIds = "";
                } else {
                    for (HashMap<String, Object> stringObjectHashMap : checkData) {
                        tagIds = tagIds + stringObjectHashMap.get("ID").toString() + "|";
                    }
                    tagIds = tagIds.substring(0, tagIds.length() - 1);
                }
                String picUrls = "";
                if (list_urls.size() == 0) {
                    picUrls = "";
                } else {
                    picUrls = list_urls_one.toString().substring(1, list_urls_one.toString().length() - 2).replace(", ", "");
                }
                String topicId = "";
                if (null == huatiDataList || huatiDataList.size() == 0) {
                    topicId = "";
                } else {
                    topicId = huatiDataList.get("ID").toString();
                }
                showLoadingProgressDialog(this, "");
                String sign = TGmd5.getMD5(distributorid + tagIds + contentStr + picUrls + location_str + topicId);
                publishTalkPresenter.publishTalk(distributorid, tagIds, contentStr, picUrls, location_str, topicId, sign);
                break;
            case R.id.rl_select_address://地址选择
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                Intent intent_one = new Intent(PublishFengActivity.this, SelectLocalAdressActivity.class);
                                startActivityForResult(intent_one, REQUEST_CODE_ADDRESS);
                            }

                            @Override
                            public void onDenied(String permission) {
                            }
                        });
                break;
            case R.id.tv_select_gallery:
                closeDialog();
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionsResultAction() {

                            @Override
                            public void onGranted() {
                                Bundle bundle = new Bundle();
                                bundle.putString("num_", list_urls.size() + "");
                                Intent intent = new Intent(PublishFengActivity.this, com.lvgou.distribution.wechat.imageloader.MainActivity.class);
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
                                imagePath = FunctionUtils.chooseImageFromCamera(PublishFengActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
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
            case R.id.rl_mark://标签
                Intent intent = new Intent(PublishFengActivity.this, FengMarkActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_huati://话题
                Intent intent2 = new Intent(PublishFengActivity.this, FengHuaTiActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_emoji:
                if (null == mFaceHelper) {
                    mFaceHelper = new SelectFaceHelper(this, ll_emoji);
                    mFaceHelper.setFaceOpreateListener(mOnFaceOprateListener);
                }
                ll_emoji.setVisibility(View.VISIBLE);
                break;
        }
    }

    SelectFaceHelper.OnFaceOprateListener mOnFaceOprateListener = new SelectFaceHelper.OnFaceOprateListener() {
        @Override
        public void onFaceSelected(SpannableString spanEmojiStr) {
            if (null != spanEmojiStr) {
                et_content.append(spanEmojiStr);
            }
        }

        @Override
        public void onFaceDeleted() {
            int selection = et_content.getSelectionStart();
            String text = et_content.getText().toString();
            if (selection > 0) {
                String text2 = text.substring(selection - 1);
                if ("]".equals(text2)) {
                    int start = text.lastIndexOf("[");
                    int end = selection;
                    et_content.getText().delete(start, end);
                    return;
                }
                et_content.getText().delete(selection - 1, selection);
            }
        }

    };
    private SelectFaceHelper mFaceHelper;
    private HashMap<String, Object> huatiDataList;

    @Override
    protected void onResume() {
        super.onResume();
        checkData = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("fengmarklist");
        rl_markdata.setVisibility(GONE);
        tv_markone.setVisibility(GONE);
        tv_marktwo.setVisibility(GONE);
        if (null != checkData) {
            if (checkData.size() == 1) {
                rl_markdata.setVisibility(View.VISIBLE);
                tv_markone.setVisibility(View.VISIBLE);
                tv_marktwo.setVisibility(GONE);
                tv_markone.setText(checkData.get(0).get("CagegoryName").toString());
            } else if (checkData.size() == 2) {
                rl_markdata.setVisibility(View.VISIBLE);
                tv_markone.setVisibility(View.VISIBLE);
                tv_marktwo.setVisibility(View.VISIBLE);
                tv_markone.setText(checkData.get(0).get("CagegoryName").toString());
                tv_marktwo.setText(checkData.get(1).get("CagegoryName").toString());
            }
        }
        huatiDataList = (HashMap<String, Object>) mcache.getAsObject("fenghuatilist");
        if (null != huatiDataList) {
            String huatiTitle = "#" + huatiDataList.get("Title").toString() + "# ";
            SpannableString spanText = new SpannableString(huatiTitle);
            spanText.setSpan(new ForegroundColorSpan(Color.parseColor("#d5aa5f")), 0, spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            String et = et_content.getText().toString();
            String replace = "";
            if (!hautiStr.equals("")) {
                replace = et.replace(hautiStr, "");

            } else {
                replace = et;
            }
            et_content.setText("");
            et_content.append(spanText);
            try {
                SpannableString spannableString = ImageLoaderEmoji.getInstance().dealExpression(replace);
                et_content.append(spannableString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            huatiLenth = huatiTitle.length();
            hautiStr = huatiTitle;
        }
    }

    private int huatiLenth = 0;//话题长度
    private String hautiStr = "";

    public void initGridView() {
        pathImageAdapter = new GridViewPathImageOneAdapter(PublishFengActivity.this, list_urls);
        gridView_img.setAdapter(pathImageAdapter);
        pathImageAdapter.setList(list_urls);

        gridView_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == getDataSize() && size <= 6) {// 点击“+”号位置添加图片
                    openDialog();
                } else if (size > 6) {
                    MyToast.show(PublishFengActivity.this, "最多上传6张图片");
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

    private void openDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, true);
    }

    private void closeDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_CODE_GALLERY) {
            showLoadingProgressDialog(PublishFengActivity.this, "");
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
                        showLoadingProgressDialog(PublishFengActivity.this, "");
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
     * 压缩单张图片 RxJava 方式
     */
    private void compressWithRx(final File fileoo) {
        if (fileoo.exists()) {
            //-----------
            Luban.get(this)
                    .load(fileoo)
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
                            long length = fileoo.length() / 1024;
                            if (length > 300) {
                                String sign = TGmd5.getMD5("");
                                doUpload(file, sign);
                            } else {
                                String sign = TGmd5.getMD5("");
                                doUpload(fileoo, sign);
                            }

                        }
                    });
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    MyToast.show(PublishFengActivity.this, "蜂优客没有系统权限，文件目录不存在");
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
        RequestTask.getInstance().doCircleUploadImage(PublishFengActivity.this, maps, new OnUploadRequestListener());
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
                    MyToast.show(PublishFengActivity.this, "上传失败");
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
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIEvent(DelPic delPic) {
        int location = delPic.getPosition();
        if (list_urls.contains(list_urls.get(location))) {
            list_urls.remove(location);
            pathImageAdapter.setList(list_urls);
            size--;

        }
        if (list_urls_one.contains(list_urls_one.get(location))) {
            list_urls_one.remove(location);
//                String sign = TGmd5.getMD5(distributorid + list_urls_one.get(location));
//                deleteImage(distributorid, list_urls_one.get(location), sign);
        }
    }
   /* public void onEventMainThread(Event<Integer> event) {
        if (event.getEventType() == EventType.DELETE_IMAGE_POSTION) {
            int location = event.getExtraData();
            if (list_urls.contains(list_urls.get(location))) {
                list_urls.remove(location);
                pathImageAdapter.setList(list_urls);
                size--;

            }
            Log.e("kjsahkfs", "*************"+size );
            if (list_urls_one.contains(list_urls_one.get(location))) {
                list_urls_one.remove(location);
                String sign = TGmd5.getMD5(distributorid + list_urls_one.get(location));
                deleteImage(distributorid, list_urls_one.get(location), sign);
            }
        }
    }*/

    private void deleteImage(String distributorid, String path, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("path", path);
        maps.put("sign", sign);

        RequestTask.getInstance().doDeleteImage(PublishFengActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(PublishFengActivity.this, "删除成功");
                } else {
                    MyToast.show(PublishFengActivity.this, "删除失败");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void OnPublishTalkSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        EventBus.getDefault().post("publishfengsuccess");
        MyToast.show(this, "发布成功");
        finish();
    }

    @Override
    public void OnPublishTalkFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closePublishTalkProgress() {

    }
}
