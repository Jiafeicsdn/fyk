package com.lvgou.distribution.model.impl;

import com.lvgou.distribution.api.Api;
import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.api.IServiceAPI;
import com.lvgou.distribution.model.CommentSubmitModel;
import com.lvgou.distribution.model.CommentTeacherModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/6.
 */

public class CommentTeacherImpl implements CommentTeacherModel {
    /**
     * 学院评论
     *
     * @param distributorid 导游ID
     * @param teacherId     课堂ID
     * @param content       评论内容(如果为打赏传空)
     * @param tuanbi        打赏团币个数(如果为评价传0)
     * @param sign          签名
     * @return
     */
    @Override
    public void commentTeacher(String distributorid, String teacherId, String content, int tuanbi, String sign, ICallBackListener callBackListener) {
        IServiceAPI mIServiceAPI = Api.getInstance().getGankService();
        mIServiceAPI.commentTeacher(distributorid, teacherId, content, tuanbi, sign)
                // Subscriber前面执行的代码都是在I/O线程中运行
                .subscribeOn(Schedulers.io())
                // 操作observeOn之后操作主线程中运行.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Api.getInstance().createSubscriber(callBackListener));
    }
}