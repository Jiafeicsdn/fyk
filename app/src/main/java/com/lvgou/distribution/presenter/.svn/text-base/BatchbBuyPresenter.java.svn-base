package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.BatchbBuyImpl;
import com.lvgou.distribution.view.BatchbBuyView;

/**
 * Created by Administrator on 2017/4/7.
 */

public class BatchbBuyPresenter extends BasePresenter<BatchbBuyView> {
    private BatchbBuyImpl batchbBuyImpl;
    private BatchbBuyView batchbBuyView;
    private Handler mHandler;

    public BatchbBuyPresenter(BatchbBuyView batchbBuyView) {
        this.batchbBuyView = batchbBuyView;
        batchbBuyImpl = new BatchbBuyImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 系列课程-批量购买
     *
     * @param distributorid 讲课Id
     * @param teacherid     课程ID包含免费课程(1,2,3,4,5)
     * @param couponid      课程对应可使用听课券ID，没有则为0(1,2,0,3,0)
     * @param sign          签名
     * @return
     */
    public void batchbBuy(String distributorid, String teacherid, String couponid, String sign) {
        batchbBuyImpl.batchbBuy(distributorid, teacherid, couponid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        batchbBuyView.closeBatchbBuyProgress();
                        batchbBuyView.OnBatchbBuySuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        batchbBuyView.closeBatchbBuyProgress();
                        batchbBuyView.OnBatchbBuyFialCallBack("1", s);
                    }
                });
            }
        });
    }

}