package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CancelJokeCollectionImpl;
import com.lvgou.distribution.view.CancelJokeCollectionView;

/**
 * Created by Administrator on 2017/3/31.
 */

public class CancelJokeCollectionPersenter extends BasePresenter<CancelJokeCollectionView> {
    private CancelJokeCollectionImpl cancelJokeCollectionImpl;
    private CancelJokeCollectionView cancelJokeCollectionView;
    private Handler mHandler;

    public CancelJokeCollectionPersenter(CancelJokeCollectionView cancelJokeCollectionView) {
        this.cancelJokeCollectionView = cancelJokeCollectionView;
        cancelJokeCollectionImpl = new CancelJokeCollectionImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 笑话顺口溜-取消收藏
     *
     * @param distributorid 导游ID
     * @param jokeid        笑话/顺口溜ID
     * @param sign          签名
     * @return
     */
    public void cancelJokeCollection(String distributorid, String jokeid, String sign) {
        cancelJokeCollectionImpl.cancelJokeCollection(distributorid, jokeid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        cancelJokeCollectionView.closeCancelJokeCollectionProgress();
                        cancelJokeCollectionView.OnCancelJokeCollectionSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        cancelJokeCollectionView.closeCancelJokeCollectionProgress();
                        cancelJokeCollectionView.OnCancelJokeCollectionFialCallBack("1", s);
                    }
                });
            }
        });
    }

}