package com.lvgou.distribution.event;

/**
 * 事件类
 * @author Robin
 * time 2015-04-22 14:19:20
 */
public class Event <T>{
	/**
	 * 附加数据
	 */
	private T extraData;
	/**
	 * 事件类型
	 */
	private EventType eventType;
	
	public Event(EventType eventType) {
		super();
		this.eventType = eventType;
	}
	public Event(EventType eventType,T extraData) {
		super();
		this.eventType = eventType;
		this.extraData = extraData;
	}
	public T getExtraData() {
		return extraData;
	}
	public void setExtraData(T extraData) {
		this.extraData = extraData;
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	@Override
	public String toString() {
		return "Event [extraData=" + extraData + ", eventType=" + eventType
				+ "]";
	}

}
