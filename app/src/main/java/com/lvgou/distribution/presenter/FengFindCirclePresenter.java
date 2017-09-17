package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.FengFindCircleImpl;
import com.lvgou.distribution.view.FengFindCircleView;

/**
 * Created by Administrator on 2017/4/14.
 */

public class FengFindCirclePresenter extends BasePresenter<FengFindCircleView> {
    private FengFindCircleImpl fengFindCircleImpl;
    private FengFindCircleView fengFindCircleView;
    private Handler mHandler;

    public FengFindCirclePresenter(FengFindCircleView fengFindCircleView) {
        this.fengFindCircleView = fengFindCircleView;
        fengFindCircleImpl = new FengFindCircleImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 蜂圈动态
     *
     * @param distributorId           导游编号
     * @param keyword                 搜索关键字没有就 ""
     * @param tagId                   热门标签编号没有就""
     * @param prePageLastDataObjectId 上一页最后一条数据的编号(第一页查询"")
     * @param currPage                当前页
     * @param sign                    签名
     * @return
     */
    public void fengFindCircle(String distributorId, String keyword, String tagId, String prePageLastDataObjectId, int currPage, String sign) {
        fengFindCircleImpl.fengFindCircle(distributorId, keyword, tagId, prePageLastDataObjectId, currPage, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        fengFindCircleView.closeFengFindCircleProgress();
                        fengFindCircleView.OnFengFindCircleSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        fengFindCircleView.closeFengFindCircleProgress();
                        fengFindCircleView.OnFengFindCircleFialCallBack("1", s);
                    }
                });
            }
        });
    }

}