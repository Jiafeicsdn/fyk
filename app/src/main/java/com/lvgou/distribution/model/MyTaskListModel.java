package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2016/10/17.
 */
public interface MyTaskListModel {

    /**
     * 获取列表
     *
     * @param distributorid
     * @param sign
     * @param iCallBackListener
     */
    void getMyTaskList(String distributorid, String sign, ICallBackListener iCallBackListener);


    /**
     * 任务操作
     *
     * @param distributorid
     * @param type
     * @param sign
     * @param iCallBackListener
     */
    void doMyTaskOperate(String distributorid, String type, String sign, ICallBackListener iCallBackListener);


}
