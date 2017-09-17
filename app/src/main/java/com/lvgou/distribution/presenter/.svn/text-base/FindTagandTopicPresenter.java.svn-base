package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.FindTagandTopicImpl;
import com.lvgou.distribution.view.FindTagandTopicView;

/**
 * Created by Administrator on 2017/4/17.
 */

public class FindTagandTopicPresenter extends BasePresenter<FindTagandTopicView> {
    private FindTagandTopicImpl findTagandTopicImpl;
    private FindTagandTopicView findTagandTopicView;
    private Handler mHandler;

    public FindTagandTopicPresenter(FindTagandTopicView findTagandTopicView) {
        this.findTagandTopicView = findTagandTopicView;
        findTagandTopicImpl = new FindTagandTopicImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 发布蜂文标签
     *
     * @param distributorId
     * @param sign
     * @return
     */
    public void findTagandTopic(String distributorId, String sign) {
        findTagandTopicImpl.findTagandTopic(distributorId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        findTagandTopicView.closeFindTagandTopicProgress();
                        findTagandTopicView.OnFindTagandTopicSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        findTagandTopicView.closeFindTagandTopicProgress();
                        findTagandTopicView.OnFindTagandTopicFialCallBack("1", s);
                    }
                });
            }
        });
    }

}