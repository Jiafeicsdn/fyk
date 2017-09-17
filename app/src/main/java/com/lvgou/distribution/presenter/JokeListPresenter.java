package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.JokeListImpl;
import com.lvgou.distribution.view.JokeListView;

/**
 * Created by Administrator on 2017/3/31.
 */

public class JokeListPresenter extends BasePresenter<JokeListView> {
    private JokeListImpl jokeListImpl;
    private JokeListView jokeListView;
    private Handler mHandler;

    public JokeListPresenter(JokeListView jokeListView) {
        this.jokeListView = jokeListView;
        jokeListImpl = new JokeListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 笑话顺口溜列表
     *
     * @param distributorid 导游ID
     * @param type          类型 1=笑话 2=顺口溜
     * @param order         排序 笑话：1=最新 2=图文 3=纯文；顺口溜：1=最热 2=最新
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void jokeList(String distributorid, int type, int order, int pageindex, String sign) {
        jokeListImpl.jokeList(distributorid, type, order, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        jokeListView.closeJokeListProgress();
                        jokeListView.OnJokeListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        jokeListView.closeJokeListProgress();
                        jokeListView.OnJokeListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}