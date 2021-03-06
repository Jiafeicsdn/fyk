package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/27.
 */

public interface QianDaoView {

    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnQianDaoSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnQianDaoFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeQianDaoProgress();
}
