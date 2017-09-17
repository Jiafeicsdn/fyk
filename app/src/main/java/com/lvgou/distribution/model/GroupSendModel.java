package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.GroupSendBean;

/**
 * Created by Snow on 2016/9/30.
 */
public interface GroupSendModel {


    /**
     * 带团，派团，
     * @param sendBean
     * @param callBackListener
     */
    void getSendGroup(GroupSendBean sendBean, ICallBackListener callBackListener);

}
