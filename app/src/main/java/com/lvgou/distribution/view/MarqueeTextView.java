package com.lvgou.distribution.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Snow on 2016/3/13 0013.
 * 字幕滚动
 */
public class MarqueeTextView extends TextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    //属性集 xml文件的配置
    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 欺骗了系统，以为我们这个textview是得到焦点了。
     */
    @Override
    public boolean isFocused() {
        return true;
    }

}
