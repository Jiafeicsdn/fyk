package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/5/18.
 */

public interface ApplyUserModel {
    void applyUser(String activityid, int pageindex,String keyword, String sign, ICallBackListener callBackListener);
}
