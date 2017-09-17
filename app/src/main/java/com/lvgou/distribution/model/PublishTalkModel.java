package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/18.
 */

public interface PublishTalkModel {
    /**
     * 发布蜂文
     *
     * @param distributorId 导游编号
     * @param tagIds        热门标签Ids（多个用英文竖线分割）
     * @param content       蜂文内容
     * @param picUrls       蜂文图片集合（多个用英文竖线分割）例如：图片地址1&图片小图地址1&图片宽度1&图片高度1
     * @param location      当前位置
     * @param topicId       话题Id
     * @param sign
     * @return
     */
    void publishTalk(String distributorId, String tagIds, String content, String picUrls, String location, String topicId, String sign, ICallBackListener callBackListener);
}
