/**
 * 带缓存的网络请求，返回数据为json格式
 * @author Robin
 *time 2015-03-25 11:34:26
 */
package com.xdroid.functions.request;


/*
 * ʹ使用方法：
 * 
    在Application类中初始化
    	//初始化缓存网络请求
		String dir = "request-cache"; 
		RequestCacheManager.init(this, dir, 1024 * 10, 1024 * 10); 

			
	    // init local cache, just use default 
		CacheManagerFactory.initDefaultCache(this, null, -1, -1); 

	public static void getData(){
		CacheAbleRequestHandler<String> cacheAbleRequestHandler=new CacheAbleRequestHandler<String>() {
			
			@Override
			public void onRequestFinish(String data) {
				LogUtils.i("onRequestFinish"+data);
				
			}
			
			@Override
			public String processOriginData(JsonData jsonData) {
				LogUtils.i("processOriginData"+jsonData.toString());
				return jsonData.toString();
			}
			
			@Override
			public void onRequestFail(FailData failData) {
				LogUtils.i("onRequestFail"+failData.getErrorType());
				
			}
			
			@Override
			public void onCacheData(String data, boolean outOfDate) {
				LogUtils.i("onCacheData"+data);
				
			}
			
			@Override
			public void onCacheAbleRequestFinish(String data, ResultType type,
					boolean outOfDate) {
				LogUtils.i("onCacheAbleRequestFinish"+data);
				
			}
		};
		CacheAbleRequest<String> cacheAbleRequest=new CacheAbleRequest<String>();
//		cacheAbleRequest.setUseCacheAnyway(false);
//		cacheAbleRequest.setTimeout(1000);
//
//        String cacheKey = "api/get-server-time";
//        cacheAbleRequest.setCacheTime(30).setCacheKey(cacheKey);
//        cacheAbleRequest.setDisableCache(false);
		
		cacheAbleRequest.setCacheAbleRequestHandler(cacheAbleRequestHandler);
//		cacheAbleRequest.getRequestData().setRequestUrl("http://www.android-cube-app-server.liaohuqiu.net/api/get-image.php");
//		cacheAbleRequest.getRequestData().addQueryData("token", "");
		
		
		//POST请求
//		cacheAbleRequest.getRequestData().setRequestUrl("http://wh.xkjnet.com/api/index.php?route=account/login");
//		cacheAbleRequest.getRequestData().addPostData("telephone", "18355338027");
//		cacheAbleRequest.getRequestData().addPostData("password","123456");
		
		
		//GET请求
		cacheAbleRequest.getRequestData().setRequestUrl("http://103.234.124.12/JsonP.asp");
		cacheAbleRequest.getRequestData().addQueryData("Cmd", "Proc_GetUser");
		cacheAbleRequest.getRequestData().addQueryData("Data",  "18130300013");
		cacheAbleRequest.getRequestData().addQueryData("Field", "");
		cacheAbleRequest.getRequestData().addQueryData("Callback", "JsonP5");
		
		cacheAbleRequest.send();
	}




 */