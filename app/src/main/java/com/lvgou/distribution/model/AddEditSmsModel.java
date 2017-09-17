package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.AddEditSmsBean;

/**
 * Created by Administrator on 2016/9/14.
 */
public interface AddEditSmsModel {

    /**
     * 编辑新增模板
     * @param addEditSmsBean
     */
    void addEditSms(AddEditSmsBean addEditSmsBean,ICallBackListener callBackListener);
}
