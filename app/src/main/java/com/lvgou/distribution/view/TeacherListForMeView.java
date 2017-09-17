package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/24.
 */

public interface TeacherListForMeView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnTeacherListForMeSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnTeacherListForMeFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeTeacherListForMeProgress();
}
