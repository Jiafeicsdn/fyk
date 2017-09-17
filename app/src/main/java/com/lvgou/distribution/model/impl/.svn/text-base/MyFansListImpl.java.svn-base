package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.MyFansListModel;
import com.lvgou.distribution.model.MyFollowListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/26.
 */

public class MyFansListImpl implements MyFansListModel {

    /**
     * 个人首页-粉丝列表
     *
     * @param distributorId
     * @param seeDistributorId
     * @param currPage
     * @param sign
     * @return
     */
    @Override
    public void myFansList(String distributorId, String seeDistributorId,int currPage, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.myFansList(distributorId,seeDistributorId, currPage, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
