package com.xdroid.common.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * 对话框工具类
 * @author Robin
 * time 2014-12-16 13:20:40
 */
public class DialogUtils {
	
	/**
	 * 显示AlertDialog
	 * @param dialog AlertDialog对象
	 */
	public static void showAlertDialog(Dialog dialog){
		if (dialog!=null&&!dialog.isShowing()) {
			dialog.show();
		}
	}
	/**
	 * 显示进度条对话框
	 * @param progressDialog  ProgressDialog对象
	 */
	public static void showProgressDialog(ProgressDialog progressDialog){
		if (progressDialog!=null&&!progressDialog.isShowing()) {
			progressDialog.show();
		}
	}
	
	/**
	 * 销毁AlertDialog
	 * @param dialog AlertDialog对象
	 */
	public static void DismissAlertDialog(Dialog dialog){
		if (dialog!=null) {
			dialog.dismiss();
		}
	}
	
	/**
	 * 销毁ProgressDialog
	 * @param progressDialog  ProgressDialog对象
	 */
	public static void DismissProgressDialog(ProgressDialog progressDialog){
		if (progressDialog!=null) {
			progressDialog.dismiss();
		}
	}
	
	/**
	 * 获取一个只显示内容的AlertDialog
	 * @param context 上下文
	 * @param message 显示内容
	 * @return Dialog对象
	 *  time 2014-12-16 22:00:14
	 */
	public static Dialog getAlertDialog(Context context,String message){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setMessage(message);
		return builder.create();
	}

	/**
	 * 获取一个带标题并显示内容的Dialog
	 * @param context 上下文
	 * @param title 标题
	 * @param message 显示内容
	 * @return Dialog对象
	 * time 2014-12-16 22:02:09
	 */
	public static Dialog getAlertDialog(Context context,String title,String message){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		return builder.create();
	}
	
	/**
	 * 获取一个带图标，标题并显示内容的Dialog
	 * @param context 上下文
	 * @param iconId 图标id
	 * @param title 标题
	 * @param message 显示的内容
	 * @return Dialog对象
	 *  time 2014-12-16 22:03:53
	 */
	public static Dialog getAlertDialog(Context context,int iconId,String title,String message){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setIcon(iconId);
		builder.setTitle(title);
		builder.setMessage(message);
		return builder.create();
	}
	
