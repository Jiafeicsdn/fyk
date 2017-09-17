package com.lvgou.distribution.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * @author xiansenxuan
 *	齿轮效果
 */
public class SawtoothView extends View {
	private int width;
	private Paint paint;

	public SawtoothView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public SawtoothView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SawtoothView(Context context) {
		super(context);
		init();
	}

	private void init() {
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		width = wm.getDefaultDisplay().getWidth()+150;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

	}
	
	public void setColor(int colorId){
		paint.setColor(colorId);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Path pt = new Path();
		for (int i = 0; i < width / 10; i++) {
			pt.moveTo(11 + (22 * i), 0);
			pt.lineTo(22 + (22 * i), 10);
			pt.lineTo(0 + 22 * i, 10);
			canvas.drawPath(pt, paint);
		}
		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(width, 10);
	}

}
