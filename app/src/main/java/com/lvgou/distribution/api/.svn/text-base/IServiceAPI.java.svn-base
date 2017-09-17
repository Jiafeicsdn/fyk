package com.lvgou.distribution.api;

import com.lvgou.distribution.bean.AddEditSmsBean;
import com.lvgou.distribution.bean.ApplyTeaacherBean;
import com.lvgou.distribution.bean.CommentZanBean;
import com.lvgou.distribution.bean.ExchangeBean;
import com.lvgou.distribution.bean.GroupAllBean;
import com.lvgou.distribution.bean.GroupSendBean;
import com.lvgou.distribution.bean.PublishGroupBean;
import com.lvgou.distribution.bean.SmsBean;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ApplyClassEntity;
import com.lvgou.distribution.entity.PublishGroupEditBean;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Author:zcmain on 2016/5/13 15:32
 * E-Mail:zcmain@163.com
 * 说明：
 * 1. 使用 retrofit将http转化为 Java接口供调用
 * 2. RxJavaCallAdapterFactory.它把Call转换成Observable.
 */
public interface IServiceAPI {
    //线上
//    public static final String ROOTXS = "https://circleapi2.quygt.com:446";

    //线下
    public static final String ROOTXS = "http://192.168.1.24:8079";

    // 发送短信
    @POST("message/messagepost")
    Observable<String> sendSms(@Body SmsBean mUserVo);

    //随时赚 列表信息
    @FormUrlEncoded
    @POST("supply/selectsupplynew")
    Observable<String> getApplicationList(@Field("distributorid") String distributorid, @Field("pageindex") String pageindex, @Field("sign") String sign);

    //去申请
    @FormUrlEncoded
    @POST("supply/supply")
    Observable<String> goApplyApplication(@Field("distributorid") String distributorid, @Field("supplyId") String supplyId, @Field("sign") String sign);

    //排行榜
    @FormUrlEncoded
    @POST("supply/rankinglist")
    Observable<String> getRankList(@Field("distributorid") String distributorid, @Field("type") String type, @Field("sign") String sign);

    //蜂优学院详情页
    @FormUrlEncoded
    @POST("study/teacherdetail")
    Observable<String> getFamousData(@Field("distributorid") String distributorid, @Field("id") String id, @Field("sign") String sign);

    //蜂优学院详情页-评论列表
    @FormUrlEncoded
    @POST("study/teachercommentlist")
    Observable<String> getFamousComment(@Field("teacherId") String teacherId, @Field("pageindex") String pageindex, @Field("sign") String sign);


    //点赞,评论接口
    @POST("study/commentteacher")
    Observable<String> doCommentZan(@Body CommentZanBean commentZanBean);

    //名师讲堂查看
    @FormUrlEncoded
    @POST("study/lookteacher")
    Observable<String> doFamousSeek(@Field("distributorid") String distributorid, @Field("id") String id, @Field("sign") String sign);

    //名师讲堂报名
    @FormUrlEncoded
    @POST("study/applyteacher")
    Observable<String> doFamousSignUp(@Field("distributorid") String distributorid, @Field("id") String id, @Field("sign") String sign);

    //视频点击量
    @FormUrlEncoded
    @POST("study/videohits")
    Observable<String> doPlayTimes(@Field("distributorid") String distributorid, @Field("studyid") String studyid, @Field("sign") String sign);

    //报备列表
    @FormUrlEncoded
    @POST("report/agentreportlist")
    Observable<String> getRepportList(@Field("distributorid") String distributorid, @Field("pageindex") String pageindex, @Field("sign") String sign);

    //常报备店与列表
    @FormUrlEncoded
    @POST("report/reporthistory")
    Observable<String> getReportShop(@Field("distributorid") String distributorid, @Field("pageindex") String pageindex, @Field("sign") String sign);

    //报备店搜索结果
    @FormUrlEncoded
    @POST("report/searchreportseller")
    Observable<String> getSeacheShop(@Field("distributorid") String distributorid, @Field("key") String key, @Field("pageindex") String pageindex, @Field("sign") String sign);

    //定位周边店铺
    @FormUrlEncoded
    @POST("report/mapsearch")
    Observable<String> getLocalData(@Field("distributorid") String distributorid, @Field("key") String key, @Field("latitude") String latitude, @Field("longitude") String longitude, @Field("pageindex") String pageindex, @Field("sign") String sign);

    //短信发送记录
    @FormUrlEncoded
    @POST("message/msgloglist")
    Observable<String> getSendSmsRecord(@Field("distributorid") String distributorid, @Field("pageindex") String pageindex, @Field("sign") String sign);

    //短信发送记录详情
    @FormUrlEncoded
    @POST("message/msglogdetail")
    Observable<String> getSendSmsRecordDetial(@Field("distributorid") String distributorid, @Field("msgid") String msgid, @Field("sign") String sign);


    //新增编辑模板
    @POST("message/templateedit")
    Observable<String> addEditSmsModel(@Body AddEditSmsBean addEditSmsBean);

    //删除模板
    @FormUrlEncoded
    @POST("message/templatedel")
    Observable<String> doDeleteModel(@Field("distributorid") String distributorid, @Field("tmpid") String tmpid, @Field("sign") String sign);

    //获取模板列表
    @FormUrlEncoded
    @POST("message/templatelist")
    Observable<String> getSmsModel(@Field("distributorid") String distributorid, @Field("sign") String sign);


    //全部课程
    @FormUrlEncoded
    @POST("study/teacherreview")
    Observable<String> getAllClasses(@Field("teacherId") String teacherId, @Field("tag") String tag, @Field("pageindex") String pageindex, @Field("type") String type, @Field("sign") String sign);

    //获取热门标签
    @FormUrlEncoded
    @POST("study/HotTag")
    Observable<String> getHotTag(@Field("distributorid") String distributorid, @Field("sign") String sign);

    //蜂优推荐
    @FormUrlEncoded
    @POST("study/recommend")
    Observable<String> getRecommentBee(@Field("distributorid") String distributorid, @Field("sign") String sign);

    //讲师列表
    @FormUrlEncoded
    @POST("study/kzjslist")
    Observable<String> getTeacherSeatList(@Field("distributorid") String distributorid, @Field("pageindex") String pageindex, @Field("key") String key, @Field("sign") String sign);

