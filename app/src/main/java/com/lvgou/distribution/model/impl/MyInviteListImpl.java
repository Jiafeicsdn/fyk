package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.MyInviteListModel;
import com.lvgou.distribution.model.TeacherListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/12.
 */

public class MyInviteListImpl implements MyInviteListModel {
    /**
     * 邀请好友-邀请记录
     *
     * @param distributorid 讲课Id
     * @param type          类型 2=待审核，3=邀请成功
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @Override
    public void myInviteList(String distributorid,int type, int pageindex, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.myInviteList(distributorid,type, pageindex, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}