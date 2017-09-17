package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/24.
 */

public interface TalkCollectionListView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnTalkCollectionListSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnTalkCollectionListFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeTalkCollectionListProgress();
}
