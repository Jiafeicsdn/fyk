package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.JokeOperationImpl;
import com.lvgou.distribution.view.JokeOperationView;

/**
 * Created by Administrator on 2017/3/31.
 */

public class JokeOperationPresenter extends BasePresenter<JokeOperationView> {
    private JokeOperationImpl jokeOperationImpl;
    private JokeOperationView jokeOperationView;
    private Handler mHandler;

    public JokeOperationPresenter(JokeOperationView jokeOperationView) {
        this.jokeOperationView = jokeOperationView;
        jokeOperationImpl = new JokeOperationImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 笑话/顺口溜顶或踩
     *
     * @param distributorid 导游ID
     * @param jokeid        笑话/顺口溜ID
     * @param type          操作类型 1=赞一下、2=踩一下
     * @param sign          签名
     * @return
     */
    public void jokeOperation(String distributorid, String jokeid, int type, String sign) {
        jokeOperationImpl.jokeOperation(distributorid, jokeid, type, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        jokeOperationView.closeJokeOperationProgress();
                        jokeOperationView.OnJokeOperationSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        jokeOperationView.closeJokeOperationProgress();
                        jokeOperationView.OnJokeOperationFialCallBack("1", s);
                    }
                });
            }
        });
    }

}