package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.DistributormainImpl;
import com.lvgou.distribution.model.impl.TopicDetailsImpl;
import com.lvgou.distribution.view.TopicDetailsView;
import com.lvgou.distribution.view.UserPersonalView;

/**
 * Created by Administrator on 2016/10/22.
 */
public class TopicDetailsPresenter extends BasePresenter<TopicDetailsView> {
    private TopicDetailsImpl requestBiz;
    private Handler mHandler;
    private TopicDetailsView iView;

    public TopicDetailsPresenter(TopicDetailsView mBaseView) {
        requestBiz = new TopicDetailsImpl();
        iView = mBaseView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void topicdetail(String topicId, String sign) {
        requestBiz.topicdetail(topicId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.topicdetailResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack("detail",s);
            }
        });
    }

    public void commenttopic(String distributorid, String topicId, String commentId, String content, String sign) {
        requestBiz.commenttopic(distributorid, topicId, commentId, content, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.commenttopicResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack("comment",s);
            }
        });
    }
    public void topiccommentdel(String distributorId, String talkCommentId,String sign) {
        requestBiz.topiccommentdel(distributorId, talkCommentId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack("del",s);
            }
        });
    }
    public void topiccommentlist(String topicId, String prePageLastDataObjectId, int currPage, String sign) {
        requestBiz.topiccommentlist(topicId, prePageLastDataObjectId, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.topiccommentlistResponse(s);
                    }
                });
            }

            @Override
            public void onFaild(String s) {
                iView.excuteFailedCallBack("list",s);
            }
        });
    }
}
