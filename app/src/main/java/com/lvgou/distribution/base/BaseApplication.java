package com.lvgou.distribution.base;


import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.activity.image.ImageLoaderConfig;
import com.lvgou.distribution.request.RequestManager;
import com.lvgou.distribution.utils.PathUtil;
import com.meiqia.core.MQManager;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;


/**
 * Created by Snow on 2016/3/7 0007.
 */
public class BaseApplication extends MultiDexApplication {

    private static final String JCHAT_CONFIGS = "JChat_configs";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static final String TARGET_ID = "targetId";
    public static final String GROUP_ID = "groupId";
    public static String PICTURE_DIR = "sdcard/JChatDemo/pictures/";

    public static BaseApplication context;

    public static Context getAppContext(){
        return context;

    }
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        // 捕获Crash异常并处理
        AppExceptionHandler mAppExceptionHandler = new AppExceptionHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(mAppExceptionHandler);


        // 全局日志控制
        LogUtils.customTagPrefix = "system.out"; // 设置前缀
        LogUtils.allowI = true; // 是否允许打印
        LogUtils.allowE = true; // 是否允许打印

        MobclickAgent.openActivityDurationTrack(false);

        initImageLoader(getApplicationContext());

        PathUtil.getInstance().initDirs("cache","files",getApplicationContext());
        //初始化美洽
        initMeiqiaSDK();
        // 初始化网络请求，启动队列
        RequestManager.getInstance().initRequest();
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        //初始化JMessage-sdk
        JMessageClient.init(getApplicationContext());
//      SharePreferenceManager.init(getApplicationContext(), JCHAT_CONFIGS);
//      //设置Notification的模式
        JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);
//      //注册Notification点击的接收器
//      NotificationClickEventReceiver notificationClickEventReceiver = new NotificationClickEventReceiver(getApplicationContext());

    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        Config.DEBUG = true;
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx384a0e20d6d4b6bd", "fed76a8faf70e872bf2404d77f4e9559");
        //QQ
        PlatformConfig.setQQZone("1104688964", "GxY7p5s7N7HEDgjI");

    }

    //美洽
    private void initMeiqiaSDK() {
        MQManager.setDebugMode(true);
        // 替换成自己的key
        String meiqiaKey = "84abf3e6f5738bbe085d6584f121934b";

        MQConfig.init(this, meiqiaKey, new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {

            }

            @Override
            public void onFailure(int code, String message) {
                Log.e("asdfhkasj", "-----------"+message );
            }
        });
    }

    // SDCard路径
    public static final String SD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();

    // 图片存储路径
    public static final String BASE_PATH = SD_PATH + "/tugou/";

    // 缓存图片路径
    public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";

    // 缓存目录
    public static final String IMAGE_CACHE = "tugou/cache/images/";

    public static void initImageLoader(Context context) {

        // 设置图片加载配置
        ImageLoaderConfig.initImageLoader(context, BASE_IMAGE_CACHE);
    }

}
