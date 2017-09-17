package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface ApplyForStudyView {

    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnApplyForStudySuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnApplyForStudyFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeApplyForStudyProgress();
}
