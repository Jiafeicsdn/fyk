package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.MerchantModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/5.
 */
public class MerchantImpl implements MerchantModel{
    @Override
    public void reportsellerindex(String distributorid, String reportid, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        serviceAPI.reportsellerindex(distributorid, reportid, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }
}
