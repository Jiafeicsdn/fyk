package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.model.impl.SaveDistributorExtendImpl;
import com.lvgou.distribution.view.SaveDistributorExtendView;

/**
 * Created by Administrator on 2017/4/10.
 */

public class SaveDistributorExtendPresenter extends BasePresenter<SaveDistributorExtendView> {
    private SaveDistributorExtendImpl saveDistributorExtendImpl;
    private SaveDistributorExtendView saveDistributorExtendView;
    private Handler mHandler;

    public SaveDistributorExtendPresenter(SaveDistributorExtendView saveDistributorExtendView) {
        this.saveDistributorExtendView = saveDistributorExtendView;
        saveDistributorExtendImpl = new SaveDistributorExtendImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * @param distributorid    导游Id

     * @param countryPath      所在城市
     * @param workDay          从业时间
     * @param education        学历（1=研究生及以上、2=本科、3=专科、4=中专、5=高中、6=其它）
     * @param certificateLevel 证件等级（1=初级、2=中级、3=高级、4=特级）
     * @param languageType     语言类型（1=中文导游、2=外语导游）
     * @param attr             业务范围（1=全陪，2=地接，4=领队（当有选择领队时，必须选择领队线路），8=景讲，16=计调，32=其它）
     * @param attrLine         领队线路（可多选）（位运算）：1=港澳台线路，2=日韩线路，4=东南亚线路，8=中东非线路，16=澳新线路，32=欧洲线路，64=地中海线路，128=南北美洲线路，256=极地线路
     * @param courseType       课程类型（1=地方课程，2=领队课程，4=基础课程，8=实战课程，16=团型课程，32=语言课程，64=才艺课程，128=职业课程）
     * @param picUrl           资质证件照片路径
     * @param sign             签名
     * @return
     */
    public void saveDistributorExtend(String distributorid, int sexgander, String countryPath, String workDay, String education,
                                      String certificateLevel, String languageType, String attr, String attrLine, String courseType,
                                      String picUrl, String sign) {
        saveDistributorExtendImpl.saveDistributorExtend(distributorid, sexgander, countryPath, workDay, education, certificateLevel, languageType,
                attr, attrLine, courseType, picUrl, sign, new ICallBackListener() {
                    @Override
                    public void onSuccess(final String s) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                saveDistributorExtendView.closeSaveDistributorExtendProgress();
                                saveDistributorExtendView.OnSaveDistributorExtendSuccCallBack("1", s);
                            }
                        });
                    }

                    @Override
                    public void onFaild(final String s) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                saveDistributorExtendView.closeSaveDistributorExtendProgress();
                                saveDistributorExtendView.OnSaveDistributorExtendFialCallBack("1", s);
                            }
                        });
                    }
                });
    }

}