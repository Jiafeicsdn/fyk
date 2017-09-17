package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.HomeStudyListImpl;
import com.lvgou.distribution.view.HomeStudyListView;

/**
 * Created by Administrator on 2017/4/13.
 */

public class HomeStudyListPresenter extends BasePresenter<HomeStudyListView> {
    private HomeStudyListImpl homeStudyListImpl;
    private HomeStudyListView homeStudyListView;
    private Handler mHandler;

    public HomeStudyListPresenter(HomeStudyListView homeStudyListView) {
        this.homeStudyListView = homeStudyListView;
        homeStudyListImpl = new HomeStudyListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 个人主页课程列表
     *
     * @param distributorid 待查询的导游编号
     * @param currPage      当前页
     * @param sign          签名
     * @return
     */
    public void homeStudyList(String distributorid, int currPage, String sign) {
        homeStudyListImpl.homeStudyList(distributorid, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        homeStudyListView.closeHomeStudyListProgress();
                        homeStudyListView.OnHomeStudyListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        homeStudyListView.closeHomeStudyListProgress();
                        homeStudyListView.OnHomeStudyListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}