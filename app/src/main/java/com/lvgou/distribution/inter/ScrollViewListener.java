package com.lvgou.distribution.inter;

import com.lvgou.distribution.view.ObservableScrollView;

/**
 * Created by Snow on 2016/7/26 0026.
 */
public interface ScrollViewListener {
    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy,boolean isLsat);
}
