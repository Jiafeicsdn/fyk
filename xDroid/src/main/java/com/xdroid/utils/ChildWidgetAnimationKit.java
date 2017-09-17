package com.xdroid.utils;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.xdroid.animation.Animation;
import com.xdroid.animation.FadeInAnimation;
import com.xdroid.animation.FlipHorizontalAnimation;
import com.xdroid.animation.PuffInAnimation;
import com.xdroid.animation.RotationAnimation;
import com.xdroid.animation.ScaleInAnimation;
import com.xdroid.animation.SlideInAnimation;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 子控件动画工具包
 * @author Robin
 *  time 2015-02-02 09:48:04
 *
 */
@SuppressWarnings("deprecation")
public class ChildWidgetAnimationKit {
	
	private static OnSetChildWidgetAnimationListener onSetChildWidgetAnimationListener;
	
	private static OnSetTextViewAnimationListener onSetTextViewAnimationListener;
	private static OnSetButtonAnimationListener onSetButtonAnimationListener;
	private static OnSetToggleButtonAnimationListener onSetToggleButtonAnimationListener;
	private static OnSetCheckBoxAnimationListener onSetCheckBoxAnimationListener;
	private static OnSetRadioButtonAnimationListener onSetRadioButtonAnimationListener;
	private static OnSetSpinnerAnimationListener onSetSpinnerAnimationListener;
	private static OnSetProgressBarAnimationListener onSetProgressBarAnimationListener;
	private static OnSetSeekBarAnimationListener onSetSeekBarAnimationListener;
	private static OnSetRadioGroupAnimationListener onSetRadioGroupAnimationListener;
	private static OnSetRatingBarAnimationListener onSetRatingBarAnimationListener;
	private static OnSetSwitchAnimationListener onSetSwitchAnimationListener;
	private static OnSetEditTextAnimationListener onSetEditTextAnimationListener;
	private static OnSetAutoCompleteTextViewAnimationListener onSetAutoCompleteTextViewAnimationListener;
	private static OnSetLinearLayoutAnimationListener onSetLinearLayoutAnimationListener;
	private static OnSetRelativeLayoutAnimationListener onSetRelativeLayoutAnimationListener;
	private static OnSetFrameLayoutAnimationListener onSetFrameLayoutAnimationListener;
	private static OnSetTableLayoutAnimationListener onSetTableLayoutAnimationListener;
	private static OnSetExpandableListViewAnimationListener onSetExpandableListViewAnimationListener;
	private static OnSetGridViewAnimationListener onSetGridViewAnimationListener;
	private static OnSetScrollViewAnimationListener onSetScrollViewAnimationListener;
	private static OnSetHorizontalScrollViewAnimationListener onSetHorizontalScrollViewAnimationListener;
	private static OnSetWebViewAnimationListener onSetWebViewAnimationListener;
	private static OnSetImageViewAnimationListener onSetImageViewAnimationListener;
	private static OnSetImageButtonAnimationListener onSetImageButtonAnimationListener;
	private static OnSetVideoViewAnimationListener onSetVideoViewAnimationListener;
	private static OnSetGalleryAnimationListener onSetGalleryAnimationListener;
	
