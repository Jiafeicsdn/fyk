package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface StudentFragmentDashangView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnStudentFragmentDashangSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnStudentFragmentDashangFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeProgress();
}
