package com.xdroid.widget.loadmore;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xdroid.common.utils.ViewUtils;
/**
 * 默认显示的底部UI view 实现，若要自定义UI view，可参考此类
 * @author Robin
 *
 */
public class LoadMoreDefaultFooterView extends RelativeLayout implements LoadMoreUIHandler {

    private TextView mTextView;

    public LoadMoreDefaultFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews();
    }

    private void setupViews() {
        /*LayoutInflater.from(getContext()).inflate(R.layout.cube_views_load_more_default_footer, this);
        mTextView = (TextView) findViewById(R.id.cube_views_load_more_default_footer_text_view);*/
    	
     	mTextView=new TextView(getContext());
    	mTextView.setGravity(Gravity.CENTER);
    	mTextView.setTextColor(Color.rgb(107, 107, 107));
		ViewUtils.setTextSize(mTextView,30);
		ViewUtils.setPadding(mTextView, 0, 35, 0, 35);
		this.addView(mTextView);
		this.setGravity(Gravity.CENTER);
    }

    @Override
    public void onLoading(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText("加载中...");
    }

    @Override
    public void onLoadFinish(LoadMoreContainer container, boolean empty, boolean hasMore) {
        if (!hasMore) {
            setVisibility(VISIBLE);
            if (empty) {
            	 mTextView.setText("暂无数据");
            } else {
            	mTextView.setText("所有数据已加载");
            }
        } else {
            setVisibility(INVISIBLE);
        }
    }

    @Override
    public void onWaitToLoadMore(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText("点击加载更多");
    }

    @Override
    public void onLoadError(LoadMoreContainer container, int errorCode, String errorMessage) {
        mTextView.setText(errorMessage);
    }
}
