package com.xdroid.widget.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 描述：包裹Listview，实现加载更多
 * 使用方法：
 * 1.布局
 *  <com.xdroid.widget.loadmore.LoadMoreListViewContainer
            android:id="@+id/load_more_list_view_container"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/cube_mints_white">

            <ListView
                android:id="@+id/load_more_small_image_list_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:divider="@null"
                android:fadingEdge="none"
                android:listSelector="@android:color/transparent"
                android:paddingLeft="12dp"
                android:paddingRight="12dp"
                android:scrollbarStyle="outsideOverlay" />

        </com.xdroid.widget.loadmore.LoadMoreListViewContainer>
        
 * 2.代码
        final LoadMoreListViewContainer loadMoreListViewContainer = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultHeader();   //使用默认的FooterView

        mListView.setAdapter(mAdapter); //Listview设置Adapter
        
        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {  //加载更多的监听
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mDataModel.queryNextPage();
            }
        });
        
        mPtrFrameLayout.addPtrUIHandler(new PtrUIRefreshCompleteHandler() {  //配合使用ptr,监听刷新完成
            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {
                loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(), mDataModel.getListPageInfo().hasMore());  //当刷新完成，调用此方法，传入的参数用以判断加载的是否是空数据以及是否有更多的数据待加载
                mAdapter.notifyDataSetChanged();
            }
        });
 */
public class LoadMoreListViewContainer extends LoadMoreContainerBase {

    private ListView mListView;

    public LoadMoreListViewContainer(Context context) {
        super(context);
    }

    public LoadMoreListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void addFooterView(View view) {
        mListView.addFooterView(view);
    }

    @Override
    protected void removeFooterView(View view) {
        mListView.removeFooterView(view);
    }

    @Override
    protected AbsListView retrieveAbsListView() {
        mListView = (ListView) getChildAt(0);
        return mListView;
    }
}
