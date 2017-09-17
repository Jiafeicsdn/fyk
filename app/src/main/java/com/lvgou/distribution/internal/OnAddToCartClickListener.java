package com.lvgou.distribution.internal;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 添加购物车点击回调接口
 * @author Robin
 * time 2015-04-09 15:51:24
 *
 */
public interface OnAddToCartClickListener <T> {
	/**
	 * 添加购物车点击回调函数
	 * @param v 计算位置的视图
	 * @param productDrawable 执行动画的图片
	 * @param mHasOption 是否有更多的选项
	 */
	public void onAddToCartClick(View v, T entityData, Drawable productDrawable, Boolean mHasOption);
}
