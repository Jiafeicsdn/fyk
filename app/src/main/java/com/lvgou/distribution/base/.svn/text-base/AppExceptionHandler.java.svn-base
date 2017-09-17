package com.lvgou.distribution.base;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.activity.SplashActivity;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.ErrorLogPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.BaseView1;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.utils.AppManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.lvgou.distribution.api.Api.GetDeviceName;


@SuppressLint("SimpleDateFormat")
public class AppExceptionHandler implements Thread.UncaughtExceptionHandler, BaseView1 {
	private StringBuffer sb = new StringBuffer();

	private static final String VERSION_NAME = "versionName";

	private static final String VERSION_CODE = "versionCode";

	private static final String CRASH_TIME = "crashTime";

	private static final String PET_NAME = "petName";

	private static final String CRASH_REPORTER_EXTENSION = ".txt";

	private Application softApp;

	public AppExceptionHandler(Application application) {
		errorLogPresenter = new ErrorLogPresenter(this);
		softApp = application;
	}
	private ErrorLogPresenter errorLogPresenter;
	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	public void uncaughtException(Thread thread, Throwable ex) {
		//-----------------------------
		String androidId = Settings.Secure.getString(softApp.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
		TelephonyManager tm = (TelephonyManager) softApp.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
		ConnectivityManager connectionManager = (ConnectivityManager) softApp.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
		//获取wifi服务
		WifiManager wifiManager = (WifiManager) softApp.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		//判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();

		PackageManager manager;

		PackageInfo info = null;

		manager = softApp.getApplicationContext().getPackageManager();

		try {

			info = manager.getPackageInfo(softApp.getApplicationContext().getPackageName(), 0);

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
		sb.append("内存:" + getMemoryInfo(Environment.getDataDirectory(),softApp.getApplicationContext())+";  ");
		sb.append("IP地址:" + intToIp(ipAddress)+";  ");
		sb.append("app版本:" + info.versionCode+";  ");
		sb.append("SDK版本:" + android.os.Build.VERSION.SDK+";  ");
		sb.append("系统版本:" + android.os.Build.VERSION.RELEASE+";  ");


		String distributorid = PreferenceHelper.readString(softApp.getApplicationContext(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
		String sign = TGmd5.getMD5(distributorid + 2+androidId+"客户端异常"+sb.toString()+ex.toString());
		errorLogPresenter.apperrorlog(distributorid, 2,androidId,"客户端异常",sb.toString(),ex.toString(),sign );
		// 保存CrashLog到本地
		saveCrashInfoToFile(ex, 1);

		// 执行子线程弹出提示
//		handleException("程序运行异常,即将退回上一界面！", ex);

		// 延时3秒杀死当前进程
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		Intent intent = new Intent(softApp.getApplicationContext(), SplashActivity.class);
		PendingIntent restartIntent = PendingIntent.getActivity(
				softApp.getApplicationContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		//退出程序
		AlarmManager mgr = (AlarmManager)softApp.getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
		AppManager.getInstance().killAllActivity();
		android.os.Process.killProcess(android.os.Process.myPid());


	}

	/**
	 * 保存CrashLog到文件
	 * 
	 * @param ex
	 * @param flag
	 * @return
	 */
	public String saveCrashInfoToFile(Throwable ex, int flag) {
		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);
		ex.printStackTrace();
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		String result = info.toString();
		printWriter.close();
		// 在Log控制台打印错误日志
		LogUtils.e("Crash错误信息:" + result);

		// 收集设备信息
		collectCrashDeviceInfo(softApp.getApplicationContext());

		// 保存崩溃的堆栈记录和时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		sb.append(CRASH_TIME + "=").append(df.format(new Date())).append("\n")
				.append("STACK_TRACE" + "=").append(result);
		try {
			String fileName;
			if (flag == 1){
//				fileName = "crash-" + df.format(new Date())
//						+ CRASH_REPORTER_EXTENSION;
				fileName="error"+CRASH_REPORTER_EXTENSION;
			}

			else{
//				fileName = "error-" + df.format(new Date())
//						+ CRASH_REPORTER_EXTENSION;
				fileName="error"+CRASH_REPORTER_EXTENSION;
			}


			File crashLogPath = null;
			File root = Environment.getExternalStorageDirectory();
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				crashLogPath = new File(root.getAbsolutePath() + File.separator
						+ Constants.BASE_PATH + "CrashLog");
			}
			if (!crashLogPath.exists()) {
				crashLogPath.mkdirs();
			}
			if (crashLogPath != null && !crashLogPath.exists()) {
				crashLogPath.mkdirs();
			}
			// 创建文件
			File file = new File(crashLogPath, fileName);
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(sb.toString().getBytes("utf-8"));
			outStream.flush();
			outStream.close();
			return fileName;
		} catch (Exception e) {
			LogUtils.i(e.toString());
		}
		// end
		return null;
	}

	/**
	 * 收集设备信息
	 * 
	 * @param ctx
	 */
	public void collectCrashDeviceInfo(Context ctx) {
		Log.e("kjashfdk", "-----------错误日志收集中----" );
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				sb.append(PET_NAME + "=")
						.append(Build.MODEL)
						.append("\n")
						.append(VERSION_NAME + "=")
						// 版本名称
						.append(pi.versionName == null ? "not set"
								: pi.versionName)
						.append("\n")

						// .append(RESOLUTION + "=")
						// .append(Gdx.graphics.getWidth()
						// +" * "+Gdx.graphics.getHeight())
						// .append("\n")

						.append(VERSION_CODE + "=").append(pi.versionCode)
						.append("\n").append("SDK_VERSION" + "=")
						.append(Build.VERSION.SDK_INT).append("\n")
						.append("RELEASE" + "=").append(Build.VERSION.RELEASE)
						.append("\n");
			}
		} catch (NameNotFoundException e) {
			LogUtils.i("Error while collect package info" + e);
		}

		String imsi = "unknow";
		String imei = "unknow";

		TelephonyManager telMgr = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telMgr != null) {
			imsi = telMgr.getSubscriberId() != null ? telMgr.getSubscriberId()
					: "unknow";
			imei = telMgr.getDeviceId() != null ? telMgr.getDeviceId()
					: "unknow";

			sb.append("Globe.REQUEST_HEADER_IMSI" + "=")
					// IMSI
					.append(imsi).append("\n")
					.append("Globe.REQUEST_HEADER_IMEI" + "=")// IMEI
					.append(imei).append("\n");
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				sb.append(field.getName() + "=")// 设备信息
						.append(field.get(null)).append("\n");
			} catch (Exception e) {
				LogUtils.i("UEHandler" + "Error while collect crash info", e);
			}
		}

	}


	/**
	 * 开辟子线程弹出提示
	 *
	 * @param ex
	 * @return
	 */
	private boolean handleException(final String showText, Throwable ex) {
		if (ex == null) {
			return true;
		}
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
//				MyToast.makeText(softApp.getApplicationContext(), showText,
//						Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		return true;
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
