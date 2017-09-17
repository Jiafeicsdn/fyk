package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/13.
 */

public interface FengwenZanView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnFengwenZanSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnFengwenZanFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeFengwenZanProgress();
}
