package com.xdroid.request.extension;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.xdroid.request.extension.cache.CacheData;
import com.xdroid.request.extension.cache.DiskCacheManager;
import com.xdroid.request.extension.cache.MemoryCacheManager;
import com.xdroid.request.extension.config.CacheConfig;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.config.Priority;
import com.xdroid.request.extension.config.TimeController;
import com.xdroid.request.extension.interfaces.OnRequestListener;
import com.xdroid.request.extension.interfaces.ResponseDelivery;
import com.xdroid.request.extension.interfaces.XDroidRequestMethodProvider;
import com.xdroid.request.extension.queue.request.RequestQueue;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.HttpURLConnection;


/**
 * Cache request
 * @author Robin
 * @since 2015-05-07 17:18:06
 * @param <RequestResultDataType>
 */
public abstract class XDroidRequest <RequestResultDataType> implements XDroidRequestMethodProvider<RequestResultDataType>,ResponseDelivery<String> ,Comparable<XDroidRequest<RequestResultDataType>>{
	/** Log tag */
	private static final String Tag = "system.out";
	
	/*
	 * constants
	 */
	/** Default expiration time */
	private static final long DEFAULT_EXPIRATION_TIME=30*1000;
	
	/** Default timeout */
	private static final long DEFAULT_TIMEOUT=20*1000;

	/*
	 * properties
	 */
	private Context context;
	
	/** This request will request's address */
	private String mUrl;
	
    /** An opaque token tagging this request; used for bulk cancellation. */
    private Object mTag;
    
    /** Whether or not this request has been canceled. */
    private boolean mCanceled = false;
	
    /** Default tag for {@link android.net.TrafficStats}. */
    private  int mDefaultTrafficStatsTag;
    
    /** Whether or not a response has been delivered for this request yet. */
    private boolean mResponseDelivered = false;
    
    /** Sequence number of this request, used to enforce FIFO ordering. */
    private Integer mSequence;
    
    /** Priority of this request ,default is "NORMAL" */
    private Priority mPriority=Priority.NORMAL;
    
	/** The request's cache key if this request need to cache */
	private String cacheKey;
	
	/** This request's cookie */
	private String cookie;
	
	/*
	 * Object
	 */
    /** The request queue this request is associated with. */
    private RequestQueue mRequestQueue;

	/** This request's related configuration */
	public CacheConfig cacheConfig;
	
	/** Memory cache manager */
	private MemoryCacheManager<String, CacheData<String>> cacheManager;
	
	/** Disk cache manager */
	private DiskCacheManager<CacheData<String>> diskCacheManager;
	
	/**The callback when this request perform finished */
	private OnRequestListener<?> onRequestListener;
	
	/*===============================================================================
	 *  constructor
	 *===============================================================================
	 */

    public XDroidRequest(Context context){
        this(context,null, null, null,null);
    }
	
	public XDroidRequest(Context context,String url) {
		this(context,null, url, url,null);
	}
	
	public XDroidRequest(Context context,CacheConfig cacheConfig,String url) {
		this(context,cacheConfig, url, url,null);
	}
	
	public XDroidRequest(Context context,CacheConfig cacheConfig,String url,String cacheKey,OnRequestListener<?> onRequestListener) {
		this.context=context;
		this.cacheConfig=cacheConfig;
		this.mUrl =url;
		this.cacheKey= cacheKey;
		this.onRequestListener=onRequestListener;
		
		if (cacheConfig==null) {
			setCacheConfig(buildDefaultCacheConfig());
		}
		mDefaultTrafficStatsTag = findDefaultTrafficStatsTag(url);
		
		cacheManager=new MemoryCacheManager<String, CacheData<String>>();
		
		diskCacheManager=new DiskCacheManager<CacheData<String>>(context);
	}
	
