package com.xdroid.functions.request;

import java.net.HttpURLConnection;

/**
 * @author http://www.liaohuqiu.net
 */
public class SimpleRequest<T> extends RequestBase<T> implements IRequest<T> {

    private RequestHandler<T> mRequestHandler;

    public SimpleRequest() {

    }

    public SimpleRequest(RequestHandler<T> handler) {
        setRequestHandler(handler);
    }

    public SimpleRequest setRequestHandler(RequestHandler<T> handler) {
        mRequestHandler = handler;
        return this;
    }

    @Override
    protected void doSendRequest() {
        SimpleRequestManager.sendRequest(this);
    }

    @Override
    protected T doRequestSync() {
        return SimpleRequestManager.requestSync(this);
    }

    @Override
    protected void prepareRequest() {
    }

    @Override
    public void onRequestSuccess(T data) {
        if (null != mRequestHandler) {
            mRequestHandler.onRequestFinish(data);
        }
    }

    @Override
    public void onRequestFail(FailData failData) {
        if (null != mRequestHandler) {
            mRequestHandler.onRequestFail(failData);
        }
    }

    @Override
    public T processOriginDataFromServer(JsonData rawData) {
        if (null != mRequestHandler) {
            return mRequestHandler.processOriginData(rawData);
        }
        return null;
    }
    
    /**
     * 获取HttpURLConnection 对象
     * @return
     */
    public HttpURLConnection getHttpURLConnection(){
    	if (SimpleRequestManager.httpURLConnection!=null) {
			return SimpleRequestManager.httpURLConnection;
		}
    	return null;
    }
}
