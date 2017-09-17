package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.ReportShopSearchModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/13.
 */
public class ReportShopSearchImpl implements ReportShopSearchModel {


    /**
     * 获取数据
     * @param distributorid
     * @param key
     * @param pageindex
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getShopSearchData(String distributorid, String key, String pageindex, String sign, ICallBackListener callBackListener) {
        IServiceAPI iServiceAPI = Api.getInstance().getGankService();
        iServiceAPI.getSeacheShop(distributorid,key,pageindex,sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
