package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.ApplyForStudyModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ApplyForStudyImpl implements ApplyForStudyModel {
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
    @Override
    public void applyForStudy(String distributorid, String theme, String starttime, String label, String crowd, String themeinfo, String zbtype, String apply, String look, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.applyForStudy(distributorid, theme, starttime, label, crowd, themeinfo, zbtype, apply, look, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
