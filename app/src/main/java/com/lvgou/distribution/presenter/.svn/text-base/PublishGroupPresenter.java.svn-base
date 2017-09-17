package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.PublishGroupBean;
import com.lvgou.distribution.entity.PublishGroupEditBean;
import com.lvgou.distribution.model.impl.PublishGroupImpl;
import com.lvgou.distribution.view.PublishGroupView;

/**
 * Created by Snow on 2016/9/26.
 */
public class PublishGroupPresenter extends BasePresenter<PublishGroupView> {

    private PublishGroupView publishGroupView;
    private PublishGroupImpl publishGroupImpl;
    private Handler mHanlder;

    public PublishGroupPresenter(PublishGroupView publishGroupView) {
        this.publishGroupView = publishGroupView;
        publishGroupImpl = new PublishGroupImpl();
        mHanlder = new Handler(Looper.getMainLooper());
    }

    /**
     * 发布派团
     *
     * @param publishGroupBean
     */
    public void publishGroup(PublishGroupBean publishGroupBean) {
        publishGroupImpl.doPublishGroup(publishGroupBean, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        publishGroupView.closeProgress();
                        publishGroupView.OnPublishSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        publishGroupView.closeProgress();
                        publishGroupView.OnPublishFialCallBack("1", s);
                    }
                });
            }
        });
    }


    /**
     * 编辑派团
     *
     * @param publishGroupBean
     */
    public void editPublishGroup(PublishGroupEditBean publishGroupBean) {
        publishGroupImpl.editPublishGroup(publishGroupBean, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        publishGroupView.closeProgress();
                        publishGroupView.OnPublishSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        publishGroupView.closeProgress();
                        publishGroupView.OnPublishFialCallBack("2", s);
                    }
                });
            }
        });
    }

    /**
     * 获取详情
     *
     * @param distributorid
     * @param seekid
     * @param sign
     */
    public void getDetialInfo(String distributorid, String seekid, String sign) {
        publishGroupImpl.getPublishGorupInfo(distributorid, seekid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        publishGroupView.closeProgress();
                        publishGroupView.OnPublishSuccCallBack("3", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        publishGroupView.closeProgress();
                        publishGroupView.OnPublishFialCallBack("3", s);
                    }
                });
            }
        });
    }
}
