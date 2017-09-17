package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.JokeOperationModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/31.
 */

public class JokeOperationImpl implements JokeOperationModel {
    /**
     * 笑话/顺口溜顶或踩
     *
     * @param distributorid 导游ID
     * @param jokeid        笑话/顺口溜ID
     * @param type          操作类型 1=赞一下、2=踩一下
     * @param sign          签名
     * @return
     */
    @Override
    public void jokeOperation(String distributorid, String jokeid, int type, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.jokeOperation(distributorid, jokeid, type, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}