package com.xdroid.request.extension.impl;

import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.cache.CacheData;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.interfaces.OnRequestListener;

/**
 * implements "OnRequestListener" interface, if you need to rewrite a callback function, can realize this
 * @author Robin
 * @since 2015/5/27 18:55.
 */
public abstract class OnRequestListenerAdapter<T> implements OnRequestListener<T> {
    /**
     * The preparation for the request
     */
    @Override
    public void onRequestPrepare() {

    }

    /**
     * Call this method when request finished
     *
     * @param data
     */
    @Override
    public void onRequestFinish(String data) {

    }

    /**
     * Call this method when request failed
     *
     * @param error
     */
    @Override
    public void onRequestFailed(String error) {

    }

    /**
     * Call this method when cache data load finished
     *
     * @param cacheData
     */
    @Override
    public void onCacheDataLoadFinish(CacheData<String> cacheData) {

    }

    /**
     * When the request is completed or the cache data loaded ,call this method , called only once, the final data delivery function
     *
     * @param request
     * @param response
     * @param dataType
     */
    @Override
    public void onDone(XDroidRequest<?> request, String response, DataType dataType) {

    }
}
