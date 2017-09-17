package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.ActivityEditloadModel;
import com.lvgou.distribution.model.CollectionListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/26.
 */

public class ActivityEditloadImpl implements ActivityEditloadModel {
    /**
     * 活动编辑加载
     *
     * @param distributorid
     * @param activityid    活动ID
     * @param sign
     * @return
     */
    @Override
    public void activityEditload(String distributorid, String activityid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.activityEditload(distributorid, activityid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
