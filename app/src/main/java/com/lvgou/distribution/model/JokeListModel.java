package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/31.
 */

public interface JokeListModel {
    /**
     * 笑话顺口溜列表
     *
     * @param distributorid 导游ID
     * @param type          类型 1=笑话 2=顺口溜
     * @param order         排序 笑话：1=最新 2=图文 3=纯文；顺口溜：1=最热 2=最新
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    void jokeList(String distributorid, int type, int order, int pageindex, String sign, ICallBackListener callBackListener);
}
