package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/13.
 */

public interface DistributorHomeView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnDistributorHomeSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnDistributorHomeFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeDistributorHomeProgress();
}
