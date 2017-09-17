package com.lvgou.distribution.driect.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RemovedFromGroup extends ActionDescription{
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String id;
	//移除好友的字符串id list string
	private String memberIdsListString;
	public String getMemberIdsListString() {
		return memberIdsListString;
	}

	public void setMemberIdsListString(String memberIdsListString) {
		this.memberIdsListString = memberIdsListString;
	}

	@SerializedName("GroupId")
	private String groupId;
	
	@SerializedName("OperatorId")
	private String operatorId;
	
	@SerializedName("MemberIds")
	private List<String> memberIds;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public List<String> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<String> memberIds) {
		this.memberIds = memberIds;
	}
}
