package com.xdroid.common.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;

/**
 * 一些功能工具类
 *
 * @author Robin time 2014-12-16 13:05:02
 *         last update time 2015-01-23 14:55:57
 */
public class FunctionUtils {
    /**
     * 跳转到拨号界面
     *
     * @param context     上下文
     * @param phoneNumber 电话号码
     */
    public static void jump2PhoneView(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                + phoneNumber));
        context.startActivity(intent);
    }

    /**
     * 跳转到qq对话界面
     *
     * @param qqNumber QQ号
     */
    public static void jump2QQView(Context context, String qqNumber) {
        String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qqNumber;
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    /**
     * 直接拨打电话
     *
     * @param context     上下文
     * @param phoneNumber 手机号码
     */
    public static void callPhone(Context context, String phoneNumber) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    /**
     * 使用反射阻止对话框关闭（用于PositiveButton,NegativeButton等）
     *
     * @param dialogInterface 对话框接口对象
     */
    public static void InterceptedDismissDialogWithReflect(
            DialogInterface dialogInterface) {
        try {
            Field field = dialogInterface.getClass().getSuperclass()
                    .getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialogInterface, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用反射关闭对话框（用于PositiveButton,NegativeButton等）
     *
     * @param dialogInterface 对话框接口对象
     */
    public static void DismissDialogWithReflect(DialogInterface dialogInterface) {
        try {
            Field field = dialogInterface.getClass().getSuperclass()
                    .getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialogInterface, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从Gallery相册选取图片
     *
     * @param activity    当前Activity
     * @param requestCode 请求码(必须>1)
     */
    public static void chooseImageFromGallery(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    private static final String IMAGE_TYPE = "image/*";

    /**
     * PopupMenu打开本地相册.
     */
    public static boolean openPhotosNormal(Activity activity, int actResultCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_TYPE);
        try {
            activity.startActivityForResult(intent, actResultCode);

        } catch (android.content.ActivityNotFoundException e) {

            return true;
        }

        return false;
    }


    /**
     * 从Gallery相册获取图片的返回值（onActivityResult方法中执行）
     *
     * @param context     上下文
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     * @return imagePath 选取图片的路径
     */
    public static String onActivityResultForChooseImageFromGallery(
            Context context, int requestCode, int resultCode, Intent data) {


        //----------------
        if (data != null) {
            Uri selectedImage = data.getData();
            selectedImage = geturi(data, context);//解决方案
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);

            cursor.close();
            return imagePath;
        } else {
            return null;
        }
    }


    /**
     * MIUI系统的相册选择
     * @param data
     */
    /*private static String setPhotoForMiuiSystem(Context context,Intent data) {
		Uri localUri = data.getData();
		String scheme = localUri.getScheme();
		String imagePath = "";
		if("content".equals(scheme)){
			String[] filePathColumns = {MediaStore.Images.Media.DATA};
			Cursor c = context.getContentResolver().query(localUri, filePathColumns, null, null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePathColumns[0]);
			imagePath = c.getString(columnIndex);
			c.close();
		}else if("file".equals(scheme)){//小米4选择云相册中的图片是根据此方法获得路径
			imagePath = localUri.getPath();
		}
		return imagePath;
	}*/


    /**
     * 解决小米手机上获取图片路径为null的情况
     *
     * @param intent
     * @return
     */
    public static Uri geturi(Intent intent, Context context) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    /**
     * 从相机获取图片的返回值（onActivityResult方法中执行---第一种方式）
     *
     * @param activity    当前Activity
     * @param requestCode 请求码
     */
    public static void chooseImageFromCamera(Activity activity,
                                             int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		intent.putExtra(MediaStore.EXTRA_OUTPUT,
//				Uri.fromFile(new File(imagePath, imageName)));   //加上这句话，无法回调onActivityResult方法，原因不明
        activity.startActivityForResult(intent, requestCode);

    }

    /**
     * 从相机获取图片的返回值（onActivityResult方法中执行---第二种方式）
     *
     * @param activity    当前Activity
     * @param requestCode 请求码
     * @param imageName   存储的图片名称
     * @return 存储路径
     */
    public static String chooseImageFromCamera(Activity activity, int requestCode, String imageName) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + "/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String savePath = Environment.getExternalStorageDirectory() + "/Images/" + imageName + String.valueOf(System.currentTimeMillis()) + ".jpg";
        Uri mUri = Uri.fromFile(
                new File(savePath));
        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);
        cameraIntent.putExtra("return-data", true);
        activity.startActivityForResult(cameraIntent, requestCode);
        return savePath;
    }


    /**
     * 从相机获取图片的返回值（onActivityResult方法中执行---针对第一种方式）
     *
     * @param context     上下文
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     * @return Bitmap对象
     */
    public static Bitmap onActivityResultForChooseImageFromCamera(
            Context context, int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                return bitmap;
            } else {
                return null;
            }

        } else {
            return null;
        }

    }


    /**
     * 从SD卡选择文件
     *
     * @param activity    当前Activity
     * @param requestCode 请求码
     */
    public static void chooseFileFromSDCard(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            activity.startActivityForResult(
                    Intent.createChooser(intent, "选择文件"), requestCode);
        } catch (android.content.ActivityNotFoundException ex) {
            ToastUtils.show(activity, "请安装一个文件管理器");
        }
    }

    /**
     * 从文件选择器获取的返回值（onActivityResult方法中执行）
     *
     * @param context     上下文
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     * @return path 选取文件的路径
     */
    public static String onActivityResultForChooseFileFromSDCard(
            Context context, int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {"_data"};
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver().query(uri,
                            projection, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow("_data");
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index);
                    }
                } catch (Exception e) {
                    return null;
                }
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
