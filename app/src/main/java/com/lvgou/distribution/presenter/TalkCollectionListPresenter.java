package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.TalkCollectionListImpl;
import com.lvgou.distribution.view.TalkCollectionListView;

/**
 * Created by Administrator on 2017/4/24.
 */

public class TalkCollectionListPresenter extends BasePresenter<TalkCollectionListView> {
    private TalkCollectionListImpl talkCollectionListImpl;
    private TalkCollectionListView talkCollectionListView;
    private Handler mHandler;

    public TalkCollectionListPresenter(TalkCollectionListView talkCollectionListView) {
        this.talkCollectionListView = talkCollectionListView;
        talkCollectionListImpl = new TalkCollectionListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 蜂文收藏列表
     *
     * @param distributorid           导游编号
     * @param prePageLastDataObjectId 上一页最后一条数据的编号(第一页查询传空)
     * @param currPage                当前页
     * @param sign                    签名
     * @return
     */
    public void talkCollectionList(String distributorid,String prePageLastDataObjectId,int currPage,  String sign) {
        talkCollectionListImpl.talkCollectionList(distributorid,prePageLastDataObjectId,currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        talkCollectionListView.closeTalkCollectionListProgress();
                        talkCollectionListView.OnTalkCollectionListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        talkCollectionListView.closeTalkCollectionListProgress();
                        talkCollectionListView.OnTalkCollectionListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}