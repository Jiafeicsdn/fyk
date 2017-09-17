package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.GroupSendBean;
import com.lvgou.distribution.model.impl.GroupSendImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Administrator on 2016/9/30.
 */
public class GroupSendPresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private GroupSendImpl groupSendImpl;
    private Handler mHandler;

    public GroupSendPresenter(GroupView groupView) {
        this.groupView = groupView;
        groupSendImpl = new GroupSendImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    public void getSendGroup(GroupSendBean groupSendBean) {
        groupSendImpl.getSendGroup(groupSendBean, new ICallBackListener() {
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

}
