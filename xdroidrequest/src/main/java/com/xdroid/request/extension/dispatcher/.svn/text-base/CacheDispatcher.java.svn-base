/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xdroid.request.extension.dispatcher;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.cache.CacheData;
import com.xdroid.request.extension.interfaces.ResponseDelivery;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * Provides a thread for performing cache triage on a queue of requests.
 *@author Robin
 *@since 2015-05-08 16:20:45
 * Requests added to the specified cache queue are resolved from cache.
 * Any deliverable response is posted back to the caller via a
 * {@link ResponseDelivery}.  Cache misses and responses that require
 * refresh are enqueued on the specified network queue for processing
 * by a {@link NetworkDispatcher}.
 */
public class CacheDispatcher extends Thread {

    private static final boolean DEBUG = true;

	private static final String Tag = "system.out";

    /** The queue of requests coming in for triage. */
    private final BlockingQueue<XDroidRequest<?>> mCacheQueue;

    /** The queue of requests going out to the network. */
    private final BlockingQueue<XDroidRequest<?>> mNetworkQueue;

    /** Used for telling us to die. */
    private volatile boolean mQuit = false;
    
    @SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
    	@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			HashMap<String, Object> hashMap=(HashMap<String, Object>) msg.obj;
			CacheData<String> cacheData = (CacheData<String>) hashMap.get("data");
    		XDroidRequest<?> request=(XDroidRequest<?>) hashMap.get("request");
    		
    		//Reset the current request has not been paid for
            request.resetDelivered();
    		
    		request.onCacheDataLoadFinish(cacheData);
    	};
    };

    /**
     * Creates a new cache triage dispatcher thread.  You must call {@link #start()}
     * in order to begin processing.
     *
     * @param cacheQueue Queue of incoming requests for triage
     * @param networkQueue Queue to post requests that require network to
     */
    public CacheDispatcher(BlockingQueue<XDroidRequest<?>> cacheQueue, BlockingQueue<XDroidRequest<?>> networkQueue) {
        mCacheQueue = cacheQueue;
        mNetworkQueue = networkQueue;
    }

    /**
     * Forces this dispatcher to quit immediately.  If any requests are still in
     * the queue, they are not guaranteed to be processed.
     */
    public void quit() {
        mQuit = true;
        interrupt();
    }

    @Override
    public void run() {
        if (DEBUG) 
        	Log.v(Tag,"start new dispatcher");
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        while (true) {
            try {
                // Get a request from the cache triage queue, blocking until
                // at least one is available.
                final XDroidRequest<?> request = mCacheQueue.take();
                Log.d(Tag,"cache-queue-take");

                // If the request has been canceled, don't bother dispatching it.
                if (request.isCanceled()) {
                    request.finish();
                    Log.e(Tag, "cache-discard-canceled-----------cacheKey:"+request.getCacheKey());
                    continue;
                }
                
                // use the cache data always 
                if (request.getCacheConfig().isUseCacheDataAnyway()) {
                	
                	 // Attempt to retrieve this item from cache.
                    CacheData<String> cacheData=request.getCache(request.getCacheKey());
                    if (cacheData == null) {
                        Log.d(Tag,"cache-miss");
                        // Cache miss; send off to the network dispatcher.
                        mNetworkQueue.put(request);
                        continue;
                    }

                    // We have a cache hit; parse its data for delivery back to the request.
                    Log.d(Tag,"cache-hit");
                    
                    //hand in main thread to call "onCacheDataLoadFinish"
                    Message msg = handler.obtainMessage(); 
                    HashMap<String, Object> hashMap=new HashMap<>();
                    hashMap.put("data", cacheData);
                    hashMap.put("request", request);
			        msg.obj = hashMap; 
			        handler.sendMessage(msg);
                    
                    mNetworkQueue.put(request);
                    
					continue;
				}

                // use the cache data when the cache data is not expired 
                if (request.getCacheConfig().isUseCacheDataWhenUnexpired()) {
                	
                	 // Attempt to retrieve this item from cache.
                    CacheData<String> cacheData=request.getCache(request.getCacheKey());
                    if (cacheData == null) {
                        Log.d(Tag,"cache-miss");
                        // Cache miss; send off to the network dispatcher.
                        mNetworkQueue.put(request);
                        continue;
                    }

                    // If it is completely expired, just send it to the network.
                    if (cacheData.isExpired()) {
                    	Log.d(Tag,"cache-hit-expired");
                        //request.setCacheEntry(entry);
                        mNetworkQueue.put(request);
                        continue;
                    }

                    // We have a cache hit; parse its data for delivery back to the request.
                    Log.d(Tag,"cache-hit");

                    //hand in main thread to call "onCacheDataLoadFinish"
                    Message msg = handler.obtainMessage(); 
                    HashMap<String, Object> hashMap=new HashMap<>();
                    hashMap.put("data", cacheData);
                    hashMap.put("request", request);
			        msg.obj = hashMap; 
			        handler.sendMessage(msg);
                    
				}else {
					   mNetworkQueue.put(request);
				}
               

            } catch (InterruptedException e) {
                // We may have been interrupted because it was time to quit.
                if (mQuit) {
                    return;
                }
                continue;
            }
        }
    }
}
