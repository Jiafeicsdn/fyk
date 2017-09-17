package com.lvgou.distribution.view;

import com.lvgou.distribution.bean.SmsBean;

/**
 * Created by Administrator on 2016/9/8.
 */
public interface FreeSmsView extends BaseView{
    //实现自己的初始化方法
    /**
     * 获取参数
     * @return
     */
    public SmsBean getParamenters();


}
