package com.xdroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * App的BaseActivity继承此Activity可方便的设置titleBar，bottomBar，以及一些公共的方法类
 * @author Robin
 * time 2015-02-27 17:40:50
 *
 */
public class XDroidBaseActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	
	}
	
	/*——————————————————————————启动Activity封装函数————————————————————————————*/
	/**
	 * 通过类名启动Activity
	 * 
	 * @param pClass
	 */
	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 * 
	 * @param pClass
	 * @param pBundle
	 */
	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * 通过Action启动Activity
	 * 
	 * @param pAction
	 */
	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	/**
	 * 通过Action启动Activity，并且含有Bundle数据
	 * 
	 * @param pAction
	 * @param pBundle
	 */
	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}
	
	/**
	 * 通过类名启动Activity，并获取返回值
	 * @param pClass 启动的类
	 * @param requestCode 请求码
	 */
	protected void openActivityForResault(Class<?> pClass,int requestCode){
		openActivityForResault(pClass, requestCode, null);
	}
	
	/**
	 * 通过类名启动Activity，并且含有Bundle数据,获取返回值
	 * @param pClass 启动的类
	 * @param requestCode 请求码
	 * @param pBundle bundle数据
	 */
	protected void openActivityForResault(Class<?> pClass,int requestCode,Bundle pBundle){
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivityForResult(intent, requestCode);
	}

	/*——————————————————————————Activity生命周期函数————————————————————————————*/
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
