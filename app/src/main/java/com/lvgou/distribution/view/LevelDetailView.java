package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/11.
 */

public interface LevelDetailView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnLevelDetailSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnLevelDetailFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeLevelDetailProgress();
}
