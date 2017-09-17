package com.lvgou.distribution.view;

/**
 * Created by S on 2016/9/22.
 */
public interface TeacherSeatView {

    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnRequestSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnRequestFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeProgress();
}

