package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.RecommentBeeModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Snow on 2016/9/21.
 */
public class RecommentBeeImpl implements RecommentBeeModel{

    /**
     * 蜂优推荐
     * @param distributorid
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getRecommentBee(String distributorid, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getRecommentBee(distributorid,sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}

