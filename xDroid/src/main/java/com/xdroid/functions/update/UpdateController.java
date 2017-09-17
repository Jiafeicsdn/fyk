package com.xdroid.functions.update;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.Gravity;
import android.view.Window;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.xdroid.R;
import com.xdroid.functions.cache.DiskFileUtils;

import java.io.File;


public class UpdateController implements DownLoadListener {


    private static final int NOTIFY_ID = 10000;

    private static final String MSG_ERROR_URL = "错误的下载地址";
    private static final String MSG_DOWNLOAD_FINISH_TICKER_TEXT = "下载已完成，请到系统通知栏查看和安装";
    private static final String MSG_DOWNLOAD_FINISH_TITLE = "下载已完成，点击安装";
    private static final String MSG_DOWN_LOAD_START = "已转入后台下载，请稍候";
    private static final String MSG_DOWNLOAD_FAIL = "下载失败";

    private static final String ACTION_CANCEL_DOWNLOAD = ".cancelDownloadApk";
    private static UpdateController sInstance;
    Notification mNotification = null;
    NotificationManager mNotifyManager = null;
    private String mDownLoadErrorMsg = MSG_DOWNLOAD_FAIL;
    private String mPackageName;
    private DownloadTask mDownloadTask;

    private Context mContext;
    private String mActionCancel;
    private String mApkPath;
    private PackageInfo mPackageInfo;

    private ProgressDialog dialog;
    private int mIcon;

    private int update_notify_view;  //通知栏布局
    private int update_notify_layout;  //通知栏布局id
    private int update_notify_image; //通知栏左边下载图表
    private int update_notify_text; //通知栏提示文字
    private int update_notify_progress;  //下载进度


    private UpdateController() {
    }

    public static UpdateController getInstance() {
        if (sInstance == null) {
            sInstance = new UpdateController();
        }
        return sInstance;
    }

    public void init(Context context, int icon) {

        if (context == null) {
            throw new IllegalArgumentException("How content can be null?");
        }

        mContext = context;
        mIcon = icon;

        PackageManager manager = context.getPackageManager();
        try {
            mPackageInfo = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Can not find package information");
        }
        mPackageName = mPackageInfo.packageName;
        mActionCancel = mPackageName + ACTION_CANCEL_DOWNLOAD;

        mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotification = new Notification();
        mNotification.icon = mIcon;
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
    }

    private void notifyDownloadFinish() {

        Intent openIntent = new Intent(Intent.ACTION_VIEW);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        openIntent.setDataAndType(Uri.fromFile(new File(mApkPath)), "application/vnd.android.package-archive");
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, openIntent, 0);

        Notification notification = new Notification();
        notification.icon = mIcon;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.contentView = new RemoteViews(mPackageName, update_notify_view);
        notification.contentView.setImageViewResource(update_notify_image, mIcon);
        notification.contentView.setProgressBar(update_notify_progress, 100, 100, false);
        notification.contentView.setTextViewText(update_notify_text, MSG_DOWNLOAD_FINISH_TITLE);
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;

        notification.tickerText = MSG_DOWNLOAD_FINISH_TICKER_TEXT;

        notification.contentIntent = contentIntent;
        mNotifyManager.notify(NOTIFY_ID, notification);
    }

    private void notifyDownLoadStart() {

//        showToastMessage(MSG_DOWN_LOAD_START);

        RemoteViews contentView = new RemoteViews(mPackageName, update_notify_view);
        mNotification.icon = mIcon;
        mNotification.tickerText = MSG_DOWN_LOAD_START;
        mNotification.contentView = contentView;
        mNotification.contentView.setProgressBar(update_notify_progress, 100, 0, false);
        mNotification.contentView.setImageViewResource(update_notify_image, mIcon);

        mNotifyManager.notify(NOTIFY_ID, mNotification);
    }

    private void notifyDownLoading(int updatePercent) {

        Intent intent = new Intent(mActionCancel);
        PendingIntent contentIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        mNotification.contentView = new RemoteViews(mPackageName, update_notify_view);
        mNotification.contentView.setImageViewResource(update_notify_image, mIcon);
        mNotification.contentView.setProgressBar(update_notify_progress, 100, updatePercent, false);
        mNotification.contentView.setTextViewText(update_notify_text, "下载进度  " + updatePercent + "%, 点击取消下载");

        mNotification.contentView.setOnClickPendingIntent(update_notify_layout, contentIntent);

        mNotifyManager.notify(NOTIFY_ID, mNotification);
    }

    public void beginDownLoad(String url) {
        showProgressDialog("正在下载...");
        String dir = DiskFileUtils.wantFilesPath(mContext, true);
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        mApkPath = dir + File.separator + "downloads" + File.separator + fileName;

        mDownloadTask = new DownloadTask(this, url, mApkPath);
        notifyDownLoadStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction(mActionCancel);
        mContext.registerReceiver(new CancelBroadcastReceiver(), filter);

        Thread thread = new Thread(mDownloadTask);
        thread.setDaemon(true);
        thread.start();
    }

    @SuppressWarnings("unused")
    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(mApkPath)), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
        mNotifyManager.cancel(NOTIFY_ID);
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onDone(boolean canceled, int result) {
        if (canceled) {
            return;
        }
        switch (result) {
            case DownloadTask.RESULT_OK:
                mNotifyManager.cancelAll();
                notifyDownloadFinish();
                dismissProgressDialog();
                openFile();
                break;
            case DownloadTask.RESULT_DOWNLOAD_ERROR:
                mNotifyManager.cancel(NOTIFY_ID);
                deleteApkFile();
                showToastMessage(mDownLoadErrorMsg);
                break;

            case DownloadTask.RESULT_NO_ENOUGH_SPACE:
                break;

            case DownloadTask.RESULT_URL_ERROR:
                mNotifyManager.cancel(NOTIFY_ID);
                deleteApkFile();
                showToastMessage(MSG_ERROR_URL);
                break;
        }
    }

    @Override
    public void onPercentUpdate(int percent) {
        notifyDownLoading(percent);
    }

    private void deleteApkFile() {
    }

    @SuppressLint("ShowToast")
    private void showToastMessage(String msg) {
        Toast toast = Toast.makeText(mContext, msg, 5000);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private class CancelBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mNotifyManager.cancel(NOTIFY_ID);
            mDownloadTask.cancel();
        }
    }

    /**
     * 设置通知栏布局资源 （注意：请在init方法前调用）
     *
     * @param layout        整个layout 布局视图
     * @param layoutId      整个layout的id
     * @param imageViewId   左边图标的id
     * @param textViewId    显示文字的id
     * @param progressBarId 下载进度的id
     */
    public void setNotificationView(int layout, int layoutId, int imageViewId, int textViewId, int progressBarId) {
        this.update_notify_view = layout;
        this.update_notify_layout = layoutId;
        this.update_notify_image = imageViewId;
        this.update_notify_text = textViewId;
        this.update_notify_progress = progressBarId;
    }


    /**
     * 显示进度条对话框
     *
     * @param message 显示文字
     */
    public void showProgressDialog(String message) {
        dialog = new ProgressDialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }


    /**
     * 销毁进度条对话框
     */
    public void dismissProgressDialog() {
        dialog.dismiss();
    }
}
