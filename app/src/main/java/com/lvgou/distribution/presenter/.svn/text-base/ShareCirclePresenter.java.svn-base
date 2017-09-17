package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.ShareCircleImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Administrator on 2016/11/15.
 */
public class ShareCirclePresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private ShareCircleImpl shareCircleImpl;
    private Handler mHandler;

    public ShareCirclePresenter(GroupView groupView) {
        this.groupView = groupView;
        shareCircleImpl = new ShareCircleImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void doShareCircle(String distributorid, String sign) {
        shareCircleImpl.shareCircleInfo(distributorid, sign, new ICallBackListener() {
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
                        groupView.OnFamousSuccCallBack("2", s);
                    }
                });
            }
        });
    }
}
