package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.ApplyForMeModel;
import com.lvgou.distribution.model.CouponListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ApplyForMeImpl implements ApplyForMeModel {
    /**
     * 活动列表-我报名的活动
     *
     * @param distributorid 导游ID
     * @param type          类型 0=全部，1=进行中，2=已结束
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @Override
    public void applyForMeDatas(String distributorid, int type, int pageindex, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.applyForMeDatas(distributorid, type, pageindex, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}