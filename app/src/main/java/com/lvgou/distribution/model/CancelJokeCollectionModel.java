package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/31.
 */

public interface CancelJokeCollectionModel {
    /**
     * 笑话顺口溜-取消收藏
     *
     * @param distributorid 导游ID
     * @param jokeid        笑话/顺口溜ID
     * @param sign          签名
     * @return
     */
    void cancelJokeCollection(String distributorid, String jokeid, String sign, ICallBackListener callBackListener);
}
