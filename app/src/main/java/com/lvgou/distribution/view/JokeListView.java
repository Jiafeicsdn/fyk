package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/31.
 */

public interface JokeListView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnJokeListSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnJokeListFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeJokeListProgress();
}
