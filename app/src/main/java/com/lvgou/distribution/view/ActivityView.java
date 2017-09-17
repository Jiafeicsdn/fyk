package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/13.
 */

public interface ActivityView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnActivitySuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnActivityFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeProgress();
}
