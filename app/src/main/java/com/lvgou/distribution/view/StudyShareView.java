package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/24.
 */

public interface StudyShareView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnStudyShareSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnStudyShareFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeStudyShareProgress();
}
