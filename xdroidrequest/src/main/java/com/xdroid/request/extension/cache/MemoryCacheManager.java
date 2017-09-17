package com.xdroid.request.extension.cache;

import android.util.LruCache;

/**
 * Manage memory cache
 * @author Robin
 * @since 2015-05-07 23:18:22
 * @param <KeyType>
 * @param <ValueType>
 */
public class MemoryCacheManager <KeyType,ValueType>{
	
	private LruCache<KeyType, ValueType> mMemoryCache;
	
	private int cacheSize =  (int) Runtime.getRuntime().maxMemory() / 8;  
	
	public MemoryCacheManager(){
		if (mMemoryCache==null) {
			mMemoryCache=new LruCache<KeyType,ValueType>(cacheSize){
				@Override
				protected int sizeOf(KeyType key, ValueType value) {
					return super.sizeOf(key, value);
				}
			};
		}
	}
	
	/**
	 * set the data to cache
	 * @param key
	 * @param value
	 */
	public void setDataToMemoryCache(KeyType key,ValueType value){
		if (getDataFromMemoryCache(key)==null) {
			mMemoryCache.put(key, value);
		}
	}
	
	/**
	 * read the data from cache
	 * @param key
	 * @return
	 */
	public ValueType getDataFromMemoryCache(KeyType key){
		return mMemoryCache.get(key);
	}

}
