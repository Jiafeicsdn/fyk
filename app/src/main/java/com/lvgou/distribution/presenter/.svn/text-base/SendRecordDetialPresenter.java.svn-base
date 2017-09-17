package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.SendSmsRecordDetialImpl;
import com.lvgou.distribution.view.SendSmsRecordDetialView;

/**
 * Created by Administrator on 2016/9/14.
 */
public class SendRecordDetialPresenter extends BasePresenter<SendSmsRecordDetialView> {

    private SendSmsRecordDetialView sendSmsRecordDetialView;
    private SendSmsRecordDetialImpl sendSmsRecordDetialImpl;
    private Handler mHandler;

    public SendRecordDetialPresenter(SendSmsRecordDetialView sendSmsRecordDetialView) {
        this.sendSmsRecordDetialView = sendSmsRecordDetialView;
        sendSmsRecordDetialImpl = new SendSmsRecordDetialImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 获取详情
     * @param distributorid
     * @param msgid
     * @param sign
     */
    public void getDetial(String distributorid, String msgid, String sign) {
        sendSmsRecordDetialImpl.getSendSmsRecordDetial(distributorid, msgid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendSmsRecordDetialView.closeProgress();
                        sendSmsRecordDetialView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendSmsRecordDetialView.closeProgress();
                        sendSmsRecordDetialView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }
}
