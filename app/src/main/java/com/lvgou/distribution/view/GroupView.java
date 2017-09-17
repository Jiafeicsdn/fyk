package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2016/9/30.
 */
public interface GroupView {

    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    void OnFamousSuccCallBack(String state, String respanse);


    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    void OnFamousFialCallBack(String state, String respanse);

    /**
     * 关闭弹窗
     */
    void closeProgress();
    void showProgress();
}
