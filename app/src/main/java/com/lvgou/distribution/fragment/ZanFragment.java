package com.lvgou.distribution.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.ZanListAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.ZanListBean;
import com.lvgou.distribution.presenter.ZanPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ZanFgView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public class ZanFragment extends Fragment implements ZanFgView {
    private PullToRefreshListView pull_refresh_list;
    private RelativeLayout rl_none;
    public static ListView listView;
    OnArticleSelectedListener mListener;
    private LinearLayout ll_visibility;
    private String distributorid;
    private int pageindex = 1;
    private ZanPresenter zanListpresenter;
    private String talkId;
    private String prePageLastDataObjectId = "";
    private int total_page;
    public ZanListAdapter zanListAdapter;
    private Context context;
    private List<ZanListBean> zanListBeans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_no_title_list, container, false);
        RelativeLayout rl_title= (RelativeLayout) view.findViewById(R.id.rl_title);
        rl_title.setVisibility(View.GONE);
        pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        ll_visibility = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        TextView rl_none_title=(TextView)view.findViewById(R.id.nono_data_title);
        rl_none_title.setText("还没有点赞，快快点赞吧！");
        listView = pull_refresh_list.getRefreshableView();
        mListener = (OnArticleSelectedListener) getActivity();
        distributorid = mListener.getDistributorId();
        talkId = mListener.getTalkId();
        zanListpresenter = new ZanPresenter(this);
        String sign = TGmd5.getMD5(distributorid + talkId + prePageLastDataObjectId + pageindex);
        zanListpresenter.talkzanlist(distributorid, talkId, prePageLastDataObjectId, pageindex, sign);
        init();
        return view;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteSuccessCallBack(String s) {
        pull_refresh_list.onRefreshComplete();
        try {
            if (pageindex == 1) {
                zanListAdapter.getZanList().clear();
            }
            JSONObject jsonObject = new JSONObject(s);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                if (jsonArray != null && jsonArray.length() > 0) {
                    Gson gson = new Gson();
//                gson.fromJson(jsonObject1.toString(),);
                    JSONArray jsonArray1 = jsonObject1.getJSONArray("Data");
                    List<ZanListBean> zanListBeans = gson.fromJson(jsonArray1.toString(), new TypeToken<List<ZanListBean>>() {
                    }.getType());
                    if (zanListBeans != null && zanListBeans.size() > 0) {
                        prePageLastDataObjectId = zanListBeans.get(zanListBeans.size() - 1).getID();
                        ll_visibility.setVisibility(View.VISIBLE);
                        rl_none.setVisibility(View.GONE);
                    } else {
                        ll_visibility.setVisibility(View.GONE);
                        rl_none.setVisibility(View.VISIBLE);
                    }
                    zanListAdapter.setZanList(zanListBeans);
                    int dataCount = jsonObject1.getInt("DataCount");
                    total_page = jsonObject1.getInt("DataPageCount");
                }
            }
            zanListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void ChangeUiView(int size) {
        if(size>0) {
            rl_none.setVisibility(View.GONE);
            ll_visibility.setVisibility(View.VISIBLE);
        }else{
            rl_none.setVisibility(View.VISIBLE);
            ll_visibility.setVisibility(View.GONE);
        }
    }
    @Override
    public void excuteFailedCallBack(String s) {
        pull_refresh_list.onRefreshComplete();
    }

    @Override
    public void excuteSuccessCallBack(String type, String s,int position) {
        switch (type) {
            case "follow":
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = (int) jsonObject.getInt("status");
                    if (status == 1) {
                        List<ZanListBean> list = zanListAdapter.getZanList();
                        if("3".equals(list.get(position).getFengwenDistributorID())) {
                            list.get(position).setFengwenDistributorID(String.valueOf(3));
                        }else{
                            list.get(position).setFengwenDistributorID(String.valueOf(2));
                        }
                        }
                        zanListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "unfollow":
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = (int) jsonObject.getInt("status");
                    if (status == 1) {
                        List<ZanListBean> list = zanListAdapter.getZanList();
                        list.get(position).setFengwenDistributorID(String.valueOf(1));
                    }
                    zanListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public interface OnArticleSelectedListener {
        public String getDistributorId();

        public String getTalkId();
    }

    private void init() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + talkId + prePageLastDataObjectId + pageindex);
                zanListpresenter.talkzanlist(distributorid, talkId, prePageLastDataObjectId, pageindex, sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageindex++;
                if (pageindex <= total_page) {
                    String sign = TGmd5.getMD5(distributorid + talkId + prePageLastDataObjectId + pageindex);
                    zanListpresenter.talkzanlist(distributorid, talkId, prePageLastDataObjectId, pageindex, sign);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pull_refresh_list.onRefreshComplete();
                        }
                    }, 1000);
                }
            }
        });
        initViewHolder();
    }

    public void initViewHolder() {
        zanListBeans = new ArrayList<>();
        zanListAdapter = new ZanListAdapter(context, zanListBeans,zanListpresenter);
        listView.setAdapter(zanListAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
