package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.CancelJokeCollectionModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/31.
 */

public class CancelJokeCollectionImpl implements CancelJokeCollectionModel {
    /**
     * 笑话顺口溜-取消收藏
     *
     * @param distributorid 导游ID
     * @param jokeid        笑话/顺口溜ID
     * @param sign          签名
     * @return
     */
    @Override
    public void cancelJokeCollection(String distributorid, String jokeid, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.cancelJokeCollection(distributorid, jokeid, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}