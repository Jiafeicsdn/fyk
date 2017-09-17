package com.xdroid.widget.loadmore;

import android.view.View;
import android.widget.AbsListView;
/**
 * 为LoadMoreContainerBase提供一些公开的方法
 * @author Robin
 *
 */
public interface LoadMoreContainer {
	/**
	 * 设置是否在第一页显示正在加载
	 * @param showLoading 是否显示正在加载 
	 */
    public void setShowLoadingForFirstPage(boolean showLoading);

    /**
     * 设置是否自动加载，为true时拉到底部自动加载，为false时则点击加载
     * @param autoLoadMore 是否自动加载
     */
    public void setAutoLoadMore(boolean autoLoadMore);

    /**
     * 设置滚动监听接口
     * @param l 滚动监听回调接口
     */
    public void setOnScrollListener(AbsListView.OnScrollListener l);

    /**
     * 设置加在更多的UI view
     * @param view 
     */
    public void setLoadMoreView(View view);

    /**
     * 设置加载更多的UI回调接口
     * @param handler 加载更多UI回调接口
     */
    public void setLoadMoreUIHandler(LoadMoreUIHandler handler);

    /**
     * 设置加载更多的回调接口
     * @param handler 加载更多回调接口
     */
    public void setLoadMoreHandler(LoadMoreHandler handler);

    /**
     * 加载完成，当数据加载完成时调用此方法更新状态
     *
     * @param emptyResult 是否是空数据
     * @param hasMore 是否还有更多数据待加载
     */
    public void loadMoreFinish(boolean emptyResult, boolean hasMore);

    /**
     * 加载失败时调用此方法
     * @param errorCode 错误码
     * @param errorMessage 错误信息
     */
    public void loadMoreError(int errorCode, String errorMessage);
}
