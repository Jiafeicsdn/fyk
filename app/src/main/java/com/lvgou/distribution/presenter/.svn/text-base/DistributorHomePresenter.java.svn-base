package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.DistributorHomeImpl;
import com.lvgou.distribution.view.DistributorHomeView;

/**
 * Created by Administrator on 2017/4/13.
 */

public class DistributorHomePresenter extends BasePresenter<DistributorHomeView> {
    private DistributorHomeImpl distributorHomeImpl;
    private DistributorHomeView distributorHomeView;
    private Handler mHandler;

    public DistributorHomePresenter(DistributorHomeView distributorHomeView) {
        this.distributorHomeView = distributorHomeView;
        distributorHomeImpl = new DistributorHomeImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 个人主页
     *
     * @param distributorid    导游编号
     * @param seeDistributorId 查看导游编号
     * @param sign             签名
     * @return
     */
    public void distributorHome(String distributorid, String seeDistributorId, String sign) {
        distributorHomeImpl.distributorHome(distributorid, seeDistributorId, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        distributorHomeView.closeDistributorHomeProgress();
                        distributorHomeView.OnDistributorHomeSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        distributorHomeView.closeDistributorHomeProgress();
                        distributorHomeView.OnDistributorHomeFialCallBack("1", s);
                    }
                });
            }
        });
    }

}