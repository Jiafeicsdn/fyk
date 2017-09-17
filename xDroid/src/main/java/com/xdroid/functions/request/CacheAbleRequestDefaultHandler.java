package com.xdroid.functions.request;

import com.lidroid.xutils.util.LogUtils;


public abstract class CacheAbleRequestDefaultHandler<T1> implements CacheAbleRequestHandler<T1> {

    public void onCacheData(T1 data, boolean outOfDate) {
    }

    @Override
    public void onRequestFail(FailData failData) {
        if (failData != null && failData.getRequest() != null && failData.getRequest().getRequestData() != null) {
            LogUtils.e("onRequestFail: %s"+ failData.getRequest().getRequestData().getRequestUrl());
        }
    }

    @Override
    public void onRequestFinish(T1 data) {
            LogUtils.d( "onRequestFinish: %s"+ data);
    }
}