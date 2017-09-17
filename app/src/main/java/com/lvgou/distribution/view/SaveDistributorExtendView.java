package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/10.
 */

public interface SaveDistributorExtendView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnSaveDistributorExtendSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnSaveDistributorExtendFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeSaveDistributorExtendProgress();
}
