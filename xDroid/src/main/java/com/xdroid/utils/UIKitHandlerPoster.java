/*
add        增加一个元索                     如果队列已满，则抛出一个IIIegaISlabEepeplian异常
remove   移除并返回队列头部的元素    如果队列为空，则抛出一个NoSuchElementException异常
element  返回队列头部的元素             如果队列为空，则抛出一个NoSuchElementException异常
offer       添加一个元素并返回true       如果队列已满，则返回false
poll         移除并返问队列头部的元素    如果队列为空，则返回null
peek       返回队列头部的元素             如果队列为空，则返回null
put         添加一个元素                      如果队列满，则阻塞
take        移除并返回队列头部的元素     如果队列为空，则阻塞 
 */

package com.xdroid.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Robin
 * on 2015-01-28 10:31:15.
 */
final class UIKitHandlerPoster extends Handler {
    private static final int ASYNC = 0x1;   //异步
    private static final int SYNC = 0x2;  //同步
    private final Queue<Runnable> mAsyncPool;  //异步Runnable任务队列
    private final Queue<UIKitSyncPost> mSyncPool;  //同步Runnable任务队列
    private final int mMaxMillisInsideHandleMessage;  //占用主线程的时间限制
    private boolean isAsyncActive;  //当前是否处于异步任务执行中
    private boolean isSyncActive;  //当前是否处于同步任务执行中

    UIKitHandlerPoster(Looper looper, int maxMillisInsideHandleMessage) {
        super(looper);
        this.mMaxMillisInsideHandleMessage = maxMillisInsideHandleMessage;
        mAsyncPool = new LinkedList<Runnable>();
        mSyncPool = new LinkedList<UIKitSyncPost>();
    }

    /**
     * 去除掉没有处理的消息，从队列彻底移除所有元素
     */
    void dispose() {
        this.removeCallbacksAndMessages(null);
        this.mAsyncPool.clear();
        this.mSyncPool.clear();
    }

    /**
     * 加锁添加一个异步Runnable任务
     * @param runnable
     */
    void async(Runnable runnable) {
        synchronized (mAsyncPool) {
            mAsyncPool.offer(runnable);  //添加一个Runnable任务并返回true  如果队列已满，则返回false
            if (!isAsyncActive) {  //判断当前是否处于异步任务执行中，如果不是：立刻改变状态，然后发送一个消息给当前Handler
                isAsyncActive = true;
                if (!sendMessage(obtainMessage(ASYNC))) {
                    throw new XDroidException("Could not send handler message");
                }
            }
        }
    }

    /**
     * 加锁添加一个同步任务
     * @param post
     */
    void sync(UIKitSyncPost post) {
        synchronized (mSyncPool) {
            mSyncPool.offer(post);
            if (!isSyncActive) {
                isSyncActive = true;
                if (!sendMessage(obtainMessage(SYNC))) {
                    throw new XDroidException("Could not send handler message");
                }
            }
        }
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == ASYNC) {//判断是否是进行异步处理的消息，如果是那么进入该位置。
            boolean rescheduled = false;  
            try {
                long started = SystemClock.uptimeMillis();   //标识开始时间
                while (true) {
                    Runnable runnable = mAsyncPool.poll();   //移除并返问队列头部的Runnable任务 如果队列为空，则返回null
                    if (runnable == null) {
                        synchronized (mAsyncPool) {
                            // Check again, this time in synchronized
                            runnable = mAsyncPool.poll();
                            if (runnable == null) {
                                isAsyncActive = false;
                                return;
                            }
                        }
                    }
                    runnable.run();
                    long timeInMethod = SystemClock.uptimeMillis() - started;  //任务执行结束时间与任务开始时间差
                    if (timeInMethod >= mMaxMillisInsideHandleMessage) {   //当执行一个任务后就判断一次如果超过了每次占用主线程的时间限制，那么不管队列中的任务是否执行完成都退出，同时发起一个新的消息到Handler循环队列
                        if (!sendMessage(obtainMessage(ASYNC))) {
                            throw new XDroidException("Could not send handler message");
                        }
                        rescheduled = true;
                        return;
                    }
                }
            } finally {
                isAsyncActive = rescheduled;
            }
        } else if (msg.what == SYNC) {
            boolean rescheduled = false;
            try {
                long started = SystemClock.uptimeMillis();
                while (true) {
                    UIKitSyncPost post = mSyncPool.poll();
                    if (post == null) {
                        synchronized (mSyncPool) {
                            // Check again, this time in synchronized
                            post = mSyncPool.poll();
                            if (post == null) {
                                isSyncActive = false;
                                return;
                            }
                        }
                    }
                    post.run();
                    long timeInMethod = SystemClock.uptimeMillis() - started;
                    if (timeInMethod >= mMaxMillisInsideHandleMessage) {
                        if (!sendMessage(obtainMessage(SYNC))) {
                            throw new XDroidException("Could not send handler message");
                        }
                        rescheduled = true;
                        return;
                    }
                }
            } finally {
                isSyncActive = rescheduled;
            }
        } else super.handleMessage(msg);
    }
}