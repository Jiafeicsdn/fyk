package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/5/5.
 */

public interface GetUserInfoView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnGetUserInfoSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnGetUserInfoFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeGetUserInfoProgress();
}
