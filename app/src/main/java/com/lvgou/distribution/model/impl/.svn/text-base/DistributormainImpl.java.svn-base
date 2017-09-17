package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.DistributormainModel;
import com.lvgou.distribution.presenter.BasePresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/22.
 */
public class DistributormainImpl implements DistributormainModel{
    BasePresenter basePresenter;
    public DistributormainImpl(BasePresenter basePresenter){
        this.basePresenter=basePresenter;
    }
    @Override
    public void distributormain(String distributorid, String seeDistributorId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.distributormain(distributorid, seeDistributorId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }

    @Override
    public void circleUnfollow(String distributorid, String friendId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.circleUnFollow(distributorid, friendId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }

    @Override
    public void circlefollow(String distributorid, String friendId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.circleFollow(distributorid, friendId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }
}
