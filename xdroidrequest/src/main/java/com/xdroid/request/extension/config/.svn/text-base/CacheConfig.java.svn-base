package com.xdroid.request.extension.config;

/**
 * cache used configuration
 * @author Robin
 * @since 2015-05-07 13:06:54
 */
public class CacheConfig {

	/**
	 * whether allow cache data
	 */
	private boolean shouldCache;
	
	/**
	 * use cache data first ,no matter the cache have expired ,then update cache when request finish
	 */
	private boolean useCacheDataAnyway;
	
	/**
	 * use cache data if request failed
	 */
	private boolean useCacheDataWhenRequestFailed;
	
	/**
	 * use cache data if the cache data is not expired
	 */
	private boolean useCacheDataWhenUnexpired;
	
	/**
	 * use cache data if timeout
	 */
	private boolean useCacheDataWhenTimeout;
	
	/**
	 * control expirationtime and timeout
	 */
	private TimeController timeController;
	
	public boolean isShouldCache() {
		return shouldCache;
	}
	public void setShouldCache(boolean shouldCache) {
		this.shouldCache = shouldCache;
	}
	public boolean isUseCacheDataAnyway() {
		return useCacheDataAnyway;
	}
	public void setUseCacheDataAnyway(boolean useCacheDataAnyway) {
		this.useCacheDataAnyway = useCacheDataAnyway;
	}
	public boolean isUseCacheDataWhenRequestFailed() {
		return useCacheDataWhenRequestFailed;
	}
	public void setUseCacheDataWhenRequestFailed(
			boolean useCacheDataWhenRequestFailed) {
		this.useCacheDataWhenRequestFailed = useCacheDataWhenRequestFailed;
	}
	public boolean isUseCacheDataWhenUnexpired() {
		return useCacheDataWhenUnexpired;
	}
	public void setUseCacheDataWhenUnexpired(boolean useCacheDataWhenUnexpired) {
		this.useCacheDataWhenUnexpired = useCacheDataWhenUnexpired;
	}

	public boolean isUseCacheDataWhenTimeout() {
		return useCacheDataWhenTimeout;
	}
	public void setUseCacheDataWhenTimeout(boolean useCacheDataWhenTimeout) {
		this.useCacheDataWhenTimeout = useCacheDataWhenTimeout;
	}
	
/*	public long getExpirationTime() {
		return timeController.getExpirationTime();
	}
	public void setExpirationTime(long expirationTime) {
		timeController.setExpirationTime(expirationTime);
	}
	
	public long getTimeout() {
		return timeController.getTimeout();
	}
	public void setTimeout(long timeout) {
		timeController.setTimeout(timeout);
	}*/
	public TimeController getTimeController() {
		return timeController;
	}
	public void setTimeController(TimeController timeController) {
		this.timeController = timeController;
	}
	
	
}
