package com.lvgou.distribution.driect.entity;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * 功能推送
 * */
public abstract class ActionNotification  {
	private String id;
	@SerializedName("AT")
	@Expose(serialize = false, deserialize = true)
	private  ActionType actionType ;
	private Date creationTime;

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
    public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public abstract ActionDescription getActionDescription();
}