package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.PersonalInfoModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/14.
 */
public class PersonalInfoImpl implements PersonalInfoModel {


    /**
     * 获取个人信息
     *
     * @param distributorid
     * @param seekid
     * @param applyid
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getPersonalInfo(String distributorid, String seekid, String applyid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.getPeronalInfo(distributorid, seekid, applyid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

    /**
     * 录用 导游
     *
     * @param distributorid
     * @param seekid
     * @param applyid
     * @param sign
     * @param callBackListener
     */
    @Override
    public void doEmployment(String distributorid, String seekid, String applyid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.doEmployment(distributorid, seekid, applyid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
