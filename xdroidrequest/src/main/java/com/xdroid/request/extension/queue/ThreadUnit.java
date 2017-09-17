package com.xdroid.request.extension.queue;

/**
 * Queue execution unit
 * @author RObin
 * @since 2015-05-07 09:48:32
 * @param <ReturnType>
 */
public class ThreadUnit<ReturnType>{
	
	private int position;
	 
    private ThreadListener<ReturnType> listener; 
    
	/**
	 * Instantiates a new ab task item.
	 */
	public ThreadUnit() {
		super();
	}

	/**
	 * Instantiates a new ab task item.
	 *
	 * @param listener the listener
	 */
	public ThreadUnit(ThreadListener<ReturnType> listener) {
		super();
		this.listener = listener;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Gets the listener.
	 *
	 * @return the listener
	 */
	public ThreadListener<ReturnType> getListener() {
		return listener;
	}

	/**
	 * Sets the listener.
	 *
	 * @param listener the new listener
	 */
	public void setListener(ThreadListener<ReturnType> listener) {
		this.listener = listener;
	}

}
