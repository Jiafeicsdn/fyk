package com.lvgou.distribution.driect.entity;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Created by Echofool on 2015/12/25.
 */
public class MessageTypeJsonConverter implements JsonSerializer<MessageType>,
		JsonDeserializer<MessageType> {
	@Override
	public MessageType deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {
		JsonPrimitive primitive = jsonElement.getAsJsonPrimitive();
		if (primitive.isNumber()) {
			return MessageType.parse(primitive.getAsInt());
		}
		return MessageType.valueOf(primitive.getAsString());
	}

	@Override
	public JsonElement serialize(MessageType messageType, Type type,
			JsonSerializationContext jsonSerializationContext) {
		if (messageType != null) {
			return new JsonPrimitive(messageType.getName());
		}
		return null;
	}
}
