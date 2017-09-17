package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.ActivityApplyModel;
import com.lvgou.distribution.model.CommentListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/23.
 */

public class CommentListImpl implements CommentListModel {
    /**
     * 活动评论-列表
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @Override
    public void commentList(String distributorid, String activityid, int pageindex, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.commentList(distributorid, activityid, pageindex, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}