package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.RankListImpl;
import com.lvgou.distribution.view.RankListView;


/**
 * Created by Administrator on 2016/9/12.
 */
public class RankListPresenter extends BasePresenter<RankListView> {

    private RankListImpl rankListImpl;
    private RankListView rankListView;
    private Handler mHandler;

    public RankListPresenter(RankListView rankListView) {
        this.rankListView = rankListView;
        rankListImpl = new RankListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 网络获取数据
     * @param distributorid
     * @param type
     * @param sign
     */
    public void getDataList(String distributorid, String type, String sign) {
        rankListImpl.getList(distributorid, type, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        rankListView.excuteSuccessCallBack(s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        rankListView.excuteSuccessCallBack(s);
                    }
                });
            }
        });
    }
}
