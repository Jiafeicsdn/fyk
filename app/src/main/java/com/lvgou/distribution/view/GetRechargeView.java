package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/5/5.
 */

public interface GetRechargeView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnGetRechargeSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnGetRechargeFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeGetRechargeProgress();
}
