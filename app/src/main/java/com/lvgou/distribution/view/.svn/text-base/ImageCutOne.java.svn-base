package com.lvgou.distribution.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lvgou.distribution.R;


public class ImageCutOne extends MyImageViewOne {

	private Paint paint_rect = new Paint();
	private final int mRadius = 200;
	private Xfermode cur_xfermode;
	private Rect r;
	private RectF rf;
	private boolean isToCutImage = false;

	public ImageCutOne(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		// paint.setColor(Color.BLUE);
		paint_rect.setColor(getResources().getColor(R.color.half_transparent_white));
		paint_rect.setAntiAlias(true);
		cur_xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return super.onTouchEvent(event);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		if (isToCutImage)
			return;

		if (rf == null || rf.isEmpty()) {
			r = new Rect(0, 0, getWidth(), getHeight());
			rf = new RectF(r);
		}
		// 锟斤拷imageview锟斤拷锟芥画锟诫背锟斤拷锟斤拷 圆锟斤拷
		int sc = canvas.saveLayer(rf, null, Canvas.MATRIX_SAVE_FLAG
				| Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
				| Canvas.FULL_COLOR_LAYER_SAVE_FLAG
				| Canvas.CLIP_TO_LAYER_SAVE_FLAG | Canvas.ALL_SAVE_FLAG);
		paint_rect.setColor(getResources().getColor(R.color.half_transparent_white));
		canvas.drawRect(r, paint_rect);
		paint_rect.setXfermode(cur_xfermode);
		paint_rect.setColor(Color.WHITE);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, paint_rect);
		canvas.restoreToCount(sc);
		paint_rect.setXfermode(null);
	}

	public Bitmap onClip() {
		// 锟斤拷取imageview锟斤拷bitmap
		// 为锟剿诧拷锟斤拷锟酵革拷锟斤拷谋锟斤拷锟斤拷锟斤拷锟斤拷锟剿拷锟斤拷锟絠mageview 锟矫伙拷删锟斤拷锟轿煌�然锟斤拷锟饺�
		Paint paint = new Paint();
		isToCutImage = true;
		invalidate();
		setDrawingCacheEnabled(true);
		Bitmap bitmap = getDrawingCache().copy(getDrawingCache().getConfig(),
				false);
		setDrawingCacheEnabled(false);
		// 锟斤拷锟斤拷锟斤拷要锟斤拷取锟斤拷位图 锟斤拷锟斤拷锟斤拷锟斤拷夜锟斤拷贫锟斤拷浅锟斤拷锟较�
		Bitmap bitmap2 = Bitmap.createBitmap(2 * mRadius, 2 * mRadius,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap2);

		// 锟斤拷实qq锟斤拷取锟斤拷锟斤拷片锟角凤拷锟轿碉拷 锟斤拷锟斤拷圆锟轿碉拷 锟斤拷锟斤拷锟揭诧拷蔚锟�锟斤拷锟斤拷锟斤拷锟劫达拷锟斤拷锟叫硷拷锟斤拷
//		canvas.drawRoundRect(new RectF(0, 0, 2 * mRadius, 2 * mRadius),
//				mRadius, mRadius, paint);
//		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		RectF dst = new RectF(-bitmap.getWidth() / 2 + mRadius, -getHeight()
				/ 2 + mRadius, bitmap.getWidth() - bitmap.getWidth() / 2
				+ mRadius, getHeight() - getHeight() / 2 + mRadius);
		canvas.drawBitmap(bitmap, null, dst, paint);
		isToCutImage = false;
		return bitmap2;
	}

}
