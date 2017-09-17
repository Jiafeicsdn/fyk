package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.entity.ApplyClassEntity;
import com.lvgou.distribution.model.PersonalModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/11.
 */
public class PersonalImpl implements PersonalModel {


    /**
     * 获取个人中心数据
     *
     * @param distributorid
     * @param sign
     * @param icallBackListener
     */
    @Override
    public void getPersonalData(String distributorid, String sign, ICallBackListener icallBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.getPersonalData(distributorid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(icallBackListener));
    }

    /**
     * 上传头像
     *
     * @param distributorid
     * @param sign
     * @param picurl
     * @param icallBackListener
     */
    @Override
    public void upLoadHead(String distributorid, String sign, String picurl, ICallBackListener icallBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.uploadHead(distributorid, picurl, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(icallBackListener));
    }


    /**
     * 获取未读数量
     *
     * @param distributorid
     * @param getNewestDistributorId
     * @param sign
     * @param icallBackListener
     */
    @Override
    public void getMessageNum(String distributorid, String getNewestDistributorId, String sign, ICallBackListener icallBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.getPersonalMessageCount(distributorid, getNewestDistributorId, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(icallBackListener));
    }

    /**
     * 获取订单列表
     *
     * @param distributorid
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getOrderNum(String distributorid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.getOrderNum(distributorid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

    /**
     * 申请开课
     *
     * @param applyClassEntity
     * @param callBackListener
     */
    @Override
    public void applyClass(ApplyClassEntity applyClassEntity, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.applyClass(applyClassEntity)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }


    /**
     * 类型选择
     *
     * @param distributorid
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getHotTag(String distributorid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.getHotTag(distributorid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
