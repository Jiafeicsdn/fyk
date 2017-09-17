package com.lvgou.distribution.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lvgou.distribution.activity.ActDetailActivity;
import com.lvgou.distribution.activity.ApplicationActivity;
import com.lvgou.distribution.activity.ApplyCourseActivity;
import com.lvgou.distribution.activity.ApplyTeacherActivity;
import com.lvgou.distribution.activity.CertificationActivity;
import com.lvgou.distribution.activity.CollegeManagerActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.activity.InviteFrendsActivity;
import com.lvgou.distribution.activity.MyProfitActivity;
import com.lvgou.distribution.activity.MyTuanBiActivity;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.NewFreeSmsActivity;
import com.lvgou.distribution.activity.NewRecomFengWenDetailsActivity;
import com.lvgou.distribution.activity.NoticeDetialActivity;
import com.lvgou.distribution.activity.OpreatGuideActivity;
import com.lvgou.distribution.activity.OrderCentralActivity;
import com.lvgou.distribution.activity.SeeCertificationActivity;
import com.lvgou.distribution.activity.SeriesClassActivity;
import com.lvgou.distribution.activity.TopicDetailsActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 * 推送页面，打开，页面操作
 * Jpush跳转
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
//        Log.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
//            MyToast.makeText(context, "regId"+regId, Toast.LENGTH_SHORT).show();

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//            Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//        	processCustomMessage(context, bundle);
//        	MyToast.makeText(context, "regId"+bundle.getString(JPushInterface.EXTRA_MESSAGE),Toast.LENGTH_SHORT).show();

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            for (String key : bundle.keySet()) {
                if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                    if (!bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                        String url_ = bundle.getString(JPushInterface.EXTRA_EXTRA);
                        try {
                            JSONObject jsonObject = new JSONObject(url_);
                            String url_content = jsonObject.getString("url");
                            turnView(url_content, context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
//            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
//            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }


    /**
     * 原生态打开：
     * 1、公告详情：http://agent.quygt.com/user/messagedetail/77，77为当前公告文章编号
     * 2、推荐有奖(邀请好友)：tjyj
     * 3、我的收益：wdsy
     * 4、订单管理：ddgl+状态 例：ddgl2 状态值:1=待付款 2=待发货 3=已发货 4=已成功 5=全部
     * 5、免费短信：mfdx
     * 6、我的任务：wdrw
     * 7、随时赚：ssz
     * 8、实名认证：smrz
     * 9、新手指南：xszn
     * 10、官方蜂文：FengWen + 蜂文编号 例：FengWen5833f4d32bab2412ec815e9e
     * 11、普通蜂文：UserFengWen + 蜂文编号 例：UserFengWen582d26cb2bab2604b8a469a4
     * 12、话题：Topic + 话题编号 例：Topic57ec78f32bab24123c26dc5c
     * 13、学院详情：http://agent.quygt.com/study/teacherdetail?id=80 ,80为当前课堂的编号
     * 14、专题列表：Series+专题的编号 例：Series1
     * 15、活动详情：Activity+活动编号 例：Activity1
     * 16、申请开课：ApplyForStudy
     * 17、申请讲师：ApplyForTeacher
     *
     * @param url
     */
    public void turnView(String url, Context context) {
        Bundle pBundle = new Bundle();
        String[] str_urls = url.split("/");
        if (url.contains("/user/messagedetail/")) { // 公告详情
            pBundle.putString("index", "1");
            pBundle.putString("id", str_urls[5].toString());
            Intent intent_one = new Intent(context, NoticeDetialActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.equals("tjyj")) {// 推荐有奖(邀请好友)
            Intent intent_one = new Intent(context, InviteFrendsActivity.class);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.equals("wdsy")) {// 我的收益
            pBundle.putString("index", "1");
            Intent intent_one = new Intent(context, MyProfitActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.contains("ddgl")) {// 订单管理
            String orderId = url.replace("ddgl", "");
            if (orderId.equals("1") || orderId.equals("2") || orderId.equals("3") || orderId.equals("4") || orderId.equals("5")) {
                Intent intent_one = new Intent(context, OrderCentralActivity.class);
                pBundle.putString("index", "1");
                pBundle.putString("tab", orderId);
                intent_one.putExtras(pBundle);
                intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent_one);
            }
        } else if (url.equals("mfdx")) {// 免费短信
            pBundle.putString("index", "1");
            pBundle.putString("state", "0");
            pBundle.putString("name", "");
            pBundle.putString("content", "");
            pBundle.putString("id", "");
            Intent intent_one = new Intent(context, NewFreeSmsActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.equals("wdrw")) {//(我的任务）
            Intent intent_one = new Intent(context, MyTuanBiActivity.class);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.equals("ssz")) {// 随时赚
            pBundle.putString("index", "1");
            Intent intent_one = new Intent(context, ApplicationActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.equals("smrz")) {// 实名认证
            String isover = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
            String state = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
            if (isover.equals("true") && state.equals("5")) {
                Intent intent_one = new Intent(context, SeeCertificationActivity.class);
                intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent_one);
            } else {
                Intent intent_one = new Intent(context, CertificationActivity.class);
                intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent_one);
            }
        } else if (url.equals("xszn")) {//（新手指南）
            pBundle.putString("index", "1");
            Intent intent_one = new Intent(context, OpreatGuideActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.contains("UserFengWen")) {//普通蜂文
            String fengId = url.replace("UserFengWen", "");
            Intent intent_one = new Intent(context, NewDynamicDetailsActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.putExtra("position", "0");
            intent_one.putExtra("talkId", fengId);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.contains("FengWen")) {//（官方蜂文）
            String fengId = url.replace("FengWen", "");
            CircleRecommentEntity circleRecommentEntity = new CircleRecommentEntity();
            circleRecommentEntity.setID(fengId);
            Intent intent_one = new Intent(context, NewRecomFengWenDetailsActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.putExtra("talkId", fengId);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.contains("Topic")) {//（话题）
            String topicId = url.replace("Topic", "");
            Intent intent_one = new Intent(context, TopicDetailsActivity.class);
            intent_one.putExtra("topicId", topicId);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.contains("study/teacherdetail")) {//学院详情
            String[] ids_ = url.split("=");
            Intent intent_one = new Intent(context, CourseIntrActivity.class);
            intent_one.putExtra("id", ids_[1]);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.equals("Series")) {//(专题列表）
            String linkUrl=url.replace("Series","");
            Intent intent = new Intent(context, SeriesClassActivity.class);
            intent.putExtra("linkUrl", linkUrl);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
          /*  String linkUrl=url.replace("Series","");
            Intent intent_one = new Intent(context, CollegeManagerActivity.class);
            intent_one.putExtra("currentPage",4);
            intent_one.putExtra("linkUrl",linkUrl);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);*/
        }else if (url.equals("Activity")) {//(活动详情）
            String activityId = url.replace("Activity", "");
            Intent intent = new Intent(context, ActDetailActivity.class);
            intent.putExtra("activityid", activityId);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }else if (url.contains("ApplyForStudy")) {//申请开课
            Intent intent = new Intent(context, ApplyCourseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } else if (url.contains("ApplyForTeacher")) {//申请讲师
            Intent intent2 = new Intent(context, ApplyTeacherActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent2);
        }
        //---------------
      /*  else if (url.equals("jdbb")) {// 进店报备
            pBundle.putString("index", "1");
            Intent intent_one = new Intent(context, ReportActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.equals("hwsp")) {// 海外商品
            pBundle.putString("index", "1");
            Intent intent_one = new Intent(context, GuoNeiActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.equals("fyxy")) {// 蜂优学院
            pBundle.putString("index", "1");
            Intent intent_one = new Intent(context, CollegeMangeActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.equals("bpst")) {//（爆品速推）
            pBundle.putString("index", "1");
            pBundle.putString("goods_id", "0");
            Intent intent_two = new Intent(context, PushSpeedActivity.class);
            intent_two.putExtras(pBundle);
            intent_two.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_two);
        } else if (url.contains("Seek")) {
            String seekid = url.replace("Seek", "");
            Intent intent_one = new Intent(context, PublishGroupDetialActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.putExtra("groupType", "1");
            intent_one.putExtra("seekid", seekid);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.contains("Topic")) {
            String topicId = url.replace("Topic", "");
            Intent intent_one = new Intent(context, TopicDetailsActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.putExtra("topicId", topicId);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        } else if (url.contains("study/teacherdetail")) {
            Bundle bundle = new Bundle();
            String[] ids_ = url.split("id=");
            String id = ids_[1].trim();
            Intent intent_one1 = new Intent(context, FamousTeacherDetialActivity.class);
            bundle.putString("index", "1");
            bundle.putString("id", id);
            intent_one1.putExtras(bundle);
            intent_one1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one1);
           *//* openActivity(FamousTeacherDetialActivity.class, bundle);//  名师讲堂 详情页 原生态打开打开
            String topicId = url.replace("Topic", "");
            Intent intent_one = new Intent(context, TopicDetailsActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.putExtra("topicId", topicId);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);*//*
        } else {
            pBundle.putString("index", "1");
            pBundle.putString("url", url);
            Intent intent_one = new Intent(context, WebViewActivity.class);
            intent_one.putExtras(pBundle);
            intent_one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent_one);
        }*/
    }


    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
//                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
