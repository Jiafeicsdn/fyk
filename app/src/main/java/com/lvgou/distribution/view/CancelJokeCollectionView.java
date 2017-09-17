package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/31.
 */

public interface CancelJokeCollectionView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnCancelJokeCollectionSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnCancelJokeCollectionFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeCancelJokeCollectionProgress();
}
