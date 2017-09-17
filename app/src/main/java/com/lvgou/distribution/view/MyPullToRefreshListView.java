package com.lvgou.distribution.view;

import android.content.Context;
import android.util.AttributeSet;

import com.lvgou.distribution.refresh.PullToRefreshListView;

/**
 * Created by Snow on 2016/7/25 0025.
 */
public class MyPullToRefreshListView extends PullToRefreshListView {

    public MyPullToRefreshListView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public MyPullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPullToRefreshListView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
