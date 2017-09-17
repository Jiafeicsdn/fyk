package com.lvgou.distribution.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Describe the function of the class
 *
 * @author zhujinlong@ichoice.com
 * @date 2016/3/12
 * @time 15:05
 * @description Describe the place where the class needs to pay attention.
 */
public class XRecyclerView extends RecyclerView {

    public XRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //加载更多
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = manager.getItemCount();
                if (dy > 0 && lastVisibleItem >= totalItemCount - 1) {
                    if(listener != null){
                        listener.onLodNextPage();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public interface OnLoadNextPageListener{

        void onLodNextPage();
    }

    private OnLoadNextPageListener listener;

    public void setOnLoadNextPageListener(OnLoadNextPageListener listener) {
        this.listener = listener;
    }
}
