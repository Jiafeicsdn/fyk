package com.lvgou.distribution.view;

/**
 * Created by Snow on 2016/9/12.
 */
public interface FamousTeacherDetiaiView {

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


}
