package com.lvgou.distribution.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

/**
 * Created by Administrator on 2016/8/21.
 */
public class LocationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private LocationManager manager;
    private MyLocationListener listener;

    // 服务一旦开启  先走onCreate
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        // 在onCreate去定位即可

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);


        // 在这完成定位

        // 我也希望获取一个模糊位置

        // 让系统给我推荐一个当前可用的定位方式 （可能是网络 也可能是基站 也可能是gps）
        // 你要告诉系统你的标准是啥
        Criteria criteria = new Criteria();

        // 告诉系统精确度越高越好
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 必要的时候允许收费
        criteria.setCostAllowed(true);

        String bestProvider = manager.getBestProvider(criteria, true);

        listener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(bestProvider, 0, 0, listener);


    }

    private class MyLocationListener implements LocationListener {

        private double jingdu;
        private double weidu;

        // 当监听到新的位置会回调这个方法
        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub

            // 拿到经纬度
            jingdu = location.getLongitude();
            weidu = location.getLatitude();

            Intent intent = new Intent();
            intent.setAction("location");
            intent.putExtra("jingdu", jingdu);
            intent.putExtra("weidu", weidu);
            sendBroadcast(intent);
            // 服务自己将自己干掉
            if (ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            manager.removeUpdates(this);
            stopSelf();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

    }

    // 当服务被销毁的时候会调用这个方法
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.removeUpdates(listener);

        super.onDestroy();
    }
}
