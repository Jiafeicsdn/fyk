package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/14.
 */

public interface FindCircleTopView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnFindCircleTopSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnFindCircleTopFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeFindCircleTopProgress();
}
