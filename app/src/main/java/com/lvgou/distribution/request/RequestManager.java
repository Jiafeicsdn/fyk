package com.lvgou.distribution.request;

import com.xdroid.request.extension.config.CacheConfig;
import com.xdroid.request.extension.config.TimeController;
import com.xdroid.request.extension.queue.request.RequestQueue;



/**
 * 网络请求管理类
 * @author Robin
 * @since 2015/5/27 16:26.
 */
public class RequestManager {

    /** Default expiration time */
    private static final long DEFAULT_EXPIRATION_TIME=30*1000;

    /** Default timeout */
    private static final long DEFAULT_TIMEOUT=20*1000;


    private RequestQueue requestQueue;


    /*==============================================================================================
     * 单例
     *==============================================================================================
     */

    private RequestManager() {
    }

    private static class RequestManagerHolder {
        public static final RequestManager INSTANCE = new RequestManager();
    }

    public static RequestManager getInstance() {
        return RequestManagerHolder.INSTANCE;
    }

     /*==============================================================================================
     * Getter And Setter
     *==============================================================================================
     */

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }


    /**
     * 初始化网络请求队列
     */
    public void initRequest(){
        if (requestQueue==null){
            requestQueue=new RequestQueue();
            requestQueue.start();
        }
    }

    /**
     * 停止网络请求队列
     */
    public void stopRequest(){
        if (requestQueue!=null){
            requestQueue.stop();
        }
    }

    /**
     * 获取一个缓存网络请求配置
     * @return
     */
    public CacheConfig getCacheRequestConfig(){
        CacheConfig cacheConfig=new CacheConfig();
        cacheConfig.setShouldCache(true);
        cacheConfig.setUseCacheDataAnyway(false);
        cacheConfig.setUseCacheDataWhenRequestFailed(true);
        cacheConfig.setUseCacheDataWhenTimeout(false);
        cacheConfig.setUseCacheDataWhenUnexpired(true);
        cacheConfig.setTimeController(getTimeController());
        return cacheConfig;
    }

    /**
     * 获取一个普通不缓存的请求配置
     * @return
     */
    public CacheConfig getRequestConfig(){
        CacheConfig cacheConfig=new CacheConfig();
        cacheConfig.setShouldCache(false);
        cacheConfig.setUseCacheDataAnyway(false);
        cacheConfig.setUseCacheDataWhenRequestFailed(false);
        cacheConfig.setUseCacheDataWhenTimeout(false);
        cacheConfig.setUseCacheDataWhenUnexpired(false);
        cacheConfig.setTimeController(getTimeController());
        return cacheConfig;
    }

    /**
     * 获取一个时间控制器
     * @return
     */
    public TimeController getTimeController(){
        TimeController timeController=new TimeController();
        timeController.setExpirationTime(DEFAULT_EXPIRATION_TIME);
        timeController.setTimeout(DEFAULT_TIMEOUT);
        return timeController;
    }

    /**
     * 取消网络请求
     * @param tag
     */
    public void cancelAllRequest(Object tag){
        requestQueue.cancelAll(tag);
    }

}
