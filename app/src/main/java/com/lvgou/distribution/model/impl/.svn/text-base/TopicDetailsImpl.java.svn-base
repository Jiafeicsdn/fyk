package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.TopicDetailsModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/24.
 */
public class TopicDetailsImpl implements TopicDetailsModel {
    @Override
    public void topicdetail(String topicId, String sign, ICallBackListener callBackListener) {
        IServiceAPI iServiceAPI = Api.getInstance().getGankService();
        iServiceAPI.topicdetail(topicId, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

    @Override
    public void commenttopic(String distributorId, String topicId, String commentId, String content, String sign, ICallBackListener callBackListener) {
        IServiceAPI iServiceAPI = Api.getInstance().getGankService();
        iServiceAPI.commenttopic(distributorId, topicId, commentId, content, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

    @Override
    public void topiccommentlist(String topicId, String prePageLastDataObjectId, int currPage, String sign, ICallBackListener callBackListener) {
        IServiceAPI iServiceAPI = Api.getInstance().getGankService();
        iServiceAPI.topiccommentlist(topicId, prePageLastDataObjectId, currPage, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
    @Override
    public void topiccommentdel(String distributorId, String talkCommentId, String sign, ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.topiccommentdel(distributorId, talkCommentId, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }
}
