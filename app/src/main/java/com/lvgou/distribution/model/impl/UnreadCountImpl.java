package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.TeacherDownloadListModel;
import com.lvgou.distribution.model.UnreadCountModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/21.
 */

public class UnreadCountImpl implements UnreadCountModel {
    /**
     * 未读消息数
     * @param distributorid
     * @param getNewestDistributorId  	是否获取最新评论或者点赞的用户编号 1=获取 其余=不获取
     * @param sign
     * @return
     */
    @Override
    public void unreadCount(String distributorid,int getNewestDistributorId, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.unreadCount(distributorid,getNewestDistributorId, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}