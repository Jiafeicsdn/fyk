package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.MyTaskImpl;
import com.lvgou.distribution.view.MyTaskView;

/**
 * Created by Administrator on 2017/4/24.
 */

public class MyTaskPresenter extends BasePresenter<MyTaskView> {
    private MyTaskImpl myTaskImpl;
    private MyTaskView myTaskView;
    private Handler mHandler;

    public MyTaskPresenter(MyTaskView myTaskView) {
        this.myTaskView = myTaskView;
        myTaskImpl = new MyTaskImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 我的团币-加载(蜂圈域名)
     * @param distributorid
     * @param sign
     * @return
     */
    public void myTaskList(String distributorid, String sign) {
        myTaskImpl.myTaskList(distributorid,sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myTaskView.closeMyTaskProgress();
                        myTaskView.OnMyTaskSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myTaskView.closeMyTaskProgress();
                        myTaskView.OnMyTaskFialCallBack("1", s);
                    }
                });
            }
        });
    }

}