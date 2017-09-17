package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.cache.CacheData;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;
import com.xdroid.request.extension.interfaces.OnRequestListener;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/3/28 0028.
 * 公告详情
 */
public class NoticeDetialActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.webView)
    private WebView webView;
    @ViewInject(R.id.img_search)
    private ImageView img_search;
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


    private String ROOT = Url.XIANSHANG_ROOT + "/user/messagedetailapi/";
    private String id = "";
    private String distributorid = "";

    private String share_title = "";
    private String share_url = "";
    private String index = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detial);
        ViewUtils.inject(this);
        id = getTextFromBundle("id");
        tv_title.setText("最新活动");
        index = getTextFromBundle("index");
        distributorid = PreferenceHelper.readString(NoticeDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + id);
        if (checkNet()) {
            showLoadingProgressDialog(NoticeDetialActivity.this, "");
            readNotice(distributorid, id, sign);
            loadWeb(ROOT + id);
        }
    }

    @OnClick({R.id.rl_back, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss, R.id.img_search})
    public void OnClick(View view) {
        UMImage image = new UMImage(NoticeDetialActivity.this, "http://m.quygt.com/Content/images2/onerror/lgtlogo.png");
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
            case R.id.rl_qq:
                UMWeb web = new UMWeb(share_url);
                web.setTitle(share_title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription("蜂优客国内领先的导游移动工作平台！");//描述
                new ShareAction(NoticeDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
               /* new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText("蜂优客国内领先的导游移动工作平台！")
                        .withTargetUrl(share_url)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_qzone:
                UMWeb web1 = new UMWeb(share_url);
                web1.setTitle(share_title);//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription("蜂优客国内领先的导游移动工作平台！");//描述
                new ShareAction(NoticeDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText("蜂优客国内领先的导游移动工作平台！")
                        .withTargetUrl(share_url)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_weixin:
                UMWeb web2 = new UMWeb(share_url);
                web2.setTitle(share_title);//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription("蜂优客国内领先的导游移动工作平台！");//描述
                new ShareAction(NoticeDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText("蜂优客国内领先的导游移动工作平台！")
                        .withTargetUrl(share_url)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_pengyou:
                UMWeb web3 = new UMWeb(share_url);
                web3.setTitle(share_title);//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription("蜂优客国内领先的导游移动工作平台！");//描述
                new ShareAction(NoticeDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText("蜂优客国内领先的导游移动工作平台！")
                        .withTargetUrl(share_url)
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_dismiss:
                closeDialog();
                break;
            case R.id.img_search:
                openDialog();
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
        closeLoadingProgressDialog();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                closeLoadingProgressDialog();
                share_title = title;
                share_url = view.getUrl();
            }
        });
    }

    public void readNotice(String distributorid, String activityid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("activityid", activityid);
        maps.put("sign", sign);
        RequestTask.getInstance().readNotice(NoticeDetialActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
//            closeLoadingProgressDialog();
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


}
