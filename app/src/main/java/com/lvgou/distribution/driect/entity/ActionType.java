package com.lvgou.distribution.driect.entity;

public enum ActionType {
	UnKnow("UnKnow",0), 
	/*
	 * 加入群组
	 * */
	JoinGroup("JoinGroup",1001),
	/*
	 * 移除群组成员
	 * */
	RemovedFromGroup("RemovedFromGroup",1002),
	/*
	 * 群组名称修改
	 * */
	GroupRename("GroupRename",1003),
	
	/*
	 * 创建群组
	 * */
	CreateGroup("CreateGroup",1004),
	/*
	 * 退出群组
	 * */
	ExitGroup("ExitGroup",1005),
	/*
	 * 群组解散
	 * */
	GroupDissolution("GroupDissolution",1006),
	
	/*
	 * 好友备注修改
	 * */
	FriendRemarkModify("FriendRemarkModify",2001),
	/*
	 * 好友申请
	 * */
	FriendRequest("FriendRequest",2002),
	/*
	 * 申请回复
	 * */
	FriendRequestReply("FriendRequestReply",2003),
	/*
	 * 同意好友申请
	 * */
	FriendRequestAllow("FriendRequestAllow",2004),
	/*
	 * 删除好友
	 * */
	FriendRemoved("FriendRemoved",2005),
	
	/*
	 * 用户昵称修改
	 * */
	UserNickNameModify("UserNickNameModify",3001),
	
	/*
	 * 用户头像修改
	 * */
	UserAvatarModify("UserAvatarModify",3002);
	private String name;
	public String getName() {
		return name;
	}

	private int value;

	ActionType(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static ActionType parse(int value) {
		for (ActionType c : ActionType.values()) {
			if (c.getValue() == value) {
				return c;
			}
		}
		return ActionType.UnKnow;
	}
}
