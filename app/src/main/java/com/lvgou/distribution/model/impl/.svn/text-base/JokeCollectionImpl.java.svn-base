package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.JokeCollectionModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/31.
 */

public class JokeCollectionImpl implements JokeCollectionModel {
    /**
     * 笑话顺口溜-收藏
     *
     * @param distributorid 导游ID
     * @param jokeid        笑话/顺口溜ID
     * @param sign          签名
     * @return
     */
    @Override
    public void jokeCollection(String distributorid, String jokeid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.jokeCollection(distributorid, jokeid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}