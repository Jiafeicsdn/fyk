package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/5.
 */

public interface TeacherDetailModel {
    /**
     * 名师课堂详情(4.0)
     *
     * @param distributorid 导游ID
     * @param id            课堂ID
     * @param sign          签名
     * @return
     */
    void teacherDetail(String distributorid, String id, String sign, ICallBackListener callBackListener);
}
