package com.lvgou.distribution.inter;

/**
 * Created by Snow on 2016/4/8
 * 点击大图显示 回调接口
 */
public interface OnShowImageClickListener<T> {
    public void onShowImageClickListener(T itemData, int postion, int num);
}
