package com.xdroid.request.extension.interfaces;

import com.xdroid.request.extension.cache.CacheData;

/**
 *  provide some public method for XDroidRequest
 * @author Robin
 * @since 2015-05-07 14:06:41
 */
public interface XDroidRequestMethodProvider<RequestResultDataType>{
	
	/** The request body,this method is open ,you can override this method to do a network request*/
	public void requestBody(String url);
	
	/** Do something before the request */
	public  void requestPrepare();
	
	/** Perform the request  */
	public void doRequest();
	
	/** When request finished */
	public void onRequestFinish(RequestResultDataType data);
	
	/** When request failed */
	public void onRequestFailed(String error);
	
	/** Transmit relative information,like "HttpUrlConnection" , "HttpClient" and so on, used to obtain header information, such as a Cookie */
	public<O> void onTransmitInformation(O obj);
	
	/** When cache data load finished */
	public void onCacheDataLoadFinish(CacheData<String> cacheData);
	
	/** Get the current request's cache data */
	public CacheData<String> getCache(String key);
	
	/** Get the request's cookie */
	public String getCookie();
	
	/** Manual call this method when the request finished , to release the same request in the "mWaitingRequests" map  */
	public void finish() ;
	
}