	/**
	 * 获取一个带显示内容，确定按钮的Dialog
	 * @param context 上下文
	 * @param message 显示内容
	 * @param positiveButtonText 确定按钮显示内容
	 * @param onPositiveDialogButtonClickListener 按钮点击回调接口
	 * @return Dialog对象
	 *  time 2014-12-16 23:05:05
	 */
	public static Dialog getAlertDialog(Context context,String message,String positiveButtonText,final OnPositiveDialogButtonClickListener onPositiveDialogButtonClickListener){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onPositiveDialogButtonClickListener.OnPositiveButtonClick(dialogInterface,position);
			}
		});
		return builder.create();
	}
	
	
	/**
	 * 获取一个带标题，内容，确定按钮的Dialog
	 * @param context 上下文
	 * @param title 标题
	 * @param message 内容
	 * @param buttonText 按钮显示内容
	 * @param onPositiveDialogButtonClickListener 按钮点击回调接口
	 * @return Dialog对象
	 *  time 2014-12-16 22:54:47
	 */
	public static Dialog getAlertDialog(Context context,String title,String message,String positiveButtonText,final OnPositiveDialogButtonClickListener onPositiveDialogButtonClickListener){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onPositiveDialogButtonClickListener.OnPositiveButtonClick(dialogInterface,position);
			}
		});
		return builder.create();
	}
	
	/**
	 * 获取一个带显示内容，两个按钮的Dialog
	 * @param context 上下文
	 * @param message 显示内容
	 * @param negativeButtonText 取消按钮显示内容
	 * @param positiveButtonText  确定按钮显示内容
	 * @param onNegativeAndPositiveDialogButtonClickListener 按钮点击回调接口
	 * @return Dialog对象
	 *  time 2014-12-16 23:03:00
	 */ 
	public static Dialog getAlertDialog(Context context,String message,String negativeButtonText,String positiveButtonText,final OnNegativeAndPositiveDialogButtonClickListener onNegativeAndPositiveDialogButtonClickListener){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onNegativeAndPositiveDialogButtonClickListener.OnNegativeButtonClick(dialogInterface, position);
			}
		});
		builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onNegativeAndPositiveDialogButtonClickListener.OnPositiveButtonClick(dialogInterface,position);
			}
		});
		return builder.create();
	}
	
	/**
	 * 获取一个带标题，内容，两个按钮的Dialog
	 * @param context 上下文
	 * @param title 标题
	 * @param message 内容
	 * @param negativeButtonText 取消按钮显示内容
	 * @param positiveButtonText 确定按钮显示内容
	 * @param onNegativeAndPositiveDialogButtonClickListener 按钮点击回调接口
	 * @return Dialog对象
	 *  time 2014-12-16 22:54:38
	 */
	public static Dialog getAlertDialog(Context context,String title,String message,String negativeButtonText,String positiveButtonText,final OnNegativeAndPositiveDialogButtonClickListener onNegativeAndPositiveDialogButtonClickListener){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onNegativeAndPositiveDialogButtonClickListener.OnNegativeButtonClick(dialogInterface, position);
			}
		});
		builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onNegativeAndPositiveDialogButtonClickListener.OnPositiveButtonClick(dialogInterface,position);
			}
		});
		return builder.create();
	}
	
	/**
	 * 返回一个自定义视图Dialog
	 * @param context 上下文
	 * @param view 视图
	 * @return Dialog对象
	 *  time 2014-12-16 23:26:09
	 */
	public static Dialog getAlertDialog(Context context,View view){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setView(view);
		return builder.create();
	}
	
	/**
	 * 获取一个带自定义视图，确定按钮的Dialog
	 * @param context 上下文
	 * @param view 视图
	 * @param positiveButtonText 确定按钮显示内容
	 * @param onPositiveDialogButtonClickListener 按钮点击回调接口
	 * @return Dialog对象
	 *  time 2014-12-16 23:19:59
	 */
	public static Dialog getAlertDialog(Context context,View view,String positiveButtonText,final OnPositiveDialogButtonClickListener onPositiveDialogButtonClickListener){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setView(view);
		builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onPositiveDialogButtonClickListener.OnPositiveButtonClick(dialogInterface,position);
			}
		});
		return builder.create();
	}
	
	/**
	 * 获取一个带标题，自定义view ，确定按钮Dialog
	 * @param context 上下文
	 * @param title 标题
	 * @param view 视图
	 * @param positiveButtonText 确定按钮显示内容
	 * @param onPositiveDialogButtonClickListener 按钮点击回调接口
	 * @return Dialog对象
	 *  time 2014-12-16 23:42:07
	 */
	public static Dialog getAlertDialog(Context context,String title,View view,String positiveButtonText,final OnPositiveDialogButtonClickListener onPositiveDialogButtonClickListener){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setView(view);
		builder.setTitle(title);
		builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onPositiveDialogButtonClickListener.OnPositiveButtonClick(dialogInterface,position);
			}
		});
		return builder.create();
	}
	
	/**
	 * 获取一个带自定义view ，两个按钮的Dialog
	 * @param context 上下文
	 * @param view 视图
	 * @param negativeButtonText 取消按钮显示内容
	 * @param positiveButtonText 确定按钮显示内容
	 * @param onNegativeAndPositiveDialogButtonClickListener 按钮点击回调接口
	 * @return Dialog对象
	 *  time 2014-12-16 23:16:36
	 */
	public static Dialog getAlertDialog(Context context,View view,String negativeButtonText,String positiveButtonText,final OnNegativeAndPositiveDialogButtonClickListener onNegativeAndPositiveDialogButtonClickListener){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setView(view);
		builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onNegativeAndPositiveDialogButtonClickListener.OnNegativeButtonClick(dialogInterface, position);
			}
		});
		builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onNegativeAndPositiveDialogButtonClickListener.OnPositiveButtonClick(dialogInterface,position);
			}
		});
		return builder.create();
	}
	
	/**
	 * 获取一个带标题，自定义view，两个按钮的Dialog
	 * @param context 上下文
	 * @param title 标题
	 * @param view 视图
	 * @param negativeButtonText 取消按钮显示内容
	 * @param positiveButtonText 确定按钮显示内容
	 * @param onNegativeAndPositiveDialogButtonClickListener 按钮点击回调接口
	 * @return Dialog对象
	 *  time 2014-12-16 23:12:28
	 */
	public static Dialog getAlertDialog(Context context,String title,View view,String negativeButtonText,String positiveButtonText,final OnNegativeAndPositiveDialogButtonClickListener onNegativeAndPositiveDialogButtonClickListener){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setView(view);
		builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onNegativeAndPositiveDialogButtonClickListener.OnNegativeButtonClick(dialogInterface, position);
			}
		});
		builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int position) {
				onNegativeAndPositiveDialogButtonClickListener.OnPositiveButtonClick(dialogInterface,position);
			}
		});
		return builder.create();
	}
	
	/**
	 * 获取一个选项列表Dialog
	 * @param context 上下文
	 * @param items 选项数组
	 * @param onDialogItemClickListener Item点击回调
	 * @return Dialog对象
	 */
	public static Dialog getAlertDialog(Context context,String[] items,final OnDialogItemClickListener onDialogItemClickListener){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialogInterface, int which) {
				onDialogItemClickListener.OnDialogItemClick(dialogInterface, which);
			}
		});
		
		return builder.create();
	}
	
	/**
	 * 返回一个进度条Dialog
	 * @param context上下文
	 * @return ProgressDialog对象
	 *  time 2014-12-17 15:09:16
	 */
	public static ProgressDialog getProgressDialog(Context context){
		ProgressDialog progressDialog=new ProgressDialog(context);
		return progressDialog;
	}
	/**
	 * 返回一个带显示内容的ProgressDialog
	 * @param context 上下文
	 * @param message 显示内容
	 * @return ProgressDialog对象
	 *  time 2014-12-17 15:08:16
	 */
	public static ProgressDialog getProgressDialog(Context context,String message){
		ProgressDialog progressDialog=new ProgressDialog(context);
		progressDialog.setMessage(message);
		return progressDialog;
	}
	
	/**
	 * 获取一个横向进度条Dialog
	 * @param context 上下文
	 * @param message 显示内容
	 * @return ProgressDialog 对象
	 * time 2015-01-04 13:55:59
 	 */
	public static ProgressDialog getHorizontalProgressDialog(Context context,String message){
		ProgressDialog progressDialog=new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setMessage(message);
		
		return progressDialog;
	}
	
	/**
	 * 所有按钮回调接口
	 * @author Robin
	 *  time 2014-12-16 22:11:30
	 */
	public interface OnDialogButtonClickListener{
		/**
		 * 取消按钮回调
		 */
		public void OnNegativeButtonClick(DialogInterface dialogInterface, int position);
		/**
		 * 中间按钮回调
		 */
		public void OnNeutralButtonClick(DialogInterface dialogInterface, int position);
		/**
		 * 确定按钮回调
		 */
		public void OnPositiveButtonClick(DialogInterface dialogInterface, int position);
	}
	
	/**
	 * 取消按钮回调接口
	 * @author Robin
	 *  time 2014-12-16 22:18:46
	 *
	 */
	public interface OnNegativeDialogButtonClickListener{
		/**
		 * 取消按钮回调
		 */
		public void OnNegativeButtonClick(DialogInterface dialogInterface, int position);
	}
	
	/**
	 * 中间按钮回调接口
	 * @author Robin
	 *  time 2014-12-16 22:19:51
	 *
	 */
	public interface OnNeutralDialogButtonClickListener{
		/**
		 * 中间按钮回调
		 */
		public void OnNeutralButtonClick(DialogInterface dialogInterface, int position);
	}
	
	/**
	 * 确定按钮回调接口
	 * @author Robin
	 *  time 2014-12-16 22:21:02
	 *
	 */
	public interface OnPositiveDialogButtonClickListener{
		/**
		 * 确定按钮回调
		 */
		public void OnPositiveButtonClick(DialogInterface dialogInterface, int position);
	}
	
	/**
	 * 确定取消按钮回调接口
	 * @author Robin
	 *  time 2014-12-16 22:21:40
	 */
	public interface OnNegativeAndPositiveDialogButtonClickListener{
		/**
		 * 取消按钮回调
		 */
		public void OnNegativeButtonClick(DialogInterface dialogInterface, int position);
		/**
		 * 确定按钮回调
		 */
		public void OnPositiveButtonClick(DialogInterface dialogInterface, int position);
	}
	
	/**
	 * 对话框选项点击回调
	 * @author Robin
	 * time 2014-12-31 10:15:03
	 */
	public interface OnDialogItemClickListener{
		public void OnDialogItemClick(DialogInterface dialogInterface,int which);
	}

}
