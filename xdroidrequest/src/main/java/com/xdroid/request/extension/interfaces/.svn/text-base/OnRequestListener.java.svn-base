package com.xdroid.request.extension.interfaces;

import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.cache.CacheData;
import com.xdroid.request.extension.config.DataType;

/**
 * when data processed finish
 * 
 * @author Robin
 * @since 2015-05-07 21:27:07
 * @param <T>
 */
public interface OnRequestListener<T> {

    /** The preparation for the request */
	public  void onRequestPrepare();

    /** Call this method when request finished  */
	public  void onRequestFinish(String data);

    /** Call this method when request failed  */
	public  void onRequestFailed(String error);

    /** Call this method when cache data load finished  */
	public  void onCacheDataLoadFinish(CacheData<String> cacheData);

    /** When the request is completed or the cache data loaded ,call this method , called only once, the final data delivery function */
	public  void onDone(XDroidRequest<?> request, String response, DataType dataType);

}
