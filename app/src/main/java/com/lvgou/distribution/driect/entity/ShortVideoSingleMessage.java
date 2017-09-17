package com.lvgou.distribution.driect.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShortVideoSingleMessage extends SingleMessage {

	@Expose(serialize = true, deserialize = false)
	@SerializedName("MT")
	private final MessageType messageType = MessageType.ShortVideo;

	@Expose(serialize = true, deserialize = true)
	@SerializedName("U")
	private String url;
	// 缩略图url形式
	@Expose(serialize = true, deserialize = true)
	@SerializedName("CI")
	private String coverImage;

	/**
	 * 获取 小视频地址
	 *
	 * @return 小视频地址
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * 设置小视频地址
	 *
	 * @param value
	 *            小视频地址
	 */
	public void setUrl(String value) {
		this.url = value;
	}

	/**
	 * 获取 封面图地址
	 *
	 * @return 封面图地址
	 */
	public String getCoverImage() {
		return this.coverImage;
	}

	/**
	 * 设置封面图地址
	 *
	 * @param value
	 *            封面图地址
	 */
	public void setCoverImage(String value) {
		this.coverImage = value;
	}

	/**
	 * 获取消息类型
	 *
	 * @return 小视频消息类型
	 */
	@Override
	public MessageType getMessageType() {
		return this.messageType;
	}

	/**
	 * 将功能推送点对点消息转换成字符串表述形式，主要用于监控及调试
	 *
	 * @return 功能名称
	 */
	@Override
	public String toString() {
		return this.url;
	}
}
