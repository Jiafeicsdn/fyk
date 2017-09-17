package com.lvgou.distribution.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
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
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.utils.AppManager;

import java.io.File;

/**
 * Created by Administrator on 2016/10/25.
 * 特卖商品
 */
public class TeMeiActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.webView)
    private WebView webView;
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

    private String distributorid = "";
    private String type_share = "";
    private String share_url = "";
    private String share_title = "";
    private String share_url_first = "";
    private String share_content = "";
    private String share_image_url = "";
    private String tuiguang_content = "";
    private String tuiguang_title = "";
    private String tuiguang_url = "";
    private String tuiguang_img_url = "";
    private String url = Url.WEBVIEW_ROOT + "/?distributorId=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(TeMeiActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        loadWeb(url + distributorid + "&isApp=yes");

    }


    @OnClick({R.id.rl_back, R.id.rl_share, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_share:
                openDialog();
                break;
            case R.id.rl_qq:
                share(type_share, "qq");
                closeDialog();
                break;
            case R.id.rl_qzone:
                share(type_share, "qzone");
                closeDialog();
                break;
            case R.id.rl_weixin:
                share(type_share, "weixin");
                closeDialog();
                break;
            case R.id.rl_pengyou:
                share(type_share, "pengyou");
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
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.loadUrl(url);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.local_obj.showSource((document.getElementsByClassName('g-note').length!=0)?document.getElementsByTagName('img')[1].getAttribute('src').trim()+','+document.getElementsByClassName('g-note')[0].lastChild.nodeValue.trim():document.getElementsByTagName('img')[1].getAttribute('src').trim()+','+'')");
                super.onPageFinished(view, url);
                closeLoadingProgressDialog();
                judgeShare(url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                share_title = title;
                share_url = view.getUrl();
            }

        });
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
     * 根据地址 显示分享按钮
     * http://m.quygt.com/product/searchDistributor?distributorId=2  店铺商城首页
     * <p/>
     * http://m.quygt.com/product/details/5628?distributorId=3769  商品详情
     * <p/>
     * http://m.quygt.com/product/hotproduct?distributorId=2&link=techan#Share|http://m.quygt.com/Product/Details/5919?distributorId=2|http://m.quygt.com|父亲节
     * <p/>
     * <p/>
     * http://m.quygt.com/product/hotproduct?distributorId=2&link=techan#Share|http://m.quygt.com/Product/Details/1197?distributorId=2|http://m.quygt.com/UploadFile/GGDicImgDir/2016/10/25/20161025184512695415.jpg|母亲节专供
     * <p/>
     * url = [NSString stringWithFormat:@"http://m.quygt.com/?distributorId=%@&isApp=yes",USER_ID];
     * title = @"蜂优客之蜂卖频道";
     * content = @"汇聚国内外精品爆款，价格更喜人！";
     */
    public void judgeShare(String url) {
        if (url.contains("/?distributorId=") || url.equals("http://m.quygt.com/")) {
            type_share = "1"; // 店铺
            rl_share.setVisibility(View.VISIBLE);
            if (share_title.length() > 12) {
                tv_title.setText(share_title.substring(0, 13) + "...");
            } else {
                tv_title.setText(share_title);
            }
        } else if (url.contains("/product/hotproduct")) {
            rl_share.setVisibility(View.GONE);
            if (url.contains("#Share")) {
                type_share = "2"; //我要推广
                String[] urls_ = url.split("Share");
                String str_one = urls_[1];
                String[] strs = str_one.split("\\|");
                tuiguang_url = strs[1];
                tuiguang_content = strs[3];
                tuiguang_title = strs[3];
                tuiguang_img_url = strs[2];
                openDialog();
            }
        } else if (url.toLowerCase().contains("/product/details")) {
            type_share = "5"; // 商品详情
            rl_share.setVisibility(View.VISIBLE);
            if (share_title.length() > 12) {
                tv_title.setText(share_title.substring(0, 13) + "...");
            } else {
                tv_title.setText(share_title);
            }
        } else {
            rl_share.setVisibility(View.GONE);
            if (share_title.length() > 12) {
                tv_title.setText(share_title.substring(0, 13) + "...");
            } else {
                tv_title.setText(share_title);
            }
        }
    }

    public void share(String type_share, String type) {
        if (share_title.length() == 0) {
            share_title = "蜂优客之蜂卖频道";
        }
        if (share_content.length() == 0) {
            share_content = "汇聚国内外精品爆款，价格更喜人！";
        }
        if (type.equals("qq")) {
            if (type_share.equals("1")) {
                UMImage image = new UMImage(TeMeiActivity.this, R.mipmap.ic_launcher);
                UMWeb web = new UMWeb(url + distributorid);
                web.setTitle("蜂优客之蜂卖频道");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("汇聚国内外精品爆款，价格更喜人！");//描述
                new ShareAction(TeMeiActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle("蜂优客之蜂卖频道")
                        .withText("汇聚国内外精品爆款，价格更喜人！")
                        .withTargetUrl(url + distributorid)
                        .withMedia(image)
                        .share();*/
            } else if (type_share.equals("5")) {
                UMImage image = new UMImage(TeMeiActivity.this, share_url_first);
                UMWeb web = new UMWeb(share_url);
                web.setTitle(share_title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(TeMeiActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url)
                        .withMedia(image)
                        .share();*/
            } else if (type_share.equals("2")) {
                if (!StringUtils.isEmpty(tuiguang_img_url)) {
                    UMImage image = new UMImage(TeMeiActivity.this, tuiguang_img_url);
                    UMWeb web = new UMWeb(tuiguang_url);
                    web.setTitle(tuiguang_title);//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(tuiguang_content);//描述
                    new ShareAction(TeMeiActivity.this)
                            .setPlatform(SHARE_MEDIA.QQ)
                            .setCallback(umShareListener)
                            .withMedia(web)
                            .share();
                    /*new ShareAction(this)
                            .setPlatform(SHARE_MEDIA.QQ)
                            .setCallback(umShareListener)
                            .withTitle(tuiguang_title)
                            .withText(tuiguang_content)
                            .withTargetUrl(tuiguang_url)
                            .withMedia(image)
                            .share();*/
                } else {
                    UMImage image = new UMImage(TeMeiActivity.this, R.mipmap.ic_launcher);
                    UMWeb web = new UMWeb(tuiguang_url);
                    web.setTitle(tuiguang_title);//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(tuiguang_content);//描述
                    new ShareAction(TeMeiActivity.this)
                            .setPlatform(SHARE_MEDIA.QQ)
                            .setCallback(umShareListener)
                            .withMedia(web)
                            .share();
                    /*new ShareAction(this)
                            .setPlatform(SHARE_MEDIA.QQ)
                            .setCallback(umShareListener)
                            .withTitle(tuiguang_title)
                            .withText(tuiguang_content)
                            .withTargetUrl(tuiguang_url)
                            .withMedia(image)
                            .share();*/
                }
            }
        } else if (type.equals("qzone")) {
            if (type_share.equals("1")) {
                UMImage image = new UMImage(TeMeiActivity.this, R.mipmap.ic_launcher);
                UMWeb web = new UMWeb(url + distributorid);
                web.setTitle("蜂优客之蜂卖频道");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("汇聚国内外精品爆款，价格更喜人！");//描述
                new ShareAction(TeMeiActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle("蜂优客之蜂卖频道")
                        .withText("汇聚国内外精品爆款，价格更喜人！")
                        .withTargetUrl(url + distributorid)
                        .withMedia(image)
                        .share();*/
            } else if (type_share.equals("5")) {
                UMImage image = new UMImage(TeMeiActivity.this, share_url_first);
                UMWeb web = new UMWeb(share_url);
                web.setTitle(share_title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(TeMeiActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url)
                        .withMedia(image)
                        .share();*/
            } else if (type_share.equals("2")) {
                if (!StringUtils.isEmpty(tuiguang_img_url)) {
                    UMImage image = new UMImage(TeMeiActivity.this, tuiguang_img_url);
                    UMWeb web = new UMWeb(tuiguang_url);
                    web.setTitle(tuiguang_title);//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(tuiguang_content);//描述
                    new ShareAction(TeMeiActivity.this)
                            .setPlatform(SHARE_MEDIA.QZONE)
                            .setCallback(umShareListener)
                            .withMedia(web)
                            .share();
                   /* new ShareAction(this)
                            .setPlatform(SHARE_MEDIA.QZONE)
                            .setCallback(umShareListener)
                            .withTitle(tuiguang_title)
                            .withText(tuiguang_content)
                            .withTargetUrl(tuiguang_url)
                            .withMedia(image)
                            .share();*/
                } else {
                    UMImage image = new UMImage(TeMeiActivity.this, R.mipmap.ic_launcher);
                    UMWeb web = new UMWeb(tuiguang_url);
                    web.setTitle(tuiguang_title);//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(tuiguang_content);//描述
                    new ShareAction(TeMeiActivity.this)
                            .setPlatform(SHARE_MEDIA.QZONE)
                            .setCallback(umShareListener)
                            .withMedia(web)
                            .share();
                   /* new ShareAction(this)
                            .setPlatform(SHARE_MEDIA.QZONE)
                            .setCallback(umShareListener)
                            .withTitle(tuiguang_title)
                            .withText(tuiguang_content)
                            .withTargetUrl(tuiguang_url)
                            .withMedia(image)
                            .share();*/
                }
            }
        } else if (type.equals("weixin")) {
            if (type_share.equals("1")) {
                UMImage image = new UMImage(TeMeiActivity.this, R.mipmap.ic_launcher);
                UMWeb web = new UMWeb(url + distributorid);
                web.setTitle("蜂优客之蜂卖频道");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("汇聚国内外精品爆款，价格更喜人！");//描述
                new ShareAction(TeMeiActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle("蜂优客之蜂卖频道")
                        .withText("汇聚国内外精品爆款，价格更喜人！")
                        .withTargetUrl(url + distributorid)
                        .withMedia(image)
                        .share();*/
            } else if (type_share.equals("5")) {
                UMImage image = new UMImage(TeMeiActivity.this, share_url_first);
                UMWeb web = new UMWeb(share_url);
                web.setTitle(share_title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(TeMeiActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
               /* new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url)
                        .withMedia(image)
                        .share();*/
            } else if (type_share.equals("2")) {
                if (!StringUtils.isEmpty(tuiguang_img_url)) {
                    UMImage image = new UMImage(TeMeiActivity.this, tuiguang_img_url);
                    UMWeb web = new UMWeb(tuiguang_url);
                    web.setTitle(tuiguang_title);//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(tuiguang_content);//描述
                    new ShareAction(TeMeiActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .setCallback(umShareListener)
                            .withMedia(web)
                            .share();
                   /* new ShareAction(this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .setCallback(umShareListener)
                            .withTitle(tuiguang_title)
                            .withText(tuiguang_content)
                            .withTargetUrl(tuiguang_url)
                            .withMedia(image)
                            .share();*/
                } else {
                    UMImage image = new UMImage(TeMeiActivity.this, R.mipmap.ic_launcher);
                    UMWeb web = new UMWeb(tuiguang_url);
                    web.setTitle(tuiguang_title);//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(tuiguang_content);//描述
                    new ShareAction(TeMeiActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .setCallback(umShareListener)
                            .withMedia(web)
                            .share();
                    /*new ShareAction(this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .setCallback(umShareListener)
                            .withTitle(tuiguang_title)
                            .withText(tuiguang_content)
                            .withTargetUrl(tuiguang_url)
                            .withMedia(image)
                            .share();*/
                }
            }
        } else if (type.equals("pengyou")) {
            if (type_share.equals("1")) {
                UMImage image = new UMImage(TeMeiActivity.this, R.mipmap.ic_launcher);
                UMWeb web = new UMWeb(url + distributorid);
                web.setTitle("蜂优客之蜂卖频道");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("汇聚国内外精品爆款，价格更喜人！");//描述
                new ShareAction(TeMeiActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle("蜂优客之蜂卖频道")
                        .withText("汇聚国内外精品爆款，价格更喜人！")
                        .withTargetUrl(url + distributorid)
                        .withMedia(image)
                        .share();*/
            } else if (type_share.equals("5")) {
                UMImage image = new UMImage(TeMeiActivity.this, share_url_first);
                UMWeb web = new UMWeb(share_url);
                web.setTitle(share_title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(TeMeiActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url)
                        .withMedia(image)
                        .share();*/
            } else if (type_share.equals("2")) {
                if (!StringUtils.isEmpty(tuiguang_img_url)) {
                    UMImage image = new UMImage(TeMeiActivity.this, tuiguang_img_url);
                    UMWeb web = new UMWeb(tuiguang_url);
                    web.setTitle(tuiguang_title);//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(tuiguang_content);//描述
                    new ShareAction(TeMeiActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .setCallback(umShareListener)
                            .withMedia(web)
                            .share();
                   /* new ShareAction(this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .setCallback(umShareListener)
                            .withTitle(tuiguang_title)
                            .withText(tuiguang_content)
                            .withTargetUrl(tuiguang_url)
                            .withMedia(image)
                            .share();*/
                } else {
                    UMImage image = new UMImage(TeMeiActivity.this, R.mipmap.ic_launcher);
                    UMWeb web = new UMWeb(tuiguang_url);
                    web.setTitle(tuiguang_title);//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(tuiguang_content);//描述
                    new ShareAction(TeMeiActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .setCallback(umShareListener)
                            .withMedia(web)
                            .share();
                   /* new ShareAction(this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .setCallback(umShareListener)
                            .withTitle(tuiguang_title)
                            .withText(tuiguang_content)
                            .withTargetUrl(tuiguang_url)
                            .withMedia(image)
                            .share();*/
                }
            }
        }
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            String[] str = html.split(",");
            share_url_first = str[0].toString();
            if (str.length > 1) {
                share_content = str[1].toString();
            } else {
                share_content = "";
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

