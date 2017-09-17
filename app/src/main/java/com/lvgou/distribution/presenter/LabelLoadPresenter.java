package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.LabelLoadImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.LabelLoadView;

/**
 * Created by Administrator on 2017/3/17.
 */

public class LabelLoadPresenter extends BasePresenter<LabelLoadView> {
    private LabelLoadImpl labelLoadImpl;
    private LabelLoadView labelLoadView;
    private Handler mHandler;

    public LabelLoadPresenter(LabelLoadView labelLoadView) {
        this.labelLoadView = labelLoadView;
        labelLoadImpl = new LabelLoadImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 申请开课-课程类型加载
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    public void labelLoadDatas(String distributorid, String sign) {
        labelLoadImpl.labelLoadDatas(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        labelLoadView.closeLabelLoadProgress();
                        labelLoadView.OnLabelLoadSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        labelLoadView.closeLabelLoadProgress();
                        labelLoadView.OnLabelLoadFialCallBack("1", s);
                    }
                });
            }
        });
    }

}
