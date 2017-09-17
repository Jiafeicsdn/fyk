package com.lvgou.distribution.driect.entity;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 群组消息JSON转化器
 */
public class GroupMessageJsonConverter implements
		JsonDeserializer<GroupMessage>, JsonSerializer<GroupMessage> {
	/**
	 * 群组消息反序列化
	 *
	 * @param jsonElement
	 *            json元素
	 * @param type
	 *            类型信息
	 * @param jsonDeserializationContext
	 *            反序列化上下文
	 * @return 群组消息
	 * @throws JsonParseException
	 *             消息类型异常
	 */
	@Override
	public GroupMessage deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		//建立连接的地方在哪里
		System.out.println(jsonElement);
		MessageType messageType = MessageType.parse(jsonObject.get("MT")
				.getAsInt());
		Class<?> clazz;
		switch (messageType) {
//		case FileTransfer:
//			clazz = FileTransferGroupMessage.class;
//			break;
//		case Function:
//			clazz = FunctionGroupMessage.class;
//			break;
		case Image:
			clazz = DirectRowPictureEntity.class;
			break;
//		case SafetyAlert:
//			clazz = SafetyAlertGroupMessage.class;
//			break;
		case ShortVideo:
			clazz = ShortVideoGroupMessage.class;
			break;
		case Text:
			clazz = TextGroupMessage.class;
			break;
		case Voice:
			clazz = DirectRowVoiceEntity.class;
			break;
		default:
			throw new JsonParseException("messageType incorrect");
		}
		return jsonDeserializationContext.deserialize(jsonElement, clazz);
	}

	/**
	 * 群组消息序列化
	 *
	 * @param message
	 *            群组消息
	 * @param type
	 *            类型
	 * @param jsonSerializationContext
	 *            序列化上下文
	 * @return json元素
	 */
	@Override
	public JsonElement serialize(GroupMessage message, Type type,
			JsonSerializationContext jsonSerializationContext) {
		JsonElement jsonElement = jsonSerializationContext.serialize(message,
				message.getClass());
		return jsonElement;
	}
}