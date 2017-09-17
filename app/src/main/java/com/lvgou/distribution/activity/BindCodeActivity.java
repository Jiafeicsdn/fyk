package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
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
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2016/4/28 0028.
 * 选择城市
 */
public class BindCodeActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_share)
    private RelativeLayout rl_share;
    @ViewInject(R.id.webview)
    private WebView webView;
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
    @ViewInject(R.id.img_custom_service)
    private ImageView img_custom_service;
    DisplayImageOptions options;

    private Dialog dialog;
    private String supplyId;
    private String index;
    private String distributorid;

    private List<String> listUrls = new ArrayList<String>();
    private String URL = Url.XIANSHANG_ROOT + "/supply/scancode1?distributorId=";

    private String URL_ONE = Url.XIANSHANG_ROOT + "/supply/supplyinfo?supplyId=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_code);
        ViewUtils.inject(this);
        tv_title.setText("蜂优客");
        supplyId = getTextFromBundle("supplyId");
        index = getTextFromBundle("index");
        distributorid = PreferenceHelper.readString(BindCodeActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (index.equals("1")) {
            loadWeb(URL_ONE + supplyId);
            rl_share.setVisibility(View.GONE);
        } else if (index.equals("2")) {
            loadWeb(URL + distributorid + "&supplyId=" + supplyId);
            rl_share.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.rl_back, R.id.rl_share, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.img_custom_service, R.id.rl_dismiss})
    public void OnClick(View view) {
        UMImage image = new UMImage(BindCodeActivity.this, "http://m.quygt.com/Content/images2/onerror/lgtlogo.png");
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_share:
                openDialogShare();
                break;
            case R.id.rl_qq:
                UMWeb web = new UMWeb(URL + distributorid + "&supplyId=" + supplyId);
                web.setTitle("扫码即抢新手大礼包！");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("随时赚，分享给身边好友，好用好玩的APP和微信。注册下载，即可享受新手福利！");//描述
                new ShareAction(BindCodeActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();

               /* new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle("扫码即抢新手大礼包！")
                        .withText("随时赚，分享给身边好友，好用好玩的APP和微信。注册下载，即可享受新手福利！")
                        .withTargetUrl(URL + distributorid + "&supplyId=" + supplyId)
                        .withMedia(image)
                        .share();
                closeDialogShare();*/
                break;
            case R.id.rl_qzone:
                UMWeb web1 = new UMWeb(URL + distributorid + "&supplyId=" + supplyId);
                web1.setTitle("扫码即抢新手大礼包！");//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription("随时赚，分享给身边好友，好用好玩的APP和微信。注册下载，即可享受新手福利！");//描述
                new ShareAction(BindCodeActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle("扫码即抢新手大礼包！")
                        .withText("随时赚，分享给身边好友，好用好玩的APP和微信。注册下载，即可享受新手福利！")
                        .withTargetUrl(URL + distributorid + "&supplyId=" + supplyId)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_weixin:
                UMWeb web2 = new UMWeb(URL + distributorid + "&supplyId=" + supplyId);
                web2.setTitle("扫码即抢新手大礼包！");//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription("随时赚，分享给身边好友，好用好玩的APP和微信。注册下载，即可享受新手福利！");//描述
                new ShareAction(BindCodeActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle("扫码即抢新手大礼包！")
                        .withText("随时赚，分享给身边好友，好用好玩的APP和微信。注册下载，即可享受新手福利！")
                        .withTargetUrl(URL + distributorid + "&supplyId=" + supplyId)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_pengyou:
                UMWeb web3 = new UMWeb(URL + distributorid + "&supplyId=" + supplyId);
                web3.setTitle("扫码即抢新手大礼包！");//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription("随时赚，分享给身边好友，好用好玩的APP和微信。注册下载，即可享受新手福利！");//描述
                new ShareAction(BindCodeActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle("扫码即抢新手大礼包！")
                        .withText("随时赚，分享给身边好友，好用好玩的APP和微信。注册下载，即可享受新手福利！")
                        .withTargetUrl(URL + distributorid + "&supplyId=" + supplyId)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_dismiss:
                closeDialogShare();
                break;
            case R.id.img_custom_service:
                Intent intent = new MQIntentBuilder(this).build();
                startActivity(intent);
                break;
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

    // 弹出拍照对话框
    private void openDialogShare() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, true);
    }

    // 关闭拍照对话框
    private void closeDialogShare() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, false);

    }


    /**
     * 加载网页
     *
     * @param url
     */
    public void loadWeb(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (view.getUrl().contains("#aPhotoDown")) {
                    String[] str = view.getUrl().split("#aPhotoDown");
                    listUrls.add(str[1]);
                    showShopDialog();
                }
            }
        });
    }

    public void showShopDialog() {
        dialog = new Dialog(BindCodeActivity.this, R.style.Mydialog);
        View view1 = View.inflate(BindCodeActivity.this,
                R.layout.dialog_show_shop, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_head = (TextView) view1.findViewById(R.id.tv_title);
        tv_head.setText("确定保存二维码？");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.show(BindCodeActivity.this, "正在保存");
                if (listUrls != null && listUrls.size() > 0) {
                    downimage(listUrls);
                }
                dialog.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view1);
        dialog.show();
    }
}
