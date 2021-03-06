package com.lvgou.distribution.driect;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.base.BaseApplication;
import com.lvgou.distribution.bean.GroupMessageExtData;
import com.lvgou.distribution.driect.entity.ActionNotification;
import com.lvgou.distribution.driect.entity.ActionType;
import com.lvgou.distribution.driect.entity.GroupMessage;
import com.lvgou.distribution.driect.entity.JoinGroup;
import com.lvgou.distribution.driect.entity.MessageHandler;
import com.lvgou.distribution.driect.entity.MessageType;
import com.lvgou.distribution.driect.entity.RemovedFromGroup;
import com.lvgou.distribution.driect.entity.SingleMessage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import microsoft.aspnet.signalr.client.ConnectionState;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;

/**
 * Created by Snow on 2016/8/12
 */
public class ChatClient {
    public HubConnection connection;
    private HubProxy proxy;

    /**
     * @param
     */
    public ChatClient(HubConnection connection, HubProxy proxy) {
        this.connection = connection;
        this.proxy = proxy;
        SetProxy(proxy);
    }

    private void SetProxy(HubProxy proxy) {
        if (proxy == null) {
            return;
        }
        final MessageHandler messageHandler = new MessageHandler();
        proxy.on("ReceiveSingleMessage",
                new SubscriptionHandler1<GroupMessage>() {//SingleMessage
                    @Override
                    public void run(GroupMessage message) {
                        Log.e("askjgfjdsaghfd", "--------222222--------");
//                            GloableParams.connectWorking = DataHelper.getNowDate();
                        if (null != message) {
                            System.out.println("=====message====" + message);
                                messageHandler.receive(message,1);// 数据库、会话层、界面等更新
                            Date creationTime = message.getCreationTime();
                            String senderId = message.getSenderId();
                            String chatId = "";
                            // 用户ID shardpreference  保存 Utils.getValue(App.getInstance(), Constants.ID)
                            /*if (senderId.equals("用户 id")) {
                                if (message.getReceiverId().equals(senderId)) {
                                    return;
                                } else {
                                    chatId = message.getReceiverId();
                                }
                            } else {
                                chatId = senderId;
                            }*/
//                                sendBrodcast(message.getId(), senderId, creationTime, Constants.SINGLE, chatId, convertType(message.getMessageType()));
                        }
                    }
                }, GroupMessage.class);

        proxy.on("ReceiveGroupMessage",
                new SubscriptionHandler1<GroupMessage>() {
                    @Override
                    public void run(GroupMessage message) {
                        Log.e("askjgfjdsaghfd", "---------群组接收-------");
//                            GloableParams.connectWorking = DataHelper.getNowDate();
                        if (null != message) {
//                            if (null != connection) {
//                                connection.stop();
//                                connection.disconnect();
//                                connection = null;
//                            }

                            messageHandler.receive(message);
                            Date creationTime = message.getCreationTime();
                            String senderId = message.getSenderId();
                            String chatId = message.getGroupId();

                        }
                    }
                }, GroupMessage.class);
//            proxy.on("ReceiveIllustratedMaterialMessage",
//                    new SubscriptionHandler1<MaterialPushViewModel>() {
//                        @Override
//                        public void run(MaterialPushViewModel message) {
//                            GloableParams.connectWorking = DataHelper.getNowDate();
//                            if (null != message) {
//                                System.out.println(message);
//                                messageHandler.receive(message);
//                                String ticker = String.valueOf(message
//                                        .getCategroyName());
//                                if (null != message.getIMMaterialInfo()
//                                        && !message.getIMMaterialInfo().isEmpty()) {
//                                    IllustratedListViewModel Model = message
//                                            .getIMMaterialInfo().get(0);
//                                    if (null != Model.getImIllustrateds()
//                                            && !Model.getImIllustrateds().isEmpty()) {
//                                        String digest = Model.getImIllustrateds()
//                                                .get(0).getTitle();
//                                        if (digest.length() >= 15) {
//                                            digest = digest.substring(0, 15)
//                                                    + "...";
//                                        }
//
//                                        sendBrodcast(Constants.SYSTEMMATERIAL,
//                                                ticker, digest);
//                                    }
//                                }
//                            }
//                        }
//                    }, MaterialPushViewModel.class);
//
//            proxy.on("ReceiveNoticeMessage",
//                    new SubscriptionHandler1<CustomNoticeViewModel>() {
//                        @Override
//                        public void run(CustomNoticeViewModel message) {
//                            GloableParams.connectWorking = DataHelper.getNowDate();
//                            if (null != message) {
//                                System.out.println(message);
//                                messageHandler.receive(message);
//                                String ticker = String.valueOf(message
//                                        .getCreatorId());
//                                String digest = message.getTitle();
//                                if (!TextUtils.isEmpty(digest)) {
//                                    if (digest.length() >= 15) {
//                                        digest = digest.substring(0, 15) + "...";
//                                    }
//                                    sendBrodcast(Constants.PLATNOTICE, ticker,
//                                            digest);
//                                }
//
//                            }
//                        }
//                    }, CustomNoticeViewModel.class);
//
//            // 一个账号在不同设备的登录的异常信息接收
//
//            proxy.on("ForceOffline",
//                    new SubscriptionHandler1<OfflineReasonMessage>() {
//                        @Override
//                        public void run(OfflineReasonMessage message) {
//                            App.getInstance2().getConnection().deleteConnect(false);
//                            // TODO Auto-generated method stub
//                            OfflineReason offlineReason = message.getReason();
//                            int type = offlineReason.getType();
//                            if (message != null) {
//                                Log.i("具体信息为", message.getMessage());
//                                Log.i("类型", type + "");
//                                sendExitLoginBroadCast(message.getMessage(), type);
//                            }
//                            Log.i("消息为空", null);
//                        }
//                    }, OfflineReasonMessage.class);
//
        proxy.on("ActionNotify",
                new SubscriptionHandler1<ActionNotification>() {
                    @Override
                    public void run(ActionNotification message) {
//                            GloableParams.connectWorking = DataHelper.getNowDate();
//                            if (null != message) {
//                                Log.i("功能通知2016511", "功能通知2016511");
//                                System.out.println("88888" + message);
//                                Date tempDate = new Date();
//                                messageHandler.receive(message, tempDate);
                        if (message.getActionType() == ActionType.JoinGroup) {
                            Intent i = new Intent("com.distribution.group.member.list");
                            BaseApplication.context.sendBroadcast(i);
//                                    JoinGroup joinGroup = (JoinGroup) message
//                                            .getActionDescription();
//                                    String chatId = joinGroup.getGroupId();
//                                    String memberId = joinGroup.getInviterId();
//
//                                    List<String> inviteeIds = joinGroup
//                                            .getInviteeIds();
//                                    sendBrodcast(joinGroup.getId(), memberId,
//                                            tempDate, Constants.GROUP, chatId,
//                                            Constants.TXT, inviteeIds);
                        } else if (message.getActionType() == ActionType.RemovedFromGroup) {
//                                    RemovedFromGroup removedFromGroup = (RemovedFromGroup) message
//                                            .getActionDescription();
//                                    String groupId = removedFromGroup.getGroupId();
//                                    String memberId = removedFromGroup
//                                            .getOperatorId();
//                                    List<String> memberIds = removedFromGroup
//                                            .getMemberIds();
//                                    sendBrodcast(removedFromGroup.getId(),
//                                            memberId, tempDate, Constants.GROUP,
//                                            groupId, Constants.TXT, memberIds);
                            Intent i = new Intent("com.distribution.group.member.list");
                            BaseApplication.context.sendBroadcast(i);
                        }

                    }
//                        }
                }, ActionNotification.class);
    }

