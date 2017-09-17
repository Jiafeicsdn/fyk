package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.MyStudyListImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.MyStudyListView;

/**
 * Created by Administrator on 2017/3/24.
 */

public class MyStudyListPresenter extends BasePresenter<MyStudyListView> {
    private MyStudyListImpl myStudyListImpl;
    private MyStudyListView myStudyListView;
    private Handler mHandler;

    public MyStudyListPresenter(MyStudyListView myStudyListView) {
        this.myStudyListView = myStudyListView;
        myStudyListImpl = new MyStudyListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 我听的课
     *
     * @param distributorid 导游ID
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void myStudyListDatas(String distributorid, int pageindex, String sign) {
        myStudyListImpl.myStudyListDatas(distributorid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myStudyListView.closeMyStudyListProgress();
                        myStudyListView.OnMyStudyListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myStudyListView.closeMyStudyListProgress();
                        myStudyListView.OnMyStudyListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}
