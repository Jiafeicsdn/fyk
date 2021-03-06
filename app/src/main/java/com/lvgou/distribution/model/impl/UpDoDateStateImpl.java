package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.LookTeacherModel;
import com.lvgou.distribution.model.UpDoDateStateModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/19.
 */

public class UpDoDateStateImpl implements UpDoDateStateModel {
    /**
     * 更改学习状态
     *
     * @param distributorid
     * @param studyid
     * @param sign
     * @return
     */
    @Override
    public void upDoDateState(String distributorid, String studyid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.upDoDateState(distributorid, studyid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}