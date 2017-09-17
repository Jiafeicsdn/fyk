package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.CollectionListModel;
import com.lvgou.distribution.model.StudyShareModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/24.
 */

public class CollectionListImpl implements CollectionListModel {
    /**
     * 笑话/顺口溜-收藏列表
     *
     * @param distributorid
     * @param type          类型 1=笑话 2=顺口溜
     * @param pageindex
     * @param sign
     * @return
     */
    @Override
    public void collectionList(String distributorid, int type, int pageindex, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.collectionList(distributorid, type, pageindex, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
