package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.MyTaskListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Snow on 2016/10/17.
 */
public class MyTaskListImpl implements MyTaskListModel {

    /**
     * 获取列表
     *
     * @param distributorid
     * @param sign
     * @param iCallBackListener
     */
    @Override
    public void getMyTaskList(String distributorid, String sign, ICallBackListener iCallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getMytasklist(distributorid, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(iCallBackListener));
    }

    /**
     * 操作列表
     *
     * @param distributorid
     * @param type
     * @param sign
     * @param iCallBackListener
     */
    @Override
    public void doMyTaskOperate(String distributorid, String type, String sign, ICallBackListener iCallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.doMytasklist(distributorid, type, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(iCallBackListener));
    }



}
