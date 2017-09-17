package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.GroupSendBean;
import com.lvgou.distribution.model.impl.GroupCarryImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Snow on 2016/9/30.
 */
public class GroupCarryPresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private GroupCarryImpl groupCarryImpl;
    private Handler mHanlder;

    public GroupCarryPresenter(GroupView groupView) {
        this.groupView = groupView;
        groupCarryImpl = new GroupCarryImpl();
        mHanlder = new Handler(Looper.getMainLooper());
    }


    public void getCarryGroup(GroupSendBean groupSendBean) {
        groupCarryImpl.getSendGroup(groupSendBean, new ICallBackListener() {
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
}
