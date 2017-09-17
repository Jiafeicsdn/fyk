package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.PublishTalkModel;
import com.lvgou.distribution.model.TopicListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/18.
 */

public class PublishTalkImpl implements PublishTalkModel {
    /**
     * 发布蜂文
     *
     * @param distributorId 导游编号
     * @param tagIds        热门标签Ids（多个用英文竖线分割）
     * @param content       蜂文内容
     * @param picUrls       蜂文图片集合（多个用英文竖线分割）例如：图片地址1&图片小图地址1&图片宽度1&图片高度1
     * @param location      当前位置
     * @param topicId       话题Id
     * @param sign
     * @return
     */
    @Override
    public void publishTalk(String distributorId, String tagIds, String content, String picUrls, String location, String topicId, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.publishTalk(distributorId, tagIds, content, picUrls, location, topicId, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}