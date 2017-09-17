package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.QianDaoImpl;
import com.lvgou.distribution.view.QianDaoView;

/**
 * Created by Administrator on 2017/4/27.
 */

public class QianDaoPresenter extends BasePresenter<QianDaoView> {
    private QianDaoImpl qianDaoImpl;
    private QianDaoView qianDaoView;
    private Handler mHandler;

    public QianDaoPresenter(QianDaoView qianDaoView) {
        this.qianDaoView = qianDaoView;
        qianDaoImpl = new QianDaoImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * APP登录日志
     *
     * @param distributorid
     * @param sign
     * @return
     */
    public void qianDao(String distributorid, String sign) {
        qianDaoImpl.qianDao(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        qianDaoView.closeQianDaoProgress();
                        qianDaoView.OnQianDaoSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        qianDaoView.closeQianDaoProgress();
                        qianDaoView.OnQianDaoFialCallBack("1", s);
                    }
                });
            }
        });
    }

}