package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.bean.ApplyTeaacherBean;
import com.lvgou.distribution.model.ApplyTeacherModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Snow on 2016/9/24.
 */
public class ApplyTeacherImpl implements ApplyTeacherModel {


    /**
     * 申请讲师
     * @param applyTeaacherBean
     */
    @Override
    public void doApplyTeacher(ApplyTeaacherBean applyTeaacherBean, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.applyTeacher(applyTeaacherBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
