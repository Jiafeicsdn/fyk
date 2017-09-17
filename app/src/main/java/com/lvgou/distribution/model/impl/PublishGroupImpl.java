package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.bean.PublishGroupBean;
import com.lvgou.distribution.entity.PublishGroupEditBean;
import com.lvgou.distribution.model.PublishGroupModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Snow on 2016/9/26.
 */
public class PublishGroupImpl implements PublishGroupModel {


    /**
     * 发布派团
     *
     * @param publishGroupBean
     * @param callBackListener
     */
    @Override
    public void doPublishGroup(PublishGroupBean publishGroupBean, ICallBackListener callBackListener) {
        IServiceAPI iServiceAPI = Api.getInstance().getGankService();
        iServiceAPI.publishGroup(publishGroupBean)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

    /**
     * 编辑派团
     *
     * @param publishGroupBean
     * @param callBackListener
     */
    @Override
    public void editPublishGroup(PublishGroupEditBean publishGroupBean, ICallBackListener callBackListener) {
        IServiceAPI iServiceAPI = Api.getInstance().getGankService();
        iServiceAPI.editPublishGroup(publishGroupBean)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

    /**
     * 获取详情
     *
     * @param distributorid
     * @param seekid
     * @param sign
     * @param iCallBackListener
     */
    @Override
    public void getPublishGorupInfo(String distributorid, String seekid, String sign, ICallBackListener iCallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getGroupDetial(distributorid, seekid, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(iCallBackListener));
    }
}
