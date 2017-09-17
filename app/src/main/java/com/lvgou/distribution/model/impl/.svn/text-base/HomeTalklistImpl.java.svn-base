package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.HomeStudyListModel;
import com.lvgou.distribution.model.HomeTalklistModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/13.
 */

public class HomeTalklistImpl implements HomeTalklistModel {
    /**
     * 个人首页-蜂文列表
     *
     * @param distributorId    导游编号
     * @param seeDistributorId 查看导游编号
     * @param currPage         当前页
     * @param sign             签名
     * @return
     */
    @Override
    public void homeTalklist(String distributorId, String seeDistributorId, int currPage, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.homeTalklist(distributorId, seeDistributorId, currPage, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}