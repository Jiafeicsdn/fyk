package com.lvgou.distribution.event;

import com.xdroid.eventbus.EventBus;

import cn.jpush.android.api.PushNotificationBuilder;

/**
 * 事件发送工厂
 *
 * @author Robin
 *         time 2015-04-22 14:28:54
 */
public class EventFactory {


    public static void sendTransitionHomeTab(int taskIndex) {
        EventBus.getDefault().post(new Event<Integer>(EventType.TASK_INDEX, taskIndex));
    }


    public static void sendUpdateCartCount(int goodsIndex) {
        EventBus.getDefault().post(new Event<Integer>(EventType.GOODS_INDEX, goodsIndex));
    }


    public static void sendGoodsGuider(int goodsIndex) {
        EventBus.getDefault().post(new Event<Integer>(EventType.GOODS_GUIDER, goodsIndex));
    }


    public static void sendSms(int goodsIndex) {
        EventBus.getDefault().post(new Event<Integer>(EventType.SMS_DETETE, goodsIndex));
    }


    public static void saveOrder(int goodsIndex) {
        EventBus.getDefault().post(new Event<Integer>(EventType.ORDER_COMPLETE, goodsIndex));
    }


    public static void goodsDismiss(int goodsIndex) {
        EventBus.getDefault().post(new Event<Integer>(EventType.GOOD_DISMISS, goodsIndex));
    }


    public static void marketDismiss(int goodsIndex) {
        EventBus.getDefault().post(new Event<Integer>(EventType.MARKET_DISMISS, goodsIndex));
    }


    public static void removeImagePostion(int numIndex) {
        EventBus.getDefault().post(new Event<Integer>(EventType.DELETE_IMAGE_POSTION, numIndex));
    }


    public static void cancleBaoBei(int numIndex) {
        EventBus.getDefault().post(new Event<Integer>(EventType.CANCLE_REPORT, numIndex));
    }

    public static void updateMap(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.MAP_REFRESH, index));
    }


    public static void turnToTeacher(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.TURN_TECHER_FRGMENT, index));
    }

    public static void turnToClass(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.TURN_CLASSES_FRAGMENT, index));
    }

    public static void dissPopwindowAll(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.DISSMISS_POPWINDOW_ALL, index));
    }

    public static void dissPopwindowSend(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.DISSMISS_POPWINDOW_SEND, index));
    }

    public static void dissPopwindowCarry(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.DISSMISS_POPWINDOW_CARRY, index));
    }

    public static void upDateGroupSend(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.UPDATE_GROUP_SEND, index));
    }

    public static void updateFengWn(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.UPDATE_FENGWEN, index));
    }

    public static void turnHomeCollege(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.TURN_HOME_COLLEGE, index));
    }

    public static void dimissFindTanKuang(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.DIMISS_FIND_TANKUANG, index));
    }

    public static void updateExchangeList(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.UPDATE_EXCHANGE_LIST, index));
    }

    public static void updateTuanbiTask(int index) {
        EventBus.getDefault().post(new Event<Integer>(EventType.UPDATE_TUANBI_TASK, index));
    }

    public static void showChoujiang(String index) {
        EventBus.getDefault().post(new Event<String>(EventType.SHOW_CHOUJIANG, index));
    }

    public static void updateCornerIndex(String index) {
        EventBus.getDefault().post(new Event<String>(EventType.UPDATE_CORNER_INDEX, index));
    }

    public static void updateHomeCircle(String index) {
        EventBus.getDefault().post(new Event<String>(EventType.UPDATE_HOME_CIRCLE, index));
    }
    public static void updateHomeCenter(String index) {
        EventBus.getDefault().post(new Event<String>(EventType.UPDATE_HOME_CENTER, index));
    }
}
