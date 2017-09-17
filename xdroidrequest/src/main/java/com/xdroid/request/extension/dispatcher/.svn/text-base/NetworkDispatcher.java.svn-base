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
import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.cache.CacheData;
import com.xdroid.request.extension.utils.NetworkUtils;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

/**
 * Provides a thread for performing network dispatch from a queue of requests.
 *@author Robin
 *@since 2015-05-08 12:30
 */
public class NetworkDispatcher extends Thread {
    private static final String Tag = "system.out";
	/** The queue of requests to service. */
    private final BlockingQueue<XDroidRequest<?>> mQueue;
    /** Used for telling us to die. */
    private volatile boolean mQuit = false;
    
    @SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
    	@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
            XDroidRequest<?> xRequest = (XDroidRequest<?>) msg.obj;
            switch (msg.what) {
                case -1:
                    Log.e(Tag,"NO-NETWORK");
                    xRequest.onRequestFailed("Not found any network connection");
                    break;
                case 0:
                    xRequest.requestPrepare();
                    break;
                case 1:
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) msg.obj;
                    CacheData<String> cacheData = (CacheData<String>) hashMap.get("data");
                    XDroidRequest<?> request = (XDroidRequest<?>) hashMap.get("request");

                    request.onCacheDataLoadFinish(cacheData);
                    break;
            }

        };
    };

    /**
     * Creates a new network dispatcher thread.  You must call {@link #start()}
     * in order to begin processing.
     *
     * @param queue Queue of incoming requests for triage
     */
    public NetworkDispatcher(BlockingQueue<XDroidRequest<?>> queue) {
        mQueue = queue;
    }

    /**
     * Forces this dispatcher to quit immediately.  If any requests are still in
     * the queue, they are not guaranteed to be processed.
     */
    public void quit() {
        mQuit = true;
        interrupt();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void addTrafficStatsTag(XDroidRequest<?> request) {
        // Tag the request (if API >= 14)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            TrafficStats.setThreadStatsTag(request.getTrafficStatsTag());
        }
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (true) {
            XDroidRequest<?> request;
            try {
                // Take a request from the queue.
                request = mQueue.take();
            } catch (InterruptedException e) {
                // We may have been interrupted because it was time to quit.
                if (mQuit) {
                    return;
                }
                continue;
            }

            try {
               Log.d(Tag,"network-queue-take");

                // If the request was cancelled already, do not perform the
                // network request.
                if (request.isCanceled()) {
                    request.finish();
                    Log.e(Tag, "cache-discard-canceled-----------cacheKey:"+request.getCacheKey());
                    continue;
                }

                addTrafficStatsTag(request);

                //Reset the current request has not been paid for
                request.resetDelivered();

                //prepare to request
                Message msg0 = handler.obtainMessage();
                msg0.what=0;
                msg0.obj=request;
                handler.sendMessage(msg0);

                // Perform the network request.
                if (NetworkUtils.checkNet(request.getContext())){
                    request.doRequest();
                }else{

                    Message errorMsg = handler.obtainMessage();
                    errorMsg.what=-1;
                    errorMsg.obj=request;
                    handler.sendMessage(errorMsg);

                }

                
                //if set "UseCacheDataWhenTimeout" 
                if (request.getCacheConfig().isUseCacheDataWhenTimeout()) {
                	final CacheData<String> cacheData=request.getCache(request.getCacheKey());
                	if (cacheData!=null) {
                		final Message msg = handler.obtainMessage();
                        msg.what=1;
  	                    HashMap<String, Object> hashMap=new HashMap<>();
  	                    hashMap.put("data", cacheData);
  	                    hashMap.put("request", request);
  				        msg.obj = hashMap; 
                    	new Timer().schedule(new TimerTask() {
          					
          					@Override
          					public void run() {
          						//hand in main thread to call "onCacheDataLoadFinish"
          				        handler.sendMessage(msg);
          	               
          					}
          				}, request.getCacheConfig().getTimeController().getTimeout());
					}
           
				}
               
                
            } catch (Exception e) {
                Log.e(Tag, "Unhandled exception"+ e.toString());
                request.postError(request, e.toString());
            }
        }
    }

}
