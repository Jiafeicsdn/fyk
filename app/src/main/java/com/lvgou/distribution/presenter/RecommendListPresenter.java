package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.adapter.RecommendListImpl;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.view.RecommendListView;


/**
 * Created by Administrator on 2017/4/28.
 */

public class RecommendListPresenter extends BasePresenter<RecommendListView> {
    private RecommendListImpl recommendListImpl;
    private RecommendListView recommendListView;
    private Handler mHandler;

    public RecommendListPresenter(RecommendListView recommendListView) {
        this.recommendListView = recommendListView;
        recommendListImpl = new RecommendListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 推荐讲师-推荐记录
     *
     * @param distributorid
     * @param sign
     * @return
     */
    public void recommendList(String distributorid, int pageindex, String sign) {
        recommendListImpl.recommendList(distributorid, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        recommendListView.closeRecommendListProgress();
                        recommendListView.OnRecommendListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        recommendListView.closeRecommendListProgress();
                        recommendListView.OnRecommendListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}