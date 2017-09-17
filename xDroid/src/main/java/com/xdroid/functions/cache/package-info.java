/**
 * 缓存包
 * @author Robin
 * time 2015-03-25 11:32:34
 *
 */
package com.xdroid.functions.cache;


/*
   使用方法：
   //设置缓存到本地
   private void setToCache() {

        final JsonData jsonData = JsonData.newMap();
        jsonData.put("uid", "哈哈");
        jsonData.put("name", "嘎嘎");

        CacheManager cacheManager = CacheManagerFactory.getDefault();
        cacheManager.setCacheData(KEY_FOR_USER_CACHE, jsonData.toString());
        Toast.makeText(MainActivity.this, "Set cache successfully.", Toast.LENGTH_SHORT).show();
    }
	
	//从本地缓存读数据
    private void readFromCache() {

        Query<JsonData> query = new Query<JsonData>(CacheManagerFactory.getDefault());
        query.setCacheTime(86400 * 10);
        QueryJsonHandler queryJsonHandler = new QueryJsonHandler() {
            @Override
            public void onQueryFinish(Query.RequestType requestType, JsonData cacheData, boolean outOfDate) {
                if (cacheData != null) {
                    Toast.makeText(MainActivity.this, "uid:"+cacheData.optString("uid")+"name:"+cacheData.optString("name"), 0).show();
                } else {
                    Toast.makeText(MainActivity.this, "No cache is avariable, set first.", 0).show();
                }
            }

            @Override
            public String createDataForCache(Query<JsonData> query) {
                return null;
            }
        };
        query.setHandler(queryJsonHandler);
        query.setCacheKey(KEY_FOR_USER_CACHE);
        query.query();
    }
 
 */