	/**
	 * create a default cache configuration when cacheConfig is null
	 * @return
	 */
	private CacheConfig buildDefaultCacheConfig() {
		CacheConfig cacheConfig=new CacheConfig();
		cacheConfig.setShouldCache(true); 
		cacheConfig.setUseCacheDataAnyway(false); 
		cacheConfig.setUseCacheDataWhenRequestFailed(true); 
		cacheConfig.setUseCacheDataWhenTimeout(false);
		cacheConfig.setUseCacheDataWhenUnexpired(true);  
		
		TimeController timeController=new TimeController();
		timeController.setExpirationTime(DEFAULT_EXPIRATION_TIME);
		timeController.setTimeout(DEFAULT_TIMEOUT);
		cacheConfig.setTimeController(timeController);
		
		return cacheConfig;
	}
	
	/*===============================================================================
	 *  Getters and Setters
	 *===============================================================================
	 */
	
	public CacheConfig getCacheConfig() {
		return cacheConfig;
	}

	public void setCacheConfig(CacheConfig cacheConfig) {
		this.cacheConfig = cacheConfig;
	}
	
	public Context getContext() {
		return context;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		this.mUrl = url;
        this.cacheKey= url;
	}

	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	public void setRequestQueue(RequestQueue mRequestQueue) {
		this.mRequestQueue = mRequestQueue;
	}
	
	public Object getTag() {
		return mTag;
	}

	public void setTag(Object mTag) {
		this.mTag = mTag;
	}
	
	  /**
     * Mark this request as canceled.  No callback will be delivered.
     */
    public void cancel() {
        mCanceled = true;
    }

    /**
     * Returns true if this request has been canceled.
     */
    public boolean isCanceled() {
        return mCanceled;
    }
    
    public void markDelivered() {
        mResponseDelivered = true;
    }
    
    public void resetDelivered(){
    	mResponseDelivered=false;
    }

    public boolean hasHadResponseDelivered() {
        return mResponseDelivered;
    }

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public int getTrafficStatsTag() {
        return mDefaultTrafficStatsTag;
    }
	
