package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.CancelApplyActModel;
import com.lvgou.distribution.model.TeacherDetailModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/5.
 */

public class TeacherDetailImpl implements TeacherDetailModel {
    /**
     * 名师课堂详情(4.0)
     *
     * @param distributorid 导游ID
     * @param id            课堂ID
     * @param sign          签名
     * @return
     */
    @Override
    public void teacherDetail(String distributorid, String id, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.teacherDetail(distributorid, id, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}