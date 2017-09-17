package com.lvgou.distribution.entity;

/**
 * Created by Snow on 2016/10/18.
 */
public class CircleCommZanEntity {


    private String id;
    private String fenwenId;
    private String userId;
    private String userName;
    private String userType;
    private String replayName;
    private String replyId;
    private String isRz;
    private String time;
    private String fengwenDistributorID;
    private String isReplayButton;// 1显示，0 隐藏
    private String messageType;// 1 评论，2 点赞
    private String title; // 回复title
    private String distributorID;// 登录者 id
    private String content;// 内容，读取字段  FengwenTitle


    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getFengwenDistributorID() {
        return fengwenDistributorID;
    }

    public void setFengwenDistributorID(String fengwenDistributorID) {
        this.fengwenDistributorID = fengwenDistributorID;
    }

    public String getReplayName() {
        return replayName;
    }

    public void setReplayName(String replayName) {
        this.replayName = replayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFenwenId() {
        return fenwenId;
    }

    public void setFenwenId(String fenwenId) {
        this.fenwenId = fenwenId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIsRz() {
        return isRz;
    }

    public void setIsRz(String isRz) {
        this.isRz = isRz;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsReplayButton() {
        return isReplayButton;
    }

    public void setIsReplayButton(String isReplayButton) {
        this.isReplayButton = isReplayButton;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistributorID() {
        return distributorID;
    }

    public void setDistributorID(String distributorID) {
        this.distributorID = distributorID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public CircleCommZanEntity(String id, String fenwenId, String userId, String userName, String userType, String isRz, String time, String isReplayButton, String messageType, String title, String distributorID, String content, String replayName, String fengwenDistributorID, String replyId) {
        this.id = id;
        this.fenwenId = fenwenId;
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.isRz = isRz;
        this.time = time;
        this.isReplayButton = isReplayButton;
        this.messageType = messageType;
        this.title = title;
        this.distributorID = distributorID;
        this.content = content;
        this.replayName = replayName;
        this.fengwenDistributorID = fengwenDistributorID;
        this.replyId = replyId;
    }

    @Override
    public String toString() {
        return "CircleCommZanEntity{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userType='" + userType + '\'' +
                ", isRz='" + isRz + '\'' +
                ", time='" + time + '\'' +
                ", isReplayButton='" + isReplayButton + '\'' +
                ", messageType='" + messageType + '\'' +
                ", title='" + title + '\'' +
                ", distributorID='" + distributorID + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
