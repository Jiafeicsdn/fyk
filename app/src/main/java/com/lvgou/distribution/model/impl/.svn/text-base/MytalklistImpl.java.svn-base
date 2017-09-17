package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.MytalklistModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/22.
 */
public class MytalklistImpl implements MytalklistModel{
    @Override
    public void mytalklist(String distributorid, String seeDistributorId, int currPage, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        serviceAPI.mytalklist(distributorid, seeDistributorId,currPage,sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }
    @Override
    public void circleZan(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        serviceAPI.circleZan(distributorid, talkId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void circleUnZan(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        serviceAPI.circleUnZan(distributorid, talkId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }
    @Override
    public void talkdel(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        serviceAPI.talkdel(distributorid, talkId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }
    @Override
    public void distributormain(String distributorid, String seeDistributorId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        serviceAPI.distributormain(distributorid, seeDistributorId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }
    @Override
    public void circleUnfollow(String distributorid, String friendId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        serviceAPI.circleUnFollow(distributorid, friendId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void circlefollow(String distributorid, String friendId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        serviceAPI.circleFollow(distributorid, friendId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }
}
