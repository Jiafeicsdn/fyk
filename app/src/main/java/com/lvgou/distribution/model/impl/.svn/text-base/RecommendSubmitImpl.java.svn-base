package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.CheckLevelUpModel;
import com.lvgou.distribution.model.RecommendSubmitModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/28.
 */

public class RecommendSubmitImpl implements RecommendSubmitModel {

    /**
     * 推荐讲师-提交
     *
     * @param distributorid
     * @param teachername
     * @param weixin
     * @param mobile
     * @param intro
     * @param sign
     * @return
     */
    @Override
    public void recommendSubmit(String distributorid, String teachername, String weixin, String mobile, String intro, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.recommendSubmit(distributorid, teachername, weixin, mobile, intro, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