    public SignalRFuture<String> send(SingleMessage message) {
        Log.e("askdjfhkah", "------------------3" );
        if (this.connection.getState() == ConnectionState.Connected) {
            Log.e("askdjfhkah", "------------------4" );
//            Log.e("askfdhksafd", "--------------" + message.getSenderId());
            return proxy.invoke(String.class, "SendSingleMessage", message);
        } else {
            return null;
        }
    }

    public SignalRFuture<String> send(GroupMessage message) {
        Log.e("askdjfhkah", "------------------1" );
        if (this.connection.getState() == ConnectionState.Connected) {
            Log.e("askdjfhkah", "------------------2" );
//            Log.e("askfdhksafd", "------group--------" + message.getSenderId());
            return proxy.invoke(String.class, "SendGroupMessage", message);
        } else {
            return null;
        }
    }


//        public SignalRFuture<String> send(SendCustomNoticeModel message) {
//            if (this.connection.getState() == ConnectionState.Connected) {
//                return proxy.invoke(String.class, "sendNotice", message);
//            } else {
//                return null;
//            }
//        }

//    private int convertType(MessageType messagetype) {
//        int msgtype = -1;
//        switch (messagetype) {
//            case Text:
//                msgtype = Constants.TXT;
//                break;
//            case Image:
//                msgtype = Constants.IMAGE;
//                break;
//            case Voice:
//                msgtype = Constants.VOICE;
//                break;
//            case ShortVideo:
//                msgtype = Constants.SHORTVIDEO;
//                break;
//            case FileTransfer:
//                msgtype = Constants.FILETRANSFER;
//                break;
//            case SafetyAlert:
//                msgtype = Constants.SafetyAlert;
//                break;
//            case Function:
//                msgtype = Constants.Function;
//                break;
//            case ActionNotify:
//                msgtype = Constants.ActionNotify;
//                break;
//        }
//        return msgtype;
//    }

