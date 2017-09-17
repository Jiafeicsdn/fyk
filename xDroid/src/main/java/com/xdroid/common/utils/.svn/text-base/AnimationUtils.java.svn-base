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
package com.xdroid.common.utils;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;

// TODO: Auto-generated Javadoc
/**
 * © 2012 amsoft.cn
 * 名称：AbAnimationUtil.java 
 * 描述：动画工具类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2011-11-10 下午11:52:13
 */
public class AnimationUtils {
	
	/** 定义动画的时间. */
	public final static long aniDurationMillis = 1L;
	
	public enum AnimationSpeed {
		FAST, MEDIUM, SLOW;
	}
	public static Runnable runnable;

	/**
	 * 用来改变当前选中区域的放大动画效果
	 * 从1.0f放大1.2f倍数
	 *
	 * @param view the view
	 * @param scale the scale
	 */
	public static void largerView(View view, float scale) {
		if (view == null)
			return;

		// 置于所有view最上层
		view.bringToFront();
		int width = view.getWidth();
		float animationSize = 1 + scale / width;
		scaleView(view, animationSize);
	}

	/**
	 * 用来还原当前选中区域的还原动画效果.
	 *
	 * @param view the view
	 * @param scale the scale
	 */
	public static void restoreLargerView(View view, float scale) {
		if (view == null)
			return;
		int width = view.getWidth();
		float toSize = 1 + scale / width;
		// 从1.2f缩小1.0f倍数
		scaleView(view, -1 * toSize);
	}

	/**
	 * 缩放View的显示.
	 *
	 * @param view 需要改变的View
	 * @param toSize 缩放的大小，其中正值代表放大，负值代表缩小，数值代表缩放的倍数
	 */
	private static void scaleView(final View view, float toSize) {
		ScaleAnimation scale = null;
		if (toSize == 0) {
			return;
		} else if (toSize > 0) {
			scale = new ScaleAnimation(1.0f, toSize, 1.0f, toSize,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
		} else {
			scale = new ScaleAnimation(toSize * (-1), 1.0f, toSize * (-1),
					1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
		}
		scale.setDuration(aniDurationMillis);
		scale.setInterpolator(new AccelerateDecelerateInterpolator());
		scale.setFillAfter(true);
		view.startAnimation(scale);
	}
	
	/**
	 * 跳动-跳起动画.
	 *
	 * @param view the view
	 * @param offsetY the offset y
	 */
    private void playJumpAnimation(final View view,final float offsetY) {
        float originalY = 0;
        float finalY = - offsetY;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(0, 0, originalY,finalY));
        animationSet.setDuration(300);
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animationSet.setFillAfter(true);

        animationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                playLandAnimation(view,offsetY);
            }
        });

        view.startAnimation(animationSet);
    }

    /**
     * 跳动-落下动画.
     *
     * @param view the view
     * @param offsetY the offset y
     */
    private void playLandAnimation(final View view,final float offsetY) {
        float originalY =  - offsetY;
        float finalY = 0;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(0, 0, originalY,finalY));
        animationSet.setDuration(200);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);

        animationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //两秒后再调
                view.postDelayed(new Runnable(){
                    
                    @Override
                    public void run(){
                        playJumpAnimation(view,offsetY);
                    }
                }, 2000);
            }
        });

        view.startAnimation(animationSet);
    }
    
    /**
     * 旋转动画
     * @param v
     * @param durationMillis
     * @param repeatCount  Animation.INFINITE
     * @param repeatMode  Animation.RESTART
     */
    public static void playRotateAnimation(View v,long durationMillis,int repeatCount,int repeatMode) {
    	
        //创建AnimationSet对象
        AnimationSet animationSet = new AnimationSet(true);
        //创建RotateAnimation对象
        RotateAnimation rotateAnimation = new RotateAnimation(0f,+360f, 
					Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        //设置动画持续
        rotateAnimation.setDuration(durationMillis);
        rotateAnimation.setRepeatCount(repeatCount);
        //Animation.RESTART
        rotateAnimation.setRepeatMode(repeatMode);
        //动画插入器
        rotateAnimation.setInterpolator(v.getContext(), android.R.anim.decelerate_interpolator);
        //添加到AnimationSet
        animationSet.addAnimation(rotateAnimation);
        
        //开始动画 
        v.startAnimation(animationSet);
	}
    
    /**
	 * 垂直方向展开动画
	 * @param view  要展开的视图
	 */
	public static void expandingAnimation(final View view) {  
        view.setVisibility(View.VISIBLE);  
 
       final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);  
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);  
        view.measure(widthSpec, heightSpec);  
  
       ValueAnimator animator = createHeightAnimator(view, 0, view.getMeasuredHeight());  
        animator.start();  
    }  
	

	/**
	 *   垂直方向折叠动画
	 * @param view 要折叠的视图
	 */
	public static void collapsingAnimation(final View view) {  
        int origHeight = view.getHeight();  
  
        ValueAnimator animator = createHeightAnimator(view, origHeight, 0);  
        animator.addListener(new AnimatorListenerAdapter() {  
            public void onAnimationEnd(Animator animation) {  
                view.setVisibility(View.GONE);  
            };  
        });  
        animator.start();  
    }  
	
	/**
	 * 创建Animator对象
	 * @param view 视图
	 * @param start 开始
	 * @param end 结束
	 * @return Animator对象
	 */
	public static ValueAnimator createHeightAnimator(final View view, int start, int end) {  
        ValueAnimator animator = ValueAnimator.ofInt(start, end);  
       animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {  
  
            @Override  
            public void onAnimationUpdate(ValueAnimator valueAnimator) {  
               int value = (Integer) valueAnimator.getAnimatedValue();  
 
               ViewGroup.LayoutParams layoutParams = view.getLayoutParams();  
               layoutParams.height = value;  
                view.setLayoutParams(layoutParams);  
            }  
        });  
