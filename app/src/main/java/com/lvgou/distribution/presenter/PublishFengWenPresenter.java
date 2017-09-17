package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.PublishFengWenMImpl;
import com.lvgou.distribution.view.GroupView;

/**
 * Created by Administrator on 2016/10/19.
 */
public class PublishFengWenPresenter extends BasePresenter<GroupView> {

    private GroupView groupView;
    private PublishFengWenMImpl publishFengWenImpl;
    private Handler mHandler;

    public PublishFengWenPresenter(GroupView groupView) {
        this.groupView = groupView;
        publishFengWenImpl = new PublishFengWenMImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取 Tag
     *
     * @param distributorId
     * @param sign
     */
    public void getTag(String distributorId, String sign) {
        publishFengWenImpl.getTag(distributorId, sign, new ICallBackListener() {
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

    /**
     * 发布蜂文
     *
     * @param distributorId
     * @param tagIds
     * @param content
     * @param picUrls
     * @param sign
     */
    public void publishFengWen(String distributorId, String tagIds, String content, String picUrls, String location, String sign) {
        publishFengWenImpl.publishFengWen(distributorId, tagIds, content, picUrls, location, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        groupView.closeProgress();
                        groupView.OnFamousFialCallBack("2", s);
                    }
                });
            }
        });
    }

}
