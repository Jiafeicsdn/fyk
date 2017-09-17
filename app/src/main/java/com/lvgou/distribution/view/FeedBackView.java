package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/30.
 */

public interface FeedBackView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnFeedBackSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnFeedBackFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeFeedBackProgress();
}
