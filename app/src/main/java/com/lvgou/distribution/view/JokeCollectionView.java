package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/31.
 */

public interface JokeCollectionView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnJokeCollectionSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnJokeCollectionFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeJokeCollectionProgress();
}
