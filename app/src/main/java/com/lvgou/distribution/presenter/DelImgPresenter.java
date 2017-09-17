package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.DelImgImpl;
import com.lvgou.distribution.view.DelImgView;

/**
 * Created by Administrator on 2017/4/28.
 */

public class DelImgPresenter extends BasePresenter<DelImgView> {
    private DelImgImpl delImgImpl;
    private DelImgView delImgView;
    private Handler mHandler;

    public DelImgPresenter(DelImgView delImgView) {
        this.delImgView = delImgView;
        delImgImpl = new DelImgImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 删除服务图片
     *
     * @param imgPath
     * @param sign
     * @return
     */
    public void delImg(String imgPath, String sign) {
        delImgImpl.delImg(imgPath, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        delImgView.closeDelImgProgress();
                        delImgView.OnDelImgSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        delImgView.closeDelImgProgress();
                        delImgView.OnDelImgFialCallBack("1", s);
                    }
                });
            }
        });
    }

}