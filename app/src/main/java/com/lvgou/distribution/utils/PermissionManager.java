package com.lvgou.distribution.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理工具类
 *
 * @author
 * @date 2016/4/15
 * @time 13:34
 * @description Describe the place where the class needs to pay attention.
 */
public class PermissionManager {

    private int PERMISSION_REQUEST_CODE = 1;

    private List<String> permissions = new ArrayList<>();

    private Activity activity;

    private Fragment fragment;

    private boolean inFragment;

    public PermissionManager(@NonNull PermissionListener listener) {
        this.listener = listener;
    }

    /**
     * activity中检测权限和申请权限
     * @param activity
     * @param permissions
     */
    public void checkPermission(int requestCode,Activity activity, String... permissions){
        PERMISSION_REQUEST_CODE = requestCode;
        inFragment = false;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            listener.permissionGranted(requestCode);
        }else{
            this.activity = new WeakReference<Activity>(activity).get();
            this.permissions.clear();
            if(permissions != null && permissions.length > 0){
                for (String permission : permissions) {
                    // 检查是否已授权  HTC 手机的bug，用户先授权，然后取消，这里一直都是已授权状态
                    if(ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED){
                        this.permissions.add(permission);
                    }
                    this.permissions.add(permission);
                }
            }
            if(this.permissions.isEmpty()){
                // 不需要申请,可以执行
                listener.permissionGranted(requestCode);
            }else{
                // 申请权限
                ActivityCompat.requestPermissions(activity,this.permissions.toArray(new String[this.permissions.size()]),PERMISSION_REQUEST_CODE);
            }
        }
    }

    /**
     * fragment中权限检测和申请
     * @param fragment
     * @param permissions
     */
    public void checkPermission(int requestCode, Fragment fragment, String... permissions){
        PERMISSION_REQUEST_CODE = requestCode;
        inFragment = true;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            listener.permissionGranted(requestCode);
        }else{
            this.activity = new WeakReference<Activity>(fragment.getActivity()).get();
            this.fragment = new WeakReference<Fragment>(fragment).get();
            this.permissions.clear();
            if(permissions != null && permissions.length > 0){
                for (String permission : permissions) {
                    // 检测是否已授权
                    if(ContextCompat.checkSelfPermission(this.activity,permission) != PackageManager.PERMISSION_GRANTED){
                        this.permissions.add(permission);
                    }
                }
            }
            if(this.permissions.isEmpty()){
                // 不需要申请,可以执行
                listener.permissionGranted(requestCode);
            }else{
                // 申请权限
                fragment.requestPermissions(this.permissions.toArray(new String[this.permissions.size()]),PERMISSION_REQUEST_CODE);
            }
        }
    }

    /**
     * 此方法必须在需要申请权限的 activity 或 fragment 中 对应的方法中调用一下
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE && allGranted(grantResults)){
            // 用户授权,可以执行
            listener.permissionGranted(PERMISSION_REQUEST_CODE);
        }else{
            // 用户拒绝
            doPermissionDennied(permissions);
        }
    }

    /**
     * 处理权限拒绝
     * @param permissions
     */
    private void doPermissionDennied(@NonNull String[] permissions){
        boolean needSetting = false;
        if(inFragment){
            for (String permission : permissions) {
                if(!fragment.shouldShowRequestPermissionRationale(permission)){
                    needSetting = true;
                    break;
                }
            }
        }else{
            for (String permission : permissions) {
                if(!ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
                    needSetting = true;
                    break;
                }
            }
        }

        if(needSetting){
            new AlertDialog.Builder(activity)
                    .setTitle("缺少权限")
                    .setMessage("点击设置进行授权!")
                    .setNegativeButton("取消",null)
                    .setPositiveButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    activity.startActivity(intent);
                }
            }).show();
        }else{
            MyToast.makeText(activity, "获取权限失败,无法执行!", Toast.LENGTH_SHORT).show();
        }

//        String message = needSetting ? "没有授权,点击设置进行授权!" : "获取权限失败,无法执行!";
//        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG);
//        if(needSetting){
//            snackbar.setAction("设置", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
//                    activity.startActivity(intent);
//                }
//            });
//        }
//        snackbar.show();
    }

    /**
     * 判断权限结果
     * @param grantResults
     * @return
     */
    private boolean allGranted(@NonNull int[] grantResults){
        boolean grant = true;
        for (int grantRsult : grantResults){
            if(grantRsult != PackageManager.PERMISSION_GRANTED){
                grant = false;
                break;
            }
        }
        return grant;
    }

    public interface PermissionListener{

        void permissionGranted(int requestCode);
    }

    private PermissionListener listener;
}