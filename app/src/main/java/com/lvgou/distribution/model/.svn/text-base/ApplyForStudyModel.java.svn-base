package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface ApplyForStudyModel {
    /**
     * 申请开课-提交
     *
     * @param distributorid 导游ID
     * @param theme         主题
     * @param starttime     直播时间(2017-3-13 18:30)
     * @param label         课程分类(103001,103002)
     * @param crowd         适合人群
     * @param themeinfo     主题简介
     * @param zbtype        开课形式 1=直播，2=录播
     * @param apply         报名团币 可为0
     * @param look          查看团币 可为0
     * @param sign          签名
     * @return
     */
    void applyForStudy(String distributorid, String theme, String starttime, String label, String crowd, String themeinfo, String zbtype, String apply, String look, String sign, ICallBackListener callBackListener);
}
