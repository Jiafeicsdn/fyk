package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.BatchBuyStudyImpl;
import com.lvgou.distribution.view.BatchBuyStudyView;

/**
 * Created by Administrator on 2017/4/6.
 */

public class BatchBuyStudyPresenter extends BasePresenter<BatchBuyStudyView> {
    private BatchBuyStudyImpl batchBuyStudyImpl;
    private BatchBuyStudyView batchBuyStudyView;
    private Handler mHandler;

    public BatchBuyStudyPresenter(BatchBuyStudyView batchBuyStudyView) {
        this.batchBuyStudyView = batchBuyStudyView;
        batchBuyStudyImpl = new BatchBuyStudyImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 系列课程详情
     *
     * @param distributorid 讲课Id
     * @param seriesid      系列ID
     * @param sign          签名
     * @return
     */
    public void batchBuyStudy(String distributorid, String seriesid, String sign) {
        batchBuyStudyImpl.batchBuyStudy(distributorid, seriesid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        batchBuyStudyView.closeBatchBuyStudyProgress();
                        batchBuyStudyView.OnBatchBuyStudySuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        batchBuyStudyView.closeBatchBuyStudyProgress();
                        batchBuyStudyView.OnBatchBuyStudyFialCallBack("1", s);
                    }
                });
            }
        });
    }

}