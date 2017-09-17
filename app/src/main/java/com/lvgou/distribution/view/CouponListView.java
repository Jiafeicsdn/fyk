package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/29.
 */

public interface CouponListView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnCouponListSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnCouponListFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeCouponListProgress();
}
