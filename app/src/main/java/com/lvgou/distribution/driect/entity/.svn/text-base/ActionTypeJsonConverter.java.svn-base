package com.lvgou.distribution.driect.entity;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ActionTypeJsonConverter implements JsonDeserializer<ActionType>,JsonSerializer<ActionType>{

	@Override
	public JsonElement serialize(ActionType actionType, Type type,
			JsonSerializationContext jsonSerializationContext) {
		if (actionType != null) {
			return new JsonPrimitive(actionType.getName());
		}
		return null;
	}

	@Override
	public ActionType deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		JsonPrimitive primitive = jsonElement.getAsJsonPrimitive();
		if (primitive.isNumber()) {
			return ActionType.parse(primitive.getAsInt());
		}
		return ActionType.valueOf(primitive.getAsString());
	}

}
