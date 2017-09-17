package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TaskOperateImpl;
import com.lvgou.distribution.view.TaskOperateView;

/**
 * Created by Administrator on 2017/4/24.
 */

public class TaskOperatePresenter extends BasePresenter<TaskOperateView> {
    private TaskOperateImpl taskOperateImpl;
    private TaskOperateView taskOperateView;
    private Handler mHandler;

    public TaskOperatePresenter(TaskOperateView taskOperateView) {
        this.taskOperateView = taskOperateView;
        taskOperateImpl = new TaskOperateImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 我的团币-领取(蜂圈域名)
     *
     * @param distributorid
     * @param type          操作类型（对应值在下方注解）
     * @param sign
     * @return
     */
    public void taskOperate(String distributorid, int type,String sign) {
        taskOperateImpl.taskOperate(distributorid,type,sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        taskOperateView.closeTaskOperateProgress();
                        taskOperateView.OnTaskOperateSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        taskOperateView.closeTaskOperateProgress();
                        taskOperateView.OnTaskOperateFialCallBack("1", s);
                    }
                });
            }
        });
    }

}