package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.StudyShareModel;
import com.lvgou.distribution.model.TalkCollectionListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/24.
 */

public class TalkCollectionListImpl implements TalkCollectionListModel {
    /**
     * 蜂文收藏列表
     *
     * @param distributorid           导游编号
     * @param prePageLastDataObjectId 上一页最后一条数据的编号(第一页查询传空)
     * @param currPage                当前页
     * @param sign                    签名
     * @return
     */
    @Override
    public void talkCollectionList(String distributorid, String prePageLastDataObjectId, int currPage, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.talkCollectionList(distributorid, prePageLastDataObjectId, currPage, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}