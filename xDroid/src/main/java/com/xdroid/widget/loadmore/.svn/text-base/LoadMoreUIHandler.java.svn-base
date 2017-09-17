package com.xdroid.widget.loadmore;
/**
 * 加载更多的UI view 回调接口,若需要自定义UI view ，可实现此接口
 * @author Robin
 *
 */
public interface LoadMoreUIHandler {

	/**
     * 正在加载状态
     * @param container LoadMoreContainer实现类
     */
    public void onLoading(LoadMoreContainer container);

    /**
     * 加载完成状态
     * @param container LoadMoreContainer实现类
     * @param empty 获取到的是否是空数据
     * @param hasMore 是否有更多数据待加载
     */
    public void onLoadFinish(LoadMoreContainer container, boolean empty, boolean hasMore);

    /**
     * 等待加载状态，example：显示 "点击加载更多"
     * @param LoadMoreContainer实现类
     */
    public void onWaitToLoadMore(LoadMoreContainer container);

    /**
     * 加载失败状态
     * @param container LoadMoreContainer实现类
     * @param errorCode 错误码
     * @param errorMessage 错误信息
     */
    public void onLoadError(LoadMoreContainer container, int errorCode, String errorMessage);
}