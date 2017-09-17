package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ReportShopImpl;
import com.lvgou.distribution.view.ReportShopView;

/**
 * Created by Administrator on 2016/9/13.
 */
public class ReportShopPresenter extends BasePresenter<ReportShopView> {

    private ReportShopView reportShopView;
    private ReportShopImpl reportShopImpl;
    private Handler mHandler;

    public ReportShopPresenter(ReportShopView reportShopView) {
        this.reportShopView = reportShopView;
        reportShopImpl = new ReportShopImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取店铺列表
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getReportShopData(String distributorid, String pageindex, String sign) {
        reportShopImpl.getShopData(distributorid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        reportShopView.closeProgress();
                        reportShopView.applcationSuccCallBck(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        reportShopView.closeProgress();
                        reportShopView.applcationFailCallBck(s);
                    }
                });
            }
        });
    }
}
