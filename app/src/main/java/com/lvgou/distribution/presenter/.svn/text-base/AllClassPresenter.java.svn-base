package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.AllClassesImpl;
import com.lvgou.distribution.view.AllClassesView;

/**
 * Created by Snow on 2016/9/21.
 */
public class AllClassPresenter extends BasePresenter<AllClassesView> {

    private AllClassesView allClassesView;
    private AllClassesImpl allClassesImpl;
    private Handler mHandlder;

    public AllClassPresenter(AllClassesView allClassesView) {
        this.allClassesView = allClassesView;
        allClassesImpl = new AllClassesImpl();
        mHandlder = new Handler(Looper.getMainLooper());
    }


    /**
     * 全部课程
     *
     * @param teacherId
     * @param tag
     * @param pageindex
     * @param type
     * @param sign
     */
    public void getAllClassesData(String teacherId, String tag, String pageindex, String type, String sign) {
        allClassesImpl.getAllClass(teacherId, tag, pageindex, type, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandlder.post(new Runnable() {
                    @Override
                    public void run() {
                        allClassesView.closeProgress();
                        allClassesView.OnRequestSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandlder.post(new Runnable() {
                    @Override
                    public void run() {
                        allClassesView.closeProgress();
                        allClassesView.OnRequestFialCallBack("1", s);
                    }
                });
            }
        });

    }


    /**
     * 获取热门标签
     *
     * @param distributorid
     * @param sign
     */
    public void getHotTag(String distributorid, String sign) {
        allClassesImpl.getHotTag(distributorid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandlder.post(new Runnable() {
                    @Override
                    public void run() {
                        allClassesView.closeProgress();
                        allClassesView.OnRequestSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandlder.post(new Runnable() {
                    @Override
                    public void run() {
                        allClassesView.closeProgress();
                        allClassesView.OnRequestFialCallBack("2", s);
                    }
                });
            }
        });
    }


    /**
     * 获取热门标签
     *
     * @param distributorid
     * @param sign
     */
    public void getMyClass(String distributorid, String currPage, String sign) {
        allClassesImpl.getMyClassList(distributorid, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandlder.post(new Runnable() {
                    @Override
                    public void run() {
                        allClassesView.closeProgress();
                        allClassesView.OnRequestSuccCallBack("3", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandlder.post(new Runnable() {
                    @Override
                    public void run() {
                        allClassesView.closeProgress();
                        allClassesView.OnRequestFialCallBack("3", s);
                    }
                });
            }
        });
    }


}
