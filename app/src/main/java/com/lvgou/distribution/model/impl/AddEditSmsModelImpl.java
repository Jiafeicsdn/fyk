package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.bean.AddEditSmsBean;
import com.lvgou.distribution.model.AddEditSmsModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/14.
 */
public class AddEditSmsModelImpl implements AddEditSmsModel {


    /**
     * 编辑新增模板
     *
     * @param addEditSmsBean
     * @param callBackListener
     */
    @Override
    public void addEditSms(AddEditSmsBean addEditSmsBean, ICallBackListener callBackListener) {
        IServiceAPI serviceAPI = Api.getInstance().getGankService();
        serviceAPI.addEditSmsModel(addEditSmsBean)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                        // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}
