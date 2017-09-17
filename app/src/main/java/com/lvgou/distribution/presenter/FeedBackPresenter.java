package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.FeedBackImpl;
import com.lvgou.distribution.view.FeedBackView;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;

/**
 * Created by Administrator on 2017/3/30.
 */

public class FeedBackPresenter extends BasePresenter<FeedBackView> {
    private FeedBackImpl feedBackImpl;
    private FeedBackView feedBackView;
    private Handler mHandler;

    public FeedBackPresenter(FeedBackView feedBackView) {
        this.feedBackView = feedBackView;
        feedBackImpl = new FeedBackImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 意见反馈
     *
     * @param distributorid 导游ID
     * @param content       内容
     * @param sign          签名
     * @return
     */
    public void feedBack(String distributorid, String content, String sign) {
        feedBackImpl.feedBack(distributorid, content, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        feedBackView.closeFeedBackProgress();
                        feedBackView.OnFeedBackSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        feedBackView.closeFeedBackProgress();
                        feedBackView.OnFeedBackFialCallBack("1", s);
                    }
                });
            }
        });
    }

}