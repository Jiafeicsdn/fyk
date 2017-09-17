package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/14.
 */

public interface StudySearchloadView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnStudySearchloadSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnStudySearchloadFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeStudySearchloadProgress();
}
