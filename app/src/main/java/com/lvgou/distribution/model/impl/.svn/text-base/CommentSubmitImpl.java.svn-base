package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.CommentSubmitModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/23.
 */

public class CommentSubmitImpl implements CommentSubmitModel {
    /**
     * 活动评论-发布
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param comment       评论内容
     * @param sign          签名
     * @return
     */
    @Override
    public void commentSubmit(String distributorid, String activityid, String comment, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.commentSubmit(distributorid, activityid, comment, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}