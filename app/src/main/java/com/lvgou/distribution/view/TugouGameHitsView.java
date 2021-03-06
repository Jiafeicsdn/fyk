package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/1.
 */

public interface TugouGameHitsView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnTugouGameHitsSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnTugouGameHitsFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeTugouGameHitsProgress();
}
