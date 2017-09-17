package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.ErrorLogImpl;
import com.lvgou.distribution.model.impl.MerchantImpl;
import com.lvgou.distribution.view.BaseView;
import com.lvgou.distribution.view.BaseView1;

/**
 * Created by Administrator on 2017/3/2.
 */

public class ErrorLogPresenter extends BasePresenter<BaseView1> {
    private BaseView1 mBaseView;
    private ErrorLogImpl errorLogImpl;
    private Handler mHandler;

    public ErrorLogPresenter(BaseView1 baseView) {
        mBaseView = baseView;
        errorLogImpl = new ErrorLogImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void apperrorlog(String distributorid, int type, String ippath, String errorurl,String appintro,String errorintro ,String sign) {
        errorLogImpl.apperrorlog(distributorid,  type,  ippath,  errorurl, appintro, errorintro , sign,  new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaseView.excuteErrSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaseView.excuteErrFailedCallBack(s);
                    }
                });
            }
        });
    }
}