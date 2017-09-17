package com.lvgou.distribution.utils;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.lvgou.distribution.internal.OnAddToCartAnimationFinishListener;
import com.xdroid.common.utils.ScreenUtils;



/**
 * 公有功能函数类
 *
 * @author Robin
 *         time 2015-04-09 16:18:18
 */
public class CommonFunctions {
    private OnAddToCartAnimationFinishListener onAddToCartAnimationFinishListener;

    private CommonFunctions() {
    }

    private static class CommonFunctionstHolder {
        public static final CommonFunctions INSTANCE = new CommonFunctions();
    }

    public static CommonFunctions getInstance() {
        return CommonFunctionstHolder.INSTANCE;
    }


    /**
     * 创建动画层
     *
     * @param activity 所属Activity
     * @return animLayout动画层
     */
    private ViewGroup createAnimLayout(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
//			animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 设置view的属性
     *
     * @param activity 所属activity
     * @param view     要执行动画的View
     * @param location 起始位置
     * @return view要执行动画的View
     */
    private View setViewProperty(final Activity activity, final View view,
                                 int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                (int) ScreenUtils.dpToPx(activity, 100),
                (int) ScreenUtils.dpToPx(activity, 100));
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 设置添加购物车动画，使用补间动画
     *
     * @param activity       所属Activity
     * @param v              要执行动画的View
     * @param start_location 起始位置数组
     * @param endX           结束x点
     * @param endY           结束y点
     */
    public void setAddToCartAnim(Activity activity, final View v, int[] start_location, int endX, int endY) {
        LinearLayout anim_mask_layout = null;
        anim_mask_layout = (LinearLayout) createAnimLayout(activity);
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = setViewProperty(activity, v,
                start_location);
            /*-------------------------------------------设置动画-----------------------------------------------*/
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);


        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
                if (onAddToCartAnimationFinishListener != null) {
                    onAddToCartAnimationFinishListener.OnAddToCartAnimationFinish();
                }
            }
        });

    }

    /**
     * 添加到购物车抛物线动画，使用属性动画
     *
     * @param activity       所属Activity
     * @param view           要执行动画的View
     * @param start_location 起始位置数组
     * @param endX           结束x点
     * @param endY           结束y点
     */
    public void setAddToCartAnimation(Activity activity, final View view, LinearLayout.LayoutParams lp, int[] start_location, int endX, int endY) {

        final LinearLayout anim_mask_layout = (LinearLayout) createAnimLayout(activity);
        anim_mask_layout.addView(view);//把动画小球添加到动画层

        int x = start_location[0];
        int y = start_location[1];

        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);

        ObjectAnimator translateXAnimator = ObjectAnimator.ofFloat(view, "translationX", 0, endX);
        translateXAnimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(view, "translationY", 0, endY);
        translateYAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 0.2f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 0.2f);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.5f);

        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(view, "rotation", 360f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translateXAnimator).with(translateYAnimator).with(scaleXAnimator).with(scaleYAnimator).with(alphaAnimator).with(rotateAnimator);
        animatorSet.setDuration(800);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
                anim_mask_layout.removeView(view);
                if (onAddToCartAnimationFinishListener != null) {
                    onAddToCartAnimationFinishListener.OnAddToCartAnimationFinish();
                }
            }
        });
    }

    /**
     * 设置购物车图标放大缩小动画
     *
     * @param view
     */
    public void setCartImageAnim(View view) {
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.5f, 1.0f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.5f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorScaleX).with(animatorScaleY);
        animatorSet.setDuration(300);
        animatorSet.start();
    }

    /**
     * 设置添加到购物车动画结束回调
     *
     * @param onAddToCartAnimationFinishListener
     */
    public void setOnAddToCartAnimationFinishListener(OnAddToCartAnimationFinishListener onAddToCartAnimationFinishListener) {
        this.onAddToCartAnimationFinishListener = onAddToCartAnimationFinishListener;
    }

    /**
     * 设置购物车view透明度
     *
     * @param willHideView 将要隐藏的view
     * @param willShowView 将要显示的view
     */
    public void setAlphaAnim(final View willHideView, final View willShowView) {
        ObjectAnimator hideAlphaAnimator = ObjectAnimator.ofFloat(willHideView, "alpha", 1.0f, 0.0f);
        ObjectAnimator showAlphaAnimator = ObjectAnimator.ofFloat(willShowView, "alpha", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.play(showAlphaAnimator).with(hideAlphaAnimator);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                willHideView.setVisibility(View.GONE);
                willShowView.setVisibility(View.VISIBLE);
            }
        });
    }

//		/**
//		 * 创建MenuDrawer抽屉
//		 */
//		public static MenuDrawer createMenuDrawer(Activity activity,int contentViewId,View menuView,Type type,Position orientation,float menuSizePercent) {
//			MenuDrawer menuDrawer = MenuDrawer.attach(activity, type, orientation,
//					MenuDrawer.MENU_DRAG_CONTENT);
//			menuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE); // 触摸打开模式
//			menuDrawer.setContentView(contentViewId);
//			menuDrawer.setMenuView(menuView); // 设置抽屉布局
//			menuDrawer.setMenuSize((int) (ScreenUtils.getScreenWidth(activity) * menuSizePercent)); // 设置抽屉的尺寸
//			menuDrawer.setBackgroundColor(Color.TRANSPARENT); // 设置背景
//			menuDrawer.setDropShadowColor(activity.getResources().getColor(R.color.transparent));
//			return menuDrawer;
//		}

}
