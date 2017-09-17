package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.SendSmsImpl;
import com.lvgou.distribution.view.FreeSmsView;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SendPersenter extends BasePresenter<FreeSmsView>{
    private SendSmsImpl requestBiz;
    private Handler mHandler;
    private FreeSmsView iView;
    public SendPersenter(FreeSmsView mBaseView){
        requestBiz=new SendSmsImpl();
        iView=mBaseView;
        mHandler=new Handler(Looper.getMainLooper());
    }

    public void sendSms(){
        requestBiz.sendSms(iView.getParamenters(),new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();

                        iView.excuteSuccessCallBack(s);
                    }});
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.closeProgress();

                        iView.excuteFailedCallBack(s);
                    }
                });
            }

//            @Override
//            public void onSuccess(final CallBackVo mCallBackVo) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Gson mGson = new Gson();
////                        JsonElement mJsonElement = mGson.toJsonTree(mCallBackVo);
////                        Type mType = new TypeToken<SmsResult>() {
////                        }.getType();
////                        SmsResult mRegisterVo = mGson.fromJson(mJsonElement, mType);
////                        mCallBackVo.setResult(mRegisterVo);
//                        iView.closeProgress();
//                        iView.excuteSuccessCallBack(mCallBackVo);
//                    }
//                });
//            }
//
//            @Override
//            public void onFaild(final CallBackVo mCallBackVo) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        iView.closeProgress();
//                        iView.excuteFailedCallBack(mCallBackVo);
//                    }
//                });
//            }

        });
    }
}
