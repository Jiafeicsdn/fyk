package com.lvgou.distribution.driect.entity;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 文本点对点消息
 */
public class TextSingleMessage extends SingleMessage {

	@Expose(serialize = true, deserialize = false)
	@SerializedName("MT")
	private final MessageType messageType;

	@Expose(serialize = true, deserialize = true)
	@SerializedName("C")
	private String content;

	/**
	 * 初始化 文本点对点消息
	 */
	public TextSingleMessage() {
		this.messageType = MessageType.Text;
	}

	/**
	 * 获取文本消息内容
	 *
	 * @return 文本消息内容
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置文本消息内容
	 *
	 * @param value
	 *            文本消息内容
	 */
	public void setContent(String value) {
		this.content = value;
	}

	/**
	 * 获取消息类型
	 *
	 * @return 文本消息类型
	 */
	@Override
	public MessageType getMessageType() {
		return this.messageType;
	}

	/**
	 * 将文本点对点消息转换成字符串表述形式，主要用于监控及调试
	 *
	 * @return 文本消息内容
	 */
	@Override
	public String toString() {
		return this.content;
	}
	
	
}
