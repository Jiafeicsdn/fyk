package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.DynamicDetailsModel;
import com.lvgou.distribution.model.HomePageModel;
import com.lvgou.distribution.presenter.BasePresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/15.
 */
public class DynamicDetailsImpl implements DynamicDetailsModel {
    BasePresenter basePresenter;

    public DynamicDetailsImpl(BasePresenter basePresenter) {
        this.basePresenter = basePresenter;
    }

    @Override
    public void talkisnormal(String talkId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.talkisnormal(talkId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }

    @Override
    public void talkcommentlist(String talkId, String prePageLastDataObjectId, int currPage, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.talkcommentlist(talkId, prePageLastDataObjectId, currPage, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }

    @Override
    public void commenttalk(String distributorid, String talkId, String commentId, String content,int tuanbi, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.commenttalk(distributorid, talkId, commentId, content, tuanbi, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }

    @Override
    public void zhuanFa(String distributorid, String talkId, String content, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.zhuangfa(distributorid, talkId, content, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }

    @Override
    public void getTalkDetial(String distributorid, String talkId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.getTalkDetail(distributorid, talkId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }

    @Override
    public void recommenttalkcontent(String talkId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.recommenttalkcontent(talkId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }

    @Override
    public void talkcommentdel(String distributorId, String talkCommentId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.talkcommentdel(distributorId, talkCommentId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }
    @Override
    public void talkcollection(String distributorId, String talkId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.talkcollection(distributorId, talkId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }
    @Override
    public void talkuncollection(String distributorId, String talkId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        basePresenter.mCompositeSubscription.add(serviceAPI.talkuncollection(distributorId, talkId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener)));
    }

}
