package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/23.
 */

public interface CommentListView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnCommentListSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnCommentListFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeCommentListProgress();
}
