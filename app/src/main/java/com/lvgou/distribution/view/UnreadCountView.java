package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/21.
 */

public interface UnreadCountView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnUnreadCountSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnUnreadCountFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeUnreadCountProgress();
}
