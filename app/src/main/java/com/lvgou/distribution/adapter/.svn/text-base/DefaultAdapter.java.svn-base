package com.lvgou.distribution.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lvgou.distribution.viewholder.BaseHolder;
import com.lvgou.distribution.viewholder.MoreHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public abstract class DefaultAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener {
    @Override
    public abstract int getCount();

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public static final int ITEM_DEFAULT = 0;//默认的条目类型
    public static final int ITEM_MORE = 1;//加载更多的条目类型
    public static final int ITEM_TITLE = 2;  //标题
    protected List<T> datas;
    private ListView listView;
    private MoreHolder moreHolder;

    public DefaultAdapter(List<T> datas, ListView listView) {
        this.datas = datas;
        this.listView = listView;
        listView.setOnItemClickListener(this);
    }


    // 条目显示的view对象
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;

        switch (getItemViewType(position)) {
            case ITEM_MORE://加载更多
                if (convertView == null) {
                    holder = getMoreHolder();
                } else {
                    holder = (BaseHolder) convertView.getTag();
                }

                break;
            default://普通条目
                if (convertView == null) {
                    holder = getHolder();
                } else {
                    holder = (BaseHolder) convertView.getTag();
                }
                T t = datas.get(position);
                holder.setData(t);
                break;
        }


        return holder.getContentView();
    }

    private BaseHolder getMoreHolder() {
        if (moreHolder == null)
            moreHolder = new MoreHolder(this, hasMore());
        return moreHolder;
    }

    /**
     * 默认有更多数据
     */
    public boolean hasMore() {
        return true;
    }



    /**
     * 请求服务器更多数据 返回集合
     *
     * @return
     */
    public List<T> load() {
        return null;
    }

    ;

    /**
     * 获取holder对象
     *
     * @return
     */
    public abstract BaseHolder<T> getHolder();

}
