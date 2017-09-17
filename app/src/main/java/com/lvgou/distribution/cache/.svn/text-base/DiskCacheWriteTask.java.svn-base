package com.lvgou.distribution.cache;

import android.content.Context;

import com.xdroid.request.extension.cache.DiskCacheManager;
import com.xdroid.request.extension.interfaces.OnCacheDataListener;

import java.util.HashMap;

/**
 * 磁盘缓存写入任务类
 * @author Robin
 * @since 2015/5/28 15:24.
 */
public class DiskCacheWriteTask {

    private DiskCacheManager<HashMap<String ,Object>> mDiskCacheManager;

    private DiskCacheWriteTask() {
    }

    private static class DiskCacheWriteTaskHolder {
        public static final DiskCacheWriteTask INSTANCE = new DiskCacheWriteTask();
    }

    public static DiskCacheWriteTask getInstance() {
        return DiskCacheWriteTaskHolder.INSTANCE;
    }

    private DiskCacheManager getDiskCacheManager(Context context){
        if (mDiskCacheManager==null){
            mDiskCacheManager=new DiskCacheManager<HashMap<String, Object>>(context);
        }
        return mDiskCacheManager;
    }

    /**
     * 存储Cookie
     * @param context
     * @param cookie
     */
    public void saveCookie(Context context,String cookie){
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put(CacheKey.COOKIE_MAP_KEY,cookie);
        getDiskCacheManager(context).setDataToDiskCacheAsync(CacheKey.COOKIE,map,new OnCacheDataListener() {
            @Override
            public void onFinish(Object data) {

            }
        });
    }

  
}
