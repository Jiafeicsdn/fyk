package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.MyFansListImpl;
import com.lvgou.distribution.view.MyFansListView;

/**
 * Created by Administrator on 2017/4/26.
 */

public class MyFansListPresenter extends BasePresenter<MyFansListView> {
    private MyFansListImpl myFansListImpl;
    private MyFansListView myFansListView;
    private Handler mHandler;

    public MyFansListPresenter(MyFansListView myFansListView) {
        this.myFansListView = myFansListView;
        myFansListImpl = new MyFansListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 个人首页-粉丝列表
     *
     * @param distributorId
     * @param seeDistributorId
     * @param currPage
     * @param sign
     * @return
     */
    public void myFansList(String distributorId, String seeDistributorId, int currPage, String sign) {
        myFansListImpl.myFansList(distributorId, seeDistributorId, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myFansListView.closeMyFansListProgress();
                        myFansListView.OnMyFansListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myFansListView.closeMyFansListProgress();
                        myFansListView.OnMyFansListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}