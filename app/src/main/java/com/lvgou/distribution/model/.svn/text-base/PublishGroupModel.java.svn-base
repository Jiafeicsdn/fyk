package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.PublishGroupBean;
import com.lvgou.distribution.entity.PublishGroupEditBean;

/**
 * Created by Snow on 2016/9/26.
 */
public interface PublishGroupModel {

    /**
     * 发布派团
     *
     * @param publishGroupBean
     * @param callBackListener
     */
    void doPublishGroup(PublishGroupBean publishGroupBean, ICallBackListener callBackListener);


    /**
     * 编辑派团
     *
     * @param publishGroupBean
     * @param callBackListener
     */
    void editPublishGroup(PublishGroupEditBean publishGroupBean, ICallBackListener callBackListener);

    /**
     * 获取详情
     *
     * @param distributorid
     * @param seekid
     * @param sign
     * @param iCallBackListener
     */
    void getPublishGorupInfo(String distributorid, String seekid, String sign, ICallBackListener iCallBackListener);
}
