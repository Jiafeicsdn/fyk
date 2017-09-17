package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.AllTopicImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Administrator on 2016/10/19.
 */
public class AllTopicPresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private AllTopicImpl allTopicImpl;
    private Handler mHandler;

    public AllTopicPresenter(GroupView groupView) {
        this.groupView = groupView;
        allTopicImpl = new AllTopicImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    public void getToppicList(String currPage, String sign) {
        allTopicImpl.getTopicList(currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("1", s);
                    }
                });
            }
        });
    }
}
