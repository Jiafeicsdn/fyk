package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.JokeListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/31.
 */

public class JokeListImpl implements JokeListModel {
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
    @Override
    public void jokeList(String distributorid, int type, int order, int pageindex, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.jokeList(distributorid, type, order, pageindex, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}