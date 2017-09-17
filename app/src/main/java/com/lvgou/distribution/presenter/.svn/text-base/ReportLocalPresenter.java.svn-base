package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ReportLocalImpl;
import com.lvgou.distribution.view.ReportLocalView;

/**
 * Created by Administrator on 2016/9/13.
 */
public class ReportLocalPresenter extends BasePresenter<ReportLocalView> {

    private ReportLocalImpl reportLocalImpl;
    private ReportLocalView reportLocalView;
    private Handler mHandler;

    public ReportLocalPresenter(ReportLocalView reportLocalView) {
        this.reportLocalView = reportLocalView;
        reportLocalImpl = new ReportLocalImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取数据
     *
     * @param distributorid
     * @param key
     * @param latitude
     * @param longitude
     * @param pageindex
     * @param sign
     */
    public void getLocalData(String distributorid, String key, String latitude, String longitude, String pageindex, String sign) {
        reportLocalImpl.getLoaclData(distributorid, key, latitude, longitude, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        reportLocalView.closeProgress();
                        reportLocalView.applcationSuccCallBck(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        reportLocalView.closeProgress();
                        reportLocalView.applcationFailCallBck(s);
                    }
                });
            }
        });
    }
}
