package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.ApplyTeaacherBean;

/**
 * Created by Snow on 2016/9/24.
 */
public interface ApplyTeacherModel {

    void doApplyTeacher(ApplyTeaacherBean applyTeaacherBean, ICallBackListener callBackListener);
}
