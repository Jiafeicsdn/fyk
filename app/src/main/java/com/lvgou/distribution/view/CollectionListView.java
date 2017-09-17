package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/4/24.
 */

public interface CollectionListView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnCollectionListSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnCollectionListFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeCollectionListProgress();
}
