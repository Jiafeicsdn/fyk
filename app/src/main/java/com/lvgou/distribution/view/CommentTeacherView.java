package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/6.
 */

public interface CommentTeacherView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnCommentTeacherSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnCommentTeacherFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeCommentTeacherProgress();
}
