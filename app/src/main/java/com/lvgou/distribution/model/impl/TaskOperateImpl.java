package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.MyTaskModel;
import com.lvgou.distribution.model.TaskOperateModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/24.
 */

public class TaskOperateImpl implements TaskOperateModel {
    /**
     * 我的团币-领取(蜂圈域名)
     *
     * @param distributorid
     * @param type          操作类型（对应值在下方注解）
     * @param sign
     * @return
     */
    @Override
    public void taskOperate(String distributorid, int type, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.taskOperate(distributorid, type, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}