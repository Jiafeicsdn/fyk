package com.lvgou.distribution.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.lvgou.distribution.R;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/12.
 * 天气
 */

public class WeatherWebActivity extends BaseActivity implements View.OnClickListener {

    private String linkUrl = "http://cdn.moji.com/html5/moji_weather/weather/index.html?cityid=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_web);
        initView();
        initLocation();
        initClick();
        showLoadingProgressDialog(this, "");
    }

    public void doUpload() {//latitude=24.455729&longitude=118.09137&callback=MOJI_JSON_CALLBACK
//        Map<String, Object> maps = new HashMap<String, Object>();
      /*  maps.put("latitude", latitude);
        maps.put("longitude", longitude);
//        maps.put("callback", "MOJI_JSON_CALLBACK");
        Log.e("aksfhdksa", "====" + maps);*/
        String url = "http://co.moji.com/api/Cityid/location?";
        String pathUrl = url + "latitude=" + latitude + "&longitude=" + longitude+"&callback=MOJI_JSON_CALLBACK";

        RequestTask.getInstance().moJiWeather(WeatherWebActivity.this, pathUrl, new OnUploadRequestListener());

    }

    private class OnUploadRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            closeLoadingProgressDialog();
            Log.e("kjsajdf", "======" + response);
            String response1=response.replace("MOJI_JSON_CALLBACK(","").replace(")","");
            try {
                JSONObject jsonObject = new JSONObject(response1);
                JSONObject data = jsonObject.getJSONObject("data");
                String cityid = (String) data.get("cityid");
                Log.e("kjhskdjfs", "===" + cityid);
                loadWeb(linkUrl + cityid);
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
            closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
//          closeLoadingProgressDialog();
        }
    }
    //========================

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void initLocation() {
        try {
            if (mLocationClient == null) {
                mLocationOption = new AMapLocationClientOption();
                //初始化定位
                mLocationClient = new AMapLocationClient(getApplicationContext());
                //设置定位回调监听
                mLocationClient.setLocationListener(mLocationListener);
                //设置为高精度定位模式
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
                //设置定位参数
                mLocationClient.setLocationOption(mLocationOption);
                // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                // 在定位结束后，在合适的生命周期调用onDestroy()方法
                // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                mLocationClient.startLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private double latitude = 0.0;
    private double longitude = 0.0;

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    latitude = aMapLocation.getLatitude();//获取纬度
                    longitude = aMapLocation.getLongitude();//获取经度
                    Log.e("skadjss", "==============" + latitude + longitude);
                    doUpload();
                    //定位完就停止
                    mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                    mLocationClient.onDestroy();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };
    //==============


    private RelativeLayout rl_back;
    private TextView tv_title;//标题
    private WebView webView;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("天气");
        webView = (WebView) findViewById(R.id.webView);

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
                view.loadUrl("javascript:function setTop(){document.querySelector('#download').style.display=\"none\";}setTop();");
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                //view.loadUrl("javascript:window.local_obj.showSource((document.getElementsByClassName('g-note').length!=0)?document.getElementsByTagName('img')[1].getAttribute('src').trim()+','+document.getElementsByClassName('g-note')[0].lastChild.nodeValue.trim():document.getElementsByTagName('img')[1].getAttribute('src').trim()+','+'')");
                view.loadUrl("javascript:function setTop(){document.querySelector('#download').style.display=\"none\";}setTop();");
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