/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xdroid.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdroid.common.utils.FileUtils;
import com.xdroid.common.utils.ImageUtils;
import com.xdroid.common.utils.ScreenUtils;
import com.xdroid.common.utils.TimeUtils;
import com.xdroid.common.utils.ViewUtils;

// TODO: Auto-generated Javadoc

/**
 * 描述：下拉刷新的Header View类.
 */
public class PullToRefreshViewHeader extends LinearLayout {
	
	/** 上下文. */
	private Context mContext;
	
	/** 主View. */
	private LinearLayout headerView;
	
	/** 箭头图标View. */
	private ImageView arrowImageView;
	
	/** 进度图标View. */
//	private ProgressBar headerProgressBar;
	private ProgressWheel headerProgressBar;
	
	/** 箭头图标. */
	private Bitmap arrowImage = null;
	
	/** 文本提示的View. */
	private TextView tipsTextview;
	
	/** 时间的View. */
	private TextView headerTimeView;
	
	/** 当前状态. */
	private int mState = -1;

	/** 向上的动画. */
	private Animation mRotateUpAnim;
	
	/** 向下的动画. */
	private Animation mRotateDownAnim;
	
	/** 动画时间. */
	private final int ROTATE_ANIM_DURATION = 180;
	
	/** 显示 下拉刷新. */
	public final static int STATE_NORMAL = 0;
	
	/** 显示 松开刷新. */
	public final static int STATE_READY = 1;
	
	/** 显示 正在刷新.... */
	public final static int STATE_REFRESHING = 2;
	
	/** 保存上一次的刷新时间. */
	private String lastRefreshTime = null;
	
	/**  Header的高度. */
	private int headerHeight;

	/**
	 * 初始化Header.
	 *
	 * @param context the context
	 */
	public PullToRefreshViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * 初始化Header.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public PullToRefreshViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 初始化View.
	 * 
	 * @param context the context
	 */
	@SuppressWarnings("deprecation")
	private void initView(Context context) {
		
		mContext  = context;
		
		//顶部刷新栏整体内容
		headerView = new LinearLayout(context);
		headerView.setOrientation(LinearLayout.HORIZONTAL);
		headerView.setGravity(Gravity.CENTER); 
		headerView.setBackgroundColor(Color.rgb(31, 62, 77));
		
		ViewUtils.setPadding(headerView, 0, 25, 0, 25);
		
		//显示箭头与进度
		FrameLayout headImage =  new FrameLayout(context);
		arrowImageView = new ImageView(context);
		//从包里获取的箭头图片
		arrowImage = FileUtils.getBitmapFromSrc("image/arrow.png");
		arrowImageView.setImageBitmap(arrowImage);
		
		//style="?android:attr/progressBarStyleSmall" 默认的样式
//		headerProgressBar = new ProgressBar(context,null,android.R.attr.progressBarStyle);
		headerProgressBar=new ProgressWheel(context);
		headerProgressBar.setBarColor(Color.rgb(255, 255, 255));
		headerProgressBar.setBarWidth((int) ScreenUtils.dpToPx(context, 2));
		headerProgressBar.setLinearProgress(true); //设置直行进度无动画
		
		//下面构建一个圆角的灰色bitmap作为headerProgressBar的背景
		int WIDTH=40;  //bitmap的宽
		int HEIGHT=40;
		//设置colors数组，作为bitmap背景色
	    int[] colors=new int[WIDTH*HEIGHT];  
        for (int y = 0; y < HEIGHT; y++) {//use of x,y is legible then the use of i,j  
             for (int x = 0; x < WIDTH; x++) {  
              /*   int r = x * 255 / (WIDTH - 1);  
                 int g = y * 255 / (HEIGHT - 1);  
                 int b = 255 - Math.min(r, g);  
                 int a = Math.max(r, g);  */
            	   int r = 200;  
                   int g = 200;  
                   int b = 200;  
                   int a = 150;  
                 colors[y*WIDTH+x]=(a<<24)|(r<<16)|(g<<8)|(b);//the shift operation generates the color ARGB  
             }  
         }
		Bitmap bitmap=Bitmap.createBitmap(colors,WIDTH, HEIGHT, Config.ARGB_8888);  //构建bitmap
		Bitmap roundBitmap=ImageUtils.getRoundedCornerBitmap(bitmap, 20);  //切为圆形bitmap
		headerProgressBar.setBackground(ImageUtils.bitmapToDrawable(roundBitmap));  //设置为背景
//		headerProgressBar.setVisibility(View.GONE);
		
		LinearLayout.LayoutParams layoutParamsWW = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW.gravity = Gravity.CENTER;
		layoutParamsWW.width = ViewUtils.scale(mContext, 50);
		layoutParamsWW.height = ViewUtils.scale(mContext, 50);
		headImage.addView(arrowImageView,layoutParamsWW);
		headImage.addView(headerProgressBar,layoutParamsWW);
		
		//顶部刷新栏文本内容
		LinearLayout headTextLayout  = new LinearLayout(context);
		tipsTextview = new TextView(context);
		headerTimeView = new TextView(context);
		headTextLayout.setOrientation(LinearLayout.VERTICAL);
		headTextLayout.setGravity(Gravity.CENTER_VERTICAL);
		ViewUtils.setPadding(headTextLayout,0, 0, 0, 0);
		LinearLayout.LayoutParams layoutParamsWW2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		headTextLayout.addView(tipsTextview,layoutParamsWW2);
		headTextLayout.addView(headerTimeView,layoutParamsWW2);
		tipsTextview.setTextColor(Color.rgb(255, 255, 255));
		headerTimeView.setTextColor(Color.argb(100,255, 255, 255));
		ViewUtils.setTextSize(tipsTextview,30);
		ViewUtils.setTextSize(headerTimeView,20);
		
		LinearLayout.LayoutParams layoutParamsWW3 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW3.gravity = Gravity.CENTER;
		layoutParamsWW3.rightMargin = ViewUtils.scale(mContext, 10);
		
		LinearLayout headerLayout = new LinearLayout(context);
		headerLayout.setOrientation(LinearLayout.HORIZONTAL);
		headerLayout.setGravity(Gravity.CENTER); 
		
		headerLayout.addView(headImage,layoutParamsWW3);
		headerLayout.addView(headTextLayout,layoutParamsWW3);
		
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.BOTTOM;
		//添加大布局
		headerView.addView(headerLayout,lp);
		
		this.addView(headerView,lp);
		//获取View的高度
		ViewUtils.measureView(this);
		headerHeight = this.getMeasuredHeight();
		
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
		
		setState(STATE_NORMAL);
	}