    //申请讲师状态
    @FormUrlEncoded
    @POST("study/getapplystate")
    Observable<String> getApplyState(@Field("distributorid") String distributorid, @Field("sign") String sign);

    //课程列表
    @FormUrlEncoded
    @POST("study/mystudylist")
    Observable<String> getMyClasses(@Field("distributorid") String distributorid, @Field("pageindex") String pageindex, @Field("sign") String sign);

    //申请讲师
    @POST("study/teacherapply")
    Observable<String> applyTeacher(@Body ApplyTeaacherBean applyTeaacherBean);


    //申请开课
    @POST("/study/applystudy")
    Observable<String> applyClass(@Body ApplyClassEntity applyClassEntity);


    //派团出团
    @POST("seek/addseek")
    Observable<String> publishGroup(@Body PublishGroupBean publishGroupBean);

    //获取详情页以
    @FormUrlEncoded
    @POST("seek/getseek")
    Observable<String> getGroupDetial(@Field("distributorid") String distributorid, @Field("seekid") String seekid, @Field("sign") String sign);

    //获取报名列表
    @FormUrlEncoded
    @POST("seek/seekapplylist")
    Observable<String> getSignUp(@Field("seekid") String seekid, @Field("pageindex") String pageindex, @Field("sign") String sign);

    //全部列表数据
    @POST("seek/seeklist")
    Observable<String> getGroupList(@Body GroupAllBean groupAllBean);

    //派团列表数据
    @POST("seek/myseeklist")
    Observable<String> getSendGroupList(@Body GroupSendBean groupSendBean);

    //带团列表数据
    @POST("seek/myapplylist")
    Observable<String> getCarryGroupList(@Body GroupSendBean groupSendBean);

    // 获取热门城市
    @FormUrlEncoded
    @POST("seek/getcity")
    Observable<String> getHotCity(@Field("distributorid") String distributorid, @Field("sign") String sign);

    // 获取个人中心数据
    @FormUrlEncoded
    @POST("user/usercenter")
    Observable<String> getPersonalData(@Field("distributorid") String distributorid, @Field("sign") String sign);

    // 获取上传头像
    @FormUrlEncoded
    @POST("user/uploadfaceimg")
    Observable<String> uploadHead(@Field("distributorid") String distributorid, @Field("picurl") String picurl, @Field("sign") String sign);

    // 取消派团
    @FormUrlEncoded
    @POST("seek/updateseekstate")
    Observable<String> canclePublishGroup(@Field("distributorid") String distributorid, @Field("seekid") String seekid, @Field("sign") String sign);

    // 删除派团
    @FormUrlEncoded
    @POST("seek/deleteseek")
    Observable<String> deletePublishGroup(@Field("distributorid") String distributorid, @Field("seekid") String seekid, @Field("sign") String sign);

    // 编辑派团
    @POST("seek/editseek")
    Observable<String> editPublishGroup(@Body PublishGroupEditBean publishGroupBean);


    // 获取消息未读消息数
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/unreadcount")
    Observable<String> getPersonalMessageCount(@Field("distributorid") String distributorid, @Field("getNewestDistributorId") String getNewestDistributorId, @Field("sign") String sign);


    //获取课程列表
    @FormUrlEncoded
    @POST("study/mystudylist")
    Observable<String> getMySchdule(@Field("distributorid") String distributorid, @Field("pageindex") String pageindex, @Field("sign") String sign);


    //课程评分
    @FormUrlEncoded
    @POST("study/studygrade")
    Observable<String> doGrade(@Field("distributorid") String distributorid, @Field("studyid") String studyid, @Field("grade") String grade, @Field("comment") String comment, @Field("sign") String sign);


    //我要报名
    @FormUrlEncoded
    @POST("seek/seekapply")
    Observable<String> doSignUp(@Field("distributorid") String distributorid, @Field("seekid") String seekid, @Field("sign") String sign);

    //录用导游
    @FormUrlEncoded
    @POST("seek/updateapplyemploy")
    Observable<String> doEmployment(@Field("distributorid") String distributorid, @Field("seekid") String seekid, @Field("applyid") String applyid, @Field("sign") String sign);


    //获取个人信息
    @FormUrlEncoded
    @POST("seek/applydistributorinfo")
    Observable<String> getPeronalInfo(@Field("distributorid") String distributorid, @Field("seekid") String seekid, @Field("applyid") String applyid, @Field("sign") String sign);


    //派团记录
    @FormUrlEncoded
    @POST("seek/seekinfolist")
    Observable<String> getSendGroupRecord(@Field("distributorid") String distributorid, @Field("pageindex") String pageindex, @Field("sign") String sign);


    //带团记录
    @FormUrlEncoded
    @POST("seek/applyinfolist")
    Observable<String> getCarryGroupRecord(@Field("distributorid") String distributorid, @Field("pageindex") String pageindex, @Field("sign") String sign);


    /**
     * 首页
     */
    @FormUrlEncoded
    @POST("mall/index")
    Observable<String> getIndex(@Field("distributorid") String distributorid, @Field("sign") String sign);


    //获取我的任务列表
    @FormUrlEncoded
    @POST(ROOTXS + "/tuanbi/mytasklist")
    Observable<String> getMytasklist(@Field("distributorid") String distributorid, @Field("sign") String sign);

    //获取我的任务操作
    @FormUrlEncoded
    @POST(ROOTXS + "/tuanbi/taskoperate")
    Observable<String> doMytasklist(@Field("distributorid") String distributorid, @Field("type") String type, @Field("sign") String sign);


    //我的评论列表
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/mycommentlist")
    Observable<String> getMyCommentList(@Field("distributorid") String distributorid, @Field("prePageLastDataObjectId") String prePageLastDataObjectId, @Field("currPage") String currPage, @Field("sign") String sign);


    //个人中心我的课程列表
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/mystudylist")
    Observable<String> getMyClassesList(@Field("distributorId") String distributorid, @Field("currPage") String currPage, @Field("sign") String sign);

