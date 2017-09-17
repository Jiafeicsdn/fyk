package com.lvgou.distribution.model;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/2.
 */

public class ErrorLogImpl implements ErrorLogModel{
    /**
     * app错误日志
     *
     * @param distributorid 导游编号
     * @param type  设备类型 1=ios 2=Android
     * @param ippath 设备ip地址
     * @param errorurl 接口地址
     * @param appintro 设备信息描述
     * @param errorintro 错误内容
     * @param sign          签名
     * @return
     */
    @Override
    public void apperrorlog(String distributorid, int type, String ippath, String errorurl,String appintro,String errorintro ,String sign,ICallBackListener mICallBackListener) {
        IServiceAPI serviceAPI= Api.getInstance().getGankService();
        serviceAPI.apperrorlog(distributorid, type,ippath,errorurl,appintro, errorintro,sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(mICallBackListener));
    }
}
