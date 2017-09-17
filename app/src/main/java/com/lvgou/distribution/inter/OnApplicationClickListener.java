package com.lvgou.distribution.inter;

import android.widget.TextView;

/**
 * Created by Snow on 2016/5/3 0003.
 */
public interface OnApplicationClickListener<T> {
    public void onApplicationClickListener(T itemdata, TextView textView,int num);
}
