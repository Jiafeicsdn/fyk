package com.lvgou.distribution.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.LruCache;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.base.BaseApplication;
import com.squareup.okhttp.internal.DiskLruCache;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/30.
 */
public class ImageLoaderEmoji {
    private static final String TAG = "ImageLoaderEmoji";
    private LruCache<String,Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;
    private Context mContext;
    private boolean mIsDiskLruCacheCreated = false;
    private static final int TAG_KEY_URI = R.id.tv_text;
    private static final int IO_BUFFER_SIZE = 8*1024;
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private static final int DISK_CACHE_INDEX = 0;
    private static final int MESSAGE_POST_result = 1;
    private static volatile ImageLoaderEmoji instance;
    //cpu核心数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //cpu核心线程数
    private static final int CORE_POOL_SIZE = CPU_COUNT +1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT *2+1;
    //线程超时时长
    private static final long KEEP_ALIVE = 10l;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"imageLoader#"+mCount.getAndIncrement());
        }
    };
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(),sThreadFactory);
    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            TextView tv = result.textView;
            SpannableString spannableString = result.spannableString;
            String getUrl = (String) tv.getTag(TAG_KEY_URI);
            if(getUrl.equals(result.url)){
                tv.setText(spannableString);
            }else{
                Log.i(TAG, "set image bitmap,but url has changed");
            }
        }
    };
    //无参构造方法，来初始化LruCache|DiskLruCache
//    public ImageLoaderEmoji(Context context){
//        mContext = context.getApplicationContext();
//        initLruCache();
//    }

    public static ImageLoaderEmoji getInstance() {
        if(instance == null) {
            Class var0 = ImageLoaderEmoji.class;
            synchronized(ImageLoaderEmoji.class) {
                if(instance == null) {
                    instance = new ImageLoaderEmoji();
                }
            }
        }

        return instance;
    }

    /**
     * 初始化LruCache
     */
    private void initLruCache(){
        //计算缓存的大小
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //设置缓存大小为当前进程可用内存的1/8
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }
    /**
     * 通过异步方式加载bitmap。
     * 步骤，先从缓存中加载bitmap，如果存在就直接返回，并设置图片。
     * 不存在，就会在线程池中调用loadBitmap方法，来更新UI
     * @param url
     * @param textView
     *
     */
    public void bindBitmap(final String messageId,final SpannableString url, final TextView textView, final Pattern sinaPatten,
                                  final int start){
        textView.setTag(TAG_KEY_URI,messageId);
        //缓存中不存在bitmap图片
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                //线程池中加载bitmap，调用loadBitmap()方法
                try {
                    SpannableString spannableString=dealExpression(url,textView,sinaPatten,start);
                    LoaderResult result = new LoaderResult(textView,messageId,spannableString);
                    mMainHandler.obtainMessage(MESSAGE_POST_result,result).sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }
    public SpannableString dealExpression(String str)
            throws Exception {
        SpannableString spannableString = new SpannableString(str);
        int k = 0;
        String REGEX_STR = "\\[\\d+\\]";
        Pattern patten= Pattern.compile(REGEX_STR, Pattern.CASE_INSENSITIVE);
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            k++;
            String key = matcher.group();
            Log.d("Key", key);
            if (matcher.start() < 0) {
                continue;
            }


//			Field field = R.mipmap.class.getDeclaredField("emoji_" + key.substring(key.indexOf("]") + 1, key.lastIndexOf("[")));
//			int resId = Integer.parseInt(field.get(null).toString());
            int signageId = Integer.parseInt(key.replace('[', ' ').replace(']', ' ').trim());
            Field field = R.mipmap.class.getDeclaredField("emoji_" + signageId);
            int resId = Integer.parseInt(field.get(null).toString());
            if (resId != 0) {
//                Bitmap bitmap = readBitMap(BaseApplication.context, resId);
                Drawable drawable = BaseApplication.context.getResources().getDrawable(resId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth()-20, drawable.getIntrinsicHeight()-20);
                ImageSpan imageSpan = new ImageSpan(drawable);
//				Bitmap bitmap = BitmapFactory.decodeResource(BaseApplication.context.getResources(), resId);
//                ImageSpan imageSpan = new ImageSpan(bitmap);
                int end = matcher.start() + key.length();
                spannableString.setSpan(imageSpan, matcher.start(), end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//                if (end < spannableString.length()) {
//                    dealExpression(context, spannableString, patten, end);
//                }
//                break;
            }
        }
        return spannableString;

    }

    public SpannableString dealExpression(SpannableString string, TextView textView, Pattern patten, int start)
            throws Exception {
        SpannableString spannableString=string;
        int k = 0;
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            k++;
            String key = matcher.group();
            Log.d("Key", key);
            if (matcher.start() < start) {
                continue;
            }


//			Field field = R.mipmap.class.getDeclaredField("emoji_" + key.substring(key.indexOf("]") + 1, key.lastIndexOf("[")));
//			int resId = Integer.parseInt(field.get(null).toString());
            int signageId = Integer.parseInt(key.replace('[', ' ').replace(']', ' ').trim());
            Field field = R.mipmap.class.getDeclaredField("emoji_" + signageId);
            int resId = Integer.parseInt(field.get(null).toString());
            if (resId != 0) {
//                Bitmap bitmap = readBitMap(BaseApplication.context, resId);
                Drawable drawable = BaseApplication.context.getResources().getDrawable(resId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth()-20, drawable.getIntrinsicHeight()-20);
                ImageSpan imageSpan = new ImageSpan(drawable);
//				Bitmap bitmap = BitmapFactory.decodeResource(BaseApplication.context.getResources(), resId);
//                ImageSpan imageSpan = new ImageSpan(bitmap);
                int end = matcher.start() + key.length();
                spannableString.setSpan(imageSpan, matcher.start(), end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//                if (end < spannableString.length()) {
//                    dealExpression(context, spannableString, patten, end);
//                }
//                break;
            }
        }
        return spannableString;

    }
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }




    /**
     * 静态方法创建ImageLoader
     * @return
     */
//    public static ImageLoaderEmoji bindBitmap(Context context){
//        return new ImageLoaderEmoji(context);
//    }

    private class LoaderResult {
        public TextView textView;
        public String url;
        public SpannableString spannableString;
        public LoaderResult(TextView textView, String url, SpannableString spannableString) {
            this.spannableString = spannableString;
            this.textView = textView;
            this.url = url;
        }
    }
}
