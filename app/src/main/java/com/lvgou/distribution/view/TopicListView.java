package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/17.
 */

public interface TopicListView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnTopicListSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnTopicListFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeTopicListProgress();
}
