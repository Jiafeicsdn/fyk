package com.lvgou.distribution.widget;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @author xiansenxuan
 *	动画监听 淡入淡出
 */
public class AnimateFirstDisplayListener extends SimpleImageLoadingListener{
	// static final List<String> displayedImages = Collections
	// .synchronizedList(new LinkedList<String>());
//	private final List<String> displayedImages = Collections
//			.synchronizedList(new LinkedList<String>());
	private final List<String> displayedImages = new ArrayList<String>();

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		if (loadedImage != null) {
			ImageView imageView = (ImageView) view;
			boolean firstDisplay = !displayedImages.contains(imageUri);
			if (firstDisplay) {
				FadeInBitmapDisplayer.animate(imageView, 800);
				displayedImages.add(imageUri);
			}
		}
	}

	public void cleanAnimImageList() {
		displayedImages.clear();
	}
}
