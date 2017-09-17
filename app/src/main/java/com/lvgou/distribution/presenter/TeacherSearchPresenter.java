package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TeacherSearchImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;
import com.lvgou.distribution.view.TeacherSearchView;

/**
 * Created by Administrator on 2017/3/14.
 */

public class TeacherSearchPresenter extends BasePresenter<TeacherSearchView> {
    private TeacherSearchImpl teacherSearchImpl;
    private TeacherSearchView teacherSearchView;
    private Handler mHandler;

    public TeacherSearchPresenter(TeacherSearchView teacherSearchView) {
        this.teacherSearchView = teacherSearchView;
        teacherSearchImpl = new TeacherSearchImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 全局搜索-讲师搜索
     *
     * @param distributorid 导游ID
     * @param searchword    搜索关键字
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    public void teacherSearchDatas(String distributorid, String searchword, int pageindex, String sign) {
        teacherSearchImpl.teacherSearchDatas(distributorid, searchword, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherSearchView.closeTeacherSearchProgress();
                        teacherSearchView.OnTeacherSearchSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        teacherSearchView.closeTeacherSearchProgress();
                        teacherSearchView.OnTeacherSearchFialCallBack("1", s);
                    }
                });
            }
        });
    }

}