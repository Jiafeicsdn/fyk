package com.xdroid.request.extension.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

import com.xdroid.request.extension.cache.diskcache.DiskLruCache;
import com.xdroid.request.extension.interfaces.OnCacheDataListener;
import com.xdroid.request.extension.queue.ThreadListener;
import com.xdroid.request.extension.queue.ThreadQueue;
import com.xdroid.request.extension.queue.ThreadUnit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * manage the disk cache
 * @author Robin
 * @since 2015-05-07 23:31:23
 */
public class DiskCacheManager <ValueType>{

	private  DiskLruCache mDiskLruCache;
	
	private  ThreadQueue mThreadQueue;
	
	private static final int DEFAULT_VALUE_COUNT=1;
	
	private static final int DEFAULT_MAX_SIZE=10*1024*1024;
	
	public  DiskCacheManager (Context context) {
		init(context);
	}

	private  void init(Context context) {
		if (mDiskLruCache==null) {
			File cacheDir = getDiskCacheDir(context, "reqeust");  
	         if (!cacheDir.exists()) {  
	             cacheDir.mkdirs();  
	         } 
	         try {
				mDiskLruCache=DiskLruCache.open(cacheDir, getAppVersion(context), DEFAULT_VALUE_COUNT, DEFAULT_MAX_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 
		if (mThreadQueue==null) {
			mThreadQueue=new ThreadQueue();
		}
	}
	
	/**
	 * set the data to disk cache
	 * @param originalKey
	 */
	public void setDataToDiskCache(String originalKey,ValueType value){
		 try {  
             String key = hashKeyForDisk(originalKey);  
             DiskLruCache.Editor editor = mDiskLruCache.edit(key);  
             if (editor != null) {  
                 OutputStream outputStream = editor.newOutputStream(0);  
                 ObjectOutputStream oos=new ObjectOutputStream(outputStream);
                 if (value!=null) {
					oos.writeObject(value);
				}
                 /*if (oos!=null) {
					oos.close();
				}*/
                 
                 if (getDataFromDiskCache(originalKey)==null) {  
                     editor.commit();  
                 } else {  
                     editor.abort();  
                 }  
             }  
             mDiskLruCache.flush();  
         } catch (IOException e) {  
             e.printStackTrace();  
         } 

	}
	
	/**
	 * set the data to disk cache by async
	 * @param originalKey
	 * @param value
	 * @param onCacheDataListener
	 */
	public void setDataToDiskCacheAsync(final String originalKey,final ValueType value,final OnCacheDataListener<ValueType> onCacheDataListener){
		ThreadUnit<ValueType> threadUnit=new ThreadUnit<ValueType>();
		threadUnit.setListener(new ThreadListener<ValueType>() {
			
			@Override
			public void onFinish(ValueType data) {
				onCacheDataListener.onFinish(data);
			}
			
			@Override
			public ValueType doInThread() {
				setDataToDiskCache(originalKey, value);
				return value;
			}
		});
		mThreadQueue.add(threadUnit);
	}
	
	/**
	 * read the data from disk cache
	 * @return
	 */
	public ValueType getDataFromDiskCache(String originalKey){
		try {  
			    String key = hashKeyForDisk(originalKey);  
			    DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);  
			    if (snapShot != null) {  
			        InputStream is = snapShot.getInputStream(0);  
			        ObjectInputStream ois=new ObjectInputStream(is);
			        try {
			        	@SuppressWarnings("unchecked")
						ValueType value=(ValueType) ois.readObject();
						return value;
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
			    }  
			} catch (IOException e) {  
			    e.printStackTrace();  
			    return null;
			}
		return null; 

	}
	
	/**
	 * delete one data
	 * @param originalKey
	 * @return
	 */
	public Boolean removeData(String originalKey){
		try {
			String key = hashKeyForDisk(originalKey);  
			return mDiskLruCache.remove(key);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * read the data from disk cache by Async
	 */
	public void getDataFromDiskCacheAsync(final String originalKey,final OnCacheDataListener<ValueType> onCacheDataListener){
		ThreadUnit<ValueType> threadUnit=new ThreadUnit<>();
		threadUnit.setListener(new ThreadListener<ValueType>() {
			
			@Override
			public void onFinish(ValueType data) {
				onCacheDataListener.onFinish(data);
			}
			
			@Override
			public ValueType doInThread() {
				return getDataFromDiskCache(originalKey);
			}
		});
		mThreadQueue.add(threadUnit);
	}

	public  static String InputStreamToString(InputStream is) throws Exception{
		int BUFFER_SIZE = 4096;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        while((count = is.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        return new String(outStream.toByteArray(),"ISO-8859-1");  
    } 
	
	
	/** 
	 * Using the MD5 algorithm to encrypt the key of the incoming and return. 
	 */  
	  public String hashKeyForDisk(String key) {  
	        String cacheKey;  
	        try {  
	            final MessageDigest mDigest = MessageDigest.getInstance("MD5");  
	            mDigest.update(key.getBytes());  
	            cacheKey = bytesToHexString(mDigest.digest());  
	        } catch (NoSuchAlgorithmException e) {  
	            cacheKey = String.valueOf(key.hashCode());  
	        }  
	        return cacheKey;  
	    }    
	    
	  private String bytesToHexString(byte[] bytes) {
	    	StringBuilder sb = new StringBuilder();  
	        for (int i = 0; i < bytes.length; i++) {  
	            String hex = Integer.toHexString(0xFF & bytes[i]);  
	            if (hex.length() == 1) {  
	                sb.append('0');  
	            }  
	            sb.append(hex);  
	        }  
	        return sb.toString(); 
	     }

	      
	    /** 
	     * Record cache synchronization to the journal file. 
	     */  
	    public void fluchCache() {  
	        if (mDiskLruCache != null) {  
	            try {  
	                mDiskLruCache.flush();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  

	
	private static int getAppVersion(Context context) {
		try {  
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),  
                    0);  
            return info.versionCode;  
        } catch (NameNotFoundException e) {  
            e.printStackTrace();  
        }  
        return 1;  
	}

	/** 
	 * According to the incoming a unique name for the path of the hard disk cache address.
	 */  
	 public static File getDiskCacheDir(Context context, String uniqueName) {  
	        String cachePath;  
	        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	                || !Environment.isExternalStorageRemovable()) {
	        	File cacheDir=context.getExternalCacheDir();
	        	if (cacheDir!=null) {
	        		cachePath = cacheDir .getPath();    ///sdcard/Android/data/<application package>/cache 
				}else {
					cachePath = context.getCacheDir().getPath();  // /data/data/<application package>/cache 
				}
	            
	        } else {  
	            cachePath = context.getCacheDir().getPath();  // /data/data/<application package>/cache 
	        }  
	        return new File(cachePath + File.separator + uniqueName);  
	    } 
	   

}