    /**
     * 蜂圈动态
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/findcircle")
    Observable<String> getFindCircle(@Field("distributorid") String distributorid,
                                     @Field("keyword") String keyword,
                                     @Field("tagId") String tagId,
                                     @Field("prePageLastDataObjectId") String prePageLastDataObjectId,
                                     @Field("currPage") int currPage,
                                     @Field("sign") String sign);

    /**
     * 未读消息数
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/unreadcount")
    Observable<String> unreadcount(@Field("distributorId") String distributorid, @Field("getNewestDistributorId") int getNewestDistributorId, @Field("sign") String sign);

    /**
     * 蜂文状态
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/talkisnormal")
    Observable<String> talkisnormal(@Field("talkId") String talkId,
                                    @Field("sign") String sign);

    /**
     * 蜂圈评论列表
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/talkcommentlist")
    Observable<String> talkcommentlist(@Field("talkId") String talkId,
                                       @Field("prePageLastDataObjectId") String prePageLastDataObjectId,
                                       @Field("currPage") int currPage,
                                       @Field("sign") String sign);

    /**
     * 发表评论
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/commenttalk")
    Observable<String> commenttalk(@Field("distributorId") String distributorid,
                                   @Field("talkId") String talkId,
                                   @Field("commentId") String commentId,
                                   @Field("content") String content, @Field("tuanbi") int tuanbi,
                                   @Field("sign") String sign);

    /**
     * 点赞
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/zan")
    Observable<String> circleZan(@Field("distributorId") String distributorid,
                                 @Field("talkId") String talkId,
                                 @Field("sign") String sign);

    /**
     * 取消点赞
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/unzan")
    Observable<String> circleUnZan(@Field("distributorId") String distributorid,
                                   @Field("talkId") String talkId,
                                   @Field("sign") String sign);

    /**
     * 关注
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/follow")
    Observable<String> circleFollow(@Field("distributorId") String distributorid,
                                    @Field("friendId") String friendId,
                                    @Field("sign") String sign);

    /**
     * 点赞列表
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/myzanlist")
    Observable<String> getMyZanList(@Field("distributorid") String distributorid, @Field("prePageLastDataObjectId") String prePageLastDataObjectId, @Field("currPage") String currPage, @Field("sign") String sign);


    //回复评论
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/commenttalk")
    Observable<String> doCommetReplay(@Field("distributorid") String distributorid, @Field("talkId") String talkId, @Field("commentId") String commentId, @Field("content") String content, @Field("sign") String sign);


    //未读消息列表
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/unreadmessage")
    Observable<String> getUnreadmessage(@Field("distributorId") String distributorId, @Field("sign") String sign);


    //蜂圈推荐列表
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/recommenttalk")
    Observable<String> getCircleRecommend(@Field("distributorid") String distributorid, @Field("prePageLastDataObjectId") String prePageLastDataObjectId, @Field("currPage") String currPage, @Field("sign") String sign);

    // 取消关注
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/unfollow")
    Observable<String> circleUnFollow(@Field("distributorid") String distributorid, @Field("friendId") String friendId, @Field("sign") String sign);


    //话题列表
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/topiclist")
    Observable<String> getTopicList(@Field("currPage") String currPage, @Field("sign") String sign);

    //搜索好友
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/usersearch")
    Observable<String> getUserSearch(@Field("distributorId") String distributorId, @Field("keyword") String keyword, @Field("currPage") String currPage, @Field("sign") String sign);


    //发现-蜂圈动态&首页-蜂圈动态
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/findcircle")
    Observable<String> getFengwenSearch(@Field("distributorId") String distributorId, @Field("keyword") String keyword, @Field("tagId") String tagId, @Field("currPage") String currPage, @Field("sign") String sign);

    //发现-热门标签,热门话题
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/findtagandtopic")
    Observable<String> getTagTopic(@Field("distributorId") String distributorId, @Field("sign") String sign);


    //上传蜂文图片
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/publishtalkimage")
    Observable<String> uploadFengwenPic(@Field("sign") String sign);

    //发布蜂文
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/publishtalk")
    Observable<String> publishFengWen(@Field("distributorId") String distributorId, @Field("tagIds") String tagIds, @Field("content") String content, @Field("picUrls") String picUrls, @Field("location") String location, @Field("sign") String sign);

    /**
     * 关注 蜂圈动态
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/followcircle")
    Observable<String> followcircle(@Field("distributorId") String distributorId, @Field("prePageLastDataObjectId") String prePageLastDataObjectId, @Field("currPage") int currPage, @Field("sign") String sign);

    /**
     * /**
     * <p/>
     * 关注-可能认识的人
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/mayknowperson")
    Observable<String> mayknowperson(@Field("distributorId") String distributorId, @Field("sign") String sign);


    //获取推荐好友列表
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/morefriendsoffriends")
    Observable<String> getRecommentFirend(@Field("distributorId") String distributorId, @Field("currPage") String currPage, @Field("sign") String sign);

    //获取可能认识的好友，通讯录好友
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/morefriends")
    Observable<String> getContactList(@Field("distributorId") String distributorId, @Field("mobiles") String mobiles, @Field("sign") String sign);

    //获取我的粉丝列表
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/myfanslist")
    Observable<String> getFansList(@Field("distributorId") String distributorId, @Field("seeDistributorId") String seeDistributorId, @Field("currPage") String currPage, @Field("sign") String sign);

    //获取我的关注列表
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/myfollowlist")
    Observable<String> getFollowList(@Field("distributorId") String distributorId, @Field("seeDistributorId") String seeDistributorId, @Field("currPage") String currPage, @Field("sign") String sign);

    /**
     * /**
     * <p/>
     * 个人首页
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/distributormain")
    Observable<String> distributormain(@Field("distributorId") String distributorId, @Field("seeDistributorId") String seeDistributorId, @Field("sign") String sign);

    /**
     * /**
     * <p/>
     * 蜂文列表
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/mytalklist")
    Observable<String> mytalklist(@Field("distributorId") String distributorId, @Field("seeDistributorId") String seeDistributorId, @Field("currPage") int currPage, @Field("sign") String sign);

    @FormUrlEncoded
    @POST(ROOTXS + "/circle/topicdetail")
    Observable<String> topicdetail(@Field("topicId") String topicId, @Field("sign") String sign);

    @FormUrlEncoded
    @POST(ROOTXS + "/circle/commenttopic")
    Observable<String> commenttopic(@Field("distributorId") String distributorId, @Field("topicId") String topicId, @Field("commentId") String commentId, @Field("content") String content, @Field("sign") String sign);

    @FormUrlEncoded
    @POST(ROOTXS + "/circle/topiccommentlist")
    Observable<String> topiccommentlist(@Field("topicId") String topicId, @Field("prePageLastDataObjectId") String prePageLastDataObjectId, @Field("currPage") int currPage, @Field("sign") String sign);

    //订单列表数字
    @FormUrlEncoded
    @POST("order/ordercount")
    Observable<String> getOrderNum(@Field("distributorId") String distributorId, @Field("sign") String sign);


    //蜂优团队，标记全部已读
    @FormUrlEncoded
    @POST("user/moreactivityread")
    Observable<String> doAllActivityRead(@Field("distributorId") String distributorId, @Field("sign") String sign);


    //未读消息操作
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/readcommentorzan")
    Observable<String> doAllMessage(@Field("distributorId") String distributorId, @Field("messageType") String messageType, @Field("sign") String sign);


    // 转发操作
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/relaytalk")
    Observable<String> zhuangfa(@Field("distributorId") String distributorId, @Field("talkId") String talkId, @Field("content") String content, @Field("sign") String sign);

    //动态详情页
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/talkdetail")
    Observable<String> getTalkDetail(@Field("distributorId") String distributorId, @Field("talkId") String talkId, @Field("sign") String sign);

    //兑换列表
    @FormUrlEncoded
    @POST("tuanbi/tuanbiproductlist")
    Observable<String> exchangeList(@Field("distributorId") String distributorId, @Field("pageindex") String pageindex, @Field("sign") String sign);

    //兑换列表详情
    @FormUrlEncoded
    @POST("tuanbi/productdetail")
    Observable<String> exchangeListDetial(@Field("distributorId") String distributorId, @Field("productid") String productid, @Field("sign") String sign);

    //礼品兑换数据加载
    @FormUrlEncoded
    @POST("tuanbi/exchangetuanbi")
    Observable<String> exchangetuanbi(@Field("distributorId") String distributorId, @Field("sign") String sign);

    //礼品兑换
    @POST("tuanbi/exchangetuanbiproduct")
    Observable<String> doExchangeCommit(@Body ExchangeBean exchangeBean);


    //充值记录列表
    @FormUrlEncoded
    @POST("tuanbi/rechargeloglist")
    Observable<String> rechargeRecordList(@Field("distributorId") String distributorId, @Field("pageindex") String pageindex, @Field("sign") String sign);


    //添加充值记录
    @FormUrlEncoded
    @POST("tuanbi/rechargelogadd")
    Observable<String> addRechargeRecord(@Field("distributorId") String distributorId, @Field("payType") int payType, @Field("tuanbi") String tuanbi, @Field("rmb") String rmb, @Field("sign") String sign);

    /**
     * 推荐蜂文详情内容
     *
     * @param talkId
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/recommenttalkcontent")
    Observable<String> recommenttalkcontent(@Field("talkId") String talkId, @Field("sign") String sign);


    //添加充值记录
    @FormUrlEncoded
    @POST("tuanbi/alipaysuccess")
    Observable<String> doAlipaySuccess(@Field("logid") String logid, @Field("state") String state, @Field("errormsg") String errormsg, @Field("sign") String sign);


    //兑换记录列表
    @FormUrlEncoded
    @POST("tuanbi/exchangelog")
    Observable<String> getExchangelogList(@Field("distributorid") String distributorId, @Field("pageindex") String pageindex, @Field("sign") String sign);


    //兑换记录详情
    @FormUrlEncoded
    @POST("tuanbi/exchangelogdetail")
    Observable<String> getExchangelogDetail(@Field("distributorid") String distributorId, @Field("logid") String logid, @Field("sign") String sign);

    /**
     * 蜂文删除
     *
     * @param distributorId
     * @param talkId
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/talkdel")
    Observable<String> talkdel(@Field("distributorid") String distributorId, @Field("talkId") String talkId, @Field("sign") String sign);


    /**
     * 推荐蜂文详情内容
     *
     * @param distributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("tuanbi/circleshare")
    Observable<String> shareCircle(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 更改学习状态
     *
     * @param distributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("study/updatestate")
    Observable<String> upDateState(@Field("distributorid") String distributorid, @Field("studyid") String studyid, @Field("sign") String sign);

    /**
     * 蜂文点赞列表
     *
     * @param distributorid           导游编号
     * @param talkId                  蜂文编号
     * @param prePageLastDataObjectId 上一页最后一条数据的编号(第一页查询传空)
     * @param currPage                当前页
     * @param sign                    签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/talkzanlist")
    Observable<String> talkzanlist(@Field("distributorid") String distributorid, @Field("talkId") String talkId, @Field("prePageLastDataObjectId") String prePageLastDataObjectId, @Field("currPage") int currPage, @Field("sign") String sign);


    /**
     * 蜂文评论删除
     *
     * @param distributorid 导游编号
     * @param talkCommentId 蜂文评论编号
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/talkcommentdel")
    Observable<String> talkcommentdel(@Field("distributorid") String distributorid, @Field("talkCommentId") String talkCommentId, @Field("sign") String sign);

    /**
     * 话题评论删除
     *
     * @param distributorid  导游编号
     * @param topicCommentId 蜂文评论编号
     * @param sign           签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/topiccommentdel")
    Observable<String> topiccommentdel(@Field("distributorid") String distributorid, @Field("topicCommentId") String topicCommentId, @Field("sign") String sign);

    /**
     * 蜂文收藏
     *
     * @param distributorid 导游编号
     * @param talkId        收藏的蜂文编号
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/talkcollection")
    Observable<String> talkcollection(@Field("distributorid") String distributorid, @Field("talkId") String talkId, @Field("sign") String sign);

    /**
     * 蜂文取消收藏
     *
     * @param distributorid 导游编号
     * @param talkId        收藏的蜂文编号
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/talkuncollection")
    Observable<String> talkuncollection(@Field("distributorid") String distributorid, @Field("talkId") String talkId, @Field("sign") String sign);

    /**
     * 蜂文收藏列表
     *
     * @param distributorid           导游编号
     * @param prePageLastDataObjectId 上一页最后一条数据的编号(第一页查询传空)
     * @param currPage                当前页
     * @param sign                    签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/talkcollectionlist")
    Observable<String> talkcollectionlist(@Field("distributorid") String distributorid, @Field("prePageLastDataObjectId") String prePageLastDataObjectId, @Field("currPage") int currPage, @Field("sign") String sign);

    /**
     * 我的评论列表
     *
     * @param distributorid           导游编号
     * @param prePageLastDataObjectId 上一页最后一条数据的编号(第一页查询传空)
     * @param currPage                当前页
     * @param sign                    签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/mycommentlist1")
    Observable<String> mycommentlist1(@Field("distributorid") String distributorid, @Field("prePageLastDataObjectId") String prePageLastDataObjectId, @Field("currPage") int currPage, @Field("sign") String sign);

    /**
     * 我的点赞列表（我点赞别人）
     *
     * @param distributorid           导游编号
     * @param prePageLastDataObjectId 上一页最后一条数据的编号(第一页查询传空)
     * @param currPage                当前页
     * @param sign                    签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/myzanlist1")
    Observable<String> myzanlist1(@Field("distributorid") String distributorid, @Field("prePageLastDataObjectId") String prePageLastDataObjectId, @Field("currPage") int currPage, @Field("sign") String sign);

    /**
     * 个性签名修改
     *
     * @param distributorid 导游编号
     * @param usersign      用户签名
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/updateusersign")
    Observable<String> updateusersign(@Field("distributorId") String distributorid, @Field("usersign") String usersign, @Field("sign") String sign);

    /**
     * 删除课程评论
     *
     * @param distributorid 导游编号
     * @param commentid     评论ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/delcomment")
    Observable<String> delcomment(@Field("distributorId") String distributorid, @Field("commentid") String commentid, @Field("sign") String sign);


    /**
     * 系统消息
     *
     * @param distributorid 导游编号
     * @param pageindex     页数
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/sysmsg/sysmsglist")
    Observable<String> sysmsglist(@Field("distributorId") String distributorid, @Field("pageindex") int pageindex, @Field("sign") String sign);


    /**
     * 商家个人主页
     *
     * @param distributorid 导游编号
     * @param reportid      报备商ID(店铺ID)
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/report/reportsellerindex")
    Observable<String> reportsellerindex(@Field("distributorId") String distributorid, @Field("reportid") String reportid, @Field("sign") String sign);


    /**
     * app错误日志
     *
     * @param distributorid 导游编号
     * @param type          设备类型 1=ios 2=Android
     * @param ippath        设备ip地址
     * @param errorurl      接口地址
     * @param appintro      设备信息描述
     * @param errorintro    错误内容
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/syslog/apperrorlog")
    Observable<String> apperrorlog(@Field("distributorId") String distributorid, @Field("type") int type, @Field("ippath") String ippath, @Field("errorurl") String errorurl, @Field("appintro") String appintro, @Field("errorintro") String errorintro, @Field("sign") String sign);

    /**
     * 直播间打赏
     *
     * @param distributorid 导游ID
     * @param studyid       课堂ID
     * @param tuanbi        打赏团币数量
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/livepay")
    Observable<String> dashang(@Field("distributorId") String distributorid, @Field("studyid") String studyid, @Field("tuanbi") int tuanbi, @Field("sign") String sign);

    /**
     * 打赏详情
     *
     * @param studyid 课堂ID
     * @param sign    签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/livepaylist")
    Observable<String> dashangDetail(@Field("studyid") String studyid, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 精品课程
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/competitivestudy")
    Observable<String> competitiveStudy(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 推荐
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/mall/index")
    Observable<String> recommendDatas(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 直播课程
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/liveteacher")
    Observable<String> liveTeacherDatas(@Field("distributorid") String distributorid, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 活动列表
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/activitylist")
    Observable<String> activityDatas(@Field("distributorid") String distributorid, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 课程分类
     *
     * @param distributorid 导游ID
     * @param label         标签路径
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/studyclassify")
    Observable<String> studyClassifyDatas(@Field("distributorid") String distributorid, @Field("label") String label, @Field("sign") String sign);

    /**
     * 全局搜索-加载
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/studysearchload")
    Observable<String> studySearchloadDatas(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 全局搜索-讲师搜索
     *
     * @param distributorid 导游ID
     * @param searchword    搜索关键字
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/teachersearch")
    Observable<String> teacherSearchDatas(@Field("distributorid") String distributorid, @Field("searchword") String searchword, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 全局搜索
     *
     * @param distributorid 导游ID
     * @param searchword    搜索关键字
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/studysearch")
    Observable<String> studySearchDatas(@Field("distributorid") String distributorid, @Field("searchword") String searchword, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 发布活动
     *
     * @param distributorid 导游ID
     * @param picurl        活动图片路径
     * @param title         活动标题
     * @param starttime     活动开始时间 格式:2017-3-10 10:00
     * @param endtime       活动结束时间 格式:2017-3-10 10:00
     * @param countrypath   城市节点
     * @param address       详细地址
     * @param maxpeople     活动上限人数
     * @param info          活动简介
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/activitysubmit")
    Observable<String> activitySubmitDatas(@Field("distributorid") String distributorid, @Field("activityid") String activityid, @Field("picurl") String picurl, @Field("title") String title, @Field("starttime") String starttime, @Field("endtime") String endtime, @Field("countrypath") String countrypath, @Field("address") String address, @Field("maxpeople") int maxpeople, @Field("info") String info, @Field("sign") String sign);

    /**
     * 申请开课-课程类型加载
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/labelload")
    Observable<String> labelLoadDatas(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 申请开课-提交
     *
     * @param distributorid 导游ID
     * @param theme         主题
     * @param starttime     直播时间(2017-3-13 18:30)
     * @param label         课程分类(103001,103002)
     * @param crowd         适合人群
     * @param themeinfo     主题简介
     * @param zbtype        开课形式 1=直播，2=录播
     * @param apply         报名团币 可为0
     * @param look          查看团币 可为0
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/applyforstudy")
    Observable<String> applyForStudy(@Field("distributorid") String distributorid, @Field("theme") String theme, @Field("starttime") String starttime, @Field("label") String label, @Field("crowd") String crowd, @Field("themeinfo") String themeinfo, @Field("zbtype") String zbtype, @Field("apply") String apply, @Field("look") String look, @Field("sign") String sign);

    /**
     * 活动详情
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/activitydetail")
    Observable<String> activityDetailDatas(@Field("distributorid") String distributorid, @Field("activityid") String activityid, @Field("sign") String sign);

    /**
     * 活动-报名
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/activityapply")
    Observable<String> activityApply(@Field("distributorid") String distributorid, @Field("activityid") String activityid, @Field("sign") String sign);

    /**
     * 活动评论-列表
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/commentlist")
    Observable<String> commentList(@Field("distributorid") String distributorid, @Field("activityid") String activityid, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 活动评论-发布
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param comment       评论内容
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/commentsubmit")
    Observable<String> commentSubmit(@Field("distributorid") String distributorid, @Field("activityid") String activityid, @Field("comment") String comment, @Field("sign") String sign);


    /**
     * 我听的课
     *
     * @param distributorid 导游ID
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/mystudylist")
    Observable<String> myStudyListDatas(@Field("distributorid") String distributorid, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 我开的课
     *
     * @param distributorid 导游ID
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/teacherlistforme")
    Observable<String> teacherListForMeDatas(@Field("distributorid") String distributorid, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 听课券列表
     *
     * @param distributorid 导游ID
     * @param type          类型 1=未使用 2=已使用
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/studycouponlist")
    Observable<String> couponListDatas(@Field("distributorid") String distributorid, @Field("type") int type, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 活动列表-我报名的活动
     *
     * @param distributorid 导游ID
     * @param type          类型 0=全部，1=进行中，2=已结束
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/applyforme")
    Observable<String> applyForMeDatas(@Field("distributorid") String distributorid, @Field("type") int type, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 活动列表-我的活动
     *
     * @param distributorid 导游ID
     * @param type          类型 0=全部，1=进行中，2=已结束
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/activityforme")
    Observable<String> activityForMeDatas(@Field("distributorid") String distributorid, @Field("type") int type, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 活动停止
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/activitystop")
    Observable<String> activityStop(@Field("distributorid") String distributorid, @Field("activityid") String activityid, @Field("sign") String sign);

    /**
     * 活动-删除
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/delactivity")
    Observable<String> delActivity(@Field("distributorid") String distributorid, @Field("activityid") String activityid, @Field("sign") String sign);


    /**
     * 意见反馈
     *
     * @param distributorid 导游ID
     * @param content       内容
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/feedback")
    Observable<String> feedBack(@Field("distributorid") String distributorid, @Field("content") String content, @Field("sign") String sign);

    /**
     * 笑话顺口溜列表
     *
     * @param distributorid 导游ID
     * @param type          类型 1=笑话 2=顺口溜
     * @param order         排序 笑话：1=最新 2=图文 3=纯文；顺口溜：1=最热 2=最新
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/joke/jokelist")
    Observable<String> jokeList(@Field("distributorid") String distributorid, @Field("type") int type, @Field("order") int order, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 笑话/顺口溜顶或踩
     *
     * @param distributorid 导游ID
     * @param jokeid        笑话/顺口溜ID
     * @param type          操作类型 1=赞一下、2=踩一下
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/joke/jokeoperation")
    Observable<String> jokeOperation(@Field("distributorid") String distributorid, @Field("jokeid") String jokeid, @Field("type") int type, @Field("sign") String sign);

    /**
     * 笑话顺口溜-收藏
     *
     * @param distributorid 导游ID
     * @param jokeid        笑话/顺口溜ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/joke/jokecollection")
    Observable<String> jokeCollection(@Field("distributorid") String distributorid, @Field("jokeid") String jokeid, @Field("sign") String sign);

    /**
     * 笑话顺口溜-取消收藏
     *
     * @param distributorid 导游ID
     * @param jokeid        笑话/顺口溜ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/joke/canceljokecollection")
    Observable<String> cancelJokeCollection(@Field("distributorid") String distributorid, @Field("jokeid") String jokeid, @Field("sign") String sign);

    /**
     * 小游戏列表
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/user/tugougame")
    Observable<String> tugouGame(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 小游戏-点击量
     *
     * @param distributorid 导游ID
     * @param gameid        游戏ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/user/tugougmaehits")
    Observable<String> tugouGamesHits(@Field("distributorid") String distributorid, @Field("gameid") String gameid, @Field("sign") String sign);

    /**
     * 邀请导游有奖
     *
     * @param distributorid 导游ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/user/userinvite")
    Observable<String> userInvite(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 活动-取消报名
     *
     * @param distributorid 导游ID
     * @param activityid    活动ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/cancelapply")
    Observable<String> cancelApply(@Field("distributorid") String distributorid, @Field("activityid") String activityid, @Field("sign") String sign);

    /**
     * 名师课堂详情(4.0)
     *
     * @param distributorid 导游ID
     * @param id            课堂ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/teacherdetail")
    Observable<String> teacherDetail(@Field("distributorid") String distributorid, @Field("id") String id, @Field("sign") String sign);

    /**
     * 名师课堂详情-评论列表
     *
     * @param teacherId 讲课Id
     * @param pageindex 当前页码
     * @param sign      签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/teachercommentlist")
    Observable<String> teacherCommentList(@Field("teacherId") String teacherId, @Field("pageindex") int pageindex, @Field("sign") String sign);

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
    @FormUrlEncoded
    @POST("/study/commentteacher")
    Observable<String> commentTeacher(@Field("distributorid") String distributorid, @Field("teacherId") String teacherId, @Field("content") String content, @Field("tuanbi") int tuanbi, @Field("sign") String sign);

    /**
     * 系列课程详情
     *
     * @param distributorid 讲课Id
     * @param seriesid      系列ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/batchbuystudy")
    Observable<String> batchBuyStudy(@Field("distributorid") String distributorid, @Field("seriesid") String seriesid, @Field("sign") String sign);

    /**
     * 系列课程-批量购买
     *
     * @param distributorid 讲课Id
     * @param teacherid     课程ID包含免费课程(1,2,3,4,5)
     * @param couponid      课程对应可使用听课券ID，没有则为0(1,2,0,3,0)
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/batchbuy")
    Observable<String> batchbBuy(@Field("distributorid") String distributorid, @Field("teacherid") String teacherid, @Field("couponid") String couponid, @Field("sign") String sign);

    /**
     * 我的-实名认证获取数据
     *
     * @param distributorid 讲课Id
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/User/LoadDistributorExtend")
    Observable<String> loadDistributorExtend(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * @param distributorid    导游Id
     * @param countryPath      所在城市
     * @param workDay          从业时间
     * @param education        学历（1=研究生及以上、2=本科、3=专科、4=中专、5=高中、6=其它）
     * @param certificateLevel 证件等级（1=初级、2=中级、3=高级、4=特级）
     * @param languageType     语言类型（1=中文导游、2=外语导游）
     * @param attr             业务范围（1=全陪，2=地接，4=领队（当有选择领队时，必须选择领队线路），8=景讲，16=计调，32=其它）
     * @param attrLine         领队线路（可多选）（位运算）：1=港澳台线路，2=日韩线路，4=东南亚线路，8=中东非线路，16=澳新线路，32=欧洲线路，64=地中海线路，128=南北美洲线路，256=极地线路
     * @param courseType       课程类型（1=地方课程，2=领队课程，4=基础课程，8=实战课程，16=团型课程，32=语言课程，64=才艺课程，128=职业课程）
     * @param picUrl           资质证件照片路径
     * @param sign             签名
     * @return
     */
    @FormUrlEncoded
    @POST("/User/SaveDistributorExtend")
    Observable<String> saveDistributorExtend(@Field("distributorid") String distributorid, @Field("sex") int sex, @Field("countryPath") String countryPath, @Field("workDay") String workDay,
                                             @Field("education") String education, @Field("certificateLevel") String certificateLevel, @Field("languageType") String languageType, @Field("attr") String attr, @Field("attrLine") String attrLine,
                                             @Field("courseType") String courseType, @Field("picUrl") String picUrl, @Field("sign") String sign);

