package com.xdroid.functions.request.sender;

import com.xdroid.functions.request.IRequest;

import java.net.HttpURLConnection;

public class GetRequestSender extends BaseRequestSender {

    public GetRequestSender(IRequest<?> request, HttpURLConnection httpURLConnection) {
        super(request, httpURLConnection);
    }

    @Override
    public void send() {

    }
}
