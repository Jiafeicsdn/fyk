package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TugouGameHitsImpl;
import com.lvgou.distribution.view.TugouGameHitsView;

/**
 * Created by Administrator on 2017/4/1.
 */

public class TugouGameHitsPresenter extends BasePresenter<TugouGameHitsView> {
    private TugouGameHitsImpl tugouGameHitsImpl;
    private TugouGameHitsView tugouGameHitsView;
    private Handler mHandler;

    public TugouGameHitsPresenter(TugouGameHitsView tugouGameHitsView) {
        this.tugouGameHitsView = tugouGameHitsView;
        tugouGameHitsImpl = new TugouGameHitsImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 小游戏-点击量
     *
     * @param distributorid 导游ID
     * @param gameid        游戏ID
     * @param sign          签名
     * @return
     */
    public void tugouGamesHits(String distributorid,String gameid, String sign) {
        tugouGameHitsImpl.tugouGamesHits(distributorid,gameid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tugouGameHitsView.closeTugouGameHitsProgress();
                        tugouGameHitsView.OnTugouGameHitsSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tugouGameHitsView.closeTugouGameHitsProgress();
                        tugouGameHitsView.OnTugouGameHitsFialCallBack("1", s);
                    }
                });
            }
        });
    }

}