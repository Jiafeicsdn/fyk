package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/16.
 */

public interface ActivitySubmitModel {
    /**
     * 发布活动
     *
     * @param distributorid 导游ID
     * @param picurl        活动图片路径
     * @param title         活动标题
     * @param starttime     活动开始时间 格式:2017-3-10 10:00
     * @param endtime       活动结束时间 格式:2017-3-10 10:00
     * @param countrypath   城市节点
     * @param address       详细地址
     * @param maxpeople     活动上限人数
     * @param info          活动简介
     * @param sign          签名
     */
    void activitySubmitDatas(String distributorid,String activityid, String picurl, String title, String starttime, String endtime, String countrypath, String address, int maxpeople, String info, String sign, ICallBackListener callBackListener);
}
