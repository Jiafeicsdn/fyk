package com.lvgou.distribution.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Snow on 2016/5/16 0016.
 */
public class MyViewPage extends ViewPager {
    private boolean willIntercept = true;

    public MyViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (willIntercept) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }

    }


    public void setTouchIntercept(boolean value) {
        willIntercept = value;
    }
}
