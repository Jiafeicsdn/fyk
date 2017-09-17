package com.lvgou.distribution.constants;

/**
 * Created by Administrator on 2016/8/23.
 */
public enum MessageType1 {
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
    private MessageType1(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (MessageType1 messageType : MessageType1.values()) {
            if (messageType.getIndex() == index) {
                return messageType.getName();
            }
        }
        return null;
    }

    public static int getIndex(String name) {
        for (MessageType1 messageType : MessageType1.values()) {
            if (messageType.getName() == name) {
                return messageType.getIndex();
            }
        }
        return -1;
    }

    public static MessageType1 parse(int value) {
        for (MessageType1 c : MessageType1.values()) {
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
