package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/28.
 */

public interface CheckLevelUpView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnCheckLevelUpSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnCheckLevelUpFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeCheckLevelUpProgress();
}
