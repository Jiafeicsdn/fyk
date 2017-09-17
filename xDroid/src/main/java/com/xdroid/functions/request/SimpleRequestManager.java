package com.xdroid.functions.request;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.xdroid.functions.concurrent.SimpleTask;
import com.xdroid.functions.request.sender.BaseRequestSender;
import com.xdroid.functions.request.sender.RequestSenderFactory;

import java.net.HttpURLConnection;

/**
 * @author http://www.liaohuqiu.net
 */
public class SimpleRequestManager {


    private final static int REQUEST_SUCCESS = 0x01;

    private final static int REQUEST_FAILED = 0x02;
    
    public static HttpURLConnection httpURLConnection;

    public static <T> T requestSync(final IRequest<T> request) {
        T data = null;
        try {
            StringBuilder sb = new StringBuilder();
            RequestData requestData = request.getRequestData();
            BaseRequestSender requestSender = RequestSenderFactory.create(request);
            if (requestSender != null) {
                requestSender.send();
                requestSender.getResponse(sb);
                data = request.onDataFromServer(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setFailData(FailData.networkError(request));
        }

        final T finalData = data;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (finalData == null) {
                    request.onRequestFail(request.getFailData());
                } else {
                    request.onRequestSuccess(finalData);
                }
            }
        };

        SimpleTask.post(runnable);

        return data;
    }

    public static <T> void sendRequest(final IRequest<T> request) {

        final Handler handler = new Handler(Looper.getMainLooper()) {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REQUEST_SUCCESS:
                        request.onRequestSuccess((T) msg.obj);
                        break;

                    case REQUEST_FAILED:
                        request.onRequestFail(request.getFailData());
                        break;

                    default:
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                T data = null;
                try {
                    StringBuilder sb = new StringBuilder();
                    RequestData requestData = request.getRequestData();
                    BaseRequestSender requestSender = RequestSenderFactory.create(request);
                    if (requestSender != null) {
                        requestSender.send();
                        httpURLConnection=requestSender.getResponse(sb);
                        data = request.onDataFromServer(sb.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setFailData(FailData.networkError(request));
                }

                if (null == data) {
                    Message msg = Message.obtain();
                    msg.what = REQUEST_FAILED;
                    handler.sendMessage(msg);
                } else {
                    Message msg = Message.obtain();
                    msg.what = REQUEST_SUCCESS;
                    msg.obj = data;
                    handler.sendMessage(msg);
                }
            }
        }, "cube-simple-request-manager").start();
    }
}
