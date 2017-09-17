package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.FengFindCircleModel;
import com.lvgou.distribution.model.FindCircleTopModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/14.
 */

public class FengFindCircleImpl implements FengFindCircleModel {
    /**
     * @param distributorId           导游编号
     * @param keyword                 搜索关键字没有就 ""
     * @param tagId                   热门标签编号没有就""
     * @param prePageLastDataObjectId 上一页最后一条数据的编号(第一页查询"")
     * @param currPage                当前页
     * @param sign                    签名
     * @return
     */
    @Override
    public void fengFindCircle(String distributorId, String keyword, String tagId, String prePageLastDataObjectId, int currPage, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.fengFindCircle(distributorId, keyword, tagId, prePageLastDataObjectId, currPage, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}