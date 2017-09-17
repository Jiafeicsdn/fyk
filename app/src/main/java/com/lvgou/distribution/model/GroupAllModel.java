package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.GroupAllBean;

/**
 * Created by Snow on 2016/9/30.
 */
public interface GroupAllModel {
    /**
     * 全部团
     *
     * @param groupAllBean
     * @param callBackListener
     */
    void getAllGroup(GroupAllBean groupAllBean, ICallBackListener callBackListener);


    /**
     * 热门城市
     *
     * @param distributorid
     * @param sign
     * @param callBackListener
     */
    void getHotCity(String distributorid, String sign, ICallBackListener callBackListener);
}


