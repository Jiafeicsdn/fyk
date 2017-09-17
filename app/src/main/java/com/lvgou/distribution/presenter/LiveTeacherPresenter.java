package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.LiveTeacherImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.LiveTeacherView;

/**
 * Created by Administrator on 2017/3/13.
 */

public class LiveTeacherPresenter extends BasePresenter<LiveTeacherView> {
    private LiveTeacherImpl liveTeacherImpl;
    private LiveTeacherView liveTeacherView;
    private Handler mHandler;

    public LiveTeacherPresenter(LiveTeacherView liveTeacherView) {
        this.liveTeacherView = liveTeacherView;
        liveTeacherImpl = new LiveTeacherImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 直播课程
     *
     * @param distributorid 导游ID
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void liveTeacherDatas(String distributorid, int pageindex,String sign) {
        liveTeacherImpl.liveTeacherDatas(distributorid, pageindex,sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        liveTeacherView.closeProgress();
                        liveTeacherView.OnLiveTeacherSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        liveTeacherView.closeProgress();
                        liveTeacherView.OnLiveTeacherFialCallBack("1", s);
                    }
                });
            }
        });
    }

}
