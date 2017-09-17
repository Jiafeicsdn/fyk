package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ReportImpl;
import com.lvgou.distribution.view.ReportView;

/**
 * Created by Administrator on 2016/9/13.
 */
public class ReportPredenter extends BasePresenter<ReportView> {

    private ReportImpl reportImpl;
    private ReportView reportView;
    private Handler mHandler;

    public ReportPredenter(ReportView reportView) {
        this.reportView = reportView;
        reportImpl = new ReportImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取数据
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getReportList(String distributorid, String pageindex, String sign) {
        reportImpl.getData(distributorid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        reportView.closeProgress();
                        reportView.applcationSuccCallBck(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        reportView.closeProgress();
                        reportView.applcationFailCallBck(s);
                    }
                });
            }
        });
    }
}
