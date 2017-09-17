package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/1.
 */

public interface UserInviteView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnUserInviteSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnUserInviteFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeUserInviteProgress();
}
