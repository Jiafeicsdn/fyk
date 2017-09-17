package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.AddEditSmsBean;
import com.lvgou.distribution.model.impl.AddEditSmsModelImpl;
import com.lvgou.distribution.view.AddEditSmsView;

/**
 * Created by Administrator on 2016/9/14.
 */
public class SmsAddEditPresenter extends BasePresenter<AddEditSmsView> {

    private AddEditSmsModelImpl smsModelImpl;
    private AddEditSmsView smsModelView;
    private Handler mHandler;

    public SmsAddEditPresenter(AddEditSmsView smsModelView) {
        this.smsModelView = smsModelView;
        smsModelImpl = new AddEditSmsModelImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 编辑删除模板
     *
     * @param addEditSmsBean
     */
    public void addEditModel(AddEditSmsBean addEditSmsBean) {
        smsModelImpl.addEditSms(addEditSmsBean, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        smsModelView.closeProgress();
                        smsModelView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        smsModelView.closeProgress();
                        smsModelView.excuteSuccessCallBack(s);
                    }
                });
            }
        });
    }
}
