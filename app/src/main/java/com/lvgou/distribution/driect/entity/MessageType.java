package com.lvgou.distribution.driect.entity;

public enum MessageType {
	/*
	 * �文本类型
	 */
	Text("Text", 0),
	/*
	 * 图片类型
	 */
	Image("Image", 1),
	/*
	 * 语音类型
	 */
	Voice("Voice", 2),
	/*
	 * 小视频类型
	 */
	ShortVideo("ShortVideo", 3),
	/*
	 * �文件传输
	 */
	FileTransfer("FileTransfer", 4),
	/*
	 * �安全提醒�
	 */
	SafetyAlert("SafetyAlert", 5),
	/*
	 * �功能推送
	 */
	Function("Function", 6),
	/*
	 * �操作通知
	 */
	ActionNotify("ActionNotify", 7);

	private String name;
	private int index;

	/*
	 * �消息类型
	 */
	private MessageType(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static String getName(int index) {
		for (MessageType messageType : MessageType.values()) {
			if (messageType.getIndex() == index) {
				return messageType.getName();
			}
		}
		return null;
	}

	public static int getIndex(String name) {
		for (MessageType messageType : MessageType.values()) {
			if (messageType.getName() == name) {
				return messageType.getIndex();
			}
		}
		return -1;
	}

	public static MessageType parse(int value) {
		for (MessageType c : MessageType.values()) {
			if (c.getIndex() == value) {
				return c;
			}
		}
		throw new IllegalArgumentException("value is incorrect");
	}

	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}
}
