package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/28.
 */

public interface DelImgView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnDelImgSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnDelImgFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeDelImgProgress();
}
