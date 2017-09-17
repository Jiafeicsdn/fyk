package com.xdroid.widget.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

/**
 * 加载更多 基类view，example：由LoadMoreListViewContainer与LoadMoreGridViewContainer继承即可实现Listview与Gridview的加载更多
 * @author Robin
 */
public abstract class LoadMoreContainerBase extends LinearLayout implements LoadMoreContainer {
	/*=================================================================
	 *  接口声明 
	 *=================================================================
	 */
	/**
	 * 滚动监听回调
	 */
    private AbsListView.OnScrollListener mOnScrollListener;
    /**
     * 加载更多的UI view 回调接口
     */
    private LoadMoreUIHandler mLoadMoreUIHandler;
    /**
     * 加载更多回调接口
     */
    private LoadMoreHandler mLoadMoreHandler;

	/*=================================================================
	 *  状态变量声明 
	 *=================================================================
	 */
    /**
     * 是否正在加载
     */
    private boolean mIsLoading;
    /**
     * 是否还有更多数据待加载
     */
    private boolean mHasMore = false;
    /**
     * 是否自动加载更多
     */
    private boolean mAutoLoadMore = true;
    /**
     * 是否加载失败
     */
    private boolean mLoadError = false;
    /**
     * 获取到的是否是空数据
     */
    private boolean mListEmpty = true;
    /**
     * 是否在第一页显示正在加载
     */
    private boolean mShowLoadingForFirstPage = false;
    
	/*=================================================================
	 *  View变量声明 
	 *=================================================================
	 */
    /**
     * 加载更多时显示的底部UI view
     */
    private View mFooterView;

    private AbsListView mAbsListView;

    public LoadMoreContainerBase(Context context) {
        super(context);
    }

    public LoadMoreContainerBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAbsListView = retrieveAbsListView();  //检索包裹的AbsListview
        init();
    }

    /**
     * 使用默认的底部UI view
     */
    public void useDefaultFooter() {
        LoadMoreDefaultFooterView footerView = new LoadMoreDefaultFooterView(getContext());
        footerView.setVisibility(GONE);
        setLoadMoreView(footerView);
        setLoadMoreUIHandler(footerView);
    }
    
    /**
     * 外部公开方法 获取是否加载失败
     * @return mLoadError
     */
    public Boolean isLoadError(){
    	return mLoadError;
    }

    /**
     * 初始化，添加底部UI view与滚动监听逻辑判断
     */
    private void init() {

        if (mFooterView != null) {
            addFooterView(mFooterView);
        }

        mAbsListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private boolean mIsEnd = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (null != mOnScrollListener) {
                    mOnScrollListener.onScrollStateChanged(view, scrollState);
                }
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (mIsEnd) {
                        onReachBottom();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (null != mOnScrollListener) {
                    mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                if (firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
                    mIsEnd = true;
                } else {
                    mIsEnd = false;
                }
            }
        });
    }

    /**
     * 执行加载更多操作
     */
    private void tryToPerformLoadMore() {
        if (mIsLoading) {
            return;
        }

        // no more content and also not load for first page
        if (!mHasMore && !(mListEmpty && mShowLoadingForFirstPage)) {
            return;
        }

        mIsLoading = true;

        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoading(this);
        }
        if (null != mLoadMoreHandler) {
            mLoadMoreHandler.onLoadMore(this);
        }
    }

    /**
     * 滚动到达底部，此时可以判断执行加载更多操作
     */
    private void onReachBottom() {
        // if has error, just leave what it should be
        if (mLoadError) {
            return;
        }
        if (mAutoLoadMore) {
            tryToPerformLoadMore();
        } else {
            if (mHasMore) {
                mLoadMoreUIHandler.onWaitToLoadMore(this);
            }
        }
    }

	/*=================================================================
	 *  复写LoadMoreContainer接口的公开函数，供外部调用
	 *=================================================================
	 */
    
    @Override
    public void setShowLoadingForFirstPage(boolean showLoading) {
        mShowLoadingForFirstPage = showLoading;
    }

    @Override
    public void setAutoLoadMore(boolean autoLoadMore) {
        mAutoLoadMore = autoLoadMore;
    }

    @Override
    public void setOnScrollListener(AbsListView.OnScrollListener l) {
        mOnScrollListener = l;
    }

    @Override
    public void setLoadMoreView(View view) {
        // has not been initialized
        if (mAbsListView == null) {
            mFooterView = view;
            return;
        }
        // remove previous
        if (mFooterView != null && mFooterView != view) {
            removeFooterView(view);
        }

        // add current
        mFooterView = view;
        mFooterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToPerformLoadMore();
            }
        });

        addFooterView(view);
    }

    @Override
    public void setLoadMoreUIHandler(LoadMoreUIHandler handler) {
        mLoadMoreUIHandler = handler;
    }

    @Override
    public void setLoadMoreHandler(LoadMoreHandler handler) {
        mLoadMoreHandler = handler;
    }

    /**
     * page has loaded
     *
     * @param emptyResult
     * @param hasMore
     */
    @Override
    public void loadMoreFinish(boolean emptyResult, boolean hasMore) {
        mLoadError = false;
        mListEmpty = emptyResult;
        mIsLoading = false;
        mHasMore = hasMore;

        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadFinish(this, emptyResult, hasMore);
        }
    }

    @Override
    public void loadMoreError(int errorCode, String errorMessage) {
        mIsLoading = false;
        mLoadError = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadError(this, errorCode, errorMessage);
        }
    }

    protected abstract void addFooterView(View view);

    protected abstract void removeFooterView(View view);

    protected abstract AbsListView retrieveAbsListView();
}