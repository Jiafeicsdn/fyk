package com.lvgou.distribution.api;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lvgou.distribution.activity.ApplicationActivity;
import com.lvgou.distribution.base.BaseApplication;
import com.lvgou.distribution.bean.CallBackVo;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.ErrorLogPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.BaseView1;
import com.squareup.okhttp.OkHttpClient;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/9.
 */
public class Api implements BaseView1 {

    private static Api ourInstance;

    private IServiceAPI gankService;
    private ErrorLogPresenter errorLogPresenter;


    public static Api getInstance() {
        if (ourInstance == null) ourInstance = new Api();
        return ourInstance;
    }

    public boolean isOne = true;

    private Api() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(7676, TimeUnit.MILLISECONDS);
        errorLogPresenter = new ErrorLogPresenter(this);
        isOne = true;
        /*
         * 查看网络请求发送状况
         */
//            if (EasyApplication.getInstance().log) {
//                okHttpClient.interceptors().add(chain -> {
//                    Response response = chain.proceed(chain.request());
//                    com.orhanobut.logger.Logger.d(chain.request().urlString());
//                    return response;
//                });
//            }
        Gson mGson = new GsonBuilder()
                .registerTypeAdapter(String.class, new DeserializerData())
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.ROOT)
                .addCallAdapterFactory(
                        RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .client(okHttpClient)
                .build();
        this.gankService = retrofit.create(IServiceAPI.class);
    }


    public IServiceAPI getGankService() {
        return gankService;
    }

    /**
     * 创建 Subscriber
     *
     * @param mICallBackListener
     * @return Subscriber
     */
    public Subscriber createSubscriber(final ICallBackListener mICallBackListener) {
        Subscriber mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
//                Log.i(TAG, "[onCompleted]");
            }

            @Override
            public void onError(Throwable e) {
//                Log.e(TAG, "[onError]" + e.getMessage());
                CallBackVo mCallBackVo = new CallBackVo();
//                mCallBackVo.setResCode("400");
//                mCallBackVo.setResMsg("请求失败");
//                mCallBackVo.setResObj(null);
                if (isOne) {
                    Context context = BaseApplication.getAppContext();
                    String distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                    String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

                    //-----------------------------
                    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
                    //获取wifi服务
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    //判断wifi是否开启
                    if (!wifiManager.isWifiEnabled()) {
                        wifiManager.setWifiEnabled(true);
                    }
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    int ipAddress = wifiInfo.getIpAddress();

                    PackageManager manager;

                    PackageInfo info = null;

                    manager = context.getPackageManager();

                    try {

                        info = manager.getPackageInfo(context.getPackageName(), 0);

                    } catch (PackageManager.NameNotFoundException m) {

// TODO Auto-generated catch block

                        m.printStackTrace();

                    }

                    StringBuilder sb = new StringBuilder();
                    String diviceName = GetDeviceName();
                    sb.append("手机型号 : " + diviceName+";  ");
//                    sb.append("DeviceId(IMEI) = " + tm.getDeviceId()+";");
                    sb.append("手机号 : " + tm.getLine1Number()+";  ");
                    sb.append("网络类型 = " + tm.getNetworkOperatorName()+";  ");
                    sb.append("网络 = " + networkInfo.getType()+";  ");
                    sb.append("SimSerialNumber = " + tm.getSimSerialNumber()+";  ");
//                    sb.append("SubscriberId(IMSI) = " + tm.getSubscriberId()+";");
                    sb.append("内存:" + getMemoryInfo(Environment.getDataDirectory(),context)+";  ");
                    sb.append("IP地址:" + intToIp(ipAddress)+";  ");
                    sb.append("app版本:" + info.versionCode+";  ");
                    sb.append("SDK版本:" + android.os.Build.VERSION.SDK+";  ");
                    sb.append("系统版本:" + android.os.Build.VERSION.RELEASE+";  ");
                    //------------------------------
                    String sign = TGmd5.getMD5(distributorid + 2 + androidId + "errorurl" + sb.toString() + e.toString());
                    errorLogPresenter.apperrorlog(distributorid, 2, androidId, "errorurl", sb.toString(), e.toString(), sign);
                    isOne = false;
                }


                mICallBackListener.onFaild("请求失败");

                return;
            }

            @Override
            public void onNext(String s) {
                Gson gosn = new Gson();
                CallBackVo mCallBackVo = gosn.fromJson(s, CallBackVo.class);
                if (mCallBackVo.getStatus().equals("1")) {
                    mICallBackListener.onSuccess(s);
                } else {
                    if (isOne) {
                        Context context = BaseApplication.getAppContext();
                        String distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

                        //-----------------------------
                        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
                        //获取wifi服务
                        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                        //判断wifi是否开启
                        if (!wifiManager.isWifiEnabled()) {
                            wifiManager.setWifiEnabled(true);
                        }
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        int ipAddress = wifiInfo.getIpAddress();

                        PackageManager manager;

                        PackageInfo info = null;

                        manager = context.getPackageManager();

                        try {

                            info = manager.getPackageInfo(context.getPackageName(), 0);

                        } catch (PackageManager.NameNotFoundException m) {

// TODO Auto-generated catch block

                            m.printStackTrace();

                        }

                        StringBuilder sb = new StringBuilder();
                        String diviceName = GetDeviceName();
                        sb.append("手机型号 : " + diviceName+";  ");
//                        sb.append("DeviceId(IMEI) = " + tm.getDeviceId());
                        sb.append("手机号 : " + tm.getLine1Number()+";  ");
                        sb.append("网络类型 = " + tm.getNetworkOperatorName()+";  ");
                        sb.append("网络 = " + networkInfo.getType()+";  ");
                        sb.append("SimSerialNumber = " + tm.getSimSerialNumber()+";  ");
//                        sb.append("SubscriberId(IMSI) = " + tm.getSubscriberId());
                        sb.append("内存:" + getMemoryInfo(Environment.getDataDirectory(),context)+";  ");

                        sb.append("IP地址:" + intToIp(ipAddress)+";  ");
                        sb.append("app版本:" + info.versionCode+";  ");
                        sb.append("SDK版本:" + android.os.Build.VERSION.SDK+";  ");
                        sb.append("系统版本:" + android.os.Build.VERSION.RELEASE+";  ");
                        //------------------------------
                        String sign = TGmd5.getMD5(distributorid + 2 + androidId + "errorurl" + sb.toString() + s.toString());
                        errorLogPresenter.apperrorlog(distributorid, 2, androidId, "errorurl", sb.toString(), s.toString(), sign);
                        isOne = false;
                    }


                    mICallBackListener.onFaild(mCallBackVo.getMessage());
                }
            }
        };
        return mSubscriber;
    }

    /* distributorId 	是 	long 	导游ID
     type 	是 	int 	设备类型：1=IOS，2=安卓
     ippath 	是 	string 	设备IP地址
     errorurl 	是 	string 	接口地址
     appintro 	是 	string 	设备信息描述
     errorintro 	是 	string 	错误内容
     sign 	是 	string 	用户名*/
    @SuppressWarnings("static-access")
    public static String GetDeviceName() {
        return new Build().MODEL;
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteErrSuccessCallBack(String s) {
    }

    @Override
    public void excuteErrFailedCallBack(String s) {
    }


    /**
     * 根据路径获取内存状态
     * @param path
     * @return
     */
    private String getMemoryInfo(File path,Context context) {
        // 获得一个磁盘状态对象
        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSize();   // 获得一个扇区的大小

        long totalBlocks = stat.getBlockCount();    // 获得扇区的总数

        long availableBlocks = stat.getAvailableBlocks();   // 获得可用的扇区数量

        // 总空间
        String totalMemory =  Formatter.formatFileSize(context, totalBlocks * blockSize);
        // 可用空间
        String availableMemory = Formatter.formatFileSize(context, availableBlocks * blockSize);

        return "总空间: " + totalMemory + " 可用空间: " + availableMemory;
    }



    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }
}