	/**
     * @return The hashcode of the URL's host component, or 0 if there is none.
     */
    private static int findDefaultTrafficStatsTag(String url) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            if (uri != null) {
                String host = uri.getHost();
                if (host != null) {
                    return host.hashCode();
                }
            }
        }
        return 0;
    }
    
	public Integer getSequence() {
		return mSequence;
	}
	
	public void setSequence(Integer mSequence) {
		this.mSequence = mSequence;
	}
	
	 public void setPriority(Priority priority) {
		 this.mPriority=priority;
	 }
	
    /**
     * Returns the {@link com.xdroid.request.extension.config.Priority} of this request; {@link com.xdroid.request.extension.config.Priority#NORMAL} by default.
     */
    public Priority getPriority() {
        return mPriority;
    }
	
	public OnRequestListener<?> getOnRequestListener() {
	    return onRequestListener;
    }

    public void setOnRequestListener(OnRequestListener<?> onRequestListener) {
	    this.onRequestListener = onRequestListener;
    }
    
		/*===============================================================================
		 *  Override XDroidRequestMethodProvider
		 *===============================================================================
		 */

		/*@Override
		public RequestResaultDataType requestBody(String url) {
			return null;
		};*/
		

		@Override
		public void requestPrepare() {
			if (cacheConfig==null) {
				throw new IllegalArgumentException("please use \"setCacheConfig\" method to set a CacheConfig Instance");
			}
			
			if (onRequestListener!=null) {
				onRequestListener.onRequestPrepare();
			}
            Log.d(Tag,"network-http-prepare");
		}

		@Override
		public void doRequest() {
			//requestPrepare();
              requestBody(mUrl);

		}

		@Override
		public void onRequestFinish(RequestResultDataType data) {
			Log.d(Tag,"network-http-complete");
			
	          /*//if already delivered
              if (hasHadResponseDelivered()) {
            	  // release the same request in the "mWaitingRequests" map
                  finish("not-modified");
              }*/
	          
	        /*//If set always use the cache and did not take out the cached data is empty, that have been delivered by "onCacheDataLoadFinish"  
	        if (getCache(getCacheKey())!=null&&cacheConfig.isUseCacheDataAnyway()) {
			}else {
				   postResponse(this, data.toString(),DataType.NETWORK_DATA);
			}*/
	        
	        /*if (isDeliveryResponseWhenRequestFinish()) {
	        	if (onRequestListener!=null) {
					onRequestListener.onRequestFinish(data.toString());
				}
	        	 postResponse(this, data.toString(),DataType.NETWORK_DATA);
	        	 if (cacheConfig.isUseCacheDataWhenTimeout()) {
	        		 //If set the timeout, disable caching data delivery
					setDeliveryResponseWhenCacheLoadFinish(false);
				}
			}*/
	          
	        // If the data has not been delivered, then delivery  
	        if (!hasHadResponseDelivered()) {
	        	if (onRequestListener!=null) {
					onRequestListener.onRequestFinish(data.toString());
				}
	        	postResponse(this, data.toString(),DataType.NETWORK_DATA);
	        	
	        	// Post the response back.
		          this.markDelivered();
			}
	        
	        // Write to cache if applicable.
	        if (this.getCacheConfig().isShouldCache()) {
	               // write memory cache 
	               CacheData<String> cacheData=cacheManager.getDataFromMemoryCache(getCacheKey());
	               if (cacheData!=null) {
					cacheData.setWriteTime(System.currentTimeMillis());
					 Log.d(Tag,"cache-memory-update");
				   }else {
					   cacheData=new CacheData<String>(data.toString(), cacheConfig.getTimeController().getExpirationTime(), System.currentTimeMillis());
		               cacheManager.setDataToMemoryCache(cacheKey, cacheData);
		               Log.d(Tag,"cache-memory-written");
				   }
	               //write disk cache
	              CacheData<String> diskCacheData= diskCacheManager.getDataFromDiskCache(getCacheKey());
	              if (diskCacheData!=null) {
					diskCacheManager.removeData(getCacheKey());
					 Log.d(Tag,"cache-disk-delete-old");
				  }
				   diskCacheData=new CacheData<String>(data.toString(), cacheConfig.getTimeController().getExpirationTime(), System.currentTimeMillis());
	               diskCacheManager.setDataToDiskCache(getCacheKey(), diskCacheData);
	               Log.d(Tag,"cache-disk-written");
	          }
	        
	         //if already delivered
            if (hasHadResponseDelivered()) {
          	  // release the same request in the "mWaitingRequests" map
                finish();
            }
            
		}

		@Override
		public void onRequestFailed(String error) {
			Log.d(Tag,"network-http-failed---->"+error);
			//If set always use the cache and did not take out the cached data is empty, that have been delivered by "onCacheDataLoadFinish"  
			/*if (getCache(getCacheKey())!=null&&cacheConfig.isUseCacheDataAnyway()) {
			}else {
				if (cacheConfig.isUseCacheDataWhenRequestFailed()) {
					//read cache
					CacheData<String> cacheData=getCache(getCacheKey());
					if (cacheData!=null) {
						postResponse(this, cacheData.getData(), DataType.CACHE_DATA);
					}
				}
		  }   */
			/*if (isDeliveryResponseWhenRequestFinish()) {
				//read cache
				CacheData<String> cacheData=getCache(getCacheKey());
				if (cacheData!=null) {
					  // If the data has not been delivered, then delivery  
			       
					
				}
			}*/
			
			 if (!hasHadResponseDelivered()) {
		        	if (onRequestListener!=null) {
						onRequestListener.onRequestFailed(error);
					}
                 if (cacheConfig.isUseCacheDataWhenRequestFailed()) {
                     //read cache
                     CacheData<String> cacheData = getCache(getCacheKey());
                     if (cacheData!=null) {
                         postResponse(this, cacheData.getData(), DataType.CACHE_DATA);
                     }
                 }

                 // Post the response back.
                 this.markDelivered();
				}
			 
			 //if already delivered
	            if (hasHadResponseDelivered()) {
	          	  // release the same request in the "mWaitingRequests" map
	                finish();
	            }
		}
		
		@Override
		public <O> void onTransmitInformation(O obj) {
			if (obj instanceof HttpURLConnection) {
				@SuppressWarnings("unused")
				HttpURLConnection mHurl=(HttpURLConnection) obj;
				Log.i(Tag, "The original request is HttpUrlConnection");
			}else if (obj instanceof HttpClient) {
				@SuppressWarnings("unused")
				DefaultHttpClient mHttpClient=(DefaultHttpClient) obj;
				Log.i(Tag, "The original request is HttpClient");
			}else if (obj instanceof String) {
				String mCookie=(String) obj;
				this.cookie=mCookie;
				Log.i(Tag, "Cookie :"+mCookie);
			}
		}
		
		@Override
		public void onCacheDataLoadFinish(CacheData<String> cacheData) {
			
			/*if (isDeliveryResponseWhenCacheLoadFinish()) {
				if (onRequestListener!=null) {
					onRequestListener.onCacheDataLoadFinish(cacheData);
				}
				postResponse(this, cacheData.getData(),DataType.CACHE_DATA);	
			}else {
				//When set the timeout, unable to deliver the cached data at this time, that have delivered the request data. 
				//Because closed the request data delivery in time after time, so there needs to be reopened request data delivery
				setDeliveryResponseWhenRequestFinish(true);
				//Has delivered the request data, there needs to be reopened the cached data delivery
				setDeliveryResponseWhenCacheLoadFinish(true);
			}*/
			
			  if (!hasHadResponseDelivered()) {
				  if (onRequestListener!=null) {
						onRequestListener.onCacheDataLoadFinish(cacheData);
					}
					postResponse(this, cacheData.getData(),DataType.CACHE_DATA);	
					// Post the response back.
			          this.markDelivered();
				}
			
			 //if already delivered
            if (hasHadResponseDelivered()) {
          	  // release the same request in the "mWaitingRequests" map
                finish();
            }
			
		}
		
		@Override
		public  CacheData<String> getCache(String key) {
			CacheData<String> 	cacheData=cacheManager.getDataFromMemoryCache(key);
			if (cacheData!=null) {
				Log.d(Tag, "cache-hint-memory");
				return cacheData;
			}
			
			cacheData=diskCacheManager.getDataFromDiskCache(key);
			if (cacheData!=null) {
				Log.d(Tag, "cache-hint-disk");
				return cacheData;
			}
			return null;
		}
		
		@Override
		public String getCookie() {
			return cookie;
		}
		
		@Override
		   public void finish() {
		        if (mRequestQueue != null) {
		            mRequestQueue.finish(this);
		        }
		    }
		
		/*===============================================================================
		 *  Override ResponseDelivery
		 *===============================================================================
		 */

		@Override
		public void postResponse(XDroidRequest<?> request, String response,DataType dataType) {
			if (onRequestListener!=null) {
				onRequestListener.onDone(this, response, dataType);
			}
			Log.i(Tag, "delivered-----dataType:"+dataType+"------data"+response+"-----cacheKey:"+getCacheKey());
			
		}


		@Override
		public void postError(XDroidRequest<?> request, String error) {
		}
		
		/*===============================================================================
		 *  Override Comparable
		 *===============================================================================
		 */
		@Override
		public int compareTo(XDroidRequest<RequestResultDataType> another) {
			Priority left = this.getPriority();
	        Priority right = another.getPriority();

	        // High-priority requests are "lesser" so they are sorted to the front.
	        // Equal priorities are sorted by sequence number to provide FIFO ordering.
	        return left == right ?this.mSequence - another.mSequence : right.ordinal() - left.ordinal();
		}


}
