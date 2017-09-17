package com.lvgou.distribution.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/3/24.
 */

public class MyHalfImageView extends ImageView {
    public MyHalfImageView(Context context) {
        this(context,null);
    }

    public MyHalfImageView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyHalfImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private Paint paint;
    private void init(){
        paint=new Paint();
        paint.setColor(Color.parseColor("#80000000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(null,30f,120f,false,paint);
        canvas.save();
    }
}
