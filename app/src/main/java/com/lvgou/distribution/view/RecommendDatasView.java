package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/10.
 */

public interface RecommendDatasView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnRecommendDatasSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnRecommendDatasFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeProgress();
}
