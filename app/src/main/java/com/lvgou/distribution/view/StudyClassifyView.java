package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/13.
 */

public interface StudyClassifyView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnStudyClassifySuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnStudyClassifyFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeProgress();
}
