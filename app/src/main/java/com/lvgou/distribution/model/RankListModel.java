package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Snow on 2016/9/12.
 */
public interface RankListModel {

    /**
     * 排行榜列表
     *
     * @param distributorid
     * @param type
     * @param sign
     */
    public void getList(String distributorid, String type, String sign, ICallBackListener callBackListener);

}
