package com.xdroid.functions.request;

public interface RequestHandler<T> extends RequestFinishHandler<T> {

    public T processOriginData(JsonData jsonData);

    public void onRequestFail(FailData failData);
}