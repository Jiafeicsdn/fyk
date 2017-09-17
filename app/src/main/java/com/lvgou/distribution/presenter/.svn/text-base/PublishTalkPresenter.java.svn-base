package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.PublishTalkImpl;
import com.lvgou.distribution.view.PublishTalkView;

/**
 * Created by Administrator on 2017/4/18.
 */

public class PublishTalkPresenter extends BasePresenter<PublishTalkView> {
    private PublishTalkImpl publishTalkImpl;
    private PublishTalkView publishTalkView;
    private Handler mHandler;

    public PublishTalkPresenter(PublishTalkView publishTalkView) {
        this.publishTalkView = publishTalkView;
        publishTalkImpl = new PublishTalkImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

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
    public void publishTalk(String distributorId, String tagIds, String content, String picUrls, String location, String topicId, String sign) {
        publishTalkImpl.publishTalk(distributorId, tagIds, content, picUrls, location, topicId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        publishTalkView.closePublishTalkProgress();
                        publishTalkView.OnPublishTalkSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        publishTalkView.closePublishTalkProgress();
                        publishTalkView.OnPublishTalkFialCallBack("1", s);
                    }
                });
            }
        });
    }

}