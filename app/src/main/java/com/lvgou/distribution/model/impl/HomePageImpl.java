package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.HomePageModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/15.
 */
public class HomePageImpl implements HomePageModel {
    @Override
    public void getIndex(String distributorid, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getIndex(distributorid, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void getFindcircle(String distributorid, String keyword, String tagId, String prePageLastDataObjectId, int currPage, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getFindCircle(distributorid, keyword, tagId, prePageLastDataObjectId, currPage, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void unreadcount(String distributorid, int getNewestDistributorId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.unreadcount(distributorid, getNewestDistributorId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void circleZan(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.circleZan(distributorid, talkId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void circleUnZan(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.circleUnZan(distributorid, talkId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void circleUnfollow(String distributorid, String friendId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.circleUnFollow(distributorid, friendId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void circlefollow(String distributorid, String friendId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.circleFollow(distributorid, friendId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void findtagandtopic(String distributorid, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getTagTopic(distributorid, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void followcircle(String distributorid, String prePageLastDataObjectId, int currPage, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.followcircle(distributorid, prePageLastDataObjectId, currPage, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

    @Override
    public void mayknowperson(String distributorid, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.mayknowperson(distributorid, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }
}
