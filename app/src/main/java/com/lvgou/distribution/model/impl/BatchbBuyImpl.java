package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.BatchbBuyModel;
import com.lvgou.distribution.model.CommentTeacherModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/7.
 */

public class BatchbBuyImpl implements BatchbBuyModel {
    /**
     * 系列课程-批量购买
     *
     * @param distributorid 讲课Id
     * @param teacherid     课程ID包含免费课程(1,2,3,4,5)
     * @param couponid      课程对应可使用听课券ID，没有则为0(1,2,0,3,0)
     * @param sign          签名
     * @return
     */
    @Override
    public void batchbBuy(String distributorid, String teacherid, String couponid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.batchbBuy(distributorid, teacherid, couponid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}