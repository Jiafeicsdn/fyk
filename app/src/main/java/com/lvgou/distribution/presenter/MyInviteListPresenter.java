package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.MyInviteListImpl;
import com.lvgou.distribution.view.MyInviteListView;

/**
 * Created by Administrator on 2017/4/12.
 */

public class MyInviteListPresenter extends BasePresenter<MyInviteListView> {
    private MyInviteListImpl myInviteListImpl;
    private MyInviteListView myInviteListView;
    private Handler mHandler;

    public MyInviteListPresenter(MyInviteListView myInviteListView) {
        this.myInviteListView = myInviteListView;
        myInviteListImpl = new MyInviteListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 邀请好友-邀请记录
     *
     * @param distributorid 讲课Id
     * @param type          类型 2=待审核，3=邀请成功
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void myInviteList(String distributorid, int type, int pageindex, String sign) {
        myInviteListImpl.myInviteList(distributorid, type, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myInviteListView.closeMyInviteListProgress();
                        myInviteListView.OnMyInviteListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myInviteListView.closeMyInviteListProgress();
                        myInviteListView.OnMyInviteListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}