package com.lvgou.distribution.viewholder;

import android.view.View;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.DefaultAdapter;
import com.lvgou.distribution.utils.UIUtils;

/**
 * Created by Administrator on 2016/8/18.
 */
public class MoreHolder extends BaseHolder<Integer>{
    public static final int HAS_MORE = 0;//有更多数据
    public static final int HAS_NO_MORE = 1;//没有更多数据   --->隐藏
    public static final int ERROR = 2;//加载失败   --->加载失败显示  其他隐藏


    private DefaultAdapter adapter;
    private boolean hasMore;
    private View rl_more_loading;
    private View rl_more_error;

    public MoreHolder(DefaultAdapter adapter, boolean hasMore) {

        this.adapter = adapter;
        this.hasMore = hasMore;
        if (!hasMore) {
            setData(HAS_NO_MORE);
        }
    }


    /**
     * 1.xml--- view
     * 2.findviewById  初始化操作
     *
     * @return
     */
    @Override
    public View initView() {
//        View view = UIUtils.inflate(R.layout.item_more);
//        rl_more_loading = view.findViewById(R.id.rl_more_loading);//加载中...
//        rl_more_error = view.findViewById(R.id.rl_more_error);//加载中中...
        return null;
    }

    /**
     * adapter中   调用more Holder 的getview 方法    就可以加载更多
     *
     * @return
     */
    @Override
    public View getContentView() {
        if (hasMore)
            loadMore();
        return super.getContentView();
    }

    /**
     * 加载更多 (请求服务器数据 将新的数据添加到原来的集合 中  notifyDataSetChange 更新listview)
     */
    private void loadMore() {
        //  自己没有集合  转交给 Adapter
        if (adapter != null) {
//            adapter.loadMore();
        }

    }

    /**
     * 设置数据  控制显示隐藏
     *
     * @param integer
     */
    @Override
    public void refreshView(Integer integer) {
        switch (integer) {
            case HAS_NO_MORE:
                //没有更多全部隐藏
                rl_more_loading.setVisibility(View.GONE);
                rl_more_error.setVisibility(View.GONE);
                break;
            case HAS_MORE:
                rl_more_loading.setVisibility(View.VISIBLE);
                rl_more_error.setVisibility(View.GONE);
                break;
            case ERROR:
                // 加载更多失败
                rl_more_loading.setVisibility(View.GONE);
                rl_more_error.setVisibility(View.VISIBLE);

                break;
        }
    }
}
