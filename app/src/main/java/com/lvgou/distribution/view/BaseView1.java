package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2017/3/4.
 */

public interface BaseView1 {
    /**
     * 显示操作进度
     */
    public void showProgress();
    /**
     * 关闭进度
     */
    public void closeProgress();

    /**
     * 成功回调
     */
    public void excuteErrSuccessCallBack(String s);
    /**
     * 失败回调
     */
    public void excuteErrFailedCallBack(String s);

}