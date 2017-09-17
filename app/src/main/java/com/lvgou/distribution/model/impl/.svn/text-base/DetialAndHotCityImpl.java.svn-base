package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.DetialAndHotCityModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/28.
 */
public class DetialAndHotCityImpl implements DetialAndHotCityModel {

    /**
     * 获取派团信息、热门城市接口
     *
     * @param distributorid
     * @param seekid           派团ID：当seekid>0时返回派团数据；seekid=0则不返回
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getGroupDetial(String distributorid, String seekid, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getGroupDetial(distributorid, seekid, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }


    /**
     * 报名列表
     *
     * @param seekid
     * @param pageindex
     * @param sign
     * @param icallBackListener
     */
    @Override
    public void getSignUpList(String seekid, String pageindex, String sign, ICallBackListener icallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getSignUp(seekid, pageindex, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(icallBackListener));

    }

    /**
     * 取消派团
     *
     * @param distributorid
     * @param seekid
     * @param sign
     * @param callBackListener
     */
    @Override
    public void canclePublishGroup(String distributorid, String seekid, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.canclePublishGroup(distributorid, seekid, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

    /**
     * 删除派团
     *
     * @param distributorid
     * @param seekid
     * @param sign
     * @param callBackListener
     */
    @Override
    public void deletePublishGroup(String distributorid, String seekid, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.deletePublishGroup(distributorid, seekid, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

    /**
     * 报名 派团
     *
     * @param distributorid
     * @param seekid
     * @param sign
     * @param callBackListener
     */
    public void doSignUp(String distributorid, String seekid, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.doSignUp(distributorid, seekid, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

}
