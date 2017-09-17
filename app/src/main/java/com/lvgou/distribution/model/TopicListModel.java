package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/17.
 */

public interface TopicListModel {
    /**
     * 蜂圈话题列表
     *
     * @param currPage
     * @param sign
     * @return
     */
    void topicList(int currPage, String sign, ICallBackListener callBackListener);
}
