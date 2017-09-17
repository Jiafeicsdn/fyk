package com.xdroid.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * 自定义Toast提示控件
 * 
 * @author Robin 
 * @version 1.0
 * @created 2014-12-30 17:22:27
 */
public class XDroidCustomToast extends Toast {

	private MediaPlayer mPlayer;
	private boolean isSound;

	public XDroidCustomToast(Context context) {
		this(context, false);
	}

	public XDroidCustomToast(Context context, boolean isSound) {
		super(context);

		this.isSound = isSound;

		/*
		 * mPlayer = MediaPlayer.create(context, R.raw.newdatatoast);
		 * mPlayer.setOnCompletionListener(new
		 * MediaPlayer.OnCompletionListener(){
		 * 
		 * @Override public void onCompletion(MediaPlayer mp) { mp.release(); }
		 * });
		 */

	}

	@Override
	public void show() {
		super.show();

		if (isSound) {
			mPlayer.start();
		}
	}

	/**
	 * 设置是否播放声音
	 */
	public void setIsSound(boolean isSound) {
		this.isSound = isSound;
	}

	/**
	 * 自定义视图，默认600毫秒
	 * 
	 * @param context
	 * @param text
	 *            提示消息
	 * @return
	 */
	public static XDroidCustomToast makeText(Context context, View v) {
		XDroidCustomToast result = new XDroidCustomToast(context);

		DisplayMetrics dm = context.getResources().getDisplayMetrics();

		v.setMinimumWidth(dm.widthPixels);// 设置控件最小宽度为手机屏幕宽度

		result.setView(v);
		result.setDuration(600);
		result.setGravity(Gravity.TOP, 0, (int) (dm.density * 75));

		return result;
	}
	
	/**
	 * 自定义视图，自定义时间
	 * @param context 上下文
	 * @param v 自定义视图
	 * @param duration 显示时长
	 * @return
	 */
	public static XDroidCustomToast makeText(Context context, View v,int duration) {
		XDroidCustomToast result = new XDroidCustomToast(context);

		DisplayMetrics dm = context.getResources().getDisplayMetrics();

		v.setMinimumWidth(dm.widthPixels);// 设置控件最小宽度为手机屏幕宽度

		result.setView(v);
		result.setDuration(duration);
		result.setGravity(Gravity.TOP, 0, (int) (dm.density * 75));

		return result;
	}

}
