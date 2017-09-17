package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.SendSmsImpl;
import com.lvgou.distribution.model.impl.SmsModelImpl;
import com.lvgou.distribution.view.SmsModelView;

/**
 * Created by Administrator on 2016/9/14.
 */
public class SendSmsModelPresenter extends BasePresenter<SmsModelView> {

    private SmsModelView smsModelView;
    private SmsModelImpl sendSmsImpl;
    private Handler mHandler;

    public SendSmsModelPresenter(SmsModelView smsModelView) {
        this.smsModelView = smsModelView;
        sendSmsImpl = new SmsModelImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取模板列表
     *
     * @param distributorid
     * @param sign
     */
    public void getModelList(String distributorid, String sign) {
        sendSmsImpl.getModelList(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        smsModelView.closeProgress();
                        smsModelView.OnFamousSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        smsModelView.closeProgress();
                        smsModelView.OnFamousFialCallBack("1", s);
                    }
                });
            }
        });
    }

    /**
     * 删除模板
     *
     * @param distributorid
     * @param tmpid
     * @param sign
     */
    public void deleteModel(String distributorid, String tmpid, String sign) {
        sendSmsImpl.deleteModel(distributorid, tmpid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        smsModelView.closeProgress();
                        smsModelView.OnFamousSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        smsModelView.closeProgress();
                        smsModelView.OnFamousFialCallBack("2", s);
                    }
                });
            }
        });
    }

}
