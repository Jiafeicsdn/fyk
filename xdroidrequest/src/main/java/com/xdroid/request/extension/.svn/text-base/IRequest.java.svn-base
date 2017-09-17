package com.xdroid.request.extension;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xdroid.request.extension.DefaultRequestBody.OnOriginalRequestListener;
import com.xdroid.request.extension.config.CacheConfig;
import com.xdroid.request.extension.interfaces.OnRequestListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Provide a default request sample, you can use the request for network request, if you want to custom request, only need to imitate this request inherit ��XDroidRequest��, override the ��requestBody�� method
 * @author Robin
 * @since 2015-05-22 12:14:52
 */ 
public class IRequest extends XDroidRequest<String> {

	HttpURLConnection httpURLConnection;

	private DefaultRequestBody defaultRequestBody;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String error = (String) msg.obj;
				onRequestFailed(error);
				break;

			case 1:
				String result = (String) msg.obj;
				onRequestFinish(result);
				break;
			}

		};
	};

    public IRequest(Context context){
        super(context);
        defaultRequestBody = new DefaultRequestBody();
    }

	public IRequest(Context context, String url) {
		super(context, url);
		defaultRequestBody = new DefaultRequestBody();
	}

	public IRequest(Context context, CacheConfig cacheConfig, String url) {
		super(context, cacheConfig, url);
		defaultRequestBody = new DefaultRequestBody();
	}

	public IRequest(Context context, CacheConfig cacheConfig, String url,
			String cacheKey, OnRequestListener<?> onRequestListener) {
		super(context, cacheConfig, url, cacheKey, onRequestListener);
		defaultRequestBody = new DefaultRequestBody();
	}

	@Override
	public void requestBody(String url) {

		// simulate a request
		/*
		 * new AsyncTask<Void, Void, String>(){
		 * 
		 * @Override protected String doInBackground(Void... params) { try {
		 * Thread.sleep(3000); } catch (InterruptedException e) {
		 * e.printStackTrace(); } return " simulate a request"; }
		 * 
		 * protected void onPostExecute(String result) {
		 * onRequestFinish(result); };
		 * 
		 * }.execute();
		 */

		/*
		 * StringRequest stringRequest=new StringRequest(url, new
		 * Response.Listener<String>() {
		 * 
		 * @Override public void onResponse(String response) {
		 * onRequestFinish(response); } }, new Response.ErrorListener() {
		 * 
		 * @Override public void onErrorResponse(VolleyError error) {
		 * onRequestFailed(error.toString()); } }){
		 * 
		 * @Override protected Response<String> parseNetworkResponse(
		 * NetworkResponse response) { try {
		 * 
		 * Map<String, String> responseHeaders = response.headers; String
		 * rawCookies = responseHeaders.get("Set-Cookie");
		 * onTransmitInfomation(rawCookies);
		 * 
		 * String dataString = new String(response.data, "UTF-8"); return
		 * Response
		 * .success(dataString,HttpHeaderParser.parseCacheHeaders(response)); }
		 * catch (UnsupportedEncodingException e) { return Response.error(new
		 * ParseError(e)); }
		 * 
		 * } };
		 * 
		 * com.xdroid.request.RequestQueue
		 * queue=Volley.newRequestQueue(getContext()); queue.add(stringRequest);
		 */

		defaultRequestBody
				.setOnOriginalRequestListener(new OnOriginalRequestListener() {

					@Override
					public void onOriginalRequestFinish(String response,
							Map<String, String> resultHeaders) {

                        onTransmitInformation(resultHeaders.get("Set-Cookie"));

						Message msg = new Message();
						msg.what = 1;
						msg.obj = response;
						handler.sendMessage(msg);

					}
				});

		new Thread() {
			public void run() {
				try {
					httpURLConnection = defaultRequestBody
							.performRequest(IRequest.this);
				} catch (IOException e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.what = 0;
					msg.obj = e.toString();
					handler.sendMessage(msg);
				}
			};
		}.start();
	}

	public DefaultRequestBody getDefaultRequestBody() {
		return defaultRequestBody;
	}
    public HttpURLConnection getHttpURLConnection() {
        return httpURLConnection;
    }
}
