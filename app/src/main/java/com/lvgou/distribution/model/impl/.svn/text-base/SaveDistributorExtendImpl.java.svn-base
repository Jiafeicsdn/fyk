package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.LoadDistributorExtendModel;
import com.lvgou.distribution.model.SaveDistributorExtendModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/10.
 */

public class SaveDistributorExtendImpl implements SaveDistributorExtendModel {
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
    @Override
    public void saveDistributorExtend(String distributorid, int sexgander, String countryPath, String workDay, String education,
                                      String certificateLevel, String languageType, String attr, String attrLine, String courseType,
                                      String picUrl, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.saveDistributorExtend(distributorid, sexgander, countryPath, workDay, education, certificateLevel, languageType, attr, attrLine, courseType, picUrl, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}