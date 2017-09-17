package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/1.
 */

public interface TugouGameView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnTugouGameSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnTugouGameFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeTugouGameProgress();
}
