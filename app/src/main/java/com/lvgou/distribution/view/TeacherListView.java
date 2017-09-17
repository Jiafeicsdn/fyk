package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/12.
 */

public interface TeacherListView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnTeacherListSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnTeacherListFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeTeacherListProgress();
}
