package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.DistributorHomeModel;
import com.lvgou.distribution.model.HomeStudyListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/13.
 */

public class HomeStudyListImpl implements HomeStudyListModel {
    /**
     * 个人主页课程列表
     *
     * @param distributorid 待查询的导游编号
     * @param currPage      当前页
     * @param sign          签名
     * @return
     */
    @Override
    public void homeStudyList(String distributorid, int currPage, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.homeStudyList(distributorid, currPage, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}