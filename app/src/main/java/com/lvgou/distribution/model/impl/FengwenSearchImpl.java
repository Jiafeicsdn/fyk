package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.FengwenSearchModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/19.
 */
public class FengwenSearchImpl implements FengwenSearchModel {

    /**
     * 蜂文列表
     * @param distributorId
     * @param keyword
     * @param tagId
     * @param currPage
     * @param sign
     * @param iCallBackListener
     */
    @Override
    public void getFengwenList(String distributorId, String keyword, String tagId, String currPage, String sign, ICallBackListener iCallBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.getFengwenSearch(distributorId, keyword, tagId, currPage, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(iCallBackListener));

    }

    /**
     * 用户列表
     * @param distributorId
     * @param keyword
     * @param currPage
     * @param sign
     * @param iCallBackListener
     */
    @Override
    public void getUserList(String distributorId, String keyword, String currPage, String sign, ICallBackListener iCallBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.getUserSearch(distributorId, keyword, currPage, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(iCallBackListener));
    }

    /**
     * 点赞
     * @param distributorId
     * @param talkId
     * @param sign
     * @param callBackListener
     */
    @Override
    public void doZan(String distributorId, String talkId, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.circleZan(distributorId, talkId, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

     /**
     * 取消点赞
     * @param distributorId
     * @param talkId
     * @param sign
     * @param callBackListener
     */
    @Override
    public void cancleZan(String distributorId, String talkId, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.circleUnZan(distributorId, talkId, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

    /**
     * 关注
     * @param distributorId
     * @param friendId
     * @param sign
     * @param callBackListener
     */
    @Override
    public void doFollow(String distributorId, String friendId, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.circleFollow(distributorId,friendId, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

    /**
     * 取消关注
     * @param distributorId
     * @param friendId
     * @param sign
     * @param callBackListener
     */
    @Override
    public void cancleFollow(String distributorId, String friendId, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.circleUnFollow(distributorId, friendId, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }


}
