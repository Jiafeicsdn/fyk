package com.xdroid.request.extension.queue;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Executor;

/**
 * execution queue of threads
 * @author Robin
 * @since 2015-05-07 10:11:58
 */
public class ThreadQueue extends Thread {
	
	@SuppressWarnings("rawtypes")
	private static LinkedList<ThreadUnit> mThreadItemList = null;
    
    /**
     * Singleton
     */
  	private static ThreadQueue mThreadQueue = null; 
  	
  	/**
  	 *  stop flag. 
  	 */
	private boolean mQuit = false;
	
	/**  
	 * Dim MyReturn As Long. 
	 */
    private static HashMap<String,Object> mResultMap;
	
    private static Handler handler = new Handler() { 
        @SuppressWarnings({ "rawtypes", "unchecked" })
		@Override 
        public void handleMessage(Message msg) { 
        	ThreadUnit mThreadUnit = (ThreadUnit)msg.obj; 
        	if (mThreadUnit.getListener()!=null) {
				mThreadUnit.getListener().onFinish(mResultMap.get(mThreadUnit.toString()));
			}
        	mResultMap.remove(mThreadUnit.toString());
        } 
    }; 
    
    /**
     * Singleton
     * @return
     */
    public static ThreadQueue getInstance() { 
        if (mThreadQueue == null) { 
        	mThreadQueue = new ThreadQueue();
        } 
        return mThreadQueue;
    } 
	
    @SuppressWarnings("rawtypes")
	public ThreadQueue() {
    	mQuit = false;
    	mThreadItemList = new LinkedList<ThreadUnit>();
    	mResultMap = new HashMap<String,Object>();
    	
    	Executor mExecutorService  = ThreadPoolFactory.getExecutorService();
    	mExecutorService.execute(this); 
    }
    
    /**
     * Start a task.
     * @param mThreadUnit 
     */
    @SuppressWarnings("rawtypes")
	public void add(ThreadUnit mThreadUnit) { 
    	addThreadUnit(mThreadUnit); 
    } 
    
    
    /**
     * Start a mission and remove the original queue
     * @param mThreadUnit 
     * @param cancelOriginal Empty before tasks
     */
    @SuppressWarnings("rawtypes")
	public void add(ThreadUnit mThreadUnit,boolean cancelOriginal) { 
	    if(cancelOriginal){
	    	 cancel(true);
	    }
	    addThreadUnit(mThreadUnit); 
    } 
    
    /**
     * Added to the thread of execution queue
     * @param mThreadUnit 
     */
    @SuppressWarnings("rawtypes")
	private synchronized void addThreadUnit(ThreadUnit mThreadUnit) { 
    	if (mThreadQueue == null) { 
    		mThreadQueue = new ThreadQueue();
        }
    	mThreadItemList.add(mThreadUnit);
    	
        this.notify();
        
    } 
    
	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
        while(!mQuit) { 
        	try {
        	    while(mThreadItemList.size() > 0){
        	    	// get a thread unit from list
					ThreadUnit mThreadUnit  = mThreadItemList.remove(0);
					
				    if (mThreadUnit.getListener() != null) {
				    	mResultMap.put(mThreadUnit.toString(), mThreadUnit.getListener().doInThread());
				    	
				    	Log.e("ThreadQueue","In the UI thread's hands");
				        Message msg = handler.obtainMessage(); 
				        msg.obj = mThreadUnit; 
				        handler.sendMessage(msg); 
				    } 
				    
				    // if stop then clear the list 
				    if(mQuit){
				    	mThreadItemList.clear();
				    	return;
				    }
        	    }
        	    try {
					// if no thread unit to execute then wait
					synchronized(this) { 
					    this.wait();
					}
				} catch (InterruptedException e) {
					Log.e("ThreadQueue","Receive the thread interrupt request");
					e.printStackTrace();
					// Is interrupted by the exit ends, or to continue
					if (mQuit) {
						mThreadItemList.clear();
	                    return;
	                }
	                continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
        } 
	}
	
    /**
     * Release the thread queue.
     *
     * @param mayInterruptIfRunning the may interrupt if running
     */
    public void cancel(boolean mayInterruptIfRunning){
		mQuit  = true;
		if(mayInterruptIfRunning){
			interrupted();
		}
		mThreadQueue  = null;
    }
}
