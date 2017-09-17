package com.lvgou.distribution.model;

import com.lvgou.distribution.api.ICallBackListener;

/**
 * Created by Administrator on 2017/4/28.
 */

public interface DelImgModel {
    void delImg(String imgPath, String sign, ICallBackListener callBackListener);
}
