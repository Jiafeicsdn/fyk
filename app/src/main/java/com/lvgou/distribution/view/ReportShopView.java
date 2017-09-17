package com.lvgou.distribution.view;

/**
 * Created by Administrator on 2016/9/13.
 */
public interface ReportShopView {
    /**
     * 获取列表成功回调
     *
     * @param response
     */
    public void applcationSuccCallBck(String response);

    /**
     * 获取列表失败回调
     *
     * @param response
     */
    public void applcationFailCallBck(String response);


    /**
     * 关闭进度
     */
    public void closeProgress();
}
