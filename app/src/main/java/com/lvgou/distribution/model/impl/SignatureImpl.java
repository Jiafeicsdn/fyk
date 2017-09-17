package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.SignatureModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/15.
 */
public class SignatureImpl implements SignatureModel{
    @Override
    public void updateUserSign(String distributorid, String usersign, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        serviceAPI.updateusersign(distributorid, usersign,sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
