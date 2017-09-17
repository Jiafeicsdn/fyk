package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.HomeTalklistImpl;
import com.lvgou.distribution.view.HomeTalklistView;

/**
 * Created by Administrator on 2017/4/13.
 */

public class HomeTalklistPresenter extends BasePresenter<HomeTalklistView> {
    private HomeTalklistImpl homeTalklistImpl;
    private HomeTalklistView homeTalklistView;
    private Handler mHandler;

    public HomeTalklistPresenter(HomeTalklistView homeTalklistView) {
        this.homeTalklistView = homeTalklistView;
        homeTalklistImpl = new HomeTalklistImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 个人首页-蜂文列表
     *
     * @param distributorId    导游编号
     * @param seeDistributorId 查看导游编号
     * @param currPage         当前页
     * @param sign             签名
     * @return
     */
    public void homeTalklist(String distributorId, String seeDistributorId, int currPage, String sign) {
        homeTalklistImpl.homeTalklist(distributorId, seeDistributorId, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        homeTalklistView.closeHomeTalklistProgress();
                        homeTalklistView.OnHomeTalklistSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        homeTalklistView.closeHomeTalklistProgress();
                        homeTalklistView.OnHomeTalklistFialCallBack("1", s);
                    }
                });
            }
        });
    }

}