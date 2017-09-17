package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.MyFollowListImpl;
import com.lvgou.distribution.view.MyFollowListView;

/**
 * Created by Administrator on 2017/4/26.
 */

public class MyFollowListPresenter extends BasePresenter<MyFollowListView> {
    private MyFollowListImpl myFollowListImpl;
    private MyFollowListView myFollowListView;
    private Handler mHandler;

    public MyFollowListPresenter(MyFollowListView myFollowListView) {
        this.myFollowListView = myFollowListView;
        myFollowListImpl = new MyFollowListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 个人首页-关注列表
     *
     * @param distributorId
     * @param seeDistributorId
     * @param currPage
     * @param sign
     * @return
     */
    public void myFollowList(String distributorId, String seeDistributorId, int currPage, String sign) {
        myFollowListImpl.myFollowList(distributorId, seeDistributorId, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myFollowListView.closeMyFollowListProgress();
                        myFollowListView.OnMyFollowListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myFollowListView.closeMyFollowListProgress();
                        myFollowListView.OnMyFollowListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}