package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.TeacherSearchModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/14.
 */

public class TeacherSearchImpl implements TeacherSearchModel {
    /**
     * 全局搜索-讲师搜索
     *
     * @param distributorid 导游ID
     * @param searchword    搜索关键字
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @Override
    public void teacherSearchDatas(String distributorid, String searchword, int pageindex, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.teacherSearchDatas(distributorid, searchword, pageindex, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
