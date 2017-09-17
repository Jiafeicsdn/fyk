package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/30.
 */

public interface ActivityForMeView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnActivityForMeSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnActivityForMeFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeActivityForMeProgress();
}
