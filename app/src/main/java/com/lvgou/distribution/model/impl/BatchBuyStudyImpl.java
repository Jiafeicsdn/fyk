package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.BatchBuyStudyModel;
import com.lvgou.distribution.model.TeacherCommentListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/6.
 */

public class BatchBuyStudyImpl implements BatchBuyStudyModel {
    /**
     * 系列课程详情
     *
     * @param distributorid 讲课Id
     * @param seriesid      系列ID
     * @param sign          签名
     * @return
     */
    @Override
    public void batchBuyStudy(String distributorid, String seriesid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.batchBuyStudy(distributorid, seriesid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}