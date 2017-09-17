package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.RecommentBeeImpl;
import com.lvgou.distribution.view.RecommentBeeView;

/**
 * Created by Snow on 2016/9/21.
 */
public class RecommentBeePresenter extends BasePresenter<RecommentBeeView>{

    private RecommentBeeView recommentBeeView;
    private RecommentBeeImpl recommentBeeImpl;
    private Handler mHandler;


    public RecommentBeePresenter(RecommentBeeView recommentBeeView) {
        this.recommentBeeView = recommentBeeView;
        recommentBeeImpl = new RecommentBeeImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 获取蜂优推荐
     *
     * @param distributorid
     * @param sign
     */
    public void getRecommentBeeData(String distributorid, String sign) {
        recommentBeeImpl.getRecommentBee(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        recommentBeeView.closeProgress();
                        recommentBeeView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        recommentBeeView.closeProgress();
                        recommentBeeView.excuteFailedCallBack(s);
                    }
                });
            }
        });
    }

}