    private void sendExitLoginBroadCast(String message, int type) {
        // TODO Auto-generated method stub
        switch (type) {
            case -1:

                break;
            case 0:

                break;

            case 1:

                break;
            case 2:
//                Intent intent = new Intent();
//                intent.setAction("com.ParentsHelper.receiver.ExitLogin2");
//                intent.putExtra("message", message);
//                App.getInstance().sendBroadcast(intent);
//                Log.i("异常信息", message);
                break;

            case 3:

                break;

        }

    }
}
//        private void sendBrodcast(String id, String senderId, Date creationTime,
//                                  int chatType, String chatId, int msgType) {
//            // if (App.getInstance2().isBackground()) {
//            // MessageBody message = MessageBodyDao.findMessageBody(chatId,
//            // chatType, creationTime, senderId, msgType);
//            // String ticker =
//            // CommonUtils.getMessageDigest(message,App.getInstance());
//            // String name =CommonUtils.getMessageName(message,App.getInstance());
//            // App.getInstance2().notifyNewMessage(name,ticker);
//            // }
//            Log.i("sendBordcast", "点对点消息广播");
//            Intent intent = new Intent();
//            intent.setAction("com.jzzs.ParentsHelper.Brodcast");
//            intent.putExtra("id", id);
//            intent.putExtra("chatType", chatType);
//            intent.putExtra("chatId", chatId);
//            intent.putExtra("senderId", senderId);
//            intent.putExtra("creationTime", creationTime.getTime());
//            intent.putExtra("msgType", msgType);
//            App.getInstance().sendOrderedBroadcast(intent, null);
//
//        }
//
//        // 用来收到添加好友请求的即时推送消息
//        private void sendBrodcast(String id, String senderId, Date creationTime,
//                                  int chatType, String chatId, int msgType, String petitionerName,
//                                  String petitionerAvatar, String petitionerMessage) {
//            // if (App.getInstance2().isBackground()) {
//            // MessageBody message = MessageBodyDao.findMessageBody(chatId,
//            // chatType, creationTime, senderId, msgType);
//            // String ticker =
//            // CommonUtils.getMessageDigest(message,App.getInstance());
//            // String name =CommonUtils.getMessageName(message,App.getInstance());
//            // App.getInstance2().notifyNewMessage(name,ticker);
//            // }
//            Intent intent = new Intent();
//            intent.setAction("com.jzzs.ParentsHelper.Brodcast");
//            intent.putExtra("id", id);
//            intent.putExtra("chatType", chatType);
//            intent.putExtra("chatId", chatId);
//            intent.putExtra("senderId", senderId);
//            intent.putExtra("creationTime", creationTime.getTime());
//            intent.putExtra("msgType", msgType);
//            intent.putExtra("petitionerName", petitionerName);
//            intent.putExtra("petitionerAvatar", petitionerAvatar);
//            intent.putExtra("petitionerMessage", petitionerMessage);
//            App.getInstance().sendOrderedBroadcast(intent, null);
//
//        }
//
//        private void sendBrodcast(String id, String senderId, Date creationTime,
//                                  int chatType, String chatId, int msgType, String groupName) {
//            // if (App.getInstance2().isBackground()) {
//            // MessageBody message = MessageBodyDao.findMessageBody(chatId,
//            // chatType, creationTime, senderId, msgType);
//            // String ticker =
//            // CommonUtils.getMessageDigest(message,App.getInstance());
//            // String name =CommonUtils.getMessageName(message,App.getInstance());
//            // App.getInstance2().notifyNewMessage(name,ticker);
//            // }
//            Intent intent = new Intent();
//            intent.setAction("com.jzzs.ParentsHelper.Brodcast");
//            intent.putExtra("id", id);
//            intent.putExtra("chatType", chatType);
//            intent.putExtra("chatId", chatId);
//            intent.putExtra("senderId", senderId);
//            intent.putExtra("creationTime", creationTime.getTime());
//            intent.putExtra("msgType", msgType);
//            intent.putExtra("groupName", groupName);
//            App.getInstance().sendOrderedBroadcast(intent, null);
//
//        }
//
//        /*
//         * 邀请好友id加入群组广播,及群组好友踢出去
//         */
//        private void sendBrodcast(String id, String senderId, Date creationTime,
//                                  int chatType, String chatId, int msgType, List<String> inviteeIds) {
//            // if (App.getInstance2().isBackground()) {
//            // MessageBody message = MessageBodyDao.findMessageBody(chatId,
//            // chatType, creationTime, senderId, msgType);
//            // String ticker =
//            // CommonUtils.getMessageDigest(message,App.getInstance());
//            // String name =CommonUtils.getMessageName(message,App.getInstance());
//            // App.getInstance2().notifyNewMessage(name,ticker);
//            // }
//            Intent intent = new Intent();
//            intent.setAction("com.jzzs.ParentsHelper.Brodcast");
//            intent.putExtra("id", id);
//            intent.putExtra("chatType", chatType);
//            intent.putExtra("chatId", chatId);
//            intent.putExtra("senderId", senderId);
//            intent.putExtra("creationTime", creationTime.getTime());
//            intent.putExtra("msgType", msgType);
//            intent.putStringArrayListExtra("inviteeIds",
//                    (ArrayList<String>) inviteeIds);
//            App.getInstance().sendOrderedBroadcast(intent, null);
//
//        }
//
//        private void sendBrodcast(int chatType, String name, String ticker) {
//            // if (App.getInstance2().isBackground()) {
//            // App.getInstance2().notifyNewMessage(name,ticker);
//            // }
//            Intent intent = new Intent();
//            intent.setAction("com.jzzs.ParentsHelper.Brodcast");
//            intent.putExtra("chatType", chatType);
//            intent.putExtra("ticker", ticker);
//            intent.putExtra("name", name);
//            App.getInstance().sendOrderedBroadcast(intent, null);
//
//        }
//    }

