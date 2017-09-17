package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ReportShopSearchImpl;
import com.lvgou.distribution.view.ReportShopSearchView;

/**
 * Created by Administrator on 2016/9/13.
 */
public class ReportShopSearchPresenter extends BasePresenter<ReportShopSearchView> {

    private ReportShopSearchView reportShopSearchView;
    private ReportShopSearchImpl reportShopSearchImpl;
    private Handler mHandler;

    public ReportShopSearchPresenter(ReportShopSearchView reportShopSearchView) {
        this.reportShopSearchView = reportShopSearchView;
        reportShopSearchImpl = new ReportShopSearchImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 搜索店铺结果
     *
     * @param distributorid
     * @param key
     * @param pageindex
     * @param sign
     */
    public void getSearchShopData(String distributorid, String key, String pageindex, String sign) {
        reportShopSearchImpl.getShopSearchData(distributorid, key, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        reportShopSearchView.closeProgress();
                        reportShopSearchView.applcationSuccCallBck(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        reportShopSearchView.closeProgress();
                        reportShopSearchView.applcationFailCallBck(s);
                    }
                });
            }
        });
    }
}
