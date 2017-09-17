package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TugouGameImpl;
import com.lvgou.distribution.view.TugouGameView;

/**
 * Created by Administrator on 2017/4/1.
 */

public class TugouGamePresenter extends BasePresenter<TugouGameView> {
    private TugouGameImpl tugouGameImpl;
    private TugouGameView tugouGameView;
    private Handler mHandler;

    public TugouGamePresenter(TugouGameView tugouGameView) {
        this.tugouGameView = tugouGameView;
        tugouGameImpl = new TugouGameImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 小游戏列表
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    public void tugouGame(String distributorid, String sign) {
        tugouGameImpl.tugouGame(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tugouGameView.closeTugouGameProgress();
                        tugouGameView.OnTugouGameSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tugouGameView.closeTugouGameProgress();
                        tugouGameView.OnTugouGameFialCallBack("1", s);
                    }
                });
            }
        });
    }

}