package com.lvgou.distribution.presenter;

import android.os.Handler;
import android.os.Looper;

import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.CommentZanBean;
import com.lvgou.distribution.model.impl.FamousDetialCollegeImpl;
import com.lvgou.distribution.view.FamousTeacherDetiaiView;


/**
 * Created by Administrator on 2016/9/12.
 */
public class FamousTeacherDetaiPresenter extends BasePresenter<FamousTeacherDetiaiView> {

    private FamousDetialCollegeImpl famousDetialCollegeImpl;
    private FamousTeacherDetiaiView famousTeacherDetiaiView;
    private Handler mHandler;

    public FamousTeacherDetaiPresenter(FamousTeacherDetiaiView famousTeacherDetiaiView) {
        this.famousTeacherDetiaiView = famousTeacherDetiaiView;
        famousDetialCollegeImpl = new FamousDetialCollegeImpl();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取数据
     *
     * @param distributorid
     * @param id
     * @param sign
     */
    public void getData(String distributorid, String id, String sign) {
        famousDetialCollegeImpl.getData(distributorid, id, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousSuccCallBack("1", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousFialCallBack("1", s);
                    }
                });
            }
        });
    }

    /**
     * 获取评论列表信息
     *
     * @param teacherId
     * @param pageindex
     * @param sign
     */
    public void getCommentData(String teacherId, String pageindex, String sign) {
        famousDetialCollegeImpl.getCommentData(teacherId, pageindex + "", sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousSuccCallBack("2", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousFialCallBack("2", s);
                    }
                });
            }
        });
    }

    /**
     * 评论，点赞操作
     *
     * @param commentZanBean
     */
    public void doCommentZan(CommentZanBean commentZanBean) {
        famousDetialCollegeImpl.doCommentZan(commentZanBean, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousSuccCallBack("3", s);

                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousFialCallBack("3", s);
                    }
                });
            }
        });
    }


    /**
     * 课程查看
     *
     * @param distributorid
     * @param id
     * @param sign
     */
    public void doFamousSeek(String distributorid, String id, String sign) {
        famousDetialCollegeImpl.doFamousSeek(distributorid, id, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousSuccCallBack("4", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousFialCallBack("4", s);
                    }
                });
            }
        });
    }

    /**
     * 报名
     *
     * @param distributorid
     * @param id
     * @param sign
     */
    public void doFamousSignUp(String distributorid, String id, String sign) {
        famousDetialCollegeImpl.doFamousSignUp(distributorid, id, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousSuccCallBack("5", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousFialCallBack("5", s);
                    }
                });
            }
        });
    }

    /**
     * 播放点击量
     *
     * @param distributorid
     * @param studyid
     * @param sign
     */
    public void doPlayTimes(String distributorid, String studyid, String sign) {
        famousDetialCollegeImpl.doPlayTimes(distributorid, studyid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousSuccCallBack("6", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousFialCallBack("6", s);
                    }
                });
            }
        });
    }


    /**
     * 更改状态
     *
     * @param distributorid
     * @param studyid
     * @param sign
     */
    public void updateState(String distributorid, String studyid, String sign) {
        famousDetialCollegeImpl.doUpdateState(distributorid, studyid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousSuccCallBack("7", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousFialCallBack("7", s);
                    }
                });
            }
        });
    }
    /**
     * 课程评论删除
     *
     * @param distributorid
     * @param commentid
     * @param sign
     */
    public void delcomment(String distributorid, String commentid, String sign) {
        famousDetialCollegeImpl.delcomment(distributorid, commentid, sign, new ICallBackListener() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousSuccCallBack("delcomment", s);
                    }
                });
            }

            @Override
            public void onFaild(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        famousTeacherDetiaiView.closeProgress();
                        famousTeacherDetiaiView.OnFamousFialCallBack("delcomment", s);
                    }
                });
            }
        });
    }
}
