package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.TeacherSeatModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Snow on 2016/9/22.
 */
public class TeacherSeatImpl implements TeacherSeatModel {

    /**
     * 讲师列表
     *
     * @param pageindex
     * @param key
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getTeacherSeatData(String distributorid, String pageindex, String key, String sign, ICallBackListener callBackListener) {
        IServiceAPI iServiceAPI = Api.getInstance().getGankService();
        iServiceAPI.getTeacherSeatList(distributorid, pageindex, key, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));

    }

    /**
     * 讲师申请状态
     *
     * @param distributorid
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getSatte(String distributorid, String sign, ICallBackListener callBackListener) {
        IServiceAPI iServiceAPI = Api.getInstance().getGankService();
        iServiceAPI.getApplyState(distributorid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
