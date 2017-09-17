package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.ApplyUserModel;
import com.lvgou.distribution.model.DelActCommentModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ApplyUserImpl implements ApplyUserModel {


    /**
     * 活动-报名人员
     *
     * @param activityid
     * @param pageindex
     * @param keyword
     * @param sign
     * @return
     */
    @Override
    public void applyUser(String activityid, int pageindex, String keyword, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.applyUser(activityid, pageindex, keyword, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}