package com.lvgou.distribution.driect.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * 点对点消息
 */
public abstract class SingleMessage {

	@SerializedName("I")
	@Expose(serialize = false, deserialize = true)
	public String id;

	@SerializedName("SI")
	@Expose(serialize = false, deserialize = true)
	public String senderId;

	@Expose(serialize = true, deserialize = true)
	@SerializedName("RI")
	public String receiverId;

	@SerializedName("CT")
	@Expose(serialize = false, deserialize = true)
	public Date creationTime;

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
	 * 获取接收者编号
	 *
	 * @return 接收者编号
	 */
	public String getReceiverId() {
		return this.receiverId;
	}

	/**
	 * 设置接收者编号
	 *
	 * @param value
	 *            接收者编号
	 */
	public void setReceiverId(String value) {
		this.receiverId = value;
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

	/**
	 * 获取消息类型
	 *
	 * @return 消息类型
	 */
	public abstract MessageType getMessageType();

	/**
	 * 将点对点消息转换成字符串表述形式，主要用于监控及调试
	 *
	 * @return 点对点消息转字符串表述形式
	 */
	@Override
	public abstract String toString();
}
