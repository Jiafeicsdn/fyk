package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.BaomingTeacherModel;
import com.lvgou.distribution.model.TopicListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/19.
 */

public class BaomingTeacherImpl implements BaomingTeacherModel {
    /**
     * 名师讲堂-报名
     *
     * @param distributorid 导游ID
     * @param id            课程ID
     * @param couponid      听课券ID(导游领取后的记录ID)
     * @param sign          签名
     * @return
     */
    @Override
    public void applyTeacher(String distributorid, String id, String couponid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.applyTeacher(distributorid, id, couponid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}