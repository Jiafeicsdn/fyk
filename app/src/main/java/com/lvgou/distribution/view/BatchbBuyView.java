package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/7.
 */

public interface BatchbBuyView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnBatchbBuySuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnBatchbBuyFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeBatchbBuyProgress();
}
