package com.lvgou.distribution.model.impl;


import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.bean.CallBackVo;
import com.lvgou.distribution.bean.SmsBean;
import com.lvgou.distribution.model.IDataModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/8.
 */
public class SendSmsImpl implements IDataModel{
    /**
     * 群发短信
     * @param smsBean:请求参数：封装为bean
     * @param mICallBackListener:回调接口
     */
    @Override
    public void sendSms(SmsBean smsBean,ICallBackListener mICallBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.sendSms(smsBean)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }

}
