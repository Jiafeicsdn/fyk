package com.lvgou.distribution.api;

import com.lvgou.distribution.bean.AddUser;
import com.lvgou.distribution.bean.GetGroupMessage;
import com.lvgou.distribution.bean.GetMemberList;
import com.lvgou.distribution.bean.JoinChatGroupBean;
import com.lvgou.distribution.bean.SmsBean;

import javax.crypto.spec.IvParameterSpec;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Author:zcmain on 2016/5/13 15:32
 * E-Mail:zcmain@163.com
 * 说明：
 * 1. 使用 retrofit将http转化为 Java接口供调用
 * 2. RxJavaCallAdapterFactory.它把Call转换成Observable.
 */
public interface IIMServiceAPI {

    //线上
//    public static final String ROOTXX = "https://d2.api.quygt.com:442";
    //线下
    public static final String ROOTXX = "http://192.168.1.24:8085";

    //新增IM用户
    @POST("Account/RegisterOtherUser")
    Observable<String> addUser(@Query("nickName") String niakName, @Body AddUser imId);

    //群关联用户
    @POST("Friends/JoinChatGroup")
    Observable<String> JoinChatGroup(@Query("access_token") String access_token, @Body JoinChatGroupBean imId);

    //获取聊天服务器地址
    @GET("IMAllocation/Get")
    Observable<String> getServer(@Query("access_token") String access_token);

    //群成员列表
    @POST("Merge/MergeData")
    Observable<String> member_list(@Query("access_token") String access_token, @Body GetMemberList imId);

    //获取历史消息
    @POST("IMAllocation/GetGroupMessageExt")
    Observable<String> GetGroupMessageExt(@Query("access_token") String access_token, @Body GetGroupMessage imId);

    //视频上传
    @POST("UpLoadFile/UploadVideo")
    Observable<String> UploadVideo(@Query("access_token") String access_token, @Body GetGroupMessage imId);


    //图片上传
    @POST("UpLoadFile/UploadImage")
    Observable<String> UploadImage(@Query("access_token") String access_token, @Body GetGroupMessage imId);

    //语音上传
    @POST("UpLoadFile/UploadAudio")
    Observable<String> UploadAudio(@Query("access_token") String access_token, @Body GetGroupMessage imId);

    //获取直播观看人数接口
    @POST("Friends/GetGroupInfo")
    Observable<String> GetGroupInfo(@Query("access_token") String access_token, @Query("groupId") String groupId);

    //群组中移除用户
    @POST("Friends/RemoveMemberGroup")
    Observable<String> RemoveMemberGroup(@Query("access_token") String access_token, @Body JoinChatGroupBean imId);

    //获取用户昵称
    @FormUrlEncoded
    @POST(ROOTXX + "/study/GetNickName")
    Observable<String> GetNickName(@Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 学员禁言列表
     *
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXX + "/study/prohibitlist")
    Observable<String> Prohibitlist(@Field("studyid") String studyid, @Field("sign") String sign);

    /**
     * 解除禁言
     *
     * @param kdbdistributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXX + "/study/releaseshutup")
    Observable<String> Releaseshutup(@Field("kdbdistributorid") String kdbdistributorid, @Field("studyid") String studyid, @Field("distributorid") String distributorid, @Field("sign") String sign);

    /**
     * 直播间禁言
     *
     * @param kdbdistributorid
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(ROOTXX + "/study/bannedtopost")
    Observable<String> Bannedtopost(@Field("kdbdistributorid") String kdbdistributorid, @Field("studyid") String studyid, @Field("distributorid") String distributorid, @Field("sign") String sign);

    //移除用户
//    @FormUrlEncoded
//    @POST("supply/rankinglist")
//    Observable<String> getRankList(@Field("distributorid") String distributorid, @Field("type") String type, @Field("sign") String sign);
//Observable<String> addUser(@Path("nickName") String niakName, @Field("UI") String imId);
    @FormUrlEncoded
    @POST("http://appapi01.safetree.com.cn/api/Merge/MergeData?access_token=1")
    Observable<String> getGroupInfo(@Field("IMGMV") String groupInfo);
}
