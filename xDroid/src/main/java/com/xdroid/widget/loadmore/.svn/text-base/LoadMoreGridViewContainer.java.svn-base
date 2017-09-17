package com.xdroid.widget.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

import com.xdroid.common.widget.GridViewWithHeaderAndFooter;

/**
 * 描述：包裹GridViewWithHeaderAndFooter，实现加载更多
 * 使用方法：
 * 1.布局
 *   <com.xdroid.widget.loadmore.LoadMoreGridViewContainer
            android:id="@+id/load_more_grid_view_container"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/cube_mints_white">

            <com.xdroid.common.widget.GridViewWithHeaderAndFooter
                android:id="@+id/load_more_grid_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:fadingEdge="none"
                android:focusable="false"
                android:horizontalSpacing="10dp"
                android:listSelector="@android:color/transparent"
                android:numColumns="2"
                android:paddingLeft="12dp"
                android:paddingRight="12dp"
                android:scrollbarStyle="outsideOverlay"
                android:stretchMode="columnWidth" />

        </com.xdroid.widget.loadmore.LoadMoreGridViewContainer>
        
 *2.代码
        final LoadMoreGridViewContainer loadMoreContainer = (LoadMoreGridViewContainer) view.findViewById(R.id.load_more_grid_view_container);
        loadMoreContainer.setAutoLoadMore(false); //设置不自动加载，点击加载
        loadMoreContainer.useDefaultHeader();

        mGridView.setAdapter(mAdapter); //设置Gridview的Adapter
        
        loadMoreContainer.setLoadMoreHandler(new LoadMoreHandler() {  //添加加载更多的监听
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });
        
         mPtrFrameLayout.addPtrUIHandler(new PtrUIRefreshCompleteHandler() {  //配合使用ptr,监听刷新完成
            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {
                loadMoreContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());   //当刷新完成，调用此方法，传入的参数用以判断加载的是否是空数据以及是否有更多的数据待加载
                mAdapter.notifyDataSetChanged();
            }
        });
 *
 */
public class LoadMoreGridViewContainer extends LoadMoreContainerBase {

    private GridViewWithHeaderAndFooter mGridView;

    public LoadMoreGridViewContainer(Context context) {
        super(context);
    }

    public LoadMoreGridViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void addFooterView(View view) {
        mGridView.addFooterView(view);
    }

    @Override
    protected void removeFooterView(View view) {
        mGridView.removeFooterView(view);
    }

    @Override
    protected AbsListView retrieveAbsListView() {
        View view = getChildAt(0);
        mGridView = (GridViewWithHeaderAndFooter) view;
        return mGridView;
    }
}