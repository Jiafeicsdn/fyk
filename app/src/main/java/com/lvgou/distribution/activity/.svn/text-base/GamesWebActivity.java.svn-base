package com.lvgou.distribution.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;

/**
 * Created by Administrator on 2017/4/1.
 */

public class GamesWebActivity extends BaseActivity implements View.OnClickListener {

    private String linkUrl;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_web);
        Intent intent = getIntent();
        linkUrl = intent.getStringExtra("LinkUrl");
        title = intent.getStringExtra("title");
        initView();
        initClick();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;//标题
    private WebView webView;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
        webView = (WebView) findViewById(R.id.webView);
        loadWeb(linkUrl);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
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
        webView.loadUrl(url);
//        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
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
//                judgeShare(url);
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
//                share_title = title;
//                share_url = view.getUrl();
            }

        });
    }
}
