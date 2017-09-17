package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/3/13.
 */

public interface StudyClassifyModel {/**
 * 活动列表
 *
 * @param distributorid 导游ID
 * @param label     标签路径
 * @param sign          签名
 * @return
 */
void studyClassifyDatas(String distributorid,String label, String sign, ICallBackListener callBackListener);
}
