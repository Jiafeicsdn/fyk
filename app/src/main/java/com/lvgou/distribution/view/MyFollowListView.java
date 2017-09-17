package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/26.
 */

public interface MyFollowListView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnMyFollowListSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnMyFollowListFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeMyFollowListProgress();
}
