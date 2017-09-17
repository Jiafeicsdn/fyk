package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/20.
 */

public interface TeacherDownloadListModel {

    void teacherDownloadList(String distributorid, String sign, ICallBackListener callBackListener);
}
