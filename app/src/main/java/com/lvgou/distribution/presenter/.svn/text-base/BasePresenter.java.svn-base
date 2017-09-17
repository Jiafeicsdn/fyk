package com.lvgou.distribution.presenter;

import com.lvgou.distribution.data.DataManager;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/9/8.
 */
public abstract class BasePresenter<T> {
    public T mView;
    public CompositeSubscription mCompositeSubscription;
    public DataManager mDataManager;
    public void attach(T mView){
        this.mView=mView;
        this.mCompositeSubscription = new CompositeSubscription();
//        this.mDataManager = DataManager.getInstance();
    }
    public void dettach(){
        mView=null;
        this.mCompositeSubscription.unsubscribe();
        this.mCompositeSubscription = null;
//        this.mDataManager = null;
    }
    public boolean isViewAttached() {
        return mView != null;
    }


    public T getMvpView() {
        return mView;
    }

}
