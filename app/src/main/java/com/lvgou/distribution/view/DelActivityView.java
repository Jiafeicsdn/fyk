package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/30.
 */

public interface DelActivityView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnDelActivitySuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnDelActivityFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeDelActivityProgress();
}
