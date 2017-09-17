package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.GroupAllBean;
import com.lvgou.distribution.model.impl.GroupAllImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Snow on 2016/9/30.
 */
public class GroupAllPresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private GroupAllImpl groupAllImpl;
    private Handler mHandler;

    public GroupAllPresenter(GroupView groupView) {
        this.groupView = groupView;
        groupAllImpl = new GroupAllImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void getAllGroup(GroupAllBean groupAllBean) {
        groupAllImpl.getAllGroup(groupAllBean, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("1", s);
                    }
                });
            }
        });
    }


    public void getHotCity(String distributorid, String sign) {
        groupAllImpl.getHotCity(distributorid, sign, new ICallBackListener() {
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
