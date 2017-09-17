package com.xdroid.utils;

import android.os.Looper;

/**
 * UI工具类，用于在子线程中操作UI
 * Created by Robin
 * on 2015-01-28 10:21:53.
 */
final public class UIKit {
    private static UIKitHandlerPoster mainPoster = null;

    private static UIKitHandlerPoster getMainPoster() {
        if (mainPoster == null) {
            synchronized (UIKit.class) {
                if (mainPoster == null) {
                    mainPoster = new UIKitHandlerPoster(Looper.getMainLooper(), 20);  //决定是在主线程执行的HandlerPoster，同时指定主线程单次运行时间为20毫秒
                }
            }
        }
        return mainPoster;
    }

    /**
     * Asynchronously
     * The child thread asynchronous run relative to the main thread,
     * not blocking the child thread
     * 子线程异步运行相对于主线程
     * 不阻塞子线程
     * 在子线程中调用，直接操作UI，不阻塞子线程，即异步切换到主线程,无需等待
     * @param runnable Runnable Interface
     */
    public static void runOnMainThreadAsync(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {  //首先判断调用该方法的是否是主线程，如果是,就直接执行；如果是子线程就调用getMainPoster().async(runnable);追加到队列中执行
            runnable.run();
            return;
        }
        getMainPoster().async(runnable);
    }

    /**
     * Synchronously
     * The child thread relative thread synchronization operation,
     * blocking the child thread,
     * thread for the main thread to complete
     * 子线程相对线程同步操作
     * 阻塞子线程
     * 等待主线程执行完毕
     * 在子线程中操作UI，同时阻塞子线程
     * @param runnable Runnable Interface
     */
    public static void runOnMainThreadSync(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {  //首先判断调用该方法的是否是主线程，如果是,就直接执行；如果是子线程就调用getMainPoster().async(runnable);追加到队列中执行
            runnable.run();
            return;
        }
        UIKitSyncPost poster = new UIKitSyncPost(runnable);
        getMainPoster().sync(poster);
        poster.waitRun();  //进行等待；直到主线程执行了SyncPost类的run方法。
    }

    /**
     * Synchronously
     * The child thread relative thread synchronization operation,
     * blocking the child thread,
     * thread for the main thread to complete
     * But the child thread just wait for the waitTime long.
     *
     * @param runnable Runnable Interface
     * @param waitTime wait for the main thread run Time
     * @param cancel   on the child thread cancel the runnable task
     */
    public static void runOnMainThreadSync(Runnable runnable, int waitTime, boolean cancel) {
        if (Looper.myLooper() == Looper.getMainLooper()) {  //如果当前是主线程
            runnable.run();
            return;
        }
        UIKitSyncPost poster = new UIKitSyncPost(runnable);
        getMainPoster().sync(poster);
        poster.waitRun(waitTime, cancel);
    }

    public static void dispose() {
        if (mainPoster != null) {
            mainPoster.dispose();
            mainPoster = null;
        }
    }
}
