package com.xdroid.request.extension.cache;

import android.util.Log;

import java.io.Serializable;

/**
 *  define the cache data entity
 * @author Robin
 * @since 2015-05-08 10:06:46
 */
@SuppressWarnings("serial")
public class CacheData <CacheDataType> implements Serializable{

	private static final String Tag = "system.out";

	/**
	 * will storage's data
	 */
	private CacheDataType data;
	
	/**
	 * the data expired time
	 */
	private long expirationTime;
	
	/**
	 * the data write to cache time
	 */
	private long writeTime;
	
	public CacheData(CacheDataType data, long expirationTime, long writeTime) {
		super();
		this.data = data;
		this.expirationTime = expirationTime;
		this.writeTime = writeTime;
	}

	public CacheDataType getData() {
		return data;
	}

	public void setData(CacheDataType data) {
		this.data = data;
	}

	public long getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}
	
	public long getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(long writeTime) {
		this.writeTime = writeTime;
	}

	/**
	 * check out the data is or not expired
	 * @return
	 */
	public Boolean isExpired(){
		if (writeTime<=0) {
			return true;
		}
		if (expirationTime<=0) {
			return true;
		}
		long intervalTime=System.currentTimeMillis()-writeTime;
		Log.d(Tag, "currentTime:"+System.currentTimeMillis()+"---writeTime:"+writeTime+"----interval:"+intervalTime);
		
		if (intervalTime<expirationTime){
			return false;
		}else {
			// expired
			return true;
		}
	}
	
}
