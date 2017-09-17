package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/1.
 */

public interface TugouGameHitsModel {
    /**
     * 小游戏-点击量
     *
     * @param distributorid 导游ID
     * @param gameid        游戏ID
     * @param sign          签名
     * @return
     */
    void tugouGamesHits(String distributorid, String gameid, String sign, ICallBackListener callBackListener);
}

