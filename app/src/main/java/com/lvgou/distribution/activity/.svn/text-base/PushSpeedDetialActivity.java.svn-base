package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;


/**
 * Created by Administrator on 2016/3/19 0019.
 * 爆品速推详情页
 */
public class PushSpeedDetialActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.web)
    private WebView webView;
    @ViewInject(R.id.rl_publish)
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

    private String distributorid = "";
    private String goods_id = "";
    private String type_share;// 1: 商品，2：品牌商品 3：海外商品
    private String share_title = "";
    private String share_img = "";
    private String share_content = "";
    private String share_url = "";

    private String shop_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_speed_detial);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(PushSpeedDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        goods_id = getTextFromBundle("goods_id");
        shop_name = getTextFromBundle("shop_name");
        type_share = getTextFromBundle("type_share");
        if (checkNet()) {
            loadWeb(Url.WEBVIEW_ROOT + "/product/details/" + goods_id + "?distributorId=" + distributorid + "&source=5");
        }
    }

    @OnClick({R.id.rl_back, R.id.rl_publish, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
                openDialog();
                break;
            case R.id.rl_qq:
                if (share_content.length() <= 0) {
                    share_content = "轻松游，放心购，国内商品应有尽有!";
                }
                if (share_img.length() <= 0) {
                    share_img = "http://m.quygt.com/Content/images2/onerror/lgtlogo.png";
                }
                UMImage image = new UMImage(PushSpeedDetialActivity.this, share_img);
                UMWeb web = new UMWeb(share_url);
                web.setTitle(share_title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(PushSpeedDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
               /* new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_qzone:
                if (share_content.length() <= 0) {
                    share_content = "轻松游，放心购，国内商品应有尽有!";
                }
                if (share_img.length() <= 0) {
                    share_img = "http://m.quygt.com/Content/images2/onerror/lgtlogo.png";
                }
                UMImage image_1 = new UMImage(PushSpeedDetialActivity.this, share_img);
                UMWeb web1 = new UMWeb(share_url);
                web1.setTitle(share_title);//标题
                web1.setThumb(image_1);  //缩略图
                web1.setDescription(share_content);//描述
                new ShareAction(PushSpeedDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url)
                        .withMedia(image_1)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_weixin:
                if (share_content.length() <= 0) {
                    share_content = "轻松游，放心购，国内商品应有尽有!";
                }
                if (share_img.length() <= 0) {
                    share_img = "http://m.quygt.com/Content/images2/onerror/lgtlogo.png";
                }
                UMImage image_2 = new UMImage(PushSpeedDetialActivity.this, share_img);
                UMWeb web2 = new UMWeb(share_url);
                web2.setTitle(share_title);//标题
                web2.setThumb(image_2);  //缩略图
                web2.setDescription(share_content);//描述
                new ShareAction(PushSpeedDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url)
                        .withMedia(image_2)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_pengyou:
                if (share_content.length() <= 0) {
                    share_content = "轻松游，放心购，国内商品应有尽有!";
                }
                if (share_img.length() <= 0) {
                    share_img = "http://m.quygt.com/Content/images2/onerror/lgtlogo.png";
                }
                UMImage image_3 = new UMImage(PushSpeedDetialActivity.this, share_img);
                UMWeb web3 = new UMWeb(share_url);
                web3.setTitle(share_title);//标题
                web3.setThumb(image_3);  //缩略图
                web3.setDescription(share_content);//描述
                new ShareAction(PushSpeedDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url)
                        .withMedia(image_3)
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


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.setVisibility(View.GONE);
    }

    /**
     * 加载网页
     *
     * @param url
     */
    public void loadWeb(String url) {
        showProgressDialog("加载中...");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.loadUrl(url);
        webView.requestFocus();
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.local_obj.showSource((document.getElementsByClassName('g-note').length!=0)?document.getElementsByTagName('img')[3].getAttribute('src').trim()+','+document.getElementsByClassName('g-note')[0].lastChild.nodeValue.trim():document.getElementsByTagName('img')[3].getAttribute('src').trim()+','+'')");
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                dismissProgressDialog();
                share_title = title;
                share_url = view.getUrl();
                judgeUrl(share_url);

            }
        });
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            String str[] = html.split(",");
            share_img = str[0].toString();
            if (str.length > 1) {
                share_content = str[1].toString();
            } else {
                if (type_share.equals("1")) {
                    share_content = "轻松游，放心购，国内商品应有尽有!";
                } else if (type_share.equals("2")) {
                    share_content = "轻松游，放心购，国内商品应有尽有!";
                } else if (type_share.equals("3")) {
                    share_content = "轻松游，放心购，海外商品应有尽有!";
                }
            }
        }
    }

    public void judgeUrl(String url) {
        if (url.contains("/product/searchDistributor") || url.contains("/product/searchdistributor")) {
            type_share = "1"; // 店铺
            rl_share.setVisibility(View.VISIBLE);
            tv_title.setText(Constants.SHOP_NAME);
        } else if (url.contains("/product/overseasproduct")) {
            type_share = "2"; // 海外商品
            rl_share.setVisibility(View.VISIBLE);
            tv_title.setText(Constants.SHOP_NAME);
        } else if (url.contains("/product/sellerproduct")) {
            type_share = "3"; // 品牌商品
            rl_share.setVisibility(View.VISIBLE);
            tv_title.setText(Constants.SHOP_NAME);
        } else if (url.contains("/product/searchsellerproduct")) {
            type_share = "4"; // 搜索商品
            rl_share.setVisibility(View.VISIBLE);
            tv_title.setText(Constants.SHOP_NAME);
        } else if (url.contains("/product/details")) {
            type_share = "5"; // 商品详情
            rl_share.setVisibility(View.VISIBLE);
            tv_title.setText(Constants.SHOP_NAME);
        } else {
            rl_share.setVisibility(View.GONE);
            if (share_title.length() > 12) {
                tv_title.setText(share_title.substring(0, 13) + "...");
            } else {
                tv_title.setText(share_title);
            }
        }
    }
}
