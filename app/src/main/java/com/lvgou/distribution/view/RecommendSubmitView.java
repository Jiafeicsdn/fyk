package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/28.
 */

public interface RecommendSubmitView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnRecommendSubmitSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnRecommendSubmitFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeRecommendSubmitProgress();
}
