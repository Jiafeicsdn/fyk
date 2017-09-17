package com.lvgou.distribution.cache;

import android.content.Context;

import com.xdroid.request.extension.cache.DiskCacheManager;
import com.xdroid.request.extension.interfaces.OnCacheDataListener;

import java.util.HashMap;

/**
 * 磁盘缓存读取任务
 * @author Robin
 * @since 2015/5/28 15:25.
 */
public class DiskCacheReadTask {
    private DiskCacheManager<HashMap<String ,Object>> mDiskCacheManager;

    private DiskCacheReadTask() {
    }

    private static class DiskCacheReadTaskHolder {
        public static final DiskCacheReadTask INSTANCE = new DiskCacheReadTask();
    }

    public static DiskCacheReadTask getInstance() {
        return DiskCacheReadTaskHolder.INSTANCE;
    }

    private DiskCacheManager getDiskCacheManager(Context context){
        if (mDiskCacheManager==null){
            mDiskCacheManager=new DiskCacheManager<HashMap<String, Object>>(context);
        }
        return mDiskCacheManager;
    }

    /**
     * 读取Cookie
     * @param context
     * @return
     */
    public String readCookie(Context context){
        HashMap<String,Object> hashMap= (HashMap<String, Object>) getDiskCacheManager(context).getDataFromDiskCache(CacheKey.COOKIE);
        if (hashMap!=null){
            String cookie= (String) hashMap.get(CacheKey.COOKIE_MAP_KEY);
            return cookie;
        }
       return null;
    }

    /**
     * 读取用户信息
     * @param key
     * @param onCacheDataListener
     */
    public void readUserInfo(Context context,String key,OnCacheDataListener<HashMap<String,Object>> onCacheDataListener){
        getDiskCacheManager(context).getDataFromDiskCacheAsync(key, onCacheDataListener);
    }
}
