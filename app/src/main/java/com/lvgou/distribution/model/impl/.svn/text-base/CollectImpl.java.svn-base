package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.CollectModel;
import com.lvgou.distribution.model.DynamicDetailsModel;
import com.lvgou.distribution.presenter.BasePresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/15.
 */
public class CollectImpl implements CollectModel {
    BasePresenter basePresenter;

    public CollectImpl(BasePresenter basePresenter) {
        this.basePresenter = basePresenter;
    }

    @Override
    public void talkcollectionlist(String distributorid, String prePageLastDataObjectId, int currPage, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.talkcollectionlist(distributorid, prePageLastDataObjectId, currPage, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }

    @Override
    public void sysmsglist(String distributorid, int currPage, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.sysmsglist(distributorid, currPage, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }
}
