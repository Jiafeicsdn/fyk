package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/24.
 */

public interface TalkCollectionListModel {
    /**
     * 蜂文收藏列表
     *
     * @param distributorid           导游编号
     * @param prePageLastDataObjectId 上一页最后一条数据的编号(第一页查询传空)
     * @param currPage                当前页
     * @param sign                    签名
     * @return
     */
    void talkCollectionList(String distributorid, String prePageLastDataObjectId, int currPage, String sign, ICallBackListener callBackListener);
}
