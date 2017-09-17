package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;

/**
 * Created by Snow on 2016/5/31 0031.
 * 推送，网页加载
 */
public class WebViewActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.webView)
    private WebView webView;

    private String url = "";
    private String distributorid = "";
    private String index = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(WebViewActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        url = getTextFromBundle("url");
        index = getTextFromBundle("index");
        if (url != null && url.length() > 0) {
            if (url.contains("product/sellerproduct")) {
                loadWeb(url + "&distributorId=" + distributorid);
            } else {
                loadWeb(url);
            }
        }
    }

    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    bundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, bundle);
                    finish();
                } else {
                    finish();
                }
                break;
        }
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
        showLoadingProgressDialog(WebViewActivity.this, "");
        webView.loadUrl(url);
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
                if (title.length() > 12) {
                    tv_title.setText(title.substring(0, 13) + "...");
                } else {
                    tv_title.setText(title);
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