//        animator.setDuration(DURATION);  
        return animator;  
    }  
	
	/**
	 * 设置晃动动画
	 */
	public static void setShakeAnimation(EditText editText) {
		editText.startAnimation(shakeAnimation(5));
	}

	/**
	 * 晃动动画
	 * 
	 * @param counts
	 *            1秒钟晃动多少下
	 * @return
	 */
	public static Animation shakeAnimation(int counts) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
		translateAnimation.setInterpolator(new CycleInterpolator(counts));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}
	
	/**
	 *  旋转ImageView
	 * @param context  上下文
	 * @param clockwise  顺时针方向
	 * @param speed  旋转速度
	 * @param imageView 要旋转的Imageview
	 */
	public static void startRotateAnimation(Context context, boolean clockwise,
			AnimationSpeed speed, final ImageView imageView) {
		Animation rotate;
	

		// set up the rotation animation
		if (clockwise) {
			rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
					0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		} else {
			rotate = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF,
					0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		}

		// set up some extra variables
		rotate.setRepeatCount(Animation.INFINITE);
		rotate.setInterpolator(new LinearInterpolator());
		rotate.setStartOffset(0);
		rotate.setRepeatMode(Animation.RESTART);

		// defaults
		rotate.setDuration(2000);

		// fast
		if (speed.equals(AnimationSpeed.FAST)) {
			rotate.setDuration(500);
		}

		// medium
		if (speed.equals(AnimationSpeed.MEDIUM)) {
			rotate.setDuration(1000);
		}

		// send the new animation to a final animation
		final Animation animation = rotate;

		// run the animation - used to work correctly on older devices
			runnable=new Runnable() {
				@Override
				public void run() {
					imageView.startAnimation(animation);
				}
			};
		imageView.postDelayed(runnable, 100);

	}
	
	/**
	 * 停止ImageView旋转动画
	 */
	public static void stopRotateAnimation(ImageView imageView) {
		// stop the animation
		imageView.clearAnimation();
		imageView.removeCallbacks(runnable);
	}

}
