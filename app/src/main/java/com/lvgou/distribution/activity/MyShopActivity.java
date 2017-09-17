package com.lvgou.distribution.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
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
 * Created by Snow on 2016/3/21 0021.
 * 我的店铺 老版，可删
 */
public class MyShopActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.img_share)
    private ImageView img_share;
    @ViewInject(R.id.img_code)
    private ImageView img_code;
    @ViewInject(R.id.tv_press_long)
    private TextView tv_press_long;
    @ViewInject(R.id.tv_net_address)
    private TextView tv_net_address;
    @ViewInject(R.id.tv_save_code)
    private TextView tv_save_code;
    @ViewInject(R.id.tv_go_shop)
    private TextView tv_go_shop;
    @ViewInject(R.id.rl_share)
    private RelativeLayout rl_share;
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
    private RelativeLayout rl_dismiss;
    private String shopname;
    private String distributorid = "";

    private String img_path;
    DisplayImageOptions options;
    private String share_img = "";
    private String index = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        ViewUtils.inject(this);
        shopname = getTextFromBundle("shopname");
        index = getTextFromBundle("index");
        tv_title.setText(shopname);

        distributorid = PreferenceHelper.readString(MyShopActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (checkNet()) {
            String sign_ = TGmd5.getMD5(distributorid);
            getData(distributorid, sign_);
        }
    }

    @OnClick({R.id.rl_back, R.id.rl_share, R.id.tv_press_long, R.id.tv_save_code, R.id.tv_go_shop, R.id.tv_net_address, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss})
    public void OnClick(View view) {
        UMImage image = new UMImage(MyShopActivity.this, share_img);
        Bundle pBundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    pBundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, pBundle);
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.rl_share:
                openDialog();
                break;
            case R.id.tv_save_code:
                List<String> urls = new ArrayList<String>();
                String img_path_ = Url.ROOT + img_path;
                urls.add(img_path_);
                if (urls.size() >= 1) {
                    MyToast.show(MyShopActivity.this, "正在保存");
                    downimage(urls);
                } else {
                    MyToast.show(MyShopActivity.this, "没有图片");
                }
                break;
            case R.id.tv_go_shop:
                pBundle.putString("shopname", shopname);
                openActivity(ShopMnagerActivity.class);
                break;
            case R.id.tv_net_address:
                String text = tv_net_address.getText().toString().trim();
                ClipboardManager myClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(clipData);
                MyToast.show(MyShopActivity.this, "复制成功");
                break;
            case R.id.rl_qq:
                UMWeb web = new UMWeb(tv_net_address.getText().toString().trim());
                web.setTitle(shopname);//标题
                web.setThumb(image);  //缩略图
                web.setDescription("轻松游，放心购，国内特产应有尽有");//描述
                new ShareAction(MyShopActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(shopname)
                        .withText("轻松游，放心购，国内特产应有尽有")
                        .withTargetUrl(tv_net_address.getText().toString().trim())
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_qzone:
                UMWeb web1 = new UMWeb(tv_net_address.getText().toString().trim());
                web1.setTitle(shopname);//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription("轻松游，放心购，国内特产应有尽有");//描述
                new ShareAction(MyShopActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle(shopname)
                        .withText("轻松游，放心购，国内特产应有尽有")
                        .withTargetUrl(tv_net_address.getText().toString().trim())
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_weixin:
                UMWeb web2 = new UMWeb(tv_net_address.getText().toString().trim());
                web2.setTitle(shopname);//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription("轻松游，放心购，国内特产应有尽有");//描述
                new ShareAction(MyShopActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(shopname)
                        .withText("轻松游，放心购，国内特产应有尽有")
                        .withTargetUrl(tv_net_address.getText().toString().trim())
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_pengyou:
                UMWeb web3 = new UMWeb(tv_net_address.getText().toString().trim());
                web3.setTitle(shopname);//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription("轻松游，放心购，国内特产应有尽有");//描述
                new ShareAction(MyShopActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle(shopname)
                        .withText("轻松游，放心购，国内特产应有尽有")
                        .withTargetUrl(tv_net_address.getText().toString().trim())
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
        RequestTask.getInstance().getMyShopInfo(MyShopActivity.this, maps, new OnRequestListener());
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
                    img_path = array.get(0).toString();
                    options = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.bg_none_good)         // 设置图片加载或解码过程中发生错误显示的图片
                            .cacheInMemory(false)                            // 设置下载的图片是否缓存在内存中
                            .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                            .build();
                    ImageLoader.getInstance().displayImage(Url.ROOT + img_path + "?v=" + Calendar.getInstance().getTimeInMillis(), img_code, options);
                    String address = array.get(1).toString();
                    tv_net_address.setText(address);
                    share_img = Url.ROOT + array.get(2).toString();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showProgressDialog("加载中...");
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            dismissProgressDialog();
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            dismissProgressDialog();
        }
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
