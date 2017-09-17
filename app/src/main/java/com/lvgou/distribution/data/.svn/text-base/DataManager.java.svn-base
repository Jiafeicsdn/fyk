package com.lvgou.distribution.data;

import com.lvgou.distribution.model.impl.SendSmsImpl;

/**
 * Created by Administrator on 2016/9/9.
 */
public class DataManager {
    private static DataManager dataManager;

    private SendSmsImpl sendSmsModel;
//    private DataModel dataModel;


    public synchronized static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }


//    private DataManager() {
//        this.sendSmsModel = SendSmsImpl.getInstance();
//    }
}
