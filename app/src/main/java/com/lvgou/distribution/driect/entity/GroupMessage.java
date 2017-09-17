package com.lvgou.distribution.driect.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 群组消息
 */
public abstract class GroupMessage {

	@SerializedName("I")
	@Expose(serialize = false, deserialize = true)
	private String id;

	@SerializedName("SI")
	@Expose(serialize = false, deserialize = true)
	private String senderId;

	@Expose(serialize = true, deserialize = true)
	@SerializedName("GI")
	private String groupId;

	@SerializedName("CT")
	@Expose(serialize = false, deserialize = true)
	private Date creationTime;

	/**
	 * 获取消息编号
	 *
	 * @return 消息编号
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置消息编号
	 *
	 * @param value
	 *            消息编号
	 */
	public void setId(String value) {
		this.id = value;
	}

	/**
	 * 获取发送者编号
	 *
	 * @return 发送者编号
	 */
	public String getSenderId() {
		return this.senderId;
	}

	/**
	 * 设置发送者编号
	 *
	 * @param value
	 *            发送者编号
	 */
	public void setSenderId(String value) {
		this.senderId = value;
	}

	/**
	 * 获取群组编号
	 *
	 * @return 群组编号
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * 设置群组编号
	 *
	 * @param value
	 *            群组编号
	 */
	public void setGroupId(String value) {
		this.groupId = value;
	}

	/**
	 * 获取消息创建时间
	 *
	 * @return 消息创建时间
	 */
	public Date getCreationTime() {
		return this.creationTime;
	}

	/**
	 * 设置消息创建时间
	 *
	 * @param value
	 *            消息创建时间
	 */
	public void setCreationTime(Date value) {
		this.creationTime = value;
	}

	public abstract MessageType getMessageType();

	@Override
	public abstract String toString();
}