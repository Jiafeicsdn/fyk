package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.FindCircleTopModel;
import com.lvgou.distribution.model.UseFollowModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/17.
 */

public class UseFollowImpl implements UseFollowModel {
    /**
     * 关注好友
     *
     * @param distributorId 导游编号
     * @param friendId      被关注导游编号
     * @param sign          签名
     * @return
     */
    @Override
    public void useFollow(String distributorId, String friendId, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.useFollow(distributorId, friendId, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}