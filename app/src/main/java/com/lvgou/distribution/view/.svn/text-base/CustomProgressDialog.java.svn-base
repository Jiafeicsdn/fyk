package com.lvgou.distribution.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.lvgou.distribution.R;
import com.xdroid.common.utils.DensityUtils;


/**
 * 自定义ProgressDialog
 * @author Robin
 * time 2015-03-17 15:03:54
 */
public class CustomProgressDialog extends Dialog {
	private static CustomProgressDialog customProgressDialog = null;
	static TextView tvMsg;

	public CustomProgressDialog(Context context) {
		super(context);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static CustomProgressDialog createDialog(Context context) {
		customProgressDialog = new CustomProgressDialog(context,
				R.style.style_custom_dialog);
		View view=View.inflate(context, R.layout.view_progress_dialog, null);
		tvMsg = (TextView) view.findViewById(R.id.tv_progress_dialog_title);
		customProgressDialog.setContentView(view);

//		customProgressDialog.getWindow().getAttributes().gravity = Gravity.TOP;

		WindowManager.LayoutParams params = customProgressDialog.getWindow()
				.getAttributes();
		customProgressDialog.getWindow().setGravity(Gravity.TOP);
		params.y = 100;  //设置距离顶部高度
		params.width=(int) (Float.parseFloat((DensityUtils.getScreenW((Activity)context)+""))*0.9);   //设置宽度
		params.height= (int) (Float.parseFloat((DensityUtils.getScreenW((Activity)context)+""))*0.2);;   //设置高度
		customProgressDialog.getWindow().setAttributes(params);

		return customProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {

		if (customProgressDialog == null) {
			return;
		}

//		 ImageView imageView = (ImageView)
//		 customProgressDialog.findViewById(R.id.image_progress_animation);
//		 AnimationDrawable animationDrawable = (AnimationDrawable)
//		 imageView.getBackground();
//		 animationDrawable.start();
	}

	/**
	 * 
	 * [Summary] setTitile 标题
	 * 
	 * @param strTitle
	 * @return
	 * 
	 */
	public CustomProgressDialog setTitile(String strTitle) {
		return customProgressDialog;
	}

	/**
	 * 
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 */
	public CustomProgressDialog setMessage(String strMessage) {

		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}

		return customProgressDialog;
	}
}
