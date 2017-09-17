package com.lvgou.distribution.viewholder;

import android.view.View;

/**
 * Created by Administrator on 2016/8/18.
 */
public abstract class BaseHolder<T> {
    private final View contentView;
    private T t;

    public BaseHolder() {
        contentView = initView();
        contentView.setTag(this);
    }

    /**
     * 返回holder 维护的view对象
     *
     * @return
     */
    public View getContentView() {
        return contentView;
    }

    public void setData(T t) {
        this.t = t;
        refreshView(t);
    }

    /**
     * 1.  完成view对象的创建
     * 2. findviewByid
     *
     * @return
     */
    public abstract View initView();

    /**
     * 真正的设置数据
     */
    public abstract void refreshView(T t);
}
