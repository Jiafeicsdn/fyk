package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.MyTaskListImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Snow on 2016/10/17.
 */
public class MyTaskListPresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private MyTaskListImpl myTaskListImpl;
    private Handler mHanlder;

    public MyTaskListPresenter(GroupView groupView) {
        this.groupView = groupView;
        myTaskListImpl = new MyTaskListImpl();
        mHanlder = new Handler(Looper.getMainLooper());
    }


    /**
     * 获取任务列表
     *
     * @param distributorid
     * @param sign
     */
    public void geMyTaskList(String distributorid, String sign) {
        myTaskListImpl.getMyTaskList(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("1", s);
                    }
                });
            }
        });
    }

    /**
     * 任务操作
     *
     * @param distributorid
     * @param type
     * @param sign
     */
    public void doMyTaskList(String distributorid, String type, String sign) {
        myTaskListImpl.doMyTaskOperate(distributorid, type, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("2", s);
                    }
                });
            }
        });
    }

}
