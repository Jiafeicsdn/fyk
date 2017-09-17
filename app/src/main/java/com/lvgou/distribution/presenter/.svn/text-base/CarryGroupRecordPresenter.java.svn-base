package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CarryGroupRecordImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Snow on 2016/10/14.
 */
public class CarryGroupRecordPresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private CarryGroupRecordImpl carryGroupRecordImpl;
    private Handler mHandler;

    public CarryGroupRecordPresenter(GroupView groupView) {
        this.groupView = groupView;
        carryGroupRecordImpl = new CarryGroupRecordImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void getCarryGroupRecord(String distributorid, String pageindex, String sign) {
        carryGroupRecordImpl.getCarryGroupRecord(distributorid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("1", s);
                    }
                });
            }
        });
    }
}
