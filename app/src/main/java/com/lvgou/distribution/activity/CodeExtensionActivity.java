package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snow on 2016/5/31 0031.
 * 二维码推广
 */
public class CodeExtensionActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_xianshang)
    private RelativeLayout rl_xianshang;
    @ViewInject(R.id.rl_group)
    private RelativeLayout rl_group;
    @ViewInject(R.id.tv_xianshang)
    private TextView tv_xianshang;
    @ViewInject(R.id.tv_group)
    private TextView tv_group;
    @ViewInject(R.id.img_xianshang)
    private ImageView img_xianshang;
    @ViewInject(R.id.img_group)
    private ImageView img_group;
    @ViewInject(R.id.ll_xianshang)
    private LinearLayout ll_xianshang;
    @ViewInject(R.id.ll_group)
    private LinearLayout ll_group;
    @ViewInject(R.id.img_code)
    private ImageView img_code;
    @ViewInject(R.id.btn_login)
    private Button btn_code;
    @ViewInject(R.id.img_code_group)
    private ImageView img_code_group;
    @ViewInject(R.id.btn_group)
    private Button btn_code_group;
    @ViewInject(R.id.rl_shop_share)
    private RelativeLayout rl_shop_share;
    @ViewInject(R.id.rl_dialog_share_root)
    private RelativeLayout rl_dialog_share_root;
    @ViewInject(R.id.ll_dialog_share_cotent)
    private LinearLayout ll_dialog_share_cotent;
    @ViewInject(R.id.rl_qq)
    private RelativeLayout rl_qq;
    @ViewInject(R.id.rl_qzone)
    private RelativeLayout rl_qzone;
    @ViewInject(R.id.rl_weixin)
    private RelativeLayout rl_weixin;
    @ViewInject(R.id.rl_pengyou)
    private RelativeLayout rl_pengyou;
    @ViewInject(R.id.rl_dismiss)
    private RelativeLayout tv_cancel;


    DisplayImageOptions options;
    DisplayImageOptions optionsone;
    private String distributorid = "";
    private List<String> xian_lists = new ArrayList<String>();
    private List<String> group_lists = new ArrayList<String>();

    private String share_shop_img;
    private String share_shop_name;
    private String share_shop_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_extension);
        ViewUtils.inject(this);
        tv_title.setText("二维码推广");
        share_shop_name = Constants.SHOP_NAME;
        distributorid = PreferenceHelper.readString(CodeExtensionActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                String sign_ = TGmd5.getMD5(distributorid);
                getData(distributorid, sign_);
            }
        }
    }

    @OnClick({R.id.rl_back, R.id.rl_xianshang, R.id.rl_group, R.id.btn_login, R.id.btn_group, R.id.rl_shop_share, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss})
    public void OnClick(View view) {
        UMImage image = new UMImage(CodeExtensionActivity.this, share_shop_img);
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_xianshang:
                initView();
                tv_xianshang.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                img_xianshang.setVisibility(View.VISIBLE);
                ll_xianshang.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_group:
                initView();
                tv_group.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                img_group.setVisibility(View.VISIBLE);
                ll_group.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_login:
                if (xian_lists.size() >= 1) {
                    MyToast.show(CodeExtensionActivity.this, "正在保存");
                    downimage(xian_lists);
                } else {
                    MyToast.show(CodeExtensionActivity.this, "没有图片");
                }
                break;
            case R.id.rl_shop_share:
                openDialog();
                break;
            case R.id.btn_group:
                if (group_lists.size() >= 1) {
                    MyToast.show(CodeExtensionActivity.this, "正在保存");
                    downimage(group_lists);
                } else {
                    MyToast.show(CodeExtensionActivity.this, "没有图片");
                }
                break;
            case R.id.rl_qq:
                UMWeb web3 = new UMWeb(share_shop_url);
                web3.setTitle(share_shop_name);//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription("轻松游，放心购，国内特产应有尽有");//描述
                new ShareAction(CodeExtensionActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
               /* new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(share_shop_name)
                        .withText("轻松游，放心购，国内特产应有尽有")
                        .withTargetUrl(share_shop_url)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_qzone:
                UMWeb web2 = new UMWeb(share_shop_url);
                web2.setTitle(share_shop_name);//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription("轻松游，放心购，国内特产应有尽有");//描述
                new ShareAction(CodeExtensionActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle(share_shop_name)
                        .withText("轻松游，放心购，国内特产应有尽有")
                        .withTargetUrl(share_shop_url)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_weixin:
                UMWeb web1 = new UMWeb(share_shop_url);
                web1.setTitle(share_shop_name);//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription("轻松游，放心购，国内特产应有尽有");//描述
                new ShareAction(CodeExtensionActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
               /* new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(share_shop_name)
                        .withText("轻松游，放心购，国内特产应有尽有")
                        .withTargetUrl(share_shop_url)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_pengyou:
                UMWeb web = new UMWeb(share_shop_url);
                web.setTitle(share_shop_name);//标题
                web.setThumb(image);  //缩略图
                web.setDescription("轻松游，放心购，国内特产应有尽有");//描述
                new ShareAction(CodeExtensionActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle(share_shop_name)
                        .withText("轻松游，放心购，国内特产应有尽有")
                        .withTargetUrl(share_shop_url)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_dismiss:
                closeDialog();
                break;
        }
    }

    // 弹出对话框
    private void openDialog() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, true);
    }

    // 关闭对话框
    private void closeDialog() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, false);
    }

    public void initView() {
        ll_xianshang.setVisibility(View.GONE);
        ll_group.setVisibility(View.GONE);
        tv_xianshang.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        img_xianshang.setVisibility(View.GONE);
        tv_group.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        img_group.setVisibility(View.GONE);
    }

    /**
     * 获取店铺信息
     *
     * @param distributorid
     * @param sign
     */
    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getMyShopInfo(CodeExtensionActivity.this, maps, new OnRequestListener());
    }


    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String img_path = array.get(0).toString();
                    if (img_path != null && img_path.length() > 0) {
                        xian_lists.add(Url.ROOT + img_path);
                    }
                    options = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.bg_none_good)         // 设置图片加载或解码过程中发生错误显示的图片
                            .build();
                    ImageLoader.getInstance().displayImage(Url.ROOT + img_path + "?v=" + Calendar.getInstance().getTimeInMillis(), img_code, options);

                    share_shop_url = array.get(1).toString();
                    share_shop_img = Url.ROOT + array.get(2).toString();
                    String img_pathone = array.get(3).toString();
                    if (img_pathone != null && img_pathone.length() > 0) {
                        group_lists.add(Url.ROOT + img_pathone);
                    }
                    optionsone = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.bg_none_good)         // 设置图片加载或解码过程中发生错误显示的图片
                            .build();
                    ImageLoader.getInstance().displayImage(Url.ROOT + img_pathone, img_code_group, optionsone);
                } else if (status.equals("0")) {
                    MyToast.show(CodeExtensionActivity.this, jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("请登录")) {
                        openActivity(LoginActivity.class);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(CodeExtensionActivity.this, "");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