	/**
	 * 设置子控件动画
	 * @param viewGroup 整个View的根布局
	 * @param delay 延时
	 */
	public static void startAnimation(final ViewGroup viewGroup,int delay) {
		Timer timer=new Timer();
		TimerTask timerTask=new TimerTask() {
			
			@Override
			public void run() {
				UIKit.runOnMainThreadAsync(new Runnable() {
					
					@Override
					public void run() {
						for (int i = 0; i < viewGroup.getChildCount(); i++) {
							View v = viewGroup.getChildAt(i);
							if (v instanceof TextView) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setTextViewAnimation();
								}else if (onSetTextViewAnimationListener!=null) {
									onSetTextViewAnimationListener.setTextViewAnimation();
								}else {
									new FadeInAnimation(v).animate();
								}
							}else if (v instanceof Button) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setButtonAnimation();
								}else if (onSetButtonAnimationListener!=null) {
									onSetButtonAnimationListener.setButtonAnimation();
								}else {
									new SlideInAnimation(v).setDirection(Animation.DIRECTION_RIGHT).animate();
								}
							}else if (v instanceof ToggleButton) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setToggleButtonAnimation();
								}else if (onSetToggleButtonAnimationListener!=null) {
									onSetToggleButtonAnimationListener.setToggleButtonAnimation();
								}else {
									new SlideInAnimation(v).setDirection(Animation.DIRECTION_RIGHT).animate();
								}
							}else if (v instanceof CheckBox) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setCheckBoxAnimation();
								}else if (onSetCheckBoxAnimationListener!=null) {
									onSetCheckBoxAnimationListener.setCheckBoxAnimation();
								}else {
									new RotationAnimation(v).setPivot(RotationAnimation.PIVOT_CENTER).animate();
								}
							}else if (v instanceof RadioButton) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setRadioButtonAnimation();
								}else if (onSetRadioButtonAnimationListener!=null) {
									onSetRadioButtonAnimationListener.setRadioButtonAnimation();
								}else {
									new SlideInAnimation(v).setDirection(Animation.DIRECTION_DOWN).animate();
								}
							}else if (v instanceof Spinner) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setSpinnerAnimation();
								}else if (onSetSpinnerAnimationListener !=null) {
									onSetSpinnerAnimationListener.setSpinnerAnimation();
								}else {
									new SlideInAnimation(v).setDirection(Animation.DIRECTION_RIGHT).animate();
								}
							}else if (v instanceof ProgressBar) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setProgressBarAnimation();
								}else if (onSetProgressBarAnimationListener !=null) {
									onSetProgressBarAnimationListener.setProgressBarAnimation();
								}else {
									new ScaleInAnimation(v).animate();
								}
							}else if (v instanceof SeekBar) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setSeekBarAnimation();
								}else if (onSetSeekBarAnimationListener !=null) {
									onSetSeekBarAnimationListener.setSeekBarAnimation();
								}else {
									new PuffInAnimation(v).animate();
								}
							}else if (v instanceof RadioGroup) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setRadioGroupAnimation();
								}else if (onSetRadioGroupAnimationListener !=null) {
									onSetRadioGroupAnimationListener.setRadioGroupAnimation();
								}else {
									new FadeInAnimation(v).animate();
								}
							}else if (v instanceof RatingBar) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setRatingBarAnimation();
								}else if (onSetRatingBarAnimationListener !=null) {
									onSetRatingBarAnimationListener.setRatingBarAnimation();
								}else {
									new SlideInAnimation(v).setDirection(Animation.DIRECTION_LEFT).animate();
								}
							}else if (v instanceof Switch) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setSwitchAnimation();
								}else if (onSetSwitchAnimationListener !=null) {
									onSetSwitchAnimationListener.setSwitchAnimation();
								}else {
									new RotationAnimation(v).setPivot(RotationAnimation.PIVOT_CENTER).animate();
								}
							}else if (v instanceof EditText) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setEditTextAnimation();
								}else if (onSetEditTextAnimationListener !=null) {
									onSetEditTextAnimationListener.setEditTextAnimation();
								}else {
									new SlideInAnimation(v).setDirection(Animation.DIRECTION_RIGHT).animate();
								}
							}else if (v instanceof AutoCompleteTextView) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setAutoCompleteTextViewAnimation();
								}else if (onSetAutoCompleteTextViewAnimationListener !=null) {
									onSetAutoCompleteTextViewAnimationListener.setAutoCompleteTextViewAnimation();
								}else {
									new ScaleInAnimation(v).animate();
								}
							}else if (v instanceof LinearLayout) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setLinearLayoutAnimation();
								}else if (onSetLinearLayoutAnimationListener !=null) {
									onSetLinearLayoutAnimationListener.setLinearLayoutAnimation();
								}else {
									new FadeInAnimation(v).animate();
								}
							}else if (v instanceof RelativeLayout) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setRelativeLayoutAnimation();
								}else if (onSetRelativeLayoutAnimationListener !=null) {
									onSetRelativeLayoutAnimationListener.setRelativeLayoutAnimation();
								}else {
									new FadeInAnimation(v).animate();
								}
							}else if (v instanceof FrameLayout) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setFrameLayoutAnimation();
								}else if (onSetFrameLayoutAnimationListener !=null) {
									onSetFrameLayoutAnimationListener.setFrameLayoutAnimation();
								}else {
									new FadeInAnimation(v).animate();
								}
							}else if (v instanceof TableLayout) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setTableLayoutAnimation();
								}else if (onSetTableLayoutAnimationListener !=null) {
									onSetTableLayoutAnimationListener.setTableLayoutAnimation();
								}else {
									new FadeInAnimation(v).animate();
								}
							}else if (v instanceof ExpandableListView) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setExpandableListViewAnimation();
								}else if (onSetExpandableListViewAnimationListener !=null) {
									onSetExpandableListViewAnimationListener.setExpandableListViewAnimation();
								}else {
									new FadeInAnimation(v).animate();
								}
							}else if (v instanceof GridView) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setGridViewAnimation();
								}else if (onSetGridViewAnimationListener !=null) {
									onSetGridViewAnimationListener.setGridViewAnimation();
								}else {
									new FadeInAnimation(v).animate();
								}
							}else if (v instanceof ScrollView) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setScrollViewAnimation();
								}else if (onSetScrollViewAnimationListener !=null) {
									onSetScrollViewAnimationListener.setScrollViewAnimation();
								}else {
									new FadeInAnimation(v).animate();
								}
							}else if (v instanceof HorizontalScrollView) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setHorizontalScrollViewAnimation();
								}else if (onSetHorizontalScrollViewAnimationListener !=null) {
									onSetHorizontalScrollViewAnimationListener.setHorizontalScrollViewAnimation();
								}else {
									new FadeInAnimation(v).animate();
								}
							}else if (v instanceof WebView) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setWebViewAnimation();
								}else if (onSetWebViewAnimationListener !=null) {
									onSetWebViewAnimationListener.setWebViewAnimation();
								}else {
									new SlideInAnimation(v).setDirection(Animation.DIRECTION_DOWN).animate();
								}
							}else if (v instanceof ImageView) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setImageViewAnimation();
								}else if (onSetImageViewAnimationListener !=null) {
									onSetImageViewAnimationListener.setImageViewAnimation();
								}else {
									new SlideInAnimation(v).setDirection(Animation.DIRECTION_UP).animate();
								}
							}else if (v instanceof ImageButton) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setImageButtonAnimation();
								}else if (onSetImageButtonAnimationListener !=null) {
									onSetImageButtonAnimationListener.setImageButtonAnimation();
								}else {
									new FlipHorizontalAnimation(v).setPivot(FlipHorizontalAnimation.PIVOT_CENTER).animate();
								}
							}else if (v instanceof VideoView) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setVideoViewAnimation();
								}else if (onSetVideoViewAnimationListener !=null) {
									onSetVideoViewAnimationListener.setVideoViewAnimation();
								}else {
									new ScaleInAnimation(v).animate();
								}
							}else if (v instanceof Gallery) {
								if (onSetChildWidgetAnimationListener!=null) {
									onSetChildWidgetAnimationListener.setGalleryAnimation();
								}else if (onSetGalleryAnimationListener !=null) {
									onSetGalleryAnimationListener.setGalleryAnimation();
								}else {
									new RotationAnimation(v).setPivot(RotationAnimation.PIVOT_CENTER).animate();
								}
							}
						}
						
						
					}
				});
			}
		};
       timer.schedule(timerTask, delay);
	}
	
	
	/**
	 * 自定义设置子控件动画接口,需在starAnimation前调用
	 * @author Robin
	 *  time 2015-02-02 10:05:40
	 *
	 */
	public interface OnSetChildWidgetAnimationListener{
		public void setTextViewAnimation();
		public void setButtonAnimation();
		public void setToggleButtonAnimation();
		public void setCheckBoxAnimation();
		public void setRadioButtonAnimation();
		public void setSpinnerAnimation();
		public void setProgressBarAnimation();
		public void setSeekBarAnimation();
		public void setRadioGroupAnimation();
		public void setRatingBarAnimation();
		public void setSwitchAnimation();
		public void setEditTextAnimation();
		public void setAutoCompleteTextViewAnimation();
		public void setLinearLayoutAnimation();
		public void setRelativeLayoutAnimation();
		public void setFrameLayoutAnimation();
		public void setTableLayoutAnimation();
		public void setExpandableListViewAnimation();
		public void setGridViewAnimation();
		public void setScrollViewAnimation();
		public void setHorizontalScrollViewAnimation();
		public void setWebViewAnimation();
		public void setImageViewAnimation();
		public void setImageButtonAnimation();
		public void setVideoViewAnimation();
		public void setGalleryAnimation();
	}
	
	public void setChildWidgetAnimationListener(OnSetChildWidgetAnimationListener onSetChildWidgetAnimationListener){
		ChildWidgetAnimationKit.onSetChildWidgetAnimationListener=onSetChildWidgetAnimationListener;
	}
	
	/**
	 * 设置TextView控件的动画接口
	 * @author Robin
	 *  time 2015-02-02 11:27:02
	 *
	 */
	public interface OnSetTextViewAnimationListener{
		public void setTextViewAnimation();
	}
	
	public static void setSetTextViewAnimationListener(OnSetTextViewAnimationListener onSetTextViewAnimationListener){
		ChildWidgetAnimationKit.onSetTextViewAnimationListener=onSetTextViewAnimationListener;
	}
	
	public interface OnSetButtonAnimationListener{
		public void setButtonAnimation();
	}
	
	public static void setSetButtonAnimationListener(OnSetButtonAnimationListener onSetButtonAnimationListener){
		ChildWidgetAnimationKit.onSetButtonAnimationListener=onSetButtonAnimationListener;
	}
	
	public interface OnSetToggleButtonAnimationListener{
		public void setToggleButtonAnimation();
	}
	
	public static void setSetToggleButtonAnimationListener(OnSetToggleButtonAnimationListener onSetToggleButtonAnimationListener){
		ChildWidgetAnimationKit.onSetToggleButtonAnimationListener=onSetToggleButtonAnimationListener;
	}
	
	public interface OnSetCheckBoxAnimationListener{
		public void setCheckBoxAnimation();
	}
	
	public static void setSetCheckBoxAnimationListener(OnSetCheckBoxAnimationListener onSetCheckBoxAnimationListener){
		ChildWidgetAnimationKit.onSetCheckBoxAnimationListener=onSetCheckBoxAnimationListener;
	}
	
	public interface OnSetRadioButtonAnimationListener{
		public void setRadioButtonAnimation();
	}
	
	public static void setSetRadioButtonAnimationListener( OnSetRadioButtonAnimationListener onSetRadioButtonAnimationListener){
		ChildWidgetAnimationKit.onSetRadioButtonAnimationListener=onSetRadioButtonAnimationListener;
	}
	
	public interface OnSetSpinnerAnimationListener{
		public void setSpinnerAnimation();
	}
	
	public static void setSetSpinnerAnimationListener(OnSetSpinnerAnimationListener onSetSpinnerAnimationListener){
		ChildWidgetAnimationKit.onSetSpinnerAnimationListener=onSetSpinnerAnimationListener;
	}
	
	public interface OnSetProgressBarAnimationListener{
		public void setProgressBarAnimation();
	}
	
	public static void setSetProgressBarAnimationListener(OnSetProgressBarAnimationListener onSetProgressBarAnimationListener){
		ChildWidgetAnimationKit.onSetProgressBarAnimationListener=onSetProgressBarAnimationListener;
	}
	
	public interface OnSetSeekBarAnimationListener{
		public void setSeekBarAnimation();
	}
	
	public static void setSetSeekBarAnimationListener(OnSetSeekBarAnimationListener onSetSeekBarAnimationListener){
		ChildWidgetAnimationKit.onSetSeekBarAnimationListener=onSetSeekBarAnimationListener;
	}
	
	public interface OnSetRadioGroupAnimationListener{
		public void setRadioGroupAnimation();
	}
	
	public static void setSetRadioGroupAnimationListener(OnSetRadioGroupAnimationListener onSetRadioGroupAnimationListener){
		ChildWidgetAnimationKit.onSetRadioGroupAnimationListener=onSetRadioGroupAnimationListener;
	}
	
	public interface OnSetRatingBarAnimationListener{
		public void setRatingBarAnimation();
	}
	
	public static void setSetRatingBarAnimationListener(OnSetRatingBarAnimationListener onSetRatingBarAnimationListener){
		ChildWidgetAnimationKit.onSetRatingBarAnimationListener=onSetRatingBarAnimationListener;
	}
	
	public interface OnSetSwitchAnimationListener{
		public void setSwitchAnimation();
	}
	
	public static void setSetSwitchAnimationListener(OnSetSwitchAnimationListener onSetSwitchAnimationListener){
		ChildWidgetAnimationKit.onSetSwitchAnimationListener=onSetSwitchAnimationListener;
	}
	
	public interface OnSetEditTextAnimationListener{
		public void setEditTextAnimation();
	}
	
	public static void setSetEditTextAnimationListener( OnSetEditTextAnimationListener onSetEditTextAnimationListener){
		ChildWidgetAnimationKit.onSetEditTextAnimationListener=onSetEditTextAnimationListener;
	}
	
	public interface OnSetAutoCompleteTextViewAnimationListener{
		public void setAutoCompleteTextViewAnimation();
	}
	
	public static void setSetAutoCompleteTextViewAnimationListener(OnSetAutoCompleteTextViewAnimationListener onSetAutoCompleteTextViewAnimationListener){
		ChildWidgetAnimationKit.onSetAutoCompleteTextViewAnimationListener=onSetAutoCompleteTextViewAnimationListener;
	}
	
	public interface OnSetLinearLayoutAnimationListener{
		public void setLinearLayoutAnimation();
	}
	
	public static void setSetLinearLayoutAnimationListener(OnSetLinearLayoutAnimationListener onSetLinearLayoutAnimationListener){
		ChildWidgetAnimationKit.onSetLinearLayoutAnimationListener=onSetLinearLayoutAnimationListener;
	}
	
	public interface OnSetRelativeLayoutAnimationListener{
		public void setRelativeLayoutAnimation();
	}
	
	public static void setSetRelativeLayoutAnimationListener(OnSetRelativeLayoutAnimationListener onSetRelativeLayoutAnimationListener){
		ChildWidgetAnimationKit.onSetRelativeLayoutAnimationListener=onSetRelativeLayoutAnimationListener;
	}
	
	public interface OnSetFrameLayoutAnimationListener{
		public void setFrameLayoutAnimation();
	}
	
	public static void setSetFrameLayoutAnimationListener(OnSetFrameLayoutAnimationListener onSetFrameLayoutAnimationListener){
		ChildWidgetAnimationKit.onSetFrameLayoutAnimationListener=onSetFrameLayoutAnimationListener;
	}
	
	public interface OnSetTableLayoutAnimationListener{
		public void setTableLayoutAnimation();
	}
	
	public static void setSetTableLayoutAnimationListener(OnSetTableLayoutAnimationListener onSetTableLayoutAnimationListener){
		ChildWidgetAnimationKit.onSetTableLayoutAnimationListener=onSetTableLayoutAnimationListener;
	}
	
	public interface OnSetExpandableListViewAnimationListener{
		public void setExpandableListViewAnimation();
	}
	
	public static void setSetExpandableListViewAnimationListener(OnSetExpandableListViewAnimationListener onSetExpandableListViewAnimationListener){
		ChildWidgetAnimationKit.onSetExpandableListViewAnimationListener=onSetExpandableListViewAnimationListener;
	}
	
	public interface OnSetGridViewAnimationListener{
		public void setGridViewAnimation();
	}
	
	public static void setSetGridViewAnimationListener(OnSetGridViewAnimationListener onSetGridViewAnimationListener){
		ChildWidgetAnimationKit.onSetGridViewAnimationListener=onSetGridViewAnimationListener;
	}
	
	public interface OnSetScrollViewAnimationListener{
		public void setScrollViewAnimation();
	}
	
	public static void setSetScrollViewAnimationListener(OnSetScrollViewAnimationListener onSetScrollViewAnimationListener){
		ChildWidgetAnimationKit.onSetScrollViewAnimationListener=onSetScrollViewAnimationListener;
	}
	
	public interface OnSetHorizontalScrollViewAnimationListener{
		public void setHorizontalScrollViewAnimation();
	}
	
	public static void setSetHorizontalScrollViewAnimationListener(OnSetHorizontalScrollViewAnimationListener onSetHorizontalScrollViewAnimationListener){
		ChildWidgetAnimationKit.onSetHorizontalScrollViewAnimationListener=onSetHorizontalScrollViewAnimationListener;
	}
	
	public interface OnSetWebViewAnimationListener{
		public void setWebViewAnimation();
	}
	
	public static void setSetWebViewAnimationListener(OnSetWebViewAnimationListener onSetWebViewAnimationListener){
		ChildWidgetAnimationKit.onSetWebViewAnimationListener=onSetWebViewAnimationListener;
	}
	
	public interface OnSetImageViewAnimationListener{
		public void setImageViewAnimation();
	}
	
	public static void setSetImageViewAnimationListener(OnSetImageViewAnimationListener onSetImageViewAnimationListener){
		ChildWidgetAnimationKit.onSetImageViewAnimationListener=onSetImageViewAnimationListener;
	}
	
	public interface OnSetImageButtonAnimationListener{
		public void setImageButtonAnimation();
	}
	
	public static void setSetImageButtonAnimationListener(OnSetImageButtonAnimationListener onSetImageButtonAnimationListener){
		ChildWidgetAnimationKit.onSetImageButtonAnimationListener=onSetImageButtonAnimationListener;
	}
	
	public interface OnSetVideoViewAnimationListener{
		public void setVideoViewAnimation();
	}
	
	public static void setSetVideoViewAnimationListener(OnSetVideoViewAnimationListener onSetVideoViewAnimationListener){
		ChildWidgetAnimationKit.onSetVideoViewAnimationListener=onSetVideoViewAnimationListener;
	}
	
	public interface OnSetGalleryAnimationListener{
		public void setGalleryAnimation();
	}
	
	
	public static void setSetGalleryAnimationListener (OnSetGalleryAnimationListener onSetGalleryAnimationListener){
		ChildWidgetAnimationKit.onSetGalleryAnimationListener=onSetGalleryAnimationListener;
	}
	
	
}
