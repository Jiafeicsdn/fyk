package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.CollectionListImpl;
import com.lvgou.distribution.view.CollectionListView;

/**
 * Created by Administrator on 2017/4/24.
 */

public class CollectionListPresenter extends BasePresenter<CollectionListView> {
    private CollectionListImpl collectionListImpl;
    private CollectionListView collectionListView;
    private Handler mHandler;

    public CollectionListPresenter(CollectionListView collectionListView) {
        this.collectionListView = collectionListView;
        collectionListImpl = new CollectionListImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 笑话/顺口溜-收藏列表
     *
     * @param distributorid
     * @param type          类型 1=笑话 2=顺口溜
     * @param pageindex
     * @param sign
     * @return
     */
    public void collectionList(String distributorid, int type, int pageindex, String sign) {
        collectionListImpl.collectionList(distributorid, type, pageindex, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectionListView.closeCollectionListProgress();
                        collectionListView.OnCollectionListSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectionListView.closeCollectionListProgress();
                        collectionListView.OnCollectionListFialCallBack("1", s);
                    }
                });
            }
        });
    }

}