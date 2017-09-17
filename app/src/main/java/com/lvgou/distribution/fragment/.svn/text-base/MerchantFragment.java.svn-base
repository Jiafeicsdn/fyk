package com.lvgou.distribution.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.MytalklistAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.entity.ClassesBackEntity;
import com.lvgou.distribution.presenter.UserDynamicPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.view.MytalklistView;
import com.lvgou.distribution.viewholder.ClassesBackViewHolder;
import com.xdroid.functions.holder.ListViewDataAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/5.
 */
public class MerchantFragment extends BaseFragment implements MytalklistView {
    private Context mContext;
    private WebView webView;
private String intro_content;
    private PullToRefreshListView pullToRefreshListView;
    public static ListView listView;
    private MytalklistAdapter mytalklistAdapter;
    UserDynamicPresenter userDynamicPresenter; View layout_webview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_view, container, false);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        listView = pullToRefreshListView.getRefreshableView();
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        userDynamicPresenter = new UserDynamicPresenter(this);
        initViewHolder();
        intro_content= getArguments().getString("Intro_Content");
        if(layout_webview==null) {
            layout_webview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_webview, null);
            listView.addHeaderView(layout_webview);
        }
        webView = (WebView) layout_webview.findViewById(R.id.web_view);
        webView.loadDataWithBaseURL(null, intro_content, "text/html", "utf-8", null);
        return view;
    }
    public void initViewHolder() {
        mytalklistAdapter = new MytalklistAdapter(getActivity(), userDynamicPresenter);
        mytalklistAdapter.setFengcircleData(new ArrayList<FengCircleDynamicBean>());
        listView.setAdapter(mytalklistAdapter);
    }

    @Override
    public void mytalklistResponse(String resonpse) {

    }

    @Override
    public void excuteFailedCallBack(String resonpse) {

    }

    @Override
    public void zanResponse(String resonpse, int position) {

    }

    @Override
    public void unzanResponse(String resonpse, int position) {

    }

    @Override
    public void talkdelResponse(String resonpse) {

    }

    @Override
    public void distributormainResponse(String resonpse) {

    }

    @Override
    public void followResponse(String resonpse, String position) {

    }

    @Override
    public void pullToRefresh() {

    }

    @Override
    public void refreshComplete() {

    }

    @Override
    public View getScrollableView() {
        return listView;
    }
}
