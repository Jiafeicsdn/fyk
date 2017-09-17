package com.lvgou.distribution.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.entity.LocalAddressEntity;
import com.lvgou.distribution.event.EventFactory;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ToolActivity extends BaseActivity implements View.OnClickListener, AMapLocationListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        initView();
        initClick();
        /**
         * 初始化 定位操作
         */
        initLocal();
    }
    /****
     * 定位相关
     ****/

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    /**
     * 定位获取经纬度
     */
    public void initLocal() {
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为低功耗模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        locationOption.setOnceLocation(false);//只有持续定位设置定位间隔才有效，单次定位无效
        locationOption.setNeedAddress(false); // 设置是否需要显示地址信息
        locationOption.setInterval(1000);// 设置定位时间间隔  1000 毫秒
        //设置定位监听
        locationClient.setLocationListener(this);
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        //启动定位
        locationClient.startLocation();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    private RelativeLayout rl_sms;//短信
    private RelativeLayout rl_weather;//天气
    private RelativeLayout rl_rate;//汇率
    private RelativeLayout rl_game;//游戏
    private RelativeLayout tl_joke;//笑话
    private RelativeLayout rl_doggerel;//顺口溜
    private RelativeLayout rl_meal_map;//团餐地图
    //    private RelativeLayout rl_shop_map;//购物店地图
    private RelativeLayout rl_suishizhuan;//随时赚
    private RelativeLayout rl_fengmai;

    private void initView() {
        rl_sms = (RelativeLayout) findViewById(R.id.rl_sms);
        rl_weather = (RelativeLayout) findViewById(R.id.rl_weather);
        rl_rate = (RelativeLayout) findViewById(R.id.rl_rate);
        rl_game = (RelativeLayout) findViewById(R.id.rl_game);
        tl_joke = (RelativeLayout) findViewById(R.id.tl_joke);
        rl_doggerel = (RelativeLayout) findViewById(R.id.rl_doggerel);
        rl_meal_map = (RelativeLayout) findViewById(R.id.rl_meal_map);
        rl_suishizhuan = (RelativeLayout) findViewById(R.id.rl_suishizhuan);
        rl_fengmai = (RelativeLayout) findViewById(R.id.rl_fengmai);
//        rl_shop_map = (RelativeLayout) findViewById(R.id.rl_shop_map);
    }

    private void initClick() {
        rl_sms.setOnClickListener(this);
        rl_weather.setOnClickListener(this);
        rl_rate.setOnClickListener(this);
        rl_game.setOnClickListener(this);
        tl_joke.setOnClickListener(this);
        rl_doggerel.setOnClickListener(this);
        rl_meal_map.setOnClickListener(this);
//        rl_shop_map.setOnClickListener(this);
        rl_suishizhuan.setOnClickListener(this);
        rl_fengmai.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sms://短信页面跳转
                Bundle pBundle = new Bundle();
                pBundle.putString("index", "0");
                pBundle.putString("state", "0");
                pBundle.putString("name", "");
                pBundle.putString("content", "");
                pBundle.putString("id", "");
                openActivity(NewFreeSmsActivity.class, pBundle);
                break;
            case R.id.rl_weather://天气页面跳转
                Intent intent = new Intent(ToolActivity.this, WeatherWebActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_rate://汇率页面跳转
                Intent intent2 = new Intent(this, GamesWebActivity.class);
                intent2.putExtra("LinkUrl", "http://agent.quygt.com/exchange/index");
                intent2.putExtra("title", "汇率实时查询");
                startActivity(intent2);
                break;
            case R.id.rl_game://游戏页面跳转
                openActivity(GamesActivity.class);
                break;
            case R.id.tl_joke://笑话页面跳转
                openActivity(JokeActivity.class);
                break;
            case R.id.rl_doggerel://顺口溜页面跳转
                openActivity(DoggerelActivity.class);
                break;
            case R.id.rl_meal_map://团餐地图页面跳转
                openActivity(ReportSearchShopActivity.class);
                break;
            case R.id.rl_suishizhuan://随时赚
                Bundle bundle = new Bundle();
                bundle.putString("index", "0");
                openActivity(ApplicationActivity.class, bundle);
                break;
            case R.id.rl_fengmai://蜂卖
                openActivity(TeMeiActivity.class);
                break;
          /*  case R.id.rl_shop_map://购物店地图跳转
                openActivity(GroupIndexActivity.class);
                break;*/
        }
    }
    private boolean is_refresh = false;
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (!is_refresh) {
            is_refresh = true;
            if (aMapLocation != null) {
                Constants.Latitude = aMapLocation.getLatitude() + "";// 纬度
                Constants.Longitude = aMapLocation.getLongitude() + "";// 经度
            }
            // 发送更新地图
            EventFactory.updateMap(0);
        }
    }
}
