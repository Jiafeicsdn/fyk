package com.lvgou.distribution.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/6/3.
 */

public class GoodsGridView extends GridView {
    public GoodsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GoodsGridView(Context context) {
        super(context);
    }

    public GoodsGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);

        //int wexpandSpec=widthMeasureSpec+2*16;
        int hexpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, hexpandSpec);
    }
}
