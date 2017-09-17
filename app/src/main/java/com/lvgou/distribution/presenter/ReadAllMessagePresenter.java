package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ReadAllMessageImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Snow on 2016/10/27.
 */
public class ReadAllMessagePresenter extends BasePresenter<GroupView> {


    private GroupView groupView;
    private ReadAllMessageImpl readAllMessageImpl;
    private Handler mHandler;

    public ReadAllMessagePresenter(GroupView groupView) {
        this.groupView = groupView;
        readAllMessageImpl = new ReadAllMessageImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 标记已读
     *
     * @param distributorid
     * @param sign
     */
    public void doAllActivityRead(String distributorid, String sign) {
        readAllMessageImpl.readAllActivity(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
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
