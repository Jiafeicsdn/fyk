package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/5.
 */

public interface TeacherDetailView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnTeacherDetailSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnTeacherDetailFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeTeacherDetailProgress();
}
