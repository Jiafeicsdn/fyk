package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.MerchantImpl;
import com.lvgou.distribution.view.BaseView;

/**
 * Created by Administrator on 2017/1/5.
 */
public class MerchantPresenter extends BasePresenter<BaseView> {
    private BaseView mBaseView;
    private MerchantImpl merchantImpl;
    private Handler mHandler;

    public MerchantPresenter(BaseView baseView) {
        mBaseView = baseView;
        merchantImpl = new MerchantImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void reportsellerindex(String distributorId, String reportid, String sign) {
        merchantImpl.reportsellerindex(distributorId, reportid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaseView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaseView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }
}