    /**
     * 我的等级
     *
     * @param distributorid 讲课Id
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/tuanbi/leveldetail")
    Observable<String> levelDetail(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 蜂优讲师
     *
     * @param distributorid 讲课Id
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/teacherlist")
    Observable<String> teacherList(@Field("distributorid") String distributorid, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 邀请好友-邀请记录
     *
     * @param distributorid 讲课Id
     * @param type          类型 2=待审核，3=邀请成功
     * @param pageindex     当前页码
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/tuanbi/myinvitelist")
    Observable<String> myInviteList(@Field("distributorid") String distributorid, @Field("type") int type, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 个人主页
     *
     * @param distributorid    导游编号
     * @param seeDistributorId 查看导游编号
     * @param sign             签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/distributormain")
    Observable<String> distributorHome(@Field("distributorid") String distributorid, @Field("seeDistributorId") String seeDistributorId, @Field("sign") String sign);


    /**
     * 个人主页课程列表
     *
     * @param distributorid 待查询的导游编号
     * @param currPage      当前页
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/mystudylist")
    Observable<String> homeStudyList(@Field("distributorId") String distributorid, @Field("currPage") int currPage, @Field("sign") String sign);

    /**
     * 个人首页-蜂文列表
     *
     * @param distributorId    导游编号
     * @param seeDistributorId 查看导游编号
     * @param currPage         当前页
     * @param sign             签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/mytalklist")
    Observable<String> homeTalklist(@Field("distributorId") String distributorId, @Field("seeDistributorId") String seeDistributorId, @Field("currPage") int currPage, @Field("sign") String sign);

    /**
     * 点赞
     *
     * @param distributorId 导游编号
     * @param talkId        点赞的蜂文编号
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/zan")
    Observable<String> fengwenZan(@Field("distributorId") String distributorId,
                                  @Field("talkId") String talkId,
                                  @Field("sign") String sign);

    /**
     * 取消点赞
     *
     * @param distributorId 导游编号
     * @param talkId        点赞的蜂文编号
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/unzan")
    Observable<String> fengwenUnZan(@Field("distributorId") String distributorId,
                                    @Field("talkId") String talkId,
                                    @Field("sign") String sign);

    /**
     * 蜂文删除
     *
     * @param distributorId 导游编号
     * @param talkId        蜂文编号
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/talkdel")
    Observable<String> fengwenDel(@Field("distributorid") String distributorId, @Field("talkId") String talkId, @Field("sign") String sign);

    /**
     * 蜂圈-发现-头部
     *
     * @param distributorId 导游编号
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/findcircletop")
    Observable<String> findCircleTop(@Field("distributorid") String distributorId, @Field("sign") String sign);

    /**
     * 蜂圈动态
     *
     * @param distributorId           导游编号
     * @param keyword                 搜索关键字没有就 ""
     * @param tagId                   热门标签编号没有就""
     * @param prePageLastDataObjectId 上一页最后一条数据的编号(第一页查询"")
     * @param currPage                当前页
     * @param sign                    签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/findcircle")
    Observable<String> fengFindCircle(@Field("distributorId") String distributorId,
                                      @Field("keyword") String keyword,
                                      @Field("tagId") String tagId,
                                      @Field("prePageLastDataObjectId") String prePageLastDataObjectId,
                                      @Field("currPage") int currPage,
                                      @Field("sign") String sign);

    /**
     * 关注好友
     *
     * @param distributorId 导游编号
     * @param friendId      被关注导游编号
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/follow")
    Observable<String> useFollow(@Field("distributorId") String distributorId, @Field("friendId") String friendId, @Field("sign") String sign);

    /**
     * 发布蜂文标签
     *
     * @param distributorId
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/findtagandtopic")
    Observable<String> findTagandTopic(@Field("distributorId") String distributorId, @Field("sign") String sign);

    /**
     * 蜂圈话题列表
     *
     * @param currPage
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/topiclist1")
    Observable<String> topicList(@Field("currPage") int currPage, @Field("sign") String sign);

    /**
     * 发布蜂文
     *
     * @param distributorId 导游编号
     * @param tagIds        热门标签Ids（多个用英文竖线分割）
     * @param content       蜂文内容
     * @param picUrls       蜂文图片集合（多个用英文竖线分割）例如：图片地址1&图片小图地址1&图片宽度1&图片高度1
     * @param location      当前位置
     * @param topicId       话题Id
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/publishtalk")
    Observable<String> publishTalk(@Field("distributorId") String distributorId, @Field("tagIds") String tagIds, @Field("content") String content, @Field("picUrls") String picUrls, @Field("location") String location, @Field("topicId") String topicId, @Field("sign") String sign);

    /**
     * 名师讲堂-报名
     *
     * @param distributorid 导游ID
     * @param id            课程ID
     * @param couponid      听课券ID(导游领取后的记录ID)
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/applyteacher")
    Observable<String> applyTeacher(@Field("distributorid") String distributorid, @Field("id") String id, @Field("couponid") String couponid, @Field("sign") String sign);

    /**
     * 名师讲堂-查看
     *
     * @param distributorid 导游ID
     * @param id            课程ID
     * @param couponid      听课券ID(导游领取后的记录ID)
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/lookteacher")
    Observable<String> lookTeacher(@Field("distributorid") String distributorid, @Field("id") String id, @Field("couponid") String couponid, @Field("sign") String sign);

    /**
     * 更改学习状态
     *
     * @param distributorid
     * @param studyid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/study/updatestate")
    Observable<String> upDoDateState(@Field("distributorid") String distributorid, @Field("studyid") String studyid, @Field("sign") String sign);

    /**
     * 我的课程-可下载列表
     *
     * @param distributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/study/teacherdownloadlist")
    Observable<String> teacherDownloadList(@Field("distributorid") String distributorid, @Field("sign") String sign);


    /**
     * 未读消息数
     *
     * @param distributorid
     * @param getNewestDistributorId 是否获取最新评论或者点赞的用户编号 1=获取 其余=不获取
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/unreadcount")
    Observable<String> unreadCount(@Field("distributorId") String distributorid, @Field("getNewestDistributorId") int getNewestDistributorId, @Field("sign") String sign);

    /**
     * 我的团币-加载(蜂圈域名)
     *
     * @param distributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/tuanbi/mytasklist")
    Observable<String> myTaskList(@Field("distributorId") String distributorid, @Field("sign") String sign);

    /**
     * 我的团币-领取(蜂圈域名)
     *
     * @param distributorid
     * @param type          操作类型（对应值在下方注解）
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/tuanbi/taskoperate")
    Observable<String> taskOperate(@Field("distributorId") String distributorid, @Field("type") int type, @Field("sign") String sign);

    /**
     * 每日课程分享奖励团币
     *
     * @param distributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/tuanbi/studyshare")
    Observable<String> studyShare(@Field("distributorId") String distributorid, @Field("sign") String sign);

    /**
     * 蜂文收藏列表
     *
     * @param distributorid           导游编号
     * @param prePageLastDataObjectId 上一页最后一条数据的编号(第一页查询传空)
     * @param currPage                当前页
     * @param sign                    签名
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/talkcollectionlist")
    Observable<String> talkCollectionList(@Field("distributorid") String distributorid, @Field("prePageLastDataObjectId") String prePageLastDataObjectId, @Field("currPage") int currPage, @Field("sign") String sign);

    /**
     * 笑话/顺口溜-收藏列表
     *
     * @param distributorid
     * @param type          类型 1=笑话 2=顺口溜
     * @param pageindex
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/joke/collectionlist")
    Observable<String> collectionList(@Field("distributorId") String distributorid, @Field("type") int type, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 活动编辑加载
     *
     * @param distributorid
     * @param activityid    活动ID
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/activityeditload")
    Observable<String> activityEditload(@Field("distributorId") String distributorid, @Field("activityid") String activityid, @Field("sign") String sign);

    /**
     * 个人首页-关注列表
     *
     * @param distributorId
     * @param seeDistributorId
     * @param currPage
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/myfollowlist")
    Observable<String> myFollowList(@Field("distributorId") String distributorId, @Field("seeDistributorId") String seeDistributorId, @Field("currPage") int currPage, @Field("sign") String sign);

    /**
     * 个人首页-粉丝列表
     *
     * @param distributorId
     * @param seeDistributorId
     * @param currPage
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/circle/myfanslist")
    Observable<String> myFansList(@Field("distributorId") String distributorId, @Field("seeDistributorId") String seeDistributorId, @Field("currPage") int currPage, @Field("sign") String sign);


    /**
     * APP登录日志
     *
     * @param distributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/mall/qiandao")
    Observable<String> qianDao(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 等级自动升级(蜂圈接口)
     *
     * @param distributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXS + "/tuanbi/checklevelup")
    Observable<String> checkLevelUp(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 推荐讲师-提交
     *
     * @param distributorid
     * @param teachername
     * @param weixin
     * @param mobile
     * @param intro
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/teacher/recommendsubmit")
    Observable<String> recommendSubmit(@Field("distributorid") String distributorid, @Field("teachername") String teachername, @Field("weixin") String weixin, @Field("mobile") String mobile, @Field("intro") String intro, @Field("sign") String sign);


    /**
     * 推荐讲师-推荐记录
     *
     * @param distributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/teacher/recommendlist")
    Observable<String> recommendList(@Field("distributorid") String distributorid, @Field("pageindex") int pageindex, @Field("sign") String sign);

    /**
     * 删除服务图片
     *
     * @param imgPath
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/user/delimg")
    Observable<String> delImg(@Field("imgPath") String imgPath, @Field("sign") String sign);


    /**
     * 删除课程评论
     *
     * @param distributorid 导游编号
     * @param commentid     评论ID
     * @param sign          签名
     * @return
     */
    @FormUrlEncoded
    @POST("/study/delcomment")
    Observable<String> delComment(@Field("distributorId") String distributorid, @Field("commentid") String commentid, @Field("sign") String sign);

    /**
     * 导游信息获取
     *
     * @param distributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/user/getuserinfo")
    Observable<String> getUserInfo(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 团币充值优惠
     *
     * @param distributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/tuanbi/getrecharge")
    Observable<String> getRecharge(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 活动评论-删除
     *
     * @param distributorid
     * @param commentid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/delcomment")
    Observable<String> delActComment(@Field("distributorid") String distributorid, @Field("commentid") String commentid, @Field("sign") String sign);

    /**
     * 活动-报名人员
     *
     * @param activityid
     * @param pageindex
     * @param keyword
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST("/activity/applyuser")
    Observable<String> applyUser(@Field("activityid") String activityid, @Field("pageindex") int pageindex, @Field("keyword") String keyword, @Field("sign") String sign);

}
