package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.SignatureImpl;
import com.lvgou.distribution.view.BaseView;

/**
 * Created by Administrator on 2016/12/15.
 */
public class SignaturePresenter extends BasePresenter<BaseView>{
    private SignatureImpl signatureimpl;
    private BaseView baseView;
    private Handler mHandlder;
    public SignaturePresenter(BaseView baseView){
        signatureimpl=new SignatureImpl();
        this.baseView=baseView;
        mHandlder = new Handler(Looper.getMainLooper());
    }
    public void updateUserSign(String distributorid, String usersign, String sign) {
        signatureimpl.updateUserSign(distributorid, usersign, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandlder.post(new Runnable() {
                    @Override
                    public void run() {
                        baseView.closeProgress();
                        baseView.excuteSuccessCallBack(s);
                    }
                });

            }

            @Override
            public void onFaild(final String s) {
                mHandlder.post(new Runnable() {
                    @Override
                    public void run() {
                        baseView.closeProgress();
                        baseView.excuteFailedCallBack(s);
                    }
                });

            }
        });
    }
}