	/**
	 * 设置状态.
	 *
	 * @param state the new state
	 */
	public void setState(int state) {
		if (state == mState) return ;
		
		if (state == STATE_REFRESHING) {	
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.INVISIBLE);
			headerProgressBar.setVisibility(View.VISIBLE);
			headerProgressBar.spin();
		} else {	
			arrowImageView.setVisibility(View.VISIBLE);
//			headerProgressBar.setVisibility(View.INVISIBLE);
		}
		
		switch(state){
			case STATE_NORMAL:
				if (mState == STATE_READY) {
					arrowImageView.startAnimation(mRotateDownAnim);
				}
				if (mState == STATE_REFRESHING) {
					arrowImageView.clearAnimation();
				}
				tipsTextview.setText("下拉刷新");
				
				if(lastRefreshTime==null){
					lastRefreshTime = TimeUtils.getCurrentTimeInString();
					headerTimeView.setText("当前刷新时间：" + lastRefreshTime);
				}else{
					headerTimeView.setText("上次刷新时间：" + lastRefreshTime);
				}
				
				break;
			case STATE_READY:
				if (mState != STATE_READY) {
					arrowImageView.clearAnimation();
					arrowImageView.startAnimation(mRotateUpAnim);
					tipsTextview.setText("松开刷新");
					headerTimeView.setText("上次刷新时间：" + lastRefreshTime);
					lastRefreshTime = TimeUtils.getCurrentTimeInString();
					
				}
				break;
			case STATE_REFRESHING:
				tipsTextview.setText("正在刷新...");
				headerTimeView.setText("本次刷新时间：" + lastRefreshTime);
				break;
				default:
			}
		
		mState = state;
	}
	
	/**
	 * 设置header可见的高度.
	 *
	 * @param height the new visiable height
	 */
	public void setVisiableHeight(int height) {
		if (height < 0) height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) headerView.getLayoutParams();
		lp.height = height;
		headerView.setLayoutParams(lp);
	}

	/**
	 * 获取header可见的高度.
	 *
	 * @return the visiable height
	 */
	public int getVisiableHeight() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)headerView.getLayoutParams();
		return lp.height;
	}

	/**
	 * 描述：获取HeaderView.
	 *
	 * @return the header view
	 */
	public LinearLayout getHeaderView() {
		return headerView;
	}
	
	/**
	 * 设置上一次刷新时间.
	 *
	 * @param time 时间字符串
	 */
	public void setRefreshTime(String time) {
		headerTimeView.setText(time);
	}

	/**
	 * 获取header的高度.
	 *
	 * @return 高度
	 */
	public int getHeaderHeight() {
		return headerHeight;
	}
	
	/**
	 * 描述：设置字体颜色.
	 *
	 * @param color the new text color
	 */
	public void setTextColor(int color){
		tipsTextview.setTextColor(color);
		headerTimeView.setTextColor(color);
	}
	
	/**
	 * 描述：设置背景颜色.
	 *
	 * @param color the new background color
	 */
	public void setBackgroundColor(int color){
		headerView.setBackgroundColor(color);
	}

	/**
	 * 描述：获取Header ProgressBar，用于设置自定义样式.
	 *
	 * @return the header progress bar
	 */
	public ProgressWheel getHeaderProgressBar() {
		return headerProgressBar;
	}

	/**
	 * 描述：设置Header ProgressBar样式.
	 *
	 * @param indeterminateDrawable the new header progress bar drawable
	 */
	@SuppressWarnings("deprecation")
	public void setHeaderProgressBarDrawable(Drawable indeterminateDrawable) {
		headerProgressBar.setBackgroundDrawable(indeterminateDrawable);
	}

	/**
	 * 描述：得到当前状态.
	 *
	 * @return the state
	 */
    public int getState(){
        return mState;
    }

	/**
	 * 设置提示状态文字的大小.
	 *
	 * @param size the new state text size
	 */
	public void setStateTextSize(int size) {
		tipsTextview.setTextSize(size);
	}

	/**
	 * 设置提示时间文字的大小.
	 *
	 * @param size the new time text size
	 */
	public void setTimeTextSize(int size) {
		headerTimeView.setTextSize(size);
	}

	/**
	 * Gets the arrow image view.
	 *
	 * @return the arrow image view
	 */
	public ImageView getArrowImageView() {
		return arrowImageView;
	}

	/**
	 * 描述：设置顶部刷新图标.（注意：如果设置了箭头就隐藏headerProgressBar，不使用headerProgressBar的进度动画）
	 *
	 * @param resId the new arrow image
	 */
	public void setArrowImage(int resId) {
		this.arrowImageView.setImageResource(resId);
		
		//如果设置了箭头就隐藏headerProgressBar，不使用headerProgressBar的进度动画
		headerProgressBar.setVisibility(View.GONE);
	}
	
    

}
