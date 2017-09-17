package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.AllClassesModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Snow on 2016/9/21.
 */
public class AllClassesImpl implements AllClassesModel {

    /**
     * 获取全部课程
     * @param teacherId
     * @param tag
     * @param pageindex
     * @param type
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getAllClass(String teacherId, String tag, String pageindex, String type, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getAllClasses(teacherId,tag,pageindex,type,sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));

    }


    /**
     * 获取热门标签
     * @param distributorid
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getHotTag(String distributorid, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getHotTag(distributorid,sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }

    /**
     * 获取我的课程
     * @param distributorid
     * @param pageindex
     * @param sign
     * @param callBackListener
     */
    @Override
    public void getMyClassList(String distributorid, String pageindex, String sign, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.getMyClassesList(distributorid,pageindex,sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
