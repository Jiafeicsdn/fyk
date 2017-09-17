package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.LoadDistributorExtendImpl;
import com.lvgou.distribution.view.LoadDistributorExtendView;

/**
 * Created by Administrator on 2017/4/10.
 */

public class LoadDistributorExtendPresenter extends BasePresenter<LoadDistributorExtendView> {
    private LoadDistributorExtendImpl loadDistributorExtendImpl;
    private LoadDistributorExtendView loadDistributorExtendView;
    private Handler mHandler;

    public LoadDistributorExtendPresenter(LoadDistributorExtendView loadDistributorExtendView) {
        this.loadDistributorExtendView = loadDistributorExtendView;
        loadDistributorExtendImpl = new LoadDistributorExtendImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 我的-实名认证获取数据
     *
     * @param distributorid 讲课Id
     * @param sign          签名
     * @return
     */
    public void loadDistributorExtend(String distributorid, String sign) {
        loadDistributorExtendImpl.loadDistributorExtend(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadDistributorExtendView.closeLoadDistributorExtendProgress();
                        loadDistributorExtendView.OnLoadDistributorExtendSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadDistributorExtendView.closeLoadDistributorExtendProgress();
                        loadDistributorExtendView.OnLoadDistributorExtendFialCallBack("1", s);
                    }
                });
            }
        });
    }

}