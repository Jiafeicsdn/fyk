package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.LevelDetailImpl;
import com.lvgou.distribution.view.LevelDetailView;

/**
 * Created by Administrator on 2017/4/11.
 */

public class LevelDetailPresenter extends BasePresenter<LevelDetailView> {
    private LevelDetailImpl levelDetailImpl;
    private LevelDetailView levelDetailView;
    private Handler mHandler;

    public LevelDetailPresenter(LevelDetailView levelDetailView) {
        this.levelDetailView = levelDetailView;
        levelDetailImpl = new LevelDetailImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 我的等级
     *
     * @param distributorid 讲课Id
     * @param sign          签名
     * @return
     */
    public void levelDetail(String distributorid, String sign) {
        levelDetailImpl.levelDetail(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        levelDetailView.closeLevelDetailProgress();
                        levelDetailView.OnLevelDetailSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        levelDetailView.closeLevelDetailProgress();
                        levelDetailView.OnLevelDetailFialCallBack("1", s);
                    }
                });
            }
        });
    }

}