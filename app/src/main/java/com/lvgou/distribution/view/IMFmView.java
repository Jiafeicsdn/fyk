package com.lvgou.distribution.view;

import com.lvgou.distribution.bean.AddUser;
import com.lvgou.distribution.bean.JoinChatGroupBean;

/**
 * Created by Administrator on 2016/9/8.
 */
public interface IMFmView extends BaseView{
    //实现自己的初始化方法
    /**
     * 获取参数
     * @return
     */
    public AddUser getParamenters();

    //历史数据
    public void GetGroupMessageExt_response(String s);

}
