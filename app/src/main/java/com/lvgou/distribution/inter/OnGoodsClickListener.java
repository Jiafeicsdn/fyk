package com.lvgou.distribution.inter;

import android.view.View;

/**
 * Created by Snow on 2016/3/28 0028.
 */
public interface OnGoodsClickListener<T> {
    public void onGoodsClick(T itemData, int postion, View view, int num);
}
