package com.xdroid.utils;

/**
 * 同步任务类,对Runnable的简单封装
 * Created by Robin
 * on 2015-01-28 10:32:05.
 */
final class UIKitSyncPost {
    private Runnable mRunnable; 
    private boolean isEnd = false;  //是否执行结束


    UIKitSyncPost(Runnable runnable) {
        this.mRunnable = runnable;
    }

    /**
     * 加锁执行Runnable任务
     */
    public void run() {
        if (!isEnd) {
            synchronized (this) {
                if (!isEnd) {
                    mRunnable.run();
                    isEnd = true;
                    try {
                        this.notifyAll();  //唤醒其他所有线程
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 等待状态，等待其他线程执行完毕
     */
    public void waitRun() {
        if (!isEnd) {
            synchronized (this) {
                if (!isEnd) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 等待状态，等待其他线程执行完毕
     * @param time 等待时间
     * @param cancel 是否取消
     */
    public void waitRun(int time, boolean cancel) {
        if (!isEnd) {
            synchronized (this) {
                if (!isEnd) {
                    try {
                        this.wait(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if (!isEnd && cancel)
                            isEnd = true;
                    }
                }
            }
        }
    }
}
