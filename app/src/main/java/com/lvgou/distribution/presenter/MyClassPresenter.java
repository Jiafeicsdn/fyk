package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.MyClassesImpl;
import com.lvgou.distribution.view.MyClcassView;

/**
 * Created by Snow on 2016/9/24.
 */
public class MyClassPresenter extends BasePresenter<MyClcassView> {

    private MyClcassView myClcassView;
    private MyClassesImpl myClassesImpl;
    private Handler mHandler;

    public MyClassPresenter(MyClcassView myClcassView) {
        this.myClcassView = myClcassView;
        myClassesImpl = new MyClassesImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 我的课程列表
     *
     * @param distributorid
     * @param pageindex
     * @param sign
     */
    public void getMyClass(String distributorid, String pageindex, String sign) {
        myClassesImpl.getMyClasses(distributorid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myClcassView.closeProgress();
                        myClcassView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myClcassView.closeProgress();
                        myClcassView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }
}
