package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/7.
 */
public interface ZanListModel {
    /**
     * 蜂文点赞列表
     *
     * @param distributorid  导游编号
     * @param talkId   蜂文编号
     * @param prePageLastDataObjectId  上一页最后一条数据的编号(第一页查询传空)
     * @param currPage  当前页
     * @param sign   签名
     * @return
     */
    public void talkzanlist(String distributorid,String talkId,String prePageLastDataObjectId, int currPage,String sign, ICallBackListener callBackListener);

}
