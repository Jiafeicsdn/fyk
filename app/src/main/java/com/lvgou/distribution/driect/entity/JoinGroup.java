package com.lvgou.distribution.driect.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class JoinGroup extends ActionDescription {
	
	private String id;
	private String inviteeIdsListString;
	public String getInviteeIdsListString() {
		return inviteeIdsListString;
	}

	public void setInviteeIdsListString(String inviteeIdsListString) {
		this.inviteeIdsListString = inviteeIdsListString;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	 * 群组ID
	 * */
	@SerializedName("GroupId")
	private String groupId;
	
	/*
	 * 邀请者Id
	 * */
	@SerializedName("InviterId")
	private String inviterId;
	
	/*
	 * 被邀请者Id
	 * */
	@SerializedName("InviteeIds")
	private List<String> inviteeIds;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getInviterId() {
		return inviterId;
	}

	public void setInviterId(String inviterId) {
		this.inviterId = inviterId;
	}

	public List<String> getInviteeIds() {
		return inviteeIds;
	}

	public void setInviteeIds(List<String> inviteeIds) {
		this.inviteeIds = inviteeIds;
	}
	
	
}
