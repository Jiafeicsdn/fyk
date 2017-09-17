package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TopicListImpl;
import com.lvgou.distribution.view.TopicListView;

/**
 * Created by Administrator on 2017/4/17.
 */

public class TopicListPresenter extends BasePresenter<TopicListView> {
    private TopicListImpl topicListImpl;
    private TopicListView topicListView;
    private Handler mHandler;

    public TopicListPresenter(TopicListView topicListView) {
        this.topicListView = topicListView;
        topicListImpl = new TopicListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 蜂圈话题列表
     *
     * @param currPage
     * @param sign
     * @return
     */
    public void topicList(int currPage, String sign) {
        topicListImpl.topicList(currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        topicListView.closeTopicListProgress();
                        topicListView.OnTopicListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        topicListView.closeTopicListProgress();
                        topicListView.OnTopicListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}