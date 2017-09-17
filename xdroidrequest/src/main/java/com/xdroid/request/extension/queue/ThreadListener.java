package com.xdroid.request.extension.queue;

/**
 * Threads execute callback
 * @author Robin
 * @since 2015-05-07 09:08:58
 */
public interface ThreadListener<ReturnType> {

	/**
	 * while the task execute finish
	 * @param data
	 */
	public void onFinish(ReturnType data);

	/**
	 * execute task in child  thread
	 * @return
	 */
	public ReturnType doInThread();

}
