package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.JokeCollectionImpl;
import com.lvgou.distribution.view.JokeCollectionView;

/**
 * Created by Administrator on 2017/3/31.
 */

public class JokeCollectionPresenter extends BasePresenter<JokeCollectionView> {
    private JokeCollectionImpl jokeCollectionImpl;
    private JokeCollectionView jokeCollectionView;
    private Handler mHandler;

    public JokeCollectionPresenter(JokeCollectionView jokeCollectionView) {
        this.jokeCollectionView = jokeCollectionView;
        jokeCollectionImpl = new JokeCollectionImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 笑话顺口溜-收藏
     *
     * @param distributorid 导游ID
     * @param jokeid        笑话/顺口溜ID
     * @param sign          签名
     * @return
     */
    public void jokeCollection(String distributorid, String jokeid, String sign) {
        jokeCollectionImpl.jokeCollection(distributorid, jokeid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        jokeCollectionView.closeJokeCollectionProgress();
                        jokeCollectionView.OnJokeCollectionSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        jokeCollectionView.closeJokeCollectionProgress();
                        jokeCollectionView.OnJokeCollectionFialCallBack("1", s);
                    }
                });
            }
        });
    }

}