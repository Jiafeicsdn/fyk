package com.lvgou.distribution.fragment;


import android.support.v4.app.Fragment;

import com.lvgou.distribution.utils.ScrollableHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements ScrollableHelper.ScrollableContainer{

    public abstract void pullToRefresh();
    public abstract void refreshComplete();
}
