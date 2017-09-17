package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.SendSmsRecrodImpl;
import com.lvgou.distribution.view.SendSmsRecordView;

/**
 * Created by Administrator on 2016/9/14.
 */
public class SendRecrodPresenter extends BasePresenter<SendSmsRecordView> {

    private SendSmsRecrodImpl sendSmsRecrodImpl;
    private SendSmsRecordView sendSmsRecordView;
    private Handler mHandler;

    public SendRecrodPresenter(SendSmsRecordView sendSmsRecordView) {
        this.sendSmsRecordView = sendSmsRecordView;
        sendSmsRecrodImpl = new SendSmsRecrodImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 获取记录列表
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getSendSmsRecord(String distributorid, String pageindex, String sign) {
        sendSmsRecrodImpl.getSmsRecord(distributorid, pageindex + "", sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendSmsRecordView.closeProgress();
                        sendSmsRecordView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendSmsRecordView.closeProgress();
                        sendSmsRecordView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }
}
