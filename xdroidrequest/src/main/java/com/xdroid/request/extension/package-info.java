/**
 * usage
 * @author Robin
 * @since 2015-05-22 14:16:49
 *
 */
package com.xdroid.request.extension;

        /*
        IRequest request=new IRequest(TestActivity.this,  "http://sujiaapi.c.zmit.cn/api/index.php?route=checkout/cart/add");
        request.setTag("sujia-cart");
        request.getDefaultRequestBody().getParams().put("product_id", "651");
        request.getDefaultRequestBody().getParams().put("quantity", "1");
        request.getDefaultRequestBody().setMethod(RequestMethod.POST);
        request.setPriority(Priority.HIGH);
        request.setOnRequestListener(this);

        RequestQueue requestQueue=new RequestQueue();
        requestQueue.add(request);
        requestQueue.start();*/

/*
@Override
public void onRequestPrepare() {
        // TODO Auto-generated method stub

        }

@Override
public void onRequestFinish(String data) {
        // TODO Auto-generated method stub

        }

@Override
public void onRequestFailed(String error) {
        // TODO Auto-generated method stub

        }

@Override
public void onCacheDataLoadFinish(CacheData<String> cacheData) {
        // TODO Auto-generated method stub

        }

@Override
public void onDone(XDroidRequest<?> request, String response,
        DataType dataType) {
        String cacheKey=request.getCacheKey();
        Toast.makeText(TestActivity.this, cacheKey+"request finish", Toast.LENGTH_SHORT).show();
        if (cacheKey.equals("http://sujiaapi.c.zmit.cn/api/index.php?route=checkout/cart/add")) {
//			request.getRequestQueue().cancelAll("sujia-cart");
        }
        }*/


        /*
        private CacheConfig getCacheConfig() {
        CacheConfig cacheConfig=new CacheConfig();
        cacheConfig.setShouldCache(true);  // Whether to allow the cache (1) is true, after the completion of the request to update the cache, (2) is false, use the request data directly, not update the cache after the request is completed
        cacheConfig.setUseCacheDataAnyway(false); //Whether always use the cache data (1) is true Directly using the cache data, regardless of the cache have expired, and update the cache after the request is completed. If the cache does not exist, the network request, the request is completed using the latest data and update the cache
        cacheConfig.setUseCacheDataWhenRequestFailed(true);  //When the network request failure whether or not to use the cache data (1) is true The use of cache request fails, (2) is false, the request fails not using the cache
        cacheConfig.setUseCacheDataWhenTimeout(true); //When the network request timeout after directly using the cache data, behind after completion of the network request to update the cache
        cacheConfig.setUseCacheDataWhenUnexpired(true);  //When the cache is not expire whether to use the cached data (1) is true In the presence of the cached data and has not expired, use the cache data, if the cache does not exist or has expired, the network request, the request is completed using the latest data, and if the cache is allowed, (2) to false update cache requests for network directly after the request is completed using the latest data, and if allowed to cache, update the cache
        return cacheConfig;
        }*/

