package com.lvgou.distribution.driect.entity;

import java.lang.reflect.Type;

import com.google.gson.reflect.*;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 功能推送JSON转化器
 */
public class ActionNotificationJsonConverter implements
		JsonDeserializer<ActionNotification>, JsonSerializer<ActionNotification> {
	/**
	 * 功能推送反序列化
	 *
	 * @param jsonElement
	 *            json元素
	 * @param type
	 *            类型信息
	 * @param jsonDeserializationContext
	 *            反序列化上下文
	 * @return 功能推送消息
	 * @throws JsonParseException
	 *             消息类型异常
	 */
	@Override
	public ActionNotification deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		ActionType actionType = ActionType.parse(jsonObject.get("AT")
				.getAsInt());
		Class<?> clazz;
		switch (actionType) {
//		case GroupRename:
//			clazz=GroupRenameNoticication.class;
//			break;
		case JoinGroup:
			clazz=JoinGroupNotification.class;
			break;
		case RemovedFromGroup:
			clazz=RemovedFromGroupNotification.class;
			break;
//		case CreateGroup:
//			clazz=CreateGroupNotification.class;
//			break;
//		case ExitGroup:
//			clazz=ExitGroupNotification.class;
//			break;
//		case GroupDissolution:
//			clazz=GroupDissolutionNotification.class;
//			break;
//		case FriendRemarkModify:
//			clazz=FriendRemarkModifyNotification.class;
//			break;
//		case FriendRequest:
//			clazz=FriendRequestNotification.class;
//			break;
//		case FriendRequestReply:
//			clazz=FriendRequestReplyNotification.class;
//			break;
//		case FriendRequestAllow:
//			clazz=FriendRequestAllowNotification.class;
//			break;
		case FriendRemoved:
			throw new JsonParseException("actionType FriendRemoved Not Implemented");
		case UserAvatarModify:
			throw new JsonParseException("actionType UserAvatarModify Not Implemented");
		case UserNickNameModify:
			throw new JsonParseException("actionType UserNickNameModify Not Implemented");
		default:
			throw new JsonParseException("actionType incorrect");
		}
		return  jsonDeserializationContext.deserialize(jsonElement, clazz);
	}

	/**
	 * 功能推送消息序列化
	 *
	 * @param message
	 *            功能推送
	 * @param type
	 *            类型
	 * @param jsonSerializationContext
	 *            序列化上下文
	 * @return json元素
	 */
	@Override
	public JsonElement serialize(ActionNotification message, Type type,
			JsonSerializationContext jsonSerializationContext) {
		JsonElement jsonElement = jsonSerializationContext.serialize(message,
				message.getClass());
		return jsonElement;
	}
}