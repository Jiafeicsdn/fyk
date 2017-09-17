package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/23.
 */

public interface ActivityDetailView {
    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnActivityDetailSuccCallBack(String state, String respanse);

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnActivityDetailFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeActivityDetailProgress();
}
