package com.xdroid.request.extension.queue;

import android.os.Process;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * used for create a executor
 * @author Robin
 * @since 2015-05-06 23:39:07
 */
public class ThreadPoolFactory {
	 /** Used for generating monotonically-increasing sequence numbers for thread. */
    private final static AtomicInteger mSequenceGenerator = new AtomicInteger(1);

	public static Executor mExecutorService = null;
	
	private static final int CORE_POOL_SIZE = 5;
	
    private static final int MAXIMUM_POOL_SIZE = 64;
    
    private static final int KEEP_ALIVE = 5;
    
    private static final BlockingQueue<Runnable> mPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(10);
    
    private static ThreadFactory mThreadFactory=new ThreadFactory() {
		
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "XDroidThread #" + mSequenceGenerator.getAndIncrement());
		}
	};
	
    public static Executor getExecutorService() { 
        if (mExecutorService == null) { 
        	int numCores = getNumCores();
        	mExecutorService
	         = new ThreadPoolExecutor(numCores * CORE_POOL_SIZE,numCores * MAXIMUM_POOL_SIZE,numCores * KEEP_ALIVE,
                    TimeUnit.SECONDS, mPoolWorkQueue, mThreadFactory);
        }
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        return mExecutorService;
    } 
	
	/** 
	 * Gets the number of cores available in this device, across all processors. 
	 * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu" 
	 * @return The number of cores, or 1 if failed to get result 
	 */ 
	public static int getNumCores() { 
		try { 
			//Get directory containing CPU info 
			File dir = new File("/sys/devices/system/cpu/"); 
			//Filter to only list the devices we care about 
			File[] files = dir.listFiles(new FileFilter(){

				@Override
				public boolean accept(File pathname) {
					//Check if filename is "cpu", followed by a single digit number 
					if(Pattern.matches("cpu[0-9]", pathname.getName())) { 
					   return true; 
				    } 
				    return false; 
				}
				
			}); 
			//Return the number of cores (virtual CPU devices) 
			return files.length; 
		} catch(Exception e) { 
			e.printStackTrace();
			return 1; 
		} 
	} 
}
